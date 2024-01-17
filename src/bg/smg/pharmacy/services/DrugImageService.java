package bg.smg.pharmacy.services;

import bg.smg.pharmacy.util.DBManager;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DrugImageService {

    private Connection connection;

    public DrugImageService() throws SQLException {
        this.connection = DBManager.getInstance().getDataSource().getConnection();
    }

    public List<String> getImageFilenames() throws SQLException {
        List<String> imageFilenames = new ArrayList<>();
        String query = "SELECT image_filename FROM drug";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                String imageFilename = resultSet.getString("image_filename");
                imageFilenames.add(imageFilename);
            }
        }
        return imageFilenames;
    }

    public void updateImageFilename(int drugId, String imageFilename) throws SQLException {
        String updateQuery = "UPDATE drug SET image_filename = ? WHERE drug_id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {
            preparedStatement.setString(1, imageFilename);
            preparedStatement.setInt(2, drugId);
            preparedStatement.executeUpdate();
        }
    }

    public void closeConnection() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }
}
