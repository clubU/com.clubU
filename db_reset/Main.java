import java.io.*;
import java.sql.*;
import java.util.*;

import org.apache.commons.csv.*;

public class Main {

    // Database
    private static final String DATABASE_DRIVER = "com.mysql.jdbc.Driver";
    private static final String DATABASE_URL = "jdbc:mysql://localhost/clubu";
    private static final String DATABASE_USERNAME = "root";
    private static final String DATABASE_PASSWORD = "root";

    // Resources
    private static final String IMAGE_DIR = "resources/image/";
    private static final String CLUB_CSV = "resources/club.csv";
    private static final String STUDENT_CSV = "resources/student.csv";
    private static final String EVENT_CSV = "resources/event.csv";
    private static final String SUBSCRIPTION_CSV = "resources/subscription.csv";
    private static final String[] CLUB_CSV_HEADER = {"id", "username", "passwordHash", "name", "email", "abbreviation", "description", "imageId"};
    private static final String[] STUDENT_CSV_HEADER = {"id", "username", "passwordHash", "firstName", "lastName", "email", "studentNumber", "dateOfBirth", "yearOfStudy", "programOfStudy", "imageId"};
    private static final String[] EVENT_CSV_HEADER = {"id", "clubId", "title", "time", "location", "description", "imageId"};
    private static final String[] SUBSCRIPTION_CSV_HEADER = {"studentId", "clubId"};

    public static void main(String[] args) {
        Connection conn = null;
        Statement stmt = null;
        PreparedStatement prepStmt = null;
        java.util.Date now = new java.util.Date();
        String sql = null;
        FileReader fileReader = null;
        CSVParser csvParser = null;
        List<CSVRecord> records = null;
        FileInputStream fileInputStream = null;
        try {
            // Database setup
            conn = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
            stmt = conn.createStatement();
            // Delete everything
            stmt.executeUpdate("DELETE FROM subscription");
            stmt.executeUpdate("DELETE FROM event");
            stmt.executeUpdate("DELETE FROM student");
            stmt.executeUpdate("DELETE FROM club");
            stmt.executeUpdate("DELETE FROM image");
            // Import images
            sql = "INSERT INTO image(id, content) VALUES(?, ?)";
            for (int i = 1; i <= 75; i++) {
                String imagePath = IMAGE_DIR + i + ".jpg";
                File file = new File(imagePath);
                byte[] data = new byte[(int)(file.length())];
                fileInputStream = new FileInputStream(file);
                fileInputStream.read(data);
                fileInputStream.close();
                prepStmt = conn.prepareStatement(sql);
                prepStmt.setInt(1, i);
                prepStmt.setBinaryStream(2, new ByteArrayInputStream(data), data.length);
                prepStmt.executeUpdate();
            }
            // Import clubs
            fileReader = new FileReader(CLUB_CSV);
            csvParser = new CSVParser(fileReader, CSVFormat.DEFAULT.withHeader(CLUB_CSV_HEADER));
            records = csvParser.getRecords();
            sql = "INSERT INTO club(id, username, password_hash, name, email, abbreviation, description, time_created, time_updated, image_id) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            for (int i = 1; i <= records.size(); i++) {
                CSVRecord record = records.get(i - 1);
                prepStmt = conn.prepareStatement(sql);
                prepStmt.setInt(1, Integer.parseInt(record.get(CLUB_CSV_HEADER[0])));
                prepStmt.setString(2, record.get(CLUB_CSV_HEADER[1]));
                prepStmt.setString(3, record.get(CLUB_CSV_HEADER[2]));
                prepStmt.setString(4, record.get(CLUB_CSV_HEADER[3]));
                prepStmt.setString(5, record.get(CLUB_CSV_HEADER[4]));
                prepStmt.setString(6, record.get(CLUB_CSV_HEADER[5]));
                prepStmt.setString(7, record.get(CLUB_CSV_HEADER[6]));
                prepStmt.setDate(8, new java.sql.Date(now.getTime()));
                prepStmt.setDate(9, new java.sql.Date(now.getTime()));
                prepStmt.setInt(10, Integer.parseInt(record.get(CLUB_CSV_HEADER[7])));
                prepStmt.executeUpdate();
            }
            // Import students
            fileReader = new FileReader(STUDENT_CSV);
            csvParser = new CSVParser(fileReader, CSVFormat.DEFAULT.withHeader(STUDENT_CSV_HEADER));
            records = csvParser.getRecords();
            sql = "INSERT INTO student(id, username, password_hash, first_name, last_name, email, student_number, date_of_birth, year_of_study, program_of_study, time_created, time_updated, image_id) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"; 
            for (int i = 1; i <= records.size(); i++) {
                CSVRecord record = records.get(i - 1);
                prepStmt = conn.prepareStatement(sql);
                prepStmt.setInt(1, Integer.parseInt(record.get(STUDENT_CSV_HEADER[0])));
                prepStmt.setString(2, record.get(STUDENT_CSV_HEADER[1]));
                prepStmt.setString(3, record.get(STUDENT_CSV_HEADER[2]));
                prepStmt.setString(4, record.get(STUDENT_CSV_HEADER[3]));
                prepStmt.setString(5, record.get(STUDENT_CSV_HEADER[4]));
                prepStmt.setString(6, record.get(STUDENT_CSV_HEADER[5]));
                prepStmt.setInt(7, Integer.parseInt(record.get(STUDENT_CSV_HEADER[6])));
                prepStmt.setDate(8, new java.sql.Date(Long.parseLong(record.get(STUDENT_CSV_HEADER[7]))));
                prepStmt.setInt(9, Integer.parseInt(record.get(STUDENT_CSV_HEADER[8])));
                prepStmt.setString(10, record.get(STUDENT_CSV_HEADER[9]));
                prepStmt.setDate(11, new java.sql.Date(now.getTime()));
                prepStmt.setDate(12, new java.sql.Date(now.getTime()));
                prepStmt.setInt(13, Integer.parseInt(record.get(STUDENT_CSV_HEADER[10])));
                prepStmt.executeUpdate();
            }
            // Import events
            fileReader = new FileReader(EVENT_CSV);
            csvParser = new CSVParser(fileReader, CSVFormat.DEFAULT.withHeader(EVENT_CSV_HEADER));
            records = csvParser.getRecords();
            sql = "INSERT INTO event(id, club_id, title, time, location, description, time_created, time_updated, image_id) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)";
            for (int i = 1; i <= records.size(); i++) {
                CSVRecord record = records.get(i - 1);
                prepStmt = conn.prepareStatement(sql);
                prepStmt.setInt(1, Integer.parseInt(record.get(EVENT_CSV_HEADER[0])));
                prepStmt.setInt(2, Integer.parseInt(record.get(EVENT_CSV_HEADER[1])));
                prepStmt.setString(3, record.get(EVENT_CSV_HEADER[2]));
                prepStmt.setDate(4, new java.sql.Date(Long.parseLong(record.get(EVENT_CSV_HEADER[3]))));
                prepStmt.setString(5, record.get(EVENT_CSV_HEADER[4]));
                prepStmt.setString(6, record.get(EVENT_CSV_HEADER[5]));
                prepStmt.setDate(7, new java.sql.Date(now.getTime()));
                prepStmt.setDate(8, new java.sql.Date(now.getTime()));
                prepStmt.setInt(9, Integer.parseInt(record.get(EVENT_CSV_HEADER[6])));
                prepStmt.executeUpdate();
            }
            // Import subscriptions
            fileReader = new FileReader(SUBSCRIPTION_CSV);
            csvParser = new CSVParser(fileReader, CSVFormat.DEFAULT.withHeader(SUBSCRIPTION_CSV_HEADER));
            records = csvParser.getRecords();
            sql = "INSERT INTO subscription(student_id, club_id) VALUES(?, ?)";
            for (int i = 1; i <= records.size(); i++) {
                CSVRecord record = records.get(i - 1);
                prepStmt = conn.prepareStatement(sql);
                prepStmt.setInt(1, Integer.parseInt(record.get(SUBSCRIPTION_CSV_HEADER[0])));
                prepStmt.setInt(2, Integer.parseInt(record.get(SUBSCRIPTION_CSV_HEADER[1])));
                prepStmt.executeUpdate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

