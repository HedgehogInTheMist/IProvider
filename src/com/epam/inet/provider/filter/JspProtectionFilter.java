package com.epam.inet.provider.filter;

import com.epam.inet.provider.resource.PathManager;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.epam.inet.provider.util.Constants.*;

/**
 * Jsp pages protection filter
 */
public class JspProtectionFilter implements Filter{

    private static final Logger LOGGER = LogManager.getLogger(JspProtectionFilter.class);

    /**
     * path manager
     */
    private PathManager pathManager = PathManager.INSTANCE;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // blank
    }

    /**
     * Protects from direct jsp page viewing
     * @param req
     * @param resp
     * @param chain
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
        response.setStatus(404);
        LOGGER.error(LOG_MSG_BYPASS_FILTER);
        LOGGER.info(LOG_MSG_BYPASS_FILTER);
        request.getRequestDispatcher(pathManager.getString(PATH_ERROR_404_PAGE)).forward(req, resp);
    }

    @Override
    public void destroy() {
    }
}
