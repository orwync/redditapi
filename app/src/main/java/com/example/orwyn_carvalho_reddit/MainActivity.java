package com.example.orwyn_carvalho_reddit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.CompoundButton;
import android.widget.ToggleButton;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements PostAdapter.OnitemClickListner {
    private static final String EXTRA_TITLE = "title";
    private static final String EXTRA_IMAGEURL = "imageurl";
    private static final String EXTRA_POINTS = "points";
    private static final String EXTRA_TEXT = "text";
    private RecyclerView mRecyclerView;
    private PostAdapter mPostAdapter;
    private ArrayList<Posts> mArrayList;
    private RequestQueue mRequestQueue;
    private ToggleButton hotnew;
    private String urlnew = "https://www.reddit.com/r/popular/new.json";
    private String urlhot = "https://www.reddit.com/r/popular/hot.json";
    String comment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        hotnew = (ToggleButton) findViewById(R.id.hotnew);
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this ));
        mArrayList = new ArrayList<>();

        mRequestQueue = Volley.newRequestQueue(this);



        hotnew.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    mArrayList.clear();
                    mPostAdapter.notifyDataSetChanged();
                    //mPostAdapter.notify();
                    jsonparser(urlhot);
                }
                else {
                    mArrayList.clear();
                    mPostAdapter.notifyDataSetChanged();
                    //mPostAdapter.notify();
                    jsonparser(urlnew);
                }
            }
        });

        jsonparser(urlnew);

        /*jsonparserhot();
        jsonparsernew();*/
    }

    private void jsonparser(String url) {

        final JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray array = response.getJSONObject("data").getJSONArray("children");

                            for(int i=0;i<array.length();i++){
                                JSONObject data = array.getJSONObject(i).getJSONObject("data");
                                String title = data.getString("title");
                                String prefix = data.getString("subreddit_name_prefixed");
                                int ups = data.getInt("ups");
                                int downs = data.getInt("downs");
                                int points = ups-downs;
                                String pic = data.getString("thumbnail");
                                String selftext = data.getString("selftext");
                                comment = data.getString("url");


                                mArrayList.add(new Posts(title,pic,String.valueOf(points),selftext,comment));
                            }

                            mPostAdapter = new PostAdapter(MainActivity.this, mArrayList);
                            mRecyclerView.setAdapter(mPostAdapter);
                            mPostAdapter.setOnItemClickListner(MainActivity.this);


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

    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(this, DetailsView.class);
        Posts clickedItem = mArrayList.get(position);

        intent.putExtra(EXTRA_TITLE,clickedItem.getTitle());
        intent.putExtra(EXTRA_IMAGEURL,clickedItem.getPic());
        intent.putExtra(EXTRA_POINTS,clickedItem.getPoints());
        intent.putExtra(EXTRA_TEXT,clickedItem.getSelftext());
        intent.putExtra("comment",clickedItem.getComment());

        startActivity(intent);
    }
}
