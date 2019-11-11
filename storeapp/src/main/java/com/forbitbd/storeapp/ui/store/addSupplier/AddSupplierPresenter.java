package com.forbitbd.storeapp.ui.store.addSupplier;



import com.forbitbd.storeapp.api.ApiClient;
import com.forbitbd.storeapp.api.ServiceGenerator;
import com.forbitbd.storeapp.models.Supplier;

import java.util.HashMap;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddSupplierPresenter implements AddSupplierContract.Presenter {

    private AddSupplierContract.View mView;

    public AddSupplierPresenter(AddSupplierContract.View mView) {
        this.mView = mView;
    }

    @Override
    public void openCamera() {
        mView.openCamera();
    }

    @Override
    public boolean validate(Supplier supplier) {
        mView.clearPreError();

        if(supplier.getName().equals("")){
            mView.showValidationError("Empty Field is Not Allowed",1);
            return false;
        }

        if(supplier.getContact().equals("")){
            mView.showValidationError("Empty Field is Not Allowed",2);
            return false;
        }

        if(supplier.getMaterial_supply().equals("")){
            mView.showValidationError("Empty Field is Not Allowed",4);
            return false;
        }


        return true;
    }

    @Override
    public void saveSupplier(Supplier supplier, byte[] bytes) {
        mView.showProgressDialog();

        MultipartBody.Part part=null;

        if(bytes!=null){
            RequestBody requestFile = RequestBody.create(MediaType.parse("image/jpeg"), bytes);
            //MultipartBody.Part body = MultipartBody.Part.createFormData("image", "image.jpg", requestFile);
            // RequestBody requestFile = RequestBody.create(MediaType.parse("image/*"), bytes);
            // Create MultipartBody.Part using file request-body,file name and part name
            part = MultipartBody.Part.createFormData("image", "image.jpg", requestFile);
        }

        RequestBody name = RequestBody.create(MediaType.parse("text/plain"), supplier.getName());
        RequestBody contact = RequestBody.create(MediaType.parse("text/plain"), supplier.getContact());
        RequestBody email = RequestBody.create(MediaType.parse("text/plain"), supplier.getEmail());
        RequestBody material_supply = RequestBody.create(MediaType.parse("text/plain"), supplier.getMaterial_supply());
        RequestBody project = RequestBody.create(MediaType.parse("text/plain"), supplier.getProject());

        HashMap<String, RequestBody> map = new HashMap<>();
        map.put("name", name);
        map.put("contact", contact);
        map.put("email", email);
        map.put("material_supply", material_supply);
        map.put("project", project);

        ApiClient client = ServiceGenerator.createService(ApiClient.class);

        client.addSupplier(supplier.getProject(),part,map)
                .enqueue(new Callback<Supplier>() {
                    @Override
                    public void onResponse(Call<Supplier> call, Response<Supplier> response) {
                        mView.hideProgressDialog();

                        if(response.isSuccessful()){
                            mView.addSupplierComplete(response.body());
                        }
                    }

                    @Override
                    public void onFailure(Call<Supplier> call, Throwable t) {
                        mView.hideProgressDialog();
                    }
                });
    }

    @Override
    public void updateSupplier(Supplier supplier, byte[] bytes) {
        mView.showProgressDialog();

        MultipartBody.Part part=null;

        if(bytes!=null){
            RequestBody requestFile = RequestBody.create(MediaType.parse("image/jpeg"), bytes);
            part = MultipartBody.Part.createFormData("image", "image.jpg", requestFile);
        }

        RequestBody name = RequestBody.create(MediaType.parse("text/plain"), supplier.getName());
        RequestBody contact = RequestBody.create(MediaType.parse("text/plain"), supplier.getContact());
        RequestBody email = RequestBody.create(MediaType.parse("text/plain"), supplier.getEmail());
        RequestBody material_supply = RequestBody.create(MediaType.parse("text/plain"), supplier.getMaterial_supply());
        RequestBody project = RequestBody.create(MediaType.parse("text/plain"), supplier.getProject());

        HashMap<String, RequestBody> map = new HashMap<>();
        map.put("name", name);
        map.put("contact", contact);
        map.put("email", email);
        map.put("material_supply", material_supply);
        map.put("project", project);


        ApiClient client = ServiceGenerator.createService(ApiClient.class);

        client.updateSupplier(supplier.getProject(),supplier.get_id(),part,map)
                .enqueue(new Callback<Supplier>() {
                    @Override
                    public void onResponse(Call<Supplier> call, Response<Supplier> response) {
                        mView.hideProgressDialog();

                        if(response.isSuccessful()){
                            mView.updateSupplierComplete(response.body());
                        }
                    }

                    @Override
                    public void onFailure(Call<Supplier> call, Throwable t) {
                        mView.hideProgressDialog();
                    }
                });

    }

    @Override
    public void bind() {
        mView.bind();
    }
}
