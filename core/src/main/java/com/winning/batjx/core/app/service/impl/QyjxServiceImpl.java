package com.winning.batjx.core.app.service.impl;

import com.winning.batjx.core.common.util.DesUtil;
import com.winning.batjx.core.app.mapper.QyjxMapper;
import com.winning.batjx.core.app.service.QyjxService;
import com.winning.batjx.core.common.domain.LoginInfo;
import com.winning.batjx.core.common.domain.LoginUser;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/***
 * @Description: 
 * @Param:
 * @return:
 * @Author: huchengwei
 * @Date: 2020/6/9
 */
@Service
public class QyjxServiceImpl implements QyjxService {
    /**
     * roleMapper object
     */
    @Resource
    private QyjxMapper qyjxMapper;


    @Override
    public LoginUser queryLoginUser(Map<String,Object> params) {
        //对取出的内容封装其ksbm
        LoginUser loginUser=qyjxMapper.queryLoginUser(params);

        if(loginUser !=null && StringUtils.isNotBlank(loginUser.getKsbm())){
            String[] ksbm=loginUser.getKsbm().split(",");
            loginUser.setKsbm("'"+ StringUtils.join(ksbm, "','")+"'");
        }
        return loginUser;
    }
    /**
     * 验证登录
     *
     * @param loginInfo
     * @return
     */
    @Override
    public LoginInfo verifyLogin(LoginInfo loginInfo) throws Exception{
        //加密密码
        String password = DesUtil.encrypt(loginInfo.getPassword(), DesUtil.getKey());
        loginInfo.setPassword(password);
        return qyjxMapper.verifyLogin(loginInfo);
    }
}
