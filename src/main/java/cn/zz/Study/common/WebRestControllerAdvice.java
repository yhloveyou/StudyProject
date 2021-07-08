package cn.zz.Study.common;

import cn.zz.Study.utils.customize.EmailUtils;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * @author admin
 * 说白了就是给 Controller 层 添加 Try catch
 */
@RestControllerAdvice
@Slf4j
public class WebRestControllerAdvice {
    @Resource
    private EmailUtils mailUtil;


    /**
     * Conroller层有自定义异常 会匹配到此
     */
    @ExceptionHandler(CustomizeException.class)
    public String handlerCustomize(CustomizeException e){
        return Result.no(e.getMessage(),e.getCode());
    }

    /**
     * 异常发送邮件
     */
    @ExceptionHandler(RuntimeException.class)
    public String emailHandler(RuntimeException e, HttpServletRequest req){
        log.error(e.getMessage());
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);
        String body = this.readBody(req);
        String content = String.format("url:%s\n请求参数:%s\n请求body:%s\n栈信息:%s", req.getRequestURL(), JSONObject.toJSONString(req.getParameterMap()), body, sw.toString());
        this.mailUtil.sendSimpleMail(content);
        return "服务器错误";
    }

    public String readBody(HttpServletRequest request) {

        BufferedReader br = null;
        StringBuilder sb = new StringBuilder("");
        try {
            br = request.getReader();
            String str;
            while ((str = br.readLine()) != null) {
                sb.append(str);
            }
            br.close();
        } catch (IOException e) {
            log.error("[严重异常]读取请求body异常", e);
        } finally {
            if (null != br) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return sb.toString();
    }
}
