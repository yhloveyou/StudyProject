package cn.zz.Study.service;

public interface LoginService {
    /**
     * 账号密码登录
     * @param phone
     * @param password
     * @return
     */
    String login(String phone,String password);
}
