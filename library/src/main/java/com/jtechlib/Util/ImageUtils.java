package com.jtechlib.Util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import com.bumptech.glide.request.target.Target;
import com.jtechlib.model.BaseModel;

import java.io.File;
import java.util.concurrent.ExecutionException;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * 图片通用类
 * Created by wuxubaiyang on 16/4/18.
 */
public class ImageUtils {

    /**
     * 显示圆形图片
     *
     * @param context
     * @param uri
     * @param imageView
     */
    public static <T extends ImageView> void showCircleImage(Context context, String uri, T imageView) {
        showCircleImage(context, uri, imageView, 0, 0);
    }

    /**
     * 显示圆形图片
     *
     * @param context
     * @param uri
     * @param imageView
     * @param errorResId
     * @param placeholderResId
     */
    public static <T extends ImageView> void showCircleImage(Context context, String uri, T imageView, int errorResId, int placeholderResId) {
        Glide.with(context)
                .load(uri)
                .error(errorResId)
                .placeholder(placeholderResId)
                .transform(new GlideCircleTransform(context))
                .into(imageView);
    }

    /**
     * 显示一张图片
     *
     * @param context
     * @param uri
     * @param imageView
     */
    public static <T extends ImageView> void showImage(Context context, String uri, T imageView) {
        showImage(context, uri, imageView, 0, 0);
    }

    /**
     * 显示一张图片
     *
     * @param context
     * @param uri
     * @param imageView
     * @param errorResId
     * @param placeholderResId
     */
    public static <T extends ImageView> void showImage(Context context, String uri, T imageView, int errorResId, int placeholderResId) {
        Glide.with(context)
                .load(uri)
                .error(errorResId)
                .placeholder(placeholderResId)
                .into(imageView);
    }

    /**
     * 请求图片
     *
     * @param context
     * @param uri
     * @param action1
     */
    public static void requestImage(Context context, String uri, Action1<? super Bitmap> action1) {
        requestImage(context, uri, Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL, action1);
    }

    /**
     * 请求图片
     *
     * @param context
     * @param uri
     * @param width
     * @param height
     * @param action
     */
    public static void requestImage(Context context, String uri, int width, int height, Action1<? super Bitmap> action) {
        requestImage(context, uri, width, height, action, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                Log.e("ImageLoadError", throwable.getMessage());
            }
        });
    }

    /**
     * 请求图片，回调bitmap
     * 本地文件必须添加资源标识符{@link RxModel#URI_FILE}
     * 网络文件必须添加资源标识符{@link RxModel#URI_HTTP}{@link RxModel#URI_HTTPS}
     *
     * @param context
     * @param uri
     * @param width
     * @param height
     * @param action
     * @param action1
     */
    public static void requestImage(Context context, String uri, int width, int height, Action1<? super Bitmap> action, Action1<Throwable> action1) {
        Observable.just(new RxModel(context, uri, width, height))
                .subscribeOn(Schedulers.io())
                .map(new Func1<RxModel, Bitmap>() {
                    @Override
                    public Bitmap call(RxModel rxModel1) {
                        if (!TextUtils.isEmpty(rxModel1.getUri())) {
                            try {
                                //处理网络文件
                                if (rxModel1.isHttp() || rxModel1.isHttps()) {
                                    File file = Glide.with(rxModel1.getContext())
                                            .load(rxModel1.getUri())
                                            .downloadOnly(rxModel1.getWidth(), rxModel1.getHeight())
                                            .get();
                                    return BitmapFactory.decodeFile(file.getAbsolutePath());
                                }
                                //处理本地文件
                                if (rxModel1.isFile()) {
                                    BitmapFactory.Options options = new BitmapFactory.Options();
                                    if (!rxModel1.isHeightOrigin()) {
                                        options.outHeight = rxModel1.getHeight();
                                    }
                                    if (!rxModel1.isWidthOrigin()) {
                                        options.outWidth = rxModel1.getWidth();
                                    }
                                    return BitmapFactory.decodeFile(rxModel1.getUri(), options);
                                }
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            } catch (ExecutionException e) {
                                e.printStackTrace();
                            }
                        }
                        return null;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(action, action1);
    }

    /**
     * 图片请求，target返回
     *
     * @param context
     * @param uri
     * @param target
     * @param <Y>
     * @return
     */
    public static <Y extends Target<File>> Y requestImage(Context context, String uri, Y target) {
        return Glide.with(context)
                .load(uri)
                .downloadOnly(target);
    }

    /**
     * 裁剪圆形
     */
    private static class GlideCircleTransform extends BitmapTransformation {
        public GlideCircleTransform(Context context) {
            super(context);
        }

        @Override
        protected Bitmap transform(BitmapPool pool, Bitmap toTransform, int outWidth, int outHeight) {
            return circleCrop(pool, toTransform);
        }

        private Bitmap circleCrop(BitmapPool pool, Bitmap toTransform) {
            if (toTransform == null) return null;
            int size = Math.min(toTransform.getWidth(), toTransform.getHeight());
            int x = (toTransform.getWidth() - size) / 2;
            int y = (toTransform.getHeight() - size) / 2;

            Bitmap squared = Bitmap.createBitmap(toTransform, x, y, size, size);

            Bitmap result = pool.get(size, size, Bitmap.Config.ARGB_8888);
            if (result == null) {
                result = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888);
            }

            Canvas canvas = new Canvas(result);
            Paint paint = new Paint();
            paint.setShader(new BitmapShader(squared, BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP));
            paint.setAntiAlias(true);
            float r = size / 2f;
            canvas.drawCircle(r, r, r, paint);
            return result;
        }

        @Override
        public String getId() {
            return getClass().getName();
        }
    }

    /**
     * 图片对象
     */
    private static class RxModel extends BaseModel {
        public static final String URI_HTTP = "http://";
        public static final String URI_HTTPS = "https://";
        public static final String URI_FILE = "file://";

        private Context context;
        private String uri;
        private int width;
        private int height;

        public RxModel(Context context, String uri, int width, int height) {
            this.context = context;
            this.uri = uri;
            this.width = width;
            this.height = height;
        }

        public String getUri() {
            return uri;
        }

        public void setUri(String uri) {
            this.uri = uri;
        }

        public Context getContext() {
            return context;
        }

        public void setContext(Context context) {
            this.context = context;
        }

        public int getWidth() {
            return width;
        }

        public void setWidth(int width) {
            this.width = width;
        }

        public int getHeight() {
            return height;
        }

        public void setHeight(int height) {
            this.height = height;
        }

        /**
         * 判断是否为http
         *
         * @return
         */
        public boolean isHttp() {
            return getUri().startsWith(RxModel.URI_HTTP);
        }

        /**
         * 是否为https
         *
         * @return
         */
        public boolean isHttps() {
            return getUri().startsWith(RxModel.URI_HTTPS);
        }

        /**
         * 是否为file
         *
         * @return
         */
        public boolean isFile() {
            return getUri().startsWith(RxModel.URI_FILE);
        }

        /**
         * 宽度是否为原始尺寸
         *
         * @return
         */
        public boolean isWidthOrigin() {
            return width == Target.SIZE_ORIGINAL;
        }

        /**
         * 高度是否为原始尺寸
         *
         * @return
         */
        public boolean isHeightOrigin() {
            return width == Target.SIZE_ORIGINAL;
        }
    }
}