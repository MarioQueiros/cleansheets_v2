/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package csheets.sp;

import java.awt.Dimension;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import javax.swing.JOptionPane;

/**
 *
 * @author Tiago
 */
public class Client extends Connection implements Runnable{
    private Socket socket;
    
    class Send implements Runnable{
        
        public void run() {
            PrintWriter out;
            
            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
            
            String str;
            while(true){
                try {
                    out = new PrintWriter(socket.getOutputStream(), true);
                    while(true){
                        str = in.readLine();

                        out.println(str);
                    }
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage());
                }
            }
            
        }
      
  }
  
    class Receive implements Runnable{
        
        public void run() {
            BufferedReader in;
            while(true){
                try {
                    in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    String str = in.readLine();
                    System.out.println("> "+str);
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage());
                }
                
            }
        }
      
    }
  
    public Client(InetAddress ip, String firstCell){
        try {
            address = ip;
            System.out.println("addr = " + address);
        }catch (Exception ex) {
            JOptionPane.showMessageDialog(null,ex.getMessage());
        }
        
    }
    
    @Override
    public void run() {
        
        try {
              socket = new Socket(address, PORT);
              Thread t1 = new Thread(this.new Send());
              Thread t2 = new Thread(this.new Receive());
              System.out.println("socket@client = " + socket);

              //base for reading what server has to say

              t1.start();
              t2.start();

              t1.join();
              t2.join();

              //to end the communication send the "END" message to server
              //out.println("END");
              socket.close();
        }
         catch (Exception ex) {
            JOptionPane.showMessageDialog(null,ex.getMessage());
        }
    }
}
