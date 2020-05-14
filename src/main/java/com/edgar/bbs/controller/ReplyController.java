package com.edgar.bbs.controller;

import com.edgar.bbs.dao.MessageDao;
import com.edgar.bbs.dao.ReplyDao;
import com.edgar.bbs.domain.Reply;
import com.edgar.bbs.service.CommunityService;
import com.edgar.bbs.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Optional;

@RestController
@Api("用户回答信息处理")
@RequestMapping("/api/reply")
public class ReplyController {
    @Resource
    private ReplyDao replyDao;

    @Resource
    private MessageDao messageDao;

    @Resource
    private CommunityService communityService;


    @ApiOperation("点赞回答")
    @RequestMapping(value = "/star_reply", method = RequestMethod.POST)
    public Result starReply(HttpSession session, @RequestParam("id") Long id, HttpServletRequest request){
        String username = session.getAttribute("username").toString();
        if(username==null){
            return new Result(400, "登录之后才能点赞哦");
        }

        Optional<Reply> reply = replyDao.findById(id);
        if(!reply.isPresent()){
            return new Result(400, "该回复不存在");
        }
        // 点赞数加一
        reply.get().setStar(reply.get().getStar()+1);
        replyDao.saveAndFlush(reply.get());

        // 用户 信息通知加一
        messageDao.insert(reply.get().getReply(),  String.format("%s点赞了你的回复", username), reply.get().getUsername(), "点赞", request.getRequestURL().toString());
        return new Result(200, "点赞成功");
    }

    @ApiOperation("取消回答")
    @RequestMapping(value = "/unstar_reply", method = RequestMethod.POST)
    public Result unStarReply(HttpSession session, @RequestParam("id") Long id){
        String username = session.getAttribute("username").toString();
        if(username==null){
            return new Result(400, "登录之后才能取消点赞!");
        }

        Optional<Reply> reply = replyDao.findById(id);
        if(!reply.isPresent()){
            return new Result(400, "该回复不存在");
        }
        // 点赞数加一
        reply.get().setStar(reply.get().getStar()-1);
        replyDao.saveAndFlush(reply.get());
        return new Result(200, "操作成功");
    }

    @ApiOperation("回答下的回复")
    @RequestMapping(value = "/comment", method = RequestMethod.POST)
    public Result comment(HttpSession session, @RequestParam("comment") String comment, @RequestParam("reply_id") Long reply_id, @RequestParam("url") String url){
        return communityService.writeCommentToReply(session, comment, url, reply_id);
    }


}
