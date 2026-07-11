import java.util.List;
import java.util.Optional;

public interface ExaminationResultDAO {
    void addExaminationResult(ExaminationResult result) throws Exception;

    void updateExaminationResult(ExaminationResult result) throws Exception;

    void deleteExaminationResult(String resultId) throws Exception;

    Optional<ExaminationResult> getExaminationResultById(String resultId) throws Exception;

    List<ExaminationResult> getAllExaminationResults() throws Exception;

    Optional<ExaminationResult> getExaminationResultByEnrollmentId(String enrollmentId) throws Exception;

    List<ExaminationResult> searchExaminationResults(String searchTerm) throws Exception;
}
