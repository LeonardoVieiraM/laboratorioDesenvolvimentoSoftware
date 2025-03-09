import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Subject {
    private int id;
    private String name;
    private boolean isMandatory;
    private List<Integer> enrolledStudents;
    private boolean isEnrollmentOpen;
    private String professor;
    private int courseId;
    private boolean isActive; // Novo atributo para indicar se a disciplina está ativa

    public Subject(int id, String name, boolean isMandatory, String professor, int courseId) {
        this.id = id;
        this.name = name;
        this.isMandatory = isMandatory;
        this.enrolledStudents = new ArrayList<>();
        this.isEnrollmentOpen = true;
        this.professor = professor;
        this.courseId = courseId;
        this.isActive = false; // Inicialmente, a disciplina não está ativa
    }

    // Método para matricular aluno
    public void enrollStudent(int studentId) {
        if (enrolledStudents.size() < 60) { // Número fixo de 60 alunos
            enrolledStudents.add(studentId);
            System.out.println("Aluno " + studentId + " matriculado na disciplina " + name + ".");
        } else {
            System.out.println("Limite de alunos atingido na disciplina " + name + ".");
        }
    }

    // Método para verificar se a disciplina está ativa
    public boolean isActive() {
        return isActive;
    }

    // Método para ativar/desativar a disciplina
    public void setActive(boolean isActive) {
        this.isActive = isActive;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMandatory(boolean isMandatory) {
        this.isMandatory = isMandatory;
    }

    public void setProfessor(String professor) {
        this.professor = professor;
    }

    public boolean isEnrollmentOpen() {
        return isEnrollmentOpen;
    }

    public void closeEnrollment() {
        isEnrollmentOpen = false;
    }

    public void removeStudent(int studentId) {
        if (enrolledStudents.remove(Integer.valueOf(studentId))) {
            System.out.println("Aluno " + studentId + " removido da disciplina " + name + ".");
        } else {
            System.out.println("Aluno " + studentId + " não encontrado na disciplina " + name + ".");
        }
    }

    public List<Integer> getEnrolledStudents() {
        return enrolledStudents;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getProfessor() {
        return professor;
    }

    public static List<Subject> loadSubjects(String filePath) {
        List<Subject> subjects = new ArrayList<>();
        try (Scanner scanner = new Scanner(new File(filePath))) {
            while (scanner.hasNextLine()) {
                String[] data = scanner.nextLine().split(",");
                int id = Integer.parseInt(data[0]);
                String name = data[1];
                boolean isMandatory = Boolean.parseBoolean(data[2]);
                String professor = data[3];
                int courseId = Integer.parseInt(data[4]);
                subjects.add(new Subject(id, name, isMandatory, professor, courseId));
            }
        } catch (IOException e) {
            System.out.println("Erro ao carregar disciplinas: " + e.getMessage());
        }
        return subjects;
    }

    public static void saveSubjects(List<Subject> subjects, String filePath) {
        try (FileWriter writer = new FileWriter(filePath)) {
            for (Subject subject : subjects) {
                writer.write(subject.getId() + "," + subject.getName() + "," + subject.isMandatory() + "," +
                        subject.getProfessor() + "," + subject.getCourseId() + "\n");
            }
        } catch (IOException e) {
            System.out.println("Erro ao salvar disciplinas: " + e.getMessage());
        }
    }

    public boolean isMandatory() {
        return isMandatory;
    }

    public void setEnrollmentOpen(boolean isEnrollmentOpen) {
        this.isEnrollmentOpen = isEnrollmentOpen;
    }

}