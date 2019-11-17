package com.forbitbd.storeapp.ui.store.supplier.supplier_detail;

import com.forbitbd.storeapp.api.ApiClient;
import com.forbitbd.storeapp.api.ServiceGenerator;
import com.forbitbd.storeapp.models.Receive;
import com.forbitbd.storeapp.models.Supplier;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SupplierDetailPresenter implements SupplierDetailContract.Presenter {

    private SupplierDetailContract.View mView;

    public SupplierDetailPresenter(SupplierDetailContract.View mView) {
        this.mView = mView;
    }

    @Override
    public void getSupplierReceive(Supplier supplier) {
        mView.showProgressDialog();

        ApiClient client = ServiceGenerator.createService(ApiClient.class);

        client.getSupplierReceive(supplier.getProject(),supplier.get_id())
                .enqueue(new Callback<List<Receive>>() {
                    @Override
                    public void onResponse(Call<List<Receive>> call, Response<List<Receive>> response) {
                        mView.hideProgressDialog();

                        if(response.isSuccessful()){
                            mView.update(response.body());
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Receive>> call, Throwable t) {
                        mView.hideProgressDialog();
                    }
                });
    }
}
