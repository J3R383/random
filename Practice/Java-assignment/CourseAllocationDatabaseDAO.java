import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CourseAllocationDatabaseDAO implements CourseAllocationDAO {

    @Override
    public void addCourseAllocation(CourseAllocation allocation) throws SQLException {
        String sql = "INSERT INTO course_allocations (allocation_id, course_id, tutor_id, semester, year) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, allocation.getAllocationId());
            pstmt.setString(2, allocation.getCourseId());
            pstmt.setString(3, allocation.getTutorId());
            pstmt.setInt(4, allocation.getSemester());
            pstmt.setInt(5, allocation.getYear());
            pstmt.executeUpdate();
        }
    }

    @Override
    public void updateCourseAllocation(CourseAllocation allocation) throws SQLException {
        String sql = "UPDATE course_allocations SET course_id = ?, tutor_id = ?, semester = ?, year = ? WHERE allocation_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, allocation.getCourseId());
            pstmt.setString(2, allocation.getTutorId());
            pstmt.setInt(3, allocation.getSemester());
            pstmt.setInt(4, allocation.getYear());
            pstmt.setString(5, allocation.getAllocationId());
            pstmt.executeUpdate();
        }
    }

    @Override
    public void deleteCourseAllocation(String allocationId) throws SQLException {
        String sql = "DELETE FROM course_allocations WHERE allocation_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, allocationId);
            pstmt.executeUpdate();
        }
    }

    @Override
    public Optional<CourseAllocation> getCourseAllocationById(String allocationId) throws SQLException {
        String sql = "SELECT * FROM course_allocations WHERE allocation_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, allocationId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapRowToCourseAllocation(rs));
                }
            }
        }
        return Optional.empty();
    }

    @Override
    public List<CourseAllocation> getAllCourseAllocations() throws SQLException {
        List<CourseAllocation> allocations = new ArrayList<>();
        String sql = "SELECT * FROM course_allocations";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                allocations.add(mapRowToCourseAllocation(rs));
            }
        }
        return allocations;
    }

    @Override
    public List<CourseAllocation> searchCourseAllocations(String searchTerm) throws SQLException {
        List<CourseAllocation> allocations = new ArrayList<>();
        String sql = "SELECT * FROM course_allocations WHERE allocation_id LIKE ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            String likeTerm = "%" + searchTerm + "%";
            pstmt.setString(1, likeTerm);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    allocations.add(mapRowToCourseAllocation(rs));
                }
            }
        }
        return allocations;
    }

    private CourseAllocation mapRowToCourseAllocation(ResultSet rs) throws SQLException {
        return new CourseAllocation(
                rs.getString("allocation_id"),
                rs.getString("course_id"),
                rs.getString("tutor_id"),
                rs.getInt("semester"),
                rs.getInt("year")
        );
    }
}
