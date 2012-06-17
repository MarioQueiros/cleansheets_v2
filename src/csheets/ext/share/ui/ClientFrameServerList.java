/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package csheets.ext.share.ui;

import csheets.core.Cell;
import csheets.core.Spreadsheet;
import csheets.ext.share.Encryptor;
import csheets.ext.share.PageSharingController;
import csheets.ui.ctrl.UIController;
import csheets.ui.sheet.SpreadsheetTableModel;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;

/**
 *
 * @author Tiago
 */
public class ClientFrameServerList extends javax.swing.JFrame {

    /**
     * Creates new form ClientFrameServerList
     */
    public ClientFrameServerList(UIController uiController) {
        this.uiController = uiController;
        clientListeners = new ArrayList<ClientListener>();

        addClientListener(PageSharingController.getInstance());
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jPasswordField1 = new javax.swing.JPasswordField();
        jLabel4 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jTextField3 = new javax.swing.JTextField();
        jComboBox1 = new javax.swing.JComboBox();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Servers List");
        setResizable(false);

        jLabel2.setText("Select one server from the above.");

        jLabel3.setText("Password");

        jLabel4.setText("First Cell Connected");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPasswordField1, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 369, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addGap(30, 30, 30)
                .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 66, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jPasswordField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(3, 3, 3))
        );

        jButton1.setText("Refresh List !");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Connect !");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButton1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton2)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton2)
                    .addComponent(jButton1))
                .addContainerGap(21, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        refreshList();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        try {
            String col = "";
            int row = 0;
            Cell firstCell = null;


            try {
                col = jTextField2.getText();
                row = Integer.parseInt(jTextField3.getText());
                firstCell = checkDataInsered(col, row, firstCell);

            } catch (Exception e) {
                firstCell = null;
            }

            if (firstCell != null) {

                fCell = firstCell;
                ipHost = availableServersAddresses.get(jComboBox1.getSelectedIndex());
                socket = new Socket(ipHost, PORT_TCP);
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                out = new PrintWriter(socket.getOutputStream(), true);
                shareName = String.valueOf(jComboBox1.getSelectedItem()).split(" | ")[2];
                out.write("list\n");
                out.flush();
                out.write(shareName + "\n");
                out.write(Encryptor.encrypt(String.valueOf(jPasswordField1.getPassword()))+ "\n");
                out.flush();
                String stream = in.readLine();
                if (stream.contains("Connection started!")) {
                    fireConnectionAdded(ipHost, fCell, uiController.getActiveSpreadsheet(), socket, shareName);
                    JOptionPane.showMessageDialog(null, "Connection started !");
                    this.setVisible(false);
                } else {
                    JOptionPane.showMessageDialog(null, "Error in data insered ( password ) !");
                }

            } else {
                JOptionPane.showMessageDialog(null, "Error in data insered!");
            }
        } catch (UnknownHostException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }//GEN-LAST:event_jButton2ActionPerformed

    public void addClientListener(ClientListener listener) {
        clientListeners.add(listener);
    }

    public void removeClientListener(ClientListener listener) {
        clientListeners.remove(listener);
    }

    /**
     * Notifies all registered listeners that the connections list was modified.
     *
     * @param workbook the workbook that was modified
     */
    private void fireConnectionAdded(InetAddress ip, Cell firstCell, Spreadsheet spreadsheet, Socket connectedSocket, String connectName) {
        ClientEvent clientEvent = new ClientEvent(this, ip, firstCell, spreadsheet, uiController, connectedSocket, connectName);

        for (ClientListener listener : clientListeners.toArray(
                new ClientListener[clientListeners.size()])) {
            listener.connect(clientEvent);
        }


    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JComboBox jComboBox1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPasswordField jPasswordField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    // End of variables declaration//GEN-END:variables
// Required variables
    private List<String> serversList= new ArrayList<String>();
    private List<InetAddress> availableServersAddresses;
    private UIController uiController;
    private List<ClientListener> clientListeners;
    private final int PORT_UDP= 53533;
    private final int PORT_TCP = 53531;
    private InetAddress ipHost;
    private Socket socket;
    private String shareName;
    private Cell fCell;
    private boolean connectionOn = false;
    private BufferedReader in;
    private PrintWriter out;
    // End of variable declaration

    private void refreshList() {
        try {
            jComboBox1.removeAllItems();
            String stream;

            stream = "Question";
            InetAddress group = InetAddress.getByName("239.255.255.255");
            MulticastSocket s = new MulticastSocket(PORT_UDP);

            s.joinGroup(group);
            DatagramPacket hi = new DatagramPacket(stream.getBytes(), stream.length(), group, 53532);
            s.send(hi);
            List<String> availableServers = new ArrayList<String>();
            serversList = new ArrayList<String>();

            s.setSoTimeout(3000);
            while (true) {
                try {
                    byte[] buf = new byte[10000];
                    DatagramPacket recv = new DatagramPacket(buf, buf.length);
                    s.receive(recv);
                    if (buf.equals(new byte[10000])) {
                        break;
                    }
                    stream = new String(buf);
                    if (!stream.contains("off")) {
                        availableServers.add(stream);
                    }
                } catch (Exception e) {
                    break;
                }
            }
            availableServersAddresses = new ArrayList<InetAddress>();
            int numberOfSharesInAddress = 0;
            for (int i = 0; i < availableServers.size(); i++) {
                stream = availableServers.get(i);
                String[] auxiliarStream = stream.split("-");
                availableServersAddresses.add(InetAddress.getByName(auxiliarStream[1]));
                numberOfSharesInAddress = Integer.parseInt(auxiliarStream[2]);
                for (int j = 0; j < numberOfSharesInAddress; j++) {
                    String text = availableServersAddresses.get(i) + " | ";
                    text += auxiliarStream[3 + (3 * j)]
                            + " | From ";
                    text += auxiliarStream[4 + (3 * j)] + " to ";
                    text += auxiliarStream[5 + (3 * j)];
                    serversList.add(text);
                }
            }
            
            jComboBox1.setModel(new javax.swing.DefaultComboBoxModel(serversList.toArray()));
            jComboBox1.setSelectedItem(jComboBox1.getSelectedItem());
           
        } catch (Exception ex) {
        }
    }

    private Cell checkDataInsered(String col, int row, Cell firstCell) {
        int colFirst = -1;

        try {
            SpreadsheetTableModel aux = new SpreadsheetTableModel(uiController.getActiveSpreadsheet(), uiController);
            int activeSpreadsheetRows = aux.getRowCount();
            int activeSpreadsheetColumns = aux.getColumnCount();


            if (row < 0 || row > activeSpreadsheetRows) {
                return null;
            }

            String[] expectedColumns = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M",
                "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z", "AA", "AB", "AC", "AD", "AE", "AF", "AG",
                "AH", "AI", "AJ", "AK", "AL", "AM", "AN", "AO", "AP", "AQ", "AR", "AS", "AT", "AU", "AV", "AW", "AX", "AY", "AZ"};


            col = col.toUpperCase();
            for (int i = 0; i < activeSpreadsheetColumns; i++) {
                if (expectedColumns[i].equals(col)) {
                    colFirst = i;
                }
            }

            if (colFirst == -1) {
                return null;
            }
        } catch (Exception e) {
            return null;
        }

        firstCell = uiController.getActiveSpreadsheet().getCell(colFirst, (row - 1));

        return firstCell;
    }
}
