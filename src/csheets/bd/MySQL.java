/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package csheets.bd;

import java.sql.*;

//MySQL precisa de melhorias e provavelmente o nome irá ser modificado
public class MySQL {

    Connection conn;
    private String nome_tabela;

    public MySQL() throws SQLException {


        DriverManager.registerDriver(new com.mysql.jdbc.Driver());



    }
    //Verificar se existe alguma Coluna vazia
    public boolean verificarErro(String [][] matriz){
        for(int i=0;i<matriz[0].length;i++){
            if(matriz[0][i].equals(""))
                return false;
        }
        
        return true;
    }
            

    public boolean connectarbd(String nome_bd) {
        try {

            String url = "jdbc:mysql://localhost:3306/" + nome_bd;
            conn = DriverManager.getConnection(
                    url, "root", "");
            conn.setAutoCommit(false);
            return true;
        } catch (Exception e) {
           e.printStackTrace();
            return false;
        }
    }
    //Cria as colunas
    public String criarColunas(String [][] matriz){
        String coluna="";
          for(int i=0;i<matriz[0].length;i++){
              if(i!=0 && i<matriz[0].length)
                  coluna=coluna+", ";
            coluna=coluna+matriz[0][i]+" varchar(120)";
            
        }
          return coluna;
        
    }
    
    public void preencherInformação(String [][] matriz) throws SQLException{
        
        Statement st = conn.createStatement();
            for(int i=0;i<matriz.length;i++){
            String infor="insert into "+nome_tabela+" values ( ";
            for(int j=0;j<matriz[0].length;j++){
                    if(j!=0 && j<matriz[0].length)
                        infor=infor+", ";
              infor=infor+"'"+matriz[i][j]+"' ";
            }
            infor=infor+" );";
            st.executeUpdate(infor);
        }
        
        
          
    }

    public boolean criarTabela(String nome_tab, String nome_bd,String [][] matriz) {

        try {
            nome_tabela=nome_tab;
            String url = "jdbc:mysql://localhost:3306/" + nome_bd;
            conn = DriverManager.getConnection(
                    url, "root", "");
            conn.setAutoCommit(false);
            Statement st = conn.createStatement();
            String colunas=criarColunas(matriz);
            String total="create table "+nome_tabela+" ("+colunas+");";
            st.executeUpdate(total);
            preencherInformação(matriz);
            st.close();
            conn.commit();
            conn.close();
            return true;
        } catch (Exception e) {
            //e.printStackTrace();
            return false;
        }
    }
}