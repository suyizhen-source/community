package com.syz.community.pojo;

import lombok.Data;

@Data
public class Question {

  private long id;
  private String title;
  private String description;
  private String tag;
  private long gmtCreate;
  private long gmtModified;
  private long creator;
  private long commentCount;
  private long viewCount;
  private long likeCount;

}
