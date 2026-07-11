import java.util.List;
import java.util.Optional;

public interface EnrollmentDAO {
    void addEnrollment(Enrollment enrollment) throws Exception;

    void updateEnrollment(Enrollment enrollment) throws Exception;

    void deleteEnrollment(String enrollmentId) throws Exception;

    Optional<Enrollment> getEnrollmentById(String enrollmentId) throws Exception;

    List<Enrollment> getAllEnrollments() throws Exception;

    List<Enrollment> getEnrollmentsByStudentId(String studentId) throws Exception;

    List<Enrollment> getEnrollmentsByStudentIdAndYear(String studentId, int year) throws Exception;

    List<Enrollment> searchEnrollments(String searchTerm) throws Exception;
}
