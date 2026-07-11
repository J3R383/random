import java.util.Objects;

/**
 * This class represents a course.
 */
public class Course {
    private String courseId;
    private String courseName;
    private int credits;
    private String department;

    /**
     * Constructs a new Course object.
     * @param courseId The ID of the course.
     * @param courseName The name of the course.
     * @param credits The number of credits for the course.
     * @param department The department offering the course.
     */
    public Course(String courseId, String courseName, int credits, String department) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.credits = credits;
        this.department = department;
    }

    // Getters and Setters for all attributes
    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public int getCredits() {
        return credits;
    }

    public void setCredits(int credits) {
        this.credits = credits;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    /**
     * Returns a string representation of the course.
     * @return A comma-separated string of the course's attributes.
     */
    @Override
    public String toString() {
        return String.join(",", courseId, courseName, String.valueOf(credits), department);
    }

    /**
     * Creates a Course object from a string.
     * @param line The string to parse.
     * @return The created Course object.
     * @throws IllegalArgumentException If the string format is invalid.
     */
    public static Course fromString(String line) {
        String[] parts = line.split(",");
        if (parts.length == 4) {
            return new Course(parts[0], parts[1], Integer.parseInt(parts[2]), parts[3]);
        }
        throw new IllegalArgumentException("Invalid Course data format: " + line);
    }

    /**
     * Checks if this course is equal to another object.
     * @param o The object to compare to.
     * @return True if the objects are equal, false otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Course course = (Course) o;
        return Objects.equals(courseId, course.courseId);
    }

    /**
     * Returns the hash code of the course.
     * @return The hash code.
     */
    @Override
    public int hashCode() {
        return Objects.hash(courseId);
    }
}