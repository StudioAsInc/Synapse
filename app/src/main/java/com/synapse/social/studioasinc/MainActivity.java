//Simplified version only Splash & Go
package com.synapse.social.studioasinc;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private static final int PERMISSION_REQUEST_CODE = 1000;

    private FirebaseAuth auth;
    private SharedPreferences languagePref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        _applyLanguage();
        setContentView(R.layout.main);
        initialize();
        startAppFlow();
    }

    /**
     * Initializes all necessary components and views.
     */
    private void initialize() {
        FirebaseApp.initializeApp(this);
        auth = FirebaseAuth.getInstance();
        languagePref = getSharedPreferences("language", Activity.MODE_PRIVATE);

        // Make status and navigation bars transparent
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow();
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
    }

    /**
     * Starts the main application flow.
     */
    private void startAppFlow() {
        // 1. Check Internet Connection
        if (!_isNetworkAvailable()) {
            _showNoInternetDialog();
            return; // Stop the flow if there's no internet
        }

        // Request storage permission if needed, otherwise continue
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q && ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
        } else {
            // 2. Check for updates
            _checkForUpdates();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                _checkForUpdates(); // Permission granted, continue the flow
            } else {
                Toast.makeText(this, "Storage permission is required to download updates.", Toast.LENGTH_LONG).show();
                finish(); // Close the app if permission is denied
            }
        }
    }

    /**
     * Checks Firebase for a new version of the app.
     */
    private void _checkForUpdates() {
        DatabaseReference versionRef = FirebaseDatabase.getInstance().getReference("inapp/version/app");
        versionRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (!snapshot.exists()) {
                    Log.d(TAG, "No update info found in Firebase. Proceeding...");
                    _handleAuthentication(); // Plan B: Proceed without update check
                    return;
                }

                GenericTypeIndicator<HashMap<String, Object>> t = new GenericTypeIndicator<HashMap<String, Object>>() {};
                HashMap<String, Object> versionData = snapshot.getValue(t);

                if (versionData == null || versionData.get("version") == null) {
                    Log.w(TAG, "Update data is malformed. Proceeding...");
                    _handleAuthentication(); // Plan B: Proceed
                    return;
                }

                try {
                    String onlineVersionStr = Objects.requireNonNull(versionData.get("version")).toString();
                    String localVersionStr = getAppVersionName();

                    if (_isUpdateRequired(localVersionStr, onlineVersionStr)) {
                        _showUpdateBottomSheet(versionData);
                    } else {
                        _handleAuthentication();
                    }
                } catch (Exception e) {
                    Log.e(TAG, "Error comparing versions", e);
                    _handleAuthentication(); // Plan B: Proceed if versions are not parsable
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e(TAG, "Firebase update check failed: " + error.getMessage());
                Toast.makeText(MainActivity.this, "Could not check for updates.", Toast.LENGTH_SHORT).show();
                _handleAuthentication(); // Plan B: Proceed even if DB call fails
            }
        });
    }

    /**
     * Checks if the user is authenticated and routes them accordingly.
     */
    private void _handleAuthentication() {
        if (auth.getCurrentUser() == null) {
            _navigateToOnboardActivity();
        } else {
            _checkUserProfile(auth.getCurrentUser().getUid());
        }
    }

    /**
     * Checks the authenticated user's profile status (banned, new, existing).
     */
    private void _checkUserProfile(@NonNull String uid) {
        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("skyline/users").child(uid);
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    // Check if banned
                    if ("true".equals(String.valueOf(snapshot.child("banned").getValue()))) {
                        _showBannedUserDialog();
                    } else {
                        _navigateToHomeActivity();
                    }
                } else {
                    // User is authenticated but has no profile data
                    _navigateToCompleteProfileActivity();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e(TAG, "Failed to check user profile: " + error.getMessage());
                Toast.makeText(MainActivity.this, "Could not verify profile. Please log in again.", Toast.LENGTH_LONG).show();
                _navigateToOnboardActivity(); // Plan B: Send back to login
            }
        });
    }

    /**
     * Displays a custom bottom sheet with update information.
     */
    private void _showUpdateBottomSheet(final HashMap<String, Object> updateData) {
        final BottomSheetDialog bs = new BottomSheetDialog(this, R.style.AppTheme_TransparentBottomSheetDialog);
        View bsView = LayoutInflater.from(this).inflate(R.layout.update_sheet, null);
        bs.setContentView(bsView);

        TextView appName = bsView.findViewById(R.id.app_name);
        TextView versionTxt = bsView.findViewById(R.id.version_txt);
        // ... find other views like updateSize, updateBtn, etc.

        // Use a helper method to create the rounded background
        bsView.findViewById(R.id.FatherLayout).setBackground(createTopRoundedDrawable(Color.WHITE, 48));

        // Populate data safely
        appName.setText(String.valueOf(updateData.getOrDefault("app name", "Synapse")));
        versionTxt.setText("Version ".concat(String.valueOf(updateData.getOrDefault("version", ""))));
        // ... populate other views

        // Update Button
        String downloadUrl = String.valueOf(updateData.get("link"));
        bsView.findViewById(R.id.update_btn).setOnClickListener(v -> {
            _startDownload(downloadUrl);
            bs.dismiss();
        });

        // Handle skippable updates
        boolean isSkippable = "true".equals(String.valueOf(updateData.get("skippable")));
        bs.setCancelable(isSkippable);
        ImageView crossIc = bsView.findViewById(R.id.cross_ic);
        crossIc.setVisibility(isSkippable ? View.VISIBLE : View.GONE);
        if (isSkippable) {
            crossIc.setOnClickListener(v -> {
                bs.dismiss();
                _handleAuthentication(); // Proceed to next step if update is skipped
            });
        }
        
        bs.show();
    }

    // --- HELPER & UTILITY METHODS ---

    private boolean _isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    private String getAppVersionName() {
        try {
            PackageInfo pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            // Remove suffixes like "-debug" to compare only the core version string
            return pInfo.versionName.split("-")[0];
        } catch (PackageManager.NameNotFoundException e) {
            Log.e(TAG, "Could not get package version name.", e);
            return "1.0.0"; // Fallback version
        }
    }

    private boolean _isUpdateRequired(String localVersion, String onlineVersion) {
        // Robust version comparison
        String[] localParts = localVersion.split("\\.");
        String[] onlineParts = onlineVersion.split("\\.");
        int length = Math.max(localParts.length, onlineParts.length);
        for (int i = 0; i < length; i++) {
            int local = i < localParts.length ? Integer.parseInt(localParts[i]) : 0;
            int online = i < onlineParts.length ? Integer.parseInt(onlineParts[i]) : 0;
            if (online > local) {
                return true;
            }
            if (online < local) {
                return false;
            }
        }
        return false;
    }

    private void _startDownload(final String url) {
        if (url == null || url.trim().isEmpty()) {
            Toast.makeText(this, "Invalid download link.", Toast.LENGTH_SHORT).show();
            return;
        }

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
        if (manager != null) {
            manager.enqueue(request);
            Toast.makeText(this, "Download started...", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Download service not available.", Toast.LENGTH_SHORT).show();
        }
    }

    private void _showNoInternetDialog() {
        new AlertDialog.Builder(this)
                .setTitle("No Internet Connection")
                .setMessage("Please connect to the internet and try again.")
                .setPositiveButton("Exit", (dialog, which) -> finish())
                .setCancelable(false)
                .show();
    }

    private void _showBannedUserDialog() {
        // ... implementation for banned dialog
        new AlertDialog.Builder(this)
                .setTitle("Account Suspended")
                .setMessage("Your account has been suspended.")
                .setPositiveButton("OK", (dialog, which) -> finishAffinity())
                .setCancelable(false)
                .show();
    }
    
    // --- NAVIGATION METHODS ---
    
    private void _navigateToHomeActivity() {
        startActivity(new Intent(this, HomeActivity.class));
        finish();
    }

    private void _navigateToCompleteProfileActivity() {
        startActivity(new Intent(this, CompleteProfileActivity.class));
        finish();
    }

    private void _navigateToOnboardActivity() {
        startActivity(new Intent(this, OnboardActivity.class));
        finish();
    }

    // --- LANGUAGE METHODS ---

    private void _applyLanguage() {
        languagePref = getSharedPreferences("language", Activity.MODE_PRIVATE);
        String langCode = languagePref.getString("language", "en"); // Default to English
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

    // --- DRAWABLE HELPER ---

    private GradientDrawable createTopRoundedDrawable(int color, float radius) {
        GradientDrawable drawable = new GradientDrawable();
        drawable.setColor(color);
        drawable.setCornerRadii(new float[]{radius, radius, radius, radius, 0, 0, 0, 0});
        return drawable;
    }
}

/*
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
import android.graphics.drawable.GradientDrawable;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.text.Html;
import android.util.Log;
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

    private static final String TAG = "MainActivity";
    private static final int PERMISSION_REQUEST_CODE = 1000;

    private final FirebaseDatabase _firebase = FirebaseDatabase.getInstance();
    private FirebaseAuth auth;
    private SharedPreferences languagePref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        _applyLanguage();
        setContentView(R.layout.main);
        initialize();
        FirebaseApp.initializeApp(this);

        // Start the main application flow
        startAppFlow();
    }

    /**
     * Initializes essential components and UI listeners.
     */
    private void initialize() {
        ImageView app_logo = findViewById(R.id.app_logo);
        auth = FirebaseAuth.getInstance();
        languagePref = getSharedPreferences("language", Activity.MODE_PRIVATE);

        // Developer debug feature
        app_logo.setOnLongClickListener(_view -> {
            if (auth.getCurrentUser() != null && "mashikahamed0@gmail.com".equals(auth.getCurrentUser().getEmail())) {
                _setLanguage("bn");
                languagePref.edit().putString("language", "bn").apply();
                navigateToHomeActivity();
            }
            return true;
        });

        // Make status and navigation bars transparent
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow();
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
    }

    /**
     * The main logic sequence for the app startup.
     */
    private void startAppFlow() {
        if (isInternetAvailable()) {
            checkForUpdates();
        } else {
            Toast.makeText(this, "No Internet Connection", Toast.LENGTH_LONG).show();
            // As a plan B, still try to proceed offline.
            handleAuthentication();
        }
    }

    /**
     * Checks for a new version on Firebase. If an update is found, it shows the bottom sheet.
     * If not, or if an error occurs, it proceeds to the authentication check.
     */
    private void checkForUpdates() {
        DatabaseReference versionRef = _firebase.getReference("inapp/version/app");
        versionRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (!snapshot.exists()) {
                    Log.w(TAG, "Firebase version node does not exist. Skipping update check.");
                    handleAuthentication(); // Plan B: Proceed without update check
                    return;
                }

                GenericTypeIndicator<HashMap<String, Object>> t = new GenericTypeIndicator<HashMap<String, Object>>() {};
                HashMap<String, Object> versionData = snapshot.getValue(t);

                if (versionData == null || versionData.get("version") == null) {
                    Log.w(TAG, "Version data is malformed. Skipping update check.");
                    handleAuthentication(); // Plan B: Proceed
                    return;
                }

                try {
                    String onlineVersionStr = String.valueOf(versionData.get("version"));
                    String localVersionStr = getAppVersionName();

                    // Compare versions properly (e.g., "1.0.1" vs "1.0.0")
                    if (isUpdateRequired(localVersionStr, onlineVersionStr)) {
                        _showUpdateBottomSheet(versionData);
                    } else {
                        handleAuthentication();
                    }
                } catch (NumberFormatException e) {
                    Log.e(TAG, "Could not parse version numbers.", e);
                    handleAuthentication(); // Plan B: Proceed if versions are not numbers
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e(TAG, "Firebase update check failed: " + error.getMessage());
                Toast.makeText(MainActivity.this, "Could not check for updates.", Toast.LENGTH_SHORT).show();
                handleAuthentication(); // Plan B: Proceed even if DB call fails
            }
        });
    }
    
    /**
     * Checks authentication status and navigates to the appropriate screen.
     */
    private void handleAuthentication() {
        if (auth.getCurrentUser() != null) {
            checkUserProfileAndNavigate();
        } else {
            navigateToOnboardActivity();
        }
    }

    /**
     * Checks if the user has a profile in the database.
     * Navigates to Home if profile exists, or CompleteProfile if not.
     */
    private void checkUserProfileAndNavigate() {
        String uid = auth.getCurrentUser().getUid();
        DatabaseReference userRef = _firebase.getReference("skyline/users").child(uid);
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    // Check for ban status
                    if ("true".equals(snapshot.child("banned").getValue(String.class))) {
                        _showBannedUserDialog();
                    } else {
                        navigateToHomeActivity();
                    }
                } else {
                    navigateToCompleteProfileActivity();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e(TAG, "Failed to check user profile: " + error.getMessage());
                Toast.makeText(MainActivity.this, "Could not verify profile. Please log in again.", Toast.LENGTH_LONG).show();
                navigateToOnboardActivity(); // Plan B: Send back to login
            }
        });
    }

    /**
     * Shows the update bottom sheet dialog.
     */
    private void _showUpdateBottomSheet(final HashMap<String, Object> updateData) {
        final BottomSheetDialog bs = new BottomSheetDialog(this, R.style.AppTheme_TransparentBottomSheetDialog);
        View bsView = getLayoutInflater().inflate(R.layout.update_sheet, null);
        bs.setContentView(bsView);

        // ... (The rest of your bottom sheet view finding and population code remains the same)
        final LinearLayout mainLayout = bsView.findViewById(R.id.FatherLayout);
        final TextView appName = bsView.findViewById(R.id.app_name);
        final TextView versionTxt = bsView.findViewById(R.id.version_txt);
        final TextView updateSize = bsView.findViewById(R.id.update_size);
        final LinearLayout updateBtn = bsView.findViewById(R.id.update_btn);
        final TextView updatedOnTxt = bsView.findViewById(R.id.update_push_date);
        final ImageView appIcon = bsView.findViewById(R.id.app_icon);
        final TextView whatsNewSubtitle = bsView.findViewById(R.id.whats_new_subtitle);
        final ImageView crossIc = bsView.findViewById(R.id.cross_ic);

        mainLayout.setBackground(createTopRoundedDrawable(Color.WHITE, 48));

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

        String downloadUrl = String.valueOf(updateData.get("link"));
        updateBtn.setOnClickListener(v -> {
            startDownloadWithPermissionCheck(downloadUrl);
            bs.dismiss();
        });

        boolean isSkippable = "true".equals(String.valueOf(updateData.get("skippable")));
        bs.setCancelable(isSkippable);
        crossIc.setVisibility(isSkippable ? View.VISIBLE : View.GONE);
        if (isSkippable) {
            crossIc.setOnClickListener(v -> {
                bs.dismiss();
                handleAuthentication(); // Proceed to next step if update is skipped
            });
        }
        
        bs.show();
    }

    /**
     * Starts the APK download after ensuring permissions are granted.
     */
    private void startDownloadWithPermissionCheck(String url) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q && ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
            Toast.makeText(this, "Storage permission needed to save the update.", Toast.LENGTH_SHORT).show();
        } else {
            _startDownload(url);
        }
    }

    private void _startDownload(final String url) {
        String fileName = Uri.parse(url).getLastPathSegment();
        if (fileName == null || fileName.isEmpty()) {
            fileName = "synapse-update.apk"; // Fallback filename
        }

        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url))
                .setTitle(fileName)
                .setDescription("Downloading Update...")
                .setMimeType("application/vnd.android.package-archive")
                .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, fileName)
                .setAllowedOverMetered(true);

        DownloadManager manager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
        if (manager != null) {
            manager.enqueue(request);
            Toast.makeText(this, "Download started...", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Download service is not available.", Toast.LENGTH_SHORT).show();
        }
    }

    // --- Utility and Helper Methods ---

    private GradientDrawable createTopRoundedDrawable(int color, float radius) {
        GradientDrawable drawable = new GradientDrawable();
        drawable.setColor(color);
        drawable.setCornerRadii(new float[]{radius, radius, radius, radius, 0, 0, 0, 0});
        return drawable;
    }

    private boolean isInternetAvailable() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm == null) return false;
        
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Network network = cm.getActiveNetwork();
            if (network == null) return false;
            NetworkCapabilities capabilities = cm.getNetworkCapabilities(network);
            return capabilities != null && (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) || capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) || capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET));
        } else {
            return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
        }
    }

    private String getAppVersionName() {
        try {
            PackageInfo pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            return pInfo.versionName.split("-")[0]; // "1.0.0-debug" -> "1.0.0"
        } catch (PackageManager.NameNotFoundException e) {
            Log.e(TAG, "Could not get package info", e);
            return "0.0.0"; // Fallback version
        }
    }

    private boolean isUpdateRequired(String localVersion, String onlineVersion) {
        // Simple version comparison assuming "major.minor.patch" format
        String[] localParts = localVersion.split("\\.");
        String[] onlineParts = onlineVersion.split("\\.");
        int length = Math.max(localParts.length, onlineParts.length);
        for (int i = 0; i < length; i++) {
            int local = i < localParts.length ? Integer.parseInt(localParts[i]) : 0;
            int online = i < onlineParts.length ? Integer.parseInt(onlineParts[i]) : 0;
            if (online > local) return true;
            if (local > online) return false;
        }
        return false;
    }
    
    private void _showBannedUserDialog() {
        // Implementation for showing a banned user dialog
    }
    
    // --- Navigation Methods ---
    
    private void navigateToHomeActivity() {
        startActivity(new Intent(this, HomeActivity.class));
        finish();
    }
    
    private void navigateToCompleteProfileActivity() {
        startActivity(new Intent(this, CompleteProfileActivity.class));
        finish();
    }
    
    private void navigateToOnboardActivity() {
        startActivity(new Intent(this, OnboardActivity.class));
        finish();
    }
    
    // --- Language Methods ---

    private void _applyLanguage() {
        languagePref = getSharedPreferences("language", Activity.MODE_PRIVATE);
        String langCode = languagePref.getString("language", "en");
        _setLanguage(langCode);
    }

    public void _setLanguage(final String languageCode) {
        Locale locale = new Locale(languageCode);
        Locale.setDefault(locale);
        Resources resources = getResources();
        Configuration config = new Configuration(resources.getConfiguration());
        config.setLocale(locale);
        createConfigurationContext(config);
        resources.updateConfiguration(config, resources.getDisplayMetrics());
    }
}
*/