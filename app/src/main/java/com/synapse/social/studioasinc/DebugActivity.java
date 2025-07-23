package com.synapse.social.studioasinc;

import android.animation.*;
import android.app.*;
import android.content.*;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.res.*;
import android.graphics.*;
import android.graphics.Typeface;
import android.graphics.drawable.*;
import android.media.*;
import android.net.*;
import android.os.*;
import android.os.Vibrator;
import android.text.*;
import android.text.style.*;
import android.util.*;
import android.view.*;
import android.view.View;
import android.view.View.*;
import android.view.animation.*;
import android.webkit.*;
import android.widget.*;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import androidx.annotation.*;
import androidx.appcompat.app.AppCompatActivity;
import androidx.browser.*;
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
import java.text.*;
import java.util.*;
import java.util.regex.*;
import org.json.*;
import java.io.*;


public class DebugActivity extends AppCompatActivity {
	
	private LinearLayout body;
	private TextView title;
	private TextView subtitle;
	private ScrollView scroll;
	private Button clearData_btn;
	private TextView close;
	private LinearLayout scroll_in_body;
	private TextView error_text;
	
	private Vibrator vbr;
	
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.debug);
		initialize(_savedInstanceState);
		FirebaseApp.initializeApp(this);
		initializeLogic();
	}
	
	private void initialize(Bundle _savedInstanceState) {
		body = findViewById(R.id.body);
		title = findViewById(R.id.title);
		subtitle = findViewById(R.id.subtitle);
		scroll = findViewById(R.id.scroll);
		clearData_btn = findViewById(R.id.clearData_btn);
		close = findViewById(R.id.close);
		scroll_in_body = findViewById(R.id.scroll_in_body);
		error_text = findViewById(R.id.error_text);
		vbr = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
		
		clearData_btn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				clearData();
			}
		});
		
		close.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				finishAffinity();
			}
		});
		
		error_text.setOnLongClickListener(new View.OnLongClickListener() {
			@Override
			public boolean onLongClick(View _view) {
				((ClipboardManager) getSystemService(getApplicationContext().CLIPBOARD_SERVICE)).setPrimaryClip(ClipData.newPlainText("clipboard", error_text.getText().toString()));
				SketchwareUtil.showMessage(getApplicationContext(), "Error code copied");
				vbr.vibrate((long)(48));
				return true;
			}
		});
	}
	
	private void initializeLogic() {
		_stateColor(0xFFFFFFFF, 0xFFFFFFFF);
		if (getIntent().hasExtra("error")) {
			error_text.setText(getIntent().getStringExtra("error"));
		}
		_viewGraphics(scroll, 0xFFF5F5F5, 0xFFEEEEEE, 50, 0, Color.TRANSPARENT);
		_viewGraphics(close, 0xFFFFFFFF, 0xFFEEEEEE, 300, 0, 0xFFEEEEEE);
		title.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/google_bold_old.ttf"), 1);
		subtitle.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/appfont.ttf"), 0);
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
	
	
	public void _extra() {
	}
	private void clearData() {
		try {
			if (Build.VERSION_CODES.KITKAT <= Build.VERSION.SDK_INT) {
				((ActivityManager)getSystemService(ACTIVITY_SERVICE)).clearApplicationUserData();
			} else {
				Runtime.getRuntime().exec("pm clear " + getApplicationContext().getPackageName());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	{
	}
	
}