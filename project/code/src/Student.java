import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Student extends User {
    private String name;
    private int courseId; 
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

    public void enrollSubject(int subjectId, List<Subject> subjects, BillingSystem billingSystem) {
        Subject subject = findSubjectById(subjectId, subjects);
        if (subject == null) {
            System.out.println("Disciplina não encontrada.");
            return;
        }
    
        if (subject.isMandatory()) {
            if (mandatorySubjects.size() < 4) {
                mandatorySubjects.add(subjectId);
                billingSystem.notifyPayment(this.id); // Notifica o sistema de cobrança
                paymentPending = true;
                //updateStudentFile();
                System.out.println("Disciplina obrigatória " + subjectId + " adicionada com sucesso.");
            } else {
                System.out.println("Limite de disciplinas obrigatórias atingido.");
            }
        } else {
            if (optionalSubjects.size() < 2) {
                optionalSubjects.add(subjectId);
                billingSystem.notifyPayment(this.id); // Notifica o sistema de cobrança
                paymentPending = true;
                //updateStudentFile();
                System.out.println("Disciplina optativa " + subjectId + " adicionada com sucesso.");
            } else {
                System.out.println("Limite de disciplinas optativas atingido.");
            }
        }
    }

    public void cancelEnrollment(int subjectId) {
        if (mandatorySubjects.remove(Integer.valueOf(subjectId))) {
            System.out.println("Disciplina obrigatória " + subjectId + " removida com sucesso.");
        } else if (optionalSubjects.remove(Integer.valueOf(subjectId))) {
            System.out.println("Disciplina optativa " + subjectId + " removida com sucesso.");
        } else {
            System.out.println("Disciplina " + subjectId + " não encontrada.");
        }
    }

    public void makePayment() {
        if (paymentPending == true) {
            System.out.println("Há um pagamento pendente. Deseja realizá-lo? (s/n)");
            Scanner scanner = new Scanner(System.in);
            String resposta = scanner.nextLine();
            //scanner.close(); Fechar o scanner buga o código
            if (resposta.equalsIgnoreCase("s")) {
                this.paymentPending = false;
                System.out.println("Pagamento realizado com sucesso.");
            } else {
                System.out.println("Pagamento cancelado.");
            }
        } else {
            System.out.println("Não há pagamento pendente para o aluno.");
        }
    }

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

    public static void saveStudents(List<Student> students, String filePath) {
    try (FileWriter writer = new FileWriter(filePath)) {
        for (Student student : students) {
            writer.write(student.getId() + "," + student.getName() + "," + student.getCourse() + "\n");
        }
    } catch (IOException e) {
        System.out.println("Erro ao salvar alunos: " + e.getMessage());
    }
}

public static List<Student> loadStudents(String filePath) {
    List<Student> students = new ArrayList<>();
    try (Scanner scanner = new Scanner(new File(filePath))) {
        while (scanner.hasNextLine()) {
            String[] data = scanner.nextLine().split(",");
            int id = Integer.parseInt(data[0]);
            String name = data[1];
            int courseId = Integer.parseInt(data[2]);
            students.add(new Student(id, "senha", name, courseId));
        }
    } catch (IOException e) {
        System.out.println("Erro ao carregar alunos: " + e.getMessage());
    }
    return students;
}

/*private void updateStudentFile() {
    int alunoId = getId();

    try {
    PrintWriter writer = new PrintWriter(new FileWriter("students.txt"));
    writer.println(alunoId + "," +this.name + "," + this.courseId + "," + this.mandatorySubjects + "," + this.optionalSubjects + "," + this.paymentPending);
    }
    catch (IOException e) {
        System.out.println("Erro ao atualizar o arquivo students.txt: " + e.getMessage());
    }
}*/

}