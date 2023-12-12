package co.walid.jokeapp;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SecondLine extends AppCompatActivity {

    // Other code...

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        // Retrieve the second line content from the intent
        String secondLine = getIntent().getStringExtra("SECOND_LINE");

        TextView secondLineTextView = findViewById(R.id.secondLineTextView);
        secondLineTextView.setText(secondLine);
    }
}
