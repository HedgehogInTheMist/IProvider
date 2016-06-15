package com.epam.inet.provider.command.admin;

import com.epam.inet.provider.command.AdminCommand;
import com.epam.inet.provider.dao.OrderDao;
import com.epam.inet.provider.command.exception.CommandException;
import com.epam.inet.provider.entity.Order;
import com.epam.inet.provider.dao.exception.DaoException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

import static com.epam.inet.provider.util.Constants.*;

/**
 *  Order command gather all performed orders
 *  Created by Hedgehog on 21.05.2016.
 */
public class OrdersCommand extends AdminCommand {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        List<Order> orders = null;
        OrderDao dao = OrderDao.getInstance();
        if (request.getParameter(ID) != null && request.getParameter(PARAMETER_PAID) != null){
            try{
                int id = Integer.parseInt(request.getParameter(ID));
                boolean paid = Boolean.parseBoolean(request.getParameter(PARAMETER_PAID));
                dao.updatePaid(id, paid);
            } catch (DaoException e) {
                throw new CommandException(e);
            }
        }
        try {
            orders = dao.findAll();
            request.setAttribute(ATTRIBUTE_ORDERS, orders);
        } catch (DaoException e) {
            throw new CommandException(e);
        }
        return pathManager.getString(PATH_ADMIN_ORDERS_PAGE);
    }
}
