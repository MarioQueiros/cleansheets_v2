package csheets.core.formula.compiler;

import csheets.core.Value;
import csheets.core.formula.util.Variavel;
import java.util.ArrayList;
import java.util.Iterator;

/**
 *
 * @author Bruno Cunha
 */
class RegistoVariaveis {

    ArrayList<Variavel> lista = null;

    public RegistoVariaveis() {
        lista = new ArrayList<Variavel>();
    }

    void add(String ref) {
        Variavel v = new Variavel(ref.replace("$", ""));
        lista.add(v);
    }

    String getText(String func) {       //$temp5
        String nome = func.replace("$", func);
        for (Iterator<Variavel> it = lista.iterator(); it.hasNext();) {
            Variavel variavel = it.next();
            if (variavel.getId().equalsIgnoreCase(nome)) {
                return variavel.getText();
            }
        }
        return null;
    }

    void add(String ref, String txt, Value vl) {
         String nome = ref.substring(1, ref.length());
        Variavel v = new Variavel(nome, txt, vl);
        if (hasVar(v)) 
            sub(v);
        else
            lista.add(v);
    }

    private boolean hasVar(Variavel v) {
        String nome = v.getId();
        for (Iterator<Variavel> it = lista.iterator(); it.hasNext();) {
            Variavel variavel = it.next();
            if (variavel.getId().equalsIgnoreCase(nome)) {
                return true;
            }
        }
        return false;
    }

    Value getValue(String func) {  //func = $temp5
        if(func.charAt(0)!=Variavel.VARIAVEL_STARTER)
            return new Value("falta o caracter inicial");   
        String nome = func.substring(1, func.length());
        return getVarById(nome).getValor();
       
    }

    private Variavel getVarById(String nome) {        
        for (Iterator<Variavel> it = lista.iterator(); it.hasNext();) {
            Variavel variavel = it.next();
            if (variavel.getId().equalsIgnoreCase(nome)) 
                return variavel;
        }
        return new Variavel("err", "VARIAVEL NAO EXISTE", new Value("VARIAVEL NAO EXISTE"));
    }

    private void sub(Variavel v) {
        String nome = v.getId();
        for (Iterator<Variavel> it = lista.iterator(); it.hasNext();) {
            Variavel variavel = it.next();
            if (variavel.getId().equalsIgnoreCase(nome)) {
                variavel.clone(v);
                return;
            }
        }
        
    }
}
// alterada para o formula.util
/*
public class Variavel {

    String id, text;
    Value valor;

    public Variavel(String p_id, String p_text, Value p_valor) {
        id = p_id;
        text = p_text;
        valor = p_valor;
    }

    public Variavel(String p_id) {
        id = p_id;
        text = "";
        valor = null;
    }
}*/