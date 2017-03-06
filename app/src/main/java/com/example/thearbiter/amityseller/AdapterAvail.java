package com.example.thearbiter.amityseller;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Aadesh Rana on 25-02-17.
 */

public class AdapterAvail extends RecyclerView.Adapter<AdapterAvail.MyViewHolder> {
    Context context;
    LayoutInflater inflater;
    private static final String PULL_ITEMS_URL = "http://frame.ueuo.com/midnightshop/availablesetter.php";
    JSONParser jsonParser = new JSONParser();
    List<InformationAvail> data = Collections.emptyList();
    final String[] sendAvail = new String[1];
    final String[] sendName = new String[1];
    public AdapterAvail(Context context, List<InformationAvail> data) {
        this.context = context;
        this.data = data;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.custom_avail, parent, false);
        AdapterAvail.MyViewHolder holder = new AdapterAvail.MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final  InformationAvail current = data.get(position);

        holder.nameProduct.setText(current.nameProduct);
        Log.d("hyaa",""+current.nameProduct);
        if(current.avail.matches("1")){
            holder.availSwitch.setChecked(true);
        }
        holder.availSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendName[0] = current.nameProduct;
                if(holder.availSwitch.isChecked()){
                    Toast.makeText(context, "Bro s not available ", Toast.LENGTH_SHORT).show();
                    sendAvail[0] = "1";
                    new SetAvail().execute();
                }
                else{
                    Toast.makeText(context, "yaw yaw its available", Toast.LENGTH_SHORT).show();
                    sendAvail[0]="0";
                    new SetAvail().execute();
                }
            }
        });


    }

    @Override
    public int getItemCount() {
        Log.d("whats the sie",""+data.size());
        return data.size();


    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView nameProduct;
        Switch availSwitch;

        public MyViewHolder(View itemView) {
            super(itemView);
            nameProduct = (TextView) itemView.findViewById(R.id.availName);
            availSwitch = (Switch) itemView.findViewById(R.id.availSwitch);

        }
    }
    public class SetAvail extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... args) {

            try {
                Log.d("Before Vie Orders", "");
                List<NameValuePair> params = new ArrayList<>();
                params.add(new BasicNameValuePair("food", sendName[0]));
                params.add(new BasicNameValuePair("availability", sendAvail[0]));


                JSONObject json = jsonParser.makeHttpRequest(PULL_ITEMS_URL, "POST", params);





            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

}}
