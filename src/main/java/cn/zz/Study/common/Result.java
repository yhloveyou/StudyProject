package cn.zz.Study.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import lombok.experimental.Accessors;


@Data
@Accessors(chain = true)
@NoArgsConstructor
/**
 *  * @author admin
 * 因为返回值花样比较多，因此定义泛型，T可以是任意类型
 */
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

    public static<T> String ok(){
        return new Result<T>()
                .setSuccess(true)
                .setCode(ErrorCode.SUCCESS.getCode())
                .setMsg(ErrorCode.SUCCESS.getMsg())
                .mineToString();
    }

    public static<T> String ok(T data){
        return new Result<T>()
                .setSuccess(true)
                .setCode(ErrorCode.SUCCESS.getCode())
                .setMsg(ErrorCode.SUCCESS.getMsg())
                .setData(data)
                .mineToString();
    }

    public static<T> String no(){
        return new Result<T>()
                .setSuccess(false)
                .setMsg(ErrorCode.FAIL.getMsg())
                .setCode(ErrorCode.FAIL.getCode())
                .mineToString();
    }

    public static<T> String no(ErrorCode errorCode){
        return new Result<T>()
                .setSuccess(false)
                .setCode(errorCode.getCode())
                .setMsg(errorCode.getMsg())
                .mineToString();
    }

    public static<T> String no(String msg){
        return new Result<T>()
                .setSuccess(false)
                .setMsg(msg)
                .setCode(ErrorCode.FAIL.getCode())
                .mineToString();
    }

    public static<T> String no(String msg,int code){
        return new Result<T>()
                .setSuccess(false)
                .setMsg(msg)
                .setCode(code)
                .mineToString();
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
