package csheets.io;

import csheets.core.Cell;
import csheets.core.Spreadsheet;
import csheets.core.Workbook;
import csheets.ext.style.StylableSpreadsheet;
import csheets.ext.style.StyleExtension;
import java.io.*;
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
            System.out.println("Root element " + doc.getDocumentElement().getNodeName());
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
                        l++;



                        System.out.println("RowID: " + rowID + " - ColumnID: " + columnID + " - Content: " + contentStr);
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
        for (int i = 0; i < workbook.getSpreadsheet(0).getRowCount(); i++) {
        }

        //Cell[] cells = workbook.getSpreadsheet(0).getRow(0);
        StylableSpreadsheet st = (StylableSpreadsheet) sheet.getExtension(StyleExtension.NAME);
//        System.out.println("st.getColumnWidth(0): " + st.getColumnWidthXML(0));
        System.out.println("st.getRowHeight(0): " + st.getRowHeight(0));

        writer.print("<?xml version='1.0' encoding='UTF-8'?>\n");
        writer.print("<cleanSheets>\n");
        for (int row = 0; row < sheet.getRowCount(); row++) {
            writer.print("\t<row id='" + row + "' rowHeight='" + st.getRowHeight(row) + ">\n");
            for (int column = 0; column <= sheet.getColumnCount(); column++) {
                String aux = sheet.getCell(column, row).getContent();
                if (aux.trim() != "") {
                    writer.print("\t\t<column id='" + columns[column] + "' width='" + st.getColumnWidth(0) + "'\n");
                    writer.print("\t\t\t<content>" + sheet.getCell(column, row).getContent() + "</content>\n");
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
