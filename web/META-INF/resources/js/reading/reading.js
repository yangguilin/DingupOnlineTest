var isfirstLoad =$("input[name='firstLoad']").val(); //true－-是否第一次加载这个文章（一个阅读有好几个文章）

var tags = /<[^>]*>/g; //<开头，>结尾
var q_tag = /<[\/]?[q_][^>]*>/g; //q标签：<q_xxx>,</q_xxx>
var q_tag_begin = /<[q_][^>]*>/g;//<q_xx_type>
var q_tag_end = /<[\/]{1}[q_][^>]*>/g;// </q_xx_type>
var p_tag = /<[\/]?[p][0-9]{1,}>/g; //p标签
var p_tag_begin = /<[p][0-9]{1,}>/g; //p标签，<pxx>－空格替换；
var p_tag_end = /<[\/]{1}[p][0-9]{1,}>/g; //</pxx>－换行

//url参数访问的格式：xxxx?sn=test01&an=1
var read = function(){
    this.left_ques_title = $('.J_question_title'); //左侧题目
    this.left_ques_answers = $('.J_answers'); //左侧答案
    this.left_ques_tips = $('.J_ques_tips'); //左侧答案下面的提示语句
    //左侧解析
    this.right_a_title = $('.J_article_title'); //右侧文章标题
    this.right_a_content = $('.J_article_content'); //右侧文章内容
    this.articleEnum ={
        arrow : 'arrow',
        insert : 'insert',
        light :'light',
        multiple :'multiple',
        summary :'summary'
    };
    this.subjectName = $("input[name='sname']").val() ;//套题名称
    this.articleid = $("input[name='aid']").val() ;//文章id（一套题里 有好几个文章）
    this.articleTitle = ''; //文章标题
    this.orgArticleJson = ''; //数据库中的文章内容
    this.qaList = []; //问题列表
    this.questionNum = $("input[name='qnum']").val(); //第几题，第一题的话back无法点击，默认1
    this.examination_id = ''; //开始考试的标志，保存答案时用 test_ygl_20160319
    this.init();
}

/*
 1,根据url上套题参数 获取套题中的文章（处理所有的标签）＋获取问题列表
 2，进入后，默认第一个文章第一题，右侧滚动到底，才显示题目
 3，加载题目－－题目显示时，根据题目类型，来显示题和答案，
 并定位文章＋处理标签(定位文章－－根据第几题＋type)
 4，点击下一步，一个是保存数据，
 同时还要判断问题列表里面是否都做完了，做完后，要加载下一个文章了。
 没有做完，就获取下一个题目并执行3

 type: arrow insert light multiple(check) summary
 */
read.prototype={
    init:function(){
        this.setDefaultParam();
        $('.J_sub_name').html(this.subjectName);
        this.events();
        if(isfirstLoad=='true' && this.articleid == 1){ //一次考试才写
            this.setStartExam();
        }

        if(isfirstLoad=="true"){ //第一次加载这个文章
            this.loadArticleWithoutTags(this.subjectName,this.articleid);

            this.loadAllQAS(this.subjectName,this.articleid);

            $("input[name='firstLoad']").val('false'); //是否第一次加载文章
        }else{


        }

    },
    setDefaultParam:function(){ //给固定的参数设置值
        //subject_name='+subName+'&article_num='+aid &qnum
        //isfirstload 根据url中的什么来判断呢？
        // sn=套题名称 an=文章id  －－目前只用这两个参数
        //先不做－根据题目id加载对应的题目  qn=题目id
        var that = this;

        var sn = that.getQueryString('sn');
        if(sn!=null){
            that.subjectName = sn;
        }
        var an = that.getQueryString('an');
        if(an!=null){
            that.articleid = an;
        }

    },
    getQueryString:function(name){ //处理url地址后的参数
        var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
        var r = window.location.search.substr(1).match(reg);
        if (r != null) return unescape(r[2]);
        return null;
    },
    events:function(){
        var that = this;
        $('.J_next').click(function(){
            that.setNextButton();
        });
        $('.J_review').click(function(){
            that.seeYourAnswer();
        });
        $('.J_view_right').scroll(function(){ //右侧文章div滚动时，判断是否到过底部
            that.setReadArticleFirst();
        });
        //$('.J_first_dialog').dialog({
        //    autoOpen: false,
        //    show: {
        //        effect: "blind",
        //        duration: 100
        //    }
        //});
    },
    loadArticleWithoutTags:function(subName,aid){//根据套题名称，文章id获取 文章
        var that = this;
        var start = 'http://ibt.topschool.com/reading/get_article.do';
        $.ajax({
            url:start,
            dataType:'jsonp',
            jsonp:'callback',
            data:'subject_name='+subName+'&article_num='+aid,
            success:function(data){

                if(data.code == 200 && typeof(data.result)!='undefined'){
                    that.articleTitle = data.result.articleTitle; //文章标题
                    that.orgArticleJson =  data.result.articleContent;//带标签的文章内容
                    var aContNoTags = that.orgArticleJson; //articleJson;
                    aContNoTags = aContNoTags.replace(q_tag,'')
                        .replace(p_tag_begin,'')
                        .replace(p_tag_end,'<br><br>');
                    // 将<q_xx_type></q_xx_type> <p1>都删掉，段落结尾标签</p1>要换行

                    that.right_a_content.html(aContNoTags);
                    that.right_a_title.html(that.articleTitle);
                }else{
                    alert('首次加载异常，请联系官方客服');
                }
            },
            error:function(data){
                alert('首次加载错误，请联系官方客服');
            }
        });

    },
    showUpDownRgn:function(){ //显示 拖拽结果的页面
        $('.J_up_down').removeClass('ts-hide');
        $('.J_left_right_rgn').addClass('ts-hide');
    },
    showLeftRightRgn:function(){ //显示 左右结果的内容区域
        $('.J_left_right_rgn').removeClass('ts-hide');
        $('.J_up_down').addClass('ts-hide');
    },
    loadCurQuesAnswer:function(curQuesAnswer){ //加载当前的题目及选项答案
        var that = this;
        that.showLeftRightRgn(); //默认显示左右结构的页面

        var tempType  = '';//当前题目类型
        try{
            tempType = curQuesAnswer.questionXml.type.toLowerCase();
        }catch(e){
            tempType = 'multiple';
        }

        var qNum = curQuesAnswer.questionNum ; //第几题（从 1 开始）

        var tempTagBeg = new RegExp('<q_'+qNum+'_'+tempType+'>','g');
        var tempTagEnd = new RegExp('</q_'+qNum+'_'+tempType+'>','g'); //当前题目的标签
        var tempArticleConts = that.orgArticleJson; //articleJson;

        var tNum = Number(qNum);
        var curQ = curQuesAnswer;
        if(tempType =="arrow"){//定位段落，参考common.js的locateArticleByQues，
            //arrow可以有多个，只定位到第一个，左侧radio
            //左侧题目
            var tempQuesTitle = curQ.questionXml.title;
            that.left_ques_title.html(tempQuesTitle);

            var tempTips = curQ.questionXml.tip;
            that.left_ques_tips.html(tempTips);

            var tempAnslist = curQ.questionXml.options;
            var nowA = [];
            nowA.push(that.setAnswersRadio(tempAnslist,'a'));
            nowA.push(that.setAnswersRadio(tempAnslist,'b'));
            nowA.push(that.setAnswersRadio(tempAnslist,'c'));
            nowA.push(that.setAnswersRadio(tempAnslist,'d'));

            that.left_ques_answers.html(nowA.join(''));

            //右侧文章
            tempArticleConts = tempArticleConts.replace(p_tag_begin,'')
                .replace(p_tag_end,'<br><br>')
                .replace(tempTagBeg,'<span class="strong-star J_scrollto">&diams;</span>')
                .replace(q_tag,'');
            that.right_a_content.html(tempArticleConts);
            $('.J_view_right').scrollTop(0); //加载完文章先默认定位到头部

            var locationTop = $('.J_scrollto').eq(0).offset().top;
            $('.J_view_right').scrollTop(locationTop-180);

        }else if(tempType =="insert"){//右侧文章中 插入一句话，左侧无radio.
            // 插入的那句话在哪里获取呢？题目中的tip吧？

            var tempQuesTitle = curQ.questionXml.title; //数据库数据有问题，先写死
            that.left_ques_title.html(tempQuesTitle);

            var tempTips = '<span class="strong-light J_insert_light">'+curQ.questionXml.tip+'</span>'; //;//数据库数据有问题，先写死
            that.left_ques_tips.html(tempTips);

            // var tempAnslist = curQ.questionXml.options;
            that.left_ques_answers.html('');

            //右侧文章，处理q_13_insert_a 标签
            var insertTagBeg = new RegExp('q_'+qNum+'_'+tempType+'_','g');
            var insertTagEnd = '/q_'+qNum+'_'+tempType+'_'; //暂时未用到
            var vTagEdn = /<\/v\w>/g; // 将</va>标签替换掉

            insertTagBeg = insertTagBeg
            tempArticleConts = tempArticleConts.replace(p_tag_begin,'')
                .replace(p_tag_end,'<br><br>')
                .replace(insertTagBeg,'v')
                .replace(q_tag,'');
            tempArticleConts = tempArticleConts.replace('<va>','<span class="strong-insert J_insert J_scrollto" data-value="a"></span>')
                .replace('<vb>','<span class="strong-insert J_insert J_scrollto" data-value="b"></span>')
                .replace('<vc>','<span class="strong-insert J_insert J_scrollto" data-value="c"></span>')
                .replace('<vd>','<span class="strong-insert J_insert J_scrollto" data-value="d"></span>');
            tempArticleConts = tempArticleConts.replace(vTagEdn,'');

            that.right_a_content.html(tempArticleConts);
            $('.J_view_right').scrollTop(0); //加载完文章先默认定位到头部

            var locationTop = $('.J_scrollto').eq(0).offset().top;
            $('.J_view_right').scrollTop(locationTop-180);

            $('.J_insert').each(function(e){ //插入

                var insertcont = $('.J_ques_tips').html();
                $(this).click(function(){
                    $(this).toggleClass('ts-hide').after(insertcont);
                    $(this).siblings('.J_insert').removeClass('ts-hide').next('.strong-light').remove();
                });
            });

        }else if(tempType =="light"){//高亮一个多个单词 或者 一句话，左侧radio
            //左侧题目
            var tempQuesTitle = curQ.questionXml.title;
            that.left_ques_title.html(tempQuesTitle);

            var tempTips = curQ.questionXml.tip;
            that.left_ques_tips.html(tempTips);

            var tempAnslist = curQ.questionXml.options;
            var nowA = [];
            nowA.push(that.setAnswersRadio(tempAnslist,'a'));
            nowA.push(that.setAnswersRadio(tempAnslist,'b'));
            nowA.push(that.setAnswersRadio(tempAnslist,'c'));
            nowA.push(that.setAnswersRadio(tempAnslist,'d'));
            that.left_ques_answers.html(nowA.join(''));

            //右侧文章
            tempArticleConts = tempArticleConts.replace(p_tag_begin,'')
                .replace(p_tag_end,'<br><br>')
                .replace(tempTagBeg,'<span class="strong-light J_scrollto">')
                .replace(tempTagEnd,'</span>')
                .replace(q_tag,'');
            that.right_a_content.html(tempArticleConts);
            $('.J_view_right').scrollTop(0); //加载完文章先默认定位到头部

            var locationTop = $('.J_scrollto').eq(0).offset().top;
            $('.J_view_right').scrollTop(locationTop-180);

        }else if(tempType.indexOf('multiple')>-1){//有可能定位段落，也可能light，暂时不管，且选项为checkbox
            //左侧题目
            var tempQuesTitle = curQ.questionXml.title;
            that.left_ques_title.html(tempQuesTitle);

            var tempTips = curQ.questionXml.tip;
            that.left_ques_tips.html(tempTips);

            var tempAnslist = curQ.questionXml.options;
            //先把object的答案列表options的key转换为数组[]
            var keys = [];
            for(var box in tempAnslist){
                keys.push(box);
            }
            keys = keys.sort();
            var nowA = ''; //[];//显示的题目 不明白为什么这个checkbox不能用数组，用了数组，显示时li后面会多个逗号
            // nowA.push(that.setAnswersCheckbox(tempAnslist,keys));
            nowA = that.setAnswersCheckbox(tempAnslist,keys);
            that.left_ques_answers.html(nowA);

            //右侧文章
            tempArticleConts = tempArticleConts.replace(q_tag,'')
                .replace(p_tag_begin,'')
                .replace(p_tag_end,'<br><br>');

            that.right_a_content.html(tempArticleConts);
            $('.J_view_right').scrollTop(0); //加载完文章先默认定位到头部


        }else if(tempType =="summary"){//6选3，新页面
            that.showUpDownRgn(); //显示 拖拽结构的页面
            var tempQuesTitle = curQ.questionXml.title;
            $('.J_drag_title').html(tempQuesTitle); //题目

            //最终答案：
            var spliReg = new RegExp(',','g'); //将正确答案里面的逗号去掉，根据答案长度，判断是一列还是两列
            var tempRight = curQ.questionXml.answer[0].replace(spliReg,'').length;
            if(tempRight>3){ //两列
                $('.J_final_answer').addClass('ts-droplist-table');
            }else{ //一列
                $('.J_final_answer').removeClass('ts-droplist-table');
            }


            var tempFinal = [];
            for(var k =0;k<tempRight;k++){
                if(k%2==0){
                    tempFinal.push('<li class="ts-read-drop J_droppable">');
                }else{
                    tempFinal.push('<li class="ts-read-drop J_droppable ts-no-right">');
                }

                tempFinal.push('<input type="hidden" name="ts-answer-drop" value="">\
                  <p class="ts-answer-itemp"></p>\
                </li>');
            }
            $('.J_final_answer').html(tempFinal.join(''));

            //answers list
            var tempAnslist = curQ.questionXml.options;//curQ.questionXml.options["a"]
            var tempChoices = []; //先把object的答案列表options的key转换为数组[]

            for(var box in tempAnslist){
                tempChoices.push(box);
            }
            tempChoices = tempChoices.sort(); //a-b顺序排序

            var allChoices = that.setAnswersList(tempAnslist,tempChoices);
            $('.J_choices_answer').html(allChoices.join(''));

            //js效果
            that.setDropDraggle();

        }

    },
    setDropDraggle:function(){ //6选3 summary 题型的拖拽效果
        //js效果
        $(".J_drag_box").draggable({
            revert: 'invalid',
            cursor:'move',
            // helper: 'clone',
            // connectToSortable: 'li.J_droppable'
        });

        $('.J_droppable').click(function(e){
            var sn = $(this).find('input:hidden').val();
            if(sn.length>0){ //点击的是3个div中有答案的

                $('.J_drag_box').each(function(){
                    var dv = $(this).attr('data-value');
                    if(dv == sn){
                        $(this).removeClass('ts-hide');
                    }
                });
                $(this).find('input:hidden').val('');
                $(this).droppable('enable').find('.ts-answer-itemp').html('');
            }

        }).droppable({
            drop:function(event,ui){
                var sn = $(ui.draggable).attr('data-value');
                $(this).find('input:hidden').val(sn);
                $(ui.draggable).addClass('ts-hide').animate({
                    "left": 0,
                    "top": 0
                });//.hide();
                $(this).find('.ts-answer-itemp').html($(ui.draggable).html());
                $(this).droppable('disable');
                //设置成不可用的disable是为了避免 当已经有选项时，其它选项拖拽到当前位置时，覆盖之前的选项内容
            }
        });
    },
    setAnswersRadio:function(obj,key){//加载radio类型的答案选项
        var tpA = [];
        if(typeof(obj[key])!='undefined'){
            tpA.push('<li>\
                  <span class="an-formbg ts-an-radio">\
                    <input type="radio" name="ts-answer" id="ts-answer_0['+key+']" value="'+key+'">\
                  </span>\
                  <div class="clearfix content-text">\
                    <label for="ts-answer_0['+key+']">\
                      <i class="option">'+key.toUpperCase()+'.  </i>\
                      <p class="main">'+obj[key]+'</p>\
                    </label>\
                  </div>\
                </li>');
        }
        return tpA;
    },
    setAnswersList:function(obj,keys){ //加载6选3类型的答案选项
        //obj － 数据库中的options；keys－排序后的a-g等
        var strList = []; //所有的答案列表
        var curIndex =1; //当前第几个，从1开始，2的倍数时，要给li多加个样式 ts-no-right
        for(var k=0;k<keys.length;k++){
            var tempItem = obj[keys[k]];
            if(curIndex%2 == 0){
                strList.push('<li class="ts-choice-box ts-no-right">');
            }else{
                strList.push('<li class="ts-choice-box">');
            }
            strList.push('<span class="sort-origin J_drag_box" data-value="'+ keys[k] +'">\
                <i>'+ keys[k].toUpperCase()+'.&nbsp;&nbsp;</i><p>'+tempItem+'</p>\
              </span>\
            </li>');
            curIndex++;
        }
        return strList;

    },
    setAnswersCheckbox:function(obj,keys){ //加载checkbox类型的答案选项
        var strList = ''; //所有的答案列表
        for(var k=0;k<keys.length;k++){
            var tempItem = obj[keys[k]];
            strList += '<li>\
        <span class="an-formbg ts-an-radio">\
          <input type="checkbox" name="ts-answer-chk" id="ts-answer_chk['+keys[k]+']" value="'+keys[k]+'">\
        </span>\
        <div class="clearfix content-text">\
          <label for="ts-answer_chk['+keys[k]+']">\
            <i class="option">'+keys[k].toUpperCase()+'.  </i>\
            <p class="main">'+tempItem+'</p>\
          </label>\
        </div>\
      </li>';

        }
        return strList;

    },
    loadAllQAS:function(subName,aid){ //根据套题名称，文章id获取这篇文章的所有问题
        var that = this;

        var start = 'http://ibt.topschool.com/reading/get_questions.do';
        $.ajax({
            url:start,
            dataType:'jsonp',
            jsonp:'callback',
            data:'subject_name='+subName+'&article_num='+aid,
            success:function(data){
                if(data.code == 200 && typeof(data.result)!='undefined'){
                    that.qaList = data.result;
                    $('.J_total_Num').html(that.qaList.length);
                }else{
                    alert('首次加载题目列表异常，请联系官方客服');
                }
            },
            error:function(data){
                alert('首次加载题目列表错误，请联系官方客服');
            }
        });
    },
    setNextButton:function(){ //按钮‘下一步’的事件
        //第一次进入阅读页面，右侧文章滚动到底部才能next；next点击后出现题目
        var that = this;

        if(isfirstLoad){
            //alert('');//读完文章
            alert('You should use the scroll bar to read the whole passage before you begin to answer the question. However, the passage will appear again with each question.');
            //$('.J_first_dialog').dialog({height: 140,
            //    modal: true});
            $('.J_ques_num_info').addClass('ts-hide');
        }else{
            $('.J_view_left').removeClass('ts-hide');
            $('.J_ques_num_info').removeClass('ts-hide');

            //保存答案
            var curQuesNum = Number(that.questionNum); //下一个加载的是第几题，从0开始， 0表示首次加载文章；1表示加载第一题

            if(curQuesNum <= 1){ // 等于1，表示当前首次加载文章或 加载第一题，不用保存答案；

            }else{ // >1 表示要加载第2、3...题了，需要提交答案了
                that.saveAnswer(that.qaList[curQuesNum-2]);
                //加载第2题，保存第一题的答案，第一题在qaList中的索引是0，所以－2
            }

            //然后加载下一题
            if(curQuesNum>0 && curQuesNum <= that.qaList.length){
                var teTy = that.qaList[curQuesNum-1]; //下一题目
                //根据类型加载当前问题
                that.loadCurQuesAnswer(teTy);

            }else{
                //加载另一个文章
                window.location.href='/reading/summary';
            }
            $('.J_cur_Num').html(that.questionNum);
            that.questionNum = curQuesNum + 1; //测试第6题  15; //

        }
    },
    setReadArticleFirst:function(){ //判断下首次加载文章是否看完过
        var clientHeight = $('.J_view_right').height(),
            scrolledHeight = $('.J_view_right').scrollTop(),
            totalHeight = $('.J_view_right')[0].scrollHeight;

        if((clientHeight+scrolledHeight+1)>=totalHeight){
            //文章读完过，可以加载题目了
            isfirstLoad = false;
        }
    },
    saveAnswer:function(curQuesAnswer){ //保存某一问题curQuesNum的答案
        var that = this;

        var tempType = '';
        if(typeof(curQuesAnswer.questionXml)=="undefined"){
            tempType = 'insert'; //这里这样判断，是因为数据库中的题目数据有问题
        }else{
            tempType = curQuesAnswer.questionXml.type.toLowerCase(); //题目类型
        }

        var selAnswer = ''; //选中的答案
        if(tempType == 'insert'){
            selAnswer = $('.J_view_right .J_insert_light').prev('J_insert').attr('data-value');

        }else if(tempType.indexOf('multiple')>-1){

            var last = [];
            $('input[name="ts-answer-chk"]:checked').each(function(){
                var tv = $(this).val();
                last.push(tv);
            });

            selAnswer = last.toString().toLowerCase();

        }else if(tempType == 'summary'){
            var last = [];
            $('input[name="ts-answer-drop"]').each(function(){
                var tv = $(this).attr('value').toLowerCase();
                if(typeof(tv)!='undefined' && tv.length>0){
                    last.push(tv);
                }
            });
            last = last.sort();
            selAnswer = last.toString().toLowerCase();
        }else{ //radio
            selAnswer = $('input[name="ts-answer"]:checked').val();
        }

        var start = 'http://ibt.topschool.com/reading/save_answer.do';
        var param = 'subject_name='+curQuesAnswer["subjectName"]+'&article_num='+curQuesAnswer["articleNum"]+'&question_num='+curQuesAnswer["questionNum"]+'&examination_id='+that.examination_id+'&answer='+selAnswer;
        $.ajax({
            url:start,
            dataType:'jsonp',
            jsonp:'callback',
            data:param,
            success:function(data){
                //code: 200, message: "答案保存成功", result: true
                if(data.code == 200 && typeof(data.result)!='undefined'){

                }else{
                    alert('保存答案失败，请联系管理员');
                }
            },
            error:function(data){
                alert('保存答案出现异常，请联系管理员');
            }
        });
    },
    setStartExam:function(){ //设置 开始考试
        var that = this;
        var start = 'http://ibt.topschool.com/reading/start_exam.do';
        var param = 'subject_name='+that.subjectName;
        $.ajax({
            url:start,
            dataType:'jsonp',
            jsonp:'callback',
            data:param,
            success:function(data){
                if(data.code == 200 && typeof(data.result)!='undefined'){
                    that.examination_id = data.result;
                }else{
                    alert('开始考试失败，请联系管理员');
                }
            },
            error:function(data){
                alert('开始考试异常error，请联系管理员');
            }
        });
    },
    seeYourAnswer:function(){ //查看答案
        window.location.href = 'summary.html';

    },

}


window.reading = new read();
