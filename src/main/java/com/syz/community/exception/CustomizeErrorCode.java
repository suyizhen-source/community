package com.syz.community.exception;

public enum CustomizeErrorCode implements ICustomizeErrorCode {
    NO_LOGIN(2000, "ログインしてください。"),
    QUESTION_NOT_FOUND(2001, "申し訳ございませんが、この問題が削除されました。"),
    TARGET_PARAM_NOT_FOUND(2002, "申し訳ございませんが、この問題をコメントすることができません。"),
    SYS_ERROR(2003, "システムエラーが発生しました。管理者と連絡してください。"),
    TYPE_PARAM_WRONG(2004, "申し訳ございませんが、予想外のエラーが発生しました。最初からやり直してください。"),
    COMMENT_NOT_FOUND(2005, "申し訳ございませんが、このコメントが削除されました。"),
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
