package bg.smg.pharmacy.app;

import bg.smg.pharmacy.model.Drug;
import bg.smg.pharmacy.model.Ingredient;
import bg.smg.pharmacy.services.DrugService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;

public class DrugListWindow extends JFrame {

    private JTable drugTable;
    private DefaultTableModel drugTableModel;
    private JButton detailsButton;
    private JButton editButton;
    private JButton deleteButton;
    private JButton addDrugButton;
    private DrugService drugService;

    public DrugListWindow() throws SQLException {
        try {
            drugService = new DrugService();
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error connecting to the database", "Error", JOptionPane.ERROR_MESSAGE);
            dispose();
            return;
        }

        setTitle("Drug List");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel mainPanel = new JPanel(new BorderLayout());

        List<Drug> drugs;
        try {
            drugs = drugService.getAllDrugs();
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error fetching drugs from the database", "Error", JOptionPane.ERROR_MESSAGE);
            dispose();
            return;
        }

        // Define column names
        String[] columnNames = {"Drug Name", "Price", "Stock"};

        // Create DefaultTableModel with columnNames and 0 rows
        drugTableModel = new DefaultTableModel(columnNames, 0);
        drugTable = new JTable(drugTableModel);

        // Populate table model with drug data
        for (Drug drug : drugs) {
            int stockQuantity = drugService.getStockQuantity(drug.getDrugId());
            Object[] rowData = {drug.getName(), drug.getPrice(), stockQuantity};
            drugTableModel.addRow(rowData);
        }

        detailsButton = new JButton("Details");
        editButton = new JButton("Edit");
        deleteButton = new JButton("Delete");
        addDrugButton = new JButton("Add Drug");

        detailsButton.addActionListener(e -> showDrugDetails());
        editButton.addActionListener(e -> editDrug());
        deleteButton.addActionListener(e -> deleteDrug());
        addDrugButton.addActionListener(e -> openAddDrugWindow());

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(detailsButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(addDrugButton);

        mainPanel.add(new JScrollPane(drugTable), BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void showDrugDetails() {
        int selectedIndex = drugTable.getSelectedRow();
        if (selectedIndex != -1) {
            List<Drug> drugs;
            try {
                drugs = drugService.getAllDrugs();
                Drug selectedDrug = drugs.get(selectedIndex);

                List<Ingredient> ingredientInfoList = drugService.getIngredientsForDrug(selectedDrug.getDrugId());

                new DrugDetailsWindow(selectedDrug, ingredientInfoList);
            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error fetching drug details from the database", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select a drug to view details", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void editDrug() {
        int selectedIndex = drugTable.getSelectedRow();
        if (selectedIndex != -1) {
            List<Drug> drugs;
            try {
                drugs = drugService.getAllDrugs();
                Drug selectedDrug = drugs.get(selectedIndex);

                new DrugEditWindow(selectedDrug.getDrugId(), drugService);
            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error fetching drug details from the database", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select a drug to edit", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteDrug() {
        int selectedIndex = drugTable.getSelectedRow();
        if (selectedIndex != -1) {
            List<Drug> drugs;
            try {
                drugs = drugService.getAllDrugs();
                Drug selectedDrug = drugs.get(selectedIndex);

                drugService.deleteDrug(selectedDrug.getDrugId());

                updateDrugList();

                JOptionPane.showMessageDialog(this, "Drug deleted successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error deleting drug", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select a drug to delete", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateDrugList() {
        drugTableModel.setRowCount(0); // Clear the table

        List<Drug> updatedDrugs;
        try {
            updatedDrugs = drugService.getAllDrugs();

            for (Drug drug : updatedDrugs) {
                int stockQuantity = drugService.getStockQuantity(drug.getDrugId());
                Object[] rowData = {drug.getName(), drug.getPrice(), stockQuantity};
                drugTableModel.addRow(rowData);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error updating drug list", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void openAddDrugWindow() {
        new AddDrugWindow(drugService);
    }
}
