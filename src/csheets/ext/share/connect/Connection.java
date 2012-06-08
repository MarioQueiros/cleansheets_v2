/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package csheets.ext.share.connect;

import csheets.core.Address;
import csheets.core.Cell;
import csheets.core.CellListener;
import csheets.core.Spreadsheet;
import csheets.core.Workbook;
import csheets.ext.share.PageSharingController;
import csheets.ext.share.ui.ClientListener;
import csheets.ext.share.ui.DisconnectListener;
import csheets.ext.share.ui.HostListener;
import csheets.ui.ctrl.UIController;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.net.InetAddress;
import java.util.List;

/**
 *
 * @author Tiago
 */
public abstract class Connection extends Thread implements Serializable, CellListener{
    
    
    protected String shareName;
    protected String type;
    protected InetAddress address;
    protected Spreadsheet connectedSpreadsheet;
    protected List<Cell> connectedCells;
    protected Workbook connectedWorkbook;
    protected List<Address> connectedFrom;
    protected PageSharingController pageSharingController;
    protected UIController uiController;
    
    public Workbook getWorkbook() {
        return connectedWorkbook;
    }
    
    public Spreadsheet getSpreadsheet() {
        return connectedSpreadsheet;
    }

    public InetAddress getAddress() {
        return address;
    }

    public String getType() {
        return type;
    }
    
    public void setType(String string) {
        type = string;
    }

    public abstract void closeSockets();
    
    public abstract void removeListeners();

    /**
     * @return the connectedCells
     */
    public List<Cell> getConnectedCells() {
        return connectedCells;
    }

    /**
     * @return the shareName
     */
    public String getShareName() {
        return shareName;
    }
    
    class AppendableObjectOutputStream extends ObjectOutputStream {

        public AppendableObjectOutputStream(OutputStream os) throws IOException {
            super(os);
        }

        protected void writeStreamHeader() {
        }
    }
}
