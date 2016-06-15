package com.epam.inet.provider.filter;

import org.apache.log4j.Logger;

import javax.servlet.*;
import java.io.IOException;

/**
 * Encoding filter
 */
public class EncodingFilter implements Filter {

	public void destroy() {
        // blank
	}

    /**
     * Sets correct encoding
     * @param req
     * @param resp
     * @param chain
     * @throws ServletException
     * @throws IOException
     */
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {

        String encoding = req.getCharacterEncoding();
		if (!"UTF-8".equalsIgnoreCase(encoding)){
			req.setCharacterEncoding("UTF-8");
		}

		chain.doFilter(req, resp);

	}

	public void init(FilterConfig config) throws ServletException {
        // blank
	}

}
