package com.dingup.onlinetest.bean;

import com.dingup.onlinetest.enums.ReadingQuestionTypeEnum;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by yanggavin on 16/3/12.
 */
public class ReadingQuestionXml {
    private String title;
    private ReadingQuestionTypeEnum type;
    private List<String> answer;
    private HashMap<String, String> options;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ReadingQuestionTypeEnum getType() {
        return type;
    }

    public void setType(ReadingQuestionTypeEnum type) {
        this.type = type;
    }

    public List<String> getAnswer() {
        return answer;
    }

    public void setAnswer(List<String> answer) {
        this.answer = answer;
    }

    public HashMap<String, String> getOptions() {
        return options;
    }

    public void setOptions(HashMap<String, String> options) {
        this.options = options;
    }

    @Override
    public String toString(){
        StringBuilder xmlBuilder = new StringBuilder();
        xmlBuilder.append("<root>");
        xmlBuilder.append(String.format("<title>%s</title>", title));
        xmlBuilder.append(String.format("<type>%s</type>", type));
        xmlBuilder.append(String.format("<answer>%s</answer>", answer));
        xmlBuilder.append("<options>");
        for (Map.Entry<String, String> entry : options.entrySet()){
            xmlBuilder.append(String.format("<%s>%s</%s>", entry.getKey(), entry.getValue(), entry.getKey()));
        }
        xmlBuilder.append("</options>");
        xmlBuilder.append("</root>");
        return xmlBuilder.toString();
    }

    public String toJsonString(){
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
