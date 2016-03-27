package com.dingup.onlinetest.util;

import com.dingup.onlinetest.bean.reading.ReadingQuestionXml;
import com.dingup.onlinetest.enums.ReadingQuestionTypeEnum;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.ByteArrayInputStream;
import java.util.*;

/**
 * Created by yanggavin on 16/3/12.
 */
public class XmlUtil {
    private final static String XML_ENCODING = "UTF-8";

    public static ReadingQuestionXml parseReadingQuestionXmlContent(String xmlContent){
        ReadingQuestionXml ret = null;
        if (xmlContent != null && !xmlContent.isEmpty()) {
            try {
                ByteArrayInputStream is = new ByteArrayInputStream(xmlContent.getBytes(XML_ENCODING));
                SAXReader saxReader = new SAXReader();
                Document doc = saxReader.read(is);
                Element root = doc.getRootElement();

                Element titleEle = root.element("title");
                Element typeEle = root.element("type");
                Element answerEle = root.element("answer");
                Element tipEle = root.element("tip");
                Element explEle = root.element("expl");
                Element optionsEle = root.element("options");
                if (titleEle != null
                        && typeEle != null
                        && answerEle != null
                        && !answerEle.getText().isEmpty()
                        && tipEle != null
                        && explEle != null){

                    ret = new ReadingQuestionXml();
                    ret.setTitle(titleEle.getText());
                    ret.setType(Enum.valueOf(ReadingQuestionTypeEnum.class, typeEle.getText().toUpperCase()));
                    ret.setAnswer(Arrays.asList(answerEle.getText()));
                    ret.setTip(tipEle.getText());
                    ret.setExpl(explEle.getText());
                    if (optionsEle != null) {
                        ret.setOptions(getOptionList(optionsEle.elements()));
                    } else {
                        ret.setOptions(new HashMap<String, String>());
                    }
                }
            } catch (Exception e) {
                ret = null;
            }
        }
        return ret;
    }

    private static HashMap<String, String> getOptionList(List<Element> elements){
        HashMap<String, String> retMap = new HashMap<>();
        for (Element ele : elements){
            retMap.put(ele.getName(), ele.getText());
        }
        // sort and reset.
        ArrayList<Map.Entry<String, String>> optionList = new ArrayList(retMap.entrySet());
        Collections.sort(optionList, new Comparator<Map.Entry<String, String>>() {
            public int compare(Map.Entry<String, String> o1, Map.Entry<String, String> o2) {
                return o1.getKey().compareTo(o2.getKey());
            }
        });
        retMap.clear();
        for (int i = 0; i < optionList.size(); i++){
            retMap.put(optionList.get(i).getKey(), optionList.get(i).getValue());
        }
        return retMap;
    }
}
