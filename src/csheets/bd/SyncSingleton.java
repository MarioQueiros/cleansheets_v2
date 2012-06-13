/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package csheets.bd;

/**
 *
 * @author joaodias
 */
public class SyncSingleton {

    static private SyncSingleton theInstance;
    public boolean stop;

    private SyncSingleton() {
        stop = true;
    }

    public boolean getStop() {
        return stop;
    }

    public void setStop(boolean s) {
        stop = s;
    }

    static synchronized public SyncSingleton Instance() {
        if (theInstance != null) {
            return theInstance;
        } else {
            theInstance = new SyncSingleton();
            return theInstance;
        }
    }
}
