package com.epam.inet.provider.command.admin;

import com.epam.inet.provider.command.util.TariffBuilder;
import com.epam.inet.provider.command.AdminCommand;
import com.epam.inet.provider.dao.TariffDao;
import com.epam.inet.provider.entity.Tariff;
import com.epam.inet.provider.entity.TariffType;
import com.epam.inet.provider.command.exception.BuildException;
import com.epam.inet.provider.command.exception.CommandException;
import com.epam.inet.provider.dao.exception.DaoException;
import com.epam.inet.provider.resource.LocaleManager;
import com.epam.inet.provider.resource.MsgManager;
import com.epam.inet.provider.service.TariffService;
import com.epam.inet.provider.service.exception.ServiceException;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Locale;

import static com.epam.inet.provider.util.Constants.*;

/**
 * Command Update Tariff performs update information fields about tariff plans
 * Created by Hedgehog on 22.05.2016
 */
public class UpdateTariffCommand extends AdminCommand {

    private static final Logger LOGGER = LogManager.getLogger(UpdateTariffCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        request.setAttribute(ATTRIBUTE_TARIFF_TYPE, TariffType.values());
        Locale locale = LocaleManager.INSTANCE.resolveLocale(request);
        TariffDao dao = TariffDao.getInstance();
        Tariff tariff = new Tariff();
        int id;
        try {
            id = Integer.parseInt(request.getParameter(ID));
        } catch (NumberFormatException e) {
            return pathManager.getString(PATH_ADMIN_MANAGER_PAGE);
        }
        if (request.getParameter(PARAMETER_SUBMIT) != null) {
            tariff = new Tariff();
            tariff.setId(id);
            TariffBuilder tariffBuilder = new TariffBuilder();
            try {
                tariffBuilder.build(request.getParameterMap(), tariff);
                if (TariffService.getInstance().updateTariffPlan(tariff)) {
                    List<Tariff> tariffs = TariffService.getInstance().fetchAllTariffPlans();
                    request.setAttribute(PARAMETER_TARIFF, tariffs);
                    return pathManager.getString(PATH_ADMIN_MANAGER_PAGE);
                }
            } catch (BuildException e) {
                LOGGER.info(MsgManager.getProperty(MESSAGE_INVALID_UPDATE_DATA));
                throw new CommandException(MsgManager.getProperty(MESSAGE_INVALID_UPDATE_DATA), e);
            } catch (ServiceException e) {
                throw new CommandException(e);
            }
        } else {
            try {
                tariff = dao.findById(id);
            } catch (DaoException e) {
//                return pathManager.getString(PATH_ADMIN_MANAGER_PAGE);
                throw new CommandException(e);
            }
        }
        request.setAttribute(ATTRIBUTE_TARIFF, tariff);
        return pathManager.getString(PATH_ADMIN_UPDATE_TARIFF);
    }
}
