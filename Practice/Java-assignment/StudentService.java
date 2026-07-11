import java.util.List;
import java.util.Optional;

public class StudentService {
    private final StudentDAO studentDAO;

    public StudentService() {
        // Here we can choose which DAO to use.
        // For now, we'll use the database DAO.
        // In a real application, this might be configured via a properties file
        // or a dependency injection framework.
        this.studentDAO = new StudentDatabaseDAO();
        // this.studentDAO = new StudentFileDAO();
    }

    public void addStudent(Student student) throws Exception {
        // Additional business logic can be added here, e.g., validation
        studentDAO.addStudent(student);
    }

    public void updateStudent(Student student) throws Exception {
        // Additional business logic can be added here
        studentDAO.updateStudent(student);
    }

    public void deleteStudent(String studentId) throws Exception {
        studentDAO.deleteStudent(studentId);
    }

    public Optional<Student> getStudentById(String studentId) throws Exception {
        return studentDAO.getStudentById(studentId);
    }

    public List<Student> getAllStudents() throws Exception {
        return studentDAO.getAllStudents();
    }

    public List<Student> searchStudents(String searchTerm) throws Exception {
        return studentDAO.searchStudents(searchTerm);
    }
}
