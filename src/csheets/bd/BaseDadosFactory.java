/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package csheets.bd;

/**
 *
 * @author joaodias
 */
public class BaseDadosFactory {
    
    public static IBaseDados getBD(String tipo){
        if(tipo.equalsIgnoreCase("mysql"))
            return  new MySQL();
        else if(tipo.equalsIgnoreCase("postgres"))
            return   new Postgres();
        else if(tipo.equalsIgnoreCase("sqlserver"))
            return  new SQLserver();
        
        return null;
        
    }
    
}
