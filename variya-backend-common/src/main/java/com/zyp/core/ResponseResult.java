package com.zyp.core;



import consts.ResultCodeConst;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * 响应体封装
 * @author zhangyunpeng
 * @date 2023/2/24 10:09
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseResult<T> {
    private Integer status;

    private T data;

    private String  msg;


    public static ResponseResult responseSuccess() {
        return new ResponseResult(null, ResultCodeConst.SUCCESS, "ok");
    }

    public static ResponseResult responseSuccess(Object data) {
        return new ResponseResult(ResultCodeConst.SUCCESS,  data, "ok");
    }

    public static ResponseResult responseError(Integer errorCode, String errorMsg) {
        return new ResponseResult(errorCode, null, errorMsg);
    }

    public static ResponseResult responseError(Integer errorCode, String errorMsg, Object data) {
        return new ResponseResult(errorCode, data, errorMsg);
    }
}
