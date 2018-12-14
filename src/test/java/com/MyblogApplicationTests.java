package com;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
//WebEnvironment.DEFINED_PORT ：配置文件的启动端口
//WebEnvironment.RANDOM_PORT  : 随机端口号
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class MyblogApplicationTests {

    @Test
    public void contextLoads() {
    }

}
