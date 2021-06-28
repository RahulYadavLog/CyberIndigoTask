package com.example.cyberindigotask;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
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

public class MainActivity extends AppCompatActivity {

    UserAdapter userAdapter;
    List<UserModel> userModels;
    RecyclerView userRecyclerView;
    EditText editSearch;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        userRecyclerView=findViewById(R.id.recyclerView);
        editSearch=findViewById(R.id.editSearch);

        userModels=new ArrayList<>();
        GridLayoutManager gridLayoutManager=new GridLayoutManager(MainActivity.this,1);
        userRecyclerView.setLayoutManager(gridLayoutManager);
        userAdapter=new UserAdapter(MainActivity.this,userModels);
        userRecyclerView.setAdapter(userAdapter);
        getUserData();

        editSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                //after the change calling the method and passing the search input
                filter(editable.toString());

            }
        });

    }
    public void getUserData()
    {
        try {

            Response.Listener<JSONObject> successListener = new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {

                    List<String> invcodarr = new ArrayList<String>();
                    List<String> categoryarr = new ArrayList<String>();
                    List<String> namearr = new ArrayList<String>();
                    List<String> catarr = new ArrayList<String>();
                    List<String> inv_type = new ArrayList<String>();
                    List<String> ids = new ArrayList<String>();
                    List<String> all = new ArrayList<String>();


                    try {

                        final String[] pass = new String[1];
                        JSONArray jsonArray = new JSONArray();

                        ArrayAdapter<String> adapter2;


                        String imgFlg = "1";

                        JSONArray batchmst = response.getJSONArray("data");

                        if (!batchmst.isNull(0)) {

                            for (int i = 0; i < batchmst.length(); i++) {
                                JSONObject c = batchmst.getJSONObject(i);


                                String id = c.getString("id");
                                String email = c.getString("email");
                                String first_name = c.getString("first_name");
                                String last_name = c.getString("last_name");
                                String image = c.getString("avatar");
                                userModels.add(new UserModel(id,email,first_name,last_name,image));
                                userAdapter.notifyDataSetChanged();

                            }
                        }
                        else {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
//                                                                    Toast.makeText(getApplicationContext(),
//                                                                            "No Product Found for Batch Code:- " + bcode,
//                                                                            Toast.LENGTH_LONG)
//                                                                            .show();
                                }
                            });

                        }


                    } catch (Exception e) {
                        e.printStackTrace();

                        Toast.makeText(MainActivity.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                }
            };
            Response.ErrorListener failureListener = new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    error.printStackTrace();
                    Toast.makeText(MainActivity.this, "" + error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            };
            JSONArray array = new JSONArray();
            final JSONObject object = new JSONObject();
            String aa = "https://reqres.in/api/users?page=2";
            String url = aa+array.toString();

            JsonObjectRequest reqApi = new JsonObjectRequest(Request.Method.GET, url, null,
                    successListener, failureListener) {
                protected VolleyError parseNetworkError(VolleyError volleyError) {
                    Log.d("MainActivity", "" + volleyError.getMessage());
                    return super.parseNetworkError(volleyError);
                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
            requestQueue.add(reqApi);

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(MainActivity.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
    private void filter(String text) {
        //new array list that will hold the filtered data
        List<UserModel> filterdNames = new ArrayList<>();

        //looping through existing elements
        for (UserModel item : userModels) {
            //if the existing elements contains the search input
            if (item.getFirstName().toLowerCase().contains(text.toLowerCase())||item.getId().toLowerCase().contains(text.toLowerCase())||item.getEmail().toLowerCase().contains(text.toLowerCase())){
                //adding the element to filtered list
                filterdNames.add(item);
            }
        }
        userAdapter.filterList(filterdNames);
    }

    public void logOut(View view) {
    }

    public void CurrentLocation(View view) {
        Intent intent=new Intent(MainActivity.this,CurrentLocationActivity.class);
        startActivity(intent);
    }
}