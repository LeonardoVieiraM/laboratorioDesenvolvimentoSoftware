import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Professor extends User {
    private String name;

    public Professor(int id, String password, String name) {
        super(id, password);
        this.name = name;
    }

    public int getId() {
        return this.id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // Método para visualizar alunos matriculados em uma disciplina
    public void viewEnrolledStudents(int subjectId, List<Student> students) {
        System.out.println("Alunos matriculados na disciplina " + subjectId + ":");
        for (Student student : students) {
            if (student.getMandatorySubjects().contains(subjectId)
                    || student.getOptionalSubjects().contains(subjectId)) {
                System.out.println(student.getName());
            }
        }
    }

    public static List<Professor> loadProfessors(String filePath) {
        List<Professor> professors = new ArrayList<>();
        try (Scanner scanner = new Scanner(new File(filePath))) {
            while (scanner.hasNextLine()) {
                String[] data = scanner.nextLine().split(",");
                int id = Integer.parseInt(data[0]);
                String name = data[1];
                professors.add(new Professor(id, "senha", name));
            }
        } catch (IOException e) {
            System.out.println("Erro ao carregar professores: " + e.getMessage());
        }
        return professors;
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