package com.syz.community.dto;

import com.syz.community.exception.CustomizeErrorCode;
import com.syz.community.exception.CustomizeException;
import lombok.Data;
import org.springframework.web.servlet.ModelAndView;

@Data
public class ResultDTO<T> {
    private Integer code;
    private String message;
    private T data;

    public static ResultDTO errorOf(Integer code,String message){
        ResultDTO resultDTO = new ResultDTO();
        resultDTO.setCode(code);
        resultDTO.setMessage(message);
        return resultDTO;
    }

    public static ResultDTO errorOf(CustomizeErrorCode errorCode) {
        return errorOf(errorCode.getCode(),errorCode.getMessage());
    }

    public static Object errorOf(CustomizeException e) {
        return errorOf(e.getCode(),e.getMessage());
    }

    public static ResultDTO successOf(){
        ResultDTO resultDTO = new ResultDTO();
        resultDTO.setCode(200);
        resultDTO.setMessage("success");
        return resultDTO;
    }
    public static <T> ResultDTO successOf(T t){
        ResultDTO resultDTO = new ResultDTO();
        resultDTO.setCode(200);
        resultDTO.setMessage("success");
        resultDTO.setData(t);
        return resultDTO;
    }
}
