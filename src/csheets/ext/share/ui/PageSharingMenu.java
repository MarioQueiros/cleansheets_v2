/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package csheets.ext.share.ui;

import csheets.ext.share.PageSharingController;
import java.awt.event.KeyEvent;

import javax.swing.JMenu;

import csheets.ui.ctrl.UIController;

public class PageSharingMenu extends JMenu {

    
        private PageSharingController pageSharingController;
        
	/**
	 * Creates a new example menu.
	 * @param uiController the user interface controller
	 */
	public PageSharingMenu(UIController uiController) {
		super("Share");
                
                pageSharingController = PageSharingController.getInstance();
                
		setMnemonic(KeyEvent.VK_Z);

		// Adds font actions
                add(new HostAction(uiController, pageSharingController));
		add(new ClientAction(uiController, pageSharingController));
		add(new DisconnectAction(uiController, pageSharingController));
	}	
        
        
}
