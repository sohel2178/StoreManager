package com.forbitbd.storeapp.ui.store.supplier;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.forbitbd.storeapp.R;
import com.forbitbd.storeapp.models.Supplier;
import com.forbitbd.storeapp.ui.store.StoreBaseFragment;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class SupplierFragment extends StoreBaseFragment
        implements SupplierContract.View,SupplierListener {

    private SupplierPresenter mPresenter;

    private SupplierAdapter adapter;


    public SupplierFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = new SupplierPresenter(this);
        this.adapter = new SupplierAdapter(getContext(),this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_supplier, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
        mPresenter.getProjectSuppliers(getProject().get_id());
    }

    public void addSupplierToAdapter(Supplier supplier){
        adapter.addRelative(supplier);
    }


    public List<Supplier> getSuppliers(){
        return adapter.getItems();
    }


    public void updateSupplierToAdapter(Supplier supplier){
        adapter.update(supplier);
    }

    @Override
    public void renderAdapter(List<Supplier> supplierList) {
        adapter.setItems(supplierList);
    }

    @Override
    public void onItemClick(int position) {

    }

    @Override
    public void onItemUpdate(int position) {
        Supplier supplier = adapter.getItem(position);
        get_activity().startUpdateSupplierActivity(supplier);
    }

    @Override
    public void onItemRemove(int position) {

    }
}
