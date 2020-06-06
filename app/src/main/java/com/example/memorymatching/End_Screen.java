package com.example.memorymatching;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class End_Screen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end__screen);

        Button main_menu_button = (Button) findViewById(R.id.main_menu_end_screen);
        Button back_end_screen = (Button) findViewById(R.id.back_end_screen);
        main_menu_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                back_to_main_menu();
            }
        });

        back_end_screen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void back_to_main_menu() {
        Intent main_menu = new Intent(this, MemoryMatching.class);
        main_menu.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(main_menu);
    }
}
