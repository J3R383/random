import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * This class provides a database implementation of the StudentDAO interface.
 * It handles all database operations related to students.
 */
public class StudentDatabaseDAO implements StudentDAO {

    /**
     * Adds a new student to the database.
     * @param student The student to add.
     * @throws SQLException If a database error occurs.
     */
    @Override
    public void addStudent(Student student) throws SQLException {
        // insert a new record into the students table
        String sql = "INSERT INTO students (student_id, first_name, last_name, major) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, student.getStudentId());
            pstmt.setString(2, student.getFirstName());
            pstmt.setString(3, student.getLastName());
            pstmt.setString(4, student.getMajor());
            pstmt.executeUpdate();
        }
    }

    /**
     * Updates an existing student in the database.
     * @param student The student to update.
     * @throws SQLException If a database error occurs.
     */
    @Override
    public void updateStudent(Student student) throws SQLException {
        // updates student details in the table
        String sql = "UPDATE students SET first_name = ?, last_name = ?, major = ? WHERE student_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, student.getFirstName());
            pstmt.setString(2, student.getLastName());
            pstmt.setString(3, student.getMajor());
            pstmt.setString(4, student.getStudentId());
            pstmt.executeUpdate();
        }
    }

    /**
     * Deletes a student from the database.
     * @param studentId The ID of the student to delete.
     * @throws SQLException If a database error occurs.
     */
    @Override
    public void deleteStudent(String studentId) throws SQLException {
        // deletes student from the table
        String sql = "DELETE FROM students WHERE student_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, studentId);
            pstmt.executeUpdate();
        }
    }

    /**
     * Retrieves a student from the database by their ID.
     * @param studentId The ID of the student to retrieve.
     * @return An Optional containing the student if found, otherwise empty.
     * @throws SQLException If a database error occurs.
     */
    @Override
    public Optional<Student> getStudentById(String studentId) throws SQLException {
        // searching for student using studentID
        String sql = "SELECT * FROM students WHERE student_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, studentId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapRowToStudent(rs));
                }
            }
        }
        return Optional.empty();
    }

    /**
     * Retrieves all students from the database.
     * @return A list of all students.
     * @throws SQLException If a database error occurs.
     */
    @Override
    public List<Student> getAllStudents() throws SQLException {
        List<Student> students = new ArrayList<>();
        String sql = "SELECT * FROM students";
        try (Connection conn = DatabaseConnection.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                students.add(mapRowToStudent(rs));
            }
        }
        return students;
    }

    /**
     * Counts the number of passed courses for a student in a given semester and year.
     * @param studentId The ID of the student.
     * @param semester The semester.
     * @param year The year.
     * @return The number of passed courses.
     * @throws Exception If an error occurs.
     */
    @Override
    public int countPassedCourses(String studentId, int semester, int year) throws Exception {
        // This should be implemented in a more sophisticated way, maybe in another DAO
        return 0;
    }

    /**
     * Checks if a student has met the graduation requirements.
     * @param studentId The ID of the student.
     * @return True if the student has met the requirements, false otherwise.
     * @throws Exception If an error occurs.
     */
    @Override
    public boolean hasMetGraduationRequirements(String studentId) throws Exception {
        // This should be implemented in a more sophisticated way, maybe in another DAO
        return false;
    }

    /**
     * Searches for students by ID, first name, or last name.
     * @param searchTerm The term to search for.
     * @return A list of students matching the search term.
     * @throws SQLException If a database error occurs.
     */
    @Override
    public List<Student> searchStudents(String searchTerm) throws SQLException {
        List<Student> students = new ArrayList<>();
        String sql = "SELECT * FROM students WHERE student_id LIKE ? OR first_name LIKE ? OR last_name LIKE ?";
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            String likeTerm = "%" + searchTerm + "%";
            pstmt.setString(1, likeTerm);
            pstmt.setString(2, likeTerm);
            pstmt.setString(3, likeTerm);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    students.add(mapRowToStudent(rs));
                }
            }
        }
        return students;
    }

    /**
     * Maps a row from the ResultSet to a Student object.
     * @param rs The ResultSet to map.
     * @return The mapped Student object.
     * @throws SQLException If a database error occurs.
     */
    private Student mapRowToStudent(ResultSet rs) throws SQLException {
        Student student = new Student();
        student.setStudentId(rs.getString("student_id"));
        student.setFirstName(rs.getString("first_name"));
        student.setLastName(rs.getString("last_name"));
        student.setMajor(rs.getString("major"));
        return student;
    }
}
