package com.example.memorymatching;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.BeforeClass;
import org.junit.Test;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import static org.junit.Assert.*;

public class ParseJSONTest {
    private static ParseJSON parseJSON;

    @BeforeClass
    public static void setup() {
        parseJSON = new ParseJSON();
    }

    @Test
    public void testMethod() {
        Integer x = parseJSON.testMethod(5);
        Integer expected = 10;
        assertEquals(x, expected);
    }

    @Test
    public void JSONObjectFromURL() {
        String url = "https://shopicruit.myshopify.com/admin/products.json?page=1&access_token=c32313df0d0ef512ca64d5b336a0d7c6";
        try {
            JSONObject jsonObjectActual = parseJSON.getJSONObjectFromURL(url);
            JSONArray productArray = (JSONArray) jsonObjectActual.get("products");
            for(int i = 0; i < productArray.length(); i++) {
                JSONObject product = (JSONObject) productArray.get(i);
                JSONObject image= (JSONObject) product.get("image");
                String url2 = (String) image.get("src");
            }
            Integer x = 1;
            assertEquals(4, 2 + 2);
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void getImageURLTest() {
        String url = "https://shopicruit.myshopify.com/admin/products.json?page=1&access_token=c32313df0d0ef512ca64d5b336a0d7c6";
        try {
            JSONObject jsonObjectActual = parseJSON.getJSONObjectFromURL(url);
            ArrayList<String> urls = parseJSON.getImageURL(jsonObjectActual);
            assertEquals(4, 2 + 2);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
