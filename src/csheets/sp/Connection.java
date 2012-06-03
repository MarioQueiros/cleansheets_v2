/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package csheets.sp;

import csheets.core.Address;
import csheets.core.Cell;
import csheets.core.CellListener;
import csheets.core.Spreadsheet;
import csheets.core.Workbook;
import java.io.Serializable;
import java.net.InetAddress;
import java.util.List;

/**
 *
 * @author Tiago
 */
public abstract class Connection implements Serializable, Runnable, CellListener{
    
    protected final int PORT=53531;
    protected String type;
    protected InetAddress address;
    protected Spreadsheet connectedSpreadsheet;
    protected List<Cell> connectedCells;
    protected Workbook connectedWorkbook;
    protected List<Address> connectedFrom;
    protected ConnectionController connectController;
    
    public Workbook getWorkbook() {
        return connectedWorkbook;
    }
    
    public Spreadsheet getSpreasheet() {
        return connectedSpreadsheet;
    }

    public InetAddress getAddress() {
        return address;
    }

    public String getType() {
        return type;
    }
    
    void setType(String string) {
        type = string;
    }

    abstract void closeSockets();
    
    abstract void removeListeners();
}
