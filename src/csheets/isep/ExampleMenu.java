package csheets.isep;

import java.awt.event.KeyEvent;

import javax.swing.JMenu;

import csheets.ui.ctrl.UIController;

public class ExampleMenu extends JMenu {

	/**
	 * Creates a new example menu.
	 * @param uiController the user interface controller
	 */
	public ExampleMenu(UIController uiController) {
		super("Example");
		setMnemonic(KeyEvent.VK_E);

		// Adds font actions
		add(new ExampleAction(uiController));
	}	
}
