package com.yapp.ios2;

import com.yapp.ios2.controller.UserControllerTest;
import com.yapp.ios2.testConfig.TestConfig;
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
    public void contextLoads() throws Exception{
        UserControllerTest userControllerTest = new UserControllerTest();
        userControllerTest.loginWithOnlyEmail();
        userControllerTest.loginWithEmailAndPhoneNum();
        userControllerTest.loginWithOnlyEmailButNoEmail();
        userControllerTest.check_phoneNum();
        userControllerTest.login_ErrorCode_C001();

    }
}