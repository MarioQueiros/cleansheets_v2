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
package csheets.core.formula.lang;

import csheets.core.IllegalValueTypeException;
import csheets.core.Value;
import csheets.core.formula.BinaryOperator;
import csheets.core.formula.Expression;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Bruno Cunha
 */
public class Incrementar implements BinaryOperator {

    /**
     * The unique version identifier used for serialization
     */
    private static final long serialVersionUID = -1880245696016234964L;

    public Incrementar() {
    }

    public Value applyTo(Expression leftOperand, Expression rightOperand) throws IllegalValueTypeException {
        double esq = 0, dir = 0;
        boolean isLetter = false;
        try {
            esq = leftOperand.evaluate().toDouble();
        } catch (IllegalValueTypeException ex) {
            isLetter = true;
        }
        try {
            dir = rightOperand.evaluate().toDouble();
            if(isLetter){
                return new Value(leftOperand.evaluate().toString() + (int)dir);  //se só houver string à esquerda ex(soma(a+b5))
            }
        } catch (IllegalValueTypeException ex) {
            if(!isLetter){
                return new Value((int)esq + rightOperand.evaluate().toString() );  //se só houver string à direita ex(soma(b5+a))
            }
            isLetter = true;
        }
        if (!isLetter) {    //valor normal, 2 numeros
            return new Value(esq + dir);
        } else {    //2 strings
            return new Value(leftOperand.evaluate().toString() + rightOperand.evaluate().toString());
        }
    }

    public String getIdentifier() {
        return "+";
    }

    public Value.Type getOperandValueType() {
        return Value.Type.NUMERIC;
    }

    public String toString() {
        return getIdentifier();
    }
}