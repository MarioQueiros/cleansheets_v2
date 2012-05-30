package csheets.io;

import csheets.core.Cell;
import csheets.core.Spreadsheet;
import csheets.core.Workbook;
import csheets.ext.style.StylableCell;
import csheets.ext.style.StylableSpreadsheet;
import csheets.ext.style.StyleExtension;
import java.awt.Color;
import java.awt.Font;
import java.io.*;
import javax.swing.border.Border;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.*;

/**
 *
 * @author MpApQ
 */
public class XMLCodec implements Codec {

    String[] columns = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M",
        "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z", "AA", "AB", "AC", "AD", "AE", "AF", "AG",
        "AH", "AI", "AJ", "AK", "AL", "AM", "AN", "AO", "AP", "AQ", "AR", "AS", "AT", "AU", "AV", "AW", "AX", "AY", "AZ"};

    public XMLCodec() {
    }

    @Override
    public Workbook read(InputStream stream) throws IOException, ClassNotFoundException {
        String[][] content = null;

        try {
            File file = new File("C:\\Users\\MpApQ\\Desktop\\a.xml");
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(file);
            doc.getDocumentElement().normalize();
            NodeList nodeLst = doc.getElementsByTagName("row");
            String rowID = "", columnID = "", contentStr = "";

            for (int s = 0; s < nodeLst.getLength(); s++) {
                Node fstNode = nodeLst.item(s);

                if (fstNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element fstElmnt = (Element) fstNode;
                    NamedNodeMap attrs = fstNode.getAttributes();

                    for (int i = 0; i < attrs.getLength(); i++) {
                        Attr attribute = (Attr) attrs.item(i);
                        rowID = attribute.getValue();
                    }

                    NodeList fstNmElmntLst = fstElmnt.getElementsByTagName("column");
                    int l = 0;
                    for (int i = 0; i < fstNmElmntLst.getLength(); i++) {
                        Node fstNodeAtri = fstNmElmntLst.item(i);
                        NamedNodeMap attrsAtr = fstNodeAtri.getAttributes();

                        for (int j = 0; j < attrsAtr.getLength(); j++) {
                            Attr attribute = (Attr) attrsAtr.item(j);
                            columnID = attribute.getValue();
                        }

                        NodeList fstNmElmntLstCont = fstElmnt.getElementsByTagName("content");
                        Element fstNmElmnt = (Element) fstNmElmntLstCont.item(l);
                        NodeList fstNm = fstNmElmnt.getChildNodes();
                        contentStr = ((Node) fstNm.item(l)).getNodeValue();

                        Font font;
                        Border border;
                        int valign, halign;
                        Color fore, back;
                        String aux[];
                        String aux1[];
                        String aux2[];
                        fstNmElmntLstCont = fstElmnt.getElementsByTagName("font");
                        fstNmElmnt = (Element) fstNmElmntLstCont.item(l);
                        fstNm = fstNmElmnt.getChildNodes();
                        aux = ((Node) fstNm.item(l)).getNodeValue().split("=");
                        aux1 = aux[2].split(",");
                        String fname = aux1[0];
                        aux2 = aux[3].split(",");
                        String style = aux2[0];
                        String size = aux[4].substring(0, aux[4].length() - 1);

                        font = new Font(fname, Integer.parseInt(style), Integer.parseInt(size));
                        
                        
                        
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Workbook(content);
    }

    @Override
    public void write(Workbook workbook, OutputStream stream) throws IOException {
        PrintWriter writer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(stream)));
        Spreadsheet sheet = workbook.getSpreadsheet(0);
        StylableCell stylableCell;
        StylableSpreadsheet st = (StylableSpreadsheet) sheet.getExtension(StyleExtension.NAME);
        writer.print("<?xml version='1.0' encoding='UTF-8'?>\n");
        writer.print("<cleanSheets>\n");
        for (int row = 0; row < sheet.getRowCount(); row++) {
            writer.print("\t<row id='" + row + "' rowHeight='" + st.getRowHeight(row) + "'>\n");
            for (int column = 0; column <= sheet.getColumnCount(); column++) {
                String aux = sheet.getCell(column, row).getContent();
                stylableCell = (StylableCell) sheet.getCell(column, row).getExtension(StyleExtension.NAME);
                if (aux.trim() != "") {
                    writer.print("\t\t<column id='" + columns[column] + "' width='" + st.getColumnWidth(0) + "'>\n");
                    writer.print("\t\t\t<content>" + sheet.getCell(column, row).getContent() + "</content>\n");
                    writer.print("\t\t\t<font>" + stylableCell.getFont() + "</font>\n");
                    writer.print("\t\t\t<valign>" + stylableCell.getVerticalAlignment() + "</valign>\n");
                    writer.print("\t\t\t<halign>" + stylableCell.getHorizontalAlignment() + "</halign>\n");
                    writer.print("\t\t\t<foreColor>" + stylableCell.getForegroundColor() + "</foreColor>\n");
                    writer.print("\t\t\t<backColor>" + stylableCell.getBackgroundColor() + "</backColor>\n");
                    writer.print("\t\t\t<border>" + stylableCell.getBorder().getBorderInsets(null) + "</border>\n");
                    writer.print("\t\t</column>\n");
                }
            }
            writer.print("\t</row>\n\n");
        }
        writer.print("</cleanSheets>");
        writer.close();
        stream.close();
    }
}
