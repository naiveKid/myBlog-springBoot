<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport"/>
<!-- bootstrap 3.0.2 -->
<link href="/static/css/admin/bootstrap.css" rel="stylesheet" type="text/css"/>
<!-- font Awesome -->
<link href="/static/css/admin/font-awesome.css" rel="stylesheet" type="text/css"/>
<!-- Theme style -->
<link href="/static/css/admin/style.css" rel="stylesheet" type="text/css"/>
<link href="/static/css/admin/table.css" rel="stylesheet" type="text/css"/>
<link href="/static/css/page.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="/static/js/jquery-1.10.2.min.js"></script>
<script type="text/javascript" src="/static/js/sockjs.js"></script>
<!-- jQuery UI 1.10.3 -->
<script src="/static/js/jquery-ui-1.10.3.js" type="text/javascript"></script>
<!-- Bootstrap -->
<script src="/static/js/bootstrap.js" type="text/javascript"></script>
<script>
    /**
     * 判断字符串是否为空值
     * @param {Object} String
     * @param {Object} str
     */
    function isNull(str) {
        var flg = false;
        if (str == null || str == "" || str == undefined || str == "undefined" || str == "null" || str == "NULL") {
            flg = true;
        }
        return flg;
    }

    /**
     * 获取当前浏览器类型和版本号
     */
    function getExplorerInfo() {
        var explorer = window.navigator.userAgent.toLowerCase();
        //ie
        if (explorer.indexOf("msie") >= 0) {
            var ver = explorer.match(/msie ([\d.]+)/)[1];
            return {type: "IE", version: ver};
        }
        //firefox
        else if (explorer.indexOf("firefox") >= 0) {
            var ver = explorer.match(/firefox\/([\d.]+)/)[1];
            return {type: "Firefox", version: ver};
        }
        //Chrome
        else if (explorer.indexOf("chrome") >= 0) {
            var ver = explorer.match(/chrome\/([\d.]+)/)[1];
            return {type: "Chrome", version: ver};
        }
        //Opera
        else if (explorer.indexOf("opera") >= 0) {
            var ver = explorer.match(/opera.([\d.]+)/)[1];
            return {type: "Opera", version: ver};
        }
        //Safari
        else if (explorer.indexOf("Safari") >= 0) {
            var ver = explorer.match(/version\/([\d.]+)/)[1];
            return {type: "Safari", version: ver};
        } else {
            //识别不出来的浏览器
            return {type: "0", version: "0"};
        }
    }

    /**
     * 创建websockt连接
     */
    function getWebSocket(url) {
        //首先获取浏览器类型和版本号
        var explorer = getExplorerInfo();
        var type = explorer.type;
        var version = explorer.version;

        //websocket连接方式,模式使用html5方式连接
        var conType = "html5";
        var websocket = null;

        //ie9以下版本都是用socketjs连接
        if (type == "IE") {
            if (parseFloat(version) < 9) {
                conType = "socketjs";
            }
        } else if (type == "0") {//如果遇到一些无法识别的浏览器，则默认使用socketjs
            conType = "socketjs";
        }

        //建立websocket连接
        if (conType == "html5") {
            websocket = new WebSocket("ws://" + url);
        }else {
            websocket = new SockJS("http://" + url + "/sockjs");
        }
        return websocket;
    }

    /**
     * ajax方式注销登录
     */
    function exitAjax() {
        $.ajax({
            async: false,
            type: 'post',
            url: '/user/exitAjax',
            data: {'userName': '${param.userName}'},
            dataType: 'json',
            success: function (data) {
                //返回登录页面
                if (data.info == 'alert') {
                    alert("您的账号在其他地方登录，如非本人操作请重新登录并修改密码！");
                    window.location.href = '/login.jsp';
                }
            },
            error: function (e) {
                alert("与服务器断开连接，请检查网络设置是否正确！");
            }
        });
    }

    var url = "<%=request.getServerName()%>:<%=request.getLocalPort()%>/websocket";
    var websocket = getWebSocket(url);

    //心跳检测
    var heartCheck = {
        timeout: 180000,//180s
        timeoutObj: null,
        reset: function () {
            clearTimeout(this.timeoutObj);
            this.start();
        },
        start: function () {
            this.timeoutObj = setTimeout(function () {
                websocket.send("heartBeat");
            }, this.timeout)
        }
    };

    //创建一个websocket连接
    function createWebSocket() {
        try {
            initEventHandle();
        } catch (e) {
            reconnect();
        }
    }

    function initEventHandle() {
        websocket.onclose = function () {
            //注销登录
            exitAjax();
        };
        websocket.onerror = function () {
            reconnect();
        };
        //客户端跟服务端连接建立成功后回调函数
        websocket.onopen = function () {
            //心跳检测开始
            heartCheck.start();
        };
        //接受服务器推送的消息
        websocket.onmessage = function (evt) {
            //消息类型
            var msgType = "";
            //服务器发送消息不为空的话，把消息转换成json对象
            var data = null;
            if (!isNull(evt.data)) {
                data = jQuery.parseJSON(evt.data);
                msgType = data.msgType;
            }
            if (!isNull(msgType)) {
                if (msgType == "updateCount") {//更新在线人数
                    $("#countOnline").html(data.onlineCount);
                } else if (msgType == "essayPublish") {//文章订阅发布消息
                    alert(data.essayMessage);
                }
            }
            //拿到任何消息都说明当前连接是正常的，心跳检测重置
            heartCheck.reset();
        };
    }

    //重连方法
    function reconnect() {
        //没连接上会一直重连，设置延迟避免请求过多
        setTimeout(function () {
            createWebSocket();
        }, 2000);
    }

    $(document).ready(function () {
        createWebSocket();
    });

    //判断控制页面初始时左右的高度一致
    window.onload = function () {   //设置页面加载完成后运行JS
        var hl = $("#leftMenu").outerHeight(); //获取左侧left层的高度
        var hr = $("#rightMenu").outerHeight(); //获取右侧right层的高度
        var mh = Math.max(hl, hr); //比较hl与hr的高度，并将最大值赋给变量mh
        $("#leftMenu").height(mh); //将left层高度设为最大高度mh
        $("#rightMenu").height(mh); //将right层高度设为最大高度
    }

    function del() {
        if (!confirm("确认要删除？")) {
            window.event.returnValue = false;
        }
    }

</script>