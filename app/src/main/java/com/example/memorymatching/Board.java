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
//                Intent intent = new Intent(getApplicationContext(),GridItemActivity.class);
//               // intent.putExtra("name",names[i]);
//                //intent.putExtra("image",images[i]);
//                startActivity(intent);
                ImageView image1 = new ImageView(MemoryMatching.getContext());

            }
        });




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
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view1 = getLayoutInflater().inflate(R.layout.row_data,null);
            //getting view in row_data
            //TextView name = view1.findViewById(R.id.imageName);
            ImageView image = view1.findViewById(R.id.images);

            //name.setText(names[position]);
            //image.setImageResource(images[position]);
            new DownloadImagesTask(image).execute(cards.get(position).getCardName());
            return view1;

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
            //getbmImage();
        }

//        private ImageView getbmImage() {
//            return this.bmImage;
//        }
    }



}
