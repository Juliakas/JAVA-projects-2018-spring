package attendance;

import com.itextpdf.text.DocumentException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import processing.*;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class AttendanceWindowController implements Initializable{

    @FXML TableView<Person> tableView;
    @FXML TableColumn<Person, String> firstNameCol;
    @FXML TableColumn<Person, String> lastNameCol;
    @FXML TableColumn<Person, Integer> groupCol;
    @FXML TableColumn<Person, ComboBox> presentCol;
    @FXML DatePicker datePicker;


    @Override
    public void initialize(URL url, ResourceBundle resources) {
        datePicker.setValue(LocalDate.now());
        datePicker.setConverter(CollectData.getConverter());
        refreshTable();
    }

    @FXML
    public void refreshTable() {
        LocalDate date = datePicker.getValue();
        String fName, lName;
        int group;
        boolean dateFound = false;
        ArrayList<Boolean> checkboxValue = new ArrayList<>();
        ArrayList<Attendance> attendanceList = CollectData.getAttendanceList();
        ArrayList<Student> studentList = CollectData.getStudentList();
        ObservableList<Person> person = FXCollections.observableArrayList();
        for(Student currentStudent : studentList) checkboxValue.add(false);
        for (Attendance item : attendanceList) {
            if (item.getDate().equals(date)) {
                dateFound = true;
                for(String student : item.getStudent()) {
                    String[] nameAndGroup = student.split("\\s");
                    fName = nameAndGroup[0];
                    lName = nameAndGroup[1];
                    group = Integer.parseInt(nameAndGroup[2]);
                    for(Student currentStudent : studentList) {
                        int index = studentList.indexOf(currentStudent);
                        if(currentStudent.getfName().equals(fName) && currentStudent.getlName().equals(lName) && currentStudent.getGroup() == group) {
                            checkboxValue.set(index, true);
                        }
                    }
                }
            }
        }
        if(!dateFound) for(Student currentStudent : studentList) {
            checkboxValue.add(false);
        }
        for(Student currentStudent : studentList) {
            person.add(new Person(currentStudent.getfName(), currentStudent.getlName(),
                    currentStudent.getGroup(), checkboxValue.get(studentList.indexOf(currentStudent))));
        }
        firstNameCol.setCellValueFactory(new PropertyValueFactory<Person, String>("firstName"));
        lastNameCol.setCellValueFactory(new PropertyValueFactory<Person, String>("lastName"));
        groupCol.setCellValueFactory(new PropertyValueFactory<Person, Integer>("group"));
        presentCol.setCellValueFactory(new PropertyValueFactory<Person, ComboBox>("present"));
        tableView.setItems(person);
    }

    @FXML
    public void saveAttendance() {
        ObservableList<Person> students = tableView.getItems();
        ArrayList<Attendance> attendance = CollectData.getAttendanceList();
        LocalDate date = datePicker.getValue();
        SaveData.clearAttendanceDat();
        boolean dateFound = false;
        for(Person person : students) {
            boolean isSelected = person.getPresent().isSelected();
            String student = person.getFirstName() + " " + person.getLastName() + " " + person.getGroup();
            for(Attendance item : attendance) {
                if(item.getDate().equals(date)) {
                    if(!item.getStudent().contains(student) && isSelected)
                        item.getStudent().add(student);
                    else if(item.getStudent().contains(student) && !isSelected)
                        item.getStudent().remove(student);
                    dateFound = true;
                }
            }
            if (!dateFound && isSelected) {
                String[] studentArr = new String[1];
                studentArr[0] = student;
                attendance.add(new Attendance(date, studentArr));
            }
        }
        SaveData.safeSaveAttendance(attendance);
    }

    @FXML
    public void savePdf() {
        LocalDate date = datePicker.getValue();
        Pdf writer = new Pdf(date.toString(), 12);
        saveAttendance();
        ArrayList<Attendance> attendance = CollectData.getAttendanceList();
        for (Attendance item : attendance) {
            if (item.getDate().equals(date)) {
                try {
                    writer.addContent(item.getStudent());
                } catch (DocumentException e) {
                    e.printStackTrace();
                }
            }
        }
        writer.close();
    }

}
