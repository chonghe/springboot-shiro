package com.cping.shirospringboot;

import com.cping.shirospringboot.mapper.UserMapper;
import com.cping.shirospringboot.pojo.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ShiroSpringbootApplicationTests {

    @Autowired
    UserMapper userMapper;
    @Test
    void contextLoads() {
        User zhangsan = userMapper.queryUserByName("zhangsan");
        System.out.println(zhangsan);
    }

}
