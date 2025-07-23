package com.synapse.social.studioasinc;

import android.animation.*;
import android.app.*;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.*;
import android.content.Context;
import android.content.DialogInterface;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Switch;
import android.widget.TextView;
import androidx.annotation.*;
import androidx.appcompat.app.AppCompatActivity;
import androidx.browser.*;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.*;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.Adapter;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;
import com.bumptech.glide.*;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.*;
import com.google.android.material.color.MaterialColors;
import com.google.android.material.slider.Slider;
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
import com.synapse.social.studioasinc.CenterCropLinearLayoutNoEffect;
import com.theartofdev.edmodo.cropper.*;
import com.yalantis.ucrop.*;
import java.io.*;
import java.io.InputStream;
import java.text.*;
import java.util.*;
import java.util.HashMap;
import java.util.regex.*;
import org.json.*;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.slider.Slider;

public class ChatsettingsActivity extends AppCompatActivity {
	
	private FirebaseDatabase _firebase = FirebaseDatabase.getInstance();
	
	private String UserAvatarUri = "";
	private double theme = 0;
	private double inAppBrowserSw = 0;
	
	private LinearLayout body;
	private LinearLayout top;
	private ScrollView scrollMain;
	private ImageView mBack;
	private TextView mTitle;
	private ImageView spc;
	private LinearLayout scrollBody;
	private TextView application_stage_title;
	private LinearLayout application_stage;
	private TextView textview67;
	private RecyclerView recyclerview2;
	private TextView textview68;
	private TextView textview22;
	private LinearLayout account_stack_3;
	private TextView textview23;
	private LinearLayout account_stack_2;
	private TextView account_stage_title;
	private LinearLayout account_stage;
	private TextView textview41;
	private LinearLayout linear45;
	private TextView textview50;
	private LinearLayout linear52;
	private LinearLayout linear59;
	private LinearLayout inappbrowser_switch_lay;
	private LinearLayout animation_btn;
	private LinearLayout linear19;
	private ImageView imageview31;
	private LinearLayout linear60;
	private TextView textview57;
	private Slider seekbar6;
	private CenterCropLinearLayoutNoEffect preview_font_size_layout;
	private TextView textview58;
	private LinearLayout user2_preview;
	private LinearLayout linear72;
	private CardView mProfileCard;
	private LinearLayout message_layout;
	private ImageView mProfileImage;
	private LinearLayout menuView_d;
	private LinearLayout messageBG;
	private LinearLayout my_message_info;
	private LinearLayout mRepliedMessageLayout;
	private TextView message_text;
	private LinearLayout mRepliedMessageLayoutLeftBar;
	private LinearLayout mRepliedMessageLayoutRightBody;
	private TextView mRepliedMessageLayoutUsername;
	private TextView mRepliedMessageLayoutMessage;
	private TextView date;
	private LinearLayout linear73;
	private LinearLayout linear74;
	private LinearLayout messageBG1;
	private LinearLayout linear76;
	private TextView txt_msg1;
	private TextView textview66;
	private ImageView message_state;
	private ImageView imageview14;
	private LinearLayout linear26;
	private ImageView imageview32;
	private TextView textview18;
	private TextView textview19;
	private ImageView imageview15;
	private LinearLayout linear28;
	private TextView textview20;
	private Slider round_seekbar;
	private TextView textview21;
	private ImageView imageview13;
	private LinearLayout linear20;
	private TextView textview15;
	private TextView textview16;
	private LinearLayout linear29;
	private LinearLayout linear31;
	private LinearLayout linear33;
	private ImageView imageview16;
	private LinearLayout linear30;
	private Switch switch1;
	private TextView textview24;
	private TextView textview25;
	private ImageView imageview17;
	private LinearLayout linear32;
	private Switch switch2;
	private TextView textview26;
	private TextView textview27;
	private ImageView imageview18;
	private LinearLayout linear34;
	private Switch switch3;
	private TextView textview29;
	private TextView textview30;
	private LinearLayout linear39;
	private LinearLayout linear41;
	private LinearLayout linear43;
	private LinearLayout linear12;
	private ImageView imageview21;
	private LinearLayout linear40;
	private Switch switch5;
	private TextView textview35;
	private ImageView imageview22;
	private LinearLayout linear42;
	private Switch switch6;
	private TextView textview37;
	private TextView textview38;
	private ImageView imageview23;
	private LinearLayout linear44;
	private Switch switch7;
	private TextView textview39;
	private TextView textview40;
	private ImageView imageview10;
	private LinearLayout linear13;
	private TextView textview10;
	private TextView textview9;
	private LinearLayout Language_btn;
	private RecyclerView appicons_listview;
	private ImageView imageview19;
	private LinearLayout linear36;
	private ImageView imageview30;
	private TextView textview31;
	private TextView textview32;
	private LinearLayout linear46;
	private LinearLayout linear47;
	private LinearLayout linear48;
	private ImageView imageview24;
	private LinearLayout linear49;
	private TextView textview48;
	private TextView textview42;
	private TextView textview43;
	private ImageView imageview25;
	private LinearLayout linear50;
	private TextView textview49;
	private TextView textview44;
	private TextView textview45;
	private ImageView imageview26;
	private LinearLayout linear51;
	private Switch switch10;
	private TextView textview46;
	private TextView textview47;
	private LinearLayout linear53;
	private LinearLayout linear54;
	private LinearLayout linear55;
	private ImageView imageview27;
	private LinearLayout linear56;
	private TextView textview51;
	private ImageView imageview28;
	private LinearLayout linear57;
	private TextView textview53;
	private ImageView imageview29;
	private LinearLayout linear58;
	private TextView textview55;
	private TextView textview56;
	
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
	private Intent intent = new Intent();
	private DatabaseReference mainDb = _firebase.getReference("/");
	private ChildEventListener _mainDb_child_listener;
	private AlertDialog.Builder d;
	private Vibrator v;
	private AlertDialog.Builder zorry;
	private SharedPreferences appSettings;
	
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.chatsettings);
		initialize(_savedInstanceState);
		FirebaseApp.initializeApp(this);
		initializeLogic();
	}
	
	private void initialize(Bundle _savedInstanceState) {
		body = findViewById(R.id.body);
		top = findViewById(R.id.top);
		scrollMain = findViewById(R.id.scrollMain);
		mBack = findViewById(R.id.mBack);
		mTitle = findViewById(R.id.mTitle);
		spc = findViewById(R.id.spc);
		scrollBody = findViewById(R.id.scrollBody);
		application_stage_title = findViewById(R.id.application_stage_title);
		application_stage = findViewById(R.id.application_stage);
		textview67 = findViewById(R.id.textview67);
		recyclerview2 = findViewById(R.id.recyclerview2);
		textview68 = findViewById(R.id.textview68);
		textview22 = findViewById(R.id.textview22);
		account_stack_3 = findViewById(R.id.account_stack_3);
		textview23 = findViewById(R.id.textview23);
		account_stack_2 = findViewById(R.id.account_stack_2);
		account_stage_title = findViewById(R.id.account_stage_title);
		account_stage = findViewById(R.id.account_stage);
		textview41 = findViewById(R.id.textview41);
		linear45 = findViewById(R.id.linear45);
		textview50 = findViewById(R.id.textview50);
		linear52 = findViewById(R.id.linear52);
		linear59 = findViewById(R.id.linear59);
		inappbrowser_switch_lay = findViewById(R.id.inappbrowser_switch_lay);
		animation_btn = findViewById(R.id.animation_btn);
		linear19 = findViewById(R.id.linear19);
		imageview31 = findViewById(R.id.imageview31);
		linear60 = findViewById(R.id.linear60);
		textview57 = findViewById(R.id.textview57);
		seekbar6 = findViewById(R.id.seekbar6);
		preview_font_size_layout = findViewById(R.id.preview_font_size_layout);
		textview58 = findViewById(R.id.textview58);
		user2_preview = findViewById(R.id.user2_preview);
		linear72 = findViewById(R.id.linear72);
		mProfileCard = findViewById(R.id.mProfileCard);
		message_layout = findViewById(R.id.message_layout);
		mProfileImage = findViewById(R.id.mProfileImage);
		menuView_d = findViewById(R.id.menuView_d);
		messageBG = findViewById(R.id.messageBG);
		my_message_info = findViewById(R.id.my_message_info);
		mRepliedMessageLayout = findViewById(R.id.mRepliedMessageLayout);
		message_text = findViewById(R.id.message_text);
		mRepliedMessageLayoutLeftBar = findViewById(R.id.mRepliedMessageLayoutLeftBar);
		mRepliedMessageLayoutRightBody = findViewById(R.id.mRepliedMessageLayoutRightBody);
		mRepliedMessageLayoutUsername = findViewById(R.id.mRepliedMessageLayoutUsername);
		mRepliedMessageLayoutMessage = findViewById(R.id.mRepliedMessageLayoutMessage);
		date = findViewById(R.id.date);
		linear73 = findViewById(R.id.linear73);
		linear74 = findViewById(R.id.linear74);
		messageBG1 = findViewById(R.id.messageBG1);
		linear76 = findViewById(R.id.linear76);
		txt_msg1 = findViewById(R.id.txt_msg1);
		textview66 = findViewById(R.id.textview66);
		message_state = findViewById(R.id.message_state);
		imageview14 = findViewById(R.id.imageview14);
		linear26 = findViewById(R.id.linear26);
		imageview32 = findViewById(R.id.imageview32);
		textview18 = findViewById(R.id.textview18);
		textview19 = findViewById(R.id.textview19);
		imageview15 = findViewById(R.id.imageview15);
		linear28 = findViewById(R.id.linear28);
		textview20 = findViewById(R.id.textview20);
		round_seekbar = findViewById(R.id.round_seekbar);
		textview21 = findViewById(R.id.textview21);
		imageview13 = findViewById(R.id.imageview13);
		linear20 = findViewById(R.id.linear20);
		textview15 = findViewById(R.id.textview15);
		textview16 = findViewById(R.id.textview16);
		linear29 = findViewById(R.id.linear29);
		linear31 = findViewById(R.id.linear31);
		linear33 = findViewById(R.id.linear33);
		imageview16 = findViewById(R.id.imageview16);
		linear30 = findViewById(R.id.linear30);
		switch1 = findViewById(R.id.switch1);
		textview24 = findViewById(R.id.textview24);
		textview25 = findViewById(R.id.textview25);
		imageview17 = findViewById(R.id.imageview17);
		linear32 = findViewById(R.id.linear32);
		switch2 = findViewById(R.id.switch2);
		textview26 = findViewById(R.id.textview26);
		textview27 = findViewById(R.id.textview27);
		imageview18 = findViewById(R.id.imageview18);
		linear34 = findViewById(R.id.linear34);
		switch3 = findViewById(R.id.switch3);
		textview29 = findViewById(R.id.textview29);
		textview30 = findViewById(R.id.textview30);
		linear39 = findViewById(R.id.linear39);
		linear41 = findViewById(R.id.linear41);
		linear43 = findViewById(R.id.linear43);
		linear12 = findViewById(R.id.linear12);
		imageview21 = findViewById(R.id.imageview21);
		linear40 = findViewById(R.id.linear40);
		switch5 = findViewById(R.id.switch5);
		textview35 = findViewById(R.id.textview35);
		imageview22 = findViewById(R.id.imageview22);
		linear42 = findViewById(R.id.linear42);
		switch6 = findViewById(R.id.switch6);
		textview37 = findViewById(R.id.textview37);
		textview38 = findViewById(R.id.textview38);
		imageview23 = findViewById(R.id.imageview23);
		linear44 = findViewById(R.id.linear44);
		switch7 = findViewById(R.id.switch7);
		textview39 = findViewById(R.id.textview39);
		textview40 = findViewById(R.id.textview40);
		imageview10 = findViewById(R.id.imageview10);
		linear13 = findViewById(R.id.linear13);
		textview10 = findViewById(R.id.textview10);
		textview9 = findViewById(R.id.textview9);
		Language_btn = findViewById(R.id.Language_btn);
		appicons_listview = findViewById(R.id.appicons_listview);
		imageview19 = findViewById(R.id.imageview19);
		linear36 = findViewById(R.id.linear36);
		imageview30 = findViewById(R.id.imageview30);
		textview31 = findViewById(R.id.textview31);
		textview32 = findViewById(R.id.textview32);
		linear46 = findViewById(R.id.linear46);
		linear47 = findViewById(R.id.linear47);
		linear48 = findViewById(R.id.linear48);
		imageview24 = findViewById(R.id.imageview24);
		linear49 = findViewById(R.id.linear49);
		textview48 = findViewById(R.id.textview48);
		textview42 = findViewById(R.id.textview42);
		textview43 = findViewById(R.id.textview43);
		imageview25 = findViewById(R.id.imageview25);
		linear50 = findViewById(R.id.linear50);
		textview49 = findViewById(R.id.textview49);
		textview44 = findViewById(R.id.textview44);
		textview45 = findViewById(R.id.textview45);
		imageview26 = findViewById(R.id.imageview26);
		linear51 = findViewById(R.id.linear51);
		switch10 = findViewById(R.id.switch10);
		textview46 = findViewById(R.id.textview46);
		textview47 = findViewById(R.id.textview47);
		linear53 = findViewById(R.id.linear53);
		linear54 = findViewById(R.id.linear54);
		linear55 = findViewById(R.id.linear55);
		imageview27 = findViewById(R.id.imageview27);
		linear56 = findViewById(R.id.linear56);
		textview51 = findViewById(R.id.textview51);
		imageview28 = findViewById(R.id.imageview28);
		linear57 = findViewById(R.id.linear57);
		textview53 = findViewById(R.id.textview53);
		imageview29 = findViewById(R.id.imageview29);
		linear58 = findViewById(R.id.linear58);
		textview55 = findViewById(R.id.textview55);
		textview56 = findViewById(R.id.textview56);
		auth = FirebaseAuth.getInstance();
		d = new AlertDialog.Builder(this);
		v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
		zorry = new AlertDialog.Builder(this);
		appSettings = getSharedPreferences("appSettings", Activity.MODE_PRIVATE);
		
		mBack.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				finish();
			}
		});
		
		inappbrowser_switch_lay.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				
			}
		});
		
		Language_btn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				startActivity(intent);
			}
		});
		
		_mainDb_child_listener = new ChildEventListener() {
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
		mainDb.addChildEventListener(_mainDb_child_listener);
		
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
		try{
			try {
				// Text size slider
				Slider textSizeSlider = findViewById(R.id.seekbar6);
				String savedTextSize = appSettings.getString("ChatTextSize", "16");
				int initialTextSizeSliderValue;
				
				try {
					initialTextSizeSliderValue = (int) Double.parseDouble(savedTextSize);
				} catch (NumberFormatException e) {
					Log.e("ChatTextSize", "Error parsing text size", e);
					initialTextSizeSliderValue = 16;
				}
				
				Log.d("ChatTextSize", "Using text size: " + initialTextSizeSliderValue);
				textSizeSlider.setValue(initialTextSizeSliderValue);
				
				txt_msg1.setTextSize(initialTextSizeSliderValue);
				message_text.setTextSize(initialTextSizeSliderValue);
				mRepliedMessageLayoutMessage.setTextSize(initialTextSizeSliderValue);
				
				textSizeSlider.addOnChangeListener(new Slider.OnChangeListener() {
					@Override
					public void onValueChange(Slider slider, float value, boolean fromUser) {
						int progress = (int) value;
						txt_msg1.setTextSize(progress);
						message_text.setTextSize(progress);
						mRepliedMessageLayoutMessage.setTextSize(progress);
						appSettings.edit().putString("ChatTextSize", String.valueOf(progress)).commit();
					}
				});
				
				// Corner radius slider
				Slider cornerRadiusSlider = findViewById(R.id.round_seekbar);
				String savedCornerRadius = appSettings.getString("ChatCornerRadius", "20");
				int initialCornerRadiusSliderValue;
				
				try {
					initialCornerRadiusSliderValue = (int) Double.parseDouble(savedCornerRadius);
				} catch (NumberFormatException e) {
					Log.e("ChatCornerRadius", "Error parsing corner radius", e);
					initialCornerRadiusSliderValue = 16;
				}
				
				Log.d("ChatCornerRadius", "Using corner radius: " + initialCornerRadiusSliderValue);
				cornerRadiusSlider.setValue(initialCornerRadiusSliderValue);
				
				// Apply corner radius to message backgrounds
				cornerRadiusSlider.addOnChangeListener(new Slider.OnChangeListener() {
					@Override
					public void onValueChange(Slider slider, float value, boolean fromUser) {
						int progress = (int) value;
						
						// Apply corner radius to messageBG and messageBG1
						{
							android.graphics.drawable.GradientDrawable SketchUi = new android.graphics.drawable.GradientDrawable();
							int d = (int) getApplicationContext().getResources().getDisplayMetrics().density;
							SketchUi.setColor(0xFF6B4CFF);
							SketchUi.setCornerRadius(d * progress);
							android.graphics.drawable.RippleDrawable SketchUiRD = new android.graphics.drawable.RippleDrawable(new android.content.res.ColorStateList(new int[][]{new int[]{}}, new int[]{0xFFE0E0E0}), SketchUi, null);
							messageBG1.setBackground(SketchUiRD);
							messageBG1.setClickable(true);
						}
						{
							android.graphics.drawable.GradientDrawable SketchUi = new android.graphics.drawable.GradientDrawable();
							int d = (int) getApplicationContext().getResources().getDisplayMetrics().density;
							SketchUi.setColor(0xFFFFFFFF);
							SketchUi.setCornerRadius(d * progress);
							SketchUi.setStroke(d * 2, 0xFFDFDFDF);
							android.graphics.drawable.RippleDrawable SketchUiRD = new android.graphics.drawable.RippleDrawable(new android.content.res.ColorStateList(new int[][]{new int[]{}}, new int[]{0xFFE0E0E0}), SketchUi, null);
							messageBG.setBackground(SketchUiRD);
							messageBG.setClickable(true);
						}
						
						// Save the corner radius setting
						appSettings.edit().putString("ChatCornerRadius", String.valueOf(progress)).commit();
					}
				});
				
				// Initial setup for messageBG and messageBG1
				{
					android.graphics.drawable.GradientDrawable SketchUi = new android.graphics.drawable.GradientDrawable();
					int d = (int) getApplicationContext().getResources().getDisplayMetrics().density;
					SketchUi.setColor(0xFF6B4CFF);
					SketchUi.setCornerRadius(d * initialCornerRadiusSliderValue);
					android.graphics.drawable.RippleDrawable SketchUiRD = new android.graphics.drawable.RippleDrawable(new android.content.res.ColorStateList(new int[][]{new int[]{}}, new int[]{0xFFE0E0E0}), SketchUi, null);
					messageBG1.setBackground(SketchUiRD);
					messageBG1.setClickable(true);
				}
				{
					android.graphics.drawable.GradientDrawable SketchUi = new android.graphics.drawable.GradientDrawable();
					int d = (int) getApplicationContext().getResources().getDisplayMetrics().density;
					SketchUi.setColor(0xFFFFFFFF);
					SketchUi.setCornerRadius(d * initialCornerRadiusSliderValue);
					SketchUi.setStroke(d * 2, 0xFFDFDFDF);
					android.graphics.drawable.RippleDrawable SketchUiRD = new android.graphics.drawable.RippleDrawable(new android.content.res.ColorStateList(new int[][]{new int[]{}}, new int[]{0xFFE0E0E0}), SketchUi, null);
					messageBG.setBackground(SketchUiRD);
					messageBG.setClickable(true);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}catch(Exception e){
			
		}
		_stateColor(0xFFFFFFFF, 0xFFFAFAFA);
		application_stage.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b) { this.setCornerRadius(a); this.setColor(b); return this; } }.getIns((int)35, 0xFFFFFFFF));
		account_stage.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b) { this.setCornerRadius(a); this.setColor(b); return this; } }.getIns((int)35, 0xFFFFFFFF));
		account_stack_2.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b) { this.setCornerRadius(a); this.setColor(b); return this; } }.getIns((int)35, 0xFFFFFFFF));
		account_stack_3.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b) { this.setCornerRadius(a); this.setColor(b); return this; } }.getIns((int)35, 0xFFFFFFFF));
		_getUserReference();
		mRepliedMessageLayoutLeftBar.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b, int c, int d) { this.setCornerRadius(a); this.setStroke(b, c); this.setColor(d); return this; } }.getIns((int)360, (int)0, Color.TRANSPARENT, getResources().getColor(R.color.colorPrimary)));
		mProfileImage.setImageResource(R.drawable.ashik_dp);
		_ImgRound(mProfileImage, 300);
	}
	
	public void _stateColor(final int _statusColor, final int _navigationColor) {
		getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
		getWindow().setStatusBarColor(_statusColor);
		getWindow().setNavigationBarColor(_navigationColor);
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
	
	
	public void _getUserReference() {
		DatabaseReference getUserReference = FirebaseDatabase.getInstance().getReference("skyline/users").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
		getUserReference.addListenerForSingleValueEvent(new ValueEventListener() {
			@Override
			public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
				if(dataSnapshot.exists()) {
					if (dataSnapshot.child("banned").getValue(String.class).equals("true")) {
						
					} else {
						if (dataSnapshot.child("avatar").getValue(String.class).equals("null")) {
							
						} else {
							
						}
					}
					if (dataSnapshot.child("nickname").getValue(String.class).equals("null")) {
						
					} else {
						
					}
					if (dataSnapshot.child("gender").getValue(String.class).equals("hidden")) {
						
					} else {
						if (dataSnapshot.child("gender").getValue(String.class).equals("male")) {
							
						} else {
							if (dataSnapshot.child("gender").getValue(String.class).equals("female")) {
								
							}
						}
					}
					if (dataSnapshot.child("user_region").getValue(String.class) != null) {
						
					} else {
						
					}
				} else {
				}
			}
			
			@Override
			public void onCancelled(@NonNull DatabaseError databaseError) {
				
			}
		});
	}
	
	
	public void _ImgRound(final ImageView _imageview, final double _value) {
		android.graphics.drawable.GradientDrawable gd = new android.graphics.drawable.GradientDrawable ();
		gd.setColor(android.R.color.transparent);
		gd.setCornerRadius((int)_value);
		_imageview.setClipToOutline(true);
		_imageview.setBackground(gd);
	}
	
	
	public void _Shape(final double _t1, final double _t2, final double _b1, final double _b2, final String _Background, final double _Stroke, final String _stroke, final double _Elevation, final View _view) {
		android.graphics.drawable.GradientDrawable gs = new android.graphics.drawable.GradientDrawable();
		
		gs.setColor(Color.parseColor(_Background));
		
		gs.setStroke((int)_Stroke, Color.parseColor(_stroke));
		
		gs.setCornerRadii(new float[]{(int)_t1,(int)_t1,(int)_t2,(int)_t2,(int)_b1,(int)_b1,(int)_b2,(int)_b2});
		
		_view.setBackground(gs);
		_view.setElevation((int)_Elevation);
	}
	
	
	public void _Corner_Of(final double _top1, final double _top2, final double _bottom1, final double _bottom2, final String _inside_color, final String _side_color, final double _side_size, final View _view) {
		Double tlr = _top1;
		Double trr = _top2;
		Double blr = _bottom2;
		Double brr = _bottom1;
		Double sw = _side_size;
		android.graphics.drawable.GradientDrawable s = new android.graphics.drawable.GradientDrawable();
		s.setShape(android.graphics.drawable.GradientDrawable.RECTANGLE);
		s.setCornerRadii(new float[] {tlr.floatValue(),tlr.floatValue(), trr.floatValue(),trr.floatValue(), blr.floatValue(),blr.floatValue(), brr.floatValue(),brr.floatValue()}); 
		
		s.setColor(Color.parseColor(_inside_color));
		s.setStroke(sw.intValue(), Color.parseColor(_side_color));
		_view.setBackground(s);
	}
	
}