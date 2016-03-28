<%--
  Created by IntelliJ IDEA.
  User: yanggavin
  Date: 16/3/16
  Time: 下午10:53
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
    <title>阅读</title>
    <meta charset="utf-8">
    <meta name="keywords" content="TOEFL，阅读练习">
    <meta name="description" content="TOEFL，阅读练习">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="mobile-agent" content="format=html5; url=http://www.topschool.com">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no">
    <link href="/js/jquery-ui.min.css" rel="stylesheet" charset="utf-8" type="text/css"/>
    <link href="/css/common.css" rel="stylesheet" type="text/css">
    <link href="/css/reading/reading.css" rel="stylesheet" type="text/css">
    <link href="/css/reading/read-multi.css" rel="stylesheet" charset="utf-8" type="text/css"/>
</head>
<body>
<div class="topschool-main">
    <div class="ts-hide">
        <input type="hidden" name="firstLoad" value="true"> <!--文章第一次加载出来-->
        <input type="hidden" name="sname" value="test01"> <!-- 第几套题 -->
        <input type="hidden" name="aid" value="1"> <!-- 第几个文章 一套题里面3个文章 -->
        <input type="hidden" name="qnum" value="1"> <!-- 第几题 -->
    </div>
    <!-- head -->
    <div class="topschool-s-header">
        <div class="head-sign clearfix">
            <h1 class="logo">顶上教育 toefl</h1>
            <h2 class="tpo-title ts-f24"><span class="J_sub_name"></span> Reading</h2>
        </div>

        <div class="head-operation">
            <ul class="operation-btn-list clearfix">
                <!--<li><a class="view-review btn-total btn-review J_review">Review</a></li>
                <li><a class="btn-total btn-back btn-back-disabled J_back">Back</a></li>-->
                <li><a class="btn-total btn-next J_next">Next</a></li>
            </ul>
            <div class="duration-time ts-hide">
                <span class="time-out J_reset_time"></span>
                <a class="btn-total btn-showtime J_btn_showtime" d-oper="show">HIDE TIME</a>
            </div>
        </div>
        <h2 class="header-practice-title J_ques_num_info ts-hide">
            <span class="title-text">Question <span class="J_cur_Num"></span> of <span class="J_total_Num">14</span></span>
        </h2>

    </div>
    <!-- 答题 -->
    <div class="topschool-view-wrapper">
        <div class="view-text-content J_left_right_rgn">

            <div class="view-text-l">
                <div class="J_view_left ts-hide">
                    <div>
                        <p class="ts-question ts-f16 J_question_title"></p>
                        <ul class="ts-answer-list J_answers" style=""></ul>
                    </div>

                    <p class="view-read-use J_ques_tips"></p>
                    <p class="view-text-empty"></p>
                </div>
            </div>
            <div class="view-text-r J_view_right">
                <h2 class="view-text-r-tit J_article_title"></h2>
                <p class="view-text-r-main view-padding J_article_content"></p>
            </div>
        </div>
        <div class="view-text-content ts-hide J_up_down">
            <div class="ts-summary-content J_drag_title"></div>
            <div class="ts-read-real-answer">
                <ul class="ts-droplist clearfix J_final_answer"></ul>
            </div>
            <div class="ts-summary-content ts-f30 ts-align-center">Answer&nbsp;&nbsp;Choices</div>
            <div class="ts-read-choices">
                <ul class="ts-choices-ul clearfix J_choices_answer"></ul>
            </div>
        </div>
    </div>
</div>
<div class="J_first_dialog" title="Message!">
    <p>
    </p>
</div>
<script src="/js/jquery.min.js" type="text/javascript"></script>
<script src="/js/jquery-ui.min.js" type="text/javascript"></script>
<script src="/js/reading/reading.js" type="text/javascript"></script>
</body>
</html>
