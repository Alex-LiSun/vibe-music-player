package com.example.vibemusicplayer;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.net.Uri;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

@SuppressLint("CustomSplashScreen")
public class SplashActivity extends AppCompatActivity {
    private ActivityResultLauncher<String[]> requestPermissionLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // 初始化权限请求启动器
        requestPermissionLauncher = registerForActivityResult(
            new ActivityResultContracts.RequestMultiplePermissions(),
            permissions -> {
                boolean allGranted = true;
                for (Boolean isGranted : permissions.values()) {
                    if (!isGranted) {
                        allGranted = false;
                        break;
                    }
                }
                
                if (allGranted) {
                    // 权限都已授予，延迟1秒后启动主Activity
                    startMainActivityWithDelay();
                } else {
                    // 权限被拒绝，显示提示并退出应用
                    Toast.makeText(this, "需要存储权限才能使用此应用", Toast.LENGTH_LONG).show();
                    finish();
                }
            }
        );

        // 检查并请求权限
        checkAndRequestPermissions();
    }

    private void checkAndRequestPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            // Android 11 及以上，检测 MANAGE_EXTERNAL_STORAGE
            if (android.os.Environment.isExternalStorageManager()) {
                startMainActivityWithDelay();
            } else {
                Toast.makeText(this, "需要所有文件访问权限才能使用此应用", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION);
                intent.setData(Uri.parse("package:" + getPackageName()));
                startActivity(intent);
                // 可在 onResume 里再次检测权限
            }
        } else {
            // Android 10 及以下，继续原有逻辑
            String[] permissions;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                permissions = new String[]{
                        Manifest.permission.READ_MEDIA_AUDIO
                };
            } else {
                permissions = new String[]{
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                };
            }

            boolean allGranted = true;
            for (String permission : permissions) {
                if (ContextCompat.checkSelfPermission(this, permission)
                        != PackageManager.PERMISSION_GRANTED) {
                    allGranted = false;
                    break;
                }
            }

            if (allGranted) {
                startMainActivityWithDelay();
            } else {
                requestPermissionLauncher.launch(permissions);
            }
        }
    }

    private void startMainActivityWithDelay() {
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                // 1秒后启动主Activity
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intent);
                finish(); // 确保启动屏Activity被销毁
            }
        }, 1000); // 1000毫秒
    }
}