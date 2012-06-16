package csheets.ext.html;

import csheets.ui.ctrl.UIController;
import java.awt.event.KeyEvent;
import javax.swing.JMenu;

public class HTMLMenu extends JMenu{
    /**
	 * Creates a new example menu.
	 * @param uiController the user interface controller
	 */
	public HTMLMenu(UIController uiController) {
		super("Export to HTML");
		setMnemonic(KeyEvent.VK_E);

		// Adds font actions
		add(new HTMLAction(uiController));
	}
}
