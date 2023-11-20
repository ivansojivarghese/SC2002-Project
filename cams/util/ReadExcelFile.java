package cams.util;

import java.io.File;  
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;  
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;  
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import cams.users.*;

public class ReadExcelFile {
	public static ArrayList<User> updateFromFile(String filename) {
		
		ArrayList<User> data = new ArrayList<User>();
		
		try {  
			
			File file = new File(System.getProperty("user.dir") + "\\cams\\util\\" + filename);
			FileInputStream fis = new FileInputStream(file);   //obtaining bytes from the file  
			
			//creating Workbook instance that refers to .xlsx file  
			XSSFWorkbook wb = new XSSFWorkbook(fis);   
			XSSFSheet sheet = wb.getSheetAt(0);     //creating a Sheet object to retrieve object  
			Iterator<Row> itr = sheet.iterator();    //iterating over excel file  
			
			int rowLevel = 1;
			int cellLevel = 0;
			
			while (itr.hasNext()) {  // iterate over each row
				Row row = itr.next();  
				Iterator<Cell> cellIterator = row.cellIterator();   //iterating over each column  
				cellLevel = 1;
				
				String name = "";
				String email = "";
				String userID = "";
				String faculty = "";
				
				while (cellIterator.hasNext()) {  
					
					Cell cell = cellIterator.next();  
					
					if (cell.getStringCellValue().length() != 0) { // only with text values
						if (rowLevel > 1) {
							switch (cellLevel) {
								case 1: // name
									name = cell.getStringCellValue();
								break;
								case 2: // email
									email = cell.getStringCellValue();
									int tgt = email.indexOf("@");
									userID = email.substring(0, tgt);
								break;
								case 3: // faculty
									faculty = cell.getStringCellValue();
								break;
							}
						}
					}
					
					cellLevel++;
				}  
				
				User user = new Staff(name, userID, faculty);
				
				if (user.getUserID() != "") {
					// System.out.println(user.getUserID());
				
					data.add(user);
				}
				
				rowLevel++;
			}  
			
		}  catch(Exception e)  {  
			e.printStackTrace();  
		}  
		
		return data;
	}
}
