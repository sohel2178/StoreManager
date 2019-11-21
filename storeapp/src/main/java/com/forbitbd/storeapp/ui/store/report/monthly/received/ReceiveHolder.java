package com.forbitbd.storeapp.ui.store.report.monthly.received;

import android.view.View;
import android.widget.TextView;

import com.forbitbd.androidutils.utils.MyUtil;
import com.forbitbd.storeapp.R;
import com.forbitbd.storeapp.baseAdapter.BaseHolder;
import com.forbitbd.storeapp.baseAdapter.BaseListener;
import com.forbitbd.storeapp.models.Receive;

public class ReceiveHolder extends BaseHolder<Receive, BaseListener> {

    TextView tvName,tvDate,tvQuantity;

    public ReceiveHolder(View itemView, BaseListener listener) {
        super(itemView, listener);

        tvName = itemView.findViewById(R.id.name);
        tvDate = itemView.findViewById(R.id.date);
        tvQuantity = itemView.findViewById(R.id.quantity);
    }

    @Override
    public void bind(Receive receive) {
        tvName.setText(receive.getName());
        tvDate.setText(MyUtil.getStringDate(receive.getDate()));
        tvQuantity.setText((int) receive.getQuantity()+" "+receive.getUnit());
    }
}
