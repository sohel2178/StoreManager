package com.forbitbd.storeapp.ui.store.supplier;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.forbitbd.androidutils.dialog.delete.DeleteDialog;
import com.forbitbd.androidutils.dialog.delete.DialogClickListener;
import com.forbitbd.storeapp.R;
import com.forbitbd.storeapp.models.Supplier;
import com.forbitbd.storeapp.ui.store.StoreBaseFragment;
import com.forbitbd.storeapp.ui.store.supplier.supplier_detail.SupplierDetailActivity;
import com.forbitbd.storeapp.utils.Constant;

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
        this.adapter = new SupplierAdapter(getContext(),this,getSharedProject().getStore());
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



    }

    @Override
    public void onResume() {
        super.onResume();
        if(adapter.getItemCount()<=0){
            mPresenter.getProjectSuppliers(getProject().get_id());
        }
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
    public void removeFromAdapter(Supplier supplier) {
        adapter.remove(adapter.getPosition(supplier));

        get_activity().removeSupplierReceive(supplier);
    }

    @Override
    public void startSupplierDetailActivity(Supplier supplier) {

        Intent intent = new Intent(getContext(), SupplierDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(Constant.SUPPLIER,supplier);
        intent.putExtras(bundle);
        startActivity(intent);

    }

    @Override
    public void onItemClick(int position) {
        mPresenter.startSupplierDetailActivity(adapter.getItem(position));
    }

    @Override
    public void onItemUpdate(int position) {
        Supplier supplier = adapter.getItem(position);
        get_activity().startUpdateSupplierActivity(supplier);
    }

    @Override
    public void onItemRemove(final int position) {
        final DeleteDialog deleteDialog = new DeleteDialog();
        Bundle bundle = new Bundle();
        bundle.putString(Constant.CONTENT,"Do you Really want to delete this Item?? Related Transaction will also be deleted!!!");
        deleteDialog.setArguments(bundle);
        deleteDialog.setListener(new DialogClickListener() {
            @Override
            public void positiveButtonClick() {
                deleteDialog.dismiss();
                mPresenter.deleteSupplier(adapter.getItem(position));
            }
        });

        deleteDialog.show(getChildFragmentManager(),"HHH");

    }

    @Override
    public void onImageClick(int position) {
        get_activity().startZoomImageActivity(adapter.getItem(position).getImage());
    }

    public void filter(String query){
        adapter.getFilter().filter(query);
    }
}
