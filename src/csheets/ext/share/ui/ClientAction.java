/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package csheets.ext.share.ui;

import csheets.ext.share.PageSharingController;
import csheets.ui.ctrl.BaseAction;
import csheets.ui.ctrl.UIController;
import java.awt.event.ActionEvent;

/**
 *
 * @author Tiago
 */
public class ClientAction extends BaseAction{
    
    ClientFrame cFrame;
    
    public ClientAction(UIController uiController, PageSharingController pageSharingController) {
        cFrame = new ClientFrame(uiController, pageSharingController);
        cFrame.setVisible(false);
        
    }
    
    @Override
    protected String getName() {
        return "Connect to...";
    }

    @Override
    public void actionPerformed(ActionEvent e) {
            cFrame.setVisible(true);
        
        
    }
    
}
