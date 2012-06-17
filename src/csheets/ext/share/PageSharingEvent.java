/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package csheets.ext.share;

import csheets.ext.share.connect.Connection;
import java.util.EventObject;
import java.util.List;

/**
 * Evento da mudança na lista de conecções
 * @author Tiago
 */
public class PageSharingEvent extends EventObject {

    /** The active connection */
    private List<Connection> connectionList;
    
    private List<PageSharingData> shareData;

    public PageSharingEvent(Object source, List<Connection> connectionList, List<PageSharingData> shareData) {
        super(source);
        this.connectionList = connectionList;
        this.shareData = shareData;
    }

    /**
     * @return the connectionList
     */
    public List<Connection> getConnectionList() {
        return connectionList;
    }

    /**
     * @return the shareData
     */
    public List<PageSharingData> getShareData() {
        return shareData;
    }
}
