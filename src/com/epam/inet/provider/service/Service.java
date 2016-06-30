package com.epam.inet.provider.service;

import com.epam.inet.provider.dao.DaoFactory;
import com.epam.inet.provider.dao.OrderDao;
import com.epam.inet.provider.dao.TariffDao;
import com.epam.inet.provider.dao.UserDao;

/**
 * Created by Hedgehog on 17.05.2016.
 */
public abstract class Service {

    protected DaoFactory daoFactory = DaoFactory.getDaoFactory();

    protected TariffDao tariffDao = (TariffDao) daoFactory.getDao(DaoFactory.DaoType.TARIFF_DAO);
    protected UserDao userDao = (UserDao) daoFactory.getDao(DaoFactory.DaoType.USER_DAO);
    protected OrderDao orderDao = (OrderDao) daoFactory.getDao(DaoFactory.DaoType.ORDER_DAO);
}
