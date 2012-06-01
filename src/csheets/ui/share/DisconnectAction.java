/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package csheets.ui.share;

import csheets.CleanSheets;
import csheets.sp.ConnectionController;
import csheets.ui.ctrl.BaseAction;
import csheets.ui.ctrl.UIController;
import java.awt.event.ActionEvent;

/**
 *
 * @author Tiago
 */
public class DisconnectAction extends BaseAction{
    
            UIController uiController;
            
            CleanSheets app;
            
            ConnectionController connectController;
            
            DisconnectFrame dscFrame = new DisconnectFrame();

    public DisconnectAction(CleanSheets app, UIController uiController, ConnectionController connectController){
           
           dscFrame.setUIController(uiController);
           dscFrame.setConnectionController(connectController);
           
           
           this.connectController = connectController;
           this.uiController=uiController;
           this.connectController = connectController;
           
           dscFrame.setVisible(false);
    }
    
    
    @Override
    protected String getName() {
        return "Disconnect from...";
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        dscFrame.refreshJComboBox();
        dscFrame.setVisible(true);
    }
    
}
