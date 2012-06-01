package csheets.ui.ctrl;

import csheets.ui.MacrosFrame;
import java.awt.event.ActionEvent;



public class MacroAction extends BaseAction {
    
    MacrosFrame aux = new MacrosFrame();
    
public MacroAction() {

}
  
    protected UIController uiController;


    public MacroAction(UIController uiController) {
        this.uiController = uiController;
    }

    protected String getName() {
        return "Criação de Macros";
    }

    protected void defineProperties() {
    }


    public void actionPerformed(ActionEvent event) {
        aux.setVisible(true);
        
    }
}

