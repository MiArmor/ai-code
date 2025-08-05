package com.xielaoban.aicode.core.filesave;

import cn.hutool.core.util.StrUtil;
import com.xielaoban.aicode.ai.enums.CodeGenTypeEnum;
import com.xielaoban.aicode.ai.model.HtmlCodeGenResult;
import com.xielaoban.aicode.exception.BusinessException;
import com.xielaoban.aicode.exception.ErrorCode;

/**
 * HTML代码文件保存器
 *
 */
public class HtmlCodeFileSaverTemplate extends CodeFileSaverTemplate<HtmlCodeGenResult> {

    @Override
    protected CodeGenTypeEnum getCodeType() {
        return CodeGenTypeEnum.HTML;
    }

    @Override
    protected void saveFiles(HtmlCodeGenResult result, String baseDirPath) {
        // 保存 HTML 文件
        writeToFile(baseDirPath, "index.html", result.getHtmlCode());
    }

    @Override
    protected void validateInput(HtmlCodeGenResult result) {
        super.validateInput(result);
        // HTML 代码不能为空
        if (StrUtil.isBlank(result.getHtmlCode())) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "HTML代码内容不能为空");
        }
    }
}
