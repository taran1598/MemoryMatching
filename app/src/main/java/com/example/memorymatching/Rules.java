package com.example.memorymatching;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class Rules extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rules);

        final EditText num_cards_to_match = (EditText) findViewById(R.id.num_cards_to_match);
        final EditText num_cards_to_match_to_win = (EditText) findViewById(R.id.num_cards_to_match_to_win);
        Button apply = (Button) findViewById(R.id.apply_rules);
        Button cancel = (Button) findViewById(R.id.cancel_rules);

        apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (num_cards_to_match.getText().toString().equals("") ||
                        num_cards_to_match_to_win.getText().toString().equals(""))
                {
                    Toast.makeText(getApplicationContext(), "One or more of the fields are empty", Toast.LENGTH_SHORT).show();
                } else {

                    int num_cards_to_match_int = Integer.parseInt(num_cards_to_match.getText().toString());
                    int num_cards_to_match_win = Integer.parseInt(num_cards_to_match_to_win.getText().toString());

                    Intent resultIntent = new Intent();
                    resultIntent.putExtra("num_cards_to_match", num_cards_to_match_int);
                    resultIntent.putExtra("num_cards_to_match_to_win", num_cards_to_match_win);

                    setResult(RESULT_OK, resultIntent);
                    finish();
                }
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // could change to starting main menu again but finish works for now.
            }
        });


    }


}
