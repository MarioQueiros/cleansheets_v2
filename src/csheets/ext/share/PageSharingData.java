/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package csheets.ext.share;

import csheets.core.Cell;
import csheets.core.Spreadsheet;
import csheets.ui.ctrl.UIController;
import java.net.InetAddress;
import java.net.Socket;
import java.util.List;

/**
 * A classe que contém a informação de uma conecção passada pela interface ao
 * modelo de negócio (core) da Partilha de Folha
 * 
 * @author Tiago
 */
public class PageSharingData {
    private Socket connectedSocket;
    private List<Cell> cellConnected;
    private int rowNumber;
    private int colNumber;
    private Spreadsheet spreadsheet;
    private UIController uiController;
    private InetAddress ip;
    private Cell cell;
    private int numberOfShares;
    private String shareName;

    public PageSharingData(Socket connectedSocket, InetAddress ip, Cell cell, Spreadsheet spreadsheet, UIController uiController,String shareName) {
        this.connectedSocket = connectedSocket;
        this.spreadsheet = spreadsheet;
        this.ip = ip;
        this.cell = cell;
        this.uiController = uiController;
        this.shareName = shareName;
    }

    public PageSharingData(String shareName, int numShares, List<Cell> cellConnected, int rowNumber, int colNumber, Spreadsheet spreadsheet, UIController uiController) {
        this.cellConnected = cellConnected;
        this.rowNumber = rowNumber;
        this.colNumber = colNumber;
        this.spreadsheet = spreadsheet;
        this.uiController = uiController;
        this.numberOfShares = numShares;
        this.shareName = shareName;
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

    public Socket getConnectedSocket() {
        return connectedSocket;
    }

    /**
     * @return the numberOfShares
     */
    public int getNumberOfShares() {
        return numberOfShares;
    }

    /**
     * @return the shareName
     */
    public String getShareName() {
        return shareName;
    }
}
