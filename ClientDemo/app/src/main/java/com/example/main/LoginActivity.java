package com.example.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.nostra13.universalimageloader.utils.L;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static com.example.main.okhttp.CommonUrl.loginUrl;
import static com.example.main.okhttp.CommonUrl.registerUrl;

public class LoginActivity extends AppCompatActivity {


    private ImageView photo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Button loginBtn = (Button) findViewById(R.id.Login);
        photo = (ImageView) findViewById(R.id.photo);
        Button register = (Button) findViewById(R.id.register);


        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText uid = (EditText) findViewById(R.id.username);
                EditText pwd = (EditText) findViewById(R.id.password);
                String id = uid.getText().toString().trim();
                String pw = pwd.getText().toString().trim();
                if (id.length() == 0) {
                    Toast.makeText(LoginActivity.this, getString(R.string.cannot_username_null), Toast.LENGTH_SHORT).show();
                    return;
                }
                if (pw.length() == 0) {
                    Toast.makeText(LoginActivity.this, getString(R.string.cannot_password_null), Toast.LENGTH_SHORT).show();
                    return;
                }

                Map<String, Object> map = new HashMap<String, Object>();
                map.put("ID", id);
                map.put("PW", pw);

                OkHttpClient mOkHttpClient = new OkHttpClient();

                FormEncodingBuilder builder = new FormEncodingBuilder();
                if (null != map && !map.isEmpty())
                    for (String key : map.keySet()) {
                        builder.add(key, map.get(key) + "");
                    }
                RequestBody requestBody = builder.build();
                Request request = new Request.Builder()
                        .url(loginUrl)
                        .post(requestBody)
                        .build();
                try {
                    mOkHttpClient.setConnectTimeout(5000, TimeUnit.MILLISECONDS);
                    mOkHttpClient.newCall(request).enqueue(new Callback() {
                        @Override
                        public void onResponse(final Response response) throws IOException {

                            final String result = response.body().string();
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        JSONObject jsonObject = new JSONObject(result);
                                        int code = jsonObject.getInt("code");

                                        if (code == 401) {
                                            Toast.makeText(LoginActivity.this, getString(R.string.username_password_error), Toast.LENGTH_SHORT).show();
                                        return;
                                        }
                                        JSONObject jsonObject2 = jsonObject.getJSONObject("user");
                                        String photoUrl = jsonObject2.getString("photo");
                                        Utils.getInstance().displayCircleImage(photoUrl, photo);

                                        Toast.makeText(LoginActivity.this, getString(R.string.login_success), Toast.LENGTH_SHORT).show();

                                    } catch (Exception e) {
                                        Log.d("gaolei","e.getMessage():"+e.getMessage().toString());
                                    }


                                }
                            });
                        }

                        @Override
                        public void onFailure(Request request, IOException e) {

                        }
                    });
                } catch (Exception e) {

                }
            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText uid = (EditText) findViewById(R.id.username);
                EditText pwd = (EditText) findViewById(R.id.password);
                String id = uid.getText().toString().trim();
                String pw = pwd.getText().toString().trim();

                Map<String, Object> map = new HashMap<String, Object>();
                map.put("ID", id);
                map.put("PW", pw);
                OkHttpClient mOkHttpClient = new OkHttpClient();

                FormEncodingBuilder builder = new FormEncodingBuilder();
                if (null != map && !map.isEmpty())
                    for (String key : map.keySet()) {
                        builder.add(key, map.get(key) + "");
                    }
                RequestBody requestBody = builder.build();
                Request request = new Request.Builder()
                        .url(registerUrl)
                        .post(requestBody)
                        .build();
                try {
                    mOkHttpClient.setConnectTimeout(5000, TimeUnit.MILLISECONDS);
                    mOkHttpClient.newCall(request).enqueue(new Callback() {

                        @Override
                        public void onResponse(final Response response) throws IOException {

                            final String result = response.body().string();
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(LoginActivity.this, result, Toast.LENGTH_SHORT).show();
                                    Toast.makeText(LoginActivity.this, result, Toast.LENGTH_SHORT).show();
                                }
                            });
                        }

                        @Override
                        public void onFailure(Request request, IOException e) {

                        }
                    });
                } catch (Exception e) {

                }
            }
        });
    }


}
