package com.epam.inet.provider.dao.pool;

import com.epam.inet.provider.dao.exception.ConnectionPoolException;
import com.epam.inet.provider.resource.DBConfigurationManager;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.Iterator;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static com.epam.inet.provider.util.Constants.*;

/**
 * Database connection pool
 */
public class ConnectionPool implements Pool<Connection>{

    /**
     * Singleton instace
     */
    private static ConnectionPool instance = null;
    /**
     * Concurrent fair lock
     */
    private static Lock lock = new ReentrantLock(true);

    /**
     * Pool size
     */
    private final int POOL_SIZE = 10;

    /**
     * Maximum waiting time
     */
    private final int MAX_WAITING_TIME = 2;

    /**
     * Database configuration manager
     */
    private final DBConfigurationManager config = DBConfigurationManager.INSTANCE;

    /**
     * Logger
     */
    private static final Logger LOGGER = Logger.getRootLogger();

    /**
     * Connections
     */
    private BlockingQueue<Connection> connections;

    private ConnectionPool() throws ConnectionPoolException{
        initConnections();
    }

    /**
     * Get single instance
     * @return
     */
    public static ConnectionPool getInstance() throws ConnectionPoolException {

        lock.lock();
        if (null == instance){
            instance = new ConnectionPool();
        }
        lock.unlock();

        return instance;
    }
    /**
     * Create and fill connection pool
     */
    private final void initConnections() throws ConnectionPoolException{
        connections = new LinkedBlockingQueue<Connection>(POOL_SIZE);

        String connectionUrl = config.getString(DBConfigurationManager.DATABASE_CONNECTION_URL);
        String username = config.getString(DBConfigurationManager.DATABASE_USERNAME);
        String password = config.getString(DBConfigurationManager.DATABASE_PASSWORD);
        String driverName = config.getString(DBConfigurationManager.DATABASE_DRIVER_NAME);

        try {
            Class.forName(driverName);
            for (int i = 0; i < POOL_SIZE; i++){
                Connection connection = DriverManager.getConnection(connectionUrl, username, password);
                connections.add(connection);
            }
        } catch (SQLException e) {
            throw new ConnectionPoolException(e);

        } catch (ClassNotFoundException e) {
            throw new ConnectionPoolException(e);
        }
    }

    /**
     * Get single connection from concurrent queue
     * @return connection to use
     */
    @Override
    public final Connection getConnection() {
        try {
            Connection connection = connections.poll(MAX_WAITING_TIME, TimeUnit.SECONDS);
            if (connection != null) {
                LOGGER.info(connection + LOG_MSG_CONNECTION_TAKEN);
            } else {
                LOGGER.error(LOG_MSG_CONNECTION_RETRIEVE_INVALID);
            }
            return connection;
        } catch (InterruptedException e) {
            LOGGER.error(e.getMessage());
            return null;
        }
    }

    /**
     * Return connection
     * @param connection connection to return to the pool
     */
    public final void release(Connection connection) {
        if (connection != null) {
            try {
                connections.put(connection);
                LOGGER.info(connection + LOG_MSG_CONNECTION_RETURNED);
                LOGGER.info((connections.size() - connections.remainingCapacity()) + LOG_MSG_CONNECTION_REMAIN_IN_POOL);
            } catch (InterruptedException e) {
                LOGGER.error(e.getMessage());
            }
        }
    }

    /**
     * Retrieve pool size
     * @return
     */
    public final int getPoolSize(){
        return POOL_SIZE;
    }

    /**
     * Close all connection
     */
    public void shutDown(){

        Iterator<Connection> iterator = connections.iterator();
        while(iterator.hasNext()){
            Connection connection = iterator.next();
            try {
                // close connection
                connection.close();
                // remove it to prevent the use of closed connection
                iterator.remove();
            } catch (SQLException e) {
                LOGGER.error(LOG_MSG_CLOSE_CONNECTION_ERROR + e.getMessage());
            }
        }
        LOGGER.info(LOG_MSG_POOL_SHUT_DOWN);
    }

    public final void closeResources(PreparedStatement statement, ResultSet resultSet) {
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                LOGGER.error(ERROR_CLOSING_STATEMENT, e);
            }
        }
        if (resultSet != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                LOGGER.error(ERROR_CLOSING_RESULT_SET, e);
            }
        }
    }

    public final void closeStatement(PreparedStatement statement) {
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                LOGGER.error(ERROR_CLOSING_STATEMENT, e);
            }
        }
    }

}
