package com.epam.inet.provider.command;

import com.epam.inet.provider.entity.User;
import com.epam.inet.provider.command.exception.CommandException;
import com.epam.inet.provider.resource.PathManager;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.epam.inet.provider.util.Constants.*;

/**
 * This command is used if empty or wrong command is specified
 */
public class EmptyCommand extends ActionCommand{

    @Override
    public boolean checkAccess(User user) {
        return true;
    }

    /**
     * default command that render main page
     * @param request request to read the command from
     * @param response response
     * @return path
     * @throws ServletException
     * @throws IOException
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        return PathManager.INSTANCE.getString(PATH_MAIN_PAGE);
    }
}
