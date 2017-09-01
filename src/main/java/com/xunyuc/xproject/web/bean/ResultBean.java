package com.xunyuc.xproject.web.bean;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;
import org.eclipse.persistence.oxm.annotations.XmlCDATA;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.annotation.*;
import javax.xml.transform.stream.StreamResult;
import java.util.Date;

/**
 * Created by Xunyuc on 2017/6/3.
 */
@XmlRootElement
public class ResultBean {

    private String resultCode;

    private String resultMessage;

    private Date resultDate;

    @XmlCDATA
    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    public String getResultMessage() {
        return resultMessage;
    }

    public void setResultMessage(String resultMessage) {
        this.resultMessage = resultMessage;
    }

    @XmlTransient
    @JSONField(serialize = false)
    public Date getResultDate() {
        return resultDate;
    }

    public void setResultDate(Date resultDate) {
        this.resultDate = resultDate;
    }


    public static void main(String[] args) throws Exception {
        ResultBean resultBean = new ResultBean();
        resultBean.setResultCode("002");
        resultBean.setResultMessage("xml");
        resultBean.setResultDate(new Date());

//        JAXBContext context = JAXBContext.newInstance(resultBean.getClass());
//        Marshaller marshal = context.createMarshaller();
//        marshal.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true); // 格式化输出
//		marshal.setProperty(Marshaller.JAXB_FRAGMENT, false);// 是否省略xml头信息
//        marshal.marshal(resultBean,System.out);

//        Jaxb2Marshaller marshal = new Jaxb2Marshaller();
//        marshal.setPackagesToScan("com.xunyuc.xproject.web");
//        marshal.marshal(resultBean, new StreamResult(System.out));

        System.out.println(JSONObject.toJSON(resultBean));
    }
}
