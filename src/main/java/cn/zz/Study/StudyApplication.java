package cn.zz.Study;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author admin
 */
@SpringBootApplication
@MapperScan("cn.zz.Study.mapper")
public class StudyApplication {
    //测试
    public static void main(String[] args) {
        SpringApplication.run(StudyApplication.class, args);
    }

}
