package com.epam.inet.provider.command.client;

import com.epam.inet.provider.dao.DaoFactory;
import com.epam.inet.provider.service.AuthenticationService;
import com.epam.inet.provider.command.ClientCommand;
import com.epam.inet.provider.dao.OrderDao;
import com.epam.inet.provider.entity.Order;
import com.epam.inet.provider.entity.User;
import com.epam.inet.provider.command.exception.CommandException;
import com.epam.inet.provider.dao.exception.DaoException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

import static com.epam.inet.provider.util.Constants.*;

/**
 * Performes view list of orders made by client
 * Created by Hedgehog on 21.05.2016
 */
public class AccountCommand extends ClientCommand {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        User user = AuthenticationService.user(request);

        try {
            DaoFactory daoFactory = DaoFactory.getDaoFactory();
            OrderDao orderDao = (OrderDao) daoFactory.getDao(DaoFactory.DaoType.ORDER_DAO);
            List<Order> orders = orderDao.findOrdersForUser(user);

//            List<Order> orders = OrderDao.getInstance().findOrdersForUser(user);
            request.setAttribute(ATTRIBUTE_ORDERS, orders);
        } catch (DaoException e) {
            throw new CommandException(e);
        }
        return pathManager.getString(PATH_CLIENT_ACCOUNT_PAGE);
    }
}
