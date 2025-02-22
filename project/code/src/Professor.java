public class Professor extends User {
    private int professorId;
    private String name;

    public Professor(int id, String password, int professorId, String name) {
        super(id, password);
        this.professorId = professorId;
        this.name = name;
    }

    public void viewEnrolledStudents(int subjectId) {}

    @Override
    public void login(int id, String password) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'login'");
    }
}