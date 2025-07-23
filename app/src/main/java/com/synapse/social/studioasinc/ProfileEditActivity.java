package com.synapse.social.studioasinc;

import android.Manifest;
import android.animation.*;
import android.app.*;
import android.content.*;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
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
import android.text.*;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.style.*;
import android.util.*;
import android.view.*;
import android.view.View;
import android.view.View.*;
import android.view.animation.*;
import android.webkit.*;
import android.widget.*;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import androidx.annotation.*;
import androidx.appcompat.app.AppCompatActivity;
import androidx.browser.*;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import com.bumptech.glide.*;
import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.*;
import com.google.android.material.color.MaterialColors;
import com.google.firebase.FirebaseApp;
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
import com.synapse.social.studioasinc.FadeEditText;
import com.theartofdev.edmodo.cropper.*;
import com.yalantis.ucrop.*;
import java.io.*;
import java.io.InputStream;
import java.text.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Calendar;
import java.util.HashMap;
import java.util.regex.*;
import org.json.*;
import androidx.appcompat.widget.SwitchCompat;
import com.google.firebase.database.Query;
import java.net.URL;
import java.net.MalformedURLException;
import com.synapse.social.studioasinc.ImageUploader;

public class ProfileEditActivity extends AppCompatActivity {
	
	public final int REQ_CD_FP = 101;
	public final int REQ_CD_FPCOVER = 102;
	
	private FirebaseDatabase _firebase = FirebaseDatabase.getInstance();
	
	private ProgressDialog SynapseLoadingDialog;
	private HashMap<String, Object> ProfileEditSendMap = new HashMap<>();
	private String UserLastProfileUri = "";
	private String UserLastCoverUri = "";
	private boolean userNameErr = false;
	private boolean nickNameErr = false;
	private boolean biographyErr = false;
	private String NewAvatarUri = "";
	private String NewCoverUri = "";
	private String CurrentUsername = "";
	private HashMap<String, Object> map = new HashMap<>();
	private String path = "";
	private String IMG_BB_API_KEY = "";
	private HashMap<String, Object> mAddProfilePhotoMap = new HashMap<>();
	
	private LinearLayout body;
	private LinearLayout top;
	private ScrollView mScroll;
	private LinearLayout mLoadingBody;
	private ImageView mBack;
	private TextView mTitle;
	private ImageView mSave;
	private LinearLayout mScrollBody;
	private CardView profileRelativeCard;
	private FadeEditText mUsernameInput;
	private FadeEditText mNicknameInput;
	private FadeEditText mBiographyInput;
	private LinearLayout gender;
	private LinearLayout region;
	private LinearLayout profile_image_history_stage;
	private LinearLayout cover_image_history_stage;
	private RelativeLayout stage1Relative;
	private ImageView profileCoverImage;
	private LinearLayout stage1RelativeUp;
	private CardView stage1RelativeUpProfileCard;
	private ImageView stage1RelativeUpProfileImage;
	private TextView gender_title;
	private TextView gender_subtitle;
	private LinearLayout gender_male;
	private LinearLayout gender_spc1;
	private LinearLayout gender_female;
	private LinearLayout gender_spc2;
	private LinearLayout gender_gone;
	private ImageView gender_male_ic;
	private TextView gender_male_title;
	private ImageView gender_male_checkbox;
	private ImageView gender_female_ic;
	private TextView gender_female_title;
	private ImageView gender_female_checkbox;
	private ImageView gender_gone_ic;
	private TextView gender_gone_title;
	private ImageView gender_gone_checkbox;
	private LinearLayout region_top;
	private TextView region_subtitle;
	private TextView region_title;
	private ImageView region_arrow;
	private LinearLayout profile_image_history_stage_top;
	private TextView profile_image_history_stage_subtext;
	private TextView profile_image_history_stage_title;
	private ImageView profile_image_history_stage_arrow;
	private LinearLayout cover_image_history_stage_top;
	private TextView cover_image_history_stage_subtext;
	private TextView cover_image_history_stage_title;
	private ImageView cover_image_history_stage_arrow;
	private ProgressBar mLoadingBar;
	
	private Intent intent = new Intent();
	private Vibrator vbr;
	private DatabaseReference main = _firebase.getReference("skyline");
	private ChildEventListener _main_child_listener;
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
	private DatabaseReference pushusername = _firebase.getReference("synapse/username");
	private ChildEventListener _pushusername_child_listener;
	private Intent fp = new Intent(Intent.ACTION_GET_CONTENT);
	private Intent fpcover = new Intent(Intent.ACTION_GET_CONTENT);
	private DatabaseReference maindb = _firebase.getReference("/");
	private ChildEventListener _maindb_child_listener;
	private Calendar cc = Calendar.getInstance();
	
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.profile_edit);
		initialize(_savedInstanceState);
		FirebaseApp.initializeApp(this);
		
		if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
			ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.READ_EXTERNAL_STORAGE}, 1000);
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
		top = findViewById(R.id.top);
		mScroll = findViewById(R.id.mScroll);
		mLoadingBody = findViewById(R.id.mLoadingBody);
		mBack = findViewById(R.id.mBack);
		mTitle = findViewById(R.id.mTitle);
		mSave = findViewById(R.id.mSave);
		mScrollBody = findViewById(R.id.mScrollBody);
		profileRelativeCard = findViewById(R.id.profileRelativeCard);
		mUsernameInput = findViewById(R.id.mUsernameInput);
		mNicknameInput = findViewById(R.id.mNicknameInput);
		mBiographyInput = findViewById(R.id.mBiographyInput);
		gender = findViewById(R.id.gender);
		region = findViewById(R.id.region);
		profile_image_history_stage = findViewById(R.id.profile_image_history_stage);
		cover_image_history_stage = findViewById(R.id.cover_image_history_stage);
		stage1Relative = findViewById(R.id.stage1Relative);
		profileCoverImage = findViewById(R.id.profileCoverImage);
		stage1RelativeUp = findViewById(R.id.stage1RelativeUp);
		stage1RelativeUpProfileCard = findViewById(R.id.stage1RelativeUpProfileCard);
		stage1RelativeUpProfileImage = findViewById(R.id.stage1RelativeUpProfileImage);
		gender_title = findViewById(R.id.gender_title);
		gender_subtitle = findViewById(R.id.gender_subtitle);
		gender_male = findViewById(R.id.gender_male);
		gender_spc1 = findViewById(R.id.gender_spc1);
		gender_female = findViewById(R.id.gender_female);
		gender_spc2 = findViewById(R.id.gender_spc2);
		gender_gone = findViewById(R.id.gender_gone);
		gender_male_ic = findViewById(R.id.gender_male_ic);
		gender_male_title = findViewById(R.id.gender_male_title);
		gender_male_checkbox = findViewById(R.id.gender_male_checkbox);
		gender_female_ic = findViewById(R.id.gender_female_ic);
		gender_female_title = findViewById(R.id.gender_female_title);
		gender_female_checkbox = findViewById(R.id.gender_female_checkbox);
		gender_gone_ic = findViewById(R.id.gender_gone_ic);
		gender_gone_title = findViewById(R.id.gender_gone_title);
		gender_gone_checkbox = findViewById(R.id.gender_gone_checkbox);
		region_top = findViewById(R.id.region_top);
		region_subtitle = findViewById(R.id.region_subtitle);
		region_title = findViewById(R.id.region_title);
		region_arrow = findViewById(R.id.region_arrow);
		profile_image_history_stage_top = findViewById(R.id.profile_image_history_stage_top);
		profile_image_history_stage_subtext = findViewById(R.id.profile_image_history_stage_subtext);
		profile_image_history_stage_title = findViewById(R.id.profile_image_history_stage_title);
		profile_image_history_stage_arrow = findViewById(R.id.profile_image_history_stage_arrow);
		cover_image_history_stage_top = findViewById(R.id.cover_image_history_stage_top);
		cover_image_history_stage_subtext = findViewById(R.id.cover_image_history_stage_subtext);
		cover_image_history_stage_title = findViewById(R.id.cover_image_history_stage_title);
		cover_image_history_stage_arrow = findViewById(R.id.cover_image_history_stage_arrow);
		mLoadingBar = findViewById(R.id.mLoadingBar);
		vbr = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
		auth = FirebaseAuth.getInstance();
		fp.setType("image/*");
		fp.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
		fpcover.setType("image/*");
		fpcover.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
		
		mBack.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				onBackPressed();
			}
		});
		
		mSave.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				if (!mUsernameInput.getText().toString().trim().equals("")) {
					if (!(userNameErr || (nickNameErr || biographyErr))) {
						ProfileEditSendMap = new HashMap<>();
						ProfileEditSendMap.put("uid", FirebaseAuth.getInstance().getCurrentUser().getUid());
						ProfileEditSendMap.put("email", FirebaseAuth.getInstance().getCurrentUser().getEmail());
						ProfileEditSendMap.put("avatar", UserLastProfileUri);
						ProfileEditSendMap.put("profile_cover_image", UserLastCoverUri);
						ProfileEditSendMap.put("username", mUsernameInput.getText().toString().trim());
						if (mNicknameInput.getText().toString().trim().equals("")) {
							ProfileEditSendMap.put("nickname", "null");
						} else {
							ProfileEditSendMap.put("nickname", mNicknameInput.getText().toString().trim());
						}
						if (mBiographyInput.getText().toString().trim().equals("")) {
							ProfileEditSendMap.put("biography", "null");
						} else {
							ProfileEditSendMap.put("biography", mBiographyInput.getText().toString().trim());
						}
						if (gender_male_checkbox.isEnabled()) {
							ProfileEditSendMap.put("gender", "male");
						} else {
							if (gender_female_checkbox.isEnabled()) {
								ProfileEditSendMap.put("gender", "female");
							} else {
								if (gender_gone_checkbox.isEnabled()) {
									ProfileEditSendMap.put("gender", "hidden");
								}
							}
						}
						main.child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).updateChildren(ProfileEditSendMap);
						FirebaseDatabase.getInstance().getReference("synapse/username")
						.child(CurrentUsername)  // Use the CurrentUsername variable directly
						.removeValue();
						SketchwareUtil.showMessage(getApplicationContext(), getResources().getString(R.string.changes_saved));
						map = new HashMap<>();
						map.put("uid", FirebaseAuth.getInstance().getCurrentUser().getUid());
						map.put("email", FirebaseAuth.getInstance().getCurrentUser().getEmail());
						map.put("username", mUsernameInput.getText().toString());
						pushusername.child(mUsernameInput.getText().toString()).updateChildren(map);
						map.clear();
					}
				} else {
					SketchwareUtil.showMessage(getApplicationContext(), getResources().getString(R.string.username_err_invalid));
				}
				vbr.vibrate((long)(48));
			}
		});
		
		mUsernameInput.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence _param1, int _param2, int _param3, int _param4) {
				final String _charSeq = _param1.toString();
				if (_charSeq.trim().equals("")) {
					mUsernameInput.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b, int c, int d) { this.setCornerRadius(a); this.setStroke(b, c); this.setColor(d); return this; } }.getIns((int)28, (int)3, 0xFFF44336, 0xFFFFFFFF));
					((EditText)mUsernameInput).setError(getResources().getString(R.string.enter_username));
					userNameErr = true;
				} else {
					if (_charSeq.matches("[a-z0-9_.]+")) {
						if (_charSeq.contains("q") || (_charSeq.contains("w") || (_charSeq.contains("e") || (_charSeq.contains("r") || (_charSeq.contains("t") || (_charSeq.contains("y") || (_charSeq.contains("u") || (_charSeq.contains("i") || (_charSeq.contains("o") || (_charSeq.contains("p") || (_charSeq.contains("a") || (_charSeq.contains("s") || (_charSeq.contains("d") || (_charSeq.contains("f") || (_charSeq.contains("g") || (_charSeq.contains("h") || (_charSeq.contains("j") || (_charSeq.contains("k") || (_charSeq.contains("l") || (_charSeq.contains("z") || (_charSeq.contains("x") || (_charSeq.contains("c") || (_charSeq.contains("v") || (_charSeq.contains("b") || (_charSeq.contains("n") || _charSeq.contains("m")))))))))))))))))))))))))) {
							if (mUsernameInput.getText().toString().length() < 3) {
								mUsernameInput.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b, int c, int d) { this.setCornerRadius(a); this.setStroke(b, c); this.setColor(d); return this; } }.getIns((int)28, (int)3, 0xFFF44336, 0xFFFFFFFF));
								((EditText)mUsernameInput).setError(getResources().getString(R.string.username_err_3_characters));
								userNameErr = true;
							} else {
								if (mUsernameInput.getText().toString().length() > 25) {
									mUsernameInput.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b, int c, int d) { this.setCornerRadius(a); this.setStroke(b, c); this.setColor(d); return this; } }.getIns((int)28, (int)3, 0xFFF44336, 0xFFFFFFFF));
									((EditText)mUsernameInput).setError(getResources().getString(R.string.username_err_25_characters));
									userNameErr = true;
								} else {
									mUsernameInput.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b, int c, int d) { this.setCornerRadius(a); this.setStroke(b, c); this.setColor(d); return this; } }.getIns((int)28, (int)3, 0xFFEEEEEE, 0xFFFFFFFF));
									DatabaseReference checkUsernameRef = FirebaseDatabase.getInstance().getReference().child("skyline/users");
									
									Query checkUsernameQuery = checkUsernameRef.orderByChild("username").equalTo(_charSeq.trim());
									checkUsernameQuery.addListenerForSingleValueEvent(new ValueEventListener() {
										@Override
										public void onDataChange(DataSnapshot dataSnapshot) {
											if (dataSnapshot.exists()) {
												for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
													String uid = childSnapshot.child("uid").getValue(String.class);
													if (uid != null && uid.equals(FirebaseAuth.getInstance().getCurrentUser().getUid())) {
														mUsernameInput.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b, int c, int d) { this.setCornerRadius(a); this.setStroke(b, c); this.setColor(d); return this; } }.getIns((int)28, (int)3, 0xFFEEEEEE, 0xFFFFFFFF));
														userNameErr = false;
													} else {
														mUsernameInput.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b, int c, int d) { this.setCornerRadius(a); this.setStroke(b, c); this.setColor(d); return this; } }.getIns((int)28, (int)1, 0xFFF44336, 0xFFFFFFFF));
														((EditText) mUsernameInput).setError(getResources().getString(R.string.username_err_already_taken));
														userNameErr = true;
													}
												}
											} else {
												mUsernameInput.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b, int c, int d) { this.setCornerRadius(a); this.setStroke(b, c); this.setColor(d); return this; } }.getIns((int)28, (int)3, 0xFFEEEEEE, 0xFFFFFFFF));
												userNameErr = false;
											}
										}
										
										@Override
										public void onCancelled(DatabaseError databaseError) {
											
										}
									});
									
								}
							}
						} else {
							mUsernameInput.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b, int c, int d) { this.setCornerRadius(a); this.setStroke(b, c); this.setColor(d); return this; } }.getIns((int)28, (int)3, 0xFFF44336, 0xFFFFFFFF));
							((EditText)mUsernameInput).setError(getResources().getString(R.string.username_err_one_letter));
							userNameErr = true;
						}
					} else {
						mUsernameInput.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b, int c, int d) { this.setCornerRadius(a); this.setStroke(b, c); this.setColor(d); return this; } }.getIns((int)28, (int)3, 0xFFF44336, 0xFFFFFFFF));
						((EditText)mUsernameInput).setError(getResources().getString(R.string.username_err_invalid_characters));
						userNameErr = true;
					}
				}
			}
			
			@Override
			public void beforeTextChanged(CharSequence _param1, int _param2, int _param3, int _param4) {
				
			}
			
			@Override
			public void afterTextChanged(Editable _param1) {
				
			}
		});
		
		mNicknameInput.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence _param1, int _param2, int _param3, int _param4) {
				final String _charSeq = _param1.toString();
				if (_charSeq.length() > 30) {
					mNicknameInput.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b, int c, int d) { this.setCornerRadius(a); this.setStroke(b, c); this.setColor(d); return this; } }.getIns((int)28, (int)3, 0xFFF44336, 0xFFFFFFFF));
					((EditText)mNicknameInput).setError(getResources().getString(R.string.nickname_err_30_characters));
					nickNameErr = true;
				} else {
					mNicknameInput.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b, int c, int d) { this.setCornerRadius(a); this.setStroke(b, c); this.setColor(d); return this; } }.getIns((int)28, (int)3, 0xFFEEEEEE, 0xFFFFFFFF));
					nickNameErr = false;
				}
			}
			
			@Override
			public void beforeTextChanged(CharSequence _param1, int _param2, int _param3, int _param4) {
				
			}
			
			@Override
			public void afterTextChanged(Editable _param1) {
				
			}
		});
		
		mBiographyInput.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence _param1, int _param2, int _param3, int _param4) {
				final String _charSeq = _param1.toString();
				if (_charSeq.length() > 250) {
					mBiographyInput.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b, int c, int d) { this.setCornerRadius(a); this.setStroke(b, c); this.setColor(d); return this; } }.getIns((int)28, (int)3, 0xFFF44336, 0xFFFFFFFF));
					((EditText)mBiographyInput).setError(getResources().getString(R.string.biography_err_250_characters));
					biographyErr = true;
				} else {
					mBiographyInput.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b, int c, int d) { this.setCornerRadius(a); this.setStroke(b, c); this.setColor(d); return this; } }.getIns((int)28, (int)3, 0xFFEEEEEE, 0xFFFFFFFF));
					biographyErr = false;
				}
			}
			
			@Override
			public void beforeTextChanged(CharSequence _param1, int _param2, int _param3, int _param4) {
				
			}
			
			@Override
			public void afterTextChanged(Editable _param1) {
				
			}
		});
		
		region.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				intent.setClass(getApplicationContext(), SelectRegionActivity.class);
				startActivity(intent);
			}
		});
		
		profile_image_history_stage.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				intent.setClass(getApplicationContext(), ProfilePhotoHistoryActivity.class);
				startActivity(intent);
			}
		});
		
		cover_image_history_stage.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				intent.setClass(getApplicationContext(), ProfileCoverPhotoHistoryActivity.class);
				startActivity(intent);
			}
		});
		
		gender_male.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				gender_male_checkbox.setImageResource(R.drawable.checkbox_checked);
				gender_female_checkbox.setImageResource(R.drawable.checkbox_not_checked);
				gender_gone_checkbox.setImageResource(R.drawable.checkbox_not_checked);
				gender_gone_checkbox.setEnabled(false);
				gender_male_checkbox.setEnabled(true);
				gender_female_checkbox.setEnabled(false);
				vbr.vibrate((long)(48));
			}
		});
		
		gender_female.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				gender_male_checkbox.setImageResource(R.drawable.checkbox_not_checked);
				gender_female_checkbox.setImageResource(R.drawable.checkbox_checked);
				gender_gone_checkbox.setImageResource(R.drawable.checkbox_not_checked);
				gender_gone_checkbox.setEnabled(false);
				gender_male_checkbox.setEnabled(false);
				gender_female_checkbox.setEnabled(true);
				vbr.vibrate((long)(48));
			}
		});
		
		gender_gone.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				gender_male_checkbox.setImageResource(R.drawable.checkbox_not_checked);
				gender_female_checkbox.setImageResource(R.drawable.checkbox_not_checked);
				gender_gone_checkbox.setImageResource(R.drawable.checkbox_checked);
				gender_gone_checkbox.setEnabled(true);
				gender_male_checkbox.setEnabled(false);
				gender_female_checkbox.setEnabled(false);
				vbr.vibrate((long)(48));
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
		
		_pushusername_child_listener = new ChildEventListener() {
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
		pushusername.addChildEventListener(_pushusername_child_listener);
		
		_maindb_child_listener = new ChildEventListener() {
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
		maindb.addChildEventListener(_maindb_child_listener);
		
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
		// UI related codings
		_stateColor(0xFFFFFFFF, 0xFFF5F5F5);
		_ImageColor(gender_male_ic, 0xFF2196F3);
		_ImageColor(gender_female_ic, 0xFFE91E63);
		_viewGraphics(region, 0xFFFFFFFF, 0xFFEEEEEE, 28, 3, 0xFFEEEEEE);
		_viewGraphics(profile_image_history_stage, 0xFFFFFFFF, 0xFFEEEEEE, 28, 3, 0xFFEEEEEE);
		_viewGraphics(cover_image_history_stage, 0xFFFFFFFF, 0xFFEEEEEE, 28, 3, 0xFFEEEEEE);
		top.setElevation((float)4);
		profileRelativeCard.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b) { this.setCornerRadius(a); this.setColor(b); return this; } }.getIns((int)28, Color.TRANSPARENT));
		stage1RelativeUpProfileCard.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b) { this.setCornerRadius(a); this.setColor(b); return this; } }.getIns((int)300, Color.TRANSPARENT));
		mUsernameInput.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b, int c, int d) { this.setCornerRadius(a); this.setStroke(b, c); this.setColor(d); return this; } }.getIns((int)28, (int)3, 0xFFEEEEEE, 0xFFFFFFFF));
		mNicknameInput.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b, int c, int d) { this.setCornerRadius(a); this.setStroke(b, c); this.setColor(d); return this; } }.getIns((int)28, (int)3, 0xFFEEEEEE, 0xFFFFFFFF));
		mBiographyInput.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b, int c, int d) { this.setCornerRadius(a); this.setStroke(b, c); this.setColor(d); return this; } }.getIns((int)28, (int)3, 0xFFEEEEEE, 0xFFFFFFFF));
		gender.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b, int c, int d) { this.setCornerRadius(a); this.setStroke(b, c); this.setColor(d); return this; } }.getIns((int)28, (int)3, 0xFFEEEEEE, 0xFFFFFFFF));
		mTitle.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/product_b.ttf"), 1);
		gender_title.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/product_b.ttf"), 1);
		region_title.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/product_b.ttf"), 1);
		cover_image_history_stage_title.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/product_b.ttf"), 1);
		profile_image_history_stage_title.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/product_b.ttf"), 1);
		// Logic coding
		stage1RelativeUpProfileCard.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				startActivityForResult(fp, REQ_CD_FP);
			}
		});
		profileCoverImage.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				startActivityForResult(fpcover, REQ_CD_FPCOVER);
			}
		});
	}
	
	@Override
	protected void onActivityResult(int _requestCode, int _resultCode, Intent _data) {
		super.onActivityResult(_requestCode, _resultCode, _data);
		
		switch (_requestCode) {
			case REQ_CD_FP:
			if (_resultCode == Activity.RESULT_OK) {
				ArrayList<String> _filePath = new ArrayList<>();
				if (_data != null) {
					if (_data.getClipData() != null) {
						for (int _index = 0; _index < _data.getClipData().getItemCount(); _index++) {
							ClipData.Item _item = _data.getClipData().getItemAt(_index);
							_filePath.add(FileUtil.convertUriToFilePath(getApplicationContext(), _item.getUri()));
						}
					}
					else {
						_filePath.add(FileUtil.convertUriToFilePath(getApplicationContext(), _data.getData()));
					}
				}
				_LoadingDialog(true);
				stage1RelativeUpProfileImage.setImageBitmap(FileUtil.decodeSampleBitmapFromPath(_filePath.get((int)(0)), 1024, 1024));
				path = _filePath.get((int)(0));
				ImageUploader.uploadImage(path, new ImageUploader.UploadCallback() {
					@Override
					public void onUploadComplete(String imageUrl) {
						ProfileEditSendMap = new HashMap<>();
						ProfileEditSendMap.put("avatar", imageUrl);
						ProfileEditSendMap.put("avatar_history_type", "local");
						main.child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).updateChildren(ProfileEditSendMap, new DatabaseReference.CompletionListener() {
							@Override
							public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
								if (databaseError == null) {
									_LoadingDialog(false);
								} else {
									SketchwareUtil.showMessage(getApplicationContext(), databaseError.getMessage());
									//	username_input.setEnabled(true);
									_LoadingDialog(false);
								}
							}
						});
						try{
							String ProfileHistoryKey = maindb.push().getKey();
							mAddProfilePhotoMap = new HashMap<>();
							mAddProfilePhotoMap.put("key", ProfileHistoryKey);
							mAddProfilePhotoMap.put("image_url", imageUrl.trim());
							mAddProfilePhotoMap.put("upload_date", String.valueOf((long)(cc.getTimeInMillis())));
							mAddProfilePhotoMap.put("type", "url");
							maindb.child("skyline/profile-history/".concat(FirebaseAuth.getInstance().getCurrentUser().getUid().concat("/".concat(ProfileHistoryKey)))).updateChildren(mAddProfilePhotoMap);
						}catch(Exception e){
							
						}
					}
					
					@Override
					public void onUploadError(String errorMessage) {
						
						
						
						SketchwareUtil.showMessage(getApplicationContext(), "Falied to upload the image.");
						_LoadingDialog(false);
					}
				});
				
			}
			else {
				
			}
			break;
			
			case REQ_CD_FPCOVER:
			if (_resultCode == Activity.RESULT_OK) {
				ArrayList<String> _filePath = new ArrayList<>();
				if (_data != null) {
					if (_data.getClipData() != null) {
						for (int _index = 0; _index < _data.getClipData().getItemCount(); _index++) {
							ClipData.Item _item = _data.getClipData().getItemAt(_index);
							_filePath.add(FileUtil.convertUriToFilePath(getApplicationContext(), _item.getUri()));
						}
					}
					else {
						_filePath.add(FileUtil.convertUriToFilePath(getApplicationContext(), _data.getData()));
					}
				}
				_LoadingDialog(true);
				profileCoverImage.setImageBitmap(FileUtil.decodeSampleBitmapFromPath(_filePath.get((int)(0)), 1024, 1024));
				path = _filePath.get((int)(0));
				ImageUploader.uploadImage(path, new ImageUploader.UploadCallback() {
					@Override
					public void onUploadComplete(String imageUrl) {
						ProfileEditSendMap = new HashMap<>();
						ProfileEditSendMap.put("profile_cover_image", imageUrl);
						main.child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).updateChildren(ProfileEditSendMap, new DatabaseReference.CompletionListener() {
							@Override
							public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
								if (databaseError == null) {
									_LoadingDialog(false);
								} else {
									SketchwareUtil.showMessage(getApplicationContext(), databaseError.getMessage());
									//	username_input.setEnabled(true);
									_LoadingDialog(false);
								}
							}
						});
						try{
							String ProfileHistoryKey = maindb.push().getKey();
							mAddProfilePhotoMap = new HashMap<>();
							mAddProfilePhotoMap.put("key", ProfileHistoryKey);
							mAddProfilePhotoMap.put("image_url", imageUrl.trim());
							mAddProfilePhotoMap.put("upload_date", String.valueOf((long)(cc.getTimeInMillis())));
							mAddProfilePhotoMap.put("type", "url");
							maindb.child("skyline/cover-image-history/".concat(FirebaseAuth.getInstance().getCurrentUser().getUid().concat("/".concat(ProfileHistoryKey)))).updateChildren(mAddProfilePhotoMap);
						}catch(Exception e){
							
						}
					}
					
					@Override
					public void onUploadError(String errorMessage) {
						
						
						
						SketchwareUtil.showMessage(getApplicationContext(), "Falied to upload the image.");
						_LoadingDialog(false);
					}
				});
				
			}
			else {
				
			}
			break;
			default:
			break;
		}
	}
	
	@Override
	public void onBackPressed() {
		finish();
	}
	
	
	@Override
	public void onResume() {
		super.onResume();
		_getUserReference();
	}
	public void _stateColor(final int _statusColor, final int _navigationColor) {
		getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
		getWindow().setStatusBarColor(_statusColor);
		getWindow().setNavigationBarColor(_navigationColor);
	}
	
	
	public void _ImageColor(final ImageView _image, final int _color) {
		_image.setColorFilter(_color,PorterDuff.Mode.SRC_ATOP);
	}
	
	
	public void _getUserReference() {
		mScroll.setVisibility(View.GONE);
		mLoadingBody.setVisibility(View.VISIBLE);
		DatabaseReference getUserReference = FirebaseDatabase.getInstance().getReference("skyline/users").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
		getUserReference.addListenerForSingleValueEvent(new ValueEventListener() {
			@Override
			public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
				if(dataSnapshot.exists()) {
					mScroll.setVisibility(View.VISIBLE);
					mLoadingBody.setVisibility(View.GONE);
					UserLastProfileUri = dataSnapshot.child("avatar").getValue(String.class);
					UserLastCoverUri = dataSnapshot.child("profile_cover_image").getValue(String.class);
					if (dataSnapshot.child("profile_cover_image").getValue(String.class).equals("null")) {
						profileCoverImage.setImageResource(R.drawable.user_null_cover_photo);
					} else {
						Glide.with(getApplicationContext()).load(Uri.parse(dataSnapshot.child("profile_cover_image").getValue(String.class))).into(profileCoverImage);
					}
					if (dataSnapshot.child("avatar").getValue(String.class).equals("null")) {
						stage1RelativeUpProfileImage.setImageResource(R.drawable.avatar);
					} else {
						Glide.with(getApplicationContext()).load(Uri.parse(dataSnapshot.child("avatar").getValue(String.class))).into(stage1RelativeUpProfileImage);
					}
					mUsernameInput.setText(dataSnapshot.child("username").getValue(String.class));
					CurrentUsername = dataSnapshot.child("username").getValue(String.class);
					if (dataSnapshot.child("nickname").getValue(String.class).equals("null")) {
						mNicknameInput.setText("");
					} else {
						mNicknameInput.setText(dataSnapshot.child("nickname").getValue(String.class));
					}
					if (dataSnapshot.child("biography").getValue(String.class).equals("null")) {
						mBiographyInput.setText("");
					} else {
						mBiographyInput.setText(dataSnapshot.child("biography").getValue(String.class));
					}
					if (dataSnapshot.child("gender").getValue(String.class).equals("hidden")) {
						gender_male_checkbox.setImageResource(R.drawable.checkbox_not_checked);
						gender_female_checkbox.setImageResource(R.drawable.checkbox_not_checked);
						gender_gone_checkbox.setImageResource(R.drawable.checkbox_checked);
						gender_gone_checkbox.setEnabled(true);
						gender_male_checkbox.setEnabled(false);
						gender_female_checkbox.setEnabled(false);
					} else {
						if (dataSnapshot.child("gender").getValue(String.class).equals("male")) {
							gender_male_checkbox.setImageResource(R.drawable.checkbox_checked);
							gender_female_checkbox.setImageResource(R.drawable.checkbox_not_checked);
							gender_gone_checkbox.setImageResource(R.drawable.checkbox_not_checked);
							gender_gone_checkbox.setEnabled(false);
							gender_male_checkbox.setEnabled(true);
							gender_female_checkbox.setEnabled(false);
						} else {
							if (dataSnapshot.child("gender").getValue(String.class).equals("female")) {
								gender_male_checkbox.setImageResource(R.drawable.checkbox_not_checked);
								gender_female_checkbox.setImageResource(R.drawable.checkbox_checked);
								gender_gone_checkbox.setImageResource(R.drawable.checkbox_not_checked);
								gender_gone_checkbox.setEnabled(false);
								gender_male_checkbox.setEnabled(false);
								gender_female_checkbox.setEnabled(true);
							}
						}
					}
				} else {
				}
			}
			
			@Override
			public void onCancelled(@NonNull DatabaseError databaseError) {
				
			}
		});
	}
	
	
	public void _viewGraphics(final View _view, final int _onFocus, final int _onRipple, final double _radius, final double _stroke, final int _strokeColor) {
		android.graphics.drawable.GradientDrawable GG = new android.graphics.drawable.GradientDrawable();
		GG.setColor(_onFocus);
		GG.setCornerRadius((int)_radius);
		GG.setStroke((int) _stroke, _strokeColor);
		android.graphics.drawable.RippleDrawable RE = new android.graphics.drawable.RippleDrawable(new android.content.res.ColorStateList(new int[][]{new int[]{}}, new int[]{ _onRipple}), GG, null);
		_view.setBackground(RE);
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
	
}