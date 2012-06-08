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
public interface HostListener extends EventListener{
    
    public void createConnect(HostEvent event);
    
}
