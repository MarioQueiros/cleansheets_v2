package csheets.bd;

import java.awt.event.ActionEvent;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

import csheets.ui.ctrl.BaseAction;
import csheets.ui.ctrl.UIController;

public class BaseDadosSyncAction extends BaseAction {
    /** The user interface controller */
    protected UIController uiController;
    private int nrlinhas = 128, nrcolunas = 52;
    private String[][] options = new String[nrlinhas][nrcolunas];
    private String[][] matriz_sel; // Área que da tabela da CleanSheets que o utilizador pretende sincronizar
    private String[] temp = new String[2];
    String[] coluna = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M",
        "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z", "AA", "AB", "AC", "AD", "AE", "AF", "AG",
        "AH", "AI", "AJ", "AK", "AL", "AM", "AN", "AO", "AP", "AQ", "AR", "AS", "AT", "AU", "AV", "AW", "AX", "AY", "AZ"};
    private boolean flag_geral = false;
    String infor[] = new String[5];
    String pk[] = new String[52];
    int[] linhas = new int[nrlinhas];
    String [][]  matriz_bd , matriz_inicial;
    
   
    int valida_colunas;
    int[] vec = new int[4]; //Vector com os extremos seleccionados
    String tipo_bd, nome_tabela;
    public class ThreadBD implements Runnable {

        public ThreadBD() {
        }

        @Override
        public void run() {
            reset();
            escolhaSGBD();
            IBaseDados bd;
            if(!flag_geral){
            bd = BaseDadosFactory.getBD(tipo_bd);
            formulario(bd);
            escolherAreaCelulas(bd);
            preencherVectorCoord();
            preencherMatrizFinal();
            matriz_bd = bd.getMatrizInf(nome_tabela,infor[0] , tipo_bd, infor[1], infor[2], infor[3], infor[4]);
            matriz_inicial= bd.getMatrizInf(nome_tabela,infor[0] , tipo_bd, infor[1], infor[2], infor[3], infor[4]);
             SyncSingleton.Instance().setStop(false);
            int i =0;
            //Ciclo para a thread, de X em X segundos actualiza a informação.
            while(!SyncSingleton.Instance().getStop()){
                    try {
                        Thread.currentThread().sleep(3000);
                        getConteudoActual();
                        System.out.println(i);
                        i++;
                        
                        
                    } catch (InterruptedException ex) {
                        Logger.getLogger(BaseDadosSyncAction.class.getName()).log(Level.SEVERE, null, ex);
                    }
            }
            
            }
        }
    }

    /**
     * Creates a new font action.
     * @param uiController the user interface controller
     * 
     */
    public BaseDadosSyncAction(UIController uiController) {
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
    // Método para teste
    public void printmatriz(){
        for(int i=0;i<matriz_sel.length;i++){
            for(int k=0;k<matriz_sel[0].length;k++){
                System.out.println(matriz_sel[i][k]+" | ");
            }
            
        }
        
    }
    // Refresh à matriz da área que o utilizador decidiu sincronizar
    public void getConteudoActual(){
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

    protected String getName() {
        return "Sincronizar";
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
   public void escolherAreaCelulas(IBaseDados a) {
        boolean flag = false;

        do {
               
           valida_colunas = a.getNrColunas(nome_tabela, infor[0], tipo_bd, infor[1], infor[2], infor[3], infor[4]);
            
            
            if (flag_geral) {
                break;
            }

            if (flag) {
                JOptionPane.showMessageDialog(null, "Introduziu valores errados ou no formato errado!");
            }
            flag = false;
            String receive;
            receive = JOptionPane.showInputDialog("Introduza a área das células que pretende sincronizar com a tabela. Exemplo : A1-D5");


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
                        if (options[i][j].equalsIgnoreCase(temp[0])) {
                            valida[0] = i;
                            valida[1] = j;
                        }
                        if (options[i][j].equalsIgnoreCase(temp[1])) {
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
        // Verifica se a área que pretende sincronizar tem o mesmo número de colunas que a tabela
        if(matriz_sel[0].length!=valida_colunas){
            JOptionPane.showMessageDialog(null, "Erro! Número de colunas incompatível");
              flag_geral = true;
             
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

            tipo_bd = JOptionPane.showInputDialog(null, "De que tipo de SGBD pretende carregar a informação para sincronizar? (Postgres/MySQL/SQLserver)");
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

    public void formulario(IBaseDados a) {
        



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
                    JOptionPane.showMessageDialog(null, "Tabela não existente na base de dados!");
                }
                
                nome_tabela = JOptionPane.showInputDialog(null, "Indique o nome da tabela:");
                 if(nome_tabela==null){
                     flag_geral=true;
                     break;
                 }
                flagtab = a.carregarInformacao(nome_tabela, uiController, infor[0] , tipo_bd, infor[1], infor[2], infor[3], infor[4]);
                if (!flagtab) {
                    flagtab_message = true;

                    if (nome_tabela == null) {
                        flag_geral = true;
                        break;
                    }

                }
            } while (!flagtab);
            if (!flag_geral) {
                //Aqui deve-se preencher a matriz 
                
                
                JOptionPane.showMessageDialog(null, "Informação exportada com sucesso!");
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
             ex.printStackTrace();
            // para ja ignoramos a excepcao
        }
    }
}
