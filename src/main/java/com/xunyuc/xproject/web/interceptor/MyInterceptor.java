package com.xunyuc.xproject.web.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.UrlPathHelper;

/**
 * Created by Xunyuc on 2017/6/3.
 */
public class MyInterceptor implements HandlerInterceptor {
    public boolean preHandle(javax.servlet.http.HttpServletRequest httpServletRequest, javax.servlet.http.HttpServletResponse httpServletResponse, Object o) throws Exception {
        /**
         * (1)部署在api目录（2）<servlet-mapping>的<url-pattern>/</url-pattern>
         * http://localhost:8080/api/test/test1
         * lookupPath:/test/test1
         * getServletPath():/test/test1
         * getPathInfo():null
         * getRequestURI():/api/test/test1
         *
         * (1)部署在api目录（2）<servlet-mapping>的<url-pattern>/test/*</url-pattern>
         * http://localhost:8080/api/test/test1
         * lookupPath:/test1
         * getServletPath():/test
         * getPathInfo():/test1
         * getRequestURI():/api/test/test1
         *
         * (1)部署在根目录（2）<servlet-mapping>的<url-pattern>/</url-pattern>
         * http://localhost:8080/test/test1
         * lookupPath：/test/test1
         * servletContextPath:/test/test1
         * pathInfo:null
         * uri:/test/test1
         *
         * (1)部署在根目录（2）<servlet-mapping>的<url-pattern>/test/*</url-pattern>
         * http://localhost:8080/test/test1
         * lookupPath：/test1
         * servletContextPath:/test
         * pathInfo:/test1
         * uri:/test/test1
         */
        String lookupPath = new UrlPathHelper().getLookupPathForRequest(httpServletRequest);
        System.out.println("in MyInterceptor preHandle：" + lookupPath);
        return true;
    }

    public void postHandle(javax.servlet.http.HttpServletRequest httpServletRequest, javax.servlet.http.HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
        System.out.println("in MyInterceptor postHandle");
    }

    public void afterCompletion(javax.servlet.http.HttpServletRequest httpServletRequest, javax.servlet.http.HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
        System.out.println("in MyInterceptor afterCompletion");
    }
}
