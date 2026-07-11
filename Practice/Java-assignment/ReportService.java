
import java.util.List;

public class ReportService {

    private final StudentService studentService;
    private final EnrollmentService enrollmentService;
    private final CourseService courseService;
    private final TutorService tutorService;
    private final ExaminationResultService examinationResultService;
    private final CourseAllocationService courseAllocationService;


    public ReportService() {
        this.studentService = new StudentService();
        this.enrollmentService = new EnrollmentService();
        this.courseService = new CourseService();
        this.tutorService = new TutorService();
        this.examinationResultService = new ExaminationResultService();
        this.courseAllocationService = new CourseAllocationService();
    }

    public String generateStudentTranscript(String studentId) throws Exception {
        StringBuilder report = new StringBuilder();
        studentService.getStudentById(studentId).ifPresent(student -> {
            report.append("Student Academic Transcript\n");
            report.append("===========================\n");
            report.append("Student: ").append(student.getFirstName()).append(" ").append(student.getLastName())
                    .append(" (").append(student.getStudentId()).append(")\n\n");

            try {
                List<Enrollment> enrollments = enrollmentService.getEnrollmentsByStudentId(studentId);
                for (Enrollment enrollment : enrollments) {
                    courseService.getCourseById(enrollment.getCourseId()).ifPresent(course -> {
                        report.append("Course: ").append(course.getCourseName()).append("\n");
                        try {
                            examinationResultService.getExaminationResultById(enrollment.getEnrollmentId()).ifPresent(result -> {
                                report.append("  Score: ").append(result.getScore()).append(", Grade: ").append(result.getGrade()).append("\n");
                            });
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    });
                    report.append("\n");
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        return report.toString();
    }


    public String generateCourseEnrollmentReport(String courseId, int semester, int year) throws Exception {
        StringBuilder report = new StringBuilder();
        courseService.getCourseById(courseId).ifPresent(course -> {
            report.append("Course Enrollment Report\n");
            report.append("========================\n");
            report.append("Course: ").append(course.getCourseName()).append(" (").append(course.getCourseId()).append(")\n");
            report.append("Semester: ").append(semester).append(", Year: ").append(year).append("\n\n");
            report.append("Enrolled Students:\n");

            try {
                List<Enrollment> enrollments = enrollmentService.getAllEnrollments();
                for (Enrollment enrollment : enrollments) {
                    if (enrollment.getCourseId().equals(courseId) && enrollment.getSemester() == semester && enrollment.getYear() == year) {
                        studentService.getStudentById(enrollment.getStudentId()).ifPresent(student -> {
                            report.append("- ").append(student.getFirstName()).append(" ").append(student.getLastName())
                                    .append(" (").append(student.getStudentId()).append(")\n");
                        });
                    }
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        return report.toString();
    }


    public String generateTutorCourseLoadReport(String tutorId, int semester, int year) throws Exception {
        StringBuilder report = new StringBuilder();
        tutorService.getTutorById(tutorId).ifPresent(tutor -> {
            report.append("Tutor Course Load Report\n");
            report.append("========================\n");
            report.append("Tutor: ").append(tutor.getFirstName()).append(" ").append(tutor.getLastName())
                    .append(" (").append(tutor.getTutorId()).append(")\n");
            report.append("Semester: ").append(semester).append(", Year: ").append(year).append("\n\n");
            report.append("Allocated Courses:\n");

            try {
                List<CourseAllocation> allocations = courseAllocationService.getAllCourseAllocations();
                for (CourseAllocation allocation : allocations) {
                    if (allocation.getTutorId().equals(tutorId) && allocation.getSemester() == semester && allocation.getYear() == year) {
                        courseService.getCourseById(allocation.getCourseId()).ifPresent(course -> {
                            report.append("- ").append(course.getCourseName()).append(" (").append(course.getCourseId()).append(")\n");
                        });
                    }
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        return report.toString();
    }


    public String generateGraduationEligibilityReport() throws Exception {
        // This is a placeholder implementation.
        // In a real application, this would involve checking if a student has met all the graduation requirements.
        StringBuilder report = new StringBuilder();
        report.append("Graduation Eligibility Report\n");
        report.append("=============================\n\n");
        report.append("The following students are eligible for graduation:\n");
        report.append("- Student 1 (ID: 12345)\n");
        report.append("- Student 2 (ID: 67890)\n");
        return report.toString();
    }

    public String generateStudentsOnProbationReport() throws Exception {
        // This is a placeholder implementation.
        // In a real application, this would involve checking if a student's academic performance is below a certain threshold.
        StringBuilder report = new StringBuilder();
        report.append("Students on Probation Report\n");
        report.append("============================\n\n");
        report.append("The following students are on probation:\n");
        report.append("- Student 3 (ID: 54321)\n");
        report.append("- Student 4 (ID: 09876)\n");
        return report.toString();
    }
}
