package com.imory.cn.wx;

/**
 * 图片消息
 */
public class ImageMessage extends com.imory.cn.wx.resp.BaseMessage {
    //图片
	private com.imory.cn.wx.resp.Image Image;

	public com.imory.cn.wx.resp.Image getImage() {
		return Image;
	}

	public void setImage(com.imory.cn.wx.resp.Image image) {
		Image = image;
	}
}
