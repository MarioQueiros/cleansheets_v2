/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package csheets.bd;

import java.awt.event.ActionEvent;
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
public class BaseDadosActionTest {
    
    public BaseDadosActionTest() {
         
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
     * A1 é uma coordenada correcta
     */
    @Test
    public void testValidaString_A1() {
        System.out.println("validaString");
        String a = "A1";
        BaseDadosAction instance = new BaseDadosAction(null);
        boolean expResult = true;
        boolean result = instance.validaString(a);
        assertEquals(expResult, result);
        
    }
    
     /**
     * A127 é uma coordenada correcta
     */
    @Test
    public void testValidaString_A127() {
        System.out.println("validaString");
        String a = "A127";
        BaseDadosAction instance = new BaseDadosAction(null);
        boolean expResult = true;
        boolean result = instance.validaString(a);
        assertEquals(expResult, result);
        
    }

    /**
     * AB é uma coordenada errada
     */
    @Test
    public void testValidaString_AB() {
        System.out.println("validaString");
        String a = "AB";
        BaseDadosAction instance = new BaseDadosAction(null);
        boolean expResult = false;
        boolean result = instance.validaString(a);
        assertEquals(expResult, result);
        
    }
    
     /**
     * A1-A2 é uma coordenada errada
     */
    @Test
    public void testValidaString_A1_A2() {
        System.out.println("validaString");
        String a = "A1-A2";
        BaseDadosAction instance = new BaseDadosAction(null);
        boolean expResult =false;
        boolean result = instance.validaString(a);
        assertEquals(expResult, result);
        
    }
}
