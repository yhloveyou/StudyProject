package cn.zz.Study.Po;

import lombok.Data;

import java.util.Date;

//测试实体类
@Data
public class testPo {
    public testPo(){

    }

    public testPo(String name, Integer age, Date birthday) {
        this.name = name;
        this.age = age;
        this.birthday = birthday;
    }

    /**
     * 姓名
     */
    private String name;

    /**
     * 年龄
     */
    private Integer age;

    /**
     * 生日
     */
    private Date birthday;
}
