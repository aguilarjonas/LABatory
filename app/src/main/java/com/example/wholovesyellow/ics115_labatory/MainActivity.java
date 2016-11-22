package com.example.wholovesyellow.ics115_labatory;

import android.content.Entity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    private static EditText email;
    private static EditText password;

    public void login(View view) throws UnsupportedEncodingException, JSONException {

//        email = (EditText)findViewById(R.id.editText_email);
//        password = (EditText)findViewById(R.id.editText_password);
//
//        if(email.getText().toString().equals("email") && password.getText().toString().equals("pass")){
//            Toast.makeText(getApplicationContext(),
//                    "Successful Login!",
//                    Toast.LENGTH_SHORT).show();
//
//            Intent loginIntent = new Intent(this, adminHome.class);
//            loginIntent.putExtra("user", email.getText().toString());
//            startActivity(loginIntent);
//        }else if(email.getText().toString().equals("labatory") && password.getText().toString().equals("pass")) {
//            Toast.makeText(getApplicationContext(),
//                    "Successful Login!",
//                    Toast.LENGTH_SHORT).show();
//
//            Intent loginIntent = new Intent(this, adminHome.class);
//            loginIntent.putExtra("user", email.getText().toString());
//            startActivity(loginIntent);
//        } else {
//            Toast.makeText(getApplicationContext(),
//                    "Invalid Login!",
//                    Toast.LENGTH_SHORT).show();
//        }
        RequestParams params = new RequestParams();
        AsyncHttpClient client = new AsyncHttpClient();
        JSONObject jsonParams = new JSONObject();
        jsonParams.put("username", "sirdanny");
        jsonParams.put("password", "dandalandan");
        StringEntity entity = new StringEntity(jsonParams.toString());
//        "http://urag.co/labatory_api/api/auth"
        client.post(null, "http://192.168.1.8/devspace/labatory_api/api/auth", entity, "application/json", new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String response = new String(responseBody, "UTF-8");
                    JSONObject obj = new JSONObject(response);
                    JSONObject meta = obj.getJSONObject("meta");
                    String token = meta.getString("token");


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                error.printStackTrace();
                error.getCause();
            }

        });

    }
}
