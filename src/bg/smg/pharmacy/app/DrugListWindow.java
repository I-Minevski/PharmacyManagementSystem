package bg.smg.pharmacy.app;
import bg.smg.pharmacy.model.Drug;
import bg.smg.pharmacy.model.Ingredient;
import bg.smg.pharmacy.services.DrugImageService;
import bg.smg.pharmacy.services.DrugService;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;

public class DrugListWindow extends JFrame {

    private JList<String> drugList;
    private DefaultListModel<String> drugListModel;
    private JButton detailsButton;
    private JButton editButton;
    private JButton deleteButton;
    private DrugService drugService;
    private DrugImageService drugImageService;

    public DrugListWindow() throws SQLException {
        try {
            drugService = new DrugService();
            drugImageService = new DrugImageService();
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

        drugListModel = new DefaultListModel<>();
        for (Drug drug : drugs) {
            int stockQuantity = drugService.getStockQuantity(drug.getDrugId());

            drugListModel.addElement(drug.getName() + " - $" + drug.getPrice() + " - Stock: " + stockQuantity);
        }
        drugList = new JList<>(drugListModel);

        detailsButton = new JButton("Details");
        editButton = new JButton("Edit");
        deleteButton = new JButton("Delete");

        detailsButton.addActionListener(e -> showDrugDetails());
        editButton.addActionListener(e -> editDrug());
        deleteButton.addActionListener(e -> deleteDrug());

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(detailsButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);

        mainPanel.add(new JScrollPane(drugList), BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void showDrugDetails() {
        int selectedIndex = drugList.getSelectedIndex();
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
        int selectedIndex = drugList.getSelectedIndex();
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
        int selectedIndex = drugList.getSelectedIndex();
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
        drugListModel.clear();
        List<Drug> updatedDrugs;
        try {
            updatedDrugs = drugService.getAllDrugs();

            for (Drug drug : updatedDrugs) {
                int stockQuantity = drugService.getStockQuantity(drug.getDrugId());

                drugListModel.addElement(drug.getName() + " - $" + drug.getPrice() + " - Stock: " + stockQuantity);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error updating drug list", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}

