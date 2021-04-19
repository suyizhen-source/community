package com.syz.community.controller;

import com.syz.community.mapper.CommentMapper;
import com.syz.community.mapper.ThumbMapper;
import com.syz.community.model.Comment;
import com.syz.community.model.Thumb;
import com.syz.community.model.ThumbExample;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

@Controller
public class ThumbController {
    @Resource
    ThumbMapper thumbMapper;

    @Resource
    CommentMapper commentMapper;

    @RequestMapping(value = "/thumb/{thumbParentId}/{thumbId}", method = RequestMethod.GET)
    @ResponseBody
    public String thumb(@PathVariable(name = "thumbParentId") Integer thumbParentId,
                        @PathVariable(name = "thumbId") Integer thumbId) {
        ThumbExample countExample = new ThumbExample();
        countExample.createCriteria().andThumbIdParentEqualTo(thumbParentId).andThumbIdEqualTo(thumbId);
        long totalCount = thumbMapper.countByExample(countExample);
        //判断同一个用户对同一个评论是否点赞超过一次
        if (totalCount >= 1) {
            ThumbExample thumbExample = new ThumbExample();
            thumbExample.createCriteria()
                    .andThumbIdParentEqualTo(thumbParentId);
            long count = thumbMapper.countByExample(thumbExample);
            return "" + count;
        }
        Thumb thumb = new Thumb();
        thumb.setThumbId(thumbId);
        thumb.setThumbIdParent(thumbParentId);
        thumbMapper.insert(thumb);
        ThumbExample thumbExample = new ThumbExample();
        thumbExample.createCriteria()
                .andThumbIdParentEqualTo(thumbParentId);
        long count = thumbMapper.countByExample(thumbExample);
        Comment comment = commentMapper.selectByPrimaryKey(thumbParentId);
        comment.setLikeCount((int) count);
        commentMapper.updateByPrimaryKey(comment);
        return "" + count;
    }
}
