import java.util.ArrayList;
import java.util.List;

public class Student extends User {
    private String name;
    private int courseId; // ID do curso associado
    private List<Integer> mandatorySubjects;
    private List<Integer> optionalSubjects;
    private boolean paymentPending;

    public Student(int id, String password, String name, int courseId) {
        super(id, password);
        this.name = name;
        this.courseId = courseId;
        this.mandatorySubjects = new ArrayList<>();
        this.optionalSubjects = new ArrayList<>();
        this.paymentPending = false;
    }

    public int getId() {
        return this.id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCourse(int courseId) {
        this.courseId = courseId;
    }

    @Override
    public void login(int id, String password) {
        if (this.id == id && this.password.equals(password)) {
            System.out.println("Login do aluno " + name + " realizado com sucesso.");
        } else {
            System.out.println("ID ou senha incorretos.");
        }
    }

    // Método para matricular o aluno em uma disciplina
    public void enrollSubject(int subjectId, List<Subject> subjects) {
        Subject subject = findSubjectById(subjectId, subjects);
        if (subject == null) {
            System.out.println("Disciplina não encontrada.");
            return;
        }
    
        if (subject.isMandatory()) { // Verifica se a disciplina é obrigatória
            if (mandatorySubjects.size() < 4) { // Limite de 4 disciplinas obrigatórias
                mandatorySubjects.add(subjectId);
                System.out.println("Disciplina obrigatória " + subjectId + " adicionada com sucesso.");
            } else {
                System.out.println("Limite de disciplinas obrigatórias atingido.");
            }
        } else {
            if (optionalSubjects.size() < 2) { // Limite de 2 disciplinas optativas
                optionalSubjects.add(subjectId);
                System.out.println("Disciplina optativa " + subjectId + " adicionada com sucesso.");
            } else {
                System.out.println("Limite de disciplinas optativas atingido.");
            }
        }
    }

    // Método para cancelar a matrícula em uma disciplina
    public void cancelEnrollment(int subjectId) {
        if (mandatorySubjects.remove(Integer.valueOf(subjectId))) {
            System.out.println("Disciplina obrigatória " + subjectId + " removida com sucesso.");
        } else if (optionalSubjects.remove(Integer.valueOf(subjectId))) {
            System.out.println("Disciplina optativa " + subjectId + " removida com sucesso.");
        } else {
            System.out.println("Disciplina " + subjectId + " não encontrada.");
        }
    }

    // Método para realizar o pagamento
    public void makePayment() {
        this.paymentPending = false;
        System.out.println("Pagamento realizado com sucesso.");
    }

    // Método auxiliar para encontrar uma disciplina pelo ID
    private Subject findSubjectById(int subjectId, List<Subject> subjects) {
        for (Subject subject : subjects) {
            if (subject.getId() == subjectId) {
                return subject;
            }
        }
        return null;
    }

    public int getCourse() {
        return courseId;
    }

    public List<Integer> getMandatorySubjects() {
        return mandatorySubjects;
    }

    public String getName() {
        return name;
    }

    public List<Integer> getOptionalSubjects() {
        return optionalSubjects;
    }
}