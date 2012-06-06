/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package csheets.ext.share.connect;

import csheets.core.Address;
import csheets.core.Cell;
import csheets.core.formula.compiler.FormulaCompilationException;
import csheets.ext.share.PageSharingData;
import csheets.ui.ctrl.EditEvent;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author Tiago
 */
public class Client extends Connection {

    private transient Socket socket;
    private Cell firstCell;
    private Object syncSockets = new Object();

    public Client(PageSharingData connectData) {
        this.uiController = connectData.getUiController();
        this.pageSharingController = connectData.getConnectController();
        connectedWorkbook = connectData.getSpreadsheet().getWorkbook();
        this.connectedSpreadsheet = connectData.getSpreadsheet();
        this.firstCell = connectData.getCell();



        connectedSpreadsheet.addCellListener(this);
        uiController.addEditListener(this);



        this.connectedCells = new ArrayList<Cell>();
        this.connectedFrom = new ArrayList<Address>();
        
        try {
            address = connectData.getIp();
            socket = new Socket(address, PORT);
            start();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "No host found on specified address.");
        }
        
    }

    @Override
    public void closeSockets() {
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
        ObjectOutputStream out;
        for (int i = 0; i < connectedCells.size(); i++) {
            if (cell.equals(connectedCells.get(i))) {
                synchronized (syncSockets) {
                    try {
                        out = new ObjectOutputStream(socket.getOutputStream());
                        out.writeObject(connectedCells.get(i));
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
        /*PrintWriter out;
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
        }*/
    }

    @Override
    public void cellCopied(Cell cell, Cell source) {
        //
    }

    @Override
    public void removeListeners() {
        connectedSpreadsheet.removeCellListener(this);
        uiController.removeEditListener(this);
    }

    @Override
    public void workbookModified(EditEvent event) {

        //try {
            /** Opens the stream */
           /* PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            synchronized (syncSockets) {
                if (event.getWorkbook().equals(connectedWorkbook)) {
                    for (int i = 0; i < connectedWorkbook.getSpreadsheetCount(); i++) {
                        if (connectedWorkbook.getSpreadsheet(i).equals((connectedSpreadsheet))) {
                            for (int j = 0; j < connectedCells.size(); j++) {
                                connectedCells.set(j, connectedWorkbook.getSpreadsheet(i).getCell(connectedCells.get(j).getAddress()));
                            }
                        }
                    }
                }


                for (int i = 0; i < connectedCells.size(); i++) {

                    out.println(connectedCells.get(i).getAddress() + ":.:" + "Content" + ":.:" + connectedCells.get(i).getContent() + "\n");
*/
                    /** Copies cell assertions, if they exist */
                   /* try {
                        if (((AssertableCell) connectedCells.get(i).getExtension(AssertionExtension.NAME)).isAsserted()) {
                            out.println(connectedCells.get(i).getAddress() + ":.:" + "USAssertion" + ":.:"
                                    + ((AssertableCell) connectedCells.get(i).getExtension(AssertionExtension.NAME)).getUSAssertion().toString());
                        } else {
                            out.println("null");
                        }
                    } catch (Exception e) {
                        out.println("null");
                    }
*/
                    /** Copies the style attributes of the cell */
/*                     try {
                       out.println(connectedCells.get(i).getAddress() + ":.:" + "Font"
                                + ((StylableCell) connectedCells.get(i).getExtension(StyleExtension.NAME)).getFont().getStyle() + ","
                                + ((StylableCell) connectedCells.get(i).getExtension(StyleExtension.NAME)).getFont().getFontName() + ","
                                + ((StylableCell) connectedCells.get(i).getExtension(StyleExtension.NAME)).getFont().getSize()
                                + "\n");
                    } catch (Exception e) {
                        out.println("null");
                    }
                    try {
                        out.println(connectedCells.get(i).getAddress() + ":.:" + "FgColor"
                                + ((StylableCell) connectedCells.get(i).getExtension(StyleExtension.NAME)).getForegroundColor().toString() + "\n");
                    } catch (Exception e) {
                        out.println("null");
                    }
                    try {
                        out.println(connectedCells.get(i).getAddress() + ":.:" + "BgColor"
                                + ((StylableCell) connectedCells.get(i).getExtension(StyleExtension.NAME)).getBackgroundColor().toString() + "\n");

                    } catch (Exception e) {
                        out.println("null");
                    }
                    try {
                        out.println(connectedCells.get(i).getAddress() + ":.:" + "Format"
                                + ((StylableCell) connectedCells.get(i).getExtension(StyleExtension.NAME)).getFormat().toString() + "\n");
                    } catch (Exception e) {
                        out.println("null");
                    }
                    try {
                        out.println(connectedCells.get(i).getAddress() + ":.:" + "hAlignment"
                                + ((StylableCell) connectedCells.get(i).getExtension(StyleExtension.NAME)).getHorizontalAlignment() + "\n");
                    } catch (Exception e) {
                        out.println("null");
                    }
                    try {
                        out.println(connectedCells.get(i).getAddress() + ":.:" + "vAlignment"
                                + ((StylableCell) connectedCells.get(i).getExtension(StyleExtension.NAME)).getVerticalAlignment() + "\n");
                    } catch (Exception e) {
                        out.println("null");
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
    /*            }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }*/
    }

    class Receive implements Runnable {

        public void run() {
            try {
                /*String stream = "";
                String[] cellInfo = new String[3];*/
                Object info;
                ObjectInputStream in;
                while (true) {
                    in = new ObjectInputStream(socket.getInputStream());
                    try {
                        info = in.readObject();
                        if (info.toString().contains("closeSocket")) {
                            break;
                        }
                        //cellInfo = stream.split(":.:");
                        synchronized (syncSockets) {
                            for (int i = 0; i < connectedCells.size(); i++) {
                                if (((Cell)info).getAddress().equals(connectedFrom.get(i))) {
                                    //boolean end = false;
                                    //if (cellInfo[1].contains("Content")) {
                                            connectedCells.get(i).copyFrom(((Cell)info));

                                    //}


                                    /*while (!end) {
                                        stream = in.readLine();

                                        if (stream.equals("null")) {
                                        } else {
                                            cellInfo = stream.split(":.:");

                                            if (cellInfo[1].contains("USAssertion")) {
                                                ((AssertableCell) connectedSpreadsheet.getCell(connectedCells.get(i).getAddress()).getExtension(AssertionExtension.NAME)).setUSAssertion(new USAssertion(cellInfo[2]));

                                            } else if (cellInfo[1].contains("Font")) {
                                                String[] split = cellInfo[2].split(",");
                                                ((StylableCell) connectedSpreadsheet.getCell(connectedCells.get(i).getAddress()).getExtension(StyleExtension.NAME)).setFont(new Font(split[1],
                                                        Integer.parseInt(split[0]), Integer.parseInt(split[2])));

                                            } else if (cellInfo[1].contains("FgColor")) {
                                                ((StylableCell) connectedSpreadsheet.getCell(connectedCells.get(i).getAddress()).getExtension(StyleExtension.NAME)).setForegroundColor(
                                                        new Color(Integer.parseInt(cellInfo[2])));

                                            } else if (cellInfo[1].contains("BgColor")) {
                                                ((StylableCell) connectedSpreadsheet.getCell(connectedCells.get(i).getAddress()).getExtension(StyleExtension.NAME)).setBackgroundColor(
                                                        new Color(Integer.parseInt(cellInfo[2])));

                                            } else if (cellInfo[1].contains("hAlignment")) {
                                                ((StylableCell) connectedCells.get(i).getExtension(StyleExtension.NAME)).getHorizontalAlignment();
                                                ((StylableCell) connectedCells.get(i).getExtension(StyleExtension.NAME)).setHorizontalAlignment(
                                                        Integer.parseInt(cellInfo[2]));

                                            } else if (cellInfo[1].contains("vAlignment")) {
                                                ((StylableCell) connectedSpreadsheet.getCell(connectedCells.get(i).getAddress()).getExtension(StyleExtension.NAME)).getVerticalAlignment();
                                                ((StylableCell) connectedSpreadsheet.getCell(connectedCells.get(i).getAddress()).getExtension(StyleExtension.NAME)).setVerticalAlignment(
                                                        Integer.parseInt(cellInfo[2]));
                                                end = true;

                                            }
                                        }
                                    }
                                    end =false;
*/

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
                    } catch (Exception e) {
                    }
                }
            } catch (Exception e) {
            }
        }
    }

    
    public @Override void run() {
        int rowNumber = 0, colNumber = 0;
        Address firstConnectedCell=null;

        Object [] startInfoSent = new Object [2];
        Object [] startInfoReceived = new Object [3];
        
        Address firstCellAddress = firstCell.getAddress();
        int firstCellRow = firstCellAddress.getRow();
        int firstCellColumn = firstCellAddress.getColumn();

        try {
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            System.out.println(in.available());
            startInfoReceived = (Object[])in.readObject();
            
            System.out.println(startInfoReceived.toString());
            /*rowNumber = in.readInt();
            colNumber = in.readInt();
            firstConnectedCell = (Address)in.readObject();*/
            
            
            
            out.writeInt(firstCellAddress.getColumn());
            out.writeInt(firstCellAddress.getRow());
            
            for (int i = 0; i < rowNumber; i++) {
                for (int j = 0; j < colNumber; j++) {
                    connectedCells.add(connectedSpreadsheet.getCell((firstCellColumn + j), (firstCellRow + i)));
                    Address addrAux = new Address((firstConnectedCell.getColumn() + j), (firstConnectedCell.getRow() + i));
                    connectedFrom.add(addrAux);
                }
            }
            
            
            Thread receiver = new Thread(this.new Receive());

            receiver.start();
            receiver.join();
            socket.close();
            pageSharingController.connectionRemoved(this);
        } catch (NullPointerException e) {
            pageSharingController.connectionRemoved(this);
        } catch (Exception ex) {
            if (ex.getMessage().contains("socket closed")) {
                JOptionPane.showMessageDialog(null, "Connection Closed!");
            } else if (ex.getMessage().contains("Connection reset")) {
                pageSharingController.connectionRemoved(this);
                JOptionPane.showMessageDialog(null, "Connection Closed!");
            } else {
                JOptionPane.showMessageDialog(null, ex.getMessage());
                ex.printStackTrace();
            }
        }
    }
}
