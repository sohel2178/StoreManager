package com.forbitbd.storeapp.ui.store.report.daily;

import android.content.Context;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.forbitbd.storeapp.R;
import com.forbitbd.storeapp.baseAdapter.BaseAdapter;
import com.forbitbd.storeapp.baseAdapter.BaseListener;
import com.forbitbd.storeapp.models.ReportSummery;

public class DailyAdapter extends BaseAdapter<ReportSummery, BaseListener,DailyHolder> {

    public DailyAdapter(Context context, BaseListener listener) {
        super(context, listener);
    }

    @NonNull
    @Override
    public DailyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new DailyHolder(inflate(R.layout.item_daily,parent),getListener());
    }
}
