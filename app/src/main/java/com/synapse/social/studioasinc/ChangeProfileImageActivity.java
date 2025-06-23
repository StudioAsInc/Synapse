package com.synapse.social.studioasinc;

import android.Manifest;
import android.animation.*;
import android.app.*;
import android.content.*;
import android.content.ClipData;
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
import android.support.customtabs.*;
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
import android.widget.TextView;
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
import androidx.recyclerview.widget.*;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.Adapter;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;
import androidx.swiperefreshlayout.*;
import androidx.transition.*;
import com.blogspot.atifsoftwares.animatoolib.*;
import com.budiyev.android.codescanner.*;
import com.bumptech.glide.Glide;
import com.caverock.androidsvg.*;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
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
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.jsibbold.zoomage.*;
import com.shobhitpuri.custombuttons.*;
import com.sigma.niceswitch.*;
import com.theartofdev.edmodo.cropper.*;
import com.theartofdev.edmodo.cropper.CropImageView;
import com.theophrast.ui.widget.*;
import com.wuyr.rippleanimation.*;
import com.yalantis.ucrop.*;
import eightbitlab.com.blurview.*;
import io.noties.markwon.*;
import io.noties.markwon.ext.strikethrough.*;
import io.noties.markwon.ext.tables.*;
import io.noties.markwon.ext.tasklist.*;
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
import kr.co.prnd.readmore.*;
import me.dm7.barcodescanner.core.*;
import org.jetbrains.kotlin.*;
import org.json.*;
import android.content.ContentResolver;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.media.MediaMetadataRetriever;
import android.database.Cursor;
import android.provider.MediaStore;
import com.bumptech.glide.request.RequestOptions;

public class ChangeProfileImageActivity extends AppCompatActivity {
	
	public final int REQ_CD_IMAGE_PICKER = 101;
	
	private FirebaseDatabase _firebase = FirebaseDatabase.getInstance();
	private FirebaseStorage _firebase_storage = FirebaseStorage.getInstance();
	
	private ProgressDialog SynapseLoadingDialog;
	private void saveBitmapAsPng(Bitmap bitmap) throws IOException {
			_LoadingDialog(true);
			Calendar cc = Calendar.getInstance();
			
			File getCacheDir = getExternalCacheDir();
			String getCacheDirName = "cropped_images";
			File getCacheFolder = new File(getCacheDir, getCacheDirName);
			getCacheFolder.mkdirs();
			File getImageFile = new File(getCacheFolder, cc.getTimeInMillis() + ".png");
			String savedFilePath = getImageFile.getAbsolutePath();
			
			final FileOutputStream outStream;
			try {
					outStream = new FileOutputStream(getImageFile);
					final Bitmap finalBitmap = bitmap;
					
					new Thread(new Runnable() {
							@Override
							public void run() {
									try {
											finalBitmap.compress(Bitmap.CompressFormat.PNG, 100, outStream);
											outStream.flush();
											outStream.close();
											
											runOnUiThread(new Runnable() {
													@Override
													public void run() {
								                            _uploadAvatar(savedFilePath, maindb.push().getKey().concat(".jpg"));
													}
											});
									} catch (IOException e) {
											runOnUiThread(new Runnable() {
													@Override
													public void run() {
															_LoadingDialog(false);
															Toast.makeText(getApplicationContext(), "Something went wrong.", Toast.LENGTH_SHORT).show();
													}
											});
											e.printStackTrace();
									}
							}
					}).start();
					
			} catch (IOException e) {
					runOnUiThread(new Runnable() {
							@Override
							public void run() {
									_LoadingDialog(false);
									Toast.makeText(getApplicationContext(), "Something went wrong.", Toast.LENGTH_SHORT).show();
							}
					});
					throw e;
			}
	}
	
	private String StorageDB = "";
	private HashMap<String, Object> mSendMap = new HashMap<>();
	private HashMap<String, Object> mSendMap2 = new HashMap<>();
	
	private ArrayList<HashMap<String, Object>> imagesListMap = new ArrayList<>();
	
	private LinearLayout main;
	private LinearLayout top;
	private CropImageView cropImageView;
	private LinearLayout body;
	private ImageView back;
	private TextView title;
	private LinearLayout topSpc;
	private TextView continueButton;
	private RecyclerView imagesView;
	private LinearLayout bottomButtons;
	private TextView textview1;
	private TextView addFromUrl;
	
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
	private Intent IMAGE_PICKER = new Intent(Intent.ACTION_GET_CONTENT);
	private Calendar cclnd = Calendar.getInstance();
	
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.change_profile_image);
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
		cropImageView = findViewById(R.id.cropImageView);
		body = findViewById(R.id.body);
		back = findViewById(R.id.back);
		title = findViewById(R.id.title);
		topSpc = findViewById(R.id.topSpc);
		continueButton = findViewById(R.id.continueButton);
		imagesView = findViewById(R.id.imagesView);
		bottomButtons = findViewById(R.id.bottomButtons);
		textview1 = findViewById(R.id.textview1);
		addFromUrl = findViewById(R.id.addFromUrl);
		auth = FirebaseAuth.getInstance();
		IMAGE_PICKER.setType("image/*");
		IMAGE_PICKER.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
		
		back.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				onBackPressed();
			}
		});
		
		continueButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				try {
						saveBitmapAsPng(cropImageView.getCroppedImage());
				} catch (IOException e) {
						
				}
			}
		});
		
		textview1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				/*
Turned off
*/
				/*
if (Build.VERSION.SDK_INT >= 23) {
	if (checkSelfPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE) == android.content.pm.PackageManager.PERMISSION_DENIED || checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == android.content.pm.PackageManager.PERMISSION_DENIED) {
		requestPermissions(new String[] {android.Manifest.permission.READ_EXTERNAL_STORAGE,android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1000);
	} else {
		Intent sendImgInt = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(sendImgInt, REQ_CD_IMAGE_PICKER);
	}
} else {
	Intent sendImgInt = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
    startActivityForResult(sendImgInt, REQ_CD_IMAGE_PICKER);
}
*/
			}
		});
		
		addFromUrl.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				
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
				mSendMap = new HashMap<>();
				mSendMap.put("avatar", _downloadUrl);
				mSendMap.put("avatar_history_type", "local");
				maindb.child("skyline/users/".concat(FirebaseAuth.getInstance().getCurrentUser().getUid())).updateChildren(mSendMap);
				String ProfileHistoryKey = maindb.push().getKey();
				mSendMap2 = new HashMap<>();
				mSendMap2.put("key", ProfileHistoryKey);
				mSendMap2.put("image_url", _downloadUrl);
				mSendMap2.put("upload_date", String.valueOf((long)(cclnd.getTimeInMillis())));
				mSendMap2.put("type", "local");
				maindb.child("skyline/profile-history/".concat(FirebaseAuth.getInstance().getCurrentUser().getUid().concat("/".concat(ProfileHistoryKey)))).updateChildren(mSendMap2);
				SketchwareUtil.showMessage(getApplicationContext(), "Profile Photo Updated");
				_LoadingDialog(false);
				finish();
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
		Display display = getWindowManager().getDefaultDisplay();
		int screenHeight = display.getHeight();
		int desiredHeight = screenHeight * 1 / 2 - 24;
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, desiredHeight);
		
		cropImageView.setLayoutParams(params);
		_stateColor(0xFFFFFFFF, 0xFFFFFFFF);
		_viewGraphics(back, 0xFFFFFFFF, 0xFFE0E0E0, 300, 0, Color.TRANSPARENT);
		_viewGraphics(continueButton, getResources().getColor(R.color.colorPrimary), getResources().getColor(R.color.colorPrimary), 300, 0, Color.TRANSPARENT);
		imagesView.setAdapter(new ImagesViewAdapter(imagesListMap));
		GridLayoutManager imagesViewGridLayout = new GridLayoutManager(this, 4);
		imagesView.setLayoutManager(imagesViewGridLayout);
		if (Build.VERSION.SDK_INT >= 23) {
				if (checkSelfPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE) == android.content.pm.PackageManager.PERMISSION_DENIED || checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == android.content.pm.PackageManager.PERMISSION_DENIED) {
						requestPermissions(new String[] {android.Manifest.permission.READ_EXTERNAL_STORAGE,android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1000);
				} else {
				        _getImageFiles();
				}
		} else {
			    _getImageFiles();
		}
		StorageDB = "skyline/users/".concat(FirebaseAuth.getInstance().getCurrentUser().getUid().concat("/avatars"));
		storage = _firebase_storage.getReference(StorageDB);
		cropImageView.setAspectRatio(1, 1);
		cropImageView.setFixedAspectRatio(true);
		_viewGraphics(textview1, getResources().getColor(R.color.colorPrimary), 0xFF1565C0, 300, 0, Color.TRANSPARENT);
		_viewGraphics(addFromUrl, getResources().getColor(R.color.colorPrimary), 0xFF388E3C, 300, 0, Color.TRANSPARENT);
	}
	
	@Override
	protected void onActivityResult(int _requestCode, int _resultCode, Intent _data) {
		super.onActivityResult(_requestCode, _resultCode, _data);
		
		switch (_requestCode) {
			case REQ_CD_IMAGE_PICKER:
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
				if (_filePath.get((int)(0)).endsWith(".png") || (_filePath.get((int)(0)).endsWith(".jpg") || _filePath.get((int)(0)).endsWith(".jpeg"))) {
					_setCropImage(_filePath.get((int)(0)));
				} else {
					SketchwareUtil.showMessage(getApplicationContext(), "Invalid File Type");
				}
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
	
	public void _stateColor(final int _statusColor, final int _navigationColor) {
		getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
		getWindow().setStatusBarColor(_statusColor);
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
	
	
	public void _ImageColor(final ImageView _image, final int _color) {
		_image.setColorFilter(_color,PorterDuff.Mode.SRC_ATOP);
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
	
	
	public void _getImageFiles() {
		String[] projection = {
			    MediaStore.Images.Media.DATA
		};
		Cursor imagesCursor = getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,projection,null,null,null);
		
		if (imagesCursor != null && imagesCursor.moveToFirst()) {
				do {
						String imagePath = imagesCursor.getString(imagesCursor.getColumnIndex(MediaStore.Images.Media.DATA));
						if (imagePath.endsWith(".png") || (imagePath.endsWith(".jpg") || imagePath.endsWith(".jpeg"))) {
								HashMap<String, Object> mediaItem = new HashMap<>();
								mediaItem.put("type", "Image");
								mediaItem.put("path", imagePath);
								imagesListMap.add(mediaItem);
						}
				} while (imagesCursor.moveToNext());
				imagesCursor.close();
		}
		
		imagesView.getAdapter().notifyDataSetChanged();
		java.io.File file = new java.io.File(imagesListMap.get((int)0).get("path").toString());
		Uri uri = Uri.fromFile(file);
		cropImageView.setImageUriAsync(uri);
	}
	
	
	public void _setCropImage(final String _path) {
		java.io.File file = new java.io.File(_path);
		Uri uri = Uri.fromFile(file);
		cropImageView.setImageUriAsync(uri);
	}
	
	
	public void _uploadAvatar(final String _uri, final String _name) {
		storage.child(_name).putFile(Uri.fromFile(new File(_uri))).addOnFailureListener(_storage_failure_listener).addOnProgressListener(_storage_upload_progress_listener).continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
			@Override
			public Task<Uri> then(Task<UploadTask.TaskSnapshot> task) throws Exception {
				return storage.child(_name).getDownloadUrl();
			}}).addOnCompleteListener(_storage_upload_success_listener);
	}
	
	public class ImagesViewAdapter extends RecyclerView.Adapter<ImagesViewAdapter.ViewHolder> {
		
		ArrayList<HashMap<String, Object>> _data;
		
		public ImagesViewAdapter(ArrayList<HashMap<String, Object>> _arr) {
			_data = _arr;
		}
		
		@Override
		public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
			LayoutInflater _inflater = getLayoutInflater();
			View _v = _inflater.inflate(R.layout.synapse_create_img_post_image_cv, null);
			RecyclerView.LayoutParams _lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
			_v.setLayoutParams(_lp);
			return new ViewHolder(_v);
		}
		
		@Override
		public void onBindViewHolder(ViewHolder _holder, final int _position) {
			View _view = _holder.itemView;
			
			final RelativeLayout relative = _view.findViewById(R.id.relative);
			final androidx.cardview.widget.CardView main = _view.findViewById(R.id.main);
			final LinearLayout relativeTop = _view.findViewById(R.id.relativeTop);
			final com.theophrast.ui.widget.SquareImageView image = _view.findViewById(R.id.image);
			final LinearLayout relativeSpc = _view.findViewById(R.id.relativeSpc);
			final ImageView typeIcon = _view.findViewById(R.id.typeIcon);
			final LinearLayout rvtSpc = _view.findViewById(R.id.rvtSpc);
			final TextView mediaDuration = _view.findViewById(R.id.mediaDuration);
			
			_ImageColor(typeIcon, 0xFFFFFFFF);
			typeIcon.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b) { this.setCornerRadius(a); this.setColor(b); return this; } }.getIns((int)24, 0x7B000000));
			mediaDuration.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b) { this.setCornerRadius(a); this.setColor(b); return this; } }.getIns((int)24, 0x7B000000));
			Glide.with(getApplicationContext()).load(_data.get((int)_position).get("path").toString()).into(image);
			typeIcon.setImageResource(R.drawable.image_ic);
			mediaDuration.setVisibility(View.GONE);
			relative.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View _view) {
					_setCropImage(_data.get((int)_position).get("path").toString());
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