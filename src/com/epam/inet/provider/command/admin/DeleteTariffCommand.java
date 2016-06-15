package com.epam.inet.provider.command.admin;

import com.epam.inet.provider.dao.exception.DaoException;
import com.epam.inet.provider.resource.LocaleManager;
import com.epam.inet.provider.command.AdminCommand;
import com.epam.inet.provider.dao.TariffDao;
import com.epam.inet.provider.entity.Tariff;
import com.epam.inet.provider.command.exception.CommandException;
import com.epam.inet.provider.resource.MessageManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Locale;

import static com.epam.inet.provider.util.Constants.*;

/**
 * Delete tariff command
 * Created by Hedgehog on 21.05.2016.
 */
public class DeleteTariffCommand extends AdminCommand {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {

        String paramId = request.getParameter(ID);
        HttpSession session = request.getSession();

        if (paramId != null){
            Locale locale = LocaleManager.INSTANCE.resolveLocale(request);
            try{
                int id = Integer.parseInt(paramId);
                TariffDao dao = TariffDao.getInstance();
                if (dao.delete(id)){
                    List<Tariff> tariffs = dao.findAll();
                    session.setAttribute(ATTRIBUTE_TARIFFS, tariffs);
                    session.setAttribute(PARAMETER_NOTIFICATION, MessageManager.INSTANCE.getMessage(MESSAGE_DB_DELETE_SUCCESS, locale));
                    return pathManager.getString(PATH_ADMIN_MANAGER_PAGE);
                }
            } catch (NumberFormatException e){
                throw new CommandException(e);
            } catch (DaoException e) {
                throw new CommandException(e);
            }
        }
        return pathManager.getString(PATH_ADMIN_MANAGER_PAGE);
    }
}
