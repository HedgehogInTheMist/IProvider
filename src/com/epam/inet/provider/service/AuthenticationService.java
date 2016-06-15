package com.epam.inet.provider.service;

import com.epam.inet.provider.dao.UserDao;
import com.epam.inet.provider.entity.User;
import com.epam.inet.provider.dao.exception.DaoException;
import com.epam.inet.provider.resource.MsgManager;
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
public class AuthenticationService {

    public static final Logger LOGGER = LogManager.getLogger(AuthenticationService.class);

    public static final String PARAMETER_SESSION_USER = "user";

    private AuthenticationService(){}

    private static AuthenticationService instance;

    public synchronized static AuthenticationService getInstance(){
        if (instance == null){
            instance = new AuthenticationService();
        }
        LOGGER.info(MsgManager.getProperty(LOG_MSG_AUTH_SERVICE_INIT));
        return instance;
    }


    /**
     * Authenticate user
     * @param login
     * @param password
     * @throws ServiceException
     */
    public static User authenticate(String login, String password) throws ServiceException {
        if (login != null && password != null){
            String hash = DigestUtils.md5Hex(password);
            UserDao dao = UserDao.getInstance();
            try {
                User user = dao.findByLoginAndPassword(login, hash);
				return user;
            } catch (DaoException e) {
                LOGGER.info(ERROR_SEREVICE_AUTHENTIFICATION, e);
                throw new ServiceException(ERROR_SEREVICE_AUTHENTIFICATION, e);
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
