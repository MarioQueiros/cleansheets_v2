/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package csheets.bd;

import java.sql.*;

//MySQL precisa de melhorias e provavelmente o nome irá ser modificado
public class BaseDados {

    Connection conn;
    private String nome_tabela;
    private boolean mysql_o;
    private boolean postgres_o;
    private boolean sqlserver_o;
    private String token_pass;
    public BaseDados(String tipo) throws SQLException, ClassNotFoundException {
            mysql_o=false;
            postgres_o=false;
            sqlserver_o=false;
       }
    //Verificar se existe alguma Coluna vazia
    public boolean verificarErro(String [][] matriz){
        for(int i=0;i<matriz[0].length;i++){
            if(matriz[0][i].equals(""))
                return false;
        }
        
        return true;
    }
    
    public void reset(){
        mysql_o=false;
        sqlserver_o=false;
        postgres_o=false;
    }
    
    public void verificarSGBD(String sgbd){
        if(sgbd.equalsIgnoreCase("mysql"))
            mysql_o=true;
         if(sgbd.equalsIgnoreCase("sqlserver"))
            sqlserver_o=true;
          if(sgbd.equalsIgnoreCase("postgres"))
            postgres_o=true;
    }
            

    public boolean connectarbd(String nome_bd,String tipobd, String end,String porta, String user, String pass) {
        try {
            reset();
            verificarPass(pass);
            verificarSGBD(tipobd);
            String url="";
           if(mysql_o){
                DriverManager.registerDriver(new com.mysql.jdbc.Driver());
                url = "jdbc:mysql://"+end+":"+porta+"/"+ nome_bd;
           }
           if(postgres_o){
               DriverManager.registerDriver(new org.postgresql.Driver());
               url = "jdbc:postgresql://"+end+":"+porta+"/"+ nome_bd;
           }
           
           if(sqlserver_o){
               DriverManager.registerDriver(new org.postgresql.Driver());
                url = "jdbc:sqlserver://"+end+":"+porta+";databaseName="+ nome_bd;
           }
            conn = DriverManager.getConnection(
                    url, user, token_pass);
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
    
    public void verificarPass(String pass){
        if(pass.equalsIgnoreCase("!#n0p@ss#!"))
            token_pass="";
        else
            token_pass=pass;
    }

    public boolean criarTabela(String nome_tab, String nome_bd,String [][] matriz,String tipobd, String end,String porta, String user, String pass ) {

        try {
            nome_tabela=nome_tab;
            reset();
            verificarPass(pass);
            verificarSGBD(tipobd);
            String url="";
             if(mysql_o){
                DriverManager.registerDriver(new com.mysql.jdbc.Driver());
                url = "jdbc:mysql://"+end+":"+porta+"/"+ nome_bd;
           }
            if(postgres_o){
               DriverManager.registerDriver(new org.postgresql.Driver());
               url = "jdbc:postgresql://"+end+":"+porta+"/"+ nome_bd;
           }
            if(sqlserver_o){
               DriverManager.registerDriver(new com.microsoft.sqlserver.jdbc.SQLServerDriver());
               url = "jdbc:sqlserver://"+end+":"+porta+";databaseName="+ nome_bd;
           }
            conn = DriverManager.getConnection(
                    url, user, token_pass);
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
            e.printStackTrace();
            return false;
        }
    }
}