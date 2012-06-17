package csheets.bd;

import java.awt.event.ActionEvent;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

import csheets.ui.ctrl.BaseAction;
import csheets.ui.ctrl.UIController;

public class BaseDadosLoadAction extends BaseAction {

    /** The user interface controller */
    protected UIController uiController;
    private boolean flag_geral = false;
    String infor[] = new String[5];
    String tipo_bd;

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
            }
        }
    }

    /**
     * Creates a new font action.
     * @param uiController the user interface controller
     * 
     */
    public BaseDadosLoadAction(UIController uiController) {
        this.uiController = uiController;

        
       

    }

    public void reset() {
        //flag começa sempre a false
        flag_geral = false;
        

    }

    protected String getName() {
        return "Carregar";
    }

    protected void defineProperties() {
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

            tipo_bd = JOptionPane.showInputDialog(null, "De que tipo de SGBD pretende carregar? (Postgres/MySQL/SQLserver)");
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
                String nome_tabela;
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
                
                
                JOptionPane.showMessageDialog(null, "Informação importada com sucesso!");
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
