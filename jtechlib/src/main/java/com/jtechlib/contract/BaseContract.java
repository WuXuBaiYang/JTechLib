package com.jtechlib.contract;

/**
 * 协议基类
 * Created by wuxubaiyang on 16/5/5.
 */
public interface BaseContract {

    interface Presenter {
    }

    interface View {
        void setPresenter(BaseContract.Presenter presenter);
    }
}