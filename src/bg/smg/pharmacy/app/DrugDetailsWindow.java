package bg.smg.pharmacy.app;

import bg.smg.pharmacy.model.Drug;
import bg.smg.pharmacy.model.Ingredient;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class DrugDetailsWindow extends JFrame {

    public DrugDetailsWindow(Drug drug, List<Ingredient> ingredientInfoList) {
        setTitle("Drug Details");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel mainPanel = new JPanel(new GridLayout(7, 2));
        mainPanel.add(new JLabel("Name:"));
        mainPanel.add(new JLabel(drug.getName()));

        mainPanel.add(new JLabel("Price:"));
        mainPanel.add(new JLabel(String.valueOf(drug.getPrice())));

        mainPanel.add(new JLabel("Description:"));
        mainPanel.add(new JLabel(drug.getDescription()));

        mainPanel.add(new JLabel("Prescription Required:"));
        mainPanel.add(new JLabel(String.valueOf(drug.isPrescriptionRequired())));

        mainPanel.add(new JLabel("Standard Dosage:"));
        mainPanel.add(new JLabel(drug.getStandardDosage()));

        mainPanel.add(new JLabel("Ingredients:"));

        for (Ingredient ingredientInfo : ingredientInfoList) {
            mainPanel.add(new JLabel(ingredientInfo.getIngredientName()));
            mainPanel.add(new JLabel("  Amount:"));
            mainPanel.add(new JLabel("" + ingredientInfo.getIngredientWeight()));
        }

        add(mainPanel);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
