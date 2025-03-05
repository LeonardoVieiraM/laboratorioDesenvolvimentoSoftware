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
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
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
                    registerStudent();
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
    private static void registerStudent() {
        System.out.println("\nCadastro de Aluno:");
        System.out.print("ID: ");
        int id = scanner.nextInt();
        scanner.nextLine(); 
        System.out.print("Senha: ");
        String password = scanner.nextLine();
        System.out.print("Matrícula: ");
        int studentId = scanner.nextInt();
        scanner.nextLine(); 
        System.out.print("Nome: ");
        String name = scanner.nextLine();
        System.out.print("Curso: ");
        String course = scanner.nextLine();

        // Salvar no arquivo users.txt
        boolean userSaved = saveToFile(USERS_FILE, id + "," + password + ",student," + studentId + "," + name + "," + course + "\n");
        if (!userSaved) {
            System.out.println("Erro ao salvar dados do usuário.");
            return;
        }

        // Salvar no arquivo students.txt
        boolean studentSaved = saveToFile(STUDENTS_FILE, studentId + "," + name + "," + course + ",,,\n");
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
                if (userData[0].equals(String.valueOf(id)) && userData[1].equals(password) && userData[2].equals("student")) {
                    System.out.println("Login bem-sucedido!");


                    //funcionalidades do aluno


                    return;
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
    private static void registerProfessor() {
        System.out.println("\nCadastro de Professor:");
        System.out.print("ID: ");
        int id = scanner.nextInt();
        scanner.nextLine(); 
        System.out.print("Senha: ");
        String password = scanner.nextLine();
        System.out.print("ID do Professor: ");
        int professorId = scanner.nextInt();
        scanner.nextLine(); 
        System.out.print("Nome: ");
        String name = scanner.nextLine();

        // Salvar no arquivo users.txt
        boolean userSaved = saveToFile(USERS_FILE, id + "," + password + ",professor," + professorId + "," + name + "\n");
        if (!userSaved) {
            System.out.println("Erro ao salvar dados do usuário.");
            return;
        }

        // Salvar no arquivo professors.txt
        boolean professorSaved = saveToFile(PROFESSORS_FILE, professorId + "," + name + "\n");
        if (!professorSaved) {
            System.out.println("Erro ao salvar dados do professor.");
            return;
        }

        System.out.println("Professor cadastrado com sucesso!");
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
                if (userData[0].equals(String.valueOf(id)) && userData[1].equals(password) && userData[2].equals("professor")) {
                    System.out.println("Login bem-sucedido!");


                    // funcionalidades do professor


                    return;
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
        System.out.print("ID da Secretaria: ");
        int secretariaId = scanner.nextInt();
        scanner.nextLine(); 
        System.out.print("Nome: ");
        String name = scanner.nextLine();

        // Salvar no arquivo users.txt
        boolean userSaved = saveToFile(USERS_FILE, id + "," + password + ",secretaria," + secretariaId + "," + name + "\n");
        if (!userSaved) {
            System.out.println("Erro ao salvar dados do usuário.");
            return;
        }

        // Salvar no arquivo secretaria.txt
        boolean secretariaSaved = saveToFile(SECRETARIA_FILE, secretariaId + "," + name + "\n");
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
                if (userData[0].equals(String.valueOf(id)) && userData[1].equals(password) && userData[2].equals("secretaria")) {
                    System.out.println("Login bem-sucedido!");


                    // funcionalidades da secretaria


                    return;
                }
            }
            System.out.println("ID ou senha incorretos.");
        } catch (IOException e) {
            System.out.println("Erro ao ler arquivo de usuários.");
        }
    }
}