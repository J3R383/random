import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * This class provides a database implementation of the CourseDAO interface.
 * It handles all database operations related to courses.
 */
public class CourseDatabaseDAO implements CourseDAO {

    /**
     * Adds a new course to the database.
     * @param course The course to add.
     * @throws SQLException If a database error occurs.
     */
    @Override
    public void addCourse(Course course) throws SQLException {
        //imsert a new course into the table
        String sql = "INSERT INTO courses (course_id, course_name, credits, department) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, course.getCourseId());
            pstmt.setString(2, course.getCourseName());
            pstmt.setInt(3, course.getCredits());
            pstmt.setString(4, course.getDepartment());
            pstmt.executeUpdate();
        }
    }

    /**
     * Updates an existing course in the database.
     * @param course The course to update.
     * @throws SQLException If a database error occurs.
     */
    @Override
    public void updateCourse(Course course) throws SQLException {
        //update course details
        String sql = "UPDATE courses SET course_name = ?, credits = ?, department = ? WHERE course_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, course.getCourseName());
            pstmt.setInt(2, course.getCredits());
            pstmt.setString(3, course.getDepartment());
            pstmt.setString(4, course.getCourseId());
            pstmt.executeUpdate();
        }
    }

    /**
     * Deletes a course from the database.
     * @param courseId The ID of the course to delete.
     * @throws SQLException If a database error occurs.
     */
    @Override
    public void deleteCourse(String courseId) throws SQLException {
        //deleting courses from the table
        String sql = "DELETE FROM courses WHERE course_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, courseId);
            pstmt.executeUpdate();
        }
    }

    /**
     * Retrieves a course from the database by its ID.
     * @param courseId The ID of the course to retrieve.
     * @return An Optional containing the course if found, otherwise empty.
     * @throws SQLException If a database error occurs.
     */
    @Override
    public Optional<Course> getCourseById(String courseId) throws SQLException {
        //searching for courses on the table
        String sql = "SELECT * FROM courses WHERE course_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, courseId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapRowToCourse(rs));
                }
            }
        }
        return Optional.empty();
    }

    /**
     * Retrieves all courses from the database.
     * @return A list of all courses.
     * @throws SQLException If a database error occurs.
     */
    @Override
    public List<Course> getAllCourses() throws SQLException {
        List<Course> courses = new ArrayList<>();
        String sql = "SELECT * FROM courses";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                courses.add(mapRowToCourse(rs));
            }
        }
        return courses;
    }

    /**
     * Searches for courses by ID or name.
     * @param searchTerm The term to search for.
     * @return A list of courses matching the search term.
     * @throws SQLException If a database error occurs.
     */
    @Override
    public List<Course> searchCourses(String searchTerm) throws SQLException {
        List<Course> courses = new ArrayList<>();
        String sql = "SELECT * FROM courses WHERE course_id LIKE ? OR course_name LIKE ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            String likeTerm = "%" + searchTerm + "%";
            pstmt.setString(1, likeTerm);
            pstmt.setString(2, likeTerm);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    courses.add(mapRowToCourse(rs));
                }
            }
        }
        return courses;
    }

    /**
     * Maps a row from the ResultSet to a Course object.
     * @param rs The ResultSet to map.
     * @return The mapped Course object.
     * @throws SQLException If a database error occurs.
     */
    private Course mapRowToCourse(ResultSet rs) throws SQLException {
        return new Course(
                rs.getString("course_id"),
                rs.getString("course_name"),
                rs.getInt("credits"),
                rs.getString("department")
        );
    }
}
