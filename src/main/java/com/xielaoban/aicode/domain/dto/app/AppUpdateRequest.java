package com.xielaoban.aicode.domain.dto.app;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
public class AppUpdateRequest implements Serializable {

    @Serial
    private static final long serialVersionUID = 6250641695569125547L;
    /**
     * 应用id
     */
    private Long id;
    /**
     * 应用名称
     */
    private String appName;

    /**
     * 封面
     */
    private String cover;

}
