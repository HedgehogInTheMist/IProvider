package com.epam.inet.provider.command;

import com.epam.inet.provider.command.exception.CommandException;
import com.epam.inet.provider.entity.Order;
import com.epam.inet.provider.entity.Role;
import com.epam.inet.provider.entity.Tariff;
import com.epam.inet.provider.entity.User;
import com.epam.inet.provider.resource.LocaleManager;
import com.epam.inet.provider.resource.MsgManager;
import com.epam.inet.provider.service.AuthenticationService;
import com.epam.inet.provider.service.exception.ServiceException;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Locale;

import static com.epam.inet.provider.util.Constants.*;


/**
 * Login command
 */
public class LoginCommand extends ActionCommand {

    private final static Logger LOGGER = LogManager.getLogger(LoginCommand.class);

    /**
     * Everyone allowed login to the system.
     *
     * @param user can be null
     * @return
     */
    @Override
    public boolean checkAccess(User user) {
        return true;
    }


    /**
     * Execute login command
     *
     * @param request  request to read the command from
     * @param response
     * @return
     * @throws CommandException
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {

        String destinationPage = null;
        Locale locale = LocaleManager.INSTANCE.resolveLocale(request);
        final String login = request.getParameter(PARAMETER_LOGIN);
        final String password = request.getParameter(PARAMETER_PASSWORD);

        if ((login != null && !login.isEmpty()) && (password != null && !password.isEmpty())) {
            try {
                User user = authenticationService.authenticate(login, password);
                HttpSession session = request.getSession();
                session.setAttribute(AuthenticationService.PARAMETER_SESSION_USER, user);
                LOGGER.info(LOG_MSG_SUCCESS_AUTH + login);
                session.setAttribute(ATTRIBUT_SUCCESS_AUTH, MsgManager.getProperty("info.auth.success"));
                if (user.getRole().getRolename().equals(Role.ROLE_ADMIN)) {
                    List<Tariff> tariffs = tariffService.fetchAllTariffPlans();
                    session.setAttribute(ATTRIBUTE_TARIFFS, tariffs);
                    return pathManager.getString(PATH_MANAGER_PAGE);
                } else if (user.getRole().getRolename().equals(Role.ROLE_CLIENT)) {
                    List<Order> orders = orderService.fetchAllOrdersForUser(user);
                    session.setAttribute(ATTRIBUTE_ORDERS, orders);
                    destinationPage = pathManager.getString(PATH_CLIENT_ACCOUNT_PAGE);
                    return destinationPage;
                }
            } catch (ServiceException e) {
                LOGGER.info(ERROR_FIND_LOGIN_PASS);
            }
        }
        request.setAttribute(PARAMETER_LAST_ENTERED_LOGIN, login);
        request.setAttribute(PARAMETER_ERROR_LOGIN, MsgManager.getProperty(MSG_ERROR_LOGIN));
        LOGGER.debug(MsgManager.getProperty(LOGGER_MSG_INCORRECT_LOGIN));
        destinationPage = pathManager.getString(PATH_LOGIN_PAGE);
        return destinationPage;
    }
}
