/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package csheets.ext.share.ui;

import csheets.ext.share.PageSharingController;
import csheets.ext.share.PageSharingEvent;
import org.junit.*;
import static org.junit.Assert.*;

/**
 *
 * @author Tiago
 */
public class IPLabelTest {
    
    public IPLabelTest() {
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
     * Test of serverInstanceOn method, of class IPLabel.
     */
    @Test
    public void testServerInstanceOnFalse() {
        System.out.println("serverInstanceOn");
        boolean state = false;
        IPLabel instance = new IPLabel();
        instance.serverInstanceOn(PageSharingController.getInstance().isServerOn());
       
        String expectedResult="Client only version";
        String result = instance.getText();
        assertEquals(expectedResult,result);
    }
}
