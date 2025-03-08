import java.util.List;

public class Professor extends User {
    private String name;

    public Professor(int id, String password, String name) {
        super(id, password);
        this.name = name;
    }

    public int getId() {
        return this.id; 
    }

    public void setName(String name) {
        this.name = name;
    }

    // Método para visualizar alunos matriculados em uma disciplina
    public void viewEnrolledStudents(int subjectId, List<Student> students) {
        System.out.println("Alunos matriculados na disciplina " + subjectId + ":");
        for (Student student : students) {
            if (student.getMandatorySubjects().contains(subjectId) || student.getOptionalSubjects().contains(subjectId)) {
                System.out.println(student.getName());
            }
        }
    }

    @Override
    public void login(int id, String password) {
        if (this.id == id && this.password.equals(password)) {
            System.out.println("Login do professor " + name + " realizado com sucesso.");
        } else {
            System.out.println("ID ou senha incorretos.");
        }
    }
}