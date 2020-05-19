package com.example.memorymatching;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

public class Board extends AppCompatActivity {

    GridView gridView;
    private ArrayList<String> imageURLs;
    private ArrayList<Card> cards;
    private GameRules gameRules;
    private int num_players = 0;
    private int total_cards;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.grid_view);

        // get gridview
        gridView = findViewById(R.id.gridview);
        // get image urls
        // get the url of images
        imageURLs = getIntent().getStringArrayListExtra("ImageURLs");
        assert imageURLs != null;
        // make all the cards to display
        cards = createDuplicateCards(imageURLs,  getIntent().getIntExtra("num_cards_to_match_to_win", 10));
        // calculate total number of cards that will be used on this board
        total_cards = getIntent().getIntExtra("num_cards_to_match_to_win", 10) *
                getIntent().getIntExtra("num_cards_to_match", 2);
        // instantiate the rules to be used on this board
        gameRules = new GameRules(getIntent().getIntExtra("num_cards_to_match", 2),
                getIntent().getIntExtra("num_players", 2),
                getIntent().getIntExtra("num_cards_to_match_to_win", 10));
        // total number of players in the game
        num_players = getIntent().getIntExtra("num_players", 1);

        CustomAdapter customAdapter = new CustomAdapter();
        gridView.setAdapter(customAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {


            }
        });




    }

    public void flip(View v) {
        ViewFlipper viewFlipper = (ViewFlipper) v;
        //ViewFlipper viewFlipper = findViewById(R.id.card_flipper);
        if (viewFlipper.getDisplayedChild() == 0) {
            viewFlipper.setDisplayedChild(1);
        } else {
            viewFlipper.setDisplayedChild(0);
        }
    }

//    public void flip_To_Front(View v) {
//        ViewFlipper v1 = findViewById(R.id.card_flipper);
//        v1.setDisplayedChild(0);
//    }
//
//    public void flip_To_Back(View v) {
//        ViewFlipper v1 = findViewById(R.id.card_flipper);
//        v1.setDisplayedChild(1);
//    }

    /**
     * Creates card objects from the card url where the picture is stored
     * @param imageURLs: url where the picture of the card is stored
     * @return: list of card objects
     */
    private ArrayList<Card> createCards(ArrayList<String> imageURLs) {
        ArrayList<Card> cards = new ArrayList<>();
        for(String elem: imageURLs) {
            cards.add(new Card(elem));
        }
        return cards;
    }

    /**
     *
     * @param imageURLs: url of where the image is
     * @param num_duplicates: number of duplicate cards we want
     * @return: list of cards
     */
    private ArrayList<Card> createDuplicateCards(ArrayList<String> imageURLs, int num_duplicates) {
        ArrayList<Card> duplicateCards = new ArrayList<>();
        Random rand = new Random();
        for (int i = 0; i < num_duplicates; i++) {
            String url = imageURLs.get(rand.nextInt(imageURLs.size()-1));
            for (int j = 0; j < getIntent().getIntExtra("num_cards_to_match", 2); j++) {
                duplicateCards.add(new Card(url));
            }
        }
        return duplicateCards;
    }


    private class CustomAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return total_cards;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view1 = getLayoutInflater().inflate(R.layout.card_flip_view,null);
            //getting view in row_data
            //TextView name = view1.findViewById(R.id.imageName);
            ImageView image_front = view1.findViewById(R.id.front_image);
            ImageView image_back = view1.findViewById(R.id.back_image);

            //name.setText(names[position]);
            //image.setImageResource(images[position]);
            new DownloadImagesTask(image_back).execute(cards.get(position).getCardName());
            image_front.setImageResource(R.drawable.card_back);

            // set onClick listener
//            final ViewFlipper viewFlipper = (ViewFlipper) findViewById(R.id.card_flipper);
//
//            viewFlipper.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    flip_image_view(viewFlipper);
//                }
//            });
            return view1;

        }

//        @Override
//        public View getView(int position, View convertView, ViewGroup parent) {
//            View view = convertView;
//            View view1 = getLayoutInflater().inflate(R.layout.card_flip_view,null);
//            //getting view in row_data
//            //TextView name = view1.findViewById(R.id.imageName);
//            ImageView image_front = view1.findViewById(R.id.front_image);
//            ImageView image_back = view1.findViewById(R.id.back_image);
//
//            //name.setText(names[position]);
//            //image.setImageResource(images[position]);
//            new DownloadImagesTask(image_front).execute(cards.get(position).getCardName());
//            image_back.setImageResource(R.drawable.card_back);
//
//            // set onClick listener
////            final ViewFlipper viewFlipper = (ViewFlipper) findViewById(R.id.card_flipper);
////
////            viewFlipper.setOnClickListener(new View.OnClickListener() {
////                @Override
////                public void onClick(View v) {
////                    flip_image_view(viewFlipper);
////                }
////            });
//            return view1;
//
//        }

        public void flip_image_view (ViewFlipper v) {
            if (v.getDisplayedChild() == 0) {
                v.setDisplayedChild(1);
            } else {
                v.setDisplayedChild(0);
            }
        }


    }

    /**
     * Async class that downloads image from url to ImageView
     */
    private class DownloadImagesTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;
        public DownloadImagesTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

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



}
