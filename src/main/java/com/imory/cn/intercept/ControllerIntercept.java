package com.imory.cn.intercept;

import com.imory.cn.annotation.SessionCheck;
import com.imory.cn.common.model.OrgAccountInfo;
import com.imory.cn.common.model.OrgCompany;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.lang.annotation.Annotation;

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

    /**
     * 登录地址
     */
    private final static String USER_LOGON_ADDRESS = "/wechat/common/login.do";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        boolean success = super.preHandle(request, response, handler);

        HttpSession session = request.getSession();
        OrgCompany orgCompany = (OrgCompany) session.getAttribute(OrgCompany.USER_COMPANY);

        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Annotation sessionCheckAnno = handlerMethod.getMethodAnnotation(SessionCheck.class);

        if (sessionCheckAnno != null && orgCompany == null) {
            // 记录用户访问的页面
            session.setAttribute( OrgAccountInfo.ORIG_URI, this.getOrgUri( request ) );
            // 跳转至登录页面
            response.sendRedirect( USER_LOGON_ADDRESS);
        }

        return success;
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

}
