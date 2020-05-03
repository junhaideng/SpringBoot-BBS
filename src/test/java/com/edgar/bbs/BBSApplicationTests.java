package com.edgar.bbs;

import com.edgar.bbs.dao.UserDao;
import com.edgar.bbs.service.UserLoginService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

@SpringBootTest
class BBSApplicationTests {

    @Resource
    private UserDao userDao;

    @Resource
    private UserLoginService userLoginService;

    @Test
    void println(){
        System.out.println(userDao.findAllByUsername("Edgar"));
    }



    @Test
    void log(){
        Logger log = LoggerFactory.getLogger(getClass());
        log.info("Hello this is a test");
    }

}
