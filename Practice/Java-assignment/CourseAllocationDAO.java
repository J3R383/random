import java.util.List;
import java.util.Optional;

public interface CourseAllocationDAO {
    void addCourseAllocation(CourseAllocation allocation) throws Exception;

    void updateCourseAllocation(CourseAllocation allocation) throws Exception;

    void deleteCourseAllocation(String allocationId) throws Exception;

    Optional<CourseAllocation> getCourseAllocationById(String allocationId) throws Exception;

    List<CourseAllocation> getAllCourseAllocations() throws Exception;

    List<CourseAllocation> searchCourseAllocations(String searchTerm) throws Exception;
}
