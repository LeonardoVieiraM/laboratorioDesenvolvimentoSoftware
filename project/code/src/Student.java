import java.io.*;
import java.nio.file.Files;
import java.util.*;
import java.util.regex.*;

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
    
    public String getName() {
        return name;
    }

    public int getCourse() {
        return courseId;
    }

    public List<Integer> getMandatorySubjects() {
        return mandatorySubjects;
    }

    public List<Integer> getOptionalSubjects() {
        return optionalSubjects;    
    }

    public boolean getPaymentPending() {
        return paymentPending;
    }   

    public void setName(String name) {
        this.name = name;
    }

    public void setCourse(int courseId) {
        this.courseId = courseId;
    }

    public void setMandatorySubjects(List<Integer> mandatorySubjects) {
        this.mandatorySubjects = new ArrayList<>(mandatorySubjects);
    }
    
    public void setOptionalSubjects(List<Integer> optionalSubjects) {
        this.optionalSubjects = new ArrayList<>(optionalSubjects);
    }
    
    public void setPaymentPending(boolean paymentPending) {
        this.paymentPending = paymentPending;
    }
    

    @Override
    public void login(int id, String password) {
        if (this.id == id && this.password.equals(password)) {
            System.out.println("Login do aluno " + name + " realizado com sucesso.");
        } else {
            System.out.println("ID ou senha incorretos.");
        }
    }

    public void enrollSubject(int subjectId, List<Subject> allSubjects, BillingSystem billingSystem) {
        boolean isMandatory = false;
        int IdDisciplina = -1;
    
        // Percorre a lista de disciplinas para encontrar o ID e verificar se é obrigatória ou opcional
        for (Subject subject : allSubjects) {
            if (subject.getId() == subjectId) {
                IdDisciplina = subject.getId();
                isMandatory = subject.isMandatory();
                break;
            }
        }
    
        if (IdDisciplina == -1) {
            System.out.println("Erro: Disciplina não encontrada.");
            return;
        }
    
        if (isMandatory) {
            if (mandatorySubjects.size() < 4 && !mandatorySubjects.contains(IdDisciplina)) {
                mandatorySubjects.add(IdDisciplina);
            } else {
                System.out.println("Erro: O aluno já tem o máximo de 4 disciplinas obrigatórias ou já está matriculado nessa obrigatória.");
                return;
            }
        } else {
            if (optionalSubjects.size() < 2 && !optionalSubjects.contains(IdDisciplina)) {
                optionalSubjects.add(IdDisciplina);
            } else {
                System.out.println("Erro: O aluno já tem o máximo de 2 disciplinas opcionais ou já está matriculado nessa opcional.");
                return;
            }
        }
    
        updateStudentFile();
    }
    
    
    

    private void updateStudentFile() {
        try {
            File file = new File("./project/code/data/students.txt");
            List<String> lines = new ArrayList<>(Files.readAllLines(file.toPath()));
    
            for (int i = 0; i < lines.size(); i++) {
                String[] data = lines.get(i).split(",(?=(?:[^\\[]*\\[[^\\]]*\\])*[^\\[]*$)"); // Respeita os colchetes
                
                if (Integer.parseInt(data[0]) == this.id) {
                    // Mantém as listas no formato correto, preservando os colchetes
                    data[3] = mandatorySubjects.toString().replace(" ", "");
                    data[4] = optionalSubjects.toString().replace(" ", "");
    
                    lines.set(i, String.join(",", data));
                    break;
                }
            }
    
            Files.write(file.toPath(), lines);
        } catch (IOException e) {
            System.out.println("Erro ao atualizar arquivo de alunos: " + e.getMessage());
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

    /*private Subject findSubjectById(int subjectId, List<Subject> subjects) {
        for (Subject subject : subjects) {
            if (subject.getId() == subjectId) {
                return subject;
            }
        }
        return null;
    }*/

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
            String line = scanner.nextLine();

            // Expressão regular para evitar divisão incorreta dentro de colchetes []
            String regex = ",(?=(?:[^\\[]*\\[[^\\]]*\\])*[^\\[]*$)";
            String[] data = line.split(regex);

            // Verifica se a linha tem pelo menos 6 elementos
            if (data.length < 6) {
                System.out.println("Erro: Linha mal formatada -> " + Arrays.toString(data));
                continue; // Ignora essa linha
            }

            int id = Integer.parseInt(data[0].trim());
            String name = data[1].trim();
            int courseId = Integer.parseInt(data[2].trim());

            // Converte os subjects
            List<Integer> mandatorySubjects = extractSubjects(line);
            List<Integer> optionalSubjects = extractSubjects(line.substring(line.indexOf("],") + 2));
            

            boolean paymentPending = Boolean.parseBoolean(data[5].trim());

            // Criar e adicionar aluno à lista
            Student student = new Student(id, "senha", name, courseId);
            student.setMandatorySubjects(mandatorySubjects);
            student.setOptionalSubjects(optionalSubjects);
            student.setPaymentPending(paymentPending);
            //System.out.println(optionalSubjects);

            students.add(student);
        }
    } catch (IOException e) {
        System.out.println("Erro ao carregar alunos: " + e.getMessage());
    }
    
    return students;
}

private static List<Integer> extractSubjects(String subjectString) {
    List<Integer> subjects = new ArrayList<>();

    // Verifica se a string é vazia ou apenas contém colchetes vazios
    if (subjectString == null || subjectString.trim().equals("[]")) {
        return subjects; // Retorna lista vazia sem tentar converter nada
    }

    // Remove colchetes e espaços extras
    subjectString = subjectString.replaceAll("[\\[\\] ]", "");

    // Se a string estiver vazia após a remoção dos colchetes, retorna uma lista vazia
    if (subjectString.isEmpty()) {
        return subjects;
    }

    // Divide os números e adiciona à lista
    String[] parts = subjectString.split(",");
    for (String part : parts) {
        try {
            subjects.add(Integer.parseInt(part));
        } catch (NumberFormatException e) {
            System.out.println("Erro ao converter disciplina: '" + part + "'");
        }
    }

    return subjects;
}



// Método auxiliar para converter lista de String para List<Integer>
/*private static List<Integer> parseSubjectList(String listString) {
    List<Integer> subjects = new ArrayList<>();

    listString = listString.replaceAll("[\\[\\]]", "").trim(); // Remove os colchetes
    System.out.println("Parsing subjects: " + listString); // Depuração

    if (!listString.isEmpty()) {
        String[] items = listString.split("\\s*,\\s*"); // Divide pelos números
        for (String item : items) {
            subjects.add(Integer.parseInt(item));
        }
    }
    return subjects;
}*/



/*private void updateStudentFile() {
    int alunoId = getId();
    File inputFile = new File("./project/code/data/students.txt");
    File tempFile = new File("./project/code/data/students_temp.txt");

    try (BufferedReader reader = new BufferedReader(new FileReader(inputFile));
         PrintWriter writer = new PrintWriter(new FileWriter(tempFile))) {

        String currentLine;
        while ((currentLine = reader.readLine()) != null) {
            String[] data = currentLine.split(",");

            // Verifica se a linha corresponde ao aluno atual pelo ID
            if (Integer.parseInt(data[0]) == alunoId) {
                // Substitui a linha pelo novo registro do aluno
                currentLine = alunoId + "," + this.name + "," + this.courseId + "," +
                              this.mandatorySubjects + "," + this.optionalSubjects + "," + this.paymentPending;
            }

            writer.println(currentLine);
        }

    } catch (IOException e) {
        System.out.println("Erro ao atualizar o arquivo students.txt: " + e.getMessage());
        return;
    }

    // Substitui o arquivo original pelo atualizado
    if (!inputFile.delete()) {
        System.out.println("Erro ao deletar o arquivo original.");
        return;
    }
    if (!tempFile.renameTo(inputFile)) {
        System.out.println("Erro ao renomear o arquivo temporário.");
    }
}*/


}