/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package csheets.sp;

import csheets.core.Address;
import csheets.core.Cell;
import csheets.core.Spreadsheet;
import csheets.core.formula.compiler.FormulaCompilationException;
import csheets.ext.assertion.AssertableCell;
import csheets.ext.assertion.AssertionExtension;
import csheets.ext.assertion.USAssertion;
import csheets.ext.style.StylableCell;
import csheets.ext.style.StyleExtension;
import csheets.ui.ctrl.EditEvent;
import csheets.ui.ctrl.UIController;
import java.awt.Color;
import java.awt.Font;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.SortedSet;
import javax.swing.JOptionPane;

/**
 *
 * @author Tiago
 */
public class Client extends Connection {

    private Socket socket;
    private Cell firstCell;
    private Object syncSockets = new Object();

    public Client(InetAddress ip, Cell firstCell, Spreadsheet connectedSpreadsheet, ConnectionController connectController, UIController uiController) {
        this.uiController = uiController;
        this.connectController = connectController;
        connectedWorkbook = connectedSpreadsheet.getWorkbook();
        this.connectedSpreadsheet = connectedSpreadsheet;
        this.firstCell = firstCell;



        connectedSpreadsheet.addCellListener(this);
        uiController.addEditListener(this);



        this.connectedCells = new ArrayList<Cell>();
        this.connectedFrom = new ArrayList<Address>();

        try {
            address = ip;
            socket = new Socket(address, PORT);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "No host found on specified address.");
        }

    }

    @Override
    void closeSockets() {
        PrintWriter out;
        try {
            out = new PrintWriter(socket.getOutputStream(), true);
            out.println("closeSocket\n");
            socket.close();
        } catch (Exception ex) {
        }
    }

    @Override
    public void valueChanged(Cell cell) {
        //
    }

    @Override
    public void contentChanged(Cell cell) {
        //
    }

    @Override
    public void dependentsChanged(Cell cell) {
        //
    }

    @Override
    public void cellCleared(Cell cell) {
        PrintWriter out;
        for (int i = 0; i < connectedCells.size(); i++) {
            if (cell.equals(connectedCells.get(i))) {
                synchronized (syncSockets) {
                    try {
                        out = new PrintWriter(socket.getOutputStream(), true);
                        out.println(cell.getAddress() + ":.:" + "Content" + ":.:" + cell.getContent() + "\n");
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
    void removeListeners() {
        connectedSpreadsheet.removeCellListener(this);
        uiController.removeEditListener(this);
    }

    @Override
    public void workbookModified(EditEvent event) {

        try {
            /** Opens the stream */
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            synchronized (syncSockets) {
                if(event.getWorkbook().equals(connectedWorkbook)){
                    for(int i=0;i<connectedWorkbook.getSpreadsheetCount();i++){
                        if(connectedWorkbook.getSpreadsheet(i).equals((connectedSpreadsheet))){        
                            for(int j=0;j<connectedCells.size();j++){
                                connectedCells.set(j, connectedWorkbook.getSpreadsheet(i).getCell(connectedCells.get(j).getAddress()));
                            }
                        }
                    }
                }
                
                
                for (int i = 0; i < connectedCells.size(); i++) {

                    out.println(connectedCells.get(i).getAddress() + ":.:" + "Content" + ":.:" + connectedCells.get(i).getContent() + "\n");

                    /** Copies cell assertions, if they exist */
                    try {
                        if (((AssertableCell) connectedCells.get(i).getExtension(AssertionExtension.NAME)).isAsserted()) {
                            out.println(connectedCells.get(i).getAddress() + ":.:" + "USAssertion" + ":.:"
                                    + ((AssertableCell) connectedCells.get(i).getExtension(AssertionExtension.NAME)).getUSAssertion().toString());
                        }
                    } catch (Exception e) {
                    }

                    /** Copies the style attributes of the cell */
                    try {
                        out.println(connectedCells.get(i).getAddress() + ":.:" + "Font"
                                + ((StylableCell) connectedCells.get(i).getExtension(StyleExtension.NAME)).getFont().getStyle() + ","
                                + ((StylableCell) connectedCells.get(i).getExtension(StyleExtension.NAME)).getFont().getFontName() + ","
                                + ((StylableCell) connectedCells.get(i).getExtension(StyleExtension.NAME)).getFont().getSize()
                                + "\n");
                    } catch (Exception e) {
                    }
                    try {
                        out.println(connectedCells.get(i).getAddress() + ":.:" + "FgColor"
                                + ((StylableCell) connectedCells.get(i).getExtension(StyleExtension.NAME)).getForegroundColor().toString() + "\n");
                    } catch (Exception e) {
                    }
                    try {
                        out.println(connectedCells.get(i).getAddress() + ":.:" + "BgColor"
                                + ((StylableCell) connectedCells.get(i).getExtension(StyleExtension.NAME)).getBackgroundColor().toString() + "\n");
                        
                    } catch (Exception e) {
                    }
                    try {
                        out.println(connectedCells.get(i).getAddress() + ":.:" + "Format"
                                + ((StylableCell) connectedCells.get(i).getExtension(StyleExtension.NAME)).getFormat().toString() + "\n");
                    } catch (Exception e) {
                    }
                    try {
                        out.println(connectedCells.get(i).getAddress() + ":.:" + "hAlignment"
                                + ((StylableCell) connectedCells.get(i).getExtension(StyleExtension.NAME)).getHorizontalAlignment() + "\n");
                    } catch (Exception e) {
                    }
                    try {
                        out.println(connectedCells.get(i).getAddress() + ":.:" + "vAlignment"
                                + ((StylableCell) connectedCells.get(i).getExtension(StyleExtension.NAME)).getVerticalAlignment() + "\n");
                    } catch (Exception e) {
                    }
                    /**Copies the tests made in the cell */
                    /*try {
                    if (((TestableCell) connectedCells.get(i).getExtension(TestExtension.NAME)).hasTestCases()) {
                    out.println(connectedCells.get(i).getAddress() + ":.:" + "testCases"
                    + ((TestableCell) connectedCells.get(i).getExtension(TestExtension.NAME)).getTestCases().toString());
                    out.println(connectedCells.get(i).getAddress() + ":.:" + "testParam"
                    + ((TestableCell) connectedCells.get(i).getExtension(TestExtension.NAME)).getTestCaseParams().toString());
                    out.println(connectedCells.get(i).getAddress() + ":.:" + "testNess"
                    + ((TestableCell) connectedCells.get(i).getExtension(TestExtension.NAME)).getTestedness());
                    
                    }
                    } catch (Exception e) {
                    }*/
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    class Receive implements Runnable {

        public void run() {
            try {
                String stream = "";
                String[] cellInfo = new String[3];
                BufferedReader in;
                while (true) {
                    in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    try {
                        stream = in.readLine();
                        if (stream.contains("closeSocket")) {
                            break;
                        }
                        cellInfo = stream.split(":.:");
                        synchronized (syncSockets) {
                            for (int i = 0; i < connectedCells.size(); i++) {
                                if (connectedFrom.get(i).toString().contains(cellInfo[0])) {
                                    if (connectedFrom.get(i).toString().contains(cellInfo[0])) {
                                        if (cellInfo[1].contains("Content")) {
                                            try {
                                                getConnectedCells().get(i).setContent(cellInfo[2]);
                                                break;
                                            } catch (FormulaCompilationException ex) {
                                                JOptionPane.showMessageDialog(null, "Error on receiving connected content!");
                                            }
                                        } else if (cellInfo[1].contains("USAssertion")) {
                                            ((AssertableCell) connectedCells.get(i).getExtension(AssertionExtension.NAME)).setUSAssertion(new USAssertion(cellInfo[2]));
                                            break;
                                        } else if (cellInfo[1].contains("Font")) {
                                            String[] split = cellInfo[2].split(",");
                                            ((StylableCell) connectedCells.get(i).getExtension(StyleExtension.NAME)).setFont(new Font(split[1],
                                                    Integer.parseInt(split[0]), Integer.parseInt(split[2])));
                                            break;
                                        } else if (cellInfo[1].contains("FgColor")) {
                                            ((StylableCell) connectedCells.get(i).getExtension(StyleExtension.NAME)).setForegroundColor(
                                                    new Color(Integer.parseInt(cellInfo[2])));
                                            break;
                                        } else if (cellInfo[1].contains("BgColor")) {
                                            ((StylableCell) connectedCells.get(i).getExtension(StyleExtension.NAME)).setBackgroundColor(
                                                    new Color(Integer.parseInt(cellInfo[2])));
                                            break;
                                        } else if (cellInfo[1].contains("hAlignment")) {
                                            ((StylableCell) connectedCells.get(i).getExtension(StyleExtension.NAME)).getHorizontalAlignment();
                                            ((StylableCell) connectedCells.get(i).getExtension(StyleExtension.NAME)).setHorizontalAlignment(
                                                    Integer.parseInt(cellInfo[2]));
                                            break;
                                        } else if (cellInfo[1].contains("vAlignment")) {
                                            ((StylableCell) connectedCells.get(i).getExtension(StyleExtension.NAME)).getVerticalAlignment();
                                            ((StylableCell) connectedCells.get(i).getExtension(StyleExtension.NAME)).setVerticalAlignment(
                                                    Integer.parseInt(cellInfo[2]));
                                            break;
                                        }
                                        
                                        
                                        /*if (cellInfo[1].contains("Content")) {
                                        try {
                                        getConnectedCells().get(i).setContent(cellInfo[2]);
                                        } catch (FormulaCompilationException ex) {
                                        JOptionPane.showMessageDialog(null, "Error on receiving connected content!");
                                        }
                                        }
                                        if (cellInfo[1].contains("Content")) {
                                        try {
                                        getConnectedCells().get(i).setContent(cellInfo[2]);
                                        } catch (FormulaCompilationException ex) {
                                        JOptionPane.showMessageDialog(null, "Error on receiving connected content!");
                                        }
                                        }*/
                                    }
                                }
                            }
                        }
                    } catch (Exception e) {
                    }
                }
            } catch (Exception e) {
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
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

            stream = in.readLine();
            out.println(firstCellAddress.getColumn() + ":.:" + firstCellAddress.getRow() + "\n");

            data = stream.split(":.:");

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
            Thread receiver = new Thread(this.new Receive());

            receiver.start();
            receiver.join();
            socket.close();
            connectController.disconnect(type + " : " + address);
        } catch (NullPointerException e) {
            connectController.disconnect("Client : " + address);
        } catch (Exception ex) {
            if (ex.getMessage().contains("socket closed")) {
                JOptionPane.showMessageDialog(null, "Connection Closedb!");
            } else if (ex.getMessage().contains("Connection reset")) {
                connectController.disconnect(type + " : " + address);
                JOptionPane.showMessageDialog(null, "Connection Closedg!");
            } else {
                JOptionPane.showMessageDialog(null, ex.getMessage());
            }
        }
    }
}
