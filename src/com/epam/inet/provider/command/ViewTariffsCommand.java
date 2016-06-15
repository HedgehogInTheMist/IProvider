package com.epam.inet.provider.command;

import com.epam.inet.provider.dao.TariffDao;
import com.epam.inet.provider.entity.Tariff;
import com.epam.inet.provider.entity.User;
import com.epam.inet.provider.command.exception.CommandException;
import com.epam.inet.provider.dao.exception.DaoException;
import com.epam.inet.provider.service.AuthenticationService;
import com.epam.inet.provider.service.ClientService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

import static com.epam.inet.provider.util.Constants.*;

/**
 * Performs table information about tarrifs plans
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
        TariffDao dao = TariffDao.getInstance();
        try {
            List<Tariff> tariffs = dao.findAll();
            User client = AuthenticationService.user(request);
            boolean regular = ClientService.isRegularClient(client);
            request.setAttribute(ATTRIBUTE_REGULAR, regular);
            request.setAttribute(ATTRIBUTE_TARIFFS, tariffs);
        } catch (DaoException e) {
            throw new CommandException(e);
        }
        destinationPage = pathManager.getString(PATH_TARIFFS_PAGE);
        return destinationPage;
    }
}
