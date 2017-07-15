import com.xunyuc.xproject.web.entity.UserInfo;
import com.xunyuc.xproject.web.proxy.UserProxy;
import com.xunyuc.xproject.web.dao.IUserInfoDAO;
import com.xunyuc.xproject.web.service.UserService;
import com.xunyuc.xproject.web.utils.SpringContextUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import redis.clients.jedis.Jedis;

import java.util.EnumSet;

/**
 * Created by Xunyuc on 2017/6/24.
 */
@ContextConfiguration(locations = {"classpath:spring.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class AnyTest {

    @Test
    public void test1() {
//        UserService userService = SpringContextUtil.getBean(UserService.class);
//        UserInfo user = userService.findUserByName("admin");
//        System.out.println(user.getSecretKey());
        Jedis jedis = new Jedis("192.168.80.130", 6379);
        jedis.set("test","testtest");

    }

//    @Test
    public void test2() {
//        UserService userService = SpringContextUtil.getBean(UserService.class);
//
//        EnumSet<UserProxy.Field> fields = EnumSet.of(UserProxy.Field.NAME, UserProxy.Field.SECRET_KEY);
//        UserProxy userProxy = userService.findUserProxyByName2(fields, "admin");
//        System.out.println(userProxy.getSecretKey());

    }

    @Test
    public void test3() {
        IUserInfoDAO IUserInfoDAO = SpringContextUtil.getBean(IUserInfoDAO.class);
        IUserInfoDAO.findUserByName("admin");
    }

}
