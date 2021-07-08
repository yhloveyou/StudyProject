package cn.zz.Study.common.vo;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author admin
 * 登录使用的接收参数VO
 */
@Data
@Accessors(chain = true)
public class LoginVO {
    /**
     * 账号
     */
    private String phone;

    /**
     * 密码
     */
    private String passWord;
}
