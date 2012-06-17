/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package csheets.ext.share.connect;

import csheets.core.Address;
import csheets.core.Cell;
import csheets.core.formula.compiler.FormulaCompilationException;
import csheets.ext.share.PageSharingController;
import csheets.ext.share.PageSharingData;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 * Subclasse de "Connection", trata-se de um cliente, que se liga a uma partilha
 *
 * @author Tiago
 */
public class Client extends Connection {

    /**
     * A socket que o liga ao servidor
     */
    protected Socket socket;
    /**
     * A primeira célula da partilha
     */
    private Cell firstCell;
    /**
     * O objecto que se encarrega de manter a sincronização de Threads
     */
    private Object syncSockets = new Object();

    public Client(PageSharingData connectData) {

        try {
            this.shareName = connectData.getShareName();
            address = connectData.getIp();
            this.socket = connectData.getConnectedSocket();
            connectedWorkbook = connectData.getSpreadsheet().getWorkbook();
            this.connectedSpreadsheet = connectData.getSpreadsheet();
            this.firstCell = connectData.getCell();

            connectedSpreadsheet.addCellListener(this);
            this.connectedCells = new ArrayList<Cell>();
            this.connectedFrom = new ArrayList<Address>();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }

    }

    /**
     * Quando do outro lado a conecção é fechada é recolhida na Thread Receive
     * que por sua vez chama este método para fechar também esta conecção
     */
    @Override
    public void closeSockets() {
        PrintWriter out;
        try {
            out = new PrintWriter(socket.getOutputStream(), true);
            out.println("closeSocket\n");
            out.flush();
            socket.close();

        } catch (Exception ex) {
        }
    }

    /**
     * ESCUTA DO EVENTO DE MUDANÇA NAS CÉLULAS
     */
    @Override
    public void valueChanged(Cell cell) {
    }

    @Override
    public void contentChanged(Cell cell) {
        PrintWriter out;
        for (int i = 0; i < connectedCells.size(); i++) {
            if (cell.equals(connectedCells.get(i))) {
                synchronized (syncSockets) {
                    try {
                        out = new PrintWriter(socket.getOutputStream(), true);
                        out.write(cell.getAddress() + "," + cell.getContent() + "\n");
                        out.flush();
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(null, e.getMessage());
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
        PrintWriter out;
        for (int i = 0; i < connectedCells.size(); i++) {
            if (cell.equals(connectedCells.get(i))) {
                synchronized (syncSockets) {
                    try {
                        out = new PrintWriter(socket.getOutputStream(), true);
                        out.write(cell.getAddress() + "," + cell.getContent() + "\n");
                        out.flush();
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(null, e.getMessage());
                    }
                }
            }
        }
    }

    @Override
    public void cellCopied(Cell cell, Cell source) {
        //
    }

    @Override
    public void removeListeners() {
        connectedSpreadsheet.removeCellListener(this);
    }

    /**
     * RUN DO CLIENT
     */
    @Override
    public void run() {
        String stream;
        String data[] = new String[4];
        int rowNumber = 0, colNumber = 0, firstRowFromConnected = 0, firstColFromConnected = 0;

        Address firstCellAddress = firstCell.getAddress();
        int firstCellRow = firstCellAddress.getRow();
        int firstCellColumn = firstCellAddress.getColumn();

        try {

            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

            stream = in.readLine();
            out.println(firstCellAddress.getColumn() + "," + firstCellAddress.getRow() + "\n");
            out.flush();
            data = stream.split(",");

            rowNumber = Integer.parseInt(data[0]);
            colNumber = Integer.parseInt(data[1]);

            firstColFromConnected = Integer.parseInt(data[2]);
            firstRowFromConnected = Integer.parseInt(data[3]);

            for (int i = 0; i < rowNumber; i++) {
                for (int j = 0; j < colNumber; j++) {
                    connectedCells.add(connectedSpreadsheet.getCell((firstCellColumn + j), (firstCellRow + i)));
                    Address addrAux = new Address((firstColFromConnected + j), (firstRowFromConnected + i));
                    connectedFrom.add(addrAux);
                }
            }



                if (!PageSharingController.getInstance().isConnectedTo(getType(), getConnectedCells().get(0),
                        getConnectedCells().get(getConnectedCells().size() - 1),
                        getSpreadsheet(), getWorkbook())) {

                    PageSharingController.getInstance().clientConfigured(this);
                    Thread receiver = new Thread(this.new Receive());
                    receiver.start();
                    receiver.join();
                    socket.close();
                    //PageSharingController.getInstance().connectionRemoved(this,false);

                } else {
                    JOptionPane.showMessageDialog(null, "Unable to create connection, already connected to that server!");
                    //PageSharingController.getInstance().connectionRemoved(this,false);
                }

            } catch (Exception ex) {
                //PageSharingController.getInstance().connectionRemoved(this);
            }
            JOptionPane.showMessageDialog(null, type + " to " + socket.getInetAddress().getHostAddress() + " \"" + shareName + "\" connection closed!");
            PageSharingController.getInstance().connectionRemoved(this, false);
        }

        /**
         * GETS E SETS
         */
        /**
         * @return the socket
         */
    

    public Socket getSocket() {
        return socket;
    }

    /**
     * Thread RECEIVE
     */
    /**
     * Esta classe encarrega-se de receber informação pela socket e a colocar no
     * seu devido lugar. Também se encarrega de verificar se a socket se mantém
     * activa e fechar o Client se for o caso
     */
    class Receive implements Runnable {

        public void run() {
            BufferedReader in;
            String stream;
            String cellInfo[] = new String[2];
            try {
                while (true) {

                    in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    stream = in.readLine();

                    if (stream.contains("closeSocket")) {
                        closeSockets();
                        break;
                    }

                    if (stream.contains("interrupted")) {
                        JOptionPane.showMessageDialog(null, "Connection to "
                                + (socket.getInetAddress().toString().split("/"))[1] + " of the area from "
                                + connectedCells.get(0) + " to "
                                + connectedCells.get(connectedCells.size() - 1)
                                + " interrupted.");
                    } else if (stream.contains("resumed")) {
                        JOptionPane.showMessageDialog(null, "Connection to "
                                + (socket.getInetAddress().toString().split("/"))[1] + " of the area from "
                                + connectedCells.get(0) + " to "
                                + connectedCells.get(connectedCells.size() - 1)
                                + " resumed.");
                    } else {

                        cellInfo = stream.split(",");

                        for (int i = 0; i < connectedCells.size(); i++) {
                            for (int j = 0; j < connectedCells.size(); j++) {
                                if (connectedFrom.get(i).toString().contains(cellInfo[0])) {
                                    try {
                                        connectedCells.get(i).setContent(cellInfo[1]);
                                    } catch (FormulaCompilationException ex) {
                                        JOptionPane.showMessageDialog(null, "Error on receiving connected content!");

                                    }
                                }
                            }
                        }


                    }
                }
            } catch (Exception ex) {
            }
        }
    }
}
