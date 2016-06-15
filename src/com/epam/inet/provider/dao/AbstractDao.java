package com.epam.inet.provider.dao;

import com.epam.inet.provider.entity.Entity;
import com.epam.inet.provider.dao.exception.DaoException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Abstract Data Access Object
 * @param <K>
 * @param <T>
 */
public abstract class AbstractDao<K, T extends Entity> {
	protected final String NO_CONNECTION = "Unable to establish connection with database. It seems pool run out of connections";
	protected final String ENTITY_WAS_NOT_FOUND = "No entity with such id";
	protected final String INVALID_DATA = "Null or invalid parameter(s)";
    protected final String NO_ROWS_AFFECTED = "No rows affected";

	/**
	 * Find all entites
	 * @return
	 * @throws DaoException
	 */
    public abstract List<T> findAll() throws DaoException;

	/**
	 * Find entity by id
	 * @param id
	 * @return
	 * @throws DaoException
	 */
    public abstract T findById(K id) throws DaoException;

	/**
	 * Delete entity by id
	 * @param id
	 * @return
	 * @throws DaoException
	 */
    public abstract boolean delete(K id) throws DaoException;

	/**
	 * Delete entity
	 * @param entity
	 * @return
	 * @throws DaoException
	 */
    public abstract boolean delete(T entity) throws DaoException;

	/**
	 * Create entity
	 * @param entity
	 * @return
	 * @throws DaoException
	 */
    public abstract boolean create(T entity) throws DaoException;

	/**
	 * Update entity
	 * @param entity
	 * @return
	 * @throws DaoException
	 */
    public abstract boolean update(T entity) throws DaoException;

	public abstract T createEntity(ResultSet set) throws SQLException;
}