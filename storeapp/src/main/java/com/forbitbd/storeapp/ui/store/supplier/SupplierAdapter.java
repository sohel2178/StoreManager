package com.forbitbd.storeapp.ui.store.supplier;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;

import com.forbitbd.androidutils.models.SharedProject;
import com.forbitbd.storeapp.R;
import com.forbitbd.storeapp.baseAdapter.BaseAdapter;
import com.forbitbd.storeapp.models.Supplier;

import java.util.ArrayList;
import java.util.List;


public class SupplierAdapter extends BaseAdapter<Supplier,SupplierListener,SupplierHolder> implements Filterable {

    private List<Supplier> originalList;
    private SharedProject.Permission storePermission;

    public SupplierAdapter(Context context, SupplierListener listener,SharedProject.Permission storePermission) {
        super(context, listener);
        this.storePermission = storePermission;
    }

    @NonNull
    @Override
    public SupplierHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SupplierHolder(inflate(R.layout.item_supplier,parent),getListener(),storePermission);
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

    public int getPosition(Supplier supplier){
        for (Supplier x:getItems()){
            if(x.get_id().equals(supplier.get_id())){
                return getItems().indexOf(x);
            }
        }

        return -1;
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

                List<Supplier> filteredList = new ArrayList<>();

                if(charString.isEmpty()){
                    filteredList = originalList;
                }else{
                    List<Supplier> tmpList = new ArrayList<>();
                    for (Supplier x: originalList){
                        if(x.getName().toLowerCase().contains(charString.toLowerCase())){
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

                setItems((List<Supplier>) results.values);

            }
        };
    }
}
