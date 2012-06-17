/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package csheets.ext.share.ui;

import csheets.ui.ctrl.BaseAction;
import csheets.ui.ctrl.UIController;
import java.awt.event.ActionEvent;

/**
 *
 * @author Tiago
 */
public class ServerListAction extends BaseAction{
    
    ClientFrameServerList serverListFrame;
    
    public ServerListAction(UIController uiController) {
        serverListFrame = new ClientFrameServerList(uiController);
        serverListFrame.setVisible(false);
        serverListFrame.setLocationRelativeTo(null);
    }
    
    @Override
    protected String getName() {
        return "Connections List";
    }

    @Override
    public void actionPerformed(ActionEvent e) {
            serverListFrame.setVisible(true);
    }
}
