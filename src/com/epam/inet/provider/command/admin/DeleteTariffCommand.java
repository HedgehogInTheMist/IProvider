package com.epam.inet.provider.command.admin;

import com.epam.inet.provider.command.AdminCommand;
import com.epam.inet.provider.command.exception.CommandException;
import com.epam.inet.provider.entity.Tariff;
import com.epam.inet.provider.service.exception.ServiceException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

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
        Tariff tariff;
        if (paramId != null){
            try{
                int id = Integer.parseInt(paramId);
                tariff = tariffService.fetchTariffPlanById(id);
                if (tariffService.deleteTariffPlan(id)){
                    List<Tariff> tariffs = tariffService.fetchAllTariffPlans();
                    session.setAttribute(ATTRIBUTE_TARIFFS, tariffs);
                    response.sendRedirect(PATH_TO_REMOVAL_DONE);
                    session.setAttribute(ATTRIBUTE_TARIFF, tariff);
                    return null;
                }
                return pathManager.getString(PATH_ADMIN_MANAGER_PAGE);
            } catch (ServiceException e) {
                throw new CommandException(e);
            } catch (IOException e) {
                throw new CommandException(MESSAGE_INVALID_REDIRECT_PAGE);
            }
        }
        return pathManager.getString(PATH_ADMIN_MANAGER_PAGE);
    }
}
