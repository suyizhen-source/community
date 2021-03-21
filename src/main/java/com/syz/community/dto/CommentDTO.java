package com.syz.community.dto;

import lombok.Data;

@Data
public class CommentDTO {
    private Integer id;
    private Integer parentId;
    private String content;
    private Integer type;
}
