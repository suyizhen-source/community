package com.syz.community.dto;

import com.syz.community.model.User;
import lombok.Data;

/**
 * Question+User DTO
 * */

@Data
public class QuestionDTO {

    private Integer id;
    private String title;
    private String description;
    private String tag;
    private Integer creator;
    private Integer commentCount;
    private Integer viewCount;
    private Integer likeCount;
    private long gmtCreate;
    private long gmtModified;
    private User user;
}
