package com.example.businessmodule.event;

import com.example.businessmodule.utils.ResultCode;

/**
 * 事件基类 <br/>
 * 用来传递业务信息
 */
public abstract class BaseEvent<K, V> {

    /**
     * resultC
     **/
    public static class Result {

        private int resultCode = ResultCode.NONE;
        private String resultMsg = "";

        public Result(int code, String msg) {
            this.resultCode = code;
            this.resultMsg = msg;
        }

        public void setCode(int resultCode) {
            this.resultCode = resultCode;
        }

        public int getCode() {
            return resultCode;
        }

        public void setMessage(String resultMsg) {
            this.resultMsg = resultMsg;
        }

        public String getMessage() {
            return resultMsg;
        }
    }

    protected K request; // event request
    protected V response; // event response

    private long eventId = 0;
    public Result result = new Result(ResultCode.NONE, "");

    public BaseEvent(long eventId) {
        this.eventId = eventId;
    }

    /**
     * 获取事件 ID
     *
     * @return eventId
     */
    public long getEventId() {
        return eventId;
    }

    /**
     * 获取事件处理结果
     *
     * @return result code
     */
    public Result getResult() {
        return result;
    }

    public void setResult(int resultCode) {
        result.setCode(resultCode);
    }

    /**
     * 设置事件处理结果
     *
     * @param resultCode
     */
    public void setResult(int resultCode, String message) {
        result.setCode(resultCode);
        result.setMessage(message);
    }

    /**
     * @return result is success or failed
     */
    public boolean isSuccess() {
        return result != null && result.getCode() == ResultCode.SUCCESS;
    }

    /**
     * 设置请求内容
     *
     * @param request
     */
    public void setRequest(K request) {
        this.request = request;
    }

    /**
     * 获取请求内容
     *
     * @return request
     */
    public K request() {
        return this.request;
    }

    /**
     * 设置响应内容
     *
     * @param response
     */
    public void setResponse(V response) {
        this.response = response;
    }

    /**
     * 获取响应内容
     *
     * @return response
     */
    public V response() {
        return response;
    }

    public  V createResonse(){
        return  response;
    }
}
