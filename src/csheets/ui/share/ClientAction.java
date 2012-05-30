/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package csheets.ui.share;

import csheets.ui.ctrl.BaseAction;
import csheets.ui.share.ConnectFrame;
import java.awt.event.ActionEvent;

/**
 *
 * @author Tiago
 */
public class ClientAction extends BaseAction{
    
    ConnectFrame cFrame = new ConnectFrame();
    
    public ClientAction() {
        cFrame.setVisible(false);
    }
    
    @Override
    protected String getName() {
        return "Connect to...";
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        
        cFrame.setVisible(false);
        cFrame.setVisible(true);
        
    }
    
}
