import java.util.List;

public class Secretary extends User {
    private String name;

    public Secretary(int id, String password, String name) {
        super(id, password);
        this.name = name;
    }

    private static final String DATA_DIR = "./project/code/data/";
    private static final String SUBJECTS_FILE = DATA_DIR+ "subjects.txt";


    public void createCourse(String name, int credits, List<Course> courses) {
        Course newCourse = new Course(name, credits);
        courses.add(newCourse);
        System.out.println("Curso " + name + " adicionado com sucesso.");
    }

    public void viewCourses(List<Course> courses) {
        System.out.println("Lista de cursos:");
        for (Course course : courses) {
            System.out.println("ID: " + course.getId() + " | Nome: " + course.getName() + " | Créditos: " + course.getCredits());
        }
    }

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

    public void deleteCourse(int courseId, List<Course> courses) {
        courses.removeIf(course -> course.getId() == courseId);
        System.out.println("Curso " + courseId + " excluído com sucesso.");
    }

    public void viewCurriculum(List<Course> courses) {
        System.out.println("Currículo:");
        for (Course course : courses) {
            System.out.println("Curso: " + course.getName() + " | Créditos: " + course.getCredits());
            System.out.println("Disciplinas:");
            for (Subject subject : course.getSubjects()) { // Iterar sobre objetos Subject
                System.out.println("  - Disciplina: " + subject.getName() + " (ID: " + subject.getId() + ")");
            }
        }
    }

    public void viewStudentEnrollments(List<Student> students) {
        System.out.println("Matrículas dos alunos:");
        for (Student student : students) {
            System.out.println("Aluno: " + student.getName() + " | Curso: " + student.getCourse());
            System.out.println("Disciplinas obrigatórias: " + student.getMandatorySubjects());
            System.out.println("Disciplinas optativas: " + student.getOptionalSubjects());
        }
    }

    public Student addStudent(String name, int courseId, List<Student> students, List<Course> courses) {
        if (!Course.courseExistsById(courseId, courses)) {
            System.out.println("Curso não encontrado.");
            return null;
        }
        int newId = students.size() + 1; 
        Student newStudent = new Student(newId, "senha", name, courseId);
        students.add(newStudent);
        System.out.println("Aluno " + name + " adicionado com sucesso.");
        return newStudent;
    }

    public void removeStudent(int studentId, List<Student> students) {
        students.removeIf(student -> student.getId() == studentId);
        System.out.println("Aluno " + studentId + " removido com sucesso.");
    }

    public void editStudent(int studentId, String newName, String newCourseName, List<Student> students, List<Course> courses) {
        int courseId = Course.getCourseIdByName(newCourseName, courses); // Chamada correta
        if (courseId == -1) {
            System.out.println("Curso não encontrado.");
            return;
        }
        for (Student student : students) {
            if (student.getId() == studentId) {
                student.setName(newName);
                student.setCourse(courseId);
                System.out.println("Aluno " + studentId + " editado com sucesso.");
                return;
            }
        }
        System.out.println("Aluno " + studentId + " não encontrado.");
    }

    public Professor addProfessor(String name) {
        System.out.println("Professor " + name + " adicionado com sucesso.");
        return new Professor(0, "senha", name);
    }

    public void removeProfessor(int professorId, List<Professor> professors) {
        professors.removeIf(professor -> professor.getId() == professorId);
        System.out.println("Professor " + professorId + " removido com sucesso.");
    }

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

    public void addSubject(String name, boolean isMandatory, String professor, int courseId, List<Subject> subjects, List<Course> courses) {
        if (!Course.courseExistsById(courseId, courses)) {
            System.out.println("Curso não encontrado.");
            return;
        }
        Subject newSubject = new Subject(subjects.size() + 1, name, isMandatory, professor, courseId);
        subjects.add(newSubject);
        Subject.saveSubjects(subjects, SUBJECTS_FILE); 
        System.out.println("Disciplina " + name + " adicionada com sucesso.");
    }

    public void removeSubject(int subjectId, List<Subject> subjects) {
        subjects.removeIf(subject -> subject.getId() == subjectId);
        System.out.println("Disciplina " + subjectId + " removida com sucesso.");
    }

    public void editSubject(int subjectId, String newName, boolean isMandatory, int maxStudents, String professor, int courseId, List<Subject> subjects) {
        for (Subject subject : subjects) {
            if (subject.getId() == subjectId) {
                subject.setName(newName);
                subject.setMandatory(isMandatory);
                subject.setProfessor(professor);
                subject.setCourseId(courseId); 
                System.out.println("Disciplina " + subjectId + " editada com sucesso.");
                Subject.saveSubjects(subjects, SUBJECTS_FILE); 
                return;
            }
        }
        System.out.println("Disciplina " + subjectId + " não encontrada.");
    }
    

    public void openEnrollmentPeriod(List<Subject> subjects) {
        for (Subject subject : subjects) {
            subject.setEnrollmentOpen(true); // Abre as matrículas para todas as disciplinas
        }
        System.out.println("Período de matrículas aberto.");
    }

    public void closeEnrollmentPeriod(List<Subject> subjects) {
        for (Subject subject : subjects) {
            if (subject.getEnrolledStudents().size() >= 3) {
                subject.setActive(true);
                System.out.println("Disciplina " + subject.getName() + " ativada.");
            } else {
                subject.setActive(false);
                System.out.println("Disciplina " + subject.getName() + " cancelada.");
            }
        }
        System.out.println("Período de matrículas fechado.");
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