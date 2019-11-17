package com.forbitbd.storeapp.ui.store.report.daily;

import android.view.View;
import android.widget.TextView;

import com.forbitbd.storeapp.R;
import com.forbitbd.storeapp.baseAdapter.BaseHolder;
import com.forbitbd.storeapp.baseAdapter.BaseListener;
import com.forbitbd.storeapp.models.ReportSummery;

public class DailyHolder extends BaseHolder<ReportSummery, BaseListener> {

    TextView tvName,tvReceived,tvConsumed;

    public DailyHolder(View itemView, BaseListener listener) {
        super(itemView, listener);
        tvName = itemView.findViewById(R.id.name);
        tvReceived = itemView.findViewById(R.id.received);
        tvConsumed = itemView.findViewById(R.id.consumed);
    }

    @Override
    public void bind(ReportSummery reportSummery) {
        tvName.setText(reportSummery.getItem());
        tvReceived.setText(String.valueOf(reportSummery.getReceived()));
        tvConsumed.setText(String.valueOf(reportSummery.getConsume()));
    }
}
