package csheets.core.formula.compiler;

import antlr.ANTLRException;
import antlr.collections.AST;
import csheets.core.Cell;
import csheets.core.CellImpl;
import csheets.core.Value;
import csheets.core.formula.*;
import csheets.core.formula.lang.*;
import csheets.ui.MacrosFrame;
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
            conteudoInstrucao = convert(parser.getAST(), nomeVar, numeroVar, source);
        } else {
            conteudoInstrucao = "erro";
        }
        return conteudoInstrucao;
    }

    public String convert(AST node, ArrayList<String> nomeVar, ArrayList<Integer> numeroVar, String source) {

        //Caso a gramatica esteja correcta, comecar a verificar se tem variaveis ou nao
        AST proximoNo;

        String topoArvore = node.getText();
        String auxiliar;
        int valorVar;


        //Verificar se e uma variavel local
        if (topoArvore.substring(0, 1).equals("$")) {
            //Enquanto houver ramos na arvore, ou seja enquanto houver valores na nossa intrucao
            proximoNo = node.getNextSibling();
            while ((proximoNo = proximoNo.getNextSibling()) != null) {
                //Significa que podera ser do genero de: $var:=sum(1;2) ou $var:=sum($res;2) ou $var:=sum(exec(macro);2)
                if (proximoNo.getNextSibling() == null && proximoNo.getNumberOfChildren() != 0) {
                    source = percorrerArvore(proximoNo, nomeVar, numeroVar, source);
                }
                //Se entrar neste if significa que a expressao e assim $var:=123 
                if (proximoNo.getNextSibling() == null && proximoNo.getNumberOfChildren() == 0) {
                    auxiliar = proximoNo.getText();
                    valorVar = Integer.parseInt(auxiliar);
                    nomeVar.add(topoArvore);
                    numeroVar.add(valorVar);
                } else //$var:=exec(macro)
                if (proximoNo.getText().equals("exec")) {
                    String conteudoMacro = MacrosFrame.verificarNomeMacro(proximoNo.getNextSibling().getText());
                    String aux[] = new String[3];
                    int valor = 0, flag = 0;

                    if (conteudoMacro.equals("") == false) {
                        aux = MacrosFrame.executarMacro(nomeVar, numeroVar, conteudoMacro);
                    } else {
                        JOptionPane.showMessageDialog(null, "O nome que introduziu nao corrresponde a uma Macro criada anteriormente!");
                        flag = 1;
                    }

                    if (flag == 0) {
                        conteudoMacro = aux[2];
                        valor = Integer.parseInt(conteudoMacro);

                        nomeVar.add(topoArvore);
                        numeroVar.add(valor);
                    }
                }
            }
        } else {
            proximoNo = node.getNextSibling();
            while ((proximoNo = proximoNo.getNextSibling()) != null) {
                auxiliar = proximoNo.getText();
                //Significa que podera ser do genero de: A1:=sum(1;2) ou A1:=sum($res;2) ou A1:=sum(exec(macro);2)
                if (proximoNo.getNextSibling() == null && proximoNo.getNumberOfChildren() != 0) {
                    source = percorrerArvore(proximoNo, nomeVar, numeroVar, source);
                    //Caso seja A1:=$res
                } else if (proximoNo.getNextSibling() == null && proximoNo.getNumberOfChildren() == 0 && auxiliar.substring(0, 1).equals("$") == true) {
                    source = substituirValorVariavel(proximoNo, nomeVar, numeroVar, source);
                    //A1:=exec(macro)
                } else if (proximoNo.getText().equals("exec")) {
                    String conteudoMacro = MacrosFrame.verificarNomeMacro(proximoNo.getNextSibling().getText());
                    String aux[] = new String[3];
                    int flag = 0;

                    if (conteudoMacro.equals("") == false) {
                        aux = MacrosFrame.executarMacro(nomeVar, numeroVar, conteudoMacro);
                    } else {
                        JOptionPane.showMessageDialog(null, "O nome que introduziu nao corrresponde a uma Macro criada anteriormente!");
                        flag = 1;
                    }

                    if (flag == 0) {
                        conteudoMacro = aux[2];                        
                        source=source.replace("exec(" + proximoNo.getNextSibling().getText() + ")", conteudoMacro);
                    }
                }
            }
        }

        return source;
    }

    public String substituirValorVariavel(AST proximoNo, ArrayList<String> nomeVar, ArrayList<Integer> numeroVar, String source) {

        //Ira verificar na nossa arrayList se existe esta variavel
        int flag = 0;
        for (int i = 0; i < nomeVar.size(); i++) {
            String no = proximoNo.getText();
            //Caso encontre a variavel na arrayList
            if (nomeVar.get(i).equals(no)) {
                //Valor da variavel
                int auxiliarVar = numeroVar.get(i);
                String auxiliar = Integer.toString(auxiliarVar);

                source = source.replace(nomeVar.get(i), auxiliar);
                flag = 1;
            }
        }
        if (nomeVar.isEmpty() || flag == 0) {
            source = source.replace(proximoNo.getText(), "0");
        }
        return source;
    }

    public String percorrerArvore(AST proximoNo, ArrayList<String> nomeVar, ArrayList<Integer> numeroVar, String source) {
        String nomeFilho;
        AST filho, novoFilho;

        filho = proximoNo.getFirstChild();
        nomeFilho = filho.getText();

        novoFilho = filho;


        if (nomeFilho.substring(0, 1).equals("$")) {
            source = substituirValorVariavel(filho, nomeVar, numeroVar, source);

            //$var:=sum(exec(macro);1)
        } else if (nomeFilho.equals("exec")) {
            source = substituirExecucaoMacro(novoFilho, nomeVar, numeroVar, source);
        }

        //Percorrer a arvore
        while ((novoFilho = novoFilho.getNextSibling()) != null) {
            nomeFilho = novoFilho.getText();
            //Verificar se e variavel e substituir o seu valor
            if (nomeFilho.substring(0, 1).equals("$")) {
                source = substituirValorVariavel(novoFilho, nomeVar, numeroVar, source);
                //$var:=sum(1;exec(macro))
            } else if (nomeFilho.equals("exec")) {
                source = substituirExecucaoMacro(novoFilho, nomeVar, numeroVar, source);
            }

            //Verificar se a arvore tem mais ramos, ou seja se existe mais filhos
            if (novoFilho.getNextSibling() == null && filho.getFirstChild() != null) {
                filho = filho.getFirstChild();
                novoFilho = filho;
                nomeFilho = novoFilho.getText();
                if (nomeFilho.substring(0, 1).equals("$")) {
                    source = substituirValorVariavel(novoFilho, nomeVar, numeroVar, source);
                } else if (nomeFilho.equals("exec")) {
                    source = substituirExecucaoMacro(novoFilho, nomeVar, numeroVar, source);
                }
            }
        }

        return source;
    }

    public String substituirExecucaoMacro(AST novoFilho, ArrayList<String> nomeVar, ArrayList<Integer> numeroVar, String source) {
        String conteudoMacro = MacrosFrame.verificarNomeMacro(novoFilho.getNextSibling().getText());
        String aux[] = new String[3];
        int flag = 0;

        if (conteudoMacro.equals("") == false) {
            aux = MacrosFrame.executarMacro(nomeVar, numeroVar, conteudoMacro);
        } else {
            JOptionPane.showMessageDialog(null, "O nome que introduziu nao corrresponde a uma Macro criada anteriormente!");
            flag = 1;
        }

        if (flag == 0) {
            conteudoMacro = aux[2];

            source = source.replace("exec(" + novoFilho.getNextSibling().getText() + ")", conteudoMacro);
        }

        return source;
    }
}
