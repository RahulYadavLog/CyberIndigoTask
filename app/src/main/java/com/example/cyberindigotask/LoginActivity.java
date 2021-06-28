package com.example.cyberindigotask;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends AppCompatActivity {

    EditText username,password;
    Button loginBtn,signUpBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        username=findViewById(R.id.username);
        password=findViewById(R.id.password);
        loginBtn=findViewById(R.id.login);
        signUpBtn=findViewById(R.id.sign_up);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!username.getText().toString().isEmpty()&&!password.getText().toString().isEmpty())
                {
                    Login();
                }
                else {
                    Toast.makeText(LoginActivity.this, "Please Enter Email and Password", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
    public void Login()
    {
        try {

            Response.Listener<JSONObject> successListener = new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {

                    Toast.makeText(LoginActivity.this, "Login Success", Toast.LENGTH_SHORT).show();

                    Intent intent=new Intent(LoginActivity.this,MainActivity.class);
                    startActivity(intent);
                }
            };
            Response.ErrorListener failureListener = new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    error.printStackTrace();
                    Toast.makeText(LoginActivity.this, "" + error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            };
            JSONArray array = new JSONArray();
            final JSONObject object = new JSONObject();
//            object.put("email", "eve.holt@reqres.in");
//            object.put("password","cityslicka");
            object.put("email", username.getText().toString());
            object.put("password",password.getText().toString());
            array.put(0, object);
            String aa = "https://reqres.in/api/login";
            String url = aa+array.toString();

            JsonObjectRequest reqApi = new JsonObjectRequest(Request.Method.GET, url, null,
                    successListener, failureListener) {
                protected VolleyError parseNetworkError(VolleyError volleyError) {
                    Log.d("MainActivity", "" + volleyError.getMessage());
                    return super.parseNetworkError(volleyError);
                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(LoginActivity.this);
            requestQueue.add(reqApi);

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(LoginActivity.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}