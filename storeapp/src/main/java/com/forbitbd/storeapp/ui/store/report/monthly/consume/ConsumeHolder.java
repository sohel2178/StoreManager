package com.forbitbd.storeapp.ui.store.report.monthly.consume;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.forbitbd.androidutils.models.Consume;
import com.forbitbd.androidutils.utils.MyUtil;
import com.forbitbd.storeapp.R;
import com.forbitbd.storeapp.baseAdapter.BaseHolder;
import com.forbitbd.storeapp.baseAdapter.BaseListener;

public class ConsumeHolder extends BaseHolder<Consume, BaseListener> {

    TextView tvName,tvDate,tvQuantity;

    public ConsumeHolder(@NonNull View itemView,BaseListener listener) {
        super(itemView);
        tvName = itemView.findViewById(R.id.name);
        tvDate = itemView.findViewById(R.id.date);
        tvQuantity = itemView.findViewById(R.id.quantity);
    }

    @Override
    public void bind(Consume consume) {
        tvName.setText(consume.getName());
        tvDate.setText(MyUtil.getStringDate(consume.getDate()));
        tvQuantity.setText((int) consume.getQuantity()+" "+consume.getUnit());
    }
}
