package csheets.bd;

import java.awt.event.KeyEvent;

import javax.swing.JMenu;

import csheets.ui.ctrl.UIController;

public class BaseDadosMenu extends JMenu {

	/**
	 * Creates a new example menu.
	 * @param uiController the user interface controller
	 */
	public BaseDadosMenu(UIController uiController) {
		super("Base de dados");
		setMnemonic(KeyEvent.VK_P);

		// Adds font actions
		add(new BaseDadosAction(uiController));
                add(new BaseDadosAddRefreshAction(uiController));
                addSeparator();      
                add(new BaseDadosLoadAction(uiController));
                addSeparator();
                add(new BaseDadosSyncAction(uiController));
                add(new StopSync(uiController));
	}	
}
