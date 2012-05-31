package csheets.core;

import org.junit.Test; 

import static org.junit.Assert.assertEquals; 
import static org.junit.Assert.assertTrue;

public class SpreadsheetTest {

	@Test public void testSetTitle() {
		
		// cria um workbook com 2 sheets
		Workbook wb=new Workbook(2);
		Spreadsheet s=wb.getSpreadsheet(0);
		
		s.setTitle("titulo");
		
		assertTrue("titulo".compareTo(s.getTitle())==0);		
	}	
}
