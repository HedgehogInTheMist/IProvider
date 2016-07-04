package com.epam.inet.provider.service;

import com.epam.inet.provider.dao.exception.DaoException;
import com.epam.inet.provider.entity.Tariff;
import com.epam.inet.provider.service.exception.ServiceException;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.util.List;

import static com.epam.inet.provider.util.Constants.EXC_MSG_WRONG_DAO_DATA;
import static com.epam.inet.provider.util.Constants.LOG_MSG_TARIFF_SERVICE;

/**
 * Created by Hedgehog on 04.06.2016.
 */
public class TariffService extends Service {

    public static final Logger LOGGER = LogManager.getLogger(TariffService.class);

    public TariffService() {
    }

    public Tariff fetchTariffPlanById(Integer id) throws ServiceException {
        try {
            return tariffDao.findById(id);
        } catch (DaoException e) {
//            LOGGER.info(MsgManager.getProperty(LOG_MSG_TARIFF_SERVICE), e);
            LOGGER.info(LOG_MSG_TARIFF_SERVICE, e);
            throw new ServiceException(EXC_MSG_WRONG_DAO_DATA, e);
        }
    }

    public List<Tariff> fetchAllTariffPlans() throws ServiceException {
        try {
            return tariffDao.findAll();
        } catch (DaoException e) {
            LOGGER.info(LOG_MSG_TARIFF_SERVICE, e);
            throw new ServiceException(EXC_MSG_WRONG_DAO_DATA, e);
        }
    }

    public boolean updateTariffPlan(Tariff entity) throws ServiceException {
        try {
                if(tariffDao.update(entity)) {
                    return true;
                }
        } catch (DaoException e) {
//            LOGGER.info(MsgManager.getProperty(LOG_MSG_TARIFF_SERVICE), e);
            LOGGER.info(LOG_MSG_TARIFF_SERVICE, e);
//            throw new ServiceException(MsgManager.getProperty(EXC_MSG_WRONG_DAO_DATA), e);
            throw new ServiceException(EXC_MSG_WRONG_DAO_DATA, e);
        }
        throw new ServiceException(EXC_MSG_WRONG_DAO_DATA);
    }

    public boolean deleteTariffPlan(Integer id) throws ServiceException {
        try {
            return tariffDao.delete(id);
        } catch (DaoException e) {
            LOGGER.info(LOG_MSG_TARIFF_SERVICE, e);
            throw new ServiceException(EXC_MSG_WRONG_DAO_DATA, e);
        }
    }


}
