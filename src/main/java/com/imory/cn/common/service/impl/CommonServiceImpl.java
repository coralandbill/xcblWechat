package com.imory.cn.common.service.impl;

import com.imory.cn.common.dao.CommonMapper;
import com.imory.cn.common.service.CommonService;
import com.imory.cn.exception.ServiceException;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Date;
import java.util.Map;

/**
 * <p>名称</p>
 *
 * @author zb.jiang
 * @version 1.0
 * @Date 2018/1/7
 */
@Service
public class CommonServiceImpl implements CommonService {

    @Autowired
    private CommonMapper commonMapper;

    @Override
    public Map<String, Object> logon(String logonId, String password) {
        return commonMapper.logon(logonId, password);
    }

    @Override
    public String getAccess_Token(String appId, String secret, String ACCESS_TOKEN) {
        Map accessToken = commonMapper.getWechatParam(ACCESS_TOKEN);

        long updateTime = ((Date) accessToken.get("updateTime")).getTime();

        long now = System.currentTimeMillis();

        if (now < updateTime + 110 * 60 * 1000) {
            // 如果未超时，返回数据库中记录的值
            return accessToken.get("paramValue").toString();
        }

        HttpClient httpClient = new HttpClient();
        NameValuePair[] nameValuePairs = new NameValuePair[3];
        nameValuePairs[0] = new NameValuePair("appid", appId);
        nameValuePairs[1] = new NameValuePair("secret", secret);
        nameValuePairs[2] = new NameValuePair("grant_type", "client_credential");
        GetMethod method = new GetMethod("https://api.weixin.qq.com/cgi-bin/token");
        method.setQueryString(nameValuePairs);
        HttpMethodParams param = method.getParams();
        param.setContentCharset("UTF-8");
        try {
            httpClient.executeMethod(method);
            String returnMsg = method.getResponseBodyAsString();
            JSONObject jsonObject = new JSONObject(returnMsg);
            if (jsonObject.has("access_token")) {
                // 表示成功
                String access_token = jsonObject.getString("access_token");
                return access_token;
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
}
