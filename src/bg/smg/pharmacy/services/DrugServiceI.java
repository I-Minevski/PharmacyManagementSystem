package bg.smg.pharmacy.services;

import bg.smg.pharmacy.model.Drug;
import bg.smg.pharmacy.model.Ingredient;

import java.sql.SQLException;
import java.util.List;

public interface DrugServiceI {
    public List<Drug> getAllDrugs() throws SQLException;
    public Drug getDrugById(int drugId) throws SQLException;
    public void updateDrug(Drug drug) throws SQLException;
    public void deleteDrug(int drugId) throws SQLException;
    public int getStockQuantity(int drugId) throws SQLException;
    public List<Ingredient> getIngredientsForDrug(int drugId) throws SQLException;
}
