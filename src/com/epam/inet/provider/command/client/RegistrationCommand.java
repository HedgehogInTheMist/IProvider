package com.epam.inet.provider.command.client;

import com.epam.inet.provider.command.ActionCommand;
import com.epam.inet.provider.dao.UserDao;
import com.epam.inet.provider.entity.User;
import com.epam.inet.provider.command.exception.CommandException;
import com.epam.inet.provider.dao.exception.DaoException;
import com.epam.inet.provider.resource.MsgManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static com.epam.inet.provider.util.Constants.*;

/**
 * Created by Hedgehog on 22.04.2016.
 */
public class RegistrationCommand extends ActionCommand {

    private static final Logger LOGGER = Logger.getRootLogger();
    User user = null;
    UserDao userDao = null;

    /**
     * Everyone allowed to register in the system.
     *
     * @param user can be null
     * @return
     */
    @Override
    public boolean checkAccess(User user) {
        return true;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        String page = null;
        user = new User();
        userDao = UserDao.getInstance();
//        userDao = new UserDao();
        HttpSession session = request.getSession();
        if (buildNewUser(request)) {
            try {
                if (userDao.findUserByLogin(user.getUsername()) == null) {
                    userDao.create(user);
                    session.setAttribute(PARAMETER_USER_CREATED, MsgManager.getProperty(MSG_USER_CREATED));
                    LOGGER.info(MsgManager.getProperty(LOGGER_MSG_COMMAND_USER_REGISTERED) + user.getUsername());
                    page = pathManager.getString(PATH_LOGIN_PAGE);
                } else {
                    page = pathManager.getString(PATH_PAGE_REGISTRATION);
                    session.setAttribute(PARAMETER_USER_EXIST, MsgManager.getProperty(MSG_USER_EXIST));
                    LOGGER.info(MsgManager.getProperty(MSG_USER_EXIST));
                }
            } catch (DaoException e) {
                LOGGER.error(LOGGER_MSG_REGISTRATION_COMMAND_ERROR);
                page = pathManager.getString(PATH_ERROR_PAGE);
                return page;
            }
        } else {
            page = pathManager.getString(PATH_PAGE_REGISTRATION);
            session.setAttribute(PARAMETER_REGISTRATION_LOGIN, request.getParameter(PARAMETER_REGISTRATION_LOGIN));
            session.setAttribute(PARAMETER_CONFIRM_PASSWORD, request.getParameter(PARAMETER_REGISTRATION_PASSWORD));
        }
        return page;
    }

    /**
     * This method checks if user building finished successful
     *
     * @param request
     * @return true if user building finished successful, else return false
     */
    public boolean buildNewUser(HttpServletRequest request) {
        boolean isBuilt = true;
        isBuilt &= buildLogin(request);
        isBuilt &= buildPassword(request);
        return isBuilt;
    }

    /**
     * This method builds user's login
     *
     * @param request
     * @return true if login is built successful, else return false
     */
    private boolean buildLogin(HttpServletRequest request) {
        boolean isBuilt = false;
        String login = request.getParameter(PARAMETER_REGISTRATION_LOGIN);
        if (login.matches(MsgManager.getProperty(PATTERN_LOGIN))) {
            user.setUsername(login);
            isBuilt = true;
        } else {
            request.setAttribute(PARAMETER_INCORRECT_LOGIN, MsgManager.getProperty(MSG_INCORRECT_LOGIN));
            LOGGER.debug(MsgManager.getProperty(MSG_INCORRECT_LOGIN));
        }
        return isBuilt;
    }

    /**
     * This method builds user's password
     *
     * @param request
     * @return true if password is built successful, else return false
     */
    private boolean buildPassword(HttpServletRequest request) {
        boolean isBuilt = false;
        String password = request.getParameter(PARAMETER_REGISTRATION_PASSWORD);
        String confirmPassword = request.getParameter(PARAMETER_CONFIRM_PASSWORD);
        if (password != null && password.matches(MsgManager.getProperty(PATTERN_PASSWORD))) {
            if (password.equals(confirmPassword)) {
                user.setPassword(password);
                isBuilt = true;
            } else {
                request.setAttribute(PARAMETER_NONCONFIRMED_PASSWORD, MsgManager.getProperty(MSG_NONCONFIRMED_PASSWORD));
                LOGGER.debug(MsgManager.getProperty(MSG_NONCONFIRMED_PASSWORD));
            }
        } else {
            request.setAttribute(PARAMETER_INCORRECT_PASSWORD, MsgManager.getProperty(MSG_INCORRECT_PASSWORD));
            LOGGER.debug(MsgManager.getProperty(MSG_INCORRECT_PASSWORD));
        }
        return isBuilt;
    }
}

