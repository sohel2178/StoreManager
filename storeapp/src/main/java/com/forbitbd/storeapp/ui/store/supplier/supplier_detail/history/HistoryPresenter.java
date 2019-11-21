package com.forbitbd.storeapp.ui.store.supplier.supplier_detail.history;

import com.forbitbd.androidutils.api.ServiceGenerator;
import com.forbitbd.storeapp.api.ApiClient;
import com.forbitbd.storeapp.models.Receive;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HistoryPresenter implements HistoryContract.Presenter {
    private HistoryContract.View mView;

    public HistoryPresenter(HistoryContract.View mView) {
        this.mView = mView;
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
