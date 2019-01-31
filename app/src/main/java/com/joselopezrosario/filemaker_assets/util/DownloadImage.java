package com.joselopezrosario.filemaker_assets.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public final class DownloadImage {

    private DownloadImage() { /*Suppressed Constructor*/}

    public static Bitmap execute(String containerUrl) {
        Bitmap bitmap = null;
        InputStream in = null;
        OutputStream os = null;
        try {
            URL url = new URL(containerUrl);
            HttpURLConnection conn;
            conn = (HttpURLConnection) url.openConnection();
            conn.connect();
            in = conn.getInputStream();
            bitmap = BitmapFactory.decodeStream(in);
            conn.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (in != null)
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }

        return bitmap;
    }
}
