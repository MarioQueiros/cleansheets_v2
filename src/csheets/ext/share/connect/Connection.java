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
import csheets.ui.ctrl.UIController;
import java.net.InetAddress;
import java.util.List;

/**
 * A classe pai, têm os atributos gerais das conecções
 * @author Tiago
 */
public abstract class Connection extends Thread implements CellListener{
    
    /** O nome da conecção */
    protected String shareName;
    
    /** O tipo da conecção */
    protected String type;
    
    /** O endereço da conecção, para "Client" é o próprio, para "Host" é
     * aquele ao qual está ligado
     */
    protected InetAddress address;
    
    /** O painel conectado */
    protected Spreadsheet connectedSpreadsheet;
    
    /** As células conectadas */
    protected List<Cell> connectedCells;
    
    /** O livro conectado */
    protected Workbook connectedWorkbook;
    
    /** Os endereços das células conectadas do outro lado da partilha */
    protected List<Address> connectedFrom;
    
    /** O controlador da interface da aplicação */
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
    
}
