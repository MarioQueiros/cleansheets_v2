/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package csheets.ext.share.ui;

import csheets.ext.share.PageSharingController;
import csheets.ext.share.PageSharingEvent;
import csheets.ext.share.PageSharingListener;

/**
 *
 * @author Tiago
 */
public class IPLabel extends javax.swing.JLabel implements PageSharingListener{

    public IPLabel () {
        PageSharingController.getInstance().addConnectionListener(this);
        
        setText("Client version");
    }

    @Override
    public void connectionsChanged(PageSharingEvent event) {
    }

    @Override
    public void connectionsInterrupted(String shareName, boolean interrupted) {
    }

    @Override
    public void serverInstanceOn(boolean state) {
        if(state){
            setText("Server & Client version - "
                    +PageSharingController.getInstance().getAppServer().getServerSocket().getInetAddress().getHostAddress().toString());
        }
        else{
            setText("Client only version");
        }
    }

}
