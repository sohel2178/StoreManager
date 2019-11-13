package com.forbitbd.storeapp.ui.store.received;


import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.forbitbd.storeapp.R;
import com.forbitbd.storeapp.models.Receive;
import com.forbitbd.storeapp.ui.store.StoreBaseFragment;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ReceivedFragment extends StoreBaseFragment implements ReceivedContract.View,ReceiveListener {

    private ReceivedPresenter mPresenter;

    private HashSet<String> set = new HashSet<>();
    private HashSet<String> nameSet = new HashSet<>();


    private ReceiveAdapter adapter;


    public ReceivedFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = new ReceivedPresenter(this);
        adapter = new ReceiveAdapter(getContext(),this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_received, container, false);
        initView(view);

        return view;
    }

    private void initView(View view) {
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
        mPresenter.getProjectReceives(getProject().get_id());
    }


    @Override
    public List<String> getUnits() {
        return new ArrayList<>(set);
    }

    @Override
    public List<String> getMaterialNames() {
        return new ArrayList<>(nameSet);
    }

    @Override
    public void renderAdapter(List<Receive> receiveList) {
        adapter.setItems(receiveList);

        for(Receive x: receiveList){
            set.add(x.getUnit());
            nameSet.add(x.getName());
        }
    }

    @Override
    public void addItem(Receive receive) {
        if(adapter!=null){
            adapter.addToFirst(receive);
            set.add(receive.getUnit());
            nameSet.add(receive.getName());
        }
    }

    @Override
    public void onItemClick(int position) {

    }

    @Override
    public void onItemUpdate(int position) {

    }

    @Override
    public void onItemRemove(int position) {

    }

    @Override
    public void onImageClick(int position) {
        Receive receive = adapter.getItem(position);
        get_activity().startZoomImageActivity(receive.getImage());
    }
}
