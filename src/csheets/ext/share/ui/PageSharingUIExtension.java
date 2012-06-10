package csheets.ext.share.ui;

import javax.swing.Icon;
import javax.swing.JMenu;

import csheets.ext.Extension;
import csheets.ui.ctrl.UIController;
import csheets.ui.ext.UIExtension;


/**
 * A extensão da Interface dedicada à Partilha de Folha
 */
public class PageSharingUIExtension extends UIExtension {

	/** The icon to display with the extension's name */
	private Icon icon;

	/** A menu that provides editing of style */
	private PageSharingMenu menu;

        
	public PageSharingUIExtension(Extension extension, UIController uiController) {
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
			menu = new PageSharingMenu(uiController);
		return menu;
	}

}

