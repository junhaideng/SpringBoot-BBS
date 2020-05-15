package com.edgar.bbs;

import com.edgar.bbs.dao.*;
import com.edgar.bbs.domain.Article;
import com.edgar.bbs.domain.Reply;
import com.edgar.bbs.service.JwcService;
import com.edgar.bbs.service.UserService;
import com.edgar.bbs.utils.FileUtil;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.io.File;
import java.util.*;

@SpringBootTest
class BBSApplicationTests {

    @Resource
    private UserDao userDao;

    @Resource
    private ArticleDao articleDao;

    @Resource
    private UserService userService;

    @Value("${upload.files}")
    private String PATH;


    @Test
    void log() {
        Logger log = LoggerFactory.getLogger(getClass());
        log.info("Hello this is a test");
    }

    @Test
    void insert() {

        Article test = new Article();
        test.setContent("I am a test");
        test.setTitle("Test title");
        test.setUsername("test");
        test.setId(4L);

        articleDao.saveAndFlush(test);

    }

    @Test
    void path() {
        System.out.println(this.getClass());
        System.out.println(System.getProperty("user.dir"));
        File file = new File(System.getProperty("user.dir"));
        String dir = file.getPath() + File.separator + "media/user/file";
        System.out.println(dir);
        System.out.println(getClass());
        System.out.println(File.separator);
        if (!file.exists()) {
            file.mkdir();


        } else {
            System.out.println("目录已经创建");
        }
    }

    @Test
    void getUpload() {

        System.out.println(PATH);

        HashMap<String, String> map = FileUtil.dealWithFileName("http");
        System.out.println(map.keySet());
        System.out.println(map.values());
        System.out.println(map.get("name"));
        System.out.println(map.get("suffix"));

    }

    @Resource
    private CarouselDao carouselDao;

    @Test
    void carousel() {
        carouselDao.insertCarousel(true, "/media/carousel", "hello");

    }

//    @Resource
//    private RedisUtils redisUtils;
//
//    @Test
//    void RedisTest() {
//        redisUtils.zadd("file", 10L, "1");
//        redisUtils.zadd("file", 11L, "2");
//        redisUtils.zadd("file", 12L, "3");
//        redisUtils.zincrby("file", "1", 1.0);
//        System.out.println();
//        Iterator<ZSetOperations.TypedTuple<String>> iterator = redisUtils.zrangeWithScores("file", 0L, -1L).iterator();
//        while (iterator.hasNext()) {
//            ZSetOperations.TypedTuple<String> next = iterator.next();
//            System.out.println(next.getScore());
//            System.out.println(next.getValue());
//        }
//        System.out.println(redisUtils.zrange("file", 0L, 2L));
//    }

    @Resource
    private JwcService jwcService;

    @Test
    void get() {
        jwcService.updateNewNotice();
    }


    @Resource
    private ReplyDao replyDao;
    @Resource
    private CommentDao commentDao;

    @Test
    void reply() {

        List<Map> list = new ArrayList<>();
        Long article_id = 2L;
        Long[] reply_id_list = replyDao.getRepliesIdByArticleId(article_id);
        for (Long id : reply_id_list) {
            Optional<Reply> reply = replyDao.findById(article_id);
            if (reply.isPresent()) {
                Map map = new HashMap();
                map.put("article", reply.get());
                System.out.println(commentDao.getCommentsByReply_id(id).toString());
                map.put("data", commentDao.getCommentsByReply_id(id).toString());
                list.add(map);
//                map.clear();
            }
        }
//        for (Map map1 : list) {
//            System.out.println(map1.toString());
//        }
        System.out.println(list.toString());
    }
}
