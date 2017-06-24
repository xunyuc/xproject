package com.xunyuc.xproject.web.utils;

import com.xunyuc.xproject.web.bean.ResultBean;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Date;
import java.util.UUID;

/**
 * Created by Xunyuc on 2017/6/17.
 */
public enum XmlUtil {

    INSTANCE;

    private Jaxb2Marshaller jaxb2Marshaller;

    XmlUtil(){
        jaxb2Marshaller = new Jaxb2Marshaller();
        // 指定包里查找有相关jaxb注解的类
        jaxb2Marshaller.setPackagesToScan("com.xunyuc.xproject");
    }

    public String bean2Xml(Object bean){
        StringWriter writer = new StringWriter();
        jaxb2Marshaller.marshal(bean, new StreamResult(writer));
        return writer.toString();
    }

    public Object xml2Bean(String xml){
        Object bean = jaxb2Marshaller.unmarshal(new StreamSource(new StringReader(xml)));
        return bean;
    }

    public static void main(String[] args) {
        ResultBean resultBean = new ResultBean();
        resultBean.setResultCode("002");
        resultBean.setResultMessage("xml");
        resultBean.setResultDate(new Date());

        String xml = XmlUtil.INSTANCE.bean2Xml(resultBean);
        System.out.println(xml);
        System.out.println(XmlUtil.INSTANCE.xml2Bean(xml));

        String s = UUID.randomUUID().toString();
        //去掉“-”符号
        String ss =  s.substring(0, 8) + s.substring(9, 13) + s.substring(14, 18) + s.substring(19, 23) + s.substring(24);
        System.out.println(s.length());
        System.out.println(ss);

    }
}
