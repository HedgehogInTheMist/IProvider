package com.epam.inet.provider.filter;

import com.epam.inet.provider.command.ActionCommand;
import com.epam.inet.provider.command.CommandHelper;
import com.epam.inet.provider.entity.User;
import com.epam.inet.provider.resource.PathManager;
import com.epam.inet.provider.service.AuthenticationService;
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

    private PathManager pathManager = PathManager.INSTANCE;
//    private static final Logger LOGGER = LogManager.getLogger(CommandAccessFilter.class);
    private Logger LOGGER = Logger.getRootLogger();

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

        CommandHelper commandHelper = CommandHelper.INSTANCE;
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;

        ActionCommand command = commandHelper.getCommand(request);

        User user = AuthenticationService.user(request);

        if (command.checkAccess(user)){
            chain.doFilter(req, resp);
        } else{
            response.setStatus(403);
            LOGGER.error(String.format("Access denied for %s to the following command: %s", (user != null) ? user : "anonymous user", command));
            request.getRequestDispatcher(pathManager.getString("path.error403")).forward(req, resp);
        }
    }

    public void init(FilterConfig config) throws ServletException {
    }

}
