package com.jtechlib.Util;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.util.SparseArray;
import android.view.View;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 负责页面跳转的链式调用方法，集成ActivityOptionsCompat
 * Created by jianghan on 2016/10/19.
 */

public final class ActivityJump {

    private ActivityJump() {
    }

    /**
     * 开始构造跳转信息
     *
     * @param currentActivity
     * @param targetClazz
     * @return
     */
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

        public Builder(ActivityOptionsCompat activityOptionsCompat, Activity activity, Intent intent) {
            this.activityOptionsCompat = activityOptionsCompat;
            this.activity = activity;
            this.intent = intent;
        }

        private Builder(@NonNull Activity currentActivity, @NonNull Class<?> targetClazz) {
            this.activity = currentActivity;
            //实例化intent
            intent = new Intent(currentActivity, targetClazz);
        }

        /**
         * 添加一个bundle
         *
         * @param bundle
         * @return
         */
        public Builder addBundle(@NonNull Bundle bundle) {
            intent.putExtras(bundle);
            return this;
        }

        /**
         * 创建一个bundle，进入bundle的结构中
         *
         * @return
         */
        public JumpBundle createBundle() {
            return new JumpBundle(activityOptionsCompat, activity, intent);
        }

        /**
         * 创建一个bundle，进入bundle的结构中
         *
         * @param bundle
         * @return
         */
        public JumpBundle createBundle(@NonNull Bundle bundle) {
            return new JumpBundle(activityOptionsCompat, activity, intent, bundle);
        }

        public Builder makeBasic() {
            activityOptionsCompat = ActivityOptionsCompat.makeBasic();
            return this;
        }

        public Builder makeTaskLaunchBehind() {
            activityOptionsCompat = ActivityOptionsCompat.makeTaskLaunchBehind();
            return this;
        }

        public Builder makeClipRevealAnimation(View source, int startX, int startY, int width, int height) {
            activityOptionsCompat = ActivityOptionsCompat.makeClipRevealAnimation(source, startX, startY, width, height);
            return this;
        }

        public Builder makeScaleUpAnimation(View source, int startX, int startY, int startWidth, int startHeight) {
            activityOptionsCompat = ActivityOptionsCompat.makeScaleUpAnimation(source, startX, startY, startWidth, startHeight);
            return this;
        }

        public Builder makeCustomAnimation(int enterResId, int exitResId) {
            activityOptionsCompat = ActivityOptionsCompat.makeCustomAnimation(activity, enterResId, exitResId);
            return this;
        }

        public Builder makeThumbnailScaleUpAnimation(View source, Bitmap thumbnail, int startX, int startY) {
            activityOptionsCompat = ActivityOptionsCompat.makeThumbnailScaleUpAnimation(source, thumbnail, startX, startY);
            return this;
        }

        public JumpSceneTransition makeSceneTransitionAnimation() {
            return new JumpSceneTransition(activityOptionsCompat, activity, intent);
        }

        public Builder makeSceneTransitionAnimation(Pair<View, String>... sharedElements) {
            activityOptionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(activity, sharedElements);
            return this;
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
     * 页面跳转的元素动画构造器
     */
    public final static class JumpSceneTransition extends Builder {
        private List<Pair> pairs;

        public JumpSceneTransition(ActivityOptionsCompat activityOptionsCompat, Activity activity, Intent intent) {
            super(activityOptionsCompat, activity, intent);
            //实例化pair集合
            pairs = new ArrayList<>();
        }

        /**
         * 添加一个pair
         *
         * @param v
         * @param k
         * @param <V>
         * @param <K>
         * @return
         */
        public <V extends View, K extends String> JumpSceneTransition addPairs(V v, K k) {
            pairs.add(Pair.create(v, k));
            return this;
        }

        /**
         * 完成添加,用户不必主动调用
         *
         * @return
         */
        public Builder makeSceneTransitionDone() {
            return makeSceneTransitionAnimation(pairs.toArray(new Pair[pairs.size()]));
        }

        @Override
        public JumpBundle createBundle() {
            return makeSceneTransitionDone().createBundle();
        }

        @Override
        public JumpBundle createBundle(@NonNull Bundle bundle) {
            return makeSceneTransitionDone().createBundle(bundle);
        }
    }

    /**
     * 链式调用的bundle
     */
    public final static class JumpBundle extends Builder {
        private Bundle bundle;

        private JumpBundle(ActivityOptionsCompat activityOptionsCompat, Activity activity, Intent intent) {
            super(activityOptionsCompat, activity, intent);
            //实例化一个bundle
            bundle = new Bundle();
        }

        private JumpBundle(ActivityOptionsCompat activityOptionsCompat, Activity activity, Intent intent, @NonNull Bundle bundle) {
            super(activityOptionsCompat, activity, intent);
            this.bundle = bundle;
        }

        /**
         * Inserts a Boolean value into the mapping of this Bundle, replacing
         * any existing value for the given key.  Either key or value may be null.
         *
         * @param key   a String, or null
         * @param value a boolean
         */
        public JumpBundle putBoolean(@Nullable String key, boolean value) {
            bundle.putBoolean(key, value);
            return this;
        }

        /**
         * Inserts an int value into the mapping of this Bundle, replacing
         * any existing value for the given key.
         *
         * @param key   a String, or null
         * @param value an int
         */
        public JumpBundle putInt(@Nullable String key, int value) {
            bundle.putInt(key, value);
            return this;
        }

        /**
         * Inserts a long value into the mapping of this Bundle, replacing
         * any existing value for the given key.
         *
         * @param key   a String, or null
         * @param value a long
         */
        public JumpBundle putLong(@Nullable String key, long value) {
            bundle.putLong(key, value);
            return this;
        }

        /**
         * Inserts a double value into the mapping of this Bundle, replacing
         * any existing value for the given key.
         *
         * @param key   a String, or null
         * @param value a double
         */
        public JumpBundle putDouble(@Nullable String key, double value) {
            bundle.putDouble(key, value);
            return this;
        }

        /**
         * Inserts a String value into the mapping of this Bundle, replacing
         * any existing value for the given key.  Either key or value may be null.
         *
         * @param key   a String, or null
         * @param value a String, or null
         */
        public JumpBundle putString(@Nullable String key, @Nullable String value) {
            bundle.putString(key, value);
            return this;
        }

        /**
         * Inserts a boolean array value into the mapping of this Bundle, replacing
         * any existing value for the given key.  Either key or value may be null.
         *
         * @param key   a String, or null
         * @param value a boolean array object, or null
         */
        public JumpBundle putBooleanArray(@Nullable String key, @Nullable boolean[] value) {
            bundle.putBooleanArray(key, value);
            return this;
        }

        /**
         * Inserts an int array value into the mapping of this Bundle, replacing
         * any existing value for the given key.  Either key or value may be null.
         *
         * @param key   a String, or null
         * @param value an int array object, or null
         */
        public JumpBundle putIntArray(@Nullable String key, @Nullable int[] value) {
            bundle.putIntArray(key, value);
            return this;
        }

        /**
         * Inserts a long array value into the mapping of this Bundle, replacing
         * any existing value for the given key.  Either key or value may be null.
         *
         * @param key   a String, or null
         * @param value a long array object, or null
         */
        public JumpBundle putLongArray(@Nullable String key, @Nullable long[] value) {
            bundle.putLongArray(key, value);
            return this;
        }


        /**
         * Inserts a double array value into the mapping of this Bundle, replacing
         * any existing value for the given key.  Either key or value may be null.
         *
         * @param key   a String, or null
         * @param value a double array object, or null
         */
        public JumpBundle putDoubleArray(@Nullable String key, @Nullable double[] value) {
            bundle.putDoubleArray(key, value);
            return this;
        }

        /**
         * Inserts a String array value into the mapping of this Bundle, replacing
         * any existing value for the given key.  Either key or value may be null.
         *
         * @param key   a String, or null
         * @param value a String array object, or null
         */
        public JumpBundle putStringArray(@Nullable String key, @Nullable String[] value) {
            bundle.putStringArray(key, value);
            return this;
        }

        /**
         * Inserts a byte value into the mapping of this Bundle, replacing
         * any existing value for the given key.
         *
         * @param key   a String, or null
         * @param value a byte
         */
        public JumpBundle putByte(@Nullable String key, byte value) {
            bundle.putByte(key, value);
            return this;
        }

        /**
         * Inserts a char value into the mapping of this Bundle, replacing
         * any existing value for the given key.
         *
         * @param key   a String, or null
         * @param value a char
         */
        public JumpBundle putChar(@Nullable String key, char value) {
            bundle.putChar(key, value);
            return this;
        }

        /**
         * Inserts a short value into the mapping of this Bundle, replacing
         * any existing value for the given key.
         *
         * @param key   a String, or null
         * @param value a short
         */
        public JumpBundle putShort(@Nullable String key, short value) {
            bundle.putShort(key, value);
            return this;
        }

        /**
         * Inserts a float value into the mapping of this Bundle, replacing
         * any existing value for the given key.
         *
         * @param key   a String, or null
         * @param value a float
         */
        public JumpBundle putFloat(@Nullable String key, float value) {
            bundle.putFloat(key, value);
            return this;
        }

        /**
         * Inserts a CharSequence value into the mapping of this Bundle, replacing
         * any existing value for the given key.  Either key or value may be null.
         *
         * @param key   a String, or null
         * @param value a CharSequence, or null
         */
        public JumpBundle putCharSequence(@Nullable String key, @Nullable CharSequence value) {
            bundle.putCharSequence(key, value);
            return this;
        }

        /**
         * Inserts a Parcelable value into the mapping of this Bundle, replacing
         * any existing value for the given key.  Either key or value may be null.
         *
         * @param key   a String, or null
         * @param value a Parcelable object, or null
         */
        public JumpBundle putParcelable(@Nullable String key, @Nullable Parcelable value) {
            bundle.putParcelable(key, value);
            return this;
        }

        /**
         * Inserts an array of Parcelable values into the mapping of this Bundle,
         * replacing any existing value for the given key.  Either key or value may
         * be null.
         *
         * @param key   a String, or null
         * @param value an array of Parcelable objects, or null
         */
        public JumpBundle putParcelableArray(@Nullable String key, @Nullable Parcelable[] value) {
            bundle.putParcelableArray(key, value);
            return this;
        }

        /**
         * Inserts a List of Parcelable values into the mapping of this Bundle,
         * replacing any existing value for the given key.  Either key or value may
         * be null.
         *
         * @param key   a String, or null
         * @param value an ArrayList of Parcelable objects, or null
         */
        public JumpBundle putParcelableArrayList(@Nullable String key, @Nullable ArrayList<? extends Parcelable> value) {
            bundle.putParcelableArrayList(key, value);
            return this;
        }

        /**
         * Inserts a SparceArray of Parcelable values into the mapping of this
         * Bundle, replacing any existing value for the given key.  Either key
         * or value may be null.
         *
         * @param key   a String, or null
         * @param value a SparseArray of Parcelable objects, or null
         */
        public JumpBundle putSparseParcelableArray(@Nullable String key, @Nullable SparseArray<? extends Parcelable> value) {
            bundle.putSparseParcelableArray(key, value);
            return this;
        }

        /**
         * Inserts an ArrayList<Integer> value into the mapping of this Bundle, replacing
         * any existing value for the given key.  Either key or value may be null.
         *
         * @param key   a String, or null
         * @param value an ArrayList<Integer> object, or null
         */
        public JumpBundle putIntegerArrayList(@Nullable String key, @Nullable ArrayList<Integer> value) {
            bundle.putIntegerArrayList(key, value);
            return this;
        }

        /**
         * Inserts an ArrayList<String> value into the mapping of this Bundle, replacing
         * any existing value for the given key.  Either key or value may be null.
         *
         * @param key   a String, or null
         * @param value an ArrayList<String> object, or null
         */
        public JumpBundle putStringArrayList(@Nullable String key, @Nullable ArrayList<String> value) {
            bundle.putStringArrayList(key, value);
            return this;
        }

        /**
         * Inserts an ArrayList<CharSequence> value into the mapping of this Bundle, replacing
         * any existing value for the given key.  Either key or value may be null.
         *
         * @param key   a String, or null
         * @param value an ArrayList<CharSequence> object, or null
         */
        public JumpBundle putCharSequenceArrayList(@Nullable String key, @Nullable ArrayList<CharSequence> value) {
            bundle.putCharSequenceArrayList(key, value);
            return this;
        }

        /**
         * Inserts a Serializable value into the mapping of this Bundle, replacing
         * any existing value for the given key.  Either key or value may be null.
         *
         * @param key   a String, or null
         * @param value a Serializable object, or null
         */
        public JumpBundle putSerializable(@Nullable String key, @Nullable Serializable value) {
            bundle.putSerializable(key, value);
            return this;
        }

        /**
         * Inserts a byte array value into the mapping of this Bundle, replacing
         * any existing value for the given key.  Either key or value may be null.
         *
         * @param key   a String, or null
         * @param value a byte array object, or null
         */
        public JumpBundle putByteArray(@Nullable String key, @Nullable byte[] value) {
            bundle.putByteArray(key, value);
            return this;
        }

        /**
         * Inserts a short array value into the mapping of this Bundle, replacing
         * any existing value for the given key.  Either key or value may be null.
         *
         * @param key   a String, or null
         * @param value a short array object, or null
         */
        public JumpBundle putShortArray(@Nullable String key, @Nullable short[] value) {
            bundle.putShortArray(key, value);
            return this;
        }

        /**
         * Inserts a char array value into the mapping of this Bundle, replacing
         * any existing value for the given key.  Either key or value may be null.
         *
         * @param key   a String, or null
         * @param value a char array object, or null
         */
        public JumpBundle putCharArray(@Nullable String key, @Nullable char[] value) {
            bundle.putCharArray(key, value);
            return this;
        }

        /**
         * Inserts a float array value into the mapping of this Bundle, replacing
         * any existing value for the given key.  Either key or value may be null.
         *
         * @param key   a String, or null
         * @param value a float array object, or null
         */
        public JumpBundle putFloatArray(@Nullable String key, @Nullable float[] value) {
            bundle.putFloatArray(key, value);
            return this;
        }

        /**
         * Inserts a CharSequence array value into the mapping of this Bundle, replacing
         * any existing value for the given key.  Either key or value may be null.
         *
         * @param key   a String, or null
         * @param value a CharSequence array object, or null
         */
        public JumpBundle putCharSequenceArray(@Nullable String key, @Nullable CharSequence[] value) {
            bundle.putCharSequenceArray(key, value);
            return this;
        }

        /**
         * Inserts a Bundle value into the mapping of this Bundle, replacing
         * any existing value for the given key.  Either key or value may be null.
         *
         * @param key   a String, or null
         * @param value a Bundle object, or null
         */
        public JumpBundle putBundle(@Nullable String key, @Nullable Bundle value) {
            bundle.putBundle(key, value);
            return this;
        }

        /**
         * Inserts an {@link IBinder} value into the mapping of this Bundle, replacing
         * any existing value for the given key.  Either key or value may be null.
         * <p>
         * <p class="note">You should be very careful when using this function.  In many
         * places where Bundles are used (such as inside of Intent objects), the Bundle
         * can live longer inside of another process than the process that had originally
         * created it.  In that case, the IBinder you supply here will become invalid
         * when your process goes away, and no longer usable, even if a new process is
         * created for you later on.</p>
         *
         * @param key   a String, or null
         * @param value an IBinder object, or null
         */
        public JumpBundle putBinder(@Nullable String key, @Nullable IBinder value) {
            bundle.putBinder(key, value);
            return this;
        }

        /**
         * bundle操作完成,用户不必主动调用
         *
         * @return
         */
        public Builder bundleDone() {
            return addBundle(bundle);
        }

        @Override
        public JumpSceneTransition makeSceneTransitionAnimation() {
            return bundleDone().makeSceneTransitionAnimation();
        }
    }
}