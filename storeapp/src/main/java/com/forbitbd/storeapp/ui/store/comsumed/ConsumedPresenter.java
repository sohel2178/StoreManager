package com.forbitbd.storeapp.ui.store.comsumed;

import com.forbitbd.storeapp.api.ApiClient;
import com.forbitbd.storeapp.api.ServiceGenerator;
import com.forbitbd.storeapp.models.Consume;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ConsumedPresenter implements ConsumedContract.Presenter {

    private ConsumedContract.View mView;

    public ConsumedPresenter(ConsumedContract.View mView) {
        this.mView = mView;
    }

    @Override
    public void getProjectConsumes(String projectID) {
        ApiClient client = ServiceGenerator.createService(ApiClient.class);

        client.getProjectConsumes(projectID)
                .enqueue(new Callback<List<Consume>>() {
                    @Override
                    public void onResponse(Call<List<Consume>> call, Response<List<Consume>> response) {
                        mView.hideProgressDialog();

                        if(response.isSuccessful()){
                            mView.renderAdapter(response.body());
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Consume>> call, Throwable t) {
                        mView.hideProgressDialog();
                    }
                });
    }

    @Override
    public void deleteConsume(final Consume consume) {

        mView.showProgressDialog();

        ApiClient client = ServiceGenerator.createService(ApiClient.class);

        client.deleteConsume(consume.getProject(),consume.get_id())
                .enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        mView.hideProgressDialog();

                        if(response.isSuccessful()){
                            mView.removeFromAdapter(consume);
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        mView.hideProgressDialog();
                    }
                });

    }
}
