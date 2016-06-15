package com.epam.inet.provider.command;

import com.epam.inet.provider.command.exception.CommandException;
import com.epam.inet.provider.dao.OrderDao;
import com.epam.inet.provider.dao.TariffDao;
import com.epam.inet.provider.dao.exception.DaoException;
import com.epam.inet.provider.entity.Order;
import com.epam.inet.provider.entity.Role;
import com.epam.inet.provider.entity.Tariff;
import com.epam.inet.provider.entity.User;
import com.epam.inet.provider.resource.LocaleManager;
import com.epam.inet.provider.resource.MessageManager;
import com.epam.inet.provider.resource.MsgManager;
import com.epam.inet.provider.service.AuthenticationService;
import com.epam.inet.provider.service.OrderService;
import com.epam.inet.provider.service.TariffService;
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

        if ((login != null && !login.isEmpty()) && (password != null && !password.isEmpty())) { //works
            try {
                User user = AuthenticationService.authenticate(login, password);
                HttpSession session = request.getSession();
                session.setAttribute(AuthenticationService.PARAMETER_SESSION_USER, user);
                LOGGER.info(LOG_MSG_SUCCESS_AUTH + login);
                session.setAttribute(ATTRIBUT_SUCCESS_AUTH, MessageManager.INSTANCE.getMessage("info.auth.success", locale));
                if (user.getRole().getRolename().equals(Role.ROLE_ADMIN)) {
                    TariffService tariffService = TariffService.getInstance();
                    List<Tariff> tariffs = tariffService.fetchAllTariffPlans();
                    session.setAttribute(ATTRIBUTE_TARIFFS, tariffs);
                    return pathManager.getString(PATH_MANAGER_PAGE);
                } else if (user.getRole().getRolename().equals(Role.ROLE_CLIENT)) {
                    OrderService orderService = OrderService.getInstance();
                    List<Order> orders = orderService.fetchAllOrdersForUser(user);
                    session.setAttribute(ATTRIBUTE_ORDERS, orders);
                    destinationPage = pathManager.getString(PATH_CLIENT_ACCOUNT_PAGE);
                    return destinationPage;
                }
            } catch (ServiceException e) {
                e.printStackTrace();
            }
        }
        request.setAttribute(PARAMETER_LAST_ENTERED_LOGIN, login);
        request.setAttribute(PARAMETER_ERROR_LOGIN, MsgManager.getProperty(MSG_ERROR_LOGIN));
//        logger.debug(MessageManager.getValue(LOGGER_MSG_INCORRECT_LOGIN));
        System.out.println("HEre инициализация пустых полей ");
        destinationPage = pathManager.getString(PATH_LOGIN_PAGE);
        return destinationPage;
    }
}
