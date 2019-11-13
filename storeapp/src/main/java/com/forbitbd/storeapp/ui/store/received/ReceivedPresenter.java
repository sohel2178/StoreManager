package com.forbitbd.storeapp.ui.store.received;

import com.forbitbd.storeapp.api.ApiClient;
import com.forbitbd.storeapp.api.ServiceGenerator;
import com.forbitbd.storeapp.models.Receive;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReceivedPresenter implements ReceivedContract.Presenter {

    private ReceivedContract.View mView;

    public ReceivedPresenter(ReceivedContract.View mView) {
        this.mView = mView;
    }

    @Override
    public void getProjectReceives(String projectID) {
        mView.showProgressDialog();

        ApiClient client = ServiceGenerator.createService(ApiClient.class);

        client.getProjectReceives(projectID)
                .enqueue(new Callback<List<Receive>>() {
                    @Override
                    public void onResponse(Call<List<Receive>> call, Response<List<Receive>> response) {
                        mView.hideProgressDialog();

                        if(response.isSuccessful()){
                            mView.renderAdapter(response.body());
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Receive>> call, Throwable t) {
                        mView.hideProgressDialog();
                    }
                });
    }

    @Override
    public void deleteReceive(final Receive receive) {
        mView.showProgressDialog();
        ApiClient client = ServiceGenerator.createService(ApiClient.class);

        client.deleteReceive(receive.getProject(),receive.get_id())
                .enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        mView.hideProgressDialog();

                        if(response.isSuccessful()){
                            mView.removeFromAdapter(receive);
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        mView.hideProgressDialog();
                    }
                });
    }
}
