package com.epam.inet.provider.command;

import com.epam.inet.provider.entity.User;
import com.epam.inet.provider.command.exception.CommandException;
import com.epam.inet.provider.service.AuthenticationService;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.epam.inet.provider.util.Constants.LOG_MSG_LOGGED_OUT;
import static com.epam.inet.provider.util.Constants.PATH_MAIN_PAGE;

/**
 * Logout command
 * Created by Hedgehog on 21.05.2016
 */
public class LogoutCommand extends ActionCommand {

    private static final Logger LOGGER = Logger.getRootLogger();

    /**
     * Everyone allowed to logout
     *
     * @param user can be null
     * @return
     */
    @Override
    public boolean checkAccess(User user) {
        return true;
    }

    /**
     * Performs logout command functions
     *
     * @param request  request to read the command from
     * @param response
     * @return
     * @throws CommandException
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        String destinationPage = null;
        User user = (User) request.getSession().getAttribute(AuthenticationService.PARAMETER_SESSION_USER);
        if (user != null) {
            AuthenticationService.logout(request.getSession());
            LOGGER.info(LOG_MSG_LOGGED_OUT + user.getUsername());
        }
        destinationPage = pathManager.getString(PATH_MAIN_PAGE);
        return destinationPage;
    }
}
