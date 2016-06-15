package com.epam.inet.provider.command.admin;

import com.epam.inet.provider.command.util.TariffBuilder;
import com.epam.inet.provider.command.AdminCommand;
import com.epam.inet.provider.dao.TariffDao;
import com.epam.inet.provider.entity.Tariff;
import com.epam.inet.provider.entity.TariffType;
import com.epam.inet.provider.command.exception.BuildException;
import com.epam.inet.provider.command.exception.CommandException;
import com.epam.inet.provider.dao.exception.DaoException;
import com.epam.inet.provider.resource.MsgManager;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

import static com.epam.inet.provider.util.Constants.*;

/**
 * Add tariff command
 * Created by Hedgehog on 21.05.2016
 */
public class AddTariffCommand extends AdminCommand {

    private static final Logger LOGGER = LogManager.getLogger(AddTariffCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {

        HttpSession session = request.getSession();
        session.setAttribute(ATTRIBUTE_TARIFF_TYPE, TariffType.values());
        Tariff tariff = new Tariff();

        if (request.getParameter(PARAMETER_SUBMIT) != null) {
            TariffBuilder tariffBuilder = new TariffBuilder();
            try {
                tariffBuilder.build(request.getParameterMap(), tariff);
                TariffDao dao = TariffDao.getInstance();
                if (dao.create(tariff)) {
                    LOGGER.info(MsgManager.getProperty(MESSAGE_DB_CREATE_SUCCESS));
                    List<Tariff> tariffs = dao.findAll();
                    request.setAttribute(ATTRIBUTE_TARIFFS, tariffs);
                    return pathManager.getString(PATH_MANAGER_PAGE);
                }
            } catch (BuildException e) {
                LOGGER.info(MsgManager.getProperty(MESSAGE_INVALID_DATA));
            } catch (DaoException e) {
                throw new CommandException(MsgManager.getProperty(MESSAGE_INVALID_EXCEPTION), e);
            }
        }
        request.setAttribute(ATTRIBUTE_TARIFF, tariff);
        return pathManager.getString(PATH_MANAGER_ADD_TARIFF);
    }
}
