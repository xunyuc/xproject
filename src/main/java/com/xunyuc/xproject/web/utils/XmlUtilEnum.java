package com.xunyuc.xproject.web.utils;

import com.xunyuc.xproject.web.bean.ResultBean;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

import javax.xml.transform.stream.StreamResult;
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
        jaxb2Marshaller.setPackagesToScan("com.xunyuc.xproject.web");
    }

    public String bean2Xml(Object bean){
        StringWriter writer = new StringWriter();
        jaxb2Marshaller.marshal(bean, new StreamResult(writer));
        return writer.toString();
    }

    public static void main(String[] args) {
        ResultBean resultBean = new ResultBean();
        resultBean.setResultCode("002");
        resultBean.setResultMessage("xml");
        resultBean.setResultDate(new Date());
        System.out.println(XmlUtilEnum.INSTANCE.bean2Xml(resultBean));
    }
}
