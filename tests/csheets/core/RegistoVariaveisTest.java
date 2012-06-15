/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package csheets.core;

import csheets.core.formula.util.Variavel;
import org.junit.*;
import static org.junit.Assert.*;

/**
 *
 * @author Administrador
 */
public class RegistoVariaveisTest {
    
    public RegistoVariaveisTest() {
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
     * Test of getInstance method, of class RegistoVariaveis.
     */
    @Test
    public void testGetInstance() {
        System.out.println("getInstance");
        RegistoVariaveis expResult = null;
        RegistoVariaveis result = RegistoVariaveis.getInstance();
        assertNotSame(expResult, result);
    }

    /**
     * Test of add method, of class RegistoVariaveis.
     */
    @Test
    public void testAdd() throws IllegalValueTypeException {
        System.out.println("add");
        RegistoVariaveis instance = RegistoVariaveis.getInstance();
        instance.add("$temp1", "soma 4 + 5", new Value(9));
        assertEquals(instance.getValue("$temp1").toDouble(), 9,0.000);
    }

    /**
     * Test of getText method, of class RegistoVariaveis.
     */
    @Test
    public void testGetText() {
        System.out.println("getText");
        RegistoVariaveis instance = RegistoVariaveis.getInstance();        
        String func = "soma 4 + 5";
        instance.add("$temp2", func, new Value(9));
        String expect = instance.getText("$temp2");
        assertEquals(func, expect );
    }

    /**
     * Test of getValue method, of class RegistoVariaveis.
     */
    @Test
    public void testGetValue() throws IllegalValueTypeException {
        System.out.println("getValue");
         RegistoVariaveis instance = RegistoVariaveis.getInstance();
        instance.add("$temp1", "media(4;8)", new Value(6));
        assertEquals(instance.getValue("$temp1").toDouble(), 6, 0.0);
    }

    /**
     * Test of clear method, of class RegistoVariaveis.
     */
    @Test
    public void testClear() {
        System.out.println("clear");
        RegistoVariaveis instance = RegistoVariaveis.getInstance();
        instance.add("$temp1", "media(4;8)", new Value(6));
        instance.clear();
        assertEquals(instance.getValue("$temp1").toString(), "VARIAVEL NAO DEFINIDA");
    }
    
    
    @Test
    public void testVariavelNaoDefinida() {
        System.out.println("varivavel inexistente");
        RegistoVariaveis instance = RegistoVariaveis.getInstance();
        instance.add("$temp1", "media(4;8)", new Value(6));
        assertEquals(instance.getText("$temp2"), "VARIAVEL NAO DEFINIDA");
    }
}
