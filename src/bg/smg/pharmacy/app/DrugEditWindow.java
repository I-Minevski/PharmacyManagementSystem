package bg.smg.pharmacy.app;

import bg.smg.pharmacy.model.Drug;
import bg.smg.pharmacy.services.DrugService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class DrugEditWindow extends JFrame {

    private JTextField nameField;
    private JTextField priceField;
    private JTextArea descriptionArea;
    private JCheckBox prescriptionCheckBox;
    private JTextField dosageField;
    private JButton updateButton;
    private DrugService drugService;
    private Integer drugId;

    public DrugEditWindow(Integer drugId, DrugService drugService) {
        this.drugId = drugId;
        this.drugService = drugService;

        setTitle("Edit Drug");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel mainPanel = new JPanel(new GridLayout(7, 2));
        mainPanel.add(new JLabel("Name:"));
        nameField = new JTextField(15);
        mainPanel.add(nameField);

        mainPanel.add(new JLabel("Price:"));
        priceField = new JTextField(15);
        mainPanel.add(priceField);

        mainPanel.add(new JLabel("Description:"));
        descriptionArea = new JTextArea(3, 15);
        JScrollPane scrollPane = new JScrollPane(descriptionArea);
        mainPanel.add(scrollPane);

        mainPanel.add(new JLabel("Prescription Required:"));
        prescriptionCheckBox = new JCheckBox();
        mainPanel.add(prescriptionCheckBox);

        mainPanel.add(new JLabel("Standard Dosage:"));
        dosageField = new JTextField(15);
        mainPanel.add(dosageField);

        updateButton = new JButton("Update");
        updateButton.addActionListener(new UpdateButtonListener());
        mainPanel.add(updateButton);

        try {
            // Fetch drug details from the database
            Drug drug = drugService.getDrugById(drugId);

            // Set initial values in the fields
            nameField.setText(drug.getName());
            priceField.setText(String.valueOf(drug.getPrice()));
            descriptionArea.setText(drug.getDescription());
            prescriptionCheckBox.setSelected(drug.isPrescriptionRequired());
            dosageField.setText(drug.getStandardDosage());

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error fetching drug details from the database", "Error", JOptionPane.ERROR_MESSAGE);
            dispose();
        }

        add(mainPanel);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private class UpdateButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            // Get updated values from the fields
            String name = nameField.getText();
            double price = Double.parseDouble(priceField.getText());
            String description = descriptionArea.getText();
            boolean prescriptionRequired = prescriptionCheckBox.isSelected();
            String standardDosage = dosageField.getText();

            // Create a Drug object with updated values
            Drug updatedDrug = new Drug();
            updatedDrug.setDrugId(drugId);
            updatedDrug.setName(name);
            updatedDrug.setPrice(price);
            updatedDrug.setDescription(description);
            updatedDrug.setPrescriptionRequired(prescriptionRequired);
            updatedDrug.setStandardDosage(standardDosage);

            try {
                // Update the drug in the database
                drugService.updateDrug(updatedDrug);
                JOptionPane.showMessageDialog(DrugEditWindow.this, "Drug updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                dispose();
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(DrugEditWindow.this, "Error updating drug", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}

