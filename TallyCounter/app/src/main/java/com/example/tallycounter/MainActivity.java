package com.example.tallycounter;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView countTextView;
    private int count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*Add code under the first 2 lines always!!!*/
        count = 0;

        //sets the countTextView element to the variable countTextView
        countTextView = findViewById(R.id.countTextView);

        //Count Button
        Button countButton = findViewById(R.id.countButton);

        countButton.setOnClickListener(onClickCountButton); //sets button on the appropriate
                                                            // listener

        //add a Reset Button to the App
        Button resetButton = findViewById(R.id.resetButton);

        resetButton.setOnClickListener(onClickResetButton);

    }

    //This Function is an On Click Listener for onClickCountButton
    //When the button is clicked, the count should increase by one and be
    //displayed by the Text View
    private final View.OnClickListener onClickCountButton = new View.OnClickListener()
    {

        @Override
        public void onClick(View view) {
            count++; //increment the count
            countTextView.setText(String.valueOf(count)); //set the text view to the value of count
        }
    };

    //This Function is an On Click Listener for onClickResetButton
    //When the button is clicked, the count should revert to 0 and be
    //displayed by the Text View
    private final View.OnClickListener onClickResetButton = new View.OnClickListener()
    {

        @Override
        public void onClick(View view) {
            count = 0; //count becomes 0
            countTextView.setText(String.valueOf(count)); //set text view to the value of count
        }
    };
}