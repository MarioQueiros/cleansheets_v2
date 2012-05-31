/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package csheets.ui.share;

import csheets.ui.ctrl.BaseAction;
import csheets.ui.share.CreateConnectionFrame;
import java.awt.event.ActionEvent;

/**
 *
 * @author Tiago
 */
public class HostAction extends BaseAction{

    CreateConnectionFrame cFrame = new CreateConnectionFrame();
    
    public HostAction() {
        cFrame.setVisible(false);
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
