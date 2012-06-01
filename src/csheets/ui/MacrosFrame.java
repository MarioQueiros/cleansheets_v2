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

        //Caso nao tenha sido introduzida nenhuma instru??o para a macro, mostrar mensagem ao utilizador
        if (verificarTexto.equals("")) {
            JOptionPane.showMessageDialog(null, "Nao inseriu nenhuma instrucao para uma macro.");
        } else {

            String[] textoMacro = jTextArea1.getText().split("\n");

            //Verificar se o nome da macro introduzido ja existe na comboBox
            int numeroMacros = jComboBox1.getItemCount();
            int flag;
            nomeMacro = jTextField1.getText();

            flag = 0;
            for (int j = 0; j < numeroMacros; j++) {
                if (nomeMacro.equals(jComboBox1.getItemAt(j))) {
                    int resposta = 0;

                    if (flag != 1) {
                        resposta = JOptionPane.showConfirmDialog(this, "A macro '" + nomeMacro + "' já está criada! Pretende substituir o valor da mesma?", "Atencao!", JOptionPane.WARNING_MESSAGE);
                    }
                    if (resposta == 0 && flag != 1) {
                        flag = 2;
                    } else if (resposta == 2) {
                        flag = 1;
                    }
                }
            }

            if (flag == 0) {

                for (int i = 0; i < textoMacro.length; i++) {

                    aux = textoMacro[i].split(":=");

                    if (aux.length == 1) {
                        JOptionPane.showMessageDialog(null, "Nao colocou nenhuma formula. Por favor tente novamente.");
                    } else {
                        Pattern p = Pattern.compile("([a-zA-Z])([1-9]+[0-9]*)");
                        Matcher m = p.matcher(aux[0]);

                        //Verificar se a expressao e valida
                        if (m.matches() == true) {

                            if (i + 1 == textoMacro.length && flag != 1) {
                                jComboBox1.addItem(nomeMacro);
                            }

                            String x = aux[0];

                            String auxiliar;
                            auxiliar = aux[1];

                            aux[1] = "=" + auxiliar;


                            char[] letra = x.substring(0, 1).toCharArray();
                            char[] numero = x.substring(1, 2).toCharArray();

                            int posx, posy;

                            posx = numero[0] - 49;
                            posy = letra[0] - 65;

                            try {
                                this.uiController.getActiveSpreadsheet().getCell(posy, posx).setContent(aux[1]);
                            } catch (FormulaCompilationException ex) {
                                JOptionPane.showMessageDialog(null, "A formula " + aux[1] + " nao esta correctamente implementada. Por favor tente novamente.");
                            }

                        } else {
                            flag = 1;
                        }


                    }

                    if (flag == 1) {
                        JOptionPane.showMessageDialog(null, "Expressao nao esta correcta. O formato devera de ser por exemplo A1:=(seguido da formula pretendida)");
                    } else {
                        //Adicionar a uma lista a macro criada
                        listaMacros.add(jTextArea1.getText());
                    }
                }

                if (flag == 0) {
                    JOptionPane.showMessageDialog(null, "A macro '" + jTextField1.getText() + "' foi criada com sucesso!");
                }

            }

            //Este if serve para actualizar a macro que já foi criada anteriormente
            if (flag == 2) {
                int totalMacros = jComboBox1.getItemCount();
                int pos = 0;


                for (int j = 0; j < totalMacros; j++) {
                    if (jComboBox1.getItemAt(j).equals(jTextField1.getText())) {
                        listaMacros.set(j - 1, jTextArea1.getText());

                        for (int i = 0; i < textoMacro.length; i++) {

                            aux = textoMacro[i].split(":=");


                            if (aux.length == 1) {
                                JOptionPane.showMessageDialog(null, "Nao colocou nenhuma formula. Por favor tente novamente.");
                            } else {


                                Pattern p = Pattern.compile("([a-zA-Z])([1-9]+[0-9]*)");
                                Matcher m = p.matcher(aux[0]);

                                //Verificar se a expressao e valida
                                if (m.matches() == true) {

                                    String x = aux[0];

                                    String auxiliar;
                                    auxiliar = aux[1];

                                    aux[1] = "=" + auxiliar;

                                    char[] letra = x.substring(0, 1).toCharArray();
                                    char[] numero = x.substring(1, 2).toCharArray();

                                    int posx, posy;

                                    posx = numero[0] - 49;
                                    posy = letra[0] - 65;

                                    try {
                                        this.uiController.getActiveSpreadsheet().getCell(posy, posx).setContent(aux[1]);
                                    } catch (FormulaCompilationException ex) {
                                        JOptionPane.showMessageDialog(null, "A formula " + aux[1] + " nao esta correctamente implementada. Por favor tente novamente.");
                                    }

                                } else {
                                    flag = 1;
                                }
                            }
                        }

                        if (flag == 2) {
                            JOptionPane.showMessageDialog(null, "A macro '" + jTextField1.getText() + "' foi alterada com sucesso!");
                        }

                        if (flag == 1) {
                            JOptionPane.showMessageDialog(null, "Expressao nao esta correcta. O formato devera de ser por exemplo A1:=(seguido da formula pretendida)");
                        }
                    }
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
}
