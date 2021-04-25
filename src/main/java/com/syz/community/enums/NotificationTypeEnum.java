package com.syz.community.enums;

public enum NotificationTypeEnum {
    REPLY_QUESTION(1, "の質問を回答しました。"),
    REPLY_COMMENT(2, "のコメントについて回答しました。"),
    THUMB_COMMENT(3, "のコメントについていいねを押しました。");
    private int type;
    private String name;


    public int getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    NotificationTypeEnum(int status, String name) {
        this.type = status;
        this.name = name;
    }
    public static String nameOfType(int type) {
        for (NotificationTypeEnum notificationTypeEnum : NotificationTypeEnum.values()) {
            if (notificationTypeEnum.getType() == type) {
                return notificationTypeEnum.getName();
            }
        }
        return "";
    }
}
