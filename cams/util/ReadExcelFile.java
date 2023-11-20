//MOVED all user data intialising to UserRepo class for better encapsulation

/*package cams.util;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Iterator;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import cams.users.*;

public class ReadExcelFile {

	public static ArrayList<User> updateFromFile(String filename, UserType userType) {
		ArrayList<User> data = new ArrayList<>();

		try {
			File file = new File(System.getProperty("user.dir") + "\\cams\\util\\" + filename);
			FileInputStream fis = new FileInputStream(file);
			XSSFWorkbook wb = new XSSFWorkbook(fis);
			XSSFSheet sheet = wb.getSheetAt(0);

            for (Row row : sheet) {
                if (row.getRowNum() == 0) continue; // Skip header row

                String name = "", email = "", userID = "";
                Faculty faculty = null;

                for (Cell cell : row) {
                    String cellValue = cell.getStringCellValue();
                    if (cellValue.isEmpty()) continue;

                    switch (cell.getColumnIndex()) {
                        case 0:
                            name = cellValue;
                            break;
                        case 1:
                            email = cellValue;
                            userID = email.substring(0, email.indexOf("@"));
                            break;
                        case 2:
                            faculty = Faculty.valueOf(cellValue);
                            break;
                    }
                }

                if (userID.isEmpty()) continue;
				switch (userType) {
					case STUDENT:
						data.add(new Student(name, userID, faculty)); // Student constructor
						break;
					case STAFF:
						data.add(new Staff(name, userID, faculty)); // Staff constructor
						break;
				}
            }

			wb.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return data;
	}
}
*/