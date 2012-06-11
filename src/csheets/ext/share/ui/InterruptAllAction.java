/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package csheets.ext.share.ui;

import csheets.ext.share.PageSharingController;
import csheets.ui.ctrl.BaseAction;
import csheets.ui.ctrl.UIController;
import java.awt.event.ActionEvent;

/**
 *
 * @author Tiago
 */
public class InterruptAllAction extends BaseAction {

    private InterruptFrame intFrame;

    public InterruptAllAction(UIController uiController, boolean allHosts) {
        intFrame = new InterruptFrame(uiController, allHosts);
        intFrame.setVisible(false);
    }

    @Override
    protected String getName() {
            return "Interrupt All...";
        
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        intFrame.setVisible(true);
    }
}