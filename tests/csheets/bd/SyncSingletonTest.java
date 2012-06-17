/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package csheets.bd;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author joaodias
 */
public class SyncSingletonTest {
    
    public SyncSingletonTest() {
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
     * Inicialmente o valor de STOP terá que ser verdadeiro.
     */
    @Test
    public void testGetStop() {
        System.out.println("getStop");
        SyncSingleton instance = SyncSingleton.Instance();
        boolean expResult = true;
        boolean result = instance.getStop();
        assertEquals(expResult, result);
        
      
    }

  
}
