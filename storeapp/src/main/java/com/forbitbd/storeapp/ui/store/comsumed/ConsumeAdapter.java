package com.forbitbd.storeapp.ui.store.comsumed;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;

import com.forbitbd.androidutils.models.Consume;
import com.forbitbd.androidutils.models.SharedProject;
import com.forbitbd.storeapp.R;
import com.forbitbd.storeapp.baseAdapter.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

public class ConsumeAdapter extends BaseAdapter<Consume,ConsumeListener,ConsumeHolder> implements Filterable {

    private List<Consume> originalList;
    private SharedProject.Permission storePermission;

    public ConsumeAdapter(Context context, ConsumeListener listener,SharedProject.Permission storePermission) {
        super(context, listener);
        this.storePermission = storePermission;
    }

    @NonNull
    @Override
    public ConsumeHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ConsumeHolder(inflate(R.layout.item_consume,parent),getListener(),storePermission);
    }

    public int getPosition(Consume consume){
        for (Consume x: getItems()){
            if(x.get_id().equals(consume.get_id())){
                return getItems().indexOf(x);
            }
        }
        return 0;
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

                List<Consume> filteredList = new ArrayList<>();

                if(charString.isEmpty()){
                    filteredList = originalList;
                }else{
                    List<Consume> tmpList = new ArrayList<>();
                    for (Consume x: originalList){
                        if(x.getName().toLowerCase().contains(charString.toLowerCase())
                         || x.getIssue_to().toLowerCase().contains(charString.toLowerCase())
                         || x.getWhere_used().getName().toLowerCase().contains(charString.toLowerCase())){
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
                setItems((List<Consume>) results.values);
            }
        };
    }
}
