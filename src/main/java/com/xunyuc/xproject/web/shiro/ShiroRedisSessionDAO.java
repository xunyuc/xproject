package com.xunyuc.xproject.web.shiro;

import com.alibaba.fastjson.JSONObject;
import com.xunyuc.xproject.web.utils.SerializableUtils;
import com.xunyuc.xproject.web.utils.ServletUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.SimpleSession;
import org.apache.shiro.session.mgt.ValidatingSession;
import org.apache.shiro.session.mgt.eis.AbstractSessionDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.Collection;
import java.util.Collections;
import java.util.concurrent.TimeUnit;

/**
 * Created by Xunyuc on 2017/7/15.
 */
@Component
public class ShiroRedisSessionDAO extends AbstractSessionDAO {

    private static final String prefix = "shirosession:";
    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    protected Serializable doCreate(Session session) {
        HttpServletRequest request = ServletUtil.getRequest();
        if (request != null){
            String uri = request.getServletPath();
            // TODO 如果是静态文件，则不创建SESSION
//            if (ServletUtil.isStaticFile(uri)){
//                return null;
//            }
        }
        Serializable sessionId = this.generateSessionId(session);
        this.assignSessionId(session, sessionId);
        this.saveSession(session);
        return sessionId;
    }

    @Override
    protected Session doReadSession(Serializable sessionId) {
        Session s = null;
        HttpServletRequest request = ServletUtil.getRequest();
        if (request != null){
            String uri = request.getServletPath();
            // TODO 如果是静态文件，则不获取SESSION
//            if (ServletUtil.isStaticFile(uri)){
//                return null;
//            }
            s = (Session)request.getAttribute("session_"+sessionId);
        }
        if (s != null){
            return s;
        }

        Session session = null;
        String key = prefix + sessionId.toString();
        String sessionStr = (String) redisTemplate.opsForValue().get(key);
        if (StringUtils.isNotBlank(sessionStr)) {
            session = (Session) SerializableUtils.deserialize(sessionStr);
        }

//        if (request != null && session != null){
//            request.setAttribute("session_"+sessionId, session);
//        }
//        if(session instanceof ValidatingSession && !((ValidatingSession)session).isValid()) {
//            return null;
//        }

        return session;
    }

    public void update(Session session) throws UnknownSessionException {
        if (session == null || session.getId() == null) {
            return;
        }
        if(session instanceof ValidatingSession && !((ValidatingSession)session).isValid()) {
            return; //如果会话过期/停止 没必要再更新了
        }
        // TODO 如果是静态文件，则不更新SESSION
        //.css,.js,.png,.jpg,.gif,.jpeg,.bmp,.ico,.swf,.psd,.htc,.htm,.html,.crx,.xpi,.exe,.ipa,.apk
        // TODO 如果是视图文件，则不更新SESSION

        HttpServletRequest request = ServletUtil.getRequest();
        if (request != null){
            System.out.println("update-----url---" + request.getServletPath());
        }
        System.out.println("update-----session---" + JSONObject.toJSON(session));
        this.saveSession(session);
    }

    public void delete(Session session) {
        if (session == null || session.getId() == null) {
            return;
        }
        String key = prefix + session.getId().toString();
        redisTemplate.delete(key);
    }

    public Collection<Session> getActiveSessions() {
        return Collections.emptySet();
    }

    private void saveSession(Session session) throws UnknownSessionException{
        if(session == null || session.getId() == null){
            return;
        }
        String key = prefix + session.getId().toString();
        String value = SerializableUtils.serialize(session);
        redisTemplate.opsForValue().set(key, value, session.getTimeout(), TimeUnit.MINUTES);
    }


}
