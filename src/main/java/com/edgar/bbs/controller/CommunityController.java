package com.edgar.bbs.controller;

import com.edgar.bbs.dao.ArticleDao;
import com.edgar.bbs.dao.ReplyDao;
import com.edgar.bbs.domain.Article;
import com.edgar.bbs.domain.JwcNotice;
import com.edgar.bbs.domain.Reply;
import com.edgar.bbs.service.CommunityService;
import com.edgar.bbs.service.JwcService;
import com.edgar.bbs.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;

@RestController
@Api("社区界面")
@RequestMapping("/api/community")
public class CommunityController {
    @Resource
    private ArticleDao articleDao;

    @Resource
    private ReplyDao replyDao;

    @Resource
    CommunityService communityService;

    @Resource
    private JwcService jwcService;

    @Value("${article.hot.num}")
    private Integer NUM;

    @ApiOperation("获取所有的文章")
    @RequestMapping("/get_all")
    public List<Article> getAllArticle(){
        return articleDao.findAll();
    }

    @ApiOperation("根据id获取文章")
    @RequestMapping(value = "/get_article", method = RequestMethod.POST)
    public Optional<Article> getArticleById(@RequestParam("id") Long id){
        return articleDao.findById(id);
    }

    @ApiOperation("查询文章对应的回答")
    @RequestMapping(value = "/get_reply", method = RequestMethod.POST)
    public List<Reply> getReplyById(@RequestParam("id") Long id){
        return replyDao.findAllByArticleId(id);
    }

    @ApiOperation("写回答")
    @RequestMapping(value = "/reply", method = RequestMethod.POST)
    public Result replyArticleById(@RequestParam("id") Long id,@RequestParam("reply") String reply, HttpSession session){
       String username = (String) session.getAttribute("username");
        if(username.equals("")){
            return new Result(400, "请登录");
        }else{
            return communityService.writeReplyToArticle(id, reply, username);
        }
    }

    @ApiOperation("获取热门帖子")
    @RequestMapping(value = "/hot", method = RequestMethod.POST)
    public List<Article> getHotArticle(@RequestParam("page") Integer page){
        page = (page-1) * NUM;
        return articleDao.findHotArticle( page);
    }

    @ApiOperation("获取教务处的通知信息")
    @RequestMapping(value = "/jwc")
    public List<JwcNotice> getJwcNotice(){
        return jwcService.getNewNotice();
    }
}
