package com.imory.cn.utils;

import com.imory.cn.wx.resp.*;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.core.util.QuickWriter;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.xml.PrettyPrintWriter;
import com.thoughtworks.xstream.io.xml.XppDriver;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MessageUtil {
    //请求消息类型：文本
	public static final String REQ_MESSAGE_TYPE_TEXT = "text";
    //请求消息类型：图片
	public static final String REQ_MESSAGE_TYPE_IMAGE = "image";
    //请求消息类型：语音
	public static final String REQ_MESSAGE_TYPE_VOICE = "voice";
    //请求消息类型：视频
	public static final String REQ_MESSAGE_TYPE_VIDEO = "video";
    //请求消息类型：地理位置
	public static final String REQ_MESSAGE_TYPE_LOCATION = "location";
    //请求消息类型：链接
	public static final String REQ_MESSAGE_TYPE_LINK = "link";
    //请求消息类型：事件推送
	public static final String REQ_MESSAGE_TYPE_EVENT = "event";

    //事件类型：订阅
	public static final String EVENT_TYPE_SUBSCRIBE = "subscribe";
    //事件类型：取消订阅
	public static final String EVENT_TYPE_UNSUBSCRIBE = "unsubscribe";
    //事件类型：关注用户扫描带参数二维码
	public static final String EVENT_TYPE_SCAN = "scan";
    //事件类型：上报地理位置
	public static final String EVENT_TYPE_LOCATION = "LOCATION";
    //事件类型：自定义菜单
	public static final String EVENT_TYPE_CLICK = "CLICK";
    //事件类型：发送模板事件结果
	public static final String EVENT_TYPE_TEMPLATESENDJOBFINISH = "TEMPLATESENDJOBFINISH";

    //微信小店付款通知
    public static final String EVENT_TYPE_MERCHANT_ORDER = "merchant_order";

    //响应消息类型：文本
	public static final String RESP_MESSAGE_TYPE_TEXT = "text";
    //响应消息类型：图片
	public static final String RESP_MESSAGE_TYPE_IMAGE = "image";
    //响应消息类型：语音
	public static final String RESP_MESSAGE_TYPE_VOICE = "voice";
    //响应消息类型：视频
	public static final String RESP_MESSAGE_TYPE_VIDEO = "video";
    //响应消息类型：音乐
	public static final String RESP_MESSAGE_TYPE_MUSIC = "music";
    //响应消息类型：新闻(图文消息)
	public static final String RESP_MESSAGE_TYPE_NEWS = "news";

    //转发到多客服
	public static final String TRANSFER_CUSTOMER_SERVICE = "transfer_customer_service";

    /**
     * 解析微信发来的请求（XML）
     * @param request
     * @return
     * @throws Exception
     */
	public static Map<String, String> parseXml(HttpServletRequest request) throws Exception {
		Map<String, String> map = new HashMap<String, String>();

		InputStream inputStream = request.getInputStream();
		SAXReader reader = new SAXReader();
		Document document = reader.read(inputStream);
		Element root = document.getRootElement();
		List<Element> elementList = root.elements();

		for (Element e : elementList)
			map.put(e.getName(), e.getText());

		inputStream.close();
		inputStream = null;

		return map;
	}

    /**
     * 解析微信发来的请求（XML）
     * @param _repXml 解密后的xml
     * @return
     * @throws Exception
     */
    public static Map<String, String> parseXml(String _repXml) throws Exception {
        Map<String, String> map = new HashMap<String, String>();

        SAXReader reader = new SAXReader();
        Document document = reader.read(new ByteArrayInputStream(_repXml.getBytes("UTF-8")));
        Element root = document.getRootElement();
        List<Element> elementList = root.elements();

        for (Element e : elementList)
            map.put(e.getName(), e.getText());


        return map;
    }

    /***
     * 扩展xstream使其支持CDATA
     */
	private static XStream xstream = new XStream(new XppDriver() {
		public HierarchicalStreamWriter createWriter(Writer out) {
			return new PrettyPrintWriter(out) {
				boolean cdata = true;

				@SuppressWarnings("unchecked")
				public void startNode(String name, Class clazz) {
					super.startNode(name, clazz);
				}

				protected void writeText(QuickWriter writer, String text) {
					if (cdata) {
						writer.write("<![CDATA[");
						writer.write(text);
						writer.write("]]>");
					} else {
						writer.write(text);
					}
				}
			};
		}
	});

    /***
     * 文本消息对象转换为XML
     * @param textMessage
     * @return
     */
	public static String messageToXml(TextMessage textMessage) {
		xstream.alias("xml", textMessage.getClass());
		return xstream.toXML(textMessage);
	}

    /**
     * 图片消息对象转换为XML
     * @param imageMessage
     * @return
     */
	public static String messageToXml(ImageMessage imageMessage) {
		xstream.alias("xml", imageMessage.getClass());
		return xstream.toXML(imageMessage);
	}

    /**
     * 语音消息对象转换为XML
     * @param voiceMessage
     * @return
     */
	public static String messageToXml(VoiceMessage voiceMessage) {
		xstream.alias("xml", voiceMessage.getClass());
		return xstream.toXML(voiceMessage);
	}

    /***
     * 视频消息对象转换为XML
     * @param videoMessage
     * @return
     */
	public static String messageToXml(VideoMessage videoMessage) {
		xstream.alias("xml", videoMessage.getClass());
		return xstream.toXML(videoMessage);
	}

    /***
     * 音乐消息对象转换为XML
     * @param musicMessage
     * @return
     */
	public static String messageToXml(MusicMessage musicMessage) {
		xstream.alias("xml", musicMessage.getClass());
		return xstream.toXML(musicMessage);
	}

    /***
     * 新闻消息对象转换为XML
     * @param newsMessage
     * @return
     */
	public static String messageToXml(NewsMessage newsMessage) {
		xstream.alias("xml", newsMessage.getClass());
		xstream.alias("item", new Article().getClass());
		return xstream.toXML(newsMessage);
	}
}
