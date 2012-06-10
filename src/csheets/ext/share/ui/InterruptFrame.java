/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package csheets.ext.share.ui;

import csheets.CleanSheets;
import csheets.ext.share.PageSharingController;
import csheets.ext.share.PageSharingEvent;
import csheets.ext.share.PageSharingListener;
import csheets.ext.share.connect.Connection;
import csheets.ext.share.connect.Host;
import csheets.ui.ctrl.UIController;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFrame;

/**
 *
 * @author Tiago
 */
public class InterruptFrame extends JFrame implements PageSharingListener {

    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JComboBox jComboBox1;
    private javax.swing.JList jList1;
    private javax.swing.JScrollPane jScrollPane1;
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
    private UIController uiController;
    private boolean allHosts;
    private List<Host> hosts;
    private List<String> shareName;
    private List<String> numberOfShares;
    private List<String> numberOfConnected;
    private List<String> isInterrupted;
    private List<InterruptListener> interruptListeners = new ArrayList<InterruptListener>();

    public InterruptFrame(UIController uiController, boolean allHosts) {

        this.uiController = uiController;
        this.allHosts = allHosts;
        PageSharingController.getInstance().addConnectionListener(this);
        addInterruptListener(PageSharingController.getInstance());

        if (!allHosts) {
            jLabel1 = new javax.swing.JLabel();
            jButton1 = new javax.swing.JButton();
            jButton2 = new javax.swing.JButton();
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

            setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
            setIconImage(Toolkit.getDefaultToolkit().getImage(
                    CleanSheets.class.getResource("res/img/sheet.gif")));

            jLabel1.setText("Select one share to interrupt/resume from the above.");
            jLabel1.setToolTipText("");

            jButton1.setText("Interrupt !");
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

            jLabel2.setText("Nr. of Shares :");

            jLabel3.setText("Nr. of Connected :");

            jLabel4.setText("Interrupted :");

            jLabel7.setText("");

            jLabel8.setText("");

            jLabel9.setText("");


            jButton2.setText("Resume !");
            jButton2.addActionListener(new java.awt.event.ActionListener() {

                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    jButton2ActionPerformed(evt);
                }
            });
            javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
            getContentPane().setLayout(layout);
            layout.setHorizontalGroup(
                    layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addGap(43, 43, 43).addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(jLabel1).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(jLabel2).addComponent(jLabel3).addComponent(jLabel4).addComponent(jLabel5).addComponent(jLabel6)).addGap(46, 46, 46).addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(jLabel9).addComponent(jLabel10).addComponent(jLabel11).addComponent(jLabel8).addComponent(jLabel7))).addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING).addGroup(layout.createSequentialGroup().addComponent(jButton1).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED).addComponent(jButton2)).addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 383, javax.swing.GroupLayout.PREFERRED_SIZE))).addContainerGap(95, Short.MAX_VALUE)));
            layout.setVerticalGroup(
                    layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addGap(34, 34, 34).addComponent(jLabel1).addGap(26, 26, 26).addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED).addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(jLabel2).addComponent(jLabel7)).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED).addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(jLabel3).addComponent(jLabel8)).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED).addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(jLabel4).addComponent(jLabel9)).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED).addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(jLabel5).addComponent(jLabel10)).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED).addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(jLabel6).addComponent(jLabel11)).addGap(39, 39, 39).addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(jButton2).addComponent(jButton1)).addContainerGap(25, Short.MAX_VALUE)));
        } else {

            jScrollPane1 = new javax.swing.JScrollPane();
            jList1 = new javax.swing.JList();
            jLabel1 = new javax.swing.JLabel();
            jButton1 = new javax.swing.JButton();
            jButton2 = new javax.swing.JButton();

            setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
            setTitle("Interrupt All");

            jScrollPane1.setViewportView(jList1);

            jLabel1.setText("The following shares will be interrupted/resumed .");
            jLabel1.setToolTipText("");

            jButton1.setText("Interrupt All !");
            jButton1.addActionListener(new java.awt.event.ActionListener() {

                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    jButton1ActionPerformedAll(evt);
                }
            });


            jButton2.setText("Resume All !");
            jButton2.addActionListener(new java.awt.event.ActionListener() {

                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    jButton2ActionPerformedAll(evt);
                }
            });

            javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
            getContentPane().setLayout(layout);
            layout.setHorizontalGroup(
                    layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addGap(20, 20, 20).addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(jLabel1).addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING).addGroup(layout.createSequentialGroup().addComponent(jButton1).addGap(18, 18, 18).addComponent(jButton2)).addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 454, javax.swing.GroupLayout.PREFERRED_SIZE))).addContainerGap(26, Short.MAX_VALUE)));
            layout.setVerticalGroup(
                    layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addGap(31, 31, 31).addComponent(jLabel1).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED).addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 183, javax.swing.GroupLayout.PREFERRED_SIZE).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED).addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(jButton2).addComponent(jButton1)).addContainerGap(27, Short.MAX_VALUE)));
        }
        pack();
        setLocationRelativeTo(null);
        setResizable(false);
    }

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {
        fireInterruptOne(shareName.get(jComboBox1.getSelectedIndex()), true);
    }

    private void jButton1ActionPerformedAll(java.awt.event.ActionEvent evt) {
        fireInterruptAll(true);
    }

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {
        if (!jComboBox1.getSelectedItem().equals(null)) {

            jLabel7.setText(numberOfShares.get(jComboBox1.getSelectedIndex()));

            jLabel8.setText(numberOfConnected.get(jComboBox1.getSelectedIndex()));

            jLabel9.setText(isInterrupted.get(jComboBox1.getSelectedIndex()));

            if (jLabel9.getText().equals("Yes")) {
                jButton1.setEnabled(false);
                jButton2.setEnabled(true);
            } else if (jLabel9.getText().equals("No")) {
                jButton1.setEnabled(true);
                jButton2.setEnabled(false);
            }
        }
    }

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {
        fireInterruptOne(shareName.get(jComboBox1.getSelectedIndex()), false);
    }

    private void jButton2ActionPerformedAll(java.awt.event.ActionEvent evt) {
        fireInterruptAll(false);
    }

    public void addInterruptListener(InterruptListener listener) {
        interruptListeners.add(listener);
    }

    public void removeInterruptListener(InterruptListener listener) {
        interruptListeners.remove(listener);
    }

    /**
     * Notifies all registered listeners that the connections
     * list was modified.
     * @param workbook the workbook that was modified
     */
    private void fireInterruptOne(String shareName, boolean state) {
        InterruptEvent interruptEvent = new InterruptEvent(this, shareName, state);

        for (InterruptListener listener : interruptListeners.toArray(
                new InterruptListener[interruptListeners.size()])) {
            listener.interruptOne(interruptEvent);
        }
    }

    /**
     * Notifies all registered listeners that the connections
     * list was modified.
     * @param workbook the workbook that was modified
     */
    private void fireInterruptAll(boolean state) {

        InterruptEvent interruptEvent = new InterruptEvent(this, state);

        for (InterruptListener listener : interruptListeners.toArray(
                new InterruptListener[interruptListeners.size()])) {
            listener.interruptAll(interruptEvent);
        }


    }

    @Override
    public void connectionsChanged(PageSharingEvent event) {
        List<Connection> connections = event.getConnectionList();
        hosts = new ArrayList<Host>();
        shareName = new ArrayList<String>();
        numberOfShares = new ArrayList<String>();
        numberOfConnected = new ArrayList<String>();
        isInterrupted = new ArrayList<String>();
        int auxIndex = 0;

        String[] hostList;

        for (int i = 0; i < connections.size(); i++) {
            if (connections.get(i).getType().equals("Host")) {
                hosts.add((Host) connections.get(i));
            }
        }
        if (hosts.size() > 0) {

            for (int i = 0; i < hosts.size(); i++) {
                if (!shareName.contains(hosts.get(i).getShareName())) {
                    shareName.add(hosts.get(i).getShareName());
                    numberOfShares.add("1");
                    numberOfConnected.add("0");
                    isInterrupted.add("No");
                } else {
                    auxIndex = shareName.lastIndexOf(hosts.get(i).getShareName());
                    numberOfShares.set(auxIndex, "" + (Integer.parseInt(numberOfShares.get(auxIndex)) + 1));
                }

                if (hosts.get(i).isConnected()) {
                    auxIndex = shareName.lastIndexOf(hosts.get(i).getShareName());
                    numberOfConnected.set(auxIndex, "" + (Integer.parseInt(numberOfConnected.get(auxIndex)) + 1));
                }

                if (hosts.get(i).isInterrupted()) {
                    auxIndex = shareName.lastIndexOf(hosts.get(i).getShareName());
                    isInterrupted.set(auxIndex, "Yes");
                }
            }



            if (allHosts) {

                hostList = new String[shareName.size()];
                for (int i = 0; i < shareName.size(); i++) {
                    hostList[i] = " Share Name : " + shareName.get(i);
                    hostList[i] += " |";
                    hostList[i] += " Nr. of Shares : " + numberOfShares.get(i);
                    hostList[i] += " |";
                    hostList[i] += " Nr. of Connected : " + numberOfConnected.get(i);
                    hostList[i] += " |";
                    hostList[i] += " Interrupted : " + isInterrupted.get(i);
                }
                jList1.setListData(hostList);
            } else {
                hostList = new String[shareName.size()];
                for (int i = 0; i < shareName.size(); i++) {
                    hostList[i] = (i + 1) + ".";
                    hostList[i] += " Share Name : " + shareName.get(i);
                }
                jComboBox1.setModel(new javax.swing.DefaultComboBoxModel(hostList));
                jComboBox1.setSelectedItem(jComboBox1.getSelectedItem());
            }

        }
    }
}