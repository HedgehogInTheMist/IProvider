package com.epam.inet.provider.service;

import com.epam.inet.provider.dao.exception.DaoException;
import com.epam.inet.provider.entity.Order;
import com.epam.inet.provider.entity.Tariff;
import com.epam.inet.provider.entity.User;
import com.epam.inet.provider.service.exception.ServiceException;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.util.Date;
import java.util.List;

import static com.epam.inet.provider.util.Constants.*;

/**
 * Performs order service function
 */
public class OrderService extends Service {

    private static final Logger LOGGER = LogManager.getLogger(OrderService.class);

    public OrderService() {
    }

    public boolean clientOrders(User user, Tariff tariff, double amount) throws ServiceException {
        if (user != null && tariff != null) {
            Order order = new Order();
            order.setUser(user);
            order.setTariff(tariff);
            order.setAmount(amount);
            order.setDateTime(new Date());
            try {
                return orderDao.create(order);
            } catch (DaoException e) {
                LOGGER.info(LOGGER_SERVICE_ORDER_ERROR);
                throw new ServiceException(SERVICE_ERROR_ORDERS);
            }
        } else {
            LOGGER.info(LOG_MSG_SERVICE_ERROR);
            return false;
        }
    }

    public List<Order> fetchAllOrdersForUser(User user) throws ServiceException {
        try {
            return orderDao.findOrdersForUser(user);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public List<Order> fetchAllOrders() throws ServiceException {
        try {
            return orderDao.findAll();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public void updateOrderAsPaid(Integer id, boolean paid) throws ServiceException {
        try {
            orderDao.updatePaid(id, paid);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }
}
