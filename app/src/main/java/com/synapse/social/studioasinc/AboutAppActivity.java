package com.synapse.social.studioasinc;

import android.animation.*;
import android.app.*;
import android.app.Activity;
import android.content.*;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.ScrollView;
import android.widget.TextView;
import androidmads.library.qrgenearator.*;
import androidx.annotation.*;
import androidx.appcompat.app.AppCompatActivity;
import androidx.asynclayoutinflater.*;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.interpolator.*;
import androidx.swiperefreshlayout.*;
import androidx.transition.*;
import com.blogspot.atifsoftwares.animatoolib.*;
import com.budiyev.android.codescanner.*;
import com.caverock.androidsvg.*;
import com.google.android.material.*;
import com.google.firebase.FirebaseApp;
import com.google.firebase.appcheck.playintegrity.*;
import com.google.firebase.perf.*;
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
import java.io.*;
import java.io.InputStream;
import java.text.*;
import java.util.*;
import java.util.regex.*;
import kr.co.prnd.readmore.*;
import me.dm7.barcodescanner.core.*;
import org.jetbrains.kotlin.*;
import org.json.*;

public class AboutAppActivity extends AppCompatActivity {
	
	private String AndroidDevelopersBlogURL = "";
	private double social = 0;
	
	private LinearLayout container;
	private LinearLayout linear2;
	private ScrollView scrollMain;
	private ImageView imageview1;
	private TextView textview1;
	private LinearLayout scrollBody;
	private LinearLayout profile_stage;
	private TextView about_app_type_tittle;
	private LinearLayout app_info_container;
	private TextView legal_main_title;
	private LinearLayout legal_info_container;
	private LinearLayout legal_info2_container;
	private LinearLayout legal_docs_container;
	private LinearLayout profile_details;
	private ImageView imageview2;
	private TextView version_name;
	private LinearLayout linear16;
	private ImageView profile_gender;
	private CardView profile_flag_card;
	private ImageView profile_flag_image;
	private LinearLayout ProfilePageTabUserInfoSecondaryButtons;
	private TextView reset_settings_btn;
	private TextView check_for_updates_btn;
	private LinearLayout application_stage_themes;
	private LinearLayout divider5;
	private LinearLayout application_stage_language;
	private LinearLayout linear15;
	private LinearLayout feedback_btn;
	private TextView application_stage_themes_title;
	private TextView web_address_txt;
	private TextView application_stage_language_title;
	private TextView text2;
	private TextView textview26;
	private TextView txt3;
	private LinearLayout privacy_policy_btn;
	private LinearLayout divider;
	private LinearLayout TOS_btn;
	private TextView account_stage_privacy_title;
	private TextView account_stage_security_title;
	private LinearLayout eula_btn;
	private LinearLayout devider2;
	private LinearLayout acknowledge_btn;
	private LinearLayout devider;
	private LinearLayout open_source_license_btn;
	private LinearLayout divider3;
	private LinearLayout social_media_switch;
	private TextView textview21;
	private TextView textview6;
	private TextView textview22;
	private TextView textview23;
	private TextView textview2;
	private TextView textview8;
	private TextView textview24;
	private TextView textview25;
	private LinearLayout links_container;
	private LinearLayout wa_link;
	private LinearLayout tg_link;
	private LinearLayout x_link;
	private LinearLayout threads_link;
	private LinearLayout ig_link;
	private LinearLayout fb_link;
	private ImageView imageview10;
	private TextView textview35;
	private ImageView imageview8;
	private TextView textview33;
	private ImageView imageview11;
	private TextView textview36;
	private ImageView imageview9;
	private TextView textview34;
	private ImageView imageview3;
	private TextView textview28;
	private ImageView imageview7;
	private TextView textview32;
	private LinearLayout contributors_btn;
	private LinearLayout divider4;
	private LinearLayout account_stage_logout;
	private TextView textview14;
	private TextView textview15;
	private TextView account_stage_logout_title;
	private TextView textview9;
	
	private Intent i = new Intent();
	private SharedPreferences appSettings;
	
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.about_app);
		initialize(_savedInstanceState);
		FirebaseApp.initializeApp(this);
		initializeLogic();
	}
	
	private void initialize(Bundle _savedInstanceState) {
		container = findViewById(R.id.container);
		linear2 = findViewById(R.id.linear2);
		scrollMain = findViewById(R.id.scrollMain);
		imageview1 = findViewById(R.id.imageview1);
		textview1 = findViewById(R.id.textview1);
		scrollBody = findViewById(R.id.scrollBody);
		profile_stage = findViewById(R.id.profile_stage);
		about_app_type_tittle = findViewById(R.id.about_app_type_tittle);
		app_info_container = findViewById(R.id.app_info_container);
		legal_main_title = findViewById(R.id.legal_main_title);
		legal_info_container = findViewById(R.id.legal_info_container);
		legal_info2_container = findViewById(R.id.legal_info2_container);
		legal_docs_container = findViewById(R.id.legal_docs_container);
		profile_details = findViewById(R.id.profile_details);
		imageview2 = findViewById(R.id.imageview2);
		version_name = findViewById(R.id.version_name);
		linear16 = findViewById(R.id.linear16);
		profile_gender = findViewById(R.id.profile_gender);
		profile_flag_card = findViewById(R.id.profile_flag_card);
		profile_flag_image = findViewById(R.id.profile_flag_image);
		ProfilePageTabUserInfoSecondaryButtons = findViewById(R.id.ProfilePageTabUserInfoSecondaryButtons);
		reset_settings_btn = findViewById(R.id.reset_settings_btn);
		check_for_updates_btn = findViewById(R.id.check_for_updates_btn);
		application_stage_themes = findViewById(R.id.application_stage_themes);
		divider5 = findViewById(R.id.divider5);
		application_stage_language = findViewById(R.id.application_stage_language);
		linear15 = findViewById(R.id.linear15);
		feedback_btn = findViewById(R.id.feedback_btn);
		application_stage_themes_title = findViewById(R.id.application_stage_themes_title);
		web_address_txt = findViewById(R.id.web_address_txt);
		application_stage_language_title = findViewById(R.id.application_stage_language_title);
		text2 = findViewById(R.id.text2);
		textview26 = findViewById(R.id.textview26);
		txt3 = findViewById(R.id.txt3);
		privacy_policy_btn = findViewById(R.id.privacy_policy_btn);
		divider = findViewById(R.id.divider);
		TOS_btn = findViewById(R.id.TOS_btn);
		account_stage_privacy_title = findViewById(R.id.account_stage_privacy_title);
		account_stage_security_title = findViewById(R.id.account_stage_security_title);
		eula_btn = findViewById(R.id.eula_btn);
		devider2 = findViewById(R.id.devider2);
		acknowledge_btn = findViewById(R.id.acknowledge_btn);
		devider = findViewById(R.id.devider);
		open_source_license_btn = findViewById(R.id.open_source_license_btn);
		divider3 = findViewById(R.id.divider3);
		social_media_switch = findViewById(R.id.social_media_switch);
		textview21 = findViewById(R.id.textview21);
		textview6 = findViewById(R.id.textview6);
		textview22 = findViewById(R.id.textview22);
		textview23 = findViewById(R.id.textview23);
		textview2 = findViewById(R.id.textview2);
		textview8 = findViewById(R.id.textview8);
		textview24 = findViewById(R.id.textview24);
		textview25 = findViewById(R.id.textview25);
		links_container = findViewById(R.id.links_container);
		wa_link = findViewById(R.id.wa_link);
		tg_link = findViewById(R.id.tg_link);
		x_link = findViewById(R.id.x_link);
		threads_link = findViewById(R.id.threads_link);
		ig_link = findViewById(R.id.ig_link);
		fb_link = findViewById(R.id.fb_link);
		imageview10 = findViewById(R.id.imageview10);
		textview35 = findViewById(R.id.textview35);
		imageview8 = findViewById(R.id.imageview8);
		textview33 = findViewById(R.id.textview33);
		imageview11 = findViewById(R.id.imageview11);
		textview36 = findViewById(R.id.textview36);
		imageview9 = findViewById(R.id.imageview9);
		textview34 = findViewById(R.id.textview34);
		imageview3 = findViewById(R.id.imageview3);
		textview28 = findViewById(R.id.textview28);
		imageview7 = findViewById(R.id.imageview7);
		textview32 = findViewById(R.id.textview32);
		contributors_btn = findViewById(R.id.contributors_btn);
		divider4 = findViewById(R.id.divider4);
		account_stage_logout = findViewById(R.id.account_stage_logout);
		textview14 = findViewById(R.id.textview14);
		textview15 = findViewById(R.id.textview15);
		account_stage_logout_title = findViewById(R.id.account_stage_logout_title);
		textview9 = findViewById(R.id.textview9);
		appSettings = getSharedPreferences("appSettings", Activity.MODE_PRIVATE);
		
		application_stage_themes.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				_OpenAndroidDevelopersBlog();
			}
		});
		
		feedback_btn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				Intent intent = new Intent(Intent.ACTION_SENDTO);
				intent.setData(Uri.parse("mailto:mashikahamed0@gmail.com"));
				intent.putExtra(Intent.EXTRA_EMAIL, new String[] { "mashikahamed0@gmail.com" });
				intent.putExtra(Intent.EXTRA_SUBJECT, "Dear Synapse Team,");
				intent.putExtra(Intent.EXTRA_TEXT, "I hope this message finds you well. I am reaching out to provide feedback on my recent experience with your \n\n[Your feedback here]\n\nThank you for taking the time to read my feedback. I look forward to seeing how StudioAs, Inc. continues to evolve and improve.\n\nBest regards,\n[Your name]");
				startActivity(Intent.createChooser(intent, "Email via..."));
			}
		});
		
		privacy_policy_btn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				if (appSettings.contains("InAppBrowser")) {
					if (appSettings.getString("appSettings", "").equals("true")) {
						_OpenAndroidDevelopersBlog();
					} else {
						i.setAction(Intent.ACTION_VIEW);
						i.setData(Uri.parse("web-synapse.pages.dev/docs/privacy-policy"));
						startActivity(i);
					}
				} else {
					SketchwareUtil.showMessage(getApplicationContext(), "No settings found");
				}
			}
		});
		
		TOS_btn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				if (appSettings.contains("InAppBrowser")) {
					if (appSettings.getString("appSettings", "").equals("true")) {
						_OpenAndroidDevelopersBlog();
					} else {
						i.setAction(Intent.ACTION_VIEW);
						i.setData(Uri.parse("web-synapse.pages.dev/docs/terms"));
						startActivity(i);
					}
				} else {
					SketchwareUtil.showMessage(getApplicationContext(), "No settings found");
				}
			}
		});
		
		eula_btn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				if (appSettings.contains("InAppBrowser")) {
					if (appSettings.getString("appSettings", "").equals("true")) {
						_OpenAndroidDevelopersBlog();
					} else {
						i.setAction(Intent.ACTION_VIEW);
						i.setData(Uri.parse("web-synapse.pages.dev/docs/eula"));
						startActivity(i);
					}
				} else {
					SketchwareUtil.showMessage(getApplicationContext(), "No settings found");
				}
			}
		});
		
		open_source_license_btn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				if (appSettings.contains("InAppBrowser")) {
					if (appSettings.getString("appSettings", "").equals("true")) {
						_OpenAndroidDevelopersBlog();
					} else {
						i.setAction(Intent.ACTION_VIEW);
						i.setData(Uri.parse("web-synapse.pages.dev/docs/licence"));
						startActivity(i);
					}
				} else {
					SketchwareUtil.showMessage(getApplicationContext(), "No settings found");
				}
			}
		});
		
		social_media_switch.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				if (social == 0) {
					_TransitionManager(scrollBody, 400);
					social++;
					links_container.setVisibility(View.VISIBLE);
				} else {
					social--;
					links_container.setVisibility(View.GONE);
					_TransitionManager(scrollBody, 400);
				}
			}
		});
		
		tg_link.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				i.setAction(Intent.ACTION_VIEW);
				i.setData(Uri.parse("https://t.me/studioasinc"));
				startActivity(i);
			}
		});
		
		x_link.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				i.setAction(Intent.ACTION_VIEW);
				i.setData(Uri.parse("https://x.com/IamAshik_x"));
				startActivity(i);
			}
		});
		
		threads_link.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				i.setAction(Intent.ACTION_VIEW);
				i.setData(Uri.parse("https://threads.net/IamAshik_fb"));
				startActivity(i);
			}
		});
		
		ig_link.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				i.setAction(Intent.ACTION_VIEW);
				i.setData(Uri.parse("https://instagram.com/IamAshik.fb"));
				startActivity(i);
			}
		});
		
		fb_link.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				i.setAction(Intent.ACTION_VIEW);
				i.setData(Uri.parse("https://facebook.com/IamAshik.fb"));
				startActivity(i);
			}
		});
		
		contributors_btn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				_OpenWebView("https://web-synapse.pages.dev/docs/contributors");
			}
		});
	}
	
	private void initializeLogic() {
		_stateColor(0xFFFFFFFF, 0xFFFAFAFA);
		legal_info_container.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b) { this.setCornerRadius(a); this.setColor(b); return this; } }.getIns((int)32, 0xFFFFFFFF));
		legal_docs_container.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b) { this.setCornerRadius(a); this.setColor(b); return this; } }.getIns((int)32, 0xFFFFFFFF));
		legal_info2_container.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b) { this.setCornerRadius(a); this.setColor(b); return this; } }.getIns((int)32, 0xFFFFFFFF));
		app_info_container.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b) { this.setCornerRadius(a); this.setColor(b); return this; } }.getIns((int)32, 0xFFFFFFFF));
		tg_link.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b, int c, int d) { this.setCornerRadius(a); this.setStroke(b, c); this.setColor(d); return this; } }.getIns((int)360, (int)0, 0xFFFFFFFF, 0xFF445E91));
		x_link.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b, int c, int d) { this.setCornerRadius(a); this.setStroke(b, c); this.setColor(d); return this; } }.getIns((int)360, (int)0, 0xFFFFFFFF, 0xFF445E91));
		ig_link.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b, int c, int d) { this.setCornerRadius(a); this.setStroke(b, c); this.setColor(d); return this; } }.getIns((int)360, (int)0, 0xFFFFFFFF, 0xFF445E91));
		fb_link.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b, int c, int d) { this.setCornerRadius(a); this.setStroke(b, c); this.setColor(d); return this; } }.getIns((int)360, (int)0, 0xFFFFFFFF, 0xFF445E91));
		threads_link.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b, int c, int d) { this.setCornerRadius(a); this.setStroke(b, c); this.setColor(d); return this; } }.getIns((int)360, (int)0, 0xFFFFFFFF, 0xFF445E91));
		social = 0;
		links_container.setVisibility(View.GONE);
		legal_info_container.setElevation((float)1);
		app_info_container.setElevation((float)1);
		legal_info2_container.setElevation((float)1);
		legal_docs_container.setElevation((float)1);
		reset_settings_btn.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b, int c, int d) { this.setCornerRadius(a); this.setStroke(b, c); this.setColor(d); return this; } }.getIns((int)100, (int)0, 0xFFFFFFFF, 0xFF445E91));
		check_for_updates_btn.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b, int c, int d) { this.setCornerRadius(a); this.setStroke(b, c); this.setColor(d); return this; } }.getIns((int)100, (int)0, 0xFFFFFFFF, 0xFF445E91));
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
	
	
	public void _stateColor(final int _statusColor, final int _navigationColor) {
		getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
		getWindow().setStatusBarColor(_statusColor);
		getWindow().setNavigationBarColor(_navigationColor);
	}
	
	
	public void _OpenAndroidDevelopersBlog() {
		AndroidDevelopersBlogURL = "https://web-synapse.pages.dev";
		CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
		builder.setToolbarColor(Color.parseColor("#242D39"));
		CustomTabsIntent customtabsintent = builder.build();
		customtabsintent.launchUrl(this, Uri.parse(AndroidDevelopersBlogURL));
	}
	
	
	public void _TransitionManager(final View _view, final double _duration) {
		LinearLayout viewgroup =(LinearLayout) _view;
		
		android.transition.AutoTransition autoTransition = new android.transition.AutoTransition(); autoTransition.setDuration((long)_duration); android.transition.TransitionManager.beginDelayedTransition(viewgroup, autoTransition);
	}
	
	
	public void _OpenWebView(final String _URL) {
		AndroidDevelopersBlogURL = _URL;
		CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
		builder.setToolbarColor(Color.parseColor("#242D39"));
		CustomTabsIntent customtabsintent = builder.build();
		customtabsintent.launchUrl(this, Uri.parse(AndroidDevelopersBlogURL));
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