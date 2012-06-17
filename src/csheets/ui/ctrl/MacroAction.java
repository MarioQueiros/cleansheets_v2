package csheets.ui.ctrl;

import csheets.ui.MacrosFrame;
import java.awt.event.ActionEvent;
import csheets.ui.ctrl.UIController;
import java.util.ArrayList;
import javax.swing.JOptionPane;


public class MacroAction extends BaseAction {
    
    protected UIController uiController;
    ArrayList<String> listaMacros = new ArrayList<String>();
    ArrayList<String> nomeMacros = new ArrayList<String>();

    

    public MacroAction() {
    }
    

    public MacroAction(UIController uiController) {
        this.uiController = uiController;               
    }
   
    protected String getName() {
        return "Criacao de Macros";
    }

    protected void defineProperties() {
    }
    
    
    
   
    public void actionPerformed(ActionEvent event) {
        MacrosFrame aux = new MacrosFrame(uiController, listaMacros, nomeMacros);         
        
        aux.setLocation(500, 150);
        aux.setVisible(true);
        listaMacros = aux.getArrayMacros();
        nomeMacros = aux.getArrayNomeMacros();
    }
}