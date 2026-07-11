import java.util.Objects;

/**
 * This class represents the allocation of a course to a tutor for a specific semester and year.
 */
public class CourseAllocation {
    private String allocationId;
    private String courseId;
    private String tutorId;
    private int semester;
    private int year;

    /**
     * Constructs a new CourseAllocation object.
     * @param allocationId The ID of the allocation.
     * @param courseId The ID of the course.
     * @param tutorId The ID of the tutor.
     * @param semester The semester of the allocation.
     * @param year The year of the allocation.
     */
    public CourseAllocation(String allocationId, String courseId, String tutorId, int semester, int year) {
        this.allocationId = allocationId;
        this.courseId = courseId;
        this.tutorId = tutorId;
        this.semester = semester;
        this.year = year;
    }

    // Getters and Setters
    public String getAllocationId() {
        return allocationId;
    }

    public void setAllocationId(String allocationId) {
        this.allocationId = allocationId;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getTutorId() {
        return tutorId;
    }

    public void setTutorId(String tutorId) {
        this.tutorId = tutorId;
    }

    public int getSemester() {
        return semester;
    }

    public void setSemester(int semester) {
        this.semester = semester;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    /**
     * Returns a string representation of the course allocation.
     * @return A comma-separated string of the course allocation's attributes.
     */
    @Override
    public String toString() {
        return String.join(",", allocationId, courseId, tutorId,
                String.valueOf(semester), String.valueOf(year));
    }

    /**
     * Creates a CourseAllocation object from a string.
     * @param line The string to parse.
     * @return The created CourseAllocation object.
     * @throws IllegalArgumentException If the string format is invalid.
     */
    public static CourseAllocation fromString(String line) {
        String[] parts = line.split(",");
        if (parts.length == 5) {
            return new CourseAllocation(parts[0], parts[1], parts[2],
                    Integer.parseInt(parts[3]), Integer.parseInt(parts[4]));
        }
        throw new IllegalArgumentException("Invalid CourseAllocation data format: " + line);
    }

    /**
     * Checks if this course allocation is equal to another object.
     * @param o The object to compare to.
     * @return True if the objects are equal, false otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        CourseAllocation that = (CourseAllocation) o;
        return Objects.equals(allocationId, that.allocationId);
    }

    /**
     * Returns the hash code of the course allocation.
     * @return The hash code.
     */
    @Override
    public int hashCode() {
        return Objects.hash(allocationId);
    }
}