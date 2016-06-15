package com.epam.inet.provider.command;

import com.epam.inet.provider.entity.User;
import com.epam.inet.provider.command.exception.CommandException;
import com.epam.inet.provider.resource.PathManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Abstract command
 * Created by Hedgehog on 20.05.2016.
 */
public abstract class ActionCommand {

    /**
     * Path manager
     */
    protected static final PathManager pathManager = PathManager.INSTANCE;

    /**
     * Check the access of userIn, return true if the userIn has access to
     * this command, otherwise return false
     * @param user can be null
     * @return
     */
    public abstract boolean checkAccess(User user);

    /**
     * This method reads a command from the request
     * and processes it. The result will be given as
     * a page to forward to
     *
     * @param request request to read the command from
     * @param response
     * @return forward page
     */
    public abstract String execute(HttpServletRequest request, HttpServletResponse response) throws CommandException;
}
