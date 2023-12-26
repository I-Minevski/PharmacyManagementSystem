package bg.smg.pharmacy.services;

import bg.smg.pharmacy.model.User;

import java.sql.SQLException;

public interface UserServiceI {
    public void saveUser(User user);
    public User getUser(int id);
    public User getUserByUsername(String username) throws SQLException;
    public boolean verifyUser(User user) throws SQLException;
}

