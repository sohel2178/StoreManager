package com.forbitbd.storeapp.ui.store.received;

import android.content.Context;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.forbitbd.storeapp.R;
import com.forbitbd.storeapp.baseAdapter.BaseAdapter;
import com.forbitbd.storeapp.models.Receive;
import com.forbitbd.storeapp.models.Supplier;

import java.util.ArrayList;
import java.util.List;

public class ReceiveAdapter extends BaseAdapter<Receive,ReceiveListener,ReceiveHolder> {

    public ReceiveAdapter(Context context, ReceiveListener listener) {
        super(context, listener);
    }

    @NonNull
    @Override
    public ReceiveHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ReceiveHolder(inflate(R.layout.item_receive,parent),getListener());
    }

    private int getPosition(Receive receive){
        for (Receive x: getItems()){
            if(x.get_id().equals(receive.get_id())){
                return getItems().indexOf(x);
            }
        }

        return 0;
    }

    public void update(Receive receive){
        int position = getPosition(receive);
        update(receive,position);
    }

    public void remove(Receive receive){
        int position = getPosition(receive);
        remove(position);
    }

    public void removeSupplierItems(Supplier supplier){
        List<Receive> tmpList = new ArrayList<>();

        for (Receive x: getItems()){
            if(!x.getReceived_from().get_id().equals(supplier.get_id())){
                tmpList.add(x);
            }
        }

        setItems(tmpList);
    }
}
