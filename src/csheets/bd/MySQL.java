/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package csheets.bd;

import java.sql.*;

//MySQL 
public class MySQL {

    Connection conn;

    public MySQL() throws SQLException {


        DriverManager.registerDriver(new com.mysql.jdbc.Driver());



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

    public boolean criarTabela(String nome_tabela, String nome_bd) {

        try {
            String url = "jdbc:mysql://localhost:3306/" + nome_bd;
            conn = DriverManager.getConnection(
                    url, "root", "");
            conn.setAutoCommit(false);
            Statement st = conn.createStatement();
            st.executeUpdate("create table "+nome_tabela+"(SUP_ID integer NOT NULL, SUP_NAME varchar(40) NOT NULL, ZIP char(5), PRIMARY KEY (SUP_ID));");
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