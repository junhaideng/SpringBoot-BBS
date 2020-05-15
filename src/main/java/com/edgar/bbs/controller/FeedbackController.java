package com.edgar.bbs.controller;

import com.edgar.bbs.dao.FeedbackDao;
import com.edgar.bbs.dao.MessageDao;
import com.edgar.bbs.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

@RestController
@Api("反馈")
@RequestMapping("/api/feedback")
public class FeedbackController {
    @Resource
    private FeedbackDao feedbackDao;

    @ApiOperation("提交反馈")
    @RequestMapping(value = "/submit", method = RequestMethod.POST)
    public Result submit(@RequestParam("title") String title, @RequestParam("email") String email, @RequestParam("content") String content, HttpSession session){
        String username = session.getAttribute("username").toString();
        if(username == null){
            return new Result(400, "登录之后才能反馈哦");
        }
        else {
            try{
                feedbackDao.insert(username, email, title, content);
                return new Result(200, "反馈成功，我们会尽快给予您回复");
            }catch (Exception e){
                e.printStackTrace();
                return new Result(400, "提交出现错误，请重新提交");
            }
        }
    }
}
