/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package csheets.sp;

import csheets.core.Cell;
import csheets.ui.ctrl.EditEvent;
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
public class ClientTest {
    
    public ClientTest() {
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
     * Test of closeSockets method, of class Client.
     */
    @Test
    public void testCloseSockets() {
        System.out.println("closeSockets");
        Client instance = null;
        instance.closeSockets();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of valueChanged method, of class Client.
     */
    @Test
    public void testValueChanged() {
        System.out.println("valueChanged");
        Cell cell = null;
        Client instance = null;
        instance.valueChanged(cell);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of contentChanged method, of class Client.
     */
    @Test
    public void testContentChanged() {
        System.out.println("contentChanged");
        Cell cell = null;
        Client instance = null;
        instance.contentChanged(cell);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of dependentsChanged method, of class Client.
     */
    @Test
    public void testDependentsChanged() {
        System.out.println("dependentsChanged");
        Cell cell = null;
        Client instance = null;
        instance.dependentsChanged(cell);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of cellCleared method, of class Client.
     */
    @Test
    public void testCellCleared() {
        System.out.println("cellCleared");
        Cell cell = null;
        Client instance = null;
        instance.cellCleared(cell);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of cellCopied method, of class Client.
     */
    @Test
    public void testCellCopied() {
        System.out.println("cellCopied");
        Cell cell = null;
        Cell source = null;
        Client instance = null;
        instance.cellCopied(cell, source);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of removeListeners method, of class Client.
     */
    @Test
    public void testRemoveListeners() {
        System.out.println("removeListeners");
        Client instance = null;
        instance.removeListeners();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of workbookModified method, of class Client.
     */
    @Test
    public void testWorkbookModified() {
        System.out.println("workbookModified");
        EditEvent event = null;
        Client instance = null;
        instance.workbookModified(event);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of run method, of class Client.
     */
    @Test
    public void testRun() {
        System.out.println("run");
        Client instance = null;
        instance.run();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
}
