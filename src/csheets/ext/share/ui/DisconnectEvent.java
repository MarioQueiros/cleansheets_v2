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
    
    private int connectioIndex;
    
    public DisconnectEvent(Object source,int connectioIndex){
        super(source);
        this.connectioIndex = connectioIndex;
    }

    /**
     * @return the connection
     */
    public int getConnectionIndex() {
        return connectioIndex;
    }
    
}
