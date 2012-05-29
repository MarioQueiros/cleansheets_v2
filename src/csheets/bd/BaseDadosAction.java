package csheets.bd;

import java.awt.event.ActionEvent;

import javax.swing.JOptionPane;

import csheets.ui.ctrl.BaseAction;
import csheets.ui.ctrl.UIController;

public class BaseDadosAction extends BaseAction {

	/** The user interface controller */
	protected UIController uiController;
        private String[][] options;
        private String[]  temp;

	/**
	 * Creates a new font action.
	 * @param uiController the user interface controller
	 */
	public BaseDadosAction(UIController uiController) {
		this.uiController = uiController;
	}

	protected String getName() {
		return "Gravar";
	}

	protected void defineProperties() {
	}
        
        public boolean validaString(String a){
             for(int i=0;i<52;++i){
                           for(int j=0;j<128;++j)
                               if(options[i][j].equals(a))
                                    return true;
         }
            return false;
            
            
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
                    
                   //Colunas e linhas das sheets
                    options = new String[52][128];
                    String[] coluna = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", 
                        "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z", "AA", "AB", "AC", "AD", "AE", "AF", "AG", 
                        "AH", "AI", "AJ", "AK", "AL", "AM", "AN", "AO", "AP", "AQ", "AR", "AS", "AT", "AU", "AV", "AW", "AX", "AY", "AZ"};
                    int[] linhas = new int[128];
                    for (int a = 0; a < 128; ++a) {
                        linhas[a] = a + 1;
                    }

                    //Preencher as opções possíveis tendo em conta o formato do Csheets
                    for(int i=0;i<52;++i){
                           for(int j=0;j<128;++j)
                               options[i][j]=coluna[i]+linhas[j];
                                
                            
                   }
                   
                    boolean flag=false;
                   
                    do{    
                        if(flag)
                                JOptionPane.showMessageDialog(null,"Introduziu valores errados ou no formato errado!");
                    String receive;   
                    receive=JOptionPane.showInputDialog("Introduza a área das células que pretende gravar. Exemplo : A1-D5");
                    System.out.println(receive);
                    
                    //Dividir a String
                  
                    temp=receive.split("-");
                    if(!validaString(temp[0]) || !validaString(temp[1]))
                        flag=true;
                   
                    } while(!validaString(temp[0]) || !validaString(temp[1]));
                    
                    
                       MySQL a = new MySQL();
                      String nome_bd;
                      boolean flagbd=false,flagbd_message=false;
                      do{
                       //Indicar o nome da base de dados destino e o nome da tabela
                          if(flagbd_message)
                              JOptionPane.showMessageDialog(null,"Base de dados inexistente no sistema");
                    
                      nome_bd=JOptionPane.showInputDialog(null,"Indique o nome da base de dados destino:");
                     
                      flagbd=a.connectarbd(nome_bd);
                      if(!flagbd)
                           flagbd_message=true;
                      } while(!flagbd);
                      
                      
                       boolean flagtab=false,flagtab_message=false;
                      do{
                       //Indicar o nome da base de dados destino e o nome da tabela
                          if(flagtab_message)
                              JOptionPane.showMessageDialog(null,"Tabela já existente na base de dados!");
                      String nome_tabela;
                      nome_tabela=JOptionPane.showInputDialog(null,"Indique o nome da tabela:");
                     
                      flagbd=a.criarTabela(nome_tabela,nome_bd);
                      if(!flagtab)
                           flagtab_message=true;
                      } while(!flagtab);
                      
                      
			//this.uiController.getActiveSpreadsheet().getCell(0, 0).setContent("Alterada");
                        
		} catch (Exception ex) {
			// para ja ignoramos a excepcao
		}
	}
}


