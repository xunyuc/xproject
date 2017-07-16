package com.xunyuc.xproject.web.utils;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Xunyuc on 2017/7/16.
 */
public class ServletUtil {

    /**
     * 获取当前请求对象
     * @return
     */
    public static HttpServletRequest getRequest(){
        try{
            return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        }catch(Exception e){
            return null;
        }
    }

}
