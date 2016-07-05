package com.epam.inet.provider.service;

import com.epam.inet.provider.dao.exception.DaoException;
import com.epam.inet.provider.entity.Role;
import com.epam.inet.provider.entity.User;
import com.epam.inet.provider.service.exception.ServiceException;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import static com.epam.inet.provider.util.Constants.*;

/**
 * ClientService singleton
 */
public class ClientService extends Service {

    private static final Logger LOGGER = LogManager.getLogger(ClientService.class);

    public ClientService() {
    }

    public boolean isRegularClient(User user) throws ServiceException {

        if (user != null) {
            if (user.getRole().getRolename().equals(Role.ROLE_CLIENT)) {
                try {
                    return userDao.isRegular(user.getId());
                } catch (DaoException e) {
                    throw new ServiceException(EXC_SERVICE_ERROR_MSG);
                }
            }
            return false;
        }
        return false;
    }

    public User fetchUserByLogin(String userLogin) throws ServiceException {
        try {
            return userDao.findUserByLogin(userLogin);
        } catch (DaoException e) {
            throw new ServiceException(EXC_SERVICE_ERROR_MSG);
        }
    }

    public boolean createNewUser(User newUser) throws ServiceException {
        try {
            if(userDao.create(newUser)) {
                return true;
            } else return false;
        } catch (DaoException e) {
            throw new ServiceException(EXC_SERVICE_ERROR);
        }
    }


}
