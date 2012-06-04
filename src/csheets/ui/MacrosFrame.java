/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package csheets.ui;

import csheets.core.formula.compiler.FormulaCompilationException;
import csheets.core.formula.lang.CellReference;
import csheets.ui.ctrl.UIController;
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

    ArrayList<String> listaMacros = new ArrayList<String>();
    protected UIController uiController;
    private String texto;
    private String[] aux = new String[100];
    private JButton jButton1;
    private JButton jButton2;
    private JLabel jLabel1;
    private JLabel jLabel2;
    private JTextField jTextField1;
    private JPanel jpanel = new JPanel();
    private JScrollPane jScrollPane1;
    private JScrollPane jScrollPane2;
    private JTextArea jTextArea1;
    private JComboBox jComboBox1;

    public MacrosFrame(UIController uiController) {
        this.uiController = uiController;

        //Parte Criacao do Layout da janela das Macros
        jLabel1 = new JLabel();
        jButton1 = new JButton();
        jLabel2 = new JLabel();
        jTextField1 = new JTextField();
        jScrollPane1 = new JScrollPane();
        jTextArea1 = new JTextArea();
        jComboBox1 = new JComboBox();
        jButton2 = new JButton();

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

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addGap(77, 77, 77).addComponent(jLabel1)).addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addComponent(jLabel2).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)).addGroup(layout.createSequentialGroup().addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE).addGap(18, 18, 18).addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))).addGroup(layout.createSequentialGroup().addGap(45, 45, 45).addComponent(jButton1).addGap(33, 33, 33).addComponent(jButton2))).addContainerGap(25, Short.MAX_VALUE)));
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addComponent(jLabel1).addGap(11, 11, 11).addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(jLabel2).addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)).addGap(13, 13, 13).addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE).addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED).addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(jButton1).addComponent(jButton2)).addContainerGap(21, Short.MAX_VALUE)));

        pack();
    }

    //Parte da execucao da Macro
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) throws ParseException {
        String nomeMacro;

        String verificarTexto;
        verificarTexto = jTextArea1.getText();

        //Caso nao tenha sido introduzida nenhuma instrucao para a macro, mostrar mensagem ao utilizador
        if (verificarTexto.equals("")) {
            JOptionPane.showMessageDialog(null, "Nao inseriu nenhuma instrucao para uma macro.");
        } else {

            String[] textoMacro = jTextArea1.getText().split("\n");

            //Verificar se o nome da macro introduzido ja existe na comboBox
            int numeroMacros = jComboBox1.getItemCount();
            int flag;
            nomeMacro = jTextField1.getText();

            flag = 0;

            //Verifica se a macro ja esta criada e verificar se o nome dela e "Macros"
            if (jTextField1.getText().equals("Macros") || jTextField1.getText().equals("Introduzir nome da macro aqui...")) {
                JOptionPane.showMessageDialog(null, "Não pode criar uma macro com esse nome!");
                flag = 5;
            }

            for (int j = 0; j < numeroMacros; j++) {
                if (nomeMacro.equals(jComboBox1.getItemAt(j))) {
                    int resposta = 0;

                    if (flag != 1 && flag != 5) {
                        resposta = JOptionPane.showConfirmDialog(this, "A macro '" + nomeMacro + "' ja esta criada! Pretende substituir o valor da mesma?", "Atencao!", JOptionPane.WARNING_MESSAGE);
                    }
                    if (resposta == 0 && flag != 1 && flag != 5) {
                        flag = 6;
                    } else if (resposta == 2) {
                        flag = 1;
                    }
                }
            }

            if (flag == 0) {

                flag = colocarCelula(textoMacro, flag);

                if (flag == 0) {
                    //Caso nao tenha havido problemas com a macro, adicionar a uma lista a macro criada e a combobox
                    listaMacros.add(jTextArea1.getText());
                    jComboBox1.addItem(nomeMacro);
                    JOptionPane.showMessageDialog(null, "A macro '" + jTextField1.getText() + "' foi criada com sucesso!");
                } else if (flag == 1) {
                    JOptionPane.showMessageDialog(null, "Expressao nao esta correcta. O formato devera de ser por exemplo A1:=(seguido da formula pretendida)");
                } else if (flag == 2) {
                    JOptionPane.showMessageDialog(null, "Existe uma ou mais instrucoes na macro que nao tem formula. Por favor verifique novamente a macro.");
                } else if (flag == 3) {
                    JOptionPane.showMessageDialog(null, "Existe uma ou mais instrucoes na macro em a fórmula nao esta correctamente implementada! Por favor verifique novamente a macro.");
                } else if (flag == 4) {
                    JOptionPane.showMessageDialog(null, "Deverá colocar um nome na Macro!");
                }
            }

            //Este if serve para actualizar a macro que já foi criada anteriormente
            if (flag == 6) {
                int totalMacros = jComboBox1.getItemCount();
                int pos = 0;
                flag = 0;

                flag = colocarCelula(textoMacro, flag);

                if (flag == 0) {

                    //Ira actualizar a arraylist que guarda as macros com o novo conteudo da macro
                    for (int j = 0; j < totalMacros; j++) {
                        if (jComboBox1.getItemAt(j).equals(jTextField1.getText())) {
                            listaMacros.set(j - 1, jTextArea1.getText());
                        }
                    }
                    JOptionPane.showMessageDialog(null, "A macro '" + jTextField1.getText() + "' foi alterada com sucesso!");

                } else if (flag == 1) {
                    JOptionPane.showMessageDialog(null, "Expressao nao esta correcta. O formato devera de ser por exemplo A1:=(seguido da formula pretendida)");
                } else if (flag == 2) {
                    JOptionPane.showMessageDialog(null, "Existe uma ou mais instrucoes na macro que nao tem formula. Por favor verifique novamente a macro.");
                } else if (flag == 3) {
                    JOptionPane.showMessageDialog(null, "Existe uma ou mais instrucoes na macro em a fórmula nao esta correctamente implementada! Por favor verifique novamente a macro.");
                } else if (flag == 4) {
                    JOptionPane.showMessageDialog(null, "Deverá colocar um nome na Macro!");
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
        //está predefinida na combobox e nao tem nenhuma macro la dentro
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

    public int colocarCelula(String[] textoMacro, int flag) {
        int posx=0, posy=0;
        
        
        for (int i = 0; i < textoMacro.length; i++) {

            aux = textoMacro[i].split(":=");

            Pattern p = Pattern.compile("([a-zA-Z])([1-9]+[0-9]*)");
            Matcher m = p.matcher(aux[0]);

            //Verificar se a expressao e valida
            if (m.matches() == true) {

                //Verificar caso tenha sido colocado "A1:="
                if (aux.length == 1) {
                    int aux = i + 1;
                    flag = 2;
                } else if (jTextField1.getText().equals("")) {
                    flag = 4;
                } else {
                    String auxiliar;
                    auxiliar = aux[1];

                    aux[1] = "=" + auxiliar;

                    for (int l = 0; l < aux[0].length(); l++) {
                        char[] caracter = aux[0].substring(l, l+1).toCharArray();
                        
                        if (Character.isDigit(caracter[0])){
                            //Numeros
                            int auxiliarPosNumeros = caracter[0] - 48;
                            
                            String auxiliarPosString = Integer.toString(auxiliarPosNumeros);
                            
                            posx = Integer.parseInt(posx+auxiliarPosString);
                        }else{
                            //Letras
                            int auxiliarPosLetras = caracter[0] - 65;
                            
                            String auxiliarPosString = Integer.toString(auxiliarPosLetras);
                            
                            posy = Integer.parseInt(posy+auxiliarPosString);
                        }
                    }

                    try {
                        this.uiController.getActiveSpreadsheet().getCell(posy, posx-1).setContent(aux[1]);
                    } catch (FormulaCompilationException ex) {
                        flag = 3;
                    }
                }

            } else {
                flag = 1;
            }
        }
        return flag;
    }
}
