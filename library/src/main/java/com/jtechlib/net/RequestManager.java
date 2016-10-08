package com.jtechlib.net;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;

/**
 * 用于管理请求
 * Created by wuxubaiyang on 16/4/18.
 */
public class RequestManager {
    private List<Call> calls;

    public RequestManager() {
        calls = new ArrayList<>();
    }

    /**
     * 添加请求
     *
     * @param call
     */
    public RequestManager addCall(Call call) {
        if (null != call && null != calls) {
            calls.add(call);
        }
        return this;
    }

    /**
     * 取消全部请求
     */
    public void cancelAll() {
        if (null != calls) {
            for (int i = 0; i < calls.size(); i++) {
                Call call = calls.get(i);
                if (null != call && !call.isCanceled()) {
                    call.cancel();
                }
            }
        }
    }
}