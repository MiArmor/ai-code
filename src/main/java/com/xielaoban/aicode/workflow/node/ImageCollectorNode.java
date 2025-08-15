package com.xielaoban.aicode.workflow.node;

import com.xielaoban.aicode.workflow.ai.service.ImageCollectionPlanService;
import com.xielaoban.aicode.workflow.ai.service.ImageCollectionService;
import com.xielaoban.aicode.workflow.model.ImageCollectionPlan;
import com.xielaoban.aicode.workflow.model.ImageResource;
import com.xielaoban.aicode.workflow.state.WorkflowContext;
import com.xielaoban.aicode.workflow.tool.ImageSearchTool;
import com.xielaoban.aicode.workflow.tool.LogoGeneratorTool;
import com.xielaoban.aicode.workflow.tool.MermaidDiagramTool;
import com.xielaoban.aicode.workflow.tool.UndrawIllustrationTool;
import com.xielaoban.aicode.workflow.util.SpringContextUtil;
import lombok.extern.slf4j.Slf4j;
import org.bsc.langgraph4j.action.AsyncNodeAction;
import org.bsc.langgraph4j.prebuilt.MessagesState;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import static org.bsc.langgraph4j.action.AsyncNodeAction.node_async;

/**
 * 图片收集节点
 * 使用AI进行工具调用，收集不同类型的图片
 */
@Slf4j
public class ImageCollectorNode {

    public static AsyncNodeAction<MessagesState<String>> create() {
        return node_async(state -> {
            WorkflowContext context = WorkflowContext.getContext(state);
            String originalPrompt = context.getOriginalPrompt();
            List<ImageResource> collectedImages = new ArrayList<>();
            

            try {
                // 1.获取图片收集计划
                ImageCollectionPlanService planService = SpringContextUtil.getBean(ImageCollectionPlanService.class);
                ImageCollectionPlan plan = planService.planImageCollection(originalPrompt);
                log.info("获取到图片收集计划: {}，开始并发执行", plan);

                // 2.并发执行图片收集
                List<CompletableFuture<List<ImageResource>>> futures = new ArrayList<>();
                // 并发执行内容图片搜索
                if (plan.getContentImageTasks() != null){
                    ImageSearchTool imageSearchTool = SpringContextUtil.getBean(ImageSearchTool.class);
                    for (ImageCollectionPlan.ImageSearchTask contentImageTask : plan.getContentImageTasks()) {
                        futures.add(CompletableFuture.supplyAsync(() ->
                                imageSearchTool.searchContentImages(contentImageTask.query())));
                    }
                }
                // 并发执行插画搜索
                if (plan.getIllustrationTasks() != null){
                    UndrawIllustrationTool undrawIllustrationTool = SpringContextUtil.getBean(UndrawIllustrationTool.class);
                    for (ImageCollectionPlan.IllustrationTask illustrationTask : plan.getIllustrationTasks()) {
                        futures.add(CompletableFuture.supplyAsync(() ->
                                undrawIllustrationTool.searchIllustrations(illustrationTask.query())));
                    }
                }
                // 并发执行 架构 图表生成
                if (plan.getDiagramTasks() != null){
                    MermaidDiagramTool mermaidDiagramTool = SpringContextUtil.getBean(MermaidDiagramTool.class);
                    for (ImageCollectionPlan.DiagramTask mermaidDiagramTask : plan.getDiagramTasks()) {
                        futures.add(CompletableFuture.supplyAsync(() ->
                                mermaidDiagramTool.generateMermaidDiagram(mermaidDiagramTask.mermaidCode(), mermaidDiagramTask.description())));
                    }
                }
                // 并发执行 Logo 生成
                if (plan.getLogoTasks() != null) {
                    LogoGeneratorTool logoGeneratorTool = SpringContextUtil.getBean(LogoGeneratorTool.class);
                    for (ImageCollectionPlan.LogoTask logoTask : plan.getLogoTasks()) {
                        futures.add(CompletableFuture.supplyAsync(() ->
                                logoGeneratorTool.generateLogos(logoTask.description())));
                    }
                }

                // 3.等待所有任务完成并收集结果
                CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();
                // 收集所有结果
                for (CompletableFuture<List<ImageResource>> future : futures) {
                    List<ImageResource> images = future.get();
                    if (images != null) {
                        collectedImages.addAll(images);
                    }
                }
                log.info("并发图片收集完成，共收集到 {} 张图片", collectedImages.size());
            } catch (Exception e) {
                log.error("图片收集失败: {}", e.getMessage(), e);
            }
            // 更新状态
            context.setCurrentStep("图片收集");
            context.setImageList(collectedImages);
            return WorkflowContext.saveContext(context);
        });
    }
}
