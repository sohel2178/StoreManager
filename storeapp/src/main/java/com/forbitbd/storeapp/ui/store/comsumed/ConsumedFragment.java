package com.forbitbd.storeapp.ui.store.comsumed;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.forbitbd.storeapp.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ConsumedFragment extends Fragment {


    public ConsumedFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_consumed, container, false);
    }

}
