package com.imory.cn.wx;

/**
 * 语音消息
 */
public class VoiceMessage extends com.imory.cn.wx.resp.BaseMessage {
    //语音
	private com.imory.cn.wx.resp.Voice Voice;

	public com.imory.cn.wx.resp.Voice getVoice() {
		return Voice;
	}

	public void setVoice(com.imory.cn.wx.resp.Voice voice) {
		Voice = voice;
	}
}
