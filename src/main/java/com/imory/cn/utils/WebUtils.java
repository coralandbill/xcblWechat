package com.imory.cn.utils;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.*;
import javax.servlet.http.*;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * web端工具类
 *
 * @author xx.liu
 * @version 1.0
 */
public class WebUtils
{
    private static String phoneReg = "\\b(ip(hone|od)|android|opera m(ob|in)i"
            + "|windows (phone|ce)|blackberry"
            + "|s(ymbian|eries60|amsung)|p(laybook|alm|rofile/midp"
            + "|laystation portable)|nokia|fennec|htc[-_]"
            + "|mobile|up.browser|[1-4][0-9]{2}x[1-4][0-9]{2})\\b";
    private static String tableReg = "\\b(ipad|tablet|(Nexus 7)|up.browser"
            + "|[1-4][0-9]{2}x[1-4][0-9]{2})\\b";

    //移动设备正则匹配：手机端、平板
    private static Pattern phonePat = Pattern.compile( phoneReg, Pattern.CASE_INSENSITIVE );
    private static Pattern tablePat = Pattern.compile( tableReg, Pattern.CASE_INSENSITIVE );

    /**
     * 根据HttpServletRequest检测是否是移动设备访问
     *
     * @return true:移动设备接入，false:pc端接入
     */
    public static boolean isMobile( HttpServletRequest request )
    {
        String userAgent = request.getHeader( "USER-AGENT" ).toLowerCase();
        Matcher matcherPhone = phonePat.matcher( userAgent );
        Matcher matcherTable = tablePat.matcher( userAgent );
        return matcherPhone.find() || matcherTable.find();
    }

    /**
     * 获得http的主机字符串，包括http://hostname:port部分
     * @param httpServletRequest 当前页面的httprequest请求
     * @return 主机字符串
     */
    public static String getHostStr( HttpServletRequest httpServletRequest )
    {
        StringBuffer fullURL = httpServletRequest.getRequestURL();
        String requestURI = httpServletRequest.getRequestURI();
        if( requestURI != null )
        {
            return fullURL.substring( 0, fullURL.length() - requestURI.length() );
        }
        else
        {
            return fullURL.toString();
        }
    }

    /**
     * 判断当前请求是否在微信浏览器中
     * @param httpServletRequest 当前页面请求
     * @return true表示在微信中
     */
    public static boolean isInWechatBrowser( HttpServletRequest httpServletRequest )
    {
        String agent = httpServletRequest.getHeader( "user-agent" );
        return agent.indexOf( "MicroMessenger" ) > 0;
    }

    public static String getShortURL( String origURL )
    {
       /* ServiceRequest request = new ServiceRequest();
        request.addRequestData( "url", origURL );
        ServiceResponse response = ServiceClientFactory.getInstance().callService( "getTinyURL", request );
        if ( response.isSuccess() )
        {
            return (String) response.getResponseData( "tinyurl" );
        }
        else
        {
            return origURL;
        }*/
       return "";
    }

    public static String handleOverflowString( String input, int maxLength, String tail ) throws UnsupportedEncodingException
    {
        String output = "";

        while ( output.getBytes( "GBK" ).length < maxLength && input.length() > 0 )
        {
            output += input.substring( 0, 1 );
            input = input.substring( 1 );
        }

        return output + ( input.length() > 0 ? tail : "" );
    }

    public static String getIpAddress( HttpServletRequest request )
    {
        String ip = request.getHeader( "X-Forwarded-For" );

        if ( StringUtils.isBlank( ip ) || "unknown".equalsIgnoreCase( ip ) )
        {
            ip = request.getHeader( "Proxy-Client-IP" );

            if ( StringUtils.isBlank( ip ) || "unknown".equalsIgnoreCase( ip ) )
            {
                ip = request.getHeader( "WL-Proxy-Client-IP" );
            }
            if ( StringUtils.isBlank( ip ) || "unknown".equalsIgnoreCase( ip ) )
            {
                ip = request.getHeader( "HTTP_CLIENT_IP" );
            }
            if ( StringUtils.isBlank( ip ) || "unknown".equalsIgnoreCase( ip ) )
            {
                ip = request.getHeader( "HTTP_X_FORWARDED_FOR" );
            }
            if ( StringUtils.isBlank( ip ) || "unknown".equalsIgnoreCase( ip ) )
            {
                ip = request.getRemoteAddr();
            }
        } else if ( ip.length() > 15 )
        {
            String[] ips = ip.split( "," );
            for ( String ip1 : ips )
            {
                if ( !( "unknown".equalsIgnoreCase( ip1 ) ) )
                {
                    ip = ip1;
                    break;
                }
            }
        }

        return ip;
    }

    public static Map parseJSON2Map( String jsonStr )
    {
        Map<String, Object> ret = new HashMap<>();

        JSONObject json = new JSONObject( jsonStr );
        for( String key : json.keySet() )
        {
            Object value = json.get( (String) key );
            if ( value instanceof JSONArray )
            {
                ret.put( key, parseJSON2List( value.toString() ) );
            }
            else if ( value instanceof JSONObject )
            {
                ret.put( key, parseJSON2Map( value.toString() ) );
            }
            else
            {
                ret.put( key, value );
            }
        }
        return ret;
    }

    public static List parseJSON2List( String jsonStr )
    {
        List<Object> ret = new ArrayList<>();

        JSONArray array = new JSONArray( jsonStr );
        for( int i = 0; i < array.length(); i++ )
        {
            Object value = array.get( i );
            if ( value instanceof JSONArray )
            {
                ret.add( parseJSON2List( value.toString() ) );
            }
            else if ( value instanceof JSONObject )
            {
                ret.add( parseJSON2Map( value.toString() ) );
            }
            else
            {
                ret.add( value );
            }
        }
        return ret;
    }
}