package com.forbitbd.storeapp.ui.store.receivedAdd;

import android.util.Log;

import com.forbitbd.androidutils.api.ServiceGenerator;
import com.forbitbd.androidutils.utils.MyUtil;
import com.forbitbd.storeapp.api.ApiClient;
import com.forbitbd.storeapp.models.Receive;

import java.util.HashMap;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReceivedPresenter implements ReceivedContract.Presenter {

    private ReceivedContract.View mView;

    public ReceivedPresenter(ReceivedContract.View mView) {
        this.mView = mView;
    }

    @Override
    public void bind() {
        mView.bind();
    }

    @Override
    public void openCalendar() {
        mView.openCalendar();
    }

    @Override
    public void checkBeforeSave() {
        mView.checkBeforeSave();
    }

    @Override
    public boolean validate(Receive receive) {
        mView.clearPreError();
        if(receive.getReceived_from()==null){
            mView.showError("Please Select a Supplier",5);
            return false;
        }

        if(receive.getDate()==null){
            mView.showToast("Please Select Received date");
            return false;
        }

        if(receive.getInvoice_no().equals("")){
            mView.showError("Invoice Number Should not Empty",1);
            return false;
        }

        if(receive.getName().equals("")){
            mView.showError("Material Name Should not Empty",2);
            return false;
        }

        if(receive.getUnit().equals("")){
            mView.showError("Material Unit Should not Empty",3);
            return false;
        }

        if(receive.getQuantity() <=0){
            mView.showError("Quantity Should be Greater than 0",4);
            return false;
        }
        return true;
    }

    @Override
    public void openCamera() {
        mView.openCamera();
    }

    @Override
    public void saveReceive(Receive receive, byte[] bytes) {

        mView.showProgressDialog();

        MultipartBody.Part part=null;

        if(bytes!=null){
            RequestBody requestFile = RequestBody.create(MediaType.parse("image/jpeg"), bytes);
            part = MultipartBody.Part.createFormData("image", "image.jpg", requestFile);
        }

        RequestBody name = RequestBody.create(MediaType.parse("text/plain"), receive.getName());
        RequestBody unit = RequestBody.create(MediaType.parse("text/plain"), receive.getUnit());
        RequestBody invoice_no = RequestBody.create(MediaType.parse("text/plain"), receive.getInvoice_no());
        RequestBody quantity = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(receive.getQuantity()));
        RequestBody received_from = RequestBody.create(MediaType.parse("text/plain"), receive.getReceived_from().get_id());
        RequestBody date = RequestBody.create(MediaType.parse("text/plain"), MyUtil.getStringDate(receive.getDate()));

        HashMap<String, RequestBody> map = new HashMap<>();
        map.put("name", name);
        map.put("unit", unit);
        map.put("invoice_no", invoice_no);
        map.put("quantity", quantity);
        map.put("received_from", received_from);
        map.put("date", date);

        ApiClient client = ServiceGenerator.createService(ApiClient.class);

        client.addReceive(receive.getProject(),part,map)
                .enqueue(new Callback<Receive>() {
                    @Override
                    public void onResponse(Call<Receive> call, Response<Receive> response) {
                        mView.hideProgressDialog();
                        Log.d("HHHHHHH","Success "+response.code());
                        if(response.isSuccessful()){
                            Log.d("HHHHHHH","Yes");
                            mView.complete(response.body());
                        }
                    }

                    @Override
                    public void onFailure(Call<Receive> call, Throwable t) {
                        Log.d("HHHHHHH","NO");
                        mView.hideProgressDialog();
                    }
                });

    }

    @Override
    public void updateReceive(final Receive receive, byte[] bytes) {

        mView.showProgressDialog();

        MultipartBody.Part part=null;

        if(bytes!=null){
            RequestBody requestFile = RequestBody.create(MediaType.parse("image/jpeg"), bytes);
            part = MultipartBody.Part.createFormData("image", "image.jpg", requestFile);
        }

        RequestBody name = RequestBody.create(MediaType.parse("text/plain"), receive.getName());
        RequestBody unit = RequestBody.create(MediaType.parse("text/plain"), receive.getUnit());
        RequestBody invoice_no = RequestBody.create(MediaType.parse("text/plain"), receive.getInvoice_no());
        RequestBody quantity = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(receive.getQuantity()));
        RequestBody received_from = RequestBody.create(MediaType.parse("text/plain"), receive.getReceived_from().get_id());
        RequestBody date = RequestBody.create(MediaType.parse("text/plain"), MyUtil.getStringDate(receive.getDate()));

        HashMap<String, RequestBody> map = new HashMap<>();
        map.put("name", name);
        map.put("unit", unit);
        map.put("invoice_no", invoice_no);
        map.put("quantity", quantity);
        map.put("received_from", received_from);
        map.put("date", date);

        ApiClient client = ServiceGenerator.createService(ApiClient.class);

        client.updateReceive(receive.getProject(),receive.get_id(),part,map)
                .enqueue(new Callback<Receive>() {
                    @Override
                    public void onResponse(Call<Receive> call, Response<Receive> response) {
                        mView.hideProgressDialog();

                        if(response.isSuccessful()){
                            mView.complete(response.body());
                        }
                    }

                    @Override
                    public void onFailure(Call<Receive> call, Throwable t) {
                        mView.hideProgressDialog();
                    }
                });

    }
}
