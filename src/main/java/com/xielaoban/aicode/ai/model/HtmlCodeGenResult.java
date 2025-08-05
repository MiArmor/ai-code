package com.xielaoban.aicode.ai.model;

import dev.langchain4j.model.output.structured.Description;
import lombok.Data;

@Data
@Description("生成HTML代码文件的结果")
public class HtmlCodeGenResult {

    @Description("生成的HTML代码")
    private String htmlCode;

    @Description("生成代码的描述")
    private String description;
}
