
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class CourseAllocationPanel extends JPanel {
    private final CourseAllocationService courseAllocationService;
    private final JTable courseAllocationTable;
    private final DefaultTableModel tableModel;

    private final JTextField allocationIdField = new JTextField(15);
    private final JTextField courseIdField = new JTextField(15);
    private final JTextField tutorIdField = new JTextField(15);
    private final JTextField semesterField = new JTextField(15);
    private final JTextField yearField = new JTextField(15);
    private final JTextField searchField = new JTextField(15);

    public CourseAllocationPanel() {
        this.courseAllocationService = new CourseAllocationService();
        setLayout(new BorderLayout(10, 10)); // Add gaps between components

        // Table
        String[] columnNames = { "Allocation ID", "Course ID", "Tutor ID", "Semester", "Year" };
        tableModel = new DefaultTableModel(columnNames, 0);
        courseAllocationTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(courseAllocationTable);
        add(scrollPane, BorderLayout.CENTER);

        // Top panel for search
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.add(new JLabel("Search by Allocation ID:"));
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
        formPanel.add(new JLabel("Allocation ID:"), gbc);
        gbc.gridx = 1;
        formPanel.add(allocationIdField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        formPanel.add(new JLabel("Course ID:"), gbc);
        gbc.gridx = 1;
        formPanel.add(courseIdField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        formPanel.add(new JLabel("Tutor ID:"), gbc);
        gbc.gridx = 1;
        formPanel.add(tutorIdField, gbc);

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

        // Buttons for form
        JPanel formButtonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton clearFormButton = new JButton("Clear Form");
        formButtonPanel.add(clearFormButton);

        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        formPanel.add(formButtonPanel, gbc);

        add(formPanel, BorderLayout.EAST);

        // Bottom panel for actions
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton addButton = new JButton("Add");
        JButton updateButton = new JButton("Update");
        JButton deleteButton = new JButton("Delete");
        JButton viewAllButton = new JButton("View All");
        JButton allocateCourseButton = new JButton("Allocate Course");


        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(viewAllButton);
        buttonPanel.add(allocateCourseButton);

        add(buttonPanel, BorderLayout.SOUTH);

        // Add Listeners
        addButton.addActionListener(e -> addCourseAllocation());
        updateButton.addActionListener(e -> updateCourseAllocation());
        deleteButton.addActionListener(e -> deleteCourseAllocation());
        searchButton.addActionListener(e -> searchCourseAllocation());
        viewAllButton.addActionListener(e -> refreshTable());
        clearFormButton.addActionListener(e -> clearForm());
        allocateCourseButton.addActionListener(e -> allocateCourse());

        courseAllocationTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedRow = courseAllocationTable.getSelectedRow();
                if (selectedRow != -1) {
                    allocationIdField.setText(tableModel.getValueAt(selectedRow, 0).toString());
                    courseIdField.setText(tableModel.getValueAt(selectedRow, 1).toString());
                    tutorIdField.setText(tableModel.getValueAt(selectedRow, 2).toString());
                    semesterField.setText(tableModel.getValueAt(selectedRow, 3).toString());
                    yearField.setText(tableModel.getValueAt(selectedRow, 4).toString());
                }
            }
        });

        refreshTable();
    }

    private void refreshTable() {
        try {
            List<CourseAllocation> allocations = courseAllocationService.getAllCourseAllocations();
            tableModel.setRowCount(0); // Clear existing data
            for (CourseAllocation allocation : allocations) {
                tableModel.addRow(new Object[] {
                        allocation.getAllocationId(),
                        allocation.getCourseId(),
                        allocation.getTutorId(),
                        allocation.getSemester(),
                        allocation.getYear()
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error loading course allocations: " + e.getMessage(), "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void addCourseAllocation() {
        try {
            CourseAllocation allocation = new CourseAllocation(
                    allocationIdField.getText(),
                    courseIdField.getText(),
                    tutorIdField.getText(),
                    Integer.parseInt(semesterField.getText()),
                    Integer.parseInt(yearField.getText()));
            courseAllocationService.addCourseAllocation(allocation);
            refreshTable();
            clearForm();
            JOptionPane.showMessageDialog(this, "Course allocation added successfully.", "Success",
                    JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error adding course allocation: " + e.getMessage(), "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateCourseAllocation() {
        try {
            int selectedRow = courseAllocationTable.getSelectedRow();
            if (selectedRow != -1) {
                CourseAllocation allocation = new CourseAllocation(
                        allocationIdField.getText(),
                        courseIdField.getText(),
                        tutorIdField.getText(),
                        Integer.parseInt(semesterField.getText()),
                        Integer.parseInt(yearField.getText()));
                courseAllocationService.updateCourseAllocation(allocation);
                refreshTable();
                clearForm();
                JOptionPane.showMessageDialog(this, "Course allocation updated successfully.", "Success",
                        JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Please select a course allocation to update.", "Warning",
                        JOptionPane.WARNING_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error updating course allocation: " + e.getMessage(), "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteCourseAllocation() {
        try {
            int selectedRow = courseAllocationTable.getSelectedRow();
            if (selectedRow != -1) {
                String allocationId = tableModel.getValueAt(selectedRow, 0).toString();
                int confirmation = JOptionPane.showConfirmDialog(this,
                        "Are you sure you want to delete this course allocation?", "Confirm Deletion",
                        JOptionPane.YES_NO_OPTION);
                if (confirmation == JOptionPane.YES_OPTION) {
                    courseAllocationService.deleteCourseAllocation(allocationId);
                    refreshTable();
                    clearForm();
                    JOptionPane.showMessageDialog(this, "Course allocation deleted successfully.", "Success",
                            JOptionPane.INFORMATION_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Please select a course allocation to delete.", "Warning",
                        JOptionPane.WARNING_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error deleting course allocation: " + e.getMessage(), "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void searchCourseAllocation() {
        try {
            String searchTerm = searchField.getText();
            if (searchTerm.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter a search term.", "Warning",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }
            List<CourseAllocation> allocations = courseAllocationService.searchCourseAllocations(searchTerm);
            tableModel.setRowCount(0); // Clear existing data
            for (CourseAllocation allocation : allocations) {
                tableModel.addRow(new Object[] {
                        allocation.getAllocationId(),
                        allocation.getCourseId(),
                        allocation.getTutorId(),
                        allocation.getSemester(),
                        allocation.getYear()
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error searching course allocations: " + e.getMessage(), "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void allocateCourse() {
        // Implement course allocation logic here
        JOptionPane.showMessageDialog(this, "Allocate Course button clicked.", "Info",
                JOptionPane.INFORMATION_MESSAGE);
    }

    private void clearForm() {
        allocationIdField.setText("");
        courseIdField.setText("");
        tutorIdField.setText("");
        semesterField.setText("");
        yearField.setText("");
        searchField.setText("");
    }
}

