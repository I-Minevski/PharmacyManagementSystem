package bg.smg.pharmacy.app;
import bg.smg.pharmacy.model.Drug;
import bg.smg.pharmacy.services.DrugService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DrugListWindow extends JFrame {

    private JList<String> drugList;
    private DefaultListModel<String> drugListModel;
    private JButton detailsButton;
    private JButton editButton;
    private JButton deleteButton;
    private DrugService drugService;

    public DrugListWindow() {
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

        drugListModel = new DefaultListModel<>();
        for (Drug drug : drugs) {
            drugListModel.addElement(drug.getName() + " - $" + drug.getPrice());
        }
        drugList = new JList<>(drugListModel);

        // Create buttons
        detailsButton = new JButton("Details");
        editButton = new JButton("Edit");
        deleteButton = new JButton("Delete");

        detailsButton.addActionListener(e -> showDrugDetails());
        editButton.addActionListener(e -> editDrug());
        deleteButton.addActionListener(e -> deleteDrug());

        // Create button panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(detailsButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);

        // Add components to main panel
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

                // Create and show the DrugDetailsWindow
                new DrugDetailsWindow(selectedDrug);
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

                // Create and show the DrugEditWindow with the drugService instance
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

                // Perform soft delete
                drugService.deleteDrug(selectedDrug.getDrugId());

                // Update the drug list
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
                drugListModel.addElement(drug.getName() + " - $" + drug.getPrice());
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error updating drug list", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}

