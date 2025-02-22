import java.util.ArrayList;
import java.util.List;

public class Subject {
    private int id;
    private String name;
    private boolean isOptional;
    private int maxStudents;
    private List<Integer> enrolledStudents;
    private boolean isEnrollmentOpen;
    private String professor;

    public Subject(int id, String name, boolean isOptional, int maxStudents, String professor) {
        this.id = id;
        this.name = name;
        this.isOptional = isOptional;
        this.maxStudents = maxStudents;
        this.enrolledStudents = new ArrayList<>();
        this.isEnrollmentOpen = true;
        this.professor = professor;
    }

    public boolean isEnrollmentOpen() { return isEnrollmentOpen; }
    public void closeEnrollment() { isEnrollmentOpen = false; }
}