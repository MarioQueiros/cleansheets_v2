package csheets.io;

import csheets.core.Spreadsheet;
import csheets.core.Workbook;
import java.io.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.*;

/**
 *
 * @author MpApQ
 */
public class XMLCodec implements Codec {

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

            for (int s = 0; s < nodeLst.getLength(); s++) {

                Node fstNode = nodeLst.item(s);

                if (fstNode.getNodeType() == Node.ELEMENT_NODE) {

                    Element fstElmnt = (Element) fstNode;
                    NamedNodeMap attrs = fstNode.getAttributes();
                    System.out.println("attrs.getLength()"+attrs.getLength());
                    for (int i = 0; i < attrs.getLength(); i++) {
                        Attr attribute = (Attr) attrs.item(i);
                        System.out.println("Row"+attribute.getName() + "=" + attribute.getValue());
                    }

                    NodeList fstNmElmntLst = fstElmnt.getElementsByTagName("column");


//                    for (int i = 0; i < fstNmElmntLst.getLength(); i++) {
//                        Element fstNmElmnt = (Element) fstNmElmntLst.item(i);
                    NodeList fstNmElmntLstCont = fstElmnt.getElementsByTagName("content");
                    for (int i = 0; i < fstNmElmntLstCont.getLength(); i++) {
                        Element fstNmElmnt = (Element) fstNmElmntLstCont.item(i);
                        NodeList fstNm = fstNmElmnt.getChildNodes();
                        System.out.println("RowID: ******* - ColumnID: ******* - Content: " + ((Node) fstNm.item(0)).getNodeValue());
                    }

//                        NodeList fstNm = fstNmElmnt.getChildNodes();
//                        System.out.println("First Name : "  + ((Node) fstNm.item(i)).getNodeValue());
//                    }

//                    Element fstNmElmnt = (Element) fstNmElmntLst.item(0);
//                    NodeList fstNm = fstNmElmnt.getChildNodes();
//                    System.out.println("First Name : " + ((Node) fstNm.item(0)).getNodeValue());
//                    NodeList lstNmElmntLst = fstElmnt.getElementsByTagName("lastname");
//                    Element lstNmElmnt = (Element) lstNmElmntLst.item(0);
//                    NodeList lstNm = lstNmElmnt.getChildNodes();
//                    System.out.println("Last Name : " + ((Node) lstNm.item(0)).getNodeValue());
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
        writer.print("<?xml version='1.0' encoding='UTF-8'?>\n");
        writer.print("<cleanSheets>\n");
        for (int row = 0; row < sheet.getRowCount(); row++) {
            writer.print("\t<row id='" + row + "'>\n");
            for (int column = 0; column <= sheet.getColumnCount(); column++) {
                String aux = sheet.getCell(column, row).getContent();
                if (aux.trim() != "") {
                    writer.print("\t\t<column id='" + column + "'>\n");
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
