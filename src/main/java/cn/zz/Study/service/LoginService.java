package cn.zz.Study.service;

import cn.zz.Study.common.vo.LoginVO;

public interface LoginService {

    /**
     * 账号密码登录
     */
    String login(LoginVO loginVO);
}
