package com.joselopezrosario.filemaker_assets.util;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.widget.ImageView;

import com.joselopezrosario.androidfm.FmCookie;
import com.squareup.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

import java.io.IOException;

import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class DownloadImage {

    static public void set(@NonNull ImageView image, final Context context, String imageUrl, int width, int height) {
        OkHttpClient downloader = new OkHttpClient()
                .newBuilder()
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Interceptor.Chain chain) throws IOException {
                        final Request original = chain.request();
                        final Request authorized = original.newBuilder()
                                .addHeader(FmCookie.getName(), FmCookie.getValue(context))
                                .build();
                        return chain.proceed(authorized);
                    }
                })
                .cache(new Cache(context.getCacheDir(), 25 * 1024 * 1024))
                .build();

        Picasso.Builder builder = new Picasso
                .Builder(context)
                .downloader(new OkHttp3Downloader(downloader));

        builder.listener(new Picasso.Listener() {
            @Override
            public void onImageLoadFailed(Picasso picasso, Uri uri, Exception exception) {
                exception.printStackTrace();
            }
        });

        builder.build().load(imageUrl).resize(width, height).centerInside().into(image);
    }
}
