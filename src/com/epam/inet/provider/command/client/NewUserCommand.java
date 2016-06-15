package com.epam.inet.provider.command.client;

import com.epam.inet.provider.command.ActionCommand;
import com.epam.inet.provider.entity.User;
import com.epam.inet.provider.command.exception.CommandException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Hedgehog on 23.05.2016.
 */
public class NewUserCommand extends ActionCommand {

    public static final String PATH_REGISTRATION_PAGE = "path.page.client.registration";

    @Override
    public boolean checkAccess(User user) {
        return true;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        return pathManager.getString(PATH_REGISTRATION_PAGE);
    }
}
