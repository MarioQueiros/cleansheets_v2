/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package csheets.ext.share.ui;

import java.util.EventListener;

/**
 *
 * @author Tiago
 */
public interface ClientListener extends EventListener{
    
    public void connect(ClientEvent event);
    
}
