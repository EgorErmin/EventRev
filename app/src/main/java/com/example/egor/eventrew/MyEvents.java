package com.example.egor.eventrew;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.example.egor.eventrew.model.Event;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;

import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.client.methods.HttpGet;

public class MyEvents extends AppCompatActivity {

    DataAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_events);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.myEventList);

        adapter = new DataAdapter(this, null, new DataAdapter.OnItemClickListener() {
            @Override
            public void onClick(Event event) {
                Intent i = new Intent(MyEvents.this, EventViewActivity.class);
                i.putExtra("event", event);
                startActivity(i);
            }
        });
        // устанавливаем для списка адаптер
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        Button backButton = (Button) findViewById(R.id.backOnMenuBtn);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent backIntent = new Intent(MyEvents.this, EventsActivity.class);
                startActivity(backIntent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        MyEvents.PostJsonDataTask getMyEvents = new PostJsonDataTask(MyEvents.this);
        getMyEvents.execute();
    }

    private class PostJsonDataTask extends AsyncTask<Void, Void, List<Event>> {

        private Context context;
        PostJsonDataTask(Context currContext) {
            context = currContext;
        }

        @Override
        protected List<Event> doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.
            List<Event> events;
            List<Event> nullEvent;
            String output = "";
            try {
                //get events
                String url = Settings.getServerAddress() + Settings.ServiceURL.GET_MY_EVENTS;
                HttpGet httpGet = new HttpGet(url);
                HttpClient client = cz.msebera.android.httpclient.impl.client.HttpClientBuilder.create().build();
                httpGet.setHeader("Cookie", String.format("JSESSIONID=%s", SingleUser.getUser().getSESSINID()));
                HttpResponse response = client.execute(httpGet);
                Integer statuscode = response.getStatusLine().getStatusCode();
                //event
                BufferedReader br = new BufferedReader(
                        new InputStreamReader((response.getEntity().getContent())));
                String temp;
                while ((temp = br.readLine()) != null) {
                    output += temp;
                }
            } catch (Exception e) {
                //return false;
            }
            events = (new Gson()).fromJson(output, new com.google.gson.reflect.TypeToken<List<Event>>(){}.getType());
            return events;
        }

        @Override
        protected void onPostExecute(final List<Event> events) {
            adapter.setData(events);
        }

        @Override
        protected void onCancelled() {

        }
    }

}
