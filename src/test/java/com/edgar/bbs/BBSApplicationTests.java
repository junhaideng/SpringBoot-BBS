package com.edgar.bbs;

import com.edgar.bbs.dao.ArticleDao;
import com.edgar.bbs.dao.UserDao;
import com.edgar.bbs.domain.Article;
import com.edgar.bbs.service.UserService;
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
    private ArticleDao articleDao;

    @Resource
    private UserService userService;

    @Test
    void println(){
        System.out.println(userDao.findUserByUsername("Edgar1"));
        if(userDao.findUserById(2L).isPresent()){
            System.out.println("存在");
        }else{
            System.out.println("不存在");
        };
    }



    @Test
    void log(){
        Logger log = LoggerFactory.getLogger(getClass());
        log.info("Hello this is a test");
    }

    @Test
    void insert(){

        Article test = new Article();
        test.setContent("I am a test");
        test.setTitle("Test title");
        test.setUserId(1L);
        test.setId(4L);

        articleDao.saveAndFlush(test);

    }

}
