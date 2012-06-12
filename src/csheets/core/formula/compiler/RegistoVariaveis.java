package csheets.core.formula.compiler;

import csheets.core.formula.util.Variavel;
import java.util.ArrayList;

/**
 *
 * @author Bruno Cunha
 */
class RegistoVariaveis {

    ArrayList<Variavel> lista = null;
    private static final RegistoVariaveis instance = new RegistoVariaveis();

    private RegistoVariaveis() {
        lista = new ArrayList<>();
    }

    public static RegistoVariaveis getInstance() {
        return instance;
    }

    void add(String ref) {
        Variavel v = new Variavel(ref.replace("$", ""));
        lista.add(v);
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