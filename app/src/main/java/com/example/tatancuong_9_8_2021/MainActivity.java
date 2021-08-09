package com.example.tatancuong_9_8_2021;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.tatancuong_9_8_2021.getpath.RealPath;
import com.example.tatancuong_9_8_2021.repository.Repository;
import com.example.tatancuong_9_8_2021.requetpermission.TedPermission;

import java.io.File;
import java.io.IOException;

import io.reactivex.disposables.CompositeDisposable;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class MainActivity extends AppCompatActivity {
    Button button_upload, button_select;
    ImageView img;

    Uri mUri;

    CompositeDisposable compositeDisposable;
    Repository repository;

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // anh xa
        button_upload = findViewById(R.id.button_upload);
        button_select = findViewById(R.id.button_select);
        img = findViewById(R.id.imageView_selected_picture);

        compositeDisposable = new CompositeDisposable();
        repository = new Repository();
        progressDialog = new ProgressDialog(this);

        // request permission
        TedPermission tedPermission = new TedPermission();
        tedPermission.requestPermission(MainActivity.this);

        button_select.setOnClickListener(v -> selectImage());
        button_upload.setOnClickListener(v -> uploadImage());

    }

    private void selectImage() {
        // dung intent de chon anh
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        activityResultLauncher.launch(Intent.createChooser(intent, "Select picture"));
    }

    @SuppressLint("CheckResult")
    private void uploadImage() {

        // sau khi chon anh thi xu ly uri cua anh duoc chon
        String strPath = RealPath.getRealPath(this, mUri); // strPath la duong dan cua image
        File file = new File(strPath);
        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("file", file.getName(), requestFile);

        compositeDisposable.add(repository.uploadImage(body)
                .doOnSubscribe(disposable -> {
                    progressDialog.show();
                })
                .doFinally(() -> {
                    progressDialog.hide();
                })
                .subscribe(
                        responseBody -> {
                            Log.d("zzzzzz", responseBody.toString() + "");
                            Toast.makeText(this, "Upload thanh cong", Toast.LENGTH_SHORT).show();
                        },
                        throwable -> {
                            Log.d("zzzzzz", throwable.getLocalizedMessage());
                            Toast.makeText(this, "Khong thanh cong", Toast.LENGTH_SHORT).show();
                        }
                ));

    }

    ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        if (data == null) {
                            return;
                        }

                        Uri uri = data.getData();
                        mUri = uri;

                        try {
                            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                            img.setImageBitmap(bitmap);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
    );

    @Override
    protected void onDestroy() {
        super.onDestroy();
        compositeDisposable.dispose();
    }
}