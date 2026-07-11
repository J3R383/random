import java.util.List;
import java.util.Optional;

public class EnrollmentService {
    private final EnrollmentDAO enrollmentDAO;

    public EnrollmentService() {
        this.enrollmentDAO = new EnrollmentDatabaseDAO();
    }

    public void addEnrollment(Enrollment enrollment) throws Exception {
        enrollmentDAO.addEnrollment(enrollment);
    }

    public void updateEnrollment(Enrollment enrollment) throws Exception {
        enrollmentDAO.updateEnrollment(enrollment);
    }

    public void deleteEnrollment(String enrollmentId) throws Exception {
        enrollmentDAO.deleteEnrollment(enrollmentId);
    }

    public Optional<Enrollment> getEnrollmentById(String enrollmentId) throws Exception {
        return enrollmentDAO.getEnrollmentById(enrollmentId);
    }

    public List<Enrollment> getAllEnrollments() throws Exception {
        return enrollmentDAO.getAllEnrollments();
    }

    public List<Enrollment> getEnrollmentsByStudentId(String studentId) throws Exception {
        return enrollmentDAO.getEnrollmentsByStudentId(studentId);
    }

    public List<Enrollment> searchEnrollments(String searchTerm) throws Exception {
        return enrollmentDAO.searchEnrollments(searchTerm);
    }
}
