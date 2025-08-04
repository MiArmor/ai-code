package com.xielaoban.aicode.service;

import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.core.service.IService;
import com.xielaoban.aicode.domain.dto.user.UserQueryRequest;
import com.xielaoban.aicode.domain.entity.User;
import com.xielaoban.aicode.domain.vo.user.LoginUserVO;
import com.xielaoban.aicode.domain.vo.user.UserVO;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

/**
 * 用户 服务层。
 *
 * @author <a href="https://github.com/MiArmor">Mi_amor</a>
 */
public interface UserService extends IService<User> {

    /**
     *
     * @param userAccount 用户账户
     * @param userPassword 用户密码
     * @param checkPassword 校验密码
     * @return 新用户id
     */
    long userRegister(String userAccount, String userPassword, String checkPassword);

    /**
     * 获取加密密码
     * @param userPassword
     * @return
     */
    String getEncryptPassword(String userPassword);


    /**
     * 返回登录用户信息
     *
     * @param user 用户
     * @return 脱敏的用户数据
     */
    LoginUserVO login(User user);

    /**
     * 用户登录
     *
     * @param userAccount  用户账户
     * @param userPassword 用户密码
     * @param request
     * @return 脱敏后的用户信息
     */
    LoginUserVO userLogin(String userAccount, String userPassword, HttpServletRequest request);

    /**
     * 获取当前登录用户
     *
     * @param request 请求
     * @return
     */
    User getLoginUser(HttpServletRequest request);

    /**
     * 用户注销
     *
     * @param request
     * @return
     */
    boolean userLogout(HttpServletRequest request);


    /**
     * 获取用户VO
     * @param user 用户
     * @return 用户VO
     */
    UserVO getUserVO(User user);

    /**
     * 获取用户VO列表
     * @param userList 用户列表
     * @return 用户VO列表
     */
    List<UserVO> getUserVOList(List<User> userList);


    /**
     * 获取查询条件
     * @param userQueryRequest 用户查询请求
     * @return 查询条件
     */
    QueryWrapper getQueryWrapper(UserQueryRequest userQueryRequest);
}
