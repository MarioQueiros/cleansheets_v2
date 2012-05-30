package csheets.bd;

import java.awt.event.ActionEvent;

import javax.swing.JOptionPane;

import csheets.ui.ctrl.BaseAction;
import csheets.ui.ctrl.UIController;

public class BaseDadosAction extends BaseAction {

    /** The user interface controller */
    protected UIController uiController;
     private int nrlinhas = 128, nrcolunas = 52;
    private String[][] options = new String[nrlinhas][nrcolunas];
    private String [][]matriz_sel;
    private String[] temp = new String[2];
    String[] coluna = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M",
                "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z", "AA", "AB", "AC", "AD", "AE", "AF", "AG",
                "AH", "AI", "AJ", "AK", "AL", "AM", "AN", "AO", "AP", "AQ", "AR", "AS", "AT", "AU", "AV", "AW", "AX", "AY", "AZ"};
   
    private boolean flag_geral=false;
    int[] linhas = new int[nrlinhas];
    int[] vec = new int[4]; //Vector com os extremos seleccionados

    /**
     * Creates a new font action.
     * @param uiController the user interface controller
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
    
    public void reset(){
            //flag começa sempre a false
            flag_geral=false;
    }

    protected String getName() {
        return "Gravar";
    }

    protected void defineProperties() {
    }

    public boolean validaString(String a) {
        for (int i = 0; i < nrlinhas; ++i) {
            for (int j = 0; j < nrcolunas; ++j) {
                if (options[i][j].equals(a)) {
                    return true;
                }
            }
        }
        return false;


    }
    
    public void escolherAreaCelulas(){
        boolean flag = false;

            do {


                if (flag) {
                    JOptionPane.showMessageDialog(null, "Introduziu valores errados ou no formato errado!");
                }
                flag = false;
                String receive;
                receive = JOptionPane.showInputDialog("Introduza a área das células que pretende gravar. Exemplo : A1-D5");
                System.out.println(receive);
                
                if(receive==null){
                    flag_geral=true;
                    break;
                }
                    
                    
                //Dividir a String
                if ( !receive.contains("-")) {
                    flag = true;
                }

                if ( receive.contains("-")) {
                    temp = receive.split("-");
                    if (!validaString(temp[0]) || !validaString(temp[1])) {
                        flag = true;
                    }

                }


                if ( receive.contains("-")) {
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
    
    
    public void preencherVectorCoord(){
        //Procurar as posições do vector para os extremos seleccionados e preencher no vector vec
            
            for (int i = 0; i < nrlinhas; ++i) {
                for (int j = 0; j < nrcolunas; ++j) {
                    if (options[i][j].equals(temp[0])) {
                        vec[0] = i;
                        vec[1] = j;
                    }
                    if (options[i][j].equals(temp[1])) {
                        vec[2] = i;
                        vec[3] = j;
                    }
                }
            }
            
    }
    
    public void preencherMatrizFinal(){
          // Matriz com a informação que o utilizador seleccionou
            int nrlinhas = vec[2] - vec[0] + 1;
            int nrcolunas = vec[3] - vec[1] + 1;
            matriz_sel = new String[nrlinhas][nrcolunas];
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
            reset();
            escolherAreaCelulas();
            preencherVectorCoord();
            preencherMatrizFinal();
            
            MySQL a = new MySQL();
            if(a.verificarErro(matriz_sel)){
               
            String nome_bd="";
            boolean flagbd = false, flagbd_message = false;
            do {
                
                if(flag_geral)
                    break;
                
                //Indicar o nome da base de dados destino e o nome da tabela
                if (flagbd_message) {
                    JOptionPane.showMessageDialog(null, "Base de dados inexistente no sistema");
                }
                 
                nome_bd = JOptionPane.showInputDialog(null, "Indique o nome da base de dados destino:");
                if(nome_bd==null){
                    flag_geral=true;
                    break;
                }
                flagbd = a.connectarbd(nome_bd);
                if (!flagbd) {
                    flagbd_message = true;
                }
            } while (!flagbd);


            boolean flagtab = false, flagtab_message = false;
            do {
                
                if(flag_geral)
                    break;
                //Indicar o nome da base de dados destino e o nome da tabela
                if (flagtab_message) {
                    JOptionPane.showMessageDialog(null, "Tabela já existente na base de dados ou colunas com nome repetido!");
                }
                String nome_tabela;
                nome_tabela = JOptionPane.showInputDialog(null, "Indique o nome da tabela:");

                flagtab = a.criarTabela(nome_tabela, nome_bd,matriz_sel);
                if (!flagtab) {
                    flagtab_message = true;
                    
                    if(nome_tabela==null){
                        flag_geral=true;
                        break;
                    }
                        
                }
            } while (!flagtab);
            if(!flag_geral)
            JOptionPane.showMessageDialog(null, "Informação gravada com sucesso!");
            }
            
            else{
                if(flag_geral){
                    //Passa em frente
                }
                else
                JOptionPane.showMessageDialog(null,"O nome das colunas é inválido");
            }


            //this.uiController.getActiveSpreadsheet().getCell(0, 0).setContent("Alterada");

        } catch (Exception ex) {
            //ex.printStackTrace();
            // para ja ignoramos a excepcao
        }
    }
}
