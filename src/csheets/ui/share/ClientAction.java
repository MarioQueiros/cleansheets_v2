/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package csheets.ui.share;

import csheets.CleanSheets;
import csheets.sp.Connection;
import csheets.sp.ConnectionController;
import csheets.ui.ctrl.BaseAction;
import csheets.ui.ctrl.UIController;
import java.awt.Dimension;
import java.awt.event.ActionEvent;

/**
 *
 * @author Tiago
 */
public class ClientAction extends BaseAction{
    
    
    ConnectFrame cFrame= new ConnectFrame();
    AreaFrame aFrame= new AreaFrame();
    Dimension d = new Dimension();
    
    String ip;
    Connection connection;
    
    UIController uiController;
    CleanSheets app;
    ConnectionController connectController;
    
    public ClientAction(CleanSheets app, UIController uiController, ConnectionController connectController) {
        this.uiController = uiController;
        this.app = app;
        this.connectController = connectController;
        
        cFrame.setUIController(uiController);
        cFrame.setConnectionController(connectController);
        cFrame.setVisible(false);
        
        /*aFrame.setController(uiController);
        aFrame.setVisible(false);*/
    }
    
    @Override
    protected String getName() {
        return "Connect to...";
    }

    @Override
    public void actionPerformed(ActionEvent e) {
            cFrame.setVisible(true);
        
        
            /*cFrame.setVisible(true);
            
            d.width=cFrame.getColumns();
            d.height=cFrame.getRows();
            
            
            aFrame.getFirstCell();*/
        
        
    }
    
}
