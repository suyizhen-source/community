package com.syz.community.dto;

import lombok.Data;

@Data
public class CommentCreateDTO {
    private Integer id;
    private Integer parentId;
    private String content;
    private Integer type;
}
