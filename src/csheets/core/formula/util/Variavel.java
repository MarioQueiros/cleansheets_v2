package csheets.core.formula.util;

import csheets.core.Cell;
import csheets.core.Value;

/**
 *
 * @author Bruno Cunha
 */
public class Variavel {
    
    public static final char VARIAVEL_STARTER = '$';

//$temp1=soma(4;5)
    private String id;  //temp1
    private String text;    //soma(4;5)
    private Value valor;    //9   
   // private Cell orig;

    public Variavel(String p_id, String p_text, Value p_valor/*, Cell c*/) {
        id = p_id;
        text = p_text;
        valor = p_valor;
      //  orig = c;
    }

    public Variavel(String p_id) {
        id = p_id;
        text = "";
        valor = null;
        //orig = c;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Value getValor() {
        return valor;
    }

    public void setValor(Value valor) {
        this.valor = valor;
    }

    public void clone(Variavel v) {
        id = v.id;
        text = v.text;
        valor = v.valor;
       // orig = v.orig;
    }
/*
    public Cell getCellOrig() {
        return orig;
    }*/
}