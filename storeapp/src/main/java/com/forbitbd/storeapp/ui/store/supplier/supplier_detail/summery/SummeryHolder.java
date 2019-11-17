package com.forbitbd.storeapp.ui.store.supplier.supplier_detail.summery;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.forbitbd.storeapp.R;
import com.forbitbd.storeapp.baseAdapter.BaseAdapter;
import com.forbitbd.storeapp.baseAdapter.BaseHolder;
import com.forbitbd.storeapp.baseAdapter.BaseListener;
import com.forbitbd.storeapp.models.Summery;

public class SummeryHolder extends BaseHolder<Summery, BaseListener> {

    TextView tvName,tvQuantity;


    public SummeryHolder(View itemView, BaseListener listener) {
        super(itemView, listener);

        tvName = itemView.findViewById(R.id.name);
        tvQuantity = itemView.findViewById(R.id.quantity);
    }

    @Override
    public void bind(Summery summery) {
        tvName.setText(summery.getName());
        tvQuantity.setText(summery.getQuantity()+" "+summery.getUnit());
    }
}
