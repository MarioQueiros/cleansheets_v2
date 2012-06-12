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
public class MySQL implements IBaseDados {

    Connection conn;
    private String nome_tabela;
    private String token_pass;
    private String[] pk = new String[52];
    private String[] vector_col;
    String pk_final[];

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
            DriverManager.registerDriver(new com.mysql.jdbc.Driver());
            url = "jdbc:mysql://" + end + ":" + porta + "/" + nome_bd;
            conn = (Connection) DriverManager.getConnection(url, user, token_pass);
            conn.setAutoCommit(false);
            conn.close();
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
            infor = infor + ", NULL );";
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

            DriverManager.registerDriver(new com.mysql.jdbc.Driver());
            url = "jdbc:mysql://" + end + ":" + porta + "/" + nome_bd;


            conn = (Connection) DriverManager.getConnection(
                    url, user, token_pass);
            conn.setAutoCommit(false);
            Statement st = (Statement) conn.createStatement();
            String colunas = criarColunas(matriz);
            String total = "create table " + nome_tabela + " (" + colunas + " , id_t_c int auto_increment not null, key(id_t_c));";
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

            DriverManager.registerDriver(new com.mysql.jdbc.Driver());
            url = "jdbc:mysql://" + end + ":" + porta + "/" + nome_bd;


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

            DriverManager.registerDriver(new com.mysql.jdbc.Driver());
            url = "jdbc:mysql://" + end + ":" + porta + "/" + nome_bd;


            conn = (Connection) DriverManager.getConnection(
                    url, user, token_pass);
            conn.setAutoCommit(false);
            Statement st = (Statement) conn.createStatement();

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
        String sql_col = "SHOW COLUMNS FROM " + nome_tab + " FROM " + nome_bd;
        String url = "";

        DriverManager.registerDriver(new com.mysql.jdbc.Driver());
        url = "jdbc:mysql://" + end + ":" + porta + "/" + nome_bd;

        int j = 0;
        j = getNrColunas(nome_tab, nome_bd, tipobd, end, porta, user, pass);
        nome_colunas = new String[j];
        conn = (Connection) DriverManager.getConnection(
                url, user, token_pass);
        conn.setAutoCommit(false);
        Statement st = (Statement) conn.createStatement();

        ResultSet rs_1 = st.executeQuery(sql_col);





        int k = 1;
        while (rs_1.next()) {


            nome_colunas[k - 1] = rs_1.getString(1);
            k++;
            if (k > j) {
                break;
            }
        }
        conn.commit();
        conn.close();

        return nome_colunas;
    }

    public String[] getPKString(String nome_tab, String nome_bd, String tipobd, String end, String porta, String user, String pass) throws SQLException {
        nome_tabela = nome_tab;
        String pks[];

        String query = "SELECT k.column_name FROM information_schema.table_constraints t JOIN information_schema.key_column_usage k USING ( constraint_name, table_schema, table_name ) WHERE t.constraint_type =  'PRIMARY KEY' AND t.table_schema =  '" + nome_bd + "' AND t.table_name =  '" + nome_tab + "' LIMIT 0 , 30";
        String url = "";

        DriverManager.registerDriver(new com.mysql.jdbc.Driver());
        url = "jdbc:mysql://" + end + ":" + porta + "/" + nome_bd;


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
            pks[j] = rs2.getString(1);
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
                infor += "'" + v[i] + "' " + " , NULL);";
            }

        }

        try {
            String url = "";

            DriverManager.registerDriver(new com.mysql.jdbc.Driver());
            url = "jdbc:mysql://" + end + ":" + porta + "/" + nome_bd;


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
                Logger.getLogger(MySQL.class.getName()).log(Level.SEVERE, null, ex);
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

                String url = "";

                DriverManager.registerDriver(new com.mysql.jdbc.Driver());
                url = "jdbc:mysql://" + end + ":" + porta + "/" + nome_bd;


                conn = (Connection) DriverManager.getConnection(
                        url, user, token_pass);
                conn.setAutoCommit(false);
                Statement st = (Statement) conn.createStatement();

                st.execute(update);
                st.close();
                conn.commit();
                conn.close();
            } catch (SQLException ex) {
                Logger.getLogger(MySQL.class.getName()).log(Level.SEVERE, null, ex);
            }




            return false;
        }

    }

    @Override
    public String[][] getMatrizInf(String nome_tab, String nome_bd, String tipobd, String end, String porta, String user, String pass) {
        try {
            nome_tabela = nome_tab;

            verificarPass(pass);

            String url = "";

            DriverManager.registerDriver(new com.mysql.jdbc.Driver());
            url = "jdbc:mysql://" + end + ":" + porta + "/" + nome_bd;


           Connection aa = (Connection) DriverManager.getConnection(
                    url, user, token_pass);
            aa.setAutoCommit(false);
            Statement st = (Statement) aa.createStatement();
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
                }
                row++;
            }

            String[][] matriz_bd = new String[row][nrcolunas - 1];
            rs = st.executeQuery(query);
            row = 0;
            while (rs.next()) {
                for (int i = 1; i <= nrcolunas - 1; i++) {
                    matriz_bd[row][i - 1] = rs.getString(i);
                }
                row++;
            }
            st.close();
            aa.commit();
            aa.close();
            return matriz_bd;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void removerLinha(String linha, String nome_tab, String nome_bd, String tipobd, String end, String porta, String user, String pass) {
        try {
            nome_tabela = nome_tab;

            verificarPass(pass);

            String url = "";


            DriverManager.registerDriver(new com.mysql.jdbc.Driver());
            url = "jdbc:mysql://" + end + ":" + porta + "/" + nome_bd;


            Connection aa = (Connection) DriverManager.getConnection(
                    url, user, token_pass);
            aa.setAutoCommit(false);
            Statement st = (Statement) aa.createStatement();
            String query = "select * from " + nome_tab + " order by id_t_c ";
            String query_conta_col = "SELECT count(*) FROM information_schema.columns WHERE table_name = '" + nome_tab + "'";
            ResultSet rs = st.executeQuery(query_conta_col);
            int nrcolunas = 0;
            //Ir buscar o número de colunas que a tabela tem
            while (rs.next()) {
                nrcolunas = rs.getInt(1);
            }
            aa.commit();
            aa.close();
            st.close();


            Connection cc = (Connection) DriverManager.getConnection(
                    url, user, token_pass);
            cc.setAutoCommit(false);
            Statement st2 = (Statement) cc.createStatement();
            String line = "";
            String deletequery = "delete from " + nome_tabela + " where ";
            rs = st2.executeQuery(query);
            int row = 0;
            while (rs.next()) {
                for (int i = 1; i <= nrcolunas - 1; i++) {
                    line += rs.getString(i);
                }

                if (line.equals(linha)) {

                    String[] col = getNomeColunas(nome_tab, nome_bd, tipobd, end, porta, user, pass);

                    for (int l = 1; l <= nrcolunas - 1; l++) {
                        if (l <= nrcolunas - 2) {
                            deletequery += col[l - 1] + " = " + "'" + rs.getString(l) + "' AND ";
                        }
                        if (l == nrcolunas - 1) {
                            deletequery += col[l - 1] + " = " + "'" + rs.getString(l) + "'";
                        }

                    }
                    break;
                }
                line = "";
                row++;
            }
            st2.close();
            cc.commit();
            cc.close();
            


            Connection a1 = (Connection) DriverManager.getConnection(
                    url, user, token_pass);
            a1.setAutoCommit(false);
            Statement st3 = (Statement) a1.createStatement();

            st3.executeUpdate(deletequery);
            st3.close();

            a1.commit();
            a1.close();

        } catch (Exception e) {
            //e.printStackTrace();
        }
    }

    public boolean inserirLinha(String[] linha, String nome_tab, String nome_bd, String tipobd, String end, String porta, String user, String pass) {
        try {
            nome_tabela = nome_tab;

            verificarPass(pass);

            String url = "";


            DriverManager.registerDriver(new com.mysql.jdbc.Driver());
            url = "jdbc:mysql://" + end + ":" + porta + "/" + nome_bd;





           Connection aa = (Connection) DriverManager.getConnection(
                    url, user, token_pass);
           aa.setAutoCommit(false);
            Statement st2 = (Statement) aa.createStatement();


            String infor = "insert into " + nome_tabela + " values ( ";
            for (int i = 0; i < linha.length; i++) {


                if (i != 0 && i < linha.length) {
                    infor = infor + ", ";
                }
                infor = infor + "'" + linha[i] + "' ";
            }
            infor = infor + " , NULL );";



            Statement st3 = (Statement) aa.createStatement();

            st3.executeUpdate(infor);
            st3.close();

            aa.commit();
            aa.close();

            return true;


        } catch (Exception e) {
            // e.printStackTrace();
            return false;
        }
    }

    public String[] registoBD(String vec[], String nome_tab, String nome_bd, String tipobd, String end, String porta, String user, String pass) {
        try {
            String[] pkz = getPKString(nome_tab, nome_bd, tipobd, end, porta, user, pass);

            String registo[];
            String query = "SELECT * FROM " + nome_tab + " WHERE ";
            for (int j = 0; j < pkz.length; j++) {
                if (j < pkz.length - 1) {
                    try {
                        query += pkz[j] + "=" + "'" + vec[getPosCol(pkz[j], nome_tab, nome_bd, tipobd, end, porta, user, pass)] + "' " + "AND ";
                    } catch (SQLException ex) {
                        Logger.getLogger(Postgres.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                if (j == pkz.length - 1) {
                    try {
                        query += pkz[j] + "=" + "'" + vec[getPosCol(pkz[j], nome_tab, nome_bd, tipobd, end, porta, user, pass)] + "' ";
                    } catch (SQLException ex) {
                        Logger.getLogger(Postgres.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }



            }

            String url = "";


            DriverManager.registerDriver(new com.mysql.jdbc.Driver());
            url = "jdbc:mysql://" + end + ":" + porta + "/" + nome_bd;



            Connection aa = (Connection) DriverManager.getConnection(
                    url, user, token_pass);
            aa.setAutoCommit(false);
            Statement st = (Statement) aa.createStatement();

            ResultSet rs = st.executeQuery(query);
            int nrco = 0;
            nrco = getNrColunas(nome_tab, nome_bd, tipobd, end, porta, user, pass);
            registo = new String[nrco];
            int k = 1;
            while (rs.next()) {

                for (int cic = 0; cic < nrco; ++cic) {
                    registo[cic] = rs.getString(cic + 1);
                }
            }
          



            return registo;
        } catch (SQLException ex) {
            Logger.getLogger(Postgres.class.getName()).log(Level.SEVERE, null, ex);
           // ex.printStackTrace();
            return null;
        }


    }
}
