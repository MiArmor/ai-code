package com.xielaoban.aicode.service;

import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.core.service.IService;
import com.xielaoban.aicode.domain.dto.chatHistory.ChatHistoryQueryRequest;
import com.xielaoban.aicode.domain.entity.ChatHistory;
import com.xielaoban.aicode.domain.entity.User;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;

import java.time.LocalDateTime;

/**
 * 对话历史 服务层。
 *
 * @author <a href="https://github.com/MiArmor">Mi_amor</a>
 */
public interface ChatHistoryService extends IService<ChatHistory> {

    /**
     * 新增对话历史
     *
     * @param appId 应用id
     * @param message 消息内容
     * @param messageType 消息类型
     * @param userId 用户id
     * @return 添加结果
     */
    boolean addChatMessage(Long appId, String message, String messageType, Long userId);

    /**
     * 根据应用id删除对话历史
     *
     * @param appId 应用id
     * @return 删除结果
     */
    boolean deleteByAppId(Long appId);

    /**
     * 获取查询包装类
     *
     * @param chatHistoryQueryRequest 查询请求
     * @return 查询包装类
     */
    QueryWrapper getQueryWrapper(ChatHistoryQueryRequest chatHistoryQueryRequest);

    /**
     * 根据应用id分页查询对话历史
     *
     * @param appId 应用id
     * @param pageSize 页大小
     * @param lastCreateTime 最后一条记录的创建时间
     * @param loginUser 登录用户
     * @return 对话历史分页结果
     */
    Page<ChatHistory> listAppChatHistoryByPage(Long appId, int pageSize,
                                               LocalDateTime lastCreateTime,
                                               User loginUser);

    /**
     * 将对话历史从数据库加载到redis中
     *
     * @param appId 应用id
     * @param chatMemory 对话历史内存
     * @param maxCount 最大数量
     * @return 加载数量
     */
    int loadChatHistoryToMemory(Long appId, MessageWindowChatMemory chatMemory, int maxCount);
}

