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
public class InterruptEvent extends EventObject{

    private String shareName;
    private boolean interrupted;
    
    public InterruptEvent(Object source, String shareName, boolean state) {
        super(source);
        this.interrupted = state;
        this.shareName = shareName;
    }

    public InterruptEvent(Object source, boolean state) {
        super(source);
        this.interrupted = state;
    }

    /**
     * @return the shareIndex
     */
    public String getShareName() {
        return shareName;
    }

    /**
     * @return the state
     */
    public boolean isInterrupted() {
        return interrupted;
    }
    

}
