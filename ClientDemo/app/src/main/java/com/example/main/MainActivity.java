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

public class MainActivity extends AppCompatActivity  {


    private ImageView photo;
    private NetRequest netRequest;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
