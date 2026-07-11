import java.util.List;
import java.util.Optional;

/**
 * This class provides services related to academic progression.
 * It is responsible for determining a student's academic status based on their performance.
 */
public class AcademicProgressionService {

    private final StudentDAO studentDAO;
    private final CourseDAO courseDAO;
    private final EnrollmentDAO enrollmentDAO;
    private final ExaminationResultDAO examinationResultDAO;

    public AcademicProgressionService(StudentDAO studentDAO, CourseDAO courseDAO, EnrollmentDAO enrollmentDAO, ExaminationResultDAO examinationResultDAO) {
        this.studentDAO = studentDAO;
        this.courseDAO = courseDAO;
        this.enrollmentDAO = enrollmentDAO;
        this.examinationResultDAO = examinationResultDAO;
    }

    /**
     * Checks if a student is eligible to register for the next semester.
     * A student must be enrolled in at least 4 courses.
     * A student cannot register for a course they have already passed.
     *
     * @param studentId The ID of the student.
     * @param courseIds The list of course IDs the student wants to register for.
     * @return true if the student can register, false otherwise.
     * @throws Exception if an error occurs.
     */
    public boolean canRegisterForNextSemester(String studentId, List<String> courseIds) throws Exception {
        if (courseIds.size() < 4) {
            return false;
        }

        List<Enrollment> enrollments = enrollmentDAO.getEnrollmentsByStudentId(studentId);
        for (Enrollment enrollment : enrollments) {
            Optional<ExaminationResult> result = examinationResultDAO.getExaminationResultByEnrollmentId(enrollment.getEnrollmentId());
            if (result.isPresent() && (result.get().getScore() > 40 || "ABCD".contains(result.get().getGrade()))) {
                if (courseIds.contains(enrollment.getCourseId())) {
                    return false; // Already passed this course
                }
            }
        }

        return true;
    }

    /**
     * Promotes a student to the next semester and year if they have passed all their courses.
     * A student must have passed at least 6 courses in the current year.
     *
     * @param studentId The ID of the student.
     * @throws Exception if an error occurs.
     */
    public void promoteStudent(String studentId) throws Exception {
        Optional<Student> studentOptional = studentDAO.getStudentById(studentId);
        if (studentOptional.isPresent()) {
            Student student = studentOptional.get();
            List<Enrollment> enrollments = enrollmentDAO.getEnrollmentsByStudentIdAndYear(studentId, student.getCurrentYear());

            if (enrollments.size() < 6) {
                return; // Not enough courses
            }

            int passedCourses = 0;
            for (Enrollment enrollment : enrollments) {
                Optional<ExaminationResult> result = examinationResultDAO.getExaminationResultByEnrollmentId(enrollment.getEnrollmentId());
                if (result.isPresent() && (result.get().getScore() > 40 || "ABCD".contains(result.get().getGrade()))) {
                    passedCourses++;
                }
            }

            if (passedCourses == enrollments.size()) {
                student.setCurrentSemester(student.getCurrentSemester() + 1);
                if (student.getCurrentSemester() > 2) {
                    student.setCurrentSemester(1);
                    student.setCurrentYear(student.getCurrentYear() + 1);
                }
                studentDAO.updateStudent(student);
            }
        }
    }

    /**
     * Checks if a student has met the graduation requirements and updates their status if they have.
     * A student must have accumulated at least 120 credits.
     *
     * @param studentId The ID of the student.
     * @throws Exception if an error occurs.
     */
    public void clearForGraduation(String studentId) throws Exception {
        Optional<Student> studentOptional = studentDAO.getStudentById(studentId);
        if (studentOptional.isPresent()) {
            Student student = studentOptional.get();
            List<Enrollment> enrollments = enrollmentDAO.getEnrollmentsByStudentId(studentId);

            int totalCredits = 0;
            for (Enrollment enrollment : enrollments) {
                Optional<ExaminationResult> result = examinationResultDAO.getExaminationResultByEnrollmentId(enrollment.getEnrollmentId());
                if (result.isPresent() && (result.get().getScore() > 40 || "ABCD".contains(result.get().getGrade()))) {
                    Optional<Course> course = courseDAO.getCourseById(enrollment.getCourseId());
                    if (course.isPresent()) {
                        totalCredits += course.get().getCredits();
                    }
                }
            }

            if (totalCredits >= 120) {
                student.setAcademicStatus("Cleared for Graduation");
                studentDAO.updateStudent(student);
            }
        }
    }
}
