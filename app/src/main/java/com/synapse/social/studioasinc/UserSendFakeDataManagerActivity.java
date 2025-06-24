package com.synapse.social.studioasinc;
import android.animation.*;
import android.app.*;
import android.content.*;
import android.content.res.*;
import android.graphics.*;
import android.graphics.drawable.*;
import android.media.*;
import android.net.*;
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
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.interpolator.*;
import androidx.swiperefreshlayout.*;
import androidx.transition.*;
/*
import com.blogspot.atifsoftwares.animatoolib.*;
import com.budiyev.android.codescanner.*;
import com.caverock.androidsvg.*;
*/
import com.google.android.material.*;
import com.google.firebase.FirebaseApp;
import com.google.firebase.appcheck.playintegrity.*;
import com.google.firebase.perf.*;
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
import java.text.*;
import java.util.*;
import java.util.regex.*;
import kr.co.prnd.readmore.*;
/*
import me.dm7.barcodescanner.core.*;
import org.jetbrains.kotlin.*;
*/
import org.json.*;

public class UserSendFakeDataManagerActivity extends AppCompatActivity {
	
	private LinearLayout body;
	private LinearLayout top;
	private ScrollView scroll_bar;
	private ImageView mBack;
	private TextView mTitle;
	private ImageView spc;
	private LinearLayout scroll_bar_body;
	private LinearLayout info_layout;
	private TextView enter_user_uid_title;
	private TextView enter_user_uid_subtext;
	private LinearLayout enter_user_uid_stage;
	private TextView send_fake_followers_title;
	private LinearLayout send_fake_followers_stage;
	private TextView send_fake_followed_title;
	private LinearLayout send_fake_followed_stage;
	private TextView send_fake_profile_likes;
	private LinearLayout send_fake_profile_likes_stage;
	private TextView info_layout_title;
	private TextView info_layout_subtext;
	private EditText enter_user_uid_stage_uid;
	private EditText send_fake_followers_stage_count;
	private TextView send_fake_followers_stage_send;
	private EditText send_fake_followed_stage_count;
	private TextView send_fake_followed_stage_send;
	private EditText send_fake_profile_likes_stage_count;
	private TextView send_fake_profile_likes_stage_send;
	
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.user_send_fake_data_manager);
		initialize(_savedInstanceState);
		FirebaseApp.initializeApp(this);
		initializeLogic();
	}
	
	private void initialize(Bundle _savedInstanceState) {
		body = findViewById(R.id.body);
		top = findViewById(R.id.top);
		scroll_bar = findViewById(R.id.scroll_bar);
		mBack = findViewById(R.id.mBack);
		mTitle = findViewById(R.id.mTitle);
		spc = findViewById(R.id.spc);
		scroll_bar_body = findViewById(R.id.scroll_bar_body);
		info_layout = findViewById(R.id.info_layout);
		enter_user_uid_title = findViewById(R.id.enter_user_uid_title);
		enter_user_uid_subtext = findViewById(R.id.enter_user_uid_subtext);
		enter_user_uid_stage = findViewById(R.id.enter_user_uid_stage);
		send_fake_followers_title = findViewById(R.id.send_fake_followers_title);
		send_fake_followers_stage = findViewById(R.id.send_fake_followers_stage);
		send_fake_followed_title = findViewById(R.id.send_fake_followed_title);
		send_fake_followed_stage = findViewById(R.id.send_fake_followed_stage);
		send_fake_profile_likes = findViewById(R.id.send_fake_profile_likes);
		send_fake_profile_likes_stage = findViewById(R.id.send_fake_profile_likes_stage);
		info_layout_title = findViewById(R.id.info_layout_title);
		info_layout_subtext = findViewById(R.id.info_layout_subtext);
		enter_user_uid_stage_uid = findViewById(R.id.enter_user_uid_stage_uid);
		send_fake_followers_stage_count = findViewById(R.id.send_fake_followers_stage_count);
		send_fake_followers_stage_send = findViewById(R.id.send_fake_followers_stage_send);
		send_fake_followed_stage_count = findViewById(R.id.send_fake_followed_stage_count);
		send_fake_followed_stage_send = findViewById(R.id.send_fake_followed_stage_send);
		send_fake_profile_likes_stage_count = findViewById(R.id.send_fake_profile_likes_stage_count);
		send_fake_profile_likes_stage_send = findViewById(R.id.send_fake_profile_likes_stage_send);
		
		mBack.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				onBackPressed();
			}
		});
		
		send_fake_followers_stage_send.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				
			}
		});
		
		send_fake_followed_stage_send.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				
			}
		});
		
		send_fake_profile_likes_stage_send.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				
			}
		});
	}
	
	private void initializeLogic() {
		_stateColor(0xFFFFFFFF, 0xFFFFFFFF);
		top.setElevation((float)4);
		_viewGraphics(mBack, 0xFFFFFFFF, 0xFFEEEEEE, 300, 0, Color.TRANSPARENT);
		info_layout.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b) { this.setCornerRadius(a); this.setColor(b); return this; } }.getIns((int)28, 0xFFFFFFFF));
		enter_user_uid_stage.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b) { this.setCornerRadius(a); this.setColor(b); return this; } }.getIns((int)48, 0xFFFFFFFF));
		enter_user_uid_stage_uid.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b) { this.setCornerRadius(a); this.setColor(b); return this; } }.getIns((int)28, 0xFFF5F5F5));
		send_fake_followers_stage.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b) { this.setCornerRadius(a); this.setColor(b); return this; } }.getIns((int)48, 0xFFFFFFFF));
		send_fake_followers_stage_count.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b) { this.setCornerRadius(a); this.setColor(b); return this; } }.getIns((int)28, 0xFFF5F5F5));
		_viewGraphics(send_fake_followers_stage_send, 0xFF000000, 0xFF616161, 300, 0, Color.TRANSPARENT);
		send_fake_followed_stage.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b) { this.setCornerRadius(a); this.setColor(b); return this; } }.getIns((int)48, 0xFFFFFFFF));
		send_fake_followed_stage_count.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b) { this.setCornerRadius(a); this.setColor(b); return this; } }.getIns((int)28, 0xFFF5F5F5));
		_viewGraphics(send_fake_followed_stage_send, 0xFF000000, 0xFF616161, 300, 0, Color.TRANSPARENT);
		send_fake_profile_likes_stage.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b) { this.setCornerRadius(a); this.setColor(b); return this; } }.getIns((int)48, 0xFFFFFFFF));
		send_fake_profile_likes_stage_count.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b) { this.setCornerRadius(a); this.setColor(b); return this; } }.getIns((int)28, 0xFFF5F5F5));
		_viewGraphics(send_fake_profile_likes_stage_send, 0xFF000000, 0xFF616161, 300, 0, Color.TRANSPARENT);
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
