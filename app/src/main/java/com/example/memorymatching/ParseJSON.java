package com.example.memorymatching;

import android.os.Build;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;

public class ParseJSON {

    public ParseJSON() {}
    /**
     * Parses the JSON object into the individual cards for memory game
     * @param jsonObject
     */
    public void parseJSONObjectIntoCards(JSONObject jsonObject) {
        //TODO: Parse object into individual cards
    }


    /**
     * Gets JSON object from url
     * @url: JSON endpoint URL
     * @return: JSON object with the contents from the URL, NULL if URL is invalid
     */
    public JSONObject getJSONObjectFromURL(String url) throws IOException {
        try (InputStream is = new URL(url).openStream()) {
            BufferedReader br = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String jsonString = readFile(br);
            return new JSONObject(jsonString);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Reads JSON file from memory
     * @param filename: name of the JSON file to read
     * @return: JSON object containing the contents of the JSON file, return null otherwise
     */
    public JSONObject getJSONObjectFromMemory(String filename) {
        String jsonFile = readFileToString(filename);
        try {
            return new JSONObject(jsonFile);
        } catch (JSONException e) {
            e.printStackTrace();
            System.out.println("Could not read JSON file into JSONObject");
        }
        return null;
    }

    // https://howtodoinjava.com/java/io/java-read-file-to-string-examples/
    private String readFileToString(String filename) {
        StringBuilder contentBuilder = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(filename)))
        {

            String sCurrentLine;
            while ((sCurrentLine = br.readLine()) != null)
            {
                contentBuilder.append(sCurrentLine).append("\n");
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return contentBuilder.toString();
    }
    private static String readFile(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }

    public Integer testMethod(Integer x) {
        return x * 2;
    }

    /**
     * Get all image URLs in @jsonObject
     * @param jsonObject: JSONObject containing the URL of the images
     * @return: List of URLs where the images
     */
    public ArrayList<String> getImageURL(JSONObject jsonObject) {
        ArrayList<String> imageURLs = new ArrayList<String>();
        try {
            JSONArray productArray = (JSONArray) jsonObject.get("products");
            for(int i = 0; i < productArray.length(); i++) {
                JSONObject product = (JSONObject) productArray.get(i);
                JSONObject image= (JSONObject) product.get("image");
                imageURLs.add((String) image.get("src"));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return imageURLs;
    }

    /**
     * Return URLs obtains from the JSON endpoint indicated in the url
     * @param url: JSON endpoint url
     * @return: List of image urls
     */
    public ArrayList<String> ImageFromURLFacade(String url) {
        JSONObject object = null;
        try {
            object = this.getJSONObjectFromURL(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return this.getImageURL(object);
    }
}
