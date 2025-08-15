package com.xielaoban.aicode.workflow.node;

import com.xielaoban.aicode.ai.AiCodeGenTypeRoutingService;
import com.xielaoban.aicode.ai.AiCodeGenTypeRoutingServiceFactory;
import com.xielaoban.aicode.ai.enums.CodeGenTypeEnum;
import com.xielaoban.aicode.workflow.state.WorkflowContext;
import com.xielaoban.aicode.workflow.util.SpringContextUtil;
import lombok.extern.slf4j.Slf4j;
import org.bsc.langgraph4j.action.AsyncNodeAction;
import org.bsc.langgraph4j.prebuilt.MessagesState;

import static org.bsc.langgraph4j.action.AsyncNodeAction.node_async;

/**
 * 智能路由节点
 */
@Slf4j
public class RouterNode {

    public static AsyncNodeAction<MessagesState<String>> create() {
        return node_async(state -> {
            WorkflowContext context = WorkflowContext.getContext(state);
            log.info("执行节点: 智能路由");

            CodeGenTypeEnum generationType;
            try {
                // 获取AI路由服务并创建新的路由服务实例
                // 获取AI路由服务工厂并创建新的路由服务实例
                AiCodeGenTypeRoutingServiceFactory factory = SpringContextUtil.getBean(AiCodeGenTypeRoutingServiceFactory.class);
                AiCodeGenTypeRoutingService routingService = factory.createAiCodeGenTypeRoutingService();
                // 根据原始提示词进行智能路由
                generationType = routingService.routeCodeGenType(context.getOriginalPrompt());
                log.info("AI智能路由完成，选择类型: {} ({})", generationType.getValue(), generationType.getText());
            } catch (Exception e) {
                log.error("AI智能路由失败，使用默认HTML类型: {}", e.getMessage());
                generationType = CodeGenTypeEnum.HTML;
            }

            // 更新状态
            context.setCurrentStep("智能路由");
            context.setGenerationType(generationType);
            return WorkflowContext.saveContext(context);
        });
    }
}

