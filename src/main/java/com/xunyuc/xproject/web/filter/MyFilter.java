package com.xunyuc.xproject.web.filter;


import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class MyFilter implements Filter {

    public void init(FilterConfig filterConfig) throws ServletException {

    }

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse res = (HttpServletResponse) servletResponse;
//        HttpSession session = req.getSession(false);
        // 登陆成功后，要修改sessionId，保证安全
//        if (session != null && !session.isNew()) {
//            session.invalidate();
//        }
//        session = req.getSession(true);
//        session.setAttribute("test", "testtest");
        filterChain.doFilter(servletRequest, servletResponse);
    }

    public void destroy() {

    }
}
