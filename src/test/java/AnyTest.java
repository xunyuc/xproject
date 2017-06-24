import com.xunyuc.xproject.web.bean.po.User;
import com.xunyuc.xproject.web.service.UserService;
import com.xunyuc.xproject.web.utils.SpringContextUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by Xunyuc on 2017/6/24.
 */
@ContextConfiguration(locations = {"classpath:spring.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class AnyTest {

    @Test
    public void test1() {
        UserService userService = SpringContextUtil.getBean(UserService.class);
        User user = userService.findUserByName("admin");
        System.out.println(user.getSecretKey());

    }

}
