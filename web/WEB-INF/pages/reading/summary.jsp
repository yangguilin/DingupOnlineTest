<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<html>
<head>
    <meta charset="utf-8">
    <title>分数页</title>
    <meta name="keywords" content="TOEFL，阅读练习">
    <meta name="description" content="TOEFL，阅读练习">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="mobile-agent" content="format=html5; url=http://www.topschool.com">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no">
    <link rel="stylesheet" charset="utf-8" href="/css/common.css" type="text/css"/>
    <link rel="stylesheet" charset="utf-8" href="/css/reading/summary.css" type="text/css"/>
</head>
<body style="background:#efefef;">
<div class="topschool-main">
    <div class="ts-result">
        <div class="ts-restart clearfix">
            <a href="/reading">重新做题</a>
        </div>
        <div class="ts-result-title clearfix">
            <span class="ts-result-one">Question</span>
            <span class="ts-result-two">Yours/Correct</span>
        </div>

        <div class="ts-result-list">
            <ul class="result-list-ul J_list"></ul>
        </div>
    </div>
</div>
<script src="/js/jquery.1.11.3.min.js" type="text/javascript"></script>
<script src="/js/reading/summary.js" type="text/javascript"></script>
</body>
</html>