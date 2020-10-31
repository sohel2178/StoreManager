package com.forbitbd.storeapp.ui.store.received;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;

import com.forbitbd.androidutils.models.SharedProject;
import com.forbitbd.storeapp.R;
import com.forbitbd.storeapp.baseAdapter.BaseAdapter;
import com.forbitbd.storeapp.models.Receive;
import com.forbitbd.storeapp.models.Supplier;

import java.util.ArrayList;
import java.util.List;

public class ReceiveAdapter extends BaseAdapter<Receive,ReceiveListener,ReceiveHolder> implements Filterable {

    private List<Receive> originalList;
    private SharedProject.Permission storePermission;

    public ReceiveAdapter(Context context, ReceiveListener listener){
        super(context, listener);
    }

    public ReceiveAdapter(Context context, ReceiveListener listener,SharedProject.Permission storePermission) {
        super(context, listener);
        this.storePermission = storePermission;
    }

    @NonNull
    @Override
    public ReceiveHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ReceiveHolder(inflate(R.layout.item_receive,parent),getListener(),storePermission);
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

    @Override
    public Filter getFilter() {
        if(originalList==null){
            originalList = getItems();
        }
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String charString = constraint.toString();

                List<Receive> filteredList = new ArrayList<>();

                if(charString.isEmpty()){
                    filteredList = originalList;
                }else{
                    List<Receive> tmpList = new ArrayList<>();
                    for (Receive x: originalList){
                        if(x.getName().toLowerCase().contains(charString.toLowerCase())
                         || x.getInvoice_no().toLowerCase().contains(charString.toLowerCase())){
                            tmpList.add(x);
                        }
                    }

                    filteredList = tmpList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values=filteredList;

                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                setItems((List<Receive>) results.values);
            }
        };
    }
}
