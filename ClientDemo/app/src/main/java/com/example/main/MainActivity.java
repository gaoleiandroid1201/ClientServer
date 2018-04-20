package com.example.main;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import com.example.main.okhttp.CallBack;
import com.example.main.okhttp.NetRequest;
import org.json.JSONObject;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static com.example.main.okhttp.CommonUrl.loginUrl;
import static com.example.main.okhttp.CommonUrl.registerUrl;

public class MainActivity extends AppCompatActivity implements CallBack.NetRequestIterface {


    private ImageView photo;
    private NetRequest netRequest;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button loginBtn = (Button) findViewById(R.id.Login);
        photo = (ImageView) findViewById(R.id.photo);
        Button register = (Button) findViewById(R.id.register);
        netRequest = new NetRequest(this, this);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText uid = (EditText) findViewById(R.id.username);
                EditText pwd = (EditText) findViewById(R.id.password);
                String id = uid.getText().toString().trim();
                String pw = pwd.getText().toString().trim();

                Map<String, Object> map = new HashMap<String, Object>();
                map.put("ID", id);
                map.put("PW", pw);
                netRequest.httpRequest(map, loginUrl);
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
                netRequest.httpRequest(map, registerUrl);
            }
        });
    }


    @Override
    public void changeView(String result, String requestUrl) {
        if (requestUrl.equals(loginUrl)) {
            try {
                JSONObject jsonObject = new JSONObject(result);
                JSONObject jsonObject2 = jsonObject.getJSONObject("user");
                String photoUrl = jsonObject2.getString("photo");
                Utils.getInstance().displayCircleImage(photoUrl, photo);
            } catch (Exception e) {
            }

            Toast.makeText(MainActivity.this, result, Toast.LENGTH_SHORT).show();
        }
        if (requestUrl.equals(registerUrl)) {

            Toast.makeText(MainActivity.this, result, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void exception(IOException e, String requestUrl) {

    }
}
