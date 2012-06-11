/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package csheets.ext.share;

import csheets.SpreadsheetAppEvent;
import csheets.core.Cell;
import csheets.core.Spreadsheet;
import csheets.core.Workbook;
import csheets.ext.share.connect.Client;
import csheets.ext.share.connect.Connection;
import csheets.ext.share.connect.Server;
import csheets.ext.share.ui.ClientEvent;
import csheets.ext.share.ui.DisconnectEvent;
import csheets.ext.share.ui.HostEvent;
import csheets.ext.share.ui.InterruptEvent;
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
public class PageSharingControllerTest {
    
    public PageSharingControllerTest() {
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
     * Test of isConnectedTo method, of class PageSharingController.
     */
    @Test
    public void testIsConnectedTo() {
        System.out.println("isConnectedTo");
        String type = "";
        Cell firstCell = null;
        Cell lastCell = null;
        Spreadsheet spreadsheet = null;
        Workbook workbook = null;
        PageSharingController instance = null;
        boolean expResult = false;
        boolean result = instance.isConnectedTo(type, firstCell, lastCell, spreadsheet, workbook);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of isShareNameAvailable method, of class PageSharingController.
     */
    @Test
    public void testIsShareNameAvailable() {
        System.out.println("isShareNameAvailable");
        String shareName = "";
        PageSharingController instance = null;
        boolean expResult = false;
        boolean result = instance.isShareNameAvailable(shareName);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    
    /**
     * Test of connectionRemoved method, of class PageSharingController.
     */
    @Test
    public void testConnectionRemoved() {
        System.out.println("connectionRemoved");
        Connection e = null;
        PageSharingController instance = null;
        instance.connectionRemoved(e);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

   
    /**
     * Test of interruptOne method, of class PageSharingController.
     */
    @Test
    public void testInterruptOne() {
        System.out.println("interruptOne");
        InterruptEvent event = null;
        PageSharingController instance = null;
        instance.interruptOne(event);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of interruptAll method, of class PageSharingController.
     */
    @Test
    public void testInterruptAll() {
        System.out.println("interruptAll");
        InterruptEvent event = null;
        PageSharingController instance = null;
        instance.interruptAll(event);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
}



 