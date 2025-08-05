package com.xielaoban.aicode.ai;

import com.xielaoban.aicode.ai.model.HtmlCodeGenResult;
import com.xielaoban.aicode.ai.model.MultiFileCodeGenResult;
import dev.langchain4j.service.SystemMessage;
import reactor.core.publisher.Flux;

public interface AiCodeGeneratorService {

    /**
     * 生成单一HTML文件
     *
     * @param userMessage 用户消息
     * @return 生成的HTML代码
     */
    @SystemMessage(fromResource = "prompt/html-prompt.txt")
    HtmlCodeGenResult generateHtmlCode(String userMessage);


    /**
     * 生成多文件代码
     *
     * @param userMessage 用户消息
     * @return 生成的代码(HTML、CSS、JS)
     */
    @SystemMessage(fromResource = "prompt/multi-file-prompt.txt")
    MultiFileCodeGenResult generateMultiFileCode(String userMessage);


    /**
     * 生成单一HTML文件(流式返回)
     *
     * @param userMessage 用户消息
     * @return 生成的HTML代码
     */
    @SystemMessage(fromResource = "prompt/html-prompt.txt")
    Flux<String> generateHtmlCodeStream(String userMessage);


    /**
     * 生成多文件代码(流式返回)
     *
     * @param userMessage 用户消息
     * @return 生成的代码(HTML、CSS、JS)
     */
    @SystemMessage(fromResource = "prompt/multi-file-prompt.txt")
    Flux<String> generateMultiFileCodeStream(String userMessage);
}
