/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package csheets.sp;

import csheets.SpreadsheetAppEvent;
import csheets.core.Cell;
import csheets.core.Spreadsheet;
import csheets.ui.ctrl.EditEvent;
import csheets.ui.ctrl.SelectionEvent;
import csheets.ui.ctrl.UIController;
import java.net.InetAddress;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Tiago
 */
public class ConnectionControllerTest {
    
    public ConnectionControllerTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of addUIListeners method, of class ConnectionController.
     */
    @Test
    public void testAddUIListeners() {
        System.out.println("addUIListeners");
        UIController uiController = null;
        ConnectionController instance = null;
        instance.addUIListeners(uiController);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of isConnectedTo method, of class ConnectionController.
     */
    @Test
    public void testIsConnectedTo() {
        System.out.println("isConnectedTo");
        InetAddress ip = null;
        ConnectionController instance = null;
        boolean expResult = false;
        boolean result = instance.isConnectedTo(ip);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of connectionAdded method, of class ConnectionController.
     */
    @Test
    public void testConnectionAdded() {
        System.out.println("connectionAdded");
        ConnectionEvent e = null;
        ConnectionController instance = null;
        instance.connectionAdded(e);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of connectionRemoved method, of class ConnectionController.
     */
    @Test
    public void testConnectionRemoved() {
        System.out.println("connectionRemoved");
        ConnectionEvent e = null;
        ConnectionController instance = null;
        instance.connectionRemoved(e);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of connectionContentModified method, of class ConnectionController.
     */
    @Test
    public void testConnectionContentModified() {
        System.out.println("connectionContentModified");
        ConnectionEvent e = null;
        ConnectionController instance = null;
        instance.connectionContentModified(e);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of workbookModified method, of class ConnectionController.
     */
    @Test
    public void testWorkbookModified() {
        System.out.println("workbookModified");
        EditEvent event = null;
        ConnectionController instance = null;
        instance.workbookModified(event);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of workbookCreated method, of class ConnectionController.
     */
    @Test
    public void testWorkbookCreated() {
        System.out.println("workbookCreated");
        SpreadsheetAppEvent event = null;
        ConnectionController instance = null;
        instance.workbookCreated(event);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of workbookLoaded method, of class ConnectionController.
     */
    @Test
    public void testWorkbookLoaded() {
        System.out.println("workbookLoaded");
        SpreadsheetAppEvent event = null;
        ConnectionController instance = null;
        instance.workbookLoaded(event);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of workbookUnloaded method, of class ConnectionController.
     */
    @Test
    public void testWorkbookUnloaded() {
        System.out.println("workbookUnloaded");
        SpreadsheetAppEvent event = null;
        ConnectionController instance = null;
        instance.workbookUnloaded(event);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of workbookSaved method, of class ConnectionController.
     */
    @Test
    public void testWorkbookSaved() {
        System.out.println("workbookSaved");
        SpreadsheetAppEvent event = null;
        ConnectionController instance = null;
        instance.workbookSaved(event);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of selectionChanged method, of class ConnectionController.
     */
    @Test
    public void testSelectionChanged() {
        System.out.println("selectionChanged");
        SelectionEvent event = null;
        ConnectionController instance = null;
        instance.selectionChanged(event);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of connect method, of class ConnectionController.
     */
    @Test
    public void testConnect() {
        System.out.println("connect");
        InetAddress ip = null;
        Cell cell = null;
        Spreadsheet spreadsheet = null;
        ConnectionController instance = null;
        instance.connect(ip, cell, spreadsheet);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of createConnect method, of class ConnectionController.
     */
    @Test
    public void testCreateConnect() {
        System.out.println("createConnect");
        List<Cell> cellConnected = null;
        int rowNumber = 0;
        int colNumber = 0;
        Spreadsheet spreadsheet = null;
        ConnectionController instance = null;
        instance.createConnect(cellConnected, rowNumber, colNumber, spreadsheet);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of addConnectionListener method, of class ConnectionController.
     */
    @Test
    public void testAddConnectionListener() {
        System.out.println("addConnectionListener");
        ConnectionListener listener = null;
        ConnectionController instance = null;
        instance.addConnectionListener(listener);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of removeConnectionListener method, of class ConnectionController.
     */
    @Test
    public void testRemoveConnectionListener() {
        System.out.println("removeConnectionListener");
        ConnectionListener listener = null;
        ConnectionController instance = null;
        instance.removeConnectionListener(listener);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getConnections method, of class ConnectionController.
     */
    @Test
    public void testGetConnections() {
        System.out.println("getConnections");
        ConnectionController instance = null;
        List expResult = null;
        List result = instance.getConnections();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of disconnect method, of class ConnectionController.
     */
    @Test
    public void testDisconnect() {
        System.out.println("disconnect");
        String selectedItem = "";
        ConnectionController instance = null;
        instance.disconnect(selectedItem);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
}
