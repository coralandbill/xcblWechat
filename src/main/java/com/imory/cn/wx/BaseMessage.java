package com.imory.cn.wx;

/***
 * 响应消息基础类
 */
public class BaseMessage {
    //发送方账号（openID）
	private String ToUserName;
    //开发者微信号
	private String FromUserName;
    //消息创建时间（整型）
	private long CreateTime;
    //消息类型（text／music／news）
	private String MsgType;

	public String getToUserName() {
		return ToUserName;
	}

	public void setToUserName(String toUserName) {
		ToUserName = toUserName;
	}

	public String getFromUserName() {
		return FromUserName;
	}

	public void setFromUserName(String fromUserName) {
		FromUserName = fromUserName;
	}

	public long getCreateTime() {
		return CreateTime;
	}

	public void setCreateTime(long createTime) {
		CreateTime = createTime;
	}

	public String getMsgType() {
		return MsgType;
	}

	public void setMsgType(String msgType) {
		MsgType = msgType;
	}
}
