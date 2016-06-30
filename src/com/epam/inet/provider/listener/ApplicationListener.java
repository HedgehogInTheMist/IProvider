package com.epam.inet.provider.listener;

import com.epam.inet.provider.dao.pool.ConnectionPool;
import com.epam.inet.provider.dao.exception.ConnectionPoolException;
import com.epam.inet.provider.resource.LocaleManager;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.*;
import java.util.Locale;

/**
 * Listener deals with locales initialization
 * Created by Hedgehog on 22.04.2016.
 */
public class ApplicationListener implements ServletContextListener,
        HttpSessionListener, HttpSessionAttributeListener, ServletRequestListener{

    private static final Logger LOGGER = LogManager.getLogger(ApplicationListener.class);
    public static final String LOCALES = "locales";
    public static final String LOCALE = "locale";
    private LocaleManager localeManager = LocaleManager.INSTANCE;


    public ApplicationListener() {
    }

    public void contextInitialized(ServletContextEvent sce) {

    /*    ServletContext context = sce.getServletContext();
        context.setAttribute(LOCALES, LocaleManager.INSTANCE.getLocales());

        Locale.setDefault(Locale.ENGLISH);*/

    }

    public void contextDestroyed(ServletContextEvent sce) {
        try {
            ConnectionPool connectionPool = ConnectionPool.getInstance();
            connectionPool.shutDown();
        } catch (ConnectionPoolException e) {
            LOGGER.error(e.getMessage());
        }
    }

    public void sessionCreated(HttpSessionEvent sessionEvent) {
        ServletContext context = sessionEvent.getSession().getServletContext();
        context.setAttribute(LOCALES, LocaleManager.INSTANCE.getLocales());

        Locale.setDefault(Locale.ENGLISH);
    }

    public void sessionDestroyed(HttpSessionEvent se) {

    }


    public void attributeAdded(HttpSessionBindingEvent sbe) {
    }

    public void attributeRemoved(HttpSessionBindingEvent sbe) {
    }

    public void attributeReplaced(HttpSessionBindingEvent sbe) {

    }

    @Override
    public void requestDestroyed(ServletRequestEvent servletRequestEvent) {

    }

    @Override
    public void requestInitialized(ServletRequestEvent servletRequestEvent) {
        ServletRequest request = servletRequestEvent.getServletRequest();
        Locale locale = localeManager.resolveLocale(request);
        request.setAttribute(LOCALE, locale);
    }
}
