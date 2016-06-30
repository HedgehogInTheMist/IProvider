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


}
