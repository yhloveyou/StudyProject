package cn.zz.Study.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import lombok.experimental.Accessors;

/**
 * @author admin
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor
public class Result<T> {
    private boolean success;

    private int code;

    private String msg;

    private T data;

    public Result(T data){
        this.data = data;
    }

    public Result(int code ,String msg){
        this.code = code;
        this.msg = msg;
    }

    public Result(ErrorCode errorCode,T data){
        this.code = errorCode.getCode();
        this.msg = errorCode.getMsg();
        this.data = data;
    }

    // static

    private static final ObjectMapper OBJECTMAPPER = new ObjectMapper();

    public static String ok(){
        return new Result().setSuccess(true).setCode(ErrorCode.SUCCESS.getCode()).setMsg(ErrorCode.SUCCESS.getMsg()).mineToString();
    }

    /**
     * 骗骗javac编译器
     * 看上去是抛异常的实际上并不~~~
     */
    @SneakyThrows
    public String mineToString() {
        return OBJECTMAPPER.writeValueAsString(this);
    }
}
