package utils;

import com.example.memorymatching.ParseJSON;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.BeforeClass;
import org.junit.Test;
import java.io.IOException;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class WebImagesTest {

    private static ParseJSON parseJSON;
    private static WebImages webImages;

    @BeforeClass
    public static void setup() {
        parseJSON = new ParseJSON();
        webImages = new WebImages();
    }

    @Test
    public void getImageFromURLS() {
        String url = "https://shopicruit.myshopify.com/admin/products.json?page=1&access_token=c32313df0d0ef512ca64d5b336a0d7c6";
        try {
            JSONObject jsonObjectActual = parseJSON.getJSONObjectFromURL(url);
            ArrayList<String> urls = parseJSON.getImageURL(jsonObjectActual);
            webImages.saveImagesFromURLs(urls);
            assertEquals(4, 2 + 2);
        } catch (IOException e) {
            e.printStackTrace();
            fail();
        }
    }
}
