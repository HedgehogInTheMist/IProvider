package com.epam.inet.provider.filter;

import com.epam.inet.provider.command.ActionCommand;
import com.epam.inet.provider.command.CommandFactory;
import com.epam.inet.provider.entity.User;
import com.epam.inet.provider.resource.PathManager;
import com.epam.inet.provider.service.AuthenticationService;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * command access filter
 * Created by Hedgehog on 22.04.2016.
 */
public class CommandAccessFilter implements Filter {
    private static final String ERROR_403_PAGE = "path.error403";
    private PathManager pathManager = PathManager.INSTANCE;
    private static final Logger LOGGER = LogManager.getLogger(CommandAccessFilter.class);

    public void destroy() {
    }

    /**
     * Checks the user role before executing command
     * If user doesn't have required role a 403 error
     * page will be displayed
     *
     * @param req
     * @param resp
     * @param chain
     * @throws ServletException
     * @throws IOException
     */
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {

        CommandFactory commandFactory = CommandFactory.getCommandFactory();

        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;

        ActionCommand command = commandFactory.getCommand(request);

        User user = AuthenticationService.user(request);

        if (command.checkAccess(user)){
            chain.doFilter(req, resp);
        } else{
            response.setStatus(403);
            LOGGER.error(String.format("Access denied for %s to the following command: %s", (user != null) ? user : "anonymous user", command));
            request.getRequestDispatcher(pathManager.getString(ERROR_403_PAGE)).forward(req, resp);
        }
    }

    public void init(FilterConfig config) throws ServletException {
    }

}
