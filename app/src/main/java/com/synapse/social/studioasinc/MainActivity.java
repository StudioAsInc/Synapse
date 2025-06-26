package com.synapse.social.studioasinc;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DownloadManager;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.io.File;
import java.util.HashMap;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private final FirebaseDatabase _firebase = FirebaseDatabase.getInstance();
    private String current_version = "1.0"; // Default version

    private FirebaseAuth auth;
    private DatabaseReference ver_ref;
    private ValueEventListener _ver_value_listener;

    private SharedPreferences language;

    @Override
    protected void onCreate(Bundle _savedInstanceState) {
        super.onCreate(_savedInstanceState);
        // Apply language settings before setting the content view
        _applyLanguage();
        setContentView(R.layout.main);
        initialize(_savedInstanceState);
        FirebaseApp.initializeApp(this);

        // Request permissions if not already granted
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q && ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1000);
        } else {
            initializeLogic();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1000) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                initializeLogic();
            } else {
                Toast.makeText(this, "Storage permission is required to download updates.", Toast.LENGTH_LONG).show();
                finish(); // Close the app if permission is denied
            }
        }
    }

    private void initialize(Bundle _savedInstanceState) {
        ImageView app_logo = findViewById(R.id.app_logo);

        auth = FirebaseAuth.getInstance();
        ver_ref = _firebase.getReference("inapp/version/app");
        language = getSharedPreferences("language", Activity.MODE_PRIVATE);

        // Developer debug feature
        app_logo.setOnLongClickListener(_view -> {
            if (auth.getCurrentUser() != null && "mashikahamed0@gmail.com".equals(auth.getCurrentUser().getEmail())) {
                _setLanguage("bn");
                language.edit().putString("language", "bn").apply();
                Intent i = new Intent(getApplicationContext(), HomeActivity.class);
                startActivity(i);
                finish();
            }
            return true;
        });

        _ver_value_listener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
                    HashMap<String, Object> _childValue = snapshot.getValue(_ind);

                    if (_childValue != null) {
                        try {
                            String onlineVersionStr = String.valueOf(_childValue.get("version"));
                            double onlineVersion = Double.parseDouble(onlineVersionStr);
                            double localVersion = Double.parseDouble(current_version);

                            if (localVersion < onlineVersion) {
                                _showUpdateBottomSheet(_childValue);
                            } else {
                                _proceedToNextActivity();
                            }
                        } catch (Exception e) {
                            Log.e("VersionCheckError", "Could not parse version numbers.", e);
                            _proceedToNextActivity(); // Proceed if there's a parsing error
                        }
                    } else {
                        _proceedToNextActivity(); // Proceed if data is malformed
                    }
                } else {
                    _proceedToNextActivity(); // Proceed if "app" node doesn't exist
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("FirebaseError", "Database error: " + error.getMessage());
                new AlertDialog.Builder(MainActivity.this)
                        .setTitle("Connection Error")
                        .setMessage("Could not check for updates. Please check your internet connection and try again.")
                        .setPositiveButton("Exit", (dialog, which) -> finish())
                        .setCancelable(false)
                        .show();
            }
        };
    }

    private void initializeLogic() {
        // Make status and navigation bars transparent
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow();
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        // Get current app version
        try {
            PackageInfo pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            current_version = pInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            Log.e("VersionError", "Could not get package info", e);
            current_version = "1.0"; // Fallback version
        }

        // Attach the listener to check for updates
        ver_ref.addListenerForSingleValueEvent(_ver_value_listener);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Listener is ValueEventListener with addListenerForSingleValueEvent, no need to remove.
    }

    @Override
    public void onBackPressed() {
        finishAffinity();
    }

    private void _showUpdateBottomSheet(final HashMap<String, Object> updateData) {
        // Use the custom transparent theme for the dialog
        final BottomSheetDialog bs = new BottomSheetDialog(this, R.style.AppTheme_TransparentBottomSheetDialog);
        View bsView = getLayoutInflater().inflate(R.layout.update_sheet, null);
        bs.setContentView(bsView);

        // All views from the layout
        final LinearLayout mainLayout = bsView.findViewById(R.id.FatherLayout);
        final TextView appName = bsView.findViewById(R.id.app_name);
        final TextView versionTxt = bsView.findViewById(R.id.version_txt);
        final TextView updateSize = bsView.findViewById(R.id.update_size);
        final LinearLayout updateBtn = bsView.findViewById(R.id.update_btn);
        final TextView updatedOnTxt = bsView.findViewById(R.id.update_push_date);
        final ImageView appIcon = bsView.findViewById(R.id.app_icon);
        final TextView whatsNewSubtitle = bsView.findViewById(R.id.whats_new_subtitle);
        final ImageView crossIc = bsView.findViewById(R.id.cross_ic);

        // Set rounded corners for the main layout
        GradientDrawable mainDrawable = new GradientDrawable();
        mainDrawable.setColor(Color.WHITE);
        mainDrawable.setCornerRadii(new float[]{48, 48, 48, 48, 0, 0, 0, 0});
        mainLayout.setBackground(mainDrawable);
        mainLayout.setElevation(8);

        // Populate data
        appName.setText(String.valueOf(updateData.get("app name")));
        versionTxt.setText("Version ".concat(String.valueOf(updateData.get("version"))));
        updateSize.setText(String.valueOf(updateData.get("size")));
        updatedOnTxt.setText(String.valueOf(updateData.get("release date")));
        Glide.with(this).load(String.valueOf(updateData.get("icon"))).into(appIcon);

        if (updateData.containsKey("changes")) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                whatsNewSubtitle.setText(Html.fromHtml(String.valueOf(updateData.get("changes")), Html.FROM_HTML_MODE_COMPACT));
            } else {
                whatsNewSubtitle.setText(Html.fromHtml(String.valueOf(updateData.get("changes"))));
            }
        }

        // Configure buttons
        String downloadUrl = String.valueOf(updateData.get("link"));
        updateBtn.setOnClickListener(v -> {
            _startDownload(downloadUrl);
            Toast.makeText(this, "Download started...", Toast.LENGTH_SHORT).show();
            bs.dismiss();
        });

        // Handle skippable updates
        boolean isSkippable = "true".equals(String.valueOf(updateData.get("skippable")));
        bs.setCancelable(isSkippable);
        crossIc.setVisibility(isSkippable ? View.VISIBLE : View.GONE);
        if (isSkippable) {
            crossIc.setOnClickListener(v -> {
                bs.dismiss();
                _proceedToNextActivity();
            });
        }
        
        bs.show();
    }

    private void _proceedToNextActivity() {
        if (auth.getCurrentUser() == null) {
            Log.e("AuthError", "User not authenticated, cannot proceed.");
            Toast.makeText(this, "Authentication failed. Please restart.", Toast.LENGTH_SHORT).show();
            return;
        }

        DatabaseReference checkUser = _firebase.getReference("skyline/users").child(auth.getCurrentUser().getUid());
        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Intent intent = new Intent();
                if (dataSnapshot.exists()) {
                    if ("false".equals(dataSnapshot.child("banned").getValue(String.class))) {
                        intent.setClass(getApplicationContext(), HomeActivity.class);
                    } else {
                        _showBannedUserDialog();
                        return; // Stop further execution
                    }
                } else {
                    intent.setClass(getApplicationContext(), CompleteProfileActivity.class);
                }
                startActivity(intent);
                finish();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("FirebaseError", "Error checking user status: " + databaseError.getMessage());
                Toast.makeText(MainActivity.this, "Could not verify user status.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void _showBannedUserDialog() {
        new AlertDialog.Builder(this)
                .setView(getLayoutInflater().inflate(R.layout.banned_dialog, null))
                .setCancelable(false)
                .create()
                .show();
    }

    private void _startDownload(final String url) {
        // MODIFIED: Replaced URLUtil with Uri.getLastPathSegment() for robustness.
        String fileName = Uri.parse(url).getLastPathSegment();
        if (fileName == null || fileName.isEmpty()) {
            fileName = "synapse-update.apk"; // Fallback filename
        }

        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url))
                .setTitle(fileName)
                .setDescription("Downloading Update...")
                .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, fileName)
                .setAllowedOverMetered(true)
                .setAllowedOverRoaming(true);

        DownloadManager manager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
        manager.enqueue(request);
    }

    public void _installApk(final String apkFileName) {
        // MODIFIED: Use modern, non-deprecated method for accessing public Downloads directory.
        File downloadsDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        File file = new File(downloadsDir, apkFileName);

        if (file.exists()) {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            Uri uri;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                uri = FileProvider.getUriForFile(this, getApplicationContext().getPackageName() + ".provider", file);
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            } else {
                uri = Uri.fromFile(file);
            }
            intent.setDataAndType(uri, "application/vnd.android.package-archive");
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            try {
                startActivity(intent);
            } catch (ActivityNotFoundException e) {
                Log.e("InstallError", "Error opening APK file.", e);
                Toast.makeText(this, "Could not open installer.", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Update file not found.", Toast.LENGTH_LONG).show();
        }
    }

    private void _applyLanguage() {
        language = getSharedPreferences("language", Activity.MODE_PRIVATE);
        String langCode = language.getString("language", "en"); // Default to English
        _setLanguage(langCode);
    }

    public void _setLanguage(final String languageCode) {
        Locale locale = new Locale(languageCode);
        Locale.setDefault(locale);
        Resources resources = getResources();
        Configuration config = new Configuration(resources.getConfiguration());
        config.setLocale(locale);
        resources.updateConfiguration(config, resources.getDisplayMetrics());
    }
}