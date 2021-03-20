package com.syz.community.controller;

import com.syz.community.dto.CommentDTO;
import com.syz.community.mapper.CommentMapper;
import com.syz.community.model.Comment;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.HashMap;

@Controller
public class CommentController {

    @Resource
    CommentMapper commentMapper;

    @RequestMapping(value = "/comment",method = RequestMethod.POST)
    @ResponseBody
    public Object post(@RequestBody CommentDTO commentDTO){
        Comment comment = new Comment();
        comment.setParentId(commentDTO.getParentId());
        comment.setContent(commentDTO.getContent());
        comment.setType(commentDTO.getType());
        comment.setCommentator(1);
        comment.setLikeCount(0L);
        comment.setGmtCreate(System.currentTimeMillis());
        comment.setGmtModified(System.currentTimeMillis());
        commentMapper.insert(comment);
        HashMap<Object, Object> objectObjectHashMap = new HashMap<>();
        objectObjectHashMap.put("message","success");
        return objectObjectHashMap;
    }

}
