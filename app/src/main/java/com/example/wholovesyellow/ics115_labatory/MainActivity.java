package com.example.wholovesyellow.ics115_labatory;

import android.app.ProgressDialog;
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

import com.example.wholovesyellow.ics115_labatory.Model.Model;
import com.onesignal.OneSignal;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if(Model.getToken() != null) {
            Intent loginIntent = new Intent(getApplicationContext(), adminHome.class);

            loginIntent.putExtra("user_type", Model.getUserType());
            startActivity(loginIntent);
            finish();
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    private static EditText email;
    private static EditText password;

    public void login(View view) throws UnsupportedEncodingException, JSONException {



        email = (EditText)findViewById(R.id.editText_email);
        password = (EditText)findViewById(R.id.editText_password);

        final ProgressDialog progress = new ProgressDialog(this);
        progress.setTitle("Loading");
        progress.setMessage("Wait while loading...");
        progress.setCancelable(false); // disable dismiss by tapping outside of the dialog
        progress.show();

        RequestParams params = new RequestParams();

        AsyncHttpClient client = new AsyncHttpClient();
        JSONObject jsonParams = new JSONObject();
        jsonParams.put("username", email.getText().toString());
        jsonParams.put("password", password.getText().toString());

        StringEntity entity = new StringEntity(jsonParams.toString());
        client.post(null, "http://urag.co/labatory_api/api/auth", entity, "application/json", new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String response = new String(responseBody, "UTF-8");
                    JSONObject obj = new JSONObject(response);
                    JSONObject meta = obj.getJSONObject("meta");
                    String token = meta.getString("token");
                    int user_type = obj.getJSONObject("data").getInt("user_type");

                    //add to model
                    Model model = new Model();
                    model.setToken(token);
                    model.setUserType(user_type);
                    OneSignal.sendTag("role", "1");
                    Toast.makeText(getApplicationContext(), "Successful Login", Toast.LENGTH_LONG).show();
                    progress.dismiss();
                    Intent loginIntent = new Intent(getApplicationContext(), adminHome.class);
                    loginIntent.putExtra("user_type", user_type);
                    startActivity(loginIntent);
                    finish();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                progress.dismiss();
                Toast.makeText(getApplicationContext(), "Invalid credentials", Toast.LENGTH_LONG).show();
                error.printStackTrace();
                error.getCause();
            }

        });
    }

}
