/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package csheets.core.formula.compiler;

import csheets.core.Cell;
import csheets.core.Spreadsheet;
import csheets.core.Workbook;
import csheets.core.formula.Formula;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 *
 * @author BrunoCunha
 */
public class FormulaTester {
    
    Workbook wb = new Workbook(2);
    Spreadsheet s = wb.getSpreadsheet(0);
    ExcelExpressionCompilerPT instancePT = new ExcelExpressionCompilerPT();
    ExcelExpressionCompiler instance = new ExcelExpressionCompiler();

    @Test
    public void testSoma() throws Exception {
        System.out.println("Teste Soma");
        String source = "#soma(2;4;6)";
        Cell cell = s.getCell(1, 1);
        Formula f = FormulaCompiler.getInstance().compile(cell, source);
        assertEquals(true, f.evaluate().toDouble()==12);        
    }
    
    @Test
    public void testMedia() throws Exception {
        System.out.println("Teste Media");
        String source = "#Media(2;4;6)";
        Cell cell = s.getCell(1, 1);
        Formula f = FormulaCompiler.getInstance().compile(cell, source);
        assertEquals(true, f.evaluate().toDouble()==4);        
    }
    
    
    @Test
    public void testEval() throws Exception {
        System.out.println("Teste Eval");
        String source = "#eval(\"Media(2;4;6)\")";
        Cell cell = s.getCell(1, 1);
        Formula f = FormulaCompiler.getInstance().compile(cell, source);
        assertEquals(true, f.evaluate().toDouble()==4);        
    }
    
    @Test
    public void testSequencia() throws Exception {
        System.out.println("Teste se e Sequencia");
        String source = "#{a2:=se(2>1;20;30);a3:=a2}";
        Cell cell = s.getCell(1, 1);
        Formula f = FormulaCompiler.getInstance().compile(cell, source);
        assertEquals(true, f.evaluate().toDouble()==20);        
    }
    
    @Test
    public void testVariavel() throws Exception {
        System.out.println("Teste variaveis");
        String source = "#{$temp1:=20;a3:=soma($temp1;10)}";
        Cell cell = s.getCell(1, 1);
        Formula f = FormulaCompiler.getInstance().compile(cell, source);
        assertEquals(true, f.evaluate().toDouble()==30);        
    }
    
}
