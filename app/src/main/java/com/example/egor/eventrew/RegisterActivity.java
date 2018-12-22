package com.example.egor.eventrew;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.client.methods.HttpPost;

public class RegisterActivity extends AppCompatActivity {
    String email;
    String  firstName;
    String lastName;
    String password;
    String confirmPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Button registerButton = findViewById(R.id.registeractivity_register_button);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = ((EditText) findViewById(R.id.registerActivity_email)).getText().toString();
                firstName = ((EditText) findViewById(R.id.registerActivity_name)).getText().toString();
                lastName = ((EditText) findViewById(R.id.registerActivity_lastName)).getText().toString();
                password = ((EditText) findViewById(R.id.registerActivity_password)).getText().toString();
                confirmPassword = ((EditText) findViewById(R.id.registerActivity_passwordConfirm)).getText().toString();
                System.out.print(password.length());
                if (password.length() < 4) {
                    Toast.makeText(RegisterActivity.this, "Password length less than four!", Toast.LENGTH_LONG).show();
                    return;
                }
                System.out.print(password + "//" + confirmPassword);
                if (password.equals(confirmPassword)) {
                    PostJsonDataTask postJsonDataTask = new PostJsonDataTask(RegisterActivity.this);
                    postJsonDataTask.execute((Void) null);
                } else {
                    Toast.makeText(RegisterActivity.this, "Passwords do not match!", Toast.LENGTH_LONG).show();
                }
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
                // Simulate network access.
                String url = Settings.getServerAddress() + Settings.ServiceURL.REGISTER + "?email=" + email + "&password=" + password + "&firstname=" + firstName + "&lastname=" + lastName;
                System.out.println(url);
                HttpPost httpPost = new HttpPost(url);
                HttpClient client = cz.msebera.android.httpclient.impl.client.HttpClientBuilder.create().build();
                HttpResponse response = client.execute(httpPost);

                Integer statuscode = response.getStatusLine().getStatusCode();

                if (statuscode > 202) {
                    return false;
                }
                //Thread.sleep(2000);
            } catch (Exception e) {
                return false;
            }
            // TODO: register the new account here.
            return true;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            if (success) {
                try {
                    Toast.makeText(RegisterActivity.this, "Registration successful!", Toast.LENGTH_LONG).show();
                    RegisterActivity.super.onBackPressed();
                }catch (Exception e) {
                    Toast.makeText(RegisterActivity.this, "Some error occured", Toast.LENGTH_LONG).show();
                }
                //finish();
            } else {
                Toast.makeText(RegisterActivity.this, "Some error occured", Toast.LENGTH_LONG).show();
                //saveButton.setVisibility(View.INVISIBLE);
            }
        }

        @Override
        protected void onCancelled() {

        }
    }
}
