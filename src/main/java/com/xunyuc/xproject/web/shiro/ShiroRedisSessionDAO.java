package com.xunyuc.xproject.web.shiro;

import com.xunyuc.xproject.web.utils.SerializableUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.eis.AbstractSessionDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

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
        Serializable sessionId = this.generateSessionId(session);
        this.assignSessionId(session, sessionId);
        this.saveSession(session);
        return sessionId;
    }

    @Override
    protected Session doReadSession(Serializable sessionId) {
        String key = prefix + sessionId.toString();
        String sessionStr = (String) redisTemplate.opsForValue().get(key);
        if (StringUtils.isNotBlank(sessionStr)) {
            Session session = (Session) SerializableUtils.deserialize(sessionStr);
            return session;
        }
        return null;
    }

    public void update(Session session) throws UnknownSessionException {
        this.saveSession(session);
    }

    public void delete(Session session) {
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
        session.setTimeout(1800000);
        String key = prefix + session.getId().toString();
        String value = SerializableUtils.serialize(session);
        redisTemplate.opsForValue().set(key, value, 30, TimeUnit.MINUTES);
    }


}
