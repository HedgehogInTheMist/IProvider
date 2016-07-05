package com.epam.inet.provider.command;

import com.epam.inet.provider.command.admin.*;
import com.epam.inet.provider.command.client.AccountCommand;
import com.epam.inet.provider.command.client.NewUserCommand;
import com.epam.inet.provider.command.client.OrderCommand;
import com.epam.inet.provider.command.client.RegistrationCommand;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Hedgehog on 05.07.2016.
 */
public class CommandFactory {

    public static final String COMMAND_PARAMETER = "action";

    private static volatile CommandFactory commandFactory = new CommandFactory();
    private Map<String, ActionCommand> commands;

    /**
     * Initialises map of commands
     */
    private CommandFactory() {
        commands = new HashMap<String, ActionCommand>(11);
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

    public static CommandFactory getCommandFactory() {
        if (commandFactory == null) {
            synchronized (CommandFactory.class) {
                if (commandFactory == null) {
                    commandFactory = new CommandFactory();
                }
            }
        }
        return commandFactory;
    }

    /**
     * Extracts command out of request and fetches proper action out of map for further actions.
     *
     * @param request
     * @return action
     */
    public ActionCommand getCommand(HttpServletRequest request) {
        String action = request.getParameter(COMMAND_PARAMETER);
        ActionCommand command = commands.get(action);
        if(command == null) {
            command = new EmptyCommand();
        }
        return command;
    }



  /*  public enum ActionType {
        LOGIN, LOGOUT, VIEW_TARIFFS,
        ACCOUNT, ORDER, MANAGER,
        ADD_TARIFF, DELETE_TARIFF, UPDATE_TARIFF,
        ORDERS, NEW_USER, REGISTRATION
    }*/
}
