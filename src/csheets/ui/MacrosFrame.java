/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package csheets.ui;

import antlr.ANTLRException;
import antlr.collections.AST;
import csheets.core.Cell;
import csheets.core.CellImpl;
import csheets.core.formula.compiler.*;
import csheets.core.formula.lang.CellReference;
import csheets.ui.ctrl.UIController;
import java.io.StringReader;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.*;

/**
 *
 * @author Hugo Dias
 */
public class MacrosFrame extends JFrame {

    protected static ArrayList<String> listaMacros;
    protected static ArrayList<String> nomeMacros;
    protected static UIController uiController;
    private static String texto;
    private static String[] aux = new String[100];
    private static JButton jButton1;
    private static JButton jButton2;
    private static JLabel jLabel1;
    private static JLabel jLabel2;
    private static JTextField jTextField1;
    private static JPanel jpanel = new JPanel();
    private static JScrollPane jScrollPane1;
    private static JScrollPane jScrollPane2;
    private static JTextArea jTextArea1;
    private static JComboBox jComboBox1;
    private static JTextArea jTextArea2;
    private static JLabel jLabel3;

    public MacrosFrame(UIController uiController, ArrayList<String> listaMacros, ArrayList<String> nomeMacros) {
        this.uiController = uiController;
        this.listaMacros = listaMacros;
        this.nomeMacros = nomeMacros;

        //Parte Criacao do Layout da janela das Macros
        jLabel1 = new JLabel();
        jButton1 = new JButton();
        jLabel2 = new JLabel();
        jTextField1 = new JTextField();
        jScrollPane1 = new JScrollPane();
        jTextArea1 = new JTextArea();
        jComboBox1 = new JComboBox();
        jButton2 = new JButton();
        jScrollPane2 = new JScrollPane();
        jTextArea2 = new JTextArea();
        jLabel3 = new JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setLocationByPlatform(true);

        jLabel1.setFont(new java.awt.Font("Arial", 1, 14));
        jLabel1.setText("Criacao de Macros");

        jButton1.setFont(new java.awt.Font("Arial", 0, 11));
        jButton1.setText("Executar Macro");
        jButton1.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent evt) {
                try {
                    jButton1ActionPerformed(evt);
                } catch (ParseException ex) {
                    Logger.getLogger(MacrosFrame.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        jLabel2.setFont(new java.awt.Font("Arial", 1, 11));
        jLabel2.setText("Macro pretendida:");

        jTextField1.setFont(new java.awt.Font("Arial", 0, 11));
        jTextField1.setText("Introduzir nome da macro aqui...");
        jTextField1.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });

        jTextArea1.setColumns(20);
        jTextArea1.setFont(new java.awt.Font("Arial", 0, 13));
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel(new String[]{"Macros"}));

        jButton2.setFont(new java.awt.Font("Arial", 0, 11));
        jButton2.setText("Cancelar");
        jButton2.addMouseListener(new java.awt.event.MouseAdapter() {
        });
        jButton2.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent evt) {
                try {
                    jButton2ActionPerformed(evt);
                } catch (ParseException ex) {
                    Logger.getLogger(MacrosFrame.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        jComboBox1.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });


        jTextArea2.setColumns(20);
        jTextArea2.setFont(new java.awt.Font("Arial", 0, 13));
        jTextArea2.setRows(5);
        jTextArea2.setEnabled(false);
        jScrollPane2.setViewportView(jTextArea2);

        jLabel3.setFont(new java.awt.Font("Arial", 1, 11));
        jLabel3.setText("Resultado da ultima instrucao:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addComponent(jScrollPane2)).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addGap(77, 77, 77).addComponent(jLabel1)).addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addComponent(jLabel2).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)).addGroup(layout.createSequentialGroup().addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE).addGap(18, 18, 18).addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))).addGroup(layout.createSequentialGroup().addContainerGap().addComponent(jLabel3)).addGroup(layout.createSequentialGroup().addGap(34, 34, 34).addComponent(jButton1).addGap(34, 34, 34).addComponent(jButton2))).addGap(0, 0, Short.MAX_VALUE))).addContainerGap()));
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addComponent(jLabel1).addGap(11, 11, 11).addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(jLabel2).addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)).addGap(13, 13, 13).addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE).addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)).addGap(21, 21, 21).addComponent(jLabel3).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE).addGap(18, 18, 18).addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(jButton1).addComponent(jButton2)).addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));

        pack();

        //Caso se abra novamente a janela, voltar a carregar as macros existentes
        for (int k = 0; k < nomeMacros.size(); k++) {
            if (nomeMacros.get(k) != null) {
                jComboBox1.addItem(nomeMacros.get(k));
            }
        }
    }

    //Parte da execucao da Macro
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) throws ParseException {
        ArrayList<String> nomeVar = new ArrayList<String>();
        ArrayList<Integer> numeroVar = new ArrayList<Integer>();

        String nomeMacro;

        String verificarTexto;
        verificarTexto = jTextArea1.getText();

        //Caso nao tenha sido introduzida nenhuma instrucao para a macro, mostrar mensagem ao utilizador
        if (verificarTexto.equals("")) {
            JOptionPane.showMessageDialog(null, "Nao inseriu nenhuma instrucao para uma macro.");
        } else {

            //Verificar se o nome da macro introduzido ja existe na comboBox
            int numeroMacros = jComboBox1.getItemCount();
            int flag, flagAlteracao = 0;
            nomeMacro = jTextField1.getText();

            flag = 0;

            //Verifica se a macro ja esta criada e verificar se o nome dela e "Macros"
            if (jTextField1.getText().equals("Macros") || jTextField1.getText().equals("Introduzir nome da macro aqui...")) {
                JOptionPane.showMessageDialog(null, "Nao pode criar uma macro com esse nome!");
                flag = 5;
            } else if (jTextField1.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "Deve de inserir um nome para a macro!");
                flag = 4;
            }

            //Verificar se o nome da Macro ja existe
            for (int j = 0; j < numeroMacros; j++) {
                if (nomeMacro.equals(jComboBox1.getItemAt(j))) {
                    int resposta = 0;

                    if (flag != 1 && flag != 5) {
                        resposta = JOptionPane.showConfirmDialog(this, "A macro '" + nomeMacro + "' ja esta criada! Pretende substituir o valor da mesma?", "Atencao!", JOptionPane.WARNING_MESSAGE);
                    }
                    if (resposta == 0 && flag != 1 && flag != 5) {
                        flag = 6;
                        flagAlteracao = 6;
                    } else if (resposta == 2) {
                        flag = 1;
                    }
                }
            }


            if (flag == 0 || flag == 6) {

                int totalMacros = jComboBox1.getItemCount();
                flag = 0;

                String conteudoInstrucao = "";
                
                String auxiliar[] = new String[3];
                
                auxiliar=executarMacro(nomeVar,numeroVar,"");
                
                conteudoInstrucao=auxiliar[1];

                flag = Integer.parseInt(auxiliar[0]);

                //Caso nao tenha havido problemas com a macro, adicionar a uma lista a macro criada e a combobox
                if (flag == 0 && conteudoInstrucao != "erro" && flagAlteracao != 6) {
                    String a = jTextField1.getText();
                    nomeMacros.add(a);
                    listaMacros.add(jTextArea1.getText());
                    jComboBox1.addItem(nomeMacro);
                    JOptionPane.showMessageDialog(null, "A macro '" + jTextField1.getText() + "' foi criada com sucesso!");
                } else //Ira actualizar os resultados da macro ja criada
                if (flag == 0 && conteudoInstrucao != "erro" && flagAlteracao == 6) {

                    //Ira actualizar a arraylist que guarda as macros com o novo conteudo da macro 
                    for (int j = 0; j < totalMacros; j++) {
                        if (jComboBox1.getItemAt(j).equals(jTextField1.getText())) {
                            listaMacros.set(j - 1, jTextArea1.getText());
                        }
                    }
                    JOptionPane.showMessageDialog(null, "A macro '" + jTextField1.getText() + "' foi alterada com sucesso!");
                }
            }
        }
    }

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) throws ParseException {
        this.setVisible(false);
    }

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {
    }

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {
        //Ira fazer com que ao seleccionar certa macro, a mesma ira aparecer na area ao lado
        int totalMacros = jComboBox1.getItemCount();
        int pos = 0;

        //Este if serve para verificar que o programa nao faz nada quando se selecciona na combobox a palavra "Macros" porque esta
        //esta predefinida na combobox e nao tem nenhuma macro la dentro
        if (jComboBox1.getSelectedItem().equals("Macros")) {
            jTextArea1.setText("");
        } else {
            for (int j = 0; j < totalMacros; j++) {
                if (jComboBox1.getItemAt(j).equals(jComboBox1.getSelectedItem())) {
                    pos = j;
                }
            }

            String nomeSeleccionado = jComboBox1.getSelectedItem().toString();
            jTextField1.setText(nomeSeleccionado);

            jTextArea1.setText(listaMacros.get(pos - 1));
        }
    }

    public static String[] executarMacro(ArrayList<String> nomeVar, ArrayList<Integer> numeroVar, String source ) {
        String auxiliar[] = new String[3];
        int flag=0;
        String [] textoMacro;
        
        
        if (source.equals("")){
           textoMacro = jTextArea1.getText().split("\n"); 
        }else{
            textoMacro = source.split("\n"); 
        }
        
        String conteudoInstrucao = "";

        MacrosExpressionCompiler mec = new MacrosExpressionCompiler();
        //Mandar linha a linha da macro e verificar se ta tudo correcta com ela e fazer os calculos
        for (int j = 0; j < textoMacro.length; j++) {
            try {
                if (conteudoInstrucao != "erro") {
                    conteudoInstrucao = mec.compile(textoMacro[j], nomeVar, numeroVar);
                }
            } catch (FormulaCompilationException ex) {
                Logger.getLogger(MacrosFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
            textoMacro[j] = conteudoInstrucao;

            String[] a = new String[5];
            a = textoMacro[j].split(":=");

            //Verificar se e uma variavel e se e maior de 4, caso nao seja significa que e do tipo $res:=123
            if (textoMacro[j].substring(0, 1).equals("$") && a[1].length() > 4 && conteudoInstrucao != "erro" || textoMacro[j].substring(0, 1).equals("$") == false && conteudoInstrucao != "erro") {
                flag = colocarCelula(textoMacro[j], flag, nomeVar, numeroVar);
                //$var:=123
            } else if (textoMacro[j].substring(0, 1).equals("$") && a[1].length() < 4) {
                jTextArea2.setText("=" + a[1]);
            }

        }
 
        auxiliar[0]=Integer.toString(flag);    //flag    
        auxiliar[1]=conteudoInstrucao;         //instrucao 
        auxiliar[2]=jTextArea2.getText();      
        auxiliar[2]=jTextArea2.getText().substring(1, auxiliar[2].length()); //resultado da ultima instrucao
        
        
        return auxiliar;
    }

    public static int colocarCelula(String textoMacro, int flag, ArrayList<String> nomeVar, ArrayList<Integer> numeroVar) {
        int posx = 0, posy = 0;
        String auxiliar;
        int valorVar;
        String valorVarString = "";

        aux = textoMacro.split(":=");

        //Caso seja do tipo $var:=sum(1;2) ou $var:=sum($res;2)
        if (textoMacro.substring(0, 1).equals("$") && aux[1].length() > 4) {

            auxiliar = aux[1];
            aux[1] = "=" + auxiliar;

            String textoCelula = aux[1];
            flag=inserirCelula(90, 90, textoCelula);

            valorVarString = MacrosFrame.uiController.getActiveSpreadsheet().getCell(90, 90).getValue().toString();
            jTextArea2.setText("=" + valorVarString);

            flag=inserirCelula(90, 90, "");

            valorVar = Integer.parseInt(valorVarString);

            nomeVar.add(aux[0]);
            numeroVar.add(valorVar);

            //Caso seja A1:=$var   
        } else if (aux[1].substring(0, 1).equals("$")) {
            int flagArray = 0;
            //Percorrer o arrayList para verificar se a variavel existe, senao mete a 0
            for (int i = 0; i < nomeVar.size(); i++) {
                //Se existir no ArrayList a variavel, retirar o seu valor
                if (nomeVar.get(i).equals(aux[1])) {
                    int auxiliarVar = numeroVar.get(i);
                    auxiliar = Integer.toString(auxiliarVar);
                    aux[1] = "=" + auxiliar;
                    flagArray = 1;
                }
            }
            //Caso nao tenha encontrado no Array,colocar a 0
            if (flagArray == 0) {
                aux[1] = "=" + "0";
            }
            posx = 0;
            posy = 0;

            int auxPos[] = new int[2];
            auxPos = posCelulas(aux, posx, posy);

            posx = auxPos[0];
            posy = auxPos[1];

            String textoCelula = aux[1];
            flag=inserirCelula(posy, posx - 1, textoCelula);
            jTextArea2.setText(aux[1]);


        }//Caso seja A1:=sum(1;2) ou A1:=123
        else {
            auxiliar = "";
            auxiliar = aux[1];

            aux[1] = "=" + auxiliar;
            posx = 0;
            posy = 0;

            int auxPos[] = new int[2];
            auxPos = posCelulas(aux, posx, posy);

            posx = auxPos[0];
            posy = auxPos[1];

            String textoCelula = aux[1];
            flag=inserirCelula(posy, posx - 1, textoCelula);
            jTextArea2.setText("=" + MacrosFrame.uiController.getActiveSpreadsheet().getCell(posy, posx - 1).getValue().toString());

        }

        return flag;
    }

    public static int[] posCelulas(String[] aux, int posx, int posy) {
        int auxPos[] = new int[2];

        for (int l = 0; l < aux[0].length(); l++) {

            char[] caracter = aux[0].substring(l, l + 1).toCharArray();

            if (Character.isDigit(caracter[0])) {
                //Numeros
                int auxiliarPosNumeros = caracter[0] - 48;
                String auxiliarPosString = Integer.toString(auxiliarPosNumeros);
                posx = Integer.parseInt(posx + auxiliarPosString);
            } else {
                //Letras
                int auxiliarPosLetras = caracter[0] - 65;
                String auxiliarPosString = Integer.toString(auxiliarPosLetras);
                posy = Integer.parseInt(posy + auxiliarPosString);
            }
        }
        auxPos[0] = posx;
        auxPos[1] = posy;


        return auxPos;
    }

    public static int inserirCelula(int posx, int posy, String aux) {
        int flag=0;
        try {
            MacrosFrame.uiController.getActiveSpreadsheet().getCell(posx, posy).setContent(aux);
        } catch (FormulaCompilationException ex) {
            JOptionPane.showMessageDialog(null, "Houve um problema a colocar os valores nas celulas. Por favor tente novamente.");
            flag=1;
        }
        return flag;
    }
    
    public static String verificarNomeMacro(String nomeMacro){
        //Verificar se a macro existe
         for(int i=0;i<nomeMacros.size();i++){
             if(nomeMacros.get(i).equals(nomeMacro)){
                 return listaMacros.get(i);
             }
         }
        return "";
    }

    public ArrayList<String> getArrayMacros() {
        return listaMacros;
    }

    public ArrayList<String> getArrayNomeMacros() {
        return nomeMacros;
    }
}
