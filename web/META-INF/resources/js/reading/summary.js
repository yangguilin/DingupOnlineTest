$(function(){
    seeYourAnswer();
    function getQueryString(name){ //处理url地址后的参数
        var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
        var r = window.location.search.substr(1).match(reg);
        if (r != null) return unescape(r[2]);
        return null;
    }
    function seeYourAnswer() {
        var exam_id = getQueryString('examid');
        if (exam_id != null) {
            try {
                var start = '/reading/get_summary_info.do';
                $.ajax({
                    url: start,
                    dataType:'json',
                    type:'post',
                    data: 'examination_id=' + exam_id,
                    success: function (data) {
                        var list = [];
                        try {
                            if (data.code == 200 && typeof(data.result) != 'undefined') {
                                var dr = data.result;
                                for (var i = 0; i < dr.length; i++) {
                                    var item = dr[i];
                                    list.push('<li class="clearfix">\
                                <span class="result-num"></span>\
                                <span class="result-question">Article ' + item.articleNum + '</span>\
                              </li>');
                                    var ansList = item.answerInfoList;
                                    for (var j = 0; j < ansList.length; j++) {
                                        var tempAns = ansList[j];
                                        list.push('<li class="clearfix">\
                                <span class="result-num">' + Number(j + 1) + '</span>\
                                <span class="result-question">' + tempAns.questionTitle + '</span>\
                                <span class="result-answer">');
                                        var yourAns = tempAns.studentAnswer.toUpperCase();
                                        var corrAns = tempAns.correctAnswer.toUpperCase();
                                        if (yourAns == "UNDEFINED") {
                                            yourAns = '－';
                                        }
                                        if (yourAns == corrAns) {
                                            list.push('<span class="ts-color-green ts-pr1">' + yourAns + '</span>');
                                        } else {
                                            list.push('<span class="ts-color-red ts-pr1">' + yourAns + '</span>');
                                        }
                                        list.push('/<span class="ts-color-default ts-pl1">' + corrAns + '</span>');
                                        list.push('</span>\
                              </li>');
                                    }
                                }
                            } else {
                                alert('统计答案出错,请先做题');
                                window.location.href = '/reading';
                            }
                        }
                        catch (e) {
                        }
                        $('.J_list').html(list.join(''));
                    },
                    error: function (data) {
                        alert('统计答案接口调用错误error');
                    }
                });
            }
            catch(e) {
                window.location.href = '/reading';
            }

        } else {
            window.location.href = '/reading';
        }
    }
});