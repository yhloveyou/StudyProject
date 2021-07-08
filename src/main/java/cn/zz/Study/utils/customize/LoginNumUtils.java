package cn.zz.Study.utils.customize;

import cn.zz.Study.common.CustomizeException;
import cn.zz.Study.common.ErrorCode;
import cn.zz.Study.common.RedisPrefixKey;
import cn.zz.Study.utils.RedisUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author admin
 * 限制登录次数
 * @value()使用 1.不能作用于静态变量（static）；
 * 2.不能作用于常量（final）;
 * 3.不能在非注册的类中使用（类需要被注册在spring上下文中，如用@Service,@RestController,@Component等）；
 * 4.使用这个类时，只能通过依赖注入的方式，用new的方式是不会自动注入这些配置的。
 */
@Component
public class LoginNumUtils {
    private static String num;
    private static String time;

    @Value("${LoginConfig.num}")
    public void getNum(String num) {
        LoginNumUtils.num = num;
    }

    @Value("${LoginConfig.time}")
    public void getTime(String time) {
        LoginNumUtils.time = time;
    }

    /**
     * 登陆失败次数 + 1
     */
    public static void loginFail(Long userId) {
        //登录次数限制 没有该Key则创建，有则不做操作
        RedisUtils.setNx(RedisPrefixKey.LOGIN_NUM.keyAppend(userId).getKey(), "1", Integer.parseInt(time));

        //获取已经登陆失败的次数
        String loginNum = RedisUtils.get(RedisPrefixKey.LOGIN_NUM.keyAppend(userId).getKey()).toString();
        if (loginNum.isBlank()) {
            return;
        }
        //登录次数超过限制抛出异常
        if (Long.parseLong(loginNum) >= Long.parseLong(num)) {
            //读取限制登陆时间，设置过期时间
            RedisUtils.expire(RedisPrefixKey.LOGIN_NUM.keyAppend(userId).getKey(), Long.valueOf(time));
            throw new CustomizeException(ErrorCode.LOGIN_NUM_ERROR);
        }

        //每次递增1
        RedisUtils.incr(RedisPrefixKey.LOGIN_NUM.keyAppend(userId).getKey(), 1L);
    }
}
