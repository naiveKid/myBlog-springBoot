<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="/WEB-INF/tld/c.tld" %>
<aside id="leftMenu" class="left-side sidebar-offcanvas">
    <section class="sidebar">
        <div class="user-panel">
            <div class="pull-left image">
                <img src="/static/images/26115.jpg" class="img-circle" alt="User Image" onerror="javascript:this.src='/static/images/error.jpg';this.onerror = null"/>
            </div>
            <div class="pull-left info">
                <p>${userName}</p>
                <a href="javascript:void(0)">
                    <i class="fa fa-circle text-success"></i>
                    Online
                </a>
            </div>
        </div>
        <ul class="sidebar-menu">
            <li <c:if test="${param.type==1}">class="active"</c:if>>
                <a href="/web/manage">
                    <i class="fa fa-question"></i>
                    <span>aboutMe</span>
                </a>
            </li>
            <li <c:if test="${param.type==2}">class="active"</c:if>>
                <a href="/mood/manage">
                    <i class="fa fa-moon-o"></i>
                    <span>Mood</span>
                </a>
            </li>
            <li <c:if test="${param.type==3}">class="active"</c:if>>
                <a href="/essay/manage">
                    <i class="fa fa-file-word-o"></i>
                    <span>Essay</span>
                </a>
            </li>
            <li <c:if test="${param.type==4}">class="active"</c:if>>
                <a href="/picture/manage">
                    <i class="fa fa-picture-o"></i>
                    <span>Photo</span>
                </a>
            </li>
            <li <c:if test="${param.type==5}">class="active"</c:if>>
                <a href="/notice/manage">
                    <i class="fa fa-comment-o"></i>
                    <span>Notice</span>
                </a>
            </li>
            <li <c:if test="${param.type==6}">class="active"</c:if>>
                <a href="/web/other">
                    <i class="fa fa-picture-o"></i>
                    <span>Other</span>
                </a>
            </li>
            <li <c:if test="${param.type==7}">class="active"</c:if>>
                <a href="/systemLog/listPagedOperationLog">
                    <i class="fa fa-cog"></i>
                    <span>systemLog</span>
                </a>
            </li>
            <li <c:if test="${param.type==8}">class="active"</c:if>>
                <a href="/redPacket/manage">
                    <i class="fa fa-credit-card"></i>
                    <span>redPacket</span>
                </a>
            </li>
        </ul>
    </section>
</aside>