package cn.zz.Study.common;


import lombok.AllArgsConstructor;

/**
 * 运行时异常类 继承 RuntimeException
 * 检查时异常 继承 Exception
 * @author admin
 * @AllArgsConstructor 提供无参构造
 */
@AllArgsConstructor
public class CustomizeException extends RuntimeException{
    private final int code;

    public CustomizeException(ErrorCode errorCode){
        //Call to 'super()' must be first statement in constructor body
        //对“super（）”的调用必须是构造函数体中的第一条语句
        super(errorCode.getMsg());
        this.code = errorCode.getCode();
    }

    public CustomizeException(ErrorCode errorCode, String msg){
        super(msg);
        this.code = errorCode.getCode();
    }
}
