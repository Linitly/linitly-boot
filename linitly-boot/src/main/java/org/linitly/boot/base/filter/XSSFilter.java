package org.linitly.boot.base.filter;

import org.linitly.boot.base.utils.filter.xss.XssHttpServletRequestWrapper;
import org.springframework.core.annotation.Order;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @author: linxiunan
 * @date: 2020/4/28 9:39
 * @descrption:
 */
@Order(2)
@WebFilter(urlPatterns = "/*", filterName = "xssFilter")
public class XSSFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        XssHttpServletRequestWrapper xssRequest = new XssHttpServletRequestWrapper(
                (HttpServletRequest) request);
        filterChain.doFilter(xssRequest, response);
    }

    @Override
    public void destroy() {

    }
}
