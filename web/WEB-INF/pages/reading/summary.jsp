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
<script src="/js/jquery.min.js" type="text/javascript"></script>
<script type="text/javascript">
    $(function(){
        function seeYourAnswer(exam_id){
            var start = 'http://ibt.topschool.com/reading/get_summary_info.do';
            $.ajax({
                url:start,
                dataType:'jsonp',
                jsonp:'callback',
                data:'examination_id='+exam_id,
                success:function(data){
                    var list = [];
                    try{
                        if(data.code == 200 && typeof(data.result)!='undefined'){
                            var dr = data.result;
                            for(var i=0;i<dr.length;i++){
                                var item = dr[i];
                                list.push('<li class="clearfix">\
                                <span class="result-num"></span>\
                                <span class="result-question">Article '+ item.articleNum +'</span>\
                              </li>');
                                var ansList = item.answerInfoList;
                                for(var j=0;j<ansList.length;j++){
                                    var tempAns = ansList[j];
                                    list.push('<li class="clearfix">\
                                <span class="result-num">'+ Number(j+1) +'</span>\
                                <span class="result-question">'+ tempAns.questionTitle +'</span>\
                                <span class="result-answer">');
                                    var yourAns = tempAns.studentAnswer.toUpperCase();
                                    var corrAns = tempAns.correctAnswer.toUpperCase();
                                    if(yourAns == "UNDEFINED"){
                                        yourAns = '－' ;
                                    }
                                    if(yourAns == corrAns){
                                        list.push('<span class="ts-color-green ts-pr1">'+yourAns+'</span>');
                                    }else{
                                        list.push('<span class="ts-color-red ts-pr1">'+ yourAns +'</span>');
                                    }
                                    list.push('/<span class="ts-color-default ts-pl1">'+ corrAns +'</span>');
                                    list.push('</span>\
                              </li>');
                                }
                            }
                        }else{
                            alert('统计答案出错')
                        }
                    }
                    catch(e){}
                    $('.J_list').html(list.join(''));
                },
                error:function(data){
                    alert('统计答案接口调用错误error');
                }
            });
        }
        seeYourAnswer('test_ygl_20160319');
    });
</script>
</body>
</html>