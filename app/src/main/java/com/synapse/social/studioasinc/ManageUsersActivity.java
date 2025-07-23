package com.synapse.social.studioasinc;

import android.animation.*;
import android.app.*;
import android.content.*;
import android.content.Intent;
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
//import com.blogspot.atifsoftwares.animatoolib.*;
//import com.budiyev.android.codescanner.*;
import com.caverock.androidsvg.*;
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
import java.io.InputStream;
import java.text.*;
import java.util.*;
import java.util.regex.*;
import kr.co.prnd.readmore.*;
//import me.dm7.barcodescanner.core.*;
//import org.jetbrains.kotlin.*;
import org.json.*;

public class ManageUsersActivity extends AppCompatActivity {
	
	private LinearLayout body;
	private LinearLayout top;
	private ScrollView scroll;
	private ImageView mBack;
	private TextView mTitle;
	private ImageView spc;
	private LinearLayout scroll_body;
	private TextView primary_tools_title;
	private LinearLayout search_user_stage;
	private LinearLayout app_blocked_users;
	private LinearLayout app_verified_users;
	private LinearLayout app_verify_requests;
	private LinearLayout app_report_requests;
	private TextView tools_for_testing_title;
	private LinearLayout app_create_custom_user;
	private LinearLayout app_send_fake_data;
	private EditText search_user_stage_input;
	private ImageView search_user_stage_continue;
	private LinearLayout app_blocked_users_top;
	private TextView app_blocked_users_subtext;
	private TextView app_blocked_users_title;
	private ImageView app_blocked_users_arrow;
	private LinearLayout app_verified_users_top;
	private TextView app_verified_users_subtext;
	private TextView app_verified_users_title;
	private ImageView app_verified_users_arrow;
	private LinearLayout app_verify_requests_top;
	private TextView app_verify_requests_subtext;
	private TextView app_verify_requests_title;
	private ImageView app_verify_requests_arrow;
	private LinearLayout app_report_requests_top;
	private TextView app_report_requests_subtext;
	private TextView app_report_requests_title;
	private ImageView app_report_requests_arrow;
	private LinearLayout app_create_custom_user_top;
	private TextView app_create_custom_user_subtext;
	private TextView app_create_custom_user_title;
	private ImageView app_create_custom_user_arrow;
	private LinearLayout app_send_fake_data_top;
	private TextView app_send_fake_data_subtext;
	private TextView app_send_fake_data_title;
	private ImageView app_send_fake_data_arrow;
	
	private Intent intent = new Intent();
	
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.manage_users);
		initialize(_savedInstanceState);
		FirebaseApp.initializeApp(this);
		initializeLogic();
	}
	
	private void initialize(Bundle _savedInstanceState) {
		body = findViewById(R.id.body);
		top = findViewById(R.id.top);
		scroll = findViewById(R.id.scroll);
		mBack = findViewById(R.id.mBack);
		mTitle = findViewById(R.id.mTitle);
		spc = findViewById(R.id.spc);
		scroll_body = findViewById(R.id.scroll_body);
		primary_tools_title = findViewById(R.id.primary_tools_title);
		search_user_stage = findViewById(R.id.search_user_stage);
		app_blocked_users = findViewById(R.id.app_blocked_users);
		app_verified_users = findViewById(R.id.app_verified_users);
		app_verify_requests = findViewById(R.id.app_verify_requests);
		app_report_requests = findViewById(R.id.app_report_requests);
		tools_for_testing_title = findViewById(R.id.tools_for_testing_title);
		app_create_custom_user = findViewById(R.id.app_create_custom_user);
		app_send_fake_data = findViewById(R.id.app_send_fake_data);
		search_user_stage_input = findViewById(R.id.search_user_stage_input);
		search_user_stage_continue = findViewById(R.id.search_user_stage_continue);
		app_blocked_users_top = findViewById(R.id.app_blocked_users_top);
		app_blocked_users_subtext = findViewById(R.id.app_blocked_users_subtext);
		app_blocked_users_title = findViewById(R.id.app_blocked_users_title);
		app_blocked_users_arrow = findViewById(R.id.app_blocked_users_arrow);
		app_verified_users_top = findViewById(R.id.app_verified_users_top);
		app_verified_users_subtext = findViewById(R.id.app_verified_users_subtext);
		app_verified_users_title = findViewById(R.id.app_verified_users_title);
		app_verified_users_arrow = findViewById(R.id.app_verified_users_arrow);
		app_verify_requests_top = findViewById(R.id.app_verify_requests_top);
		app_verify_requests_subtext = findViewById(R.id.app_verify_requests_subtext);
		app_verify_requests_title = findViewById(R.id.app_verify_requests_title);
		app_verify_requests_arrow = findViewById(R.id.app_verify_requests_arrow);
		app_report_requests_top = findViewById(R.id.app_report_requests_top);
		app_report_requests_subtext = findViewById(R.id.app_report_requests_subtext);
		app_report_requests_title = findViewById(R.id.app_report_requests_title);
		app_report_requests_arrow = findViewById(R.id.app_report_requests_arrow);
		app_create_custom_user_top = findViewById(R.id.app_create_custom_user_top);
		app_create_custom_user_subtext = findViewById(R.id.app_create_custom_user_subtext);
		app_create_custom_user_title = findViewById(R.id.app_create_custom_user_title);
		app_create_custom_user_arrow = findViewById(R.id.app_create_custom_user_arrow);
		app_send_fake_data_top = findViewById(R.id.app_send_fake_data_top);
		app_send_fake_data_subtext = findViewById(R.id.app_send_fake_data_subtext);
		app_send_fake_data_title = findViewById(R.id.app_send_fake_data_title);
		app_send_fake_data_arrow = findViewById(R.id.app_send_fake_data_arrow);
		
		mBack.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				onBackPressed();
			}
		});
		
		app_blocked_users.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				intent.setClass(getApplicationContext(), BannedUsersActivity.class);
				startActivity(intent);
			}
		});
		
		app_verified_users.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				intent.setClass(getApplicationContext(), VerifiedUsersActivity.class);
				startActivity(intent);
			}
		});
		
		app_verify_requests.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				startActivity(intent);
			}
		});
		
		app_report_requests.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				intent.setClass(getApplicationContext(), ReportRequestsActivity.class);
				startActivity(intent);
			}
		});
		
		app_send_fake_data.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				startActivity(intent);
			}
		});
		
		search_user_stage_continue.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				if (!search_user_stage_input.getText().toString().trim().equals("")) {
					intent.setClass(getApplicationContext(), PreviewUserProfileActivity.class);
					intent.putExtra("uid", search_user_stage_input.getText().toString().trim());
					startActivity(intent);
				} else {
					((EditText)search_user_stage_input).setError("Enter a user UID");
				}
			}
		});
	}
	
	private void initializeLogic() {
		_stateColor(0xFFFFFFFF, 0xFFFFFFFF);
		top.setElevation((float)4);
		_viewGraphics(mBack, 0xFFFFFFFF, 0xFFEEEEEE, 300, 0, Color.TRANSPARENT);
		search_user_stage_input.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b, int c, int d) { this.setCornerRadius(a); this.setStroke(b, c); this.setColor(d); return this; } }.getIns((int)300, (int)0, 0xFFF5F5F5, 0xFFF5F5F5));
		_viewGraphics(search_user_stage_continue, 0xFFF5F5F5, 0xFFE0E0E0, 300, 0, 0xFFE0E0E0);
		_viewGraphics(app_blocked_users, 0xFFFFFFFF, 0xFFEEEEEE, 28, 3, 0xFFE0E0E0);
		_viewGraphics(app_verified_users, 0xFFFFFFFF, 0xFFEEEEEE, 28, 3, 0xFFE0E0E0);
		_viewGraphics(app_verify_requests, 0xFFFFFFFF, 0xFFEEEEEE, 28, 3, 0xFFE0E0E0);
		_viewGraphics(app_report_requests, 0xFFFFFFFF, 0xFFEEEEEE, 28, 3, 0xFFE0E0E0);
		_viewGraphics(app_create_custom_user, 0xFFFFFFFF, 0xFFEEEEEE, 28, 3, 0xFFE0E0E0);
		_viewGraphics(app_send_fake_data, 0xFFFFFFFF, 0xFFEEEEEE, 28, 3, 0xFFE0E0E0);
	}
	
	@Override
	public void onBackPressed() {
		finish();
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
