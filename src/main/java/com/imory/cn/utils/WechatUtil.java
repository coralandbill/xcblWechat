package com.imory.cn.utils;

import com.imory.cn.exception.ServiceException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.httpclient.*;
import org.apache.commons.httpclient.methods.*;
import org.apache.commons.httpclient.params.HttpMethodParams;

/**
 * <p>微信工具类</p>
 *
 * @author Liyong He
 * @version 1.0
 */
public class WechatUtil {
    /**
     * ACCESS_TOKEN过期时间
     */
    private static long ACCESS_TOKEN_EXPIRE_TIME = 0;

    /**
     * 当前的ACCESS_TOKEN
     */
    private static String ACCESS_TOKEN;

    /**
     * 刷新ACCESS_TOKEN时的线程锁
     */
    private static final String ACCESS_TOKEN_LOCK = "ACCESS_TOKEN";

    /**
     * JSP_TICKET过期时间
     */
    private static long JSP_TICKET_EXPIRE_TIME = 0;

    /**
     * 缓存的JSP_TICKET
     */
    private static String JSP_TICKET;

    /**
     * JSP_TICKET线程所
     */
    private static final String JSP_TICKET_LOCK = "JSP_TICKET";

    /**
     * 获得OAuth业务的用户授权信息
     *
     * @param code   code码
     * @param appId  微信appid
     * @param secret 微信app密钥
     * @return 用户授权信息
     * @throws ServiceException 调用微信接口失败
     */
    public static Map<String, Object> getOAuthOpenId(String code, String appId, String secret) throws ServiceException {
        HttpClient httpClient = new HttpClient();
        NameValuePair[] nameValuePairs = new NameValuePair[4];
        nameValuePairs[0] = new NameValuePair("appid", appId);
        nameValuePairs[1] = new NameValuePair("secret", secret);
        nameValuePairs[2] = new NameValuePair("code", code);
        nameValuePairs[3] = new NameValuePair("grant_type", "authorization_code");
        GetMethod method = new GetMethod("https://api.weixin.qq.com/sns/oauth2/access_token");
        method.setQueryString(nameValuePairs);
        HttpMethodParams param = method.getParams();
        param.setContentCharset("UTF-8");
        try {
            httpClient.executeMethod(method);
            String returnMsg = method.getResponseBodyAsString();
            JSONObject jsonObject = new JSONObject(returnMsg);
            if (jsonObject.has("access_token")) {
                // 表示成功
                Map<String, Object> oauthInfo = new HashMap<>();
                oauthInfo.put("access_token", jsonObject.get("access_token"));
                oauthInfo.put("openid", jsonObject.get("openid"));
                return oauthInfo;
            } else {
                // 访问失败
                int errCode = jsonObject.getInt("errcode");
                String errMsg = jsonObject.getString("errmsg");
                throw new ServiceException(errCode, errMsg);
            }
        } catch (IOException e) {
            throw new ServiceException(-9999, e.getMessage(), e);
        }
    }

    /**
     * 获得公众号的access_token
     *
     * @param appId  微信appid
     * @param secret 微信app密钥
     * @return access_token
     */
    public static String getAccess_Token(String appId, String secret) throws ServiceException {
        return "";
    }

    /**
     * 获得JSP_TICKET
     *
     * @param appId  微信应用ID
     * @param secret 微信密钥
     * @return JSP访问的TICKET
     */
    public static String getJspTicket(String appId, String secret) {
        long now = System.currentTimeMillis();

        if (now > JSP_TICKET_EXPIRE_TIME) {
            synchronized (JSP_TICKET_LOCK) {
                HttpClient httpClient = new HttpClient();
                NameValuePair[] nameValuePairs = new NameValuePair[2];
                nameValuePairs[0] = new NameValuePair("access_token", getAccess_Token(appId, secret));
                nameValuePairs[1] = new NameValuePair("type", "jsapi");
                GetMethod method = new GetMethod("https://api.weixin.qq.com/cgi-bin/ticket/getticket");
                method.setQueryString(nameValuePairs);
                HttpMethodParams param = method.getParams();
                param.setContentCharset("UTF-8");
                try {
                    httpClient.executeMethod(method);
                    String returnMsg = method.getResponseBodyAsString();
                    JSONObject jsonObject = new JSONObject(returnMsg);
                    Integer returnCode = jsonObject.getInt("errcode");
                    if (returnCode != 0) {
                        throw new ServiceException(-9998, "获取JSPTicket失败[" + jsonObject.getString("errmsg"));
                    } else {
                        JSP_TICKET = jsonObject.getString("ticket");
                        JSP_TICKET_EXPIRE_TIME = now + 7000 * 1000; // JSP_TICKET过期时间为7200s，本处处理为7000s
                        return JSP_TICKET;
                    }
                } catch (IOException e) {
                    throw new ServiceException(-9999, e.getMessage(), e);
                }
            }
        } else {
            return JSP_TICKET;
        }
    }

    /**
     * 获得用户微信信息
     *
     * @param appId  微信appid
     * @param secret 微信app密钥
     * @param openId 用户的openId
     * @return
     */
    public static Map<String, Object> getUserInfo(String appId, String secret, String openId) {
        HttpClient httpClient = new HttpClient();
        NameValuePair[] nameValuePairs = new NameValuePair[2];
        nameValuePairs[0] = new NameValuePair("access_token", getAccess_Token(appId, secret));
        nameValuePairs[1] = new NameValuePair("openid", openId);
        GetMethod method = new GetMethod("https://api.weixin.qq.com/cgi-bin/user/info");
        method.setQueryString(nameValuePairs);
        HttpMethodParams param = method.getParams();
        param.setContentCharset("UTF-8");

        try {
            httpClient.executeMethod(method);
            String returnMsg = method.getResponseBodyAsString();
            System.out.println(returnMsg);
            JSONObject jsonObject = new JSONObject(returnMsg);
            if (jsonObject.has("errcode")) {
                throw new ServiceException(-9999, "获取用户信息失败[" + jsonObject.getInt("errcode") + "|" + jsonObject.getString("errmsg") + "]");
            } else {
                Map<String, Object> userInfo = new HashMap<>();
                Iterator<String> keys = jsonObject.keys();
                while (keys.hasNext()) {
                    String key = keys.next();
                    if ("nickname".equals(key)) {
                        String nickName = (String) jsonObject.get(key);
                        String value = "";
                        for (int i = 0; i < nickName.length(); i++) {
                            char c = nickName.charAt(i);
                            if (notEmojiCharacter(c))
                                value += c;
                        }
                        userInfo.put(key, value);
                    } else
                        userInfo.put(key, jsonObject.get(key));
                }
                return userInfo;
            }
        } catch (IOException e) {
            throw new ServiceException(-9999, "获取用户信息失败[" + e.getMessage() + "]", e);
        }
    }

    /**
     * 网页授权方式获得用户微信信息
     *
     * @param openId            微信openid
     * @param temp_access_token 微信授权临时access_token
     * @return
     */
    public static Map<String, Object> getUserInfoWithOAuth(String openId, String temp_access_token) {
        HttpClient httpClient = new HttpClient();
        NameValuePair[] nameValuePairs = new NameValuePair[3];
        nameValuePairs[0] = new NameValuePair("access_token", temp_access_token);
        nameValuePairs[1] = new NameValuePair("openid", openId);
        nameValuePairs[2] = new NameValuePair("lang", "zh_CN");
        GetMethod method = new GetMethod("https://api.weixin.qq.com/sns/userinfo");
        method.setQueryString(nameValuePairs);
        HttpMethodParams param = method.getParams();
        param.setContentCharset("UTF-8");

        try {
            httpClient.executeMethod(method);
            String returnMsg = method.getResponseBodyAsString();
            System.out.println(returnMsg);
            JSONObject jsonObject = new JSONObject(returnMsg);
            if (jsonObject.has("errcode")) {
                throw new ServiceException(-9999, "获取用户信息失败[" + jsonObject.getInt("errcode") + "|" + jsonObject.getString("errmsg") + "]");
            } else {
                Map<String, Object> userInfo = new HashMap<>();
                Iterator<String> keys = jsonObject.keys();
                while (keys.hasNext()) {
                    String key = keys.next();
                    if ("nickname".equals(key)) {
                        String nickName = (String) jsonObject.get(key);
                        String value = "";
                        for (int i = 0; i < nickName.length(); i++) {
                            char c = nickName.charAt(i);
                            if (notEmojiCharacter(c))
                                value += c;
                        }
                        userInfo.put(key, value);
                    } else
                        userInfo.put(key, jsonObject.get(key));
                }
                return userInfo;
            }
        } catch (IOException e) {
            throw new ServiceException(-9999, "获取用户信息失败[" + e.getMessage() + "]", e);
        }
    }

    /**
     * 下载图片文件
     *
     * @param appId   微信appid
     * @param secret  微信app密钥
     * @param mediaId 多媒体文件ID，由微信返回
     */
    public static InputStream downloadImageMedia(String appId, String secret, String mediaId) {
        HttpClient httpClient = new HttpClient();
        NameValuePair[] nameValuePairs = new NameValuePair[2];
        nameValuePairs[0] = new NameValuePair("access_token", getAccess_Token(appId, secret));
        nameValuePairs[1] = new NameValuePair("media_id", mediaId);
        GetMethod method = new GetMethod("http://file.api.weixin.qq.com/cgi-bin/media/get");
        method.setQueryString(nameValuePairs);
        HttpMethodParams param = method.getParams();
        param.setContentCharset("UTF-8");
        try {
            httpClient.executeMethod(method);
            if ("image/jpeg".equals(method.getResponseHeader("Content-Type").getValue()))
                return method.getResponseBodyAsStream();
            else {
                String returnMsg = method.getResponseBodyAsString();
                JSONObject jsonObject = new JSONObject(returnMsg);
                if (jsonObject.has("errcode")) {
                    throw new ServiceException(-9998, "获取图片失败[" + jsonObject.getInt("errcode") + "|" + jsonObject.getString("errmsg") + "]");
                }
            }
        } catch (IOException e) {
            throw new ServiceException(-9999, "获取图片失败[" + e.getMessage() + "]", e);
        }
        return null;
    }

    /**
     * 获取微信素材列表信息
     *
     * @param type   素材类型 图片（image）、视频（video）、语音 （voice）、图文（news）
     * @param offset 从全部素材的该偏移位置开始返回，0表示从第一个素材 返回
     * @param count  返回素材的数量，取值在1到20之间
     * @return
     */
    public static String getMaterialList(String token, String type, int offset, int count) {
        String url = "https://api.weixin.qq.com/cgi-bin/material/batchget_material?access_token=" + token;

        String query = "{\n" +
                "    \"type\":\"" + type + "\",\n" +
                "    \"offset\":\"" + offset + "\",\n" +
                "    \"count\":\"" + count + "\"\n" +
                "}";
        HttpClient client = new HttpClient();
        PostMethod post = new PostMethod(url);
        post.setRequestHeader("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10.9; rv:30.0) Gecko/20100101 Firefox/30.0");
        post.setRequestHeader("Connection", "Keep-Alive");
        post.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "utf-8");
        post.setRequestHeader("Cache-Control", "no-cache");
        try {
            post.setRequestBody(query);
            int status = client.executeMethod(post);
            if (status == org.apache.http.HttpStatus.SC_OK) {
                String responseContent = post.getResponseBodyAsString();
                return responseContent;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取微信素材信息
     *
     * @param mediaId 素材meidia_id
     * @return
     */
    public static String getMaterialInfo(String token, String mediaId) {
        String url = "https://api.weixin.qq.com/cgi-bin/material/get_material?access_token=" + token;

        String query = "{\n" +
                "\"media_id\":\"" + mediaId + "\"\n" +
                "}";
        HttpClient client = new HttpClient();
        PostMethod post = new PostMethod(url);
        post.setRequestHeader("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10.9; rv:30.0) Gecko/20100101 Firefox/30.0");
        post.setRequestHeader("Connection", "Keep-Alive");
        post.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "UTF-8");
        post.setRequestHeader("Cache-Control", "no-cache");
        try {
            post.setRequestBody(query);
            int status = client.executeMethod(post);
            if (status == org.apache.http.HttpStatus.SC_OK) {
                String responseContent = post.getResponseBodyAsString();
                return responseContent;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取微信素材信息
     *
     * @param mediaId 素材meidia_id
     * @return
     */
    public static InputStream getStreamMaterialInfo(String token, String mediaId) {
        String url = "https://api.weixin.qq.com/cgi-bin/material/get_material?access_token=" + token;

        String query = "{\n" +
                "\"media_id\":\"" + mediaId + "\"\n" +
                "}";
        HttpClient client = new HttpClient();
        PostMethod post = new PostMethod(url);
        post.setRequestHeader("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10.9; rv:30.0) Gecko/20100101 Firefox/30.0");
        post.setRequestHeader("Connection", "Keep-Alive");
        post.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "UTF-8");
        post.setRequestHeader("Cache-Control", "no-cache");
        try {
            post.setRequestBody(query);
            int status = client.executeMethod(post);
            if (status == org.apache.http.HttpStatus.SC_OK) {
                InputStream inputStream = post.getResponseBodyAsStream();
                return inputStream;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 创建客服会话
     *
     * @param token
     * @param kf_account 完整客服账号，格式为：账号前缀@公众号微信号
     * @param openid     客户openid
     * @param type       type=1喜报和路演资料 type=2 研究报告
     * @return
     */
    public static String createKfSession(String token, String kf_account, String openid, String msg, int type) {
        String url = "https://api.weixin.qq.com/customservice/kfsession/create?access_token=" + token;

        String query = "{\n" +
                "    \"kf_account\" : \"" + kf_account + "\",\n" +
                "    \"openid\" : \"" + openid + "\",\n" +
                "    \"text\" : \"" + msg + "\"\n" +
                " }";
        HttpClient client = new HttpClient();
        PostMethod post = new PostMethod(url);
        post.setRequestHeader("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10.9; rv:30.0) Gecko/20100101 Firefox/30.0");
        post.setRequestHeader("Connection", "Keep-Alive");
        post.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "UTF-8");
        post.setRequestHeader("Cache-Control", "no-cache");
        try {
            post.setRequestBody(query);
            int status = client.executeMethod(post);
            if (status == org.apache.http.HttpStatus.SC_OK) {
                String responseContent = post.getResponseBodyAsString();
                return responseContent;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取微信在线客服
     *
     * @param token 微信token
     * @return access_token
     */
    public static String getWechatOnlineKf(String token) throws ServiceException {
        HttpClient httpClient = new HttpClient();
        NameValuePair[] nameValuePairs = new NameValuePair[1];
        nameValuePairs[0] = new NameValuePair("access_token", token);
        GetMethod method = new GetMethod("https://api.weixin.qq.com/cgi-bin/customservice/getonlinekflist?access_token=" + token);
        method.setQueryString(nameValuePairs);
        HttpMethodParams param = method.getParams();
        param.setContentCharset("UTF-8");
        try {
            httpClient.executeMethod(method);
            String returnMsg = method.getResponseBodyAsString();
            return returnMsg;
        } catch (IOException e) {
            throw new ServiceException(-9999, e.getMessage(), e);
        }

    }

    /**
     * 获得公众号的自动回复
     *
     * @param token 微信token
     * @return access_token
     */
    public static JSONObject getWechatReply(String token) throws ServiceException {
        long now = System.currentTimeMillis();
        if (now > ACCESS_TOKEN_EXPIRE_TIME) {
            // 需要刷新access_token
            HttpClient httpClient = new HttpClient();
            NameValuePair[] nameValuePairs = new NameValuePair[1];
            nameValuePairs[0] = new NameValuePair("access_token", token);
            GetMethod method = new GetMethod("https://api.weixin.qq.com/cgi-bin/get_current_autoreply_info");
            method.setQueryString(nameValuePairs);
            HttpMethodParams param = method.getParams();
            param.setContentCharset("UTF-8");
            try {
                httpClient.executeMethod(method);
                String returnMsg = method.getResponseBodyAsString();
                JSONObject jsonObject = new JSONObject(returnMsg);
                return jsonObject;
            } catch (IOException e) {
                throw new ServiceException(-9999, e.getMessage(), e);
            }
        }

        return null;
    }

    /**
     * 创建菜单
     *
     * @param jsonStr     菜单json数据
     * @param accessToken 凭证
     * @return true创建成功 false创建失败
     */
    public static boolean createMenu(String jsonStr, String accessToken) {
        boolean result = false;
        String menu_create_url = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN";
        String url = menu_create_url.replace("ACCESS_TOKEN", accessToken);
        //发起post请求创建菜单
        JSONObject jsonObject = CommonUtil.httpsRequest(url, "POST", jsonStr);

        if (null != jsonObject) {
            int errorCode = jsonObject.getInt("errcode");
            //String errorMsg = jsonObject.getString("errmsg");
            if (0 == errorCode) {
                result = true;
            } else {
                result = false;
            }
        }

        return result;
    }

    public static void sendTemplateMsg(String appId, String secret, String templateId, String url, String openId, JSONObject data) {
        HttpClient httpClient = new HttpClient();

        JSONObject postData = new JSONObject();
        postData.put("touser", openId);
        postData.put("template_id", templateId);
        postData.put("url", url);
        postData.put("topcolor", "#FF0000");
        postData.put("data", data);

        /*StringEntity se = new StringEntity( postData.toString() );
        se.setContentEncoding( "UTF-8" );
        se.setContentType("application/json");

        HttpPost postMethod = new HttpPost( "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token="+getAccess_Token( appId, secret ) );
        postMethod.setEntity( se );*/


        try {
            PostMethod method = new PostMethod("https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=" + getAccess_Token(appId, secret));
            StringRequestEntity sre = new StringRequestEntity(postData.toString(), "application/json", "utf-8");
            method.setRequestEntity(sre);
            httpClient.executeMethod(method);

            String returnMsg = method.getResponseBodyAsString();
            JSONObject jsonObject = new JSONObject(returnMsg);
            Integer returnCode = jsonObject.getInt("errcode");
            if (returnCode != 0) {
                throw new ServiceException(-9998, "发送模板消息失败[" + jsonObject.get("errmsg") + "]");
            }
        } catch (IOException e) {
            throw new ServiceException(-9999, "发送模板消息时发生错误[" + e.getMessage() + "]", e);
        }
    }


    /**
     * 微信用户分组
     *
     * @param groupId 分组id
     * @param openId  openId
     * @param token   调用接口凭证
     */
    public static String userWechatGropu(String token, Integer groupId, String openId) {
        String url = "https://api.weixin.qq.com/cgi-bin/groups/members/update?access_token=" + token;

        String query = "{\n" +
                "    \"openid\":\"" + openId + "\",\n" +
                "    \"to_groupid\":\"" + groupId + "\"\n" +
                "}";
        HttpClient client = new HttpClient();
        PostMethod post = new PostMethod(url);
        post.setRequestHeader("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10.9; rv:30.0) Gecko/20100101 Firefox/30.0");
        post.setRequestHeader("Connection", "Keep-Alive");
        post.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "utf-8");
        post.setRequestHeader("Cache-Control", "no-cache");
        try {
            post.setRequestBody(query);
            int status = client.executeMethod(post);
            if (status == org.apache.http.HttpStatus.SC_OK) {
                String responseContent = post.getResponseBodyAsString();
                return responseContent;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取微信素材列表信息
     *
     * @param long_url 需要转换的长链接，支持http://、https://、weixin://wxpay 格式的url
     * @return
     */
    public static String getShortUrl(String token, String long_url) {
        String url = "https://api.weixin.qq.com/cgi-bin/shorturl?access_token=" + token;

        String query = "{\n" +
                "    \"action\":\"long2short\",\n" +
                "    \"long_url\":\"" + long_url + "\"\n" +
                "}";
        HttpClient client = new HttpClient();
        PostMethod post = new PostMethod(url);
        post.setRequestHeader("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10.9; rv:30.0) Gecko/20100101 Firefox/30.0");
        post.setRequestHeader("Connection", "Keep-Alive");
        post.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "utf-8");
        post.setRequestHeader("Cache-Control", "no-cache");
        try {
            post.setRequestBody(query);
            int status = client.executeMethod(post);
            if (status == org.apache.http.HttpStatus.SC_OK) {
                String responseContent = post.getResponseBodyAsString();
                JSONObject jsonObject = new JSONObject(responseContent);
                Integer returnCode = jsonObject.optInt("errcode");
                if (returnCode != 0) {
                    throw new ServiceException(-9998, "获取短连接失败[" + jsonObject.get("errmsg") + "]");
                }
                String short_url = jsonObject.optString("short_url");
                return short_url;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 根据订单ID获取微信小店订单信息
     *
     * @param orderId 微信订单id
     * @return access_token
     * @throws com.otcqiku.common.service.excepiton.ServiceException 调用微信接口失败
     */
    public static JSONObject getOrderInfoById(String token, String orderId) throws ServiceException {
        String url = "https://api.weixin.qq.com/merchant/order/getbyid?access_token=" + token;

        String query = "{\n" +
                "    \"order_id\":\"" + orderId + "\"" +
                "}";
        HttpClient client = new HttpClient();
        PostMethod post = new PostMethod(url);
        post.setRequestHeader("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10.9; rv:30.0) Gecko/20100101 Firefox/30.0");
        post.setRequestHeader("Connection", "Keep-Alive");
        post.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "utf-8");
        post.setRequestHeader("Cache-Control", "no-cache");
        try {
            post.setRequestBody(query);
            int status = client.executeMethod(post);
            if (status == org.apache.http.HttpStatus.SC_OK) {
                String responseContent = post.getResponseBodyAsString();
                JSONObject jsonObject = new JSONObject(responseContent);
                Integer returnCode = jsonObject.optInt("errcode");
                if (returnCode != 0) {
                    throw new ServiceException(-9998, "获取微信小店订单信息失败[" + jsonObject.get("errmsg") + "]");
                }
                return jsonObject;
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 根据订单ID获取微信商品信息
     *
     * @param productId 商品id
     * @return access_token
     * @throws com.otcqiku.common.service.excepiton.ServiceException 调用微信接口失败
     */
    public static JSONObject getProductInfoById(String token, String productId) throws ServiceException {
        String url = "https://api.weixin.qq.com/merchant/get?access_token=" + token;

        String query = "{\n" +
                "    \"product_id\":\"" + productId + "\"" +
                "}";
        HttpClient client = new HttpClient();
        PostMethod post = new PostMethod(url);
        post.setRequestHeader("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10.9; rv:30.0) Gecko/20100101 Firefox/30.0");
        post.setRequestHeader("Connection", "Keep-Alive");
        post.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "utf-8");
        post.setRequestHeader("Cache-Control", "no-cache");
        try {
            post.setRequestBody(query);
            int status = client.executeMethod(post);
            if (status == org.apache.http.HttpStatus.SC_OK) {
                String responseContent = post.getResponseBodyAsString();
                JSONObject jsonObject = new JSONObject(responseContent);
                Integer returnCode = jsonObject.optInt("errcode");
                if (returnCode != 0) {
                    System.out.println("returnCode:" + returnCode);
                    throw new ServiceException(-9998, "获取微信商品信息失败[" + jsonObject.get("errmsg") + "]");
                }
                return jsonObject;
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static boolean notEmojiCharacter(char codePoint) {
        return (codePoint == 0x0) ||
                (codePoint == 0x9) ||
                (codePoint == 0xA) ||
                (codePoint == 0xD) ||
                ((codePoint >= 0x20) && (codePoint <= 0xD7FF)) ||
                ((codePoint >= 0xE000) && (codePoint <= 0xFFFD)) ||
                ((codePoint >= 0x10000) && (codePoint <= 0x10FFFF));
    }
}
