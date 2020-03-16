package nl.han.oose.dea.spotitube.datasource.dao;

import nl.han.oose.dea.spotitube.controller.dto.UserDTO;
import nl.han.oose.dea.spotitube.datasource.connection.DBConnection;
import nl.han.oose.dea.spotitube.datasource.dao.interfaces.UserDAO;

import javax.inject.Inject;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAOImpl implements UserDAO {
    DBConnection dbConnection;

    @Inject
    public void setDbConnection(DBConnection dbConnection) {
        this.dbConnection = dbConnection;
    }

    @Override
    public UserDTO read(String userLogin) {
        try {
            Connection connection = dbConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM user WHERE user_login = ?");
            preparedStatement.setString(1, userLogin);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                return new UserDTO(
                        resultSet.getString("username"),
                        resultSet.getString("token")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dbConnection.closeConnection();
        }
        return null;
    }
}
