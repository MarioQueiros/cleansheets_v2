/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package csheets.sp;

import csheets.SpreadsheetAppEvent;
import csheets.core.Spreadsheet;
import csheets.core.Workbook;
import java.util.EventObject;

/**
 *
 * @author Tiago
 */
public class ConnectionEvent extends EventObject{
    
        /** The connected workbook */
	private Workbook workbook;

	/** The connected piece of spreadsheet. */
	private Spreadsheet spreadsheet;
        
        /** The active connection */
        private Connection connection;

    

    

    /**
     * @return the type
     */
    public Type getType() {
        return type;
    }
        
        /** The types of events that are fired from a connection controller */
	public enum Type {

		/** Denotes that a connection was created */
		CREATED,

		/** Denotes that a connection content was modified */
		MODIFIED,

		/** Denotes that a connection was removed */
		REMOVED,
	}
        
        private Type type;
        
    public ConnectionEvent(Object source, Connection connection ,Type type){
        super(source);
        this.connection = connection;
        this.type = type;
    }

    /**
     * @return the workbook
     */
    public Workbook getWorkbook() {
        return workbook;
    }

    /**
     * @return the spreadsheet
     */
    public Spreadsheet getSpreadsheet() {
        return spreadsheet;
    }

    /**
     * @return the connection
     */
    public Connection getConnection() {
        return connection;
    }

    
    
}
