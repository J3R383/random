import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    public MainFrame() {
        setTitle("Chuka University - Student Records Management System");
        setSize(1200, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        // Center the window

        JTabbedPane tabbedPane = new JTabbedPane();

        // Initialize panels
        tabbedPane.addTab("Students", new StudentPanel());
        tabbedPane.addTab("Courses", new CoursePanel());
        tabbedPane.addTab("Tutors", new TutorPanel());
        tabbedPane.addTab("Enrollments", new EnrollmentPanel());
        tabbedPane.addTab("Examination Results", new ExaminationResultPanel());
        tabbedPane.addTab("Course Allocation", new CourseAllocationPanel());
        tabbedPane.addTab("Reports", new ReportPanel());
        add(tabbedPane);
    }

    public static void main(String[] args) {
        try {
            // Set system look and feel for a more native appearance
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
            // Handle exception or default to a standard look and feel
        }

        SwingUtilities.invokeLater(() -> {
            new MainFrame().setVisible(true);
        });
    }
}