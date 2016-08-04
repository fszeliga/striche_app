package org.szeliga.android.taskerwidget.app_fragments;

import android.app.Activity;
import android.app.Notification;
import android.content.Context;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.json.JSONObject;
import org.szeliga.android.taskerwidget.NavigationActivity;
import org.szeliga.android.taskerwidget.R;
import org.szeliga.android.taskerwidget.util.AsyncRequests.AddUserAsync;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;

public class AddUser extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);
        setTitle("Add new user!");

        final EditText userName = (EditText) findViewById(R.id.editTextAddUser);

        Button btnCancel = (Button) findViewById(R.id.btnCancelAddUser);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent returnIntent = new Intent();
                setResult(Activity.RESULT_CANCELED, returnIntent);
                returnIntent.putExtra("result", "Adding user cancelled");
                finish();
            }
        });

        Button btnAddUser = (Button) findViewById(R.id.btnAddUser);
        btnAddUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                AddUserAsync task = new AddUserAsync(userName.getText().toString(),  new AddUserAsync.AsyncCallback() {
                    @Override
                    public void myMethod(boolean result) {
                        String statsMsg = "";
                        Intent returnIntent = new Intent();

                        if (result == true) {
                           statsMsg = "Successfully added user - " + userName.getText().toString();
                            setResult(Activity.RESULT_OK, returnIntent);

                        } else {
                            statsMsg = "Failed adding user";
                            setResult(Activity.RESULT_CANCELED, returnIntent);
                        }
                        returnIntent.putExtra("result", statsMsg);
                        finish();
                    }
                });
                task.execute();
            }
        });
    }
}
