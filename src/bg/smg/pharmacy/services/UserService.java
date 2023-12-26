package bg.smg.pharmacy.services;

import bg.smg.pharmacy.model.User;
import bg.smg.pharmacy.util.DBManager;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Base64;

public class UserService implements UserServiceI {
    private DataSource dataSource;
    private Connection connection;

    public UserService() throws SQLException {
        dataSource = DBManager.getInstance().getDataSource();
    }
    @Override
    public void saveUser(User user) {

    }

    @Override
    public User getUser(int id) {
        return null;
    }

    @Override
    public User getUserByUsername(String username) throws SQLException {
        try {
            this.connection = dataSource.getConnection();
            try (PreparedStatement statement = connection.prepareStatement(
                    "SELECT * FROM users WHERE username=?")) {
                statement.setString(1, username);
                ResultSet resultSet = statement.executeQuery();
                resultSet.first();
                User user = new User();
                user.setUsername(resultSet.getString("username"));
                user.setPassword(resultSet.getString("password"));
                user.setTimestamp(resultSet.getTimestamp("created"));
                user.setActive(resultSet.getBoolean("is_active"));
                return user;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            if (connection != null) {
                System.out.println("Closing database connection...");
                connection.close();
                System.out.println("Connection valid: " + connection.isValid(5));
            }
        }
        return null;
    }

    public String encode(String password){
        String encodedPassword = Base64.getEncoder().encodeToString(password.getBytes());
        return encodedPassword;
    }

    public boolean verifyUser(User user) throws SQLException {
        User registeredUser = getUserByUsername(user.getUsername());
        String encodedPwd = encode(user.getPassword());
        if(registeredUser != null && registeredUser.getPassword().equals(encodedPwd))
            return true;
        else
            return false;
    }

    public String decodePassword(String passwordToDecode){
        byte[] decodedBytes = Base64.getDecoder().decode(passwordToDecode);
        return new String(decodedBytes);
    }
}
