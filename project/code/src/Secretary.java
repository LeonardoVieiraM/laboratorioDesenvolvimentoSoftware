import java.util.List;

public class Secretary extends User {
    private String name;

    public Secretary(int id, String password, String name) {
        super(id, password);
        this.name = name;
    }

    private static final String DATA_DIR = "./project/code/data/";
    private static final String SUBJECTS_FILE = DATA_DIR+ "subjects.txt";


    // Método para adicionar um curso
    public void createCourse(String name, int credits, List<Course> courses) {
        Course newCourse = new Course(name, credits);
        courses.add(newCourse);
        System.out.println("Curso " + name + " adicionado com sucesso.");
    }

    // Método para visualizar todos os cursos
    public void viewCourses(List<Course> courses) {
        System.out.println("Lista de cursos:");
        for (Course course : courses) {
            System.out.println("ID: " + course.getId() + " | Nome: " + course.getName() + " | Créditos: " + course.getCredits());
        }
    }

    // Método para atualizar um curso
    public void updateCourse(int courseId, String newName, int newCredits, List<Course> courses) {
        for (Course course : courses) {
            if (course.getId() == courseId) {
                course.setName(newName);
                course.setCredits(newCredits);
                System.out.println("Curso " + courseId + " atualizado com sucesso.");
                return;
            }
        }
        System.out.println("Curso " + courseId + " não encontrado.");
    }

    // Método para excluir um curso
    public void deleteCourse(int courseId, List<Course> courses) {
        courses.removeIf(course -> course.getId() == courseId);
        System.out.println("Curso " + courseId + " excluído com sucesso.");
    }

    // Método para visualizar o currículo
    public void viewCurriculum(List<Course> courses) {
        System.out.println("Currículo:");
        for (Course course : courses) {
            System.out.println("Curso: " + course.getName() + " | Créditos: " + course.getCredits());
            System.out.println("Disciplinas:");
            for (int subjectId : course.getSubjects()) {
                System.out.println("  - Disciplina ID: " + subjectId);
            }
        }
    }

    // Método para visualizar as matrículas dos alunos
    public void viewStudentEnrollments(List<Student> students) {
        System.out.println("Matrículas dos alunos:");
        for (Student student : students) {
            System.out.println("Aluno: " + student.getName() + " | Curso: " + student.getCourse());
            System.out.println("Disciplinas obrigatórias: " + student.getMandatorySubjects());
            System.out.println("Disciplinas optativas: " + student.getOptionalSubjects());
        }
    }

    // Método para adicionar um aluno
    public Student addStudent(String name, String course, List<Course> courses) {
        if (!courseExists(course, courses)) {
            System.out.println("Curso não encontrado.");
            return null;
        }
        System.out.println("Aluno " + name + " adicionado com sucesso.");
        return new Student(0, "senha", name, course);
    }

    // Método para remover um aluno
    public void removeStudent(int studentId, List<Student> students) {
        students.removeIf(student -> student.getId() == studentId);
        System.out.println("Aluno " + studentId + " removido com sucesso.");
    }

    // Método para editar um aluno
    public void editStudent(int studentId, String newName, String newCourse, List<Student> students) {
        for (Student student : students) {
            if (student.getId() == studentId) {
                student.setName(newName);
                student.setCourse(newCourse);
                System.out.println("Aluno " + studentId + " editado com sucesso.");
                return;
            }
        }
        System.out.println("Aluno " + studentId + " não encontrado.");
    }

    // Método para adicionar um professor
    public Professor addProfessor(String name) {
        System.out.println("Professor " + name + " adicionado com sucesso.");
        return new Professor(0, "senha", name);
    }

    // Método para remover um professor
    public void removeProfessor(int professorId, List<Professor> professors) {
        professors.removeIf(professor -> professor.getId() == professorId);
        System.out.println("Professor " + professorId + " removido com sucesso.");
    }

    // Método para editar um professor
    public void editProfessor(int professorId, String newName, List<Professor> professors) {
        for (Professor professor : professors) {
            if (professor.getId() == professorId) {
                professor.setName(newName);
                System.out.println("Professor " + professorId + " editado com sucesso.");
                return;
            }
        }
        System.out.println("Professor " + professorId + " não encontrado.");
    }

    // Método para adicionar uma disciplina
    public void addSubject(String name, boolean isMandatory, int maxStudents, String professor, int courseId, List<Subject> subjects, List<Course> courses) {
        if (!Course.courseExistsById(courseId, courses)) {
            System.out.println("Curso não encontrado.");
            return;
        }
        Subject newSubject = new Subject(subjects.size() + 1, name, isMandatory, maxStudents, professor, courseId);
        subjects.add(newSubject);
        Subject.saveSubjects(subjects, SUBJECTS_FILE); 
        System.out.println("Disciplina " + name + " adicionada com sucesso.");
    }

    // Método para remover uma disciplina
    public void removeSubject(int subjectId, List<Subject> subjects) {
        subjects.removeIf(subject -> subject.getId() == subjectId);
        System.out.println("Disciplina " + subjectId + " removida com sucesso.");
    }

    public void editSubject(int subjectId, String newName, boolean isMandatory, int maxStudents, String professor, int courseId, List<Subject> subjects) {
        for (Subject subject : subjects) {
            if (subject.getId() == subjectId) {
                subject.setName(newName);
                subject.setMandatory(isMandatory);
                subject.setMaxStudents(maxStudents);
                subject.setProfessor(professor);
                subject.setCourseId(courseId); // Atualiza o ID do curso associado
                System.out.println("Disciplina " + subjectId + " editada com sucesso.");
                Subject.saveSubjects(subjects, SUBJECTS_FILE); // Salva as disciplinas no arquivo
                return;
            }
        }
        System.out.println("Disciplina " + subjectId + " não encontrada.");
    }
    

    // Método para abrir o período de matrículas
    public void openEnrollmentPeriod() {
        System.out.println("Período de matrículas aberto.");
    }

    // Método para fechar o período de matrículas
    public void closeEnrollmentPeriod() {
        System.out.println("Período de matrículas fechado.");
    }

    // Método auxiliar para verificar se um curso existe
    private boolean courseExists(String courseName, List<Course> courses) {
        for (Course course : courses) {
            if (course.getName().equals(courseName)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void login(int id, String password) {
        if (this.id == id && this.password.equals(password)) {
            System.out.println("Login da secretaria " + name + " realizado com sucesso.");
        } else {
            System.out.println("ID ou senha incorretos.");
        }
    }
}