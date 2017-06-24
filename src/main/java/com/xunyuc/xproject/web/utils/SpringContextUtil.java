package com.xunyuc.xproject.web.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * Created by Xunyuc on 2017/6/24.
 */
@Component
public class SpringContextUtil implements ApplicationContextAware{

    public static ApplicationContext context;

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringContextUtil.context = applicationContext;
    }

    public static <T> T getBean(Class<? extends Object> clazz) {
        if (context == null) {
            return null;
        }
        return (T) context.getBean(clazz);
    }
}
