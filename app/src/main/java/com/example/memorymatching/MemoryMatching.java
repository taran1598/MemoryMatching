package com.example.memorymatching;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;

/**
 * Main Activity
 */
public class MemoryMatching extends Activity implements AsyncResponse{

    private static Context context;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        context = getApplicationContext();
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        String jsonEndpoint = "https://shopicruit.myshopify.com/admin/products.json?page=1&access_token=c32313df0d0ef512ca64d5b336a0d7c6";
        new GetImageURLs(this).execute(jsonEndpoint);




    }

    public static Context getContext() {
        return context;
    }


    @Override
    public void processFinish(final ArrayList<String> strings) {
        Button start_game_button = (Button) findViewById(R.id.Start_Game_Button);
        start_game_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent board_activity = new Intent(getApplicationContext(), Board.class);
                board_activity.putStringArrayListExtra("ImageURLs", strings);
                board_activity.putExtra("num_cards_to_match", 2);
                board_activity.putExtra("num_players", 1);
                board_activity.putExtra("num_cards_to_match_to_win", 10);
                startActivity(board_activity);
            }
        });

    }



    private class GetImageURLs extends AsyncTask<String, Void, ArrayList<String>> {


        private ArrayList<String> imageURLs;
        public AsyncResponse delegate = null;
        public GetImageURLs(AsyncResponse delegate) {
            this.delegate = delegate;
        }


        protected ArrayList<String> doInBackground(String... urls) {
            String jsonEndpoint = urls[0];
            ParseJSON parseJSON = new ParseJSON();
            return parseJSON.ImageFromURLFacade(jsonEndpoint);
        }

        protected void onPostExecute(ArrayList<String> result) {
            this.imageURLs = result;
            this.delegate.processFinish(result);
        }
    }
}
