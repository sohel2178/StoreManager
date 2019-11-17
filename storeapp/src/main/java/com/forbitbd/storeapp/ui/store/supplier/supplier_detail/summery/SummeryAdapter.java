package com.forbitbd.storeapp.ui.store.supplier.supplier_detail.summery;

import android.content.Context;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.forbitbd.storeapp.R;
import com.forbitbd.storeapp.baseAdapter.BaseAdapter;
import com.forbitbd.storeapp.baseAdapter.BaseListener;
import com.forbitbd.storeapp.models.Summery;

public class SummeryAdapter extends BaseAdapter<Summery, BaseListener,SummeryHolder> {
    public SummeryAdapter(Context context, BaseListener listener) {
        super(context, listener);
    }

    @NonNull
    @Override
    public SummeryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SummeryHolder(inflate(R.layout.item_summery,parent),getListener());
    }
}
