/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package csheets.ext.share.connect;

import csheets.ext.share.PageSharingController;
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
 * Esta classe é a classe servidor da instância da aplicação, e só pode existir
 * uma numa instância por computador
 * @author Tiago
 */
public class Server extends Thread {

    /** A Socket do servidor que vai receber clientes */
    private ServerSocket servSocket;
    
    /** A socket criada quando o cliente se liga */
    private Socket clntSocket;
    
    /** A porta da aplicação */
    private final int PORT = 53531;

    public Server() throws IOException {

        servSocket = new ServerSocket(PORT, 0, InetAddress.getLocalHost());

    }

    
/** O RUN DO SERVER */
    
    /**
     * Neste run existe uma lógica de envio e recepção de informação trocada com
     * o cliente que se quer ligar.
     * Para garantir consistência de informação, um e só um cliente se pode 
     * ligar de cada vez.
     */
    
    public @Override
    void run() {
        BufferedReader in = null;
        PrintWriter out = null;
        List<Connection> connections;
        String hostsAvailable;
        int hostNum;
        List<String> hostRelativeInConnections;

        while (true) {
            try {
                clntSocket = servSocket.accept();

                in = new BufferedReader(new InputStreamReader(clntSocket.getInputStream()));
                out = new PrintWriter(clntSocket.getOutputStream(), true);
                connections = PageSharingController.getInstance().getConnections();
                hostNum = 0;
                hostsAvailable = "";
                hostRelativeInConnections = new ArrayList<String>();

                for (int i = 0; i < connections.size(); i++) {
                    if (connections.get(i).getType().equals("Host")) {
                        if (!((Host) connections.get(i)).isConnected() && !((Host) connections.get(i)).isInterrupted()) {
                            hostNum++;
                            hostsAvailable += connections.get(i).getShareName() + " : "
                                    + connections.get(i).getConnectedCells().get(0).getAddress().toString() + " to "
                                    + connections.get(i).getConnectedCells().get(connections.get(i).getConnectedCells().size() - 1).getAddress().toString()
                                    + "-";
                            hostRelativeInConnections.add(hostNum + ":" + i);
                        }
                    }
                }

                if (hostNum == 0) {
                    out.write("No hosts available!\n");
                    out.flush();
                } else {
                    hostsAvailable += "\n";

                    out.write(hostNum + "\n");
                    out.write(hostsAvailable);
                    out.flush();
                    String infoRead;
                    infoRead = in.readLine();
                    String info;
                    String[] split = new String[2];
                    boolean active = false;

                    try {
                        for (int i = 0; i < hostRelativeInConnections.size(); i++) {
                            info = hostRelativeInConnections.get(i);
                            split = info.split(":");
                            if (split[0].equals(infoRead)) {
                                ((Host) connections.get(Integer.parseInt(split[1]))).setClientSocket(clntSocket);
                                connections.get(Integer.parseInt(split[1])).start();
                                ((Host) connections.get(Integer.parseInt(split[1]))).setConnected(true);
                                out.write("Connection started!\n");
                                out.flush();
                                JOptionPane.showMessageDialog(null, "Client from " 
                                        + clntSocket.getInetAddress().toString().substring(1)
                                        + " connected to "
                                        + connections.get(Integer.parseInt(split[1])).getShareName());
                                active = true;
                                PageSharingController.getInstance().connectionOn();
                            }
                        }
                        if (!active) {
                            out.write("Connection started!\n");
                            out.flush();
                        }
                    } catch (Exception e) {
                        out.write("Connection Error!\n");
                        out.flush();
                    }

                }
            } catch (Exception ex) {
                try{
                    out.write("Connection Error!\n");
                    out.flush();
                }catch(Exception e){
                    
                }
            }
        }
    }

    /**
     * @return the servSocket
     */
    public ServerSocket getServerSocket() {
        return servSocket;
    }

    /**
     * @return the clntSocket
     */
    public Socket getClientSocket() {
        return clntSocket;
    }
}
