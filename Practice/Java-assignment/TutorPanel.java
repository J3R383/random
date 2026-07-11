
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class TutorPanel extends JPanel {
    private final TutorService tutorService;
    private final JTable tutorTable;
    private final DefaultTableModel tableModel;

    private final JTextField idField = new JTextField(15);
    private final JTextField firstNameField = new JTextField(15);
    private final JTextField lastNameField = new JTextField(15);
    private final JTextField departmentField = new JTextField(15);
    private final JTextField searchField = new JTextField(15);

    public TutorPanel() {
        this.tutorService = new TutorService();
        setLayout(new BorderLayout(10, 10)); // Add gaps between components

        // Table
        String[] columnNames = { "ID", "First Name", "Last Name", "Department" };
        tableModel = new DefaultTableModel(columnNames, 0);
        tutorTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(tutorTable);
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
        addButton.addActionListener(e -> addTutor());
        updateButton.addActionListener(e -> updateTutor());
        deleteButton.addActionListener(e -> deleteTutor());
        searchButton.addActionListener(e -> searchTutor());
        viewAllButton.addActionListener(e -> refreshTable());
        clearFormButton.addActionListener(e -> clearForm());

        tutorTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedRow = tutorTable.getSelectedRow();
                if (selectedRow != -1) {
                    idField.setText(tableModel.getValueAt(selectedRow, 0).toString());
                    firstNameField.setText(tableModel.getValueAt(selectedRow, 1).toString());
                    lastNameField.setText(tableModel.getValueAt(selectedRow, 2).toString());
                    departmentField.setText(tableModel.getValueAt(selectedRow, 3).toString());
                }
            }
        });

        refreshTable();
    }

    private void refreshTable() {
        try {
            List<Tutor> tutors = tutorService.getAllTutors();
            tableModel.setRowCount(0); // Clear existing data
            for (Tutor tutor : tutors) {
                tableModel.addRow(new Object[] {
                        tutor.getTutorId(),
                        tutor.getFirstName(),
                        tutor.getLastName(),
                        tutor.getDepartment()
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error loading tutors: " + e.getMessage(), "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void addTutor() {
        try {
            Tutor tutor = new Tutor(
                    idField.getText(),
                    firstNameField.getText(),
                    lastNameField.getText(),
                    departmentField.getText());
            tutorService.addTutor(tutor);
            refreshTable();
            clearForm();
            JOptionPane.showMessageDialog(this, "Tutor added successfully.", "Success",
                    JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error adding tutor: " + e.getMessage(), "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateTutor() {
        try {
            int selectedRow = tutorTable.getSelectedRow();
            if (selectedRow != -1) {
                Tutor tutor = new Tutor(
                        idField.getText(),
                        firstNameField.getText(),
                        lastNameField.getText(),
                        departmentField.getText());
                tutorService.updateTutor(tutor);
                refreshTable();
                clearForm();
                JOptionPane.showMessageDialog(this, "Tutor updated successfully.", "Success",
                        JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Please select a tutor to update.", "Warning",
                        JOptionPane.WARNING_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error updating tutor: " + e.getMessage(), "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteTutor() {
        try {
            int selectedRow = tutorTable.getSelectedRow();
            if (selectedRow != -1) {
                String tutorId = tableModel.getValueAt(selectedRow, 0).toString();
                int confirmation = JOptionPane.showConfirmDialog(this,
                        "Are you sure you want to delete this tutor?", "Confirm Deletion",
                        JOptionPane.YES_NO_OPTION);
                if (confirmation == JOptionPane.YES_OPTION) {
                    tutorService.deleteTutor(tutorId);
                    refreshTable();
                    clearForm();
                    JOptionPane.showMessageDialog(this, "Tutor deleted successfully.", "Success",
                            JOptionPane.INFORMATION_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Please select a tutor to delete.", "Warning",
                        JOptionPane.WARNING_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error deleting tutor: " + e.getMessage(), "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void searchTutor() {
        try {
            String searchTerm = searchField.getText();
            if (searchTerm.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter a search term.", "Warning",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }
            List<Tutor> tutors = tutorService.searchTutors(searchTerm);
            tableModel.setRowCount(0); // Clear existing data
            for (Tutor tutor : tutors) {
                tableModel.addRow(new Object[] {
                        tutor.getTutorId(),
                        tutor.getFirstName(),
                        tutor.getLastName(),
                        tutor.getDepartment()
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error searching tutors: " + e.getMessage(), "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void clearForm() {
        idField.setText("");
        firstNameField.setText("");
        lastNameField.setText("");
        departmentField.setText("");
        searchField.setText("");
    }
}

