/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package csheets.sp;

import csheets.core.Address;
import csheets.core.Cell;
import csheets.core.CellListener;
import csheets.core.Spreadsheet;
import csheets.core.formula.compiler.FormulaCompilationException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Locale;
import javax.swing.JOptionPane;

/**
 *
 * @author Tiago
 */
public class Client extends Connection{
    private Socket socket;
    private Cell firstCell;
    private Object syncSockets = new Object();
    
    
    
  
    public Client(InetAddress ip, Cell firstCell, Spreadsheet connectedSpreadsheet, ConnectionController connectController){
        
        try {
            address = ip;
            socket = new Socket(address, PORT);
            this.connectController=connectController;
            connectedWorkbook = connectedSpreadsheet.getWorkbook();
            this.connectedSpreadsheet = connectedSpreadsheet;
            this.firstCell = firstCell;
            connectedSpreadsheet.addCellListener(this);
            this.connectedCells = new ArrayList<Cell>();
            this.connectedFrom = new ArrayList<Address>();
            
        }catch (Exception ex) {
            JOptionPane.showMessageDialog(null,ex.getMessage());
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
        PrintWriter out;
        for(int i=0;i<getConnectedCells().size();i++){
            if(cell.equals(getConnectedCells().get(i))){
                synchronized(syncSockets){
                    try{
                        out = new PrintWriter(socket.getOutputStream(), true);
                        out.println(cell.getAddress()+","+cell.getContent()+"\n");
                    }catch(Exception e){
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
    void removeListeners() {
        connectedSpreadsheet.removeCellListener(this);
    }
    
   
  
    class Receive implements Runnable{
        
        public void run() {
                BufferedReader in;
                String stream;
                String cellInfo[] = new String [2];
                    try {
                        
                        while(true){
                            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                            stream = in.readLine();
                            try{
                                if(stream.contains("closeSocket")){
                                    break;
                                }
                                cellInfo = stream.split(",");

                                for(int i=0;i<getConnectedCells().size();i++){
                                    for(int j=0;j<getConnectedCells().size();j++){
                                        if(connectedFrom.get(i).toString().contains(cellInfo[0])){
                                            try {
                                                getConnectedCells().get(i).setContent(cellInfo[1]);
                                            } catch (FormulaCompilationException ex) {
                                                JOptionPane.showMessageDialog(null, "Error on receiving connected content!");

                                            }
                                        }
                                    }
                                }
                            }catch(Exception e){}
                        }

                    } catch (IOException ex) {
                    }

            
            }
        }
      
    
    @Override
    public void run() {
            String stream;
            String data[] = new String [4];
            int rowNumber=0, colNumber=0,firstRowFromConnected=0,firstColFromConnected=0;
            
            
            Address firstCellAddress = firstCell.getAddress();
            int firstCellRow = firstCellAddress.getRow();
            int firstCellColumn = firstCellAddress.getColumn();
            
            try {
                    BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    PrintWriter out= new PrintWriter(socket.getOutputStream(), true);
                    
                    stream = in.readLine();
                    out.println(firstCellAddress.getColumn()+","+firstCellAddress.getRow()+"\n");
                    
                    data = stream.split(",");
                    
                    rowNumber = Integer.parseInt(data[0]);
                    colNumber = Integer.parseInt(data[1]);
                    
                    firstColFromConnected = Integer.parseInt(data[2]);
                    firstRowFromConnected = Integer.parseInt(data[3]);
                    
                    for(int i=0;i<rowNumber;i++){
                        for(int j=0;j<colNumber;j++){
                            getConnectedCells().add(connectedSpreadsheet.getCell((firstCellColumn + j),(firstCellRow + i)));
                            
                            Address addrAux = new Address((firstColFromConnected+j),(firstRowFromConnected+i));
                            connectedFrom.add(addrAux);
                        }
                    }
                    
                    Thread receiver = new Thread(this.new Receive());
                  
                    receiver.start();

                    receiver.join();

                    socket.close();
                    connectController.disconnect(type+" : "+address);
            }
             catch (Exception ex) {
                 if(ex.getMessage().contains("socket closed")){
                     JOptionPane.showMessageDialog(null,"Connection Closed!");
                 }
                 else if(ex.getMessage().contains("Connection reset")){
                     connectController.disconnect(type+" : "+address);
                     JOptionPane.showMessageDialog(null,"Connection Closed!");
                 }
                 else JOptionPane.showMessageDialog(null,ex.getMessage());
            }
        }
    
}
