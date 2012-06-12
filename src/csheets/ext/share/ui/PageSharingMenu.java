/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package csheets.ext.share.ui;

import csheets.ext.share.PageSharingController;
import java.awt.event.KeyEvent;

import javax.swing.JMenu;

import csheets.ui.ctrl.UIController;

/**
 * O menu da extensão
 * @author Tiago
 */
public class PageSharingMenu extends JMenu {

    
        private PageSharingController pageSharingController;
        
	/**
	 * Creates a new Page Sharing menu.
	 * @param uiController the user interface controller
	 */
	public PageSharingMenu(UIController uiController) {
		super("Share");
                
		setMnemonic(KeyEvent.VK_Z);

		// Adds font actions
                add(new HostAction(uiController));
		add(new ClientAction(uiController));
                addSeparator();
		add(new DisconnectAction(uiController));
                addSeparator();
                add(new InterruptOneAction(uiController,false));
                add(new InterruptAllAction(uiController,true));
	}	
        
        
}
