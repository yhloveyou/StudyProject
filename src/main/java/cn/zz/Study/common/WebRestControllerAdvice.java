package cn.zz.Study.common;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author admin
 * 说白了就是给 Controller 层 添加 Try catch
 */
@RestControllerAdvice
public class WebRestControllerAdvice {
    @ExceptionHandler(CustomizeException.class)
    public String handlerCustomize(CustomizeException e){
        return Result.no(e.getMessage(),e.getCode());
    }
}
