/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package csheets.ext.share;

import csheets.ext.share.connect.Server;
import csheets.ext.share.connect.Connection;
import csheets.ext.share.connect.Client;
import csheets.SpreadsheetAppEvent;
import csheets.SpreadsheetAppListener;
import csheets.core.Cell;
import csheets.core.Spreadsheet;
import csheets.core.Workbook;
import csheets.ext.share.connect.UDPTransmitter;
import csheets.ext.share.ui.ClientEvent;
import csheets.ext.share.ui.ClientListener;
import csheets.ext.share.ui.DisconnectEvent;
import csheets.ext.share.ui.DisconnectListener;
import csheets.ext.share.ui.HostEvent;
import csheets.ext.share.ui.HostListener;
import csheets.ext.share.ui.InterruptEvent;
import csheets.ext.share.ui.InterruptListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

/**
 * Classe que controla as conecções entre instâncias de cleansheets, e que trata
 * de interligar com a interface gráfica.
 *
 * @author Tiago
 */
public class PageSharingController implements SpreadsheetAppListener, HostListener, DisconnectListener, ClientListener, InterruptListener {

    /**
     * O Singleton, que guarda a instância da classe
     */
    private static final PageSharingController instance = new PageSharingController();
    /**
     * A lista de conecções na instância
     */
    private List<Connection> connections = new ArrayList<Connection>();
    /**
     * A lista de classes que escutam alterações na lista de conecções
     */
    private List<PageSharingListener> connectionListeners = new ArrayList<PageSharingListener>();
    /**
     * A classe servidor, que server para receber novos clientes
     */
    private Server appServer;
    /**
     * As booleans de teste para apanhar excepções, visto que num computador, só
     * uma instância pode ser servidor
     */
    private boolean serverOn = false;
    private boolean clientOnly = false;
    /**
     * Lista com a informação das conecções de sharing
     */
    private List<PageSharingData> connectionDataOfShares = new ArrayList<PageSharingData>();
    private UDPTransmitter udpTransmitter;

    private PageSharingController() {
        udpTransmitter = new UDPTransmitter();
    }

    public static PageSharingController getInstance() {
        return instance;
    }

    /**
     * MÉTODOS CONDICIONAIS
     */
    /**
     * Este método retorna verdade caso uma conecção já exista na instância
     * actual.
     *
     * @param type "Client" ou "Host"
     * @param firstCell A primeira célula da área conectada
     * @param lastCell A última da mesma
     * @param spreadsheet O painel de células conectado
     * @param workbook O livro em que este acima se encontra
     * @return Verdade ou Falso
     */
    public boolean isConnectedTo(String type,
            Cell firstCell, Cell lastCell,
            Spreadsheet spreadsheet, Workbook workbook) {
        boolean doubleClientCheck = false;
        if (!connections.isEmpty()) {
            if (type.equals("Client")) {

                for (int i = 0; i < connections.size() - 1; i++) {
                    if (connections.get(i).getType().equals("Client")) {
                        if (((Client) connections.get(connections.size() - 1)).getSocket().getInetAddress().equals(((Client) connections.get(i)).getSocket().getInetAddress())) {
                            doubleClientCheck = true;
                        }
                    }

                }
            } else {
                doubleClientCheck = true;
            }


            if (doubleClientCheck) {

                int row, column;
                int fRow = firstCell.getAddress().getRow();
                int fCol = firstCell.getAddress().getColumn();

                int lRow = lastCell.getAddress().getRow();
                int lCol = lastCell.getAddress().getColumn();

                /**
                 * Neste ciclo é testado se existe match na lista de conecções,
                 * e se a área conectada já esta a ser partilhada, impedindo de
                 * criar sobre uma área já partilhada uma nova partilha
                 */
                for (int i = 0; i < connections.size(); i++) {

                    if (connections.get(i).getType().equals(type)) {
                        if (connections.get(i).getSpreadsheet().getWorkbook().equals(workbook)) {
                            if (connections.get(i).getSpreadsheet().equals(spreadsheet)) {

                                row = connections.get(i).getConnectedCells().get(0).getAddress().getRow();
                                column = connections.get(i).getConnectedCells().get(0).getAddress().getColumn();

                                if (row == fRow && fCol == column) {
                                    row = connections.get(i).getConnectedCells().get(connections.get(i).getConnectedCells().size() - 1).getAddress().getRow();
                                    column = connections.get(i).getConnectedCells().get(connections.get(i).getConnectedCells().size() - 1).getAddress().getColumn();
                                    if (row == lRow && lCol == column) {
                                        return true;
                                    }
                                }
                            }
                        }
                    }


                }
            }
        }
        return false;
    }

    /**
     * Verifica se um nome de conecção se encontra disponível
     *
     * @param shareName
     * @return
     */
    public boolean isShareNameAvailable(String shareName) {
        for (int i = 0; i < connectionDataOfShares.size(); i++) {
            if (connectionDataOfShares.get(i).getShareName().equals(shareName)) {
                return true;
            }
        }
        return false;
    }

    /**
     * GESTÃO DE CONECÇÕES
     */
    /**
     * Adiciona uma nova conecção de "host", isto é conecção como servidor
     *
     * @param connectData Os dados conectados
     */
    public void connectionHostAdded(PageSharingData connectData) {

        try {

            /**
             * Aqui inicia o servidor na primeira instância aberta. Nas outras
             * existe uma excepção e não permite
             */
            if (!isServerOn()) {
                try {
                    Server aux = new Server();
                    appServer = aux;
                    getAppServer().start();
                    serverOn = true;
                    clientOnly = false;
                    fireServerInstanceOn(true);
                } catch (Exception e) {
                    e.printStackTrace();
                    clientOnly = true;
                    fireServerInstanceOn(false);
                }
            }

            /**
             * Se não for instância servidor, não pode criar uma partilha
             */
            if (!isClientOnly()) {


                if (!isConnectedTo(connectData.getType(), connectData.getCellConnected().get(0),
                        connectData.getCellConnected().get(connectData.getCellConnected().size() - 1),
                        connectData.getSpreadsheet(), connectData.getSpreadsheet().getWorkbook())) {
                    getConnectionDataOfShares().add(connectData);
                    fireConnectionsChanged(connections, connectionDataOfShares);
                } else {

                    JOptionPane.showMessageDialog(null, "Already established a share in that area!");

                }

            } else {
                JOptionPane.showMessageDialog(null, "Unable to create a share, server is already created\n in another instance. Client only !");
            }
        } catch (NullPointerException e) {
            JOptionPane.showMessageDialog(null, "Creation Canceled !");
        } catch (NumberFormatException exc) {

            JOptionPane.showMessageDialog(null, "Creation Canceled !");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }


    }

    /**
     * Cria uma nova conecção cliente
     *
     * @param connectData Os dados da conecção
     */
    public void connectionClientAdded(PageSharingData connectData) {


        try {
            Client c = new Client(connectData);
            c.setType("Client");
            c.start();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }

    }

    /**
     * Remove uma conecção existente
     *
     * @param e A conecção a remover
     */
    public void connectionRemoved(Connection e, boolean isServerDisconnection) {
        String type = null;
        Cell firstCell = null, lastCell = null;
        Spreadsheet spreadsheet = null;
        Workbook workbook = null;
        String connectionName = null;
        try {
            for (int i = 0; i < connections.size(); i++) {
                if (connections.get(i) == e) {
                    type = e.getType();

                    firstCell = e.getConnectedCells().get(0);
                    lastCell = e.getConnectedCells().get(e.getConnectedCells().size() - 1);
                    spreadsheet = e.getSpreadsheet();
                    workbook = e.getWorkbook();
                    connectionName = e.getShareName();
                    if (type.equals("Host")) {
                        for (int j = 0; j < connectionDataOfShares.size(); j++) {
                            if (connectionName.equals(connectionDataOfShares.get(j).getShareName())) {
                                connectionDataOfShares.get(j).removeConnection();
                            }
                        }
                    }

                    connections.get(i).removeListeners();
                    connections.get(i).interrupt();
                    connections.remove(i);

                    break;
                }
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error disconnecting!");
        }

      
        fireConnectionsChanged(connections, connectionDataOfShares);
    }

    /**
     * LISTENER DE EVENTOS
     */
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
                        connections.get(i).closeSockets();
                        break;
                    }
                }
            }
        }

    }

    @Override
    public void workbookSaved(SpreadsheetAppEvent event) {
    }

    @Override
    public void interruptOne(InterruptEvent event) {
        for (int i = 0; i < connectionDataOfShares.size(); i++) {

            if (connectionDataOfShares.get(i).getShareName().equals(event.getShareName())) {

                connectionDataOfShares.get(i).setInterrupted(event.isInterrupted());

            }

        }

        fireConnectionsInterrupted(event.getShareName(), event.isInterrupted());
        fireConnectionsChanged(connections, connectionDataOfShares);
    }

    @Override
    public void interruptAll(InterruptEvent event) {
        for (int i = 0; i < connectionDataOfShares.size(); i++) {


            if (connectionDataOfShares.get(i).isInterrupted() != event.isInterrupted()) {

                connectionDataOfShares.get(i).setInterrupted(event.isInterrupted());
                fireConnectionsInterrupted(connectionDataOfShares.get(i).getShareName(), event.isInterrupted());

            }


        }
        fireConnectionsChanged(connections, connectionDataOfShares);
    }

    /**
     * EVENTO DE CONECÇÕES
     */
    /**
     * Adiciona uma nova classe que escuta o evento de conecção
     *
     * @param listener A classe em questão
     */
    public void addConnectionListener(PageSharingListener listener) {
        connectionListeners.add(listener);
    }

    /**
     * Remove uma classe que escuta o evento de conecção
     *
     * @param listener A classe em questão
     */
    public void removeConnectionListener(PageSharingListener listener) {
        connectionListeners.remove(listener);
    }

    /**
     * Notifica todos os escutas que a lista de conecções foi alterada
     *
     * @param connectionList a lista que foi modificada
     */
    private void fireConnectionsChanged(List<Connection> connectionList, List<PageSharingData> shareData) {
        PageSharingEvent connectEvent = new PageSharingEvent(this, connectionList, shareData);

        for (PageSharingListener listener : connectionListeners.toArray(
                new PageSharingListener[connectionListeners.size()])) {
            listener.connectionsChanged(connectEvent);
        }


    }

    /**
     * Notifica todos os hosts que a partilha está interrompida
     *
     * @param connectionList a lista que foi modificada
     */
    private void fireConnectionsInterrupted(String shareName, boolean interrupted) {

        for (PageSharingListener listener : connectionListeners.toArray(
                new PageSharingListener[connectionListeners.size()])) {
            listener.connectionsInterrupted(shareName, interrupted);
        }


    }

    /**
     * Notifica todos que estão a escuta se a aplicação é servidor e cliente ou
     * apenas cliente
     *
     * @param state Server on ou off ( true ou false )
     */
    private void fireServerInstanceOn(boolean state) {

        for (PageSharingListener listener : connectionListeners.toArray(
                new PageSharingListener[connectionListeners.size()])) {
            listener.serverInstanceOn(state);
        }


    }

    @Override
    public void createConnect(HostEvent event) {
        PageSharingData connectData = new PageSharingData(event.getShareName(), event.getPassword(), event.getCellConnected(), event.getRowNumber(), event.getColNumber(), event.getSpreadsheet(), event.getUiController(), "Host");

        connectionHostAdded(connectData);
    }

    @Override
    public void disconnect(DisconnectEvent event) {
        int selectedItem = event.getConnectionIndex();
        connections.get(selectedItem).closeSockets();

    }

    @Override
    public void connect(ClientEvent event) {
        PageSharingData connectData = new PageSharingData(event.getConnectedSocket(), event.getIp(), event.getFirstCell(), event.getSpreadsheet(), event.getUiController(), event.getConnectName(), "Client");
        connectionClientAdded(connectData);
    }

    /**
     * AVISOS DE CONECÇÕES
     */
    /**
     * Caso o cliente seja configurado com sucesso é adicionado a lista de
     * conecções
     *
     * @param c O "Client", conecção
     */
    public void clientConfigured(Client c) {
        connections.add(c);
        fireConnectionsChanged(connections, connectionDataOfShares);
    }

    /**
     * Avisa que uma conecção "host" anteriormente criada recebeu um "client"
     */
    public void connectionOn() {
        fireConnectionsChanged(connections, connectionDataOfShares);
    }

    /**
     * GETS E SETS
     */
    /**
     * @return the serverOn
     */
    public boolean isServerOn() {
        return serverOn;
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

    public List<Connection> getConnections() {
        return connections;
    }

    /**
     * @return the connectionDataOfShares
     */
    public List<PageSharingData> getConnectionDataOfShares() {
        return connectionDataOfShares;
    }

    /**
     * Caso o ip do servidor mude, não pode manter os shares que têm pois nada
     * vai ser connectado e dai este metodo remove todos os host e a informação
     * destes.
     *
     * @param state
     */
    public void refreshIp(boolean state) {
        boolean disconnectionOccured = false;
        for (int i = 0; i < connections.size(); i++) {
            if (connections.get(i).getType().equals("Host")) {
                connectionRemoved(connections.get(i), true);
                disconnectionOccured = true;
            }
        }

        if (disconnectionOccured) {
            JOptionPane.showMessageDialog(null, "All shares closed due to Server Disconnection of the Network connected");
        }
        serverOn = state;
        fireServerInstanceOn(state);
    }

    public void refresh() {
        fireConnectionsChanged(connections,connectionDataOfShares);
    }
}
