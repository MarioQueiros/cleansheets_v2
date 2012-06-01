/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package csheets.sp;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import javax.swing.JOptionPane;

/**
 *
 * @author Tiago
 */
public class Host extends Connection implements Runnable{
    ServerSocket servSocket;
    Socket clntSocket;
    
    class Send implements Runnable{

        public void run() {
            PrintWriter out;
            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
            String str;
            while(true){
                try {
                    out = new PrintWriter(clntSocket.getOutputStream(), true);
                    while(true){
                        str = in.readLine();
                        out.println(str);
                    }
                }catch (Exception ex) {
                    JOptionPane.showMessageDialog(null,ex.getMessage());
                }
            }
            
        }
      
    }
  
    class Receive implements Runnable{
      
        public void run() {
            BufferedReader in;
            while(true){
                try {
                    in = new BufferedReader(new InputStreamReader(clntSocket.getInputStream()));
                    String str = in.readLine();
                    if (str.equals("END")) {
                        break;
                    }
                    System.out.println("> "+str);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null,ex.getMessage());
                }
                
            }
        }
      
    }
  
    public Host(String firstCell, String lastCell){
        try {
            address = InetAddress.getByName("localhost");
        } catch (UnknownHostException ex) {
            JOptionPane.showMessageDialog(null,ex.getMessage());
        }
         System.out.println("addr = " + address);
    }

    @Override
    public void run() {
        try {
            servSocket = new ServerSocket(PORT,0,address);
            System.out.println("Server: started " + servSocket);
            System.out.println("Server: awaiting new TCP connection...");
            clntSocket = servSocket.accept();// awaiting incoming connection
            Thread t1 = new Thread(this.new Send());
            Thread t2 = new Thread(this.new Receive());
            
            t1.start();
            t2.start();
            t1.join();
            t1.join();
        }catch(Exception e){
            JOptionPane.showMessageDialog(null,e.getMessage());
        }
    }
}
