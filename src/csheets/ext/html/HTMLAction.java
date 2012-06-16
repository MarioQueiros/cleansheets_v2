package csheets.ext.html;

import csheets.core.Spreadsheet;
import csheets.core.Workbook;
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
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

public class HTMLAction extends BaseAction {

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
            //Handle open button action.
            JFileChooser fc = new JFileChooser();

            FileNameExtensionFilter filter = new FileNameExtensionFilter("HyperText Markup Language (*.htm; *.html)", "htm", "html");
            fc.setFileFilter(filter);
            fc.setAcceptAllFileFilterUsed(false);
            fc.setMultiSelectionEnabled(false);

            int returnVal = fc.showSaveDialog(null);

            if (returnVal == JFileChooser.APPROVE_OPTION) {
                File file = fc.getSelectedFile();

                String path = fc.getSelectedFile().getAbsolutePath();
                String[] auxPath = path.split("\\.");

                if ((auxPath[0].length() == path.length())) {
                    path = path + ".html";
                } else {
                    if (auxPath[auxPath.length - 1].equals("html") || auxPath[auxPath.length - 1].equals("htm")) {
                    } else {
                        path = path + ".html";
                    }
                }

                Spreadsheet sheet = uiController.getActiveSpreadsheet();
                Workbook work = uiController.getActiveWorkbook();
                String[] files = new String[work.getSpreadsheetCount()];

                files[0]=path;
                for (int p = 1; p < work.getSpreadsheetCount(); p++) {
                    auxPath = path.split("\\.");
                    String auxPath1 = "";
                    for (int k = 0; k < auxPath.length - 1; k++) {
                        if (k != 0) {
                            auxPath1 += "." + auxPath[k];
                        } else {
                            auxPath1 += auxPath[k];
                        }
                    }
                    auxPath1 += (p + 1) + "." + auxPath[auxPath.length - 1];

                    files[p] = auxPath1;
                }

                for (int m = 0; m < files.length; m++) {
                    sheet = work.getSpreadsheet(m);
                    OutputStream stream = null;
                    try {
                        stream = new FileOutputStream(files[m]);
                    } catch (FileNotFoundException ex) {
                    }
                    String[] columns = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M",
                        "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z", "AA", "AB", "AC", "AD", "AE", "AF", "AG",
                        "AH", "AI", "AJ", "AK", "AL", "AM", "AN", "AO", "AP", "AQ", "AR", "AS", "AT", "AU", "AV", "AW", "AX", "AY", "AZ"};

                    PrintWriter writer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(stream)));


                    StylableCell stylableCell;

                    writer.print("<!DOCTYPE html PUBLIC '-//W3C//DTD XHTML 1.0 Strict//EN' 'http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd'><html xmlns='http://www.w3.org/1999/xhtml' lang='en'><head><style type='text/css'>"
                            + ".tooltip {"
                            + "border-bottom: 1px dotted #000000; outline: none;"
                            + "cursor: help; text-decoration: none;"
                            + "position: relative;"
                            + "}"
                            + ".tooltip span {"
                            + "margin-left: -999em;"
                            + "position: absolute;"
                            + "}"
                            + ".tooltip:hover span {"
                            + "border-radius: 5px 5px; -moz-border-radius: 5px; -webkit-border-radius: 5px;"
                            + "box-shadow: 5px 5px 5px rgba(0, 0, 0, 0.1); -webkit-box-shadow: 5px 5px rgba(0, 0, 0, 0.1); -moz-box-shadow: 5px 5px rgba(0, 0, 0, 0.1);"
                            + "font-family: Calibri, Tahoma, Geneva, sans-serif;"
                            + "position: absolute; left: 1em; top: 2em; z-index: 99;"
                            + "margin-left: 0; width: 250px;"
                            + "}"
                            + ".classic { padding: 0.8em 1em; }"
                            + ".classic {background: #B8CFE5; border: 1px solid #000000; }"
                            + "</style><title>CleanSheets - "+ sheet.getTitle() +"</title ><meta http-equiv='content-type' content='text/html; charset=utf-8' /></head ><body><center><h1>"+sheet.getTitle()+"</h1></center><br /><table border='1' width='2600'><thead><tr><td width='30' align='center'></td>");

                    for (int j = 0; j < 52; j++) {
                        writer.print("<td width='50' align='center'>" + columns[j] + "</td>");
                    }
                    writer.print("</tr></tr></thead><tbody>");
                    for (int i = 0; i < 128; i++) {
                        writer.print("\n<tr><td width='30' align='center'>" + (i + 1) + "</td>");
                        for (int j = 0; j < 52; j++) {
                            stylableCell = (StylableCell) sheet.getCell(j, i).getExtension(StyleExtension.NAME);

                            String fcolor = convertRGBtoHEX(String.valueOf(stylableCell.getForegroundColor().getRed()), String.valueOf(stylableCell.getForegroundColor().getGreen()), String.valueOf(stylableCell.getForegroundColor().getBlue()));
                            String bcolor = convertRGBtoHEX(String.valueOf(stylableCell.getBackgroundColor().getRed()), String.valueOf(stylableCell.getBackgroundColor().getGreen()), String.valueOf(stylableCell.getBackgroundColor().getBlue()));
                            String font = stylableCell.getFont().getName();
                            int size = stylableCell.getFont().getSize();
                            int type = stylableCell.getFont().getStyle();

                            String aux = "";

                            if (!fcolor.equals("333333")) {
                                if (!bcolor.equals("ffffff")) {
                                    switch (type) {
                                        case Font.BOLD:
                                            if (sheet.getCell(j, i).getContent().startsWith("#") || sheet.getCell(j, i).getContent().startsWith("=")) {
                                                aux = "<td width='50' bgcolor='#" + bcolor + "'><font face='" + font + "'><a style='color: #" + fcolor + "' class='tooltip' href='#'><b>" + sheet.getCell(j, i).getValue() + "</b><span class='classic'><center>" + sheet.getCell(j, i).getContent() + "</center></span></a></font></td>";
                                            } else {
                                                aux = "<td width='50' bgcolor='#" + bcolor + "'><font face='" + font + "' color='#" + fcolor + "'><b>" + sheet.getCell(j, i).getValue() + "</b></font></td>";
                                            }
                                            break;
                                        case Font.ITALIC:
                                            if (sheet.getCell(j, i).getContent().startsWith("#") || sheet.getCell(j, i).getContent().startsWith("=")) {
                                                aux = "<td width='50' bgcolor='#" + bcolor + "'><font face='" + font + "'><a style='color: #" + fcolor + "' class='tooltip' href='#'><i>" + sheet.getCell(j, i).getValue() + "<i><span class='classic'><center>" + sheet.getCell(j, i).getContent() + "</center></span></a></font></td>";
                                            } else {
                                                aux = "<td width='50' bgcolor='#" + bcolor + "'><font face='" + font + "' color='#" + fcolor + "'><i>" + sheet.getCell(j, i).getValue() + "<i></font></td>";
                                            }
                                            break;
                                        case Font.PLAIN:
                                            if (sheet.getCell(j, i).getContent().startsWith("#") || sheet.getCell(j, i).getContent().startsWith("=")) {
                                                aux = "<td width='50' bgcolor='#" + bcolor + "'><font face='" + font + "'><a  style='color: #" + fcolor + "'class='tooltip' href='#'>" + sheet.getCell(j, i).getValue() + "<span class='classic'><center>" + sheet.getCell(j, i).getContent() + "</center></span></a></font></td>";
                                            } else {
                                                aux = "<td width='50' bgcolor='#" + bcolor + "'><font face='" + font + "' color='#" + fcolor + "'>" + sheet.getCell(j, i).getValue() + "</font></td>";
                                            }
                                            break;
                                        case Font.BOLD | Font.ITALIC:
                                            if (sheet.getCell(j, i).getContent().startsWith("#") || sheet.getCell(j, i).getContent().startsWith("=")) {
                                                aux = "<td width='50' bgcolor='#" + bcolor + "'><font face='" + font + "'><a style='color: #" + fcolor + "' class='tooltip' href='#'><b><i>" + sheet.getCell(j, i).getValue() + "</i></b><span class='classic'><center>" + sheet.getCell(j, i).getContent() + "</center></span></a></font></td>";
                                            } else {
                                                aux = "<td width='50' bgcolor='#" + bcolor + "'><font face='" + font + "' color='#" + fcolor + "'><b><i>" + sheet.getCell(j, i).getValue() + "</i></b></font></td>";
                                            }
                                            break;
                                    }
                                } else {
                                    switch (type) {
                                        case Font.BOLD:
                                            if (sheet.getCell(j, i).getContent().startsWith("#") || sheet.getCell(j, i).getContent().startsWith("=")) {
                                                aux = "<td width='50'><font face='" + font + "'><a style='color: #" + fcolor + "' class='tooltip' href='#'><b>" + sheet.getCell(j, i).getValue() + "</b><span class='classic'><center>" + sheet.getCell(j, i).getContent() + "</center></span></a></font></td>";
                                            } else {
                                                aux = "<td width='50'><font face='" + font + "' color='#" + fcolor + "'><b>" + sheet.getCell(j, i).getValue() + "</b></font></td>";
                                            }
                                            break;
                                        case Font.ITALIC:
                                            if (sheet.getCell(j, i).getContent().startsWith("#") || sheet.getCell(j, i).getContent().startsWith("=")) {
                                                aux = "<td width='50'><font face='" + font + "'><a style='color: #" + fcolor + "' class='tooltip' href='#'><i>" + sheet.getCell(j, i).getValue() + "</i><span class='classic'><center>" + sheet.getCell(j, i).getContent() + "</center></span></a></font></td>";
                                            } else {
                                                aux = "<td width='50'><font face='" + font + "' color='#" + fcolor + "'><i>" + sheet.getCell(j, i).getValue() + "</i></font></td>";
                                            }
                                            break;
                                        case Font.PLAIN:
                                            if (sheet.getCell(j, i).getContent().startsWith("#") || sheet.getCell(j, i).getContent().startsWith("=")) {
                                                aux = "<td width='50'><font face='" + font + "'><a style='color: #" + fcolor + "' class='tooltip' href='#'>" + sheet.getCell(j, i).getValue() + "<span class='classic'><center>" + sheet.getCell(j, i).getContent() + "</center></span></a></font></td>";
                                            } else {
                                                aux = "<td width='50'><font face='" + font + "' color='#" + fcolor + "'>" + sheet.getCell(j, i).getValue() + "</font></td>";
                                            }
                                            break;
                                        case Font.BOLD | Font.ITALIC:
                                            if (sheet.getCell(j, i).getContent().startsWith("#") || sheet.getCell(j, i).getContent().startsWith("=")) {
                                                aux = "<td width='50'><font face='" + font + "'><a style='color: #" + fcolor + "' class='tooltip' href='#'><b><i>" + sheet.getCell(j, i).getValue() + "</i></b><span class='classic'><center>" + sheet.getCell(j, i).getContent() + "</center></span></a></font></td>";
                                            } else {
                                                aux = "<td width='50'><font face='" + font + "' color='#" + fcolor + "'><b><i>" + sheet.getCell(j, i).getValue() + "</i></b></font></td>";
                                            }
                                            break;
                                    }
                                }
                            } else {
                                if (!bcolor.equals("ffffff")) {
                                    switch (type) {
                                        case Font.BOLD:
                                            if (sheet.getCell(j, i).getContent().startsWith("#") || sheet.getCell(j, i).getContent().startsWith("=")) {
                                                aux = "<td width='50' bgcolor='#" + bcolor + "'><font face='" + font + "'><a style='color: #000000' class='tooltip' href='#'><b>" + sheet.getCell(j, i).getValue() + "</b><span class='classic'><center>" + sheet.getCell(j, i).getContent() + "</center></span></a></font></td>";
                                            } else {
                                                aux = "<td width='50' bgcolor='#" + bcolor + "'><font face='" + font + "'><b>" + sheet.getCell(j, i).getValue() + "</b></font></td>";
                                            }
                                            break;
                                        case Font.ITALIC:
                                            if (sheet.getCell(j, i).getContent().startsWith("#") || sheet.getCell(j, i).getContent().startsWith("=")) {
                                                aux = "<td width='50' bgcolor='#" + bcolor + "'><font face='" + font + "'><a style='color: #000000' class='tooltip' href='#'><i>" + sheet.getCell(j, i).getValue() + "</i><span class='classic'><center>" + sheet.getCell(j, i).getContent() + "</center></span></a></font></td>";
                                            } else {
                                                aux = "<td width='50' bgcolor='#" + bcolor + "'><font face='" + font + "'><i>" + sheet.getCell(j, i).getValue() + "</i></font></td>";
                                            }
                                            break;
                                        case Font.PLAIN:
                                            if (sheet.getCell(j, i).getContent().startsWith("#") || sheet.getCell(j, i).getContent().startsWith("=")) {
                                                aux = "<td width='50' bgcolor='#" + bcolor + "'><font face='" + font + "'><a style='color: #000000' class='tooltip' href='#'>" + sheet.getCell(j, i).getValue() + "<span class='classic'><center>" + sheet.getCell(j, i).getContent() + "</center></span></a></font></td>";
                                            } else {
                                                aux = "<td width='50' bgcolor='#" + bcolor + "'><font face='" + font + "'>" + sheet.getCell(j, i).getValue() + "</font></td>";
                                            }
                                            break;
                                        case Font.BOLD | Font.ITALIC:
                                            if (sheet.getCell(j, i).getContent().startsWith("#") || sheet.getCell(j, i).getContent().startsWith("=")) {
                                                aux = "<td width='50' bgcolor='#" + bcolor + "'><font face='" + font + "'><a style='color: #000000' class='tooltip' href='#'><b><i>" + sheet.getCell(j, i).getValue() + "</i></b><span class='classic'><center>" + sheet.getCell(j, i).getContent() + "</center></span></a></font></td>";
                                            } else {
                                                aux = "<td width='50' bgcolor='#" + bcolor + "'><font face='" + font + "'><b><i>" + sheet.getCell(j, i).getValue() + "</i></b></font></td>";
                                            }
                                            break;
                                    }
                                } else {
                                    switch (type) {
                                        case Font.BOLD:
                                            if (sheet.getCell(j, i).getContent().startsWith("#") || sheet.getCell(j, i).getContent().startsWith("=")) {
                                                aux = "<td width='50'><font face='" + font + "'><a style='color: #000000' class='tooltip' href='#'><b>" + sheet.getCell(j, i).getValue() + "</b><span class='classic'><center>" + sheet.getCell(j, i).getContent() + "</center></span></a></font></td>";
                                            } else {
                                                aux = "<td width='50'><font face='" + font + "'><b>" + sheet.getCell(j, i).getValue() + "</b></font></td>";
                                            }
                                            break;
                                        case Font.ITALIC:
                                            if (sheet.getCell(j, i).getContent().startsWith("#") || sheet.getCell(j, i).getContent().startsWith("=")) {
                                                aux = "<td width='50'><font face='" + font + "'><a style='color: #000000' class='tooltip' href='#'><i>" + sheet.getCell(j, i).getValue() + "</i><span class='classic'><center>" + sheet.getCell(j, i).getContent() + "</center></span></a></font></td>";
                                            } else {
                                                aux = "<td width='50'><font face='" + font + "'><i>" + sheet.getCell(j, i).getValue() + "</i></font></td>";
                                            }
                                            break;
                                        case Font.PLAIN:
                                            if (sheet.getCell(j, i).getContent().startsWith("#") || sheet.getCell(j, i).getContent().startsWith("=")) {
                                                aux = "<td width='50'><font face='" + font + "'><a style='color: #000000' class='tooltip' href='#'>" + sheet.getCell(j, i).getValue() + "<span class='classic'><center>" + sheet.getCell(j, i).getContent() + "</center></span></a></font></td>";
                                            } else {
                                                aux = "<td width='50'><font face='" + font + "'>" + sheet.getCell(j, i).getValue() + "</font></td>";
                                            }
                                            break;
                                        case Font.BOLD | Font.ITALIC:
                                            if (sheet.getCell(j, i).getContent().startsWith("#") || sheet.getCell(j, i).getContent().startsWith("=")) {
                                                aux = "<td width='50'><font face='" + font + "'><a style='color: #000000' class='tooltip' href='#'><b><i>" + sheet.getCell(j, i).getValue() + "</i></b><span class='classic'><center>" + sheet.getCell(j, i).getContent() + "</center></span></a></font></td>";
                                            } else {
                                                aux = "<td width='50'><font face='" + font + "'><b><i>" + sheet.getCell(j, i).getValue() + "</i></b></font></td>";
                                            }
                                            break;
                                    }
                                }
                            }

                            int valign = stylableCell.getVerticalAlignment();
                            int halign = stylableCell.getHorizontalAlignment();

                            if (valign == 0) {
                                aux = aux.replace("<td width='50'", "<td width='50' valign='middle'");
                            } else if (valign == 1) {
                                aux = aux.replace("<td width='50'", "<td width='50' valign='top'");
                            } else if (valign == 3) {
                                aux = aux.replace("<td width='50'", "<td width='50' valign='bottom'");
                            }

                            if (halign == 0) {
                                aux = aux.replace("<td width='50' ", "<td width='50' align='center' ");
                            } else if (halign == 2) {
                                aux = aux.replace("<td width='50' ", "<td width='50' align='left' ");
                            } else if (halign == 4) {
                                aux = aux.replace("<td width='50' ", "<td width='50' align='right' ");
                            }

                            writer.print(aux);

                        }
                        writer.print("</tr>");

                    }
                    writer.print("</tbody></table></body></html>");
                    writer.close();
                    stream.close();
                }

            }
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
