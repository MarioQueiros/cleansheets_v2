/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package csheets.ext.share;

import csheets.core.Address;
import csheets.ext.share.connect.Server;
import csheets.ext.share.connect.Connection;
import csheets.ext.share.connect.Client;
import csheets.ext.share.connect.Host;
import csheets.SpreadsheetAppEvent;
import csheets.SpreadsheetAppListener;
import csheets.core.Cell;
import csheets.core.Spreadsheet;
import csheets.core.Workbook;
import csheets.ext.share.ui.ClientEvent;
import csheets.ext.share.ui.ClientListener;
import csheets.ext.share.ui.DisconnectEvent;
import csheets.ext.share.ui.DisconnectListener;
import csheets.ext.share.ui.HostEvent;
import csheets.ext.share.ui.HostListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author Tiago
 */
public class PageSharingController implements SpreadsheetAppListener, HostListener, DisconnectListener, ClientListener {

    private static final PageSharingController instance = new PageSharingController();
    private List<Connection> connections = new ArrayList<Connection>();
    private List<PageSharingListener> connectionListeners = new ArrayList<PageSharingListener>();
    private Server appServer;
    private boolean serverOn = false;
    private boolean clientOnly = false;

    private PageSharingController() {
    }

    public static PageSharingController getInstance() {
        return instance;
    }

    public boolean isConnectedTo(String type,
            Cell firstCell, Cell lastCell,
            Spreadsheet spreadsheet, Workbook workbook) {
        int row, column;
        int fRow = firstCell.getAddress().getRow();
        int fCol = firstCell.getAddress().getColumn();

        int lRow = lastCell.getAddress().getRow();
        int lCol = lastCell.getAddress().getColumn();

        for (int i = 0; i < connections.size(); i++) {

            if (connections.get(i).getType().equals(type)) {
                if (connections.get(i).getWorkbook().equals(workbook)) {
                    if (connections.get(i).getSpreadsheet().equals(spreadsheet)) {
                        for (int j = 0; j < connections.get(i).getConnectedCells().size(); j++) {
                            row = connections.get(i).getConnectedCells().get(0).getAddress().getRow();
                            column = connections.get(i).getConnectedCells().get(0).getAddress().getColumn();
                            if (fRow == lRow) {
                                if (fCol == lCol) {
                                    if (row == fRow && column == fCol) {
                                        return true;
                                    }

                                } else if (row == fRow && (column >= fCol || column <= lCol)) {
                                    return true;
                                }
                            } else if (fCol == lCol) {
                                if (fRow == lRow) {
                                    if (row == fRow && column == fCol) {
                                        return true;
                                    }
                                } else if ((row >= fRow || row <= lRow) && column == fCol) {
                                    return true;
                                }
                            } else if ((row >= fRow || row <= lRow) && (column >= fCol || column <= lCol)) {

                                return true;
                            }
                        }
                    }
                }
            }


        }
        return false;
    }

    public void connectionHostAdded(PageSharingData connectData) {
        String shareName = "";
        int numberOfHosts = -1;

        try {

            if (!isServerOn()) {
                try {
                    Server aux = new Server(this);
                    appServer = aux;
                    getAppServer().start();
                    serverOn = true;
                    clientOnly = false;
                } catch (Exception e) {
                    clientOnly = true;
                }
            }

            if (!isClientOnly()) {
                while (numberOfHosts < 0) {
                    numberOfHosts = Integer.parseInt(JOptionPane.showInputDialog(null,
                            "How much connections do you "
                            + "want on\n the connected "
                            + "area that was selected?",
                            "Number of Hosting Connections", 1));

                    if (numberOfHosts < 0) {
                        JOptionPane.showMessageDialog(null, "Invalid number of connections!");
                    }
                }


                while (shareName.equals("") || shareName.contains("Client") || shareName.contains("Host") || shareName.contains("-")) {
                    shareName = JOptionPane.showInputDialog(null, "What name do you want for this new connection?\n"
                            + "( Can't contain \"-\", \"Client\" or \"Host\" in it )", "Name for Connection", 1);
                    if (shareName.equals("") || shareName.contains("Client") || shareName.contains("Host") || shareName.contains("-")) {
                        JOptionPane.showMessageDialog(null, "Incorrect Name!");
                    }
                }
                Host h = new Host(connectData, shareName);
                h.setType("Host");
                if (!isConnectedTo(h.getType(), h.getConnectedCells().get(0),
                        h.getConnectedCells().get(h.getConnectedCells().size() - 1),
                        h.getSpreadsheet(), h.getWorkbook())) {

                    connections.add(h);
                    fireConnectionsChanged(connections);
                    numberOfHosts--;

                    for (int i = 0; i < numberOfHosts; i++) {
                        Host h1 = new Host(connectData, shareName);
                        h1.setType("Host");
                        connections.add(h1);
                        fireConnectionsChanged(connections);
                    }

                } else {
                    // A ALTERAR
                    JOptionPane.showMessageDialog(null, "Already established as a host!");
                    h.removeListeners();
                    h.closeSockets();
                    h.join();

                }

            }else{
                JOptionPane.showMessageDialog(null,"Unable to create a host, server is already created\n in another instance. Client only !");
            }
        } catch (NullPointerException e) {

            JOptionPane.showMessageDialog(null, "Creation Canceled !");
            e.printStackTrace();
        } catch (NumberFormatException exc) {

            JOptionPane.showMessageDialog(null, "Creation Canceled !");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
            ex.printStackTrace();
        }


    }

    public void connectionClientAdded(PageSharingData connectData) {


        try {
            Client c = new Client(connectData);

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }

    }

    public void connectionRemoved(Connection e) {
        try {
            for (int i = 0; i < connections.size(); i++) {
                if (e.equals(connections.get(i))) {
                    //if (!connections.get(i).isInterrupted()) {

                    connections.get(i).removeListeners();
                    connections.get(i).closeSockets();

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
        PageSharingEvent connectEvent = new PageSharingEvent(this, connectionList);

        for (PageSharingListener listener : connectionListeners.toArray(
                new PageSharingListener[connectionListeners.size()])) {
            listener.connectionsChanged(connectEvent);
        }


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
        int selectedItem = event.getConnectionIndex();

        connectionRemoved(connections.get(selectedItem));

    }

    @Override
    public void connect(ClientEvent event) {
        PageSharingData connectData = new PageSharingData(event.getConnectedSocket(), event.getIp(), event.getFirstCell(), event.getSpreadsheet(), this, event.getUiController(), event.getConnectName());
        connectionClientAdded(connectData);
    }

    public void clientConfigured(Client c) {
        connections.add(c);
        fireConnectionsChanged(connections);
    }

    /**
     * @return the serverOn
     */
    public boolean isServerOn() {
        return serverOn;
    }

    public void connectionOn() {
        fireConnectionsChanged(connections);
    }

    /**
     * @return the appServer
     */
    public Server getAppServer() {
        return appServer;
    }

    /**
     * @return the clientOnly
     */
    public boolean isClientOnly() {
        return clientOnly;
    }
}
