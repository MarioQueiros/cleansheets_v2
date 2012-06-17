/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package csheets.core.formula.compiler;

import antlr.ANTLRException;
import java.io.StringReader;
import static org.junit.Assert.fail;
import org.junit.Test;

/**
 *
 * @author Bruno Cunha
 */
public class FormulaParserTest {
    

    @Test
    public void testExpression() throws Exception {
        System.out.println("teste formula Parser");
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
