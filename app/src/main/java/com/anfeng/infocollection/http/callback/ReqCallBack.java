package com.anfeng.infocollection.http.callback;

/**
 * 作者: huangtao on 2016/12/30.
 */

public interface ReqCallBack<T>
{
    /**
     * 响应成功
     */
    void onReqSuccess(T result);

    /**
     * 响应失败
     */
    void onReqFailed(String errorMsg);
}