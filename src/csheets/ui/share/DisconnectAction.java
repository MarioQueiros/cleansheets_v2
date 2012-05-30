/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package csheets.ui.share;

import csheets.ui.ctrl.BaseAction;
import java.awt.event.ActionEvent;

/**
 *
 * @author Tiago
 */
public class DisconnectAction extends BaseAction{
            DisconnectFrame dscFrame = new DisconnectFrame();

    public DisconnectAction(){
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
