import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ExaminationResultDatabaseDAO implements ExaminationResultDAO {

    @Override
    public void addExaminationResult(ExaminationResult result) throws SQLException {
        String sql = "INSERT INTO examination_results (result_id, enrollment_id, score, grade, semester, year) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, result.getResultId());
            pstmt.setString(2, result.getEnrollmentId());
            pstmt.setInt(3, result.getScore());
            pstmt.setString(4, result.getGrade());
            pstmt.setInt(5, result.getSemester());
            pstmt.setInt(6, result.getYear());
            pstmt.executeUpdate();
        }
    }

    @Override
    public void updateExaminationResult(ExaminationResult result) throws SQLException {
        String sql = "UPDATE examination_results SET enrollment_id = ?, score = ?, grade = ?, semester = ?, year = ? WHERE result_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, result.getEnrollmentId());
            pstmt.setInt(2, result.getScore());
            pstmt.setString(3, result.getGrade());
            pstmt.setInt(4, result.getSemester());
            pstmt.setInt(5, result.getYear());
            pstmt.setString(6, result.getResultId());
            pstmt.executeUpdate();
        }
    }

    @Override
    public void deleteExaminationResult(String resultId) throws SQLException {
        String sql = "DELETE FROM examination_results WHERE result_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, resultId);
            pstmt.executeUpdate();
        }
    }

    @Override
    public Optional<ExaminationResult> getExaminationResultById(String resultId) throws SQLException {
        String sql = "SELECT * FROM examination_results WHERE result_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, resultId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapRowToExaminationResult(rs));
                }
            }
        }
        return Optional.empty();
    }

    @Override
    public List<ExaminationResult> getAllExaminationResults() throws SQLException {
        List<ExaminationResult> results = new ArrayList<>();
        String sql = "SELECT * FROM examination_results";
        try (Connection conn = DatabaseConnection.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                results.add(mapRowToExaminationResult(rs));
            }
        }
        return results;
    }

    private ExaminationResult mapRowToExaminationResult(ResultSet rs) throws SQLException {
        return new ExaminationResult(
                rs.getString("result_id"),
                rs.getString("enrollment_id"),
                rs.getInt("score"),
                rs.getString("grade"),
                rs.getInt("semester"),
                rs.getInt("year"));
    }

    @Override
    public Optional<ExaminationResult> getExaminationResultByEnrollmentId(String enrollmentId) throws SQLException {
        String sql = "SELECT * FROM examination_results WHERE enrollment_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, enrollmentId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapRowToExaminationResult(rs));
                }
            }
        }
        return Optional.empty();
    }

    @Override
    public List<ExaminationResult> searchExaminationResults(String searchTerm) throws SQLException {
        List<ExaminationResult> results = new ArrayList<>();
        String sql = "SELECT * FROM examination_results WHERE result_id LIKE ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            String likeTerm = "%" + searchTerm + "%";
            pstmt.setString(1, likeTerm);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    results.add(mapRowToExaminationResult(rs));
                }
            }
        }
        return results;
    }
}
