package com.jtechlib.Util;

import android.os.Bundle;
import android.support.annotation.NonNull;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * bundle的链式调用
 * Created by jianghan on 2016/10/28.
 */

public class BundleChain {

    public static Builder build(@NonNull Bundle bundle) {
        return new Builder(bundle);
    }

    public static Builder build() {
        return new Builder();
    }

    /**
     * 构造器
     */
    public static class Builder {
        private Bundle bundle;

        public Builder(Bundle bundle) {
            this.bundle = new Bundle(bundle);
        }

        public Builder() {
            this.bundle = new Bundle();
        }

        public Builder putString(String key, String value) {
            bundle.putString(key, value);
            return this;
        }

        public Builder putStringArray(String key, String[] value) {
            bundle.putStringArray(key, value);
            return this;
        }

        public Builder putStringArrayList(String key, ArrayList<String> value) {
            bundle.putStringArrayList(key, value);
            return this;
        }


        public Builder putInt(String key, int value) {
            bundle.putInt(key, value);
            return this;
        }

        public Builder putIntArray(String key, int[] value) {
            bundle.putIntArray(key, value);
            return this;
        }

        public Builder putIntegerArrayList(String key, ArrayList<Integer> value) {
            bundle.putIntegerArrayList(key, value);
            return this;
        }

        public Builder putBoolean(String key, boolean value) {
            bundle.putBoolean(key, value);
            return this;
        }

        public Builder putBooleanArray(String key, boolean[] value) {
            bundle.putBooleanArray(key, value);
            return this;
        }

        public Builder putDouble(String key, double value) {
            bundle.putDouble(key, value);
            return this;
        }

        public Builder putDoubleArray(String key, double[] value) {
            bundle.putDoubleArray(key, value);
            return this;
        }

        public Builder putLong(String key, long value) {
            bundle.putLong(key, value);
            return this;
        }

        public Builder putLongArray(String key, long[] value) {
            bundle.putLongArray(key, value);
            return this;
        }

        public Builder putFloat(String key, float value) {
            bundle.putFloat(key, value);
            return this;
        }

        public Builder putFloatArray(String key, float[] value) {
            bundle.putFloatArray(key, value);
            return this;
        }

        public Builder putShort(String key, short value) {
            bundle.putShort(key, value);
            return this;
        }

        public Builder putShortArray(String key, short[] value) {
            bundle.putShortArray(key, value);
            return this;
        }

        public Builder putSerializable(String key, Serializable value) {
            bundle.putSerializable(key, value);
            return this;
        }

        public Bundle toBundle() {
            return bundle;
        }
    }
}