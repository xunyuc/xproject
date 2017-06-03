package com.xunyuc.xproject.web.controller;

import com.xunyuc.xproject.web.bean.ResultBean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;

/**
 * Created by Xunyuc on 2017/6/3.
 */

@Controller
@RequestMapping("/test")
public class TestController {

    @RequestMapping(value = "/test1", method = RequestMethod.GET)
    public ResultBean test1(@RequestParam(value = "format") String format) {
        ResultBean resultBean = new ResultBean();
        resultBean.setResultCode("002");
        resultBean.setResultMessage(format);
        resultBean.setResultDate(new Date());
        return  resultBean;
    }
}
