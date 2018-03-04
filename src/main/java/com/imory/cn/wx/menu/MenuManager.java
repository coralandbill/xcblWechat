package com.imory.cn.wx.menu;

import com.imory.cn.utils.CommonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MenuManager {
	private static Logger log = LoggerFactory.getLogger(MenuManager.class);

	/**
	 * 定义菜单结构
	 * 
	 * @return
	 */
	private static Menu getMenu() {
		ClickButton btn11 = new ClickButton();
		btn11.setName("开源中国");
		btn11.setType("click");
		btn11.setKey("oschina");

		ClickButton btn12 = new ClickButton();
		btn12.setName("ITeye");
		btn12.setType("click");
		btn12.setKey("iteye");

		ViewButton btn13 = new ViewButton();
		btn13.setName("CocoaChina");
		btn13.setType("view");
		btn13.setUrl("http://www.iteye.com");

		ViewButton btn21 = new ViewButton();
		btn21.setName("淘宝");
		btn21.setType("view");
		btn21.setUrl("http://m.taobao.com");

		ViewButton btn22 = new ViewButton();
		btn22.setName("京东");
		btn22.setType("view");
		btn22.setUrl("http://m.jd.com");

		ViewButton btn23 = new ViewButton();
		btn23.setName("唯品会");
		btn23.setType("view");
		btn23.setUrl("http://m.vipshop.com");

		ViewButton btn24 = new ViewButton();
		btn24.setName("当当网");
		btn24.setType("view");
		btn24.setUrl("http://m.dangdang.com");

		ViewButton btn25 = new ViewButton();
		btn25.setName("苏宁易购");
		btn25.setType("view");
		btn25.setUrl("http://m.suning.com");

		ViewButton btn31 = new ViewButton();
		btn31.setName("多泡");
		btn31.setType("view");
		btn31.setUrl("http://www.duopao.com");

		ViewButton btn32 = new ViewButton();
		btn32.setName("一窝88");
		btn32.setType("view");
		btn32.setUrl("http://www.yi588.com");

		ComplexButton mainBtn1 = new ComplexButton();
		mainBtn1.setName("技术交流");
		mainBtn1.setSub_button(new Button[] { btn11, btn12, btn13 });

		ComplexButton mainBtn2 = new ComplexButton();
		mainBtn2.setName("购物");
		mainBtn2.setSub_button(new Button[] { btn21, btn22, btn23, btn24, btn25 });

		ComplexButton mainBtn3 = new ComplexButton();
		mainBtn3.setName("网页游戏");
		mainBtn3.setSub_button(new Button[] { btn31, btn32 });

		Menu menu = new Menu();
		menu.setButton(new Button[] { mainBtn1, mainBtn2, mainBtn3 });

		return menu;
	}

	public static void main(String[] args) {
		String appId = "wx14d509dca2e81024";
		String appSecret = "5cac92baf10bfeccd248749fddba7860 ";

		Token token = CommonUtil.getToken(appId, appSecret);

        String jsonArr = "{\n" +
                "    \"button\": [\n" +
                "        {\n" +
                "            \"name\": \"扫码\", \n" +
                "            \"sub_button\": [\n" +
                "                {\n" +
                "                    \"type\": \"scancode_waitmsg\", \n" +
                "                    \"name\": \"扫码带提示\", \n" +
                "                    \"key\": \"rselfmenu_0_0\", \n" +
                "                    \"sub_button\": [ ]\n" +
                "                }, \n" +
                "                {\n" +
                "                    \"type\": \"scancode_push\", \n" +
                "                    \"name\": \"扫码推事件\", \n" +
                "                    \"key\": \"rselfmenu_0_1\", \n" +
                "                    \"sub_button\": [ ]\n" +
                "                }\n" +
                "            ]\n" +
                "        }, \n" +
                "        {\n" +
                "            \"name\": \"发图\", \n" +
                "            \"sub_button\": [\n" +
                "                {\n" +
                "                    \"type\": \"pic_sysphoto\", \n" +
                "                    \"name\": \"系统拍照发图\", \n" +
                "                    \"key\": \"rselfmenu_1_0\", \n" +
                "                   \"sub_button\": [ ]\n" +
                "                 }, \n" +
                "                {\n" +
                "                    \"type\": \"pic_photo_or_album\", \n" +
                "                    \"name\": \"拍照或者相册发图\", \n" +
                "                    \"key\": \"rselfmenu_1_1\", \n" +
                "                    \"sub_button\": [ ]\n" +
                "                }, \n" +
                "                {\n" +
                "                    \"type\": \"pic_weixin\", \n" +
                "                    \"name\": \"微信相册发图\", \n" +
                "                    \"key\": \"rselfmenu_1_2\", \n" +
                "                    \"sub_button\": [ ]\n" +
                "                }\n" +
                "            ]\n" +
                "        }, \n" +
                "        {\n" +
                "            \"name\": \"发送位置\", \n" +
                "            \"type\": \"location_select\", \n" +
                "            \"key\": \"rselfmenu_2_0\"\n" +
                "        },\n" +
                "        {\n" +
                "           \"type\": \"media_id\", \n" +
                "           \"name\": \"图片\", \n" +
                "           \"media_id\": \"MEDIA_ID1\"\n" +
                "        }, \n" +
                "        {\n" +
                "           \"type\": \"view_limited\", \n" +
                "           \"name\": \"图文消息\", \n" +
                "           \"media_id\": \"MEDIA_ID2\"\n" +
                "        }\n" +
                "    ]\n" +
                "}";

		if (null != token) {
			//boolean result = MenuUtil.createMenu(getMenu(), token.getAccessToken());
			boolean result = MenuUtil.createMenu(jsonArr, token.getAccessToken());

			if (result)
            {
                System.out.println("菜单创建成功!");
                log.info("菜单创建成功！");
            }
			else
            {
                log.info("菜单创建失败！");
                System.out.println("菜单创建失败!");
            }
		}
	}
}
