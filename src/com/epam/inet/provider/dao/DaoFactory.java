package com.epam.inet.provider.dao;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Hedgehog on 17.06.2016.
 */
public final class DaoFactory {

    private static volatile DaoFactory daoFactory = new DaoFactory();
    private Map<DaoType, AbstractDao> daoTypeMap;

    private DaoFactory() {
        daoTypeMap = new HashMap<>(2);
        daoTypeMap.put(DaoType.USER_DAO, new UserDao());
        daoTypeMap.put(DaoType.ORDER_DAO, new OrderDao());
        daoTypeMap.put(DaoType.TARIFF_DAO, new TariffDao());
    }

    public static DaoFactory getDaoFactory() {
        if(daoFactory == null) {
            synchronized (DaoFactory.class) {
                if (daoFactory == null) {
                    daoFactory = new DaoFactory();
                }
            }
        }
        return daoFactory;
    }

    public AbstractDao getDao(DaoType daoType) {
        return daoTypeMap.get(daoType);
    }

    public enum DaoType {
        USER_DAO,
        ORDER_DAO,
        TARIFF_DAO
    }
}
