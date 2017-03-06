package com.example.thearbiter.amityseller.FirebaseDir;

/**
 * Created by Gaurav Jayasawal on 1/18/2017.
 */

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Gaurav Jayasawal on 1/15/2017.
 */

public class FcmInstanceIdService extends FirebaseInstanceIdService {

    String app_server_url = "http://frame.ueuo.com/images/NotificationFirebaseShop/fcm_insert_shop.php";

    @Override
    public void onTokenRefresh() {
        String recent_token = FirebaseInstanceId.getInstance().getToken();

        SharedPreferences sharedpref;
        sharedpref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor edit = sharedpref.edit();
        edit.putString("token", recent_token);
        edit.apply();

        final String token = sharedpref.getString("token", "");
        Log.d("REFRESEH TOKEN", "km  " + token);

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
        MySingleton.getmInstance(getApplicationContext()).addToRequestque(stringRequest);

    }
}
