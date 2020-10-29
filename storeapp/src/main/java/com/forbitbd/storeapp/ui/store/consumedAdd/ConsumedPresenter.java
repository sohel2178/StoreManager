package com.forbitbd.storeapp.ui.store.consumedAdd;

import android.util.Log;

import com.forbitbd.androidutils.api.ServiceGenerator;
import com.forbitbd.androidutils.models.Consume;
import com.forbitbd.androidutils.models.Task;
import com.forbitbd.androidutils.utils.MyUtil;
import com.forbitbd.storeapp.api.ApiClient;

import java.util.HashMap;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ConsumedPresenter implements ConsumedContract.Presenter {

    private ConsumedContract.View mView;

    public ConsumedPresenter(ConsumedContract.View mView) {
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
    public boolean validate(Consume consume) {
        mView.clearPreError();

        if(consume.getDate()==null){
            mView.showToast("Please Select Consumption date");
            return false;
        }

        if(consume.getName().equals("")){
            mView.showError("Material Name Should not Empty",1);
            return false;
        }

        if(consume.getUnit().equals("")){
            mView.showError("Material Unit Should not Empty",2);
            return false;
        }

        if(consume.getIssue_to().equals("")){
            mView.showError("Field Should not Empty",3);
            return false;
        }

        if(consume.getWhere_used()==null){
            mView.showError("Where used the Material Should not Empty",4);
            return false;
        }

        if(consume.getQuantity() <=0){
            mView.showError("Quantity Should be Greater than 0",5);
            return false;
        }
        return true;
    }

    @Override
    public void openCamera() {
        mView.openCamera();

    }

    @Override
    public void saveConsume(Consume consume, byte[] bytes) {
        mView.showProgressDialog();

        MultipartBody.Part part=null;

        if(bytes!=null){
            RequestBody requestFile = RequestBody.create(MediaType.parse("image/jpeg"), bytes);
            part = MultipartBody.Part.createFormData("image", "image.jpg", requestFile);
        }

        RequestBody name = RequestBody.create(MediaType.parse("text/plain"), consume.getName());
        RequestBody unit = RequestBody.create(MediaType.parse("text/plain"), consume.getUnit());
        RequestBody issue_to = RequestBody.create(MediaType.parse("text/plain"), consume.getIssue_to());
        RequestBody where_used = RequestBody.create(MediaType.parse("text/plain"), consume.getWhere_used().get_id());
        RequestBody quantity = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(consume.getQuantity()));
        RequestBody date = RequestBody.create(MediaType.parse("text/plain"), MyUtil.getStringDate(consume.getDate()));

        HashMap<String, RequestBody> map = new HashMap<>();
        map.put("name", name);
        map.put("unit", unit);
        map.put("issue_to", issue_to);
        map.put("quantity", quantity);
        map.put("where_used", where_used);
        map.put("date", date);

        ApiClient client = ServiceGenerator.createService(ApiClient.class);

        client.addConsume(consume.getProject(),part,map)
                .enqueue(new Callback<Consume>() {
                    @Override
                    public void onResponse(Call<Consume> call, Response<Consume> response) {
                        mView.hideProgressDialog();

                        if(response.isSuccessful()){
                            mView.complete(response.body());
                            Log.d("FATHER","Success");
                        }else{
                            Log.d("FATHER","Failed "+response.code());
                        }
                    }

                    @Override
                    public void onFailure(Call<Consume> call, Throwable t) {
                        Log.d("FATHER","Failed "+t.getMessage());
                        mView.hideProgressDialog();
                    }
                });



    }

    @Override
    public void updateConsume(final Consume consume, byte[] bytes) {

        mView.showProgressDialog();

        MultipartBody.Part part=null;

        if(bytes!=null){
            RequestBody requestFile = RequestBody.create(MediaType.parse("image/jpeg"), bytes);
            part = MultipartBody.Part.createFormData("image", "image.jpg", requestFile);
        }

        RequestBody name = RequestBody.create(MediaType.parse("text/plain"), consume.getName());
        RequestBody unit = RequestBody.create(MediaType.parse("text/plain"), consume.getUnit());
        RequestBody issue_to = RequestBody.create(MediaType.parse("text/plain"), consume.getIssue_to());
        RequestBody where_used = RequestBody.create(MediaType.parse("text/plain"), consume.getWhere_used().get_id());
        RequestBody quantity = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(consume.getQuantity()));
        RequestBody date = RequestBody.create(MediaType.parse("text/plain"), MyUtil.getStringDate(consume.getDate()));

        HashMap<String, RequestBody> map = new HashMap<>();
        map.put("name", name);
        map.put("unit", unit);
        map.put("issue_to", issue_to);
        map.put("quantity", quantity);
        map.put("where_used", where_used);
        map.put("date", date);


        ApiClient client = ServiceGenerator.createService(ApiClient.class);
        client.updateConsume(consume.getProject(),consume.get_id(),part,map)
                .enqueue(new Callback<Consume>() {
                    @Override
                    public void onResponse(Call<Consume> call, Response<Consume> response) {
                        mView.hideProgressDialog();

                        if(response.isSuccessful()){
                            mView.complete(consume);
                        }
                    }

                    @Override
                    public void onFailure(Call<Consume> call, Throwable t) {
                        mView.hideProgressDialog();
                    }
                });

    }

    @Override
    public void getAllTask(String projectId) {
        ApiClient client = ServiceGenerator.createService(ApiClient.class);
        client.getProjectTasks(projectId)
                .enqueue(new Callback<List<Task>>() {
                    @Override
                    public void onResponse(Call<List<Task>> call, Response<List<Task>> response) {
                        if(response.isSuccessful()){
                            mView.updateTaskAdapter(response.body());
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Task>> call, Throwable t) {
                        Log.d("HHHHH","Fail");
                    }
                });
    }
}
