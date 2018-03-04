package com.imory.cn.common.controller;

import com.imory.cn.utils.MessageUtil;
import com.imory.cn.utils.SignUtil;
import com.imory.cn.wx.resp.*;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Map;

/**
 * <p>短信验证码发送接口</p>
 * <p/>
 * <p>URL</p>
 *
 * @author zb.jiang 作者
 * @version 1.0 版本
 * date 15/4/3 时间
 */
@Controller
@RequestMapping("/wx")
public class WxMessageController
{
    @Value( "#{runtimeProperties['wechat.appid']}" )
    private String appId;

    private static Logger logger = Logger.getLogger("WECHATEVENT");

    @RequestMapping(value = "/coreServlet",method = RequestMethod.GET)
    public void doWxMsg(HttpServletRequest servletRequest, HttpServletResponse response)
    {
        String signature = servletRequest.getParameter("signature");
        String timestamp = servletRequest.getParameter("timestamp");
        String nonce = servletRequest.getParameter("nonce");
        String echostr = servletRequest.getParameter("echostr");
        try {
            PrintWriter out = response.getWriter();
            //验证token
            if (SignUtil.checkSignature(signature, timestamp, nonce)) {
                out.print(echostr);
            }
            out.close();
        }catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    @RequestMapping(value = "/coreServlet",method = RequestMethod.POST)
    public void doRespWxMsg(HttpServletRequest servletRequest, HttpServletResponse response)
    {
        try {

            servletRequest.setCharacterEncoding("UTF-8");
            response.setCharacterEncoding("UTF-8");

            String signature = servletRequest.getParameter( "signature" );
            String timestamp = servletRequest.getParameter( "timestamp" );
            String nonce = servletRequest.getParameter( "nonce" );

            PrintWriter out;
            try {
                out = response.getWriter();
                if (SignUtil.checkSignature( signature, timestamp, nonce )) {
                    String respXml = processRequest(servletRequest);
                    logger.debug( respXml );
                    if(!StringUtils.isEmpty(respXml))
                    {
                        out.print(respXml);
                    }
                }
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    /**
     * 处理微信发来的请求
     *
     * @param request http请求
     */
    private String processRequest(HttpServletRequest request)
    {
        String respXml = "";
        try
        {
            //解析请求XML
            Map<String, String> requestMap = MessageUtil.parseXml(request);
            //获取发送方账号（用户）
            String fromUserName = requestMap.get( "FromUserName" );
            //开发者微信号
            String toUserName = requestMap.get( "ToUserName" );
            //消息类型
            String msgType = requestMap.get( "MsgType" );
            //内容
            String content = requestMap.get("Content");
            logger.debug("微信服务发来消息。消息类型[" + msgType + "]用户来源[" + fromUserName + "]");

            if (msgType.equalsIgnoreCase(MessageUtil.REQ_MESSAGE_TYPE_TEXT))
            {
                //解析用户文本消息
                respXml = analysisTextMsg(content,fromUserName,toUserName);
            } else if (msgType.equalsIgnoreCase(MessageUtil.REQ_MESSAGE_TYPE_IMAGE))
            {
                logger.debug("您发送的是图片消息！");
            } else if (msgType.equalsIgnoreCase(MessageUtil.REQ_MESSAGE_TYPE_VOICE))
            {
                logger.debug("您发送的是视频消息！");
            } else if (msgType.equalsIgnoreCase(MessageUtil.REQ_MESSAGE_TYPE_VIDEO))
            {
                logger.debug("您发送的是音频消息！");
            } else if (msgType.equalsIgnoreCase(MessageUtil.REQ_MESSAGE_TYPE_LOCATION))
            {
                logger.debug("您发送的是地理位置消息！");
            } else if (msgType.equalsIgnoreCase(MessageUtil.REQ_MESSAGE_TYPE_LINK))
            {
                logger.debug("您发送的是链接消息！");
            } else if (msgType.equalsIgnoreCase(MessageUtil.REQ_MESSAGE_TYPE_EVENT))
            {
                //解析事件信息
               respXml = analysisEventMsg(fromUserName, toUserName, requestMap);
            }
        } catch (Exception e)
        {
            e.printStackTrace();
            logger.error( "处理消息发生错误", e );
            return "";
        }
        return respXml;
    }

    /***
     * 解析用户发送的事件信息
     * @param fromUserName 发送方账号
     * @param toUserName 公众号
     * @param requestMap 请求map
     */
    private String analysisEventMsg(String fromUserName, String toUserName, Map<String, String> requestMap)
    {
        //获取事件类型
        String eventType = requestMap.get("Event");
        if (eventType.equals(MessageUtil.EVENT_TYPE_SUBSCRIBE))
        {
            //解析用户关注事件
            analysisSubscribeEvent(fromUserName);
            //订阅
            return getSubscribeRespXml(fromUserName,toUserName);
        } else if (eventType.equalsIgnoreCase(MessageUtil.EVENT_TYPE_UNSUBSCRIBE))
        {
            return "";
        } else if (eventType.equals(MessageUtil.EVENT_TYPE_TEMPLATESENDJOBFINISH))
        {
            logger.debug("模板消息发送结果[OpenId:" + fromUserName + "|MsgId:" + requestMap.get("MsgID") + "|Status:" + requestMap.get("Status") + "]");
            return getDefaultRespXml(fromUserName,toUserName);
        } else if (eventType.equalsIgnoreCase(MessageUtil.EVENT_TYPE_SCAN))
        {
            // 处理扫描带参数二维码事件
            return getScanEventRespXml(requestMap,fromUserName,toUserName);
        } else if (eventType.equalsIgnoreCase(MessageUtil.EVENT_TYPE_LOCATION))
        {
            // 处理上报地理位置事件
            return getDefaultRespXml(fromUserName,toUserName);
        } else if (eventType.equalsIgnoreCase(MessageUtil.EVENT_TYPE_CLICK))
        {
            logger.info("开始请求queryBtnClickService服务");
            TextMessage textMessage = new TextMessage();
            textMessage.setToUserName(fromUserName);
            textMessage.setFromUserName(toUserName);
            textMessage.setCreateTime(new Date().getTime());
            textMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);
            String respMsg = "您好！请点击下方菜单栏中“会议大厅”选项，选择您要参加的会议并进行签到。";
            textMessage.setContent(respMsg);
            return MessageUtil.messageToXml(textMessage);
        } else if(eventType.equalsIgnoreCase(MessageUtil.EVENT_TYPE_MERCHANT_ORDER))
        {
            //处理微信小店付款信息
            return getDefaultRespXml(fromUserName,toUserName);
        }
        else
        {
            return getDefaultRespXml(fromUserName,toUserName);
        }
    }

    /**
     * 用户关注微信号事件解析
     * @param fromUserName 发送方账号
     */
    private void analysisSubscribeEvent(String fromUserName)
    {
    }

    /***
     * 处理二维码扫描事件
     * @param requestMap 请求map
     * @param fromUserName
     * @param toUserName 公众号
     */
    private String getScanEventRespXml(Map<String, String> requestMap, String fromUserName, String toUserName)
    {
        String eventKey = requestMap.get("EventKey");
        String openId = requestMap.get("FromUserName");
        logger.debug( "捕获到扫描带参数二维码事件，事件KEY=[" + eventKey + "]，用户openid=[" + openId + "]" );
        return getDefaultRespXml( fromUserName, toUserName );
    }

    /***
     * 获取默认响应信息xml
     */
    private String getRespXmlByMsg(String fromUserName,String toUserName,String message)
    {
        TextMessage textMessage = new TextMessage();
        textMessage.setToUserName(fromUserName);
        textMessage.setFromUserName(toUserName);
        textMessage.setCreateTime(new Date().getTime());
        textMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);
        textMessage.setContent( message );
        return MessageUtil.messageToXml(textMessage);
    }

    /***
     * 获取默认响应信息xml
     */
    private String getHistoryRespXml(String fromUserName,String toUserName,String btnUrl,String message)
    {
        logger.debug("从redis获取配置的回复信息,textMsg:" + message);
        message = message.replaceAll("<a>","<a href='" + btnUrl + "'>");

        TextMessage textMessage = new TextMessage();
        textMessage.setToUserName(fromUserName);
        textMessage.setFromUserName( toUserName );
        textMessage.setCreateTime( new Date().getTime() );
        textMessage.setMsgType( MessageUtil.RESP_MESSAGE_TYPE_TEXT );
        String respMsg = getAutoReplayDefaultMsg();
        textMessage.setContent(respMsg + "\n" + message );
        return MessageUtil.messageToXml(textMessage);
    }

    /***
     * 获取默认响应信息xml
     */
    private String getDefaultRespXml(String fromUserName,String toUserName)
    {
        TextMessage textMessage = new TextMessage();
        textMessage.setToUserName( fromUserName );
        textMessage.setFromUserName( toUserName );
        textMessage.setCreateTime( new Date().getTime() );
        textMessage.setMsgType(MessageUtil.REQ_MESSAGE_TYPE_TEXT);
        String respMsg = getAutoReplayDefaultMsg();
        textMessage.setContent(respMsg);
        //创建客服会话
        return MessageUtil.messageToXml(textMessage);
    }

    /***
     * 获取默认订阅信息xml
     */
    private String getSubscribeRespXml(String fromUserName,String toUserName)
    {
        TextMessage textMessage = new TextMessage();
        textMessage.setToUserName(fromUserName);
        textMessage.setFromUserName(toUserName);
        textMessage.setCreateTime(new Date().getTime());
        textMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);
        String respMsg = getAutoReplySubscribeMsg();
        textMessage.setContent(respMsg);
        return MessageUtil.messageToXml(textMessage);
    }

    /***
     * 解析用户发送的文本消息
     * @param content 发送内容
     * @param fromUserName 发送方账号
     * @param toUserName 公众号
     */
    private String analysisTextMsg(String content, String fromUserName, String toUserName )
    {
        logger.debug( "开始解析用户输入【" + content + "】" );
        return getDefaultRespXml( fromUserName, toUserName );
    }

    /***
     * 获取微信订阅回复
     */
    private String getAutoReplySubscribeMsg()
    {
        return "";
    }

    /**
     * 获取微信回复默认消息
     */
    private String getAutoReplayDefaultMsg()
    {
        return "";
    }

    /***
     * 获取微信关键字回复信息
     * @param content 用户输入的内容
     */
    private String getKeyWordRespXml(String fromUserName,String toUserName,String content)
    {
        return "";
    }
}