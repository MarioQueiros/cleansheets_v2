package csheets.bd;

import java.awt.event.ActionEvent;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

import csheets.ui.ctrl.BaseAction;
import csheets.ui.ctrl.UIController;

public class BaseDadosAction extends BaseAction {

    /** The user interface controller */
    protected UIController uiController;
    private int nrlinhas = 128, nrcolunas = 52;
    private String[][] options = new String[nrlinhas][nrcolunas];
    private String[][] matriz_sel;
    private String[] temp = new String[2];
    String[] coluna = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M",
        "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z", "AA", "AB", "AC", "AD", "AE", "AF", "AG",
        "AH", "AI", "AJ", "AK", "AL", "AM", "AN", "AO", "AP", "AQ", "AR", "AS", "AT", "AU", "AV", "AW", "AX", "AY", "AZ"};
    private boolean flag_geral = false;
    String infor[] = new String[5];
    String pk[] = new String[52];
    int[] linhas = new int[nrlinhas];
    int[] vec = new int[4]; //Vector com os extremos seleccionados
    String tipo_bd, chave;

    public class ThreadBD implements Runnable {

        public ThreadBD() {
        }

        @Override
        public void run() {
            reset();
            escolhaSGBD();
            escolherAreaCelulas();
            preencherVectorCoord();
            preencherMatrizFinal();
            IBaseDados bd;

            if (!flag_geral) {
                bd = BaseDadosFactory.getBD(tipo_bd);
                formulario(bd);
            }
        }
    }

    /**
     * Creates a new font action.
     * @param uiController the user interface controller
     * 
     */
    public BaseDadosAction(UIController uiController) {
        this.uiController = uiController;

        //Colunas e linhas das sheets
        for (int a = 0; a < nrlinhas; ++a) {
            linhas[a] = a + 1;
        }

        //Preencher as opções possíveis tendo em conta o formato do Csheets
        for (int i = 0; i < nrcolunas; ++i) {
            for (int j = 0; j < nrlinhas; ++j) {
                options[j][i] = coluna[i] + linhas[j];
            }
        }

    }

    public void reset() {
        //flag começa sempre a false
        flag_geral = false;


    }

    protected String getName() {
        return "Gravar";
    }

    protected void defineProperties() {
    }

    public boolean validaString(String a) {
        for (int i = 0; i < nrlinhas; ++i) {
            for (int j = 0; j < nrcolunas; ++j) {
                if (options[i][j].equalsIgnoreCase(a)) {
                    return true;
                }
            }
        }
        return false;


    }

    public void escolherAreaCelulas() {
        boolean flag = false;

        do {
            if (flag_geral) {
                break;
            }

            if (flag) {
                JOptionPane.showMessageDialog(null, "Introduziu valores errados ou no formato errado!");
            }
            flag = false;
            String receive;
            receive = JOptionPane.showInputDialog("Introduza a área das células que pretende gravar. Exemplo : A1-D5");


            if (receive == null) {
                flag_geral = true;
                break;
            }


            //Dividir a String
            if (!receive.contains("-")) {
                flag = true;
            }

            if (receive.contains("-")) {
                temp = receive.split("-");
                if (!validaString(temp[0]) || !validaString(temp[1])) {
                    flag = true;
                }

            }


            if (receive.contains("-")) {
                //Validar as posições do vector para os extremos seleccionados e preencher no vector vec
                int[] valida = new int[4];
                for (int i = 0; i < nrlinhas; ++i) {
                    for (int j = 0; j < nrcolunas; ++j) {
                        if (options[i][j].equals(temp[0])) {
                            valida[0] = i;
                            valida[1] = j;
                        }
                        if (options[i][j].equals(temp[1])) {
                            valida[2] = i;
                            valida[3] = j;
                        }
                    }
                }
                //Exemplo : Tem que ser A1-D5 e não D5-A1 || não pode ser A5-D1
                if (valida[3] < valida[1] || valida[2] < valida[0]) {
                    flag = true;
                }

            }




        } while (!validaString(temp[0]) || !validaString(temp[1]) || flag);
    }

    public void preencherVectorCoord() {
        //Procurar as posições do vector para os extremos seleccionados e preencher no vector vec

        for (int i = 0; i < nrlinhas; ++i) {
            for (int j = 0; j < nrcolunas; ++j) {
                if (options[i][j].equalsIgnoreCase(temp[0])) {
                    vec[0] = i;
                    vec[1] = j;
                }
                if (options[i][j].equalsIgnoreCase(temp[1])) {
                    vec[2] = i;
                    vec[3] = j;
                }
            }
        }

    }

    public void preencherMatrizFinal() {
        // Matriz com a informação que o utilizador seleccionou
        int nrlinhas2 = vec[2] - vec[0] + 1;
        int nrcolunas2 = vec[3] - vec[1] + 1;
        matriz_sel = new String[nrlinhas2][nrcolunas2];
        int row = 0, col = 0;

        for (int k = vec[0]; k <= vec[2]; k++) {

            for (int l = vec[1]; l <= vec[3]; l++) {

                matriz_sel[row][col] = this.uiController.getActiveSpreadsheet().getCell(l, k).getContent();
                col++;

            }
            col = 0;
            row++;
        }
    }

    public void escolhaSGBD() {
        //Qual o SGBD que pretende

        boolean tipo_bd_f = false, tipobd_message = false;
        do {

            if (flag_geral) {
                break;
            }


            if (tipobd_message) {
                JOptionPane.showMessageDialog(null, "Esse sistema SGDB não está disponível, digite novamente!");
            }

            tipo_bd = JOptionPane.showInputDialog(null, "Em que tipo de SGBD pretende gravar? (Postgres/MySQL/SQLserver)");
            if (tipo_bd == null) {
                flag_geral = true;
                break;
            }

            if (tipo_bd.equalsIgnoreCase("mysql") || tipo_bd.equalsIgnoreCase("postgres") || tipo_bd.equalsIgnoreCase("sqlserver")) {
                tipo_bd_f = true;
            }
            if (!tipo_bd_f) {
                tipobd_message = true;
            }
        } while (!tipo_bd_f);

    }

    public void escolhaPK(IBaseDados a, String[][] matriz) {
        //Qual o SGBD que pretende

        boolean pk_f = false, pk_message = false;
        do {

            if (flag_geral) {
                break;
            }


            if (pk_message) {
                JOptionPane.showMessageDialog(null, "Colunas inválidas");
            }

            chave = JOptionPane.showInputDialog(null, "Quais as colunas que pretende que sejam chave PK da sua tabela?\nSe tem NOME | NIF | MORADA e pretende que nif e morada sejam PK escreva 2;3");
            if (chave != null) {
                pk = chave.split(";");
                pk_f = a.validarPK(pk, matriz_sel);
            }
            if (chave == null) {
                flag_geral = true;
            }
            if (tipo_bd == null) {
                flag_geral = true;
                break;
            }
            if (!pk_f) {
                pk_message = true;
            }

        } while (!pk_f);

    }

    public void formulario(IBaseDados a) {



        if (a.verificarErro(matriz_sel)) {


            escolhaPK(a, matriz_sel);
            String dados_bd = "";
            boolean flagbd = false, flagbd_message = false;
            do {

                if (flag_geral) {
                    break;
                }

                //Indicar o nome da base de dados destino e o nome da tabela
                if (flagbd_message) {
                    JOptionPane.showMessageDialog(null, "Dados inválidos e/ou base de dados indisponível!");
                }

                dados_bd = JOptionPane.showInputDialog(null, "Indique base de dados destino, endereço ou ip de conexão, porta , user e pass no seguinte formato : \n"
                        + "nomebd;conexao;porta;user;pass  (Se não há pass use o seguinte token no lugar de pass !#n0p@ss#!)");
                if (dados_bd == null) {
                    flag_geral = true;
                    break;
                }

                infor = dados_bd.split(";");
                if (infor.length == 5) {
                    flagbd = a.connectarbd(infor[0], tipo_bd, infor[1], infor[2], infor[3], infor[4]);
                }
                if (!flagbd) {
                    flagbd_message = true;
                }
            } while (!flagbd);




            boolean flagtab = false, flagtab_message = false;
            do {

                if (flag_geral) {
                    break;
                }
                //Indicar o nome da base de dados destino e o nome da tabela
                if (flagtab_message) {
                    JOptionPane.showMessageDialog(null, "Tabela já existente na base de dados ou colunas com nome repetido e/ou inválido!");
                }
                String nome_tabela;
                nome_tabela = JOptionPane.showInputDialog(null, "Indique o nome da tabela:");

                flagtab = a.criarTabela(nome_tabela, infor[0], matriz_sel, tipo_bd, infor[1], infor[2], infor[3], infor[4]);
                if (!flagtab) {
                    flagtab_message = true;

                    if (nome_tabela == null) {
                        flag_geral = true;
                        break;
                    }

                }
            } while (!flagtab);
            if (!flag_geral) {
                JOptionPane.showMessageDialog(null, "Informação gravada com sucesso!");
            }
        } else {
            if (flag_geral) {
                //Passa em frente
            } else {
                JOptionPane.showMessageDialog(null, "O nome das colunas é inválido");
            }
        }
    }

    /**
     * Lets the user select a font from a chooser.
     * Then applies the font to the selected cells in the focus owner table.
     * @param event the event that was fired
     */
    public void actionPerformed(ActionEvent event) {

        // Lets user select a font
        //Perguntar qual a área da folha que o utilizador pretende gravar

        // Vamos exemplificar como se acede ao modelo de dominio (o workbook)
        try {
            //Correr a thread criada
            Thread a = new Thread(new ThreadBD());
            a.start();



        } catch (Exception ex) {
            // ex.printStackTrace();
            // para ja ignoramos a excepcao
        }
    }
}
