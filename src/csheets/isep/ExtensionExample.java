package csheets.isep;

import csheets.ext.Extension;
import csheets.ui.ctrl.UIController;
import csheets.ui.ext.UIExtension;

public class ExtensionExample extends Extension {

	/** The name of the extension */
	public static final String NAME = "Example";

	/**
	 * Creates a new Example extension.
	 */
	public ExtensionExample() {
		super(NAME);
	}
	
	/**
	 * Returns the user interface extension of this extension.
	 * @param uiController the user interface controller
	 * @return a user interface extension, or null if none is provided
	 */
	public UIExtension getUIExtension(UIController uiController) {
		return new UIExtensionExample(this, uiController);
	}
}
