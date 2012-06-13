package csheets.core.formula.lang;

import csheets.core.Cell;
import csheets.core.IllegalValueTypeException;
import csheets.core.Value;
import csheets.core.formula.Expression;
import csheets.core.formula.Function;
import csheets.core.formula.FunctionParameter;
import csheets.core.formula.compiler.ExcelExpressionCompilerPT;
import csheets.core.formula.compiler.FormulaCompilationException;
import csheets.core.formula.compiler.FormulaCompiler;

/**
 * @author Bruno Cunha
 */
public class Eval implements Function {

    public static final FunctionParameter[] parameters = new FunctionParameter[]{
        new FunctionParameter(Value.Type.TEXT, "Instruction", false,
        "Instruction to be evaluated")
    };

    public Eval() {
    }

    public String getIdentifier() {
        return "Eval";
    }
    
    public Value applyTo(Expression[] arguments) throws IllegalValueTypeException {
   
        Cell ref = ExcelExpressionCompilerPT.getLastActiveCell(this);
        //a = UIController.getActiveCell();
        try {
            String str = arguments[0].evaluate().toString();
            Expression e = FormulaCompiler.getInstance().compile(ref, ExcelExpressionCompilerPT.FORMULA_STARTER + str);
            return e.evaluate();
        } catch (FormulaCompilationException ex) {
            return new Value(ex);
        }
    }

    public FunctionParameter[] getParameters() {
        return parameters;
    }

    public boolean isVarArg() {
        return true;
    }
}