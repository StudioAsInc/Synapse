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
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
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
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.synapse.social.studioasinc.FadeEditText;
import com.theartofdev.edmodo.cropper.*;
import com.yalantis.ucrop.*;
import java.io.*;
import java.io.File;
import java.io.InputStream;
import java.text.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Calendar;
import java.util.HashMap;
import java.util.regex.*;
import org.json.*;
import com.google.firebase.database.Query;
import com.synapse.social.studioasinc.ImageUploader;

public class CompleteProfileActivity extends AppCompatActivity {
	
	public final int REQ_CD_SELECTAVATAR = 101;
	
	private FirebaseDatabase _firebase = FirebaseDatabase.getInstance();
	private FirebaseStorage _firebase_storage = FirebaseStorage.getInstance();
	
	private boolean userNameErr = false;
	private String avatarUri = "";
	private String avatarName = "";
	private String StorageDB = "";
	private HashMap<String, Object> createUserMap = new HashMap<>();
	private boolean emailVerify = false;
	private HashMap<String, Object> m = new HashMap<>();
	private HashMap<String, Object> m2 = new HashMap<>();
	private HashMap<String, Object> map = new HashMap<>();
	private String IMG_BB_API_KEY = "";
	private String path = "";
	private String imageUrl = "";
	private HashMap<String, Object> mDp = new HashMap<>();
	private String thedpurl = "";
	
	private ScrollView scroll;
	private LinearLayout body;
	private LinearLayout top;
	private TextView title;
	private TextView subtitle;
	private CardView profile_image_card;
	private FadeEditText username_input;
	private FadeEditText nickname_input;
	private FadeEditText biography_input;
	private LinearLayout email_verification;
	private LinearLayout buttons;
	private ImageView back;
	private LinearLayout topMiddle;
	private ImageView cancelCreateAccount;
	private ProgressBar cancel_create_account_progress;
	private ImageView profile_image;
	private TextView email_verification_title;
	private TextView email_verification_subtitle;
	private LinearLayout email_verification_middle;
	private TextView email_verification_send;
	private ImageView email_verification_error_ic;
	private ImageView email_verification_verified_ic;
	private TextView email_verification_status;
	private ImageView email_verification_status_refresh;
	private TextView skip_button;
	private LinearLayout complete_button;
	private TextView complete_button_title;
	private ProgressBar complete_button_loader_bar;
	
	private Vibrator vbr;
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
	private Intent intent = new Intent();
	private DatabaseReference main = _firebase.getReference("skyline");
	private ChildEventListener _main_child_listener;
	private StorageReference uploadAvatar = _firebase_storage.getReference("/");
	private OnCompleteListener<Uri> _uploadAvatar_upload_success_listener;
	private OnSuccessListener<FileDownloadTask.TaskSnapshot> _uploadAvatar_download_success_listener;
	private OnSuccessListener _uploadAvatar_delete_success_listener;
	private OnProgressListener _uploadAvatar_upload_progress_listener;
	private OnProgressListener _uploadAvatar_download_progress_listener;
	private OnFailureListener _uploadAvatar_failure_listener;
	private Calendar getJoinTime = Calendar.getInstance();
	private Intent SelectAvatar = new Intent(Intent.ACTION_GET_CONTENT);
	private DatabaseReference fdb = _firebase.getReference("notify");
	private ChildEventListener _fdb_child_listener;
	private DatabaseReference pushusername = _firebase.getReference("synapse/username");
	private ChildEventListener _pushusername_child_listener;
	
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.complete_profile);
		initialize(_savedInstanceState);
		FirebaseApp.initializeApp(this);
		
initializeLogic();
	}
	
	@Override
	public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
		super.onRequestPermissionsResult(requestCode, permissions, grantResults);
		if (requestCode == 1000) {
			initializeLogic();
		}
	}
	
	private void initialize(Bundle _savedInstanceState) {
		scroll = findViewById(R.id.scroll);
		body = findViewById(R.id.body);
		top = findViewById(R.id.top);
		title = findViewById(R.id.title);
		subtitle = findViewById(R.id.subtitle);
		profile_image_card = findViewById(R.id.profile_image_card);
		username_input = findViewById(R.id.username_input);
		nickname_input = findViewById(R.id.nickname_input);
		biography_input = findViewById(R.id.biography_input);
		email_verification = findViewById(R.id.email_verification);
		buttons = findViewById(R.id.buttons);
		back = findViewById(R.id.back);
		topMiddle = findViewById(R.id.topMiddle);
		cancelCreateAccount = findViewById(R.id.cancelCreateAccount);
		cancel_create_account_progress = findViewById(R.id.cancel_create_account_progress);
		profile_image = findViewById(R.id.profile_image);
		email_verification_title = findViewById(R.id.email_verification_title);
		email_verification_subtitle = findViewById(R.id.email_verification_subtitle);
		email_verification_middle = findViewById(R.id.email_verification_middle);
		email_verification_send = findViewById(R.id.email_verification_send);
		email_verification_error_ic = findViewById(R.id.email_verification_error_ic);
		email_verification_verified_ic = findViewById(R.id.email_verification_verified_ic);
		email_verification_status = findViewById(R.id.email_verification_status);
		email_verification_status_refresh = findViewById(R.id.email_verification_status_refresh);
		skip_button = findViewById(R.id.skip_button);
		complete_button = findViewById(R.id.complete_button);
		complete_button_title = findViewById(R.id.complete_button_title);
		complete_button_loader_bar = findViewById(R.id.complete_button_loader_bar);
		vbr = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
		auth = FirebaseAuth.getInstance();
		SelectAvatar.setType("image/*");
		SelectAvatar.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
		
		profile_image_card.setOnLongClickListener(new View.OnLongClickListener() {
			@Override
			public boolean onLongClick(View _view) {
				avatarUri = "null";
				profile_image.setImageResource(R.drawable.avatar);
				vbr.vibrate((long)(48));
				return true;
			}
		});
		
		profile_image_card.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				startActivityForResult(SelectAvatar, REQ_CD_SELECTAVATAR);
			}
		});
		
		username_input.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence _param1, int _param2, int _param3, int _param4) {
				final String _charSeq = _param1.toString();
				if (_charSeq.trim().equals("")) {
					username_input.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b, int c, int d) { this.setCornerRadius(a); this.setStroke(b, c); this.setColor(d); return this; } }.getIns((int)28, (int)3, 0xFFF44336, 0xFFFFFFFF));
					((EditText)username_input).setError(getResources().getString(R.string.enter_username));
					userNameErr = true;
				} else {
					if (_charSeq.matches("[a-z0-9_.]+")) {
						if (_charSeq.contains("q") || (_charSeq.contains("w") || (_charSeq.contains("e") || (_charSeq.contains("r") || (_charSeq.contains("t") || (_charSeq.contains("y") || (_charSeq.contains("u") || (_charSeq.contains("i") || (_charSeq.contains("o") || (_charSeq.contains("p") || (_charSeq.contains("a") || (_charSeq.contains("s") || (_charSeq.contains("d") || (_charSeq.contains("f") || (_charSeq.contains("g") || (_charSeq.contains("h") || (_charSeq.contains("j") || (_charSeq.contains("k") || (_charSeq.contains("l") || (_charSeq.contains("z") || (_charSeq.contains("x") || (_charSeq.contains("c") || (_charSeq.contains("v") || (_charSeq.contains("b") || (_charSeq.contains("n") || _charSeq.contains("m")))))))))))))))))))))))))) {
							if (username_input.getText().toString().length() < 3) {
								username_input.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b, int c, int d) { this.setCornerRadius(a); this.setStroke(b, c); this.setColor(d); return this; } }.getIns((int)28, (int)3, 0xFFF44336, 0xFFFFFFFF));
								((EditText)username_input).setError(getResources().getString(R.string.username_err_3_characters));
								userNameErr = true;
							} else {
								if (username_input.getText().toString().length() > 25) {
									username_input.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b, int c, int d) { this.setCornerRadius(a); this.setStroke(b, c); this.setColor(d); return this; } }.getIns((int)28, (int)3, 0xFFF44336, 0xFFFFFFFF));
									((EditText)username_input).setError(getResources().getString(R.string.username_err_25_characters));
									userNameErr = true;
								} else {
									username_input.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b, int c, int d) { this.setCornerRadius(a); this.setStroke(b, c); this.setColor(d); return this; } }.getIns((int)28, (int)3, 0xFFEEEEEE, 0xFFFFFFFF));
									DatabaseReference checkUsernameRef = FirebaseDatabase.getInstance().getReference().child("skyline/users");
									
									Query checkUsernameQuery = checkUsernameRef.orderByChild("username").equalTo(_charSeq.trim());
									checkUsernameQuery.addListenerForSingleValueEvent(new ValueEventListener() {
										@Override
										public void onDataChange(DataSnapshot dataSnapshot) {
											if (dataSnapshot.exists()) {
												username_input.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b, int c, int d) { this.setCornerRadius(a); this.setStroke(b, c); this.setColor(d); return this; } }.getIns((int)28, (int)3, 0xFFF44336, 0xFFEEEEEE));
												((EditText)username_input).setError(getResources().getString(R.string.username_err_already_taken));
												userNameErr = true;
											} else {
												username_input.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b, int c, int d) { this.setCornerRadius(a); this.setStroke(b, c); this.setColor(d); return this; } }.getIns((int)28, (int)3, 0xFFEEEEEE, 0xFFFFFFFF));
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
							username_input.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b, int c, int d) { this.setCornerRadius(a); this.setStroke(b, c); this.setColor(d); return this; } }.getIns((int)28, (int)3, 0xFFF44336, 0xFFFFFFFF));
							((EditText)username_input).setError(getResources().getString(R.string.username_err_one_letter));
							userNameErr = true;
						}
					} else {
						username_input.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b, int c, int d) { this.setCornerRadius(a); this.setStroke(b, c); this.setColor(d); return this; } }.getIns((int)28, (int)3, 0xFFF44336, 0xFFFFFFFF));
						((EditText)username_input).setError(getResources().getString(R.string.username_err_invalid_characters));
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
		
		nickname_input.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence _param1, int _param2, int _param3, int _param4) {
				final String _charSeq = _param1.toString();
				if (_charSeq.length() > 30) {
					nickname_input.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b, int c, int d) { this.setCornerRadius(a); this.setStroke(b, c); this.setColor(d); return this; } }.getIns((int)28, (int)3, 0xFFF44336, 0xFFFFFFFF));
					((EditText)nickname_input).setError(getResources().getString(R.string.nickname_err_30_characters));
				} else {
					nickname_input.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b, int c, int d) { this.setCornerRadius(a); this.setStroke(b, c); this.setColor(d); return this; } }.getIns((int)28, (int)3, 0xFFEEEEEE, 0xFFFFFFFF));
				}
			}
			
			@Override
			public void beforeTextChanged(CharSequence _param1, int _param2, int _param3, int _param4) {
				
			}
			
			@Override
			public void afterTextChanged(Editable _param1) {
				
			}
		});
		
		biography_input.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence _param1, int _param2, int _param3, int _param4) {
				final String _charSeq = _param1.toString();
				if (_charSeq.length() > 250) {
					biography_input.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b, int c, int d) { this.setCornerRadius(a); this.setStroke(b, c); this.setColor(d); return this; } }.getIns((int)28, (int)3, 0xFFF44336, 0xFFFFFFFF));
					((EditText)biography_input).setError(getResources().getString(R.string.biography_err_250_characters));
				} else {
					biography_input.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b, int c, int d) { this.setCornerRadius(a); this.setStroke(b, c); this.setColor(d); return this; } }.getIns((int)28, (int)3, 0xFFEEEEEE, 0xFFFFFFFF));
				}
			}
			
			@Override
			public void beforeTextChanged(CharSequence _param1, int _param2, int _param3, int _param4) {
				
			}
			
			@Override
			public void afterTextChanged(Editable _param1) {
				
			}
		});
		
		back.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				onBackPressed();
			}
		});
		
		cancelCreateAccount.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				{
					final AlertDialog NewCustomDialog = new AlertDialog.Builder(CompleteProfileActivity.this).create();
					LayoutInflater NewCustomDialogLI = getLayoutInflater();
					View NewCustomDialogCV = (View) NewCustomDialogLI.inflate(R.layout.synapse_dialog_bg_view, null);
					NewCustomDialog.setView(NewCustomDialogCV);
					NewCustomDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
					
					final TextView dialog_title = (TextView) NewCustomDialogCV.findViewById(R.id.dialog_title);
					final TextView dialog_message = (TextView) NewCustomDialogCV.findViewById(R.id.dialog_message);
					final TextView dialog_no_button = (TextView) NewCustomDialogCV.findViewById(R.id.dialog_no_button);
					final TextView dialog_yes_button = (TextView) NewCustomDialogCV.findViewById(R.id.dialog_yes_button);
					dialog_yes_button.setTextColor(0xFFF44336);
					_viewGraphics(dialog_yes_button, 0xFFFFFFFF, 0xFFFFCDD2, 28, 0, Color.TRANSPARENT);
					dialog_no_button.setTextColor(0xFF2196F3);
					_viewGraphics(dialog_no_button, 0xFFFFFFFF, 0xFFBBDEFB, 28, 0, Color.TRANSPARENT);
					dialog_title.setText(getResources().getString(R.string.info));
					dialog_message.setText(getResources().getString(R.string.cancel_create_account_warn).concat("\n\n".concat(getResources().getString(R.string.cancel_create_account_warn2))));
					dialog_yes_button.setText(getResources().getString(R.string.yes));
					dialog_no_button.setText(getResources().getString(R.string.no));
					dialog_yes_button.setOnClickListener(new View.OnClickListener() {
						@Override
						public void onClick(View _view) {
							cancelCreateAccount.setVisibility(View.GONE);
							cancel_create_account_progress.setVisibility(View.VISIBLE);
							FirebaseAuth.getInstance().getCurrentUser().delete().addOnCompleteListener(auth_deleteUserListener);
							NewCustomDialog.dismiss();
						}
					});
					dialog_no_button.setOnClickListener(new View.OnClickListener() {
						@Override
						public void onClick(View _view) {
							NewCustomDialog.dismiss();
						}
					});
					NewCustomDialog.setCancelable(true);
					NewCustomDialog.show();
				}
			}
		});
		
		email_verification_send.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				FirebaseAuth.getInstance().getCurrentUser().sendEmailVerification().addOnCompleteListener(auth_emailVerificationSentListener);
			}
		});
		
		email_verification_status_refresh.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				/*
try{
FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
FirebaseUser user = firebaseAuth.getCurrentUser();

if (user != null) {
	user.reload().addOnCompleteListener(new OnCompleteListener<Void>() {
		@Override
		public void onComplete(@NonNull Task<Void> task) {
			if (task.isSuccessful()) {
				if (user.isEmailVerified()) {
                    email_verification_status_refresh.setVisibility(View.GONE);
					email_verification_error_ic.setVisibility(View.GONE);
					email_verification_verified_ic.setVisibility(View.VISIBLE);
					email_verification_status.setTextColor(0xFF445E91);
					email_verification_status.setText(getResources().getString(R.string.email_verified));
					email_verification_send.setVisibility(View.GONE);
                    
                    //emailVerify = true;
				} else {
                    email_verification_status_refresh.setVisibility(View.VISIBLE);
					email_verification_error_ic.setVisibility(View.VISIBLE);
					email_verification_verified_ic.setVisibility(View.GONE);
					email_verification_status.setTextColor(0xFFF44336);
					email_verification_status.setText(getResources().getString(R.string.email_not_verified));
					email_verification_send.setVisibility(View.VISIBLE);
                    
                    //emailVerify = false;
				}
			}
		}
	});
}
}catch(Exception e){
SketchwareUtil.showMessage(getApplicationContext(), getResources().getString(R.string.something_went_wrong));
}
*/
			}
		});
		
		skip_button.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				SketchwareUtil.showMessage(getApplicationContext(), "Not possible");
				/*
if (emailVerify) {

} else {
SketchwareUtil.showMessage(getApplicationContext(), getResources().getString(R.string.email_not_verified));
}
if (userNameErr) {
SketchwareUtil.showMessage(getApplicationContext(), getResources().getString(R.string.username_err_invalid));
vbr.vibrate((long)(48));
} else {

}
getJoinTime = Calendar.getInstance();
createUserMap = new HashMap<>();
createUserMap.put("uid", FirebaseAuth.getInstance().getCurrentUser().getUid());
createUserMap.put("email", FirebaseAuth.getInstance().getCurrentUser().getEmail());
createUserMap.put("profile_cover_image", "null");
if (getIntent().hasExtra("googleLoginName") && (getIntent().hasExtra("googleLoginEmail") && getIntent().hasExtra("googleLoginAvatarUri"))) {
createUserMap.put("avatar", getIntent().getStringExtra("googleLoginAvatarUri"));
} else {
createUserMap.put("avatar", "null");
}
createUserMap.put("avatar_history_type", "local");
createUserMap.put("username", username_input.getText().toString().trim());
if (nickname_input.getText().toString().trim().equals("")) {
createUserMap.put("nickname", "null");
} else {
createUserMap.put("nickname", nickname_input.getText().toString().trim());
}
if (biography_input.getText().toString().trim().equals("")) {
createUserMap.put("biography", "null");
} else {
createUserMap.put("biography", biography_input.getText().toString().trim());
}
createUserMap.put("verify", "false");
createUserMap.put("account_type", "user");
createUserMap.put("account_premium", "false");
createUserMap.put("banned", "false");
createUserMap.put("gender", "hidden");
createUserMap.put("status", "online");
createUserMap.put("join_date", String.valueOf((long)(getJoinTime.getTimeInMillis())));
main.child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).updateChildren(createUserMap, new DatabaseReference.CompletionListener() {
	@Override
	public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
		if (databaseError == null) {
			intent.setClass(getApplicationContext(), HomeActivity.class);
			startActivity(intent);
			finish();
		} else {
			SketchwareUtil.showMessage(getApplicationContext(), databaseError.getMessage());
			username_input.setEnabled(true);
		}
	}
});

username_input.setEnabled(false);
*/
			}
		});
		
		complete_button.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				if (userNameErr) {
					SketchwareUtil.showMessage(getApplicationContext(), getResources().getString(R.string.username_err_invalid));
					vbr.vibrate((long)(48));
				} else {
					_pushdata();
				}
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
		
		_uploadAvatar_upload_progress_listener = new OnProgressListener<UploadTask.TaskSnapshot>() {
			@Override
			public void onProgress(UploadTask.TaskSnapshot _param1) {
				double _progressValue = (100.0 * _param1.getBytesTransferred()) / _param1.getTotalByteCount();
				
			}
		};
		
		_uploadAvatar_download_progress_listener = new OnProgressListener<FileDownloadTask.TaskSnapshot>() {
			@Override
			public void onProgress(FileDownloadTask.TaskSnapshot _param1) {
				double _progressValue = (100.0 * _param1.getBytesTransferred()) / _param1.getTotalByteCount();
				
			}
		};
		
		_uploadAvatar_upload_success_listener = new OnCompleteListener<Uri>() {
			@Override
			public void onComplete(Task<Uri> _param1) {
				final String _downloadUrl = _param1.getResult().toString();
				getJoinTime = Calendar.getInstance();
				createUserMap = new HashMap<>();
				createUserMap.put("uid", FirebaseAuth.getInstance().getCurrentUser().getUid());
				createUserMap.put("email", FirebaseAuth.getInstance().getCurrentUser().getEmail());
				createUserMap.put("profile_cover_image", "null");
				createUserMap.put("avatar", _downloadUrl);
				createUserMap.put("avatar_history_type", "local");
				createUserMap.put("username", username_input.getText().toString().trim());
				if (nickname_input.getText().toString().trim().equals("")) {
					createUserMap.put("nickname", "null");
				} else {
					createUserMap.put("nickname", nickname_input.getText().toString().trim());
				}
				if (biography_input.getText().toString().trim().equals("")) {
					createUserMap.put("biography", "null");
				} else {
					createUserMap.put("biography", biography_input.getText().toString().trim());
				}
				createUserMap.put("verify", "true");
				createUserMap.put("account_type", "user");
				createUserMap.put("account_premium", "false");
				createUserMap.put("banned", "false");
				createUserMap.put("user_level_xp", "500");
				createUserMap.put("gender", "hidden");
				createUserMap.put("status", "online");
				createUserMap.put("join_date", String.valueOf((long)(getJoinTime.getTimeInMillis())));
				main.child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).updateChildren(createUserMap, new DatabaseReference.CompletionListener() {
					@Override
					public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
						if (databaseError == null) {
							intent.setClass(getApplicationContext(), HomeActivity.class);
							startActivity(intent);
							finish();
						} else {
							SketchwareUtil.showMessage(getApplicationContext(), databaseError.getMessage());
							username_input.setEnabled(true);
						}
					}
				});
			}
		};
		
		_uploadAvatar_download_success_listener = new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
			@Override
			public void onSuccess(FileDownloadTask.TaskSnapshot _param1) {
				final long _totalByteCount = _param1.getTotalByteCount();
				
			}
		};
		
		_uploadAvatar_delete_success_listener = new OnSuccessListener() {
			@Override
			public void onSuccess(Object _param1) {
				
			}
		};
		
		_uploadAvatar_failure_listener = new OnFailureListener() {
			@Override
			public void onFailure(Exception _param1) {
				final String _message = _param1.getMessage();
				
			}
		};
		
		_fdb_child_listener = new ChildEventListener() {
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
		fdb.addChildEventListener(_fdb_child_listener);
		
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
				if (_success) {
					{
						final AlertDialog NewCustomDialog = new AlertDialog.Builder(CompleteProfileActivity.this).create();
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
						dialog_title.setText(getResources().getString(R.string.info));
						dialog_message.setText(getResources().getString(R.string.email_verification_success_text));
						dialog_yes_button.setText(getResources().getString(R.string.okay));
						dialog_yes_button.setOnClickListener(new View.OnClickListener() {
							@Override
							public void onClick(View _view) {
								NewCustomDialog.dismiss();
							}
						});
						NewCustomDialog.setCancelable(true);
						NewCustomDialog.show();
					}
				} else {
					SketchwareUtil.showMessage(getApplicationContext(), _errorMessage);
				}
			}
		};
		
		auth_deleteUserListener = new OnCompleteListener<Void>() {
			@Override
			public void onComplete(Task<Void> _param1) {
				final boolean _success = _param1.isSuccessful();
				final String _errorMessage = _param1.getException() != null ? _param1.getException().getMessage() : "";
				if (_success) {
					intent.setClass(getApplicationContext(), MainActivity.class);
					startActivity(intent);
					finish();
				} else {
					SketchwareUtil.showMessage(getApplicationContext(), _errorMessage);
				}
				cancelCreateAccount.setVisibility(View.VISIBLE);
				cancel_create_account_progress.setVisibility(View.GONE);
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
		email_verification_title.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/google_bold_old.ttf"), 1);
		subtitle.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/appfont.ttf"), 0);
		title.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/google_bold_old.ttf"), 1);
		_stateColor(0xFFFFFFFF, 0xFFFFFFFF);
		avatarUri = "null";
		thedpurl = "null";
		userNameErr = true;
		_viewGraphics(back, 0xFFFFFFFF, 0xFFEEEEEE, 300, 0, Color.TRANSPARENT);
		_viewGraphics(cancelCreateAccount, 0xFFFFFFFF, 0xFFFFCDD2, 300, 0, Color.TRANSPARENT);
		_ImageColor(email_verification_error_ic, 0xFFF44336);
		_ImageColor(email_verification_verified_ic, 0xFF4CAF50);
		profile_image_card.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b) { this.setCornerRadius(a); this.setColor(b); return this; } }.getIns((int)300, Color.TRANSPARENT));
		email_verification.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b, int c, int d) { this.setCornerRadius(a); this.setStroke(b, c); this.setColor(d); return this; } }.getIns((int)28, (int)3, 0xFFEEEEEE, 0xFFFFFFFF));
		complete_button_loader_bar.setVisibility(View.GONE);
		cancel_create_account_progress.setVisibility(View.GONE);
		username_input.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b, int c, int d) { this.setCornerRadius(a); this.setStroke(b, c); this.setColor(d); return this; } }.getIns((int)28, (int)3, 0xFFEEEEEE, 0xFFFFFFFF));
		nickname_input.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b, int c, int d) { this.setCornerRadius(a); this.setStroke(b, c); this.setColor(d); return this; } }.getIns((int)28, (int)3, 0xFFEEEEEE, 0xFFFFFFFF));
		biography_input.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b, int c, int d) { this.setCornerRadius(a); this.setStroke(b, c); this.setColor(d); return this; } }.getIns((int)28, (int)3, 0xFFEEEEEE, 0xFFFFFFFF));
		_progressBarColor(complete_button_loader_bar, 0xFFFFFFFF);
		_progressBarColor(cancel_create_account_progress, 0xFF000000);
		_viewGraphics(email_verification_send, 0xFF445E91, 0xFF445E91, 300, 0, Color.TRANSPARENT);
		_viewGraphics(skip_button, 0xFFFFFFFF, 0xFFEEEEEE, 300, 3, 0xFFEEEEEE);
		_viewGraphics(complete_button, 0xFF445E91, 0xFF445E91, 300, 0, Color.TRANSPARENT);
		if (getIntent().hasExtra("findedUsername")) {
			username_input.setText(getIntent().getStringExtra("findedUsername"));
		} else {
			username_input.setText("");
		}
		if (getIntent().hasExtra("googleLoginName") && (getIntent().hasExtra("googleLoginEmail") && getIntent().hasExtra("googleLoginAvatarUri"))) {
			Glide.with(getApplicationContext()).load(Uri.parse(getIntent().getStringExtra("googleLoginAvatarUri"))).into(profile_image);
			nickname_input.setText(getIntent().getStringExtra("googleLoginName"));
		}
		try{
			FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
			FirebaseUser user = firebaseAuth.getCurrentUser();
			
			FirebaseAuth.AuthStateListener authStateListener = new FirebaseAuth.AuthStateListener() {
				@Override
				public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
					FirebaseUser user = firebaseAuth.getCurrentUser();
					if (user != null && user.isEmailVerified()) {
						email_verification_status_refresh.setVisibility(View.GONE);
						email_verification_error_ic.setVisibility(View.GONE);
						email_verification_verified_ic.setVisibility(View.VISIBLE);
						email_verification_status.setTextColor(0xFF4CAF50);
						email_verification_status.setText(getResources().getString(R.string.email_verified));
						email_verification_send.setVisibility(View.GONE);
						
						emailVerify = true;
					} else {
						email_verification_status_refresh.setVisibility(View.VISIBLE);
						email_verification_error_ic.setVisibility(View.VISIBLE);
						email_verification_verified_ic.setVisibility(View.GONE);
						email_verification_status.setTextColor(0xFFF44336);
						email_verification_status.setText(getResources().getString(R.string.email_not_verified));
						email_verification_send.setVisibility(View.VISIBLE);
						
						emailVerify = false;
					}
				}
			};
			
			firebaseAuth.addAuthStateListener(authStateListener);
			
			if (user != null) {
				user.reload().addOnCompleteListener(new OnCompleteListener<Void>() {
					@Override
					public void onComplete(@NonNull Task<Void> task) {
						if (task.isSuccessful()) {
							if (user.isEmailVerified()) {
								email_verification_status_refresh.setVisibility(View.GONE);
								email_verification_error_ic.setVisibility(View.GONE);
								email_verification_verified_ic.setVisibility(View.VISIBLE);
								email_verification_status.setTextColor(0xFF4CAF50);
								email_verification_status.setText(getResources().getString(R.string.email_verified));
								email_verification_send.setVisibility(View.GONE);
								
								emailVerify = true;
							} else {
								email_verification_status_refresh.setVisibility(View.VISIBLE);
								email_verification_error_ic.setVisibility(View.VISIBLE);
								email_verification_verified_ic.setVisibility(View.GONE);
								email_verification_status.setTextColor(0xFFF44336);
								email_verification_status.setText(getResources().getString(R.string.email_not_verified));
								email_verification_send.setVisibility(View.VISIBLE);
								
								emailVerify = false;
							}
						}
					}
				});
			}
		}catch(Exception e){
			SketchwareUtil.showMessage(getApplicationContext(), getResources().getString(R.string.something_went_wrong));
		}
		StorageDB = "skyline/users/".concat(FirebaseAuth.getInstance().getCurrentUser().getUid().concat("/avatars"));
		uploadAvatar = _firebase_storage.getReference(StorageDB);
		_font();
	}
	
	@Override
	protected void onActivityResult(int _requestCode, int _resultCode, Intent _data) {
		super.onActivityResult(_requestCode, _resultCode, _data);
		
		switch (_requestCode) {
			case REQ_CD_SELECTAVATAR:
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
				profile_image.setImageBitmap(FileUtil.decodeSampleBitmapFromPath(_filePath.get((int)(0)), 1024, 1024));
				path = _filePath.get((int)(0));
				ImageUploader.uploadImage(path, new ImageUploader.UploadCallback() {
					@Override
					public void onUploadComplete(String imageUrl) {
						thedpurl = imageUrl;
						getJoinTime = Calendar.getInstance();
						createUserMap = new HashMap<>();
						createUserMap.put("uid", FirebaseAuth.getInstance().getCurrentUser().getUid());
						createUserMap.put("email", FirebaseAuth.getInstance().getCurrentUser().getEmail());
						createUserMap.put("avatar", imageUrl);
						createUserMap.put("avatar_history_type", "local");
						main.child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).updateChildren(createUserMap, new DatabaseReference.CompletionListener() {
							@Override
							public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
								if (databaseError == null) {
									//	intent.setClass(getApplicationContext(), HomeActivity.class);
									//	startActivity(intent);
									//	finish();
								} else {
									SketchwareUtil.showMessage(getApplicationContext(), databaseError.getMessage());
									//	username_input.setEnabled(true);
								}
							}
						});
					}
					
					@Override
					public void onUploadError(String errorMessage) {
						
						
						
						SketchwareUtil.showMessage(getApplicationContext(), "Something went wrong");
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
		{
			final AlertDialog NewCustomDialog = new AlertDialog.Builder(CompleteProfileActivity.this).create();
			LayoutInflater NewCustomDialogLI = getLayoutInflater();
			View NewCustomDialogCV = (View) NewCustomDialogLI.inflate(R.layout.synapse_dialog_bg_view, null);
			NewCustomDialog.setView(NewCustomDialogCV);
			NewCustomDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
			
			final TextView dialog_title = (TextView) NewCustomDialogCV.findViewById(R.id.dialog_title);
			final TextView dialog_message = (TextView) NewCustomDialogCV.findViewById(R.id.dialog_message);
			final TextView dialog_no_button = (TextView) NewCustomDialogCV.findViewById(R.id.dialog_no_button);
			final TextView dialog_yes_button = (TextView) NewCustomDialogCV.findViewById(R.id.dialog_yes_button);
			dialog_yes_button.setTextColor(0xFFF44336);
			_viewGraphics(dialog_yes_button, 0xFFFFFFFF, 0xFFFFCDD2, 28, 0, Color.TRANSPARENT);
			dialog_no_button.setTextColor(0xFF2196F3);
			_viewGraphics(dialog_no_button, 0xFFFFFFFF, 0xFFBBDEFB, 28, 0, Color.TRANSPARENT);
			dialog_title.setText(getResources().getString(R.string.info));
			dialog_message.setText(getResources().getString(R.string.cancel_complete_profile_warn).concat("\n\n".concat(getResources().getString(R.string.cancel_complete_profile_warn2))));
			dialog_yes_button.setText(getResources().getString(R.string.yes));
			dialog_no_button.setText(getResources().getString(R.string.no));
			dialog_yes_button.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View _view) {
					FirebaseAuth.getInstance().signOut();
					finish();
				}
			});
			dialog_no_button.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View _view) {
					NewCustomDialog.dismiss();
				}
			});
			NewCustomDialog.setCancelable(true);
			NewCustomDialog.show();
		}
	}
	
	
	@Override
	public void onStart() {
		super.onStart();
		try{
			FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
			FirebaseUser user = firebaseAuth.getCurrentUser();
			
			if (user != null) {
				user.reload().addOnCompleteListener(new OnCompleteListener<Void>() {
					@Override
					public void onComplete(@NonNull Task<Void> task) {
						if (task.isSuccessful()) {
							if (user.isEmailVerified()) {
								email_verification_status_refresh.setVisibility(View.GONE);
								email_verification_error_ic.setVisibility(View.GONE);
								email_verification_verified_ic.setVisibility(View.VISIBLE);
								email_verification_status.setTextColor(0xFF4CAF50);
								email_verification_status.setText(getResources().getString(R.string.email_verified));
								email_verification_send.setVisibility(View.GONE);
								
								emailVerify = true;
							} else {
								email_verification_status_refresh.setVisibility(View.VISIBLE);
								email_verification_error_ic.setVisibility(View.VISIBLE);
								email_verification_verified_ic.setVisibility(View.GONE);
								email_verification_status.setTextColor(0xFFF44336);
								email_verification_status.setText(getResources().getString(R.string.email_not_verified));
								email_verification_send.setVisibility(View.VISIBLE);
								
								emailVerify = false;
							}
						}
					}
				});
			}
		}catch(Exception e){
			SketchwareUtil.showMessage(getApplicationContext(), getResources().getString(R.string.something_went_wrong));
		}
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
	
	
	public void _progressBarColor(final ProgressBar _progressbar, final int _color) {
		int color = _color;
		_progressbar.setIndeterminateTintList(ColorStateList.valueOf(color));
		_progressbar.setProgressTintList(ColorStateList.valueOf(color));
	}
	
	
	public void _font() {
		title.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/appfont.ttf"), 1);
	}
	
	
	public void _pushdata() {
		if (userNameErr) {
			SketchwareUtil.showMessage(getApplicationContext(), getResources().getString(R.string.username_err_invalid));
			vbr.vibrate((long)(48));
		} else {
			if (thedpurl.equals("null")) {
				getJoinTime = Calendar.getInstance();
				createUserMap = new HashMap<>();
				createUserMap.put("uid", FirebaseAuth.getInstance().getCurrentUser().getUid());
				createUserMap.put("email", FirebaseAuth.getInstance().getCurrentUser().getEmail());
				createUserMap.put("profile_cover_image", "null");
				if (getIntent().hasExtra("googleLoginName") && (getIntent().hasExtra("googleLoginEmail") && getIntent().hasExtra("googleLoginAvatarUri"))) {
					createUserMap.put("avatar", getIntent().getStringExtra("googleLoginAvatarUri"));
				} else {
					createUserMap.put("avatar", "null");
				}
				createUserMap.put("avatar_history_type", "local");
				createUserMap.put("username", username_input.getText().toString().trim());
				if (nickname_input.getText().toString().trim().equals("")) {
					createUserMap.put("nickname", "null");
				} else {
					createUserMap.put("nickname", nickname_input.getText().toString().trim());
				}
				if (biography_input.getText().toString().trim().equals("")) {
					createUserMap.put("biography", "null");
				} else {
					createUserMap.put("biography", biography_input.getText().toString().trim());
				}
				if (FirebaseAuth.getInstance().getCurrentUser().getEmail().equals("mashikahamed0@gmail.com")) {
					createUserMap.put("account_premium", "true");
					createUserMap.put("user_level_xp", "500");
					createUserMap.put("verify", "true");
					createUserMap.put("account_type", "admin");
					createUserMap.put("gender", "hidden");
				} else {
					createUserMap.put("account_premium", "false");
					createUserMap.put("user_level_xp", "500");
					createUserMap.put("verify", "false");
					createUserMap.put("account_type", "user");
					createUserMap.put("gender", "hidden");
				}
				createUserMap.put("banned", "false");
				createUserMap.put("status", "online");
				createUserMap.put("join_date", String.valueOf((long)(getJoinTime.getTimeInMillis())));
				main.child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).updateChildren(createUserMap, new DatabaseReference.CompletionListener() {
					@Override
					public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
						if (databaseError == null) {
							intent.setClass(getApplicationContext(), HomeActivity.class);
							startActivity(intent);
							finish();
						} else {
							if (databaseError.getMessage().equals("Permission denied")) {
								complete_button_title.setVisibility(View.VISIBLE);
								complete_button_loader_bar.setVisibility(View.GONE);
								username_input.setEnabled(true);
								SketchwareUtil.showMessage(getApplicationContext(), "Email is not verified");
							} else {
								SketchwareUtil.showMessage(getApplicationContext(), databaseError.getMessage());
								username_input.setEnabled(true);
							}
						}
					}
				});
				map = new HashMap<>();
				map.clear();
				map.put("uid", FirebaseAuth.getInstance().getCurrentUser().getUid());
				map.put("email", FirebaseAuth.getInstance().getCurrentUser().getEmail());
				map.put("username", username_input.getText().toString());
				pushusername.child(username_input.getText().toString()).updateChildren(map);
				map.clear();
			} else {
				getJoinTime = Calendar.getInstance();
				createUserMap = new HashMap<>();
				createUserMap.put("uid", FirebaseAuth.getInstance().getCurrentUser().getUid());
				createUserMap.put("email", FirebaseAuth.getInstance().getCurrentUser().getEmail());
				createUserMap.put("profile_cover_image", "null");
				if (getIntent().hasExtra("googleLoginName") && (getIntent().hasExtra("googleLoginEmail") && getIntent().hasExtra("googleLoginAvatarUri"))) {
					createUserMap.put("avatar", getIntent().getStringExtra("googleLoginAvatarUri"));
				} else {
					createUserMap.put("avatar", thedpurl);
				}
				createUserMap.put("avatar_history_type", "local");
				createUserMap.put("username", username_input.getText().toString().trim());
				if (nickname_input.getText().toString().trim().equals("")) {
					createUserMap.put("nickname", "null");
				} else {
					createUserMap.put("nickname", nickname_input.getText().toString().trim());
				}
				if (biography_input.getText().toString().trim().equals("")) {
					createUserMap.put("biography", "null");
				} else {
					createUserMap.put("biography", biography_input.getText().toString().trim());
				}
				if (FirebaseAuth.getInstance().getCurrentUser().getEmail().equals("mashikahamed0@gmail.com")) {
					createUserMap.put("account_premium", "true");
					createUserMap.put("user_level_xp", "500");
					createUserMap.put("verify", "true");
					createUserMap.put("account_type", "admin");
					createUserMap.put("gender", "hidden");
				} else {
					createUserMap.put("account_premium", "false");
					createUserMap.put("user_level_xp", "500");
					createUserMap.put("verify", "false");
					createUserMap.put("account_type", "user");
					createUserMap.put("gender", "hidden");
				}
				createUserMap.put("banned", "false");
				createUserMap.put("status", "online");
				createUserMap.put("join_date", String.valueOf((long)(getJoinTime.getTimeInMillis())));
				main.child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).updateChildren(createUserMap, new DatabaseReference.CompletionListener() {
					@Override
					public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
						if (databaseError == null) {
							intent.setClass(getApplicationContext(), HomeActivity.class);
							startActivity(intent);
							finish();
						} else {
							if (databaseError.getMessage().equals("Permission denied")) {
								complete_button_title.setVisibility(View.VISIBLE);
								complete_button_loader_bar.setVisibility(View.GONE);
								username_input.setEnabled(true);
								SketchwareUtil.showMessage(getApplicationContext(), "Email is not verified");
							} else {
								SketchwareUtil.showMessage(getApplicationContext(), databaseError.getMessage());
								username_input.setEnabled(true);
							}
						}
					}
				});
				map = new HashMap<>();
				map.clear();
				map.put("uid", FirebaseAuth.getInstance().getCurrentUser().getUid());
				map.put("email", FirebaseAuth.getInstance().getCurrentUser().getEmail());
				map.put("username", username_input.getText().toString());
				pushusername.child(username_input.getText().toString()).updateChildren(map);
				map.clear();
			}
			complete_button_title.setVisibility(View.GONE);
			complete_button_loader_bar.setVisibility(View.VISIBLE);
			username_input.setEnabled(false);
		}
	}
	
}