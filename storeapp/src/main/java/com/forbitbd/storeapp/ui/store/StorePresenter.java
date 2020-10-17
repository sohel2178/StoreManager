package com.forbitbd.storeapp.ui.store;


import com.forbitbd.androidutils.api.ServiceGenerator;
import com.forbitbd.androidutils.models.Project;
import com.forbitbd.storeapp.api.ApiClient;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StorePresenter implements StoreContract.Presenter {

    private StoreContract.View mView;

    public StorePresenter(StoreContract.View mView) {
        this.mView = mView;
    }

    @Override
    public void startAddSupplierActivity() {
        mView.startAddSupplierActivity();
    }

    @Override
    public void startReceivedActivity() {
        mView.startReceivedActivity();
    }

    @Override
    public void startConsumedActivity() {
        mView.startConsumedActivity();
    }

    @Override
    public void startReportActivity() {
        mView.startReportActivity();
    }

    @Override
    public void filter(String query) {
        mView.filter(query);
    }

    @Override
    public void downloadFile(Project project) {
        mView.showProgressDialog();

        ApiClient client = ServiceGenerator.createService(ApiClient.class);

        client.downloadFile(project.get_id())
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        mView.hideProgressDialog();

                        if(response.isSuccessful()){
                            String path = mView.saveFile(response.body());

                            if(path!=null){
                                mView.openFile(path);
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        mView.hideProgressDialog();
                    }
                });

    }
}
