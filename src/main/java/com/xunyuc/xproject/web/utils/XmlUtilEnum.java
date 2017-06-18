package com.xunyuc.xproject.web.utils;

import com.xunyuc.xproject.web.bean.ResultBean;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Date;

/**
 * Created by Xunyuc on 2017/6/17.
 */
public enum XmlUtilEnum {

    INSTANCE;

    private Jaxb2Marshaller jaxb2Marshaller;

    XmlUtilEnum(){
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

        String xml = XmlUtilEnum.INSTANCE.bean2Xml(resultBean);
        System.out.println(xml);
        System.out.println(XmlUtilEnum.INSTANCE.xml2Bean(xml));


    }
}
