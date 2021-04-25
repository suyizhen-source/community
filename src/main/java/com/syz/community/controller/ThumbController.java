package com.syz.community.controller;

import com.syz.community.enums.NotificationTypeEnum;
import com.syz.community.exception.CustomizeErrorCode;
import com.syz.community.exception.CustomizeException;
import com.syz.community.mapper.CommentMapper;
import com.syz.community.mapper.QuestionMapper;
import com.syz.community.mapper.ThumbMapper;
import com.syz.community.mapper.UserMapper;
import com.syz.community.model.*;
import com.syz.community.service.CommentService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class ThumbController {
    @Resource
    ThumbMapper thumbMapper;

    @Resource
    CommentMapper commentMapper;

    @Resource
    private QuestionMapper questionMapper;

    @Resource
    private UserMapper userMapper;

    @Resource
    private CommentService commentService;


    @RequestMapping(value = "/thumb/{commentId}/{thumbUserId}/{questionId}", method = RequestMethod.GET)
    @ResponseBody
    public List thumb(@PathVariable(name = "commentId") Integer commentId,
                      @PathVariable(name = "thumbUserId") Integer thumbUserId,
                      @PathVariable(name = "questionId") Integer questionId) {
        ThumbExample countExample = new ThumbExample();
        countExample.createCriteria().andThumbIdParentEqualTo(commentId).andThumbIdEqualTo(thumbUserId);
        long totalCount = thumbMapper.countByExample(countExample);
        Comment comment = commentMapper.selectByPrimaryKey(commentId);
        Question question = questionMapper.selectByPrimaryKey(questionId);
        User thumbUser = userMapper.selectByPrimaryKey(thumbUserId);
        List result= new ArrayList<>();
        //判断同一个用户对同一个评论是否点赞超过一次
        if (totalCount >= 1) {
            int i = thumbMapper.deleteByExample(countExample);
            ThumbExample thumbExample = new ThumbExample();
            thumbExample.createCriteria()
                    .andThumbIdParentEqualTo(commentId);
            long count = thumbMapper.countByExample(thumbExample);
            if (i>0){
                comment.setLikeCount((int) count);
                commentMapper.updateByPrimaryKey(comment);
                result.add(false);
                result.add(""+count);
                return result;
            }else {
                throw new CustomizeException(CustomizeErrorCode.THUMB_DELETE_FAIL);
            }
        }
        Thumb thumb = new Thumb();
        thumb.setThumbId(thumbUserId);
        thumb.setThumbIdParent(commentId);
        thumbMapper.insert(thumb);
        ThumbExample thumbExample = new ThumbExample();
        thumbExample.createCriteria()
                .andThumbIdParentEqualTo(commentId);
        long count = thumbMapper.countByExample(thumbExample);
        comment.setLikeCount((int) count);
        commentMapper.updateByPrimaryKey(comment);
        commentService.createNotify(comment, comment.getCommentator(), thumbUser.getName(), question, NotificationTypeEnum.THUMB_COMMENT);
        result.add(true);
        result.add(""+count);
        return result;
    }
}
