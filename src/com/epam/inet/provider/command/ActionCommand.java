package com.epam.inet.provider.command;

import com.epam.inet.provider.dao.DaoFactory;
import com.epam.inet.provider.dao.UserDao;
import com.epam.inet.provider.entity.User;
import com.epam.inet.provider.command.exception.CommandException;
import com.epam.inet.provider.resource.PathManager;
import com.epam.inet.provider.service.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Abstract command
 * Created by Hedgehog on 20.05.2016.
 */
public abstract class ActionCommand {

    ServiceFactory serviceFactory = ServiceFactory.getServiceFactory();

    protected AuthenticationService authenticationService = (AuthenticationService) serviceFactory.getService(ServiceFactory.ServiceType.AUTHENTICATION);
    public ClientService clientService = (ClientService) serviceFactory.getService(ServiceFactory.ServiceType.CLIENT);
    protected OrderService orderService = (OrderService) serviceFactory.getService(ServiceFactory.ServiceType.ORDER);
    protected TariffService tariffService = (TariffService) serviceFactory.getService(ServiceFactory.ServiceType.TARIFF);

    /**
     * Path manager
     */
    protected static final PathManager pathManager = PathManager.INSTANCE;

    /**
     * Check the access of user, return true if the userIn has access to
     * this command, otherwise return false
     * @param user can be null
     * @return
     */
    public abstract boolean checkAccess(User user);

    /**
     * Method reads a command from the request
     * and processes it. The result will be given as
     * a forward page
     *
     * @param request request to read the command from
     * @param response
     * @return forward page
     */
    public abstract String execute(HttpServletRequest request, HttpServletResponse response) throws CommandException;
}
