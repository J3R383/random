
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class CoursePanel extends JPanel {
    private final CourseService courseService;
    private final JTable courseTable;
    private final DefaultTableModel tableModel;

    private final JTextField idField = new JTextField(15);
    private final JTextField nameField = new JTextField(15);
    private final JTextField creditsField = new JTextField(15);
    private final JTextField departmentField = new JTextField(15);
    private final JTextField searchField = new JTextField(15);

    public CoursePanel() {
        this.courseService = new CourseService();
        setLayout(new BorderLayout(10, 10)); // Add gaps between components

        // Table
        String[] columnNames = { "ID", "Name", "Credits", "Department" };
        tableModel = new DefaultTableModel(columnNames, 0);
        courseTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(courseTable);
        add(scrollPane, BorderLayout.CENTER);

        // Top panel for search
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.add(new JLabel("Search by ID or Name:"));
        topPanel.add(searchField);
        JButton searchButton = new JButton("Search");
        topPanel.add(searchButton);
        add(topPanel, BorderLayout.NORTH);

        // Right panel for form
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(new JLabel("ID:"), gbc);
        gbc.gridx = 1;
        formPanel.add(idField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        formPanel.add(new JLabel("Name:"), gbc);
        gbc.gridx = 1;
        formPanel.add(nameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        formPanel.add(new JLabel("Credits:"), gbc);
        gbc.gridx = 1;
        formPanel.add(creditsField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        formPanel.add(new JLabel("Department:"), gbc);
        gbc.gridx = 1;
        formPanel.add(departmentField, gbc);

        // Buttons for form
        JPanel formButtonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton clearFormButton = new JButton("Clear Form");
        formButtonPanel.add(clearFormButton);

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        formPanel.add(formButtonPanel, gbc);

        add(formPanel, BorderLayout.EAST);

        // Bottom panel for actions
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton addButton = new JButton("Add");
        JButton updateButton = new JButton("Update");
        JButton deleteButton = new JButton("Delete");
        JButton viewAllButton = new JButton("View All");

        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(viewAllButton);

        add(buttonPanel, BorderLayout.SOUTH);

        // Add Listeners
        addButton.addActionListener(e -> addCourse());
        updateButton.addActionListener(e -> updateCourse());
        deleteButton.addActionListener(e -> deleteCourse());
        searchButton.addActionListener(e -> searchCourse());
        viewAllButton.addActionListener(e -> refreshTable());
        clearFormButton.addActionListener(e -> clearForm());

        courseTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedRow = courseTable.getSelectedRow();
                if (selectedRow != -1) {
                    idField.setText(tableModel.getValueAt(selectedRow, 0).toString());
                    nameField.setText(tableModel.getValueAt(selectedRow, 1).toString());
                    creditsField.setText(tableModel.getValueAt(selectedRow, 2).toString());
                    departmentField.setText(tableModel.getValueAt(selectedRow, 3).toString());
                }
            }
        });

        refreshTable();
    }

    private void refreshTable() {
        try {
            List<Course> courses = courseService.getAllCourses();
            tableModel.setRowCount(0); // Clear existing data
            for (Course course : courses) {
                tableModel.addRow(new Object[] {
                        course.getCourseId(),
                        course.getCourseName(),
                        course.getCredits(),
                        course.getDepartment()
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error loading courses: " + e.getMessage(), "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void addCourse() {
        try {
            Course course = new Course(
                    idField.getText(),
                    nameField.getText(),
                    Integer.parseInt(creditsField.getText()),
                    departmentField.getText());
            courseService.addCourse(course);
            refreshTable();
            clearForm();
            JOptionPane.showMessageDialog(this, "Course added successfully.", "Success",
                    JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error adding course: " + e.getMessage(), "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateCourse() {
        try {
            int selectedRow = courseTable.getSelectedRow();
            if (selectedRow != -1) {
                Course course = new Course(
                        idField.getText(),
                        nameField.getText(),
                        Integer.parseInt(creditsField.getText()),
                        departmentField.getText());
                courseService.updateCourse(course);
                refreshTable();
                clearForm();
                JOptionPane.showMessageDialog(this, "Course updated successfully.", "Success",
                        JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Please select a course to update.", "Warning",
                        JOptionPane.WARNING_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error updating course: " + e.getMessage(), "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteCourse() {
        try {
            int selectedRow = courseTable.getSelectedRow();
            if (selectedRow != -1) {
                String courseId = tableModel.getValueAt(selectedRow, 0).toString();
                int confirmation = JOptionPane.showConfirmDialog(this,
                        "Are you sure you want to delete this course?", "Confirm Deletion",
                        JOptionPane.YES_NO_OPTION);
                if (confirmation == JOptionPane.YES_OPTION) {
                    courseService.deleteCourse(courseId);
                    refreshTable();
                    clearForm();
                    JOptionPane.showMessageDialog(this, "Course deleted successfully.", "Success",
                            JOptionPane.INFORMATION_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Please select a course to delete.", "Warning",
                        JOptionPane.WARNING_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error deleting course: " + e.getMessage(), "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void searchCourse() {
        try {
            String searchTerm = searchField.getText();
            if (searchTerm.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter a search term.", "Warning",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }
            List<Course> courses = courseService.searchCourses(searchTerm);
            tableModel.setRowCount(0); // Clear existing data
            for (Course course : courses) {
                tableModel.addRow(new Object[] {
                        course.getCourseId(),
                        course.getCourseName(),
                        course.getCredits(),
                        course.getDepartment()
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error searching courses: " + e.getMessage(), "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void clearForm() {
        idField.setText("");
        nameField.setText("");
        creditsField.setText("");
        departmentField.setText("");
        searchField.setText("");
    }
}

