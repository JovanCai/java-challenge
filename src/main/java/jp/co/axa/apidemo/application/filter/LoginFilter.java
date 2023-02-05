package jp.co.axa.apidemo.application.filter;

import jp.co.axa.apidemo.application.auth.TokenUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LoginFilter implements Filter {
    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void destroy() {
        logger = null;
    }


    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse rsp = (HttpServletResponse) response;

        String url = req.getRequestURL().toString();
        // No need login validation in those pattern
        if (url.contains("login") || url.contains("doLogin") || TokenUtils.getUser() != null) {
            filterChain.doFilter(request, response);
        } else {
            rsp.sendRedirect("/login");
        }

    }
}
