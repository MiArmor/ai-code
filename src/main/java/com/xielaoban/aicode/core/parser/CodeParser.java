package com.xielaoban.aicode.core.parser;

/**
 * @Description: 代码解析器
 * @param <T> 解析后的结果类型（HtmlCodeGenResult和MultiFileCodeGenResult）
 */
public interface CodeParser<T> {
    /**
     * 解析代码内容
     *
     * @param codeContent 模型生成的原始内容
     * @return 解析后的结果
     */
    T parseCode(String codeContent);
}
