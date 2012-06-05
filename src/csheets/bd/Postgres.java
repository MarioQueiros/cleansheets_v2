/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package csheets.bd;

import csheets.ui.ctrl.UIController;
import java.sql.*;

/**
 *
 * @author joaodias
 */
public class Postgres implements IBaseDados {

    Connection conn;
    private String nome_tabela;
    private String token_pass;
    private String[] pk = new String[52];

    public Postgres() {
    }

    //Verificar se existe alguma Coluna vazia
    public boolean verificarErro(String[][] matriz) {
        for (int i = 0; i < matriz[0].length; i++) {
            if (matriz[0][i].equals("")) {
                return false;
            }
        }

        return true;
    }

    public boolean validarPK(String[] v, String[][] matriz_sele) {
          
        for (int k = 0; k < v.length-1; ++k) {
            for (int l = k + 1; l < v.length; l++) {
                if (v[k].equalsIgnoreCase(v[l])) {
                    return false;
                }
            }
        }
        
        int nrcolunas = matriz_sele[0].length;
        int num = -1;
        for (int i = 0; i < v.length; i++) {
            try {
                num = Integer.parseInt(v[i]);
            } catch (Exception E) {
                return false;
            }
            if (num > nrcolunas || num <= 0) {
                return false;
            }

        }

        for (int j = 0; j < v.length; j++) {
            pk[j] = matriz_sele[0][Integer.parseInt(v[j]) - 1];
        }

        return true;
    }

    public boolean connectarbd(String nome_bd, String tipobd, String end, String porta, String user, String pass) {
        try {

            verificarPass(pass);

            String url = "";

            DriverManager.registerDriver(new org.postgresql.Driver());
            url = "jdbc:postgresql://" + end + ":" + porta + "/" + nome_bd;

            conn = (Connection) DriverManager.getConnection(
                    url, user, token_pass);
            conn.setAutoCommit(false);
            return true;
        } catch (Exception e) {
            //  e.printStackTrace();
            return false;
        }
    }

    //Cria as colunas
    public String criarColunas(String[][] matriz) {
        String coluna = "";
        for (int i = 0; i < matriz[0].length; i++) {
            if (i != 0 && i < matriz[0].length) {
                coluna = coluna + ", ";
            }
            coluna = coluna + matriz[0][i] + " varchar(120)";

        }
        //PKs
        coluna = coluna + ", ";
        for (int j = 0; j < pk.length; j++) {
            if (pk[j] != null) {
                if (j < pk.length) {
                }
                if (j == 0) {
                    coluna = coluna + "primary key (" + pk[j];
                }
                if (j > 0) {
                    coluna = coluna + " ," + pk[j];
                }
            }
        }
        coluna = coluna + ")";
        return coluna;

    }

    public void preencherInformacao(String[][] matriz) throws SQLException {

        Statement st = (Statement) conn.createStatement();
        for (int i = 0; i < matriz.length; i++) {
            String infor = "insert into " + nome_tabela + " values ( ";
            for (int j = 0; j < matriz[0].length; j++) {
                if (j != 0 && j < matriz[0].length) {
                    infor = infor + ", ";
                }
                infor = infor + "'" + matriz[i][j] + "' ";
            }
            infor = infor + " );";
            st.executeUpdate(infor);
        }



    }

    public void verificarPass(String pass) {
        if (pass.equalsIgnoreCase("!#n0p@ss#!")) {
            token_pass = "";
        } else {
            token_pass = pass;
        }
    }

    public boolean criarTabela(String nome_tab, String nome_bd, String[][] matriz, String tipobd, String end, String porta, String user, String pass) {

        try {
            nome_tabela = nome_tab;

            verificarPass(pass);

            String url = "";

            DriverManager.registerDriver(new org.postgresql.Driver());
            url = "jdbc:postgresql://" + end + ":" + porta + "/" + nome_bd;

            conn = (Connection) DriverManager.getConnection(
                    url, user, token_pass);
            conn.setAutoCommit(false);
            Statement st = (Statement) conn.createStatement();
            String colunas = criarColunas(matriz);
            String total = "create table " + nome_tabela + " (" + colunas + ");";
            st.executeUpdate(total);
            preencherInformacao(matriz);
            st.close();
            conn.commit();
            conn.close();
            return true;
        } catch (Exception e) {
            // e.printStackTrace();
            return false;
        }
    }

    public boolean carregarInformacao(String nome_tab, UIController ui, String nome_bd, String tipobd, String end, String porta, String user, String pass) {
        try {
            nome_tabela = nome_tab;

            verificarPass(pass);

            String url = "";

            DriverManager.registerDriver(new org.postgresql.Driver());
            url = "jdbc:postgresql://" + end + ":" + porta + "/" + nome_bd;

            conn = (Connection) DriverManager.getConnection(
                    url, user, token_pass);
            conn.setAutoCommit(false);
            Statement st = (Statement) conn.createStatement();
            String query = "select * from " + nome_tab;
            String query_conta_col = "SELECT count(*) FROM information_schema.columns WHERE table_name = '" + nome_tab + "'";
            ResultSet rs = st.executeQuery(query_conta_col);
            int nrcolunas = 0;
            //Ir buscar o número de colunas que a tabela tem
            while (rs.next()) {
                nrcolunas = rs.getInt(1);
            }

            rs = st.executeQuery(query);
            int row = 0;
            while (rs.next()) {
                for (int i = 1; i <= nrcolunas; i++) {
                    ui.getActiveSpreadsheet().getCell(i - 1, row).setContent(rs.getString(i));
                }
                row++;
            }
            st.close();
            conn.commit();
            conn.close();
            return true;
        } catch (Exception e) {
            // e.printStackTrace();
            return false;
        }
    }
}
