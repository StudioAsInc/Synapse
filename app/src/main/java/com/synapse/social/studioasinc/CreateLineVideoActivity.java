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
import android.widget.TextView;
import androidx.annotation.*;
import androidx.appcompat.app.AppCompatActivity;
import androidx.browser.*;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import com.bumptech.glide.*;
import com.google.android.material.*;
import com.google.android.material.color.MaterialColors;
import com.google.firebase.FirebaseApp;
import com.theartofdev.edmodo.cropper.*;
import com.yalantis.ucrop.*;
import java.io.*;
import java.io.InputStream;
import java.text.*;
import java.util.*;
import java.util.regex.*;
import org.json.*;
import java.net.URL;
import java.net.MalformedURLException;

public class CreateLineVideoActivity extends AppCompatActivity {
	
	public final int REQ_CD_VIDEO_PICKER = 101;
	
	private ProgressDialog SynapseLoadingDialog;
	private String LocalVideoUri = null;
	private String UrlVideoUri = null;
	
	private LinearLayout main;
	private LinearLayout top;
	private LinearLayout body;
	private LinearLayout bottomButtons;
	private ImageView back;
	private TextView title;
	private LinearLayout topSpc;
	private TextView continueButton;
	private VideoView video_preview;
	private TextView selectVideo;
	private TextView addFromUrlBtn;
	
	private Intent intent = new Intent();
	private Intent VIDEO_PICKER = new Intent(Intent.ACTION_GET_CONTENT);
	private SharedPreferences Premium;
	
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.create_line_video);
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
		main = findViewById(R.id.main);
		top = findViewById(R.id.top);
		body = findViewById(R.id.body);
		bottomButtons = findViewById(R.id.bottomButtons);
		back = findViewById(R.id.back);
		title = findViewById(R.id.title);
		topSpc = findViewById(R.id.topSpc);
		continueButton = findViewById(R.id.continueButton);
		video_preview = findViewById(R.id.video_preview);
		MediaController video_preview_controller = new MediaController(this);
		video_preview.setMediaController(video_preview_controller);
		selectVideo = findViewById(R.id.selectVideo);
		addFromUrlBtn = findViewById(R.id.addFromUrlBtn);
		VIDEO_PICKER.setType("video/*");
		VIDEO_PICKER.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
		Premium = getSharedPreferences("Premium", Activity.MODE_PRIVATE);
		
		back.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				onBackPressed();
			}
		});
		
		continueButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				if (LocalVideoUri != null && UrlVideoUri == null) {
					intent.setClass(getApplicationContext(), CreateLineVideoNextStepActivity.class);
					intent.putExtra("type", "local");
					intent.putExtra("path", LocalVideoUri);
					startActivity(intent);
					finish();
				} else {
					intent.setClass(getApplicationContext(), CreateLineVideoNextStepActivity.class);
					intent.putExtra("type", "url");
					intent.putExtra("path", UrlVideoUri);
					startActivity(intent);
					finish();
				}
			}
		});
		
		video_preview.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
			@Override
			public void onPrepared(MediaPlayer _mediaPlayer) {
				video_preview.start();
			}
		});
		
		selectVideo.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				if (Premium.contains("premium")) {
					if (Premium.getString("premium", "").equals("true")) {
						if (Build.VERSION.SDK_INT >= 23) {
							if (checkSelfPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE) == android.content.pm.PackageManager.PERMISSION_DENIED || checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == android.content.pm.PackageManager.PERMISSION_DENIED) {
								requestPermissions(new String[] {android.Manifest.permission.READ_EXTERNAL_STORAGE,android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1000);
							} else {
								Intent sendVideoInt = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Video.Media.EXTERNAL_CONTENT_URI);
								startActivityForResult(sendVideoInt, REQ_CD_VIDEO_PICKER);
							}
						} else {
							Intent sendVideoInt = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Video.Media.EXTERNAL_CONTENT_URI);
							startActivityForResult(sendVideoInt, REQ_CD_VIDEO_PICKER);
						}
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
		
		addFromUrlBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				_AddFromUrlDialog();
			}
		});
	}
	
	private void initializeLogic() {
		_stateColor(0xFFFFFFFF, 0xFF000000);
		_viewGraphics(back, 0xFFFFFFFF, 0xFFE0E0E0, 300, 0, Color.TRANSPARENT);
		_viewGraphics(continueButton, getResources().getColor(R.color.colorPrimary), 0xFFE0E0E0, 300, 0, Color.TRANSPARENT);
		_viewGraphics(selectVideo, getResources().getColor(R.color.colorPrimary), 0xFF448AFF, 300, 0, Color.TRANSPARENT);
		_viewGraphics(addFromUrlBtn, getResources().getColor(R.color.colorPrimary), 0xFF388E3C, 300, 0, Color.TRANSPARENT);
	}
	
	@Override
	protected void onActivityResult(int _requestCode, int _resultCode, Intent _data) {
		super.onActivityResult(_requestCode, _resultCode, _data);
		
		switch (_requestCode) {
			case REQ_CD_VIDEO_PICKER:
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
				_setVideoUri(_filePath.get((int)(0)), false);
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
	
	
	public void _AddFromUrlDialog() {
		/*
{
final AlertDialog NewCustomDialog = new AlertDialog.Builder(CreateLineVideoActivity.this).create();
LayoutInflater NewCustomDialogLI = getLayoutInflater();
View NewCustomDialogCV = (View) NewCustomDialogLI.inflate(R.layout.create_image_post_add_from_url, null);
NewCustomDialog.setView(NewCustomDialogCV);
NewCustomDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

final androidx.cardview.widget.CardView dialog_card = NewCustomDialogCV.findViewById(R.id.dialog_card);
final EditText image_url_input = NewCustomDialogCV.findViewById(R.id.image_url_input);
final TextView add_button = NewCustomDialogCV.findViewById(R.id.add_button);
final TextView cancel_button = NewCustomDialogCV.findViewById(R.id.cancel_button);

dialog_card.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b) { this.setCornerRadius(a); this.setColor(b); return this; } }.getIns((int)28, 0xFFFFFFFF));
image_url_input.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b, int c, int d) { this.setCornerRadius(a); this.setStroke(b, c); this.setColor(d); return this; } }.getIns((int)28, (int)3, 0xFFEEEEEE, Color.TRANSPARENT));
_viewGraphics(add_button, 0xFF4CAF50, 0xFF388E3C, 300, 0, Color.TRANSPARENT);
_viewGraphics(cancel_button, 0xFFF5F5F5, 0xFFE0E0E0, 300, 0, Color.TRANSPARENT);
image_url_input.addTextChangedListener(new TextWatcher() {
	@Override
	public void onTextChanged(CharSequence _param1, int _param2, int _param3, int _param4) {
		final String _charSeq = _param1.toString();
		
		if (!_charSeq.trim().equals("")) {
			if (_checkValidUrl(_charSeq.trim())) {
                
			} else {
				((EditText)image_url_input).setError("Invalid URL");
			}
		} else {
			((EditText)image_url_input).setError("Enter Profile Image URL");
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
if (!image_url_input.getText().toString().trim().equals("")) {
if (_checkValidUrl(image_url_input.getText().toString().trim())) {
_setVideoUri(image_url_input.getText().toString().trim(), true);
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
*/
	}
	
	
	public void _setVideoUri(final String _path, final boolean _isUrl) {
		if (!_isUrl) {
			UrlVideoUri = null;
			LocalVideoUri = _path;
			video_preview.setVideoURI(Uri.parse(_path));
		} else {
			UrlVideoUri = _path;
			LocalVideoUri = null;
			video_preview.setVideoURI(Uri.parse(_path));
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
	
}