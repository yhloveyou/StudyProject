package cn.zz.Study.Practice;

import cn.zz.Study.utils.RedisUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;
import java.time.Duration;

//测试Redis
@SpringBootTest
public class RedisTest {
    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    //有两个测试包，另一个不会启动整个项目之启动单个方法
    @Test
    public void redis() {
        //redis操作时没有该key也不会报错
        //根据key删除value
        redisTemplate.delete("key");
        //设置过期时间
        redisTemplate.expire("key", Duration.ofSeconds(10));
        //判断该key是否存在
        redisTemplate.hasKey("key");


        //String（key-value都是String）
        redisTemplate.opsForValue().set("StringTest", "90");
        redisTemplate.opsForValue().get("StringTest");


        //Set（数组而已，不会插入重复值）
        redisTemplate.opsForSet().add("NameArray", "嫉妒", "怠惰", "色欲", "强欲", "傲慢", "愤怒", "暴食", "虚饰");
        redisTemplate.opsForSet().members("NameArray");//根据Key取Value
        redisTemplate.opsForSet().remove("NameArray", "虚饰");//删除数组中其中一个value


        //List
        redisTemplate.opsForList().rightPush("大罪", "嫉妒");//右插入
        redisTemplate.opsForList().leftPush("大罪","强欲");//左插入
        redisTemplate.opsForList().remove("大罪",1,"嫉妒");//删除从左到右第一个符合value的元素 count=0删除所有
        redisTemplate.opsForList().index("大罪", 1);//获取指定元素, -1表示倒数第一个元素，以此类推
        redisTemplate.opsForList().range("大罪", 0, -1);//获取所有元素，也可获取从Start ==>>  end的元素


        //配置工具类后
        RedisUtils.set("大罪","愤怒",10);
    }

}
