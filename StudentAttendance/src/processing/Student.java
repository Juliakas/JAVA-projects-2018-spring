package processing;

public class Student {

    private String fName;
    private String lName;
    private Integer group;

    public Student(String fName, String lName, int group) {
        this.fName = fName;
        this.lName = lName;
        this.group = group;
    }

    public String getfName() {
        return fName;
    }

    public String getlName() {
        return lName;
    }

    public int getGroup() {
        return group;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public void setlName(String lName) {
        this.lName = lName;
    }

    public void setGroup(int group) {
        this.group = group;
    }
}
