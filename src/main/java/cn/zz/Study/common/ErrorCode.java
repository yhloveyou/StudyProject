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

    //数据异常错误
    DATA_ERROR(20001,"不存在该数据，请刷新后重试")




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
