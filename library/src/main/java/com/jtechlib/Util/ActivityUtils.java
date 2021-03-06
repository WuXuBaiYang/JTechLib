/*
 * Copyright 2016, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.jtechlib.Util;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import java.util.List;

/**
 * This provides methods to help Activities load their UI.
 */
public class ActivityUtils {

    /**
     * The {@code fragment} is added to the container view with id {@code frameId}. The operation is
     * performed by the {@code fragmentManager}.
     */
    public static <T extends Fragment> void addFragmentToActivity(@NonNull FragmentManager fragmentManager, @NonNull T fragment, int frameId) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(frameId, fragment);
        transaction.commit();
    }

    /**
     * 同时添加一个集合的fragment到同一个视图上
     *
     * @param fragmentManager
     * @param fragments
     * @param frameId
     */
    public static void addFragmentListToActivity(@NonNull FragmentManager fragmentManager, @NonNull List<? extends Fragment> fragments, int frameId) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        for (int i = 0; i < fragments.size(); i++) {
            transaction.add(frameId, fragments.get(i));
        }
        transaction.commit();
    }

    /**
     * 显示fragment
     *
     * @param fragmentManager
     * @param fragment
     */
    public static <T extends Fragment> void showFragment(@NonNull FragmentManager fragmentManager, @NonNull T fragment) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.show(fragment);
        transaction.commit();
    }

    /**
     * 隐藏fragment
     *
     * @param fragmentManager
     * @param fragment
     */
    public static <T extends Fragment> void hideFragment(@NonNull FragmentManager fragmentManager, @NonNull T fragment) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.hide(fragment);
        transaction.commit();
    }

    /**
     * 显示一个集合中的一个fragment
     *
     * @param fragmentManager
     * @param fragments
     * @param index
     */
    public static void selectFragment(@NonNull FragmentManager fragmentManager, @NonNull List<? extends Fragment> fragments, int index) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        for (int i = 0; i < fragments.size(); i++) {
            if (index == i) {
                transaction.show(fragments.get(i));
            } else {
                transaction.hide(fragments.get(i));
            }
        }
        transaction.commit();
    }
}