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
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.annotation.*;
import androidx.appcompat.app.AppCompatActivity;
import androidx.browser.*;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.*;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.Adapter;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout.OnRefreshListener;
import com.bumptech.glide.*;
import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.*;
import com.google.android.material.color.MaterialColors;
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
import com.theartofdev.edmodo.cropper.*;
import com.yalantis.ucrop.*;
import java.io.*;
import java.io.InputStream;
import java.text.*;
import java.util.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.*;
import org.json.*;
import com.google.firebase.database.Query;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.appbar.AppBarLayout;
import android.os.Handler;
import android.os.Looper;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class UserFollowsListActivity extends AppCompatActivity {
	
	private FirebaseDatabase _firebase = FirebaseDatabase.getInstance();
	
	private ProgressDialog SynapseLoadingDialog;
	private HashMap<String, Object> UserInfoCacheMap = new HashMap<>();
	
	private ArrayList<HashMap<String, Object>> mFollowersList = new ArrayList<>();
	private ArrayList<HashMap<String, Object>> mFollowingList = new ArrayList<>();
	
	private LinearLayout body;
	private LinearLayout top;
	private LinearLayout topSpace;
	private LinearLayout m_coordinator_layout;
	private LinearLayout mLoadingLayout;
	private ImageView back;
	private LinearLayout topProfileLayout;
	private LinearLayout topProfileLayoutSpace;
	private ImageView more;
	private CardView topProfileCard;
	private LinearLayout topProfileLayoutRight;
	private ImageView topProfileLayoutProfileImage;
	private LinearLayout topProfileLayoutRightTop;
	private TextView topProfileLayoutUsername2;
	private TextView topProfileLayoutUsername;
	private ImageView topProfileLayoutGenderBadge;
	private ImageView topProfileLayoutVerifiedBadge;
	private LinearLayout m_coordinator_layout_appbar;
	private SwipeRefreshLayout swipe_layout;
	private LinearLayout m_coordinator_layout_collapsing_toolbar;
	private LinearLayout m_coordinator_layout_collapsing_toolbar_body;
	private TextView tab_followers;
	private TextView tab_followings;
	private LinearLayout swipe_layout_body;
	private LinearLayout followers_layout;
	private LinearLayout following_layout;
	private RecyclerView followers_layout_list;
	private TextView followers_layout_no_followers;
	private LinearLayout followers_layout_loading;
	private ProgressBar followers_layout_loading_bar;
	private RecyclerView following_layout_list;
	private TextView following_layout_no_follow;
	private LinearLayout following_layout_loading;
	private ProgressBar following_layout_loading_bar;
	private ProgressBar mLoadingLayoutBar;
	
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
	
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.user_follows_list);
		initialize(_savedInstanceState);
		FirebaseApp.initializeApp(this);
		initializeLogic();
	}
	
	private void initialize(Bundle _savedInstanceState) {
		body = findViewById(R.id.body);
		top = findViewById(R.id.top);
		topSpace = findViewById(R.id.topSpace);
		m_coordinator_layout = findViewById(R.id.m_coordinator_layout);
		mLoadingLayout = findViewById(R.id.mLoadingLayout);
		back = findViewById(R.id.back);
		topProfileLayout = findViewById(R.id.topProfileLayout);
		topProfileLayoutSpace = findViewById(R.id.topProfileLayoutSpace);
		more = findViewById(R.id.more);
		topProfileCard = findViewById(R.id.topProfileCard);
		topProfileLayoutRight = findViewById(R.id.topProfileLayoutRight);
		topProfileLayoutProfileImage = findViewById(R.id.topProfileLayoutProfileImage);
		topProfileLayoutRightTop = findViewById(R.id.topProfileLayoutRightTop);
		topProfileLayoutUsername2 = findViewById(R.id.topProfileLayoutUsername2);
		topProfileLayoutUsername = findViewById(R.id.topProfileLayoutUsername);
		topProfileLayoutGenderBadge = findViewById(R.id.topProfileLayoutGenderBadge);
		topProfileLayoutVerifiedBadge = findViewById(R.id.topProfileLayoutVerifiedBadge);
		m_coordinator_layout_appbar = findViewById(R.id.m_coordinator_layout_appbar);
		swipe_layout = findViewById(R.id.swipe_layout);
		m_coordinator_layout_collapsing_toolbar = findViewById(R.id.m_coordinator_layout_collapsing_toolbar);
		m_coordinator_layout_collapsing_toolbar_body = findViewById(R.id.m_coordinator_layout_collapsing_toolbar_body);
		tab_followers = findViewById(R.id.tab_followers);
		tab_followings = findViewById(R.id.tab_followings);
		swipe_layout_body = findViewById(R.id.swipe_layout_body);
		followers_layout = findViewById(R.id.followers_layout);
		following_layout = findViewById(R.id.following_layout);
		followers_layout_list = findViewById(R.id.followers_layout_list);
		followers_layout_no_followers = findViewById(R.id.followers_layout_no_followers);
		followers_layout_loading = findViewById(R.id.followers_layout_loading);
		followers_layout_loading_bar = findViewById(R.id.followers_layout_loading_bar);
		following_layout_list = findViewById(R.id.following_layout_list);
		following_layout_no_follow = findViewById(R.id.following_layout_no_follow);
		following_layout_loading = findViewById(R.id.following_layout_loading);
		following_layout_loading_bar = findViewById(R.id.following_layout_loading_bar);
		mLoadingLayoutBar = findViewById(R.id.mLoadingLayoutBar);
		auth = FirebaseAuth.getInstance();
		
		back.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				onBackPressed();
			}
		});
		
		swipe_layout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
			@Override
			public void onRefresh() {
				_getFollowersReference();
				_getFollowingReference();
				swipe_layout.setRefreshing(false);
			}
		});
		
		tab_followers.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				_setTab(0);
			}
		});
		
		tab_followings.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				_setTab(1);
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
		_viewGraphics(back, 0xFFFFFFFF, 0xFFEEEEEE, 300, 0, Color.TRANSPARENT);
		topProfileCard.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b) { this.setCornerRadius(a); this.setColor(b); return this; } }.getIns((int)300, Color.TRANSPARENT));
		followers_layout_list.setLayoutManager(new LinearLayoutManager(this));
		following_layout_list.setLayoutManager(new LinearLayoutManager(this));
		followers_layout_list.setAdapter(new Followers_layout_listAdapter(mFollowersList));
		following_layout_list.setAdapter(new Following_layout_listAdapter(mFollowingList));
		_setTab(0);
		_getUserReference();
		/*
		*/
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
	
	
	public void _getFollowersReference() {
		followers_layout_list.setVisibility(View.GONE);
		followers_layout_no_followers.setVisibility(View.GONE);
		followers_layout_loading.setVisibility(View.VISIBLE);
		Query getFollowersRef = FirebaseDatabase.getInstance().getReference("skyline/followers").child(getIntent().getStringExtra("uid"));
		getFollowersRef.addListenerForSingleValueEvent(new ValueEventListener() {
			@Override
			public void onDataChange(DataSnapshot _dataSnapshot) {
				if(_dataSnapshot.exists()) {
					followers_layout_list.setVisibility(View.VISIBLE);
					followers_layout_no_followers.setVisibility(View.GONE);
					followers_layout_loading.setVisibility(View.GONE);
					
					mFollowersList.clear();
					for (DataSnapshot snapshot : _dataSnapshot.getChildren()) {
						String secondUid = snapshot.getKey();
						HashMap<String, Object> map = new HashMap<>();
						map.put("uid", secondUid);
						mFollowersList.add(map);
					}
					
					followers_layout_list.setAdapter(new Followers_layout_listAdapter(mFollowersList));
				} else {
					followers_layout_list.setVisibility(View.GONE);
					followers_layout_no_followers.setVisibility(View.VISIBLE);
					followers_layout_loading.setVisibility(View.GONE);
				}
			}
			
			@Override
			public void onCancelled(DatabaseError _databaseError) {
				
			}
		});
	}
	
	
	public void _getFollowingReference() {
		following_layout_list.setVisibility(View.GONE);
		following_layout_no_follow.setVisibility(View.GONE);
		following_layout_loading.setVisibility(View.VISIBLE);
		Query getFollowersRef = FirebaseDatabase.getInstance().getReference("skyline/following").child(getIntent().getStringExtra("uid"));
		getFollowersRef.addListenerForSingleValueEvent(new ValueEventListener() {
			@Override
			public void onDataChange(DataSnapshot _dataSnapshot) {
				if(_dataSnapshot.exists()) {
					following_layout_list.setVisibility(View.VISIBLE);
					following_layout_no_follow.setVisibility(View.GONE);
					following_layout_loading.setVisibility(View.GONE);
					
					mFollowingList.clear();
					for (DataSnapshot snapshot : _dataSnapshot.getChildren()) {
						String secondUid = snapshot.getKey();
						HashMap<String, Object> map = new HashMap<>();
						map.put("uid", secondUid);
						mFollowingList.add(map);
					}
					
					following_layout_list.setAdapter(new Following_layout_listAdapter(mFollowingList));
				} else {
					following_layout_list.setVisibility(View.GONE);
					following_layout_no_follow.setVisibility(View.VISIBLE);
					following_layout_loading.setVisibility(View.GONE);
				}
			}
			
			@Override
			public void onCancelled(DatabaseError _databaseError) {
				
			}
		});
	}
	
	
	public void _getUserReference() {
		m_coordinator_layout.setVisibility(View.GONE);
		topProfileLayout.setVisibility(View.GONE);
		mLoadingLayout.setVisibility(View.VISIBLE);
		DatabaseReference getUserReference = FirebaseDatabase.getInstance().getReference("skyline/users").child(getIntent().getStringExtra("uid"));
		getUserReference.addListenerForSingleValueEvent(new ValueEventListener() {
			@Override
			public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
				if(dataSnapshot.exists()) {
					_getFollowersReference();
					_getFollowingReference();
					if (dataSnapshot.child("banned").getValue(String.class).equals("true")) {
						topProfileLayoutProfileImage.setImageResource(R.drawable.banned_avatar);
					} else {
						if (dataSnapshot.child("avatar").getValue(String.class).equals("null")) {
							topProfileLayoutProfileImage.setImageResource(R.drawable.avatar);
						} else {
							Glide.with(getApplicationContext()).load(Uri.parse(dataSnapshot.child("avatar").getValue(String.class))).into(topProfileLayoutProfileImage);
						}
					}
					topProfileLayoutUsername2.setText("@" + dataSnapshot.child("username").getValue(String.class));
					if (dataSnapshot.child("nickname").getValue(String.class).equals("null")) {
						topProfileLayoutUsername.setText("@" + dataSnapshot.child("username").getValue(String.class));
					} else {
						topProfileLayoutUsername.setText(dataSnapshot.child("nickname").getValue(String.class));
					}
					if (dataSnapshot.child("gender").getValue(String.class).equals("hidden")) {
						topProfileLayoutGenderBadge.setVisibility(View.GONE);
					} else {
						if (dataSnapshot.child("gender").getValue(String.class).equals("male")) {
							topProfileLayoutGenderBadge.setImageResource(R.drawable.male_badge);
							topProfileLayoutGenderBadge.setVisibility(View.VISIBLE);
						} else {
							if (dataSnapshot.child("gender").getValue(String.class).equals("female")) {
								topProfileLayoutGenderBadge.setImageResource(R.drawable.female_badge);
								topProfileLayoutGenderBadge.setVisibility(View.VISIBLE);
							}
						}
					}
					if (dataSnapshot.child("account_type").getValue(String.class).equals("admin")) {
						topProfileLayoutVerifiedBadge.setImageResource(R.drawable.admin_badge);
						topProfileLayoutVerifiedBadge.setVisibility(View.VISIBLE);
					} else {
						if (dataSnapshot.child("account_type").getValue(String.class).equals("moderator")) {
							topProfileLayoutVerifiedBadge.setImageResource(R.drawable.moderator_badge);
							topProfileLayoutVerifiedBadge.setVisibility(View.VISIBLE);
						} else {
							if (dataSnapshot.child("account_type").getValue(String.class).equals("support")) {
								topProfileLayoutVerifiedBadge.setImageResource(R.drawable.support_badge);
								topProfileLayoutVerifiedBadge.setVisibility(View.VISIBLE);
							} else {
								if (dataSnapshot.child("account_premium").getValue(String.class).equals("true")) {
									topProfileLayoutVerifiedBadge.setImageResource(R.drawable.premium_badge);
									topProfileLayoutVerifiedBadge.setVisibility(View.VISIBLE);
								} else {
									if (dataSnapshot.child("verify").getValue(String.class).equals("true")) {
										topProfileLayoutVerifiedBadge.setImageResource(R.drawable.verified_badge);
										topProfileLayoutVerifiedBadge.setVisibility(View.VISIBLE);
									} else {
										topProfileLayoutVerifiedBadge.setVisibility(View.GONE);
									}
								}
							}
						}
					}
					m_coordinator_layout.setVisibility(View.VISIBLE);
					topProfileLayout.setVisibility(View.VISIBLE);
					mLoadingLayout.setVisibility(View.GONE);
				} else {
					finish();
				}
			}
			
			@Override
			public void onCancelled(@NonNull DatabaseError databaseError) {
				
			}
		});
	}
	
	
	public void _setTab(final double _id) {
		if (_id == 0) {
			_viewGraphics(tab_followers, getResources().getColor(R.color.colorPrimary), 0xFF3F51B5, 300, 0, Color.TRANSPARENT);
			_viewGraphics(tab_followings, 0xFFFFFFFF, 0xFFEEEEEE, 300, 2, 0xFFEEEEEE);
			tab_followers.setTextColor(0xFFFFFFFF);
			tab_followings.setTextColor(0xFF616161);
			followers_layout.setVisibility(View.VISIBLE);
			following_layout.setVisibility(View.GONE);
		}
		if (_id == 1) {
			_viewGraphics(tab_followers, 0xFFFFFFFF, 0xFFEEEEEE, 300, 2, 0xFFEEEEEE);
			_viewGraphics(tab_followings, getResources().getColor(R.color.colorPrimary), 0xFF3949AB, 300, 0, Color.TRANSPARENT);
			tab_followers.setTextColor(0xFF616161);
			tab_followings.setTextColor(0xFFFFFFFF);
			followers_layout.setVisibility(View.GONE);
			following_layout.setVisibility(View.VISIBLE);
		}
	}
	
	public class Followers_layout_listAdapter extends RecyclerView.Adapter<Followers_layout_listAdapter.ViewHolder> {
		
		ArrayList<HashMap<String, Object>> _data;
		
		public Followers_layout_listAdapter(ArrayList<HashMap<String, Object>> _arr) {
			_data = _arr;
		}
		
		@Override
		public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
			LayoutInflater _inflater = getLayoutInflater();
			View _v = _inflater.inflate(R.layout.user_followers_list, null);
			RecyclerView.LayoutParams _lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
			_v.setLayoutParams(_lp);
			return new ViewHolder(_v);
		}
		
		@Override
		public void onBindViewHolder(ViewHolder _holder, final int _position) {
			View _view = _holder.itemView;
			
			final LinearLayout body = _view.findViewById(R.id.body);
			final RelativeLayout profileCardRelative = _view.findViewById(R.id.profileCardRelative);
			final LinearLayout lin = _view.findViewById(R.id.lin);
			final androidx.cardview.widget.CardView profileCard = _view.findViewById(R.id.profileCard);
			final LinearLayout ProfileRelativeUp = _view.findViewById(R.id.ProfileRelativeUp);
			final ImageView profileAvatar = _view.findViewById(R.id.profileAvatar);
			final LinearLayout userStatusCircleBG = _view.findViewById(R.id.userStatusCircleBG);
			final LinearLayout userStatusCircleIN = _view.findViewById(R.id.userStatusCircleIN);
			final LinearLayout usr = _view.findViewById(R.id.usr);
			final TextView name = _view.findViewById(R.id.name);
			final TextView username = _view.findViewById(R.id.username);
			final ImageView genderBadge = _view.findViewById(R.id.genderBadge);
			final ImageView badge = _view.findViewById(R.id.badge);
			
			RecyclerView.LayoutParams _lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
			_view.setLayoutParams(_lp);
			_viewGraphics(body, 0xFFFFFFFF, 0xFFEEEEEE, 0, 0, Color.TRANSPARENT);
			profileCard.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b) { this.setCornerRadius(a); this.setColor(b); return this; } }.getIns((int)300, Color.TRANSPARENT));
			userStatusCircleBG.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b) { this.setCornerRadius(a); this.setColor(b); return this; } }.getIns((int)300, 0xFFFFFFFF));
			userStatusCircleIN.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b) { this.setCornerRadius(a); this.setColor(b); return this; } }.getIns((int)300, 0xFF2196F3));
			body.setVisibility(View.GONE);
			if (UserInfoCacheMap.containsKey("uid-".concat(_data.get((int)_position).get("uid").toString()))) {
				if (UserInfoCacheMap.get("banned-".concat(_data.get((int)_position).get("uid").toString())).toString().equals("true")) {
					profileAvatar.setImageResource(R.drawable.banned_avatar);
				} else {
					if (UserInfoCacheMap.get("avatar-".concat(_data.get((int)_position).get("uid").toString())).toString().equals("null")) {
						profileAvatar.setImageResource(R.drawable.avatar);
					} else {
						Glide.with(getApplicationContext()).load(Uri.parse(UserInfoCacheMap.get("avatar-".concat(_data.get((int)_position).get("uid").toString())).toString())).into(profileAvatar);
					}
				}
				if (UserInfoCacheMap.get("status-".concat(_data.get((int)_position).get("uid").toString())).toString().equals("online")) {
					userStatusCircleBG.setVisibility(View.VISIBLE);
				} else {
					userStatusCircleBG.setVisibility(View.GONE);
				}
				if (UserInfoCacheMap.get("nickname-".concat(_data.get((int)_position).get("uid").toString())).toString().equals("null")) {
					username.setText("@" + UserInfoCacheMap.get("username-".concat(_data.get((int)_position).get("uid").toString())).toString());
				} else {
					username.setText(UserInfoCacheMap.get("nickname-".concat(_data.get((int)_position).get("uid").toString())).toString());
				}
				name.setText("@" + UserInfoCacheMap.get("username-".concat(_data.get((int)_position).get("uid").toString())).toString());
				if (UserInfoCacheMap.get("gender-".concat(_data.get((int)_position).get("uid").toString())).toString().equals("hidden")) {
					genderBadge.setVisibility(View.GONE);
				} else {
					if (UserInfoCacheMap.get("gender-".concat(_data.get((int)_position).get("uid").toString())).toString().equals("male")) {
						genderBadge.setImageResource(R.drawable.male_badge);
						genderBadge.setVisibility(View.VISIBLE);
					} else {
						if (UserInfoCacheMap.get("gender-".concat(_data.get((int)_position).get("uid").toString())).toString().equals("female")) {
							genderBadge.setImageResource(R.drawable.female_badge);
							genderBadge.setVisibility(View.VISIBLE);
						}
					}
				}
				if (UserInfoCacheMap.get("acc_type-".concat(_data.get((int)_position).get("uid").toString())).toString().equals("admin")) {
					badge.setImageResource(R.drawable.admin_badge);
					badge.setVisibility(View.VISIBLE);
				} else {
					if (UserInfoCacheMap.get("acc_type-".concat(_data.get((int)_position).get("uid").toString())).toString().equals("moderator")) {
						badge.setImageResource(R.drawable.moderator_badge);
						badge.setVisibility(View.VISIBLE);
					} else {
						if (UserInfoCacheMap.get("acc_type-".concat(_data.get((int)_position).get("uid").toString())).toString().equals("support")) {
							badge.setImageResource(R.drawable.support_badge);
							badge.setVisibility(View.VISIBLE);
						} else {
							if (UserInfoCacheMap.get("acc_type-".concat(_data.get((int)_position).get("uid").toString())).toString().equals("user")) {
								if (UserInfoCacheMap.get("verify-".concat(_data.get((int)_position).get("uid").toString())).toString().equals("true")) {
									badge.setVisibility(View.VISIBLE);
								} else {
									badge.setVisibility(View.GONE);
								}
							}
						}
					}
				}
				body.setVisibility(View.VISIBLE);
			} else {
				{
					ExecutorService mExecutorService = Executors.newSingleThreadExecutor();
					Handler mMainHandler = new Handler(Looper.getMainLooper());
					
					mExecutorService.execute(new Runnable() {
						@Override
						public void run() {
							DatabaseReference getReference = FirebaseDatabase.getInstance().getReference().child("skyline/users").child(_data.get((int)_position).get("uid").toString());
							getReference.addListenerForSingleValueEvent(new ValueEventListener() {
								@Override
								public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
									if(dataSnapshot.exists()) {
										UserInfoCacheMap.put("uid-".concat(_data.get((int)_position).get("uid").toString()), _data.get((int)_position).get("uid").toString());
										UserInfoCacheMap.put("banned-".concat(_data.get((int)_position).get("uid").toString()), dataSnapshot.child("banned").getValue(String.class));
										UserInfoCacheMap.put("nickname-".concat(_data.get((int)_position).get("uid").toString()), dataSnapshot.child("nickname").getValue(String.class));
										UserInfoCacheMap.put("username-".concat(_data.get((int)_position).get("uid").toString()), dataSnapshot.child("username").getValue(String.class));
										UserInfoCacheMap.put("status-".concat(_data.get((int)_position).get("uid").toString()), dataSnapshot.child("status").getValue(String.class));
										UserInfoCacheMap.put("avatar-".concat(_data.get((int)_position).get("uid").toString()), dataSnapshot.child("avatar").getValue(String.class));
										UserInfoCacheMap.put("gender-".concat(_data.get((int)_position).get("uid").toString()), dataSnapshot.child("gender").getValue(String.class));
										UserInfoCacheMap.put("verify-".concat(_data.get((int)_position).get("uid").toString()), dataSnapshot.child("verify").getValue(String.class));
										UserInfoCacheMap.put("acc_type-".concat(_data.get((int)_position).get("uid").toString()), dataSnapshot.child("account_type").getValue(String.class));
										
										mMainHandler.post(new Runnable() {
											@Override
											public void run() {
												if (UserInfoCacheMap.get("banned-".concat(_data.get((int)_position).get("uid").toString())).toString().equals("true")) {
													profileAvatar.setImageResource(R.drawable.banned_avatar);
												} else {
													if (UserInfoCacheMap.get("avatar-".concat(_data.get((int)_position).get("uid").toString())).toString().equals("null")) {
														profileAvatar.setImageResource(R.drawable.avatar);
													} else {
														Glide.with(getApplicationContext()).load(Uri.parse(UserInfoCacheMap.get("avatar-".concat(_data.get((int)_position).get("uid").toString())).toString())).into(profileAvatar);
													}
												}
												if (UserInfoCacheMap.get("status-".concat(_data.get((int)_position).get("uid").toString())).toString().equals("online")) {
													userStatusCircleBG.setVisibility(View.VISIBLE);
												} else {
													userStatusCircleBG.setVisibility(View.GONE);
												}
												if (UserInfoCacheMap.get("nickname-".concat(_data.get((int)_position).get("uid").toString())).toString().equals("null")) {
													username.setText("@" + UserInfoCacheMap.get("username-".concat(_data.get((int)_position).get("uid").toString())).toString());
												} else {
													username.setText(UserInfoCacheMap.get("nickname-".concat(_data.get((int)_position).get("uid").toString())).toString());
												}
												name.setText("@" + UserInfoCacheMap.get("username-".concat(_data.get((int)_position).get("uid").toString())).toString());
												if (UserInfoCacheMap.get("gender-".concat(_data.get((int)_position).get("uid").toString())).toString().equals("hidden")) {
													genderBadge.setVisibility(View.GONE);
												} else {
													if (UserInfoCacheMap.get("gender-".concat(_data.get((int)_position).get("uid").toString())).toString().equals("male")) {
														genderBadge.setImageResource(R.drawable.male_badge);
														genderBadge.setVisibility(View.VISIBLE);
													} else {
														if (UserInfoCacheMap.get("gender-".concat(_data.get((int)_position).get("uid").toString())).toString().equals("female")) {
															genderBadge.setImageResource(R.drawable.female_badge);
															genderBadge.setVisibility(View.VISIBLE);
														}
													}
												}
												if (UserInfoCacheMap.get("acc_type-".concat(_data.get((int)_position).get("uid").toString())).toString().equals("admin")) {
													badge.setImageResource(R.drawable.admin_badge);
													badge.setVisibility(View.VISIBLE);
												} else {
													if (UserInfoCacheMap.get("acc_type-".concat(_data.get((int)_position).get("uid").toString())).toString().equals("moderator")) {
														badge.setImageResource(R.drawable.moderator_badge);
														badge.setVisibility(View.VISIBLE);
													} else {
														if (UserInfoCacheMap.get("acc_type-".concat(_data.get((int)_position).get("uid").toString())).toString().equals("support")) {
															badge.setImageResource(R.drawable.support_badge);
															badge.setVisibility(View.VISIBLE);
														} else {
															if (UserInfoCacheMap.get("acc_type-".concat(_data.get((int)_position).get("uid").toString())).toString().equals("user")) {
																if (UserInfoCacheMap.get("verify-".concat(_data.get((int)_position).get("uid").toString())).toString().equals("true")) {
																	badge.setVisibility(View.VISIBLE);
																} else {
																	badge.setVisibility(View.GONE);
																}
															}
														}
													}
												}
												body.setVisibility(View.VISIBLE);
											}
										});
									} else {
										
									}
								}
								@Override
								public void onCancelled(@NonNull DatabaseError databaseError) {
									
								}
							});
						}
					});
				}
			}
			body.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View _view) {
					intent.setClass(getApplicationContext(), ProfileActivity.class);
					intent.putExtra("uid", _data.get((int)_position).get("uid").toString());
					startActivity(intent);
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
	
	public class Following_layout_listAdapter extends RecyclerView.Adapter<Following_layout_listAdapter.ViewHolder> {
		
		ArrayList<HashMap<String, Object>> _data;
		
		public Following_layout_listAdapter(ArrayList<HashMap<String, Object>> _arr) {
			_data = _arr;
		}
		
		@Override
		public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
			LayoutInflater _inflater = getLayoutInflater();
			View _v = _inflater.inflate(R.layout.user_followers_list, null);
			RecyclerView.LayoutParams _lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
			_v.setLayoutParams(_lp);
			return new ViewHolder(_v);
		}
		
		@Override
		public void onBindViewHolder(ViewHolder _holder, final int _position) {
			View _view = _holder.itemView;
			
			final LinearLayout body = _view.findViewById(R.id.body);
			final RelativeLayout profileCardRelative = _view.findViewById(R.id.profileCardRelative);
			final LinearLayout lin = _view.findViewById(R.id.lin);
			final androidx.cardview.widget.CardView profileCard = _view.findViewById(R.id.profileCard);
			final LinearLayout ProfileRelativeUp = _view.findViewById(R.id.ProfileRelativeUp);
			final ImageView profileAvatar = _view.findViewById(R.id.profileAvatar);
			final LinearLayout userStatusCircleBG = _view.findViewById(R.id.userStatusCircleBG);
			final LinearLayout userStatusCircleIN = _view.findViewById(R.id.userStatusCircleIN);
			final LinearLayout usr = _view.findViewById(R.id.usr);
			final TextView name = _view.findViewById(R.id.name);
			final TextView username = _view.findViewById(R.id.username);
			final ImageView genderBadge = _view.findViewById(R.id.genderBadge);
			final ImageView badge = _view.findViewById(R.id.badge);
			
			RecyclerView.LayoutParams _lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
			_view.setLayoutParams(_lp);
			_viewGraphics(body, 0xFFFFFFFF, 0xFFEEEEEE, 0, 0, Color.TRANSPARENT);
			profileCard.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b) { this.setCornerRadius(a); this.setColor(b); return this; } }.getIns((int)300, Color.TRANSPARENT));
			userStatusCircleBG.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b) { this.setCornerRadius(a); this.setColor(b); return this; } }.getIns((int)300, 0xFFFFFFFF));
			userStatusCircleIN.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b) { this.setCornerRadius(a); this.setColor(b); return this; } }.getIns((int)300, 0xFF2196F3));
			if (UserInfoCacheMap.containsKey("uid-".concat(_data.get((int)_position).get("uid").toString()))) {
				if (UserInfoCacheMap.get("banned-".concat(_data.get((int)_position).get("uid").toString())).toString().equals("true")) {
					profileAvatar.setImageResource(R.drawable.banned_avatar);
				} else {
					if (UserInfoCacheMap.get("avatar-".concat(_data.get((int)_position).get("uid").toString())).toString().equals("null")) {
						profileAvatar.setImageResource(R.drawable.avatar);
					} else {
						Glide.with(getApplicationContext()).load(Uri.parse(UserInfoCacheMap.get("avatar-".concat(_data.get((int)_position).get("uid").toString())).toString())).into(profileAvatar);
					}
				}
				if (UserInfoCacheMap.get("status-".concat(_data.get((int)_position).get("uid").toString())).toString().equals("online")) {
					userStatusCircleBG.setVisibility(View.VISIBLE);
				} else {
					userStatusCircleBG.setVisibility(View.GONE);
				}
				if (UserInfoCacheMap.get("nickname-".concat(_data.get((int)_position).get("uid").toString())).toString().equals("null")) {
					username.setText("@" + UserInfoCacheMap.get("username-".concat(_data.get((int)_position).get("uid").toString())).toString());
				} else {
					username.setText(UserInfoCacheMap.get("nickname-".concat(_data.get((int)_position).get("uid").toString())).toString());
				}
				name.setText("@" + UserInfoCacheMap.get("username-".concat(_data.get((int)_position).get("uid").toString())).toString());
				if (UserInfoCacheMap.get("gender-".concat(_data.get((int)_position).get("uid").toString())).toString().equals("hidden")) {
					genderBadge.setVisibility(View.GONE);
				} else {
					if (UserInfoCacheMap.get("gender-".concat(_data.get((int)_position).get("uid").toString())).toString().equals("male")) {
						genderBadge.setImageResource(R.drawable.male_badge);
						genderBadge.setVisibility(View.VISIBLE);
					} else {
						if (UserInfoCacheMap.get("gender-".concat(_data.get((int)_position).get("uid").toString())).toString().equals("female")) {
							genderBadge.setImageResource(R.drawable.female_badge);
							genderBadge.setVisibility(View.VISIBLE);
						}
					}
				}
				if (UserInfoCacheMap.get("acc_type-".concat(_data.get((int)_position).get("uid").toString())).toString().equals("admin")) {
					badge.setImageResource(R.drawable.admin_badge);
					badge.setVisibility(View.VISIBLE);
				} else {
					if (UserInfoCacheMap.get("acc_type-".concat(_data.get((int)_position).get("uid").toString())).toString().equals("moderator")) {
						badge.setImageResource(R.drawable.moderator_badge);
						badge.setVisibility(View.VISIBLE);
					} else {
						if (UserInfoCacheMap.get("acc_type-".concat(_data.get((int)_position).get("uid").toString())).toString().equals("support")) {
							badge.setImageResource(R.drawable.support_badge);
							badge.setVisibility(View.VISIBLE);
						} else {
							if (UserInfoCacheMap.get("acc_type-".concat(_data.get((int)_position).get("uid").toString())).toString().equals("user")) {
								if (UserInfoCacheMap.get("verify-".concat(_data.get((int)_position).get("uid").toString())).toString().equals("true")) {
									badge.setVisibility(View.VISIBLE);
								} else {
									badge.setVisibility(View.GONE);
								}
							}
						}
					}
				}
				body.setVisibility(View.VISIBLE);
			} else {
				{
					ExecutorService mExecutorService = Executors.newSingleThreadExecutor();
					Handler mMainHandler = new Handler(Looper.getMainLooper());
					
					mExecutorService.execute(new Runnable() {
						@Override
						public void run() {
							DatabaseReference getReference = FirebaseDatabase.getInstance().getReference().child("skyline/users").child(_data.get((int)_position).get("uid").toString());
							getReference.addListenerForSingleValueEvent(new ValueEventListener() {
								@Override
								public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
									if(dataSnapshot.exists()) {
										UserInfoCacheMap.put("uid-".concat(_data.get((int)_position).get("uid").toString()), _data.get((int)_position).get("uid").toString());
										UserInfoCacheMap.put("banned-".concat(_data.get((int)_position).get("uid").toString()), dataSnapshot.child("banned").getValue(String.class));
										UserInfoCacheMap.put("nickname-".concat(_data.get((int)_position).get("uid").toString()), dataSnapshot.child("nickname").getValue(String.class));
										UserInfoCacheMap.put("username-".concat(_data.get((int)_position).get("uid").toString()), dataSnapshot.child("username").getValue(String.class));
										UserInfoCacheMap.put("status-".concat(_data.get((int)_position).get("uid").toString()), dataSnapshot.child("status").getValue(String.class));
										UserInfoCacheMap.put("avatar-".concat(_data.get((int)_position).get("uid").toString()), dataSnapshot.child("avatar").getValue(String.class));
										UserInfoCacheMap.put("gender-".concat(_data.get((int)_position).get("uid").toString()), dataSnapshot.child("gender").getValue(String.class));
										UserInfoCacheMap.put("verify-".concat(_data.get((int)_position).get("uid").toString()), dataSnapshot.child("verify").getValue(String.class));
										UserInfoCacheMap.put("acc_type-".concat(_data.get((int)_position).get("uid").toString()), dataSnapshot.child("account_type").getValue(String.class));
										
										mMainHandler.post(new Runnable() {
											@Override
											public void run() {
												if (UserInfoCacheMap.get("banned-".concat(_data.get((int)_position).get("uid").toString())).toString().equals("true")) {
													profileAvatar.setImageResource(R.drawable.banned_avatar);
												} else {
													if (UserInfoCacheMap.get("avatar-".concat(_data.get((int)_position).get("uid").toString())).toString().equals("null")) {
														profileAvatar.setImageResource(R.drawable.avatar);
													} else {
														Glide.with(getApplicationContext()).load(Uri.parse(UserInfoCacheMap.get("avatar-".concat(_data.get((int)_position).get("uid").toString())).toString())).into(profileAvatar);
													}
												}
												if (UserInfoCacheMap.get("status-".concat(_data.get((int)_position).get("uid").toString())).toString().equals("online")) {
													userStatusCircleBG.setVisibility(View.VISIBLE);
												} else {
													userStatusCircleBG.setVisibility(View.GONE);
												}
												if (UserInfoCacheMap.get("nickname-".concat(_data.get((int)_position).get("uid").toString())).toString().equals("null")) {
													username.setText("@" + UserInfoCacheMap.get("username-".concat(_data.get((int)_position).get("uid").toString())).toString());
												} else {
													username.setText(UserInfoCacheMap.get("nickname-".concat(_data.get((int)_position).get("uid").toString())).toString());
												}
												name.setText("@" + UserInfoCacheMap.get("username-".concat(_data.get((int)_position).get("uid").toString())).toString());
												if (UserInfoCacheMap.get("gender-".concat(_data.get((int)_position).get("uid").toString())).toString().equals("hidden")) {
													genderBadge.setVisibility(View.GONE);
												} else {
													if (UserInfoCacheMap.get("gender-".concat(_data.get((int)_position).get("uid").toString())).toString().equals("male")) {
														genderBadge.setImageResource(R.drawable.male_badge);
														genderBadge.setVisibility(View.VISIBLE);
													} else {
														if (UserInfoCacheMap.get("gender-".concat(_data.get((int)_position).get("uid").toString())).toString().equals("female")) {
															genderBadge.setImageResource(R.drawable.female_badge);
															genderBadge.setVisibility(View.VISIBLE);
														}
													}
												}
												if (UserInfoCacheMap.get("acc_type-".concat(_data.get((int)_position).get("uid").toString())).toString().equals("admin")) {
													badge.setImageResource(R.drawable.admin_badge);
													badge.setVisibility(View.VISIBLE);
												} else {
													if (UserInfoCacheMap.get("acc_type-".concat(_data.get((int)_position).get("uid").toString())).toString().equals("moderator")) {
														badge.setImageResource(R.drawable.moderator_badge);
														badge.setVisibility(View.VISIBLE);
													} else {
														if (UserInfoCacheMap.get("acc_type-".concat(_data.get((int)_position).get("uid").toString())).toString().equals("support")) {
															badge.setImageResource(R.drawable.support_badge);
															badge.setVisibility(View.VISIBLE);
														} else {
															if (UserInfoCacheMap.get("acc_type-".concat(_data.get((int)_position).get("uid").toString())).toString().equals("user")) {
																if (UserInfoCacheMap.get("verify-".concat(_data.get((int)_position).get("uid").toString())).toString().equals("true")) {
																	badge.setVisibility(View.VISIBLE);
																} else {
																	badge.setVisibility(View.GONE);
																}
															}
														}
													}
												}
												body.setVisibility(View.VISIBLE);
											}
										});
									} else {
										
									}
								}
								@Override
								public void onCancelled(@NonNull DatabaseError databaseError) {
									
								}
							});
						}
					});
				}
			}
			body.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View _view) {
					intent.setClass(getApplicationContext(), ProfileActivity.class);
					intent.putExtra("uid", _data.get((int)_position).get("uid").toString());
					startActivity(intent);
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
}