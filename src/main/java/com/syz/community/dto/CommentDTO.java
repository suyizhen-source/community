package com.syz.community.dto;

import lombok.Data;

@Data
public class CommentDTO {
    private Long id;
    private Long parentId;
    private String content;
    private Integer type;
}
