package com.imory.cn.wx.menu;

import com.imory.cn.utils.CommonUtil;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 自定义菜单工具类
 *
 * @author liufeng
 * @date 2013-10-17
 */
public class MenuUtil {
	private static Logger log = LoggerFactory.getLogger(MenuUtil.class);

    //菜单创建（post）
	public final static String menu_create_url = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN";
    //菜单查询（GET）
	public final static String menu_get_url = "https://api.weixin.qq.com/cgi-bin/menu/get?access_token=ACCESS_TOKEN";
    //菜单删除（GET）
	public final static String menu_delete_url = "https://api.weixin.qq.com/cgi-bin/menu/delete?access_token=ACCESS_TOKEN";

	/**
	 *创建菜单
	 *
	 * @param menu 菜单实例
	 * @param accessToken 凭证
	 * @return true创建成功 false创建失败
	 */
	public static boolean createMenu(Menu menu, String accessToken) {
		boolean result = false;
		String url = menu_create_url.replace("ACCESS_TOKEN", accessToken);
        //将菜单对象转换成Json字符串
		String jsonMenu = new JSONObject(menu).toString();
        //发起post请求创建菜单
		JSONObject jsonObject = CommonUtil.httpsRequest(url, "POST", jsonMenu);

		if (null != jsonObject) {
			int errorCode = jsonObject.getInt("errcode");
			String errorMsg = jsonObject.getString("errmsg");
			if (0 == errorCode) {
				result = true;
			} else {
				result = false;
				log.error("创建菜单失败errcode:{} errmsg:{}", errorCode, errorMsg);
			}
		}

		return result;
	}

    /**
     *创建菜单
     *
     * @param jsonStr 菜单json数据
     * @param accessToken 凭证
     * @return true创建成功 false创建失败
     */
    public static boolean createMenu(String jsonStr, String accessToken) {
        boolean result = false;
        String url = menu_create_url.replace("ACCESS_TOKEN", accessToken);
        //发起post请求创建菜单
        JSONObject jsonObject = CommonUtil.httpsRequest(url, "POST", jsonStr);

        if (null != jsonObject) {
            int errorCode = jsonObject.getInt("errcode");
            String errorMsg = jsonObject.getString("errmsg");
            if (0 == errorCode) {
                result = true;
            } else {
                result = false;
                log.error("创建菜单失败errcode:{} errmsg:{}", errorCode, errorMsg);
            }
        }

        return result;
    }

	/**
	 * 查询菜单
	 *
	 * @param accessToken 凭证
	 * @return
	 */
	public static String getMenu(String accessToken) {
		String result = null;
		String requestUrl = menu_get_url.replace("ACCESS_TOKEN", accessToken);
        //发起请求查询菜单
		JSONObject jsonObject = CommonUtil.httpsRequest(requestUrl, "GET", null);

		if (null != jsonObject) {
			result = jsonObject.toString();
		}
		return result;
	}

	/**
	 *删除菜单
	 *
	 * @param accessToken 凭证
	 * @return true删除成功 false删除失败
	 */
	public static boolean deleteMenu(String accessToken) {
		boolean result = false;
		String requestUrl = menu_delete_url.replace("ACCESS_TOKEN", accessToken);
        //发起请求删除菜单
		JSONObject jsonObject = CommonUtil.httpsRequest(requestUrl, "GET", null);

		if (null != jsonObject) {
			int errorCode = jsonObject.getInt("errcode");
			String errorMsg = jsonObject.getString("errmsg");
			if (0 == errorCode) {
				result = true;
			} else {
				result = false;
				log.error("删除菜单失败errcode:{} errmsg:{}", errorCode, errorMsg);
			}
		}
		return result;
	}
}