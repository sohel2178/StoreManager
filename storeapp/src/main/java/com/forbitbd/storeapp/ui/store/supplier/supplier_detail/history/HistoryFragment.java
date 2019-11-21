package com.forbitbd.storeapp.ui.store.supplier.supplier_detail.history;


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
import com.forbitbd.storeapp.ui.store.received.ReceiveAdapter;
import com.forbitbd.storeapp.ui.store.received.ReceiveListener;
import com.forbitbd.storeapp.ui.store.received.receiveDetail.ReceivedDetail;
import com.forbitbd.storeapp.ui.store.supplier.supplier_detail.StoreDetailBase;
import com.forbitbd.storeapp.utils.Constant;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class HistoryFragment extends StoreDetailBase implements HistoryContract.View, ReceiveListener {

    private HistoryPresenter mPresenter;

    private ReceiveAdapter adapter;


    public HistoryFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new ReceiveAdapter(getContext(),this);
        mPresenter = new HistoryPresenter(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_history, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void update(List<Receive> receiveList) {
        adapter.setItems(receiveList);
    }



    @Override
    public void removeFromAdapter(Receive receive) {
        adapter.remove(receive);
    }

    @Override
    public void onImageClick(int position) {

    }

    @Override
    public void onItemClick(int position) {
        ReceivedDetail receivedDetail = new ReceivedDetail();
        Bundle bundle = new Bundle();
        bundle.putSerializable(Constant.RECEIVED,adapter.getItem(position));
        receivedDetail.setArguments(bundle);

        receivedDetail.show(getChildFragmentManager(),"HHHHH");

    }

    @Override
    public void onItemUpdate(int position) {

    }

    @Override
    public void onItemRemove(final int position) {
        /*final DeleteDialog deleteDialog = new DeleteDialog();
        Bundle bundle = new Bundle();
        bundle.putString(Constant.CONTENT,"Really want to delete this Item??");
        deleteDialog.setArguments(bundle);
        deleteDialog.setListener(new DialogClickListener() {
            @Override
            public void positiveButtonClick() {
                deleteDialog.dismiss();
                mPresenter.deleteReceive(adapter.getItem(position));
            }
        });

        deleteDialog.show(getChildFragmentManager(),"HHH");*/
    }
}
