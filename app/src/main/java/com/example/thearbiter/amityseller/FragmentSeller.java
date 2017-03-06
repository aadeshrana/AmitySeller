package com.example.thearbiter.amityseller;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by Aadesh Rana on 20-02-17.
 */

public class FragmentSeller extends Fragment {

    static public String nameOfPerson[];
    static public String nameOfProduct[];
    static public String address[];
    static public String quantity[];
    static public String totalPrice[];
    static public String phonenumber[];

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recycler_layout,container,false);
        RecyclerView recyclerView =(RecyclerView)view.findViewById(R.id.recycleviewseller);
        AdapterSeller adapterSeller = new AdapterSeller(getActivity(),getData());
        recyclerView.setAdapter(adapterSeller);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        return view;


    }

    public List<InformationSeller>getData(){
        List<InformationSeller>data = new ArrayList<>();
        try{
            for(int j=0;j<nameOfPerson.length;j++){
                InformationSeller current = new InformationSeller();
                current.nameOfPerson = nameOfPerson[j];
                current.productName = nameOfProduct[j];
                current.quantity = quantity[j];
                current.totalPrice = totalPrice[j];
                current.address= address[j];
                current.phoneNumber = phonenumber[j];
                data.add(current);
            }

        }catch (Exception e){

        }
        return data;
    }
}
