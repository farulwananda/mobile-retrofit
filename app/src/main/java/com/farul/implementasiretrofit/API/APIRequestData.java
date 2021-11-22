package com.farul.implementasiretrofit.API;

import com.farul.implementasiretrofit.Model.ResponseModel;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface APIRequestData {
    @GET("data-view.php")
    Call<ResponseModel> ardRetrieveData();

    @FormUrlEncoded
    @POST("data-create.php")
    Call<ResponseModel> ardCreateData(
            @Field("NIM") String NIM,
            @Field("Nama") String Nama,
            @Field("Semester") String Semester,
            @Field("Jurusan") String Jurusan,
            @Field("Prodi") String Prodi
    );

    @FormUrlEncoded
    @POST("data-delete.php")
    Call<ResponseModel> ardDeleteData(
            @Field("NIM") String NIM
    );

    @FormUrlEncoded
    @POST("data-get.php")
    Call<ResponseModel> ardGetData (
            @Field("NIM") String NIM
    );

    @FormUrlEncoded
    @POST("data-update.php")
    Call<ResponseModel> ardUpdateData(
            @Field("NIM") String NIM,
            @Field("Nama") String Nama,
            @Field("Semester") String Semester,
            @Field("Jurusan") String Jurusan,
            @Field("Prodi") String Prodi
    );
}
