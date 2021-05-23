package by.vsu.controller.filters;

import jakarta.servlet.*;

import java.io.IOException;

/**
 * При переходе на любую страницу вызывает самым первым и задает нужную
 * кодировку.
 *
 * @author Kovzov Vladislav
 */
public class EncodingFilter implements Filter {
    private String encoding;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        encoding = filterConfig.getInitParameter("encoding");
    }

    @Override
    public void doFilter(ServletRequest servletRequest,
                         ServletResponse servletResponse,
                         FilterChain filterChain)
            throws IOException, ServletException {
        if (encoding != null) {
            servletRequest.setCharacterEncoding(encoding);
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
