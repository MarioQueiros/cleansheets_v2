/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package csheets.ui.share;

import csheets.core.Spreadsheet;
import csheets.sp.ConnectionController;
import csheets.sp.ConnectionEvent;
import csheets.sp.ConnectionListener;
import csheets.sp.Host;
import csheets.ui.ctrl.UIController;
import csheets.ui.sheet.SpreadsheetTableModel;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author Tiago
 */
public class CreateConnectionFrame extends JFrame{
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JTextField jTextField6;
    private javax.swing.JTextField jTextField7;
    private javax.swing.JTextField jTextField8;
    private UIController uiController;
    private ConnectionController connectController;
    
    public CreateConnectionFrame(){
        

        jLabel2 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jTextField5 = new javax.swing.JTextField();
        jTextField6 = new javax.swing.JTextField();
        jTextField7 = new javax.swing.JTextField();
        jTextField8 = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel2.setText("Area");

        jButton1.setText("Create Connection!");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        /*jTextField5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField5ActionPerformed(evt);
            }
        });*/

        jLabel6.setText("First Cell");

        jLabel7.setText("Last Cell");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addGap(71, 71, 71)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField7, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(55, 55, 55)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField8, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(36, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(170, Short.MAX_VALUE)
                .addComponent(jButton1)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel6)
                    .addComponent(jLabel7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField7, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField8, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(52, 52, 52)
                .addComponent(jButton1)
                .addGap(35, 35, 35))
        );

        pack();
    }
    
    public void jButton1ActionPerformed(java.awt.event.ActionEvent evt){
        /*boolean error=false;
        String col1,col2;
        int row1=0,row2=0;
        col1 = jTextField5.getText();
        col2 = jTextField7.getText();
        
        try{
            row1=Integer.parseInt(jTextField6.getText());
            row2=Integer.parseInt(jTextField8.getText());
            error=checkDataInsered(col1,col2,row1,row2);
        }catch(Exception e){
            error=true;
        }*/
        
        //if(error == false){
        connectController.createConnect("A1","D2",uiController.getActiveWorkbook(),uiController.getActiveSpreadsheet());
        /*}
        else{
            JOptionPane.showMessageDialog(null, "Error in data insered!");
        }*/
    }
    
    private boolean checkDataInsered(String col1,String col2, int row1, int row2){
        boolean error=false;
        int colFirst=0,colLast=0;
        
        SpreadsheetTableModel aux = new SpreadsheetTableModel(uiController.getActiveSpreadsheet(),uiController);
        int activeSpreadsheetRows = aux.getRowCount();
        int activeSpreadsheetColumns = aux.getColumnCount();
        
        String[] expectedColumns = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M",
                "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z", "AA", "AB", "AC", "AD", "AE", "AF", "AG",
                "AH", "AI", "AJ", "AK", "AL", "AM", "AN", "AO", "AP", "AQ", "AR", "AS", "AT", "AU", "AV", "AW", "AX", "AY", "AZ"};
        
        col1=col1.toUpperCase();
        col2=col2.toUpperCase();
        for(int i=0;i<activeSpreadsheetColumns;i++){
            if(expectedColumns[i].equals(col1)) {
                colFirst=i;
            }
            if(expectedColumns[i].equals(col2)) {
                colLast=i;
            }
        }
        
        if( colFirst == 0||colLast == 0|| row1 < row2 || colFirst < colLast ){
            return true;
        }
        
        return false;
    }

    
    public void setUIController(UIController uiController) {
        this.uiController=uiController;
    }

    void setConnectionController(ConnectionController connectController) {
        this.connectController = connectController;
    }

}
