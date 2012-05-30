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
    AreaFrame aFrame = new AreaFrame();
    
    public ClientAction() {
        cFrame.setVisible(false);
        aFrame.setVisible(false);
        
    }
    
    @Override
    protected String getName() {
        return "Connect to...";
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        
        cFrame.setVisible(true);
        cFrame.getIP();
        cFrame.getArea();
        aFrame.setVisible(true);
        aFrame.getFirstCell();
    }
    
}
