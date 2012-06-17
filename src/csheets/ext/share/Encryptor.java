/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package csheets.ext.share;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Tiago
 */
public class Encryptor {

    public static byte[] encrypt(String target) {
        byte[] result;
        try {
            String enc = null;
            char[] encAux = target.toCharArray();
            for (int i = 0; i < encAux.length; i++) {
                encAux[i] = Character.reverseBytes(encAux[i]);
            }
            enc = String.valueOf(encAux);
            result = enc.getBytes("UTF-8");
            return result;
        } catch (UnsupportedEncodingException ex) {
            ex.printStackTrace();

        }
        return target.getBytes();
    }

    public static String dencrypt(byte[] target) {
        
        String dec = new String(target);
        char[] encAux = dec.toCharArray();
        for (int i = 0; i < encAux.length; i++) {
            encAux[i] = Character.reverseBytes(encAux[i]);
        }
        dec = String.valueOf(encAux);

        return dec;
    }
}
