/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package csheets.ext.share;

import java.util.EventListener;

/**
 *
 * @author Tiago
 */
public interface PageSharingListener extends EventListener{
    
    public void connectionsChanged(PageSharingEvent event);
    
}
