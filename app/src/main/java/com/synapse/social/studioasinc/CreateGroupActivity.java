package com.synapse.social.studioasinc;
import android.Manifest;
import android.animation.*;
import android.app.*;
import android.app.Activity;
import android.content.*;
import android.content.ClipData;
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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
//import androidmads.library.qrgenearator.*;
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
//import com.blogspot.atifsoftwares.animatoolib.*;
//import com.budiyev.android.codescanner.*;
import com.caverock.androidsvg.*;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.*;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
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
/* import com.jsibbold.zoomage.*;
import com.shobhitpuri.custombuttons.*;
import com.sigma.niceswitch.*;
import com.theartofdev.edmodo.cropper.*;
import com.theophrast.ui.widget.*;
import com.wuyr.rippleanimation.*;
import com.yalantis.ucrop.*;
import eightbitlab.com.blurview.*;
import io.noties.markwon.*;
import io.noties.markwon.ext.strikethrough.*;
import io.noties.markwon.ext.tables.*;
import io.noties.markwon.ext.tasklist.*; */
import java.io.*;
import java.io.File;
import java.text.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.*;
import kr.co.prnd.readmore.*;
/*
import me.dm7.barcodescanner.core.*;
import org.jetbrains.kotlin.*;
*/
import org.json.*;

public class CreateGroupActivity extends AppCompatActivity {
	
	public final int REQ_CD_FP = 101;
	
	private Timer _timer = new Timer();
	private FirebaseDatabase _firebase = FirebaseDatabase.getInstance();
	private FirebaseStorage _firebase_storage = FirebaseStorage.getInstance();
	
	private ProgressDialog SynapseLoadingDialog;
	private FloatingActionButton _fab;
	private String group_key = "";
	private HashMap<String, Object> map = new HashMap<>();
	private String group_image = "";
	private String file = "";
	private String filename = "";
	
	private ScrollView vscroll3;
	private LinearLayout body;
	private ImageView back;
	private TextView title;
	private TextView subtitle;
	private ImageView group_picture;
	private EditText group_name;
	private EditText group_describtion;
	
	private DatabaseReference groups = _firebase.getReference("synapse/groups");
	private ChildEventListener _groups_child_listener;
	private FirebaseAuth OAuth;
	private OnCompleteListener<AuthResult> _OAuth_create_user_listener;
	private OnCompleteListener<AuthResult> _OAuth_sign_in_listener;
	private OnCompleteListener<Void> _OAuth_reset_password_listener;
	private OnCompleteListener<Void> OAuth_updateEmailListener;
	private OnCompleteListener<Void> OAuth_updatePasswordListener;
	private OnCompleteListener<Void> OAuth_emailVerificationSentListener;
	private OnCompleteListener<Void> OAuth_deleteUserListener;
	private OnCompleteListener<Void> OAuth_updateProfileListener;
	private OnCompleteListener<AuthResult> OAuth_phoneAuthListener;
	private OnCompleteListener<AuthResult> OAuth_googleSignInListener;
	private DatabaseReference main = _firebase.getReference("synapse");
	private ChildEventListener _main_child_listener;
	private RequestNetwork net;
	private RequestNetwork.RequestListener _net_request_listener;
	private StorageReference uploadGroupPhoto = _firebase_storage.getReference("synapse/group_photos");
	private OnCompleteListener<Uri> _uploadGroupPhoto_upload_success_listener;
	private OnSuccessListener<FileDownloadTask.TaskSnapshot> _uploadGroupPhoto_download_success_listener;
	private OnSuccessListener _uploadGroupPhoto_delete_success_listener;
	private OnProgressListener _uploadGroupPhoto_upload_progress_listener;
	private OnProgressListener _uploadGroupPhoto_download_progress_listener;
	private OnFailureListener _uploadGroupPhoto_failure_listener;
	private Intent fp = new Intent(Intent.ACTION_GET_CONTENT);
	private TimerTask After;
	private Calendar c = Calendar.getInstance();
	private SharedPreferences Premium;
	
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.create_group);
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
		vscroll3 = findViewById(R.id.vscroll3);
		body = findViewById(R.id.body);
		back = findViewById(R.id.back);
		title = findViewById(R.id.title);
		subtitle = findViewById(R.id.subtitle);
		group_picture = findViewById(R.id.group_picture);
		group_name = findViewById(R.id.group_name);
		group_describtion = findViewById(R.id.group_describtion);
		OAuth = FirebaseAuth.getInstance();
		net = new RequestNetwork(this);
		fp.setType("image/*");
		fp.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
		Premium = getSharedPreferences("Premium", Activity.MODE_PRIVATE);
		
		group_picture.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				startActivityForResult(fp, REQ_CD_FP);
			}
		});
		
		_fab.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				if (Premium.contains("premium")) {
					if (Premium.getString("premium", "").equals("true")) {
						net.startRequestNetwork(RequestNetworkController.POST, "https://google.com", "google", _net_request_listener);
					} else {
						if (Premium.getString("premium", "").equals("false")) {
							SketchwareUtil.showMessage(getApplicationContext(), "Buy premium to use this feature");
						} else {
							SketchwareUtil.showMessage(getApplicationContext(), "Something went wrong");
						}
					}
				} else {
					Premium.edit().putString("premium", "false").commit();
					SketchwareUtil.showMessage(getApplicationContext(), "This is strange");
				}
			}
		});
		
		_groups_child_listener = new ChildEventListener() {
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
		groups.addChildEventListener(_groups_child_listener);
		
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
		
		_net_request_listener = new RequestNetwork.RequestListener() {
			@Override
			public void onResponse(String _param1, String _param2, HashMap<String, Object> _param3) {
				final String _tag = _param1;
				final String _response = _param2;
				final HashMap<String, Object> _responseHeaders = _param3;
				c = Calendar.getInstance();
				group_key = groups.push().getKey();
				map = new HashMap<>();
				map.put("group_name", group_name.getText().toString());
				map.put("about_group", group_describtion.getText().toString());
				map.put("group_key", group_key);
				map.put("group_image", "https://media.giphy.com/media/v1.Y2lkPTc5MGI3NjExaG56dGJucHV4dXkxZGUzOGttNGx3M2M2ZzhtOW00ejdzZ3g1ZHk1byZlcD12MV9naWZzX3NlYXJjaCZjdD1n/X8V1x7aEdWMidFRn7o/giphy.gif");
				map.put("group_password", "null");
				map.put("creator", FirebaseAuth.getInstance().getCurrentUser().getUid());
				map.put("created_on", String.valueOf((long)(c.getTimeInMillis())));
				groups.child(group_key).updateChildren(map);
				map.clear();
				group_describtion.setText("");
				group_name.setText("");
				After = new TimerTask() {
					@Override
					public void run() {
						runOnUiThread(new Runnable() {
							@Override
							public void run() {
								_LoadingDialog(false);
								SketchwareUtil.hideKeyboard(getApplicationContext(), getWindow().getDecorView());
								finish();
							}
						});
					}
				};
				_timer.schedule(After, (int)(700));
			}
			
			@Override
			public void onErrorResponse(String _param1, String _param2) {
				final String _tag = _param1;
				final String _message = _param2;
				
			}
		};
		
		_uploadGroupPhoto_upload_progress_listener = new OnProgressListener<UploadTask.TaskSnapshot>() {
			@Override
			public void onProgress(UploadTask.TaskSnapshot _param1) {
				double _progressValue = (100.0 * _param1.getBytesTransferred()) / _param1.getTotalByteCount();
				SketchwareUtil.showMessage(getApplicationContext(), String.valueOf((long)(_progressValue)));
				_LoadingDialog(true);
			}
		};
		
		_uploadGroupPhoto_download_progress_listener = new OnProgressListener<FileDownloadTask.TaskSnapshot>() {
			@Override
			public void onProgress(FileDownloadTask.TaskSnapshot _param1) {
				double _progressValue = (100.0 * _param1.getBytesTransferred()) / _param1.getTotalByteCount();
				
			}
		};
		
		_uploadGroupPhoto_upload_success_listener = new OnCompleteListener<Uri>() {
			@Override
			public void onComplete(Task<Uri> _param1) {
				final String _downloadUrl = _param1.getResult().toString();
				c = Calendar.getInstance();
				group_key = groups.push().getKey();
				map = new HashMap<>();
				map.put("group_name", group_name.getText().toString());
				map.put("about_group", group_describtion.getText().toString());
				map.put("group_key", group_key);
				map.put("group_image", _downloadUrl);
				map.put("group_password", "null");
				map.put("creator", FirebaseAuth.getInstance().getCurrentUser().getUid());
				map.put("created_on", String.valueOf((long)(c.getTimeInMillis())));
				groups.child(group_key).updateChildren(map);
				map.clear();
				group_describtion.setText("");
				group_name.setText("");
				After = new TimerTask() {
					@Override
					public void run() {
						runOnUiThread(new Runnable() {
							@Override
							public void run() {
								_LoadingDialog(false);
								SketchwareUtil.hideKeyboard(getApplicationContext(), getWindow().getDecorView());
								finish();
							}
						});
					}
				};
				_timer.schedule(After, (int)(700));
			}
		};
		
		_uploadGroupPhoto_download_success_listener = new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
			@Override
			public void onSuccess(FileDownloadTask.TaskSnapshot _param1) {
				final long _totalByteCount = _param1.getTotalByteCount();
				
			}
		};
		
		_uploadGroupPhoto_delete_success_listener = new OnSuccessListener() {
			@Override
			public void onSuccess(Object _param1) {
				
			}
		};
		
		_uploadGroupPhoto_failure_listener = new OnFailureListener() {
			@Override
			public void onFailure(Exception _param1) {
				final String _message = _param1.getMessage();
				
			}
		};
		
		OAuth_updateEmailListener = new OnCompleteListener<Void>() {
			@Override
			public void onComplete(Task<Void> _param1) {
				final boolean _success = _param1.isSuccessful();
				final String _errorMessage = _param1.getException() != null ? _param1.getException().getMessage() : "";
				
			}
		};
		
		OAuth_updatePasswordListener = new OnCompleteListener<Void>() {
			@Override
			public void onComplete(Task<Void> _param1) {
				final boolean _success = _param1.isSuccessful();
				final String _errorMessage = _param1.getException() != null ? _param1.getException().getMessage() : "";
				
			}
		};
		
		OAuth_emailVerificationSentListener = new OnCompleteListener<Void>() {
			@Override
			public void onComplete(Task<Void> _param1) {
				final boolean _success = _param1.isSuccessful();
				final String _errorMessage = _param1.getException() != null ? _param1.getException().getMessage() : "";
				
			}
		};
		
		OAuth_deleteUserListener = new OnCompleteListener<Void>() {
			@Override
			public void onComplete(Task<Void> _param1) {
				final boolean _success = _param1.isSuccessful();
				final String _errorMessage = _param1.getException() != null ? _param1.getException().getMessage() : "";
				
			}
		};
		
		OAuth_phoneAuthListener = new OnCompleteListener<AuthResult>() {
			@Override
			public void onComplete(Task<AuthResult> task) {
				final boolean _success = task.isSuccessful();
				final String _errorMessage = task.getException() != null ? task.getException().getMessage() : "";
				
			}
		};
		
		OAuth_updateProfileListener = new OnCompleteListener<Void>() {
			@Override
			public void onComplete(Task<Void> _param1) {
				final boolean _success = _param1.isSuccessful();
				final String _errorMessage = _param1.getException() != null ? _param1.getException().getMessage() : "";
				
			}
		};
		
		OAuth_googleSignInListener = new OnCompleteListener<AuthResult>() {
			@Override
			public void onComplete(Task<AuthResult> task) {
				final boolean _success = task.isSuccessful();
				final String _errorMessage = task.getException() != null ? task.getException().getMessage() : "";
				
			}
		};
		
		_OAuth_create_user_listener = new OnCompleteListener<AuthResult>() {
			@Override
			public void onComplete(Task<AuthResult> _param1) {
				final boolean _success = _param1.isSuccessful();
				final String _errorMessage = _param1.getException() != null ? _param1.getException().getMessage() : "";
				
			}
		};
		
		_OAuth_sign_in_listener = new OnCompleteListener<AuthResult>() {
			@Override
			public void onComplete(Task<AuthResult> _param1) {
				final boolean _success = _param1.isSuccessful();
				final String _errorMessage = _param1.getException() != null ? _param1.getException().getMessage() : "";
				
			}
		};
		
		_OAuth_reset_password_listener = new OnCompleteListener<Void>() {
			@Override
			public void onComplete(Task<Void> _param1) {
				final boolean _success = _param1.isSuccessful();
				
			}
		};
	}
	
	private void initializeLogic() {
		getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
		getWindow().setStatusBarColor(0xFFFFFFFF);
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
				file = _filePath.get((int)(0));
				filename = Uri.parse(file).getLastPathSegment();
				group_picture.setImageBitmap(FileUtil.decodeSampleBitmapFromPath(_filePath.get((int)(0)), 1024, 1024));
			}
			else {
				
			}
			break;
			default:
			break;
		}
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
