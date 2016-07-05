package com.epam.inet.provider.command.client;

import com.epam.inet.provider.command.ClientCommand;
import com.epam.inet.provider.command.exception.CommandException;
import com.epam.inet.provider.entity.Order;
import com.epam.inet.provider.entity.User;
import com.epam.inet.provider.service.AuthenticationService;
import com.epam.inet.provider.service.exception.ServiceException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

import static com.epam.inet.provider.util.Constants.ATTRIBUTE_ORDERS;
import static com.epam.inet.provider.util.Constants.EXC_MSG_WRONG_DAO_DATA;
import static com.epam.inet.provider.util.Constants.PATH_CLIENT_ACCOUNT_PAGE;

/**
 * Performes view list of orders made by client
 * Created by Hedgehog on 21.05.2016
 */
public class AccountCommand extends ClientCommand {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        User user = AuthenticationService.user(request);
        List<Order> orders;
        try {
            orders = orderService.fetchAllOrdersForUser(user);
            request.setAttribute(ATTRIBUTE_ORDERS, orders);
        } catch (ServiceException e) {
            throw new CommandException(EXC_MSG_WRONG_DAO_DATA + " " + e);
        }
        return pathManager.getString(PATH_CLIENT_ACCOUNT_PAGE);
    }
}
