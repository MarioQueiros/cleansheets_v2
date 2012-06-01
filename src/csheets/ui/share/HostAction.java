/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package csheets.ui.share;

import csheets.CleanSheets;
import csheets.sp.ConnectionController;
import csheets.ui.ctrl.BaseAction;
import csheets.ui.ctrl.UIController;
import java.awt.ComponentOrientation;
import java.awt.event.ActionEvent;

/**
 *
 * @author Tiago
 */
public class HostAction extends BaseAction{
    UIController uiController;
    CleanSheets app;
    ConnectionController connectController;
    
    CreateConnectionFrame cFrame = new CreateConnectionFrame();
    
    public HostAction(CleanSheets app, UIController uiController, ConnectionController connectController) {
        this.app = app;
        this.connectController = connectController;
        this.uiController=uiController;
        
        cFrame.setVisible(false);
        cFrame.setUIController(uiController);
        cFrame.setConnectionController(connectController);
    }
    @Override
    protected String getName() {
        return "Create connection...";
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        
        cFrame.setVisible(true);
        
        
    }
    
}
