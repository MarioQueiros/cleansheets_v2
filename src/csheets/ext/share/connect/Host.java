/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package csheets.ext.share.connect;

import csheets.core.Address;
import csheets.core.Cell;
import csheets.core.formula.compiler.FormulaCompilationException;
import csheets.ext.share.PageSharingData;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author Tiago
 */
public class Host extends Connection {

    private Socket clntSocket;
    private int rowNumber;
    private int colNumber;
    private Object syncSockets = new Object();
    private boolean connected = false;
    private boolean interrupted = false;

    public Host(PageSharingData connectData, String shareName) {

        try {
            this.shareName = shareName;
            this.pageSharingController = connectData.getConnectController();
            this.connectedCells = connectData.getCellConnected();

            this.connectedSpreadsheet = connectData.getSpreadsheet();
            connectedWorkbook = connectData.getSpreadsheet().getWorkbook();
            this.rowNumber = connectData.getRowNumber();
            this.colNumber = connectData.getColNumber();
            this.connectedFrom = new ArrayList<Address>();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }

    }

    @Override
    public void valueChanged(Cell cell) {
    }

    @Override
    public void contentChanged(Cell cell) {
        PrintWriter out;
        if(!isInterrupted()){
            for (int i = 0; i < connectedCells.size(); i++) {
                if (cell.equals(connectedCells.get(i))) {
                    synchronized (syncSockets) {
                        try {
                            out = new PrintWriter(clntSocket.getOutputStream(), true);
                            out.println(cell.getAddress() + "," + cell.getContent() + "\n");
                            out.flush();
                        } catch (Exception e) {
                            JOptionPane.showMessageDialog(null, e.getMessage());
                        }
                    }
                }
            }
        }

    }

    @Override
    public void dependentsChanged(Cell cell) {
    }

    @Override
    public void cellCleared(Cell cell) {
    }

    @Override
    public void cellCopied(Cell cell, Cell source) {
    }

    @Override
    public void removeListeners() {
        connectedSpreadsheet.removeCellListener(this);
    }

    @Override
    public void closeSockets() {

        PrintWriter out;
        try {
            out = new PrintWriter(clntSocket.getOutputStream(), true);
            out.println("closeSocket\n");
            out.flush();
            clntSocket.close();
        } catch (Exception ex) {
        }

    }

    /**
     * @return the connected
     */
    public boolean isConnected() {
        return connected;
    }

    void setConnected(boolean b) {
        connected = true;
    }

    /**
     * @param clntSocket the clntSocket to set
     */
    public void setClientSocket(Socket clntSocket) {
        this.clntSocket = clntSocket;
        this.address = clntSocket.getLocalAddress();
    }

    /**
     * @return the clntSocket
     */
    public Socket getClientSocket() {
        return clntSocket;
    }

    /**
     * @return the interrupted
     */
    public boolean isInterrupted() {
        return interrupted;
    }

    /**
     * @param interrupted the interrupted to set
     */
    public void setInterrupted(boolean interrupted) {
        this.interrupted = interrupted;
    }

    class Receive implements Runnable {

        public void run() {
            BufferedReader in;
            String stream;
            String cellInfo[] = new String[2];
            try {

                while (true) {

                    in = new BufferedReader(new InputStreamReader(clntSocket.getInputStream()));
                    stream = in.readLine();

                    try {
                        if (stream.contains("closeSocket")) {
                            break;
                        }

                        if (isInterrupted()) {
                            
                        } else {
                            cellInfo = stream.split(",");

                            synchronized (syncSockets) {
                                for (int i = 0; i < getConnectedCells().size(); i++) {
                                    if (connectedFrom.get(i).toString().contains(cellInfo[0])) {
                                        try {
                                            getConnectedCells().get(i).setContent(cellInfo[1]);
                                        } catch (FormulaCompilationException ex) {
                                            JOptionPane.showMessageDialog(null, "Error on receiving connected content!");
                                        }
                                    }
                                }
                            }
                        }
                    } catch (Exception e) {
                    }
                }

            } catch (Exception ex) {
            }

        }
    }

    @Override
    public void run() {

        String stream;
        String data[] = new String[2];
        int firstRowFromConnected = 0, firstColFromConnected = 0;

        try {

            connectedSpreadsheet.addCellListener(this);
            BufferedReader in = new BufferedReader(new InputStreamReader(clntSocket.getInputStream()));
            PrintWriter out = new PrintWriter(clntSocket.getOutputStream(), true);
            out.println(rowNumber + "," + colNumber + "," + getConnectedCells().get(0).getAddress().getColumn() + "," + getConnectedCells().get(0).getAddress().getRow() + "\n");
            out.flush();
            stream = in.readLine();
            data = stream.split(",");

            firstColFromConnected = Integer.parseInt(data[0]);
            firstRowFromConnected = Integer.parseInt(data[1]);

            for (int i = 0; i < rowNumber; i++) {
                for (int j = 0; j < colNumber; j++) {
                    Address addrAux = new Address((firstColFromConnected + j), (firstRowFromConnected + i));
                    connectedFrom.add(addrAux);
                }
            }

            Thread receiver = new Thread(this.new Receive());

            receiver.start();
            receiver.join();

            clntSocket.close();

            pageSharingController.connectionRemoved(this);

        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (Exception ex) {
            if (ex.getMessage().contains("socket closed")) {
                JOptionPane.showMessageDialog(null, "Connection Closed!");
            } else if (ex.getMessage().contains("Connection reset")) {
                pageSharingController.connectionRemoved(this);
                JOptionPane.showMessageDialog(null, "Connection Closed!");
            } else {
                JOptionPane.showMessageDialog(null, ex.getMessage());
            }
        }
    }

    class HandlerOneClient extends Thread {

        private Socket socket;

        public HandlerOneClient(Socket s) throws IOException {
            this.socket = s;
            System.out.println("Server Handler: started for client " + this.socket);
            start(); //calls run() method
        }

        public @Override
        void run() {
            try {
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

                //server reads client messages until "END" comes in
                while (true) {
                    String str = in.readLine();

                    if (str.equalsIgnoreCase("END")) {
                        break;
                    }

                    System.out.println("Server Handler: echoing " + str + " for client on port " + socket.getPort());
                    out.println(str);
                }

            } catch (IOException e) {
                System.err.println(e.getMessage());

            } finally {
                System.out.println("Server Handler: closing client socket...");

                try {
                    socket.close();
                } catch (IOException e) {
                    System.err.println(e.getMessage());
                }
            }
        }
    }
}
