import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Subject {
    private int id;
    private String name;
    private boolean isOptional;
    private int maxStudents;
    private List<Integer> enrolledStudents;
    private boolean isEnrollmentOpen;
    private String professor;
    private boolean isMandatory;
    private int courseId; // Campo para associar a disciplina a um curso

    // Construtor para disciplinas sem curso associado (opcional)
    public Subject(int id, String name, boolean isOptional, int maxStudents, String professor) {
        this.id = id;
        this.name = name;
        this.isOptional = isOptional;
        this.maxStudents = maxStudents;
        this.enrolledStudents = new ArrayList<>();
        this.isEnrollmentOpen = true;
        this.professor = professor;
    }

    // Construtor para disciplinas com curso associado
    public Subject(int id, String name, boolean isMandatory, int maxStudents, String professor, int courseId) {
        this.id = id;
        this.name = name;
        this.isMandatory = isMandatory;
        this.maxStudents = maxStudents;
        this.professor = professor;
        this.courseId = courseId;
    }

    // Método para obter o ID do curso associado
    public int getCourseId() {
        return courseId;
    }

    // Método para definir o ID do curso associado
    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    // Método para definir o nome da disciplina
    public void setName(String name) {
        this.name = name;
    }

    // Método para definir se a disciplina é obrigatória
    public void setMandatory(boolean isMandatory) {
        this.isMandatory = isMandatory;
    }

    // Método para definir o número máximo de alunos
    public void setMaxStudents(int maxStudents) {
        this.maxStudents = maxStudents;
    }

    // Método para definir o professor responsável
    public void setProfessor(String professor) {
        this.professor = professor;
    }

    // Método para verificar se a disciplina é obrigatória
    public boolean isMandatory() {
        return !isOptional; // Se não é optativa, é obrigatória
    }

    // Método para verificar se a matrícula está aberta
    public boolean isEnrollmentOpen() {
        return isEnrollmentOpen;
    }

    // Método para fechar a matrícula
    public void closeEnrollment() {
        isEnrollmentOpen = false;
    }

    // Método para matricular um aluno na disciplina
    public void enrollStudent(int studentId) {
        if (enrolledStudents.size() < maxStudents) {
            enrolledStudents.add(studentId);
            System.out.println("Aluno " + studentId + " matriculado na disciplina " + name + ".");
        } else {
            System.out.println("Limite de alunos atingido na disciplina " + name + ".");
        }
    }

    // Método para remover um aluno da disciplina
    public void removeStudent(int studentId) {
        if (enrolledStudents.remove(Integer.valueOf(studentId))) {
            System.out.println("Aluno " + studentId + " removido da disciplina " + name + ".");
        } else {
            System.out.println("Aluno " + studentId + " não encontrado na disciplina " + name + ".");
        }
    }

    // Método para obter a lista de alunos matriculados
    public List<Integer> getEnrolledStudents() {
        return enrolledStudents;
    }

    // Método para obter o ID da disciplina
    public int getId() {
        return id;
    }

    // Método para obter o número máximo de alunos
    public int getMaxStudents() {
        return maxStudents;
    }

    // Método para obter o nome da disciplina
    public String getName() {
        return name;
    }

    // Método para obter o professor responsável
    public String getProfessor() {
        return professor;
    }

    // Método para carregar disciplinas do arquivo
    public static List<Subject> loadSubjects(String filePath) {
        List<Subject> subjects = new ArrayList<>();
        try (Scanner scanner = new Scanner(new File(filePath))) {
            while (scanner.hasNextLine()) {
                String[] data = scanner.nextLine().split(",");
                int id = Integer.parseInt(data[0]);
                String name = data[1];
                boolean isMandatory = Boolean.parseBoolean(data[2]);
                int maxStudents = Integer.parseInt(data[3]);
                String professor = data[4];
                int courseId = Integer.parseInt(data[5]);
                subjects.add(new Subject(id, name, isMandatory, maxStudents, professor, courseId));
            }
        } catch (IOException e) {
            System.out.println("Erro ao carregar disciplinas: " + e.getMessage());
        }
        return subjects;
    }

    // Método para salvar disciplinas no arquivo
    public static void saveSubjects(List<Subject> subjects, String filePath) {
        try (FileWriter writer = new FileWriter(filePath)) {
            for (Subject subject : subjects) {
                writer.write(subject.getId() + "," + subject.getName() + "," + subject.isMandatory() + "," +
                        subject.getMaxStudents() + "," + subject.getProfessor() + "," + subject.getCourseId() + "\n");
            }
        } catch (IOException e) {
            System.out.println("Erro ao salvar disciplinas: " + e.getMessage());
        }
    }
}