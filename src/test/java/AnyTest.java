import com.xunyuc.xproject.web.bean.po.User;
import com.xunyuc.xproject.web.bean.proxy.UserProxy;
import com.xunyuc.xproject.web.bean.proxy.UserProxy2;
import com.xunyuc.xproject.web.service.UserService;
import com.xunyuc.xproject.web.utils.SpringContextUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.EnumSet;

/**
 * Created by Xunyuc on 2017/6/24.
 */
@ContextConfiguration(locations = {"classpath:spring.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class AnyTest {

    //@Test
    public void test1() {
        UserService userService = SpringContextUtil.getBean(UserService.class);
        User user = userService.findUserByName("admin");
        System.out.println(user.getSecretKey());

        String[] fields = new String[]{UserProxy.NAME, UserProxy.SECRET_KEY};
        UserProxy userProxy = userService.findUserProxyByName(fields, "admin");
        System.out.println(userProxy.getSecretKey());

    }

    @Test
    public void test2() {
        UserService userService = SpringContextUtil.getBean(UserService.class);

        EnumSet<UserProxy2.Field> fields = EnumSet.of(UserProxy2.Field.NAME, UserProxy2.Field.SECRET_KEY);
        UserProxy2 userProxy = userService.findUserProxyByName2(fields, "admin");
        System.out.println(userProxy.getSecretKey());

    }

}
