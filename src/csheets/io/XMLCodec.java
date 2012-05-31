package csheets.io;

import csheets.core.*;
import csheets.ext.style.*;
import java.awt.Color;
import java.awt.Font;
import java.io.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.*;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.border.Border;
import javax.xml.XMLConstants;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.dom.DOMSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import org.xml.sax.InputSource;

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
        Font font = null;
        Workbook work = new Workbook();
        try {

            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(stream);
            //Schema schema = loadSchema("xsd.xsd");

            doc.getDocumentElement().normalize();
            //String validade = validateXML(schema, doc);

            //if (validade.equals("true")) {

            NodeList nodeLstSP = doc.getElementsByTagName("spreadsheet");
            for (int h = 0; h < nodeLstSP.getLength(); h++) {
                Node fstNodeSP = nodeLstSP.item(h);
               // if (fstNodeSP.getNodeType() == Node.ELEMENT_NODE) {

                    NodeList nodeLst = doc.getElementsByTagName("row");
                    String rowID = "", columnID = "", contentStr = "";

                    content = new String[MATRIX_WIDTH][MATRIX_HEIGHT];

                    for (int i = 0; i < MATRIX_WIDTH; i++) {
                        for (int j = 0; j < MATRIX_HEIGHT; j++) {
                            content[i][j] = "";
                        }
                    }

                    for (int s = 0; s < nodeLst.getLength(); s++) {
                        Node fstNode = nodeLst.item(s);


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


                            fstNmElmntLstCont = fstElmnt.getElementsByTagName("halign");
                            fstNmElmnt = (Element) fstNmElmntLstCont.item(0);
                            fstNm = fstNmElmnt.getChildNodes();
                            halign = Integer.parseInt(((Node) fstNm.item(0)).getNodeValue());


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

                            content[s][i] = contentStr;

                        }
                    }
                    
               // }
                work.addSpreadsheet(content);
            }
            // }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
//        for (int i = 0; i < MATRIX_WIDTH; i++) {
//            for (int j = 0; j < MATRIX_HEIGHT; j++) {
//                System.out.println("content[" + i + "][" + j + "]:" + content[i][j]);
//            }
//        }

        stream.close();
        
        
        StylableCell st = (StylableCell) work.getSpreadsheet(0).getCell(0, 0).getExtension(StyleExtension.NAME);
        st.setFont(font);
        //work.getSpreadsheet(0).
        //work.getSpreadsheet(0).getCell(0, 0).getExtension(StyleExtension.NAME);
        return new Workbook(content);
    }

    @Override
    public void write(Workbook workbook, OutputStream stream) throws IOException {
        PrintWriter writer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(stream)));
        Spreadsheet sheet;
        StylableCell stylableCell;
        StylableSpreadsheet st;
        writer.print("<?xml version='1.0' encoding='UTF-8'?>\n");
        writer.print("<cleanSheets>\n");
        for (int i = 0; i < workbook.getSpreadsheetCount(); i++) {
            sheet = workbook.getSpreadsheet(i);
            st = (StylableSpreadsheet) sheet.getExtension(StyleExtension.NAME);
            writer.print("\t<spreadsheet title='" + sheet.getTitle() + "'>\n");
            for (int row = 0; row < sheet.getRowCount(); row++) {
                writer.print("\t\t<row id='" + row + "' rowHeight='" + st.getRowHeight(row) + "'>\n");
                for (int column = 0; column <= sheet.getColumnCount(); column++) {
                    String aux = sheet.getCell(column, row).getContent();
                    stylableCell = (StylableCell) sheet.getCell(column, row).getExtension(StyleExtension.NAME);
                    if (aux.trim() != "") {
                        writer.print("\t\t\t<column id='" + columns[column] + "' columnWidth='" + st.getColumnWidth(0) + "'>\n");
                        writer.print("\t\t\t\t<content>" + sheet.getCell(column, row).getContent() + "</content>\n");
                        writer.print("\t\t\t\t<font>" + stylableCell.getFont() + "</font>\n");
                        writer.print("\t\t\t\t<valign>" + stylableCell.getVerticalAlignment() + "</valign>\n");
                        writer.print("\t\t\t\t<halign>" + stylableCell.getHorizontalAlignment() + "</halign>\n");
                        writer.print("\t\t\t\t<foreColor>" + stylableCell.getForegroundColor() + "</foreColor>\n");
                        writer.print("\t\t\t\t<backColor>" + stylableCell.getBackgroundColor() + "</backColor>\n");
                        writer.print("\t\t\t\t<border>" + stylableCell.getBorder().getBorderInsets(null) + "</border>\n");
                        writer.print("\t\t\t</column>\n");
                    }
                }
                writer.print("\t\t</row>\n\n");
            }
            writer.print("\t</spreadsheet>\n\n");
        }

        writer.print("</cleanSheets>");
        writer.close();
        stream.close();
    }

    public static String validateXML(Schema schema, Document document) {
        String validate = "";
        try {
            Validator validator = schema.newValidator();
            validator.validate(new DOMSource(document));
            validate = "true";

        } catch (Exception e) {
            e.printStackTrace();
        }
        return validate;
    }

    public static Schema loadSchema(String name) {
        Schema schema = null;
        try {
            String language = XMLConstants.W3C_XML_SCHEMA_NS_URI;
            SchemaFactory factory = SchemaFactory.newInstance(language);
            schema = factory.newSchema(new File(name));
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        return schema;
    }
    /*
     * public static Document parseXMLDom(InputStream name) { Document document
     * = null; try { DocumentBuilderFactory factory =
     * DocumentBuilderFactory.newInstance(); DocumentBuilder builder =
     * factory.newDocumentBuilder(); document = builder.parse(name);
     * document.getDocumentElement().normalize(); } catch (Exception e) {
     * System.out.println(e.toString()); } return document; }
     */
}
