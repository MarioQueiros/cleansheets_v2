/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package csheets.ext.share.connect;

import csheets.ext.share.PageSharingController;
import csheets.ext.share.PageSharingData;
import csheets.ext.share.PageSharingEvent;
import csheets.ext.share.PageSharingListener;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.List;

/**
 *
 * @author Tiago
 */
public class UDPTransmitter extends Thread implements PageSharingListener {

    private final int PORT = 53532;
    private MulticastSocket udpSocket;
    private InetAddress group;
    private InetAddress localhost;
    private List<PageSharingData> shares;

    public UDPTransmitter() {
        try {
            udpSocket = new MulticastSocket(PORT);
            group = InetAddress.getByName("239.255.255.255");
            udpSocket.joinGroup(group);
            start();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            Question qst = new Question();
            qst.start();
            qst.join();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    @Override
    public void connectionsChanged(PageSharingEvent event) {
        //
    }

    @Override
    public void connectionsInterrupted(String shareName, boolean interrupted) {
        //
    }

    @Override
    public void serverInstanceOn(boolean state) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    class Question extends Thread {

        @Override
        public void run() {
            DatagramPacket dp;
            byte[] buf;
            while (true) {
                buf = new byte[1000];
                dp = new DatagramPacket(buf, buf.length);
                try {
                    udpSocket.receive(dp);
                    String aux = new String(buf);
                    if (aux.contains("Question")) {
                        String aux2 = new String();

                        if (PageSharingController.getInstance().isServerOn()) {
                            shares = PageSharingController.getInstance().getConnectionDataOfShares();
                            aux2 = "on-" + InetAddress.getLocalHost().getHostAddress();
                            aux2 += "-" + shares.size();
                            for (int i = 0; i < shares.size(); i++) {
                                if (!shares.get(i).isInterrupted()) {
                                    aux2 += "-" + shares.get(i).getShareName();
                                    aux2 += "-" + shares.get(i).getCellConnected().get(0).getAddress();
                                    aux2 += "-" + shares.get(i).getCellConnected().get(shares.get(i).getCellConnected().size() - 1).getAddress();
                                }
                            }
                        } else {
                            aux2 = "off";
                        }

                        dp = new DatagramPacket(aux2.getBytes(), aux2.length(), group, 53533);
                        udpSocket.send(dp);

                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
    }
}
