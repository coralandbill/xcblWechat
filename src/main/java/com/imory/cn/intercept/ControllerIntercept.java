package com.imory.cn.intercept;

import com.imory.cn.annotation.SessionCheck;
import com.imory.cn.common.model.OrgAccountInfo;
import com.imory.cn.common.model.OrgCompany;
import com.imory.cn.org.dao.OrgAccountInfoMapper;
import com.imory.cn.org.dto.OrgAccountInfoExample;
import com.imory.cn.utils.WebUtils;
import com.imory.cn.utils.WechatOAuthUtils;
import com.imory.cn.utils.WechatUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.lang.annotation.Annotation;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <p>名称</p>
 * <p/>
 * <p>wikiURL</p>
 *
 * @author zb.jiang
 * @version 1.0
 * @Date 2017/9/30
 */
public class ControllerIntercept extends HandlerInterceptorAdapter {

    private static Logger logger = Logger.getLogger(ControllerIntercept.class);

    @Value("#{runtimeProperties['wechat.appid']}")
    private String appId;

    @Value("#{runtimeProperties['wechat.appsecret']}")
    private String appsecret;

    @Autowired
    private OrgAccountInfoMapper orgAccountInfoMapper;

    /**
     * 登录地址
     */
    private final static String USER_LOGON_ADDRESS = "/wechat/common/login.do";

    /**
     * 接收微信推送消息地址
     */
    private final static String WECHAT_POST_ADDRESS = "/wx/coreServlet.do";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws
            Exception {
        boolean success = super.preHandle(request, response, handler);

        String requestURI = request.getRequestURI();
        String ipAddress = this.getIpAddress(request);
        HttpSession session = request.getSession();

        // 对于微信主动发起的链接不做任何过滤
        if (requestURI.contains(WECHAT_POST_ADDRESS)) {
            logger.debug("微信主动调用，直接跳转");
            return true;
        }
        logger.debug("用户IP地址为[" + ipAddress + "]，访问了[" + requestURI + "]");

        OrgAccountInfo orgAccoInfo = (OrgAccountInfo) request.getSession().getAttribute(OrgAccountInfo.ORG_SESSION_ID);
        boolean inWechat = WebUtils.isInWechatBrowser(request);
        if (inWechat && orgAccoInfo == null) {
            // 微信浏览器
            String wechat_code = request.getParameter("code");
            String wechat_state = request.getParameter("state");
            logger.debug("wechat_code:[" + wechat_code + "]，wechat_state:[" + wechat_state + "]");

            // 尝试自动登录
            if (StringUtils.isNotBlank(wechat_code) && StringUtils.isNotBlank(wechat_state)) {
                logger.debug("用户来自微信浏览器，尝试自动登录");
                this.getUserByWechat(wechat_code, request);
            } else if (!HttpMethod.POST.name().equalsIgnoreCase(request.getMethod())) {
                // 非POST情况下尝试微信授权
                String origURL = request.getRequestURL().toString();
                //过滤登录地址，避免由于redirect登录地址造成原访问地址丢失情况
                String queryStr = request.getQueryString();
                if (StringUtils.isNotBlank(queryStr)) {
                    origURL = origURL + "?" + queryStr;
                }
                origURL = WechatOAuthUtils.changeIntoOAuthURL(appId,origURL);
                response.sendRedirect(origURL);
                logger.debug("用户来自微信浏览器，尝试获取微信授权");
                return false;
            }
        }

        OrgCompany orgCompany = (OrgCompany) session.getAttribute(OrgCompany.USER_COMPANY);
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Annotation sessionCheckAnno = handlerMethod.getMethodAnnotation(SessionCheck.class);
        if (sessionCheckAnno != null) {
            String _value = handlerMethod.getMethodAnnotation(SessionCheck.class).value();
            if (sessionCheckAnno != null && OrgCompany.USER_COMPANY.equalsIgnoreCase(_value) && orgCompany == null) {
                // 记录用户访问的页面
                session.setAttribute(OrgAccountInfo.ORIG_URI, this.getOrgUri(request));
                // 跳转至登录页面
                response.sendRedirect(USER_LOGON_ADDRESS);
            }
        }
        return success;
    }

    // 微信自动登录
    private void getUserByWechat(String code,HttpServletRequest httpRequest) {
        // 获取微信OPENID
        Map<String, Object> oauthInfo = WechatUtil.getOAuthOpenId(code, appId, appsecret);

        String openId = (String) oauthInfo.get("openid");
        logger.debug("获得openId，值为[" + openId + "]");
        logger.debug("获得access_token，值为[" + oauthInfo.get("access_token") + "]");
        if (StringUtils.isNotBlank(openId)) {
            // 将OPENID保存至SESSION中
            httpRequest.getSession().setAttribute(OrgAccountInfo.USER_OPEN_ID, openId);

            OrgAccountInfoExample accountInfoExample = new OrgAccountInfoExample();
            OrgAccountInfoExample.Criteria criteria = accountInfoExample.createCriteria();
            criteria.andOpenIdEqualTo(openId);
            List<com.imory.cn.org.dto.OrgAccountInfo> accountInfoList = orgAccountInfoMapper.selectByExample
                    (accountInfoExample);
            if (accountInfoList != null) {
                com.imory.cn.org.dto.OrgAccountInfo orgAccountInfo = accountInfoList.get(0);
                OrgAccountInfo accountInfo = new OrgAccountInfo();
                accountInfo.setCompanyId(orgAccountInfo.getCompanyId());
                accountInfo.setId(orgAccountInfo.getId());
                accountInfo.setOpenId(openId);
                accountInfo.setUserName(orgAccountInfo.getUserName());
                accountInfo.setCreateTime(orgAccountInfo.getCreateTime());
                httpRequest.getSession().setAttribute(OrgAccountInfo.ORG_SESSION_ID, accountInfo);
                logger.debug("用户[" + accountInfo.getId() + "]根据openId[" + openId + "]自动登录");
            } else {
                //注册新用户
                com.imory.cn.org.dto.OrgAccountInfo accountInfo = new com.imory.cn.org.dto.OrgAccountInfo();
                String nickName = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
                accountInfo.setOpenId(openId);
                accountInfo.setCreateTime(new Date());
                accountInfo.setNickName(nickName);
                accountInfo.setUserName(nickName);
                OrgCompany orgCompany = (OrgCompany) httpRequest.getSession().getAttribute(OrgCompany.USER_COMPANY);
                if(orgCompany != null)
                {
                    accountInfo.setCompanyId(orgCompany.getId());
                }
                orgAccountInfoMapper.insert(accountInfo);

                OrgAccountInfo acco = new OrgAccountInfo();
                acco.setCompanyId(accountInfo.getCompanyId());
                acco.setId(accountInfo.getId());
                acco.setOpenId(openId);
                acco.setUserName(accountInfo.getUserName());
                acco.setCreateTime(accountInfo.getCreateTime());
                httpRequest.getSession().setAttribute(OrgAccountInfo.ORG_SESSION_ID, acco);
            }
        }
    }

    // 获取用户访问的原地址
    private String getOrgUri(HttpServletRequest request) {
        String origURI = request.getRequestURI();
        String queryStr = request.getQueryString();
        if (StringUtils.isNotBlank(queryStr)) {
            origURI = origURI + "?" + queryStr;
        }
        return origURI;
    }

    // 获取用户客户端IP地址
    private String getIpAddress(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");

        if (StringUtils.isBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");

            if (StringUtils.isBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("WL-Proxy-Client-IP");
            }
            if (StringUtils.isBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("HTTP_CLIENT_IP");
            }
            if (StringUtils.isBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("HTTP_X_FORWARDED_FOR");
            }
            if (StringUtils.isBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getRemoteAddr();
            }
        } else if (ip.length() > 15) {
            String[] ips = ip.split(",");
            for (String ip1 : ips) {
                if (!("unknown".equalsIgnoreCase(ip1))) {
                    ip = ip1;
                    break;
                }
            }
        }

        return ip;
    }

}
