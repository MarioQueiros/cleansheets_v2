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
    
    private String connection;
    
    public DisconnectEvent(Object source,String connection){
        super(source);
        this.connection = connection;
    }

    /**
     * @return the connection
     */
    public String getConnection() {
        return connection;
    }
    
}
