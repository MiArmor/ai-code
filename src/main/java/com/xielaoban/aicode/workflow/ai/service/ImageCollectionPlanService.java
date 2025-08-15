package com.xielaoban.aicode.workflow.ai.service;

import com.xielaoban.aicode.workflow.model.ImageCollectionPlan;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;

/**
 * 图片收集计划AI服务
 *
 */
public interface ImageCollectionPlanService {

    /**
     * 根据用户提示词分析需要收集的图片类型和参数
     */
    @SystemMessage(fromResource = "prompt/image-collection-plan-system-prompt.txt")
    ImageCollectionPlan planImageCollection(@UserMessage String userPrompt);
}
