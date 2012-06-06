/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package csheets.ext.share;

import csheets.core.Cell;
import csheets.core.Spreadsheet;
import csheets.ui.ctrl.UIController;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Tiago
 */
public class PageSharingData {

    private List<Cell> cellConnected;
    private int rowNumber;
    private int colNumber;
    private Spreadsheet spreadsheet;
    private PageSharingController connectController;
    private UIController uiController;
    private InetAddress ip;
    private Cell cell;

    public PageSharingData(InetAddress ip, Cell cell, Spreadsheet spreadsheet, PageSharingController aThis, UIController uiController) {
        this.spreadsheet = spreadsheet;
        this.ip = ip;
        this.cell = cell;
        this.connectController = aThis;
        this.uiController = uiController;
    }

    public PageSharingData(List<Cell> cellConnected, int rowNumber, int colNumber, Spreadsheet spreadsheet, PageSharingController aThis, UIController uiController) {
        this.cellConnected = cellConnected;
        this.rowNumber = rowNumber;
        this.colNumber = colNumber;
        this.spreadsheet = spreadsheet;
        this.connectController = aThis;
        this.uiController = uiController;
    }

    public UIController getUiController() {
        return uiController;
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
     * @return the aThis
     */
    public PageSharingController getConnectController() {
        return connectController;
    }

    /**
     * @return the ip
     */
    public InetAddress getIp() {
        return ip;
    }

    /**
     * @return the cell
     */
    public Cell getCell() {
        return cell;
    }
}
