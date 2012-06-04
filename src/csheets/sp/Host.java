/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package csheets.sp;

import csheets.core.Address;
import csheets.core.Cell;
import csheets.core.Spreadsheet;
import csheets.core.formula.compiler.FormulaCompilationException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author Tiago
 */
public class Host extends Connection {
    ServerSocket servSocket;
    Socket clntSocket;
    int rowNumber;
    int colNumber;
    Object syncSockets = new Object ();
    
    
    public Host(List <Cell> cellConnected, int rowNumber, int colNumber , Spreadsheet connectedSpreadsheet, ConnectionController connectController){
            
            try{
                address = InetAddress.getLocalHost();
                servSocket = new ServerSocket(PORT,0,address);
                
                this.connectController=connectController;
                this.connectedCells = cellConnected;
                
                this.connectedSpreadsheet = connectedSpreadsheet;
                connectedWorkbook = connectedSpreadsheet.getWorkbook();
                this.rowNumber = rowNumber;
                this.colNumber = colNumber;
                this.connectedFrom = new ArrayList<Address>();
                connectedSpreadsheet.addCellListener(this);
            }catch(Exception e){
                JOptionPane.showMessageDialog(null,e.getMessage());
            }
       
    }
    
    @Override
    void closeSockets() {
        PrintWriter out;
        try {    
            out = new PrintWriter(clntSocket.getOutputStream(), true);
            out.println("closeSocket\n");
            servSocket.close();
            clntSocket.close();
        } catch (Exception ex) {
        }
    }

    @Override
    public void valueChanged(Cell cell) {}

    @Override
    public void contentChanged(Cell cell) {
        PrintWriter out;
        for(int i=0;i<getConnectedCells().size();i++){
            if(cell.equals(getConnectedCells().get(i))){
                synchronized(syncSockets){
                    try{
                        out = new PrintWriter(clntSocket.getOutputStream(), true);
                        out.println(cell.getAddress()+","+cell.getContent()+"\n");
                    }catch(Exception e){
                       JOptionPane.showMessageDialog(null, e.getMessage());
                    }
                }
            }
        }
        
    }

    @Override
    public void dependentsChanged(Cell cell) {}

    @Override
    public void cellCleared(Cell cell) {}

    @Override
    public void cellCopied(Cell cell, Cell source) {}

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
                        
                        in = new BufferedReader(new InputStreamReader(clntSocket.getInputStream()));
                        stream = in.readLine();
                        
                        try{
                            if(stream.contains("closeSocket")){
                                break;
                            }
                            cellInfo = stream.split(",");
                            
                            synchronized(syncSockets){
                                for(int i=0;i<getConnectedCells().size();i++){
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
            String data[] = new String [2];
            int firstRowFromConnected=0,firstColFromConnected=0;
            
            try {
                  
                  clntSocket = servSocket.accept();
                  BufferedReader in = new BufferedReader(new InputStreamReader(clntSocket.getInputStream()));
                  PrintWriter out = new PrintWriter(clntSocket.getOutputStream(), true);
                  out.println(rowNumber+","+colNumber+","+getConnectedCells().get(0).getAddress().getColumn()+","+getConnectedCells().get(0).getAddress().getRow()+"\n");
                  
                  stream = in.readLine();
                  data = stream.split(",");
                  
                  firstColFromConnected = Integer.parseInt(data[0]);
                  firstRowFromConnected = Integer.parseInt(data[1]);
                    
                  for(int i=0;i<rowNumber;i++){
                        for(int j=0;j<colNumber;j++){
                            Address addrAux = new Address((firstColFromConnected+j),(firstRowFromConnected+i));
                            connectedFrom.add(addrAux);
                        }
                  }
                  
                  Thread receiver = new Thread(this.new Receive());
                  
                  receiver.start();
                  receiver.join();
                  
                  clntSocket.close();
                  
                  servSocket.close();
                  
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
