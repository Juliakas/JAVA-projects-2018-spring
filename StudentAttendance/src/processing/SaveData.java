package processing;

import java.io.*;
import java.util.ArrayList;

public class SaveData {

    private static final File studentsDat = new File("src/data/students.dat");
    private static final File attendanceDat = new File("src/data/attendance.dat");

    public static void saveStudentData(Student student) {
        try {
            PrintWriter writer = new PrintWriter(new FileWriter(studentsDat, true));
            writer.println();
            writer.print(student.getfName() + " " + student.getlName() + " " + student.getGroup());
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void saveAttendanceData(Attendance attendance) {
        try {
            PrintWriter writer = new PrintWriter(new FileWriter(attendanceDat, true));
            RandomAccessFile raf = new RandomAccessFile(attendanceDat, "r");
            raf.seek(0);
            if(raf.readLine() != null) {
                writer.println();
            }
            writer.print(attendance.getDate());
            for(String student : attendance.getStudent()) {
                writer.print(student);
                if(!(attendance.getStudent().indexOf(student) == attendance.getStudent().size() - 1)) {
                    writer.print(",");
                }
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void safeSaveAttendance(ArrayList<Attendance> attendance) {
        for (int index = 0; index < attendance.size(); index++) {
            if (attendance.get(index).getStudent().isEmpty()) {
                attendance.remove(index--);
            } else SaveData.saveAttendanceData(attendance.get(index));
        }
    }
    public static void createGroup() {
        try {
            RandomAccessFile writer = new RandomAccessFile(studentsDat, "rw");
            writer.seek(0);
            writer.writeBytes(String.valueOf(CollectData.getNumberOfGroups() + 1));
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void deleteGroup(int group) {
        try {
            ArrayList<Student> studentList = CollectData.getStudentList();
            ArrayList<Attendance> attendance = CollectData.getAttendanceList();
            clearAttendanceDat();
            int numberOfGroups = CollectData.getNumberOfGroups() - 1;
            PrintWriter writer = new PrintWriter(new FileWriter(studentsDat, false));
            writer.print(numberOfGroups);
            writer.close();
            for(int studentIndex = 0; studentIndex < studentList.size(); studentIndex++) {
                int currentGroup = studentList.get(studentIndex).getGroup();
                if(currentGroup == group)
                    studentList.remove(studentIndex--);
                else if(currentGroup > group)
                    studentList.get(studentIndex).setGroup(studentList.get(studentIndex).getGroup() - 1);
                if(currentGroup != group) saveStudentData(studentList.get(studentIndex));
            }
            for(int itemIndex = 0; itemIndex < attendance.size(); itemIndex++) {
                for (int studentIndex = 0; studentIndex < attendance.get(itemIndex).getStudent().size(); studentIndex++) {
                    int currentGroup = Integer.parseInt(attendance.get(itemIndex).getStudent().get(studentIndex).split("\\s")[2]);
                    if (currentGroup == group) {
                        attendance.get(itemIndex).getStudent().remove(studentIndex--);
                        if(attendance.get(itemIndex).getStudent().isEmpty()) {
                            attendance.remove(itemIndex--);
                            break;
                        }
                    }
                    else if (currentGroup > group) {
                        int newGroup = Integer.parseInt(attendance.get(itemIndex).getStudent().get(studentIndex).split("\\s")[2]) - 1;
                        String[] str = attendance.get(itemIndex).getStudent().get(studentIndex).split("\\s");
                        attendance.get(itemIndex).getStudent().set(studentIndex, str[0] + " " + str[1] + " " + newGroup);
                    }
                }
            }
            safeSaveAttendance(attendance);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void deleteStudent(Student oldStudent) {
        ArrayList<Student> studentList = CollectData.getStudentList();
        int groups = CollectData.getNumberOfGroups();
        PrintWriter writer = null;
        try {
            writer = new PrintWriter(new FileWriter(studentsDat, false));
            writer.print(groups);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (Student currentStudent : studentList)
            if (!(currentStudent.getGroup() == oldStudent.getGroup()
                    && currentStudent.getfName().equals(oldStudent.getfName())
                    && currentStudent.getlName().equals(oldStudent.getlName())))
                saveStudentData(currentStudent);
        deleteStudentFromAttendance(oldStudent);
    }

    public static void deleteStudentFromAttendance(Student oldStudent) {
        ArrayList<Attendance> attendance = CollectData.getAttendanceList();
        clearAttendanceDat();
        for(Attendance item : attendance) {
            for(int studentIndex = 0; studentIndex < item.getStudent().size(); studentIndex ++) {
                if(item.getStudent().get(studentIndex).equals(oldStudent.getfName() + " " + oldStudent.getlName() + " " + oldStudent.getGroup())) {
                    item.getStudent().remove(studentIndex--);
                }
            }
        }
        safeSaveAttendance(attendance);
    }

    public static void clearStudentsDat() {
        try {
            new FileWriter(studentsDat, false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void clearAttendanceDat() {
        try {
            new FileWriter(attendanceDat, false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
