/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package csheets.bd;

import csheets.ui.ctrl.UIController;

/**
 *
 * @author joaodias
 */
public interface IBaseDados {
    
    
  public boolean connectarbd(String nome_bd,String tipobd, String end,String porta, String user, String pass);
  public boolean criarTabela(String nome_tab, String nome_bd,String [][] matriz,String tipobd, String end,String porta, String user, String pass ) ;
  public boolean verificarErro(String[][] matriz) ;
  public String criarColunas(String [][] matriz); 
  public boolean carregarInformacao(String nome_tab, UIController ui, String nome_bd,String tipobd, String end,String porta, String user, String pass);


}
