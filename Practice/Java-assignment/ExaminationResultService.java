import java.util.List;
import java.util.Optional;

public class ExaminationResultService {
    private final ExaminationResultDAO examinationResultDAO;

    public ExaminationResultService() {
        this.examinationResultDAO = new ExaminationResultDatabaseDAO();
    }

    public void addExaminationResult(ExaminationResult result) throws Exception {
        examinationResultDAO.addExaminationResult(result);
    }

    public void updateExaminationResult(ExaminationResult result) throws Exception {
        examinationResultDAO.updateExaminationResult(result);
    }

    public void deleteExaminationResult(String resultId) throws Exception {
        examinationResultDAO.deleteExaminationResult(resultId);
    }

    public Optional<ExaminationResult> getExaminationResultById(String resultId) throws Exception {
        return examinationResultDAO.getExaminationResultById(resultId);
    }

    public List<ExaminationResult> getAllExaminationResults() throws Exception {
        return examinationResultDAO.getAllExaminationResults();
    }

    public List<ExaminationResult> searchExaminationResults(String searchTerm) throws Exception {
        return examinationResultDAO.searchExaminationResults(searchTerm);
    }
}
