package com.example.memorymatching;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import com.example.memorymatching.exception.CardsException;
import org.w3c.dom.Text;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.net.URL;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Board extends AppCompatActivity {

    GridView gridView;
    private ArrayList<String> imageURLs;
    private ArrayList<Card> cards;
    private ArrayList<Card> cards_flipped = new ArrayList<>();
    private ArrayList<Integer> pos_cards_flipped = new ArrayList<>();
    private GameRules gameRules;
    private int num_players = 0;
    private int total_cards;
    private int num_cards_to_match;
    private int num_cards_flipped = 0;
    private int points;
    private int num_cards_to_match_to_win;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.grid_view);

        // get gridview
        gridView = findViewById(R.id.gridview);
        // get image urls
        // get the url of images
        imageURLs = getIntent().getStringArrayListExtra("ImageURLs");
        num_cards_to_match =  getIntent().getIntExtra("num_cards_to_match", 2);
        points = 0;
        num_cards_to_match_to_win =  getIntent().getIntExtra("num_cards_to_match_to_win", 10);
        // total number of players in the game
        num_players = getIntent().getIntExtra("num_players", 1);
        assert imageURLs != null;
        // make all the cards to display
        cards = createDuplicateCards(imageURLs,  num_cards_to_match_to_win);
        shuffleCards(cards);
        // calculate total number of cards that will be used on this board
        total_cards = num_cards_to_match_to_win * num_cards_to_match;
        // instantiate the rules to be used on this board
        gameRules = new GameRules(num_cards_to_match, num_players, num_cards_to_match_to_win);

        CustomAdapter customAdapter = new CustomAdapter();
        gridView.setAdapter(customAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                LinearLayout li = (LinearLayout) view;
                ViewFlipper viewFlipper = (ViewFlipper) li.getChildAt(0);
                boolean flipped = flip(viewFlipper);
                if (flipped) {
                    cards_flipped.add(cards.get(position));
                    pos_cards_flipped.add(position);
                    game_logic();
                }

            }
        });




    }

    private void game_logic() {
        try {
            int points = gameRules.num_points(cards_flipped);
            if (points == 0) {
                num_cards_flipped = 0;
                // add delay before flipping cards back
                // https://stackoverflow.com/questions/15874117/how-to-set-delay-in-android
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        flip_cards_back();
                    }
                }, 1000);
            } else {
                num_cards_flipped = 0;
                this.points += points;
                TextView textView = (TextView) findViewById(R.id.points);
                textView.setText("Points " + points);
                cards_flipped.clear();
                pos_cards_flipped.clear();
                // Check if user has won
                if (points == num_cards_to_match_to_win) {
                    //TODO: start new activity that displays the user has won and the user can play again
                }
            }

        } catch (CardsException e) {
            // TODO: Tell user to select more cards
        }
    }

    private void flip_cards_back() {
        for (int i = 0; i < cards_flipped.size(); i++) {
            int idx_of_card_flipped = pos_cards_flipped.get(i);
            LinearLayout li  = (LinearLayout) gridView.getChildAt(idx_of_card_flipped);
            ViewFlipper viewFlipper = (ViewFlipper) li.getChildAt(0);
            viewFlipper.setDisplayedChild(0);
        }
        cards_flipped.clear();
        pos_cards_flipped.clear();
    }

    /**
     * Method that is called when image in gridView is clicked. Flips card to other image
     * @param v: ViewFlipper
     * @return: True if card was flipped, false otherwise
     */
    public boolean flip(View v) {
        ViewFlipper viewFlipper = (ViewFlipper) v;
        // flip only if the image is not already showing
        if (num_cards_flipped < num_cards_to_match && viewFlipper.getDisplayedChild() == 0) {
            // increment number of cards flipped
            num_cards_flipped++;
            viewFlipper.setDisplayedChild(1);
            return true;
        }
        return false;
    }

    /**
     * Shuffle cards into random order
     * @param cards: List of Card objects
     */
    private void shuffleCards(ArrayList<Card> cards) {
        Collections.shuffle(cards);
    }

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
            return cards.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view1 = getLayoutInflater().inflate(R.layout.card_flip_view,null);

            ImageView image_front = view1.findViewById(R.id.front_image);
            ImageView image_back = view1.findViewById(R.id.back_image);

            new DownloadImagesTask(image_front).execute(cards.get(position).getCardName());
            image_back.setImageResource(R.drawable.card_back);

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
