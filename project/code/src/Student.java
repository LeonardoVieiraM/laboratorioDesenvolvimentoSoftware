import java.util.ArrayList;
import java.util.List;

public class Student extends User {
    private int studentId;
    private String name;
    private String course;
    private List<Integer> mandatorySubjects;
    private List<Integer> optionalSubjects;
    private boolean paymentPending;

    public Student(int id, String password, int studentId, String name, String course) {
        super(id, password);
        this.studentId = studentId;
        this.name = name;
        this.course = course;
        this.mandatorySubjects = new ArrayList<>();
        this.optionalSubjects = new ArrayList<>();
        this.paymentPending = false;
    }

    public void enrollSubject(int subjectId, boolean isMandatory) {}
    public void cancelEnrollment(int subjectId) {}
    public void makePayment() {}

    @Override
    public void login(int id, String password) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'login'");
    }
}