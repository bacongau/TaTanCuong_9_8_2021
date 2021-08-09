package com.example.tatancuong_9_8_2021.repository;

import com.example.tatancuong_9_8_2021.api.ApiClient;
import com.example.tatancuong_9_8_2021.api.ApiService;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MultipartBody;
import okhttp3.ResponseBody;

public class Repository {
    private final ApiService apiService;

    public Repository() {
        this.apiService = ApiClient.getInstance().getApiService();
    }

    public Single<ResponseBody> uploadImage(MultipartBody.Part fileImage) {
        return apiService.uploadImage(fileImage)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
