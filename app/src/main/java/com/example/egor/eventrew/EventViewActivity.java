package com.example.egor.eventrew;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.egor.eventrew.model.Event;
import com.example.egor.eventrew.model.User;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.client.methods.HttpGet;

public class EventViewActivity extends AppCompatActivity {

    Boolean t = true;
    Event eventReview;
    DataUserAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_view);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.userList);

        final Event event = (Event) getIntent().getSerializableExtra("event");
        eventReview = event;

        List<User> users = new ArrayList<>();
        users.addAll(event.getUsers());

        adapter = new DataUserAdapter(this, users, null);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        adapter.setData(users);

        if (event != null) {
            TextView name = findViewById(R.id.nameEvent);
            TextView information = findViewById(R.id.informationEvent);
            //TextView date = findViewById(R.id.dateEvent);
            TextView place = findViewById(R.id.placeEvent);
            information.setText(event.getInformation());
            name.setText(event.getName());
            //date.setText(event.getData().toString());
            place.setText(event.getPlace());
        }

        for(User u : event.getUsers()){
            if(u.getId().equals(SingleUser.getUser().getId())){
                System.out.println("has");
                t = false;
                break;
            }
        }
        Button registerButton = (Button) findViewById(R.id.registerEvent);
        if (!t) {
            registerButton.setText("You are register!");
            registerButton.setTextColor(Color.GREEN);
        }
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println(t);
                if(t){
                    PostJsonDataTask eventRegistration = new PostJsonDataTask(EventViewActivity.this);
                    eventRegistration.execute();
                }
                Intent registerIntent = new Intent(EventViewActivity.this, EventsActivity.class);
                startActivity(registerIntent);
            }
        });
    }

    private class PostJsonDataTask extends AsyncTask<Void, Void, Boolean> {

        private Context context;

        PostJsonDataTask(Context currContext) {
            context = currContext;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.
            try {
                //get events
                String url = Settings.getServerAddress() + Settings.ServiceURL.EVENT_REGISTER + eventReview.getId().toString();
                HttpGet httpGet = new HttpGet(url);
                HttpClient client = cz.msebera.android.httpclient.impl.client.HttpClientBuilder.create().build();
                httpGet.setHeader("Cookie", String.format("JSESSIONID=%s", SingleUser.getUser().getSESSINID()));
                HttpResponse response = client.execute(httpGet);
                Integer statuscode = response.getStatusLine().getStatusCode();
            } catch (Exception e) {
                return false;
            }
            return true;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            if (success) {
                try {
                    Toast.makeText(EventViewActivity.this, "Event registration!", Toast.LENGTH_LONG).show();
                }catch (Exception e) {
                    Toast.makeText(EventViewActivity.this, "Some error occured", Toast.LENGTH_LONG).show();
                }
                //finish();
            } else {
                Toast.makeText(EventViewActivity.this, "Some error occured", Toast.LENGTH_LONG).show();
                //saveButton.setVisibility(View.INVISIBLE);
            }
        }

        @Override
        protected void onCancelled() {

        }
    }
}
