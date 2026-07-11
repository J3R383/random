import java.util.List;
import java.util.Optional;

public class CourseService {
    private final CourseDAO courseDAO;

    public CourseService() {
        this.courseDAO = new CourseDatabaseDAO();
    }

    public void addCourse(Course course) throws Exception {
        courseDAO.addCourse(course);
    }

    public void updateCourse(Course course) throws Exception {
        courseDAO.updateCourse(course);
    }

    public void deleteCourse(String courseId) throws Exception {
        courseDAO.deleteCourse(courseId);
    }

    public Optional<Course> getCourseById(String courseId) throws Exception {
        return courseDAO.getCourseById(courseId);
    }

    public List<Course> getAllCourses() throws Exception {
        return courseDAO.getAllCourses();
    }

    public List<Course> searchCourses(String searchTerm) throws Exception {
        return courseDAO.searchCourses(searchTerm);
    }
}
