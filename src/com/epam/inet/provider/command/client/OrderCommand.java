package com.epam.inet.provider.command.client;

import com.epam.inet.provider.command.ClientCommand;
import com.epam.inet.provider.entity.Tariff;
import com.epam.inet.provider.entity.User;
import com.epam.inet.provider.command.exception.CommandException;
import com.epam.inet.provider.dao.exception.DaoException;
import com.epam.inet.provider.resource.PathManager;
import com.epam.inet.provider.service.AuthenticationService;
import com.epam.inet.provider.service.ClientService;
import com.epam.inet.provider.service.OrderService;
import com.epam.inet.provider.resource.LocaleManager;
import com.epam.inet.provider.service.TariffService;
import com.epam.inet.provider.service.exception.ServiceException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Locale;

import static com.epam.inet.provider.util.Constants.*;

/**
 * Performs a tariff order by the client
 * Created by Hedgehog on 22.05.2016.
 */
public class OrderCommand extends ClientCommand {
    public static final String PARAMETER_VALUE_CONFIRM_ORDER = "yes";
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        User user = AuthenticationService.user(request);
        int id;
        Locale locale = LocaleManager.INSTANCE.resolveLocale(request);
        HttpSession session = request.getSession();
        try {
            id = Integer.parseInt(request.getParameter(ID));
        } catch (NumberFormatException e){
            return pathManager.getString(PATH_MANAGER_PAGE);
        }
        try{
//            TariffService tariffService = TariffService.getInstance();
            Tariff tariff = tariffService.fetchTariffPlanById(id);
            boolean regular = clientService.isRegularClient(user);
            double amount = regular ? (float) (tariff.getPrice() - (tariff.getPrice() * tariff.getRegularDiscount() * 0.01)) : tariff.getPrice();
            session.setAttribute(ATTRIBUTE_AMOUNT, amount);
            session.setAttribute(ATTRIBUTE_TARIFF, tariff);

            if (PARAMETER_VALUE_CONFIRM_ORDER.equals(request.getParameter(ATTRIBUTE_CONFIRM))){
                boolean result = orderService.clientOrders(user, tariff, amount);
                if (result){
//                    response.sendRedirect(PathManager.INSTANCE.getString(PATH_CLIENT_COMPLETE));
                    response.sendRedirect("/OrderComplete");
                    return null;
                }
            }
        } catch (ServiceException e) {
            throw new CommandException(e);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return pathManager.getString(PATH_CLIENT_CLIENT_ORDER);
    }
}
