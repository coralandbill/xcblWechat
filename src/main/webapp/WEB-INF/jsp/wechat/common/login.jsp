<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="utf-8">
    <title>登录</title>
    <meta name="description" content="手机网站">
    <meta name="Keywords" content="手机网站">
    <meta content="initial-scale=1.0,user-scalable=no,maximum-scale=1,width=device-width" name="viewport"/>
    <link type="text/css" rel="stylesheet" href="/css/login/css/basic.css"/>
    <link rel="stylesheet" href="//cdn.bootcss.com/weui/1.1.1/style/weui.min.css">
    <link rel="stylesheet" href="//cdn.bootcss.com/jquery-weui/1.0.1/css/jquery-weui.min.css">
    <!--加载样式-->
</head>

<body>
<div class="w">
    <!--header 开始-->
    <header>
        <div class="header"><a class="new-a-back" href="javascript:history.back();"> <span>返回</span> </a>
            <h2>登录</h2>
        </div>
    </header>
    <!--header 结束-->
    <div class="page">
        <div class="main">
            <form id="frm_login" method="post" action="/wechat/common/doLogon.do">
                <div class="item item-username">
                    <input id="username" class="txt-input txt-username" type="text" placeholder="请输入用户名" value="${username}"
                           name="username">
                    <b class="input-close" style="display: block;"></b></div>
                <div class="item item-password">
                    <input id="password" class="txt-input txt-password ciphertext" type="password" placeholder="请输入密码"
                           name="password" style="display: inline;">
                    <div class="ui-btn-wrap"><a class="ui-btn-lg ui-btn-primary" href="javascript:$('#frm_login').submit();">用户登录</a></div>
                </div>
            </form>
        </div>
    </div>
</div>
<script type="text/javascript" src="/scripts/jquery-2.2.4.js"></script>
<script src="//cdn.bootcss.com/jquery-weui/1.0.1/js/jquery-weui.min.js"></script>
<script>
    var errorMsg = "${errorMsg}";
    if(errorMsg.length > 0)
    {
        $.alert(errorMsg);
    }
</script>
</body>
</html>
