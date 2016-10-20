package com.jtechlib.view.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.view.View;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 负责页面跳转的工具类
 * Created by jianghan on 2016/10/20.
 */

public class ActivityJump {

    public static Builder build(@NonNull Activity currentActivity, @NonNull Class<?> targetClazz) {
        return new Builder(currentActivity, targetClazz);
    }

    /**
     * 构造器
     */
    public static class Builder {
        private ActivityOptionsCompat activityOptionsCompat;
        private Activity activity;
        private Intent intent;

        public Builder(Activity activity, Class<?> targetClazz) {
            this.activity = activity;
            //实例化intent
            intent = new Intent(activity, targetClazz);
        }

        public Builder addBundle(Bundle bundle) {
            intent.putExtras(bundle);
            return this;
        }

        public JumpBundle createBundle() {
            return new JumpBundle(null, this);
        }

        public JumpBundle createBundle(Bundle bundle) {
            return new JumpBundle(bundle, this);
        }

        public MakeAnimation makeBasic() {
            activityOptionsCompat = ActivityOptionsCompat.makeBasic();
            return new MakeAnimation(this);
        }

        public MakeAnimation makeTaskLaunchBehind() {
            activityOptionsCompat = ActivityOptionsCompat.makeTaskLaunchBehind();
            return new MakeAnimation(this);
        }

        public MakeAnimation makeClipRevealAnimation(View source, int startX, int startY, int width, int height) {
            activityOptionsCompat = ActivityOptionsCompat.makeClipRevealAnimation(source, startX, startY, width, height);
            return new MakeAnimation(this);
        }

        public MakeAnimation makeScaleUpAnimation(View source, int startX, int startY, int startWidth, int startHeight) {
            activityOptionsCompat = ActivityOptionsCompat.makeScaleUpAnimation(source, startX, startY, startWidth, startHeight);
            return new MakeAnimation(this);
        }

        public MakeAnimation makeCustomAnimation(int enterResId, int exitResId) {
            activityOptionsCompat = ActivityOptionsCompat.makeCustomAnimation(activity, enterResId, exitResId);
            return new MakeAnimation(this);
        }

        public MakeAnimation makeThumbnailScaleUpAnimation(View source, Bitmap thumbnail, int startX, int startY) {
            activityOptionsCompat = ActivityOptionsCompat.makeThumbnailScaleUpAnimation(source, thumbnail, startX, startY);
            return new MakeAnimation(this);
        }

        public MakeAnimation makeSceneTransitionAnimation(Pair<View, String>... sharedElements) {
            activityOptionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(activity, sharedElements);
            return new MakeAnimation(this);
        }

        public MakeSceneTransitionAnimation makeSceneTransitionAnimation() {
            return new MakeSceneTransitionAnimation(this);
        }

        /**
         * 发起跳转
         */
        public void jump() {
            ActivityCompat.startActivity(activity, intent, optionsToBundle());
        }

        /**
         * 发起跳转，带有回调
         *
         * @param requestCode
         */
        public void jumpForResult(int requestCode) {
            ActivityCompat.startActivityForResult(activity, intent, requestCode, optionsToBundle());
        }

        /**
         * 将动画配置转换成bundle
         *
         * @return
         */
        private Bundle optionsToBundle() {
            if (null == activityOptionsCompat) {
                return null;
            }
            return activityOptionsCompat.toBundle();
        }
    }

    /**
     * 创建动画
     */
    public static class MakeAnimation {
        private Builder builder;

        public MakeAnimation(Builder builder) {
            this.builder = builder;
        }

        public MakeAnimation addBundle(Bundle bundle) {
            builder.addBundle(bundle);
            return this;
        }

        public JumpBundle createBundle() {
            return new JumpBundle(null, builder);
        }

        public JumpBundle createBundle(Bundle bundle) {
            return new JumpBundle(bundle, builder);
        }

        public void jump() {
            builder.jump();
        }

        public void jumpForResult(int requestCode) {
            builder.jumpForResult(requestCode);
        }
    }

    /**
     * 创建场景元素转换动画
     */
    public static class MakeSceneTransitionAnimation extends MakeAnimation {
        private List<Pair> pairs;

        public MakeSceneTransitionAnimation(Builder builder) {
            super(builder);
            //实例化pair集合
            pairs = new ArrayList<>();
        }

        public <V extends View, K extends String> MakeSceneTransitionAnimation addPairs(V v, K k) {
            pairs.add(Pair.create(v, k));
            return this;
        }

        /**
         * 一次性将全部的pairs都加入到场景转换的动画中
         */
        private void putAllPairs() {
            super.builder.makeSceneTransitionAnimation(pairs.toArray(new Pair[pairs.size()]));
        }

        @Override
        public void jump() {
            putAllPairs();
            super.jump();
        }

        @Override
        public void jumpForResult(int requestCode) {
            putAllPairs();
            super.jumpForResult(requestCode);
        }
    }

    /**
     * 设置跳转动画中调用的bundle对象
     */
    public static class JumpBundle {
        private Builder builder;
        private Bundle bundle;

        public JumpBundle(Bundle bundle, Builder builder) {
            this.builder = builder;
            //实例化bundle
            this.bundle = null == bundle ? new Bundle() : new Bundle(bundle);
        }

        public JumpBundle putBoolean(@Nullable String key, boolean value) {
            bundle.putBoolean(key, value);
            return this;
        }

        public JumpBundle putInt(@Nullable String key, int value) {
            bundle.putInt(key, value);
            return this;
        }

        public JumpBundle putLong(@Nullable String key, long value) {
            bundle.putLong(key, value);
            return this;
        }

        public JumpBundle putDouble(@Nullable String key, double value) {
            bundle.putDouble(key, value);
            return this;
        }

        public JumpBundle putString(@Nullable String key, @Nullable String value) {
            bundle.putString(key, value);
            return this;
        }

        public JumpBundle putBooleanArray(@Nullable String key, @Nullable boolean[] value) {
            bundle.putBooleanArray(key, value);
            return this;
        }

        public JumpBundle putIntArray(@Nullable String key, @Nullable int[] value) {
            bundle.putIntArray(key, value);
            return this;
        }

        public JumpBundle putLongArray(@Nullable String key, @Nullable long[] value) {
            bundle.putLongArray(key, value);
            return this;
        }

        public JumpBundle putDoubleArray(@Nullable String key, @Nullable double[] value) {
            bundle.putDoubleArray(key, value);
            return this;
        }

        public JumpBundle putStringArray(@Nullable String key, @Nullable String[] value) {
            bundle.putStringArray(key, value);
            return this;
        }

        public JumpBundle putFloat(@Nullable String key, float value) {
            bundle.putFloat(key, value);
            return this;
        }

        public JumpBundle putSerializable(@Nullable String key, @Nullable Serializable value) {
            bundle.putSerializable(key, value);
            return this;
        }

        public JumpBundle putFloatArray(@Nullable String key, @Nullable float[] value) {
            bundle.putFloatArray(key, value);
            return this;
        }

        public MakeAnimation makeBasic() {
            getBuilder().makeBasic();
            return new MakeAnimation(getBuilder());
        }

        public MakeAnimation makeTaskLaunchBehind() {
            getBuilder().makeTaskLaunchBehind();
            return new MakeAnimation(getBuilder());
        }

        public MakeAnimation makeClipRevealAnimation(View source, int startX, int startY, int width, int height) {
            getBuilder().makeClipRevealAnimation(source, startX, startY, width, height);
            return new MakeAnimation(getBuilder());
        }

        public MakeAnimation makeScaleUpAnimation(View source, int startX, int startY, int startWidth, int startHeight) {
            getBuilder().makeScaleUpAnimation(source, startX, startY, startWidth, startHeight);
            return new MakeAnimation(getBuilder());
        }

        public MakeAnimation makeCustomAnimation(int enterResId, int exitResId) {
            getBuilder().makeCustomAnimation(enterResId, exitResId);
            return new MakeAnimation(getBuilder());
        }

        public MakeAnimation makeThumbnailScaleUpAnimation(View source, Bitmap thumbnail, int startX, int startY) {
            getBuilder().makeThumbnailScaleUpAnimation(source, thumbnail, startX, startY);
            return new MakeAnimation(getBuilder());
        }

        public MakeAnimation makeSceneTransitionAnimation(Pair<View, String>... sharedElements) {
            getBuilder().makeSceneTransitionAnimation(sharedElements);
            return new MakeAnimation(getBuilder());
        }

        public MakeSceneTransitionAnimation makeSceneTransitionAnimation() {
            return new MakeSceneTransitionAnimation(getBuilder());
        }

        private void addBundle() {
            builder.addBundle(bundle);
        }

        protected Builder getBuilder() {
            return builder;
        }

        public void jump() {
            addBundle();
            builder.jump();
        }

        public void jumpForResult(int requestCode) {
            addBundle();
            builder.jumpForResult(requestCode);
        }
    }
}