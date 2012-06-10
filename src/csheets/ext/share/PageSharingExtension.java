package csheets.ext.share;

import csheets.ext.share.ui.PageSharingUIExtension;
import csheets.ext.Extension;
import csheets.ui.ctrl.UIController;
import csheets.ui.ext.UIExtension;

/**
 * Classe que interliga o Modelo de negócio do programa da extensão de
 * Partilha de Folha
 * @author Tiago
 */

public class PageSharingExtension extends Extension {

	/** The name of the extension */
	public static final String NAME = "Share";

        /** The Page Sharing Controller */
        
	/**
	 * Creates a new Page Sharing extension.
	 */
	public PageSharingExtension() {
		super(NAME);
	}
	
	/**
	 * Returns the user interface extension of this extension.
	 * @param uiController the user interface controller
	 * @return a user interface extension, or null if none is provided
	 */
	public UIExtension getUIExtension(UIController uiController) {
		return new PageSharingUIExtension(this, uiController);
	}
}
