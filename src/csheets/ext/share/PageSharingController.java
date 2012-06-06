/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package csheets.ext.share;

import csheets.ext.share.connect.Connection;
import csheets.ext.share.connect.Client;
import csheets.ext.share.connect.Host;
import csheets.SpreadsheetAppEvent;
import csheets.SpreadsheetAppListener;
import csheets.ext.share.ui.ClientEvent;
import csheets.ext.share.ui.ClientListener;
import csheets.ext.share.ui.DisconnectEvent;
import csheets.ext.share.ui.DisconnectListener;
import csheets.ext.share.ui.HostEvent;
import csheets.ext.share.ui.HostListener;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author Tiago
 */
public class PageSharingController implements SpreadsheetAppListener, HostListener, DisconnectListener, ClientListener{

    private static final PageSharingController instance = new PageSharingController();
    
    private List<Connection> connections = new ArrayList<Connection>();
    private List<PageSharingListener> connectionListeners = new ArrayList<PageSharingListener>();

    private PageSharingController() {
    }
    
    public static PageSharingController getInstance() {
		return instance;
    }

    public boolean isConnectedTo(InetAddress ip, String type) {
        for (int i = 0; i < connections.size(); i++) {
            if (connections.get(i).getAddress().equals(ip) && connections.get(i).getType().equals(type)) {
                return true;
            }
        }
        return false;
    }

    public void connectionHostAdded(PageSharingData connectData) {

        System.out.println(Thread.activeCount());
        try {
            Host h = new Host(connectData);

            if (!isConnectedTo(h.getAddress(), h.getType())) {
                h.setType("Host");
                connections.add(h);
                fireConnectionsChanged(connections);
            } else {
                // A ALTERAR
                JOptionPane.showMessageDialog(null, "Already established as a server!");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
        JOptionPane.showMessageDialog(null, "TEST MESSAGE");
        System.out.println(Thread.activeCount());

        System.out.println(connections.get(0).toString());

    }

    public void connectionClientAdded(PageSharingData connectData) {


        try {
            Client c = new Client(connectData);

            if (!isConnectedTo(c.getAddress(), c.getType())) {
                c.setType("Client");
                connections.add(c);
                fireConnectionsChanged(connections);

            } else {
                JOptionPane.showMessageDialog(null, "Unable to create connection, already connected to that server!");
                c.removeListeners();
                c.closeSockets();
                c.join();
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }

    }

    public void connectionRemoved(Connection e) {
        try {
            for (int i = 0; i < connections.size(); i++) {
                if (e.equals(connections.get(i))) {
                    //if (!connections.get(i).isInterrupted()) {
                    connections.get(i).closeSockets();
                    connections.get(i).removeListeners();

                    System.out.println(connections.get(0).toString());
                    System.out.println(Thread.activeCount());
                    connections.get(i).interrupt();

                    System.out.println(connections.get(0).toString());
                    System.out.println(Thread.activeCount());
                    connections.remove(e);
                    fireConnectionsChanged(connections);
                    //}

                }
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error disconnecting!");
        }
        JOptionPane.showMessageDialog(null, "Connection Closed!");
    }

    public void connectionContentModified(PageSharingEvent e) {
    }

    @Override
    public void workbookCreated(SpreadsheetAppEvent event) {
    }

    @Override
    public void workbookLoaded(SpreadsheetAppEvent event) {
    }

    @Override
    public void workbookUnloaded(SpreadsheetAppEvent event) {

        for (int i = 0; i < connections.size(); i++) {

            if (event.getWorkbook() == connections.get(i).getWorkbook()) {
                for (int j = 0; j < event.getWorkbook().getSpreadsheetCount(); j++) {
                    if (event.getWorkbook().getSpreadsheet(j) == connections.get(i).getSpreadsheet()) {
                        connectionRemoved(connections.get(i));
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


    public void addConnectionListener(PageSharingListener listener) {
        connectionListeners.add(listener);
    }

    /**
     * Removes the given listener from the spreadsheet application.
     * @param listener the listener to be removed
     */
    public void removeConnectionListener(PageSharingListener listener) {
        connectionListeners.remove(listener);
    }

    /**
     * Notifies all registered listeners that the connections
     * list was modified.
     * @param workbook the workbook that was modified
     */
    private void fireConnectionsChanged(List<Connection> connectionList) {
        PageSharingEvent connectEvent = new PageSharingEvent(this,connectionList);
        
        for (PageSharingListener listener : connectionListeners.toArray(
				new PageSharingListener[connectionListeners.size()]))
			listener.connectionsChanged(connectEvent);
	
        
    }

    public List<Connection> getConnections() {
        return connections;
    }

    @Override
    public void createConnect(HostEvent event) {
        PageSharingData connectData = new PageSharingData(event.getCellConnected(), event.getRowNumber(), event.getColNumber(), event.getSpreadsheet(), this, event.getUiController());

        connectionHostAdded(connectData);
    }

    @Override
    public void disconnect(DisconnectEvent event) {
        Connection connect = null;
        String selectedItem = event.getConnection();
        
        for (int i = 0; i < connections.size(); i++) {
            if (selectedItem.contains(connections.get(i).getType() + " : " + connections.get(i).getAddress().toString())) {
                connect = connections.get(i);
            }
        }

        if (connect != null) {
            connectionRemoved(connect);
        }
    }

    @Override
    public void connect(ClientEvent event) {
        PageSharingData connectData = new PageSharingData(event.getIp(),event.getFirstCell(),event.getSpreadsheet(), this, event.getUiController());
        connectionClientAdded(connectData);
    }

}
