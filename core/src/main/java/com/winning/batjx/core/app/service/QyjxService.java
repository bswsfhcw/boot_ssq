package com.winning.batjx.core.app.service;

import com.winning.batjx.core.common.domain.LoginInfo;
import com.winning.batjx.core.common.domain.LoginUser;

import java.util.Map;

/***
 * @Description:
 * @Param:
 * @return:
 * @Author: huchengwei
 * @Date: 2020/6/9
 */
public interface QyjxService {

    LoginUser queryLoginUser(Map<String, Object> params);

    LoginInfo verifyLogin(LoginInfo loginInfo) throws Exception;

}
