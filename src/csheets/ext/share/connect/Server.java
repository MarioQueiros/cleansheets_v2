/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package csheets.ext.share.connect;

import csheets.ext.share.Encryptor;
import csheets.ext.share.PageSharingController;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 * Esta classe é a classe servidor da instância da aplicação, e só pode existir
 * uma numa instância por computador
 *
 * @author Tiago
 */
public class Server extends Thread {

    /**
     * A Socket do servidor que vai receber clientes
     */
    private ServerSocket servSocket;
    /**
     * A socket criada quando o cliente se liga
     */
    private Socket clntSocket;
    /**
     * A porta da aplicação
     */
    private final int PORT = 53531;
    private BufferedReader in = null;
    private PrintWriter out = null;
    private String hostsAvailable;
    private List<String> hostRelativeInConnections;

    public Server() throws IOException {

        servSocket = new ServerSocket(PORT, 0, InetAddress.getLocalHost());
        servSocket.setReuseAddress(true);

        //Desbloqueia o accept de 3 em 3 segundos para descobrir  se mudou de ip
        servSocket.setSoTimeout(3000);

    }

    /**
     * O RUN DO SERVER
     */
    /**
     * Neste run existe uma lógica de envio e recepção de informação trocada com
     * o cliente que se quer ligar. Para garantir consistência de informação, um
     * e só um cliente se pode ligar de cada vez.
     */
    public @Override
    void run() {

        while (true) {

            try {
                clntSocket = servSocket.accept();

                in = new BufferedReader(new InputStreamReader(clntSocket.getInputStream()));
                out = new PrintWriter(clntSocket.getOutputStream(), true);
                hostsAvailable = "";
                hostRelativeInConnections = new ArrayList<String>();
                String checker = new String();
                checker = in.readLine();
                if (checker.equals("simple")) {
                    createShareListFromConnections();

                    if (PageSharingController.getInstance().getConnectionDataOfShares().isEmpty()) {
                        out.write("No hosts available!\n");
                        out.flush();
                    } else {
                        createNewHostFromClient();
                    }
                } else if (checker.equals("list")) {
                    listConnecter();
                }

            } catch (Exception ex) {

                if (ex.getMessage().equals("Accept timed out")) {
                    try {
                        if (!servSocket.getInetAddress().equals(InetAddress.getLocalHost())) {

                            servSocket.close();
                            servSocket = new ServerSocket(PORT, 0, InetAddress.getLocalHost());
                            servSocket.setReuseAddress(true);

                            //Desbloqueia o accept de 3 em 3 segundos para descobrir  se mudou de ip
                            servSocket.setSoTimeout(3000);

                            PageSharingController.getInstance().refreshIp(true);
                        }
                    } catch (Exception e) {
                        PageSharingController.getInstance().refreshIp(false);
                    }
                } else {

                    try {
                        out.write("Connection Error!\n");
                        out.flush();
                    } catch (Exception e) {
                    }

                }
            }
        }
    }

    private void createNewHostFromClient() throws IOException {
        hostsAvailable += "\n";

        out.write(PageSharingController.getInstance().getConnectionDataOfShares().size() + "\n");
        out.write(hostsAvailable);
        out.flush();

        String infoRead;
        String password;
        boolean wrongPassword = false;
        while (true) {
            infoRead = in.readLine();
            password = in.readLine();
            password = Encryptor.dencrypt(password.getBytes());
            boolean active = false;

            try {

                if (!infoRead.equals("-1")) {
                    if (PageSharingController.getInstance().getConnectionDataOfShares().get(Integer.parseInt(infoRead) - 1).getPassword().equals(password)) {
                        wrongPassword = false;
                        PageSharingController.getInstance().getConnectionDataOfShares().get(Integer.parseInt(infoRead) - 1).addConnection();
                        Host h = new Host(PageSharingController.getInstance().getConnectionDataOfShares().get(Integer.parseInt(infoRead) - 1));
                        PageSharingController.getInstance().getConnections().add(h);
                        h.setType("Host");
                        h.setClientSocket(clntSocket);
                        h.setConnected(true);
                        h.start();

                        out.write("Connection started!\n");
                        out.flush();
                        JOptionPane.showMessageDialog(null, "Client from "
                                + (clntSocket.getInetAddress().toString().split("/")[1])
                                + " connected to "
                                + PageSharingController.getInstance().getConnectionDataOfShares().get(Integer.parseInt(infoRead) - 1).getShareName());
                        active = true;
                        PageSharingController.getInstance().connectionOn();
                        break;
                    } else {
                        out.write("Wrong password\n");
                        out.flush();
                        wrongPassword = true;
                    }


                    if (!active && !wrongPassword) {
                        out.write("Connection Error!\n");
                        out.flush();
                    }

                } else {
                    break;
                }

            } catch (Exception e) {
                e.printStackTrace();
                out.write("Connection Error!\n");
                out.flush();
            }
        }
    }

    private void createShareListFromConnections() {
        for (int i = 0; i < PageSharingController.getInstance().getConnectionDataOfShares().size(); i++) {
            if (PageSharingController.getInstance().getConnectionDataOfShares().get(i).getType().equals("Host")) {
                if (!PageSharingController.getInstance().getConnectionDataOfShares().get(i).isInterrupted()) {

                    String auxiliarString = PageSharingController.getInstance().getConnectionDataOfShares().get(i).getShareName() + " : "
                            + PageSharingController.getInstance().getConnectionDataOfShares().get(i).getCellConnected().get(0).getAddress().toString() + " to "
                            + PageSharingController.getInstance().getConnectionDataOfShares().get(i).getCellConnected().get(PageSharingController.getInstance().getConnectionDataOfShares().get(i).getCellConnected().size() - 1).getAddress().toString()
                            + "-";

                    if (!hostsAvailable.contains(auxiliarString)) {

                        hostsAvailable += auxiliarString;
                        hostRelativeInConnections.add(String.valueOf(i));

                    }
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

    private void listConnecter() {
        String password;
        boolean wrongPassword = false;
        String shareName;

        boolean active = false;

        try {

            shareName = in.readLine();
            password = in.readLine();
            password = Encryptor.dencrypt(password.getBytes());

            for (int i = 0; i < PageSharingController.getInstance().getConnectionDataOfShares().size(); i++) {
                if (shareName.equals(PageSharingController.getInstance().getConnectionDataOfShares().get(i).getShareName())) {
                    if (PageSharingController.getInstance().getConnectionDataOfShares().get(i).getPassword().equals(password)) {
                        wrongPassword = false;
                        PageSharingController.getInstance().getConnectionDataOfShares().get(i).addConnection();
                        Host h = new Host(PageSharingController.getInstance().getConnectionDataOfShares().get(i));
                        PageSharingController.getInstance().getConnections().add(h);
                        h.setType("Host");
                        h.setClientSocket(clntSocket);
                        h.setConnected(true);
                        h.start();

                        out.write("Connection started!\n");
                        out.flush();
                        JOptionPane.showMessageDialog(null, "Client from "
                                + (clntSocket.getInetAddress().toString().split("/")[1])
                                + " connected to "
                                + PageSharingController.getInstance().getConnectionDataOfShares().get(i).getShareName());
                        active = true;
                        PageSharingController.getInstance().connectionOn();
                        break;
                    } else {
                        out.write("Wrong password\n");
                        out.flush();
                        wrongPassword = true;
                        break;
                    }


                }
            }


            if (!active && !wrongPassword) {
                out.write("Connection Error!\n");
                out.flush();
            }
        } catch (Exception e) {
            out.write("Connection Error!\n");
            out.flush();
        }
    }
}
