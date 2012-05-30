package csheets.io;

import csheets.core.Spreadsheet;
import csheets.core.Workbook;
import java.io.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

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
