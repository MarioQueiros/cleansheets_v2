/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package csheets.ext.share.ui;

import csheets.CleanSheets;
import csheets.core.Cell;
import csheets.core.Spreadsheet;
import csheets.ext.share.PageSharingController;
import csheets.ui.ctrl.UIController;
import csheets.ui.sheet.SpreadsheetTableModel;
import java.awt.Toolkit;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author Tiago
 */
public class ClientFrame extends JFrame {

    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton3;
    private javax.swing.JComboBox jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JProgressBar jProgressBar1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JTextField jTextField6;
    private UIController uiController;
    private List<ClientListener> clientListeners;
    private final int PORT=53531;
    
    
    private InetAddress ipHost;
    private Socket socket;
    private String shareName;
    private Cell fCell;
    
    
    /* A Frame for the ClientAction */
    /* Lacks limit of chars in Text Fields and Listeners*/
    public ClientFrame(UIController uiController) {
        this.uiController = uiController;
        clientListeners = new ArrayList<ClientListener>();
        
        addClientListener(PageSharingController.getInstance());

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jProgressBar1 = new javax.swing.JProgressBar();
        jComboBox1 = new javax.swing.JComboBox();
        jButton3 = new javax.swing.JButton();
        jTextField1 = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jTextField3 = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jTextField4 = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jTextField5 = new javax.swing.JTextField();
        jTextField6 = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Connect to");
        setResizable(false);

        jLabel1.setText("Select the IP address of the Host and the Address of the first cell to be connected.");

        jLabel2.setText("IP Address");

        jLabel3.setText("First Cell");

        jLabel4.setText("Column");

        jLabel5.setText("Row");

        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });

        jButton3.setText("Send!");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jTextField1.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jLabel6.setText(".");

        jTextField2.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jTextField3.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jLabel7.setText(".");

        jTextField4.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jLabel8.setText(".");
        jLabel8.setToolTipText("");

        jTextField5.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jTextField6.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jButton1.setText("Connect !");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel9.setText("Choose a share connection from the above.");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(48, 48, 48)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel9)
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jProgressBar1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jComboBox1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 270, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 62, Short.MAX_VALUE)
                                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jSeparator1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 446, Short.MAX_VALUE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addGap(27, 27, 27)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel2)
                                            .addComponent(jLabel4)
                                            .addComponent(jLabel5))
                                        .addGap(18, 18, 18)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(jTextField6, 0, 0, Short.MAX_VALUE)
                                            .addComponent(jTextField5, 0, 0, Short.MAX_VALUE)
                                            .addComponent(jTextField1, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE))
                                        .addGap(6, 6, 6)
                                        .addComponent(jLabel6)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel8)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 4, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(4, 4, 4)
                                        .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 78, Short.MAX_VALUE)
                                        .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel3)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 378, Short.MAX_VALUE)))))
                        .addGap(66, 66, 66))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addComponent(jLabel1)
                .addGap(25, 25, 25)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6)
                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8)
                    .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7)
                    .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(26, 26, 26)
                .addComponent(jLabel3)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(jTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, 39, Short.MAX_VALUE)
                        .addGap(34, 34, 34)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel9)
                .addGap(9, 9, 9)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.DEFAULT_SIZE, 36, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jProgressBar1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(23, 23, 23))
        );

        pack();
        setLocationRelativeTo(null);
        setResizable(false);
    }
    

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
    }

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {
        boolean error;
        String ip1, ip2, ip3, ip4, ip;
        String col = "";
        int row = 0;
        InetAddress ad;
        Cell firstCell = null;

        ip1 = jTextField1.getText();
        ip2 = jTextField2.getText();
        
        ip3 = jTextField4.getText();
        ip4 = jTextField3.getText();


        try {
            col = jTextField5.getText();
            row = Integer.parseInt(jTextField6.getText());
            firstCell = checkDataInsered(ip1, ip2, ip3, ip4, col, row, firstCell);
        } catch (Exception e) {
            firstCell = null;
        }

        try {
            if (firstCell != null) {
                ip = ip1 + "." + ip2 + "." + ip3 + "." + ip4;

                ad = InetAddress.getByName(ip);
                
                chooseHostToConnect(ad,firstCell);
                
                setVisible(false);
            } else {
                JOptionPane.showMessageDialog(null, "Error in data insered!");
            }
        } catch (UnknownHostException ex) {
            JOptionPane.showMessageDialog(null, "Error connecting to the Host!");
        }




    }

    private Cell checkDataInsered(String ip1, String ip2, String ip3, String ip4, String col, int row, Cell firstCell) {

        int numberAux1 = 0, numberAux2 = 0, numberAux3 = 0, numberAux4 = 0, colFirst = -1;

        try {
            numberAux1 = Integer.parseInt(ip1);
            numberAux2 = Integer.parseInt(ip2);
            numberAux3 = Integer.parseInt(ip3);
            numberAux4 = Integer.parseInt(ip4);
            if ((numberAux1 < 0 || numberAux1 > 255) || (numberAux2 < 0 || numberAux2 > 255) || (numberAux3 < 0 || numberAux3 > 255) || (numberAux4 < 0 || numberAux4 > 255)) {
                return null;
            }
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

    public void addClientListener(ClientListener listener) {
        clientListeners.add(listener);
    }

    public void removeClientListener(ClientListener listener) {
        clientListeners.remove(listener);
    }

    /**
     * Notifies all registered listeners that the connections
     * list was modified.
     * @param workbook the workbook that was modified
     */
    private void fireConnectionAdded(InetAddress ip, Cell firstCell, Spreadsheet spreadsheet, Socket connectedSocket,String connectName) {
        ClientEvent clientEvent = new ClientEvent(this, ip, firstCell, spreadsheet,uiController,connectedSocket,connectName);

        for (ClientListener listener : clientListeners.toArray(
                new ClientListener[clientListeners.size()])) {
            listener.connect(clientEvent);
        }


    }

    private void chooseHostToConnect(InetAddress ad, Cell firstCell) {
        try {
            String stream;
            int hostsAvailable;
            String [] split;
            Socket socket = new Socket(ad, PORT);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            
            stream = in.readLine();
            if(stream.contains("No hosts available!")){
                JOptionPane.showMessageDialog(this, "No hosts available to connect in that IP!");
            }
            else{
                int response = -1;
                hostsAvailable = Integer.parseInt(stream);
                split = new String[hostsAvailable];
                stream = in.readLine();
                split = stream.split("-");
                
                jComboBox1.setModel(new javax.swing.DefaultComboBoxModel(split));
                jComboBox1.setSelectedItem(jComboBox1.getSelectedItem());
                
                out.write((jComboBox1.getSelectedIndex()+1)+"\n");
                out.flush();
                stream = in.readLine();
                if(stream.equals("Connection started!")){
                    stream = split[jComboBox1.getSelectedIndex()];
                    split = stream.split(" :");
                    ipHost = ad;
                    fCell = firstCell;
                    shareName = split[0];
                    
                }else{
                    JOptionPane.showMessageDialog(this, "No hosts available to connect in that IP!");
                }
            }
            
        
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "No hosts available to connect in that IP!");
        }
        
    }
    
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {
        fireConnectionAdded(ipHost, fCell, uiController.getActiveSpreadsheet(),socket,shareName);
        JOptionPane.showMessageDialog(this, "Connection started!");
        setVisible(false);
    }
}
