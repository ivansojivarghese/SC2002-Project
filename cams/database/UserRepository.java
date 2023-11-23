package cams.database;

import cams.users.Staff;
import cams.users.Student;
import cams.users.User;
import cams.util.Faculty;
import cams.util.UserType;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;

public interface UserRepository {
        public void addUser(User user);
        //UserID is always uppercase to remove case sensitivity
        public User retrieveUser(String userID);
        public boolean isEmpty();
}
