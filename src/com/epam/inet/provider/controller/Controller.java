package com.epam.inet.provider.controller;

import com.epam.inet.provider.command.ActionCommand;
import com.epam.inet.provider.command.CommandFactory;
import com.epam.inet.provider.command.exception.CommandException;
import com.epam.inet.provider.resource.PathManager;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.MissingResourceException;

import static com.epam.inet.provider.util.Constants.*;

/**
 * Front Controller performs processing requests coming from user side.
 * Created by Hedgehog
 */
public class Controller extends HttpServlet {

    private static final Logger LOGGER = LogManager.getLogger(Controller.class);

    private CommandFactory commandFactory = CommandFactory.getCommandFactory();
    private String errorPagePath;

    @Override
    public void init() throws ServletException {
        try {
            BasicConfigurator.configure();
//            PropertyConfigurator.configure(PathManager.INSTANCE.getString("properties/log4j"));
            errorPagePath = PathManager.INSTANCE.getString(PATH_ERROR_500_PAGE);
        } catch (MissingResourceException e) {
            LOGGER.error(LOG_MSG_ERROR_INIT_CONTROLLER);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Process any incoming request
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ActionCommand command = commandFactory.getCommand(request);
        try {
            LOGGER.info(command.getClass().getSimpleName() + LOG_MSG_COMMAND_WORKS);
            String page = command.execute(request, response);
            if (page != null) {
                request.getRequestDispatcher(page).forward(request, response);
            }
        } catch (CommandException e) {
            if (errorPagePath != null) {
                LOGGER.error(LOG_MSG_ERROR_REQUEST);
                request.getRequestDispatcher(errorPagePath).forward(request, response);
            }

        }
    }

}
