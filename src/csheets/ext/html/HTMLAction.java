/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package csheets.ext.html;

import csheets.core.Spreadsheet;
import csheets.ext.style.StylableCell;
import csheets.ext.style.StyleExtension;
import csheets.ui.ctrl.BaseAction;
import csheets.ui.ctrl.UIController;
import java.awt.Color;
import java.awt.Font;
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
        try {
            OutputStream stream = null;
            try {
                stream = new FileOutputStream("html.htm");
            } catch (FileNotFoundException ex) {
            }
            String[] columns = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M",
                "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z", "AA", "AB", "AC", "AD", "AE", "AF", "AG",
                "AH", "AI", "AJ", "AK", "AL", "AM", "AN", "AO", "AP", "AQ", "AR", "AS", "AT", "AU", "AV", "AW", "AX", "AY", "AZ"};

            PrintWriter writer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(stream)));

            Spreadsheet sheet = uiController.getActiveSpreadsheet();

            StylableCell stylableCell;

            writer.print("<html lang='en' xmlns='http://www.w3.org/1999/xhtml' align='center'><head><META http-equiv='Content-Type' content='text/html; charset=ISO-8859-1' align='center'><title>CleanSheets</title><meta name='description' content='Receitas Low Cost' align='center'><meta http-equiv='Content-Language' content='pt-pt' align='center'><meta http-equiv='Content-Type' content='text/html; charset=windows-1252' align='center'></head><body><table border='1' width='2600'><thead><tr><td width='30' align='center'></td>");

            for (int j = 0; j < 52; j++) {
                writer.print("<td width='50' align='center'>" + columns[j] + "</td>");
            }
            writer.print("</tr></tr></thead><tbody>");
            for (int i = 0; i < 128; i++) {
                writer.print("\n<tr height=100><td width='30' align='center'>" + (i + 1) + "</td>");
                for (int j = 0; j < 52; j++) {
                    stylableCell = (StylableCell) sheet.getCell(j, i).getExtension(StyleExtension.NAME);

                    String fcolor = convertRGBtoHEX(String.valueOf(stylableCell.getForegroundColor().getRed()), String.valueOf(stylableCell.getForegroundColor().getGreen()), String.valueOf(stylableCell.getForegroundColor().getBlue()));
                    String bcolor = convertRGBtoHEX(String.valueOf(stylableCell.getBackgroundColor().getRed()), String.valueOf(stylableCell.getBackgroundColor().getGreen()), String.valueOf(stylableCell.getBackgroundColor().getBlue()));
                    String font = stylableCell.getFont().getFontName();

                    int type = stylableCell.getFont().getStyle();

                    if (!fcolor.equals("333333")) {
                        if (!bcolor.equals("ffffff")) {
                            switch (type) {
                                case Font.BOLD:
                                    writer.print("<td width='50' bgcolor=#'" + bcolor + "'><font face='" + font + "' color='" + fcolor + "'><b>" + sheet.getCell(j, i).getContent() + "</b></color></td>");
                                    break;
                                case Font.ITALIC:
                                    writer.print("<td width='50' bgcolor=#'" + bcolor + "'><font face='" + font + "' color='" + fcolor + "'><i>" + sheet.getCell(j, i).getContent() + "<i></color></td>");
                                    break;
                                case Font.PLAIN:
                                    writer.print("<td width='50' bgcolor=#'" + bcolor + "'><font face='" + font + "' color='" + fcolor + "'>" + sheet.getCell(j, i).getContent() + "</color></td>");
                                    break;
                                case Font.BOLD | Font.ITALIC:
                                    writer.print("<td width='50' bgcolor=#'" + bcolor + "'><font face='" + font + "' color='" + fcolor + "'><b><i>" + sheet.getCell(j, i).getContent() + "</i></b></color></td>");
                                    break;
                            }
                        } else {
                            switch (type) {
                                case Font.BOLD:
                                    writer.print("<td width='50'><font face='" + font + "' color='#" + fcolor + "'><b>" + sheet.getCell(j, i).getContent() + "</b></color></td>");
                                    break;
                                case Font.ITALIC:
                                    writer.print("<td width='50'><font face='" + font + "' color='#" + fcolor + "'><i>" + sheet.getCell(j, i).getContent() + "</i></color></td>");
                                    break;
                                case Font.PLAIN:
                                    writer.print("<td width='50'><font face='" + font + "' color='#" + fcolor + "'>" + sheet.getCell(j, i).getContent() + "</color></td>");
                                    break;
                                case Font.BOLD | Font.ITALIC:
                                    writer.print("<td width='50'><font face='" + font + "' color='#" + fcolor + "'><b><i>" + sheet.getCell(j, i).getContent() + "</i></b></color></td>");
                                    break;
                            }
                        }
                    } else {
                        if (!bcolor.equals("ffffff")) {
                            switch (type) {
                                case Font.BOLD:
                                    writer.print("<td width='50' bgcolor='" + bcolor + "'><font face='" + font + "'><b>" + sheet.getCell(j, i).getContent() + "</b></font></td>");
                                    break;
                                case Font.ITALIC:
                                    writer.print("<td width='50' bgcolor='" + bcolor + "'><font face='" + font + "'><i>" + sheet.getCell(j, i).getContent() + "</i></font></td>");
                                    break;
                                case Font.PLAIN:
                                    writer.print("<td width='50' bgcolor='" + bcolor + "'><font face='" + font + "'>" + sheet.getCell(j, i).getContent() + "</font></td>");
                                    break;
                                case Font.BOLD | Font.ITALIC:
                                    writer.print("<td width='50' bgcolor='" + bcolor + "'><font face='" + font + "'><b><i>" + sheet.getCell(j, i).getContent() + "</i></b></font></td>");
                                    break;
                            }
                        } else {
                            switch (type) {
                                case Font.BOLD:
                                    writer.print("<td width='50'><font face='" + font + "'><b>" + sheet.getCell(j, i).getContent() + "</b></font></td>");
                                    break;
                                case Font.ITALIC:
                                    writer.print("<td width='50'><font face='" + font + "'><i>" + sheet.getCell(j, i).getContent() + "</i></font></td>");
                                    break;
                                case Font.PLAIN:
                                    writer.print("<td width='50' valign=0><font face='" + font + "'>" + sheet.getCell(j, i).getContent() + "</font></td>");
                                    break;
                                case Font.BOLD | Font.ITALIC:
                                    writer.print("<td width='50'><font face='" + font + "'><b><i>" + sheet.getCell(j, i).getContent() + "</i></b></font></td>");
                                    break;
                            }
                        }
                    }
                }
                writer.print("</tr>");

            }
            writer.print("</tbody></table></body></html>");
            writer.close();
            stream.close();
        } catch (IOException ex) {
            Logger.getLogger(HTMLAction.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String convertRGBtoHEX(String r, String g, String b) {
        int i = Integer.parseInt(r);
        int j = Integer.parseInt(g);
        int k = Integer.parseInt(b);

        Color c = new Color(i, j, k);

        return Integer.toHexString(c.getRGB() & 0x00ffffff);
    }
}
