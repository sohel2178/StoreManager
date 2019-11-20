package com.forbitbd.storeapp.ui.store.report.monthly.received;

import android.content.Context;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.forbitbd.storeapp.R;
import com.forbitbd.storeapp.baseAdapter.BaseAdapter;
import com.forbitbd.storeapp.baseAdapter.BaseListener;
import com.forbitbd.storeapp.models.Receive;

public class ReceiveAdapter extends BaseAdapter<Receive, BaseListener,ReceiveHolder> {
    public ReceiveAdapter(Context context, BaseListener listener) {
        super(context, listener);
    }

    @NonNull
    @Override
    public ReceiveHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ReceiveHolder(inflate(R.layout.item_monthly_receive,parent),getListener());
    }
}
