/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package csheets.bd;

/**
 *
 * @author joaodias
 */
public interface IBaseDados {
    
    
  public boolean connectarbd(String nome_bd,String tipobd, String end,String porta, String user, String pass);
  public boolean criarTabela(String nome_tab, String nome_bd,String [][] matriz,String tipobd, String end,String porta, String user, String pass ) ;
  public boolean verificarErro(String[][] matriz) ;
  public String criarColunas(String [][] matriz); 


}
