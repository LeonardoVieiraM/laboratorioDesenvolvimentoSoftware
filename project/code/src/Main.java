import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.io.FileWriter;
import java.io.File;
import java.io.IOException;

public class Main {
    private static final String DATA_DIR = "./project/code/data/";
    private static final String USERS_FILE = DATA_DIR + "users.txt";
    private static final String STUDENTS_FILE = DATA_DIR + "students.txt";
    private static final String PROFESSORS_FILE = DATA_DIR + "professors.txt";
    private static final String SECRETARIA_FILE = DATA_DIR + "secretaria.txt";
    private static final String COURSES_FILE = DATA_DIR + "courses.txt";
    private static final String SUBJECTS_FILE = DATA_DIR + "subjects.txt";

    private static Scanner scanner = new Scanner(System.in);

    private static List<Student> students = new ArrayList<>();
    private static List<Professor> professors = new ArrayList<>();
    private static List<Subject> subjects = new ArrayList<>();
    private static List<Course> courses = new ArrayList<>();
    private static BillingSystem billingSystem = new BillingSystem();

    public static void main(String[] args) {
        loadInitialData();

        while (true) {
            System.out.println("Bem-vindo ao Sistema de Matrícula da Universidade!");
            System.out.println("Escolha uma opção:");
            System.out.println("1 - Aluno");
            System.out.println("2 - Professor");
            System.out.println("3 - Secretaria");
            System.out.println("0 - Sair");
            System.out.print("Opção: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    handleStudentMenu();
                    break;
                case 2:
                    handleProfessorMenu();
                    break;
                case 3:
                    handleSecretariaMenu();
                    break;
                case 0:
                    System.out.println("Saindo do sistema...");
                    return;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        }
    }

    private static void loadInitialData() {
        courses = Course.loadCourses(COURSES_FILE);
        subjects = Subject.loadSubjects(SUBJECTS_FILE);
        professors = Professor.loadProfessors(PROFESSORS_FILE);

        for (Subject subject : subjects) {
            Course course = courses.stream()
                    .filter(c -> c.getId() == subject.getCourseId())
                    .findFirst()
                    .orElse(null);
            if (course != null) {
                course.addSubject(subject);
            }
        }

        if (courses.isEmpty()) {
            courses.add(new Course("Ciência da Computação", 120));
            courses.add(new Course("Engenharia de Software", 120));
        }
        if (subjects.isEmpty()) {
            subjects.add(new Subject(1, "Programação I", false, "Dr. Silva", 1));
            subjects.add(new Subject(2, "Banco de Dados", true, "Dr. Souza", 1));
        }
    }

    // Menu do Aluno
    private static void handleStudentMenu() {
        while (true) {
            System.out.println("\nMenu do Aluno:");
            System.out.println("1 - Cadastrar");
            System.out.println("2 - Login");
            System.out.println("0 - Voltar");
            System.out.print("Opção: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    registerStudent(courses);
                    break;
                case 2:
                    loginStudent();
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        }
    }

    // Cadastro de Aluno
    private static void registerStudent(List<Course> courses) {
        System.out.println("\nCadastro de Aluno:");

        // Exibir lista de cursos cadastrados
        System.out.println("Cursos disponíveis:");
        for (Course course : courses) {
            System.out.println("ID: " + course.getId() + " | Nome: " + course.getName());
        }

        // Solicitar ID do curso
        System.out.print("Selecione o ID do curso: ");
        int courseId = scanner.nextInt();
        scanner.nextLine();

        // Verificar se o curso existe
        if (!Course.courseExistsById(courseId, courses)) {
            System.out.println("Curso não encontrado.");
            return;
        }

        // Solicitar nome e senha do aluno
        System.out.print("Nome do aluno: ");
        String name = scanner.nextLine();
        System.out.print("Senha do aluno: ");
        String password = scanner.nextLine();

        // Gerar ID automático para o aluno
        int newId = students.size() + 1;

        // Criar e adicionar o aluno à lista
        Student newStudent = new Student(newId, password, name, courseId);
        students.add(newStudent);

        // Salvar no arquivo users.txt
        boolean userSaved = saveToFile(USERS_FILE, newId + "," + password + ",student," + name + "," + courseId + "\n");
        if (!userSaved) {
            System.out.println("Erro ao salvar dados do usuário.");
            return;
        }

        // Salvar no arquivo students.txt
        boolean studentSaved = saveToFile(STUDENTS_FILE, newId + "," + name + "," + courseId + ",,,\n");
        if (!studentSaved) {
            System.out.println("Erro ao salvar dados do aluno.");
            return;
        }

        System.out.println("Aluno cadastrado com sucesso!");
    }

    // Método genérico para salvar dados em um arquivo
    private static boolean saveToFile(String fileName, String data) {
        try (FileWriter writer = new FileWriter(fileName, true)) {
            writer.write(data);
            return true;
        } catch (IOException e) {
            System.out.println("Erro ao acessar o arquivo: " + fileName);
            return false;
        }
    }

    // Login de Aluno
    private static void loginStudent() {
        System.out.println("\nLogin de Aluno:");
        System.out.print("ID: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Senha: ");
        String password = scanner.nextLine();

        // Verificar credenciais no arquivo users.txt
        try (Scanner fileScanner = new Scanner(new File(USERS_FILE))) {
            while (fileScanner.hasNextLine()) {
                String[] userData = fileScanner.nextLine().split(",");
                if (userData[0].equals(String.valueOf(id)) && userData[1].equals(password)
                        && userData[2].equals("student")) {
                    System.out.println("Login bem-sucedido!");

                    // Criar objeto Student
                    String name = userData[3];
                    int courseId = Integer.parseInt(userData[4]);
                    Student student = new Student(id, password, name, courseId);

                    // Menu de funcionalidades do aluno
                    while (true) {
                        System.out.println("\nMenu do Aluno:");
                        System.out.println("1 - Matricular em disciplina");
                        System.out.println("2 - Cancelar matrícula");
                        System.out.println("3 - Realizar pagamento");
                        System.out.println("0 - Sair");
                        System.out.print("Opção: ");
                        int choice = scanner.nextInt();
                        scanner.nextLine();

                        switch (choice) {
                            case 1:
                                System.out.print("ID da disciplina: ");
                                int subjectId = scanner.nextInt();
                                scanner.nextLine();
                                student.enrollSubject(subjectId, subjects, billingSystem); // Passa o BillingSystem
                                break;
                            case 2:
                                System.out.print("ID da disciplina: ");
                                subjectId = scanner.nextInt();
                                scanner.nextLine();
                                student.cancelEnrollment(subjectId);
                                break;
                            case 3:
                                student.makePayment();
                                break;
                            case 0:
                                return;
                            default:
                                System.out.println("Opção inválida. Tente novamente.");
                        }
                    }
                }
            }
            System.out.println("ID ou senha incorretos.");
        } catch (IOException e) {
            System.out.println("Erro ao ler arquivo de usuários.");
        }
    }

    // Menu do Professor
    private static void handleProfessorMenu() {
        while (true) {
            System.out.println("\nMenu do Professor:");
            System.out.println("1 - Cadastrar");
            System.out.println("2 - Login");
            System.out.println("0 - Voltar");
            System.out.print("Opção: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    registerProfessor();
                    break;
                case 2:
                    loginProfessor();
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        }
    }

    // Cadastro de Professor
    public static void registerProfessor() {
        System.out.println("\nCadastro de Professor:");
        System.out.print("Nome: ");
        String name = scanner.nextLine();

        // Gerar ID automático para o professor
        int newId = professors.size() + 1;

        // Criar e adicionar o professor à lista
        Professor newProfessor = new Professor(newId, "senha", name);
        professors.add(newProfessor);

        // Salvar no arquivo users.txt
        boolean userSaved = saveToFile(USERS_FILE, newId + ",senha,professor," + name + "\n");
        if (!userSaved) {
            System.out.println("Erro ao salvar dados do usuário.");
            return;
        }

        // Salvar no arquivo professors.txt
        boolean professorSaved = saveToFile(PROFESSORS_FILE, newId + "," + name + "\n");
        if (!professorSaved) {
            System.out.println("Erro ao salvar dados do professor.");
            return;
        }

        System.out.println("Professor " + name + " adicionado com sucesso.");
    }

    // Login de Professor
    private static void loginProfessor() {
        System.out.println("\nLogin de Professor:");
        System.out.print("ID: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Senha: ");
        String password = scanner.nextLine();

        // Verificar credenciais no arquivo users.txt
        try (Scanner fileScanner = new Scanner(new File(USERS_FILE))) {
            while (fileScanner.hasNextLine()) {
                String[] userData = fileScanner.nextLine().split(",");
                if (userData[0].equals(String.valueOf(id)) && userData[1].equals(password)
                        && userData[2].equals("professor")) {
                    System.out.println("Login bem-sucedido!");

                    // Criar objeto Professor
                    String name = userData[3];
                    Professor professor = new Professor(id, password, name);

                    // Menu de funcionalidades do professor
                    while (true) {
                        System.out.println("\nMenu do Professor:");
                        System.out.println("1 - Visualizar alunos matriculados");
                        System.out.println("0 - Sair");
                        System.out.print("Opção: ");
                        int choice = scanner.nextInt();
                        scanner.nextLine();

                        switch (choice) {
                            case 1:
                                System.out.print("ID da disciplina: ");
                                int subjectId = scanner.nextInt();
                                scanner.nextLine();
                                professor.viewEnrolledStudents(subjectId, students);
                                break;
                            case 0:
                                return;
                            default:
                                System.out.println("Opção inválida. Tente novamente.");
                        }
                    }
                }
            }
            System.out.println("ID ou senha incorretos.");
        } catch (IOException e) {
            System.out.println("Erro ao ler arquivo de usuários.");
        }
    }

    // Menu da Secretaria
    private static void handleSecretariaMenu() {
        while (true) {
            System.out.println("\nMenu da Secretaria:");
            System.out.println("1 - Cadastrar");
            System.out.println("2 - Login");
            System.out.println("0 - Voltar");
            System.out.print("Opção: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    registerSecretaria();
                    break;
                case 2:
                    loginSecretaria();
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        }
    }

    // Cadastro de Secretaria
    private static void registerSecretaria() {
        System.out.println("\nCadastro de Secretaria:");
        System.out.print("ID: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Senha: ");
        String password = scanner.nextLine();
        System.out.print("Nome: ");
        String name = scanner.nextLine();

        // Salvar no arquivo users.txt
        boolean userSaved = saveToFile(USERS_FILE, id + "," + password + ",secretaria," + name + "\n");
        if (!userSaved) {
            System.out.println("Erro ao salvar dados do usuário.");
            return;
        }

        // Salvar no arquivo secretaria.txt
        boolean secretariaSaved = saveToFile(SECRETARIA_FILE, id + "," + name + "\n");
        if (!secretariaSaved) {
            System.out.println("Erro ao salvar dados da secretaria.");
            return;
        }

        System.out.println("Secretaria cadastrada com sucesso!");
    }

    // Login de Secretaria
    private static void loginSecretaria() {
        System.out.println("\nLogin de Secretaria:");
        System.out.print("ID: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Senha: ");
        String password = scanner.nextLine();

        // Verificar credenciais no arquivo users.txt
        try (Scanner fileScanner = new Scanner(new File(USERS_FILE))) {
            while (fileScanner.hasNextLine()) {
                String[] userData = fileScanner.nextLine().split(",");
                if (userData[0].equals(String.valueOf(id)) && userData[1].equals(password)
                        && userData[2].equals("secretaria")) {
                    System.out.println("Login bem-sucedido!");

                    // Criar objeto Secretaria
                    String name = userData[3];
                    Secretary secretaria = new Secretary(id, password, name);

                    // Menu de funcionalidades da secretaria
                    while (true) {
                        System.out.println("\nMenu da Secretaria:");
                        System.out.println("1 - Visualizar currículo");
                        System.out.println("2 - Visualizar matrículas dos alunos");
                        System.out.println("3 - Adicionar aluno");
                        System.out.println("4 - Remover aluno");
                        System.out.println("5 - Editar aluno");
                        System.out.println("6 - Adicionar professor");
                        System.out.println("7 - Remover professor");
                        System.out.println("8 - Editar professor");
                        System.out.println("9 - Adicionar disciplina");
                        System.out.println("10 - Remover disciplina");
                        System.out.println("11 - Editar disciplina");
                        System.out.println("12 - Abrir período de matrículas");
                        System.out.println("13 - Fechar período de matrículas");
                        System.out.println("14 - Adicionar curso");
                        System.out.println("15 - Visualizar cursos");
                        System.out.println("16 - Atualizar curso");
                        System.out.println("17 - Excluir curso");
                        System.out.println("0 - Sair");
                        System.out.print("Opção: ");
                        int choice = scanner.nextInt();
                        scanner.nextLine();

                        switch (choice) {
                            case 1:
                                secretaria.viewCurriculum(courses);
                                break;
                            case 2:
                                secretaria.viewStudentEnrollments(students);
                                break;
                            case 3:
                                System.out.print("Nome do aluno: ");
                                String studentName = scanner.nextLine();
                                System.out.print("Curso do aluno (ID): ");
                                int courseId = scanner.nextInt();
                                scanner.nextLine();
                                secretaria.addStudent(studentName, courseId, students, courses);
                                break;
                            case 4:
                                System.out.print("ID do aluno: ");
                                int studentId = scanner.nextInt();
                                scanner.nextLine();
                                secretaria.removeStudent(studentId, students);
                                break;
                            case 5:
                                System.out.print("ID do aluno: ");
                                studentId = scanner.nextInt();
                                scanner.nextLine();
                                System.out.print("Novo nome: ");
                                String newName = scanner.nextLine();
                                System.out.print("Novo curso: ");
                                String newCourse = scanner.nextLine();
                                secretaria.editStudent(studentId, newName, newCourse, students, courses);
                                break;
                            case 6:
                                System.out.print("Nome do professor: ");
                                String professorName = scanner.nextLine();
                                secretaria.addProfessor(professorName);
                                break;
                            case 7:
                                System.out.print("ID do professor: ");
                                int professorId = scanner.nextInt();
                                scanner.nextLine();
                                secretaria.removeProfessor(professorId, professors);
                                break;
                            case 8:
                                System.out.print("ID do professor: ");
                                professorId = scanner.nextInt();
                                scanner.nextLine();
                                System.out.print("Novo nome: ");
                                newName = scanner.nextLine();
                                secretaria.editProfessor(professorId, newName, professors);
                                break;
                            case 9:
                                System.out.print("Nome da disciplina: ");
                                String subjectName = scanner.nextLine();
                                System.out.print("É obrigatória? (true/false): ");
                                boolean isMandatory = scanner.nextBoolean();
                                scanner.nextLine();
                                System.out.println("Professores disponíveis:");
                                for (Professor professor : professors) {
                                    System.out.println("ID: " + professor.getId() + " | Nome: " + professor.getName());
                                }
                                System.out.print("Selecione o nome do professor responsável: ");
                                professorName = scanner.nextLine();
                                System.out.println("Cursos disponíveis:");
                                for (Course course : courses) {
                                    System.out.println("ID: " + course.getId() + " | Nome: " + course.getName());
                                }
                                System.out.print("Selecione o ID do curso associado: ");
                                courseId = scanner.nextInt();
                                scanner.nextLine();
                                if (!Course.courseExistsById(courseId, courses)) {
                                    System.out.println(
                                            "Curso não encontrado. Crie o curso antes de adicionar a disciplina.");
                                } else {
                                    Secretary.addSubject(subjectName, isMandatory, professorName, courseId, subjects,
                                            courses);
                                    Subject.saveSubjects(subjects, SUBJECTS_FILE);
                                }
                                break;
                            case 10:
                                System.out.print("ID da disciplina: ");
                                int subjectId = scanner.nextInt();
                                scanner.nextLine();
                                secretaria.removeSubject(subjectId, subjects);
                                Subject.saveSubjects(subjects, SUBJECTS_FILE);
                                break;
                            case 11:
                                System.out.print("ID da disciplina: ");
                                subjectId = scanner.nextInt();
                                scanner.nextLine();
                                System.out.print("Novo nome: ");
                                newName = scanner.nextLine();
                                System.out.print("É obrigatória? (true/false): ");
                                isMandatory = scanner.nextBoolean();
                                scanner.nextLine();
                                System.out.println("Professores disponíveis:");
                                for (Professor professor : professors) {
                                    System.out.println("ID: " + professor.getId() + " | Nome: " + professor.getName());
                                }
                                System.out.print("Selecione o nome do professor responsável: ");
                                professorName = scanner.nextLine();
                                scanner.nextLine();
                                System.out.println("Cursos disponíveis:");
                                for (Course course : courses) {
                                    System.out.println("ID: " + course.getId() + " | Nome: " + course.getName());
                                }
                                System.out.print("Selecione o ID do curso associado: ");
                                courseId = scanner.nextInt();
                                scanner.nextLine();
                                scanner.nextLine();
                                if (!Course.courseExistsById(courseId, courses)) {
                                    System.out.println(
                                            "Curso não encontrado. Crie o curso antes de editar a disciplina.");
                                } else {
                                    secretaria.editSubject(subjectId, newName, isMandatory, professorName,
                                            courseId, subjects);
                                    Subject.saveSubjects(subjects, SUBJECTS_FILE);
                                }
                                break;
                            case 12:
                                secretaria.openEnrollmentPeriod(subjects);
                                break;
                            case 13:
                                secretaria.closeEnrollmentPeriod(subjects);
                                break;
                            case 14:
                                System.out.print("Nome do curso: ");
                                String courseName = scanner.nextLine();
                                System.out.print("Créditos do curso: ");
                                int credits = scanner.nextInt();
                                scanner.nextLine();
                                secretaria.createCourse(courseName, credits, courses);
                                Course.saveCourses(courses, COURSES_FILE);
                                break;
                            case 15:
                                secretaria.viewCourses(courses);
                                break;
                            case 16:
                                System.out.print("ID do curso: ");
                                courseId = scanner.nextInt();
                                scanner.nextLine();
                                System.out.print("Novo nome: ");
                                String newCourseName = scanner.nextLine();
                                System.out.print("Novos créditos: ");
                                int newCredits = scanner.nextInt();
                                scanner.nextLine();
                                secretaria.updateCourse(courseId, newCourseName, newCredits, courses);
                                Course.saveCourses(courses, COURSES_FILE);
                                break;
                            case 17:
                                System.out.print("ID do curso: ");
                                courseId = scanner.nextInt();
                                scanner.nextLine();
                                secretaria.deleteCourse(courseId, courses);
                                Course.saveCourses(courses, COURSES_FILE);
                                break;
                            case 0:
                                return;
                            default:
                                System.out.println("Opção inválida. Tente novamente.");
                        }
                    }
                }
            }
            System.out.println("ID ou senha incorretos.");
        } catch (IOException e) {
            System.out.println("Erro ao ler arquivo de usuários.");
        }
    }
}