package csheets.ext.html;

import csheets.ext.Extension;
import csheets.ui.ctrl.UIController;
import csheets.ui.ext.UIExtension;

public class ExtensionHTML extends Extension {

    /**
     * The name of the extension
     */
    public static final String NAME = "Export to HTML";

    /**
     * Creates a new Example extension.
     */
    public ExtensionHTML() {
        super(NAME);
    }

    /**
     * Returns the user interface extension of this extension.
     *
     * @param uiController the user interface controller
     * @return a user interface extension, or null if none is provided
     */
    public UIExtension getUIExtension(UIController uiController) {
        return new UIExtensionHTML(this, uiController);
    }
}
