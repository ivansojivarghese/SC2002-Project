package cams.util;

import java.io.File;  
import java.io.FileInputStream;  
import java.util.Iterator;  
import org.apache.poi.ss.usermodel.Cell;  
import org.apache.poi.ss.usermodel.Row;  
import org.apache.poi.xssf.usermodel.XSSFSheet;  
import org.apache.poi.xssf.usermodel.XSSFWorkbook;  

public class ReadExcelFile { 
	public static void updateFromFile(String filename) {
		try {  
			// File file = new File("C:\\Users\\Ivan\\eclipse-workspace\\SC2002-Project-main\\SC2002-Project-main\\staff_list.xlsx");   //creating a new file instance  
			File file = new File(System.getProperty("user.dir") + "\\cams\\util\\staff_list.xslx");
			FileInputStream fis = new FileInputStream(file);   //obtaining bytes from the file  
			
			//creating Workbook instance that refers to .xlsx file  
			XSSFWorkbook wb = new XSSFWorkbook(fis);   
			XSSFSheet sheet = wb.getSheetAt(0);     //creating a Sheet object to retrieve object  
			Iterator<Row> itr = sheet.iterator();    //iterating over excel file  
			
			while (itr.hasNext()) {  // iterate over each row
				Row row = itr.next();  
				Iterator<Cell> cellIterator = row.cellIterator();   //iterating over each column  
				while (cellIterator.hasNext()) {  
				Cell cell = cellIterator.next();  
				
				System.out.println(cell.getStringCellValue());
										
				}  
			}  
		}  catch(Exception e)  {  
			e.printStackTrace();  
		}  
	}
}
