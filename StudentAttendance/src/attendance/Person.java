package attendance;

import javafx.scene.control.CheckBox;

public class Person {

    private String firstName;
    private String lastName;
    private Integer group;
    private CheckBox present;

    public Person(String firstName, String lastName, Integer group, boolean checkBoxValue) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.group = group;
        present = new CheckBox();
        present.setSelected(checkBoxValue);
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Integer getGroup() {
        return group;
    }

    public void setGroup(Integer group) {
        this.group = group;
    }

    public CheckBox getPresent() {
        return present;
    }

    public void setPresent(CheckBox present) {
        this.present = present;
    }
}
