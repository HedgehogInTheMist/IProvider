package com.epam.inet.provider.service;

import com.epam.inet.provider.dao.UserDao;
import com.epam.inet.provider.dao.pool.ConnectionPool;
import com.epam.inet.provider.entity.Role;
import com.epam.inet.provider.entity.User;
import com.epam.inet.provider.dao.exception.ConnectionPoolException;
import com.epam.inet.provider.dao.exception.DaoException;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static com.epam.inet.provider.util.Constants.*;

/**
 *  ClientService singleton
 */
public class ClientService {

    private static final Logger LOGGER = LogManager.getLogger(ClientService.class);

    private ClientService(){

    }

    private static ClientService instance;

    public synchronized static ClientService getInstance(){
        if (instance == null){
            instance = new ClientService();
        }
        LOGGER.info(LOG_MSG_CLIENT_SERVICE);
        return instance;
    }

    public static boolean isRegularClient(User user) throws DaoException {

        if (user != null){
            if (user.getRole().getRolename().equals(Role.ROLE_CLIENT)){
                return UserDao.getInstance().isRegular(user.getId());
            }
            return false;
        }
        return false;
    }





}
