import java.util.List;
import java.util.Optional;

public class TutorService {
    private final TutorDAO tutorDAO;

    public TutorService() {
        this.tutorDAO = new TutorDatabaseDAO();
    }

    public void addTutor(Tutor tutor) throws Exception {
        tutorDAO.addTutor(tutor);
    }

    public void updateTutor(Tutor tutor) throws Exception {
        tutorDAO.updateTutor(tutor);
    }

    public void deleteTutor(String tutorId) throws Exception {
        tutorDAO.deleteTutor(tutorId);
    }

    public Optional<Tutor> getTutorById(String tutorId) throws Exception {
        return tutorDAO.getTutorById(tutorId);
    }

    public List<Tutor> getAllTutors() throws Exception {
        return tutorDAO.getAllTutors();
    }

    public List<Tutor> searchTutors(String searchTerm) throws Exception {
        return tutorDAO.searchTutors(searchTerm);
    }
}
