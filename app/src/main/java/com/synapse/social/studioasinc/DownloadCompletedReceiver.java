package com.synapse.social.studioasinc;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import androidx.core.content.FileProvider;
import java.io.File;

public class DownloadCompletedReceiver extends BroadcastReceiver {
    private static final String TAG = "DownloadReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        if (DownloadManager.ACTION_DOWNLOAD_COMPLETE.equals(intent.getAction())) {
            long downloadId = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
            if (downloadId == -1) return;

            DownloadManager manager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
            Uri uri = manager.getUriForDownloadedFile(downloadId);

            if (uri != null) {
                installApk(context, uri);
            }
        }
    }

    private void installApk(Context context, Uri apkUri) {
        try {
            Intent installIntent = new Intent(Intent.ACTION_VIEW);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                installIntent.setDataAndType(apkUri, "application/vnd.android.package-archive");
                installIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            } else {
                File file = new File(apkUri.getPath());
                Uri legacyUri = Uri.fromFile(file);
                installIntent.setDataAndType(legacyUri, "application/vnd.android.package-archive");
            }
            installIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(installIntent);
        } catch (Exception e) {
            Log.e(TAG, "Error installing APK", e);
        }
    }
}