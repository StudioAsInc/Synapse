package com.synapse.social.studioasinc;

import android.animation.*;
import android.animation.ObjectAnimator;
import android.app.*;
import android.app.Activity;
import android.content.*;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.*;
import android.graphics.*;
import android.graphics.Typeface;
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
import android.view.View;
import android.view.View.*;
import android.view.animation.*;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.BounceInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.webkit.*;
import android.widget.*;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidmads.library.qrgenearator.*;
import androidx.annotation.*;
import androidx.appcompat.app.AppCompatActivity;
import androidx.asynclayoutinflater.*;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.interpolator.*;
import androidx.swiperefreshlayout.*;
import androidx.transition.*;
import com.blogspot.atifsoftwares.animatoolib.*;
import com.budiyev.android.codescanner.*;
import com.caverock.androidsvg.*;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.*;
import com.google.firebase.FirebaseApp;
import com.google.firebase.appcheck.playintegrity.*;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
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
import java.util.*;
import java.util.HashMap;
import java.util.regex.*;
import kr.co.prnd.readmore.*;
import me.dm7.barcodescanner.core.*;
import org.jetbrains.kotlin.*;
import org.json.*;
import com.google.firebase.database.Query;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;

import com.google.android.material.textfield.TextInputLayout;

public class OnboardActivity extends AppCompatActivity {
	
	public final int REQ_CD_LOGIN_GOOGLE = 101;
	
	private FirebaseDatabase _firebase = FirebaseDatabase.getInstance();
	
	private ProgressDialog SynapseLoadingDialog;
	private GoogleSignInOptions mGoogleSignInOptions;
	private GoogleSignInAccount mGoogleSignInAccount;
	private AuthCredential mGoogleLoginCredential;
	private FirebaseUser FirebaseUser;
	private double num2 = 0;
	private String RandomUsername = "";
	private String random_bg = "";
	private HashMap<String, Object> logins = new HashMap<>();
	private String hash = "";
	
	private CenterCropLinearLayoutNoEffect body;
	private LinearLayout linear4;
	private LinearLayout middle;
	private LinearLayout linear2;
	private TextView textview1;
	private ImageView app_logo;
	private TextView welcome_txt;
	private TextView textview3;
	private TextView textview4;
	private LinearLayout loginWithMail;
	private LinearLayout button1;
	private LinearLayout button2;
	private TextView textview5;
	private ImageView sign_google_button_ic;
	private TextView sign_google_button_title;
	private ImageView github_ic;
	private TextView github_btn_txt;
	
	private Vibrator vbr;
	private Intent intent = new Intent();
	private GoogleSignInClient LOGIN_GOOGLE;
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
	private DatabaseReference login_history = _firebase.getReference("skyline/users");
	private ChildEventListener _login_history_child_listener;
	private SharedPreferences theme;
	private ObjectAnimator Animator = new ObjectAnimator();
	private ObjectAnimator animator = new ObjectAnimator();
	private AlertDialog cd;
	private FirebaseAuth hashauth;
	private OnCompleteListener<AuthResult> _hashauth_create_user_listener;
	private OnCompleteListener<AuthResult> _hashauth_sign_in_listener;
	private OnCompleteListener<Void> _hashauth_reset_password_listener;
	private OnCompleteListener<Void> hashauth_updateEmailListener;
	private OnCompleteListener<Void> hashauth_updatePasswordListener;
	private OnCompleteListener<Void> hashauth_emailVerificationSentListener;
	private OnCompleteListener<Void> hashauth_deleteUserListener;
	private OnCompleteListener<Void> hashauth_updateProfileListener;
	private OnCompleteListener<AuthResult> hashauth_phoneAuthListener;
	private OnCompleteListener<AuthResult> hashauth_googleSignInListener;
	
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.onboard);
		initialize(_savedInstanceState);
		FirebaseApp.initializeApp(this);
		initializeLogic();
	}
	
	private void initialize(Bundle _savedInstanceState) {
		body = findViewById(R.id.body);
		linear4 = findViewById(R.id.linear4);
		middle = findViewById(R.id.middle);
		linear2 = findViewById(R.id.linear2);
		textview1 = findViewById(R.id.textview1);
		app_logo = findViewById(R.id.app_logo);
		welcome_txt = findViewById(R.id.welcome_txt);
		textview3 = findViewById(R.id.textview3);
		textview4 = findViewById(R.id.textview4);
		loginWithMail = findViewById(R.id.loginWithMail);
		button1 = findViewById(R.id.button1);
		button2 = findViewById(R.id.button2);
		textview5 = findViewById(R.id.textview5);
		sign_google_button_ic = findViewById(R.id.sign_google_button_ic);
		sign_google_button_title = findViewById(R.id.sign_google_button_title);
		github_ic = findViewById(R.id.github_ic);
		github_btn_txt = findViewById(R.id.github_btn_txt);
		vbr = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
		auth = FirebaseAuth.getInstance();
		theme = getSharedPreferences("theme", Activity.MODE_PRIVATE);
		hashauth = FirebaseAuth.getInstance();
		
		loginWithMail.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				intent.setClass(getApplicationContext(), LoginActivity.class);
				startActivity(intent);
			}
		});
		
		button1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				_LoginWithDeviceUniqueness();
				/*
Intent mGoogleLoginIntent = LOGIN_GOOGLE.getSignInIntent();
startActivityForResult(mGoogleLoginIntent, REQ_CD_LOGIN_GOOGLE);
*/
			}
		});
		
		button2.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				
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
		
		_login_history_child_listener = new ChildEventListener() {
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
		login_history.addChildEventListener(_login_history_child_listener);
		
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
				if (_success) {
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
				} else {
					
				}
			}
		};
		
		_auth_reset_password_listener = new OnCompleteListener<Void>() {
			@Override
			public void onComplete(Task<Void> _param1) {
				final boolean _success = _param1.isSuccessful();
				
			}
		};
		
		hashauth_updateEmailListener = new OnCompleteListener<Void>() {
			@Override
			public void onComplete(Task<Void> _param1) {
				final boolean _success = _param1.isSuccessful();
				final String _errorMessage = _param1.getException() != null ? _param1.getException().getMessage() : "";
				
			}
		};
		
		hashauth_updatePasswordListener = new OnCompleteListener<Void>() {
			@Override
			public void onComplete(Task<Void> _param1) {
				final boolean _success = _param1.isSuccessful();
				final String _errorMessage = _param1.getException() != null ? _param1.getException().getMessage() : "";
				
			}
		};
		
		hashauth_emailVerificationSentListener = new OnCompleteListener<Void>() {
			@Override
			public void onComplete(Task<Void> _param1) {
				final boolean _success = _param1.isSuccessful();
				final String _errorMessage = _param1.getException() != null ? _param1.getException().getMessage() : "";
				
			}
		};
		
		hashauth_deleteUserListener = new OnCompleteListener<Void>() {
			@Override
			public void onComplete(Task<Void> _param1) {
				final boolean _success = _param1.isSuccessful();
				final String _errorMessage = _param1.getException() != null ? _param1.getException().getMessage() : "";
				
			}
		};
		
		hashauth_phoneAuthListener = new OnCompleteListener<AuthResult>() {
			@Override
			public void onComplete(Task<AuthResult> task) {
				final boolean _success = task.isSuccessful();
				final String _errorMessage = task.getException() != null ? task.getException().getMessage() : "";
				
			}
		};
		
		hashauth_updateProfileListener = new OnCompleteListener<Void>() {
			@Override
			public void onComplete(Task<Void> _param1) {
				final boolean _success = _param1.isSuccessful();
				final String _errorMessage = _param1.getException() != null ? _param1.getException().getMessage() : "";
				
			}
		};
		
		hashauth_googleSignInListener = new OnCompleteListener<AuthResult>() {
			@Override
			public void onComplete(Task<AuthResult> task) {
				final boolean _success = task.isSuccessful();
				final String _errorMessage = task.getException() != null ? task.getException().getMessage() : "";
				
			}
		};
		
		_hashauth_create_user_listener = new OnCompleteListener<AuthResult>() {
			@Override
			public void onComplete(Task<AuthResult> _param1) {
				final boolean _success = _param1.isSuccessful();
				final String _errorMessage = _param1.getException() != null ? _param1.getException().getMessage() : "";
				
			}
		};
		
		_hashauth_sign_in_listener = new OnCompleteListener<AuthResult>() {
			@Override
			public void onComplete(Task<AuthResult> _param1) {
				final boolean _success = _param1.isSuccessful();
				final String _errorMessage = _param1.getException() != null ? _param1.getException().getMessage() : "";
				if (_success) {
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
				} else {
					hash = "hash.".concat(Build.ID).concat(Build.VERSION.SDK);
					hashauth.createUserWithEmailAndPassword(hash.concat(" @sai.com"), hash).addOnCompleteListener(OnboardActivity.this, _hashauth_create_user_listener);
				}
			}
		};
		
		_hashauth_reset_password_listener = new OnCompleteListener<Void>() {
			@Override
			public void onComplete(Task<Void> _param1) {
				final boolean _success = _param1.isSuccessful();
				
			}
		};
	}
	
	private void initializeLogic() {
		_detect_theme();
		getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
		String GoogleAuthClientKey = "269633434355-8gmtl2eoja05ojll4ubhs1kpppt1q1mh.apps.googleusercontent.com";
		GoogleSignInOptions mGoogleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestIdToken(GoogleAuthClientKey).requestEmail().build();
		LOGIN_GOOGLE = GoogleSignIn.getClient(this, mGoogleSignInOptions);
		auth = FirebaseAuth.getInstance();
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) { Window w = getWindow();  w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS); };
		sign_google_button_title.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/appfont.ttf"), 1);
		Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/ginto650otf.ttf");
		welcome_txt.setTypeface(typeface);
		Animator.setTarget(welcome_txt);
		Animator.setPropertyName("alpha");
		Animator.setFloatValues((float)(0.1d), (float)(1));
		Animator.setDuration((int)(1000));
		Animator.start();
	}
	
	@Override
	protected void onActivityResult(int _requestCode, int _resultCode, Intent _data) {
		super.onActivityResult(_requestCode, _resultCode, _data);
		
		switch (_requestCode) {
			case REQ_CD_LOGIN_GOOGLE:
			if (_resultCode == Activity.RESULT_OK) {
				Task<GoogleSignInAccount> _task = GoogleSignIn.getSignedInAccountFromIntent(_data);
				
				try{
					GoogleSignInAccount mGoogleSignInAccount = _task.getResult(ApiException.class);
					_GoogleLoginContinue(mGoogleSignInAccount);
				}catch(Exception e){
					SketchwareUtil.showMessage(getApplicationContext(), e.getMessage());
				}
			}
			else {
				_LoadingDialog(false);
			}
			break;
			default:
			break;
		}
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
	
	
	public void _viewGraphics(final View _view, final int _onFocus, final int _onRipple, final double _radius, final double _stroke, final int _strokeColor) {
		android.graphics.drawable.GradientDrawable GG = new android.graphics.drawable.GradientDrawable();
		GG.setColor(_onFocus);
		GG.setCornerRadius((float)_radius);
		GG.setStroke((int) _stroke, _strokeColor);
		android.graphics.drawable.RippleDrawable RE = new android.graphics.drawable.RippleDrawable(new android.content.res.ColorStateList(new int[][]{new int[]{}}, new int[]{ _onRipple}), GG, null);
		_view.setBackground(RE);
	}
	
	
	public void _GoogleLoginContinue(final GoogleSignInAccount _account) {
		_LoadingDialog(true);
		AuthCredential mGoogleLoginCredential = GoogleAuthProvider.getCredential(_account.getIdToken(), null);
		auth.signInWithCredential(mGoogleLoginCredential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
				@Override
				public void onComplete(@NonNull Task<AuthResult> task) {
						if (task.isSuccessful()) {
								FirebaseUser = auth.getCurrentUser();
								if (task.getResult().getAdditionalUserInfo().isNewUser()) {
										_createRandomUsernameRepeat();
								} else {
						                _LoadingDialog(false);
										intent.setClass(getApplicationContext(), HomeActivity.class);
										startActivity(intent);
										finish();
								}
						} else {
								SketchwareUtil.showMessage(getApplicationContext(), "Something went wrong");
					            _LoadingDialog(false);
						}
				}
		});
	}
	
	
	public void _createRandomUsernameRepeat() {
		num2 = SketchwareUtil.getRandom((int)(1111111111), (int)(2111111111));
		RandomUsername = "user".concat(String.valueOf((long)(num2)));
		DatabaseReference createRandomUsernameRef = FirebaseDatabase.getInstance().getReference().child("skyline/users");
		Query createRandomUsernameQuery = createRandomUsernameRef.orderByChild("username").equalTo(RandomUsername);
		createRandomUsernameQuery.addListenerForSingleValueEvent(new ValueEventListener() {
			@Override
			public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
				if(dataSnapshot.exists()) {
					_createRandomUsernameRepeat();
				} else {
					_LoadingDialog(false);
					intent.setClass(getApplicationContext(), CompleteProfileActivity.class);
					intent.putExtra("findedUsername", RandomUsername);
					intent.putExtra("googleLoginName", FirebaseUser.getDisplayName());
					intent.putExtra("googleLoginEmail", FirebaseUser.getEmail());
					intent.putExtra("googleLoginAvatarUri", FirebaseUser.getPhotoUrl().toString());
					startActivity(intent);
					finish();
				}
			}
			 
			@Override
			public void onCancelled(@NonNull DatabaseError databaseError) {
						
			}
		});
	}
	
	
	public void _transparentStatus() {
		if (Build.VERSION.SDK_INT >= 19) {
				getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
		}
		if (Build.VERSION.SDK_INT >= 21) {
				setWindowFlag(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION, false);
				getWindow().setStatusBarColor(Color.TRANSPARENT);
		}
		
	}
	private void setWindowFlag(final int bits, boolean on) {
		    Window win = getWindow();
		    WindowManager.LayoutParams winParams = win.getAttributes();
		    if (on) {
			        winParams.flags |= bits;
			    } else {
			        winParams.flags &= ~bits;
			    }
		    win.setAttributes(winParams);
	}
	{
	}
	
	
	public void _LoadingDialog(final boolean _visibility) {
		if (_visibility) {
			if (SynapseLoadingDialog== null){
					SynapseLoadingDialog = new ProgressDialog(this);
					SynapseLoadingDialog.setCancelable(false);
					SynapseLoadingDialog.setCanceledOnTouchOutside(false);
					
					SynapseLoadingDialog.requestWindowFeature(Window.FEATURE_NO_TITLE); 
					SynapseLoadingDialog.getWindow().setBackgroundDrawable(new android.graphics.drawable.ColorDrawable(Color.TRANSPARENT));
					
			}
			SynapseLoadingDialog.show();
			SynapseLoadingDialog.setContentView(R.layout.loading_synapse);
			
			LinearLayout loading_bar_layout = (LinearLayout)SynapseLoadingDialog.findViewById(R.id.loading_bar_layout);
			
			
			//loading_bar_layout.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b) { this.setCornerRadius(a); this.setColor(b); return this; } }.getIns((int)100, 0xFFFFFFFF));
		} else {
			if (SynapseLoadingDialog != null){
				SynapseLoadingDialog.dismiss();
			}
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
		random_bg = String.valueOf((long)(SketchwareUtil.getRandom((int)(1), (int)(9))));
		if (random_bg.equals("1")) {
			body.setBackgroundResource(R.drawable.bg_gradient_1);
		} else {
			if (random_bg.equals("2")) {
				body.setBackgroundResource(R.drawable.bg_gradient_2);
			} else {
				if (random_bg.equals("3")) {
					body.setBackgroundResource(R.drawable.bg_gradient_4);
				} else {
					if (random_bg.equals("4")) {
						body.setBackgroundResource(R.drawable.bg_gradient_5);
					} else {
						if (random_bg.equals("5")) {
							body.setBackgroundResource(R.drawable.bg_gradient_7);
						} else {
							if (random_bg.equals("6")) {
								body.setBackgroundResource(R.drawable.bg_gradient_8);
							} else {
								if (random_bg.equals("7")) {
									body.setBackgroundResource(R.drawable.bg_gradient_7);
								} else {
									if (random_bg.equals("8")) {
										body.setBackgroundResource(R.drawable.bg_gradient_13);
									} else {
										if (random_bg.equals("9")) {
											body.setBackgroundResource(R.drawable.bg_gradient_13);
										} else {
											
										}
									}
								}
							}
						}
					}
				}
			}
		}
		sign_google_button_title.setTextColor(0xFF000000);
		github_btn_txt.setTextColor(0xFF000000);
		_viewGraphics(button2, 0xFFFFFFFF, 0xFFFFFFFF, 18, 0, 0xFFFFFFFF);
		_viewGraphics(button1, 0xFFFFFFFF, 0xFFFFFFFF, 100, 0, 0xFFFFFFFF);
		_viewGraphics(loginWithMail, 0xFFFFFFFF, 0xFFFFFFFF, 100, 0, 0xFFFFFFFF);
	}
	
	
	public void _dark_theme_settings() {
		random_bg = String.valueOf((long)(SketchwareUtil.getRandom((int)(1), (int)(4))));
		if (random_bg.equals("1")) {
			body.setBackgroundResource(R.drawable.bg_gradient_6);
		} else {
			if (random_bg.equals("2")) {
				body.setBackgroundResource(R.drawable.bg_gradient_9);
			} else {
				if (random_bg.equals("3")) {
					body.setBackgroundResource(R.drawable.bg_gradient_12);
				} else {
					if (random_bg.equals("4")) {
						body.setBackgroundResource(R.drawable.bg_gradient_14);
					} else {
						if (random_bg.equals("5")) {
							body.setBackgroundResource(R.drawable.bg_gradient_7);
						} else {
							if (random_bg.equals("6")) {
								body.setBackgroundResource(R.drawable.bg_gradient_8);
							} else {
								if (random_bg.equals("7")) {
									body.setBackgroundResource(R.drawable.bg_gradient_10);
								} else {
									if (random_bg.equals("8")) {
										body.setBackgroundResource(R.drawable.bg_gradient_11);
									} else {
										if (random_bg.equals("9")) {
											body.setBackgroundResource(R.drawable.bg_gradient_13);
										} else {
											
										}
									}
								}
							}
						}
					}
				}
			}
		}
		_viewGraphics(button1, 0xFF212121, 0xFF212121, 100, 0, 0xFFFFFFFF);
		sign_google_button_title.setTextColor(0xFFFFFFFF);
	}
	
	
	public void _amoled_theme_settings() {
		
	}
	
	
	public void _night_theme_settings() {
		
	}
	
	
	public void _GoogleBtnClicked() {
		Intent mGoogleLoginIntent = LOGIN_GOOGLE.getSignInIntent();
		startActivityForResult(mGoogleLoginIntent, REQ_CD_LOGIN_GOOGLE);
		vbr.vibrate((long)(48));
	}
	
	
	public void _LoginWithDeviceUniqueness() {
		hash = "hash.".concat(Build.ID).concat(Build.VERSION.SDK);
		hashauth.signInWithEmailAndPassword(hash.concat(" @sai.com"), hash).addOnCompleteListener(OnboardActivity.this, _hashauth_sign_in_listener);
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