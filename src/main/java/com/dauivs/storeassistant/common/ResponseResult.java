package com.dauivs.storeassistant.common;

/**
 * 请求响应结果
 */
public class ResponseResult {

    /** 响应结果状态  成功 */
    public static final int CODE_SUCCESS = 0;

    /** 响应结果状态  失败 */
    public static final int CODE_FAIL = 1;

    /** 响应结果状态  登录失效 */
    public static final int CODE_LOGIN_EXPIRED = -10;

    /** 响应结果消息  操作成功 */
    public static final String MESSAGE_SUCCESS = "操作成功";

    /** 响应结果消息  */
    public static final String MESSAGE_FAIL01 = "操作失败，请联系系统管理员";

    /** 响应结果消息 */
    public static final String MESSAGE_FAIL02 = "登录失效，请重新登录";

    private int code;

    private String message;

    private Object data;

    ResponseResult() {
    }

    ResponseResult(int code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    /**
     * 操作成功
     * @param data 响应结果数据
     * @param message 响应消息
     * @return
     */
    public static ResponseResult success(String message, Object data) {
        return new ResponseResult(CODE_SUCCESS, message, data);
    }

    /**
     * 操作成功
     * @param data 响应结果数据
     * @return
     */
    public static ResponseResult success(Object data) {
        return new ResponseResult(CODE_SUCCESS, MESSAGE_SUCCESS, data);
    }

    /**
     * 操作失败
     * @param message 响应结果消息
     * @param data 响应结果数据
     * @return
     */
    public static ResponseResult fail(String message, Object data) {
        return new ResponseResult(CODE_FAIL, message, data);
    }

    /**
     * 操作失败
     * @param message 响应结果消息
     * @return
     */
    public static ResponseResult fail(String message) {
        return new ResponseResult(CODE_FAIL, message, null);
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
