package com.example.egor.eventrew;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONObject;

import java.util.List;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.client.CookieStore;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.client.methods.HttpPost;
import cz.msebera.android.httpclient.client.protocol.ClientContext;
import cz.msebera.android.httpclient.cookie.Cookie;
import cz.msebera.android.httpclient.impl.client.BasicCookieStore;
import cz.msebera.android.httpclient.impl.client.HttpClientBuilder;
import cz.msebera.android.httpclient.impl.cookie.BasicClientCookie;
import cz.msebera.android.httpclient.impl.cookie.BasicClientCookie2;
import cz.msebera.android.httpclient.protocol.BasicHttpContext;
import cz.msebera.android.httpclient.protocol.HttpContext;

public class AddEvent extends AppCompatActivity {

    String nameEvent;
    String informationEvent;
    String dateEvent = null;
    String placeEvent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);

        Button addEventButonn =(Button) findViewById(R.id.addEventBtn);
        addEventButonn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nameEvent = ((EditText) findViewById(R.id.nameEvent)).getText().toString();
                informationEvent = ((EditText) findViewById(R.id.informationEvent)).getText().toString();
                placeEvent = ((EditText) findViewById(R.id.placeEvent)).getText().toString();
                PostJsonDataTask postJsonDataTask = new PostJsonDataTask(AddEvent.this);
                postJsonDataTask.execute((Void) null);
            }
        });
    }

    private class PostJsonDataTask extends AsyncTask<Void, Void, Boolean> {

        private Context context;
        private JSONObject json;

        PostJsonDataTask(Context currContext) {
            context = currContext;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.
            try {
                String url = Settings.getServerAddress() + Settings.ServiceURL.ADD_EVENT + "?name_event=" + nameEvent + "&date=" + dateEvent + "&place=" + placeEvent + "&information=" + informationEvent;
                System.out.println(url);

                HttpPost httpPost = new HttpPost(url);

                HttpClient client = HttpClientBuilder.create().build();
                httpPost.setHeader("Cookie", String.format("JSESSIONID=%s", SingleUser.getUser().getSESSINID()));
                HttpResponse response = client.execute(httpPost);

                Integer statuscode = response.getStatusLine().getStatusCode();

                if (statuscode > 202) {
                    return false;
                }
                //Thread.sleep(2000);
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
            // TODO: register the new account here.
            return true;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            if (success) {
                try {
                    Toast.makeText(AddEvent.this, "Event was added!", Toast.LENGTH_LONG).show();
                    AddEvent.super.onBackPressed();
                }catch (Exception e) {
                    Toast.makeText(AddEvent.this, "Some error occured", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
                //finish();
            } else {
                Toast.makeText(AddEvent.this, "Some error occured", Toast.LENGTH_LONG).show();
                //saveButton.setVisibility(View.INVISIBLE);
            }
        }

        @Override
        protected void onCancelled() {

        }
    }
}
