package com.jtechlib.model;

import java.io.Serializable;

import io.realm.RealmObject;

/**
 * 基础的realm数据对象
 * Created by jianghan on 2016/9/19.
 */
public class BaseRealmModel extends RealmObject implements Serializable {
    private long modelId = System.currentTimeMillis();

    public long getModelId() {
        return modelId;
    }

    public void setModelId(long modelId) {
        this.modelId = modelId;
    }
}