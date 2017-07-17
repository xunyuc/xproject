package com.xunyuc.xproject.web.controller;

import com.xunyuc.xproject.web.bean.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Created by Xunyuc on 2017/6/4.
 */
@Controller
public class HomeController {


    @RequestMapping(value="/login",method=RequestMethod.POST)
    public String login(String userName, String password){
        try {
            //使用权限工具进行用户登录，登录成功后跳到shiro配置的successUrl中，与下面的return没什么关系！
            SecurityUtils.getSubject().login(new UsernamePasswordToken(userName, password));
            return "redirect:/index.jsp";
        } catch (AuthenticationException e) {
            return "redirect:/login.jsp";
        }
    }

    @RequestMapping(value="/logout",method=RequestMethod.GET)
    public String logout(RedirectAttributes redirectAttributes ){
        //使用权限管理工具进行用户的退出，跳出登录，给出提示信息
        SecurityUtils.getSubject().logout();
        redirectAttributes.addFlashAttribute("message", "您已安全退出");
//        return "login";
        return "redirect:/login.jsp";
    }

    @RequestMapping("/403")
    public String unauthorizedRole(){
        return "/403";
    }

}
