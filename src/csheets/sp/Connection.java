/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package csheets.sp;

import csheets.core.Spreadsheet;
import csheets.core.Workbook;
import java.io.Serializable;
import java.net.InetAddress;

/**
 *
 * @author Tiago
 */
public class Connection implements Serializable{
    
    protected final int PORT=53531;
    protected InetAddress address;
    protected Workbook connectedWorkbook;
    protected Spreadsheet connectedSpreadsheet;

    public Workbook getWorkbook() {
        return connectedWorkbook;
    }
    
    public Spreadsheet getSpreasheet() {
        return connectedSpreadsheet;
    }

    public InetAddress getAddress() {
        return address;
    }

    
}
