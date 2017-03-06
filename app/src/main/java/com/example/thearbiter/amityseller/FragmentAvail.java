package com.example.thearbiter.amityseller;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Aadesh Rana on 25-02-17.
 */

public class FragmentAvail extends Fragment {
    static public String nameOfProduct[];

    static public String avail[];
    static public String totalPrice[];
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recyclerfor_avail,container,false);
        RecyclerView recyclerView =(RecyclerView)view.findViewById(R.id.recycleviewseller);
        AdapterAvail adapteravail = new AdapterAvail(getActivity(), getData());
        recyclerView.setAdapter(adapteravail);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        return view;


    }
    public List<InformationAvail> getData(){
        List<InformationAvail>data = new ArrayList<>();
        try{
            for(int j=0;j<nameOfProduct.length;j++){
                InformationAvail current = new InformationAvail();
                current.nameProduct = nameOfProduct[j];
                Log.d("eta?",""+current.nameProduct);
                current.avail = avail[j];
                data.add(current);
            }

        }catch (Exception e){

        }
        return data;
    }
}
