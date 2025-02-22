public class Secretary extends User {
    private int secretaryId;
    private String name;

    public Secretary(int id, String password, int secretaryId, String name) {
        super(id, password);
        this.secretaryId = secretaryId;
        this.name = name;
    }

    public void viewCurriculum() {}
    public void viewStudentEnrollments() {}
    public Student addStudent(String name) { return null; }
    public void removeStudent(int studentId) {}
    public void editStudent(int studentId) {}
    public void viewProfessors() {}
    public Professor addProfessor(String name) { return null; }
    public void removeProfessor(int professorId) {}
    public void editProfessor(int professorId) {}
    public void viewSubjects() {}
    public Subject addSubject(String name) { return null; }
    public void removeSubject(int subjectId) {}
    public void editSubject(int subjectId) {}
    public void openEnrollmentPeriod() {}
    public void closeEnrollmentPeriod() {}

    @Override
    public void login(int id, String password) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'login'");
    }
}