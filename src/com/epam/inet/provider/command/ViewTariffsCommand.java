package com.epam.inet.provider.command;

import com.epam.inet.provider.dao.TariffDao;
import com.epam.inet.provider.entity.Tariff;
import com.epam.inet.provider.entity.User;
import com.epam.inet.provider.command.exception.CommandException;
import com.epam.inet.provider.dao.exception.DaoException;
import com.epam.inet.provider.service.AuthenticationService;
import com.epam.inet.provider.service.ClientService;
import com.epam.inet.provider.service.TariffService;
import com.epam.inet.provider.service.exception.ServiceException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

import static com.epam.inet.provider.util.Constants.*;

/**
 * Performs building table information about tariffs plans
 * Created by Hedgehog on 21.05.2016
 */
public class ViewTariffsCommand extends ActionCommand {
    @Override
    public boolean checkAccess(User user) {
        return true;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        String destinationPage = null;
//        TariffDao dao = TariffDao.getInstance();
//        TariffService tariffService = TariffService.getInstance(); ///
        try {
//            List<Tariff> tariffs = dao.findAll();
            List<Tariff> tariffs = tariffService.fetchAllTariffPlans();
            User client = AuthenticationService.user(request);
            boolean regular = clientService.isRegularClient(client);
            request.setAttribute(ATTRIBUTE_REGULAR, regular);
            request.setAttribute(ATTRIBUTE_TARIFFS, tariffs);
        } catch (ServiceException e) {
            throw new CommandException();
        }
        destinationPage = pathManager.getString(PATH_TARIFFS_PAGE);
        return destinationPage;
    }
}
