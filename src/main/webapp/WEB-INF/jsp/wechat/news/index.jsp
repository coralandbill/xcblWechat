<!doctype html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix='fmt' uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta name="viewport" content="width=device-width, maximum-scale=1.0, initial-scale=1.0, user-scalable=0"/>
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <title>${newsType == 1 ? '新闻列表' : '活动列表'}</title>
    <link rel="stylesheet" href="/css/frozen.css" type="text/css"/>
    <link rel="stylesheet" href="/css/css.css" type="text/css"/>
    <link type="text/css" href="/css/font-awesome.min.css" rel="stylesheet">
    <link rel="stylesheet" href="//cdn.bootcss.com/weui/1.1.1/style/weui.min.css">
    <link rel="stylesheet" href="//cdn.bootcss.com/jquery-weui/1.0.1/css/jquery-weui.min.css">

</head>

<body>
<header class="w100">
    <div class="logo fl"><a href="#"><img src="/images/logo.png"/></a></div>
</header>
<!--header end -->
<div class="content">
    <div class="new_banner"><img src="/images/banner_08.png"/></div>
    <div class="new_list new_list1 t10">
        <ul id="newsUl">
            <script type="text/x-jquery-tmpl" id="newInfoTmpl">
                <li class="clearfix"><a href="/wechat/news/detail.do?id=#[$data.id]">#[$data.title]</a><span>#[$data.newsDateFormat]</span></li>

            </script>
            <p style="display: none;" id="news_p"></p>
        </ul>
    </div>
</div>
<!--content end-->
<div class="clear"></div>
<jsp:include page="../../common/footer.jsp"/>
<div class="weui-loadmore" id="loadingWeUi">
    <i class="weui-loading"></i>
    <span class="weui-loadmore__tips">正在加载</span>
</div>
<!--导航弹出层-->
<script type="text/javascript" src="/scripts/jquery-2.2.4.js"></script>
<script type="text/javascript" src="/scripts/xiala.js"></script><!--下拉菜单-->
<script type="text/javascript" src="/scripts/main.js"></script>
<script type="text/javascript" src="/scripts/modernizr.js"></script>
<script type="text/javascript" src="${resourcePath}/scripts/jquery.tmpl.min.js"></script>
<script type="text/javascript" src="http://res.wx.qq.com/open/js/jweixin-1.2.0.js"></script>
<script src="//cdn.bootcss.com/jquery-weui/1.0.1/js/jquery-weui.min.js"></script>
<script type="text/javascript">
    var loadingFlag;
    var startPos = 0;
    var maxRows = 10;
    var hasMore = false;

    $(document).ready(function () {
        loadNewsInfo();
    });

    function loadNewsInfo() {
        $.ajax({
            url: "/wechat/newsAjax/list.do",
            contentType: "application/x-www-form-urlencoded; charset=utf-8",
            type: "POST",
            dataType: "json",
            traditional: true,
            data: {
                newsType: ${newsType},
                startPos: startPos,
                maxRows: maxRows
            },
            success: function (result) {
                if (result.success) {
                    loadingFlag = true;
                    var newList = result.newsList;
                    if (newList != undefined && newList.length > 0) {
                        startPos += newList.length;
                    }
                    $("#newInfoTmpl").tmpl(newList).insertBefore("#news_p");
                    hasMore = result.hasMore;
                    $("#loadingWeUi").hide();
                }
            }
        });
    }
    $(document.body).infinite().on("infinite", function () {
        if (hasMore && loadingFlag) {
            $("#loadingWeUi").show();
            loadingFlag = false;
            loadNewsInfo();
        }
    });
</script>
<!--导航弹出层-->
</body>
</html>
