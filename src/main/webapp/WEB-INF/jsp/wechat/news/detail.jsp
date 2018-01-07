<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="viewport" content="width=device-width, maximum-scale=1.0, initial-scale=1.0, user-scalable=0"/>
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <title>${newsDetail.title}详情</title>
    <link rel="stylesheet" href="/css/frozen.css" type="text/css"/>
    <link rel="stylesheet" href="/css/css.css" type="text/css"/>
    <link type="text/css" href="/css/font-awesome.min.css" rel="stylesheet">

</head>

<body>
<!--header end -->
<div class="content">
    <div class="new_ny">
        <h3>${newsDetail.title}<span>新闻日期:${newsDetail.newsDateFormat}</span></h3>
        <div><img src="${newsDetail.imgUrl}">
        </div>
        <p>${newsDetail.content}</p>
    </div>
    <%--<div class="fanye">
        <ul class="clearfix">
            <li class="clearfix fl"><span class="fl"><img src="/images/page_left.png"/></span><a
                    class="fr none">上一篇：没有了</a></li>
            <li class="clearfix fr"><a class="fl">下一篇：山东省饲料大会召开</a><span class="fr"><img
                    src="/images/page_right.png"/></span></li>
        </ul>
    </div>--%>
</div>
<!--content end-->
<div class="clear"></div>
<jsp:include page="../../common/footer.jsp"/>
</body>
</html>
