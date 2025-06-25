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
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
//import androidmads.library.qrgenearator.*;
import androidx.annotation.*;
import androidx.appcompat.app.AppCompatActivity;
import androidx.asynclayoutinflater.*;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.interpolator.*;
import androidx.swiperefreshlayout.*;
import androidx.transition.*;
/*
import com.blogspot.atifsoftwares.animatoolib.*;
import com.budiyev.android.codescanner.*;
import com.canhub.cropper.*;
import com.caverock.androidsvg.*;
*/
import com.google.android.material.*;
import com.google.android.material.color.MaterialColors;
import com.google.firebase.FirebaseApp;
import com.google.firebase.appcheck.playintegrity.*;
import com.google.firebase.perf.*;
/*
import com.jsibbold.zoomage.*;
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
import io.noties.markwon.ext.tasklist.*;
*/
import java.io.*;
import java.text.*;
import java.util.*;
import java.util.regex.*;
//import kr.co.prnd.readmore.*;
//import me.dm7.barcodescanner.core.*;
import org.jetbrains.kotlin.*;
import org.json.*;
import java.io.*;

public class DebugActivity extends AppCompatActivity {
	
	private LinearLayout body;
	private TextView title;
	private TextView subtitle;
	private ScrollView scroll;
	private TextView sumbit;
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
		sumbit = findViewById(R.id.sumbit);
		close = findViewById(R.id.close);
		scroll_in_body = findViewById(R.id.scroll_in_body);
		error_text = findViewById(R.id.error_text);
		vbr = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
		
		sumbit.setOnClickListener(new View.OnClickListener() {
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
		_viewGraphics(sumbit, 0xFF445E91, 0xFF445E91, 300, 0, Color.TRANSPARENT);
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
	
	
	private int getMaterialColor(int resourceId) {
		    return MaterialColors.getColor(getWindow().getDecorView().getRootView(), resourceId);
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