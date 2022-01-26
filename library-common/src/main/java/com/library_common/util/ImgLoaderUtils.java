package com.library_common.util;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.library_common.MyApplication;

import java.io.File;

/**
 * Created by cxf on 2017/8/9.
 */

public class ImgLoaderUtils {
    private static RequestManager sManager;

    static {
        sManager = Glide.with(MyApplication.getInstance());
    }

    public static void Load(ImageView imageview, String url, int defaultImg) {
        RequestOptions requestOptions = new RequestOptions()
                .placeholder(new ColorDrawable(Color.BLACK))
                .error(new ColorDrawable(Color.BLUE))
                .fallback(new ColorDrawable(Color.RED))
                .fitCenter()
                .error(defaultImg)
                .skipMemoryCache(true)
                .dontAnimate()//防止设置placeholder导致第一次不显示网络图片,只显示默认图片的问题
                .placeholder(defaultImg)
                .override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                .diskCacheStrategy(DiskCacheStrategy.ALL);


        if (url != null && url.endsWith("gif")) {
            Glide.with(MyApplication.getInstance())
                    .load(url)
                    .apply(requestOptions)
                    .into(imageview);
        } else {
            Glide.with(MyApplication.getInstance())
                    .load(url)
                    .apply(requestOptions)
                    .into(imageview);
        }
    }

    public static void LoadCircle(ImageView imageview, String url, int defaultImg) {
        RequestOptions requestOptions = new RequestOptions()
                .placeholder(new ColorDrawable(Color.BLACK))
                .error(new ColorDrawable(Color.BLUE))
                .fitCenter()
                .transform(new GlideCircleTransform(MyApplication.getInstance()))
                .error(defaultImg)
                .skipMemoryCache(true)
                .dontAnimate()//防止设置placeholder导致第一次不显示网络图片,只显示默认图片的问题
                .placeholder(defaultImg)
                .override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                .diskCacheStrategy(DiskCacheStrategy.ALL);

        Glide.with(MyApplication.getInstance())
                .load(url)
                .apply(requestOptions)
                .into(imageview);
    }

    public static void LoadCircle(ImageView imageview, File url, int defaultImg) {
        RequestOptions requestOptions = new RequestOptions()
                .placeholder(new ColorDrawable(Color.BLACK))
                .error(new ColorDrawable(Color.BLUE))
                .fitCenter()
                .transform(new GlideCircleTransform(MyApplication.getInstance()))
                .error(defaultImg)
                .skipMemoryCache(true)
                .dontAnimate()//防止设置placeholder导致第一次不显示网络图片,只显示默认图片的问题
                .placeholder(defaultImg)
                .override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                .diskCacheStrategy(DiskCacheStrategy.ALL);

        Glide.with(MyApplication.getInstance())
                .load(url)
                .apply(requestOptions)
                .into(imageview);
    }

    public static void LoadRoundedCorners(String url, ImageView imageview, int defaultImg) {
        //设置图片圆角角度
        RoundedCorners roundedCorners = new RoundedCorners(18);
        //通过RequestOptions扩展功能,override:采样率,因为ImageView就这么大,可以压缩图片,降低内存消耗
        RequestOptions options = RequestOptions.bitmapTransform(roundedCorners)
                .override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                .placeholder(defaultImg)
                .error(defaultImg);

        Glide.with(MyApplication.getInstance()).load(url).apply(options).into(imageview);
    }

    public static void LoadRoundedCorners(int resource, ImageView imageview, int defaultImg) {
        //设置图片圆角角度
        RoundedCorners roundedCorners = new RoundedCorners(20);
        //通过RequestOptions扩展功能,override:采样率,因为ImageView就这么大,可以压缩图片,降低内存消耗
        RequestOptions options = RequestOptions.bitmapTransform(roundedCorners)
                .override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                .placeholder(defaultImg)
                .error(defaultImg);
        Glide.with(MyApplication.getInstance()).load(resource).apply(options).into(imageview);
    }
}
