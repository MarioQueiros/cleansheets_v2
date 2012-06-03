/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package csheets.sp;

import csheets.CleanSheets;
import csheets.SpreadsheetAppEvent;
import csheets.SpreadsheetAppListener;
import csheets.core.Cell;
import csheets.core.Spreadsheet;
import csheets.ui.ctrl.EditEvent;
import csheets.ui.ctrl.EditListener;
import csheets.ui.ctrl.SelectionEvent;
import csheets.ui.ctrl.SelectionListener;
import csheets.ui.ctrl.UIController;
import java.awt.EventQueue;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

/**
 *
 * @author Tiago
 */

public class ConnectionController implements EditListener,SpreadsheetAppListener,SelectionListener, ConnectionListener{
    
    
    private List <Thread> threadManager = new ArrayList<Thread> () ;
    
    private CleanSheets app;
    
    private UIController uiController;
    
    private List<Connection> connections = new ArrayList <Connection>();
    
    /** The connection listeners registered to receive events */
    private List<ConnectionListener> connectionListeners = new ArrayList<ConnectionListener>();
    
    public ConnectionController(CleanSheets app){
        app.addSpreadsheetAppListener(this);
        this.addConnectionListener(this);
    }
    
    /* Um pc pode criar um e um só servidor, dois pcs só se podem ligar uma vez, numa célula, numa spreadsheet*/
    
    public void addUIListeners(UIController uiController){
        this.uiController = uiController;
        uiController.addEditListener(this);
        uiController.addSelectionListener(this);
    }
    
    public boolean isConnectedTo(InetAddress ip){
        for(int i = 0;i<connections.size();i++){
            if(connections.get(i).getAddress().equals(ip)){
                return true;
            }
        }
        return false;
    }
    
    public void connectionAdded(ConnectionEvent e){
        Thread t1;
        
        try{
            if((e.getConnection().getClass().getName().contains("Host"))&&(isConnectedTo(e.getConnection().getAddress())==false)){
                
                ((Host)e.getConnection()).setType("Host");
                connections.add(e.getConnection());
                
                t1= new Thread(((Host)e.getConnection()));
                t1.setName("Host : "+e.getConnection().getAddress());
                threadManager.add(t1);
                t1.start();
                
                
            }else if((e.getConnection().getClass().getName().contains("Client"))&&(isConnectedTo(e.getConnection().getAddress())==false)){
                
                ((Client)e.getConnection()).setType("Client");
                connections.add(e.getConnection());
                t1= new Thread((Client)e.getConnection());
                t1.setName("Client : "+e.getConnection().getAddress());
                threadManager.add(t1);
                t1.start();
                
            }else if((isConnectedTo(e.getConnection().getAddress())==true)){
                JOptionPane.showMessageDialog(null,"Connection already established as a client to the selected server!");
            }
            else{
                JOptionPane.showMessageDialog(null,"Unable to create connection!");
            }
        }
        catch(Exception ex){
                JOptionPane.showMessageDialog(null,ex.getMessage());
        }
        
        }

        public void connectionRemoved(ConnectionEvent e){
            try{
                connections.remove(e.getConnection());
                for(int i=0;i<threadManager.size();i++){
                    if(threadManager.get(i).getName().contains(e.getConnection().getType()+" : "+e.getConnection().getAddress())){
                        if(!threadManager.get(i).isInterrupted()){
                            threadManager.get(i).join();
                            threadManager.get(i).interrupt();
                            JOptionPane.showMessageDialog(null,"Connection Closed!");

                        }
                        break;
                    }
                }
            }catch(Exception ex){
                JOptionPane.showMessageDialog(null,"Error disconnecting!");
            }
        }

        public void connectionContentModified(ConnectionEvent e){
        }

        @Override
        public void workbookModified(EditEvent event) {
            //if(event.getWorkbook()=)
        }

        @Override
        public void workbookCreated(SpreadsheetAppEvent event) {
        }

        @Override
        public void workbookLoaded(SpreadsheetAppEvent event) {
        }

        @Override
        public void workbookUnloaded(SpreadsheetAppEvent event) {

            for(int i=0;i<connections.size();i++){

                if(event.getWorkbook() == connections.get(i).getWorkbook()){
                    for(int j=0;j<event.getWorkbook().getSpreadsheetCount();j++){
                        if(event.getWorkbook().getSpreadsheet(j) == connections.get(i).getSpreasheet()){
                            fireConnectionEvent(connections.get(i),ConnectionEvent.Type.REMOVED);
                            break;
                        }
                    }
                }
            }

        }

        @Override
        public void workbookSaved(SpreadsheetAppEvent event) {
            //
        }

        @Override
        public void selectionChanged(SelectionEvent event) {
            //
        }

        private void fireConnectionEvent(Connection connect,ConnectionEvent.Type type) {
                    ConnectionEvent event = new ConnectionEvent(this, connect, type);

                    if (SwingUtilities.isEventDispatchThread())
                            for (ConnectionListener listener : connectionListeners)
                                    switch (event.getType()) {
                                            case CREATED:
                                                    listener.connectionAdded(event); break;
                                            case MODIFIED:
                                                    listener.connectionContentModified(event); break;
                                            case REMOVED:
                                                    listener.connectionRemoved(event); break;
                                    }
                    else
                            SwingUtilities.invokeLater(
                                    new EventDispatcher(event, 
                                            connectionListeners.toArray(new ConnectionListener[connectionListeners.size()])
                                    )
                            );
        }


        public void connect(InetAddress ip, Cell cell, Spreadsheet spreadsheet){
            fireConnectionEvent(new Client(ip,cell, spreadsheet,this),ConnectionEvent.Type.CREATED);
        }

        public void createConnect(List <Cell> cellConnected, int rowNumber, int colNumber, Spreadsheet spreadsheet){
            
            fireConnectionEvent(new Host(cellConnected, rowNumber, colNumber, spreadsheet,this),ConnectionEvent.Type.CREATED);
        }

        public void addConnectionListener(ConnectionListener listener) {
                    connectionListeners.add(listener);
        }

	/**
	 * Removes the given listener from the spreadsheet application.
	 * @param listener the listener to be removed
	 */
	public void removeConnectionListener(ConnectionListener listener) {
		connectionListeners.remove(listener);
	}

        public List <Connection> getConnections() {
            return connections;
        }

        public void disconnect(String selectedItem) {
            Connection connect = null;
            String typeAndAddress="";
            
            for(int i=0;i<threadManager.size();i++){
                if(threadManager.get(i).getName().contains(selectedItem)){
                    typeAndAddress = threadManager.get(i).getName();
                    break;
                }
            }
            
            for(int i=0;i<connections.size();i++){
                if(typeAndAddress.contains(connections.get(i).getAddress().toString())){
                    connect = connections.get(i);
                    connect.closeSockets();
                    connect.removeListeners();
                }
            }
            if(connect != null){
                
                fireConnectionEvent(connect,ConnectionEvent.Type.REMOVED);
            }
        }

    
        /**
	 * A utility for dispatching events on the AWT event dispatching thread.
	 * @author Einar Pehrson
	 */
	public static class EventDispatcher implements Runnable {

		/** The event to fire */
		private ConnectionEvent event;

		/** The listeners to which the event should be dispatched */
		private ConnectionListener[] listeners;

		/**
		 * Creates a new event dispatcher.
		 * @param event the event to fire
		 * @param listeners the listeners to which the event should be dispatched
		 */
		public EventDispatcher(ConnectionEvent event, ConnectionListener[] listeners) {
			this.event = event;
			this.listeners = listeners;
		}

		/**
		 * Dispatches the event.
		 */
		public void run() {
			for (ConnectionListener listener : listeners){
				switch(event.getType()){
                                    case CREATED:
                                        listener.connectionAdded(event); break;
                                    case MODIFIED:
                                        listener.connectionContentModified(event);break;
                                    case REMOVED:
                                        listener.connectionRemoved(event);break;
                                }
                        }
		}
	}
        
        
        
     public static class Creator implements Runnable{

        /** The component that was created */
		private ConnectionController connectController;

	/** The CleanSheets application */
		private CleanSheets app;
           
        public Creator(CleanSheets app) {
            this.app = app;
        }

        public ConnectionController createAndWait() {
            try {
                    EventQueue.invokeAndWait(this);
            } catch (InterruptedException e) {
                    return null;
            } catch (java.lang.reflect.InvocationTargetException e) {
                    e.printStackTrace();
                    return null;
            }
            return connectController;
        }
        
                /**
		 * Asks the event queue to create a component at a later time.
		 * (Included for conformity.)
		 */
		public void createLater() {
			EventQueue.invokeLater(this);
		}

		public void run() {
			connectController = new ConnectionController(app);
		}
	}
}

