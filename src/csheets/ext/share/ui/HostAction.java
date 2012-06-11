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
public class HostAction extends BaseAction{
    HostFrame hFrame;
    
    public HostAction(UIController uiController) {
        
        hFrame = new HostFrame(uiController);
        hFrame.setVisible(false);
        
    }
    @Override
    protected String getName() {
        return "Create connection...";
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        
        hFrame.setVisible(true);
        
        
    }
    
}
