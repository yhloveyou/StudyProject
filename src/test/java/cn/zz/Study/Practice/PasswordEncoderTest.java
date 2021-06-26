package cn.zz.Study.Practice;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
public class PasswordEncoderTest {

    @Test
    public void TestPasswordEncoder(){
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        //采用SHA-256 +随机盐+密钥对密码进行加密。
        // SHA系列是Hash算法，不是加密算法，使用加密算法意味着可以解密（这个与编码/解码一样），但是采用Hash处理，其过程是不可逆的。
        String encode = passwordEncoder.encode("1234");
        System.out.println(encode);

        //密码比较
        boolean matches = passwordEncoder.matches("1234", encode);
        System.out.println(matches);
    }

}
