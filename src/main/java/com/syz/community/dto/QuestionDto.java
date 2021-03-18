package com.syz.community.dto;

import com.syz.community.model.User;
import lombok.Data;

@Data
public class QuestionDto {

    private Integer id;
    private String title;
    private String description;
    private String tag;
    private long gmtCreate;
    private long gmtModified;
    private Integer creator;
    private long commentCount;
    private long viewCount;
    private long likeCount;
    private User user;
}
