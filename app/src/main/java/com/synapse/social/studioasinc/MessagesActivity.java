package com.synapse.social.studioasinc;

import android.animation.*;
import android.app.*;
import android.app.AlertDialog;
import android.content.*;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.*;
import android.graphics.*;
import android.graphics.Typeface;
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
import com.google.android.material.progressindicator.CircularProgressIndicator;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener;
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
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.*;
import org.json.*;
import androidx.core.widget.NestedScrollView;
import com.google.firebase.database.Query;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import android.os.Handler;
import android.os.Looper;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsControllerCompat;
import androidx.appcompat.app.AppCompatDelegate;

public class MessagesActivity extends AppCompatActivity {
	
	private Timer _timer = new Timer();
	private FirebaseDatabase _firebase = FirebaseDatabase.getInstance();
	
	private HashMap<String, Object> UserInfoCacheMap = new HashMap<>();
	private String fabMode = "";
	private String group_key = "";
	private HashMap<String, Object> map = new HashMap<>();
	private String username = "";
	private String dp = "";
	
	private ArrayList<HashMap<String, Object>> ChatInboxList = new ArrayList<>();
	private ArrayList<HashMap<String, Object>> lm_group = new ArrayList<>();
	
	private LinearLayout body;
	private TabLayout MessagesPageTabLayout;
	private LinearLayout middleLayout;
	private SwipeRefreshLayout swipeLayout;
	private LinearLayout noInternetBody;
	private LinearLayout loadingBody;
	private LinearLayout swipeBody;
	private LinearLayout InboxChatMessagesLayout;
	private RecyclerView InboxRecyclerView;
	private TextView noInbox;
	private ImageView noInternetBodyIc;
	private TextView noInternetBodyTitle;
	private TextView noInternetBodySubtitle;
	private TextView noInternetBodyRetry;
	private CircularProgressIndicator loading_bar;
	
	private Intent intent = new Intent();
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
	private DatabaseReference main = _firebase.getReference("skyline");
	private ChildEventListener _main_child_listener;
	private RequestNetwork req;
	private RequestNetwork.RequestListener _req_request_listener;
	private com.google.android.material.bottomsheet.BottomSheetDialog bs;
	private AlertDialog cd;
	private AlertDialog.Builder dialog2;
	private AlertDialog.Builder dialog;
	private TimerTask t;
	
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.messages);
		initialize(_savedInstanceState);
		FirebaseApp.initializeApp(this);
		initializeLogic();
	}
	
	private void initialize(Bundle _savedInstanceState) {
		body = findViewById(R.id.body);
		MessagesPageTabLayout = findViewById(R.id.MessagesPageTabLayout);
		middleLayout = findViewById(R.id.middleLayout);
		swipeLayout = findViewById(R.id.swipeLayout);
		noInternetBody = findViewById(R.id.noInternetBody);
		loadingBody = findViewById(R.id.loadingBody);
		swipeBody = findViewById(R.id.swipeBody);
		InboxChatMessagesLayout = findViewById(R.id.InboxChatMessagesLayout);
		InboxRecyclerView = findViewById(R.id.InboxRecyclerView);
		noInbox = findViewById(R.id.noInbox);
		noInternetBodyIc = findViewById(R.id.noInternetBodyIc);
		noInternetBodyTitle = findViewById(R.id.noInternetBodyTitle);
		noInternetBodySubtitle = findViewById(R.id.noInternetBodySubtitle);
		noInternetBodyRetry = findViewById(R.id.noInternetBodyRetry);
		loading_bar = findViewById(R.id.loading_bar);
		auth = FirebaseAuth.getInstance();
		req = new RequestNetwork(this);
		dialog2 = new AlertDialog.Builder(this);
		dialog = new AlertDialog.Builder(this);
		
		swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
			@Override
			public void onRefresh() {
				_getReference();
			}
		});
		
		noInternetBodyRetry.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				_getReference();
			}
		});
		
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
		
		_req_request_listener = new RequestNetwork.RequestListener() {
			@Override
			public void onResponse(String _param1, String _param2, HashMap<String, Object> _param3) {
				final String _tag = _param1;
				final String _response = _param2;
				final HashMap<String, Object> _responseHeaders = _param3;
				swipeLayout.setVisibility(View.VISIBLE);
				noInternetBody.setVisibility(View.GONE);
				loadingBody.setVisibility(View.GONE);
			}
			
			@Override
			public void onErrorResponse(String _param1, String _param2) {
				final String _tag = _param1;
				final String _message = _param2;
				/*
swipeLayout.setVisibility(View.GONE);
noInternetBody.setVisibility(View.VISIBLE);
loadingBody.setVisibility(View.GONE);
*/
			}
		};
		
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
		_viewGraphics(noInternetBodyRetry, getResources().getColor(R.color.colorPrimary), 0xFF9FA8DA, 100, 0, 0xFF1E88E5);
		_stateColor(0xFFFFFFFF, 0xFFFFFFFF);
		MessagesPageTabLayout.addTab(MessagesPageTabLayout.newTab().setText("CHATS"));
		MessagesPageTabLayout.addTab(MessagesPageTabLayout.newTab().setText("CHANNELS"));
		MessagesPageTabLayout.addTab(MessagesPageTabLayout.newTab().setText("GROUPS"));
		MessagesPageTabLayout.setTabTextColors(0xFF9E9E9E, getResources().getColor(R.color.colorPrimary));
		MessagesPageTabLayout.setTabRippleColor(new android.content.res.ColorStateList(new int[][]{new int[]{android.R.attr.state_pressed}}, 
		
		new int[] {0xFFEEEEEE}));
		MessagesPageTabLayout.setSelectedTabIndicatorColor(getResources().getColor(R.color.colorPrimary));
		MessagesPageTabLayout.setElevation((float)2);
		InboxChatMessagesLayout.setVisibility(View.VISIBLE);
		InboxRecyclerView.setAdapter(new InboxRecyclerViewAdapter(ChatInboxList));
		InboxRecyclerView.setLayoutManager(new LinearLayoutManager(this));
		noInternetBodySubtitle.setText(getResources().getString(R.string.reasons_may_be).concat("\n\n".concat(getResources().getString(R.string.err_no_internet).concat("\n".concat(getResources().getString(R.string.err_app_maintenance).concat("\n".concat(getResources().getString(R.string.err_problem_on_our_side))))))));
		_getReference();
		InboxRecyclerView.setAdapter(new InboxRecyclerViewAdapter(ChatInboxList));
	}
	
	
	@Override
	public void onBackPressed() {
		intent.setClass(getApplicationContext(), HomeActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
		startActivity(intent);
		finish();
	}
	
	@Override
	public void onResume() {
		super.onResume();
		_getReference();
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
	
	
	public void _getReference() {
		swipeLayout.setVisibility(View.GONE);
		noInternetBody.setVisibility(View.GONE);
		loadingBody.setVisibility(View.VISIBLE);
		req.startRequestNetwork(RequestNetworkController.POST, "https://google.com", "google", _req_request_listener);
		_getInboxReference();
		swipeLayout.setRefreshing(false);
	}
	
	
	public void _getInboxReference() {
		Query getInboxRef = FirebaseDatabase.getInstance().getReference("skyline/inbox").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
		getInboxRef.addValueEventListener(new ValueEventListener() {
			@Override
			public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
				if(dataSnapshot.exists()) {
					InboxRecyclerView.setVisibility(View.VISIBLE);
					noInbox.setVisibility(View.GONE);
					ChatInboxList.clear();
					try {
						GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
						for (DataSnapshot _data : dataSnapshot.getChildren()) {
							HashMap<String, Object> _map = _data.getValue(_ind);
							ChatInboxList.add(_map);
						}
					} catch (Exception _e) {
						_e.printStackTrace();
					}
					
					SketchwareUtil.sortListMap(ChatInboxList, "push_date", false, false);
					InboxRecyclerView.getAdapter().notifyDataSetChanged();
				} else {
					InboxRecyclerView.setVisibility(View.GONE);
					noInbox.setVisibility(View.VISIBLE);
				}
			}
			
			@Override
			public void onCancelled(@NonNull DatabaseError databaseError) {
				
			}
		});
	}
	
	
	public void _setTime(final double _currentTime, final TextView _txt) {
		Calendar c1 = Calendar.getInstance();
		Calendar c2 = Calendar.getInstance();
		double time_diff = c1.getTimeInMillis() - _currentTime;
		if (time_diff < 60000) {
			if ((time_diff / 1000) < 2) {
				_txt.setText("1" + " " + getResources().getString(R.string.seconds_ago));
			} else {
				_txt.setText(String.valueOf((long)(time_diff / 1000)).concat(" " + getResources().getString(R.string.seconds_ago)));
			}
		} else {
			if (time_diff < (60 * 60000)) {
				if ((time_diff / 60000) < 2) {
					_txt.setText("1" + " " + getResources().getString(R.string.minutes_ago));
				} else {
					_txt.setText(String.valueOf((long)(time_diff / 60000)).concat(" " + getResources().getString(R.string.minutes_ago)));
				}
			} else {
				if (time_diff < (24 * (60 * 60000))) {
					if ((time_diff / (60 * 60000)) < 2) {
						_txt.setText(String.valueOf((long)(time_diff / (60 * 60000))).concat(" " + getResources().getString(R.string.hours_ago)));
					} else {
						_txt.setText(String.valueOf((long)(time_diff / (60 * 60000))).concat(" " + getResources().getString(R.string.hours_ago)));
					}
				} else {
					if (time_diff < (7 * (24 * (60 * 60000)))) {
						if ((time_diff / (24 * (60 * 60000))) < 2) {
							_txt.setText(String.valueOf((long)(time_diff / (24 * (60 * 60000)))).concat(" " + getResources().getString(R.string.days_ago)));
						} else {
							_txt.setText(String.valueOf((long)(time_diff / (24 * (60 * 60000)))).concat(" " + getResources().getString(R.string.days_ago)));
						}
					} else {
						c2.setTimeInMillis((long)(_currentTime));
						_txt.setText(new SimpleDateFormat("dd-MM-yyyy").format(c2.getTime()));
					}
				}
			}
		}
	}
	
	
	public void _ImgRound(final ImageView _imageview, final double _value) {
		android.graphics.drawable.GradientDrawable gd = new android.graphics.drawable.GradientDrawable ();
		gd.setColor(android.R.color.transparent);
		gd.setCornerRadius((int)_value);
		_imageview.setClipToOutline(true);
		_imageview.setBackground(gd);
	}
	
	public class InboxRecyclerViewAdapter extends RecyclerView.Adapter<InboxRecyclerViewAdapter.ViewHolder> {
		
		ArrayList<HashMap<String, Object>> _data;
		
		public InboxRecyclerViewAdapter(ArrayList<HashMap<String, Object>> _arr) {
			_data = _arr;
		}
		
		@Override
		public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
			LayoutInflater _inflater = getLayoutInflater();
			View _v = _inflater.inflate(R.layout.inbox_msg_list_cv_synapse, null);
			RecyclerView.LayoutParams _lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
			_v.setLayoutParams(_lp);
			return new ViewHolder(_v);
		}
		
		@Override
		public void onBindViewHolder(ViewHolder _holder, final int _position) {
			View _view = _holder.itemView;
			
			final LinearLayout main = _view.findViewById(R.id.main);
			final LinearLayout body = _view.findViewById(R.id.body);
			final LinearLayout spcBottom = _view.findViewById(R.id.spcBottom);
			final RelativeLayout profileCardRelative = _view.findViewById(R.id.profileCardRelative);
			final LinearLayout lin = _view.findViewById(R.id.lin);
			final androidx.cardview.widget.CardView profileCard = _view.findViewById(R.id.profileCard);
			final LinearLayout ProfileRelativeUp = _view.findViewById(R.id.ProfileRelativeUp);
			final ImageView profileCardImage = _view.findViewById(R.id.profileCardImage);
			final LinearLayout userStatusCircleBG = _view.findViewById(R.id.userStatusCircleBG);
			final LinearLayout userStatusCircleIN = _view.findViewById(R.id.userStatusCircleIN);
			final LinearLayout usr = _view.findViewById(R.id.usr);
			final LinearLayout btnss = _view.findViewById(R.id.btnss);
			final LinearLayout spc = _view.findViewById(R.id.spc);
			final TextView push = _view.findViewById(R.id.push);
			final TextView username = _view.findViewById(R.id.username);
			final ImageView genderBadge = _view.findViewById(R.id.genderBadge);
			final ImageView verifiedBadge = _view.findViewById(R.id.verifiedBadge);
			final TextView last_message = _view.findViewById(R.id.last_message);
			final ImageView message_state = _view.findViewById(R.id.message_state);
			final TextView unread_messages_count_badge = _view.findViewById(R.id.unread_messages_count_badge);
			
			try{
				RecyclerView.LayoutParams _lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
				_view.setLayoutParams(_lp);
				_viewGraphics(main, 0xFFFFFFFF, 0xFFEEEEEE, 0, 0, Color.TRANSPARENT);
				userStatusCircleBG.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b) { this.setCornerRadius(a); this.setColor(b); return this; } }.getIns((int)300, 0xFFFFFFFF));
				userStatusCircleIN.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b) { this.setCornerRadius(a); this.setColor(b); return this; } }.getIns((int)300, 0xFF388E3C));
				unread_messages_count_badge.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b) { this.setCornerRadius(a); this.setColor(b); return this; } }.getIns((int)300, getResources().getColor(R.color.colorPrimary)));
				unread_messages_count_badge.setVisibility(View.GONE);
				profileCard.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b) { this.setCornerRadius(a); this.setColor(b); return this; } }.getIns((int)300, Color.TRANSPARENT));
				main.setVisibility(View.GONE);
				if (_data.get((int)_position).get("last_message_text").toString().equals("null")) {
					last_message.setText(getResources().getString(R.string.m_no_chats));
				} else {
					last_message.setText(_data.get((int)_position).get("last_message_text").toString());
				}
				if (_data.get((int)_position).get("last_message_uid").toString().equals(FirebaseAuth.getInstance().getCurrentUser().getUid())) {
					if (_data.get((int)_position).get("last_message_state").toString().equals("sended")) {
						message_state.setImageResource(R.drawable.icon_done_round);
					} else {
						message_state.setImageResource(R.drawable.icon_done_all_round);
					}
					last_message.setTextColor(0xFF616161);
					push.setTextColor(0xFF616161);
					last_message.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/appfont.ttf"), 0);
					push.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/appfont.ttf"), 0);
					message_state.setVisibility(View.VISIBLE);
					unread_messages_count_badge.setVisibility(View.GONE);
				} else {
					message_state.setVisibility(View.GONE);
					{
						ExecutorService mExecutorService = Executors.newSingleThreadExecutor();
						Handler mMainHandler = new Handler(Looper.getMainLooper());
						
						mExecutorService.execute(new Runnable() {
							@Override
							public void run() {
								Query getUnreadMessagesCount = FirebaseDatabase.getInstance().getReference("skyline/chats").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(_data.get((int)_position).get("uid").toString()).orderByChild("message_state").equalTo("sended");
								getUnreadMessagesCount.addValueEventListener(new ValueEventListener() {
									@Override
									public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
										mMainHandler.post(new Runnable() {
											@Override
											public void run() {
												long unReadMessageCount = dataSnapshot.getChildrenCount();
												if(dataSnapshot.exists()) {
													last_message.setTextColor(0xFF000000);
													push.setTextColor(0xFF000000);
													last_message.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/appfont.ttf"), 1);
													push.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/appfont.ttf"), 1);
													unread_messages_count_badge.setText(String.valueOf((long)(unReadMessageCount)));
													unread_messages_count_badge.setVisibility(View.VISIBLE);
												} else {
													last_message.setTextColor(0xFF616161);
													push.setTextColor(0xFF616161);
													last_message.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/appfont.ttf"), 0);
													push.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/appfont.ttf"), 0);
													unread_messages_count_badge.setVisibility(View.GONE);
												}
											}
										});
									}
									
									@Override
									public void onCancelled(@NonNull DatabaseError databaseError) {
										
									}
								});
							}
						});
					}
				}
				_setTime(Double.parseDouble(_data.get((int)_position).get("push_date").toString()), push);
				if (UserInfoCacheMap.containsKey("uid-".concat(_data.get((int)_position).get("uid").toString()))) {
					main.setVisibility(View.VISIBLE);
					
					// Get uid once to avoid repeated calls
					String uid = _data.get((int)_position).get("uid").toString();
					
					// Handle banned status with null check
					Object bannedObj = UserInfoCacheMap.get("banned-".concat(uid));
					if (bannedObj != null && bannedObj.toString().equals("true")) {
						profileCardImage.setImageResource(R.drawable.banned_avatar);
					} else {
						// Handle avatar with null check
						Object avatarObj = UserInfoCacheMap.get("avatar-".concat(uid));
						if (avatarObj == null || avatarObj.toString().equals("null")) {
							profileCardImage.setImageResource(R.drawable.avatar);
						} else {
							Glide.with(getApplicationContext())
							.load(Uri.parse(avatarObj.toString()))
							.into(profileCardImage);
						}
					}
					
					// Handle nickname with null check
					Object nicknameObj = UserInfoCacheMap.get("nickname-".concat(uid));
					if (nicknameObj == null || nicknameObj.toString().equals("null")) {
						Object usernameObj = UserInfoCacheMap.get("username-".concat(uid));
						username.setText("@" + (usernameObj != null ? usernameObj.toString() : "unknown"));
					} else {
						username.setText(nicknameObj.toString());
					}
					
					// Handle status with null check
					Object statusObj = UserInfoCacheMap.get("status-".concat(uid));
					userStatusCircleBG.setVisibility(statusObj != null && statusObj.toString().equals("online") 
					? View.VISIBLE : View.GONE);
					
					// Handle gender with null check
					Object genderObj = UserInfoCacheMap.get("gender-".concat(uid));
					if (genderObj == null || genderObj.toString().equals("hidden")) {
						genderBadge.setVisibility(View.GONE);
					} else {
						genderBadge.setVisibility(View.VISIBLE);
						String gender = genderObj.toString();
						if (gender.equals("male")) {
							genderBadge.setImageResource(R.drawable.male_badge);
						} else if (gender.equals("female")) {
							genderBadge.setImageResource(R.drawable.female_badge);
						}
					}
					
					// Handle account type and badges with null checks
					Object accountTypeObj = UserInfoCacheMap.get("account_type-".concat(uid));
					Object premiumObj = UserInfoCacheMap.get("account_premium-".concat(uid));
					Object verifyObj = UserInfoCacheMap.get("verify-".concat(uid));
					
					if (accountTypeObj != null) {
						String accountType = accountTypeObj.toString();
						if (accountType.equals("admin")) {
							verifiedBadge.setImageResource(R.drawable.admin_badge);
							verifiedBadge.setVisibility(View.VISIBLE);
						} else if (accountType.equals("moderator")) {
							verifiedBadge.setImageResource(R.drawable.moderator_badge);
							verifiedBadge.setVisibility(View.VISIBLE);
						} else if (accountType.equals("support")) {
							verifiedBadge.setImageResource(R.drawable.support_badge);
							verifiedBadge.setVisibility(View.VISIBLE);
						} else if (premiumObj != null && premiumObj.toString().equals("true")) {
							verifiedBadge.setImageResource(R.drawable.premium_badge);
							verifiedBadge.setVisibility(View.VISIBLE);
						} else if (verifyObj != null && verifyObj.toString().equals("true")) {
							verifiedBadge.setImageResource(R.drawable.verified_badge);
							verifiedBadge.setVisibility(View.VISIBLE);
						} else {
							verifiedBadge.setVisibility(View.GONE);
						}
					} else if (premiumObj != null && premiumObj.toString().equals("true")) {
						verifiedBadge.setImageResource(R.drawable.premium_badge);
						verifiedBadge.setVisibility(View.VISIBLE);
					} else if (verifyObj != null && verifyObj.toString().equals("true")) {
						verifiedBadge.setImageResource(R.drawable.verified_badge);
						verifiedBadge.setVisibility(View.VISIBLE);
					} else {
						verifiedBadge.setVisibility(View.GONE);
					}
				} else {
					{
						ExecutorService mExecutorService = Executors.newSingleThreadExecutor();
						Handler mMainHandler = new Handler(Looper.getMainLooper());
						
						mExecutorService.execute(new Runnable() {
							@Override
							public void run() {
								DatabaseReference getUserReference = FirebaseDatabase.getInstance().getReference("skyline/users").child(_data.get((int)_position).get("uid").toString());
								getUserReference.addListenerForSingleValueEvent(new ValueEventListener() {
									@Override
									public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
										mMainHandler.post(new Runnable() {
											@Override
											public void run() {
												if(dataSnapshot.exists()) {
													UserInfoCacheMap.put("uid-".concat(_data.get((int)_position).get("uid").toString()), _data.get((int)_position).get("uid").toString());
													UserInfoCacheMap.put("avatar-".concat(_data.get((int)_position).get("uid").toString()), dataSnapshot.child("avatar").getValue(String.class));
													UserInfoCacheMap.put("banned-".concat(_data.get((int)_position).get("uid").toString()), dataSnapshot.child("banned").getValue(String.class));
													UserInfoCacheMap.put("username-".concat(_data.get((int)_position).get("uid").toString()), dataSnapshot.child("username").getValue(String.class));
													UserInfoCacheMap.put("nickname-".concat(_data.get((int)_position).get("uid").toString()), dataSnapshot.child("nickname").getValue(String.class));
													UserInfoCacheMap.put("status-".concat(_data.get((int)_position).get("uid").toString()), dataSnapshot.child("status").getValue(String.class));
													UserInfoCacheMap.put("gender-".concat(_data.get((int)_position).get("uid").toString()), dataSnapshot.child("gender").getValue(String.class));
													UserInfoCacheMap.put("account_type-".concat(_data.get((int)_position).get("uid").toString()), dataSnapshot.child("account_type").getValue(String.class));
													UserInfoCacheMap.put("account_premium-".concat(_data.get((int)_position).get("uid").toString()), dataSnapshot.child("account_premium").getValue(String.class));
													UserInfoCacheMap.put("verify-".concat(_data.get((int)_position).get("uid").toString()), dataSnapshot.child("verify").getValue(String.class));
													main.setVisibility(View.VISIBLE);
													if (dataSnapshot.child("banned").getValue(String.class).equals("true")) {
														profileCardImage.setImageResource(R.drawable.banned_avatar);
													} else {
														if (dataSnapshot.child("avatar").getValue(String.class).equals("null")) {
															profileCardImage.setImageResource(R.drawable.avatar);
														} else {
															Glide.with(getApplicationContext()).load(Uri.parse(dataSnapshot.child("avatar").getValue(String.class))).into(profileCardImage);
														}
													}
													if (dataSnapshot.child("nickname").getValue(String.class).equals("null")) {
														username.setText("@" + dataSnapshot.child("username").getValue(String.class));
													} else {
														username.setText(dataSnapshot.child("nickname").getValue(String.class));
													}
													if (dataSnapshot.child("status").getValue(String.class).equals("online")) {
														userStatusCircleBG.setVisibility(View.VISIBLE);
													} else {
														userStatusCircleBG.setVisibility(View.GONE);
													}
													if (dataSnapshot.child("gender").getValue(String.class).equals("hidden")) {
														genderBadge.setVisibility(View.GONE);
													} else {
														if (dataSnapshot.child("gender").getValue(String.class).equals("male")) {
															genderBadge.setImageResource(R.drawable.male_badge);
															genderBadge.setVisibility(View.VISIBLE);
														} else {
															if (dataSnapshot.child("gender").getValue(String.class).equals("female")) {
																genderBadge.setImageResource(R.drawable.female_badge);
																genderBadge.setVisibility(View.VISIBLE);
															}
														}
													}
													if (dataSnapshot.child("account_type").getValue(String.class).equals("admin")) {
														verifiedBadge.setImageResource(R.drawable.admin_badge);
														verifiedBadge.setVisibility(View.VISIBLE);
													} else {
														if (dataSnapshot.child("account_type").getValue(String.class).equals("moderator")) {
															verifiedBadge.setImageResource(R.drawable.moderator_badge);
															verifiedBadge.setVisibility(View.VISIBLE);
														} else {
															if (dataSnapshot.child("account_type").getValue(String.class).equals("support")) {
																verifiedBadge.setImageResource(R.drawable.support_badge);
																verifiedBadge.setVisibility(View.VISIBLE);
															} else {
																if (dataSnapshot.child("account_premium").getValue(String.class).equals("true")) {
																	verifiedBadge.setImageResource(R.drawable.premium_badge);
																	verifiedBadge.setVisibility(View.VISIBLE);
																} else {
																	if (dataSnapshot.child("verify").getValue(String.class).equals("true")) {
																		verifiedBadge.setImageResource(R.drawable.verified_badge);
																		verifiedBadge.setVisibility(View.VISIBLE);
																	} else {
																		verifiedBadge.setVisibility(View.GONE);
																	}
																}
															}
														}
													}
												} else {
												}
											}
										});
									}
									
									@Override
									public void onCancelled(@NonNull DatabaseError databaseError) {
										
									}
								});
							}
						});
					}
					
				}
				main.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View _view) {
						intent.setClass(getApplicationContext(), ChatActivity.class);
						intent.putExtra("uid", _data.get((int)_position).get("uid").toString());
						startActivity(intent);
					}
				});
			}catch(Exception e){
				
			}
			/*
delete_btn.setOnClickListener(new View.OnClickListener() {
@Override
public void onClick(View _view) {
if (checkbox1.isChecked()) {
cd.dismiss();
FirebaseDatabase.getInstance().getReference("skyline/chats")
    .child(_data.get((int)_position).get("uid").toString()) // his UID
    .child(FirebaseAuth.getInstance().getCurrentUser().getUid()) // your UID
    .removeValue();
FirebaseDatabase.getInstance().getReference("skyline/inbox")
    .child(FirebaseAuth.getInstance().getCurrentUser().getUid()) // your UID
    .child(_data.get((int)_position).get("uid").toString()) // his UID
    .removeValue();
FirebaseDatabase.getInstance().getReference("skyline/inbox")
    .child(_data.get((int)_position).get("uid").toString()) // his UID
    .child(FirebaseAuth.getInstance().getCurrentUser().getUid()) // your UID
    .removeValue();
FirebaseDatabase.getInstance().getReference("skyline/chats")
    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
    .child(_data.get((int)_position).get("uid").toString())
    .removeValue();
} else {
FirebaseDatabase.getInstance().getReference("skyline/chats")
    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
    .child(_data.get((int)_position).get("uid").toString())
    .removeValue();
FirebaseDatabase.getInstance().getReference("skyline/inbox")
    .child(FirebaseAuth.getInstance().getCurrentUser().getUid()) // your UID
    .child(_data.get((int)_position).get("uid").toString()) // his UID
    .removeValue();
FirebaseDatabase.getInstance().getReference("skyline/inbox")
    .child(_data.get((int)_position).get("uid").toString()) // his UID
    .child(FirebaseAuth.getInstance().getCurrentUser().getUid()) // your UID
    .removeValue();
}
}
});
archive_btn.setOnClickListener(new View.OnClickListener() {
@Override
public void onClick(View _view) {
FirebaseDatabase.getInstance().getReference("skyline/inbox")
    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
    .child(_data.get((int)_position).get("uid").toString())
    .removeValue();
}
});
*/
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