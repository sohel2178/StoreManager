package com.forbitbd.storeapp.ui.store.report.monthly.consume;

import android.content.Context;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.forbitbd.storeapp.R;
import com.forbitbd.storeapp.baseAdapter.BaseAdapter;
import com.forbitbd.storeapp.baseAdapter.BaseListener;
import com.forbitbd.storeapp.models.Consume;

public class ConsumeAdapter extends BaseAdapter<Consume, BaseListener,ConsumeHolder> {
    public ConsumeAdapter(Context context, BaseListener listener) {
        super(context, listener);
    }

    @NonNull
    @Override
    public ConsumeHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ConsumeHolder(inflate(R.layout.item_monthly_receive,parent),getListener());
    }
}
