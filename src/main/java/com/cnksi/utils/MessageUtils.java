package com.cnksi.utils;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.InputStreamRequestEntity;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;

/**
 * Created by DELL on 2016/12/23.
 */
public class MessageUtils {

    public static void main(String[] args) {


        String phoneNum="18081329165";//"18081329165";
        String content="肖老师，已给电科院电话联系，让他帮我们审核；";

        //sendSingleMsg(phoneNum,content);
        send1Msg(phoneNum,content);
    }

    public static void sendSingleMsg(String phoneNum,String content){

        String url = "http://10.176.3.194/scdl_sms_send_ws.asmx";//请求Webservice地址

         /*1.发送*/
        StringBuffer   soapRequestData = new StringBuffer();
        soapRequestData.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
        soapRequestData.append("<soap12:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap12=\"http://www.w3.org/2003/05/soap-envelope\">");
        soapRequestData.append("<soap12:Body>");
        soapRequestData.append("     <scdl_sms_send_ws_Single xmlns=\"scdl_sms_interface_ws\">");
        soapRequestData.append("        <UserId>lesan001</UserId>");
        soapRequestData.append("         <Pwd>abcd@1234</Pwd>");
        soapRequestData.append("         <PhoneNum>"+phoneNum+"</PhoneNum>");
        soapRequestData.append("         <Content>"+content+"</Content>");
        soapRequestData.append("    </scdl_sms_send_ws_Single>");
        soapRequestData.append("</soap12:Body>");
        soapRequestData.append("</soap12:Envelope>");

        getWebService(soapRequestData.toString(),url);
    }


    public static  void getSendStatus(){

        String url = "http://10.176.3.194/scdl_sms_rpt_ws.asmx";//请求Webservice地址

          /*1.发送状态查询*/
        StringBuffer soapRequestData = new StringBuffer();
        soapRequestData.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
        soapRequestData.append("<soap12:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap12=\"http://www.w3.org/2003/05/soap-envelope\">");
        soapRequestData.append("<soap12:Body>");
        soapRequestData.append("     <Scdl_Sms_Rpt_Ws xmlns=\"scdl_sms_interface_ws\">");
        soapRequestData.append("        <UserId>lesan001</UserId>");
        soapRequestData.append("         <Pwd>abcd@1234</Pwd>");
        soapRequestData.append("        <XMLInfo></XMLInfo>");
        soapRequestData.append("    </Scdl_Sms_Rpt_Ws>");
        soapRequestData.append("</soap12:Body>");
        soapRequestData.append("</soap12:Envelope>");

        getWebService(soapRequestData.toString(),url);
    }


    public static void send1Msg(String phoneNum,String content){

        String url = "http://10.176.3.194/scdl_sms_send_ws.asmx";//请求Webservice地址

         /*1.发送*/
        StringBuffer   soapRequestData = new StringBuffer();
        soapRequestData.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
        soapRequestData.append("<soap12:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap12=\"http://www.w3.org/2003/05/soap-envelope\">");
        soapRequestData.append("<soap12:Body>");
        soapRequestData.append("     <scdl_sms_send_ws1 xmlns=\"scdl_sms_interface_ws\">");
        soapRequestData.append("        <UserId>lesan001</UserId>");
        soapRequestData.append("         <Pwd>abcd@1234</Pwd>");
        soapRequestData.append("         <Count>2</Count>");
        soapRequestData.append("         <XMLInfo><OneToOneData><SmsList>" +
                "<SubDataOneToOne><PHONENUM>"+phoneNum+"</PHONENUM><SMSCONTENT>"+content+"</SMSCONTENT><MSG_ID>123</MSG_ID><BS_TYPE_ID>831</BS_TYPE_ID><UNIT_ID></UNIT_ID><SENDTIME></SENDTIME><SEND_USER_ID></SEND_USER_ID> </SubDataOneToOne>" +
                "<SubDataOneToOne><PHONENUM>"+phoneNum+"</PHONENUM><SMSCONTENT>"+content+"</SMSCONTENT><MSG_ID>123</MSG_ID><BS_TYPE_ID>831</BS_TYPE_ID><UNIT_ID></UNIT_ID><SENDTIME></SENDTIME><SEND_USER_ID></SEND_USER_ID> </SubDataOneToOne>" +
                "</SmsList> </OneToOneData> </XMLInfo>".toLowerCase());

        soapRequestData.append("    </scdl_sms_send_ws1>");
        soapRequestData.append("</soap12:Body>");
        soapRequestData.append("</soap12:Envelope>");


        getWebService(soapRequestData.toString(),url);
    }


    public static void getWebService(String soapRequestData,String url) {

        PostMethod postMethod = new PostMethod(url);//请求连接
        System.out.println(soapRequestData);
        // 然后把Soap请求数据添加到PostMethod中
        try {
            byte[] b = soapRequestData.getBytes("utf-8");
            InputStream is = new ByteArrayInputStream(b, 0, b.length);
            RequestEntity re = new InputStreamRequestEntity(is, b.length,"application/soap+xml; charset=utf-8");
            postMethod.setRequestEntity(re);

            // 最后生成一个HttpClient对象，并发出postMethod请求
            HttpClient httpClient = new HttpClient();
            int statusCode = httpClient.executeMethod(postMethod);
            String soapResponseData = postMethod.getResponseBodyAsString();
            soapResponseData = soapResponseData.replace("&lt;","<");
            soapResponseData = soapResponseData.replace("&gt;",">");
            System.out.println(statusCode);
            System.out.print(soapResponseData);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (HttpException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
