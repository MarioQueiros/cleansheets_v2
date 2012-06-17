/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package csheets.ext.share;

import java.util.EventListener;

/**
 * Interface das classes que escutam as mudanças da lista de conecções
 * @author Tiago
 */

public interface PageSharingListener extends EventListener{
    
    public void connectionsChanged(PageSharingEvent event);

    public void connectionsInterrupted(String shareName,boolean interrupted);
    
    public void serverInstanceOn(boolean state);
    
}
