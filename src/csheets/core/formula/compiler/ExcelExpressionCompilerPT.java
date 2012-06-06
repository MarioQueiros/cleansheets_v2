/*
 * Copyright (c) 2005 Einar Pehrson <einar@pehrson.nu>.
 *
 * This file is part of
 * CleanSheets - a spreadsheet application for the Java platform.
 *
 * CleanSheets is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * CleanSheets is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with CleanSheets; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */
package csheets.core.formula.compiler;

import java.io.StringReader;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import antlr.ANTLRException;
import antlr.collections.AST;
import csheets.core.Cell;
import csheets.core.IllegalValueTypeException;
import csheets.core.Value;
import csheets.core.formula.*;
import csheets.core.formula.lang.CellReference;
import csheets.core.formula.lang.Language;
import csheets.core.formula.lang.RangeReference;
import csheets.core.formula.lang.ReferenceOperation;
import csheets.core.formula.lang.UnknownElementException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * A compiler that generates Excel-style formulas from strings.
 *
 * @author Bruno Cunha
 */
public class ExcelExpressionCompilerPT implements ExpressionCompiler {

    /**
     * The character that signals that a cell's content is a formula ('#')
     */
    public static final char FORMULA_STARTER = '#';
    private String content;

    /**
     * Creates the Excel expression compiler.
     */
    public ExcelExpressionCompilerPT() {
    }

    public char getStarter() {
        return FORMULA_STARTER;
    }

    public Expression compile(Cell cell, String source) throws FormulaCompilationException {
        // Creates the lexer and parser
        content = source;
        FormulaParser parser = new FormulaParser(
                new FormulaLexer_PT(new StringReader(source)));

        try {
            // Attempts to match an expression
            parser.expression();
        } catch (ANTLRException e) {
            throw new FormulaCompilationException(e);
        }

        // Converts the expression and returns it
        return convert(cell, parser.getAST());
    }

    /**
     * Converts the given ANTLR AST to an expression.
     *
     * @param node the abstract syntax tree node to convert
     * @return the result of the conversion
     */
    protected Expression convert(Cell cell, AST node) throws FormulaCompilationException {
        //System.out.println("Converting node '" + node.getText() + "' of tree '" + node.toStringTree() + "' with " + node.getNumberOfChildren() + " children.");
        try {
            AST nodeP, nodeFunc, nodeRef;
            CellReference cr;
            Cell alvo;
            Expression e;
            nodeP = node.getNextSibling();
            if (nodeP.getText().equalsIgnoreCase(":=")) {  //Formato alinea a)
                nodeFunc = nodeP.getNextSibling();
                cr = new CellReference(cell.getSpreadsheet(), node.getText());
                alvo = cr.getCell();
                e = convert(alvo, nodeFunc);
                alvo.setContent(e.evaluate().toString());
                return e;
                //return new Literal(Value.parseValue(content, Value.Type.BOOLEAN, Value.Type.DATE));
            } else if (node.getText().equalsIgnoreCase("{")) {   //Formato alinea b)
                nodeP = node; //P = proximo                
                do {
                    nodeRef = nodeP.getNextSibling();//Exemplo:#{a1:=soma(4;5);a2:=se(a1>10;"grande";"pequeno")}  nodeRef="a1"
                    nodeFunc = nodeRef.getNextSibling().getNextSibling(); //nodeFunc=Sum(a1:a11)
                    nodeP = nodeFunc.getNextSibling();//nodeP = ";"     
                    cr = new CellReference(cell.getSpreadsheet(), nodeRef.getText());
                    alvo = cr.getCell();
                    e = convert(alvo, nodeFunc);
                    alvo.setContent(e.evaluate().toString());
                } while (nodeP.getText().equalsIgnoreCase(";"));
                return e;
                //return new Literal(Value.parseValue(content, Value.Type.BOOLEAN, Value.Type.DATE));
            }


        } catch (Exception ex) {
            // System.out.println("Nao esta no formato pedido na alinea a) da it 2");
        }

        if (node.getNumberOfChildren() == 0) {
            try {
                switch (node.getType()) {
                    case FormulaParserTokenTypes.NUMBER:
                        return new Literal(Value.parseNumericValue(node.getText()));
                    case FormulaParserTokenTypes.STRING:
                        return new Literal(Value.parseValue(node.getText(), Value.Type.BOOLEAN, Value.Type.DATE));
                    case FormulaParserTokenTypes.CELL_REF:
                        return new CellReference(cell.getSpreadsheet(), node.getText());
                    case FormulaParserTokenTypes.NAME:
                    /*
                     * return cell.getSpreadsheet().getWorkbook().
                     * getRange(node.getText()) (Reference)
                     */
                }
            } catch (ParseException ex) {
                throw new FormulaCompilationException(ex);
            }
        }

        // Convert function call
        Function function = null;
        try {
            function = Language.getInstance().getFunction_PT(node.getText());
        } catch (UnknownElementException ex) {
        }

        if (function != null) {
            List<Expression> args = new ArrayList<Expression>();
            AST child = node.getFirstChild();
            if (child != null) {
                args.add(convert(cell, child));
                while ((child = child.getNextSibling()) != null) {
                    args.add(convert(cell, child));
                }
            }
            Expression[] argArray = args.toArray(new Expression[args.size()]);
            return new FunctionCall(function, argArray);
        }

        if (node.getNumberOfChildren() == 1) // Convert unary operation
        {
            return new UnaryOperation(
                    Language.getInstance().getUnaryOperator(node.getText()),
                    convert(cell, node.getFirstChild()));
        } else if (node.getNumberOfChildren() == 2) {
            // Convert binary operation
            BinaryOperator operator = Language.getInstance().getBinaryOperator(node.getText());
            if (operator instanceof RangeReference) {
                return new ReferenceOperation(
                        (Reference) convert(cell, node.getFirstChild()),
                        (RangeReference) operator,
                        (Reference) convert(cell, node.getFirstChild().getNextSibling()));
            } else {
                return new BinaryOperation(
                        convert(cell, node.getFirstChild()),
                        operator,
                        convert(cell, node.getFirstChild().getNextSibling()));
            }
        } else // Shouldn't happen
        {
            throw new FormulaCompilationException();
        }
    }
}