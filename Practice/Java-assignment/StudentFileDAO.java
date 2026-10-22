import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class StudentFileDAO implements StudentDAO {
    private static final String FILE_NAME = "students.csv";

    @Override
    public void addStudent(Student student) throws Exception {
        List<Student> students = getAllStudents();
        students.add(student);
        writeToFile(students);
    }

    @Override
    public void updateStudent(Student student) throws Exception {
        List<Student> students = getAllStudents();
        for (int i = 0; i < students.size(); i++) {
            if (students.get(i).getStudentId().equals(student.getStudentId())) {
                students.set(i, student);
                break;
            }
        }
        writeToFile(students);
    }

    @Override
    public void deleteStudent(String studentId) throws Exception {
        List<Student> students = getAllStudents();
        students.removeIf(student -> student.getStudentId().equals(studentId));
        writeToFile(students);
    }

    @Override
    public Optional<Student> getStudentById(String studentId) throws Exception {
        return getAllStudents().stream()
                .filter(student -> student.getStudentId().equals(studentId))
                .findFirst();
    }

    @Override
    public List<Student> getAllStudents() throws Exception {
        List<Student> students = new ArrayList<>();
        File file = new File(FILE_NAME);
        if (!file.exists()) {
            return students;
        }
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                students.add(fromCsv(line));
            }
        }
        return students;
    }

    @Override
    public int countPassedCourses(String studentId, int semester, int year) throws Exception {
        // This should be implemented in a more sophisticated way, maybe in another DAO
        return 0;
    }

    @Override
    public boolean hasMetGraduationRequirements(String studentId) throws Exception {
        // This should be implemented in a more sophisticated way, maybe in another DAO
        return false;
    }

    private void writeToFile(List<Student> students) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (Student student : students) {
                writer.write(toCsv(student));
                writer.newLine();
            }
        }
    }

    private String toCsv(Student student) {
        return student.toString();
    }

    private Student fromCsv(String csvLine) {
        return Student.fromString(csvLine);
    }

    @Override
    public List<Student> searchStudents(String searchTerm) throws Exception {
        throw new UnsupportedOperationException("Unimplemented method 'searchStudents'");
    }
}
