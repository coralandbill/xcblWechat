package com.imory.cn.wx;

import com.imory.cn.utils.MessageUtil;

import javax.servlet.http.HttpServletRequest;

import com.imory.cn.wx.resp.TextMessage;

import java.util.Date;
import java.util.Map;

/**
 * 核心服务类
 */
@Deprecated
public class CoreService {
    /**
     * 处理微信发来的请求
     *
     * @param request
     * @return
     */
    public static String processRequest(HttpServletRequest request) {
        String respXml = null;
        //默认返回的文本消息内容
        String respContent = "未知的消息类型！";
        try {
            //解析请求XML
            Map<String, String> requestMap = MessageUtil.parseXml(request);
            //获取发送方账号（用户）
            String fromUserName = requestMap.get("FromUserName");
            //开发者微信号
            String toUserName = requestMap.get("ToUserName");
            //消息类型
            String msgType = requestMap.get("MsgType");
            //内容
            String content = requestMap.get("Content");

            if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_TEXT)) {
                TextMessage textMessage = new TextMessage();
                textMessage.setToUserName(fromUserName);
                textMessage.setFromUserName(toUserName);
                textMessage.setCreateTime(new Date().getTime());
                textMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);
                //respContent = "您发送的是文本消息！";
                respXml = MessageUtil.messageToXml(textMessage);
            } else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_IMAGE)) {
                respContent = "您发送的是图片消息！";
            } else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_VOICE)) {
                respContent = "您发送的是视频消息！";
            } else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_VIDEO)) {
                respContent = "您发送的是音频消息！";
            } else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_LOCATION)) {
                respContent = "您发送的是地理位置消息！";
            } else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_LINK)) {
                respContent = "您发送的是链接消息！";
            } else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_EVENT)) {
                String eventType = requestMap.get("Event");
                if (eventType.equals(MessageUtil.EVENT_TYPE_SUBSCRIBE)) {
                    respContent = "谢谢您的关注";
                } else if (eventType.equals(MessageUtil.EVENT_TYPE_UNSUBSCRIBE)) {
                    // TODO 取消订阅后，用户不会再受到公众账号发送的消息
                } else if (eventType.equals(MessageUtil.EVENT_TYPE_SCAN)) {

                    // TODO 处理扫描带参数二维码事件
                } else if (eventType.equals(MessageUtil.EVENT_TYPE_LOCATION)) {
                    // TODO 处理上报地理位置事件
                } else if (eventType.equals(MessageUtil.EVENT_TYPE_CLICK)) {
                    // TODO 处理菜单点击事件
                }
            }
            //textMessage.setContent(respContent);
            //respXml = MessageUtil.messageToXml(textMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return respXml;
    }

}
