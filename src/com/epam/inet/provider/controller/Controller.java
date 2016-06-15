package com.epam.inet.provider.controller;

import com.epam.inet.provider.command.CommandHelper;
import com.epam.inet.provider.command.ActionCommand;
import com.epam.inet.provider.command.exception.CommandException;
import com.epam.inet.provider.resource.PathManager;
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
 * Front Controller performs processing requests coming from userIn side.
 */
public class Controller extends HttpServlet {

    private static final Logger LOGGER = LogManager.getLogger(Controller.class);

    private final CommandHelper commandHelper = CommandHelper.INSTANCE;

	private String errorPagePath;

	@Override
	public void init() throws ServletException {
		try{
			errorPagePath = PathManager.INSTANCE.getString(PATH_ERROR_500_PAGE);
		} catch (MissingResourceException e){
			LOGGER.error(LOG_MSG_ERROR_INIT_CONTROLLER);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Process any incoming request
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ActionCommand command = commandHelper.getCommand(request);

        try{
            LOGGER.info(command.getClass().getSimpleName() + LOG_MSG_COMMAND_WORKS);
            String page = command.execute(request, response);
            if (page != null){
                request.getRequestDispatcher(page).forward(request, response);
            }
        } catch (CommandException e) {
			if (errorPagePath != null){
				request.getRequestDispatcher(errorPagePath).forward(request, response);
			}
			LOGGER.error(LOG_MSG_ERROR_REQUEST);
        }
    }

}
