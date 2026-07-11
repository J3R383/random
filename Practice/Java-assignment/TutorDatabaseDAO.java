import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TutorDatabaseDAO implements TutorDAO {

    @Override
    public void addTutor(Tutor tutor) throws Exception {
        String sql = "INSERT INTO tutors (tutor_id, first_name, last_name, department) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, tutor.getTutorId());
            pstmt.setString(2, tutor.getFirstName());
            pstmt.setString(3, tutor.getLastName());
            pstmt.setString(4, tutor.getDepartment());
            pstmt.executeUpdate();
        }
    }

    @Override
    public void updateTutor(Tutor tutor) throws Exception {
        String sql = "UPDATE tutors SET first_name = ?, last_name = ?, department = ? WHERE tutor_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, tutor.getFirstName());
            pstmt.setString(2, tutor.getLastName());
            pstmt.setString(3, tutor.getDepartment());
            pstmt.setString(4, tutor.getTutorId());
            pstmt.executeUpdate();
        }
    }

    @Override
    public void deleteTutor(String tutorId) throws Exception {
        String sql = "DELETE FROM tutors WHERE tutor_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, tutorId);
            pstmt.executeUpdate();
        }
    }

    @Override
    public Optional<Tutor> getTutorById(String tutorId) throws Exception {
        String sql = "SELECT * FROM tutors WHERE tutor_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, tutorId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapRowToTutor(rs));
                }
            }
        }
        return Optional.empty();
    }

    @Override
    public List<Tutor> getAllTutors() throws Exception {
        List<Tutor> tutors = new ArrayList<>();
        String sql = "SELECT * FROM tutors";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                tutors.add(mapRowToTutor(rs));
            }
        }
        return tutors;
    }

    @Override
    public List<Tutor> searchTutors(String searchTerm) throws Exception {
        List<Tutor> tutors = new ArrayList<>();
        String sql = "SELECT * FROM tutors WHERE tutor_id LIKE ? OR first_name LIKE ? OR last_name LIKE ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            String likeTerm = "%" + searchTerm + "%";
            pstmt.setString(1, likeTerm);
            pstmt.setString(2, likeTerm);
            pstmt.setString(3, likeTerm);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    tutors.add(mapRowToTutor(rs));
                }
            }
        }
        return tutors;
    }

    private Tutor mapRowToTutor(ResultSet rs) throws SQLException {
        return new Tutor(
                rs.getString("tutor_id"),
                rs.getString("first_name"),
                rs.getString("last_name"),
                rs.getString("department")
        );
    }
}
