package com.forbitbd.storeapp.ui.store.supplier;



import com.forbitbd.androidutils.api.ServiceGenerator;
import com.forbitbd.storeapp.api.ApiClient;
import com.forbitbd.storeapp.models.Supplier;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SupplierPresenter implements SupplierContract.Presenter {

    private SupplierContract.View mView;

    public SupplierPresenter(SupplierContract.View mView) {
        this.mView = mView;
    }

    @Override
    public void getProjectSuppliers(String projectID) {
        mView.showProgressDialog();

        ApiClient client = ServiceGenerator.createService(ApiClient.class);

        client.getProjectSuppliers(projectID)
                .enqueue(new Callback<List<Supplier>>() {
                    @Override
                    public void onResponse(Call<List<Supplier>> call, Response<List<Supplier>> response) {
                        mView.hideProgressDialog();
                        if(response.isSuccessful()){
                            mView.renderAdapter(response.body());
                        }else {

                        }
                    }

                    @Override
                    public void onFailure(Call<List<Supplier>> call, Throwable t) {
                        mView.hideProgressDialog();
                    }
                });
    }

    @Override
    public void deleteSupplier(final Supplier supplier) {
        mView.showProgressDialog();

        ApiClient client = ServiceGenerator.createService(ApiClient.class);

        client.deleteSupplier(supplier.getProject(),supplier.get_id())
                .enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        mView.hideProgressDialog();

                        if(response.isSuccessful()){
                            mView.removeFromAdapter(supplier);
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        mView.hideProgressDialog();
                    }
                });
    }

    @Override
    public void startSupplierDetailActivity(Supplier supplier) {
        mView.startSupplierDetailActivity(supplier);
    }
}
