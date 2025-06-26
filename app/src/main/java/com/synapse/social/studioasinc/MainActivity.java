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
import android.util.URLUtil;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;
import com.synapse.social.studioasinc.CenterCropLinearLayoutNoEffect;

import java.io.File;
import java.util.HashMap;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

	private final FirebaseDatabase _firebase = FirebaseDatabase.getInstance();

	private String current_version = "";

	private CenterCropLinearLayoutNoEffect body;
	private ImageView app_logo;

	private FirebaseAuth auth;
	private DatabaseReference ver;
	private ChildEventListener _ver_child_listener;

	private SharedPreferences theme;
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
		if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
			ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1000);
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
		body = findViewById(R.id.body);
		app_logo = findViewById(R.id.app_logo);

		auth = FirebaseAuth.getInstance();
		ver = _firebase.getReference("inapp/version");
		theme = getSharedPreferences("theme", Activity.MODE_PRIVATE);
		language = getSharedPreferences("language", Activity.MODE_PRIVATE);

		// Developer debug feature: Long-click logo to change language to Bengali and proceed
		app_logo.setOnLongClickListener(new View.OnLongClickListener() {
			@Override
			public boolean onLongClick(View _view) {
				if ("mashikahamed0@gmail.com".equals(auth.getCurrentUser().getEmail())) {
					_setLanguage("bn");
					language.edit().putString("language", "bn").apply();
					Intent i = new Intent(getApplicationContext(), HomeActivity.class);
					startActivity(i);
					finish();
				}
				return true;
			}
		});

        _ver_child_listener = new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, String previousChildName) {
                GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
                final String _childKey = snapshot.getKey();
                final HashMap<String, Object> _childValue = snapshot.getValue(_ind);

                if ("app".equals(_childKey) && _childValue != null) {
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
                    _proceedToNextActivity();
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, String previousChildName) {
                // You may want to handle remote config changes while the app is running
            }
            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {}
            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, String previousChildName) {}

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Show an error if the app can't connect to the database to check for version
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


		// Get current app version from package info
        try {
            PackageInfo pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            current_version = pInfo.versionName; // Use versionName for semantic versioning (e.g., "1.0.1")
        } catch (PackageManager.NameNotFoundException e) {
            current_version = "1.0"; // Fallback version
        }

		// Attach the listener to check for updates
		ver.addChildEventListener(_ver_child_listener);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		// Important: Remove the listener to prevent memory leaks
		if (ver != null && _ver_child_listener != null) {
			ver.removeEventListener(_ver_child_listener);
		}
	}

	@Override
	public void onBackPressed() {
		finishAffinity();
	}

    /**
     * Shows a bottom sheet dialog with update information.
     * @param updateData A map containing details about the update.
     */
	
private void _showUpdateBottomSheet(final HashMap<String, Object> updateData) {
    // MODIFICATION 1: Pass the custom transparent theme to the dialog's constructor.
    // This uses the style you added to styles.xml.
    final BottomSheetDialog bs = new BottomSheetDialog(MainActivity.this, R.style.AppTheme_TransparentBottomSheetDialog);

    View bsView = getLayoutInflater().inflate(R.layout.update_sheet, null);
    bs.setContentView(bsView);
    
    // MODIFICATION 2: Delete this line. The theme now handles the transparent background.
    // This was the line causing the compilation error.
    // bs.getWindow().findViewById(R.id.design_bottom_sheet).setBackgroundResource(android.R.color.transparent);

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

    // Populate the dialog with data from Firebase
    if (updateData.containsKey("app name")) appName.setText(String.valueOf(updateData.get("app name")));
    if (updateData.containsKey("version")) versionTxt.setText("Version ".concat(String.valueOf(updateData.get("version"))));
    if (updateData.containsKey("size")) updateSize.setText(String.valueOf(updateData.get("size")));
    if (updateData.containsKey("release date")) updatedOnTxt.setText(String.valueOf(updateData.get("release date")));
    if (updateData.containsKey("changes")) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            whatsNewSubtitle.setText(Html.fromHtml(String.valueOf(updateData.get("changes")), Html.FROM_HTML_MODE_COMPACT));
        } else {
            whatsNewSubtitle.setText(Html.fromHtml(String.valueOf(updateData.get("changes"))));
        }
    }
    if (updateData.containsKey("icon")) {
        Glide.with(getApplicationContext()).load(Uri.parse(String.valueOf(updateData.get("icon")))).into(appIcon);
    }

    // Configure buttons
    String downloadUrl = updateData.containsKey("link") ? String.valueOf(updateData.get("link")) : null;
    if (downloadUrl != null) {
        updateBtn.setOnClickListener(v -> {
            _startDownload(downloadUrl, "/Download/");
            Toast.makeText(getApplicationContext(), "Download started...", Toast.LENGTH_SHORT).show();
            bs.dismiss();
        });
    }

    // Handle skippable updates
    boolean isSkippable = "true".equals(String.valueOf(updateData.get("skippable")));
    bs.setCancelable(isSkippable);
    if (isSkippable) {
        crossIc.setVisibility(View.VISIBLE);
        crossIc.setOnClickListener(v -> {
            bs.dismiss();
            _proceedToNextActivity();
        });
    } else {
        crossIc.setVisibility(View.GONE);
    }

    bs.show();
}

	/**
	 * Determines the next screen based on user authentication status.
	 */
	private void _proceedToNextActivity() {
		DatabaseReference checkUser = FirebaseDatabase.getInstance().getReference("skyline/users").child(auth.getCurrentUser().getUid());
		checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
			@Override
			public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
				Intent intent = new Intent();
				if (dataSnapshot.exists()) {
					if ("false".equals(dataSnapshot.child("banned").getValue(String.class))) {
						intent.setClass(getApplicationContext(), HomeActivity.class);
						startActivity(intent);
						finish();
					} else {
						_showBannedUserDialog();
					}
				} else {
					intent.setClass(getApplicationContext(), CompleteProfileActivity.class);
					startActivity(intent);
					finish();
				}
			}

			@Override
			public void onCancelled(@NonNull DatabaseError databaseError) {
				Log.e("FirebaseError", "Error checking user status: " + databaseError.getMessage());
				Toast.makeText(MainActivity.this, "Could not verify user status. Please try again.", Toast.LENGTH_SHORT).show();
			}
		});
	}

	/**
	 * Shows a dialog for users who are banned.
	 */
	private void _showBannedUserDialog() {
		final AlertDialog bannedDialog = new AlertDialog.Builder(MainActivity.this).create();
		View dialogView = getLayoutInflater().inflate(R.layout.banned_dialog, null);
		bannedDialog.setView(dialogView);
		bannedDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

		final TextView createRequestButton = dialogView.findViewById(R.id.create_request_button);
		final TextView closeAppButton = dialogView.findViewById(R.id.close_app_button);

		View.OnClickListener closeListener = v -> {
			bannedDialog.dismiss();
			finishAffinity();
		};

		createRequestButton.setOnClickListener(closeListener);
		closeAppButton.setOnClickListener(closeListener);

		bannedDialog.setCancelable(false);
		bannedDialog.show();
	}

	/**
	 * Starts a download using Android's DownloadManager.
	 * @param url  The URL of the file to download.
	 * @param path The destination path (e.g., "/Download/").
	 */
	private void _startDownload(final String url, final String path) {
		String fileName = URLUtil.guessFileName(url, null, null);
		DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url))
				.setTitle(fileName)
				.setDescription("Downloading Update...")
				.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
				.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, fileName)
				.setAllowedOverMetered(true)
				.setAllowedOverRoaming(true);

		DownloadManager manager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
		long downloadId = manager.enqueue(request);

        // You can optionally listen for the download completion broadcast
        // to automatically trigger the install prompt.
	}


	/**
	 * Initiates the installation of a downloaded APK file.
	 * @param relativeApkPath The path to the APK relative to the external storage directory (e.g., "/Download/app.apk").
	 */
	public void _installApk(final String relativeApkPath) {
		File file = new File(Environment.getExternalStorageDirectory() + relativeApkPath);
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
				Toast.makeText(getApplicationContext(), "Could not open installer.", Toast.LENGTH_SHORT).show();
			}
		} else {
			Toast.makeText(getApplicationContext(), "Update file not found.", Toast.LENGTH_LONG).show();
		}
	}


	/**
	 * Applies the saved language preference to the activity.
	 */
	private void _applyLanguage() {
		language = getSharedPreferences("language", Activity.MODE_PRIVATE);
		if (language.contains("language")) {
			String langCode = language.getString("language", "en");
			_setLanguage(langCode);
		}
	}

	/**
	 * Sets the app's locale and updates the configuration.
	 * @param languageCode The language code (e.g., "en", "bn").
	 */
	public void _setLanguage(final String languageCode) {
		Locale locale = new Locale(languageCode);
		Locale.setDefault(locale);
		Resources resources = getResources();
		Configuration config = resources.getConfiguration();
		config.setLocale(locale);
		resources.updateConfiguration(config, resources.getDisplayMetrics());
	}
}