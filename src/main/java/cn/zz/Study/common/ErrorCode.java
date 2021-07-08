package cn.zz.Study.common;

import com.baomidou.mybatisplus.annotation.EnumValue;

/**
 * @author admin
 */

public enum ErrorCode {
    //正常
    SUCCESS(0,"正常"),

    //常见错误
    PARAMETER_ERROR(10001,"参数错误"),
    LOGIN_ERROR(10002,"密码错误"),
    LOGIN_NUM_ERROR(10003,"登陆失败次数超过限制"),

    //数据异常错误
    DATA_ERROR(20001,"不存在该数据，请刷新后重试"),
    PASSWORD_ERROR(20002,"密码错误"),

    //Token解析失败
    TOKEN_ANALYSIS_FAIL(30001,"安全令牌解析失败，请重新登录或刷新重试"),




    ;
    @EnumValue
    private final int code;
    private final String msg;


    ErrorCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode(){
        return this.code;
    }

    public String getMsg(){
        return this.msg;
    }

}
