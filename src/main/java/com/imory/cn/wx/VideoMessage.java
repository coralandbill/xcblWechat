package com.imory.cn.wx;

/**
 * 视频消息
 */
public class VideoMessage extends com.imory.cn.wx.resp.BaseMessage {
    //视频
	private com.imory.cn.wx.resp.Video Video;

	public com.imory.cn.wx.resp.Video getVideo() {
		return Video;
	}

	public void setVideo(com.imory.cn.wx.resp.Video video) {
		Video = video;
	}
}
