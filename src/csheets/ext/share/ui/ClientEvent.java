/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package csheets.ext.share.ui;

import csheets.core.Cell;
import csheets.core.Spreadsheet;
import csheets.ui.ctrl.UIController;
import java.net.InetAddress;
import java.net.Socket;
import java.util.EventObject;

/**
 *
 * @author Tiago
 */
public class ClientEvent extends EventObject{
    
    private InetAddress ip;
    private Cell firstCell;
    private Spreadsheet spreadsheet;
    private UIController uiController;
    private Socket connectedSocket;
    private String connectName;
    
    public ClientEvent(Object source, InetAddress ip, Cell firstCell, Spreadsheet spreadsheet, UIController uiController,Socket connectedSocket,String connectName) {
        super(source);
        this.connectName = connectName;
        this.connectedSocket = connectedSocket;
        this.ip = ip;
        this.firstCell = firstCell;
        this.spreadsheet = spreadsheet;
        this.uiController = uiController;
    }

    /**
     * @return the ip
     */
    public InetAddress getIp() {
        return ip;
    }

    /**
     * @return the firstCell
     */
    public Cell getFirstCell() {
        return firstCell;
    }

    /**
     * @return the spreadsheet
     */
    public Spreadsheet getSpreadsheet() {
        return spreadsheet;
    }

    /**
     * @return the uiController
     */
    public UIController getUiController() {
        return uiController;
    }

    /**
     * @return the connectedSocket
     */
    public Socket getConnectedSocket() {
        return connectedSocket;
    }

    /**
     * @return the connectName
     */
    public String getConnectName() {
        return connectName;
    }
    
}
