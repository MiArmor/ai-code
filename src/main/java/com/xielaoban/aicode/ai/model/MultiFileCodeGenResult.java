package com.xielaoban.aicode.ai.model;

import dev.langchain4j.model.output.structured.Description;
import lombok.Data;

@Data
@Description("多文件代码的生成结果")
public class MultiFileCodeGenResult {

    @Description("生成代码的描述")
    private String description;

    @Description("html代码")
    private String htmlCode;

    @Description("javascript代码")
    private String jsCode;

    @Description("css代码")
    private String cssCode;
}
