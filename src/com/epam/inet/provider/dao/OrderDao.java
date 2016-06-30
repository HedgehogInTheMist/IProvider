package com.epam.inet.provider.dao;

import com.epam.inet.provider.dao.pool.ConnectionPool;
import com.epam.inet.provider.entity.Order;
import com.epam.inet.provider.entity.Tariff;
import com.epam.inet.provider.entity.User;
import com.epam.inet.provider.dao.exception.ConnectionPoolException;
import com.epam.inet.provider.dao.exception.DaoException;
import com.epam.inet.provider.resource.MsgManager;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

import static com.epam.inet.provider.util.Constants.*;

/**
 *  Deals with a bunch of methods performing OrderDao functions
 *  Created by Hedgehog on 18.05.2016.
 */
public class OrderDao extends AbstractDao<Integer, Order> {

    private static final Logger LOGGER = LogManager.getLogger(OrderDao.class);

    private static final String SELECT_USER_ORDERS = "SELECT order.id, order.paid, user.username, tariff.tariffname, order.amount, order.order_date FROM `order` JOIN `tariff` ON order.service_id = tariff.id JOIN user ON user.id = order.client_id WHERE order.client_id = ?";
    private static final String EMPTY_USER = "EMPTY USER";
    private static final String UPDATE_ORDER = "UPDATE `order` SET paid=? WHERE id =?";
    private final String CREATE_ORDER = "INSERT INTO `order` (service_id, client_id, amount, order_date) VALUES (?, ?, ?, ?)";
    private final String SELECT_ORDERS = "SELECT order.id, order.paid, user.username, tariff.tariffname, order.amount, order.order_date FROM `order` JOIN `tariff` ON order.service_id = tariff.id JOIN user ON user.id = order.client_id /*where tour_purchase.paid=0*/"; // запрос для админа

    public OrderDao(){}

    /**
     * Fetches all orders
     * @return
     * @throws DaoException
     */
    @Override
    public List<Order> findAll() throws DaoException {
        ConnectionPool connectionPool = null;
        try{
            connectionPool = ConnectionPool.getInstance();
        } catch (ConnectionPoolException e){
            throw new DaoException(e);
        }
        Connection connection = connectionPool.getConnection();
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        LinkedList<Order> orders = new LinkedList<Order>();
        if (connection != null){
            try {
                statement = connection.prepareStatement(SELECT_ORDERS);
                resultSet = statement.executeQuery();
                while(resultSet.next()){
                    Order order = createOrder(resultSet);
                    orders.add(order);
                }
                return orders;
            } catch (SQLException e) {
                LOGGER.info(DAO_ERROR_FETCH_ALL_ORDERS, e);
                throw new DaoException(DAO_ERROR_FETCH_ALL_ORDERS, e);
            } finally {
                connectionPool.closeResources(statement, resultSet);
                connectionPool.release(connection);
            }
        } else{
            throw new DaoException(NO_CONNECTION);
        }
    }

    /**
     * find orders for user
     * @param user
     * @return
     * @throws DaoException
     */
    public List<Order> findOrdersForUser(User user) throws DaoException {
        if (user != null){
            ConnectionPool connectionPool = null;
            try{
                connectionPool = ConnectionPool.getInstance();
            } catch (ConnectionPoolException e){
                throw new DaoException(e);
            }
            Connection connection = connectionPool.getConnection();
            PreparedStatement statement = null;
            ResultSet resultSet = null;
            LinkedList<Order> orders = new LinkedList<Order>();
            if (connection != null){
                try {
                    statement = connection.prepareStatement(SELECT_USER_ORDERS);
                    statement.setInt(1, user.getId());
                    resultSet = statement.executeQuery();
                    while(resultSet.next()){
                        Order order = createOrder(resultSet);
                        orders.add(order);
                    }
                    return orders;
                } catch (SQLException e) {
                    LOGGER.info(DAO_ERROR_FETCH_ALL_ORDER_BY_USER, e);
                    throw new DaoException(DAO_ERROR_FETCH_ALL_ORDER_BY_USER, e);
                } finally {
                    connectionPool.closeResources(statement, resultSet);
                    connectionPool.release(connection);
                }
            } else{
                throw new DaoException(NO_CONNECTION);
            }
        } else {
            throw new DaoException(EMPTY_USER);
        }

    }

    /**
     * Creates order
     * @param set
     * @return
     * @throws SQLException
     */
    private Order createOrder(ResultSet set) throws SQLException{
        Order order = new Order();
        order.setId(set.getInt(ORDER_ID));
        order.setPaid(set.getBoolean(ORDER_PAID));

        User user = new User();
        user.setUsername(set.getString(USER_LOGIN));
        order.setUser(user);

        Tariff tariff = new Tariff();
        tariff.setTariffname(set.getString(TARIFF_NAME));
        order.setTariff(tariff);

        order.setAmount(set.getDouble(TARIFF_AMOUNT));
        order.setDateTime(set.getTimestamp(ORDER_DATE));

        return order;
    }

    /**
     * Creates an order
     * @param entity
     * @return
     * @throws DaoException
     */
    @Override
    public boolean create(Order entity) throws DaoException {
        if (entity != null){
            ConnectionPool connectionPool = null;
            try{
                connectionPool = ConnectionPool.getInstance();
            } catch (ConnectionPoolException e){
                throw new DaoException(e);
            }
            Connection connection = connectionPool.getConnection();
            PreparedStatement statement = null;
            if (connection != null){
                try {
                    statement = connection.prepareStatement(CREATE_ORDER);
                    statement.setInt(1, entity.getTariff().getId());
                    statement.setInt(2, entity.getUser().getId());
                    statement.setDouble(3, entity.getAmount());
                    statement.setDate(4, new Date(entity.getDateTime().getTime()));
                    int affected = statement.executeUpdate();
                    return (affected > 0);
                } catch (SQLException e) {
                    LOGGER.info(DAO_ERROR_CREATE_NEW_ORDER, e);
                    throw new DaoException(DAO_ERROR_CREATE_NEW_ORDER, e);
                } finally {
                    connectionPool.closeStatement(statement);
                    connectionPool.release(connection);
                }
            } else{
                throw new DaoException(NO_CONNECTION);
            }
        }
        return false;
    }

    /**
     * Updates tariff's records if their where paid
     * @param id
     * @param paid
     * @throws DaoException
     */
    public void updatePaid(Integer id, boolean paid) throws  DaoException {
        ConnectionPool connectionPool = null;
        try{
            connectionPool = ConnectionPool.getInstance();
        } catch (ConnectionPoolException e){
            throw new DaoException(e);
        }
        Connection connection = connectionPool.getConnection();
        PreparedStatement statement = null;
        if (connection != null){
            try {
                statement = connection.prepareStatement(UPDATE_ORDER);
                statement.setBoolean(1, paid);
                statement.setInt(2, id);
                int affected = statement.executeUpdate();
                if (affected < 0){
                    throw new DaoException(NO_ROWS_AFFECTED);
                } else{
                    return;
                }
            } catch (SQLException e) {
                LOGGER.info(DAO_ERROR_UPDATE_PAID_FIELD, e);
                throw new DaoException(DAO_ERROR_UPDATE_PAID_FIELD, e);
            } finally {
                connectionPool.closeStatement(statement);
                connectionPool.release(connection);
            }
        } else{
            throw new DaoException(NO_CONNECTION);
        }
    }

    /**
     * Updates order record
     * @param entity
     * @return
     * @throws DaoException
     */
    @Override
    public boolean update(Order entity) throws DaoException {
        return false;
    }

    /**
     * Fetches order by id
     * @param id
     * @return
     * @throws DaoException
     */
    @Override
    public Order findById(Integer id) throws DaoException {
        return null;
    }

    @Override
    public boolean delete(Integer id) throws DaoException {
        return false;
    }

    @Override
    public boolean delete(Order entity) throws DaoException {
        return false;
    }

    /**
     * Creates new order entity
     * @param set
     * @return
     * @throws SQLException
     */
    @Override
    public Order createEntity(ResultSet set) throws SQLException {
        Order order = new Order();
        order.setId(set.getInt(ORDER_ID));
        order.setAmount(set.getDouble(ORDER_AMOUNT));
        order.setDateTime(set.getTimestamp(ORDER_DATE));
        order.setPaid(set.getBoolean(ORDER_PAID));
        return order;
    }
}
