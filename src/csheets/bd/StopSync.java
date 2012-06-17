/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package csheets.bd;

import csheets.ui.ctrl.BaseAction;
import csheets.ui.ctrl.UIController;
import java.awt.event.ActionEvent;
import javax.swing.JOptionPane;

/**
 *
 * @author joaodias
 */
public class StopSync extends BaseAction {

    /** The user interface controller */
    protected UIController uiController;

    /**
     * Creates a new font action.
     * @param uiController the user interface controller
     */
    public StopSync(UIController uiController) {
        this.uiController = uiController;
    }

    protected String getName() {
        return "Parar a sincronização";
    }

    protected void defineProperties() {
    }

    /**
     * Lets the user select a font from a chooser.
     * Then applies the font to the selected cells in the focus owner table.
     * @param event the event that was fired
     */
    public void actionPerformed(ActionEvent event) {

        // Lets user select a font


        // Vamos exemplificar como se acede ao modelo de dominio (o workbook)
        try {
            if (!SyncSingleton.Instance().getStop()) {
                SyncSingleton.Instance().setStop(true);
                JOptionPane.showMessageDialog(null, "Sincronização cancelada!");
            } else {
                 JOptionPane.showMessageDialog(null, "Não há qualquer sincronização activa!");
            }

        } catch (Exception ex) {
            // para ja ignoramos a excepcao
        }
    }
}
