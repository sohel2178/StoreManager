package com.forbitbd.storeapp.ui.store.supplier;

import android.content.Context;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.forbitbd.storeapp.R;
import com.forbitbd.storeapp.baseAdapter.BaseAdapter;
import com.forbitbd.storeapp.models.Supplier;


public class SupplierAdapter extends BaseAdapter<Supplier,SupplierListener,SupplierHolder> {

    public SupplierAdapter(Context context, SupplierListener listener) {
        super(context, listener);
    }

    @NonNull
    @Override
    public SupplierHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SupplierHolder(inflate(R.layout.item_supplier,parent),getListener());
    }

    public void update(Supplier supplier){

        int position = getPosition(supplier);
        update(supplier,position);

    }

    public void addRelative(Supplier supplier){
        int position = getRelativePosition(supplier);
        addToposition(supplier,position);
    }

    private int getRelativePosition(Supplier supplier){
        int position = 0;
        for (Supplier x: getItems()){
            if(supplier.getName().compareTo(x.getName())>0){
                position = getItems().indexOf(x)+1;
            }
        }
        return position;
    }

    private int getPosition(Supplier supplier){
        for (Supplier x:getItems()){
            if(x.get_id().equals(supplier.get_id())){
                return getItems().indexOf(x);
            }
        }

        return -1;
    }
}
