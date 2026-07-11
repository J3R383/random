
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.swing.text.MaskFormatter;
import java.text.ParseException;

public class EnrollmentPanel extends JPanel {
    private final EnrollmentService enrollmentService;
    private final JTable enrollmentTable;
    private final DefaultTableModel tableModel;

    private final JTextField idField = new JTextField(15);
    private final JTextField studentIdField = new JTextField(15);
    private final JTextField courseIdField = new JTextField(15);
    private final JTextField semesterField = new JTextField(15);
    private final JTextField yearField = new JTextField(15);
    private final JFormattedTextField dateField;
    private final JTextField searchField = new JTextField(15);

    public EnrollmentPanel() {
        this.enrollmentService = new EnrollmentService();
        setLayout(new BorderLayout(10, 10)); // Add gaps between components

        // Date field with formatter
        try {
            MaskFormatter dateMask = new MaskFormatter("####-##-##");
            dateField = new JFormattedTextField(dateMask);
            dateField.setColumns(15);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }


        // Table
        String[] columnNames = { "ID", "Student ID", "Course ID", "Semester", "Year", "Date" };
        tableModel = new DefaultTableModel(columnNames, 0);
        enrollmentTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(enrollmentTable);
        add(scrollPane, BorderLayout.CENTER);

        // Top panel for search
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.add(new JLabel("Search by ID:"));
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
        formPanel.add(new JLabel("Student ID:"), gbc);
        gbc.gridx = 1;
        formPanel.add(studentIdField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        formPanel.add(new JLabel("Course ID:"), gbc);
        gbc.gridx = 1;
        formPanel.add(courseIdField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        formPanel.add(new JLabel("Semester:"), gbc);
        gbc.gridx = 1;
        formPanel.add(semesterField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        formPanel.add(new JLabel("Year:"), gbc);
        gbc.gridx = 1;
        formPanel.add(yearField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        formPanel.add(new JLabel("Date (YYYY-MM-DD):"), gbc);
        gbc.gridx = 1;
        formPanel.add(dateField, gbc);

        // Buttons for form
        JPanel formButtonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton clearFormButton = new JButton("Clear Form");
        formButtonPanel.add(clearFormButton);

        gbc.gridx = 0;
        gbc.gridy = 6;
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
        addButton.addActionListener(e -> addEnrollment());
        updateButton.addActionListener(e -> updateEnrollment());
        deleteButton.addActionListener(e -> deleteEnrollment());
        searchButton.addActionListener(e -> searchEnrollment());
        viewAllButton.addActionListener(e -> refreshTable());
        clearFormButton.addActionListener(e -> clearForm());

        enrollmentTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedRow = enrollmentTable.getSelectedRow();
                if (selectedRow != -1) {
                    idField.setText(tableModel.getValueAt(selectedRow, 0).toString());
                    studentIdField.setText(tableModel.getValueAt(selectedRow, 1).toString());
                    courseIdField.setText(tableModel.getValueAt(selectedRow, 2).toString());
                    semesterField.setText(tableModel.getValueAt(selectedRow, 3).toString());
                    yearField.setText(tableModel.getValueAt(selectedRow, 4).toString());
                    dateField.setText(tableModel.getValueAt(selectedRow, 5).toString());
                }
            }
        });

        refreshTable();
    }

    private void refreshTable() {
        try {
            List<Enrollment> enrollments = enrollmentService.getAllEnrollments();
            tableModel.setRowCount(0); // Clear existing data
            for (Enrollment enrollment : enrollments) {
                tableModel.addRow(new Object[] {
                        enrollment.getEnrollmentId(),
                        enrollment.getStudentId(),
                        enrollment.getCourseId(),
                        enrollment.getSemester(),
                        enrollment.getYear(),
                        enrollment.getEnrollmentDate().format(DateTimeFormatter.ISO_LOCAL_DATE)
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error loading enrollments: " + e.getMessage(), "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void addEnrollment() {
        try {
            Enrollment enrollment = new Enrollment(
                    idField.getText(),
                    studentIdField.getText(),
                    courseIdField.getText(),
                    Integer.parseInt(semesterField.getText()),
                    Integer.parseInt(yearField.getText()),
                    LocalDate.parse(dateField.getText()));
            enrollmentService.addEnrollment(enrollment);
            refreshTable();
            clearForm();
            JOptionPane.showMessageDialog(this, "Enrollment added successfully.", "Success",
                    JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error adding enrollment: " + e.getMessage(), "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateEnrollment() {
        try {
            int selectedRow = enrollmentTable.getSelectedRow();
            if (selectedRow != -1) {
                Enrollment enrollment = new Enrollment(
                        idField.getText(),
                        studentIdField.getText(),
                        courseIdField.getText(),
                        Integer.parseInt(semesterField.getText()),
                        Integer.parseInt(yearField.getText()),
                        LocalDate.parse(dateField.getText()));
                enrollmentService.updateEnrollment(enrollment);
                refreshTable();
                clearForm();
                JOptionPane.showMessageDialog(this, "Enrollment updated successfully.", "Success",
                        JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Please select an enrollment to update.", "Warning",
                        JOptionPane.WARNING_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error updating enrollment: " + e.getMessage(), "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteEnrollment() {
        try {
            int selectedRow = enrollmentTable.getSelectedRow();
            if (selectedRow != -1) {
                String enrollmentId = tableModel.getValueAt(selectedRow, 0).toString();
                int confirmation = JOptionPane.showConfirmDialog(this,
                        "Are you sure you want to delete this enrollment?", "Confirm Deletion",
                        JOptionPane.YES_NO_OPTION);
                if (confirmation == JOptionPane.YES_OPTION) {
                    enrollmentService.deleteEnrollment(enrollmentId);
                    refreshTable();
                    clearForm();
                    JOptionPane.showMessageDialog(this, "Enrollment deleted successfully.", "Success",
                            JOptionPane.INFORMATION_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Please select an enrollment to delete.", "Warning",
                        JOptionPane.WARNING_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error deleting enrollment: " + e.getMessage(), "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void searchEnrollment() {
        try {
            String searchTerm = searchField.getText();
            if (searchTerm.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter a search term.", "Warning",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }
            List<Enrollment> enrollments = enrollmentService.searchEnrollments(searchTerm);
            tableModel.setRowCount(0); // Clear existing data
            for (Enrollment enrollment : enrollments) {
                tableModel.addRow(new Object[] {
                        enrollment.getEnrollmentId(),
                        enrollment.getStudentId(),
                        enrollment.getCourseId(),
                        enrollment.getSemester(),
                        enrollment.getYear(),
                        enrollment.getEnrollmentDate().format(DateTimeFormatter.ISO_LOCAL_DATE)
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error searching enrollments: " + e.getMessage(), "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void clearForm() {
        idField.setText("");
        studentIdField.setText("");
        courseIdField.setText("");
        semesterField.setText("");
        yearField.setText("");
        dateField.setText("");
        searchField.setText("");
    }
}

