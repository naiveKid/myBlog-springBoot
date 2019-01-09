<%@ page language="java" contentType="text/html; charset=utf-8" %>
<%@taglib prefix="c" uri="/WEB-INF/tld/c.tld" %>
<%@taglib prefix="fn" uri="/WEB-INF/tld/fn.tld" %>
<%@taglib prefix="fmt" uri="/WEB-INF/tld/fmt.tld" %>
<%@taglib prefix="spring" uri="/WEB-INF/tld/spring.tld" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>兔小白个人博客</title>
    <script src="/static/js/jquery.min.js"></script>
    <link href="/static/css/aboutMe.css" type="text/css" rel="stylesheet"/>
    <link rel="stylesheet" href="/static/css/buttons.css" type="text/css">
    <!-- layer -->
    <script src="/static/js/layer/layer.js" type="text/javascript"></script>
    <style type="text/css">
        input {
            border: 2px solid #ddd;
            border-radius: 0;
            color: #999;
            display: inline;
            font: 400 1em/1 "Open Sans", sans-serif;
            margin: 0 0 .5em;
            padding: 1em 1.25em;
            -webkit-transition: border-color 300ms, color 300ms;
            transition: border-color 300ms, color 300ms;
            -webkit-appearance: none
        }

        input:focus{
            border-color: #333;
            color: #333;
            outline: 0
        }

        .dibu {
            text-align: center;
            position: fixed;
            bottom: 20px;
            width: 80%;
            height: 50px;
        }

        #container {
            height: 75%;
            margin: 0px auto 0;
        }

        .header {
            background: #B0E2FF;
            width: 100%;
            height: 35px;
            color: #FFFFFF;
            line-height: 34px;
            font-size: 20px;
            padding: 0 10px;
        }

        .content {
            font-size: 12px;
            width: 100%;
            height: 100%;
            overflow: auto;
            padding: 1%;
            margin-bottom: 60px;
        }

        .content li {
            margin-top: 10px;
            padding-left: 0px;
            width: 100%;
            display: block;
            clear: both;
            overflow: hidden;
        }

        .content li img {
            float: left;
        }

        .content li span {
            background: #7cfc00;
            padding: 10px;
            border-radius: 10px;
            float: left;
            margin: 6px 10px 0 10px;
            max-width: 200px;
            border: 1px solid #ccc;
            box-shadow: 0 0 3px #ccc;
        }

        .content li img.imgleft {
            float: left;
        }

        .content li img.imgright {
            float: right;
        }

        .content li span.spanleft {
            float: left;
            background: #fff;
        }

        .content li span.spanright {
            float: right;
            background: #7cfc00;
        }
    </style>
</head>
<body>
<jsp:include page="/WEB-INF/jsp/include/top.jsp">
    <jsp:param name="type" value="2"/>
</jsp:include>
<article class="aboutcon">
    <h1 class="t_nav">
        <span>像“草根”一样，紧贴着地面，低调的存在，冬去春来，枯荣无恙。</span>
        <a href="/web/myBlog/index" class="n1">网站首页</a>
        <a href="#" class="n2"><spring:message code="aboutMe"/></a>
    </h1>
    <div>
        <div id="container">
            <div class="header">
                <span id="biaoti" style="float: left;"></span>
                <span style="float: right;"><a id="laheiBut" onclick="lahei()">👉拉黑舔狗👈</a></span>
            </div>
            <ul class="content" id="talkContent"></ul>
        </div>
    </div>
    <div class="dibu" id="sendAll">
        <input id="shuru" name="shuru" placeholder="输入聊天内容" style='width:80%'/>
        <input type="submit" id="send " value="发送" onclick="send()" style='background:#F0F0F0; width:15% '/>
    </div>
</article>
<jsp:include page="/WEB-INF/jsp/include/bottom_second.jsp"/>
<script type="text/javascript">
    var iNow = -1;    //用来累加改变左右浮动
    var text = document.getElementById("shuru");
    var content = document.getElementById('talkContent');
    var img = content.getElementsByTagName('img');
    var span = content.getElementsByTagName('span');
    var laheiBut = document.getElementById("laheiBut");
    var xxnum = 0;
    var xxbnum = 0;
    var xh;
    var hisName = '${boyName}';
    var myName = '${girlName}';

    var sayNum = 0;
    var saying;

    var say_1 = new Array(
        "我真的好喜欢你啊，" + myName,
        "在干嘛呢？" + myName,
        "我想你了，" + myName,
        "吃了吗？",
        "很想你",
        "你在干嘛呢半天不回消息？",
        "最近怎么样？",
        "在吗？",
        "有什么心事记得告诉我。",
        "有需要我的告诉我哦。",
        "为什么不理我啊？" + myName,
        "相信我可以对你好一辈子。" + myName,
        "在不在？你忙的话可以告诉我一下啊我一直在等你呢。",
        myName + "，我又喝酒了头痛的感觉比心痛好多了。",
        "你能回我消息我就开心的不得了，比开心果还开心。",
        "在一起好不好？" + myName,
        "出来吃个饭吧？海底捞好不好？",
        "可以和我说会话吗？哪怕一分钟也行！",
        "感觉好想死啊，爱一个人好难啊！",
        "回我个消息好不好？",
        "晚安，" + myName,
        "早安，" + myName,
        "你长咋这么好看呢？" + myName,
        myName + "，我喜欢你",
        "真的好喜欢你，" + myName,
        "你喜欢我吗？",
        "你真的很棒，比棒球还棒！",
        "你真的很优秀！爱你么么哒QAQ",
        "你真好！比陈好还好",
        "天哪，" + myName + "你也太好看了8",
        "我想抱着二百多斤的你一起看星星看月亮",
        "等我有钱了，一定要买一个能收到你回复的手机！",
        "当我看到你照片的那一刻，我又把手伸进了裤裆，告诉我这是今天最后一次！",
        myName + "你知道吗？看到你的脸，我的心跳立刻加速到了500次/秒！",
        myName + "，你这辈子最大的遗憾就是亲不到自己的脸。",
        "你出现一瞬间，我心动好几年。",
        "想你了，" + myName,
        "想认你做对象",
        "对不起，我不该用我的iphonexs给你一直发消息，打扰到你了，抱歉",
        "我知道你不喜欢我，但我还是想和你说，我喜欢你，" + myName,
        "你爱我，我爱你，你不爱我我还爱你，我爱你你还不理我，那我就舔你！",
        "昨晚梦到你了，真的",
        "可以带我打一把游戏吗？" + myName,
        "那你有时间了回我一下吧，我等你，" + myName,
        "我错了",
        "对不起",
        "洗完澡了吗？",
        myName + "，你走，我不送你，你来，多大风雨我都接你！",
        "我渴望被爱，但我得到的都是伤害。",
        "下辈子我要做你的一颗牙，至少我疼，你也会难受。",
        myName + "，我愿意做你的备胎，我愿意一直等你，求你头部顺时针旋转117度回头看看我吧",
        myName + "，我愿意把我的心回手掏掏出来给你看！",
        "你为什么在我眼里能发光？" + myName,
        "自从你来了，我的体重又重了一点，因为你到我的心里了。",
        myName + "我求求你别散发魅力了，我怕我控制不住自己",
        "你肠胃不好，记得多喝粥。你总说高跟鞋磨脚，我在你的每双鞋后面都贴上了磨脚贴。你出门总忘记关电源，我已经给你充值了三万块钱的电费。你过的好我就开心了",
        "为什么不说话呢？不是我的孩子也没关系，我养！",
        "早安，今天又是爱你的一天。",
        "晚安，我今天一天都在想你，好难受。",
        myName + "，你知道今天是什么日子吗？是我对你心动的第13个月，第312个小时，第18720分钟，第1123200秒",
        myName + "，你拉的屎真香！",
        "哇，你的口水晶莹剔透，真美！",
        "你的小鼻屎好可爱呀，圆圆的软软的，喜欢死了！",
        "我的天呐" + myName + "！你的耳屎竟然是黄色的，就像天上的星星一样明亮照人！",
        "你指甲里的泥可以送给我吗？我想它一定能养出最美的花！",
        "你的眼屎怎么生的如此婀娜多姿，可以弹给我吗？" + myName,
        "我的马鸭，你吐的痰简直像一副画，请问你是毕加索吗？",
        "你的脚真香，味道很浓郁，想点赞！",
        "为什么不说话？去找你对象了吗？",
        "早安啦",
        "晚安啦",
        "在做什么呢？我在想你",
        "能看到我的消息吗？" + myName,
        "怎么看不了你的朋友圈呀？",
        "QQ空间怎么把我屏蔽了呢？",
        "人生若只如初见，我想和你网恋奔现",
        "曾经沧海难为水，我想亲亲你的嘴",
        myName + "你以后走路能不能看着点啊？非要撞在我心上？",
        "喜欢你是件很麻烦的事，但我偏偏喜欢找麻烦。",
        "我想你一定很忙，所以你只看前三个字就好",
        "我只是喜欢你，我没有恶意",
        "这是我的手背，这是我的脚背，你是我的宝贝",
        "这是个狼人，那是个贱人，你是个美人",
        "人生若只如初见，我想和你网恋奔现",
        "曾经沧海难为水，我想亲亲你的嘴",
        "夜来风雨声，我还在死撑。",
        "但愿人长久，想和你寒暄。",
        "十年生死两茫茫，请你做我的女王",
        "问君能有几多愁，恰似你对他的温柔",
        "忽如一夜春风来，不要和我说拜拜",
        "山重水复疑无路，你不理我好冷酷",
        "采菊东篱下，你在干嘛呀？",
        "海内存知己，打扰了对不起",
        "枯藤老树昏鸦，想和你有个家",
        "不识庐山真面目，你来花钱我支付",
        "夕阳无限好，对象搞不搞？",
        "两个黄鹂鸣翠柳，一点喜欢都没有？",
        "东边日出西边雨，我想把你往家娶",
        "春花秋月何时了，卧槽你咋那么叼？",
        "春风又绿江南岸，你却找了负心汉！",
        "春色满园关不住，对象到底处不处？",
        "相见时难别亦难，你让我有点心寒。",
        "独在异乡为异客，你只会回我呵呵。",
        "落红不是无情物，你是我唯一的守护！",
        "春眠不觉晓，你心灵手巧。",
        "玲珑骰子安红豆，别让渣男把你透",
        "读书破万卷，听我一句劝",
        "欲把西湖比西子，你不回我我想死",
        "飞流直下三千尺，我愿意为你吃屎",
        "唉，那好吧，听你的"
    );

    var say_2 = new Array(
        "哦",
        "？",
        "......",
        "有事？",
        "闭嘴行吗？" + myName,
        "啊", "别bb了",
        myName + "你烦不烦？",
        "你好，我现在有事不在，一会再和你联系。",
        "我手机就剩99%的电了，先不聊了",
        "我去洗澡了886",
        "随便",
        "关我屁事",
        "别总说那些没用的行吗？",
        "闭嘴",
        myName + "你咋那么能墨迹？",
        "我要睡了",
        "呵呵",
        "滚",
        "有病？",
        "？？？",
        "你sb吧，" + myName,
        "别几把和我说话了！",
        "再bb互删",
        "？？",
        "？？？？",
        "？？？？？",
        "。",
        "。。",
        "。。。",
        "哦呵呵",
        "然后呢？",
        myName + "你到底想干啥？",
        "给老子滚",
        "下线了拜拜"
    );

    biaoti.innerHTML = "和 " + hisName + " 聊天中";
    var hisIconSrc = "/static/images/hisIcon.jpg";
    var myIconSrc = "/static/images/myIcon.jpg";

    var modelNum =${role};

    if (modelNum == 1) {
        setTimeout(fristSend, 1500);
        saying = say_1;
    } else if (modelNum == 2) {
        laheiBut.style.display = 'none';
        saying = say_2;
    }

    function send() {
        if (text.value == ''&&modelNum==1) {
            layer.alert('舔狗必死，你必须狠狠的虐待它！', {icon: 6});
        }else if (text.value == ''&&modelNum==2) {
            layer.alert('作为舔狗你一个字都不打就想发送消息？', {icon: 6});
        } else {
            content.innerHTML += '<li><img src="' + myIconSrc + '"width=40px height=40px ></img><span style="word-wrap: break-word; word-break: break-all;">' + text.value + '</span></li>';
            iNow++;
            img[iNow].className += 'imgright';
            span[iNow].className += 'spanright';
            text.value = '';
            //将滚动条放置到最底端
            content.scrollTop = content.scrollHeight;
            xxbnum = parseInt((Math.random() * 10) + "") + 1;
            if (modelNum == 2) {
                if (xxbnum >= 2) {
                    xxnum++;
                }
                if (xxnum >= 2) {
                    setTimeout(leftSend, parseInt(Math.random() * 1000) + 1000);
                    xxnum = 0;
                }
            }
        }
    }

    function leftSend() {
        var scrollTop = $(this).scrollTop();  //scrollTop() 方法返回或设置匹配元素的滚动条的垂直位置
        var scrollHeight = $(document).height(); //整个文档的高度
        var windowHeight = $(this).height(); //当前可见区域的大小
        if(scrollTop + windowHeight == scrollHeight||scrollHeight<windowHeight-140){//未出现滚动栏或出现滚动栏则必须滑到底部
            sayNum = Math.random();
            sayNum = Math.ceil(sayNum * saying.length);
            content.innerHTML += '<li><img src="' + hisIconSrc + '" width=40px height=40px ><span style="word-wrap: break-word; word-break: break-all;">' + saying[sayNum - 1] + '</span></li>';
            saying.splice(sayNum - 1, 1);
            iNow++;
            img[iNow].className += 'imgleft';
            span[iNow].className += 'spanleft';
            content.scrollTop = content.scrollHeight;
        }else{
            return;
        }
    }

    function fristSend() {
        var kcb = "在吗？"+myName;
        content.innerHTML += '<li><img src="' + hisIconSrc + '" width=40px height=40px ><span style="word-wrap: break-word; word-break: break-all;">' + kcb + '</span></li>';
        iNow++;
        img[iNow].className += 'imgleft';
        span[iNow].className += 'spanleft';
        content.scrollTop = content.scrollHeight;
        xh = window.setInterval("leftSend()", 2000);
    }

    function lahei() {
        window.clearInterval(xh);
        layer.alert('已成功拉黑 ' + hisName, {icon: 6});
    }
</script>
</body>
</html>