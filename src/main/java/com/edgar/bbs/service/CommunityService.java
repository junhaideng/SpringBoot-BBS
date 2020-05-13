package com.edgar.bbs.service;

import com.edgar.bbs.dao.ArticleDao;
import com.edgar.bbs.dao.MessageDao;
import com.edgar.bbs.dao.ReplyDao;
import com.edgar.bbs.domain.Article;
import com.edgar.bbs.domain.Reply;
import com.edgar.bbs.utils.Result;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Optional;

@Service
public class CommunityService {
    @Resource
    private ReplyDao replyDao;

    @Resource
    private ArticleDao articleDao;

    @Resource
    private MessageDao messageDao;


    /*
    给对应的帖子回复
     */
    public Result writeReplyToArticle(Long article_id, String reply, String username) {
        try {
            Optional<Reply> r = replyDao.findReplyByArticleIdAndUsername(article_id, username);
            if (r.isPresent()) {
                return new Result(400, "您对该帖子已经进行回答了");
            }
            Article article = articleDao.findById(article_id).get();
            article.setComments(article.getComments() + 1);
            articleDao.saveAndFlush(article);
            replyDao.insertReply(article_id, reply, username);
            String author = articleDao.getUsernameById(article_id);
            messageDao.insert(article.getTitle(), String.format("%s评论了你的文章", username), author, "评论");
            return new Result(200, "回复成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(400, "回复失败");
        }
    }

    /*
    删除对应的回答
     */

    public Result deleteArticleById(Long article_id, String username) {
        Optional<Reply> reply = replyDao.findReplyByArticleIdAndUsername(article_id, username);
        try {
            if (reply.isPresent()) {
                replyDao.deleteReplyByUsername(username);
                return new Result(200, "删除成功");
            } else {
                return new Result(400, "您对该帖子并未进行回复");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(400, "删除失败");
        }
    }

}
