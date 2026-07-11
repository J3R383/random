import java.time.LocalDate;
import java.util.Objects;

public class Student {
    private String studentId;
    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
    private String major;
    private int currentSemester;
    private int currentYear;
    private String academicStatus;

    // Default Constructor
    public Student() {
    }

    // Full Parameterized Constructor
    public Student(String studentId, String firstName, String lastName,
                   LocalDate dateOfBirth, String major, int currentSemester, int currentYear,
            String academicStatus) {
        this.studentId = studentId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.major = major;
        this.currentSemester = currentSemester;
        this.currentYear = currentYear;
        this.academicStatus = academicStatus;
    }

    // --- Getters and Setters ---

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    } // Fixed: No longer throws exception

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }


    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getMajor() {
        return major;
    } // Fixed: No longer throws exception

    public void setMajor(String major) {
        this.major = major;
    }

    public int getCurrentSemester() {
        return currentSemester;
    }

    public void setCurrentSemester(int currentSemester) {
        this.currentSemester = currentSemester;
    }

    public int getCurrentYear() {
        return currentYear;
    }

    public void setCurrentYear(int currentYear) {
        this.currentYear = currentYear;
    }

    public String getAcademicStatus() {
        return academicStatus;
    }

    public void setAcademicStatus(String academicStatus) {
        this.academicStatus = academicStatus;
    }

    // --- Helper Methods ---

    @Override
    public String toString() {
        // Included email in the CSV string representation
        return String.join(",",
                studentId, firstName, lastName,
                dateOfBirth.toString(), major,
                String.valueOf(currentSemester),
                String.valueOf(currentYear), academicStatus);
    }

    public static Student fromString(String line) {
        String[] parts = line.split(",");
        // Updated to 9 parts to include the email field
        if (parts.length == 9) {
            return new Student(
                    parts[0], // studentId
                    parts[1], // firstName
                    parts[2], // lastName
                    LocalDate.parse(parts[4]), // dateOfBirth
                    parts[5], // major
                    Integer.parseInt(parts[6]), // currentSemester
                    Integer.parseInt(parts[7]), // currentYear
                    parts[8] // academicStatus
            );
        }
        throw new IllegalArgumentException(
                "Invalid Student data format. Expected 9 fields, got " + parts.length + ": " + line);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Student student = (Student) o;
        return Objects.equals(studentId, student.studentId); // Identity based on ID
    }

    @Override
    public int hashCode() {
        return Objects.hash(studentId);
    }
}