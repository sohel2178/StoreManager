package com.forbitbd.storeapp.ui.store.report;

import com.forbitbd.androidutils.api.ServiceGenerator;
import com.forbitbd.storeapp.api.ApiClient;
import com.forbitbd.storeapp.models.StoreResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReportPresenter implements ReportContract.Presenter {

    private ReportContract.View mView;

    public ReportPresenter(ReportContract.View mView) {
        this.mView = mView;
    }

    @Override
    public void getStoreData(String projectID) {
        mView.showProgressDialog();

        ApiClient client = ServiceGenerator.createService(ApiClient.class);

        client.getStoreData(projectID)
                .enqueue(new Callback<StoreResponse>() {
                    @Override
                    public void onResponse(Call<StoreResponse> call, Response<StoreResponse> response) {
                        mView.hideProgressDialog();

                        if(response.isSuccessful()){
                            mView.initializeData(response.body());
                        }
                    }

                    @Override
                    public void onFailure(Call<StoreResponse> call, Throwable t) {
                        mView.hideProgressDialog();
                    }
                });
    }
}
