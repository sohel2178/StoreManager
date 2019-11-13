package com.forbitbd.storeapp.api;

import com.forbitbd.storeapp.models.Consume;
import com.forbitbd.storeapp.models.Receive;
import com.forbitbd.storeapp.models.Supplier;

import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Path;
import retrofit2.http.Url;

public interface ApiClient {

    @GET
    Call<ResponseBody> getImage(@Url String url);

    //Suppliers

    @GET("/api/projects/{project_id}/suppliers")
    Call<List<Supplier>> getProjectSuppliers(@Path("project_id") String projectId);

    @POST("/api/projects/{project_id}/suppliers")
    @Multipart
    Call<Supplier> addSupplier(@Path("project_id") String projectId,
                               @Part MultipartBody.Part file,
                               @PartMap() Map<String, RequestBody> partMap);


    @PUT("/api/projects/{project_id}/suppliers/{supplier_id}")
    @Multipart
    Call<Supplier> updateSupplier(@Path("project_id") String projectId,
                                  @Path("supplier_id") String supplier_id,
                                  @Part MultipartBody.Part file,
                                  @PartMap() Map<String, RequestBody> partMap);




    @GET("/api/projects/{project_id}/receives")
    Call<List<Receive>> getProjectReceives(@Path("project_id") String projectId);


    @POST("/api/projects/{project_id}/receives")
    @Multipart
    Call<Receive> addReceive(@Path("project_id") String projectId,
                              @Part MultipartBody.Part file,
                              @PartMap() Map<String, RequestBody> partMap);

    @PUT("/api/projects/{project_id}/receives/{receive_id}")
    @Multipart
    Call<Receive> updateReceive(@Path("project_id") String projectId,
                                  @Path("receive_id") String receive_id,
                                  @Part MultipartBody.Part file,
                                  @PartMap() Map<String, RequestBody> partMap);

    @DELETE("/api/projects/{project_id}/receives/{receive_id}")
    Call<Void> deleteReceive(@Path("project_id") String projectId,
                             @Path("receive_id") String receive_id);



    @GET("/api/projects/{project_id}/consumes")
    Call<List<Consume>> getProjectConsumes(@Path("project_id") String projectId);

    @POST("/api/projects/{project_id}/consumes")
    @Multipart
    Call<Consume> addConsume(@Path("project_id") String projectId,
                             @Part MultipartBody.Part file,
                             @PartMap() Map<String, RequestBody> partMap);

    @PUT("/api/projects/{project_id}/consumes/{consume_id}")
    @Multipart
    Call<Consume> updateConsume(@Path("project_id") String projectId,
                                @Path("consume_id") String consume_id,
                                @Part MultipartBody.Part file,
                                @PartMap() Map<String, RequestBody> partMap);

    @DELETE("/api/projects/{project_id}/consumes/{consume_id}")
    Call<Void> deleteConsume(@Path("project_id") String projectId,
                             @Path("consume_id") String consume_id);
}
