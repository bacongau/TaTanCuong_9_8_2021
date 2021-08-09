package com.example.tatancuong_9_8_2021.requetpermission;

import android.Manifest;
import android.content.Context;
import android.widget.Toast;

import com.example.tatancuong_9_8_2021.MainActivity;
import com.gun0912.tedpermission.PermissionListener;

import java.util.List;

public class TedPermission {
    public void requestPermission(Context context) {
        if (!com.gun0912.tedpermission.TedPermission.isGranted(context,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA,
                Manifest.permission.INTERNET)) {

            PermissionListener permissionlistener = new PermissionListener() {
                @Override
                public void onPermissionGranted() {
                    Toast.makeText(context, "Permission Granted", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onPermissionDenied(List<String> deniedPermissions) {
                    Toast.makeText(context, "Permission Denied\n" + deniedPermissions.toString(), Toast.LENGTH_SHORT).show();
                }
            };

            com.gun0912.tedpermission.TedPermission.with(context)
                    .setPermissionListener(permissionlistener)
                    .setDeniedMessage("If you reject permission,you can not use this service\n\nPlease turn on permissions at [Setting] > [Permission]")
                    .setPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.CAMERA,
                            Manifest.permission.INTERNET)
                    .check();
        }
    }
}
