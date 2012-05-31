/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package csheets.sp;

import csheets.CleanSheets;
import csheets.SpreadsheetAppEvent;
import csheets.SpreadsheetAppListener;
import csheets.core.Spreadsheet;
import csheets.core.Workbook;
import csheets.ui.ctrl.EditEvent;
import csheets.ui.ctrl.EditListener;
import csheets.ui.ctrl.SelectionEvent;
import csheets.ui.ctrl.SelectionListener;
import csheets.ui.ctrl.UIController;
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

    /*public static class Creator implements Runnable{

        /** The component that was created */
		//private ConnectionController connectController;

	/** The CleanSheets application */
		//private CleanSheets app;
                
        /** The UI Controller */      
         /*       private UIController uiController;
                
        public Creator(CleanSheets app, UIController uiController) {
            this.app = app;
            this.uiController = uiController;
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
        }*/
        
        /**
		 * Asks the event queue to create a component at a later time.
		 * (Included for conformity.)
		 */
		/*public void createLater() {
			EventQueue.invokeLater(this);
		}

		public void run() {
			connectController = new ConnectionController(app,uiController);
		}
	}*/
    

    List<Thread> threadManager = new ArrayList<Thread> () ;
    
    private boolean serverOn;
    
    CleanSheets app;
    
    UIController uiController;
    
    List<Connection> connections = new ArrayList <Connection>();
    
    /** The connection listeners registered to receive events */
    private List<ConnectionListener> connectionListeners = new ArrayList<ConnectionListener>();
    
    public ConnectionController(CleanSheets app, UIController uiController){
        app.addSpreadsheetAppListener(this);
        uiController.addEditListener(this);
        uiController.addEditListener(this);
        addConnectionListener(this);
        serverOn = false;
    }
    
    public void connectionAdded(ConnectionEvent e){
        Thread t1;
        
        try{
            if(e.getConnection().getClass().getName().contains("Host")&&(isServerOn()!=true)){
                
                serverOn = true;
                connections.add(e.getConnection());
                t1= new Thread(((Host)e.getConnection()));
                //t1.setName("Host: "+e.getConnection().getAddress());
                threadManager.add(t1);
                t1.start();
                
            }else if(e.getConnection().getClass().getName().contains("Client")){
                
                connections.add(e.getConnection());
                t1= new Thread((Client)e.getConnection());
                //t1.setName("Client: "+e.getConnection().getAddress());
                threadManager.add(t1);
                t1.start();
                
            }else
                JOptionPane.showMessageDialog(null,"Unable to create connection!");
            }catch(Exception ex){
            JOptionPane.showMessageDialog(null,ex.getMessage());
            }
        
        }

        public void connectionRemoved(ConnectionEvent e){
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
                        if(event.getWorkbook().getSpreadsheet(j) == connections.get(i).getSpreasheet())
                            fireConnectionEvent(connections.get(i),ConnectionEvent.Type.REMOVED);
                    }
                }
            }

        }

        @Override
        public void workbookSaved(SpreadsheetAppEvent event) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public void selectionChanged(SelectionEvent event) {
            throw new UnsupportedOperationException("Not supported yet.");
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

        private boolean isServerOn() {
            return serverOn;
        }

        public void connect(InetAddress ip, String cell, Workbook workbook, Spreadsheet spreadsheet){
            fireConnectionEvent(new Client(ip,cell),ConnectionEvent.Type.CREATED);
        }

        public void createConnect(String firstCell, String lastCell,Workbook workbook, Spreadsheet spreadsheet){
            fireConnectionEvent(new Host(firstCell,lastCell),ConnectionEvent.Type.CREATED);
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
}

