/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package csheets.core.formula.compiler;

import antlr.collections.AST;
import csheets.core.Cell;
import csheets.core.formula.Expression;
import csheets.core.formula.lang.Language;
import org.junit.*;
import static org.junit.Assert.*;

/**
 *
 * @author Administrador
 */
public class ExcelExpressionCompilerPTTest {
    
    public ExcelExpressionCompilerPTTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
        Language.getInstance();
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
     * Test of getStarter method, of class ExcelExpressionCompilerPT.
     */
    @Test
    public void testGetStarter() {
        System.out.println("getStarter");
        ExcelExpressionCompilerPT instance = new ExcelExpressionCompilerPT();
        char expResult = '#';
        char result = instance.getStarter();
        assertEquals(expResult, result);
    }

    /**
     * Test of compile method, of class ExcelExpressionCompilerPT.
     */
    @Test
    public void testCompile() throws Exception {
        System.out.println("compile");
        Cell cell = null;
        String source = "";
        ExcelExpressionCompilerPT instance = new ExcelExpressionCompilerPT();
        Expression expResult = null;
        Expression result = instance.compile(cell, source);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of convert method, of class ExcelExpressionCompilerPT.
     */
    @Test
    public void testConvert() throws Exception {
        System.out.println("convert");
        Cell cell = null;
        AST node = null;
        ExcelExpressionCompilerPT instance = new ExcelExpressionCompilerPT();
        Expression expResult = null;
        Expression result = instance.convert(cell, node);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
}
