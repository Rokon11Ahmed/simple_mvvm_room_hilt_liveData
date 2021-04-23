package com.android.simpleblog.common.network;

import com.android.simpleblog.common.models.ResponseModel;

import retrofit2.Call;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;

public interface BlogApiService {

    @GET("simple-blog-api/db")
    Call<ResponseModel> getBlogFromNetwork();
}
