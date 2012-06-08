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
public class Client extends Connection {

    private Socket socket;
    private Cell firstCell;
    private Object syncSockets = new Object();

    public Client(PageSharingData connectData) {

        try {
            this.shareName = connectData.getConnectName();
            address = connectData.getIp();
            this.socket = connectData.getConnectedSocket();
            this.pageSharingController = connectData.getConnectController();
            connectedWorkbook = connectData.getSpreadsheet().getWorkbook();
            this.connectedSpreadsheet = connectData.getSpreadsheet();
            this.firstCell = connectData.getCell();

            connectedSpreadsheet.addCellListener(this);
            this.connectedCells = new ArrayList<Cell>();
            this.connectedFrom = new ArrayList<Address>();
            start();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }

    }

    @Override
    public void closeSockets() {
        PrintWriter out;
        try {
            out = new PrintWriter(getSocket().getOutputStream(), true);
            out.println("closeSocket\n");
            out.flush();
            getSocket().close();
        } catch (Exception ex) {
        }
    }

    @Override
    public void valueChanged(Cell cell) {
        //
    }

    @Override
    public void contentChanged(Cell cell) {
        PrintWriter out;
        for (int i = 0; i < connectedCells.size(); i++) {
            if (cell.equals(connectedCells.get(i))) {
                synchronized (syncSockets) {
                    try {
                        out = new PrintWriter(getSocket().getOutputStream(), true);
                        out.println(cell.getAddress() + "," + cell.getContent() + "\n");
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
        //
    }

    @Override
    public void cellCleared(Cell cell) {
        //
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
     * @return the socket
     */
    public Socket getSocket() {
        return socket;
    }

    class Receive implements Runnable {

        public void run() {
            BufferedReader in;
            String stream;
            String cellInfo[] = new String[2];
            try {

                while (true) {
                    in = new BufferedReader(new InputStreamReader(getSocket().getInputStream()));
                    stream = in.readLine();
                    try {
                        if (stream.contains("closeSocket")) {
                            break;
                        }
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
        String data[] = new String[4];
        int rowNumber = 0, colNumber = 0, firstRowFromConnected = 0, firstColFromConnected = 0;

        Address firstCellAddress = firstCell.getAddress();
        int firstCellRow = firstCellAddress.getRow();
        int firstCellColumn = firstCellAddress.getColumn();

        try {

            BufferedReader in = new BufferedReader(new InputStreamReader(getSocket().getInputStream()));
            PrintWriter out = new PrintWriter(getSocket().getOutputStream(), true);

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

            setType("Client");
            if (!pageSharingController.isConnectedTo(getType(), getConnectedCells().get(0),
                    getConnectedCells().get(getConnectedCells().size() - 1),
                    getSpreadsheet(), getWorkbook())) {
                
                pageSharingController.clientConfigured(this);
                Thread receiver = new Thread(this.new Receive());
                receiver.start();
                receiver.join();
                getSocket().close();
                pageSharingController.connectionRemoved(this);

            } else {
                JOptionPane.showMessageDialog(null, "Unable to create connection, already connected to that server!");
                pageSharingController.connectionRemoved(this);
            }

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
}
