package com.synapse.social.studioasinc;

import android.Manifest;
import android.animation.*;
import android.app.*;
import android.content.*;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.*;
import android.graphics.*;
import android.graphics.drawable.*;
import android.media.*;
import android.net.*;
import android.net.Uri;
import android.os.*;
import android.os.Bundle;
import android.text.*;
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
import android.widget.TextView;
import androidx.annotation.*;
import androidx.appcompat.app.AppCompatActivity;
import androidx.browser.*;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.*;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.Adapter;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout.OnRefreshListener;
import com.bumptech.glide.*;
import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.*;
import com.google.android.material.color.MaterialColors;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
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
import com.theartofdev.edmodo.cropper.*;
import com.yalantis.ucrop.*;
import java.io.*;
import java.io.File;
import java.io.InputStream;
import java.text.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.regex.*;
import org.json.*;
import com.google.firebase.database.Query;
import java.net.URL;
import java.net.MalformedURLException;

public class ProfilePhotoHistoryActivity extends AppCompatActivity {
	
	private FirebaseDatabase _firebase = FirebaseDatabase.getInstance();
	private FirebaseStorage _firebase_storage = FirebaseStorage.getInstance();
	
	private ProgressDialog SynapseLoadingDialog;
	private FloatingActionButton _fab;
	private String CurrentAvatarUri = "";
	private HashMap<String, Object> mSendMap = new HashMap<>();
	private HashMap<String, Object> mAddProfilePhotoMap = new HashMap<>();
	
	private ArrayList<HashMap<String, Object>> ProfileHistoryList = new ArrayList<>();
	
	private LinearLayout main;
	private LinearLayout top;
	private LinearLayout body;
	private ImageView back;
	private TextView title;
	private SwipeRefreshLayout mSwipeLayout;
	private LinearLayout mLoadingBody;
	private LinearLayout mLoadedBody;
	private LinearLayout isDataExistsLayout;
	private LinearLayout isDataNotExistsLayout;
	private RecyclerView ProfilePhotosHistoryList;
	private TextView isDataNotExistsLayoutTitle;
	private TextView isDataNotExistsLayoutSubTitle;
	private ProgressBar mLoadingBar;
	
	private DatabaseReference maindb = _firebase.getReference("/");
	private ChildEventListener _maindb_child_listener;
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
	private StorageReference storage = _firebase_storage.getReference("/");
	private OnCompleteListener<Uri> _storage_upload_success_listener;
	private OnSuccessListener<FileDownloadTask.TaskSnapshot> _storage_download_success_listener;
	private OnSuccessListener _storage_delete_success_listener;
	private OnProgressListener _storage_upload_progress_listener;
	private OnProgressListener _storage_download_progress_listener;
	private OnFailureListener _storage_failure_listener;
	private Intent intent = new Intent();
	private Calendar cc = Calendar.getInstance();
	
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.profile_photo_history);
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
		_fab = findViewById(R.id._fab);
		main = findViewById(R.id.main);
		top = findViewById(R.id.top);
		body = findViewById(R.id.body);
		back = findViewById(R.id.back);
		title = findViewById(R.id.title);
		mSwipeLayout = findViewById(R.id.mSwipeLayout);
		mLoadingBody = findViewById(R.id.mLoadingBody);
		mLoadedBody = findViewById(R.id.mLoadedBody);
		isDataExistsLayout = findViewById(R.id.isDataExistsLayout);
		isDataNotExistsLayout = findViewById(R.id.isDataNotExistsLayout);
		ProfilePhotosHistoryList = findViewById(R.id.ProfilePhotosHistoryList);
		isDataNotExistsLayoutTitle = findViewById(R.id.isDataNotExistsLayoutTitle);
		isDataNotExistsLayoutSubTitle = findViewById(R.id.isDataNotExistsLayoutSubTitle);
		mLoadingBar = findViewById(R.id.mLoadingBar);
		auth = FirebaseAuth.getInstance();
		
		back.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				onBackPressed();
			}
		});
		
		mSwipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
			@Override
			public void onRefresh() {
				_getReference();
			}
		});
		
		_fab.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				_addProfilePhotoUrlDialog();
			}
		});
		
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
		
		_storage_upload_progress_listener = new OnProgressListener<UploadTask.TaskSnapshot>() {
			@Override
			public void onProgress(UploadTask.TaskSnapshot _param1) {
				double _progressValue = (100.0 * _param1.getBytesTransferred()) / _param1.getTotalByteCount();
				
			}
		};
		
		_storage_download_progress_listener = new OnProgressListener<FileDownloadTask.TaskSnapshot>() {
			@Override
			public void onProgress(FileDownloadTask.TaskSnapshot _param1) {
				double _progressValue = (100.0 * _param1.getBytesTransferred()) / _param1.getTotalByteCount();
				
			}
		};
		
		_storage_upload_success_listener = new OnCompleteListener<Uri>() {
			@Override
			public void onComplete(Task<Uri> _param1) {
				final String _downloadUrl = _param1.getResult().toString();
				
			}
		};
		
		_storage_download_success_listener = new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
			@Override
			public void onSuccess(FileDownloadTask.TaskSnapshot _param1) {
				final long _totalByteCount = _param1.getTotalByteCount();
				
			}
		};
		
		_storage_delete_success_listener = new OnSuccessListener() {
			@Override
			public void onSuccess(Object _param1) {
				
			}
		};
		
		_storage_failure_listener = new OnFailureListener() {
			@Override
			public void onFailure(Exception _param1) {
				final String _message = _param1.getMessage();
				
			}
		};
		
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
		_stateColor(0xFFFFFFFF, 0xFFFFFFFF);
		_viewGraphics(back, 0xFFFFFFFF, 0xFFE0E0E0, 300, 0, Color.TRANSPARENT);
		top.setElevation((float)4);
		GridLayoutManager profileImagesHistoryListGrid = new GridLayoutManager(this, 3);
		ProfilePhotosHistoryList.setLayoutManager(profileImagesHistoryListGrid);
		ProfilePhotosHistoryList.setAdapter(new ProfilePhotosHistoryListAdapter(ProfileHistoryList));
		_getReference();
	}
	
	@Override
	public void onBackPressed() {
		finish();
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
	
	
	public void _stateColor(final int _statusColor, final int _navigationColor) {
		getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
		getWindow().setStatusBarColor(_statusColor);
		getWindow().setNavigationBarColor(_navigationColor);
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
	
	
	public void _getReference() {
		isDataExistsLayout.setVisibility(View.GONE);
		isDataNotExistsLayout.setVisibility(View.GONE);
		mSwipeLayout.setVisibility(View.GONE);
		mLoadingBody.setVisibility(View.VISIBLE);
		DatabaseReference getUserReference = FirebaseDatabase.getInstance().getReference("skyline/users").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
		getUserReference.addValueEventListener(new ValueEventListener() {
			@Override
			public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
				if(dataSnapshot.exists()) {
					CurrentAvatarUri = dataSnapshot.child("avatar").getValue(String.class);
				} else {
				}
			}
			
			@Override
			public void onCancelled(@NonNull DatabaseError databaseError) {
				
			}
		});
		Query getProfileHistoryRef = FirebaseDatabase.getInstance().getReference("skyline/profile-history").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
		getProfileHistoryRef.addListenerForSingleValueEvent(new ValueEventListener() {
			@Override
			public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
				if(dataSnapshot.exists()) {
					isDataExistsLayout.setVisibility(View.VISIBLE);
					isDataNotExistsLayout.setVisibility(View.GONE);
					mSwipeLayout.setVisibility(View.VISIBLE);
					mLoadingBody.setVisibility(View.GONE);
					ProfileHistoryList.clear();
					try {
						GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
						for (DataSnapshot _data : dataSnapshot.getChildren()) {
							HashMap<String, Object> _map = _data.getValue(_ind);
							ProfileHistoryList.add(_map);
						}
					} catch (Exception _e) {
						_e.printStackTrace();
					}
					
					SketchwareUtil.sortListMap(ProfileHistoryList, "upload_date", false, false);
					ProfilePhotosHistoryList.getAdapter().notifyDataSetChanged();
				} else {
					isDataExistsLayout.setVisibility(View.GONE);
					isDataNotExistsLayout.setVisibility(View.VISIBLE);
					mSwipeLayout.setVisibility(View.VISIBLE);
					mLoadingBody.setVisibility(View.GONE);
				}
			}
			
			@Override
			public void onCancelled(@NonNull DatabaseError databaseError) {
				
			}
		});
		mSwipeLayout.setRefreshing(false);
	}
	
	
	public void _deleteProfileImage(final String _key, final String _type, final String _uri) {
		{
			final AlertDialog NewCustomDialog = new AlertDialog.Builder(ProfilePhotoHistoryActivity.this).create();
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
			dialog_message.setText("Are you sure you want to delete this profile photo completely? ");
			dialog_no_button.setText(getResources().getString(R.string.no));
			dialog_yes_button.setText(getResources().getString(R.string.yes));
			dialog_no_button.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View _view) {
					NewCustomDialog.dismiss();
				}
			});
			dialog_yes_button.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View _view) {
					if (_uri.equals(CurrentAvatarUri)) {
						mSendMap = new HashMap<>();
						mSendMap.put("avatar", "null");
						mSendMap.put("avatar_history_type", "local");
						maindb.child("skyline/users/".concat(FirebaseAuth.getInstance().getCurrentUser().getUid())).updateChildren(mSendMap);
						CurrentAvatarUri = "null";
						mSendMap.clear();
					}
					if (_type.equals("local")) {
						_firebase_storage.getReferenceFromUrl(_uri).delete().addOnSuccessListener(_storage_delete_success_listener).addOnFailureListener(_storage_failure_listener);
					}
					maindb.child("skyline/profile-history/".concat(FirebaseAuth.getInstance().getCurrentUser().getUid().concat("/".concat(_key)))).removeValue();
					_getReference();
					NewCustomDialog.dismiss();
				}
			});
			NewCustomDialog.setCancelable(true);
			NewCustomDialog.show();
		}
	}
	
	
	public void _addProfilePhotoUrlDialog() {
		{
			final AlertDialog NewCustomDialog = new AlertDialog.Builder(ProfilePhotoHistoryActivity.this).create();
			LayoutInflater NewCustomDialogLI = getLayoutInflater();
			View NewCustomDialogCV = (View) NewCustomDialogLI.inflate(R.layout.add_dp_with_url_synapse, null);
			NewCustomDialog.setView(NewCustomDialogCV);
			NewCustomDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
			
			final androidx.cardview.widget.CardView dialog_card = NewCustomDialogCV.findViewById(R.id.dialog_card);
			final androidx.cardview.widget.CardView user_avatar_card = NewCustomDialogCV.findViewById(R.id.user_avatar_card);
			final EditText user_avatar_url_input = NewCustomDialogCV.findViewById(R.id.user_avatar_url_input);
			final ImageView user_avatar_image = NewCustomDialogCV.findViewById(R.id.user_avatar_image);
			final TextView add_button = NewCustomDialogCV.findViewById(R.id.add_button);
			final TextView cancel_button = NewCustomDialogCV.findViewById(R.id.cancel_button);
			
			dialog_card.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b) { this.setCornerRadius(a); this.setColor(b); return this; } }.getIns((int)28, 0xFFFFFFFF));
			user_avatar_card.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b) { this.setCornerRadius(a); this.setColor(b); return this; } }.getIns((int)300, Color.TRANSPARENT));
			user_avatar_url_input.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b, int c, int d) { this.setCornerRadius(a); this.setStroke(b, c); this.setColor(d); return this; } }.getIns((int)28, (int)3, 0xFFEEEEEE, Color.TRANSPARENT));
			_viewGraphics(add_button, 0xFF2196F3, 0xFF1976D2, 300, 0, Color.TRANSPARENT);
			_viewGraphics(cancel_button, 0xFFF5F5F5, 0xFFE0E0E0, 300, 0, Color.TRANSPARENT);
			user_avatar_url_input.addTextChangedListener(new TextWatcher() {
				@Override
				public void onTextChanged(CharSequence _param1, int _param2, int _param3, int _param4) {
					final String _charSeq = _param1.toString();
					
					if (!_charSeq.trim().equals("")) {
						if (_checkValidUrl(_charSeq.trim())) {
							Glide.with(getApplicationContext()).load(Uri.parse(_charSeq.trim())).into(user_avatar_image);
						} else {
							((EditText)user_avatar_url_input).setError("Invalid URL");
						}
					} else {
						((EditText)user_avatar_url_input).setError("Enter Profile Image URL");
					}
				}
				
				@Override
				public void beforeTextChanged(CharSequence _param1, int _param2, int _param3, int _param4) {
					
				}
				
				@Override
				public void afterTextChanged(Editable _param1) {
					
				}
			});
			
			add_button.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View _view) {
					if (!user_avatar_url_input.getText().toString().trim().equals("")) {
						if (_checkValidUrl(user_avatar_url_input.getText().toString().trim())) {
							String ProfileHistoryKey = maindb.push().getKey();
							mAddProfilePhotoMap = new HashMap<>();
							mAddProfilePhotoMap.put("key", ProfileHistoryKey);
							mAddProfilePhotoMap.put("image_url", user_avatar_url_input.getText().toString().trim());
							mAddProfilePhotoMap.put("upload_date", String.valueOf((long)(cc.getTimeInMillis())));
							mAddProfilePhotoMap.put("type", "url");
							maindb.child("skyline/profile-history/".concat(FirebaseAuth.getInstance().getCurrentUser().getUid().concat("/".concat(ProfileHistoryKey)))).updateChildren(mAddProfilePhotoMap);
							SketchwareUtil.showMessage(getApplicationContext(), "Profile Photo Added");
							_getReference();
							NewCustomDialog.dismiss();
						}
					}
				}
			});
			cancel_button.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View _view) {
					NewCustomDialog.dismiss();
				}
			});
			
			NewCustomDialog.setCancelable(true);
			NewCustomDialog.show();
		}
	}
	
	
	public boolean _checkValidUrl(final String _url) {
		try {
			new URL(_url);
			return true;
		} catch (MalformedURLException e) {
			return false;
		}
	}
	
	public class ProfilePhotosHistoryListAdapter extends RecyclerView.Adapter<ProfilePhotosHistoryListAdapter.ViewHolder> {
		
		ArrayList<HashMap<String, Object>> _data;
		
		public ProfilePhotosHistoryListAdapter(ArrayList<HashMap<String, Object>> _arr) {
			_data = _arr;
		}
		
		@Override
		public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
			LayoutInflater _inflater = getLayoutInflater();
			View _v = _inflater.inflate(R.layout.dp_history_cv, null);
			RecyclerView.LayoutParams _lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
			_v.setLayoutParams(_lp);
			return new ViewHolder(_v);
		}
		
		@Override
		public void onBindViewHolder(ViewHolder _holder, final int _position) {
			View _view = _holder.itemView;
			
			final LinearLayout body = _view.findViewById(R.id.body);
			final androidx.cardview.widget.CardView card = _view.findViewById(R.id.card);
			final RelativeLayout relative = _view.findViewById(R.id.relative);
			final ImageView profile = _view.findViewById(R.id.profile);
			final LinearLayout checked = _view.findViewById(R.id.checked);
			final ImageView checked_ic = _view.findViewById(R.id.checked_ic);
			
			_viewGraphics(body, 0xFFFFFFFF, 0xFFEEEEEE, 28, 0, Color.TRANSPARENT);
			card.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b) { this.setCornerRadius(a); this.setColor(b); return this; } }.getIns((int)300, Color.TRANSPARENT));
			checked.setBackgroundColor(0x50000000);
			_ImageColor(checked_ic, 0xFFFFFFFF);
			Glide.with(getApplicationContext()).load(Uri.parse(_data.get((int)_position).get("image_url").toString())).into(profile);
			if (_data.get((int)_position).get("image_url").toString().equals(CurrentAvatarUri)) {
				checked.setVisibility(View.VISIBLE);
			} else {
				checked.setVisibility(View.GONE);
			}
			body.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View _view) {
					if (_data.get((int)_position).get("image_url").toString().equals(CurrentAvatarUri)) {
						mSendMap = new HashMap<>();
						mSendMap.put("avatar", "null");
						mSendMap.put("avatar_history_type", "local");
						maindb.child("skyline/users/".concat(FirebaseAuth.getInstance().getCurrentUser().getUid())).updateChildren(mSendMap);
						CurrentAvatarUri = "null";
						mSendMap.clear();
						notifyDataSetChanged();
					} else {
						mSendMap = new HashMap<>();
						mSendMap.put("avatar", _data.get((int)_position).get("image_url").toString());
						mSendMap.put("avatar_history_type", _data.get((int)_position).get("type").toString());
						maindb.child("skyline/users/".concat(FirebaseAuth.getInstance().getCurrentUser().getUid())).updateChildren(mSendMap);
						CurrentAvatarUri = _data.get((int)_position).get("image_url").toString();
						mSendMap.clear();
						notifyDataSetChanged();
					}
				}
			});
			body.setOnLongClickListener(new View.OnLongClickListener() {
				@Override
				public boolean onLongClick(View _view) {
					_deleteProfileImage(_data.get((int)_position).get("key").toString(), _data.get((int)_position).get("type").toString(), _data.get((int)_position).get("image_url").toString());
					return true;
				}
			});
		}
		
		@Override
		public int getItemCount() {
			return _data.size();
		}
		
		public class ViewHolder extends RecyclerView.ViewHolder {
			public ViewHolder(View v) {
				super(v);
			}
		}
	}
}