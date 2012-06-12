/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package csheets.ext.html;

import csheets.ui.ctrl.BaseAction;
import csheets.ui.ctrl.UIController;
import java.awt.event.ActionEvent;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author MpApQ
 */
public class HTMLAction extends BaseAction {

    /**
     * The user interface controller
     */
    protected UIController uiController;

    /**
     * Creates a new font action.
     *
     * @param uiController the user interface controller
     */
    public HTMLAction(UIController uiController) {
        this.uiController = uiController;
    }

    protected String getName() {
        return "Export to HTML";
    }

    protected void defineProperties() {
    }

    /**
     * Lets the user select a font from a chooser. Then applies the font to the
     * selected cells in the focus owner table.
     *
     * @param event the event that was fired
     */
    public void actionPerformed(ActionEvent event) {
        OutputStream stream = null;
        try {
            stream = new FileOutputStream("html.htm");
        } catch (FileNotFoundException ex) {
            Logger.getLogger(HTMLAction.class.getName()).log(Level.SEVERE, null, ex);
        }
        PrintWriter writer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(stream)));
        
        writer.print("<html lang='en' xmlns='http://www.w3.org/1999/xhtml'><head><META http-equiv='Content-Type' content='text/html; charset=ISO-8859-1'><title>CleanSheets</title><meta name='description' content='Receitas Low Cost'><meta http-equiv='Content-Language' content='pt-pt'><meta http-equiv='Content-Type' content='text/html; charset=windows-1252'></head><body><table><thead><tr><td width='100'></td><td width='100'>A</td><td width='100'>B</td><td width='100'>C</td><td width='100'>D</td><td width='100'>E</td><td width='100'>F</td><td width='100'>G</td><td width='100'>H</td><td width='100'>I</td><td width='100'>J</td><td width='100'>K</td><td width='100'>L</td><td width='100'>M</td><td width='100'>N</td><td width='100'>O</td><td width='100'>P</td><td width='100'>Q</td><td width='100'>R</td><td width='100'>S</td><td width='100'>T</td><td width='100'>U</td><td width='100'>V</td><td width='100'>W</td><td width='100'>X</td><td width='100'>Y</td><td width='100'>Z</td><td width='100'>AA</td><td width='100'>AB</td><td width='100'>AC</td><td width='100'>AD</td><td width='100'>AE</td><td width='100'>AF</td><td width='100'>AG</td><td width='100'>AH</td><td width='100'>AI</td><td width='100'>AJ</td><td width='100'>AK</td><td width='100'>AL</td><td width='100'>AM</td><td width='100'>AN</td><td width='100'>AO</td><td width='100'>AP</td><td width='100'>AQ</td><td width='100'>AR</td><td width='100'>AS</td><td width='100'>AT</td><td width='100'>AU</td><td width='100'>AV</td><td width='100'>AW</td><td width='100'>AX</td><td width='100'>AY</td><td width='100'>AZ</td></tr></thead><tbody>");
        for(int i=0; i<128;i++)
        {
            writer.print("<tr><td>"+(i+1)+"</td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td></tr>");
        }
        writer.print("");
        writer.print("</tbody></table></body></html>");
        
    }
}
