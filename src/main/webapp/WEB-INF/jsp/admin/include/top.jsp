<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<header class="header">
    <a href="/web/manage" class="logo">兔小白个人博客</a>
    <nav class="navbar navbar-static-top">
        <div class="navbar-right">
            <ul class="nav navbar-nav">
                <li>
                    <div style="position: relative;display: block;padding: 15px 3px;">
                        <i class="fa fa-user"></i>
                        在线<span id="countOnline">0</span>人
                    </div>
                </li>
                <li>
                    <a href="javascript:logout();" class="dropdown-toggle">
                        <i class="fa fa-power-off"></i>
                        <span>注销</span>
                    </a>
                </li>
            </ul>
        </div>
    </nav>
</header>