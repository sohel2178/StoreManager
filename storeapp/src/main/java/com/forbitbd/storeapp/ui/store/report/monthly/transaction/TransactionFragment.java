package com.forbitbd.storeapp.ui.store.report.monthly.transaction;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.forbitbd.storeapp.R;
import com.forbitbd.storeapp.models.Consume;
import com.forbitbd.storeapp.models.Receive;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class TransactionFragment extends Fragment {


    public TransactionFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_transaction, container, false);
    }

    public void update(List<Receive> receiveList, List<Consume> consumeList){
        Log.d("YYYYYY","RECEIVE "+receiveList.size());
        Log.d("YYYYYY","CONSUME "+consumeList.size());
    }

}
