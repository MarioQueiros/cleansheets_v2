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
    private String password;
    private String shareName;
    private String type;
    private boolean interrupted = false;
    private int numberConnections;

    public PageSharingData(Socket connectedSocket, InetAddress ip, Cell cell, Spreadsheet spreadsheet, UIController uiController,String shareName,String type) {
        this.connectedSocket = connectedSocket;
        this.spreadsheet = spreadsheet;
        this.ip = ip;
        this.cell = cell;
        this.uiController = uiController;
        this.shareName = shareName;
        this.type = type;
    }

    public PageSharingData(String shareName, String password, List<Cell> cellConnected, int rowNumber, int colNumber, Spreadsheet spreadsheet, UIController uiController,String type) {
        this.cellConnected = cellConnected;
        this.rowNumber = rowNumber;
        this.colNumber = colNumber;
        this.spreadsheet = spreadsheet;
        this.uiController = uiController;
        this.password = password;
        this.shareName = shareName;
        this.type = type;
        this.numberConnections = 0;
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
    public String  getPassword() {
        return password;
    }

    /**
     * @return the shareName
     */
    public String getShareName() {
        return shareName;
    }

    /**
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * @return the interrupted
     */
    public boolean isInterrupted() {
        return interrupted;
    }

    void setInterrupted(boolean b) {
        this.interrupted = b;
    }

    /**
     * @return the numberConnections
     */
    public int getNumberConnections() {
        return numberConnections;
    }

    public void addConnection() {
        numberConnections++;
    }
    
    public void removeConnection() {
        numberConnections = numberConnections - 1;
    }
}
