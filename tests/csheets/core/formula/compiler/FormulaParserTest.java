/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package csheets.core.formula.compiler;

import antlr.ANTLRException;
import csheets.core.formula.lang.Language;
import java.io.StringReader;
import org.junit.*;
import static org.junit.Assert.*;

/**
 *
 * @author Bruno Cunha
 */
public class FormulaParserTest {
    
    public FormulaParserTest() {
    }

    @Before
    public void setUp() {
                Language.getInstance();
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void testExpression() throws Exception {
        System.out.println("expression");
        String source = "#A2:=Soma(4;5)";
        FormulaParser parser = new FormulaParser(
                new FormulaLexer_PT(new StringReader(source)));
        try {
            parser.expression();
            
        } catch (ANTLRException e) {
            fail("FormulaCompilation error");
        }
    }

   
}
