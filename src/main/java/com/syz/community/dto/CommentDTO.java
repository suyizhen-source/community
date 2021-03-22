package com.syz.community.dto;

import com.syz.community.model.User;
import lombok.Data;

@Data
public class CommentDTO {
    private Integer id;
    private Integer parentId;
    private Integer type;
    private Integer commentator;
    private String content;
    private Integer likeCount;
    private Integer commentCount;
    private Long gmtCreate;
    private Long gmtModified;
    private User user;
}
