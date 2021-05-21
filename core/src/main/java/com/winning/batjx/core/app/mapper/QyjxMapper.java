package com.winning.batjx.core.app.mapper;

import com.winning.batjx.core.common.domain.LoginInfo;
import com.winning.batjx.core.common.domain.LoginUser;

import java.util.Map;

/***
 * @Description: 
 * @Param:
 * @return:
 * @Author: huchengwei
 * @Date: 、
 */

public interface QyjxMapper {

    LoginUser queryLoginUser(Map<String, Object> params);
    LoginInfo verifyLogin(LoginInfo loginInfo);
}