package csheets.core.formula.compiler;

import antlr.ANTLRException;
import antlr.collections.AST;
import csheets.core.Cell;
import csheets.core.CellImpl;
import csheets.core.Value;
import csheets.core.formula.*;
import csheets.core.formula.lang.*;
import java.io.StringReader;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class MacrosExpressionCompiler {

    public String compile(String source, ArrayList<String> nomeVar, ArrayList<Integer> numeroVar) throws FormulaCompilationException {
        int flag = 0;
        String conteudoInstrucao;

        //Creates the lexer and parser
        MacrosParser parser = new MacrosParser(
                new MacrosLexer(new StringReader(source)));

        try {
            //Attempts to match an expression
            parser.expression();
        } catch (ANTLRException e) {
            JOptionPane.showMessageDialog(null, "Existe alguma instrucao que nao esta correcta. Por favor tente novamente.");
            flag = 1;
            //throw new FormulaCompilationException(e);
        }

        if (flag != 1) {
            //Converts the expression and returns it
            conteudoInstrucao = convert(parser.getAST(), nomeVar, numeroVar, source);
        } else {
            conteudoInstrucao = "erro";
        }
        return conteudoInstrucao;
    }

    public String convert(AST node, ArrayList<String> nomeVar, ArrayList<Integer> numeroVar, String source) {

        //Caso a gramatica esteja correcta, comear a verificar se tem variaveis ou nao
        AST proximoNo;

        String topoArvore = node.getText();
        String auxiliar;
        int valorVar;


        //Verificar se e uma variavel local
        if (topoArvore.substring(0, 1).equals("$")) {
            //Enquanto houver ramos na arvore, ou seja enquanto houver valores na nossa intrucao
            proximoNo = node.getNextSibling();
            while ((proximoNo = proximoNo.getNextSibling()) != null) {
                //Significa que podera ser do genero de: $var:=sum(1;2) ou $var:=sum($res;2)
                if (proximoNo.getNextSibling() == null && proximoNo.getNumberOfChildren() != 0) {
                    source = verificarVariavelFilhos(proximoNo, nomeVar, numeroVar, source);
                }
                //Se entrar neste if significa que a expressao  assim $var:=123
                if (proximoNo.getNextSibling() == null && proximoNo.getNumberOfChildren() == 0) {
                    auxiliar = proximoNo.getText();
                    valorVar = Integer.parseInt(auxiliar);
                    nomeVar.add(topoArvore);
                    numeroVar.add(valorVar);
                }
            }
        } else {
            proximoNo = node.getNextSibling();
            while ((proximoNo = proximoNo.getNextSibling()) != null) {
                auxiliar=proximoNo.getText();
                //Significa que podera ser do genero de: A1:=sum(1;2) ou A1:=sum($res;2)
                if (proximoNo.getNextSibling() == null && proximoNo.getNumberOfChildren() != 0) {
                    source = verificarVariavelFilhos(proximoNo, nomeVar, numeroVar, source);
                    //Caso seja A1:=$res
                }else if(proximoNo.getNextSibling() == null && proximoNo.getNumberOfChildren() == 0 && auxiliar.substring(0, 1).equals("$")==true){
                    source = verificarVariavel(proximoNo, nomeVar, numeroVar, source);                
                }
            }
        }

        return source;
    }
    public String verificarVariavel(AST proximoNo, ArrayList<String> nomeVar, ArrayList<Integer> numeroVar, String source) {
        
            //Ira verificar na nossa arrayList se existe esta variavel
            int flag = 0;
            for (int i = 0; i < nomeVar.size(); i++) {
                String a = proximoNo.getText();
                //Caso encontre a variavel na arrayList
                if (nomeVar.get(i).equals(a)) {
                    //Valor da variavel
                    int auxiliarVar = numeroVar.get(i);
                    String auxiliar = Integer.toString(auxiliarVar);

                    String[] instrucao = source.split(":=");

                    source = instrucao[0] + ":=";

                    //Substituir a variavel pelo seu valor
                    instrucao[1] = instrucao[1].replace(nomeVar.get(i), auxiliar);
                    source = source + instrucao[1];
                    flag = 1;
                }
            }

            if (nomeVar.size() == 0 || flag == 0) {
                String[] instrucao = source.split(":=");

                source = instrucao[0] + ":=";

                //Substituir a variavel pelo seu valor
                instrucao[1] = instrucao[1].replace(proximoNo.getText(), "0");
                source = source + instrucao[1];
            }


        
        return source;
    }

    public String verificarVariavelFilhos(AST proximoNo, ArrayList<String> nomeVar, ArrayList<Integer> numeroVar, String source) {
        int auxiliarVar;
        String nomeFilho;
        AST filho;
        String auxiliar;

        filho = proximoNo.getFirstChild();
        nomeFilho = filho.getText();
        //Verificar se encontra uma variavel dentro da formula
        if (nomeFilho.substring(0, 1).equals("$")) {

            //Ira verificar na nossa arrayList se existe esta variavel
            int flag = 0;
            for (int i = 0; i < nomeVar.size(); i++) {
                //Caso encontre a variavel na arrayList
                if (nomeVar.get(i).equals(nomeFilho)) {
                    //Valor da variavel
                    auxiliarVar = numeroVar.get(i);
                    auxiliar = Integer.toString(auxiliarVar);

                    String[] instrucao = source.split(":=");

                    source = instrucao[0] + ":=";

                    //Substituir a variavel pelo seu valor
                    instrucao[1] = instrucao[1].replace(nomeVar.get(i), auxiliar);
                    source = source + instrucao[1];
                    flag = 1;
                }
            }

            if (nomeVar.size() == 0 || flag == 0) {
                String[] instrucao = source.split(":=");

                source = instrucao[0] + ":=";

                //Substituir a variavel pelo seu valor
                instrucao[1] = instrucao[1].replace(filho.getText(), "0");
                source = source + instrucao[1];
            }


        }

        //Percorrer os outros filhos
        while ((filho = filho.getNextSibling()) != null) {
            nomeFilho = filho.getText();
            if (nomeFilho.substring(0, 1).equals("$")) {
                //Ira verificar na nossa arrayList se existe esta variavel
                int flag = 0;
                for (int i = 0; i < nomeVar.size(); i++) {
                    //Caso encontre a variavel na arrayList
                    if (nomeVar.get(i).equals(nomeFilho)) {

                        //Valor da variavel
                        auxiliarVar = numeroVar.get(i);
                        auxiliar = Integer.toString(auxiliarVar);

                        String[] instrucao = source.split(":=");

                        source = instrucao[0] + ":=";

                        //Substituir a variavel pelo seu valor
                        instrucao[1] = instrucao[1].replace(nomeVar.get(i), auxiliar);
                        source = source + instrucao[1];
                        flag = 1;
                    }
                }

                if (nomeVar.size() == 0 || flag == 0) {
                    String[] instrucao = source.split(":=");

                    source = instrucao[0] + ":=";

                    //Substituir a variavel pelo seu valor
                    instrucao[1] = instrucao[1].replace(filho.getText(), "0");
                    source = source + instrucao[1];
                }
            }
        }
        return source;
    }
}
