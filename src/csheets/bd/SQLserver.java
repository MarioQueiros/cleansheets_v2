/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package csheets.bd;

import csheets.ui.ctrl.UIController;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author joaodias
 */
public class SQLserver implements IBaseDados {

    Connection conn;
    private String nome_tabela;
    private String token_pass;
    private String[] pk = new String[52];
    private String[] vector_col;
    String pk_final[];

    public SQLserver() {
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
        if (v.length == 0) {
            return false;
        }

        //verificar se existem elementos repetidos  
        for (int k = 0; k < v.length - 1; ++k) {
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

            DriverManager.registerDriver(new com.microsoft.sqlserver.jdbc.SQLServerDriver());
            url = "jdbc:sqlserver://" + end + ":" + porta + ";databaseName=" + nome_bd;

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

            DriverManager.registerDriver(new com.microsoft.sqlserver.jdbc.SQLServerDriver());
            url = "jdbc:sqlserver://" + end + ":" + porta + ";databaseName=" + nome_bd;

            conn = (Connection) DriverManager.getConnection(
                    url, user, token_pass);
            conn.setAutoCommit(false);
            Statement st = (Statement) conn.createStatement();
            String colunas = criarColunas(matriz);
            String total = "create table " + nome_tabela + " (" + colunas + ", id_t_c INT IDENTITY);";
            st.executeUpdate(total);
            preencherInformacao(matriz);
            st.close();
            conn.commit();
            conn.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean carregarInformacao(String nome_tab, UIController ui, String nome_bd, String tipobd, String end, String porta, String user, String pass) {
        try {
            nome_tabela = nome_tab;

            verificarPass(pass);

            String url = "";

            DriverManager.registerDriver(new com.microsoft.sqlserver.jdbc.SQLServerDriver());
            url = "jdbc:sqlserver://" + end + ":" + porta + ";databaseName=" + nome_bd;

            conn = (Connection) DriverManager.getConnection(
                    url, user, token_pass);
            conn.setAutoCommit(false);
            Statement st = (Statement) conn.createStatement();
            String query = "select * from " + nome_tab + " order by id_t_c";
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
                for (int i = 1; i <= nrcolunas - 1; i++) {
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

    @Override
    public int getNrColunas(String nome_tab, String nome_bd, String tipobd, String end, String porta, String user, String pass) {
        try {
            nome_tabela = nome_tab;

            verificarPass(pass);

            String url = "";

            DriverManager.registerDriver(new com.microsoft.sqlserver.jdbc.SQLServerDriver());
            url = "jdbc:sqlserver://" + end + ":" + porta + ";databaseName=" + nome_bd;


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
            conn.commit();
            conn.close();

            return nrcolunas - 1;
        } catch (Exception e) {
            return -1;
        }
    }

    public String[] getNomeColunas(String nome_tab, String nome_bd, String tipobd, String end, String porta, String user, String pass) throws SQLException {
        String[] nome_colunas;
        // Buscar nome das colunas
        String sql_col = "select top 1 * from " + nome_tabela + " order by id_t_c ;";
        String url = "";
        int j = 0;
        j = getNrColunas(nome_tab, nome_bd, tipobd, end, porta, user, pass);
        nome_colunas = new String[j];
        DriverManager.registerDriver(new com.microsoft.sqlserver.jdbc.SQLServerDriver());
        url = "jdbc:sqlserver://" + end + ":" + porta + ";databaseName=" + nome_bd;


        conn = (Connection) DriverManager.getConnection(
                url, user, token_pass);
        conn.setAutoCommit(false);
        Statement st = (Statement) conn.createStatement();
        ResultSet rs = st.executeQuery(sql_col);
       

        ResultSet rs_1 = st.executeQuery(sql_col);

        int k = 1;
        while (rs_1.next()) {

            for (int cic = 0; cic < j; ++cic) {
                nome_colunas[cic] = rs_1.getString(cic + 1);
            }
        }
        conn.commit();
        conn.close();
        return nome_colunas;
    }

    public String[] getPKString(String nome_tab, String nome_bd, String tipobd, String end, String porta, String user, String pass) throws SQLException {
        nome_tabela = nome_tab;
        String pks[];

        String query = "SELECT KU.table_name as tablename,column_name as primarykeycolumn FROM INFORMATION_SCHEMA.TABLE_CONSTRAINTS AS TC INNER JOIN INFORMATION_SCHEMA.KEY_COLUMN_USAGE AS KU ON TC.CONSTRAINT_TYPE = 'PRIMARY KEY' AND TC.CONSTRAINT_NAME = KU.CONSTRAINT_NAME and ku.table_name='" + nome_tabela + "' ORDER BY KU.TABLE_NAME, KU.ORDINAL_POSITION;";
        String url = "";

        DriverManager.registerDriver(new com.microsoft.sqlserver.jdbc.SQLServerDriver());
        url = "jdbc:sqlserver://" + end + ":" + porta + ";databaseName=" + nome_bd;


        conn = (Connection) DriverManager.getConnection(
                url, user, token_pass);
        conn.setAutoCommit(false);
        Statement st = (Statement) conn.createStatement();
        ResultSet rs = st.executeQuery(query);
        int nr_pk = 0;
        while (rs.next()) {
            nr_pk++;
        }
        pks = new String[nr_pk];
        ResultSet rs2 = st.executeQuery(query);
        int j = 0;
        while (rs2.next()) {
            pks[j] = rs2.getString(2);
            j++;
        }
        st.close();
        conn.commit();
        conn.close();

        return pks;

    }

    public int getPosCol(String col, String nome_tab, String nome_bd, String tipobd, String end, String porta, String user, String pass) throws SQLException {
        String[] vec = getNomeColunas(nome_tab, nome_bd, tipobd, end, porta, user, pass);
        for (int i = 0; i < vec.length; i++) {
            if (col.equalsIgnoreCase(vec[i])) {
                return i;
            }
        }
        return -1;

    }

    public boolean addNewInf(String nome_tab, String nome_bd, String[] v, String tipobd, String end, String porta, String user, String pass) {
        nome_tabela = nome_tab;
        String nome_colunas[];

        String infor = "insert into " + nome_tabela + " values ( ";
        for (int i = 0; i < v.length; i++) {
            if (i < v.length - 1) {
                infor += "'" + v[i] + "' " + ",";
            }
            if (i == v.length - 1) {
                infor += "'" + v[i] + "' " + " );";
            }

        }

        try {
            String url = "";

            DriverManager.registerDriver(new com.microsoft.sqlserver.jdbc.SQLServerDriver());
            url = "jdbc:sqlserver://" + end + ":" + porta + ";databaseName=" + nome_bd;;


            conn = (Connection) DriverManager.getConnection(
                    url, user, token_pass);
            conn.setAutoCommit(false);
            Statement st = (Statement) conn.createStatement();

            st.executeUpdate(infor);
            st.close();
            conn.commit();
            conn.close();
            return true;
        } catch (Exception e) {

            try {
                conn.commit();
                conn.close();
                vector_col = getNomeColunas(nome_tab, nome_bd, tipobd, end, porta, user, pass);

            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            try {
                pk_final = getPKString(nome_tab, nome_bd, tipobd, end, porta, user, pass);
            } catch (SQLException ex) {
                Logger.getLogger(SQLserver.class.getName()).log(Level.SEVERE, null, ex);
            }

            //Código UPDATE
            String update = "update " + nome_tabela + " SET  ";
            String up = "";
            for (int i = 0; i < v.length; i++) {
                if (i < v.length - 1) {
                    up += vector_col[i] + "=" + "'" + v[i] + "' " + ",";
                }
                if (i == v.length - 1) {
                    up += vector_col[i] + "=" + "'" + v[i] + "' ";
                }


            }
            update = update + up;
            update = update + " WHERE ";
            for (int i = 0; i < pk_final.length; i++) {
                if (i < pk_final.length - 1) {
                    try {
                        update += pk_final[i] + "=" + "'" + v[getPosCol(pk_final[i], nome_tab, nome_bd, tipobd, end, porta, user, pass)] + "' " + "AND ";
                    } catch (SQLException ex) {
                        Logger.getLogger(SQLserver.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                if (i == pk_final.length - 1) {
                    try {
                        update += pk_final[i] + "=" + "'" + v[getPosCol(pk_final[i], nome_tab, nome_bd, tipobd, end, porta, user, pass)] + "' ";
                    } catch (SQLException ex) {
                        Logger.getLogger(SQLserver.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }


            }

            Statement ft;
            try {
                
                System.out.println(update);

                String url = "";

                DriverManager.registerDriver(new com.microsoft.sqlserver.jdbc.SQLServerDriver());
                url = "jdbc:sqlserver://" + end + ":" + porta + ";databaseName=" + nome_bd;


                conn = (Connection) DriverManager.getConnection(
                        url, user, token_pass);
                conn.setAutoCommit(false);
                Statement st = (Statement) conn.createStatement();

                st.executeUpdate(update);
                st.close();
                conn.commit();
                conn.close();
            } catch (SQLException ex) {
                Logger.getLogger(SQLserver.class.getName()).log(Level.SEVERE, null, ex);
            }



            //e.printStackTrace();
            return false;
        }

    }
}
