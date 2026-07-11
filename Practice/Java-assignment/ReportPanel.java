
import javax.swing.*;
import java.awt.*;

public class ReportPanel extends JPanel {

    private final ReportService reportService;
    private final JTextArea reportArea;
    private final JComboBox<String> reportSelector;
    private final JTextField studentIdField = new JTextField(15);
    private final JTextField courseIdField = new JTextField(15);
    private final JTextField tutorIdField = new JTextField(15);
    private final JTextField semesterField = new JTextField(15);
    private final JTextField yearField = new JTextField(15);

    public ReportPanel() {
        this.reportService = new ReportService();
        setLayout(new BorderLayout(10, 10));

        // Top panel for report selection and input
        JPanel topPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        String[] reports = {
                "Student Academic Transcript",
                "Course Enrollment Report",
                "Tutor Course Load Report",
                "Graduation Eligibility Report",
                "Students on Probation Report"
        };
        reportSelector = new JComboBox<>(reports);

        gbc.gridx = 0;
        gbc.gridy = 0;
        topPanel.add(new JLabel("Select Report:"), gbc);
        gbc.gridx = 1;
        topPanel.add(reportSelector, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        topPanel.add(new JLabel("Student ID:"), gbc);
        gbc.gridx = 1;
        topPanel.add(studentIdField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        topPanel.add(new JLabel("Course ID:"), gbc);
        gbc.gridx = 1;
        topPanel.add(courseIdField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        topPanel.add(new JLabel("Tutor ID:"), gbc);
        gbc.gridx = 1;
        topPanel.add(tutorIdField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        topPanel.add(new JLabel("Semester:"), gbc);
        gbc.gridx = 1;
        topPanel.add(semesterField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        topPanel.add(new JLabel("Year:"), gbc);
        gbc.gridx = 1;
        topPanel.add(yearField, gbc);


        JButton generateButton = new JButton("Generate Report");
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        topPanel.add(generateButton, gbc);

        add(topPanel, BorderLayout.NORTH);

        // Center panel for report display
        reportArea = new JTextArea();
        reportArea.setEditable(false);
        reportArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        add(new JScrollPane(reportArea), BorderLayout.CENTER);

        // Add Listeners
        generateButton.addActionListener(e -> generateReport());
        reportSelector.addActionListener(e -> toggleInputFields());

        toggleInputFields(); // Set initial visibility
    }

    private void toggleInputFields() {
        String selectedReport = (String) reportSelector.getSelectedItem();
        studentIdField.setVisible(false);
        courseIdField.setVisible(false);
        tutorIdField.setVisible(false);
        semesterField.setVisible(false);
        yearField.setVisible(false);

        if (selectedReport == null) return;

        switch (selectedReport) {
            case "Student Academic Transcript":
                studentIdField.setVisible(true);
                break;
            case "Course Enrollment Report":
                courseIdField.setVisible(true);
                semesterField.setVisible(true);
                yearField.setVisible(true);
                break;
            case "Tutor Course Load Report":
                tutorIdField.setVisible(true);
                semesterField.setVisible(true);
                yearField.setVisible(true);
                break;
            default:
                // No input fields needed for other reports
                break;
        }
    }

    private void generateReport() {
        String selectedReport = (String) reportSelector.getSelectedItem();
        if (selectedReport == null) {
            JOptionPane.showMessageDialog(this, "Please select a report to generate.", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            String reportContent = "";
            switch (selectedReport) {
                case "Student Academic Transcript":
                    String studentId = studentIdField.getText();
                    if (studentId.isEmpty()) {
                        JOptionPane.showMessageDialog(this, "Please enter a Student ID.", "Warning", JOptionPane.WARNING_MESSAGE);
                        return;
                    }
                    reportContent = reportService.generateStudentTranscript(studentId);
                    break;
                case "Course Enrollment Report":
                    String courseId = courseIdField.getText();
                    int semester = Integer.parseInt(semesterField.getText());
                    int year = Integer.parseInt(yearField.getText());
                    if (courseId.isEmpty()) {
                        JOptionPane.showMessageDialog(this, "Please enter a Course ID.", "Warning", JOptionPane.WARNING_MESSAGE);
                        return;
                    }
                    reportContent = reportService.generateCourseEnrollmentReport(courseId, semester, year);
                    break;
                case "Tutor Course Load Report":
                    String tutorId = tutorIdField.getText();
                    int tutorSemester = Integer.parseInt(semesterField.getText());
                    int tutorYear = Integer.parseInt(yearField.getText());
                    if (tutorId.isEmpty()) {
                        JOptionPane.showMessageDialog(this, "Please enter a Tutor ID.", "Warning", JOptionPane.WARNING_MESSAGE);
                        return;
                    }
                    reportContent = reportService.generateTutorCourseLoadReport(tutorId, tutorSemester, tutorYear);
                    break;
                case "Graduation Eligibility Report":
                    reportContent = reportService.generateGraduationEligibilityReport();
                    break;
                case "Students on Probation Report":
                    reportContent = reportService.generateStudentsOnProbationReport();
                    break;
            }
            reportArea.setText(reportContent);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid number format for Semester or Year.", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error generating report: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}

