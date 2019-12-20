package com.example.orwyn_carvalho_reddit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class DetailsView extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private DetailsAdapter mDetailsAdapter;
    private ArrayList<Details> mArrayList;
    private RequestQueue mRequestQueue;
    private String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_view);

        Intent intent = getIntent();
        String imageurl = intent.getStringExtra("imageurl");
        String detailTitle = intent.getStringExtra("title");
        String points = intent.getStringExtra("points");
        String text  = intent.getStringExtra("text");
        url = intent.getStringExtra("comment");

        Log.d("SHOW",url);

        ImageView imageView = (ImageView)findViewById(R.id.detailimage);
        TextView title = findViewById(R.id.detailtitle);
        TextView detailsText = findViewById(R.id.textdetails);
        TextView detailspoints = findViewById(R.id.detailspoints);

        Picasso.get().load(imageurl).into(imageView);
        title.setText(detailTitle);
        detailsText.setText(text);
        detailspoints.setText(points);

        mRecyclerView = (RecyclerView) findViewById(R.id.detailrecycle);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this ));
        mArrayList = new ArrayList<>();

        mRequestQueue = Volley.newRequestQueue(this);
        jsonparser(url);

    }

    private void jsonparser(String url) {

        final JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Log.d("SHOW","HEllo");
                            JSONArray array = response.getJSONObject("data").getJSONArray("children");

                            for(int i=0;i<array.length();i++){
                                JSONArray children = array.getJSONObject(i).getJSONObject("data").getJSONObject("replies").getJSONObject("data").getJSONArray("children");

                                String body="";
                                for(int j=0;j<children.length();j++)
                                {
                                    JSONObject child = children.getJSONObject(j);
                                    body = child.getString("body");
                                    Log.d("SHOW",body);
                                    mArrayList.add(new Details(body));
                                }





                            }

                            mDetailsAdapter = new DetailsAdapter(DetailsView.this, mArrayList);
                            mRecyclerView.setAdapter(mDetailsAdapter);


                        } catch (JSONException e) {


                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        mRequestQueue.add(request);
    }

}
