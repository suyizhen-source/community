package com.syz.community.pojo;

import lombok.Data;

@Data
public class User {

  private long id;
  private String accountId;
  private String name;
  private String token;
  private long gmtCreate;
  private long gmtModified;
  private String avatarUrl;

}
