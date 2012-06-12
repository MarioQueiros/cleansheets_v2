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
 * Subclasse de "Connection", trata-se de um hoste, que cria uma
 * partilha
 * @author Tiago
 */
public class Host extends Connection {

    /** A socket ao qual se liga */
    private Socket clntSocket;
    
    /** O número de linhas que partilha */
    private int rowNumber;
    
    /** O número de colunas que partilha */
    private int colNumber;
    
    /** O objecto que se encarrega de manter a sincronização de Threads */
    private Object syncSockets = new Object();
    
    /** Booleans que identificam o estado da partilha */
    private boolean connected = false;
    private boolean interrupted = false;

    public Host(PageSharingData connectData) {

        try {
            this.shareName = connectData.getShareName();
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

    
/** ESCUTA DO EVENTO DE MUDANÇA NAS CÉLULAS */
    @Override
    public void valueChanged(Cell cell) {
    }

    @Override
    public void contentChanged(Cell cell) {
        PrintWriter out;
        if (!isInterrupted()) {
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
        PrintWriter out;
        if (!isInterrupted()) {
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
    public void cellCopied(Cell cell, Cell source) {
        PrintWriter out;
        if (!isInterrupted()) {
            for (int i = 0; i < connectedCells.size(); i++) {
                if (cell.equals(connectedCells.get(i))) {
                    synchronized (syncSockets) {
                        try {
                            cell.setContent(source.getContent());
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
    public void removeListeners() {
        connectedSpreadsheet.removeCellListener(this);
    }
    
     /** Quando do outro lado a conecção é fechada é recolhida na Thread Receive
     * que por sua vez chama este método para fechar também esta conecção
     */

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

/** GETS E SETS */
    
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

    
 /** Thread RECEIVE */
    
    /**
     * Esta classe encarrega-se de receber informação pela socket e
     * a colocar no seu devido lugar. Também se encarrega de verificar
     * se a socket se mantém activa e fechar o Host se for o caso
     */
    class Receive implements Runnable {

        public void run() {
            BufferedReader in;
            String stream;
            String cellInfo[] = new String[2];
            try {
                while (true) {

                    in = new BufferedReader(new InputStreamReader(clntSocket.getInputStream()));
                    stream = in.readLine();


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
                }
            } catch (Exception ex) {
            }
        }
    }

    
    
/** RUN DO HOST */
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

            PageSharingController.getInstance().connectionRemoved(this);

        } catch (Exception ex) {
        }
        PageSharingController.getInstance().connectionRemoved(this);
    }
}
