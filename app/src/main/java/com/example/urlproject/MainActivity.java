package com.example.urlproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    TextView _tv;
    EditText _et;
    String _s;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void getLink(View v) {
        this._tv = findViewById(R.id.texto);
        this._et = findViewById(R.id.url);
        this._s = this._et.getText().toString();
        new HTMLExtractor().execute();
    }

    public class HTMLExtractor extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            String message = "";

            try {
                URL url = new URL(_s);
                BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
                String line = "";
                while ((line = in.readLine()) != null) {
                    message+=line;
                }
                in.close();

            } catch (MalformedURLException e) {
                System.out.println("Malformed URL: " + e.getMessage());
            } catch (IOException e) {
                System.out.println("I/O Error: " + e.getMessage());
            }
            String[] text;
            text=message.split("<div id=\"paste\">");
            text=text[1].split("</div>");
            message=text[0];
            message=message.replace("<p>","");
            message=message.replace("</p>","\n");
            return message;
        }
        protected void onPostExecute(String finalMessage){
            _tv.setText(finalMessage);
        }
    }
}
