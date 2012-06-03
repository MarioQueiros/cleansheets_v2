/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package csheets.ui.share;

import csheets.core.Cell;
import csheets.core.Spreadsheet;
import csheets.sp.ConnectionController;
import csheets.ui.ctrl.UIController;
import csheets.ui.sheet.SpreadsheetTableModel;
import java.net.InetAddress;
import java.net.UnknownHostException;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author Tiago
 */
public class ConnectFrame extends JFrame{

    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField6;
    private javax.swing.JTextField jTextField7;
    private UIController uiController;
    private ConnectionController connectController;
    
    
    /* A Frame for the ClientAction */
    /* Lacks limit of chars in Text Fields and Listeners*/
    
    public ConnectFrame(){
    
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jTextField2 = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jTextField3 = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jTextField4 = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jTextField6 = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jTextField7 = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setText("IP Address");

        jLabel2.setText("Area");

        jTextField1.setColumns(3);
        jTextField1.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField1.setToolTipText("");
        

        jTextField2.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jButton1.setText("Create Connection!");
        
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jTextField3.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jLabel3.setText(".");

        jLabel4.setText(".");

        jLabel5.setText(".");

        jTextField4.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jLabel8.setText("Select the IP Address and the dimension of the Area to be synced.");

        jTextField6.setColumns(3);
        jTextField6.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField6.setToolTipText("");
        

        jLabel6.setText("x");

        jTextField7.setColumns(3);
        jTextField7.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField7.setToolTipText("");
        

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(154, 154, 154)
                        .addComponent(jButton1))
                    .addComponent(jLabel8)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2))
                        .addGap(48, 48, 48)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jTextField7)
                            .addComponent(jTextField1, javax.swing.GroupLayout.DEFAULT_SIZE, 35, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel6)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextField6)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(19, 19, 19))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel8)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jLabel1)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5)
                    .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel6)
                    .addComponent(jTextField7, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(45, 45, 45)
                .addComponent(jButton1)
                .addGap(22, 22, 22))
        );

        pack();
    
    }

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {  
        boolean error;
        String ip1,ip2,ip3,ip4,ip;
        String col="";
        int row=0;
        InetAddress ad;
        Cell firstCell = null;
        
        ip1=jTextField1.getText();
        ip2=jTextField2.getText();
        ip3=jTextField3.getText();
        ip4=jTextField4.getText();
        
       
        
        try{
            col=jTextField7.getText();
            row=Integer.parseInt(jTextField6.getText());
            firstCell=checkDataInsered(ip1,ip2,ip3,ip4,col,row,firstCell);
        }catch(Exception e){
            firstCell=null;
        }
        
        try {
            if(firstCell!=null){
                ip=ip1+"."+ip2+"."+ip3+"."+ip4;

                ad = InetAddress.getByName(ip);
                
                connectController.connect(ad,firstCell,uiController.getActiveSpreadsheet());
            }
            else{
                JOptionPane.showMessageDialog(null,"Error in data insered!");
            }
        } catch (UnknownHostException ex) {
                JOptionPane.showMessageDialog(null,"Error connecting to the Host!");
        }
        
        

      
    }
    
    
    private Cell checkDataInsered(String ip1,String ip2,String ip3,String ip4,String col, int row, Cell firstCell){
        
        int numberAux1=0,numberAux2=0,numberAux3=0,numberAux4=0,colFirst=-1;
        
        try{
            numberAux1=Integer.parseInt(ip1);
            numberAux2=Integer.parseInt(ip2);
            numberAux3=Integer.parseInt(ip3);
            numberAux4=Integer.parseInt(ip4);
            if((numberAux1<0||numberAux1>255)||(numberAux2<0||numberAux2>255)||(numberAux3<0||numberAux3>255)||(numberAux4<0||numberAux4>255)){
                return null;
            }
            SpreadsheetTableModel aux = new SpreadsheetTableModel(uiController.getActiveSpreadsheet(),uiController);
            int activeSpreadsheetRows = aux.getRowCount();
            int activeSpreadsheetColumns = aux.getColumnCount();


            if(row < 0 || row > activeSpreadsheetRows){
                return null;
            }
            
            String[] expectedColumns = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M",
                "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z", "AA", "AB", "AC", "AD", "AE", "AF", "AG",
                "AH", "AI", "AJ", "AK", "AL", "AM", "AN", "AO", "AP", "AQ", "AR", "AS", "AT", "AU", "AV", "AW", "AX", "AY", "AZ"};
        
        
            col=col.toUpperCase();
            for(int i=0;i<activeSpreadsheetColumns;i++){
                if(expectedColumns[i].equals(col)) {
                    colFirst=i;
                }
            }
            
            if( colFirst == -1){
                return null;
            }
        }
        catch(Exception e){
            return null;
        }
        
        firstCell = uiController.getActiveSpreadsheet().getCell(colFirst,(row-1));
        
        return firstCell;
    }
    
     void setUIController(UIController uiController) {
        this.uiController=uiController;
     }
     
     
    public void setConnectionController(ConnectionController connectController) {
        this.connectController=connectController;
    }
}
