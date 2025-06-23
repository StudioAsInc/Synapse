package com.synapse.social.studioasinc;

import android.animation.*;
import android.app.*;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.*;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.*;
import android.graphics.*;
import android.graphics.Typeface;
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
import android.widget.ProgressBar;
import android.widget.TextView;
import androidmads.library.qrgenearator.*;
import androidx.annotation.*;
import androidx.appcompat.app.AppCompatActivity;
import androidx.asynclayoutinflater.*;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.interpolator.*;
import androidx.recyclerview.widget.*;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.Adapter;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;
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
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener;
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
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.*;
import kr.co.prnd.readmore.*;
import me.dm7.barcodescanner.core.*;
import org.jetbrains.kotlin.*;
import org.json.*;
import androidx.core.widget.NestedScrollView;
import com.google.firebase.database.Query;
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
import eightbitlab.com.blurview.BlurView;
import eightbitlab.com.blurview.RenderScriptBlur;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class MessagesActivity extends AppCompatActivity {
	
	private Timer _timer = new Timer();
	private FirebaseDatabase _firebase = FirebaseDatabase.getInstance();
	
	private FloatingActionButton _fab;
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
	private LinearLayout bottomSpc;
	private LinearLayout bottomBar;
	private SwipeRefreshLayout swipeLayout;
	private LinearLayout noInternetBody;
	private LinearLayout loadingBody;
	private LinearLayout swipeBody;
	private LinearLayout InboxChatMessagesLayout;
	private LinearLayout InboxMessagesRequestsLayout;
	private LinearLayout InboxMessagesGroupsLayout;
	private RecyclerView InboxRecyclerView;
	private TextView noInbox;
	private RecyclerView InboxMessagesRequestsList;
	private TextView InboxMessagesRequestsNoRequests;
	private RecyclerView groups_name_list;
	private ImageView noInternetBodyIc;
	private TextView noInternetBodyTitle;
	private TextView noInternetBodySubtitle;
	private TextView noInternetBodyRetry;
	private ProgressBar loading_bar;
	private LinearLayout bottom_home;
	private LinearLayout bottom_search;
	private LinearLayout bottom_videos;
	private LinearLayout bottom_chats;
	private LinearLayout bottom_profile;
	private ImageView bottom_home_ic;
	private ImageView bottom_search_ic;
	private ImageView bottom_videos_ic;
	private ImageView bottom_chats_ic;
	private ImageView bottom_profile_ic;
	
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
	private SharedPreferences sp;
	private DatabaseReference groups = _firebase.getReference("synapse/groups");
	private ChildEventListener _groups_child_listener;
	private SharedPreferences cache;
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
		_fab = findViewById(R.id._fab);
		body = findViewById(R.id.body);
		MessagesPageTabLayout = findViewById(R.id.MessagesPageTabLayout);
		middleLayout = findViewById(R.id.middleLayout);
		bottomSpc = findViewById(R.id.bottomSpc);
		bottomBar = findViewById(R.id.bottomBar);
		swipeLayout = findViewById(R.id.swipeLayout);
		noInternetBody = findViewById(R.id.noInternetBody);
		loadingBody = findViewById(R.id.loadingBody);
		swipeBody = findViewById(R.id.swipeBody);
		InboxChatMessagesLayout = findViewById(R.id.InboxChatMessagesLayout);
		InboxMessagesRequestsLayout = findViewById(R.id.InboxMessagesRequestsLayout);
		InboxMessagesGroupsLayout = findViewById(R.id.InboxMessagesGroupsLayout);
		InboxRecyclerView = findViewById(R.id.InboxRecyclerView);
		noInbox = findViewById(R.id.noInbox);
		InboxMessagesRequestsList = findViewById(R.id.InboxMessagesRequestsList);
		InboxMessagesRequestsNoRequests = findViewById(R.id.InboxMessagesRequestsNoRequests);
		groups_name_list = findViewById(R.id.groups_name_list);
		noInternetBodyIc = findViewById(R.id.noInternetBodyIc);
		noInternetBodyTitle = findViewById(R.id.noInternetBodyTitle);
		noInternetBodySubtitle = findViewById(R.id.noInternetBodySubtitle);
		noInternetBodyRetry = findViewById(R.id.noInternetBodyRetry);
		loading_bar = findViewById(R.id.loading_bar);
		bottom_home = findViewById(R.id.bottom_home);
		bottom_search = findViewById(R.id.bottom_search);
		bottom_videos = findViewById(R.id.bottom_videos);
		bottom_chats = findViewById(R.id.bottom_chats);
		bottom_profile = findViewById(R.id.bottom_profile);
		bottom_home_ic = findViewById(R.id.bottom_home_ic);
		bottom_search_ic = findViewById(R.id.bottom_search_ic);
		bottom_videos_ic = findViewById(R.id.bottom_videos_ic);
		bottom_chats_ic = findViewById(R.id.bottom_chats_ic);
		bottom_profile_ic = findViewById(R.id.bottom_profile_ic);
		auth = FirebaseAuth.getInstance();
		req = new RequestNetwork(this);
		dialog2 = new AlertDialog.Builder(this);
		dialog = new AlertDialog.Builder(this);
		sp = getSharedPreferences("sp", Activity.MODE_PRIVATE);
		cache = getSharedPreferences("cache", Activity.MODE_PRIVATE);
		
		MessagesPageTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
			@Override
			public void onTabSelected(TabLayout.Tab tab) {
				final int _position = tab.getPosition();
				if (_position == 0) {
					InboxChatMessagesLayout.setVisibility(View.VISIBLE);
					InboxMessagesRequestsLayout.setVisibility(View.GONE);
					_fab.setVisibility(View.VISIBLE);
					InboxMessagesGroupsLayout.setVisibility(View.GONE);
					fabMode = "chat";
				}
				if (_position == 1) {
					InboxChatMessagesLayout.setVisibility(View.GONE);
					InboxMessagesRequestsLayout.setVisibility(View.VISIBLE);
					_fab.setVisibility(View.GONE);
					InboxMessagesGroupsLayout.setVisibility(View.GONE);
					fabMode = "request";
				}
				if (_position == 2) {
					_fab.setVisibility(View.VISIBLE);
					InboxChatMessagesLayout.setVisibility(View.GONE);
					InboxMessagesRequestsLayout.setVisibility(View.GONE);
					InboxMessagesGroupsLayout.setVisibility(View.VISIBLE);
					fabMode = "groups";
				}
			}
			
			@Override
			public void onTabUnselected(TabLayout.Tab tab) {
				final int _position = tab.getPosition();
				
			}
			
			@Override
			public void onTabReselected(TabLayout.Tab tab) {
				final int _position = tab.getPosition();
				
			}
		});
		
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
		
		bottom_home.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				intent.setClass(getApplicationContext(), HomeActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
				startActivity(intent);
				finish();
			}
		});
		
		bottom_search.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				intent.setClass(getApplicationContext(), SearchActivity.class);
				startActivity(intent);
			}
		});
		
		bottom_videos.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				intent.setClass(getApplicationContext(), LineVideoPlayerActivity.class);
				startActivity(intent);
			}
		});
		
		bottom_profile.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				intent.setClass(getApplicationContext(), ProfileActivity.class);
				intent.putExtra("uid", FirebaseAuth.getInstance().getCurrentUser().getUid());
				startActivity(intent);
			}
		});
		
		_fab.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				if (fabMode.equals("groups")) {
					intent.setClass(getApplicationContext(), CreateGroupActivity.class);
					startActivity(intent);
				} else {
					if (fabMode.equals("request")) {
						/*
 
*/
					} else {
						if (fabMode.equals("chat")) {
							intent.setClass(getApplicationContext(), SearchActivity.class);
							startActivity(intent);
							finish();
						} else {
							
						}
					}
				}
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
				// --- START of OnReq Response CODE ---
				
				// This code runs when the internet check is successful (you are online).
				
				// Since we have an internet connection, we must ensure the main
				// content area is visible and any error or loading screens are hidden.
				swipeLayout.setVisibility(View.VISIBLE);
				noInternetBody.setVisibility(View.GONE);
				loadingBody.setVisibility(View.GONE);
				
				// Stop the swipe-to-refresh spinner. The _getInboxReference block will
				// handle updating the list with fresh data, but this confirms connectivity.
				swipeLayout.setRefreshing(false);
				
				// --- END of OnReq Response CODE ---
				/*
swipeLayout.setVisibility(View.VISIBLE);
noInternetBody.setVisibility(View.GONE);
loadingBody.setVisibility(View.GONE);
*/
			}
			
			@Override
			public void onErrorResponse(String _param1, String _param2) {
				final String _tag = _param1;
				final String _message = _param2;
				// --- START of OnReq ErrorResponse CODE ---
				
				// This code runs when the internet check fails (e.g., you are offline).
				
				// First, stop any loading indicators that might be running.
				// If the swipe-to-refresh spinner was active, this will hide it.
				swipeLayout.setRefreshing(false);
				
				// Now, we decide whether to show the "No Internet" screen.
				// We only show it if the user has NO cached data to look at.
				if (ChatInboxList.isEmpty()) {
						// The ChatInboxList is empty, which means no cache was loaded in onCreate.
						// Since there is also no internet, we must show the error screen.
						swipeLayout.setVisibility(View.GONE);
						noInternetBody.setVisibility(View.VISIBLE);
						loadingBody.setVisibility(View.GONE);
				} else {
						// The user is offline, BUT we successfully loaded a cached list of chats in onCreate.
						// In this case, we do nothing. We let the user see their old messages.
						// The swipeLayout (containing the list) remains visible.
				}
				
				// --- END of OnReq ErrorResponse CODE ---
				/*
swipeLayout.setVisibility(View.GONE);
noInternetBody.setVisibility(View.VISIBLE);
loadingBody.setVisibility(View.GONE);
*/
			}
		};
		
		_groups_child_listener = new ChildEventListener() {
			@Override
			public void onChildAdded(DataSnapshot _param1, String _param2) {
				GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
				final String _childKey = _param1.getKey();
				final HashMap<String, Object> _childValue = _param1.getValue(_ind);
				groups.addListenerForSingleValueEvent(new ValueEventListener() {
					@Override
					public void onDataChange(DataSnapshot _dataSnapshot) {
						lm_group = new ArrayList<>();
						try {
							GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
							for (DataSnapshot _data : _dataSnapshot.getChildren()) {
								HashMap<String, Object> _map = _data.getValue(_ind);
								lm_group.add(_map);
							}
						} catch (Exception _e) {
							_e.printStackTrace();
						}
						groups_name_list.setAdapter(new Groups_name_listAdapter(lm_group));
						groups_name_list.setLayoutManager(new LinearLayoutManager(MessagesActivity.this));
						cache.edit().putString("group_cache", new Gson().toJson(lm_group)).commit();
					}
					@Override
					public void onCancelled(DatabaseError _databaseError) {
					}
				});
			}
			
			@Override
			public void onChildChanged(DataSnapshot _param1, String _param2) {
				GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
				final String _childKey = _param1.getKey();
				final HashMap<String, Object> _childValue = _param1.getValue(_ind);
				groups.addListenerForSingleValueEvent(new ValueEventListener() {
					@Override
					public void onDataChange(DataSnapshot _dataSnapshot) {
						lm_group = new ArrayList<>();
						try {
							GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
							for (DataSnapshot _data : _dataSnapshot.getChildren()) {
								HashMap<String, Object> _map = _data.getValue(_ind);
								lm_group.add(_map);
							}
						} catch (Exception _e) {
							_e.printStackTrace();
						}
						groups_name_list.setAdapter(new Groups_name_listAdapter(lm_group));
						groups_name_list.setLayoutManager(new LinearLayoutManager(MessagesActivity.this));
						cache.edit().putString("group_cache", new Gson().toJson(lm_group)).commit();
					}
					@Override
					public void onCancelled(DatabaseError _databaseError) {
					}
				});
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
		groups.addChildEventListener(_groups_child_listener);
		
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
		// Set status bar colors
		_stateColor(0xFFFFFFFF, 0xFFFFFFFF);
		
		// Set bottom navigation bar icon colors
		_ImageColor(bottom_home_ic, 0xFFBDBDBD);
		_ImageColor(bottom_search_ic, 0xFFBDBDBD);
		_ImageColor(bottom_videos_ic, 0xFFBDBDBD);
		_ImageColor(bottom_chats_ic, 0xFF000000); // Current screen is active
		_ImageColor(bottom_profile_ic, 0xFFBDBDBD);
		
		// Style the "Retry" button on the no-internet screen
		_viewGraphics(noInternetBodyRetry, getResources().getColor(R.color.colorPrimary), 0xFF9FA8DA, 100, 0, 0xFF1E88E5);
		
		// --- Setup for the Top Tab Layout ---
		MessagesPageTabLayout.addTab(MessagesPageTabLayout.newTab().setText("CHATS"));
		MessagesPageTabLayout.addTab(MessagesPageTabLayout.newTab().setText("CHANNELS"));
		MessagesPageTabLayout.addTab(MessagesPageTabLayout.newTab().setText("GROUPS"));
		
		// Style the tabs
		MessagesPageTabLayout.setTabTextColors(0xFF9E9E9E, getResources().getColor(R.color.colorPrimary));
		MessagesPageTabLayout.setTabRippleColor(new android.content.res.ColorStateList(new int[][]{new int[]{android.R.attr.state_pressed}}, 
		new int[] {0xFFEEEEEE}));
		MessagesPageTabLayout.setSelectedTabIndicatorColor(getResources().getColor(R.color.colorPrimary));
		MessagesPageTabLayout.setElevation((float)2);
		
		// Set the initial visibility of the different tab layouts
		InboxChatMessagesLayout.setVisibility(View.VISIBLE);
		InboxMessagesRequestsLayout.setVisibility(View.GONE);
		InboxMessagesGroupsLayout.setVisibility(View.GONE); // Assuming you hide this until the tab is clicked
		
		
		// --- START of Caching and RecyclerView Setup ---
		
		// First, initialize your main chat list.
		// Make sure ChatInboxList is declared as a variable at the top of your activity, like:
		// private ArrayList<HashMap<String, Object>> ChatInboxList = new ArrayList<>();
		ChatInboxList = new ArrayList<>();
		
		// ** NEW PART: Load Chat Inbox from Cache **
		// We use the key "chat_inbox_cache" to store the chat list in SharedPreferences.
		// This check sees if there is any saved data.
		if (!cache.getString("chat_inbox_cache", "").equals("")) {
				try {
						// Use Gson to convert the saved JSON string back into your ArrayList
						ChatInboxList = new Gson().fromJson(cache.getString("chat_inbox_cache", ""), new TypeToken<ArrayList<HashMap<String, Object>>>(){}.getType());
						// Sort the list immediately by date so it's in the correct order
						SketchwareUtil.sortListMap(ChatInboxList, "push_date", false, false);
				} catch (Exception e) {
						// If there's an error reading the cache (e.g., corrupted data), just start with an empty list
						SketchwareUtil.showMessage(getApplicationContext(), "Error reading chat cache");
						ChatInboxList = new ArrayList<>();
				}
		}
		
		// Set up the RecyclerView for CHATS with the cached data (or an empty list if no cache exists)
		InboxRecyclerView.setAdapter(new InboxRecyclerViewAdapter(ChatInboxList));
		InboxRecyclerView.setLayoutManager(new LinearLayoutManager(this));
		
		
		// Set up your "no internet" message text. This is good to do once on create.
		noInternetBodySubtitle.setText(getResources().getString(R.string.reasons_may_be).concat("\n\n".concat(getResources().getString(R.string.err_no_internet).concat("\n".concat(getResources().getString(R.string.err_app_maintenance).concat("\n".concat(getResources().getString(R.string.err_problem_on_our_side))))))));
		
		
		// This is your existing code for loading GROUPS from cache. It is correct and works well.
		if (!cache.getString("group_cache", "").equals("")) {
				lm_group = new Gson().fromJson(cache.getString("group_cache", ""), new TypeToken<ArrayList<HashMap<String, Object>>>(){}.getType());
				groups_name_list.setLayoutManager(new LinearLayoutManager(this));
				groups_name_list.setAdapter(new Groups_name_listAdapter(lm_group));
		} else {
				// If there's no group cache, you can leave this empty or initialize an empty adapter
				// This part is up to you, your original code was empty here.
		}
		
		
		// --- END of Caching and RecyclerView Setup ---
		
		
		// Finally, call _getReference() to check for internet and fetch fresh data from Firebase.
		// This will run after the cached data has already been displayed, making the app feel fast.
		_getReference();
		/*
_stateColor(0xFFFFFFFF, 0xFFFFFFFF);
_ImageColor(bottom_home_ic, 0xFFBDBDBD);
_ImageColor(bottom_search_ic, 0xFFBDBDBD);
_ImageColor(bottom_videos_ic, 0xFFBDBDBD);
_ImageColor(bottom_chats_ic, 0xFF000000);
_ImageColor(bottom_profile_ic, 0xFFBDBDBD);
_viewGraphics(noInternetBodyRetry, getResources().getColor(R.color.colorPrimary), 0xFF9FA8DA, 100, 0, 0xFF1E88E5);
MessagesPageTabLayout.addTab(MessagesPageTabLayout.newTab().setText("CHATS"));
MessagesPageTabLayout.addTab(MessagesPageTabLayout.newTab().setText("CHANNELS"));
MessagesPageTabLayout.addTab(MessagesPageTabLayout.newTab().setText("GROUPS"));
MessagesPageTabLayout.setTabTextColors(0xFF9E9E9E, getResources().getColor(R.color.colorPrimary));
MessagesPageTabLayout.setTabRippleColor(new android.content.res.ColorStateList(new int[][]{new int[]{android.R.attr.state_pressed}}, 

new int[] {0xFFEEEEEE}));
MessagesPageTabLayout.setSelectedTabIndicatorColor(getResources().getColor(R.color.colorPrimary));
MessagesPageTabLayout.setElevation((float)2);
InboxChatMessagesLayout.setVisibility(View.VISIBLE);
InboxMessagesRequestsLayout.setVisibility(View.GONE);
InboxRecyclerView.setAdapter(new InboxRecyclerViewAdapter(ChatInboxList));
InboxRecyclerView.setLayoutManager(new LinearLayoutManager(this));
noInternetBodySubtitle.setText(getResources().getString(R.string.reasons_may_be).concat("\n\n".concat(getResources().getString(R.string.err_no_internet).concat("\n".concat(getResources().getString(R.string.err_app_maintenance).concat("\n".concat(getResources().getString(R.string.err_problem_on_our_side))))))));
_getReference();
InboxRecyclerView.setAdapter(new InboxRecyclerViewAdapter(ChatInboxList));
if (!cache.getString("group_cache", "").equals("")) {
lm_group = new Gson().fromJson(cache.getString("group_cache", ""), new TypeToken<ArrayList<HashMap<String, Object>>>(){}.getType());
groups_name_list.setLayoutManager(new LinearLayoutManager(this));
groups_name_list.setAdapter(new Groups_name_listAdapter(lm_group));
} else {

}
*/
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
		// --- START of _getReference More Block CODE ---
		
		// This block is called from onCreate and onResume to start refreshing data.
		
		// First, we decide which loading indicator to show based on whether we have cached data.
		if (ChatInboxList.isEmpty()) {
				// If the chat list is empty, it means we have no cache.
				// For the best user experience, show a full-screen loading indicator.
				swipeLayout.setVisibility(View.GONE);
				noInternetBody.setVisibility(View.GONE);
				loadingBody.setVisibility(View.VISIBLE);
		} else {
				// If the chat list is NOT empty, it means we are already showing cached data.
				// We don't want a disruptive full-screen loader. Instead, we show the
				// small, subtle swipe-to-refresh spinner at the top to indicate a background update.
				swipeLayout.setRefreshing(true);
		}
		
		// Start the network request to check for an active internet connection.
		// This will trigger either onResponse (online) or onErrorResponse (offline).
		req.startRequestNetwork(RequestNetworkController.POST, "https://google.com", "google", _req_request_listener);
		
		// At the same time, start fetching the latest inbox data from Firebase.
		// This runs in parallel with the internet check.
		_getInboxReference();
		
		// Note: We no longer have swipeLayout.setRefreshing(false) here.
		// It is now correctly handled inside the network response events.
		
		// --- END of _getReference More Block CODE ---
		/*
swipeLayout.setVisibility(View.GONE);
noInternetBody.setVisibility(View.GONE);
loadingBody.setVisibility(View.VISIBLE);
req.startRequestNetwork(RequestNetworkController.POST, "https://google.com", "google", _req_request_listener);
_getInboxReference();
swipeLayout.setRefreshing(false);
*/
	}
	
	
	public void _getInboxReference() {
		// --- START of _getInboxReference More Block CODE ---
		
		// This block fetches the user's chat inbox from the Firebase Realtime Database.
		
		// Create a query to get all children under the current user's inbox path.
		Query getInboxRef = FirebaseDatabase.getInstance().getReference("skyline/inbox").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
		
		// Attach a listener that will trigger once with the initial data and again if the data changes.
		getInboxRef.addValueEventListener(new ValueEventListener() {
			    @Override
			    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
				        // This method is called when data is retrieved from Firebase.
				        
				        if (dataSnapshot.exists()) {
					            // The user has chats in their inbox on the server.
					            
					            // Make the RecyclerView visible and hide the "No inbox messages" text.
					            InboxRecyclerView.setVisibility(View.VISIBLE);
					            noInbox.setVisibility(View.GONE);
					            
					            // Clear the existing list to avoid duplicating items.
					            ChatInboxList.clear();
					            
					            // Loop through all the chat items returned from Firebase.
					            try {
						                GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
						                for (DataSnapshot _data : dataSnapshot.getChildren()) {
							                    HashMap<String, Object> _map = _data.getValue(_ind);
							                    ChatInboxList.add(_map);
							                }
						            } catch (Exception _e) {
						                _e.printStackTrace();
						            }
					            
					            // Sort the newly fetched list by date to show the most recent chats first.
					            SketchwareUtil.sortListMap(ChatInboxList, "push_date", false, false);
					            
					            // Notify the adapter that the data has changed, which will refresh the UI.
					            InboxRecyclerView.getAdapter().notifyDataSetChanged();
					            
					            // ** IMPORTANT CACHING STEP **
					            // Convert the fresh list into a JSON String using Gson.
					            String freshInboxJson = new Gson().toJson(ChatInboxList);
					            // Save this JSON string to SharedPreferences for offline use.
					            cache.edit().putString("chat_inbox_cache", freshInboxJson).commit();
					            
					        } else {
					            // The dataSnapshot does not exist, meaning the user's inbox is empty on the server.
					            
					            // Hide the RecyclerView and show the "No inbox messages" text.
					            InboxRecyclerView.setVisibility(View.GONE);
					            noInbox.setVisibility(View.VISIBLE);
					            
					            // ** IMPORTANT CACHE CLEARING STEP **
					            // Since the server confirms the inbox is empty, we must also clear our local cache
					            // to ensure the app state is consistent.
					            cache.edit().remove("chat_inbox_cache").commit();
					        }
				    }
			    
			    @Override
			    public void onCancelled(@NonNull DatabaseError databaseError) {
				        // This method is called if there is an error reading data from Firebase.
				        // You could show an error message here if needed.
				    }
		});
		
		// --- END of _getInboxReference More Block CODE ---
		/*
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
*/
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
					if (UserInfoCacheMap.get("banned-".concat(_data.get((int)_position).get("uid").toString())).toString().equals("true")) {
							profileCardImage.setImageResource(R.drawable.banned_avatar);
					} else {
							if (UserInfoCacheMap.get("avatar-".concat(_data.get((int)_position).get("uid").toString())).toString().equals("null")) {
							        //dp = UserInfoCacheMap.get("avatar-".concat(_data.get((int)_position).get("avatar").toString()));
									profileCardImage.setImageResource(R.drawable.avatar);
							} else {
									Glide.with(getApplicationContext()).load(Uri.parse(UserInfoCacheMap.get("avatar-".concat(_data.get((int)_position).get("uid").toString())).toString())).into(profileCardImage);
							}
					}
					if (UserInfoCacheMap.get("nickname-".concat(_data.get((int)_position).get("uid").toString())).toString().equals("null")) {
							username.setText("@" + UserInfoCacheMap.get("username-".concat(_data.get((int)_position).get("uid").toString())).toString());
						    //username = UserInfoCacheMap.get("nickname-".concat(_data.get((int)_position).get("uid").toString()));
					} else {
							username.setText(UserInfoCacheMap.get("nickname-".concat(_data.get((int)_position).get("uid").toString())).toString());
						    //username = UserInfoCacheMap.get("nickname-".concat(_data.get((int)_position).get("uid").toString()));
					}
					if (UserInfoCacheMap.get("status-".concat(_data.get((int)_position).get("uid").toString())).toString().equals("online")) {
							userStatusCircleBG.setVisibility(View.VISIBLE);
					} else {
							userStatusCircleBG.setVisibility(View.GONE);
					}
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
					if (UserInfoCacheMap.get("account_type-".concat(_data.get((int)_position).get("uid").toString())).toString().equals("admin")) {
							verifiedBadge.setImageResource(R.drawable.admin_badge);
							verifiedBadge.setVisibility(View.VISIBLE);
					} else {
							if (UserInfoCacheMap.get("account_type-".concat(_data.get((int)_position).get("uid").toString())).toString().equals("moderator")) {
									verifiedBadge.setImageResource(R.drawable.moderator_badge);
									verifiedBadge.setVisibility(View.VISIBLE);
							} else {
									if (UserInfoCacheMap.get("account_type-".concat(_data.get((int)_position).get("uid").toString())).toString().equals("support")) {
											verifiedBadge.setImageResource(R.drawable.support_badge);
											verifiedBadge.setVisibility(View.VISIBLE);
									} else {
											if (UserInfoCacheMap.get("account_premium-".concat(_data.get((int)_position).get("uid").toString())).toString().equals("true")) {
													verifiedBadge.setImageResource(R.drawable.premium_badge);
													verifiedBadge.setVisibility(View.VISIBLE);
											} else {
													if (UserInfoCacheMap.get("verify-".concat(_data.get((int)_position).get("uid").toString())).toString().equals("true")) {
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
			main.setOnLongClickListener(new View.OnLongClickListener() {
				@Override
				public boolean onLongClick(View _view) {
					try{
						bs = new com.google.android.material.bottomsheet.BottomSheetDialog(MessagesActivity.this);
						View bsV;
						bsV = getLayoutInflater().inflate(R.layout.inbox_bs,null );
						bs.setContentView(bsV);
						bs.getWindow().findViewById(R.id.design_bottom_sheet).setBackgroundResource(android.R.color.transparent);
						final TextView title_with_username = (TextView) bsV.findViewById(R.id.title_with_username);
						final TextView archive_btn = (TextView) bsV.findViewById(R.id.archive_btn);
						final TextView delete_btn = (TextView) bsV.findViewById(R.id.delete_btn);
						final LinearLayout bs_bar = (LinearLayout) bsV.findViewById(R.id.bs_bar);
						final LinearLayout group_btn = (LinearLayout) bsV.findViewById(R.id.group_btn);
						final LinearLayout read_btn = (LinearLayout) bsV.findViewById(R.id.read_btn);
						final LinearLayout block_btn = (LinearLayout) bsV.findViewById(R.id.read_btn);
						final LinearLayout mute_btn = (LinearLayout) bsV.findViewById(R.id.mute_btn);
						final ImageView user_dp = (ImageView) bsV.findViewById(R.id.user_dp);
						bs.setCancelable(true);
						bs.show();
						_ImgRound(user_dp, 300);
						delete_btn.setOnClickListener(new View.OnClickListener() {
							@Override
							public void onClick(View _view) {
								cd = new AlertDialog.Builder(MessagesActivity.this).create();
								LayoutInflater cdLI = getLayoutInflater();
								View cdCV = (View) cdLI.inflate(R.layout.delete_chat_c_dialog, null);
								cd.setView(cdCV);
								final LinearLayout delete_btn = (LinearLayout)
								cdCV.findViewById(R.id.delete_btn);
								final LinearLayout content = (LinearLayout)
								cdCV.findViewById(R.id.content);
								final LinearLayout blured = (LinearLayout)
								cdCV.findViewById(R.id.blured);
								final CheckBox checkbox1 = (CheckBox)
								cdCV.findViewById(R.id.checkbox1);
								final ImageView userpicture = (ImageView)
								cdCV.findViewById(R.id.userpicture);
								final TextView username = (TextView)
								cdCV.findViewById(R.id.username);
								final TextView subtitle = (TextView)
								cdCV.findViewById(R.id.subtitle);
								cd.setCancelable(true);
								cd.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
								cd.show();
								_ImgRound(userpicture, 300);
								_viewGraphics(delete_btn, getResources().getColor(R.color.colorPrimary), 0xFF388E3C, 300, 0, Color.TRANSPARENT);
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
											cd.dismiss();
										}
									}
								});
								bs.dismiss();
							}
						});
						/*
subtitle.setText("Permanently delete the chats with ".concat(username.concat("?")));
username.setText("Username");

*/
						archive_btn.setOnClickListener(new View.OnClickListener() {
							@Override
							public void onClick(View _view) {
								FirebaseDatabase.getInstance().getReference("skyline/inbox")
								    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
								    .child(_data.get((int)_position).get("uid").toString())
								    .removeValue();
								bs.dismiss();
							}
						});
						mute_btn.setOnClickListener(new View.OnClickListener() {
							@Override
							public void onClick(View _view) {
								
							}
						});
						read_btn.setOnClickListener(new View.OnClickListener() {
							@Override
							public void onClick(View _view) {
								
							}
						});
						_viewGraphics(bs_bar, 0xFF616161, 0xFF388E3C, 300, 0, Color.TRANSPARENT);
						_viewGraphics(delete_btn, getResources().getColor(R.color.colorPrimary), 0xFF388E3C, 300, 0, Color.TRANSPARENT);
					}catch(Exception e){
						dialog.setTitle("Delete");
						dialog.setMessage("Delete the entire conversation?");
						dialog.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface _dialog, int _which) {
								
							}
						});
						dialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface _dialog, int _which) {
								SketchwareUtil.showMessage(getApplicationContext(), "Conversation deleted.");
							}
						});
						dialog.create().show();
					}
					return true;
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
	
	public class Groups_name_listAdapter extends RecyclerView.Adapter<Groups_name_listAdapter.ViewHolder> {
		
		ArrayList<HashMap<String, Object>> _data;
		
		public Groups_name_listAdapter(ArrayList<HashMap<String, Object>> _arr) {
			_data = _arr;
		}
		
		@Override
		public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
			LayoutInflater _inflater = getLayoutInflater();
			View _v = _inflater.inflate(R.layout.group_cv, null);
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
			final ImageView profileCardImage = _view.findViewById(R.id.profileCardImage);
			final LinearLayout usr = _view.findViewById(R.id.usr);
			final LinearLayout btnss = _view.findViewById(R.id.btnss);
			final LinearLayout spc = _view.findViewById(R.id.spc);
			final TextView push = _view.findViewById(R.id.push);
			final TextView groupName = _view.findViewById(R.id.groupName);
			final ImageView genderBadge = _view.findViewById(R.id.genderBadge);
			final ImageView verifiedBadge = _view.findViewById(R.id.verifiedBadge);
			final TextView last_message = _view.findViewById(R.id.last_message);
			final ImageView message_state = _view.findViewById(R.id.message_state);
			final TextView unread_messages_count_badge = _view.findViewById(R.id.unread_messages_count_badge);
			
			groupName.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/product_b.ttf"), 0);
			groupName.setText(_data.get((int)_position).get("group_name").toString());
			if (_data.get((int)_position).containsKey("group_image")) {
				Glide.with(getApplicationContext()).load(Uri.parse(_data.get((int)_position).get("group_image").toString())).into(profileCardImage);
			} else {
				
			}
			if (_data.get((int)_position).containsKey("created_on")) {
				last_message.setText(_data.get((int)_position).get("created_on").toString());
			} else {
				
			}
			if (_data.get((int)_position).containsKey("group_image")) {
				Glide.with(getApplicationContext()).load(Uri.parse(_data.get((int)_position).get("group_image").toString())).into(profileCardImage);
				_ImgRound(profileCardImage, 360);
			} else {
				
			}
			body.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View _view) {
					if (_data.get((int)_position).containsKey("group_password")) {
						if (_data.get((int)_position).get("group_password").toString().equals("null")) {
							if (_data.get((int)_position).containsKey("group_key")) {
								intent.setClass(getApplicationContext(), GlobalGroupActivity.class);
								intent.putExtra("key", _data.get((int)_position).get("group_key").toString());
								intent.putExtra("uid", FirebaseAuth.getInstance().getCurrentUser().getUid());
								intent.putExtra("group_name", _data.get((int)_position).get("group_name").toString());
								startActivity(intent);
							} else {
								SketchwareUtil.showMessage(getApplicationContext(), "Something went wrong.");
							}
						} else {
							dialog2.setTitle("Enter group password!");
							dialog2.setMessage("This is a private group, enter passkey to join the group");
							final EditText edit1 = new EditText(MessagesActivity.this);
							
							dialog2.setView(edit1);
							edit1.setTextColor(Color.WHITE);
							dialog2.setPositiveButton("Enter", new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface _dialog, int _which) {
									if (_data.get((int)_position).get("group_password").toString().equals(edit1.getText().toString())) {
										intent.setClass(getApplicationContext(), GlobalGroupActivity.class);
										intent.putExtra("key", _data.get((int)_position).get("group_key").toString());
										intent.putExtra("group_name", _data.get((int)_position).get("group_name").toString());
										startActivity(intent);
									} else {
										SketchwareUtil.showMessage(getApplicationContext(), "Password is not correct.");
									}
								}
							});
							dialog2.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface _dialog, int _which) {
									
								}
							});
							dialog2.create().show();
						}
					} else {
						
					}
				}
			});
			profileCardImage.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b, int c, int d) { this.setCornerRadius(a); this.setStroke(b, c); this.setColor(d); return this; } }.getIns((int)360, (int)0, Color.TRANSPARENT, Color.TRANSPARENT));
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