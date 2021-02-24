package org.primefaces.showcase.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.primefaces.showcase.view.login.LoginView;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LoginFilter implements Filter {

    private static final Logger LOGGER = Logger.getLogger(LoginFilter.class.getName());

    public static final String LOGIN_PAGE = "/login.xhtml";

    @Override
    public void doFilter(ServletRequest servletRequest,
                         ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {

        HttpServletRequest httpServletRequest =
                (HttpServletRequest) servletRequest;
        HttpServletResponse httpServletResponse =
                (HttpServletResponse) servletResponse;

        // managed bean name is exactly the session attribute name
        LoginView loginView = (LoginView) httpServletRequest
                .getSession().getAttribute("loginView");

        if (loginView != null) {
            if (loginView.isLoggedIn()) {
                LOGGER.log(Level.INFO, "user is logged in");
                // user is logged in, continue request
                filterChain.doFilter(servletRequest, servletResponse);
            } else {
                LOGGER.log(Level.INFO, "user is not logged in");
                // user is not logged in, redirect to login page
                httpServletResponse.sendRedirect(httpServletRequest.getContextPath() + LOGIN_PAGE);
            }
        } else {
            LOGGER.log(Level.INFO, "loginView not found");
            // user is not logged in, redirect to login page
            httpServletResponse.sendRedirect(httpServletRequest.getContextPath() + LOGIN_PAGE);
        }
    }

    @Override
    public void init(FilterConfig arg0) throws ServletException {
        LOGGER.log(Level.INFO, "loginFilter initialized");
    }

    @Override
    public void destroy() {
        // close resources
    }
}