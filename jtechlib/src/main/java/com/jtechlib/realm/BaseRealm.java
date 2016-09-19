package com.jtechlib.realm;

import io.realm.Realm;
import io.realm.RealmModel;
import io.realm.RealmObject;
import io.realm.RealmResults;

/**
 * 数据库基础操作方法
 * Created by jianghan on 2016/8/31.
 */
public abstract class BaseRealm {
    /**
     * 插入
     *
     * @param clazz
     * @param realm
     * @param realmObject
     */
    public void insertModel(Class clazz, Realm realm, RealmObject realmObject) {
        realm.beginTransaction();
        realm.insert(realmObject);
        realm.commitTransaction();
    }

    /**
     * 更新
     *
     * @param realm
     * @param realmObject
     */
    public void updateModel(Realm realm, RealmObject realmObject) {
        realm.beginTransaction();
        realm.insertOrUpdate(realmObject);
        realm.commitTransaction();
    }

    /**
     * 删除
     *
     * @param clazz
     * @param realm
     */
    public void deleteModel(Class clazz, Realm realm) {
        realm.beginTransaction();
        realm.delete(clazz);
        realm.commitTransaction();
    }

    /**
     * 查询
     *
     * @param clazz
     * @param realm
     * @return
     */
    public RealmModel queryModel(Class clazz, Realm realm) {
        realm.beginTransaction();
        RealmModel realmModel = realm.where(clazz).findFirst();
        realm.commitTransaction();
        return realmModel;
    }

    /**
     * 查询数据集合
     *
     * @param clazz
     * @param realm
     * @return
     */
    public RealmResults queryModelList(Class clazz, Realm realm) {
        realm.beginTransaction();
        RealmResults realmResults = realm.where(clazz).findAll();
        realm.commitTransaction();
        return realmResults;
    }
}