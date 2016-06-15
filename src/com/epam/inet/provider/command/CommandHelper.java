package com.epam.inet.provider.command;

import com.epam.inet.provider.command.admin.*;
import com.epam.inet.provider.command.client.AccountCommand;
import com.epam.inet.provider.command.client.OrderCommand;
import com.epam.inet.provider.command.client.RegistrationCommand;
import com.epam.inet.provider.command.client.NewUserCommand;
import org.apache.log4j.Logger;

import javax.servlet.ServletRequest;
import java.util.HashMap;

/**
 * Command center helps to deal with commands comming from pages.
 * Created by Hedgehog on 20.05.2016
 */
public enum CommandHelper {

    INSTANCE;

    private final Logger logger = Logger.getRootLogger();
    /**
     * Request parameter name for command
     */
    public static final String COMMAND_PARAMETER = "action";

    /**
     * action commands
     */
    private HashMap<String, ActionCommand> commands = new HashMap<String, ActionCommand>();
    {
        commands.put("login", new LoginCommand());
        commands.put("logout", new LogoutCommand());
		commands.put("tariffs", new ViewTariffsCommand());
        commands.put("account", new AccountCommand());
        commands.put("order", new OrderCommand());
		commands.put("manager", new ManagerCommand());
		commands.put("add_tariff", new AddTariffCommand());
        commands.put("delete_tariff", new DeleteTariffCommand());
        commands.put("update_tariff", new UpdateTariffCommand());
        commands.put("orders", new OrdersCommand());
        commands.put("newUser", new NewUserCommand());
        commands.put("registration", new RegistrationCommand());
    }

    /**
     * Find command from request
     * @param request
     * @return
     */
    public ActionCommand getCommand(ServletRequest request){
        String action = request.getParameter(COMMAND_PARAMETER);
        return getCommand(action);
    }

    /**
     *  Find command by name
     * @param action
     * @return
     */
    public ActionCommand getCommand(String action){
        ActionCommand command = commands.get(action);
        if (command == null){
            command = new EmptyCommand();
        }
        return command;
    }
}
