/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package csheets.core.formula.compiler;

import antlr.collections.AST;
import csheets.core.Cell;
import csheets.core.Spreadsheet;
import csheets.core.Workbook;
import csheets.core.formula.Expression;
import csheets.core.formula.Formula;
import csheets.core.formula.lang.Language;
import org.junit.*;
import static org.junit.Assert.*;

/**
 *
 * @author Bruno Cunha
 */
public class ExcelExpressionCompilersTest {

    Workbook wb = new Workbook(2);
    Spreadsheet s = wb.getSpreadsheet(0);
    ExcelExpressionCompilerPT instancePT = new ExcelExpressionCompilerPT();
    ExcelExpressionCompiler instance = new ExcelExpressionCompiler();

    public ExcelExpressionCompilersTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
        Language.getInstance();
        s.setTitle("titulo");
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testGetStarterPT() {
        System.out.println("teste excelExpressionCompiler PT");
        char expResult = '#';
        char result = instancePT.getStarter();
        assertEquals(expResult, result);
    }

    @Test
    public void testGetStarter() {
        System.out.println("teste excelExpressionCompiler ENG");
        char expResult = '=';
        char result = instance.getStarter();
        assertEquals(expResult, result);
    }

    /**
     * Test of compile method, of class ExcelExpressionCompilerPT.
     */
    @Test
    public void testCompile() throws Exception {
        System.out.println("Teste PARSER e LEXER!");
        String source = "#Media(2;4;6)";
        Cell cell = s.getCell(1, 1);
        Formula f = FormulaCompiler.getInstance().compile(cell, source);
        assertEquals(true, f.toString().length()>0);        
    }

    
}
