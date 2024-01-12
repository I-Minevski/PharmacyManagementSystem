package bg.smg.pharmacy.app;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class DrugListWindow extends JFrame {

    private JList<String> drugList;
    private DefaultListModel<String> drugListModel;
    private JButton detailsButton;
    private JButton editButton;
    private JButton deleteButton;

    public DrugListWindow() {
        setTitle("Drug List");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel mainPanel = new JPanel(new BorderLayout());

        // Create sample drug data
        List<String> sampleDrugs = new ArrayList<>();
        sampleDrugs.add("Drug 1");
        sampleDrugs.add("Drug 2");
        sampleDrugs.add("Drug 3");

        // Create drug list model and JList
        drugListModel = new DefaultListModel<>();
        for (String drug : sampleDrugs) {
            drugListModel.addElement(drug);
        }
        drugList = new JList<>(drugListModel);

        // Create buttons
        detailsButton = new JButton("Details");
        editButton = new JButton("Edit");
        deleteButton = new JButton("Delete");

        // Add action listeners to buttons
        detailsButton.addActionListener(new DetailsButtonListener());
        editButton.addActionListener(new EditButtonListener());
        deleteButton.addActionListener(new DeleteButtonListener());

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

    private class DetailsButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            // Implement details button action
            JOptionPane.showMessageDialog(DrugListWindow.this, "Details Button Clicked");
        }
    }

    private class EditButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            // Implement edit button action
            JOptionPane.showMessageDialog(DrugListWindow.this, "Edit Button Clicked");
        }
    }

    private class DeleteButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            // Implement delete button action
            int selectedIndex = drugList.getSelectedIndex();
            if (selectedIndex != -1) {
                drugListModel.remove(selectedIndex);
                JOptionPane.showMessageDialog(DrugListWindow.this, "Drug deleted");
            } else {
                JOptionPane.showMessageDialog(DrugListWindow.this, "Select a drug to delete", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new DrugListWindow());
    }
}

