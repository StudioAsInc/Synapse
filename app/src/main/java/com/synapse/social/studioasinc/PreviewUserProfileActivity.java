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
//import android.support.customtabs.*;
import androidx.browser.customtabs.CustomTabsIntent;
import android.text.*;
import android.text.style.*;
import android.util.*;
import android.view.*;
import android.view.View;
import android.view.View.*;
import android.view.animation.*;
import android.webkit.*;
import android.widget.*;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidmads.library.qrgenearator.*;
import androidx.annotation.*;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.asynclayoutinflater.*;
import androidx.cardview.widget.CardView;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.interpolator.*;
import androidx.swiperefreshlayout.*;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout.OnRefreshListener;
import androidx.transition.*;
import com.blogspot.atifsoftwares.animatoolib.*;
import com.budiyev.android.codescanner.*;
import com.bumptech.glide.Glide;
import com.caverock.androidsvg.*;
import com.google.android.gms.tasks.OnCompleteListener;
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
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Calendar;
import java.util.HashMap;
import java.util.regex.*;
import kr.co.prnd.readmore.*;
import me.dm7.barcodescanner.core.*;
import org.jetbrains.kotlin.*;
import org.json.*;
import androidx.appcompat.widget.SwitchCompat;
import androidx.core.widget.NestedScrollView;
import com.google.firebase.database.Query;
import android.os.Handler;
import android.os.Looper;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;



public class PreviewUserProfileActivity extends AppCompatActivity {
	
	private FirebaseDatabase _firebase = FirebaseDatabase.getInstance();
	
	private ProgressDialog SynapseLoadingDialog;
	
	private LinearLayout ProfilePageBody;
	private LinearLayout ProfilePageTopBar;
	private LinearLayout ProfilePageMiddleLayout;
	private ImageView ProfilePageTopBarBack;
	private LinearLayout ProfilePageTopBarSpace;
	private ImageView ProfilePageTopBarMenu;
	private TextView ProfilePageTopBarUsername;
	private SwipeRefreshLayout ProfilePageSwipeLayout;
	private LinearLayout ProfilePageLoadingBody;
	private LinearLayout ProfilePageSwipeLayoutBody;
	private NestedScrollView ProfilePageTabUserInfo;
	private LinearLayout ProfilePageTabUserInfoBody;
	private LinearLayout ProfilePageTabUserInfoCoverLayout;
	private LinearLayout prfLay;
	private LinearLayout ProfilePageTabUserInfoLayoutMarg;
	private ImageView ProfilePageTabUserInfoCoverImage;
	private LinearLayout ProfilePageTabUserInfoCardLayout;
	private LinearLayout prfLaySpc;
	private CardView ProfilePageTabUserInfoProfileCard;
	private ImageView ProfilePageTabUserInfoProfileImage;
	private LinearLayout likeUserProfileButton;
	private ImageView likeUserProfileButtonIc;
	private TextView likeUserProfileButtonLikeCount;
	private LinearLayout bannedUserInfo;
	private LinearLayout ProfilePageTabUserInfoNameLayout;
	private LinearLayout ProfilePageTabUserInfoStateDetails;
	private LinearLayout ProfilePageTabUserInfoFollowsDetails;
	private HorizontalScrollView ProfilePageTabUserInfoBadgesScroll;
	private LinearLayout ProfilePageTabUserInfoBioLayout;
	private LinearLayout join_date_layout;
	private LinearLayout user_uid_layout;
	private LinearLayout manage_account_panel;
	private ImageView bannedUserInfoIc;
	private TextView bannedUserInfoText;
	private TextView ProfilePageTabUserInfoNickname;
	private ImageView ProfilePageTabUserInfoGenderBadge;
	private CardView ProfilePageTabUserInfoRegionFlagCard;
	private ImageView ProfilePageTabUserInfoRegionFlag;
	private TextView ProfilePageTabUserInfoUsername;
	private TextView ProfilePageTabUserInfoStateSpc;
	private TextView ProfilePageTabUserInfoStatus;
	private TextView ProfilePageTabUserInfoFollowersCount;
	private TextView ProfilePageTabUserInfoSpc;
	private TextView ProfilePageTabUserInfoFollowingCount;
	private LinearLayout ProfilePageTabUserInfoBadgesScrollLayoutBody;
	private LinearLayout ProfilePageTabUserInfoBadgesScrollLayoutAccountTypeBadge;
	private LinearLayout ProfilePageTabUserInfoBadgesScrollLayoutVerifiedBadge;
	private LinearLayout ProfilePageTabUserInfoBadgesScrollLayoutPremiumBadge;
	private ImageView ProfilePageTabUserInfoBadgesScrollLayoutAccountTypeBadgeIc;
	private TextView ProfilePageTabUserInfoBadgesScrollLayoutAccountTypeBadgeTitle;
	private ImageView ProfilePageTabUserInfoBadgesScrollLayoutVerifiedBadgeIc;
	private TextView ProfilePageTabUserInfoBadgesScrollLayoutVerifiedBadgeTitle;
	private ImageView ProfilePageTabUserInfoBadgesScrollLayoutPremiumBadgeIc;
	private TextView ProfilePageTabUserInfoBadgesScrollLayoutPremiumBadgeTitle;
	private TextView ProfilePageTabUserInfoBioLayoutTitle;
	private TextView ProfilePageTabUserInfoBioLayoutText;
	private TextView join_date_layout_title;
	private TextView join_date_layout_text;
	private TextView user_uid_layout_title;
	private TextView user_uid_layout_text;
	private SwitchCompat manage_account_panel_verify;
	private SwitchCompat manage_account_panel_ban;
	private SwitchCompat manage_account_panel_premium;
	private ProgressBar ProfilePageLoadingBodyBar;
	
	private Intent intent = new Intent();
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
	private Calendar cc_1 = Calendar.getInstance();
	
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.preview_user_profile);
		initialize(_savedInstanceState);
		FirebaseApp.initializeApp(this);
		initializeLogic();
	}
	
	private void initialize(Bundle _savedInstanceState) {
		ProfilePageBody = findViewById(R.id.ProfilePageBody);
		ProfilePageTopBar = findViewById(R.id.ProfilePageTopBar);
		ProfilePageMiddleLayout = findViewById(R.id.ProfilePageMiddleLayout);
		ProfilePageTopBarBack = findViewById(R.id.ProfilePageTopBarBack);
		ProfilePageTopBarSpace = findViewById(R.id.ProfilePageTopBarSpace);
		ProfilePageTopBarMenu = findViewById(R.id.ProfilePageTopBarMenu);
		ProfilePageTopBarUsername = findViewById(R.id.ProfilePageTopBarUsername);
		ProfilePageSwipeLayout = findViewById(R.id.ProfilePageSwipeLayout);
		ProfilePageLoadingBody = findViewById(R.id.ProfilePageLoadingBody);
		ProfilePageSwipeLayoutBody = findViewById(R.id.ProfilePageSwipeLayoutBody);
		ProfilePageTabUserInfo = findViewById(R.id.ProfilePageTabUserInfo);
		ProfilePageTabUserInfoBody = findViewById(R.id.ProfilePageTabUserInfoBody);
		ProfilePageTabUserInfoCoverLayout = findViewById(R.id.ProfilePageTabUserInfoCoverLayout);
		prfLay = findViewById(R.id.prfLay);
		ProfilePageTabUserInfoLayoutMarg = findViewById(R.id.ProfilePageTabUserInfoLayoutMarg);
		ProfilePageTabUserInfoCoverImage = findViewById(R.id.ProfilePageTabUserInfoCoverImage);
		ProfilePageTabUserInfoCardLayout = findViewById(R.id.ProfilePageTabUserInfoCardLayout);
		prfLaySpc = findViewById(R.id.prfLaySpc);
		ProfilePageTabUserInfoProfileCard = findViewById(R.id.ProfilePageTabUserInfoProfileCard);
		ProfilePageTabUserInfoProfileImage = findViewById(R.id.ProfilePageTabUserInfoProfileImage);
		likeUserProfileButton = findViewById(R.id.likeUserProfileButton);
		likeUserProfileButtonIc = findViewById(R.id.likeUserProfileButtonIc);
		likeUserProfileButtonLikeCount = findViewById(R.id.likeUserProfileButtonLikeCount);
		bannedUserInfo = findViewById(R.id.bannedUserInfo);
		ProfilePageTabUserInfoNameLayout = findViewById(R.id.ProfilePageTabUserInfoNameLayout);
		ProfilePageTabUserInfoStateDetails = findViewById(R.id.ProfilePageTabUserInfoStateDetails);
		ProfilePageTabUserInfoFollowsDetails = findViewById(R.id.ProfilePageTabUserInfoFollowsDetails);
		ProfilePageTabUserInfoBadgesScroll = findViewById(R.id.ProfilePageTabUserInfoBadgesScroll);
		ProfilePageTabUserInfoBioLayout = findViewById(R.id.ProfilePageTabUserInfoBioLayout);
		join_date_layout = findViewById(R.id.join_date_layout);
		user_uid_layout = findViewById(R.id.user_uid_layout);
		manage_account_panel = findViewById(R.id.manage_account_panel);
		bannedUserInfoIc = findViewById(R.id.bannedUserInfoIc);
		bannedUserInfoText = findViewById(R.id.bannedUserInfoText);
		ProfilePageTabUserInfoNickname = findViewById(R.id.ProfilePageTabUserInfoNickname);
		ProfilePageTabUserInfoGenderBadge = findViewById(R.id.ProfilePageTabUserInfoGenderBadge);
		ProfilePageTabUserInfoRegionFlagCard = findViewById(R.id.ProfilePageTabUserInfoRegionFlagCard);
		ProfilePageTabUserInfoRegionFlag = findViewById(R.id.ProfilePageTabUserInfoRegionFlag);
		ProfilePageTabUserInfoUsername = findViewById(R.id.ProfilePageTabUserInfoUsername);
		ProfilePageTabUserInfoStateSpc = findViewById(R.id.ProfilePageTabUserInfoStateSpc);
		ProfilePageTabUserInfoStatus = findViewById(R.id.ProfilePageTabUserInfoStatus);
		ProfilePageTabUserInfoFollowersCount = findViewById(R.id.ProfilePageTabUserInfoFollowersCount);
		ProfilePageTabUserInfoSpc = findViewById(R.id.ProfilePageTabUserInfoSpc);
		ProfilePageTabUserInfoFollowingCount = findViewById(R.id.ProfilePageTabUserInfoFollowingCount);
		ProfilePageTabUserInfoBadgesScrollLayoutBody = findViewById(R.id.ProfilePageTabUserInfoBadgesScrollLayoutBody);
		ProfilePageTabUserInfoBadgesScrollLayoutAccountTypeBadge = findViewById(R.id.ProfilePageTabUserInfoBadgesScrollLayoutAccountTypeBadge);
		ProfilePageTabUserInfoBadgesScrollLayoutVerifiedBadge = findViewById(R.id.ProfilePageTabUserInfoBadgesScrollLayoutVerifiedBadge);
		ProfilePageTabUserInfoBadgesScrollLayoutPremiumBadge = findViewById(R.id.ProfilePageTabUserInfoBadgesScrollLayoutPremiumBadge);
		ProfilePageTabUserInfoBadgesScrollLayoutAccountTypeBadgeIc = findViewById(R.id.ProfilePageTabUserInfoBadgesScrollLayoutAccountTypeBadgeIc);
		ProfilePageTabUserInfoBadgesScrollLayoutAccountTypeBadgeTitle = findViewById(R.id.ProfilePageTabUserInfoBadgesScrollLayoutAccountTypeBadgeTitle);
		ProfilePageTabUserInfoBadgesScrollLayoutVerifiedBadgeIc = findViewById(R.id.ProfilePageTabUserInfoBadgesScrollLayoutVerifiedBadgeIc);
		ProfilePageTabUserInfoBadgesScrollLayoutVerifiedBadgeTitle = findViewById(R.id.ProfilePageTabUserInfoBadgesScrollLayoutVerifiedBadgeTitle);
		ProfilePageTabUserInfoBadgesScrollLayoutPremiumBadgeIc = findViewById(R.id.ProfilePageTabUserInfoBadgesScrollLayoutPremiumBadgeIc);
		ProfilePageTabUserInfoBadgesScrollLayoutPremiumBadgeTitle = findViewById(R.id.ProfilePageTabUserInfoBadgesScrollLayoutPremiumBadgeTitle);
		ProfilePageTabUserInfoBioLayoutTitle = findViewById(R.id.ProfilePageTabUserInfoBioLayoutTitle);
		ProfilePageTabUserInfoBioLayoutText = findViewById(R.id.ProfilePageTabUserInfoBioLayoutText);
		join_date_layout_title = findViewById(R.id.join_date_layout_title);
		join_date_layout_text = findViewById(R.id.join_date_layout_text);
		user_uid_layout_title = findViewById(R.id.user_uid_layout_title);
		user_uid_layout_text = findViewById(R.id.user_uid_layout_text);
		manage_account_panel_verify = findViewById(R.id.manage_account_panel_verify);
		manage_account_panel_ban = findViewById(R.id.manage_account_panel_ban);
		manage_account_panel_premium = findViewById(R.id.manage_account_panel_premium);
		ProfilePageLoadingBodyBar = findViewById(R.id.ProfilePageLoadingBodyBar);
		auth = FirebaseAuth.getInstance();
		
		ProfilePageTopBarBack.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				onBackPressed();
			}
		});
		
		ProfilePageTopBarMenu.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				
			}
		});
		
		ProfilePageSwipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
			@Override
			public void onRefresh() {
				_getUserMainReference();
			}
		});
		
		manage_account_panel_verify.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				if (manage_account_panel_verify.isChecked()) {
					FirebaseDatabase.getInstance().getReference("skyline/users").child(getIntent().getStringExtra("uid")).child("verify").setValue("true");
				} else {
					FirebaseDatabase.getInstance().getReference("skyline/users").child(getIntent().getStringExtra("uid")).child("verify").setValue("false");
				}
			}
		});
		
		manage_account_panel_ban.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				if (manage_account_panel_ban.isChecked()) {
					FirebaseDatabase.getInstance().getReference("skyline/users").child(getIntent().getStringExtra("uid")).child("banned").setValue("true");
				} else {
					FirebaseDatabase.getInstance().getReference("skyline/users").child(getIntent().getStringExtra("uid")).child("banned").setValue("false");
				}
			}
		});
		
		manage_account_panel_premium.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				if (manage_account_panel_premium.isChecked()) {
					FirebaseDatabase.getInstance().getReference("skyline/users").child(getIntent().getStringExtra("uid")).child("account_premium").setValue("true");
				} else {
					FirebaseDatabase.getInstance().getReference("skyline/users").child(getIntent().getStringExtra("uid")).child("account_premium").setValue("false");
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
		_stateColor(0xFFFFFFFF, 0xFFFFFFFF);
		_viewGraphics(ProfilePageTopBarBack, 0xFFFFFFFF, 0xFFEEEEEE, 300, 0, Color.TRANSPARENT);
		_viewGraphics(ProfilePageTopBarMenu, 0xFFFFFFFF, 0xFFEEEEEE, 300, 0, Color.TRANSPARENT);
		ProfilePageTabUserInfoProfileCard.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b) { this.setCornerRadius(a); this.setColor(b); return this; } }.getIns((int)300, Color.TRANSPARENT));
		ProfilePageTabUserInfoCardLayout.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b) { this.setCornerRadius(a); this.setColor(b); return this; } }.getIns((int)300, 0xFFFFFFFF));
		bannedUserInfo.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b) { this.setCornerRadius(a); this.setColor(b); return this; } }.getIns((int)28, 0xFFE91E63));
		_ImageColor(bannedUserInfoIc, 0xFFFFFFFF);
		_viewGraphics(likeUserProfileButton, 0xFFFFFFFF, 0xFFEEEEEE, 300, 0, 0xFF9E9E9E);
		likeUserProfileButton.setElevation((float)2);
		ProfilePageTabUserInfoBioLayout.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b) { this.setCornerRadius(a); this.setColor(b); return this; } }.getIns((int)28, 0xFFF5F5F5));
		manage_account_panel.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b) { this.setCornerRadius(a); this.setColor(b); return this; } }.getIns((int)28, 0xFFF5F5F5));
		_getUserMainReference();
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
	
	
	public void _getUserMainReference() {
		ProfilePageSwipeLayout.setVisibility(View.GONE);
		ProfilePageTopBarUsername.setVisibility(View.GONE);
		ProfilePageLoadingBody.setVisibility(View.VISIBLE);
		DatabaseReference getUserReference = FirebaseDatabase.getInstance().getReference("skyline/users").child(getIntent().getStringExtra("uid"));
		getUserReference.addValueEventListener(new ValueEventListener() {
			@Override
			public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
				if(dataSnapshot.exists()) {
					ProfilePageSwipeLayout.setVisibility(View.VISIBLE);
					ProfilePageTopBarUsername.setVisibility(View.VISIBLE);
					ProfilePageLoadingBody.setVisibility(View.GONE);
					user_uid_layout_text.setText(dataSnapshot.child("uid").getValue(String.class));
					cc_1.setTimeInMillis((long)(Double.parseDouble(dataSnapshot.child("join_date").getValue(String.class))));
					if (dataSnapshot.child("banned").getValue(String.class).equals("true")) {
						ProfilePageTabUserInfoProfileImage.setImageResource(R.drawable.banned_avatar);
						ProfilePageTabUserInfoCoverImage.setImageResource(R.drawable.banned_cover_photo);
						bannedUserInfo.setVisibility(View.VISIBLE);
						manage_account_panel_ban.setChecked(true);
					} else {
						if (dataSnapshot.child("profile_cover_image").getValue(String.class).equals("null")) {
							ProfilePageTabUserInfoCoverImage.setImageResource(R.drawable.user_null_cover_photo);
						} else {
							Glide.with(getApplicationContext()).load(Uri.parse(dataSnapshot.child("profile_cover_image").getValue(String.class))).into(ProfilePageTabUserInfoCoverImage);
						}
						if (dataSnapshot.child("avatar").getValue(String.class).equals("null")) {
							ProfilePageTabUserInfoProfileImage.setImageResource(R.drawable.avatar);
						} else {
							Glide.with(getApplicationContext()).load(Uri.parse(dataSnapshot.child("avatar").getValue(String.class))).into(ProfilePageTabUserInfoProfileImage);
						}
						bannedUserInfo.setVisibility(View.GONE);
						manage_account_panel_ban.setChecked(false);
					}
					if (dataSnapshot.child("status").getValue(String.class).equals("online")) {
						ProfilePageTabUserInfoStatus.setText("online");
						ProfilePageTabUserInfoStatus.setTextColor(0xFF2196F3);
					} else {
						if (dataSnapshot.child("status").getValue(String.class).equals("offline")) {
							ProfilePageTabUserInfoStatus.setText("offline");
						} else {
							_setUserLastSeen(Double.parseDouble(dataSnapshot.child("status").getValue(String.class)), ProfilePageTabUserInfoStatus);
						}
						ProfilePageTabUserInfoStatus.setTextColor(0xFF757575);
					}
					ProfilePageTopBarUsername.setText("@" + dataSnapshot.child("username").getValue(String.class));
					ProfilePageTabUserInfoUsername.setText("@" + dataSnapshot.child("username").getValue(String.class));
					if (dataSnapshot.child("nickname").getValue(String.class).equals("null")) {
						ProfilePageTabUserInfoNickname.setText("@" + dataSnapshot.child("username").getValue(String.class));
					} else {
						ProfilePageTabUserInfoNickname.setText(dataSnapshot.child("nickname").getValue(String.class));
					}
					if (dataSnapshot.child("biography").getValue(String.class).equals("null")) {
						ProfilePageTabUserInfoBioLayout.setVisibility(View.GONE);
					} else {
						ProfilePageTabUserInfoBioLayoutText.setText(dataSnapshot.child("biography").getValue(String.class));
						ProfilePageTabUserInfoBioLayout.setVisibility(View.VISIBLE);
					}
					if (dataSnapshot.child("gender").getValue(String.class).equals("hidden")) {
						ProfilePageTabUserInfoGenderBadge.setVisibility(View.GONE);
					} else {
						if (dataSnapshot.child("gender").getValue(String.class).equals("male")) {
							ProfilePageTabUserInfoGenderBadge.setImageResource(R.drawable.male_badge);
							ProfilePageTabUserInfoGenderBadge.setVisibility(View.VISIBLE);
						} else {
							if (dataSnapshot.child("gender").getValue(String.class).equals("female")) {
								ProfilePageTabUserInfoGenderBadge.setImageResource(R.drawable.female_badge);
								ProfilePageTabUserInfoGenderBadge.setVisibility(View.VISIBLE);
							}
						}
					}
					if (dataSnapshot.child("user_region").getValue(String.class) != null) {
						Glide.with(getApplicationContext()).load(Uri.parse("https://flagcdn.com/w640/".concat(dataSnapshot.child("user_region").getValue(String.class).concat(".png")))).into(ProfilePageTabUserInfoRegionFlag);
						ProfilePageTabUserInfoRegionFlagCard.setVisibility(View.VISIBLE);
					} else {
						ProfilePageTabUserInfoRegionFlagCard.setVisibility(View.GONE);
					}
					if (dataSnapshot.child("account_type").getValue(String.class).equals("admin")) {
						ProfilePageTabUserInfoBadgesScrollLayoutAccountTypeBadge.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b, int c, int d) { this.setCornerRadius(a); this.setStroke(b, c); this.setColor(d); return this; } }.getIns((int)24, (int)2, 0xFF651FFF, 0xFFFFFFFF));
						ProfilePageTabUserInfoBadgesScrollLayoutAccountTypeBadgeIc.setImageResource(R.drawable.admin_badge);
						ProfilePageTabUserInfoBadgesScrollLayoutAccountTypeBadgeTitle.setText("Admin");
						ProfilePageTabUserInfoBadgesScrollLayoutAccountTypeBadgeTitle.setTextColor(0xFF651FFF);
						ProfilePageTabUserInfoBadgesScrollLayoutAccountTypeBadge.setVisibility(View.VISIBLE);
					} else {
						if (dataSnapshot.child("account_type").getValue(String.class).equals("moderator")) {
							ProfilePageTabUserInfoBadgesScrollLayoutAccountTypeBadge.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b, int c, int d) { this.setCornerRadius(a); this.setStroke(b, c); this.setColor(d); return this; } }.getIns((int)24, (int)2, 0xFF2196F3, 0xFFFFFFFF));
							ProfilePageTabUserInfoBadgesScrollLayoutAccountTypeBadgeIc.setImageResource(R.drawable.moderator_badge);
							ProfilePageTabUserInfoBadgesScrollLayoutAccountTypeBadgeTitle.setText("Moderator");
							ProfilePageTabUserInfoBadgesScrollLayoutAccountTypeBadgeTitle.setTextColor(0xFF2196F3);
							ProfilePageTabUserInfoBadgesScrollLayoutAccountTypeBadge.setVisibility(View.VISIBLE);
						} else {
							if (dataSnapshot.child("account_type").getValue(String.class).equals("support")) {
								ProfilePageTabUserInfoBadgesScrollLayoutAccountTypeBadge.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b, int c, int d) { this.setCornerRadius(a); this.setStroke(b, c); this.setColor(d); return this; } }.getIns((int)24, (int)2, 0xFF03A9F4, 0xFFFFFFFF));
								ProfilePageTabUserInfoBadgesScrollLayoutAccountTypeBadgeIc.setImageResource(R.drawable.support_badge);
								ProfilePageTabUserInfoBadgesScrollLayoutAccountTypeBadgeTitle.setText("Support");
								ProfilePageTabUserInfoBadgesScrollLayoutAccountTypeBadgeTitle.setTextColor(0xFF03A9F4);
								ProfilePageTabUserInfoBadgesScrollLayoutAccountTypeBadgeIc.setVisibility(View.VISIBLE);
							} else {
								ProfilePageTabUserInfoBadgesScrollLayoutAccountTypeBadge.setVisibility(View.GONE);
							}
						}
					}
					if (dataSnapshot.child("account_premium").getValue(String.class).equals("true")) {
						ProfilePageTabUserInfoBadgesScrollLayoutPremiumBadge.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b, int c, int d) { this.setCornerRadius(a); this.setStroke(b, c); this.setColor(d); return this; } }.getIns((int)24, (int)2, 0xFFFF9800, 0xFFFFFFFF));
						ProfilePageTabUserInfoBadgesScrollLayoutPremiumBadge.setVisibility(View.VISIBLE);
						manage_account_panel_premium.setChecked(true);
					} else {
						ProfilePageTabUserInfoBadgesScrollLayoutPremiumBadge.setVisibility(View.GONE);
						manage_account_panel_premium.setChecked(false);
					}
					if (dataSnapshot.child("verify").getValue(String.class).equals("true")) {
						ProfilePageTabUserInfoBadgesScrollLayoutVerifiedBadge.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b, int c, int d) { this.setCornerRadius(a); this.setStroke(b, c); this.setColor(d); return this; } }.getIns((int)24, (int)2, 0xFF2196F3, 0xFFFFFFFF));
						ProfilePageTabUserInfoBadgesScrollLayoutVerifiedBadge.setVisibility(View.VISIBLE);
						manage_account_panel_verify.setChecked(true);
					} else {
						ProfilePageTabUserInfoBadgesScrollLayoutVerifiedBadge.setVisibility(View.GONE);
						manage_account_panel_verify.setChecked(false);
					}
					join_date_layout_text.setText(new SimpleDateFormat("dd-MM-yyyy").format(cc_1.getTime()));
					if (ProfilePageTabUserInfoBadgesScrollLayoutAccountTypeBadge.getVisibility() == View.VISIBLE || (ProfilePageTabUserInfoBadgesScrollLayoutVerifiedBadge.getVisibility() == View.VISIBLE || ProfilePageTabUserInfoBadgesScrollLayoutPremiumBadge.getVisibility() == View.VISIBLE)) {
						ProfilePageTabUserInfoBadgesScroll.setVisibility(View.VISIBLE);
					} else {
						ProfilePageTabUserInfoBadgesScroll.setVisibility(View.GONE);
					}
				} else {
				}
			}
			
			@Override
			public void onCancelled(@NonNull DatabaseError databaseError) {
						
			}
		});
		ProfilePageSwipeLayout.setRefreshing(false);
	}
	
	
	public void _setCount(final TextView _txt, final double _number) {
		if (_number < 10000) {
				_txt.setText(String.valueOf((long) _number));
		} else {
				DecimalFormat decimalFormat = new DecimalFormat("0.0");
				String numberFormat;
				double formattedNumber;
				if (_number < 1000000) {
						numberFormat = "K";
						formattedNumber = _number / 1000;
				} else if (_number < 1000000000) {
						numberFormat = "M";
						formattedNumber = _number / 1000000;
				} else if (_number < 1000000000000L) {
						numberFormat = "B";
						formattedNumber = _number / 1000000000;
				} else {
						numberFormat = "T";
						formattedNumber = _number / 1000000000000L;
				}
				_txt.setText(decimalFormat.format(formattedNumber) + numberFormat);
		}
		
	}
	
	
	public void _setUserLastSeen(final double _currentTime, final TextView _txt) {
		Calendar c1 = Calendar.getInstance();
		Calendar c2 = Calendar.getInstance();
		c2.setTimeInMillis((long)_currentTime);
		
		long time_diff = c1.getTimeInMillis() - c2.getTimeInMillis();
		
		long seconds = time_diff / 1000;
		long minutes = seconds / 60;
		long hours = minutes / 60;
		long days = hours / 24;
		long weeks = days / 7;
		long months = days / 30;
		long years = days / 365;
		
		if (seconds < 60) {
				if (seconds < 2) {
						_txt.setText("1 " + "seconds ago");
				} else {
						_txt.setText(String.valueOf(seconds) + " " + "seconds ago");
				}
		} else if (minutes < 60) {
				if (minutes < 2) {
						_txt.setText("1 " + "minutes ago");
				} else {
						_txt.setText(String.valueOf(minutes) + " " + "minutes ago");
				}
		} else if (hours < 24) {
				if (hours < 2) {
						_txt.setText("1 " + "hours ago");
				} else {
						_txt.setText(String.valueOf(hours) + " " + "hours ago");
				}
		} else if (days < 7) {
				if (days < 2) {
						_txt.setText("1 " + "days ago");
				} else {
						_txt.setText(String.valueOf(days) + " " + "days ago");
				}
		} else if (weeks < 4) {
				if (weeks < 2) {
						_txt.setText("1 " + "weeks ago");
				} else {
						_txt.setText(String.valueOf(weeks) + " " + "weeks ago");
				}
		} else if (months < 12) {
				if (months < 2) {
						_txt.setText("1 " + "months ago");
				} else {
						_txt.setText(String.valueOf(months) + " " + "months ago");
				}
		} else {
				if (years < 2) {
						_txt.setText("1 " + "years ago");
				} else {
						_txt.setText(String.valueOf(years) + " " + "years ago");
				}
		}
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
