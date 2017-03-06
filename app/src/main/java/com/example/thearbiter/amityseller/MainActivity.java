package com.example.thearbiter.amityseller;

import android.Manifest;
import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.thearbiter.amityseller.FirebaseDir.MySingleton;
import com.google.firebase.iid.FirebaseInstanceId;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private static final String PULL_ITEMS_URL = "http://frame.ueuo.com/midnightshop/pullOrders.php";
    JSONParser jsonParser = new JSONParser();
    String app_server_url = "http://frame.ueuo.com/images/NotificationFirebaseShop/fcm_insert_shop.php";
    ArrayList<String> nameOfPerson = new ArrayList<>();
    ArrayList<String> productName = new ArrayList<>();
    ArrayList<String> address = new ArrayList<>();
    ArrayList<String> quantity = new ArrayList<>();
    ArrayList<String> totalPrice = new ArrayList<>();
    ArrayList<String> phoneNumber = new ArrayList<>();
   static Toolbar toolbar;
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE};
    public static void verifyStoragePermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getToken();
        new PullAllIBItems().execute();

        verifyStoragePermissions(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu_kayout, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.log_out) {
            addnewItems();
        }
        if(id == R.id.avail_check){
            Intent intent  = new Intent(this,Availability.class);
            startActivity(intent);

        }
        if(id==R.id.shop_status){
            shopStatus();
        }

        return super.onOptionsItemSelected(item);
    }

    private void getToken() {
        final String recent_token = FirebaseInstanceId.getInstance().getToken();

        SharedPreferences sharedpref;
        sharedpref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor edit = sharedpref.edit();
        edit.putString("token", recent_token);
        edit.apply();
        final String token = sharedpref.getString("token", "");
        Log.d("TOKEN LOG", "km  " + token);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, app_server_url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("fcm_token", token);
                params.put("user", "shopAdmin");
                return params;
            }
        };
        MySingleton.getmInstance(MainActivity.this).addToRequestque(stringRequest);
    }



    public class PullAllIBItems extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... args) {

            try {
                Log.d("Before Vie Orders", "");
                List<NameValuePair> params1 = new ArrayList<>();
                params1.add(new BasicNameValuePair("course", "anything hola"));
                JSONObject json = jsonParser.makeHttpRequest(PULL_ITEMS_URL, "POST", params1);

                nameOfPerson.clear();
                productName.clear();
                address.clear();
                quantity.clear();
                totalPrice.clear();

                try {
                    for (int i = 0; i < 100; i++) {
                        Log.d("ayo?","um"+json.getString("c" + i));
                        nameOfPerson.add(json.getString("c" + i));
                        productName.add(json.getString("a" + i));
                        address.add(json.getString("e" + i));
                        quantity.add(json.getString("b" + i));
                        totalPrice.add(json.getString("f" + i));
                        phoneNumber.add(json.getString("d"+i));

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }


                FragmentSeller.nameOfPerson = new String[nameOfPerson.size()];
                FragmentSeller.nameOfProduct = new String[productName.size()];
                FragmentSeller.quantity = new String[quantity.size()];
                FragmentSeller.totalPrice = new String[totalPrice.size()];
                FragmentSeller.address = new String[address.size()];
                FragmentSeller.phonenumber = new String[phoneNumber.size()];

                FragmentSeller.nameOfPerson = nameOfPerson.toArray(new String[nameOfPerson.size()]);
                FragmentSeller.nameOfProduct = productName.toArray(new String[productName.size()]);
                FragmentSeller.quantity = quantity.toArray(new String[quantity.size()]);
                FragmentSeller.totalPrice = totalPrice.toArray(new String[totalPrice.size()]);
                FragmentSeller.address = address.toArray(new String[address.size()]);
                FragmentSeller.phonenumber = phoneNumber.toArray(new String[phoneNumber.size()]);


            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            FragmentSeller fragmentSeller = new FragmentSeller();
            FragmentManager manager = getFragmentManager();

            FragmentTransaction transaction = manager.beginTransaction();
            transaction.add(R.id.paster, fragmentSeller, "asdf");
            transaction.commit();
        }
    }

    public void addnewItems(){
        CustomAddDiag customAddDiag = new CustomAddDiag();
        customAddDiag.show(getFragmentManager(),"abc");

    }
    public void shopStatus(){
        CustomShopDiag customShopDiag = new CustomShopDiag();
        customShopDiag.show(getFragmentManager(),"cde");
    }


}
