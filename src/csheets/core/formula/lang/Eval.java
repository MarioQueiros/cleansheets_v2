package csheets.core.formula.lang;

import csheets.core.Cell;
import csheets.core.CellImpl;
import csheets.core.IllegalValueTypeException;
import csheets.core.Value;
import csheets.core.formula.Expression;
import csheets.core.formula.Function;
import csheets.core.formula.FunctionParameter;

/**
 * @author Bruno Cunha
 */
public class Eval implements Function {

    public static final FunctionParameter[] parameters = new FunctionParameter[]{
       /* new FunctionParameter(Value.Type.NUMERIC, "Termo", false,
        "O numero da celula que se quer referenciar"), */new FunctionParameter(Value.Type.TEXT, "Referencia a celula", true,
        "a celula a que deve ser feita referencia")
    };

    public Eval() {
    }

    public String getIdentifier() {
        return "Eval";
    }

    public Value applyTo(Expression[] arguments) throws IllegalValueTypeException {
        double nCel = 0;
        String celula = "";
        for (Expression expression : arguments) {
            Value value = expression.evaluate();
            if (value.getType() == Value.Type.NUMERIC) {
                nCel = value.toDouble();
            } else if (value.getType() == Value.Type.MATRIX) {
                for (Value[] vector : value.toMatrix()) {
                    for (Value item : vector) {
                        if (item.getType() == Value.Type.NUMERIC) {
                            nCel += item.toDouble();
                        } else {
                            throw new IllegalValueTypeException(item, Value.Type.NUMERIC);
                        }
                    }
                }
            } else if (value.getType() == Value.Type.TEXT) {
                celula += value;     //celula="A"
            } else {
                throw new IllegalValueTypeException(value, Value.Type.UNDEFINED);
            }
        }
        String retorno = (String) celula + (int) nCel;
        return new Value(retorno);
    }

    public FunctionParameter[] getParameters() {
        return parameters;
    }

    public boolean isVarArg() {
        return true;
    }
}