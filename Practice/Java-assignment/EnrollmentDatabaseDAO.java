import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class EnrollmentDatabaseDAO implements EnrollmentDAO {

    @Override
    public void addEnrollment(Enrollment enrollment) throws SQLException {
        String sql = "INSERT INTO enrollments (enrollment_id, student_id, course_id, semester, year, enrollment_date) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, enrollment.getEnrollmentId());
            pstmt.setString(2, enrollment.getStudentId());
            pstmt.setString(3, enrollment.getCourseId());
            pstmt.setInt(4, enrollment.getSemester());
            pstmt.setInt(5, enrollment.getYear());
            pstmt.setDate(6, Date.valueOf(enrollment.getEnrollmentDate()));
            pstmt.executeUpdate();
        }
    }

    @Override
    public void updateEnrollment(Enrollment enrollment) throws SQLException {
        String sql = "UPDATE enrollments SET student_id = ?, course_id = ?, semester = ?, year = ?, enrollment_date = ? WHERE enrollment_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, enrollment.getStudentId());
            pstmt.setString(2, enrollment.getCourseId());
            pstmt.setInt(3, enrollment.getSemester());
            pstmt.setInt(4, enrollment.getYear());
            pstmt.setDate(5, Date.valueOf(enrollment.getEnrollmentDate()));
            pstmt.setString(6, enrollment.getEnrollmentId());
            pstmt.executeUpdate();
        }
    }

    @Override
    public void deleteEnrollment(String enrollmentId) throws SQLException {
        String sql = "DELETE FROM enrollments WHERE enrollment_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, enrollmentId);
            pstmt.executeUpdate();
        }
    }

    @Override
    public Optional<Enrollment> getEnrollmentById(String enrollmentId) throws SQLException {
        String sql = "SELECT * FROM enrollments WHERE enrollment_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, enrollmentId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapRowToEnrollment(rs));
                }
            }
        }
        return Optional.empty();
    }

    @Override
    public List<Enrollment> getAllEnrollments() throws SQLException {
        List<Enrollment> enrollments = new ArrayList<>();
        String sql = "SELECT * FROM enrollments";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                enrollments.add(mapRowToEnrollment(rs));
            }
        }
        return enrollments;
    }

    private Enrollment mapRowToEnrollment(ResultSet rs) throws SQLException {
        return new Enrollment(
                rs.getString("enrollment_id"),
                rs.getString("student_id"),
                rs.getString("course_id"),
                rs.getInt("semester"),
                rs.getInt("year"),
                rs.getDate("enrollment_date").toLocalDate()
        );
    }

    @Override
    public List<Enrollment> getEnrollmentsByStudentId(String studentId) throws SQLException {
        List<Enrollment> enrollments = new ArrayList<>();
        String sql = "SELECT * FROM enrollments WHERE student_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, studentId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    enrollments.add(mapRowToEnrollment(rs));
                }
            }
        }
        return enrollments;
    }

    @Override
    public List<Enrollment> getEnrollmentsByStudentIdAndYear(String studentId, int year) throws SQLException {
        List<Enrollment> enrollments = new ArrayList<>();
        String sql = "SELECT * FROM enrollments WHERE student_id = ? AND year = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, studentId);
            pstmt.setInt(2, year);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    enrollments.add(mapRowToEnrollment(rs));
                }
            }
        }
        return enrollments;
    }

    @Override
    public List<Enrollment> searchEnrollments(String searchTerm) throws SQLException {
        List<Enrollment> enrollments = new ArrayList<>();
        String sql = "SELECT * FROM enrollments WHERE enrollment_id LIKE ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            String likeTerm = "%" + searchTerm + "%";
            pstmt.setString(1, likeTerm);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    enrollments.add(mapRowToEnrollment(rs));
                }
            }
        }
        return enrollments;
    }
}
