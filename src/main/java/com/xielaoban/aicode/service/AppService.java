package com.xielaoban.aicode.service;

import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.core.service.IService;
import com.xielaoban.aicode.domain.dto.app.AppAddRequest;
import com.xielaoban.aicode.domain.dto.app.AppQueryRequest;
import com.xielaoban.aicode.domain.entity.App;
import com.xielaoban.aicode.domain.entity.User;
import com.xielaoban.aicode.domain.vo.app.AppVO;
import reactor.core.publisher.Flux;

import java.util.List;

/**
 * 应用 服务层。
 *
 * @author <a href="https://github.com/MiArmor">Mi_amor</a>
 */
public interface AppService extends IService<App> {

    AppVO getAppVO(App app);

    QueryWrapper getQueryWrapper(AppQueryRequest appQueryRequest);

    List<AppVO> getAppVOList(List<App> appList);

    /**
     * 流式输出
     *
     * @param appId 应用id
     * @param message 用户消息
     * @param loginUser 当前登录用户
     * @return AI生成结果流
     */
    Flux<String> chatToGenCode(Long appId, String message, User loginUser);

    String deployApp(Long appId, User loginUser);

    Long createApp(AppAddRequest appAddRequest, User loginUser);
    

    void generateAppScreenshotAsync(Long appId, String appUrl);
}
