package org.linitly.boot.base.filter;

import org.linitly.boot.base.utils.filter.trim.TrimHttpServletRequestWrapper;
import org.springframework.core.annotation.Order;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @author: linxiunan
 * @date: 2020/11/25 11:19
 * @descrption:
 */
@Order(1)
@WebFilter(urlPatterns = "/*", filterName = "trimFilter")
public class TrimFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        TrimHttpServletRequestWrapper trimWrapper = new TrimHttpServletRequestWrapper(
                (HttpServletRequest) request);
        chain.doFilter(trimWrapper, response);
    }
}
