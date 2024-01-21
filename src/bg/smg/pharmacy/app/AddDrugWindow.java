package bg.smg.pharmacy.app;

import bg.smg.pharmacy.model.Drug;
import bg.smg.pharmacy.services.DrugService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class AddDrugWindow extends JFrame {

    private JTextField nameField;
    private JTextField priceField;
    private JTextArea descriptionArea;
    private JCheckBox prescriptionCheckBox;
    private JTextField dosageField;
    private JButton addButton;
    private DrugService drugService;

    public AddDrugWindow(DrugService drugService) {
        this.drugService = drugService;

        setTitle("Add Drug");
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

        addButton = new JButton("Add Drug");
        addButton.addActionListener(new AddButtonListener());
        mainPanel.add(addButton);

        add(mainPanel);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private class AddButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String name = nameField.getText();
            double price = Double.parseDouble(priceField.getText());
            String description = descriptionArea.getText();
            boolean prescriptionRequired = prescriptionCheckBox.isSelected();
            String standardDosage = dosageField.getText();

            Drug newDrug = new Drug(name, price, description, prescriptionRequired, standardDosage);

            try {
                drugService.addDrug(newDrug);
                JOptionPane.showMessageDialog(AddDrugWindow.this, "Drug added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                dispose();
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(AddDrugWindow.this, "Error adding drug", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
