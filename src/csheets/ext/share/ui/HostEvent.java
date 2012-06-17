/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package csheets.ext.share.ui;

import csheets.core.Cell;
import csheets.core.Spreadsheet;
import csheets.ui.ctrl.UIController;
import java.util.EventObject;
import java.util.List;

/**
 *
 * @author Tiago
 */
public class HostEvent extends EventObject{

    private List<Cell> cellConnected;
    private int rowNumber;
    private int colNumber;
    private String password;
    private String shareName;
    private Spreadsheet spreadsheet;
    private UIController uiController;
            
    HostEvent(Object source, List<Cell> cellConnected, int rowNumber, int colNumber, Spreadsheet spreadsheet, UIController uiController,String shareName, char [] password) {
        super(source);
        this.cellConnected = cellConnected;
        this.password = String.valueOf(password);
        this.shareName = shareName;
        this.colNumber = colNumber;
        this.rowNumber = rowNumber;
        this.spreadsheet = spreadsheet;
        this.uiController = uiController;
    }

    /**
     * @return the cellConnected
     */
    public List<Cell> getCellConnected() {
        return cellConnected;
    }

    /**
     * @return the rowNumber
     */
    public int getRowNumber() {
        return rowNumber;
    }

    /**
     * @return the colNumber
     */
    public int getColNumber() {
        return colNumber;
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
     * @return the numberOfShares
     */
    public String getPassword() {
        return password;
    }

    /**
     * @return the shareName
     */
    public String getShareName() {
        return shareName;
    }
    
}
