package com.example.newsinhand;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    ListView lv;
    ImageView poster;
    TextView headline,source;
    String[] news_poster=new String[20];
    String[] news_headline=new String[20];
    String[] news_source=new String[20];
    String[] news_url = new String[20];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lv=findViewById(R.id.lv);
        String url ="https://newsapi.org/v2/top-headlines?country=us&apiKey=32f70c60fc714320a48dda65beb24d94";

        StringRequest stringRequest=new StringRequest(StringRequest.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject parent =new JSONObject(response);
                    JSONArray articles = parent.getJSONArray("articles");
                    for(int i=0;i<articles.length();i++){
                        JSONObject currentObject = articles.getJSONObject(i);

                        news_headline[i]=currentObject.getString("title");
                        news_source[i]=currentObject.getString("author");
                        news_poster[i]=currentObject.getString("urlToImage");
                        news_url[i]=currentObject.getString("url");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "Some error occured", Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue requestQueue= Volley.newRequestQueue(MainActivity.this);
        requestQueue.add(stringRequest);
        CustomAdapter customAdapter= new CustomAdapter();
        lv.setAdapter(customAdapter);
    }

    private class CustomAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return news_headline.length;
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {

            view= getLayoutInflater().inflate(R.layout.single_news_layout,null);

            poster= view.findViewById(R.id.poster);
            headline=view.findViewById(R.id.headline);
            source=view.findViewById(R.id.source);

            headline.setText(news_headline[i]);
            source.setText(news_source[i]);
            Picasso.with(MainActivity.this).load(news_poster[i]).into(poster);
            return view;
        }
    }
}