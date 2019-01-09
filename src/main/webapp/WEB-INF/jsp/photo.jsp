<%@ page language="java" import="java.util.*" contentType="text/html; charset=utf-8" %>
<%@taglib prefix="c" uri="/WEB-INF/tld/c.tld" %>
<%@taglib prefix="fn" uri="/WEB-INF/tld/fn.tld" %>
<%@taglib prefix="fmt" uri="/WEB-INF/tld/fmt.tld" %>
<%@taglib prefix="spring" uri="/WEB-INF/tld/spring.tld" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>兔小白个人博客</title>
    <link href="/static/css/mood.css" rel="stylesheet">
    <link rel="stylesheet" href="/static/css/hero-slider-style.css">
    <link rel="stylesheet" href="/static/css/magnific-popup.css">
    <link rel="stylesheet" href="/static/css/templatemo-style.css">
    <script src="/static/js/jquery-1.10.2.min.js"></script>
    <script>
        var tm_gray_site = false;
        if (tm_gray_site) {
            $('html').addClass('gray');
        } else {
            $('html').removeClass('gray');
        }
    </script>
</head>
<body>
<body>
<jsp:include page="/WEB-INF/jsp/include/top.jsp">
    <jsp:param name="type" value="5"/>
</jsp:include>
<article class="moodlist">
    <h1 class="t_nav" style="text-align: right;">
        <span>我的生活，点滴记录</span>
        <a href="/web/myBlog/index" class="n1">网站首页</a>
        <a href="#" class="n2"><spring:message code="photo"/></a>
    </h1>
    <br/>
    <div class="cd-hero">
        <ul class="cd-hero-slider">
            <li class="selected">
                <div class="cd-full-width">
                    <div class="container-fluid js-tm-page-content" data-page-no="1" data-page-type="gallery">
                        <div class="tm-img-gallery-container">
                            <c:choose>
                                <c:when test="${not empty list}">
                                    <div class="tm-img-gallery gallery-one">
                                        <c:forEach items="${list}" var="picture" varStatus="status">
                                            <div class="grid-item">
                                                <figure class="effect-sadie">
                                                    <img src="${picture.pictureName}" alt="${picture.pictureName}" class="img-fluid tm-img" onerror="javascript:this.src='/static/images/error.jpg';this.onerror = null"/>
                                                    <figcaption>
                                                        <h2 class="tm-figure-title">
                                                            <span><strong>${picture.pictureTitle}</strong></span>
                                                        </h2>
                                                        <p class="tm-figure-description">${picture.pictureContent}</p>
                                                        <a href="${picture.pictureName}">查看大图</a>
                                                    </figcaption>
                                                </figure>
                                            </div>
                                        </c:forEach>
                                    </div>
                                </c:when>
                                <c:otherwise>
                                    <font style="font-size: 16px;">暂无相关信息</font>
                                </c:otherwise>
                            </c:choose>
                        </div>
                    </div>
                </div>
            </li>
        </ul>
    </div>
    <div id="loader-wrapper">
        <div id="loader"></div>
        <div class="loader-section section-left"></div>
        <div class="loader-section section-right"></div>
    </div>
</article>
<script src="/static/js/hero-slider-main.js"></script>
<script src="/static/js/jquery.magnific-popup.min.js"></script>
<script>
    function adjustHeightOfPage(pageNo) {
        var pageContentHeight = 0;
        var pageType = $('div[data-page-no="' + pageNo + '"]').data("page-type");
        if (pageType != undefined && pageType == "gallery") {
            pageContentHeight = $(".cd-hero-slider li:nth-of-type(" + pageNo + ") .tm-img-gallery-container").height();
        } else {
            pageContentHeight = $(".cd-hero-slider li:nth-of-type(" + pageNo + ") .js-tm-page-content").height() + 20;
        }

        var totalPageHeight = $('.cd-slider-nav').height() + pageContentHeight + $('.tm-footer').outerHeight();
        if (totalPageHeight > $(window).height()) {
            $('.cd-hero-slider').addClass('small-screen');
            $('.cd-hero-slider li:nth-of-type(' + pageNo + ')').css("min-height", totalPageHeight + "px");
        } else {
            $('.cd-hero-slider').removeClass('small-screen');
            $('.cd-hero-slider li:nth-of-type(' + pageNo + ')').css("min-height", "100%");
        }
    }

    $(window).load(function () {
        $('.gallery-one').magnificPopup({
            delegate: 'a',
            type: 'image',
            gallery: {
                enabled: true
            }
        });
        $('#tmNavbar a').click(function () {
            $('#tmNavbar').collapse('hide');
            adjustHeightOfPage($(this).data("no"));
        });
        $(window).resize(function () {
            var currentPageNo = $(".cd-hero-slider li.selected .js-tm-page-content").data("page-no");
            setTimeout(function () {
                adjustHeightOfPage(currentPageNo);
            }, 1000);
        });
        $('body').addClass('loaded');
    });
</script>
</body>
</html>