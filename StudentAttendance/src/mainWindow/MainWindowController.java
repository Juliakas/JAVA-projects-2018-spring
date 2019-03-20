package mainWindow;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import processing.CollectData;
import processing.SaveData;
import processing.Student;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class MainWindowController implements Initializable{

    @FXML private TextField firstNameText;
    @FXML private TextField lastNameText;
    @FXML private TableColumn<Person, String> firstNameCol;
    @FXML private TableColumn<Person, String> lastNameCol;
    @FXML private ComboBox<String> groupBox;
    @FXML private TableView<Person> tableView;

    @Override
    public void initialize(URL url, ResourceBundle resources) {
        for(int i = 0; i < CollectData.getNumberOfGroups(); i++)
            groupBox.getItems().add(String.format("Group %d", i + 1));
        if(!(CollectData.getNumberOfGroups() == 0)) groupBox.setValue("Group " + 1);
        groupRefresh();
    }

    @FXML
    public void addStudent() {
        if(CollectData.getNumberOfGroups() == 0) return;
        String fName = firstNameText.getText();
        String lName = lastNameText.getText();
        firstNameText.clear();
        lastNameText.clear();
        int group = Integer.parseInt(groupBox.getValue().substring(6));
        SaveData.saveStudentData(new Student(fName, lName, group));
        CollectData.loadStudents();
        groupRefresh();
    }

    @FXML
    public void groupRefresh() {
        int group;
        try {
            group = Integer.parseInt(groupBox.getValue().substring(6));
        } catch (NullPointerException e) {
            group = -1;
        }
        ArrayList<Student> studentList = CollectData.getStudentList();
        ObservableList<Person> person = FXCollections.observableArrayList();
        for (Student item : studentList) {
            if (item.getGroup() == group) {
                person.add(new Person(item.getfName(), item.getlName()));
            }
        }
        firstNameCol.setCellValueFactory(new PropertyValueFactory<Person, String>("firstName"));
        lastNameCol.setCellValueFactory(new PropertyValueFactory<Person, String>("lastName"));
        tableView.setItems(person);
    }

    @FXML
    public void createGroup() {
        SaveData.createGroup();
        groupBox.getItems().add(String.format("Group %d", CollectData.getNumberOfGroups()));
        groupBox.setValue(String.format("Group %d", CollectData.getNumberOfGroups()));
    }

    @FXML
    public void removeGroup() {
        if(CollectData.getNumberOfGroups() == 0) return;
        int number = Integer.parseInt(groupBox.getValue().substring(6));
        groupBox.getItems().remove(0, CollectData.getNumberOfGroups());
        SaveData.deleteGroup(number);
        for(int i = 0; i < CollectData.getNumberOfGroups(); i++)
            groupBox.getItems().add(String.format("Group %d", i + 1));
        if(!(CollectData.getNumberOfGroups() == 0)) groupBox.setValue(String.format("Group %d", 1));
        groupRefresh();
    }

    @FXML
    public void removeStudent() {
        int group = Integer.parseInt(groupBox.getValue().substring(6));
        Person person = tableView.getSelectionModel().getSelectedItem();
        Student selectedStudent = new Student(person.getFirstName(), person.getLastName(), group);
        SaveData.deleteStudent(selectedStudent);
        groupRefresh();
    }

    @FXML
    public void openAttendance() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/attendance/AttendanceWindow.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }



}
