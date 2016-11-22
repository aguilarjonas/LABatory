package com.example.wholovesyellow.ics115_labatory;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    private static EditText email;
    private static EditText password;

    public void login(View view){

        email = (EditText)findViewById(R.id.editText_email);
        password = (EditText)findViewById(R.id.editText_password);

        if(email.getText().toString().equals("email") && password.getText().toString().equals("pass")){
            Toast.makeText(getApplicationContext(),
                    "Successful Login!",
                    Toast.LENGTH_SHORT).show();

            Intent loginIntent = new Intent(this, adminHome.class);
            loginIntent.putExtra("user", email.getText().toString());
            startActivity(loginIntent);
        }else if(email.getText().toString().equals("labatory") && password.getText().toString().equals("pass")) {
            Toast.makeText(getApplicationContext(),
                    "Successful Login!",
                    Toast.LENGTH_SHORT).show();

            Intent loginIntent = new Intent(this, adminHome.class);
            loginIntent.putExtra("user", email.getText().toString());
            startActivity(loginIntent);
        } else {
            Toast.makeText(getApplicationContext(),
                    "Invalid Login!",
                    Toast.LENGTH_SHORT).show();
        }


    }
}
