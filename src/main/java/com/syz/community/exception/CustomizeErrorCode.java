package com.syz.community.exception;

public enum CustomizeErrorCode implements ICustomizeErrorCode {
    NO_LOGIN(2000, "ログインしてください。"),
    QUESTION_NOT_FOUND(2001, "申し訳ございませんが、この問題が削除されました。"),
    TARGET_PARAM_NOT_FOUND(2002, "申し訳ございませんが、この問題をコメントすることができません。"),
    SYS_ERROR(2003, "システムエラーが発生しました。管理者と連絡してください。"),
    TYPE_PARAM_WRONG(2004, "申し訳ございませんが、予想外のエラーが発生しました。最初からやり直してください。"),
    COMMENT_NOT_FOUND(2005, "申し訳ございませんが、このコメントが削除されました。"),
    COMMENT_IS_EMPTY(2006, "コメントを入力してください。"),
    READ_NOTIFICATION_FAIL(2007, "この問題を見ることができません。"),
    NOTIFICATION_NOT_FOUND(2008, "申し訳ございませんが、このメッセージが削除されました。"),
    IMAGE_UPLOAD_FAIL(2009, "申し訳ございませんが、この写真をアップロードすることができません。。"),
    IMAGE_DELETE_FAIL(2010, "申し訳ございませんが、この写真を削除することができません。。"),
    ;

    private Integer code;
    private String message;

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public Integer getCode() {
        return code;
    }

    CustomizeErrorCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
