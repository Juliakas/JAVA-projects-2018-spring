package processing;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;

public class Attendance {

    private LocalDate date;
    private ArrayList<String> student = new ArrayList<>();

    public Attendance(LocalDate date, String[] student) {
        this.date = date;
        this.student.addAll(Arrays.asList(student));
    }

    public LocalDate getDate() {
        return date;
    }

    public ArrayList<String> getStudent() {
        return student;
    }
}
