package com.imory.cn.utils;


import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;


/**
 * <p>微信OAuth相关工具类</p>
 *
 * @author Liyong He
 * @version 1.0
 */
public class WechatOAuthUtils {

    private static Logger logger = Logger.getLogger(WechatOAuthUtils.class);

    /**
     * 生成微信OAuth认证链接
     *
     * @param uri                需要生成的地址，该地址不包含http://site:port部分
     * @param httpServletRequest 当前页面的request
     * @param autoDetect         是否判断在微信浏览器内，如果为true，那么如果发现不在微信浏览器中，则不生成微信OAuth链接，否则无论如何都生成
     * @param requirdFollow      生成的链接，是否要求必须关注微信号后才能打开，如果为true，则将使用snsapi_base方式，该方式如果用户未关注当前微信号，则报错，否则，使用snsapi_userinfo方式
     * @return 可直接使用的link地址
     */
    public static String changeIntoOAuthURL(String appId,String uri, HttpServletRequest httpServletRequest,
                                            boolean autoDetect, boolean requirdFollow) {
        if (!autoDetect || WebUtils.isInWechatBrowser(httpServletRequest)) {

            String url = WebUtils.getHostStr( httpServletRequest ) + uri;
            String scope = requirdFollow ? "snsapi_base" : "snsapi_userinfo";
            String state = "imory";

            try
            {
                StringBuilder sb = new StringBuilder();
                sb.append( "https://open.weixin.qq.com/connect/oauth2/authorize?appid=" )
                        .append( appId )
                        .append( "&redirect_uri=" )
                        .append( URLEncoder.encode( url, "utf-8" ) )
                        .append( "&response_type=code&scope=" )
                        .append( scope )
                        .append( "&state=" )
                        .append( state )
                        .append( "#wechat_redirect" );
                return sb.toString();
            }
            catch( UnsupportedEncodingException e )
            {
                logger.error( "URL编码失败", e );
                return uri;
            }
        } else {
            return uri;
        }
    }

    public static String changeIntoOAuthURL(String appId,String fullpath) {
        String state = "imory";
        try
        {
            StringBuilder sb = new StringBuilder();
            sb.append( "https://open.weixin.qq.com/connect/oauth2/authorize?appid=" )
                    .append( appId )
                    .append( "&redirect_uri=" )
                    .append( URLEncoder.encode( fullpath, "utf-8" ) )
                    .append( "&response_type=code&scope=" )
                    .append( "snsapi_base" )
                    .append( "&state=" )
                    .append( state )
                    .append( "#wechat_redirect" );
            return sb.toString();
        }
        catch( UnsupportedEncodingException e )
        {
            logger.error( "URL编码失败", e );
        }
        return "";
    }
}
