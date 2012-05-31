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
    
    public  IBaseDados getBD(String tipo){
        if(tipo.equalsIgnoreCase("mysql"))
            return (IBaseDados) new MySQL();
        else if(tipo.equalsIgnoreCase("postgres"))
            return  (IBaseDados) new Postgres();
        else if(tipo.equalsIgnoreCase("sqlserver"))
            return (IBaseDados) new SQLserver();
        
        return null;
        
    }
    
}
