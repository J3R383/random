
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ExaminationResultPanel extends JPanel {
    private final ExaminationResultService examinationResultService;
    private final JTable examinationResultTable;
    private final DefaultTableModel tableModel;

    private final JTextField resultIdField = new JTextField(15);
    private final JTextField enrollmentIdField = new JTextField(15);
    private final JTextField scoreField = new JTextField(15);
    private final JTextField gradeField = new JTextField(15);
    private final JTextField semesterField = new JTextField(15);
    private final JTextField yearField = new JTextField(15);
    private final JTextField searchField = new JTextField(15);

    public ExaminationResultPanel() {
        this.examinationResultService = new ExaminationResultService();
        setLayout(new BorderLayout(10, 10)); // Add gaps between components

        // Table
        String[] columnNames = { "Result ID", "Enrollment ID", "Score", "Grade", "Semester", "Year" };
        tableModel = new DefaultTableModel(columnNames, 0);
        examinationResultTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(examinationResultTable);
        add(scrollPane, BorderLayout.CENTER);

        // Top panel for search
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.add(new JLabel("Search by Result ID:"));
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
        formPanel.add(new JLabel("Result ID:"), gbc);
        gbc.gridx = 1;
        formPanel.add(resultIdField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        formPanel.add(new JLabel("Enrollment ID:"), gbc);
        gbc.gridx = 1;
        formPanel.add(enrollmentIdField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        formPanel.add(new JLabel("Score:"), gbc);
        gbc.gridx = 1;
        formPanel.add(scoreField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        formPanel.add(new JLabel("Grade:"), gbc);
        gbc.gridx = 1;
        formPanel.add(gradeField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        formPanel.add(new JLabel("Semester:"), gbc);
        gbc.gridx = 1;
        formPanel.add(semesterField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        formPanel.add(new JLabel("Year:"), gbc);
        gbc.gridx = 1;
        formPanel.add(yearField, gbc);

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
        addButton.addActionListener(e -> addExaminationResult());
        updateButton.addActionListener(e -> updateExaminationResult());
        deleteButton.addActionListener(e -> deleteExaminationResult());
        searchButton.addActionListener(e -> searchExaminationResult());
        viewAllButton.addActionListener(e -> refreshTable());
        clearFormButton.addActionListener(e -> clearForm());

        examinationResultTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedRow = examinationResultTable.getSelectedRow();
                if (selectedRow != -1) {
                    resultIdField.setText(tableModel.getValueAt(selectedRow, 0).toString());
                    enrollmentIdField.setText(tableModel.getValueAt(selectedRow, 1).toString());
                    scoreField.setText(tableModel.getValueAt(selectedRow, 2).toString());
                    gradeField.setText(tableModel.getValueAt(selectedRow, 3).toString());
                    semesterField.setText(tableModel.getValueAt(selectedRow, 4).toString());
                    yearField.setText(tableModel.getValueAt(selectedRow, 5).toString());
                }
            }
        });

        refreshTable();
    }

    private void refreshTable() {
        try {
            List<ExaminationResult> results = examinationResultService.getAllExaminationResults();
            tableModel.setRowCount(0); // Clear existing data
            for (ExaminationResult result : results) {
                tableModel.addRow(new Object[] {
                        result.getResultId(),
                        result.getEnrollmentId(),
                        result.getScore(),
                        result.getGrade(),
                        result.getSemester(),
                        result.getYear()
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error loading examination results: " + e.getMessage(), "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void addExaminationResult() {
        try {
            ExaminationResult result = new ExaminationResult(
                    resultIdField.getText(),
                    enrollmentIdField.getText(),
                    Integer.parseInt(scoreField.getText()),
                    gradeField.getText(),
                    Integer.parseInt(semesterField.getText()),
                    Integer.parseInt(yearField.getText()));
            examinationResultService.addExaminationResult(result);
            refreshTable();
            clearForm();
            JOptionPane.showMessageDialog(this, "Examination result added successfully.", "Success",
                    JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error adding examination result: " + e.getMessage(), "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateExaminationResult() {
        try {
            int selectedRow = examinationResultTable.getSelectedRow();
            if (selectedRow != -1) {
                ExaminationResult result = new ExaminationResult(
                        resultIdField.getText(),
                        enrollmentIdField.getText(),
                        Integer.parseInt(scoreField.getText()),
                        gradeField.getText(),
                        Integer.parseInt(semesterField.getText()),
                        Integer.parseInt(yearField.getText()));
                examinationResultService.updateExaminationResult(result);
                refreshTable();
                clearForm();
                JOptionPane.showMessageDialog(this, "Examination result updated successfully.", "Success",
                        JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Please select an examination result to update.", "Warning",
                        JOptionPane.WARNING_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error updating examination result: " + e.getMessage(), "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteExaminationResult() {
        try {
            int selectedRow = examinationResultTable.getSelectedRow();
            if (selectedRow != -1) {
                String resultId = tableModel.getValueAt(selectedRow, 0).toString();
                int confirmation = JOptionPane.showConfirmDialog(this,
                        "Are you sure you want to delete this examination result?", "Confirm Deletion",
                        JOptionPane.YES_NO_OPTION);
                if (confirmation == JOptionPane.YES_OPTION) {
                    examinationResultService.deleteExaminationResult(resultId);
                    refreshTable();
                    clearForm();
                    JOptionPane.showMessageDialog(this, "Examination result deleted successfully.", "Success",
                            JOptionPane.INFORMATION_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Please select an examination result to delete.", "Warning",
                        JOptionPane.WARNING_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error deleting examination result: " + e.getMessage(), "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void searchExaminationResult() {
        try {
            String searchTerm = searchField.getText();
            if (searchTerm.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter a search term.", "Warning",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }
            List<ExaminationResult> results = examinationResultService.searchExaminationResults(searchTerm);
            tableModel.setRowCount(0); // Clear existing data
            for (ExaminationResult result : results) {
                tableModel.addRow(new Object[] {
                        result.getResultId(),
                        result.getEnrollmentId(),
                        result.getScore(),
                        result.getGrade(),
                        result.getSemester(),
                        result.getYear()
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error searching examination results: " + e.getMessage(), "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void clearForm() {
        resultIdField.setText("");
        enrollmentIdField.setText("");
        scoreField.setText("");
        gradeField.setText("");
        semesterField.setText("");
        yearField.setText("");
        searchField.setText("");
    }
}

