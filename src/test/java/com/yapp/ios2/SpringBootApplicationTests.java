package com.yapp.ios2;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestConfig.class)
@ActiveProfiles("test")
public class SpringBootApplicationTests {

    @Test
    public void contextLoads() {
    }
}