/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package csheets.ui.share;

import csheets.CleanSheets;
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
            
            
            DisconnectFrame dscFrame = new DisconnectFrame();

    public DisconnectAction(CleanSheets app, UIController uiController){
           this.uiController=uiController;
           dscFrame.setVisible(false);
    }
    
    
    @Override
    protected String getName() {
        return "Disconnect from...";
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        dscFrame.setVisible(true);
    }
    
}
