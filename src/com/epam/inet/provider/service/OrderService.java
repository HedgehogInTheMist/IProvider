package com.epam.inet.provider.service;

import com.epam.inet.provider.dao.OrderDao;
import com.epam.inet.provider.entity.Tariff;
import com.epam.inet.provider.entity.User;
import com.epam.inet.provider.dao.exception.DaoException;
import com.epam.inet.provider.entity.Order;
import com.epam.inet.provider.resource.MsgManager;
import com.epam.inet.provider.service.exception.ServiceException;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.util.Date;
import java.util.List;

import static com.epam.inet.provider.util.Constants.*;

/**
 * Performs order service function
 */
public class OrderService {

    private static final Logger LOGGER = LogManager.getLogger(OrderService.class);

    private OrderService(){}

    private static OrderService instance;

    public synchronized static OrderService getInstance(){
        if (instance == null){
            instance = new OrderService();
        }
        LOGGER.info(MsgManager.getProperty(LOG_MSG_ORDER_SERVICE_INIT));
        return instance;
    }

    public static boolean clientOrders(User user, Tariff tariff, double amount) throws ServiceException {

        if (user != null && tariff != null){
            Order order = new Order();
            order.setUser(user);
            order.setTariff(tariff);

            order.setAmount(amount);
            order.setDateTime(new Date());

            OrderDao dao = OrderDao.getInstance();
            try{
                return dao.create(order);
            } catch (DaoException e) {
                LOGGER.info(LOGGER_SERVICE_ORDER_ERROR);
                throw new ServiceException(SERVICE_ERROR_ORDERS);
            }
        } else{
            LOGGER.info(LOG_MSG_SERVICE_ERROR);
            return false;
        }
    }


    public List<Order> fetchAllOrdersForUser(User user) throws ServiceException {
        try {
            return OrderDao.getInstance().findOrdersForUser(user);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }
}
