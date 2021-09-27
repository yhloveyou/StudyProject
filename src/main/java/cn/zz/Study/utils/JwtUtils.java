package cn.zz.Study.utils;

import cn.zz.Study.common.CustomizeException;
import cn.zz.Study.common.ErrorCode;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;

/**
 * 它是html5新增的一个本地存储API，所谓localStorage就是一个小仓库的意思，
 * 它有5M的大小空间，存储在浏览器中，我们可以通过js来操纵localStorage。
 */
@Component
public class JwtUtils {
    private static String secret;

    @Value("${Jwt.secret}")
    public void getSecret(String secret) {
        JwtUtils.secret = secret;
    }


    /**
     * 签发对象：这个用户的id
     * 签发时间：现在
     * 有效时间：30分钟
     * 载荷内容：暂时设计为：这个人的名字，这个人的昵称
     * 加密密钥：这个人的id加上一串字符串
     */
    public static String createToken(String userId, String userName) {

        LocalTime hours = LocalTime.now().plusMinutes(30);
        Date times = Date.from(hours.atDate(LocalDate.now()).atZone(ZoneId.systemDefault()).toInstant());
        //签发对象
        return JWT.create().withAudience(userId)
                //发行时间
                .withIssuedAt(new Date())
                //有效时间
                .withExpiresAt(times)
                //载荷，随便写几个都可以
                .withClaim("userName", userName)
                //加密
                .sign(Algorithm.HMAC256(secret + "WDNMD"));
    }

    /**
     * 检查Token，获取Token中的内容
     */
    public static void verifyToken(String token) {
        try {
            JWT.require(Algorithm.HMAC256(secret + "WDNMD")).build().verify(token);
        } catch (Exception e) {
            //这里是token解析失败
            throw new CustomizeException(ErrorCode.TOKEN_ANALYSIS_FAIL);
        }
    }

    /**
     * 获取签发对象
     */
    public static String getAudience(String token) {
        String audience = null;
        try {
            audience = JWT.decode(token).getAudience().get(0);
        } catch (JWTDecodeException j) {
            //这里是token解析失败
            throw new CustomizeException(ErrorCode.TOKEN_ANALYSIS_FAIL);
        }
        return audience;
    }

    /**
     * 通过载荷名字获取载荷的值
     */
    public static Claim getClaimByName(String token, String name){
        return JWT.decode(token).getClaim(name);
    }
}
