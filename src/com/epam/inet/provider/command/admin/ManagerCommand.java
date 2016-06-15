package com.epam.inet.provider.command.admin;

import com.epam.inet.provider.command.AdminCommand;
import com.epam.inet.provider.dao.TariffDao;
import com.epam.inet.provider.entity.Tariff;
import com.epam.inet.provider.command.exception.CommandException;
import com.epam.inet.provider.dao.exception.DaoException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

import static com.epam.inet.provider.util.Constants.*;

/**
 * Command for managing tariffs
 * Created by Hedgehog on 21.05.2016.
 */
public class ManagerCommand extends AdminCommand {

    /**
     * Displays a list of tariffs with the ability to
     * delete, update or create a new one
     *
     * @param request request to read the command from
     * @param response
     * @return
     * @throws CommandException
     */
    @Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        TariffDao dao = TariffDao.getInstance();
        try {
            List<Tariff> tariffs = dao.findAll();
            request.setAttribute(ATTRIBUTE_TARIFFS, tariffs);
        } catch (DaoException e) {
            throw new CommandException(e);
        }
		return pathManager.getString(PATH_ADMIN_MANAGER_PAGE);
	}
}
