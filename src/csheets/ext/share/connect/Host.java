/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package csheets.ext.share.connect;

import csheets.core.Address;
import csheets.core.Cell;
import csheets.ext.share.PageSharingData;
import csheets.ui.ctrl.EditEvent;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author Tiago
 */
public class Host extends Connection {

    private transient ServerSocket servSocket;
    private transient Socket clntSocket;
    private int rowNumber;
    private int colNumber;
    private Object syncSockets = new Object();

    public Host(PageSharingData connectData) {

        try {
            address = InetAddress.getLocalHost();
            servSocket = new ServerSocket(PORT, 0, address);

            this.uiController = connectData.getUiController();
            this.pageSharingController = connectData.getConnectController();
            connectedWorkbook = connectData.getSpreadsheet().getWorkbook();
            this.connectedSpreadsheet = connectData.getSpreadsheet();
            this.rowNumber = connectData.getRowNumber();
            this.colNumber = connectData.getColNumber();
            this.connectedCells = connectData.getCellConnected();
            this.connectedFrom = new ArrayList<Address>();
            connectedSpreadsheet.addCellListener(this);
            uiController.addEditListener(this);
            start();

        } catch (Exception e) {
            //Apanhar aqui o erro de socket already connecting
            JOptionPane.showMessageDialog(null, e.getMessage());
        }


    }

    public @Override
    void run() {

        int firstRowFromConnected = 0, firstColFromConnected = 0;
        Object [] startInfoSent = new Object [3];
        Object [] startInfoReceived = new Object [2];
        try {
            clntSocket = servSocket.accept();
            ObjectInputStream in = new ObjectInputStream(clntSocket.getInputStream());
            ObjectOutputStream out = new ObjectOutputStream(clntSocket.getOutputStream());


            startInfoSent[0] = rowNumber;
            startInfoSent[1] = colNumber;
            startInfoSent[2] = connectedCells.get(0).getAddress();

            out.writeObject(startInfoSent);
            out.flush();

            firstColFromConnected = in.readInt();
            firstRowFromConnected = in.readInt();



            for (int i = 0; i < rowNumber; i++) {
                for (int j = 0; j < colNumber; j++) {
                    Address addrAux = new Address((firstColFromConnected + j), (firstRowFromConnected + i));
                    connectedFrom.add(addrAux);
                }
            }



            for (int i = 0; i < connectedCells.size(); i++) {
                synchronized (syncSockets) {
                    out.writeObject(connectedCells.get(i));
                    out.flush();
                }
            }

            Thread receiver = new Thread(this.new Receive());
            receiver.start();
            receiver.join();

            clntSocket.close();

            servSocket.close();

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

    @Override
    public void closeSockets() {
        PrintWriter out;
        try {
            out = new PrintWriter(clntSocket.getOutputStream(), true);
            out.println("closeSocket\n");
            clntSocket.close();
            servSocket.close();
        } catch (Exception ex) {
            try {
                servSocket.close();
            } catch (Exception e) {
            }
        }
    }

    @Override
    public void valueChanged(Cell cell) {
    }

    @Override
    public void contentChanged(Cell cell) {
        ObjectOutputStream out;
        for (int i = 0; i < connectedCells.size(); i++) {
            if (cell.equals(connectedCells.get(i))) {
                synchronized (syncSockets) {
                    try {
                        out = new ObjectOutputStream(clntSocket.getOutputStream());
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
    }

    @Override
    public void cellCleared(Cell cell) {
        /*PrintWriter out;
        for (int i = 0; i < connectedCells.size(); i++) {
        if (cell.equals(connectedCells.get(i))) {
        synchronized (syncSockets) {
        try {
        out = new PrintWriter(clntSocket.getOutputStream(), true);
        out.println(cell.getAddress() + ":.:" + "Content" + ":.:" + cell.getContent() + " \n");
        } catch (Exception e) {
        JOptionPane.showMessageDialog(null, e.getMessage());
        }
        }
        }
        }*/
    }

    @Override
    public void cellCopied(Cell cell, Cell source) {
    }

    @Override
    public void workbookModified(EditEvent event) {
        //try {
        /** Opens the stream */
        /*PrintWriter out = new PrintWriter(clntSocket.getOutputStream(), true);
        
        
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
        for (int i = 0; i < connectedCells.size(); i++) {*/
        //out.println(connectedCells.get(i).getAddress() + ":.:" + "Content" + ":.:" + connectedCells.get(i).getContent() + "\n");
        /** Copies cell assertions, if they exist */
        /*try {
        if (((AssertableCell) connectedCells.get(i).getExtension(AssertionExtension.NAME)).isAsserted()) {
        out.println(connectedCells.get(i).getAddress() + ":.:" + "USAssertion" + ":.:"
        + ((AssertableCell) connectedCells.get(i).getExtension(AssertionExtension.NAME)).getUSAssertion().toString());
        } else {
        out.println("null");
        }
        } catch (Exception e) {
        out.println("null");
        }*/
        /** Copies the style attributes of the cell */
        /*try {
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
        }*/
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
        /* }
        }
        } catch (Exception e) {
        e.printStackTrace();
        }*/
    }

    @Override
    public void removeListeners() {
        connectedSpreadsheet.removeCellListener(this);
        uiController.removeEditListener(this);
    }

    class Receive implements Runnable {

        public void run() {
            try {
                /*String stream = "";
                String[] cellInfo = new String[3];*/
                Object info;
                ObjectInputStream in;
                while (true) {
                    in = new ObjectInputStream(clntSocket.getInputStream());
                    try {
                        info = in.readObject();
                        if (info.toString().contains("closeSocket")) {
                            break;
                        }
                        //cellInfo = stream.split(":.:");
                        synchronized (syncSockets) {
                            for (int i = 0; i < connectedCells.size(); i++) {
                                if (((Cell) info).getAddress().equals(connectedFrom.get(i))) {
                                    //boolean end = false;
                                    //if (cellInfo[1].contains("Content")) {
                                    connectedCells.get(i).copyFrom(((Cell) info));

                                    //}


                                    /* while (!end) {
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
                                    end = false;
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

    
}
