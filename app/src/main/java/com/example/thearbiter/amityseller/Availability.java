package com.example.thearbiter.amityseller;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Availability extends AppCompatActivity {

    private static final String PULL_ITEMS_URL = "http://frame.ueuo.com/midnightshop/pullAvail.php";

    JSONParser jsonParser = new JSONParser();
    ArrayList<String> nameOfProduct = new ArrayList<>();
    ArrayList<String> avail = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_availability);
        new PullAvail().execute();

    }

    public class PullAvail extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... args) {

            try {
                Log.d("Before Vie Orders", "");
                List<NameValuePair> params1 = new ArrayList<>();
                params1.add(new BasicNameValuePair("course", "anything hola"));
                JSONObject json = jsonParser.makeHttpRequest(PULL_ITEMS_URL, "POST", params1);

                nameOfProduct.clear();
                avail.clear();

                try {
                    for (int i = 0; i < 10; i++) {
                        Log.d("ayo","um"+json.getString("a" + i));
                        nameOfProduct.add(json.getString("a" + i));
                        avail.add(json.getString("b" + i));


                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }


                FragmentAvail.nameOfProduct = new String[nameOfProduct.size()];
                FragmentAvail.avail = new String[avail.size()];

                FragmentAvail.nameOfProduct = nameOfProduct.toArray(new String[nameOfProduct.size()]);
                FragmentAvail.avail = avail.toArray(new String[avail.size()]);



            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            FragmentAvail fragmentavail = new FragmentAvail();
            FragmentManager manager = getFragmentManager();

            FragmentTransaction transaction = manager.beginTransaction();
            transaction.add(R.id.paster, fragmentavail, "asdf");
            transaction.commit();
        }
    }


}
