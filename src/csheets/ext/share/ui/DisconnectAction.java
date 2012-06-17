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
public class DisconnectAction extends BaseAction{
    
            DisconnectFrame dscFrame;

    public DisconnectAction(UIController uiController){
           dscFrame = new DisconnectFrame(uiController);
           dscFrame.setVisible(false);
           dscFrame.setLocationRelativeTo(null);
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
