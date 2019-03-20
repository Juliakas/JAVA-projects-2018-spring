package processing;

import javafx.util.StringConverter;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

public class CollectData {

    private static final File studentsDat = new File("src/data/students.dat");
    private static final File attendanceDat = new File("src/data/attendance.dat");
    private static ArrayList<Student> studentList;
    private static ArrayList<Attendance> attendanceList;
    private static StringConverter<LocalDate> converter = new StringConverter<LocalDate>() {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        @Override
        public String toString(LocalDate date) {
            if (date != null) {
                return dateFormatter.format(date);
            } else {
                return "";
            }
        }
        @Override
        public LocalDate fromString(String string) {
            if (string != null && !string.isEmpty()) {
                return LocalDate.parse(string, dateFormatter);
            } else {
                return null;
            }
        }
    };

    public static int getNumberOfGroups() {
        try {
            Scanner input = new Scanner(studentsDat);
            return input.nextInt();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public static void loadStudents() {
        Student studentObj;
        studentList = new ArrayList<>();
        String buffer;
        try {
            Scanner input = new Scanner(studentsDat);
            input.nextLine();
            while(input.hasNextLine()) {
                buffer = input.nextLine();
                String[] name = buffer.split("\\s");
                studentObj = new Student(name[0], name[1], Integer.parseInt(name[2]));
                studentList.add(studentObj);
            }
            input.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void loadAttendance() {
        Attendance attendanceObj;
        attendanceList = new ArrayList<>();
        String buffer;
        String dateStr;
        LocalDate date;
        try {
            Scanner input = new Scanner(attendanceDat);
            while(input.hasNextLine()) {
                buffer = input.nextLine();
                String[] line = buffer.split(",");
                dateStr = line[0].substring(0, 10);
                date = converter.fromString(dateStr);
                line[0] = line[0].substring(10);
                attendanceObj = new Attendance(date, line);
                attendanceList.add(attendanceObj);
            }
            input.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<Student> getStudentList() {
        loadStudents();
        return studentList;
    }

    public static ArrayList<Attendance> getAttendanceList() {
        loadAttendance();
        return attendanceList;
    }

    public static StringConverter<LocalDate> getConverter() {
        return converter;
    }
}
