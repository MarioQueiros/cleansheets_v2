package csheets.core;

import org.junit.Test; 

import static org.junit.Assert.assertEquals; 

public class WorkbookTest {
	
	@Test public void testGetSpreadsheetCount() {
		
		// cria um workbook com 2 sheets
		Workbook wb=new Workbook(2);
		
		assertEquals("Result", 2, wb.getSpreadsheetCount());		
	}
}
