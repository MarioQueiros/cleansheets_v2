/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package csheets.ext.share.ui;

import csheets.CleanSheets;
import csheets.ext.share.PageSharingController;
import csheets.ext.share.PageSharingEvent;
import csheets.ext.share.PageSharingListener;
import csheets.ext.share.connect.Client;
import csheets.ext.share.connect.Connection;
import csheets.ext.share.connect.Host;
import csheets.ui.ctrl.UIController;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author Tiago
 */
public class DisconnectFrame extends JFrame implements PageSharingListener {

    private javax.swing.JButton jButton1;
    private javax.swing.JComboBox jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private PageSharingController pageSharingController;
    private UIController uiController;
    private List<DisconnectListener> disconnectListeners;
    private List<String> connectionsText;

    public DisconnectFrame(UIController uiController, PageSharingController pageSharingController) {
        this.uiController = uiController;
        disconnectListeners = new ArrayList<DisconnectListener>();

        PageSharingController.getInstance().addConnectionListener(this);
        addDisconnectListener(PageSharingController.getInstance());


        jLabel1 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jComboBox1 = new javax.swing.JComboBox();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setIconImage(Toolkit.getDefaultToolkit().getImage(
                CleanSheets.class.getResource("res/img/sheet.gif")));

        jLabel1.setText("Select one connection to be disconnected from the combo box above.");
        jLabel1.setToolTipText("");

        jButton1.setText("Disconnect !");
        jButton1.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel());
        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });   

        jLabel2.setText("Type :");

        jLabel3.setText("Host IP :");

        jLabel4.setText("Client IP:");

        jLabel5.setText("Connected Cells : ");

        jLabel6.setText("Spreadsheet :");

        jLabel7.setText("");

        jLabel8.setText("");

        jLabel9.setText("");

        jLabel10.setText("");

        jLabel11.setText("");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addGap(43, 43, 43).addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING).addComponent(jButton1).addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(jLabel1).addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 383, javax.swing.GroupLayout.PREFERRED_SIZE).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(jLabel2).addComponent(jLabel3).addComponent(jLabel4).addComponent(jLabel5).addComponent(jLabel6)).addGap(46, 46, 46).addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(jLabel9).addComponent(jLabel10).addComponent(jLabel11).addComponent(jLabel8).addComponent(jLabel7))))).addContainerGap(95, Short.MAX_VALUE)));
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addGap(34, 34, 34).addComponent(jLabel1).addGap(26, 26, 26).addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE).addGap(18, 18, 18).addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(jLabel2).addComponent(jLabel7)).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED).addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(jLabel3).addComponent(jLabel8)).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED).addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(jLabel4).addComponent(jLabel9)).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED).addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(jLabel5).addComponent(jLabel10)).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED).addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(jLabel6).addComponent(jLabel11)).addGap(18, 18, 18).addComponent(jButton1).addContainerGap(16, Short.MAX_VALUE)));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>

    private void jComboBox1ActionPerformed(ActionEvent evt) {

        String[] split = new String[6];
        if (!jComboBox1.getSelectedItem().equals(null)) {
            split = connectionsText.get(jComboBox1.getSelectedIndex()).split(" - ");
            
            jLabel7.setText(split[1]);
            
            jLabel8.setText(split[2].split(": ")[1]);

            jLabel9.setText(split[3].split(": ")[1]);

            jLabel10.setText(split[4]);

            jLabel11.setText(split[5].substring(12));
        }
    }

    private void jButton1ActionPerformed(ActionEvent evt) {
        if (jComboBox1.getSelectedItem() != null) {

            fireConnectionRemoved(jComboBox1.getSelectedIndex());
            jComboBox1.removeItem(jComboBox1.getSelectedItem());
        } else {
            JOptionPane.showMessageDialog(null, "Error in removing connection!");
        }
    }

    @Override
    public void connectionsChanged(PageSharingEvent event) {
        connectionsText = new ArrayList<String>();
        List<Connection> connections = event.getConnectionList();
        String[] comboBoxSelections = new String[connections.size()];
        if (!connections.isEmpty()) {
            for (int i = 0; i < connections.size(); i++) {
                try {

                    comboBoxSelections[i] = connections.get(i).getShareName();
                    comboBoxSelections[i] += " - ";
                    comboBoxSelections[i] += connections.get(i).getType();
                    comboBoxSelections[i] += " - ";

                    if (connections.get(i).getType().equals("Client")) {
                        comboBoxSelections[i] += "H: " + ((Client) connections.get(i)).getSocket().getInetAddress().toString().substring(1);
                        comboBoxSelections[i] += " - ";
                        comboBoxSelections[i] += "C: " + ((Client) connections.get(i)).getSocket().getLocalAddress().toString().substring(1);
                        comboBoxSelections[i] += " - ";
                    } else if (connections.get(i).getType().equals("Host")) {
                        comboBoxSelections[i] += "H: " + PageSharingController.getInstance().getAppServer().getServerSocket().getInetAddress().toString().split("/")[1];
                        comboBoxSelections[i] += " - ";
                        if (((Host) connections.get(i)).isConnected()) {
                            comboBoxSelections[i] += "C: " + ((Host) connections.get(i)).getAddress().toString().substring(1);
                            comboBoxSelections[i] += " - ";
                        } else {
                            comboBoxSelections[i] += "C: " + "( No connection )";
                            comboBoxSelections[i] += " - ";
                        }
                    }
                    comboBoxSelections[i] += "From ";
                    comboBoxSelections[i] += connections.get(i).getConnectedCells().get(0).getAddress();
                    comboBoxSelections[i] += " to ";
                    comboBoxSelections[i] += connections.get(i).getConnectedCells().get(connections.get(i).getConnectedCells().size() - 1).getAddress();
                    comboBoxSelections[i] += " - ";
                    comboBoxSelections[i] += "Spreadsheet " + connections.get(i).getSpreadsheet().getTitle();
                    connectionsText.add(comboBoxSelections[i]);
                    comboBoxSelections[i] = (i+1)+". ";
                    comboBoxSelections[i] += connections.get(i).getShareName();
                    comboBoxSelections[i] += " - ";
                    comboBoxSelections[i] += connections.get(i).getType();
                } catch (IndexOutOfBoundsException e) {
                    comboBoxSelections[i] = "Connection with Errors!";
                }
            }
            
        }
        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel(comboBoxSelections));
        jComboBox1.setSelectedItem(jComboBox1.getModel().getSelectedItem());
        repaint();
    }

    public void addDisconnectListener(DisconnectListener listener) {
        disconnectListeners.add(listener);
    }

    public void removeDisconnectListener(DisconnectListener listener) {
        disconnectListeners.remove(listener);
    }

    /**
     * Notifies all registered listeners that the connections
     * list was modified.
     * @param workbook the workbook that was modified
     */
    private void fireConnectionRemoved(int connectionIndex) {
        DisconnectEvent disconnectEvent = new DisconnectEvent(this, connectionIndex);

        for (DisconnectListener listener : disconnectListeners.toArray(
                new DisconnectListener[disconnectListeners.size()])) {
            listener.disconnect(disconnectEvent);
        }


    }
}
