package com.synapse.social.studioasinc;

import android.Manifest;
import android.animation.*;
import android.app.*;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.*;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.*;
import android.graphics.*;
import android.graphics.drawable.*;
import android.media.*;
import android.net.*;
import android.net.Uri;
import android.os.*;
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
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import androidx.annotation.*;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
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
import java.text.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Calendar;
import java.util.HashMap;
import java.util.regex.*;
import org.json.*;
import androidx.appcompat.widget.SwitchCompat;
import com.synapse.social.studioasinc.ImageUploader;

public class CreateImagePostNextStepActivity extends AppCompatActivity {
	
	private FirebaseDatabase _firebase = FirebaseDatabase.getInstance();
	private FirebaseStorage _firebase_storage = FirebaseStorage.getInstance();
	
	private ProgressDialog SynapseLoadingDialog;
	private String UniquePostKey = "";
	private HashMap<String, Object> PostSendMap = new HashMap<>();
	private HashMap<String, Object> m = new HashMap<>();
	private String IMG_BB_API_KEY = "";
	private String path = "";
	
	private LinearLayout main;
	private LinearLayout top;
	private LinearLayout topSpace;
	private ScrollView scroll;
	private ImageView back;
	private LinearLayout topSpc;
	private TextView continueButton;
	private TextView title;
	private LinearLayout scrollBody;
	private LinearLayout PostInfoTop1;
	private LinearLayout topSpace2;
	private LinearLayout spc2;
	private LinearLayout post_settings_statistics;
	private LinearLayout spc3;
	private LinearLayout post_settings_privacy;
	private LinearLayout spc4;
	private CardView cropperImageCard;
	private FadeEditText postDescription;
	private ImageView croppedImageView;
	private LinearLayout post_settings_statistics_top;
	private LinearLayout post_settings_statistics_middle;
	private ImageView post_settings_statistics_top_ic;
	private TextView post_settings_statistics_top_title;
	private ImageView post_settings_statistics_top_arrow;
	private LinearLayout post_settings_statistics_hide_views;
	private LinearLayout post_settings_statistics_hide_likes;
	private LinearLayout post_settings_statistics_hide_comments;
	private SwitchCompat post_settings_statistics_hide_views_switch;
	private TextView post_settings_statistics_hide_views_subtitle;
	private SwitchCompat post_settings_statistics_hide_likes_switch;
	private TextView post_settings_statistics_hide_likes_subtitle;
	private SwitchCompat post_settings_statistics_hide_comments_switch;
	private TextView post_settings_statistics_hide_comments_subtitle;
	private LinearLayout post_settings_privacy_top;
	private LinearLayout post_settings_privacy_middle;
	private ImageView post_settings_privacy_top_ic;
	private TextView post_settings_privacy_top_title;
	private ImageView post_settings_privacy_top_arrow;
	private LinearLayout post_settings_privacy_hide_post;
	private LinearLayout post_settings_privacy_disable_save;
	private LinearLayout post_settings_privacy_disable_comments;
	private SwitchCompat post_settings_privacy_hide_post_switch;
	private TextView post_settings_privacy_hide_post_subtitle;
	private SwitchCompat post_settings_privacy_disable_save_switch;
	private TextView post_settings_privacy_disable_save_subtitle;
	private SwitchCompat post_settings_privacy_disable_comments_switch;
	private TextView post_settings_privacy_disable_comments_subtitle;
	
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
	private DatabaseReference maindb = _firebase.getReference("skyline");
	private ChildEventListener _maindb_child_listener;
	private StorageReference post_image_storage_db = _firebase_storage.getReference("skyline");
	private OnCompleteListener<Uri> _post_image_storage_db_upload_success_listener;
	private OnSuccessListener<FileDownloadTask.TaskSnapshot> _post_image_storage_db_download_success_listener;
	private OnSuccessListener _post_image_storage_db_delete_success_listener;
	private OnProgressListener _post_image_storage_db_upload_progress_listener;
	private OnProgressListener _post_image_storage_db_download_progress_listener;
	private OnFailureListener _post_image_storage_db_failure_listener;
	private Calendar cc = Calendar.getInstance();
	private SharedPreferences appSavedData;
	private DatabaseReference fdb = _firebase.getReference("notify");
	private ChildEventListener _fdb_child_listener;
	private AlertDialog.Builder d;
	private ProgressDialog p;
	
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.create_image_post_next_step);
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
		main = findViewById(R.id.main);
		top = findViewById(R.id.top);
		topSpace = findViewById(R.id.topSpace);
		scroll = findViewById(R.id.scroll);
		back = findViewById(R.id.back);
		topSpc = findViewById(R.id.topSpc);
		continueButton = findViewById(R.id.continueButton);
		title = findViewById(R.id.title);
		scrollBody = findViewById(R.id.scrollBody);
		PostInfoTop1 = findViewById(R.id.PostInfoTop1);
		topSpace2 = findViewById(R.id.topSpace2);
		spc2 = findViewById(R.id.spc2);
		post_settings_statistics = findViewById(R.id.post_settings_statistics);
		spc3 = findViewById(R.id.spc3);
		post_settings_privacy = findViewById(R.id.post_settings_privacy);
		spc4 = findViewById(R.id.spc4);
		cropperImageCard = findViewById(R.id.cropperImageCard);
		postDescription = findViewById(R.id.postDescription);
		croppedImageView = findViewById(R.id.croppedImageView);
		post_settings_statistics_top = findViewById(R.id.post_settings_statistics_top);
		post_settings_statistics_middle = findViewById(R.id.post_settings_statistics_middle);
		post_settings_statistics_top_ic = findViewById(R.id.post_settings_statistics_top_ic);
		post_settings_statistics_top_title = findViewById(R.id.post_settings_statistics_top_title);
		post_settings_statistics_top_arrow = findViewById(R.id.post_settings_statistics_top_arrow);
		post_settings_statistics_hide_views = findViewById(R.id.post_settings_statistics_hide_views);
		post_settings_statistics_hide_likes = findViewById(R.id.post_settings_statistics_hide_likes);
		post_settings_statistics_hide_comments = findViewById(R.id.post_settings_statistics_hide_comments);
		post_settings_statistics_hide_views_switch = findViewById(R.id.post_settings_statistics_hide_views_switch);
		post_settings_statistics_hide_views_subtitle = findViewById(R.id.post_settings_statistics_hide_views_subtitle);
		post_settings_statistics_hide_likes_switch = findViewById(R.id.post_settings_statistics_hide_likes_switch);
		post_settings_statistics_hide_likes_subtitle = findViewById(R.id.post_settings_statistics_hide_likes_subtitle);
		post_settings_statistics_hide_comments_switch = findViewById(R.id.post_settings_statistics_hide_comments_switch);
		post_settings_statistics_hide_comments_subtitle = findViewById(R.id.post_settings_statistics_hide_comments_subtitle);
		post_settings_privacy_top = findViewById(R.id.post_settings_privacy_top);
		post_settings_privacy_middle = findViewById(R.id.post_settings_privacy_middle);
		post_settings_privacy_top_ic = findViewById(R.id.post_settings_privacy_top_ic);
		post_settings_privacy_top_title = findViewById(R.id.post_settings_privacy_top_title);
		post_settings_privacy_top_arrow = findViewById(R.id.post_settings_privacy_top_arrow);
		post_settings_privacy_hide_post = findViewById(R.id.post_settings_privacy_hide_post);
		post_settings_privacy_disable_save = findViewById(R.id.post_settings_privacy_disable_save);
		post_settings_privacy_disable_comments = findViewById(R.id.post_settings_privacy_disable_comments);
		post_settings_privacy_hide_post_switch = findViewById(R.id.post_settings_privacy_hide_post_switch);
		post_settings_privacy_hide_post_subtitle = findViewById(R.id.post_settings_privacy_hide_post_subtitle);
		post_settings_privacy_disable_save_switch = findViewById(R.id.post_settings_privacy_disable_save_switch);
		post_settings_privacy_disable_save_subtitle = findViewById(R.id.post_settings_privacy_disable_save_subtitle);
		post_settings_privacy_disable_comments_switch = findViewById(R.id.post_settings_privacy_disable_comments_switch);
		post_settings_privacy_disable_comments_subtitle = findViewById(R.id.post_settings_privacy_disable_comments_subtitle);
		vbr = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
		auth = FirebaseAuth.getInstance();
		appSavedData = getSharedPreferences("data", Activity.MODE_PRIVATE);
		d = new AlertDialog.Builder(this);
		
		back.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				onBackPressed();
			}
		});
		
		continueButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				if (getIntent().hasExtra("type")) {
					if (getIntent().getStringExtra("type").equals("local")) {
						if (getIntent().hasExtra("path")) {
							UniquePostKey = maindb.push().getKey();
							_LoadingDialog(true);
							ImageUploader.uploadImage(path, new ImageUploader.UploadCallback() {
								@Override
								public void onUploadComplete(String imageUrl) {
									cc = Calendar.getInstance();
									PostSendMap = new HashMap<>();
									PostSendMap.put("key", UniquePostKey);
									PostSendMap.put("uid", FirebaseAuth.getInstance().getCurrentUser().getUid());
									PostSendMap.put("post_type", "IMAGE");
									if (!postDescription.getText().toString().trim().equals("")) {
										PostSendMap.put("post_text", postDescription.getText().toString().trim());
									}
									PostSendMap.put("post_image", imageUrl);
									if (!appSavedData.contains("user_region_data") && appSavedData.getString("user_region_data", "").equals("none")) {
										PostSendMap.put("post_region", "none");
									} else {
										PostSendMap.put("post_region", appSavedData.getString("user_region_data", ""));
									}
									if (post_settings_statistics_hide_views_switch.isChecked()) {
										PostSendMap.put("post_hide_views_count", "true");
									} else {
										PostSendMap.put("post_hide_views_count", "false");
									}
									if (post_settings_statistics_hide_likes_switch.isChecked()) {
										PostSendMap.put("post_hide_like_count", "true");
									} else {
										PostSendMap.put("post_hide_like_count", "false");
									}
									if (post_settings_statistics_hide_comments_switch.isChecked()) {
										PostSendMap.put("post_hide_comments_count", "true");
									} else {
										PostSendMap.put("post_hide_comments_count", "false");
									}
									if (post_settings_privacy_hide_post_switch.isChecked()) {
										PostSendMap.put("post_visibility", "private");
									} else {
										PostSendMap.put("post_visibility", "public");
									}
									if (post_settings_privacy_disable_save_switch.isChecked()) {
										PostSendMap.put("post_disable_favorite", "true");
									} else {
										PostSendMap.put("post_disable_favorite", "false");
									}
									if (post_settings_privacy_disable_comments_switch.isChecked()) {
										PostSendMap.put("post_disable_comments", "true");
									} else {
										PostSendMap.put("post_disable_comments", "false");
									}
									PostSendMap.put("publish_date", String.valueOf((long)(cc.getTimeInMillis())));
									FirebaseDatabase.getInstance().getReference("skyline/posts").child(UniquePostKey).updateChildren(PostSendMap, new DatabaseReference.CompletionListener() {
										@Override
										public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
											if (databaseError == null) {
												SketchwareUtil.showMessage(getApplicationContext(), getResources().getString(R.string.post_publish_success));
												_LoadingDialog(false);
												finish();
											} else {
												SketchwareUtil.showMessage(getApplicationContext(), databaseError.getMessage());
												_LoadingDialog(false);
											}
										}
									});
									
								}
								
								@Override
								public void onUploadError(String errorMessage) {
									
									
									
									SketchwareUtil.showMessage(getApplicationContext(), "Something went wrong");
									_LoadingDialog(false);
								}
							});
							
						}
					} else {
						if (getIntent().hasExtra("path")) {
							_LoadingDialog(true);
							UniquePostKey = maindb.push().getKey();
							cc = Calendar.getInstance();
							PostSendMap = new HashMap<>();
							PostSendMap.put("key", UniquePostKey);
							PostSendMap.put("uid", FirebaseAuth.getInstance().getCurrentUser().getUid());
							PostSendMap.put("post_type", "IMAGE");
							if (!postDescription.getText().toString().trim().equals("")) {
								PostSendMap.put("post_text", postDescription.getText().toString().trim());
							}
							PostSendMap.put("post_image", getIntent().getStringExtra("path"));
							if (!appSavedData.contains("user_region_data") && appSavedData.getString("user_region_data", "").equals("none")) {
								PostSendMap.put("post_region", "none");
							} else {
								PostSendMap.put("post_region", appSavedData.getString("user_region_data", ""));
							}
							if (post_settings_statistics_hide_views_switch.isChecked()) {
								PostSendMap.put("post_hide_views_count", "true");
							} else {
								PostSendMap.put("post_hide_views_count", "false");
							}
							if (post_settings_statistics_hide_likes_switch.isChecked()) {
								PostSendMap.put("post_hide_like_count", "true");
							} else {
								PostSendMap.put("post_hide_like_count", "false");
							}
							if (post_settings_statistics_hide_comments_switch.isChecked()) {
								PostSendMap.put("post_hide_comments_count", "true");
							} else {
								PostSendMap.put("post_hide_comments_count", "false");
							}
							if (post_settings_privacy_hide_post_switch.isChecked()) {
								PostSendMap.put("post_visibility", "private");
							} else {
								PostSendMap.put("post_visibility", "public");
							}
							if (post_settings_privacy_disable_save_switch.isChecked()) {
								PostSendMap.put("post_disable_favorite", "true");
							} else {
								PostSendMap.put("post_disable_favorite", "false");
							}
							if (post_settings_privacy_disable_comments_switch.isChecked()) {
								PostSendMap.put("post_disable_comments", "true");
							} else {
								PostSendMap.put("post_disable_comments", "false");
							}
							PostSendMap.put("publish_date", String.valueOf((long)(cc.getTimeInMillis())));
							FirebaseDatabase.getInstance().getReference("skyline/posts").child(UniquePostKey).updateChildren(PostSendMap, new DatabaseReference.CompletionListener() {
								@Override
								public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
									if (databaseError == null) {
										SketchwareUtil.showMessage(getApplicationContext(), getResources().getString(R.string.post_publish_success));
										_LoadingDialog(false);
										finish();
									} else {
										SketchwareUtil.showMessage(getApplicationContext(), databaseError.getMessage());
										_LoadingDialog(false);
									}
								}
							});
							
						}
					}
				} else {
					if (getIntent().hasExtra("path")) {
						UniquePostKey = maindb.push().getKey();
						_LoadingDialog(true);
					}
				}
			}
		});
		
		postDescription.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence _param1, int _param2, int _param3, int _param4) {
				final String _charSeq = _param1.toString();
				_TransitionManager(PostInfoTop1, 130);
			}
			
			@Override
			public void beforeTextChanged(CharSequence _param1, int _param2, int _param3, int _param4) {
				
			}
			
			@Override
			public void afterTextChanged(Editable _param1) {
				
			}
		});
		
		post_settings_statistics_top.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				if (post_settings_statistics_middle.getVisibility() == View.GONE) {
					post_settings_statistics_middle.setVisibility(View.VISIBLE);
					post_settings_statistics_top_arrow.setImageResource(R.drawable.ic_expand_less_black);
				} else {
					post_settings_statistics_middle.setVisibility(View.GONE);
					post_settings_statistics_top_arrow.setImageResource(R.drawable.ic_keyboard_arrow_down_black);
				}
			}
		});
		
		post_settings_statistics_hide_views.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				if (post_settings_statistics_hide_views_switch.isChecked()) {
					post_settings_statistics_hide_views_switch.setChecked(false);
				} else {
					post_settings_statistics_hide_views_switch.setChecked(true);
				}
				vbr.vibrate((long)(48));
			}
		});
		
		post_settings_statistics_hide_likes.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				if (post_settings_statistics_hide_likes_switch.isChecked()) {
					post_settings_statistics_hide_likes_switch.setChecked(false);
				} else {
					post_settings_statistics_hide_likes_switch.setChecked(true);
				}
				vbr.vibrate((long)(48));
			}
		});
		
		post_settings_statistics_hide_comments.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				if (post_settings_statistics_hide_comments_switch.isChecked()) {
					post_settings_statistics_hide_comments_switch.setChecked(false);
				} else {
					post_settings_statistics_hide_comments_switch.setChecked(true);
				}
				vbr.vibrate((long)(48));
			}
		});
		
		post_settings_privacy_top.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				if (post_settings_privacy_middle.getVisibility() == View.GONE) {
					post_settings_privacy_middle.setVisibility(View.VISIBLE);
					post_settings_privacy_top_arrow.setImageResource(R.drawable.ic_expand_less_black);
				} else {
					post_settings_privacy_middle.setVisibility(View.GONE);
					post_settings_privacy_top_arrow.setImageResource(R.drawable.ic_keyboard_arrow_down_black);
				}
			}
		});
		
		post_settings_privacy_hide_post.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				if (post_settings_privacy_hide_post_switch.isChecked()) {
					post_settings_privacy_hide_post_switch.setChecked(false);
				} else {
					post_settings_privacy_hide_post_switch.setChecked(true);
				}
				vbr.vibrate((long)(48));
			}
		});
		
		post_settings_privacy_disable_save.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				if (post_settings_privacy_disable_save_switch.isChecked()) {
					post_settings_privacy_disable_save_switch.setChecked(false);
				} else {
					post_settings_privacy_disable_save_switch.setChecked(true);
				}
				vbr.vibrate((long)(48));
			}
		});
		
		post_settings_privacy_disable_comments.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				if (post_settings_privacy_disable_comments_switch.isChecked()) {
					post_settings_privacy_disable_comments_switch.setChecked(false);
				} else {
					post_settings_privacy_disable_comments_switch.setChecked(true);
				}
				vbr.vibrate((long)(48));
			}
		});
		
		post_settings_privacy_hide_post_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton _param1, boolean _param2) {
				final boolean _isChecked = _param2;
				if (_isChecked) {
					post_settings_privacy_disable_comments.setEnabled(false);
					post_settings_privacy_disable_comments_switch.setChecked(true);
				} else {
					post_settings_privacy_disable_comments.setEnabled(true);
					post_settings_privacy_disable_comments_switch.setChecked(false);
				}
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
		
		_post_image_storage_db_upload_progress_listener = new OnProgressListener<UploadTask.TaskSnapshot>() {
			@Override
			public void onProgress(UploadTask.TaskSnapshot _param1) {
				double _progressValue = (100.0 * _param1.getBytesTransferred()) / _param1.getTotalByteCount();
				
			}
		};
		
		_post_image_storage_db_download_progress_listener = new OnProgressListener<FileDownloadTask.TaskSnapshot>() {
			@Override
			public void onProgress(FileDownloadTask.TaskSnapshot _param1) {
				double _progressValue = (100.0 * _param1.getBytesTransferred()) / _param1.getTotalByteCount();
				
			}
		};
		
		_post_image_storage_db_upload_success_listener = new OnCompleteListener<Uri>() {
			@Override
			public void onComplete(Task<Uri> _param1) {
				final String _downloadUrl = _param1.getResult().toString();
				cc = Calendar.getInstance();
				PostSendMap = new HashMap<>();
				PostSendMap.put("key", UniquePostKey);
				PostSendMap.put("uid", FirebaseAuth.getInstance().getCurrentUser().getUid());
				PostSendMap.put("post_type", "IMAGE");
				if (!postDescription.getText().toString().trim().equals("")) {
					PostSendMap.put("post_text", postDescription.getText().toString().trim());
				}
				PostSendMap.put("post_image", _downloadUrl);
				if (!appSavedData.contains("user_region_data") && appSavedData.getString("user_region_data", "").equals("none")) {
					PostSendMap.put("post_region", "none");
				} else {
					PostSendMap.put("post_region", appSavedData.getString("user_region_data", ""));
				}
				if (post_settings_statistics_hide_views_switch.isChecked()) {
					PostSendMap.put("post_hide_views_count", "true");
				} else {
					PostSendMap.put("post_hide_views_count", "false");
				}
				if (post_settings_statistics_hide_likes_switch.isChecked()) {
					PostSendMap.put("post_hide_like_count", "true");
				} else {
					PostSendMap.put("post_hide_like_count", "false");
				}
				if (post_settings_statistics_hide_comments_switch.isChecked()) {
					PostSendMap.put("post_hide_comments_count", "true");
				} else {
					PostSendMap.put("post_hide_comments_count", "false");
				}
				if (post_settings_privacy_hide_post_switch.isChecked()) {
					PostSendMap.put("post_visibility", "private");
				} else {
					PostSendMap.put("post_visibility", "public");
				}
				if (post_settings_privacy_disable_save_switch.isChecked()) {
					PostSendMap.put("post_disable_favorite", "true");
				} else {
					PostSendMap.put("post_disable_favorite", "false");
				}
				if (post_settings_privacy_disable_comments_switch.isChecked()) {
					PostSendMap.put("post_disable_comments", "true");
				} else {
					PostSendMap.put("post_disable_comments", "false");
				}
				PostSendMap.put("publish_date", String.valueOf((long)(cc.getTimeInMillis())));
				FirebaseDatabase.getInstance().getReference("skyline/posts").child(UniquePostKey).updateChildren(PostSendMap, new DatabaseReference.CompletionListener() {
					@Override
					public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
						if (databaseError == null) {
							SketchwareUtil.showMessage(getApplicationContext(), getResources().getString(R.string.post_publish_success));
							_LoadingDialog(false);
							finish();
						} else {
							SketchwareUtil.showMessage(getApplicationContext(), databaseError.getMessage());
							_LoadingDialog(false);
						}
					}
				});
				
			}
		};
		
		_post_image_storage_db_download_success_listener = new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
			@Override
			public void onSuccess(FileDownloadTask.TaskSnapshot _param1) {
				final long _totalByteCount = _param1.getTotalByteCount();
				
			}
		};
		
		_post_image_storage_db_delete_success_listener = new OnSuccessListener() {
			@Override
			public void onSuccess(Object _param1) {
				
			}
		};
		
		_post_image_storage_db_failure_listener = new OnFailureListener() {
			@Override
			public void onFailure(Exception _param1) {
				final String _message = _param1.getMessage();
				_LoadingDialog(false);
				SketchwareUtil.showMessage(getApplicationContext(), _message);
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
		_setStatusBarColor(true, 0xFFFFFFFF, 0xFFFFFFFF);
		_viewGraphics(back, 0xFFFFFFFF, 0xFFE0E0E0, 300, 0, Color.TRANSPARENT);
		_viewGraphics(continueButton, 0xFF445E91, 0xFF445E91, 300, 0, Color.TRANSPARENT);
		cropperImageCard.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b, int c, int d) { this.setCornerRadius(a); this.setStroke(b, c); this.setColor(d); return this; } }.getIns((int)22, (int)2, 0xFFEEEEEE, 0xFFFFFFFF));
		postDescription.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b, int c, int d) { this.setCornerRadius(a); this.setStroke(b, c); this.setColor(d); return this; } }.getIns((int)28, (int)3, 0xFFEEEEEE, 0xFFFFFFFF));
		post_settings_statistics_middle.setVisibility(View.GONE);
		post_settings_privacy_middle.setVisibility(View.GONE);
		_viewGraphics(post_settings_statistics_top, 0xFFFFFFFF, 0xFFEEEEEE, 0, 0, Color.TRANSPARENT);
		_viewGraphics(post_settings_statistics_hide_views, 0xFFFFFFFF, 0xFFEEEEEE, 0, 0, Color.TRANSPARENT);
		_viewGraphics(post_settings_statistics_hide_likes, 0xFFFFFFFF, 0xFFEEEEEE, 0, 0, Color.TRANSPARENT);
		_viewGraphics(post_settings_statistics_hide_comments, 0xFFFFFFFF, 0xFFEEEEEE, 0, 0, Color.TRANSPARENT);
		_viewGraphics(post_settings_privacy_top, 0xFFFFFFFF, 0xFFEEEEEE, 0, 0, Color.TRANSPARENT);
		_viewGraphics(post_settings_privacy_hide_post, 0xFFFFFFFF, 0xFFEEEEEE, 0, 0, Color.TRANSPARENT);
		_viewGraphics(post_settings_privacy_disable_save, 0xFFFFFFFF, 0xFFEEEEEE, 0, 0, Color.TRANSPARENT);
		_viewGraphics(post_settings_privacy_disable_comments, 0xFFFFFFFF, 0xFFEEEEEE, 0, 0, Color.TRANSPARENT);
		if (getIntent().hasExtra("type")) {
			if (getIntent().getStringExtra("type").equals("local")) {
				Glide.with(getApplicationContext()).load(getIntent().getStringExtra("path")).into(croppedImageView);
			} else {
				Glide.with(getApplicationContext()).load(Uri.parse(getIntent().getStringExtra("path"))).into(croppedImageView);
			}
		} else {
			if (getIntent().hasExtra("path")) {
				Glide.with(getApplicationContext()).load(getIntent().getStringExtra("path")).into(croppedImageView);
			}
		}
		path = getIntent().getStringExtra("path");
	}
	
	@Override
	public void onBackPressed() {
		finish();
	}
	
	public void _setStatusBarColor(final boolean _isLight, final int _stateColor, final int _navigationColor) {
		if (_isLight) {
			getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
		}
		getWindow().setStatusBarColor(_stateColor);
		getWindow().setNavigationBarColor(_navigationColor);
	}
	
	
	public void _viewGraphics(final View _view, final int _onFocus, final int _onRipple, final double _radius, final double _stroke, final int _strokeColor) {
		android.graphics.drawable.GradientDrawable GG = new android.graphics.drawable.GradientDrawable();
		GG.setColor(_onFocus);
		GG.setCornerRadius((float)_radius);
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
	
	
	public void _TransitionManager(final View _view, final double _duration) {
		LinearLayout viewgroup =(LinearLayout) _view;
		
		android.transition.AutoTransition autoTransition = new android.transition.AutoTransition(); autoTransition.setDuration((long)_duration); android.transition.TransitionManager.beginDelayedTransition(viewgroup, autoTransition);
	}
	
}