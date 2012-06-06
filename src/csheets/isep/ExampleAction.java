package csheets.isep;

import java.awt.event.ActionEvent;

import javax.swing.JOptionPane;

import csheets.ui.ctrl.BaseAction;
import csheets.ui.ctrl.UIController;

public class ExampleAction extends BaseAction {

	/** The user interface controller */
	protected UIController uiController;

	/**
	 * Creates a new font action.
	 * @param uiController the user interface controller
	 */
	public ExampleAction(UIController uiController) {
		this.uiController = uiController;
	}

	protected String getName() {
		return "Example...";
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
		JOptionPane.showConfirmDialog(null, "Seleccionou o sdgfsdgsffsdg. Vou alterar a celula A1...");
		
		// Vamos exemplificar como se acede ao modelo de dominio (o workbook)
		try {
			this.uiController.getActiveSpreadsheet().getCell(0, 0).setContent("Alterada");
		} catch (Exception ex) {
			// para ja ignoramos a excepcao
		}
	}
}
