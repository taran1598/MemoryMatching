package utils;

import android.graphics.Bitmap;
import android.graphics.DrawFilter;
import android.graphics.drawable.Drawable;
import android.media.Image;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class WebImages {

    public WebImages() {}

    /**
     * partial code from https://stackoverflow.com/questions/10292792/getting-image-from-url-java
     * Gets images at urls specified in urls and saves them to disk
     * @param urls: list of urls that contain an image
     */
    public void saveImagesFromURLs(ArrayList<String> urls) {

        for(String elem: urls) {
            try {
                URL url = new URL(elem);
                InputStream is = url.openStream();
                String destFile = destFileName(elem);
                File file = new File(destFileName(elem));
                OutputStream os = new FileOutputStream(file);

                byte[] b = new byte[4096];
                int length;

                while ((length = is.read()) != -1) {
                    os.write(b, 0, length);
                }

                is.close();
                os.close();
            } catch (MalformedURLException e) {
                // want to still continue to get images so not point of throwing
                e.printStackTrace();
                System.out.println("URL" + elem + "is malformed");
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Couldn't open inputs stream for " + elem);
            }
        }
    }

    public static ArrayList<Drawable> getImagesFromURLs(ArrayList<String> urls) {
        ArrayList<Drawable> drawables = new ArrayList<>();
        for (String elem: urls) {
            try {
                InputStream is = (InputStream) new URL(elem).getContent();
                Drawable d = Drawable.createFromStream(is, "src name");
                drawables.add(d);
            } catch (IOException e) {
                e.printStackTrace();
                //TODO: Add logging for errors
            }
        }
        return drawables;
    }

    /**
     * Create destination file string where the folder is ../images
     * @param elem: String to get the file name from
     * @return: destinationFileName that is a concatanatin of ../images + the last word after /
     */
    private String destFileName(String elem) {
        String destFileName = "..//images//";
        String[] parts = elem.split("/");
        return destFileName + parts[parts.length-1] + ".png";
    }

}
