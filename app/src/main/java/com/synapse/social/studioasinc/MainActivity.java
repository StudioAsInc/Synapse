package com.synapse.social.studioasinc;

import com.synapse.social.studioasinc.CheckpermissionActivity;
import android.Manifest;
import android.animation.*;
import android.app.*;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.*;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.*;
import android.graphics.*;
import android.graphics.drawable.*;
import android.media.*;
import android.net.*;
import android.net.Uri;
import android.os.*;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.customtabs.*;
import android.text.*;
import android.text.style.*;
import android.util.*;
import android.view.*;
import android.view.View.*;
import android.view.animation.*;
import android.webkit.*;
import android.widget.*;
import android.widget.ImageView;
import android.widget.LinearLayout;
import androidmads.library.qrgenearator.*;
import androidx.annotation.*;
import androidx.appcompat.app.AppCompatActivity;
import androidx.asynclayoutinflater.*;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.interpolator.*;
import androidx.swiperefreshlayout.*;
import androidx.transition.*;
import com.blogspot.atifsoftwares.animatoolib.*;
import com.budiyev.android.codescanner.*;
import com.bumptech.glide.Glide;
import com.caverock.androidsvg.*;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.*;
import com.google.firebase.FirebaseApp;
import com.google.firebase.appcheck.playintegrity.*;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.perf.*;
import com.jsibbold.zoomage.*;
import com.shobhitpuri.custombuttons.*;
import com.sigma.niceswitch.*;
import com.synapse.social.studioasinc.CenterCropLinearLayoutNoEffect;
import com.theartofdev.edmodo.cropper.*;
import com.theophrast.ui.widget.*;
import com.wuyr.rippleanimation.*;
import com.yalantis.ucrop.*;
import eightbitlab.com.blurview.*;
import io.noties.markwon.*;
import io.noties.markwon.ext.strikethrough.*;
import io.noties.markwon.ext.tables.*;
import io.noties.markwon.ext.tasklist.*;
import java.io.*;
import java.io.InputStream;
import java.text.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.*;
import kr.co.prnd.readmore.*;
import me.dm7.barcodescanner.core.*;
import org.jetbrains.kotlin.*;
import org.json.*;
import com.google.android.material.snackbar.Snackbar;

//more
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.util.Base64;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyProperties;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.KeyStore;
import java.security.NoSuchAlgorithmException;
import java.security.KeyStoreException;
import java.security.cert.CertificateException;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchProviderException;


import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
	
	private Timer _timer = new Timer();
	private FirebaseDatabase _firebase = FirebaseDatabase.getInstance();
	
	private String current_version = "";
	private String package_name = "";
	private HashMap<String, Object> map_ver = new HashMap<>();
	private String fileName = "";
	private double dl_progress = 0;
	private String AndroidDevelopersBlogURL = "";
	
	private ArrayList<HashMap<String, Object>> commentsListMap = new ArrayList<>();
	
	private CenterCropLinearLayoutNoEffect body;
	private LinearLayout linear1;
	private LinearLayout middleLayout;
	private LinearLayout linear3;
	private ImageView app_logo;
	private ImageView imageview4;
	
	private Intent intent = new Intent();
	private TimerTask timer;
	private FirebaseAuth auth;
	private OnCompleteListener<AuthResult> _auth_create_user_listener;
	private OnCompleteListener<AuthResult> _auth_sign_in_listener;
	private OnCompleteListener<Void> _auth_reset_password_listener;
	private OnCompleteListener<Void> auth_updateEmailListener;
	private OnCompleteListener<Void> auth_updatePasswordListener;
	private OnCompleteListener<Void> auth_emailVerificationSentListener;
	private OnCompleteListener<Void> auth_deleteUserListener;
	private OnCompleteListener<Void> auth_updateProfileListener;
	private OnCompleteListener<AuthResult> auth_phoneAuthListener;
	private OnCompleteListener<AuthResult> auth_googleSignInListener;
	private DatabaseReference main = _firebase.getReference("skyline");
	private ChildEventListener _main_child_listener;
	private RequestNetwork request;
	private RequestNetwork.RequestListener _request_request_listener;
	private Calendar mCalendar = Calendar.getInstance();
	private TimerTask t;
	private Vibrator v;
	private DatabaseReference ver = _firebase.getReference("inapp/version");
	private ChildEventListener _ver_child_listener;
	private com.google.android.material.bottomsheet.BottomSheetDialog bs;
	private Intent i = new Intent();
	private SharedPreferences theme;
	private SharedPreferences language;
	private AlertDialog.Builder d;
	
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
// do not remove this method 
language = getSharedPreferences("language", Activity.MODE_PRIVATE);
if (language.contains("language")) {
	Locale locale = new Locale(language.getString("language", "en"));
	Locale.setDefault(locale);
	Resources resources = getResources();
	Configuration config = resources.getConfiguration();
	
	// Update the app configuration with the new locale
	if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
		config.setLocale(locale);
		getApplicationContext().createConfigurationContext(config);
	} else {
		config.locale = locale;
	}
	
	// Update the configuration in resources
	resources.updateConfiguration(config, resources.getDisplayMetrics());
}
		setContentView(R.layout.main);
		initialize(_savedInstanceState);
		FirebaseApp.initializeApp(this);
		
		if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED
		|| ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
			ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1000);
		} else {
			initializeLogic();
		}
	}
	
	@Override
	public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
		super.onRequestPermissionsResult(requestCode, permissions, grantResults);
		if (requestCode == 1000) {
			initializeLogic();
		}
	}
	
	private void initialize(Bundle _savedInstanceState) {
		body = findViewById(R.id.body);
		linear1 = findViewById(R.id.linear1);
		middleLayout = findViewById(R.id.middleLayout);
		linear3 = findViewById(R.id.linear3);
		app_logo = findViewById(R.id.app_logo);
		imageview4 = findViewById(R.id.imageview4);
		auth = FirebaseAuth.getInstance();
		request = new RequestNetwork(this);
		v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
		theme = getSharedPreferences("theme", Activity.MODE_PRIVATE);
		language = getSharedPreferences("language", Activity.MODE_PRIVATE);
		d = new AlertDialog.Builder(this);
		
		app_logo.setOnLongClickListener(new View.OnLongClickListener() {
			@Override
			public boolean onLongClick(View _view) {
				if (FirebaseAuth.getInstance().getCurrentUser().getEmail().equals("mashikahamed0@gmail.com")) {
					if ((FirebaseAuth.getInstance().getCurrentUser() != null)) {
						_setLanguage("bn");
						language.edit().putString("language", "bn").commit();
						i.setClass(getApplicationContext(), HomeActivity.class);
						startActivity(i);
						finish();
					} else {
						i.setClass(getApplicationContext(), OnboardActivity.class);
						startActivity(i);
						finish();
					}
				} else {
					
				}
				return true;
			}
		});
		
		_main_child_listener = new ChildEventListener() {
			@Override
			public void onChildAdded(DataSnapshot _param1, String _param2) {
				GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
				final String _childKey = _param1.getKey();
				final HashMap<String, Object> _childValue = _param1.getValue(_ind);
				
			}
			
			@Override
			public void onChildChanged(DataSnapshot _param1, String _param2) {
				GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
				final String _childKey = _param1.getKey();
				final HashMap<String, Object> _childValue = _param1.getValue(_ind);
				
			}
			
			@Override
			public void onChildMoved(DataSnapshot _param1, String _param2) {
				
			}
			
			@Override
			public void onChildRemoved(DataSnapshot _param1) {
				GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
				final String _childKey = _param1.getKey();
				final HashMap<String, Object> _childValue = _param1.getValue(_ind);
				
			}
			
			@Override
			public void onCancelled(DatabaseError _param1) {
				final int _errorCode = _param1.getCode();
				final String _errorMessage = _param1.getMessage();
				
			}
		};
		main.addChildEventListener(_main_child_listener);
		
		_request_request_listener = new RequestNetwork.RequestListener() {
			@Override
			public void onResponse(String _param1, String _param2, HashMap<String, Object> _param3) {
				final String _tag = _param1;
				final String _response = _param2;
				final HashMap<String, Object> _responseHeaders = _param3;
				if ((FirebaseAuth.getInstance().getCurrentUser() != null)) {
					_GoNextPage();
				} else {
					intent.setClass(getApplicationContext(), OnboardActivity.class);
					startActivity(intent);
					finish();
				}
			}
			
			@Override
			public void onErrorResponse(String _param1, String _param2) {
				final String _tag = _param1;
				final String _message = _param2;
				SketchwareUtil.showMessage(getApplicationContext(), _message);
			}
		};
		
		_ver_child_listener = new ChildEventListener() {
			@Override
			public void onChildAdded(DataSnapshot _param1, String _param2) {
				GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
				final String _childKey = _param1.getKey();
				final HashMap<String, Object> _childValue = _param1.getValue(_ind);
				if (_childKey.equals("app")) {
					if (Double.parseDouble(current_version) < Double.parseDouble(_childValue.get("version").toString())) {
						bs = new com.google.android.material.bottomsheet.BottomSheetDialog(MainActivity.this);
						View bsV;
						bsV = getLayoutInflater().inflate(R.layout.update_sheet,null );
						bs.setContentView(bsV);
						bs.getWindow().findViewById(R.id.design_bottom_sheet).setBackgroundResource(android.R.color.transparent);
						final LinearLayout main = (LinearLayout) bsV.findViewById(R.id.FatherLayout);
						final TextView app_name = (TextView) bsV.findViewById(R.id.app_name);
						final TextView version_txt = (TextView) bsV.findViewById(R.id.version_txt);
						final TextView app_size = (TextView) bsV.findViewById(R.id.update_size);
						final LinearLayout more_btn1 = (LinearLayout) bsV.findViewById(R.id.more_btn);
						final LinearLayout update_btn1 = (LinearLayout) bsV.findViewById(R.id.update_btn);
						final TextView updated_on_txt = (TextView) bsV.findViewById(R.id.update_push_date);
						final ImageView app_icon = (ImageView) bsV.findViewById(R.id.app_icon);
						final TextView whats_new_subtitle1 = (TextView) bsV.findViewById(R.id.whats_new_subtitle);
						final ProgressBar progressbar = (ProgressBar) bsV.findViewById(R.id.progressbar1);
						final LinearLayout DownloadLayout = (LinearLayout) bsV.findViewById(R.id.DownloadLayout);
						final LinearLayout whats_new_layout1 = (LinearLayout) bsV.findViewById(R.id.whats_new_layout);
						final LinearLayout linear11 = (LinearLayout) bsV.findViewById(R.id.linear11);
						final LinearLayout container = (LinearLayout) bsV.findViewById(R.id.container);
						final LinearLayout space = (LinearLayout) bsV.findViewById(R.id.space);
						final LinearLayout app_holder = (LinearLayout) bsV.findViewById(R.id.app_holder);
						final LinearLayout whatisnew_holder = (LinearLayout) bsV.findViewById(R.id.whatisnew_holder);
						final LinearLayout useless_holder = (LinearLayout) bsV.findViewById(R.id.useless_holder);
						final LinearLayout btn_holder = (LinearLayout) bsV.findViewById(R.id.btn_holder);
						final LinearLayout linear6 = (LinearLayout) bsV.findViewById(R.id.linear6);
						final LinearLayout app_text_holder = (LinearLayout) bsV.findViewById(R.id.app_text_holder);
						final LinearLayout linear10 = (LinearLayout) bsV.findViewById(R.id.linear10);
						final LinearLayout useless_layout = (LinearLayout) bsV.findViewById(R.id.useless_layout);
						final ImageView cross_ic = (ImageView) bsV.findViewById(R.id.cross_ic);
						if (_childValue.containsKey("skippable")) {
							if (_childValue.get("skippable").toString().equals("true")) {
								bs.setCancelable(true);
							} else {
								bs.setCancelable(false);
							}
						} else {
							bs.setCancelable(false);
						}
						bs.show();
						{
							android.graphics.drawable.GradientDrawable SketchUi = new android.graphics.drawable.GradientDrawable();
							int d = (int) getApplicationContext().getResources().getDisplayMetrics().density;
							SketchUi.setColor(0xFFFFFFFF);SketchUi.setCornerRadii(new float[]{
								d*17,d*17,d*17 ,d*17,d*0,d*0 ,d*0,d*0});
							main.setElevation(d*1);
							main.setBackground(SketchUi);
						}
						if (_childValue.containsKey("changes")) {
							whats_new_subtitle1.setText(Html.fromHtml(_childValue.get("changes").toString()));
						}
						if (_childValue.containsKey("version")) {
							version_txt.setText("Version ".concat(_childValue.get("version").toString()));
						}
						if (_childValue.containsKey("app name")) {
							app_name.setText(_childValue.get("app name").toString());
						}
						if (_childValue.containsKey("size")) {
							app_size.setText(_childValue.get("size").toString());
						}
						if (_childValue.containsKey("release date")) {
							updated_on_txt.setText(_childValue.get("release date").toString());
						}
						if (_childValue.containsKey("icon")) {
							Glide.with(getApplicationContext()).load(Uri.parse(_childValue.get("icon").toString())).into(app_icon);
						}
						if (_childValue.containsKey("link")) {
							update_btn1.setOnClickListener(new View.OnClickListener() {
								@Override
								public void onClick(View _view) {
									_Download_No_Dialog(_childValue.get("link").toString(), "/Download/");
									/*
progressbar.setVisibility(View.VISIBLE);
*/
									DownloadLayout.setVisibility(View.VISIBLE);
								}
							});
							more_btn1.setOnClickListener(new View.OnClickListener() {
								@Override
								public void onClick(View _view) {
									_OpenWebView(_childValue.get("link").toString());
								}
							});
							more_btn1.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b, int c, int d) { this.setCornerRadius(a); this.setStroke(b, c); this.setColor(d); return this; } }.getIns((int)360, (int)3, 0xFF2962FF, Color.TRANSPARENT));
							update_btn1.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b, int c, int d) { this.setCornerRadius(a); this.setStroke(b, c); this.setColor(d); return this; } }.getIns((int)360, (int)0, getResources().getColor(R.color.colorPrimary), getResources().getColor(R.color.colorPrimary)));
							_viewGraphics(whats_new_layout1, 0xFFF6F7FB, 0xFFF6F7FB, 20, 0, 0xFF2962FF);
							cross_ic.setOnClickListener(new View.OnClickListener() {
								@Override
								public void onClick(View _view) {
									finish();
								}
							});
							progressbar.setVisibility(View.GONE);
						}
					} else {
						request.startRequestNetwork(RequestNetworkController.POST, "https://google.com", "google", _request_request_listener);
					}
				} else {
					request.startRequestNetwork(RequestNetworkController.POST, "https://google.com", "google", _request_request_listener);
				}
			}
			
			@Override
			public void onChildChanged(DataSnapshot _param1, String _param2) {
				GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
				final String _childKey = _param1.getKey();
				final HashMap<String, Object> _childValue = _param1.getValue(_ind);
				
			}
			
			@Override
			public void onChildMoved(DataSnapshot _param1, String _param2) {
				
			}
			
			@Override
			public void onChildRemoved(DataSnapshot _param1) {
				GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
				final String _childKey = _param1.getKey();
				final HashMap<String, Object> _childValue = _param1.getValue(_ind);
				
			}
			
			@Override
			public void onCancelled(DatabaseError _param1) {
				final int _errorCode = _param1.getCode();
				final String _errorMessage = _param1.getMessage();
				
			}
		};
		ver.addChildEventListener(_ver_child_listener);
		
		auth_updateEmailListener = new OnCompleteListener<Void>() {
			@Override
			public void onComplete(Task<Void> _param1) {
				final boolean _success = _param1.isSuccessful();
				final String _errorMessage = _param1.getException() != null ? _param1.getException().getMessage() : "";
				
			}
		};
		
		auth_updatePasswordListener = new OnCompleteListener<Void>() {
			@Override
			public void onComplete(Task<Void> _param1) {
				final boolean _success = _param1.isSuccessful();
				final String _errorMessage = _param1.getException() != null ? _param1.getException().getMessage() : "";
				
			}
		};
		
		auth_emailVerificationSentListener = new OnCompleteListener<Void>() {
			@Override
			public void onComplete(Task<Void> _param1) {
				final boolean _success = _param1.isSuccessful();
				final String _errorMessage = _param1.getException() != null ? _param1.getException().getMessage() : "";
				
			}
		};
		
		auth_deleteUserListener = new OnCompleteListener<Void>() {
			@Override
			public void onComplete(Task<Void> _param1) {
				final boolean _success = _param1.isSuccessful();
				final String _errorMessage = _param1.getException() != null ? _param1.getException().getMessage() : "";
				
			}
		};
		
		auth_phoneAuthListener = new OnCompleteListener<AuthResult>() {
			@Override
			public void onComplete(Task<AuthResult> task) {
				final boolean _success = task.isSuccessful();
				final String _errorMessage = task.getException() != null ? task.getException().getMessage() : "";
				
			}
		};
		
		auth_updateProfileListener = new OnCompleteListener<Void>() {
			@Override
			public void onComplete(Task<Void> _param1) {
				final boolean _success = _param1.isSuccessful();
				final String _errorMessage = _param1.getException() != null ? _param1.getException().getMessage() : "";
				
			}
		};
		
		auth_googleSignInListener = new OnCompleteListener<AuthResult>() {
			@Override
			public void onComplete(Task<AuthResult> task) {
				final boolean _success = task.isSuccessful();
				final String _errorMessage = task.getException() != null ? task.getException().getMessage() : "";
				
			}
		};
		
		_auth_create_user_listener = new OnCompleteListener<AuthResult>() {
			@Override
			public void onComplete(Task<AuthResult> _param1) {
				final boolean _success = _param1.isSuccessful();
				final String _errorMessage = _param1.getException() != null ? _param1.getException().getMessage() : "";
				
			}
		};
		
		_auth_sign_in_listener = new OnCompleteListener<AuthResult>() {
			@Override
			public void onComplete(Task<AuthResult> _param1) {
				final boolean _success = _param1.isSuccessful();
				final String _errorMessage = _param1.getException() != null ? _param1.getException().getMessage() : "";
				
			}
		};
		
		_auth_reset_password_listener = new OnCompleteListener<Void>() {
			@Override
			public void onComplete(Task<Void> _param1) {
				final boolean _success = _param1.isSuccessful();
				
			}
		};
	}
	
	private void initializeLogic() {
		package_name = "com.synapse.social.studioasinc";
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) { Window w = getWindow();  w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS); };
		current_version = "1";
		DatabaseReference rootRef = _firebase.getReference();
		 rootRef.child("inapp/version").addListenerForSingleValueEvent(new ValueEventListener() {
			@Override
			public void onDataChange(DataSnapshot snapshot) {
				if (snapshot.exists()) { } else {
					map_ver = new HashMap<>();
					map_ver.put("version", current_version);
					ver.child("app").updateChildren(map_ver);
					map_ver.clear();
				} }
			@Override
			public void onCancelled(DatabaseError _error) { } });
		getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
		setTitle(_getString("app_name"));
		//For dynamic app icon, remove if your device is below sdk 26 and change sdk min level
	}
	
	
	@Override
	public void onBackPressed() {
		finishAffinity();
	}
	public void _stateColor(final int _statusColor, final int _navigationColor) {
		getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
		getWindow().setStatusBarColor(_statusColor);
		getWindow().setNavigationBarColor(_navigationColor);
	}
	
	
	public void _ImageColor(final ImageView _image, final int _color) {
		_image.setColorFilter(_color,PorterDuff.Mode.SRC_ATOP);
	}
	
	
	public void _InfoDialogView(final String _title, final String _message) {
		{
			final AlertDialog NewCustomDialog = new AlertDialog.Builder(MainActivity.this).create();
			LayoutInflater NewCustomDialogLI = getLayoutInflater();
			View NewCustomDialogCV = (View) NewCustomDialogLI.inflate(R.layout.synapse_dialog_bg_view, null);
			NewCustomDialog.setView(NewCustomDialogCV);
			NewCustomDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
			
			final TextView dialog_title = (TextView) NewCustomDialogCV.findViewById(R.id.dialog_title);
			final TextView dialog_message = (TextView) NewCustomDialogCV.findViewById(R.id.dialog_message);
			final TextView dialog_no_button = (TextView) NewCustomDialogCV.findViewById(R.id.dialog_no_button);
			final TextView dialog_yes_button = (TextView) NewCustomDialogCV.findViewById(R.id.dialog_yes_button);
			dialog_no_button.setVisibility(View.GONE);
			dialog_yes_button.setTextColor(0xFF2196F3);
			_viewGraphics(dialog_yes_button, 0xFFFFFFFF, 0xFFBBDEFB, 28, 0, Color.TRANSPARENT);
			dialog_no_button.setTextColor(0xFF2196F3);
			_viewGraphics(dialog_no_button, 0xFFFFFFFF, 0xFFBBDEFB, 28, 0, Color.TRANSPARENT);
			dialog_title.setText(_title);
			dialog_message.setText(_message);
			dialog_yes_button.setText(getResources().getString(R.string.okay));
			dialog_no_button.setText(getResources().getString(R.string.no));
			dialog_yes_button.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View _view) {
					finishAffinity();
				}
			});
			NewCustomDialog.setCancelable(false);
			NewCustomDialog.show();
		}
	}
	
	
	public void _viewGraphics(final View _view, final int _onFocus, final int _onRipple, final double _radius, final double _stroke, final int _strokeColor) {
		android.graphics.drawable.GradientDrawable GG = new android.graphics.drawable.GradientDrawable();
		GG.setColor(_onFocus);
		GG.setCornerRadius((float)_radius);
		GG.setStroke((int) _stroke, _strokeColor);
		android.graphics.drawable.RippleDrawable RE = new android.graphics.drawable.RippleDrawable(new android.content.res.ColorStateList(new int[][]{new int[]{}}, new int[]{ _onRipple}), GG, null);
		_view.setBackground(RE);
	}
	
	
	public void _GoNextPage() {
		DatabaseReference checkUser = FirebaseDatabase.getInstance().getReference("skyline/users").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
		checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
			@Override
			public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
				if (dataSnapshot.exists()) {
					if (dataSnapshot.child("banned").getValue(String.class).equals("false")) {
						intent.setClass(getApplicationContext(), HomeActivity.class);
						startActivity(intent);
						finish();
					} else {
						_openBlockAlert();
					}
				} else {
					intent.setClass(getApplicationContext(), CompleteProfileActivity.class);
					startActivity(intent);
					finish();
				}
			}
			
			@Override
			public void onCancelled(@NonNull DatabaseError databaseError) {
						
			}
		});
	}
	
	
	public void _openBlockAlert() {
		{
			final AlertDialog NewCustomDialog = new AlertDialog.Builder(MainActivity.this).create();
			LayoutInflater NewCustomDialogLI = getLayoutInflater();
			View NewCustomDialogCV = (View) NewCustomDialogLI.inflate(R.layout.banned_dialog, null);
			NewCustomDialog.setView(NewCustomDialogCV);
			NewCustomDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
			
			final LinearLayout dialog_body = (LinearLayout) NewCustomDialogCV.findViewById(R.id.dialog_body);
			final TextView create_request_button = (TextView) NewCustomDialogCV.findViewById(R.id.create_request_button);
			final TextView close_app_button = (TextView) NewCustomDialogCV.findViewById(R.id.close_app_button);
			dialog_body.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b) { this.setCornerRadius(a); this.setColor(b); return this; } }.getIns((int)48, 0xFFFFFFFF));
			_viewGraphics(create_request_button, 0xFF000000, 0xFF616161, 300, 0, Color.TRANSPARENT);
			_viewGraphics(close_app_button, 0xFFF5F5F5, 0xFFE0E0E0, 300, 0, Color.TRANSPARENT);
			create_request_button.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View _view) {
					NewCustomDialog.dismiss();
					finishAffinity();
				}
			});
			close_app_button.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View _view) {
					NewCustomDialog.dismiss();
					finishAffinity();
				}
			});
			NewCustomDialog.setCancelable(false);
			NewCustomDialog.show();
		}
	}
	
	
	public void _Download_No_Dialog(final String _url, final String _path) {
		if (FileUtil.isExistFile("/Download/Synapse.apk")) {
			FileUtil.deleteFile("/Download/Synapse.apk");
			FileUtil.makeDir("Synapse/updates/apk/");
			
		} else {
			FileUtil.makeDir(FileUtil.getPackageDataDir(getApplicationContext()));
			android.net.ConnectivityManager connMgr = (android.net.ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
			android.net.NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
			if (networkInfo != null && networkInfo.isConnected()) {
				
				
				final String urlDownload = _url;
				
				DownloadManager.Request request = new DownloadManager.Request(Uri.parse(urlDownload));
				
				final String fileName = URLUtil.guessFileName(urlDownload, null, null);
				
				request.setDescription("Download Processing...");
				
				request.setTitle(fileName);
				
				request.allowScanningByMediaScanner();
				
				request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
				
				request.setDestinationInExternalPublicDir(_path, fileName);
				
				final DownloadManager manager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
				
				final long downloadId = manager.enqueue(request);
				
				
				new Thread(new Runnable() {
					@Override
					public void run() {
						
						boolean downloading = true;
						
						while (downloading) {
							
							DownloadManager.Query q = new DownloadManager.Query();
							
							q.setFilterById(downloadId);
							
							android.database.Cursor cursor = manager.query(q);
							
							cursor.moveToFirst();
							
							int bytes_downloaded = cursor.getInt(cursor .getColumnIndex(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR));
							
							int bytes_total = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_TOTAL_SIZE_BYTES));
							
							if (cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS)) == DownloadManager.STATUS_SUCCESSFUL) {
								
								downloading = false;
								
							}
							
							final int dl_progress = (int) ((bytes_downloaded * 100l) / bytes_total);
							
							runOnUiThread(new Runnable() {
								@Override
								public void run() {
									
									
									if (dl_progress == 100) {
										/*
 
*/
									}
								} });
						} } }).start();
				
			} else {
				
				/*
rn2.startRequestNetwork(RequestNetworkController.GET, "https://google.com", "", _rn2_request_listener);
*/
			}
		}
	}
	
	
	public void _Install(final String _apk) {
		String PATH = Environment.getExternalStorageDirectory() + _apk;
		    java.io.File file = new java.io.File(PATH);
		    if(file.exists()) {
				        Intent intent = new Intent(Intent.ACTION_VIEW);
				        intent.setDataAndType(uriFromFile(getApplicationContext(), new java.io.File(PATH)), "application/vnd.android.package-archive");
				        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
				        try {
						            getApplicationContext().startActivity(intent);
						        } catch (ActivityNotFoundException e) {
						            e.printStackTrace();
						            Log.e("TAG", "Error in opening the file!");
						        }
				    }else{
				        Toast.makeText(getApplicationContext(),"installing",Toast.LENGTH_LONG).show();
				    }
	}
	Uri uriFromFile(Context context, java.io.File file) {
		    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
				        return androidx.core.content.FileProvider.getUriForFile(context,context.getApplicationContext().getPackageName() + ".provider", file); 
				    } else {
				        return Uri.fromFile(file);
				    }
	}
	
	
	public void _download() {
	}
	private class ProgDiagTask extends AsyncTask<String, Integer, String> {
			ProgressDialog prog;
			@Override
			protected void onPreExecute() {
					super.onPreExecute();
					prog = new ProgressDialog(MainActivity.this);
					prog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
									prog.setMax(100);
									prog.setIndeterminate(false);
									prog.setCancelable(false);
									prog.setCanceledOnTouchOutside(false);
					prog.setTitle("Updating app");
					prog.setMessage("Downloading...");
					prog.show();
			}
			
			String filename = "";
			String result = "";
			double size = 0;
			double sumCount = 0;
			
			@Override
			protected String doInBackground(String... address) {
					try {
							filename= URLUtil.guessFileName(address[0], null, null);
							prog.setMessage("\nDownloading: ".concat(filename));
							int resCode = -1;
							java.io.InputStream in = null;
							java.net.URL url = new java.net.URL(address[0]);
							java.net.URLConnection urlConn = url.openConnection();
							if (!(urlConn instanceof java.net.HttpURLConnection)) {
									throw new java.io.IOException("URL is not an Http URL"); }
							java.net.HttpURLConnection httpConn = (java.net.HttpURLConnection) urlConn; httpConn.setAllowUserInteraction(false); httpConn.setInstanceFollowRedirects(true); httpConn.setRequestMethod("GET"); httpConn.connect();
							resCode = httpConn.getResponseCode();
							if (resCode == java.net.HttpURLConnection.HTTP_OK) {
									in = httpConn.getInputStream();
									size = httpConn.getContentLength();
									
							} else { result = "There was an error"; }
							
							String path =FileUtil.getExternalStorageDir().concat("/Download/".concat(filename));
							
							java.io.File file = new java.io.File(path);
							
							java.io.OutputStream output = new java.io.FileOutputStream(file);
							try {
									int bytesRead;
									sumCount = 0;
									byte[] buffer = new byte[1024];
									while ((bytesRead = in.read(buffer)) != -1) {
											output.write(buffer, 0, bytesRead);
											sumCount += bytesRead;
											if (size > 0) {
													publishProgress((int)Math.round(sumCount*100 / size));
											}
									}
							} finally {
									output.close();
							}
							in.close();
					} catch (java.net.MalformedURLException e) {
							result = e.getMessage();
					} catch (java.io.IOException e) {
							result = e.getMessage();
					} catch (Exception e) {
							result = e.toString();
					}
					return result;
			}
			@Override
			protected void onProgressUpdate(Integer... values) {
					super.onProgressUpdate(values);
					prog.setProgress(values[values.length - 1]);
					double num = prog.getProgress();
					
			}
			@Override
			protected void onPostExecute(String s){
					if (filename.contains(".apk")) {
							_Install("/Download/".concat(filename));
					}
					prog.dismiss();
			}
	}
	
	
	public void _detect_theme() {
		if (theme.contains("theme")) {
			if (theme.getString("theme", "").equals("light")) {
				_light_theme_settings();
			} else {
				if (theme.getString("theme", "").equals("dark")) {
					_dark_theme_settings();
				} else {
					if (theme.getString("theme", "").equals("night")) {
						_night_theme_settings();
					} else {
						if (theme.getString("theme", "").equals("amoled")) {
							_amoled_theme_settings();
						} else {
							
						}
					}
				}
			}
		} else {
			_light_theme_settings();
		}
	}
	
	
	public void _light_theme_settings() {
		
	}
	
	
	public void _dark_theme_settings() {
		body.setBackgroundResource(R.drawable.bgimage);
	}
	
	
	public void _night_theme_settings() {
		
	}
	
	
	public void _amoled_theme_settings() {
		
	}
	
	
	public String _getString(final String _stringKey) {
		int resourceId = getResources().getIdentifier(_stringKey, "string", getPackageName());
		if (resourceId != 0) {
				return getString(resourceId);
		} else {
				return "String not found"; // or handle this case as needed
		}
	}
	
	
	public void _setLanguage(final String _languageCode) {
		Locale locale = new Locale(_languageCode);
		Locale.setDefault(locale);
		Resources resources = getResources();
		Configuration config = resources.getConfiguration();
		
		// Update the app configuration with the new locale
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
				config.setLocale(locale);
				getApplicationContext().createConfigurationContext(config);
		} else {
				config.locale = locale;
		}
		
		// Update the configuration in resources
		resources.updateConfiguration(config, resources.getDisplayMetrics());
		
		// Restart the current activity to apply the language change
		recreate();
	}
	
	
	public void _OpenWebView(final String _URL) {
		AndroidDevelopersBlogURL = _URL;
		CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
		builder.setToolbarColor(Color.parseColor("#242D39"));
		CustomTabsIntent customtabsintent = builder.build();
		customtabsintent.launchUrl(this, Uri.parse(AndroidDevelopersBlogURL));
	}
	
	
	@Deprecated
	public void showMessage(String _s) {
		Toast.makeText(getApplicationContext(), _s, Toast.LENGTH_SHORT).show();
	}
	
	@Deprecated
	public int getLocationX(View _v) {
		int _location[] = new int[2];
		_v.getLocationInWindow(_location);
		return _location[0];
	}
	
	@Deprecated
	public int getLocationY(View _v) {
		int _location[] = new int[2];
		_v.getLocationInWindow(_location);
		return _location[1];
	}
	
	@Deprecated
	public int getRandom(int _min, int _max) {
		Random random = new Random();
		return random.nextInt(_max - _min + 1) + _min;
	}
	
	@Deprecated
	public ArrayList<Double> getCheckedItemPositionsToArray(ListView _list) {
		ArrayList<Double> _result = new ArrayList<Double>();
		SparseBooleanArray _arr = _list.getCheckedItemPositions();
		for (int _iIdx = 0; _iIdx < _arr.size(); _iIdx++) {
			if (_arr.valueAt(_iIdx))
			_result.add((double)_arr.keyAt(_iIdx));
		}
		return _result;
	}
	
	@Deprecated
	public float getDip(int _input) {
		return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, _input, getResources().getDisplayMetrics());
	}
	
	@Deprecated
	public int getDisplayWidthPixels() {
		return getResources().getDisplayMetrics().widthPixels;
	}
	
	@Deprecated
	public int getDisplayHeightPixels() {
		return getResources().getDisplayMetrics().heightPixels;
	}
}