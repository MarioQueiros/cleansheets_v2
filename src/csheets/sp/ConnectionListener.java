/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package csheets.sp;

import java.util.EventListener;

/**
 *
 * @author Tiago
 */
public interface ConnectionListener extends EventListener{
    
    public void connectionAdded(ConnectionEvent e);
    
    public void connectionRemoved(ConnectionEvent e);
    
    public void connectionContentModified(ConnectionEvent e);
    
}
