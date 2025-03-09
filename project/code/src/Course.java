import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Course {
    private static int nextId = 1;
    private int id;
    private String name;
    private int credits;
    private List<Subject> subjects; // Lista de disciplinas

    public Course(String name, int credits) {
        this.id = nextId++;
        this.name = name;
        this.credits = credits;
        this.subjects = new ArrayList<>();
    }

    public Course(int id, String name, int credits) {
        this.id = id;
        this.name = name;
        this.credits = credits;
        this.subjects = new ArrayList<>();
    }

    // Métodos para adicionar/remover disciplinas
    public void addSubject(Subject subject) {
        subjects.add(subject);
    }

    public void removeSubject(Subject subject) {
        subjects.remove(subject);
    }

    // Getters e Setters
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getCredits() {
        return credits;
    }

    public List<Subject> getSubjects() {
        return subjects;
    }

    public static int getNextId() {
        return nextId;
    }

    public void setCredits(int credits) {
        this.credits = credits;
    }

    public void setId(int id) {
        this.id = id;
    }
    public void setName(String name) {
        this.name = name;
    }
    public static void setNextId(int nextId) {
        Course.nextId = nextId;
    }
    public void setSubjects(List<Subject> subjects) {
        this.subjects = subjects;
    }

    // Método para salvar cursos em arquivo
    public static void saveCourses(List<Course> courses, String filePath) {
        try (FileWriter writer = new FileWriter(filePath)) {
            for (Course course : courses) {
                writer.write(course.getId() + "," + course.getName() + "," + course.getCredits() + "\n");
            }
        } catch (IOException e) {
            System.out.println("Erro ao salvar cursos: " + e.getMessage());
        }
    }

    // Método para carregar cursos do arquivo
    public static List<Course> loadCourses(String filePath) {
        List<Course> courses = new ArrayList<>();
        try (Scanner scanner = new Scanner(new File(filePath))) {
            while (scanner.hasNextLine()) {
                String[] data = scanner.nextLine().split(",");
                int id = Integer.parseInt(data[0]);
                String name = data[1];
                int credits = Integer.parseInt(data[2]);
                courses.add(new Course(id, name, credits));
            }
        } catch (IOException e) {
            System.out.println("Erro ao carregar cursos: " + e.getMessage());
        }
        return courses;
    }

    public static boolean courseExistsById(int courseId, List<Course> courses) {
        for (Course course : courses) {
            if (course.getId() == courseId) {
                return true;
            }
        }
        return false;
    }

    public static int getCourseIdByName(String courseName, List<Course> courses) {
        for (Course course : courses) {
            if (course.getName().equalsIgnoreCase(courseName)) {
                return course.getId();
            }
        }
        return -1; // Retorna -1 se o curso não for encontrado
    }

}