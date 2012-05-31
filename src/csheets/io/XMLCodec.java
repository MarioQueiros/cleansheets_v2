package csheets.io;

import csheets.core.Cell;
import csheets.core.Spreadsheet;
import csheets.core.Workbook;
import csheets.ext.style.StylableCell;
import csheets.ext.style.StylableSpreadsheet;
import csheets.ext.style.StyleExtension;
import java.awt.Color;
import java.awt.Component;
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
    int MATRIX_WIDTH = columns.length;
    int MATRIX_HEIGHT = 128;

    public XMLCodec() {
    }

    @Override
    public Workbook read(InputStream stream) throws IOException, ClassNotFoundException {
        String[][] content = null;

        try {

            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(stream);
            
            doc.getDocumentElement().normalize();
            NodeList nodeLst = doc.getElementsByTagName("row");
            String rowID = "", columnID = "", contentStr = "";

            content = new String[MATRIX_WIDTH][MATRIX_HEIGHT];



            for (int s = 0; s < nodeLst.getLength(); s++) {
                Node fstNode = nodeLst.item(s);

                if (fstNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element fstElmnt = (Element) fstNode;
                    NamedNodeMap attrs = fstNode.getAttributes();
                    NodeList fstNmElmntLst = fstElmnt.getElementsByTagName("column");
                    for (int i = 0; i < attrs.getLength(); i++) {
                        Attr attribute = (Attr) attrs.item(i);
                        rowID = attribute.getValue();
                    }
                    for (int i = 0; i < fstNmElmntLst.getLength(); i++) {
                        Node fstNodeAtri = fstNmElmntLst.item(i);
                        NamedNodeMap attrsAtr = fstNodeAtri.getAttributes();

                        Attr attribute = (Attr) attrsAtr.item(0);
                        columnID = attribute.getValue();
                        attribute = (Attr) attrsAtr.item(1);
                        String columnWidth = attribute.getValue();

                        NodeList fstNmElmntLstCont = fstElmnt.getElementsByTagName("content");
                        Element fstNmElmnt = (Element) fstNmElmntLstCont.item(0);
                        NodeList fstNm = fstNmElmnt.getChildNodes();
                        contentStr = ((Node) fstNm.item(0)).getTextContent();

                        Font font = null;
                        Border border;
                        int valign, halign;
                        Color fore, back;
                        String aux[];
                        String aux1[];
                        String aux2[];
                        String aux3[];
                        fstNmElmntLstCont = fstElmnt.getElementsByTagName("font");
                        fstNmElmnt = (Element) fstNmElmntLstCont.item(0);
                        fstNm = fstNmElmnt.getChildNodes();
                        aux = ((Node) fstNm.item(0)).getNodeValue().split("=");
                        aux1 = aux[2].split(",");
                        String fname = aux1[0];
                        aux2 = aux[3].split(",");
                        String style = aux2[0];
                        String size = aux[4].substring(0, aux[4].length() - 1);
                        switch (style) {
                            case "plain":
                                font = new Font(fname, Font.PLAIN, Integer.parseInt(size));
                                break;
                            case "bold":
                                font = new Font(fname, Font.BOLD, Integer.parseInt(size));
                                break;
                            case "italic":
                                font = new Font(fname, Font.ITALIC, Integer.parseInt(size));
                                break;
                            case "bolditalic":
                                font = new Font(fname, Font.BOLD | Font.ITALIC, Integer.parseInt(size));
                                break;
                        }

                        fstNmElmntLstCont = fstElmnt.getElementsByTagName("valign");
                        fstNmElmnt = (Element) fstNmElmntLstCont.item(0);
                        fstNm = fstNmElmnt.getChildNodes();
                        valign = Integer.parseInt(((Node) fstNm.item(0)).getNodeValue());
                        System.out.println("valign:" + valign);


                        fstNmElmntLstCont = fstElmnt.getElementsByTagName("halign");
                        fstNmElmnt = (Element) fstNmElmntLstCont.item(0);
                        fstNm = fstNmElmnt.getChildNodes();
                        halign = Integer.parseInt(((Node) fstNm.item(0)).getNodeValue());
                        System.out.println("halign:" + halign);


                        fstNmElmntLstCont = fstElmnt.getElementsByTagName("foreColor");
                        fstNmElmnt = (Element) fstNmElmntLstCont.item(0);
                        fstNm = fstNmElmnt.getChildNodes();
                        aux = ((Node) fstNm.item(0)).getNodeValue().split("=");
                        aux1 = aux[1].split(",");
                        String fr = aux1[0];
                        aux2 = aux[2].split(",");
                        String fg = aux2[0];
                        String fb = aux[3].substring(0, aux[3].length() - 1);
                        fore = new Color(Integer.parseInt(fb), Integer.parseInt(fg), Integer.parseInt(fb));
                        System.out.println("forecolor:" + fr + " " + fg + " " + fb);


                        fstNmElmntLstCont = fstElmnt.getElementsByTagName("backColor");
                        fstNmElmnt = (Element) fstNmElmntLstCont.item(0);
                        fstNm = fstNmElmnt.getChildNodes();
                        aux = ((Node) fstNm.item(0)).getNodeValue().split("=");
                        aux1 = aux[1].split(",");
                        String br = aux1[0];
                        aux2 = aux[2].split(",");
                        String bg = aux2[0];
                        String bb = aux[3].substring(0, aux[3].length() - 1);
                        back = new Color(Integer.parseInt(bb), Integer.parseInt(bg), Integer.parseInt(bb));
                        System.out.println("backcolor:" + br + " " + bg + " " + bb);


                        fstNmElmntLstCont = fstElmnt.getElementsByTagName("border");
                        fstNmElmnt = (Element) fstNmElmntLstCont.item(0);
                        fstNm = fstNmElmnt.getChildNodes();
                        aux = ((Node) fstNm.item(0)).getNodeValue().split("=");
                        aux1 = aux[1].split(",");
                        String top = aux1[0];
                        aux2 = aux[2].split(",");
                        String left = aux2[0];
                        aux3 = aux[3].split(",");
                        String bottom = aux3[0];
                        String right = aux[4].substring(0, aux[4].length() - 1);
                        System.out.println("border:" + top + " " + left + " " + bottom + " " + right);

                        content[s][i] = contentStr;

                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
//        for (int i = 0; i < MATRIX_WIDTH; i++) {
//            for (int j = 0; j < MATRIX_HEIGHT; j++) {
//                System.out.println("content[" + i + "][" + j + "]:" + content[i][j]);
//            }
//        }
        stream.close();
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
            System.out.println("sheet.getColumn(row).length: " + sheet.getColumn(row).length);
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
