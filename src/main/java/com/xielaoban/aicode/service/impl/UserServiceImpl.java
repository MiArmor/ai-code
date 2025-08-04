package com.xielaoban.aicode.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.xielaoban.aicode.constant.UserConstant;
import com.xielaoban.aicode.domain.dto.user.UserQueryRequest;
import com.xielaoban.aicode.domain.entity.User;
import com.xielaoban.aicode.domain.enums.UserRoleEnum;
import com.xielaoban.aicode.domain.vo.user.LoginUserVO;
import com.xielaoban.aicode.domain.vo.user.UserVO;
import com.xielaoban.aicode.exception.BusinessException;
import com.xielaoban.aicode.exception.ErrorCode;
import com.xielaoban.aicode.mapper.UserMapper;
import com.xielaoban.aicode.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 用户 服务层实现。
 *
 * @author <a href="https://github.com/MiArmor">Mi_amor</a>
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>  implements UserService{

    @Override
    public long userRegister(String userAccount, String userPassword, String checkPassword) {
        //1.校验
        if(StrUtil.hasBlank(userAccount, userPassword, checkPassword)){
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "参数为空");
        }
        if(userAccount.length() < 3){
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户账号长度不能小于3");
        }
        if(userPassword.length() < 6 || checkPassword.length() < 6){
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "密码长度不能小于6");
        }
        if(!userPassword.equals(checkPassword)){
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "两次密码不一致");
        }
        //2.检查是否重复
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("userAccount", userAccount);
        long count = this.mapper.selectCountByQuery(queryWrapper);
        if(count > 0){
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户账号已存在");
        }
        //3.加密
        String encryptPassword = getEncryptPassword(userPassword);
        //4.插入数据
        User user = new User();
        user.setUserAccount(userAccount);
        user.setUserPassword(encryptPassword);
        user.setUserName("默认名");
        user.setUserRole(UserRoleEnum.USER.getValue());
        boolean result = this.save(user);
        if(!result){
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "注册失败");
        }
        //5.返回id(主键回传)
        return user.getId();
    }

    @Override
    public String getEncryptPassword(String userPassword) {
        // 盐值，混淆密码
        final String SALT = "xielaoban";
        return DigestUtils.md5DigestAsHex((SALT + userPassword).getBytes(StandardCharsets.UTF_8));
    }

    @Override
    public LoginUserVO login(User user) {
        if(user == null){
            return null;
        }
        return BeanUtil.copyProperties(user, LoginUserVO.class);
    }

    @Override
    public LoginUserVO userLogin(String userAccount, String userPassword, HttpServletRequest request) {
        //1.校验
        if(StrUtil.hasBlank(userAccount, userPassword)){
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "参数为空");
        }
        if(userAccount.length() < 3){
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户账号长度不能小于3");
        }
        if(userPassword.length() < 6){
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "密码长度不能小于6");
        }
        //2.加密
        String encryptPassword = getEncryptPassword(userPassword);
        //3.检查账号密码
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("userAccount", userAccount);
        queryWrapper.eq("userPassword", encryptPassword);
        User user = this.mapper.selectOneByQuery(queryWrapper);
        if(user == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户账号不存在");
        }
        //4.记录用户的登录状态
        request.getSession().setAttribute(UserConstant.USER_LOGIN_STATE, user);
        //5.获得脱敏后的用户信息
        return this.login(user);
    }

    @Override
    public User getLoginUser(HttpServletRequest request) {
        //1.判断是否登录
        Object userObj = request.getSession().getAttribute(UserConstant.USER_LOGIN_STATE);
        User currentUser = (User) userObj;
        if(currentUser == null || currentUser.getId() == null) {
            throw new BusinessException(ErrorCode.NOT_LOGIN_ERROR);
        }
        //2.查询并返回
        long userId = currentUser.getId();
        currentUser = this.getById(userId);
        if(currentUser == null) {
            throw new BusinessException(ErrorCode.NOT_LOGIN_ERROR);
        }
        return currentUser;
    }

    @Override
    public boolean userLogout(HttpServletRequest request) {
        //1.判断是否登录
        Object userObj = request.getSession().getAttribute(UserConstant.USER_LOGIN_STATE);
        if(userObj == null) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "未登录");
        }
        //2.清除session
        request.getSession().removeAttribute(UserConstant.USER_LOGIN_STATE);
        return true;
    }

    @Override
    public UserVO getUserVO(User user) {
        if (user == null) {
            return null;
        }
        UserVO userVO = new UserVO();
        BeanUtil.copyProperties(user, userVO);
        return userVO;
    }

    @Override
    public List<UserVO> getUserVOList(List<User> userList) {
        if (CollUtil.isEmpty(userList)) {
            return new ArrayList<>();
        }
        return userList.stream().map(this::getUserVO).collect(Collectors.toList());
    }

    @Override
    public QueryWrapper getQueryWrapper(UserQueryRequest userQueryRequest) {
        if (userQueryRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "请求参数为空");
        }
        Long id = userQueryRequest.getId();
        String userAccount = userQueryRequest.getUserAccount();
        String userName = userQueryRequest.getUserName();
        String userProfile = userQueryRequest.getUserProfile();
        String userRole = userQueryRequest.getUserRole();
        String sortField = userQueryRequest.getSortField();
        String sortOrder = userQueryRequest.getSortOrder();
        return QueryWrapper.create()
                .eq("id", id)
                .eq("userRole", userRole)
                .like("userAccount", userAccount)
                .like("userName", userName)
                .like("userProfile", userProfile)
                .orderBy(sortField, "ascend".equals(sortOrder));
    }


}
