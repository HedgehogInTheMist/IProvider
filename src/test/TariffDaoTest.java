package test;

import com.epam.inet.provider.dao.DaoFactory;
import com.epam.inet.provider.dao.TariffDao;
import com.epam.inet.provider.dao.exception.DaoException;
import com.epam.inet.provider.entity.Tariff;
import com.epam.inet.provider.entity.TariffType;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by Hedgehog on 17.06.2016.
 */
public class TariffDaoTest {

    private DaoFactory daoFactory = DaoFactory.getDaoFactory();
    private TariffDao tariffDao = (TariffDao) daoFactory.getDao(DaoFactory.DaoType.TARIFF_DAO);
    private String tariffName;
    private Tariff actualTariff;
    private Tariff newTariff;

    @Before
    public void init() throws DaoException {
        newTariff = new Tariff();
        newTariff.setTariffname("Тестовый тарифный план");
        newTariff.setDetails("Описание условий нового тарифного плана для теста");
        newTariff.setHot(true);
        newTariff.setPrice(1200);
        newTariff.setRegularDiscount(15);
        newTariff.setType(TariffType.ETHERNETINET);
        tariffDao.create(newTariff);
        tariffName = newTariff.getTariffname();
    }

    @Test
    public void testAddTariff() throws DaoException {
        actualTariff = tariffDao.fetchTariffByName("Тестовый тарифный план");
        assertEquals("Seems tariff's name must be equals", actualTariff.getTariffname(), newTariff.getTariffname());
        assertEquals("Seems tariff's details must be equals", actualTariff.getDetails(), newTariff.getDetails());
        assertEquals("Seems tariff's prices must be equals", actualTariff.getPrice(), newTariff.getPrice());
        assertEquals("Seems tariff's discounts must be equals", actualTariff.getRegularDiscount(), newTariff.getRegularDiscount());
    }

    @Test
    public void testDeleteTariffByName() throws DaoException {
        assertTrue(tariffDao.deleteByName(tariffName));
        tariffDao.create(newTariff);
    }

    /*@Test
    public void updateTariff() throws DaoException {
        newTariff.setTariffname("Новое наименование тестового тарифа");
        *//*newTariff.setDetails("Новое обновленное описание нового тарифного плана");
        newTariff.setHot(true);
        newTariff.setPrice(1700);
        newTariff.setRegularDiscount(0);
        newTariff.setType(TariffType.CELLPHONE);*//*
        System.out.println(newTariff.getTariffname());
        System.out.println(newTariff.getType());
//        tariffDao.create(newTariff);
//        newTariff.setType(TariffType.CELLPHONE);
        tariffDao.update(newTariff);
//        actualTariff = tariffDao.fetchTariffByName("Новое наименование тестового тарифа");
//        assertEquals("Seems tariff's name must be equals", actualTariff.getTariffname(), newTariff.getTariffname());
    }*/

    @After
    public void deleteNewTariff() throws DaoException {
        tariffName = newTariff.getTariffname();
        tariffDao.deleteByName(tariffName);
    }


}
