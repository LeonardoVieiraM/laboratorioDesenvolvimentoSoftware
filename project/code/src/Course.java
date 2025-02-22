import java.util.ArrayList;
import java.util.List;

public class Course {
    private String name;
    private List<Integer> subjects;
    private int credits;

    public Course(String name, int credits) {
        this.name = name;
        this.subjects = new ArrayList<>();
        this.credits = credits;
    }

    public void addSubject(int subjectId) {}
    public void removeSubject(int subjectId) {}
}