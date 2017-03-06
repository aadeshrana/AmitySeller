package com.example.thearbiter.amityseller;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.Switch;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Aadesh Rana on 02-03-17.
 */

public class CustomShopDiag extends DialogFragment {
    String sendStatus,sendConstant ="0";
    String sharedStatus;
    private static final String PULL_ITEMS_URL = "http://frame.ueuo.com/midnightshop/shopstat.php";

    SharedPreferences sharedPreferences;


    JSONParser jsonParser = new JSONParser();
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.shop_status,container,false);
        sharedPreferences  = PreferenceManager.getDefaultSharedPreferences(getActivity());
        sharedStatus =  sharedPreferences.getString("shopStaus","0");
        final Switch shopStatus = (Switch)view.findViewById(R.id.statusSwitch);
        Button doneButton = (Button)view.findViewById(R.id.buttonDone);
        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        try{
            if(sharedStatus.equals("0")){


                shopStatus.setChecked(false);
            }
            else {
                shopStatus.setChecked(true);
            }
        }catch (Exception e){

        }



        shopStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(shopStatus.isChecked()){
                    sendStatus ="1";
                    SharedPreferences.Editor edit = sharedPreferences.edit();
                    edit.putString("shopStaus","1");
                    edit.apply();
                    new setStatus().execute();
                }

                else{
                    sendStatus="0";
                    SharedPreferences.Editor edit = sharedPreferences.edit();
                    edit.putString("shopStaus","0");
                    edit.apply();
                    new setStatus().execute();
                }
            }
        });
        setStyle(STYLE_NO_TITLE,0);
        setCancelable(false);
        return view;
    }

    public class setStatus extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... args) {

            try {
                Log.d("Before Vie Orders", "");
                List<NameValuePair> params = new ArrayList<>();
                params.add(new BasicNameValuePair("food", sendConstant));
                params.add(new BasicNameValuePair("availability", sendStatus));


                JSONObject json = jsonParser.makeHttpRequest(PULL_ITEMS_URL, "POST", params);


            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}
