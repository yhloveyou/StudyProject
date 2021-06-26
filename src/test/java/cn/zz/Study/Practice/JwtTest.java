package cn.zz.Study.Practice;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@SpringBootTest
public class JwtTest {
    //签发对象
    private static final Long userId = 888L;
    //盐
    private static final String tokenYan = "yan";


    /**
     * 颁发 token
     */
    @Test
    public void issueJwt(){
        //过期时间 30 分钟
        Date time = Date.from(LocalDateTime.now().plusMinutes(30).atZone(ZoneId.systemDefault()).toInstant());

        //生成Token token分为三部分
        // 1、header（头），         作用：记录令牌类型、签名算法等 例如：{“alg":"HS256","type","JWT}  可以被Base64反编译
        // 2、payload（有效载荷），   作用：携带一些用户信息 例如{"userId":"1","username":"mingzi"}   可以被Base64反编译
        // 3、signature（签名），    作用：防止Token被篡改、确保安全性 例如 计算出来的签名，一个字符串
        String token = JWT.create()
                .withAudience(String.valueOf(userId))//签发对象
                .withIssuedAt(new Date())                //发行时间
                .withExpiresAt(time)                     //过期时间
                .withClaim("userName","张三") //自定义参数
                .sign(Algorithm.HMAC256(userId+tokenYan));//加密
        System.out.println(token);
    }

    /**
     * 解析 token
     */
    @Test
    public void analysisJwt(){
        JWTVerifier build = JWT.require(Algorithm.HMAC256(userId + tokenYan)).build();
        //获得颁发Token的内容
        DecodedJWT jwt = build.verify
                ("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI4ODgiLCJleHAiOjE2MjQyNjA3NTEsInVzZXJOYW1lIjoi5byg5LiJIiwiaWF0IjoxNjI0MjU4OTUxfQ.bjtVSp0-WK8kO7hl81_-SmqoX_3sGSKnd9woTFI6bgs");

        //打印token中所包含的信息
        System.out.println(jwt.getClaim("userName").asString());
        System.out.println(jwt.getExpiresAt());
        System.out.println(jwt.getAudience());
    }
}
