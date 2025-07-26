package com.xielaoban.aicode.controller;

import com.xielaoban.aicode.common.BaseResponse;
import com.xielaoban.aicode.common.ResultUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/health")
public class HealthController {

    @GetMapping()
    public BaseResponse<String> healthCheck() {
        return ResultUtils.success("I'm OK");
    }
}
