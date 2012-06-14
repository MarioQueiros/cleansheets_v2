package csheets.bd;

import javax.swing.Icon;
import javax.swing.JMenu;

import csheets.ext.Extension;
import csheets.ui.ctrl.UIController;
import csheets.ui.ext.UIExtension;

public class UIExtensionBaseDados extends UIExtension {

	/** The icon to display with the extension's name */
	private Icon icon;

	/** A menu that provides editing of style */
	private BaseDadosMenu menu;

	public UIExtensionBaseDados(Extension extension, UIController uiController) {
		super(extension, uiController);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * Returns an icon to display with the extension's name.
	 * @return an icon with style
	 */
	public Icon getIcon() {
		return null;
	}

	/**
	 * Returns a menu that provides editing of style.
	 * @return a JMenu component
	 */
	public JMenu getMenu() {
		if (menu == null)
			menu = new BaseDadosMenu(uiController);
		return menu;
	}

}
