package com.epam.inet.provider.service;

import com.epam.inet.provider.entity.User;
import com.epam.inet.provider.dao.exception.DaoException;
import com.epam.inet.provider.service.exception.ServiceException;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static com.epam.inet.provider.util.Constants.*;

/**
 * Performs authentication
 */
public class AuthenticationService extends Service {

    public static final Logger LOGGER = LogManager.getLogger(AuthenticationService.class);

    public static final String PARAMETER_SESSION_USER = "user";

    public AuthenticationService() {

    }

    /**
     * Authenticate user
     * @param login
     * @param password
     * @throws ServiceException
     */
    public User authenticate(String login, String password) throws ServiceException {
        User user;
        if (login != null && password != null){
            String hash = DigestUtils.md5Hex(password);
            try {
                user = userDao.findByLoginAndPassword(login, hash);
				return user;
            } catch (DaoException e) {
                LOGGER.info(EXC_SERVICE_ERROR_AUTHENTIFICATION, e);
                throw new ServiceException(EXC_SERVICE_ERROR_AUTHENTIFICATION, e);
            }
        }
        return null;
    }

    /**
     * check if user is logged in to the system
     * @param request
     * @return
     */
    public static boolean isLoggedIn(HttpServletRequest request){
        HttpSession session = request.getSession(true);
        return  (session.getAttribute(PARAMETER_SESSION_USER) != null);
    }

    /**
     * perform logout
     * @param session
     */
    public static void logout(HttpSession session){
        session.invalidate();
    }

    /**
     *
     * @param request
     * @return
     */
    public static User user(HttpServletRequest request){
        HttpSession session = request.getSession();
        Object ob = session.getAttribute(PARAMETER_SESSION_USER);
        return (ob != null && ob.getClass().equals(User.class)) ? (User) ob : null;
    }
}
