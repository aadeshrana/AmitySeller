package com.example.thearbiter.amityseller;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Collections;
import java.util.List;

/**
 * Created by Aadesh Rana on 20-02-17.
 */

public class AdapterSeller extends RecyclerView.Adapter<AdapterSeller.MyViewHolder> {
Context context;
    LayoutInflater inflater;
    List<InformationSeller> data = Collections.emptyList();

    public AdapterSeller(Context context, List<InformationSeller> data){
        this.context=context;
        inflater = LayoutInflater.from(context);
        this.data = data;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.custom_seller,parent,false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final  InformationSeller current = data.get(position);
        holder.nameOfPerson.setText(current.nameOfPerson);
        holder.productName.setText(current.productName);
        holder.phoneNumber.setText(current.phoneNumber);
        holder.hostelAddress.setText(current.address);
        holder.totalPrice.setText(current.totalPrice);
        holder.quantity.setText(current.quantity);
        final MainActivity mainActivity = new MainActivity();
        holder.cardView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Toast.makeText(context, "delete this", Toast.LENGTH_SHORT).show();
            return false;
            }

        });



    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView nameOfPerson, productName,quantity,totalPrice,hostelAddress,phoneNumber;
        public CardView cardView;
        public MyViewHolder(View itemView) {
            super(itemView);
            nameOfPerson =(TextView)itemView.findViewById(R.id.nameOfPerson);
            productName = (TextView)itemView.findViewById(R.id.nameOfProduct);
            quantity =(TextView)itemView.findViewById(R.id.Quantity);
            totalPrice =(TextView)itemView.findViewById(R.id.totalPrice);
            hostelAddress =(TextView)itemView.findViewById(R.id.hostelAddress);
            phoneNumber =(TextView)itemView.findViewById(R.id.phoneNumber);
            cardView = (CardView)itemView.findViewById(R.id.cardViewfindSchool);
        }
    }
}
