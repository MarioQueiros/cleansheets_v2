/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package csheets.ext.share.ui;

import csheets.ext.share.PageSharingController;
import csheets.ext.share.PageSharingEvent;
import csheets.ext.share.PageSharingListener;
import csheets.ext.share.connect.Connection;
import csheets.ui.ctrl.UIController;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author Tiago
 */
public class DisconnectFrame extends JFrame implements PageSharingListener{
    
    private javax.swing.JButton jButton1;
    private javax.swing.JComboBox jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private PageSharingController pageSharingController;
    private UIController uiController;
    private List<DisconnectListener> disconnectListeners;
    
    public DisconnectFrame(UIController uiController, PageSharingController pageSharingController){
        this.uiController = uiController;
        disconnectListeners = new ArrayList<DisconnectListener>();
        
        PageSharingController.getInstance().addConnectionListener(this);
        addDisconnectListener(PageSharingController.getInstance());
        
        
        jLabel2 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jComboBox1 = new javax.swing.JComboBox();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel2.setText("Connections:");

        jButton1.setText("Disconnect!");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        
        
        jLabel1.setText("Select one connection to be disconnected.");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addGap(34, 34, 34)
                                .addComponent(jComboBox1, 0, 189, Short.MAX_VALUE))
                            .addComponent(jButton1, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addGap(25, 25, 25))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addContainerGap(108, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(65, 65, 65)
                        .addComponent(jButton1))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(19, Short.MAX_VALUE))
        );

        pack();
        setSize(this.getWidth()+200, this.getHeight());
        setLocationRelativeTo(null);
    }

    private void jButton1ActionPerformed(ActionEvent evt) {
        if(jComboBox1.getSelectedItem()!=null){
            String [] split = ((String)jComboBox1.getSelectedItem()).split(";");
            fireConnectionRemoved(split[0]);
            jComboBox1.removeItem(jComboBox1.getSelectedItem());
        }
        else{
            JOptionPane.showMessageDialog(null,"Error in removing connection!");
        }
    }

    @Override
    public void connectionsChanged(PageSharingEvent event) {
        List <Connection> connections = event.getConnectionList();
        String [] comboBoxSelections = new String [connections.size()];
        if(!connections.isEmpty()){
            for(int i=0;i<connections.size();i++){
                try{
                    comboBoxSelections[i] = connections.get(i).getType();
                    comboBoxSelections[i] +=" : "+connections.get(i).getAddress().toString();
                    comboBoxSelections[i] +="; From ";
                    comboBoxSelections[i] +=connections.get(i).getConnectedCells().get(0).getAddress();
                    comboBoxSelections[i] +=" to ";
                    comboBoxSelections[i] +=connections.get(i).getConnectedCells().get(
                            connections.get(i).getConnectedCells().size()-1).getAddress();
                }catch(IndexOutOfBoundsException e){
                    comboBoxSelections[i] = "Connection with Errors!";
                }
            }
        }
        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel(comboBoxSelections));
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
    private void fireConnectionRemoved(String connection) {
        DisconnectEvent disconnectEvent = new DisconnectEvent(this,connection);
        
        for (DisconnectListener listener : disconnectListeners.toArray(
				new DisconnectListener[disconnectListeners.size()]))
			listener.disconnect(disconnectEvent);
	
        
    }
}
