package com.epam.inet.provider.dao;

import com.epam.inet.provider.dao.exception.ConnectionPoolException;
import com.epam.inet.provider.dao.exception.DaoException;
import com.epam.inet.provider.dao.pool.ConnectionPool;
import com.epam.inet.provider.entity.Role;
import com.epam.inet.provider.entity.User;
import com.epam.inet.provider.resource.MessageManager;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import static com.epam.inet.provider.util.Constants.*;

/**
 * Deals with a bunch of methods performing UserDao functions
 * Created by Hedgehog on 15.05.2016.
 */
public class UserDao extends AbstractDao<Integer, User> {

    private static final Logger LOGGER = Logger.getRootLogger();

    private static final String SELECT_ALL = "SELECT user.id, user.username, user.password, user.role_id, role.rolename FROM user JOIN role ON user.role_id = role.id";
    private static final String FIND_BY_ID = "SELECT user.id, user.username, user.password, user.role_id, role.rolename FROM user JOIN role ON user.role_id = role.id WHERE user.id = ?";
    private static final String FIND_BY_LOGIN_PASSWORD = "SELECT user.id, user.username, user.password, user.role_id, role.rolename FROM user JOIN role ON user.role_id = role.id WHERE user.username = ? AND user.password = ?";
    private static final String DELETE_BY_ID = "DELETE FROM user WHERE id = ?";
    private static final String CREATE_USER = "INSERT INTO user (username, password, role_id) VALUES(?, ?, ?)";
    private static final String UPDATE_USER = "UPDATE users SET username = ?, password = ?, role_id = ? WHERE id = ?";
//    private static final String USER_NOT_FOUND = "User not found";

    private static final String SQL_FIND_BY_LOGIN = "SELECT id, username, role_id from user where username=?";
    private static final String SQL_ADD_USER = "INSERT INTO `ifuture_provider`.`user` (`username`, `password`, `role_id`) VALUES (?, ?, ?)";
    private static final String SQL_QUERY = "SELECT client_info.is_regular FROM user  LEFT JOIN client_info ON user.id = client_info.user_id WHERE user.id = ?";
    private static final String SQL_ERROR = "Sql error";
    private static final String NO_CONNECTION_MESSAGE = "Unable to get connection";

    public UserDao() {
    }

    /**
     * Fetches all users
     *
     * @return
     * @throws DaoException
     */
    @Override
    public List<User> findAll() throws DaoException {

        ConnectionPool connectionPool = null;
        try {
            connectionPool = ConnectionPool.getInstance();
        } catch (ConnectionPoolException e) {
            throw new DaoException(e);
        }
        Connection connection = connectionPool.getConnection();
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        LinkedList<User> users = new LinkedList<User>();
        if (connection != null) {
            try {
                statement = connection.prepareStatement(SELECT_ALL);
                resultSet = statement.executeQuery();
                while (resultSet.next()) {
                    User user = createEntity(resultSet);
                    users.add(user);
                }
                return users;
            } catch (SQLException e) {
                LOGGER.info(LOGGER_MSG_DAO_ERROR_FETCH_ALLUSERS);
                throw new DaoException(DAO_ERROR_FETCH_ALLUSERS, e);
            } finally {
                connectionPool.closeResources(statement, resultSet);
                connectionPool.release(connection);
            }
        } else {
            throw new DaoException(NO_CONNECTION);
        }
    }

    /**
     * Fetches a user by id
     *
     * @param id
     * @return
     */
    @Override
    public User findById(Integer id) throws DaoException {

        User user = null;

        if (null != id) {
            ConnectionPool connectionPool = null;
            try {
                connectionPool = ConnectionPool.getInstance();
            } catch (ConnectionPoolException e) {
                throw new DaoException(e);
            }
            Connection connection = connectionPool.getConnection();
            PreparedStatement statement = null;
            ResultSet resultSet = null;
            if (connection != null) {
                try {
                    statement = connection.prepareStatement(FIND_BY_ID);
                    statement.setInt(1, id);
                    resultSet = statement.executeQuery();
                    if (resultSet.next()) {
                        user = createEntity(resultSet);
                    }
                } catch (SQLException e) {
                    throw new DaoException(e);
                } finally {
                    connectionPool.closeResources(statement, resultSet);
                    connectionPool.release(connection);
                }
            } else {
                throw new DaoException(NO_CONNECTION);
            }
        }

        return user;
    }

    /**
     * Fetches user by username and hashed password
     *
     * @param login
     * @param password
     * @return
     */
    public User findByLoginAndPassword(String login, String password) throws DaoException {
        User user = null;
        if (login != null && password != null) {
            ConnectionPool connectionPool = null;
            try {
                connectionPool = ConnectionPool.getInstance();
            } catch (ConnectionPoolException e) {
                throw new DaoException(e);
            }
            Connection connection = connectionPool.getConnection();
            PreparedStatement statement = null;
//            ResultSet resultSet = null;
            if (connection != null) {
                try {
                    statement = connection.prepareStatement(FIND_BY_LOGIN_PASSWORD);
                    statement.setString(1, login);
                    statement.setString(2, password);
                    ResultSet resultSet = statement.executeQuery();
                    if (resultSet.next()) {
                        user = createEntity(resultSet);
                    } else {
                        LOGGER.info(USER_NOT_FOUND + login);
                        throw new DaoException(USER_NOT_FOUND);
                    }
                } catch (SQLException e) {
                    throw new DaoException(e);
                } finally {
//                    connectionPool.closeResources(statement, resultSet);
                    connectionPool.closeStatement(statement);
                    connectionPool.release(connection);
                }
            } else {
                throw new DaoException(NO_CONNECTION);
            }
        }
        return user;
    }

    /**
     * Delete user by id
     *
     * @param id
     * @return
     */
    @Override
    public boolean delete(Integer id) throws DaoException {

        if (null != id) {

            ConnectionPool connectionPool = null;
            try {
                connectionPool = ConnectionPool.getInstance();
            } catch (ConnectionPoolException e) {
                throw new DaoException(e);
            }

            Connection connection = connectionPool.getConnection();
            PreparedStatement statement = null;
            if (connection != null) {
                try {
                    statement = connection.prepareStatement(DELETE_BY_ID);
                    statement.setInt(1, id);
                    int affected = statement.executeUpdate();
                    return (affected > 0);

                } catch (SQLException e) {
                    LOGGER.info(DAO_ERROR_DELETE_USER_BY_ID, e);
                    throw new DaoException(DAO_ERROR_DELETE_USER_BY_ID, e);
                } finally {
                    connectionPool.closeStatement(statement);
                    connectionPool.release(connection);
                }
            } else {
                throw new DaoException(NO_CONNECTION);
            }
        }
        return true;
    }

    /**
     * Delete user
     *
     * @param entity
     * @return
     */
    @Override
    public boolean delete(User entity) throws DaoException {
        if (entity != null) {
            return delete(entity.getId());
        } else {
            throw new DaoException(INVALID_DATA);
        }
    }

    /**
     * Creates user entity out of thing air
     *
     * @param entity
     * @return
     */
    @Override
    public boolean create(User entity) throws DaoException {
        if (entity != null) {
            ConnectionPool connectionPool = null;
            try {
                connectionPool = ConnectionPool.getInstance();
            } catch (ConnectionPoolException e) {
                throw new DaoException(e);
            }
            // Feed plain password to DigestUtils in order to get md5 sequence of password
            String password = entity.getPassword();
            String hashedPassword = DigestUtils.md5Hex(password);
            Connection connection = connectionPool.getConnection();
            PreparedStatement statement = null;
            if (connection != null) {
                try {
                    statement = connection.prepareStatement(CREATE_USER);
                    statement.setString(1, entity.getUsername());
                    statement.setString(2, hashedPassword);
                    statement.setInt(3, 1);
                    int affected = statement.executeUpdate();
                    if (affected > 0) {
                        return true;
                    }
                } catch (SQLException e) {
                    LOGGER.info(DAO_ERROR_CREATE_USER, e);
                    throw new DaoException(DAO_ERROR_CREATE_USER, e);
                } finally {
                    connectionPool.closeStatement(statement);
                    connectionPool.release(connection);
                }
            } else {
                throw new DaoException(NO_CONNECTION);
            }
        }
        return true;
    }

    /**
     * Updates user
     *
     * @param entity
     * @return
     */
    @Override
    public boolean update(User entity) throws DaoException {
        if (entity != null) {
            ConnectionPool connectionPool = null;
            try {
                connectionPool = ConnectionPool.getInstance();
            } catch (ConnectionPoolException e) {
                throw new DaoException(e);
            }
            Connection connection = connectionPool.getConnection();
            PreparedStatement statement = null;
            if (connection != null) {
                try {
                    statement = connection.prepareStatement(UPDATE_USER);
                    statement.setString(1, entity.getUsername());
                    statement.setString(2, entity.getPassword());
                    statement.setInt(3, entity.getRole().getId());
                    statement.setInt(4, entity.getId());
                    int affected = statement.executeUpdate();
                    if (affected > 0) {
                        return true;
                    } else {
                        throw new DaoException(NO_ROWS_AFFECTED);
                    }
                } catch (SQLException e) {
                    throw new DaoException(e);
                } finally {
                    connectionPool.closeStatement(statement);
                    connectionPool.release(connection);
                }
            } else {
                throw new DaoException(NO_CONNECTION);
            }
        }
        return true;
    }

    /**
     * Creates user from result set
     *
     * @param set
     * @return
     * @throws SQLException
     */

    @Override
    public User createEntity(ResultSet set) throws SQLException {

        User user = new User();
        user.setId(set.getInt("id"));
        user.setUsername(set.getString("username"));
        user.setPassword(set.getString("password"));

        Role role = new Role();
        role.setId(set.getInt("role_id"));
        role.setRolename(set.getString("rolename"));

        user.setRole(role);

        return user;
    }

    /**
     * Fetches user by username from DB
     *
     * @param username
     * @return
     * @throws DaoException
     */
    public User findUserByLogin(String username) throws DaoException {
        ConnectionPool connectionPool = null;
        try {
            connectionPool = ConnectionPool.getInstance();
        } catch (ConnectionPoolException e) {
            throw new DaoException(e);
        }
        Connection connection = connectionPool.getConnection();
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        User user = null;
        try {
            statement = connection.prepareStatement(SQL_FIND_BY_LOGIN);
            statement.setString(1, username);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                user = buildUser(resultSet);
                LOGGER.info(MessageManager.getProperty(LOGGER_MSG_USER_FIND_BY_LOGIN));
            } else {
                LOGGER.info(MessageManager.getProperty(LOGGER_MSG_USER_FIND_BY_LOGIN_NOT_FOUND) + username);
            }
        } catch (SQLException e) {
            LOGGER.info(DAO_ERROR_FETCH_USER_BY_LOGIN);
            throw new DaoException(DAO_ERROR_FETCH_USER_BY_LOGIN, e);
        } finally {
            connectionPool.closeResources(statement, resultSet);
            connectionPool.release(connection);
        }
        return user;
    }

    /**
     * This method builds user from ResultSet
     *
     * @param resultSet the param uses for returning result of query from database
     * @return user
     * @throws SQLException, if arise database access error or other database errors
     */
    private User buildUser(ResultSet resultSet) throws SQLException {
        User user = new User();
        user.setId(resultSet.getInt(USER_ID));
        user.setUsername(resultSet.getString(USER_LOGIN));
        return user;
    }

    /**
     * Checks on weather user is regular client or not
     *
     * @param id
     * @return
     * @throws DaoException
     */
    public boolean isRegular(Integer id) throws DaoException {
        if (id != null) {
            ConnectionPool connectionPool = null;
            try {
                connectionPool = ConnectionPool.getInstance();
            } catch (ConnectionPoolException e) {
                throw new DaoException(e);
            }
            Connection connection = connectionPool.getConnection();
            PreparedStatement statement = null;
            ResultSet resultSet = null;
            if (connection != null) {
                try {
                    statement = connection.prepareStatement(SQL_QUERY);
                    statement.setInt(1, id);
                    resultSet = statement.executeQuery();
                    if (resultSet.next()) {
                        return resultSet.getBoolean(IS_REGULAR);
                    } else {
                        throw new DaoException(SQL_ERROR);
                    }
                } catch (SQLException e) {
                    throw new DaoException(e);
                } finally {
                    connectionPool.closeResources(statement, resultSet);
                    connectionPool.release(connection);
                }
            } else {
                throw new DaoException(NO_CONNECTION_MESSAGE);
            }
        }
        return false;
    }

}
