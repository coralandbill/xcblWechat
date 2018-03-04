<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="utf-8">
    <title>举报</title>
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
        <div class="header">
            <a class="new-a-back" href="javascript:history.back();"> <span>返回</span> </a>
            <h2>举报</h2>
            <a class="new-a-jd" id="trigger-overlay" href="javascript:void(0)"> <span>导航菜单</span> </a>
        </div>
    </header>
    <!--header 结束-->
    <div class="page">
        <div class="main">
            <form id="frm_login" method="post" action="">
                <div class="item item-username">
                    <label>举报标题</label>
                    <input id="title" class="txt-input txt-username" type="text" placeholder="请输入举报标题"
                           name="title">
                </div>
                <%--<div class="item item-username">
                    <div class="weui-cells weui-cells_form">
                        <div class="weui-cell">
                            <div class="weui-cell__bd">
                                <div class="weui-uploader">
                                    <div class="weui-uploader__hd">
                                        <p class="weui-uploader__title">图片上传</p>
                                    </div>
                                    <div class="weui-uploader__bd">
                                        <ul class="weui-uploader__files" id="uploaderFiles">
                                        </ul>
                                        <div class="weui-uploader__input-box">
                                            <input id="uploaderInput" class="weui-uploader__input" type="file" accept="image/*" multiple="">
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>--%>
                <div class="item item-username">
                    <label>举报内容</label>
                    <textarea class="txt-input" id="content" name="content" style="height: 100px;"
                              placeholder="请输入举报内容"></textarea>
                </div>
                <div class="item item-username">
                    <label>联系手机</label>
                    <input id="mobile" class="txt-input txt-username" type="text" placeholder="如需回复请填写"
                           name="mobile">
                </div>
                <div class="ui-btn-wrap"><a class="ui-btn-lg ui-btn-primary" href="javascript:saveInform();">提交举报</a>
                </div>
            </form>
        </div>
        <script type="text/javascript" src="/scripts/jquery-2.2.4.js"></script>
    </div>
    <!--footer 开始-->
    <jsp:include page="../../common/footer.jsp"/>
    <!--footer end-->
</div>
<script type="text/javascript" src="/scripts/jquery-2.2.4.js"></script>
<script src="//cdn.bootcss.com/jquery-weui/1.0.1/js/jquery-weui.min.js"></script>
<script>
    function saveInform() {
        var _title = $("#title").val();
        var _content = $("#content").val();
        if (_title.length == 0) {
            $.alert("请输入标题");
            return;
        }
        if (_content.length == 0) {
            $.alert("请输入举报内容");
            return;
        }
        $.ajax({
            url: "/wechat/informAjax/saveInform.do",
            contentType: "application/x-www-form-urlencoded; charset=utf-8",
            type: "POST",
            dataType: "json",
            traditional: true,
            data: {
                title: _title,
                content: _content,
                mobile: $("#mobile").val()
            },
            success: function (result) {
                if (result.success) {
                    $.alert("提交成功");
                }
                else {
                    $.alert("提交失败");
                }
            }
        });
    }
</script>
</body>
</html>
