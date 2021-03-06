package com.epam.inet.provider.command.admin;

import com.epam.inet.provider.command.AdminCommand;
import com.epam.inet.provider.command.exception.BuildException;
import com.epam.inet.provider.command.exception.CommandException;
import com.epam.inet.provider.command.util.TariffBuilder;
import com.epam.inet.provider.entity.Tariff;
import com.epam.inet.provider.entity.TariffType;
import com.epam.inet.provider.resource.MessageManager;
import com.epam.inet.provider.service.exception.ServiceException;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static com.epam.inet.provider.util.Constants.*;

/**
 * Add tariff command
 * Created by Hedgehog on 21.05.2016
 */
public class AddTariffCommand extends AdminCommand {

    private static final Logger LOGGER = LogManager.getLogger(AddTariffCommand.class);

    /**
     *
     * @param request request to read the command from
     * @param response
     * @return
     * @throws CommandException
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {

        HttpSession session = request.getSession();
        session.setAttribute(ATTRIBUTE_TARIFF_TYPE, TariffType.values());
        Tariff tariff = new Tariff();

        if (request.getParameter(PARAMETER_SUBMIT) != null) {
            TariffBuilder tariffBuilder = new TariffBuilder();
            try {
                tariffBuilder.build(request.getParameterMap(), tariff);
                if (tariffService.createNewTariff(tariff)) {
                    LOGGER.info(MessageManager.getProperty(MESSAGE_DB_CREATE_SUCCESS));
                    session.setAttribute(ATTRIBUTE_TARIFF, tariff);
                    response.sendRedirect(PATH_TO_SUPPLEMENT_DONE);
                    return null;
                }
            } catch (BuildException e) {
                LOGGER.info(MessageManager.getProperty(MESSAGE_INVALID_DATA));
            } catch (IOException e) {
                throw new CommandException(MESSAGE_INVALID_REDIRECT_PAGE, e);
            } catch (ServiceException e) {
                throw new CommandException(MessageManager.getProperty(MESSAGE_INVALID_EXCEPTION), e);
            }
        }
        request.setAttribute(ATTRIBUTE_TARIFF, tariff);
        return pathManager.getString(PATH_MANAGER_ADD_TARIFF);
    }
}
