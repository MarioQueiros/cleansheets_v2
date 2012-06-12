package csheets.core.formula.util;

import csheets.core.Value;

/**
 *
 * @author Bruno Cunha
 */
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
}