package csheets.ui.ctrl;

import csheets.ui.MacrosFrame;
import java.awt.event.ActionEvent;
import csheets.ui.ctrl.UIController;
import javax.swing.JOptionPane;


public class MacroAction extends BaseAction {
    
    protected UIController uiController;

    

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
        MacrosFrame aux = new MacrosFrame(uiController); 
        
        
        aux.setLocation(500, 250);
        aux.setVisible(true);
    }
}