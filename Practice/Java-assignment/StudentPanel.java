
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

/**
 * This class represents the panel for managing students.
 * It provides a UI for adding, updating, deleting, and viewing students.
 */
public class StudentPanel extends JPanel {
    private final StudentService studentService;
    private final JTable studentTable;
    private final DefaultTableModel tableModel;

    private final JTextField idField = new JTextField(15);
    private final JTextField firstNameField = new JTextField(15);
    private final JTextField lastNameField = new JTextField(15);
    private final JTextField majorField = new JTextField(15);
    private final JTextField searchField = new JTextField(15);

    /**
     * Constructs a new StudentPanel.
     */
    public StudentPanel() {
        this.studentService = new StudentService();
        setLayout(new BorderLayout(10, 10)); // Add gaps between components

        // Table
        String[] columnNames = { "ID", "First Name", "Last Name", "Major" };
        tableModel = new DefaultTableModel(columnNames, 0);
        studentTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(studentTable);
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
        formPanel.add(new JLabel("First Name:"), gbc);
        gbc.gridx = 1;
        formPanel.add(firstNameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        formPanel.add(new JLabel("Last Name:"), gbc);
        gbc.gridx = 1;
        formPanel.add(lastNameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        formPanel.add(new JLabel("Major:"), gbc);
        gbc.gridx = 1;
        formPanel.add(majorField, gbc);

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
        JButton clearForGraduationButton = new JButton("Clear for Graduation");
        JButton promoteToNextClassButton = new JButton("Promote to Next Class");

        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(viewAllButton);
        buttonPanel.add(clearForGraduationButton);
        buttonPanel.add(promoteToNextClassButton);

        add(buttonPanel, BorderLayout.SOUTH);

        // Add Listeners
        addButton.addActionListener(e -> addStudent());
        updateButton.addActionListener(e -> updateStudent());
        deleteButton.addActionListener(e -> deleteStudent());
        searchButton.addActionListener(e -> searchStudent());
        viewAllButton.addActionListener(e -> refreshTable());
        clearFormButton.addActionListener(e -> clearForm());
        clearForGraduationButton.addActionListener(e -> clearForGraduation());
        promoteToNextClassButton.addActionListener(e -> promoteToNextClass());

        studentTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedRow = studentTable.getSelectedRow();
                if (selectedRow != -1) {
                    idField.setText(tableModel.getValueAt(selectedRow, 0).toString());
                    firstNameField.setText(tableModel.getValueAt(selectedRow, 1).toString());
                    lastNameField.setText(tableModel.getValueAt(selectedRow, 2).toString());
                    majorField.setText(tableModel.getValueAt(selectedRow, 3).toString());
                }
            }
        });

        refreshTable();
    }

    /**
     * Refreshes the student table with the latest data from the database.
     */
    private void refreshTable() {
        try {
            List<Student> students = studentService.getAllStudents();
            tableModel.setRowCount(0); // Clear existing data
            for (Student student : students) {
                tableModel.addRow(new Object[] {
                        student.getStudentId(),
                        student.getFirstName(),
                        student.getLastName(),
                        student.getMajor()
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error loading students: " + e.getMessage(), "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Adds a new student based on the data entered in the form.
     */
    private void addStudent() {
        try {
            Student student = new Student();
            student.setStudentId(idField.getText());
            student.setFirstName(firstNameField.getText());
            student.setLastName(lastNameField.getText());
            student.setMajor(majorField.getText());

            studentService.addStudent(student);
            refreshTable();
            clearForm();
            JOptionPane.showMessageDialog(this, "Student added successfully.", "Success",
                    JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error adding student: " + e.getMessage(), "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Updates the selected student with the data entered in the form.
     */
    private void updateStudent() {
        try {
            int selectedRow = studentTable.getSelectedRow();
            if (selectedRow != -1) {
                Student student = new Student();
                student.setStudentId(idField.getText());
                student.setFirstName(firstNameField.getText());
                student.setLastName(lastNameField.getText());
                student.setMajor(majorField.getText());
                studentService.updateStudent(student);
                refreshTable();
                clearForm();
                JOptionPane.showMessageDialog(this, "Student updated successfully.", "Success",
                        JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Please select a student to update.", "Warning",
                        JOptionPane.WARNING_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error updating student: " + e.getMessage(), "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Deletes the selected student from the table and database.
     */
    private void deleteStudent() {
        try {
            int selectedRow = studentTable.getSelectedRow();
            if (selectedRow != -1) {
                String studentId = tableModel.getValueAt(selectedRow, 0).toString();
                int confirmation = JOptionPane.showConfirmDialog(this,
                        "Are you sure you want to delete this student?", "Confirm Deletion",
                        JOptionPane.YES_NO_OPTION);
                if (confirmation == JOptionPane.YES_OPTION) {
                    studentService.deleteStudent(studentId);
                    refreshTable();
                    clearForm();
                    JOptionPane.showMessageDialog(this, "Student deleted successfully.", "Success",
                            JOptionPane.INFORMATION_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Please select a student to delete.", "Warning",
                        JOptionPane.WARNING_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error deleting student: " + e.getMessage(), "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Searches for a student by ID or name.
     */
    private void searchStudent() {
        try {
            String searchTerm = searchField.getText();
            if (searchTerm.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter a search term.", "Warning",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }
            List<Student> students = studentService.searchStudents(searchTerm);
            tableModel.setRowCount(0); // Clear existing data
            for (Student student : students) {
                tableModel.addRow(new Object[] {
                        student.getStudentId(),
                        student.getFirstName(),
                        student.getLastName(),
                        student.getMajor()
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error searching students: " + e.getMessage(), "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Clears the selected student for graduation.
     */
    private void clearForGraduation() {
        int selectedRow = studentTable.getSelectedRow();
        if (selectedRow != -1) {
            String studentId = tableModel.getValueAt(selectedRow, 0).toString();
            // Implement graduation logic here, e.g., call a service method
            JOptionPane.showMessageDialog(this, "Student " + studentId + " cleared for graduation.",
                    "Success", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "Please select a student to clear for graduation.",
                    "Warning", JOptionPane.WARNING_MESSAGE);
        }
    }

    /**
     * Promotes the selected student to the next class.
     */
    private void promoteToNextClass() {
        int selectedRow = studentTable.getSelectedRow();
        if (selectedRow != -1) {
            String studentId = tableModel.getValueAt(selectedRow, 0).toString();
            // Implement promotion logic here, e.g., call a service method
            JOptionPane.showMessageDialog(this, "Student " + studentId + " promoted to the next class.",
                    "Success", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "Please select a student to promote.", "Warning",
                    JOptionPane.WARNING_MESSAGE);
        }
    }

    /**
     * Clears the input fields in the form.
     */
    private void clearForm() {
        idField.setText("");
        firstNameField.setText("");
        lastNameField.setText("");
        majorField.setText("");
        searchField.setText("");
    }
}

