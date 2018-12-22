package com.example.egor.eventrew;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.egor.eventrew.model.User;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.client.CookieStore;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.client.methods.HttpPost;
import cz.msebera.android.httpclient.client.protocol.ClientContext;
import cz.msebera.android.httpclient.impl.client.BasicCookieStore;
import cz.msebera.android.httpclient.protocol.BasicHttpContext;
import cz.msebera.android.httpclient.protocol.HttpContext;

public class LoginActivity extends AppCompatActivity {

    String email;
    String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button loginButonn =(Button) findViewById(R.id.loginBtn);
        loginButonn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = ((EditText) findViewById(R.id.emailEditText)).getText().toString();
                password = ((EditText) findViewById(R.id.passwordEditText)).getText().toString();
                //System.out.println(password);
                PostJsonDataTask postJsonDataTask = new PostJsonDataTask(LoginActivity.this);
                postJsonDataTask.execute((Void) null);
            }
        });

        Button registerButton = (Button) findViewById(R.id.registerBtn);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent registerIntent = new Intent(LoginActivity.this, RegisterActivity.class);
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
                CookieStore cookieStore = new BasicCookieStore();
                // Create local HTTP context
                HttpContext localContext = new BasicHttpContext();
                // Bind custom cookie store to the local context
                localContext.setAttribute(ClientContext.COOKIE_STORE, cookieStore);
                // Simulate network access.
                String url = Settings.getServerAddress() + Settings.ServiceURL.LOGIN + "?email=" + email + "&password=" + password;
                System.out.println(url);
                HttpPost httpPost = new HttpPost(url);
                HttpClient client = cz.msebera.android.httpclient.impl.client.HttpClientBuilder.create().build();
                HttpResponse response = client.execute(httpPost,localContext);

                Integer statuscode = response.getStatusLine().getStatusCode();

                if(statuscode == 401){
                    // сообщение об неверной авторизации
                    return false;
                }
                if (statuscode > 202) {
                    return false;
                }
                String cookie = cookieStore.getCookies().get(0).getValue();
                System.out.println(cookie);
                BufferedReader br = new BufferedReader(
                        new InputStreamReader((response.getEntity().getContent())));

                String output = "";
                String temp;

                while ((temp = br.readLine()) != null) {
                    output += temp;
                }
                Gson gson = new Gson();
                User user = gson.fromJson(output, User.class);
                user.setSESSINID(cookie);
                SingleUser.setUser(user);
                return true;
            } catch (Exception e) {
                return false;
            }
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            if (success) {
                try {
                    Toast.makeText(LoginActivity.this, "User was authorized!", Toast.LENGTH_LONG).show();
                    Intent eventsIntent = new Intent(LoginActivity.this, EventsActivity.class);
                    startActivity(eventsIntent);
                }catch (Exception e) {
                    Toast.makeText(LoginActivity.this, "Some error occured", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
                //finish();
            } else {
                Toast.makeText(LoginActivity.this, "Some error occured", Toast.LENGTH_LONG).show();
            }
        }

        @Override
        protected void onCancelled() {

        }
    }
}
