package csheets.bd;

import csheets.ext.Extension;
import csheets.ui.ctrl.UIController;
import csheets.ui.ext.UIExtension;

public class ExtensionBaseDados extends Extension {

	/** The name of the extension */
	public static final String NAME = "Base de dados";

	/**
	 * Creates a new Example extension.
	 */
	public ExtensionBaseDados() {
		super(NAME);
	}
	
	/**
	 * Returns the user interface extension of this extension.
	 * @param uiController the user interface controller
	 * @return a user interface extension, or null if none is provided
	 */
	public UIExtension getUIExtension(UIController uiController) {
		return new UIExtensionBaseDados(this, uiController);
	}
}
