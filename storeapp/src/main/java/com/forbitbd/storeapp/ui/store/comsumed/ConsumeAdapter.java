package com.forbitbd.storeapp.ui.store.comsumed;

import android.content.Context;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.forbitbd.storeapp.R;
import com.forbitbd.storeapp.baseAdapter.BaseAdapter;
import com.forbitbd.storeapp.models.Consume;

public class ConsumeAdapter extends BaseAdapter<Consume,ConsumeListener,ConsumeHolder> {
    public ConsumeAdapter(Context context, ConsumeListener listener) {
        super(context, listener);
    }

    @NonNull
    @Override
    public ConsumeHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ConsumeHolder(inflate(R.layout.item_consume,parent),getListener());
    }

    public int getPosition(Consume consume){
        for (Consume x: getItems()){
            if(x.get_id().equals(consume.get_id())){
                return getItems().indexOf(x);
            }
        }

        return 0;
    }
}
