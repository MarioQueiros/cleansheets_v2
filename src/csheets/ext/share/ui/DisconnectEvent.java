/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package csheets.ext.share.ui;

import java.util.EventObject;

/**
 *
 * @author Tiago
 */
public class DisconnectEvent extends EventObject{
    
    private int connectionIndex;
    
    public DisconnectEvent(Object source,int connectionIndex){
        super(source);
        this.connectionIndex = connectionIndex;
    }

    /**
     * @return the connection
     */
    public int getConnectionIndex() {
        return connectionIndex;
    }
    
}
