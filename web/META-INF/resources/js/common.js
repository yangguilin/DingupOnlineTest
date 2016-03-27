// 头部js
$(function(){
    var subjectName = $("input[name='sname']").val() ;//套题名称
    var articleid = $("input[name='aid']").val() ;//文章id（一套题里 有好几个文章）
    var isfirstLoad =$("input[name='firstLoad']").val(); //true－-是否第一次加载这个文章（一个阅读有好几个文章）
    var questionNum = $("input[name='qnum']").val(); //第几题，第一题的话back无法点击，默认1
    var articleJson,qaJson; //当前文章；以及该文章的所有题目
    var tags = /<[^>]*>/g; //<开头，>结尾
    var arrow = /<[^>]*q_[0-9]{1,2}_arrow>/g; //q_n_arrow标签
    var q_tag = /<[\/]?[q_][^>]*>/g; //q标签：<q_xxx>,</q_xxx>
    var q_tag_begin = /<[q_][^>]*>/g;
    var q_tag_end = /<[\/]{1}[q_][^>]*>/g;
    var p_tag_begin = /<[p][0-9]{1,}>/g; //p标签，<pxx>－空格替换；
    var p_tag_end = /<[\/]{1}[p][0-9]{1,}>/g; //</pxx>－换行

    /*
     1,根据url上套题参数 获取套题中的文章（处理所有的标签）＋获取问题列表
     2，进入后，默认第一个文章第一题，右侧滚动到底，才显示题目
     3，加载题目－－题目显示时，根据题目类型，来显示题和答案，
     并定位文章＋处理标签(定位文章－－根据第几题＋type)
     4，点击下一步，一个是保存数据，一个是获取下一个题目，并执行3

     type: arrow insert light multiple(check) summary
     */

    setcdTimer();//倒计时
    backBtnEvent(questionNum); //上一题 按钮设置
    //这里缺少一个加载文章的操作－调用桂林接口
    begin('test01');
    // loadArticle('','');
    loadQuestions(); //加载题目

    /*
     * 加载文章
     * subName:套题名称－先从url中传入参数；artid：文章id
     */
    function loadArticle(subName,artid){
        articleJson = '<p1><q_1_arrow>this is a page1111page1 page1.</p1><q_2_arrow><p2>this page2</p2>';//带标签的文章
        var aFinal = articleJson.replace(q_tag,''); //替换q_n_xx
        // alert(aFinal);
        aFinal = aFinal.replace(p_tag_begin,''); //替换<pn>
        // alert(aFinal);
        aFinal = aFinal.replace(p_tag_end,'<br>'); //替换</pn>
        // alert(aFinal);
        $('.J_article_content').html(aFinal);
        //处理标签－－根据左侧题目来处理？？？
    }

    /*http://test.topschool.com/reading/start_exam.do
     发送方式：POST
     参数1：subject_name*/
    function begin(subName){
        var start = 'http://test.topschool.com/reading/get_article.do'; //http://test.topschool.com/reading/start_exam.do';
        // $.ajax({
        //   url:start,
        //   dataType:'jsonp',
        //   data:'subject_name='+subName+'&article_num=1&question_num=1',
        //   success:function(data){
        //     debugger;
        //   },
        //   error:function(data){
        //     debugger;
        //   }
        // });

        $.ajax({
            url:start,
            dataType:'jsonp',
            jsonp:'callback',
            data:'subject_name='+subName+'&article_num=1',
            success:function(data){
                debugger;
            },
            error:function(data){
                debugger;
            }
        });

        // $.getJSON(start,{subject_name:'test01',article_num:'1',question_num:'1'},function(data){
        //   debugger;
        // }); //跨域问题   post也有跨域，

    }
    locateArticleByQues('arrow','2');
    function locateArticleByQues(type,q_num){
        //根据 question类型来定位文章
        articleJson = '<p1><q_1_arrow>this is a page1111page1 page1.<br><br><br><br><br><br><br></p1><q_2_arrow><p2>this page2</p2><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br>';//带标签的文章

        var locatag = '<q_'+q_num+'_'+type+'>'; //q_1_arrow
        var aFinal = articleJson; // articleJson.replace(q_tag,''); //替换q_n_xx
        // alert(aFinal);
        aFinal = aFinal.replace(p_tag_begin,''); //替换<pn>
        // alert(aFinal);
        aFinal = aFinal.replace(p_tag_end,'<br>'); //替换</pn>

        // debugger;
        aFinal = aFinal.replace(locatag,'<span class="strong-star J_scrollto">&diams;</span>');
        aFinal = aFinal.replace(q_tag,''); //
        // debugger;
        $('.J_article_content').html(aFinal);
        var locationTop = $('.J_scrollto').offset().top;
        $('.view-text-r').scrollTop(locationTop-250);//250是窗口到文章标题的距离

    }
    /*
     * 加载该篇文章的所有题目
     * subName:套题名称；artid：文章id
     */
    function loadQuestions(subName,artid){
        var qa ='{"title":"The word persistent in the passage is closest in meaning to","type":"LIGHT","answer":"c","options":{"d":"rfulpowerfulpowerfulpowerfulpowerfulpowerfulpowerfulpowerfulpowerfulpowerful","b":"fascinating","c":"lasting","a":"important"}},'
            +'{"title":"this is 2","type":"LIGHT","answer":"c","options":{"a":"aaa","b":"fascinating","c":"lasting","d":"important"}}';
        qaJson = eval('(' + qa + ')');

        $('.J_question').html(qaJson.title);
        var anObj = [];
        for(var op in qaJson.options){
            // debugger;//op - 对应key：a b c d  ；qaJson.options[op]－－value
            var ankey = op.toLocaleUpperCase();// A B C D
            anObj.push('<li>\
              <span class="an-formbg ts-an-radio">\
                <input type="radio" name="answer_0[]" id="answer_0['+op+']" value="'+ankey+'">\
              </span>\
              <div class="clearfix content-text">\
                <label for="answer_0['+op+']">\
                  <i class="option">'+ ankey +'. </i>\
                  <p class="main">'+qaJson.options[op]+'</p>\
                </label>\
              </div>\
          </li>');

        }
        $('.J_answers').html(anObj.join(''));
    }

    //‘下一步’按钮的点击事件
    $('.btn-next').click(function(){

        //第一次进入阅读页面，右侧文章滚动到底部才能next；next点击后出现题目
        //可视区域  $('.view-text-r').height(),  177
        //总高度    $('.view-text-r')[0].scrollHeight  1763
        // 滚动的高度 $('.view-text-r').scrollTop()  1585
        if(isfirstLoad){
            var clientHeight = $('.view-text-r').height(),
                scrolledHeight = $('.view-text-r').scrollTop(),
                totalHeight = $('.view-text-r')[0].scrollHeight;
            if((clientHeight+scrolledHeight+1)>=totalHeight){
                //next点击
                isfirstLoad = false;
                //next-显示题目和答案
                $('.J_view_left').removeClass('ts-hide');
            }else{
                alert('滚动条需要到最下面');
            }
        }else{
            $('.J_view_left').removeClass('ts-hide');
            //答题 的 下一步：1，保存答案 2，加载下一道题
            //3，判断是否是这个文章的最后一题，最后一题需要跳转到read-multi页面


        }
    });

    //页面load时判断back按钮
    function backBtnEvent(firstQ){
        if(firstQ=="1"){ //第一题
            // 且btn-back没有disabled
            if($('.btn-back').hasClass('btn-back-disabled')==false){
                $('.btn-back').addClass('btn-back-disabled');
            }

        }else{
            $('.btn-back').removeClass('btn-back-disabled');
        }
    }


    //页面加载时显示剩余的时间
    var countDownTime = 60*60 ;//倒计时的秒数60分钟
    function setcdTimer(){
        window.setInterval(function(){
            var hour=1,
                minute=0,
                second=0;//时间默认值
            var hourtext=''; //显示的小时 文本
            if(countDownTime > 0){
                hour = Math.floor(countDownTime / (60 * 60)) ;
                minute = Math.floor(countDownTime / 60) - (hour * 60);
                second = Math.floor(countDownTime) - (hour * 60 * 60) - (minute * 60);
            }
            if(hour<1){hourtext=''; }else{hourtext = '0'+hour+':';}
            if (minute <= 9) {minute = '0' + minute;}
            if (second <= 9) {second = '0' + second;}
            $('.J_reset_time').html(hourtext+''+minute+':'+second);
            countDownTime--;
        }, 1000);
    }

    //隐藏或展示时间
    $('.J_btn_showtime').click(function(){
        var curStatus = $(this).attr('d-oper');
        if(curStatus == "show"){ //当前展示时间，希望隐藏时间
            $(this).attr('d-oper','hide').text('SHOW TIME').siblings('.J_reset_time').hide();

        }else{ //当前没有展示时间，希望显示时间
            $(this).attr('d-oper','show').text('HIDE TIME').siblings('.J_reset_time').show();
        }
    });

    //插入段落
    var insertcont='<span class="strong-line J_insert_cont">this is insert test!</span>';
    $('.strong-insert').each(function(e){
        $(this).click(function(){
            $(this).toggleClass('ts-hide').after(insertcont);
            $(this).siblings('.strong-insert').removeClass('ts-hide').next('span').remove();
        });
    });


    //调用桂林接口，加载文章及题目


});
