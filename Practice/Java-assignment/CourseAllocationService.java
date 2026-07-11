import java.util.List;
import java.util.Optional;

public class CourseAllocationService {
    private final CourseAllocationDAO courseAllocationDAO;

    public CourseAllocationService() {
        this.courseAllocationDAO = new CourseAllocationDatabaseDAO();
    }

    public void addCourseAllocation(CourseAllocation allocation) throws Exception {
        courseAllocationDAO.addCourseAllocation(allocation);
    }

    public void updateCourseAllocation(CourseAllocation allocation) throws Exception {
        courseAllocationDAO.updateCourseAllocation(allocation);
    }

    public void deleteCourseAllocation(String allocationId) throws Exception {
        courseAllocationDAO.deleteCourseAllocation(allocationId);
    }

    public Optional<CourseAllocation> getCourseAllocationById(String allocationId) throws Exception {
        return courseAllocationDAO.getCourseAllocationById(allocationId);
    }

    public List<CourseAllocation> getAllCourseAllocations() throws Exception {
        return courseAllocationDAO.getAllCourseAllocations();
    }

    public List<CourseAllocation> searchCourseAllocations(String searchTerm) throws Exception {
        return courseAllocationDAO.searchCourseAllocations(searchTerm);
    }
}
