package com.iptv.iptv2.utils;

import android.Manifest;
import android.app.AlertDialog;
import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import com.iptv.iptv2.activities.MainActivity;

public class UpdateChecker {

    private static final String VERSION_URL = "https://dylancfarrell.com/version.json";
    private static final int PERMISSION_REQUEST_CODE = 1001;
    private Context context;
    private long downloadId;
    private String apkUrl;
    private int latestVersionCode;

    public UpdateChecker(Context context) {
        this.context = context;
    }

    public void checkForUpdate() {
        new Thread(() -> {
            try {
                URL url = new URL(VERSION_URL);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));

                StringBuilder result = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    result.append(line);
                }

                JSONObject jsonObject = new JSONObject(result.toString());
                latestVersionCode = jsonObject.getInt("versionCode");
                apkUrl = jsonObject.getString("apkUrl");

                int currentVersionCode = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionCode;

                Log.i("UpdateChecker", "Current version code: " + currentVersionCode);
                Log.i("UpdateChecker", "Latest version code: " + latestVersionCode);
                Log.i("UpdateChecker", "Version JSON: " + result.toString());

                if (latestVersionCode > currentVersionCode) {
                    showUpdateDialog();
                }

                urlConnection.disconnect();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    private void showUpdateDialog() {
        new Handler(Looper.getMainLooper()).post(() -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("Update Available")
                    .setMessage("A new version of the app is available. Please update to the latest version.")
                    .setPositiveButton("Update", (dialog, which) -> {
                        if (ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                            requestStoragePermission();
                        } else {
                            if (context.getPackageManager().canRequestPackageInstalls()) {
                                startDownload(apkUrl);
                            } else {
                                requestInstallPermission();
                            }
                        }
                    })
                    .setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss())
                    .setCancelable(false)
                    .show();
        });
    }

    private void requestStoragePermission() {
        new Handler(Looper.getMainLooper()).post(() -> {
            ActivityCompat.requestPermissions((MainActivity) context, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
        });
    }

    private void requestInstallPermission() {
        new Handler(Looper.getMainLooper()).post(() -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("Permission Required")
                    .setMessage("To install the update, please allow app installations from unknown sources.")
                    .setPositiveButton("Allow", (dialog, which) -> {
                        Intent intent = new Intent(android.provider.Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES,
                                Uri.parse("package:" + context.getPackageName()));
                        context.startActivity(intent);
                    })
                    .setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss())
                    .setCancelable(false)
                    .show();
        });
    }

    private void startDownload(String apkUrl) {
        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "update.apk");
        if (file.exists()) {
            file.delete(); // Delete old APK if it exists
            Log.i("UpdateChecker", "Old APK deleted.");
        }

        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(apkUrl));
        request.setTitle("Downloading update...");
        request.setDescription("Please wait while the update is downloaded.");
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "update.apk");
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);

        DownloadManager downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
        downloadId = downloadManager.enqueue(request);

        context.registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context ctxt, Intent intent) {
                long id = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
                if (id == downloadId) {
                    Log.i("UpdateChecker", "Download completed, starting installation");
                    installApk();
                }
            }
        }, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
    }

    private void installApk() {
        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "update.apk");
        Log.i("UpdateChecker", "APK file path: " + file.getAbsolutePath());
        Uri fileUri = FileProvider.getUriForFile(context, context.getPackageName() + ".provider", file);
        Intent intent = new Intent(Intent.ACTION_INSTALL_PACKAGE);
        intent.setDataAndType(fileUri, "application/vnd.android.package-archive");
        intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        context.startActivity(intent);
    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (context.getPackageManager().canRequestPackageInstalls()) {
                    startDownload(apkUrl);
                } else {
                    requestInstallPermission();
                }
            } else {
                // Permission denied, handle as needed
            }
        }
    }
}
