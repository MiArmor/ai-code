package com.xielaoban.aicode.domain.dto.app;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
public class AppAddRequest implements Serializable {

    @Serial
    private static final long serialVersionUID = 7181407025317918048L;
    /**
     * 应用名称
     */
    private String appName;

    /**
     * 应用初始化的prompt
     */
    private String initPrompt;
}
