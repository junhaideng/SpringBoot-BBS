package com.edgar.bbs.service;

import com.edgar.bbs.dao.ArticleDao;
import com.edgar.bbs.dao.CommentDao;
import com.edgar.bbs.dao.MessageDao;
import com.edgar.bbs.dao.ReplyDao;
import com.edgar.bbs.domain.Article;
import com.edgar.bbs.domain.Reply;
import com.edgar.bbs.utils.Result;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.*;

@Service
public class CommunityService {
    @Resource
    private ReplyDao replyDao;

    @Resource
    private ArticleDao articleDao;

    @Resource
    private MessageDao messageDao;

    @Resource
    private CommentDao commentDao;


    /*
    给对应的帖子回复
     */
    public Result writeReplyToArticle(Long article_id, String reply, String username, String url) {
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
            messageDao.insert(reply, String.format("%s评论了你的文章 %s", username, article.getTitle()), author, "评论", url);
            return new Result(200, "回复成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(400, "回复失败");
        }
    }

    /*
    给回答写评论
     */
    public Result writeCommentToReply(HttpSession session, String comment, String url, Long reply_id) {
        String username = (String) session.getAttribute("username");
        if (username == null) {
            return new Result(400, "请先登录");
        }
        Optional<Reply> reply = replyDao.findById(reply_id);

        if (reply.isPresent()) {
            reply.get().setComments(reply.get().getComments()+1);
            replyDao.saveAndFlush(reply.get());
            commentDao.insert(reply_id, comment, username);  // 评论插入到数据库中
            messageDao.insert(comment, String.format("%s 评论了你的回答", username), reply.get().getUsername(), "评论", url);
            return new Result(200, "评论成功");
        } else {
            return new Result(200, "评论的回答不存在");
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

    /*
    获取回答，以及下面的回复内容
     */
    public List<?> getReplyInfo(Long article_id) {
        List<Map> list = new ArrayList<>();
        Long[] reply_id_list = replyDao.getRepliesIdByArticleId(article_id);
        for (Long id : reply_id_list) {
            Optional<Reply> reply = replyDao.findById(id);
            if (reply.isPresent()) {
                Map map = new HashMap();
                map.put("article", reply.get());
                map.put("data", commentDao.getCommentsByReply_id(id));
                list.add(map);
            }
        }
        return list;
    }
}
