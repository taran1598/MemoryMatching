package com.example.memorymatching;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Objects;

public class DownloadImages extends AsyncTask<String, Void, Bitmap> {
    ImageView bmImage;
    public DownloadImages(ImageView bmImage) {
        this.bmImage = bmImage;
    }

    /**
     *
     * @param urls: url of images
     * @return: bitmap of the image
     */
    protected Bitmap doInBackground(String... urls) {
        String urldisplay = urls[0];
        Bitmap bitmap = null;
        try {
            InputStream is = new URL(urldisplay).openStream();
            bitmap = BitmapFactory.decodeStream(is);
        } catch (IOException e) {
            Log.e("Error", Objects.requireNonNull(e.getMessage()));
            e.printStackTrace();
        }
        return bitmap;
    }

    protected void onPostExecute(Bitmap result) {
        bmImage.setImageBitmap(result);
    }
}
