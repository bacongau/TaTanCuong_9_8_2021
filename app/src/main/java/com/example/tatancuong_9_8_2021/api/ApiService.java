package com.example.tatancuong_9_8_2021.api;

import io.reactivex.Single;
import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface ApiService {

    @Multipart
    @POST("api/products/upload/image")
    Single<ResponseBody> uploadImage(@Part MultipartBody.Part fileImage);

}
