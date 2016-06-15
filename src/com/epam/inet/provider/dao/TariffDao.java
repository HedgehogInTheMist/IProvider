package com.epam.inet.provider.dao;

import com.epam.inet.provider.dao.pool.ConnectionPool;
import com.epam.inet.provider.entity.Tariff;
import com.epam.inet.provider.entity.TariffType;
import com.epam.inet.provider.dao.exception.ConnectionPoolException;
import com.epam.inet.provider.dao.exception.DaoException;
import com.epam.inet.provider.resource.MsgManager;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import static com.epam.inet.provider.util.Constants.*;
/**
 * Tariff DAO
 * Created by Hedgehog on 19.05.2016.
 */
public class TariffDao extends AbstractDao<Integer, Tariff> {

	private final String SELECT_ALL = "SELECT id, tariffname, details, hot, price, regularDiscount, tariff.type FROM `tariff`";
	private final String FIND_BY_ID = "SELECT id, tariffname, details, hot, price, regularDiscount, tariff.type FROM `tariff` WHERE id = ?";
	private final String CREATE_NEW_TARIFF = "INSERT INTO `tariff` (tariffname, details, hot, price, regularDiscount, tariff.type) VALUES(?, ?, ?, ?, ?, ?)";
	private final String UPDATE_BY_ID = "UPDATE `tariff` SET tariffname=?, details=?, hot=?, price=?, regularDiscount=?, tariff.type=? WHERE id=?";
	private final String DELETE_TOUR_BY_ID = "DELETE FROM `tariff` WHERE id = ?";

	private static final Logger LOGGER = Logger.getRootLogger();

    private TariffDao(){}

    private static TariffDao instance;

    public synchronized static TariffDao getInstance(){
        if (instance == null){
            instance = new TariffDao();
        }
		LOGGER.info(MsgManager.getProperty(LOG_MSG_TARIFF_DAO_INIT));
        return instance;
    }

	/**
	 * Fetches all tariff plans record from Database
	 * @return
	 * @throws DAOLogicalException
	 * @throws DaoException
     */
	@Override
	public List<Tariff> findAll() throws DaoException {
		ConnectionPool connectionPool = null;
		try{
			connectionPool = ConnectionPool.getInstance();
		} catch (ConnectionPoolException e){
			throw new DaoException(e);
		}
		Connection connection = connectionPool.getConnection();
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		LinkedList<Tariff> tariffs= new LinkedList<Tariff>();
		if (connection != null){
			try {
				statement = connection.prepareStatement(SELECT_ALL);
				resultSet = statement.executeQuery();
				while(resultSet.next()){
					Tariff tariff = createEntity(resultSet);
					tariffs.add(tariff);
				}
				return tariffs;
			} catch (SQLException e) {
				throw new DaoException(e);
			} finally {
				connectionPool.closeResources(statement, resultSet);
				connectionPool.release(connection);
			}
		} else{
			throw new DaoException(NO_CONNECTION);
		}
	}

    /**
     * Fetches tariff plan by id
     * @param id
     * @return
     * @throws DaoException
     */
	@Override
	public Tariff findById(Integer id) throws DaoException {
		if (id != null){
			ConnectionPool connectionPool = null;
			try {
				connectionPool = ConnectionPool.getInstance();
			} catch (ConnectionPoolException e) {
				throw new DaoException(e);
			}
			Connection connection = connectionPool.getConnection();
			PreparedStatement statement = null;
			ResultSet resultSet = null;
			if (connection != null){
				try {
					statement = connection.prepareStatement(FIND_BY_ID);
				   	statement.setInt(1, id);
					resultSet = statement.executeQuery();
					if (resultSet.next()){
						return createEntity(resultSet);
					} else{
						throw new DaoException(ENTITY_WAS_NOT_FOUND);
					}
				} catch (SQLException e) {
					throw new DaoException(e);
				} finally {
					connectionPool.closeResources(statement, resultSet);
					connectionPool.release(connection);
				}
				}
			} else{
				throw new DaoException(NO_CONNECTION);
			}
		return null;
	}


    /**
     * Delete tariff plan by id
     * @param id
     * @return
     * @throws DaoException
     */
	@Override
	public boolean delete(Integer id) throws DaoException {
        if (null != id) {
            ConnectionPool connectionPool = null;
            try{
                connectionPool = ConnectionPool.getInstance();
            } catch (ConnectionPoolException e){
                throw new DaoException(e);
            }
            Connection connection = connectionPool.getConnection();
            PreparedStatement statement = null;
            if (connection != null) {
                try {
                    statement = connection.prepareStatement(DELETE_TOUR_BY_ID);
                    statement.setInt(1, id);
                    int affected = statement.executeUpdate();
                    if (affected > 0){
                        return true;
                    } else{
                    	LOGGER.info(DAO_ERROR_TARIFF_NO_RECORD);
                        throw new DaoException(DAO_ERROR_TARIFF_NO_RECORD);
                    }
                } catch (SQLException e) {
                    LOGGER.info(DAO_ERROR_DELETE_TARIFF_BY_ID, e);
					throw new DaoException(DAO_ERROR_DELETE_TARIFF_BY_ID, e);
                } finally {
					connectionPool.closeStatement(statement);
					connectionPool.release(connection);
                }
            } else{
                throw new DaoException(NO_CONNECTION);
            }
        } else {
			throw new DaoException(INVALID_DATA);
		}
	}

    /**
     * Delete tariff
     * @param entity
     * @return
     * @throws DaoException
     */
	@Override
	public boolean delete(Tariff entity) throws DaoException {
		return false;  
	}

    /**
     * Create tariff plan
     * @param entity
     * @return
     * @throws DaoException
     */
	@Override
	public boolean create(Tariff entity) throws DaoException {
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
                    statement = connection.prepareStatement(CREATE_NEW_TARIFF);
                    statement.setString(1, entity.getTariffname());
                    statement.setString(2, entity.getDetails());
                    statement.setBoolean(3, entity.isHot());
                    statement.setDouble(4, entity.getPrice());
                    statement.setInt(5, entity.getRegularDiscount());
                    statement.setInt(6, entity.getType().getId());
                    int affected = statement.executeUpdate();
                    return (affected > 0);
                } catch (SQLException e) {
					LOGGER.info(DAO_ERROR_CREATE_TARIFF_PLAN_RECORD, e);
                    throw new DaoException(DAO_ERROR_CREATE_TARIFF_PLAN_RECORD, e);
                } finally {
					connectionPool.closeStatement(statement);
					connectionPool.release(connection);
                }
            } else{
                throw new DaoException(NO_CONNECTION);
            }
        } else {
			throw new DaoException(INVALID_DATA);
		}
	}

	/**
	 * Updates all fields of certain Tariff plan
	 * @param entity
	 * @return
	 * @throws DaoException
     */
	@Override
	public boolean update(Tariff entity) throws DaoException {
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
					statement = connection.prepareStatement(UPDATE_BY_ID);
					statement.setString(1, entity.getTariffname());
					statement.setString(2, entity.getDetails());
					statement.setBoolean(3, entity.isHot());
					statement.setDouble(4, entity.getPrice());
					statement.setInt(5, entity.getRegularDiscount());
					statement.setInt(6, entity.getType().getId());
					statement.setInt(7, entity.getId());
					int affected = statement.executeUpdate();
					if (affected > 0){
						return true;
					} else{
						throw new DaoException(NO_ROWS_AFFECTED);
					}
				} catch (SQLException e) {
					LOGGER.info(DAO_ERROR_UPDATE_TARIFF_PLAN_RECORD, e);
					throw new DaoException(DAO_ERROR_UPDATE_TARIFF_PLAN_RECORD, e);
				} finally {
					connectionPool.closeStatement(statement);
					connectionPool.release(connection);
				}
			} else{
				throw new DaoException(NO_CONNECTION);
			}
		} else {
			throw new DaoException(INVALID_DATA);
		}
	}

	/**
	 * Builds new Tariff Entity
	 * @param set
	 * @return
	 * @throws SQLException
     */
	@Override
	public Tariff createEntity(ResultSet set) throws SQLException{
		Tariff tariff = new Tariff();

		tariff.setId(set.getInt(TARIFF_ID));
		tariff.setDetails(set.getString(TARIFF_DETAILS));

		int hot = set.getInt(TARIFF_HOT);
		tariff.setHot(hot == 1);

		tariff.setPrice(set.getInt(TARIFF_PRICE));
		tariff.setTariffname(set.getString(TARIFF_NAME));
		tariff.setRegularDiscount(set.getInt(TARIFF_REGULAR_DISCOUNT));

		TariffType type = TariffType.findById(set.getInt(TARIFF_TYPE));
		tariff.setType(type);

		return tariff;
	}
}
