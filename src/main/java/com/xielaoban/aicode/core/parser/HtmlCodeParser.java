package com.xielaoban.aicode.core.parser;

import com.xielaoban.aicode.ai.model.HtmlCodeGenResult;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * html代码解析器
 */
public class HtmlCodeParser implements CodeParser<HtmlCodeGenResult> {
    //正则匹配
    private static final Pattern HTML_CODE_PATTERN = Pattern.compile("```html\\s*\\n([\\s\\S]*?)```", Pattern.CASE_INSENSITIVE);
    @Override
    public HtmlCodeGenResult parseCode(String codeContent) {
        HtmlCodeGenResult result = new HtmlCodeGenResult();
        //匹配html代码
        String htmlCode = extractHtmlCode(codeContent);
        if (htmlCode != null && !htmlCode.trim().isEmpty()) {
            result.setHtmlCode(htmlCode.trim());
        }else {
            //如果没有找到，将所有内容当作html返回
            result.setHtmlCode(codeContent.trim());
        }
        return result;
    }

    /**
     * 提取HTML
     *
     * @param codeContent
     * @return
     */
    private String extractHtmlCode(String codeContent) {
        Matcher matcher = HTML_CODE_PATTERN.matcher(codeContent);
        if (matcher.find()) {
            return matcher.group(1);
        } else {
            return null;
        }
    }

}

