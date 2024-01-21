package bg.smg.pharmacy.services;

import bg.smg.pharmacy.model.Drug;
import bg.smg.pharmacy.model.Ingredient;
import bg.smg.pharmacy.util.DBManager;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DrugService implements DrugServiceI{
    private DataSource dataSource;

    public DrugService() throws SQLException {
        dataSource = DBManager.getInstance().getDataSource();
    }

    public List<Drug> getAllDrugs() throws SQLException {
        List<Drug> drugs = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM drug WHERE is_deleted = false")) {

            while (resultSet.next()) {
                Drug drug = new Drug();
                drug.setDrugId(resultSet.getInt("drug_id"));
                drug.setName(resultSet.getString("name"));
                drug.setPrice(resultSet.getDouble("price"));
                drug.setDescription(resultSet.getString("description"));
                drug.setPrescriptionRequired(resultSet.getBoolean("prescription_required"));
                drug.setStandardDosage(resultSet.getString("standard_dosage"));
                drug.setDeleted(false);
                drugs.add(drug);
            }
        }
        return drugs;
    }

    public Drug getDrugById(int drugId) throws SQLException {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM drug WHERE drug_id = ?")) {

            statement.setInt(1, drugId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Drug drug = new Drug(
                        resultSet.getString("name"),
                        resultSet.getDouble("price"),
                        resultSet.getString("description"),
                        resultSet.getBoolean("prescription_required"),
                        resultSet.getString("standard_dosage"));
                drug.setDrugId(resultSet.getInt("drug_id"));
                drug.setDeleted(resultSet.getBoolean("is_deleted"));
                return drug;
            }
        }
        return null;
    }

    public void updateDrug(Drug drug) throws SQLException {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "UPDATE drug SET name=?, price=?, description=?, prescription_required=?, standard_dosage=? WHERE drug_id=?")) {

            statement.setString(1, drug.getName());
            statement.setDouble(2, drug.getPrice());
            statement.setString(3, drug.getDescription());
            statement.setBoolean(4, drug.isPrescriptionRequired());
            statement.setString(5, drug.getStandardDosage());
            statement.setInt(6, drug.getDrugId());

            statement.executeUpdate();
        }
    }

    public void deleteDrug(int drugId) throws SQLException {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("UPDATE drug SET is_deleted = true WHERE drug_id = ?")) {
            preparedStatement.setInt(1, drugId);
            preparedStatement.executeUpdate();
        }
    }

    public int getStockQuantity(int drugId) throws SQLException {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT quantity FROM stock WHERE drug_id = ?")) {

            statement.setInt(1, drugId);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getInt("quantity");
            } else {
                return 0;
            }
        }
    }

    public List<Ingredient> getIngredientsForDrug(int drugId) throws SQLException {
        List<Ingredient> ingredientInfoList = new ArrayList<>();

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "SELECT i.name AS ingredient_name, di.ingredient_weight " +
                             "FROM drug_ingredient di " +
                             "JOIN ingredient i ON di.ingredient_id = i.ingredient_id " +
                             "WHERE di.drug_id = ?")) {

            statement.setInt(1, drugId);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String ingredientName = resultSet.getString("ingredient_name");
                int ingredientWeight = resultSet.getInt("ingredient_weight");
                ingredientInfoList.add(new Ingredient(ingredientName, ingredientWeight));
            }
        }

        return ingredientInfoList;
    }

    public void addDrug(Drug drug) throws SQLException {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "INSERT INTO drug (name, price, description, prescription_required, standard_dosage) " +
                             "VALUES (?, ?, ?, ?, ?)")) {

            statement.setString(1, drug.getName());
            statement.setDouble(2, drug.getPrice());
            statement.setString(3, drug.getDescription());
            statement.setBoolean(4, drug.isPrescriptionRequired());
            statement.setString(5, drug.getStandardDosage());

            statement.executeUpdate();
        }
    }
}

