package com.example.loginscreen;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private int errorCount = 0; //This is the counter for the error count function
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Edit Text field and field Action Listener
        EditText passwordEditText = findViewById(R.id.passwordEditText);
        passwordEditText.setOnEditorActionListener(onEditTextAction);

        //Login button and onClickListener
        Button loginButton = findViewById(R.id.loginButton);
        loginButton.setOnClickListener(onClickLoginButton);
    }

    private final View.OnClickListener onClickLoginButton = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            EditText usernameEditText = findViewById(R.id.usernameEditText); //username EditView
            EditText passwordEditText = findViewById(R.id.passwordEditText); //password EditView
            TextView errorTextView = findViewById(R.id.errorTextView);  //error TextView
            Button loginButton = findViewById(R.id.loginButton);    //login button

            //string storage of the entered Username and Password
            String username = usernameEditText.getText().toString();
            String password = passwordEditText.getText().toString();

            //Create instance of Login Manager
            LoginManager loginManager = new LoginManager(username, password);

            if (loginManager.hasValidCredentials()) //check for valid credentials
            {
                //success - log user in
                errorTextView.setVisibility(View.INVISIBLE);
                errorCount = 0;
            }
            else
            {
                ++errorCount;
                //failure - error message
                errorTextView.setText(getString(R.string.error_text, errorCount));
                errorTextView.setVisibility(View.VISIBLE);

                //if the amount of tries is greater than 3
                if(errorCount > 3)
                {
                    loginButton.setEnabled(false); //disable the button
                    errorTextView.setText(R.string.lock_out_text); //display lockout text
                }
            }
        }
    };

    private final TextView.OnEditorActionListener onEditTextAction = new TextView.OnEditorActionListener()
    {
        @Override
        public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
            //check if the action's id is a Done action
            if (i == EditorInfo.IME_ACTION_DONE)
            {
                //setup the login buttons again
                Button loginButton = findViewById(R.id.loginButton);
                loginButton.setOnClickListener(onClickLoginButton);
                loginButton.performClick();//click
                return true;
            }
            return false;
        }
    };
}