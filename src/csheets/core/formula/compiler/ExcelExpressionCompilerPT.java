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

import antlr.ANTLRException;
import antlr.collections.AST;
import csheets.core.Cell;
import csheets.core.RegistoVariaveis;
import csheets.core.Value;
import csheets.core.formula.*;
import csheets.core.formula.lang.*;
import csheets.core.formula.util.Variavel;
import java.io.StringReader;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

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

        
     private static  RegistoVariaveis vars = RegistoVariaveis.getInstance();
        
    private static Cell lastActiveCell = null;

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
        lastActiveCell = cell;
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
        AST nodeP, nodeFunc, nodeRef;
        CellReference cr;
        Cell alvo;
        Expression e = null;
        try {
            nodeP = node.getNextSibling();
            if (nodeP.getText().equalsIgnoreCase(":=")) {  //Formato alinea a)
                nodeFunc = nodeP.getNextSibling();
                cr = new CellReference(cell.getSpreadsheet(), node.getText());
                alvo = cr.getCell();
                e = convert(alvo, nodeFunc);
                alvo.setContent(e.evaluate().toString());
                return e;
            } else if (node.getText().equalsIgnoreCase("{")) {   //Formato alinea b)
                nodeP = node; //P = proximo     
                String ref , func;
                do {
                    nodeRef = nodeP.getNextSibling();//Exemplo:#{a1:=soma(4;5);a2:=se(a1>10;"grande";"pequeno")}  nodeRef="a1"
                    nodeFunc = nodeRef.getNextSibling().getNextSibling(); //nodeFunc=Sum(a1:a11)
                    nodeP = nodeFunc.getNextSibling();//nodeP = ";"    
                    ref = nodeRef.getText();

                    if (ref.charAt(0)==Variavel.VARIAVEL_STARTER) {    //variavel - $temp1
                        e = convert(cell, nodeFunc);
                        Value v = e.evaluate();       //cria variavel
                        vars.add(ref,nodeFunc.getText(),v);
                    } else {
                        cr = new CellReference(cell.getSpreadsheet(), ref);
                        alvo = cr.getCell();
                        func = nodeFunc.getText();
                        if(func.charAt(0)==Variavel.VARIAVEL_STARTER){
                            //atribuir valor da variavel
                            alvo.setContent(vars.getValue(func).toString());   //func=$temp5
                        }else{
                        e = convert(alvo, nodeFunc);
                        alvo.setContent(e.evaluate().toString());
                        }
                    }
                } while (nodeP.getText().equalsIgnoreCase(";"));
                // vars.clear();  //desnecessario com o mecanismo na cell
                return e;
            }
        } catch (Exception ex) {
            // System.out.println("Nao esta no formato pedido na alinea a) da it 2");
        }

        if (node.getNumberOfChildren() == 0) {
            try {
                if(node.getText().charAt(0)==(Variavel.VARIAVEL_STARTER)){
                    return new Literal (vars.getValue(node.getText()/*,cell*/));
                } else switch (node.getType()) {
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
            BinaryOperator operator = Language.getInstance().getBinaryOperator_PT(node.getText());
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

    /**
     * @return a ultima cell que foi chamada para compilar. nao deve ser chamado
     * directamente, para uso exclusivo da formula "Eval" Tentativas de chamadas
     * por outras funcs retornam "null"
     */
    public static Cell getLastActiveCell(Function function) {
        if (function.getIdentifier().equalsIgnoreCase("Eval")) {
            return lastActiveCell;
        }
        return null;
    }
}