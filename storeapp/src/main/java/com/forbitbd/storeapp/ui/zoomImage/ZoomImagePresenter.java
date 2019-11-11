package com.forbitbd.storeapp.ui.zoomImage;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.forbitbd.storeapp.api.ApiClient;
import com.forbitbd.storeapp.api.ServiceGenerator;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ZoomImagePresenter implements ZoomImageContract.Presenter {

    private ZoomImageContract.View mView;

    public ZoomImagePresenter(ZoomImageContract.View mView) {
        this.mView = mView;
    }

    @Override
    public void getImage(String url) {
        mView.showProgressDialog();

        ApiClient client = ServiceGenerator.createService(ApiClient.class);
        client.getImage(url)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        mView.hideProgressDialog();

                        if(response.isSuccessful()){
                            Log.d("HHHHHHH","SUCCESSFUL");
                            Bitmap bitmap = BitmapFactory.decodeStream(response.body().byteStream());
                            mView.updateImage(bitmap);
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        mView.hideProgressDialog();

                        Log.d("HHHHHHH","UNSUCCESSFUL "+t.getMessage());
                    }
                });
    }
}
