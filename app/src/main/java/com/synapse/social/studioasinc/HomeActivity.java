package com.synapse.social.studioasinc;

import android.Manifest;
import android.animation.*;
import android.app.*;
import android.app.AlertDialog;
import android.content.*;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.*;
import android.graphics.*;
import android.graphics.Typeface;
import android.graphics.drawable.*;
import android.media.*;
import android.media.MediaPlayer;
import android.net.*;
import android.net.Uri;
import android.os.*;
import android.os.Bundle;
import android.os.Vibrator;
//import android.support.design.*;
import com.google.android.material.*;
import android.text.*;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.style.*;
import android.util.*;
import android.view.*;
import android.view.View;
import android.view.View.*;
import android.view.animation.*;
import android.webkit.*;
import android.widget.*;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.annotation.*;
import androidx.appcompat.app.AppCompatActivity;
import androidx.browser.*;
import androidx.cardview.widget.CardView;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.*;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
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
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.*;
import com.google.android.material.appbar.AppBarLayout;
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
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.*;
import com.yalantis.ucrop.*;
import java.io.*;
import java.io.File;
import java.io.InputStream;
import java.text.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.regex.*;
import org.json.*;
import androidx.core.widget.NestedScrollView;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.firebase.database.Query;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.appbar.AppBarLayout;
import android.os.Handler;
import android.os.Looper;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.drawerlayout.widget.DrawerLayout;

//start
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import com.google.android.material.switchmaterial.SwitchMaterial;
import androidx.appcompat.widget.SwitchCompat;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import androidx.browser.customtabs.CustomTabsIntent;

import com.synapse.social.studioasinc.animations.layout.layoutshaker;
// For Java
import com.synapse.social.studioasinc.styling.TextStylingUtil;


public class HomeActivity extends AppCompatActivity {
	
	private FirebaseDatabase _firebase = FirebaseDatabase.getInstance();
	private FirebaseStorage _firebase_storage = FirebaseStorage.getInstance();
	
	private HashMap<String, Object> createPostMap = new HashMap<>();
	private HashMap<String, Object> postLikeCountCache = new HashMap<>();
	private HashMap<String, Object> UserInfoCacheMap = new HashMap<>();
	private HashMap<String, Object> postFavoriteCountCache = new HashMap<>();
	private String AddFromUrlStr = "";
	private String object_clicked = "";
	private String handle = "";
	private HashMap<String, Object> m = new HashMap<>();
	private boolean Expanded = false;
	private String user_ref_uid = "";
	private String user2nickname = "";
	private String AndroidDevelopersBlogURL = "";
	
	private ArrayList<HashMap<String, Object>> storiesList = new ArrayList<>();
	private ArrayList<HashMap<String, Object>> PostsList = new ArrayList<>();
	private ArrayList<HashMap<String, Object>> FollowedUsersPostList = new ArrayList<>();
	private ArrayList<HashMap<String, Object>> FavoritePostsListmap = new ArrayList<>();
	private ArrayList<HashMap<String, Object>> LocalPostsListmap = new ArrayList<>();
	
	private LinearLayout body;
	private LinearLayout middleLayout;
	private LinearLayout bottomSpc;
	private CoordinatorLayout m_coordinator_layout;
	private LinearLayout noInternetBody;
	private LinearLayout loadingBody;
	private AppBarLayout m_coordinator_layout_appbar;
	private SwipeRefreshLayout swipeLayout;
	private CollapsingToolbarLayout m_coordinator_layout_collapsing_toolbar;
	private LinearLayout topCollapsingSpc;
	private LinearLayout m_coordinator_layout_collapsing_toolbar_body;
	private LinearLayout topSpc14;
	private LinearLayout storiesAndMiniPostsSpc;
	private LinearLayout stories;
	private LinearLayout miniPostLayout;
	private HorizontalScrollView miniPostLayoutFiltersScroll;
	private LinearLayout topBar;
	private LinearLayout bottomBar;
	private RecyclerView storiesView;
	private TextView app_name_bar;
	private LinearLayout topBarSpace;
	private ImageView imageview1;
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
	private LinearLayout miniPostLayoutTop;
	private LinearLayout miniPostLayoutMiddleSpc;
	private LinearLayout miniPostLayoutBottom;
	private LinearLayout miniPostLayoutBottomSpc;
	private CardView miniPostLayoutProfileCard;
	private EditText miniPostLayoutTextPostInput;
	private ImageView miniPostLayoutProfileImage;
	private ImageView miniPostLayoutImagePost;
	private ImageView miniPostLayoutVideoPost;
	private ImageView miniPostLayoutTextPost;
	private ImageView miniPostLayoutMoreButton;
	private LinearLayout miniPostLayoutBottomSpace;
	private TextView miniPostLayoutTextPostPublish;
	private LinearLayout miniPostLayoutFiltersScrollBody;
	private TextView miniPostLayoutFiltersScrollBodyFilterLOCAL;
	private TextView miniPostLayoutFiltersScrollBodyFilterPUBLIC;
	private TextView miniPostLayoutFiltersScrollBodyFilterFOLLOWED;
	private TextView miniPostLayoutFiltersScrollBodyFilterFAVORITE;
	private LinearLayout swipeLayoutBody;
	private LinearLayout LocalPostsBody;
	private LinearLayout PublicPostsBody;
	private LinearLayout FollowedUsersPostsBody;
	private LinearLayout FavoritesPostsBody;
	private RecyclerView LocalPostsBodyList;
	private TextView LocalPostsBodyNoPosts;
	private RecyclerView PublicPostsList;
	private TextView PublicPostsListNotFound;
	private RecyclerView FollowedUsersPostsList;
	private TextView FollowedUsersPostsFollowedUserNotFound;
	private TextView FollowedUsersPostsNotFound;
	private RecyclerView FavoritesPostsList;
	private TextView FavoritesPostsNotFound;
	private ImageView noInternetBodyIc;
	private TextView noInternetBodyTitle;
	private TextView noInternetBodySubtitle;
	private TextView noInternetBodyRetry;
	private ProgressBar loading_bar;
	
	private Intent intent = new Intent();
	private Vibrator vbr;
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
	private DatabaseReference udb = _firebase.getReference("skyline/users");
	private ChildEventListener _udb_child_listener;
	private RequestNetwork req;
	private RequestNetwork.RequestListener _req_request_listener;
	private Calendar cc = Calendar.getInstance();
	
	class c {
		Context co;
		public <T extends Activity> c(T a) {
			co = a;
		}
		public <T extends Fragment> c(T a) {
			co = a.getActivity();
		}
		public <T extends DialogFragment> c(T a) {
			co = a.getActivity();
		}
		
		public Context getContext() {
			return co;
		}
		
	}
	private MediaPlayer mp;
	private DatabaseReference rootdb = _firebase.getReference("/");
	private ChildEventListener _rootdb_child_listener;
	private AlertDialog cd;
	private AlertDialog.Builder zorry;
	private com.google.android.material.bottomsheet.BottomSheetDialog bs;
	private StorageReference post_image_storage_db = _firebase_storage.getReference("skyline");
	private OnCompleteListener<Uri> _post_image_storage_db_upload_success_listener;
	private OnSuccessListener<FileDownloadTask.TaskSnapshot> _post_image_storage_db_download_success_listener;
	private OnSuccessListener _post_image_storage_db_delete_success_listener;
	private OnProgressListener _post_image_storage_db_upload_progress_listener;
	private OnProgressListener _post_image_storage_db_download_progress_listener;
	private OnFailureListener _post_image_storage_db_failure_listener;
	private DatabaseReference pushusername = _firebase.getReference("synapse/username");
	private ChildEventListener _pushusername_child_listener;
	private Intent i = new Intent();
	
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.home);
		initialize(_savedInstanceState);
		FirebaseApp.initializeApp(this);
		
		if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED
		|| ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
			ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1000);
		} else {
			initializeLogic();
		}
	}
	
	@Override
	public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
		super.onRequestPermissionsResult(requestCode, permissions, grantResults);
		if (requestCode == 1000) {
			initializeLogic();
		}
	}
	
	private void initialize(Bundle _savedInstanceState) {
		body = findViewById(R.id.body);
		middleLayout = findViewById(R.id.middleLayout);
		bottomSpc = findViewById(R.id.bottomSpc);
		m_coordinator_layout = findViewById(R.id.m_coordinator_layout);
		noInternetBody = findViewById(R.id.noInternetBody);
		loadingBody = findViewById(R.id.loadingBody);
		m_coordinator_layout_appbar = findViewById(R.id.m_coordinator_layout_appbar);
		swipeLayout = findViewById(R.id.swipeLayout);
		m_coordinator_layout_collapsing_toolbar = findViewById(R.id.m_coordinator_layout_collapsing_toolbar);
		topCollapsingSpc = findViewById(R.id.topCollapsingSpc);
		m_coordinator_layout_collapsing_toolbar_body = findViewById(R.id.m_coordinator_layout_collapsing_toolbar_body);
		topSpc14 = findViewById(R.id.topSpc14);
		storiesAndMiniPostsSpc = findViewById(R.id.storiesAndMiniPostsSpc);
		stories = findViewById(R.id.stories);
		miniPostLayout = findViewById(R.id.miniPostLayout);
		miniPostLayoutFiltersScroll = findViewById(R.id.miniPostLayoutFiltersScroll);
		topBar = findViewById(R.id.topBar);
		bottomBar = findViewById(R.id.bottomBar);
		storiesView = findViewById(R.id.storiesView);
		app_name_bar = findViewById(R.id.app_name_bar);
		topBarSpace = findViewById(R.id.topBarSpace);
		imageview1 = findViewById(R.id.imageview1);
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
		miniPostLayoutTop = findViewById(R.id.miniPostLayoutTop);
		miniPostLayoutMiddleSpc = findViewById(R.id.miniPostLayoutMiddleSpc);
		miniPostLayoutBottom = findViewById(R.id.miniPostLayoutBottom);
		miniPostLayoutBottomSpc = findViewById(R.id.miniPostLayoutBottomSpc);
		miniPostLayoutProfileCard = findViewById(R.id.miniPostLayoutProfileCard);
		miniPostLayoutTextPostInput = findViewById(R.id.miniPostLayoutTextPostInput);
		miniPostLayoutProfileImage = findViewById(R.id.miniPostLayoutProfileImage);
		miniPostLayoutImagePost = findViewById(R.id.miniPostLayoutImagePost);
		miniPostLayoutVideoPost = findViewById(R.id.miniPostLayoutVideoPost);
		miniPostLayoutTextPost = findViewById(R.id.miniPostLayoutTextPost);
		miniPostLayoutMoreButton = findViewById(R.id.miniPostLayoutMoreButton);
		miniPostLayoutBottomSpace = findViewById(R.id.miniPostLayoutBottomSpace);
		miniPostLayoutTextPostPublish = findViewById(R.id.miniPostLayoutTextPostPublish);
		miniPostLayoutFiltersScrollBody = findViewById(R.id.miniPostLayoutFiltersScrollBody);
		miniPostLayoutFiltersScrollBodyFilterLOCAL = findViewById(R.id.miniPostLayoutFiltersScrollBodyFilterLOCAL);
		miniPostLayoutFiltersScrollBodyFilterPUBLIC = findViewById(R.id.miniPostLayoutFiltersScrollBodyFilterPUBLIC);
		miniPostLayoutFiltersScrollBodyFilterFOLLOWED = findViewById(R.id.miniPostLayoutFiltersScrollBodyFilterFOLLOWED);
		miniPostLayoutFiltersScrollBodyFilterFAVORITE = findViewById(R.id.miniPostLayoutFiltersScrollBodyFilterFAVORITE);
		swipeLayoutBody = findViewById(R.id.swipeLayoutBody);
		LocalPostsBody = findViewById(R.id.LocalPostsBody);
		PublicPostsBody = findViewById(R.id.PublicPostsBody);
		FollowedUsersPostsBody = findViewById(R.id.FollowedUsersPostsBody);
		FavoritesPostsBody = findViewById(R.id.FavoritesPostsBody);
		LocalPostsBodyList = findViewById(R.id.LocalPostsBodyList);
		LocalPostsBodyNoPosts = findViewById(R.id.LocalPostsBodyNoPosts);
		PublicPostsList = findViewById(R.id.PublicPostsList);
		PublicPostsListNotFound = findViewById(R.id.PublicPostsListNotFound);
		FollowedUsersPostsList = findViewById(R.id.FollowedUsersPostsList);
		FollowedUsersPostsFollowedUserNotFound = findViewById(R.id.FollowedUsersPostsFollowedUserNotFound);
		FollowedUsersPostsNotFound = findViewById(R.id.FollowedUsersPostsNotFound);
		FavoritesPostsList = findViewById(R.id.FavoritesPostsList);
		FavoritesPostsNotFound = findViewById(R.id.FavoritesPostsNotFound);
		noInternetBodyIc = findViewById(R.id.noInternetBodyIc);
		noInternetBodyTitle = findViewById(R.id.noInternetBodyTitle);
		noInternetBodySubtitle = findViewById(R.id.noInternetBodySubtitle);
		noInternetBodyRetry = findViewById(R.id.noInternetBodyRetry);
		loading_bar = findViewById(R.id.loading_bar);
		vbr = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
		auth = FirebaseAuth.getInstance();
		req = new RequestNetwork(this);
		zorry = new AlertDialog.Builder(this);
		
		swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
			@Override
			public void onRefresh() {
				_getReference();
			}
		});
		
		bottom_search.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				intent.setClass(getApplicationContext(), SearchActivity.class);
				intent.removeExtra("ref");
				startActivity(intent);
			}
		});
		
		bottom_videos.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				intent.setClass(getApplicationContext(), LineVideoPlayerActivity.class);
				startActivity(intent);
				finish();
			}
		});
		
		bottom_chats.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				intent.setClass(getApplicationContext(), MessagesActivity.class);
				startActivity(intent);
				finish();
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
		
		miniPostLayoutTextPostInput.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence _param1, int _param2, int _param3, int _param4) {
				final String _charSeq = _param1.toString();
				if (_charSeq.length() == 0) {
					miniPostLayoutTextPostPublish.setVisibility(View.GONE);
				} else {
					_viewGraphics(miniPostLayoutTextPostPublish, getResources().getColor(R.color.colorPrimary), 0xFFC5CAE9, 300, 0, Color.TRANSPARENT);
					miniPostLayoutTextPostPublish.setVisibility(View.VISIBLE);
				}
			}
			
			@Override
			public void beforeTextChanged(CharSequence _param1, int _param2, int _param3, int _param4) {
				
			}
			
			@Override
			public void afterTextChanged(Editable _param1) {
				
			}
		});
		
		miniPostLayoutImagePost.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				intent.setClass(getApplicationContext(), CreateImagePostActivity.class);
				startActivity(intent);
			}
		});
		
		miniPostLayoutVideoPost.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				intent.setClass(getApplicationContext(), CreateLineVideoActivity.class);
				startActivity(intent);
			}
		});
		
		miniPostLayoutTextPost.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				
			}
		});
		
		miniPostLayoutTextPostPublish.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				if (miniPostLayoutTextPostInput.getText().toString().trim().equals("")) {
					SketchwareUtil.showMessage(getApplicationContext(), getResources().getString(R.string.please_enter_text));
				} else {
					if (!(miniPostLayoutTextPostInput.getText().toString().length() > 1500)) {
						String uniqueKey = udb.push().getKey();
						cc = Calendar.getInstance();
						createPostMap = new HashMap<>();
						createPostMap.put("key", uniqueKey);
						createPostMap.put("uid", FirebaseAuth.getInstance().getCurrentUser().getUid());
						createPostMap.put("post_text", miniPostLayoutTextPostInput.getText().toString().trim());
						createPostMap.put("post_type", "TEXT");
						createPostMap.put("post_hide_views_count", "false");
						createPostMap.put("post_region", "none");
						createPostMap.put("post_hide_like_count", "false");
						createPostMap.put("post_hide_comments_count", "false");
						createPostMap.put("post_visibility", "public");
						createPostMap.put("post_disable_favorite", "false");
						createPostMap.put("post_disable_comments", "false");
						createPostMap.put("publish_date", String.valueOf((long)(cc.getTimeInMillis())));
						FirebaseDatabase.getInstance().getReference("skyline/posts").child(uniqueKey).updateChildren(createPostMap, new DatabaseReference.CompletionListener() {
							@Override
							public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
								if (databaseError == null) {
									SketchwareUtil.showMessage(getApplicationContext(), getResources().getString(R.string.post_publish_success));
								} else {
									SketchwareUtil.showMessage(getApplicationContext(), databaseError.getMessage());
								}
							}
						});
						
						miniPostLayoutTextPostInput.setText("");
					}
				}
				vbr.vibrate((long)(48));
			}
		});
		
		miniPostLayoutFiltersScrollBodyFilterLOCAL.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				_viewGraphics(miniPostLayoutFiltersScrollBodyFilterLOCAL, getResources().getColor(R.color.colorPrimary), 0xFF9FA8DA, 300, 0, Color.TRANSPARENT);
				_viewGraphics(miniPostLayoutFiltersScrollBodyFilterPUBLIC, 0xFFFFFFFF, 0xFFEEEEEE, 300, 2, 0xFFEEEEEE);
				_viewGraphics(miniPostLayoutFiltersScrollBodyFilterFOLLOWED, 0xFFFFFFFF, 0xFFEEEEEE, 300, 2, 0xFFEEEEEE);
				_viewGraphics(miniPostLayoutFiltersScrollBodyFilterFAVORITE, 0xFFFFFFFF, 0xFFEEEEEE, 300, 2, 0xFFEEEEEE);
				miniPostLayoutFiltersScrollBodyFilterLOCAL.setTextColor(0xFFFFFFFF);
				miniPostLayoutFiltersScrollBodyFilterPUBLIC.setTextColor(0xFF616161);
				miniPostLayoutFiltersScrollBodyFilterFOLLOWED.setTextColor(0xFF616161);
				miniPostLayoutFiltersScrollBodyFilterFAVORITE.setTextColor(0xFF616161);
				LocalPostsBody.setVisibility(View.VISIBLE);
				PublicPostsBody.setVisibility(View.GONE);
				FollowedUsersPostsBody.setVisibility(View.GONE);
				FavoritesPostsBody.setVisibility(View.GONE);
			}
		});
		
		miniPostLayoutFiltersScrollBodyFilterPUBLIC.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				_viewGraphics(miniPostLayoutFiltersScrollBodyFilterLOCAL, 0xFFFFFFFF, 0xFFEEEEEE, 300, 2, 0xFFEEEEEE);
				_viewGraphics(miniPostLayoutFiltersScrollBodyFilterPUBLIC, getResources().getColor(R.color.colorPrimary), 0xFF1A237E, 300, 0, Color.TRANSPARENT);
				_viewGraphics(miniPostLayoutFiltersScrollBodyFilterFOLLOWED, 0xFFFFFFFF, 0xFFEEEEEE, 300, 2, 0xFFEEEEEE);
				_viewGraphics(miniPostLayoutFiltersScrollBodyFilterFAVORITE, 0xFFFFFFFF, 0xFFEEEEEE, 300, 2, 0xFFEEEEEE);
				miniPostLayoutFiltersScrollBodyFilterLOCAL.setTextColor(0xFF616161);
				miniPostLayoutFiltersScrollBodyFilterPUBLIC.setTextColor(0xFFFFFFFF);
				miniPostLayoutFiltersScrollBodyFilterFOLLOWED.setTextColor(0xFF616161);
				miniPostLayoutFiltersScrollBodyFilterFAVORITE.setTextColor(0xFF616161);
				LocalPostsBody.setVisibility(View.GONE);
				PublicPostsBody.setVisibility(View.VISIBLE);
				FollowedUsersPostsBody.setVisibility(View.GONE);
				FavoritesPostsBody.setVisibility(View.GONE);
			}
		});
		
		miniPostLayoutFiltersScrollBodyFilterFOLLOWED.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				_viewGraphics(miniPostLayoutFiltersScrollBodyFilterLOCAL, 0xFFFFFFFF, 0xFFEEEEEE, 300, 2, 0xFFEEEEEE);
				_viewGraphics(miniPostLayoutFiltersScrollBodyFilterPUBLIC, 0xFFFFFFFF, 0xFFEEEEEE, 300, 2, 0xFFEEEEEE);
				_viewGraphics(miniPostLayoutFiltersScrollBodyFilterFOLLOWED, getResources().getColor(R.color.colorPrimary), 0xFF616161, 300, 0, Color.TRANSPARENT);
				_viewGraphics(miniPostLayoutFiltersScrollBodyFilterFAVORITE, 0xFFFFFFFF, 0xFFEEEEEE, 300, 2, 0xFFEEEEEE);
				miniPostLayoutFiltersScrollBodyFilterLOCAL.setTextColor(0xFF616161);
				miniPostLayoutFiltersScrollBodyFilterPUBLIC.setTextColor(0xFF616161);
				miniPostLayoutFiltersScrollBodyFilterFOLLOWED.setTextColor(0xFFFFFFFF);
				miniPostLayoutFiltersScrollBodyFilterFAVORITE.setTextColor(0xFF616161);
				LocalPostsBody.setVisibility(View.GONE);
				PublicPostsBody.setVisibility(View.GONE);
				FollowedUsersPostsBody.setVisibility(View.VISIBLE);
				FavoritesPostsBody.setVisibility(View.GONE);
			}
		});
		
		miniPostLayoutFiltersScrollBodyFilterFAVORITE.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				_viewGraphics(miniPostLayoutFiltersScrollBodyFilterLOCAL, 0xFFFFFFFF, 0xFFEEEEEE, 300, 2, 0xFFEEEEEE);
				_viewGraphics(miniPostLayoutFiltersScrollBodyFilterPUBLIC, 0xFFFFFFFF, 0xFFEEEEEE, 300, 2, 0xFFEEEEEE);
				_viewGraphics(miniPostLayoutFiltersScrollBodyFilterFOLLOWED, 0xFFFFFFFF, 0xFFEEEEEE, 300, 2, 0xFFEEEEEE);
				_viewGraphics(miniPostLayoutFiltersScrollBodyFilterFAVORITE, getResources().getColor(R.color.colorPrimary), 0xFF9FA8DA, 300, 0, Color.TRANSPARENT);
				miniPostLayoutFiltersScrollBodyFilterLOCAL.setTextColor(0xFF616161);
				miniPostLayoutFiltersScrollBodyFilterPUBLIC.setTextColor(0xFF616161);
				miniPostLayoutFiltersScrollBodyFilterFOLLOWED.setTextColor(0xFF616161);
				miniPostLayoutFiltersScrollBodyFilterFAVORITE.setTextColor(0xFFFFFFFF);
				LocalPostsBody.setVisibility(View.GONE);
				PublicPostsBody.setVisibility(View.GONE);
				FollowedUsersPostsBody.setVisibility(View.GONE);
				FavoritesPostsBody.setVisibility(View.VISIBLE);
			}
		});
		
		noInternetBodyRetry.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				_getReference();
			}
		});
		
		_udb_child_listener = new ChildEventListener() {
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
		udb.addChildEventListener(_udb_child_listener);
		
		_req_request_listener = new RequestNetwork.RequestListener() {
			@Override
			public void onResponse(String _param1, String _param2, HashMap<String, Object> _param3) {
				final String _tag = _param1;
				final String _response = _param2;
				final HashMap<String, Object> _responseHeaders = _param3;
				try{
					DatabaseReference getReference = FirebaseDatabase.getInstance().getReference().child("skyline/users").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
					getReference.addListenerForSingleValueEvent(new ValueEventListener() {
						@Override
						public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
							if(dataSnapshot.exists()) {
								swipeLayout.setVisibility(View.VISIBLE);
								noInternetBody.setVisibility(View.GONE);
								loadingBody.setVisibility(View.GONE);
								if (dataSnapshot.child("user_region").getValue(String.class) != null) {
									Query getLocalPostsReference = FirebaseDatabase.getInstance().getReference("skyline/posts").orderByChild("post_region").equalTo(dataSnapshot.child("user_region").getValue(String.class));
									getLocalPostsReference.addListenerForSingleValueEvent(new ValueEventListener() {
										@Override
										public void onDataChange(DataSnapshot _dataSnapshot) {
											if(_dataSnapshot.exists()) {
												LocalPostsBodyList.setVisibility(View.VISIBLE);
												LocalPostsBodyNoPosts.setVisibility(View.GONE);
												LocalPostsListmap.clear();
												try {
													GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
													for (DataSnapshot _data : _dataSnapshot.getChildren()) {
														HashMap<String, Object> _map = _data.getValue(_ind);
														LocalPostsListmap.add(_map);
													}
												} catch (Exception _e) {
													_e.printStackTrace();
												}
												SketchwareUtil.sortListMap(LocalPostsListmap, "publish_date", false, false);
												LocalPostsBodyList.setAdapter(new LocalPostsBodyListAdapter(LocalPostsListmap));
											} else {
												LocalPostsBodyList.setVisibility(View.GONE);
												LocalPostsBodyNoPosts.setVisibility(View.VISIBLE);
											}
										}
										@Override
										public void onCancelled(DatabaseError _databaseError) {
											LocalPostsBodyList.setVisibility(View.GONE);
											LocalPostsBodyNoPosts.setVisibility(View.VISIBLE);
										}
									});
									
								} else {
									LocalPostsBodyList.setVisibility(View.GONE);
									LocalPostsBodyNoPosts.setVisibility(View.VISIBLE);
								}
								if (dataSnapshot.child("avatar").getValue(String.class).equals("null")) {
									miniPostLayoutProfileImage.setImageResource(R.drawable.avatar);
								} else {
									Glide.with(getApplicationContext()).load(Uri.parse(dataSnapshot.child("avatar").getValue(String.class))).into(miniPostLayoutProfileImage);
									Glide.with(getApplicationContext()).load(Uri.parse(dataSnapshot.child("avatar").getValue(String.class))).into(bottom_profile_ic);
								}
								if (dataSnapshot.child("account_premium").getValue(String.class).equals("true")) {
									
								} else {
									
								}
								if (dataSnapshot.child("account_type").getValue(String.class).equals("admin")) {
									
								} else {
									
								}
								SynapseApp.setUserStatus();
							} else {
								swipeLayout.setVisibility(View.GONE);
								noInternetBody.setVisibility(View.VISIBLE);
								loadingBody.setVisibility(View.GONE);
							}
						}
						@Override
						public void onCancelled(@NonNull DatabaseError databaseError) {
							swipeLayout.setVisibility(View.GONE);
							noInternetBody.setVisibility(View.VISIBLE);
							loadingBody.setVisibility(View.GONE);
						}
					});
				}catch(Exception e){
					SketchwareUtil.showMessage(getApplicationContext(), "Something went wrong");
				}
			}
			
			@Override
			public void onErrorResponse(String _param1, String _param2) {
				final String _tag = _param1;
				final String _message = _param2;
				swipeLayout.setVisibility(View.GONE);
				noInternetBody.setVisibility(View.VISIBLE);
				loadingBody.setVisibility(View.GONE);
			}
		};
		
		_rootdb_child_listener = new ChildEventListener() {
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
		rootdb.addChildEventListener(_rootdb_child_listener);
		
		_post_image_storage_db_upload_progress_listener = new OnProgressListener<UploadTask.TaskSnapshot>() {
			@Override
			public void onProgress(UploadTask.TaskSnapshot _param1) {
				double _progressValue = (100.0 * _param1.getBytesTransferred()) / _param1.getTotalByteCount();
				
			}
		};
		
		_post_image_storage_db_download_progress_listener = new OnProgressListener<FileDownloadTask.TaskSnapshot>() {
			@Override
			public void onProgress(FileDownloadTask.TaskSnapshot _param1) {
				double _progressValue = (100.0 * _param1.getBytesTransferred()) / _param1.getTotalByteCount();
				
			}
		};
		
		_post_image_storage_db_upload_success_listener = new OnCompleteListener<Uri>() {
			@Override
			public void onComplete(Task<Uri> _param1) {
				final String _downloadUrl = _param1.getResult().toString();
				
			}
		};
		
		_post_image_storage_db_download_success_listener = new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
			@Override
			public void onSuccess(FileDownloadTask.TaskSnapshot _param1) {
				final long _totalByteCount = _param1.getTotalByteCount();
				
			}
		};
		
		_post_image_storage_db_delete_success_listener = new OnSuccessListener() {
			@Override
			public void onSuccess(Object _param1) {
				
			}
		};
		
		_post_image_storage_db_failure_listener = new OnFailureListener() {
			@Override
			public void onFailure(Exception _param1) {
				final String _message = _param1.getMessage();
				
			}
		};
		
		_pushusername_child_listener = new ChildEventListener() {
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
		pushusername.addChildEventListener(_pushusername_child_listener);
		
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
		_getReference();
		{
			HashMap<String, Object> _item = new HashMap<>();
			_item.put("uid", FirebaseAuth.getInstance().getCurrentUser().getUid());
			storiesList.add(_item);
		}
		noInternetBodySubtitle.setText(getResources().getString(R.string.reasons_may_be).concat("\n\n".concat(getResources().getString(R.string.err_no_internet).concat("\n".concat(getResources().getString(R.string.err_app_maintenance).concat("\n".concat(getResources().getString(R.string.err_problem_on_our_side))))))));
		_viewGraphics(miniPostLayoutFiltersScrollBodyFilterLOCAL, 0xFFFFFFFF, 0xFFEEEEEE, 300, 2, 0xFFEEEEEE);
		_viewGraphics(miniPostLayoutFiltersScrollBodyFilterPUBLIC, getResources().getColor(R.color.colorPrimary), 0xFF9FA8DA, 300, 0, Color.TRANSPARENT);
		_viewGraphics(miniPostLayoutFiltersScrollBodyFilterFOLLOWED, 0xFFFFFFFF, 0xFFEEEEEE, 300, 2, 0xFFEEEEEE);
		_viewGraphics(miniPostLayoutFiltersScrollBodyFilterFAVORITE, 0xFFFFFFFF, 0xFFEEEEEE, 300, 2, 0xFFEEEEEE);
		miniPostLayoutFiltersScrollBodyFilterLOCAL.setTextColor(0xFF616161);
		miniPostLayoutFiltersScrollBodyFilterPUBLIC.setTextColor(0xFFFFFFFF);
		miniPostLayoutFiltersScrollBodyFilterFOLLOWED.setTextColor(0xFF616161);
		miniPostLayoutTextPostPublish.setVisibility(View.GONE);
		miniPostLayoutFiltersScrollBodyFilterFAVORITE.setTextColor(0xFF616161);
		LocalPostsBody.setVisibility(View.GONE);
		PublicPostsBody.setVisibility(View.VISIBLE);
		FollowedUsersPostsBody.setVisibility(View.GONE);
		FavoritesPostsBody.setVisibility(View.GONE);
		_stateColor(0xFFFFFFFF, 0xFFFFFFFF);
		//_ImageColor(bottom_search_ic, 0xFFBDBDBD);
		//_ImageColor(bottom_videos_ic, 0xFFBDBDBD);
		//_ImageColor(bottom_chats_ic, 0xFFBDBDBD);
		//_ImageColor(bottom_profile_ic, 0xFFBDBDBD);
		_viewGraphics(noInternetBodyRetry, 0xFF445E91, 0xFF1976D2, 24, 3, 0xFF1E88E5);
		miniPostLayoutProfileCard.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b) { this.setCornerRadius(a); this.setColor(b); return this; } }.getIns((int)300, Color.TRANSPARENT));
		_ImageColor(miniPostLayoutImagePost, 0xFF445E91);
		_ImageColor(miniPostLayoutVideoPost, 0xFF445E91);
		_ImageColor(miniPostLayoutTextPost, 0xFF445E91);
		_ImageColor(miniPostLayoutMoreButton, 0xFF445E91);
		_viewGraphics(miniPostLayoutImagePost, 0xFFFFFFFF, 0xFFEEEEEE, 300, 1, 0xFFEEEEEE);
		_viewGraphics(miniPostLayoutVideoPost, 0xFFFFFFFF, 0xFFEEEEEE, 300, 1, 0xFFEEEEEE);
		_viewGraphics(miniPostLayoutTextPost, 0xFFFFFFFF, 0xFFEEEEEE, 300, 1, 0xFFEEEEEE);
		_viewGraphics(miniPostLayoutMoreButton, 0xFFFFFFFF, 0xFFEEEEEE, 300, 1, 0xFFEEEEEE);
		
		//_viewGraphics(miniPostLayoutFiltersScrollBodyFilterPUBLIC, 0xFF000000, 0xFF616161, 300, 0, Color.TRANSPARENT);
		//_viewGraphics(miniPostLayoutFiltersScrollBodyFilterLOCAL, 0xFFFFFFFF, 0xFFEEEEEE, 300, 2, 0xFFEEEEEE);
		//_viewGraphics(miniPostLayoutFiltersScrollBodyFilterFOLLOWED, 0xFFFFFFFF, 0xFFEEEEEE, 300, 2, 0xFFEEEEEE);
		//_viewGraphics(miniPostLayoutFiltersScrollBodyFilterFAVORITE, 0xFFFFFFFF, 0xFFEEEEEE, 300, 2, 0xFFEEEEEE);
		LocalPostsBody.setVisibility(View.GONE);
		PublicPostsBody.setVisibility(View.VISIBLE);
		FollowedUsersPostsBody.setVisibility(View.GONE);
		FavoritesPostsBody.setVisibility(View.GONE);
		storiesView.setAdapter(new StoriesViewAdapter(storiesList));
		storiesView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL, false));
		LocalPostsBodyList.setLayoutManager(new LinearLayoutManager(this));
		PublicPostsList.setLayoutManager(new LinearLayoutManager(this));
		FollowedUsersPostsList.setLayoutManager(new LinearLayoutManager(this));
		FavoritesPostsList.setLayoutManager(new LinearLayoutManager(this));
		_ImgRound(bottom_profile_ic, 360);
		_viewGraphics(miniPostLayoutTextPostPublish, Color.TRANSPARENT, Color.TRANSPARENT, 300, 2, 0xFF616161);
		app_name_bar.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/ginto650otf.ttf"), 1);
	}
	
	
	@Override
	public void onBackPressed() {
		//Dialog
		MaterialAlertDialogBuilder zorry = new MaterialAlertDialogBuilder(HomeActivity.this);
		
		zorry.setTitle("Exit Synapse");
		zorry.setMessage("Are you certain you wish to terminate the Synapse session? Please confirm your decision.");
		zorry.setIcon(R.drawable.baseline_logout_black_48dp);
		zorry.setPositiveButton("Exit", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface _dialog, int _which) {
				finishAffinity();
			}
		});
		zorry.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface _dialog, int _which) {
				
			}
		});
		zorry.create().show();
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
		req.startRequestNetwork(RequestNetworkController.POST, "https://google.com", "get", _req_request_listener);
		swipeLayout.setRefreshing(false);
		_getPostsReference();
	}
	
	
	public void _setMargin(final View _view, final double _r, final double _l, final double _t, final double _b) {
		float dpRatio = new c(this).getContext().getResources().getDisplayMetrics().density;
		int right = (int)(_r * dpRatio);
		int left = (int)(_l * dpRatio);
		int top = (int)(_t * dpRatio);
		int bottom = (int)(_b * dpRatio);
		
		boolean _default = false;
		
		ViewGroup.LayoutParams p = _view.getLayoutParams();
		if (p instanceof LinearLayout.LayoutParams) {
			LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams)p;
			lp.setMargins(left, top, right, bottom);
			_view.setLayoutParams(lp);
		} else if (p instanceof RelativeLayout.LayoutParams) {
			RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams)p;
			lp.setMargins(left, top, right, bottom);
			_view.setLayoutParams(lp);
		} else if (p instanceof TableRow.LayoutParams) {
			TableRow.LayoutParams lp = (TableRow.LayoutParams)p;
			lp.setMargins(left, top, right, bottom);
			_view.setLayoutParams(lp);
		}
		
	}
	
	
	public void _getPostsReference() {
		Query getPostsReference = FirebaseDatabase.getInstance().getReference("skyline/posts").orderByChild("publish_date");
		Query getFollowingCheckReference = FirebaseDatabase.getInstance().getReference("skyline/following").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
		Query getFavoritePostsReference = FirebaseDatabase.getInstance().getReference("skyline/favorite-posts").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
		getPostsReference.addListenerForSingleValueEvent(new ValueEventListener() {
			@Override
			public void onDataChange(DataSnapshot _dataSnapshot) {
				if(_dataSnapshot.exists()) {
					PublicPostsList.setVisibility(View.VISIBLE);
					PublicPostsListNotFound.setVisibility(View.GONE);
					PostsList.clear();
					try {
						GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
						for (DataSnapshot _data : _dataSnapshot.getChildren()) {
							HashMap<String, Object> _map = _data.getValue(_ind);
							PostsList.add(_map);
						}
					} catch (Exception _e) {
						_e.printStackTrace();
					}
					SketchwareUtil.sortListMap(PostsList, "publish_date", false, false);
					PublicPostsList.setAdapter(new PublicPostsListAdapter(PostsList));
				} else {
					PublicPostsList.setVisibility(View.GONE);
					PublicPostsListNotFound.setVisibility(View.VISIBLE);
				}
			}
			@Override
			public void onCancelled(DatabaseError _databaseError) {
				
			}
		});
		getFollowingCheckReference.addListenerForSingleValueEvent(new ValueEventListener() {
			@Override
			public void onDataChange(DataSnapshot _dataSnapshot) {
				if (_dataSnapshot.exists()) {
					Map<String, Object> followingUsersData = (Map<String, Object>) _dataSnapshot.getValue();
					FollowedUsersPostList.clear();
					
					if (followingUsersData != null) {
						List<String> followingUsersUids = new ArrayList<>(followingUsersData.keySet());
						
						for (String uid : followingUsersUids) {
							Query getUserPostsRef = FirebaseDatabase.getInstance().getReference("skyline/posts").orderByChild("uid").equalTo(uid).limitToLast(15);
							getUserPostsRef.addListenerForSingleValueEvent(new ValueEventListener() {
								@Override
								public void onDataChange(DataSnapshot _dataSnapshot) {
									if (_dataSnapshot.exists()) {
										FollowedUsersPostsList.setVisibility(View.VISIBLE);
										FollowedUsersPostsFollowedUserNotFound.setVisibility(View.GONE);
										FollowedUsersPostsNotFound.setVisibility(View.GONE);
										try {
											GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
											for (DataSnapshot _data : _dataSnapshot.getChildren()) {
												HashMap<String, Object> _map = _data.getValue(_ind);
												FollowedUsersPostList.add(_map);
											}
										} catch (Exception _e) {
											_e.printStackTrace();
										}
										SketchwareUtil.sortListMap(FollowedUsersPostList, "publish_date", false, false);
										FollowedUsersPostsList.setAdapter(new FollowedUsersPostsListAdapter(FollowedUsersPostList));
									} else {
										if (FollowedUsersPostList.size() == 0) {
											FollowedUsersPostsList.setVisibility(View.GONE);
											FollowedUsersPostsFollowedUserNotFound.setVisibility(View.GONE);
											FollowedUsersPostsNotFound.setVisibility(View.VISIBLE);
										}
									}
								}
								
								@Override
								public void onCancelled(DatabaseError _databaseError) {}
							});
						}
					}
				} else {
					FollowedUsersPostsList.setVisibility(View.GONE);
					FollowedUsersPostsFollowedUserNotFound.setVisibility(View.VISIBLE);
					FollowedUsersPostsNotFound.setVisibility(View.GONE);
				}
			}
			
			@Override
			public void onCancelled(DatabaseError _databaseError) {}
		});
		
		getFavoritePostsReference.addListenerForSingleValueEvent(new ValueEventListener() {
			@Override
			public void onDataChange(DataSnapshot _dataSnapshot) {
				if(_dataSnapshot.exists()) {
					Map<String, Object> favoritesPostsData = (Map<String, Object>) _dataSnapshot.getValue();
					FavoritePostsListmap.clear();
					
					if (favoritesPostsData != null) {
						List<String> favoritesPostsKeys = new ArrayList<>(favoritesPostsData.keySet());
						
						for (String key : favoritesPostsKeys) {
							Query getFavoritesPostsRef = FirebaseDatabase.getInstance().getReference("skyline/posts").orderByChild("key").equalTo(key);
							getFavoritesPostsRef.addListenerForSingleValueEvent(new ValueEventListener() {
								@Override
								public void onDataChange(DataSnapshot _dataSnapshot) {
									if (_dataSnapshot.exists()) {
										FavoritesPostsList.setVisibility(View.VISIBLE);
										FavoritesPostsNotFound.setVisibility(View.GONE);
										try {
											GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
											for (DataSnapshot _data : _dataSnapshot.getChildren()) {
												HashMap<String, Object> _map = _data.getValue(_ind);
												FavoritePostsListmap.add(_map);
											}
										} catch (Exception _e) {
											_e.printStackTrace();
										}
										Collections.reverse(FavoritePostsListmap);
										FavoritesPostsList.setAdapter(new FavoritesPostsListAdapter(FavoritePostsListmap));
									} else {
										if (FavoritePostsListmap.size() == 0) {
											FavoritesPostsList.setVisibility(View.GONE);
											FavoritesPostsNotFound.setVisibility(View.VISIBLE);
										}
									}
								}
								
								@Override
								public void onCancelled(DatabaseError _databaseError) {}
							});
						}
					}
				} else {
					FavoritesPostsList.setVisibility(View.GONE);
					FavoritesPostsNotFound.setVisibility(View.VISIBLE);
				}
			}
			@Override
			public void onCancelled(DatabaseError _databaseError) {
				
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
	
	
	public void _AddFromUrlDialog() {
		
	}
	
	
	public void _textview_mh(final TextView _txt, final String _value) {
		_txt.setMovementMethod(android.text.method.LinkMovementMethod.getInstance());
		//_txt.setTextIsSelectable(true);
		updateSpan(_value, _txt);
	}
	private void updateSpan(String str, TextView _txt){
		SpannableStringBuilder ssb = new SpannableStringBuilder(str);
		java.util.regex.Pattern pattern = java.util.regex.Pattern.compile("(?<![^\\s])(([@]{1}|[#]{1})([A-Za-z0-9_-]\\.?)+)(?![^\\s,])|\\*\\*(.*?)\\*\\*|__(.*?)__|~~(.*?)~~|_(.*?)_|\\*(.*?)\\*|///(.*?)///");
		java.util.regex.Matcher matcher = pattern.matcher(str);
		int offset = 0;
		
		while (matcher.find()) {
			int start = matcher.start() + offset;
			int end = matcher.end() + offset;
			
			if (matcher.group(3) != null) {
				// For mentions or hashtags
				ProfileSpan span = new ProfileSpan();
				ssb.setSpan(span, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
			} else if (matcher.group(4) != null) {
				// For bold text (**bold**)
				String boldText = matcher.group(4); // Extract text inside **
				ssb.replace(start, end, boldText);
				ssb.setSpan(new android.text.style.StyleSpan(android.graphics.Typeface.BOLD), start, start + boldText.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
				offset -= 4; // Update offset for bold text replacement
			} else if (matcher.group(5) != null) {
				// For italic text (__italic__)
				String italicText = matcher.group(5);
				ssb.replace(start, end, italicText);
				ssb.setSpan(new android.text.style.StyleSpan(android.graphics.Typeface.ITALIC), start, start + italicText.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
				offset -= 4; // Update offset for italic text replacement
			} else if (matcher.group(6) != null) {
				// For strikethrough text (~~strikethrough~~)
				String strikethroughText = matcher.group(6);
				ssb.replace(start, end, strikethroughText);
				ssb.setSpan(new android.text.style.StrikethroughSpan(), start, start + strikethroughText.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
				offset -= 4; // Update offset for strikethrough text replacement
			} else if (matcher.group(7) != null) {
				// For underline text (_underline_)
				String underlineText = matcher.group(7);
				ssb.replace(start, end, underlineText);
				ssb.setSpan(new android.text.style.UnderlineSpan(), start, start + underlineText.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
				offset -= 2; // Update offset for underline text replacement
			} else if (matcher.group(8) != null) {
				// For italic text (*italic*)
				String italicText = matcher.group(8);
				ssb.replace(start, end, italicText);
				ssb.setSpan(new android.text.style.StyleSpan(android.graphics.Typeface.ITALIC), start, start + italicText.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
				offset -= 2; // Update offset for italic text replacement
			} else if (matcher.group(9) != null) {
				// For bold-italic text (///bold-italic///)
				String boldItalicText = matcher.group(9);
				ssb.replace(start, end, boldItalicText);
				ssb.setSpan(new android.text.style.StyleSpan(android.graphics.Typeface.BOLD_ITALIC), start, start + boldItalicText.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
				offset -= 6; // Update offset for bold-italic text replacement
			}
		}
		_txt.setText(ssb);
	}
	private class ProfileSpan extends android.text.style.ClickableSpan{
		
		
		@Override
		public void onClick(View view){
			
			if(view instanceof TextView){
				TextView tv = (TextView)view;
				
				if(tv.getText() instanceof Spannable){
					Spannable sp = (Spannable)tv.getText();
					
					int start = sp.getSpanStart(this);
					int end = sp.getSpanEnd(this);
					object_clicked = sp.subSequence(start,end).toString();
					handle = object_clicked.replace("@", ""); 
					DatabaseReference getReference = FirebaseDatabase.getInstance().getReference()
					.child("synapse/username")
					.child(handle);  // This points directly to "synapse/username/[handle]"
					getReference.addListenerForSingleValueEvent(new ValueEventListener() {
						@Override
						public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
							if(dataSnapshot.exists()) {
								if (!dataSnapshot.child("uid").getValue(String.class).equals("null")) {
									intent.setClass(getApplicationContext(), ProfileActivity.class);
									intent.putExtra("uid", dataSnapshot.child("uid").getValue(String.class));
									startActivity(intent);
								} else {
									
								}
							} else {
							}
						}
						@Override
						public void onCancelled(@NonNull DatabaseError databaseError) {
							//	swipeLayout.setVisibility(View.GONE);
							//noInternetBody.setVisibility(View.VISIBLE);
							//	loadingBody.setVisibility(View.GONE);
						}
					});
				}
			}
			
		}
		@Override
		public void updateDrawState(TextPaint ds) {
			ds.setUnderlineText(false);
			ds.setColor(Color.parseColor("#445E91"));
			ds.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
		}
	}
	
	
	public void _rippleRoundStroke(final View _view, final String _focus, final String _pressed, final double _round, final double _stroke, final String _strokeclr) {
		android.graphics.drawable.GradientDrawable GG = new android.graphics.drawable.GradientDrawable();
		GG.setColor(Color.parseColor(_focus));
		GG.setCornerRadius((float)_round);
		GG.setStroke((int) _stroke,
		Color.parseColor("#" + _strokeclr.replace("#", "")));
		android.graphics.drawable.RippleDrawable RE = new android.graphics.drawable.RippleDrawable(new android.content.res.ColorStateList(new int[][]{new int[]{}}, new int[]{ Color.parseColor("#FF757575")}), GG, null);
		_view.setBackground(RE);
	}
	
	
	public void _ImgRound(final ImageView _imageview, final double _value) {
		android.graphics.drawable.GradientDrawable gd = new android.graphics.drawable.GradientDrawable ();
		gd.setColor(android.R.color.transparent);
		gd.setCornerRadius((int)_value);
		_imageview.setClipToOutline(true);
		_imageview.setBackground(gd);
	}
	
	
	public void _DeleteFirebaseStorage(final String _deletingURL) {
		_firebase_storage.getReferenceFromUrl(_deletingURL).delete().addOnSuccessListener(_post_image_storage_db_delete_success_listener).addOnFailureListener(_post_image_storage_db_failure_listener);
	}
	
	
	public void _OpenWebView(final String _URL) {
		AndroidDevelopersBlogURL = _URL;
		CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
		builder.setToolbarColor(Color.parseColor("#242D39"));
		CustomTabsIntent customtabsintent = builder.build();
		customtabsintent.launchUrl(this, Uri.parse(AndroidDevelopersBlogURL));
	}
	
	public class StoriesViewAdapter extends RecyclerView.Adapter<StoriesViewAdapter.ViewHolder> {
		
		ArrayList<HashMap<String, Object>> _data;
		
		public StoriesViewAdapter(ArrayList<HashMap<String, Object>> _arr) {
			_data = _arr;
		}
		
		@Override
		public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
			LayoutInflater _inflater = getLayoutInflater();
			View _v = _inflater.inflate(R.layout.synapse_story_cv, null);
			RecyclerView.LayoutParams _lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
			_v.setLayoutParams(_lp);
			return new ViewHolder(_v);
		}
		
		@Override
		public void onBindViewHolder(ViewHolder _holder, final int _position) {
			View _view = _holder.itemView;
			
			final LinearLayout storiesMyStory = _view.findViewById(R.id.storiesMyStory);
			final LinearLayout storiesSecondStory = _view.findViewById(R.id.storiesSecondStory);
			final androidx.cardview.widget.CardView storiesMyStoryProfileCard = _view.findViewById(R.id.storiesMyStoryProfileCard);
			final TextView storiesMyStoryTitle = _view.findViewById(R.id.storiesMyStoryTitle);
			final RelativeLayout storiesMyStoryRelative = _view.findViewById(R.id.storiesMyStoryRelative);
			final ImageView storiesMyStoryProfileImage = _view.findViewById(R.id.storiesMyStoryProfileImage);
			final LinearLayout storiesMyStoryRelativeAddBody = _view.findViewById(R.id.storiesMyStoryRelativeAddBody);
			final ImageView storiesMyStoryRelativeAdd = _view.findViewById(R.id.storiesMyStoryRelativeAdd);
			final androidx.cardview.widget.CardView storiesSecondStoryProfileCard = _view.findViewById(R.id.storiesSecondStoryProfileCard);
			final TextView storiesSecondStoryTitle = _view.findViewById(R.id.storiesSecondStoryTitle);
			final ImageView storiesSecondStoryProfileImage = _view.findViewById(R.id.storiesSecondStoryProfileImage);
			
			RecyclerView.LayoutParams _lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
			_view.setLayoutParams(_lp);
			_ImageColor(storiesMyStoryRelativeAdd, 0xFFFFFFFF);
			_viewGraphics(storiesMyStory, 0xFFFFFFFF, 0xFFEEEEEE, 18, 0, Color.TRANSPARENT);
			_viewGraphics(storiesSecondStory, 0xFFFFFFFF, 0xFFEEEEEE, 18, 0, Color.TRANSPARENT);
			storiesMyStoryRelativeAddBody.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b) { this.setCornerRadius(a); this.setColor(b); return this; } }.getIns((int)0, 0x7B000000));
			storiesMyStoryProfileCard.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b) { this.setCornerRadius(a); this.setColor(b); return this; } }.getIns((int)300, Color.TRANSPARENT));
			storiesSecondStoryProfileCard.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b) { this.setCornerRadius(a); this.setColor(b); return this; } }.getIns((int)300, Color.TRANSPARENT));
			if (_position == 0) {
				storiesMyStoryTitle.setText(getResources().getString(R.string.add_story));
				DatabaseReference getReference = FirebaseDatabase.getInstance().getReference().child("skyline/users").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
				getReference.addListenerForSingleValueEvent(new ValueEventListener() {
					@Override
					public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
						if(dataSnapshot.exists()) {
							if (dataSnapshot.child("avatar").getValue(String.class).equals("null")) {
								storiesMyStoryProfileImage.setImageResource(R.drawable.avatar);
							} else {
								Glide.with(getApplicationContext()).load(Uri.parse(dataSnapshot.child("avatar").getValue(String.class))).into(storiesMyStoryProfileImage);
							}
						} else {
						}
					}
					@Override
					public void onCancelled(@NonNull DatabaseError databaseError) {
						
					}
				});
				storiesMyStory.setVisibility(View.VISIBLE);
				storiesSecondStory.setVisibility(View.GONE);
				_setMargin(storiesMyStory, 8, 16, 8, 8);
			} else {
				storiesMyStory.setVisibility(View.GONE);
				storiesSecondStory.setVisibility(View.VISIBLE);
				if (_position == (storiesList.size() - 1)) {
					_setMargin(storiesSecondStory, 16, 0, 8, 8);
				} else {
					_setMargin(storiesSecondStory, 8, 0, 8, 8);
				}
			}
			storiesMyStory.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View _view) {
					i.setClass(getApplicationContext(), GlobalsearchActivity.class);
					startActivity(i);
				}
			});
			storiesSecondStory.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View _view) {
					
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
	
	public class LocalPostsBodyListAdapter extends RecyclerView.Adapter<LocalPostsBodyListAdapter.ViewHolder> {
		
		ArrayList<HashMap<String, Object>> _data;
		
		public LocalPostsBodyListAdapter(ArrayList<HashMap<String, Object>> _arr) {
			_data = _arr;
		}
		
		@Override
		public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
			LayoutInflater _inflater = getLayoutInflater();
			View _v = _inflater.inflate(R.layout.synapse_post_cv, null);
			RecyclerView.LayoutParams _lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
			_v.setLayoutParams(_lp);
			return new ViewHolder(_v);
		}
		
		@Override
		public void onBindViewHolder(ViewHolder _holder, final int _position) {
			View _view = _holder.itemView;
			
			final LinearLayout body = _view.findViewById(R.id.body);
			final LinearLayout top = _view.findViewById(R.id.top);
			final LinearLayout linear1 = _view.findViewById(R.id.linear1);
			final LinearLayout middle = _view.findViewById(R.id.middle);
			final LinearLayout bottom = _view.findViewById(R.id.bottom);
			final LinearLayout userInfo = _view.findViewById(R.id.userInfo);
			final LinearLayout topSpace = _view.findViewById(R.id.topSpace);
			final ImageView topMoreButton = _view.findViewById(R.id.topMoreButton);
			final androidx.cardview.widget.CardView userInfoProfileCard = _view.findViewById(R.id.userInfoProfileCard);
			final LinearLayout userInfoRight = _view.findViewById(R.id.userInfoRight);
			final ImageView userInfoProfileImage = _view.findViewById(R.id.userInfoProfileImage);
			final LinearLayout userInfoUsernameRightTop = _view.findViewById(R.id.userInfoUsernameRightTop);
			final LinearLayout btmPost = _view.findViewById(R.id.btmPost);
			final TextView userInfoUsername = _view.findViewById(R.id.userInfoUsername);
			final ImageView userInfoGenderBadge = _view.findViewById(R.id.userInfoGenderBadge);
			final ImageView userInfoUsernameVerifiedBadge = _view.findViewById(R.id.userInfoUsernameVerifiedBadge);
			final TextView postPublishDate = _view.findViewById(R.id.postPublishDate);
			final ImageView postPrivateStateIcon = _view.findViewById(R.id.postPrivateStateIcon);
			final TextView postMessageTextMiddle = _view.findViewById(R.id.postMessageTextMiddle);
			final ImageView postImage = _view.findViewById(R.id.postImage);
			final WebView linkprewebview = _view.findViewById(R.id.linkprewebview);
			final VideoView PostVideoPlayer = _view.findViewById(R.id.PostVideoPlayer);
			final LinearLayout likeButton = _view.findViewById(R.id.likeButton);
			final LinearLayout commentsButton = _view.findViewById(R.id.commentsButton);
			final LinearLayout shareButton = _view.findViewById(R.id.shareButton);
			final LinearLayout bottomSpc = _view.findViewById(R.id.bottomSpc);
			final ImageView favoritePostButton = _view.findViewById(R.id.favoritePostButton);
			final ImageView likeButtonIc = _view.findViewById(R.id.likeButtonIc);
			final TextView likeButtonCount = _view.findViewById(R.id.likeButtonCount);
			final TextView textview1 = _view.findViewById(R.id.textview1);
			final TextView tv_1 = _view.findViewById(R.id.tv_1);
			final ImageView commentsButtonIc = _view.findViewById(R.id.commentsButtonIc);
			final TextView commentsButtonCount = _view.findViewById(R.id.commentsButtonCount);
			final TextView textview2 = _view.findViewById(R.id.textview2);
			final TextView tv_2 = _view.findViewById(R.id.tv_2);
			final ImageView shareButtonIc = _view.findViewById(R.id.shareButtonIc);
			final TextView shareButtonCount = _view.findViewById(R.id.shareButtonCount);
			
			RecyclerView.LayoutParams _lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
			_view.setLayoutParams(_lp);
			body.setVisibility(View.GONE);
			_ImageColor(postPrivateStateIcon, 0xFF616161);
			_viewGraphics(topMoreButton, 0xFFFFFFFF, 0xFFEEEEEE, 300, 0, Color.TRANSPARENT);
			if (_data.get((int)_position).get("post_type").toString().equals("TEXT") || (_data.get((int)_position).get("post_type").toString().equals("IMAGE") || _data.get((int)_position).get("post_type").toString().equals("VIDEO"))) {
				if (_data.get((int)_position).containsKey("post_text")) {
					// Initialize TextStylingUtil by getting the context from the TextView
					// postMessageTextMiddle.getContext() will correctly return the Activity context
					TextStylingUtil textStylingUtil = new TextStylingUtil(postMessageTextMiddle.getContext()); // <-- CORRECTED HERE
					
					String postText = _data.get((int)_position).get("post_text").toString();
					textStylingUtil.applyStyling(postText, postMessageTextMiddle);
					
					postMessageTextMiddle.setVisibility(View.VISIBLE);
				} else {
					postMessageTextMiddle.setVisibility(View.GONE);
				}
				if (_data.get((int)_position).containsKey("post_image")) {
					Glide.with(getApplicationContext())
					.load(Uri.parse(_data.get((int)_position).get("post_image").toString()))
					.placeholder(R.drawable.ic_loading_png)
					.into(postImage);
					postImage.setVisibility(View.VISIBLE);
				} else {
					postImage.setVisibility(View.GONE);
				}
			} else {
				postImage.setVisibility(View.GONE);
				postMessageTextMiddle.setVisibility(View.GONE);
			}
			if (_data.get((int)_position).get("post_hide_like_count").toString().equals("true")) {
				likeButtonCount.setVisibility(View.GONE);
			} else {
				likeButtonCount.setVisibility(View.VISIBLE);
			}
			if (_data.get((int)_position).get("post_hide_comments_count").toString().equals("true")) {
				commentsButtonCount.setVisibility(View.GONE);
			} else {
				commentsButtonCount.setVisibility(View.VISIBLE);
			}
			if (_data.get((int)_position).get("post_disable_comments").toString().equals("true")) {
				commentsButton.setVisibility(View.GONE);
			} else {
				commentsButton.setVisibility(View.VISIBLE);
			}
			_setTime(Double.parseDouble(_data.get((int)_position).get("publish_date").toString()), postPublishDate);
			if (UserInfoCacheMap.containsKey("uid-".concat(_data.get((int)_position).get("uid").toString()))) {
				if (_data.get((int)_position).get("post_visibility").toString().equals("private")) {
					if (_data.get((int)_position).get("uid").toString().equals(FirebaseAuth.getInstance().getCurrentUser().getUid())) {
						postPrivateStateIcon.setVisibility(View.VISIBLE);
						body.setVisibility(View.VISIBLE);
					} else {
						body.setVisibility(View.GONE);
					}
				} else {
					body.setVisibility(View.VISIBLE);
					postPrivateStateIcon.setVisibility(View.GONE);
				}
				if (UserInfoCacheMap.get("banned-".concat(_data.get((int)_position).get("uid").toString())).toString().equals("true")) {
					userInfoProfileImage.setImageResource(R.drawable.banned_avatar);
				} else {
					if (UserInfoCacheMap.get("avatar-".concat(_data.get((int)_position).get("uid").toString())).toString().equals("null")) {
						userInfoProfileImage.setImageResource(R.drawable.avatar);
					} else {
						Glide.with(getApplicationContext()).load(Uri.parse(UserInfoCacheMap.get("avatar-".concat(_data.get((int)_position).get("uid").toString())).toString())).into(userInfoProfileImage);
					}
				}
				if (UserInfoCacheMap.get("nickname-".concat(_data.get((int)_position).get("uid").toString())).toString().equals("null")) {
					userInfoUsername.setText("@" + UserInfoCacheMap.get("username-".concat(_data.get((int)_position).get("uid").toString())).toString());
				} else {
					userInfoUsername.setText(UserInfoCacheMap.get("nickname-".concat(_data.get((int)_position).get("uid").toString())).toString());
				}
				if (UserInfoCacheMap.get("gender-".concat(_data.get((int)_position).get("uid").toString())).toString().equals("hidden")) {
					userInfoGenderBadge.setVisibility(View.GONE);
				} else {
					if (UserInfoCacheMap.get("gender-".concat(_data.get((int)_position).get("uid").toString())).toString().equals("male")) {
						userInfoGenderBadge.setImageResource(R.drawable.male_badge);
						userInfoGenderBadge.setVisibility(View.VISIBLE);
					} else {
						if (UserInfoCacheMap.get("gender-".concat(_data.get((int)_position).get("uid").toString())).toString().equals("female")) {
							userInfoGenderBadge.setImageResource(R.drawable.female_badge);
							userInfoGenderBadge.setVisibility(View.VISIBLE);
						}
					}
				}
				if (UserInfoCacheMap.get("acc_type-".concat(_data.get((int)_position).get("uid").toString())).toString().equals("admin")) {
					userInfoUsernameVerifiedBadge.setImageResource(R.drawable.admin_badge);
					userInfoUsernameVerifiedBadge.setVisibility(View.VISIBLE);
				} else {
					if (UserInfoCacheMap.get("acc_type-".concat(_data.get((int)_position).get("uid").toString())).toString().equals("moderator")) {
						userInfoUsernameVerifiedBadge.setImageResource(R.drawable.moderator_badge);
						userInfoUsernameVerifiedBadge.setVisibility(View.VISIBLE);
					} else {
						if (UserInfoCacheMap.get("acc_type-".concat(_data.get((int)_position).get("uid").toString())).toString().equals("support")) {
							userInfoUsernameVerifiedBadge.setImageResource(R.drawable.support_badge);
							userInfoUsernameVerifiedBadge.setVisibility(View.VISIBLE);
						} else {
							if (UserInfoCacheMap.get("acc_type-".concat(_data.get((int)_position).get("uid").toString())).toString().equals("user")) {
								if (UserInfoCacheMap.get("verify-".concat(_data.get((int)_position).get("uid").toString())).toString().equals("true")) {
									userInfoUsernameVerifiedBadge.setVisibility(View.VISIBLE);
								} else {
									userInfoUsernameVerifiedBadge.setVisibility(View.GONE);
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
							DatabaseReference getReference = FirebaseDatabase.getInstance().getReference().child("skyline/users").child(_data.get((int)_position).get("uid").toString());
							getReference.addListenerForSingleValueEvent(new ValueEventListener() {
								@Override
								public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
									if(dataSnapshot.exists()) {
										UserInfoCacheMap.put("uid-".concat(_data.get((int)_position).get("uid").toString()), _data.get((int)_position).get("uid").toString());
										UserInfoCacheMap.put("banned-".concat(_data.get((int)_position).get("uid").toString()), dataSnapshot.child("banned").getValue(String.class));
										UserInfoCacheMap.put("nickname-".concat(_data.get((int)_position).get("uid").toString()), dataSnapshot.child("nickname").getValue(String.class));
										UserInfoCacheMap.put("username-".concat(_data.get((int)_position).get("uid").toString()), dataSnapshot.child("username").getValue(String.class));
										UserInfoCacheMap.put("avatar-".concat(_data.get((int)_position).get("uid").toString()), dataSnapshot.child("avatar").getValue(String.class));
										UserInfoCacheMap.put("gender-".concat(_data.get((int)_position).get("uid").toString()), dataSnapshot.child("gender").getValue(String.class));
										UserInfoCacheMap.put("verify-".concat(_data.get((int)_position).get("uid").toString()), dataSnapshot.child("verify").getValue(String.class));
										UserInfoCacheMap.put("acc_type-".concat(_data.get((int)_position).get("uid").toString()), dataSnapshot.child("account_type").getValue(String.class));
										
										mMainHandler.post(new Runnable() {
											@Override
											public void run() {
												if (_data.get((int)_position).get("post_visibility").toString().equals("private")) {
													if (_data.get((int)_position).get("uid").toString().equals(FirebaseAuth.getInstance().getCurrentUser().getUid())) {
														postPrivateStateIcon.setVisibility(View.VISIBLE);
														body.setVisibility(View.VISIBLE);
													} else {
														body.setVisibility(View.GONE);
													}
												} else {
													body.setVisibility(View.VISIBLE);
													postPrivateStateIcon.setVisibility(View.GONE);
												}
												if (UserInfoCacheMap.get("banned-".concat(_data.get((int)_position).get("uid").toString())).toString().equals("true")) {
													userInfoProfileImage.setImageResource(R.drawable.banned_avatar);
												} else {
													if (UserInfoCacheMap.get("avatar-".concat(_data.get((int)_position).get("uid").toString())).toString().equals("null")) {
														userInfoProfileImage.setImageResource(R.drawable.avatar);
													} else {
														Glide.with(getApplicationContext()).load(Uri.parse(UserInfoCacheMap.get("avatar-".concat(_data.get((int)_position).get("uid").toString())).toString())).into(userInfoProfileImage);
													}
												}
												if (UserInfoCacheMap.get("nickname-".concat(_data.get((int)_position).get("uid").toString())).toString().equals("null")) {
													userInfoUsername.setText("@" + UserInfoCacheMap.get("username-".concat(_data.get((int)_position).get("uid").toString())).toString());
												} else {
													userInfoUsername.setText(UserInfoCacheMap.get("nickname-".concat(_data.get((int)_position).get("uid").toString())).toString());
												}
												if (UserInfoCacheMap.get("gender-".concat(_data.get((int)_position).get("uid").toString())).toString().equals("hidden")) {
													userInfoGenderBadge.setVisibility(View.GONE);
												} else {
													if (UserInfoCacheMap.get("gender-".concat(_data.get((int)_position).get("uid").toString())).toString().equals("male")) {
														userInfoGenderBadge.setImageResource(R.drawable.male_badge);
														userInfoGenderBadge.setVisibility(View.VISIBLE);
													} else {
														if (UserInfoCacheMap.get("gender-".concat(_data.get((int)_position).get("uid").toString())).toString().equals("female")) {
															userInfoGenderBadge.setImageResource(R.drawable.female_badge);
															userInfoGenderBadge.setVisibility(View.VISIBLE);
														}
													}
												}
												if (UserInfoCacheMap.get("acc_type-".concat(_data.get((int)_position).get("uid").toString())).toString().equals("admin")) {
													userInfoUsernameVerifiedBadge.setImageResource(R.drawable.admin_badge);
													userInfoUsernameVerifiedBadge.setVisibility(View.VISIBLE);
												} else {
													if (UserInfoCacheMap.get("acc_type-".concat(_data.get((int)_position).get("uid").toString())).toString().equals("moderator")) {
														userInfoUsernameVerifiedBadge.setImageResource(R.drawable.moderator_badge);
														userInfoUsernameVerifiedBadge.setVisibility(View.VISIBLE);
													} else {
														if (UserInfoCacheMap.get("acc_type-".concat(_data.get((int)_position).get("uid").toString())).toString().equals("support")) {
															userInfoUsernameVerifiedBadge.setImageResource(R.drawable.support_badge);
															userInfoUsernameVerifiedBadge.setVisibility(View.VISIBLE);
														} else {
															if (UserInfoCacheMap.get("acc_type-".concat(_data.get((int)_position).get("uid").toString())).toString().equals("user")) {
																if (UserInfoCacheMap.get("verify-".concat(_data.get((int)_position).get("uid").toString())).toString().equals("true")) {
																	userInfoUsernameVerifiedBadge.setVisibility(View.VISIBLE);
																} else {
																	userInfoUsernameVerifiedBadge.setVisibility(View.GONE);
																}
															}
														}
													}
												}
												
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
			DatabaseReference getLikeCheck = FirebaseDatabase.getInstance().getReference("skyline/posts-likes").child(_data.get((int)_position).get("key").toString()).child(FirebaseAuth.getInstance().getCurrentUser().getUid());
			DatabaseReference getCommentsCount = FirebaseDatabase.getInstance().getReference("skyline/posts-comments").child(_data.get((int)_position).get("key").toString());
			DatabaseReference getLikesCount = FirebaseDatabase.getInstance().getReference("skyline/posts-likes").child(_data.get((int)_position).get("key").toString());
			DatabaseReference getFavoriteCheck = FirebaseDatabase.getInstance().getReference("skyline/favorite-posts").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(_data.get((int)_position).get("key").toString());
			
			getLikeCheck.addListenerForSingleValueEvent(new ValueEventListener() {
				@Override
				public void onDataChange(@NonNull DataSnapshot dataSnapshot) { 
					if(dataSnapshot.exists()) {
						likeButtonIc.setImageResource(R.drawable.post_icons_1_2);
					} else {
						likeButtonIc.setImageResource(R.drawable.post_icons_1_1);
					}
				}
				
				@Override
				public void onCancelled(@NonNull DatabaseError databaseError) {
					
				}
			});
			getCommentsCount.addListenerForSingleValueEvent(new ValueEventListener() {
				@Override
				public void onDataChange(DataSnapshot dataSnapshot) {
					long count = dataSnapshot.getChildrenCount();
					_setCount(commentsButtonCount, count);
				}
				
				@Override
				public void onCancelled(DatabaseError databaseError) {
					
				}
			});
			getLikesCount.addListenerForSingleValueEvent(new ValueEventListener() {
				@Override
				public void onDataChange(DataSnapshot dataSnapshot) {
					long count = dataSnapshot.getChildrenCount();
					_setCount(likeButtonCount, count);
					postLikeCountCache.put(_data.get((int)_position).get("key").toString(), String.valueOf((long)(count)));
				}
				
				@Override
				public void onCancelled(DatabaseError databaseError) {
					
				}
			});
			getFavoriteCheck.addListenerForSingleValueEvent(new ValueEventListener() {
				@Override
				public void onDataChange(@NonNull DataSnapshot dataSnapshot) { 
					if(dataSnapshot.exists()) {
						favoritePostButton.setImageResource(R.drawable.delete_favorite_post_ic);
					} else {
						favoritePostButton.setImageResource(R.drawable.add_favorite_post_ic);
					}
				}
				
				@Override
				public void onCancelled(@NonNull DatabaseError databaseError) {
					
				}
			});
			likeButton.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View _view) {
					DatabaseReference getLikeCheck = FirebaseDatabase.getInstance().getReference("skyline/posts-likes").child(_data.get((int)_position).get("key").toString()).child(FirebaseAuth.getInstance().getCurrentUser().getUid());
					getLikeCheck.addListenerForSingleValueEvent(new ValueEventListener() {
						@Override
						public void onDataChange(@NonNull DataSnapshot dataSnapshot) { 
							if(dataSnapshot.exists()) {
								getLikeCheck.removeValue();
								postLikeCountCache.put(_data.get((int)_position).get("key").toString(), String.valueOf((long)(Double.parseDouble(postLikeCountCache.get(_data.get((int)_position).get("key").toString()).toString()) - 1)));
								_setCount(likeButtonCount, Double.parseDouble(postLikeCountCache.get(_data.get((int)_position).get("key").toString()).toString()));
								likeButtonIc.setImageResource(R.drawable.post_icons_1_1);
							} else {
								getLikeCheck.setValue(FirebaseAuth.getInstance().getCurrentUser().getUid());
								postLikeCountCache.put(_data.get((int)_position).get("key").toString(), String.valueOf((long)(Double.parseDouble(postLikeCountCache.get(_data.get((int)_position).get("key").toString()).toString()) + 1)));
								_setCount(likeButtonCount, Double.parseDouble(postLikeCountCache.get(_data.get((int)_position).get("key").toString()).toString()));
								likeButtonIc.setImageResource(R.drawable.post_icons_1_2);
							}
						}
						
						@Override
						public void onCancelled(@NonNull DatabaseError databaseError) {
							
						}
					});
					vbr.vibrate((long)(24));
				}
			});
			commentsButton.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View _view) {
					Bundle sendPostKey = new Bundle();
					sendPostKey.putString("postKey", _data.get((int)_position).get("key").toString());
					sendPostKey.putString("postPublisherUID", _data.get((int)_position).get("uid").toString());
					sendPostKey.putString("postPublisherAvatar", UserInfoCacheMap.get("avatar-".concat(_data.get((int)_position).get("uid").toString())).toString());
					PostCommentsBottomSheetDialog postCommentsBottomSheet = new PostCommentsBottomSheetDialog();
					postCommentsBottomSheet.setArguments(sendPostKey);
					postCommentsBottomSheet.show(getSupportFragmentManager(), postCommentsBottomSheet.getTag());
				}
			});
			userInfo.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View _view) {
					intent.setClass(getApplicationContext(), ProfileActivity.class);
					intent.putExtra("uid", _data.get((int)_position).get("uid").toString());
					startActivity(intent);
				}
			});
			favoritePostButton.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View _view) {
					DatabaseReference getFavoriteCheck = FirebaseDatabase.getInstance().getReference("skyline/favorite-posts").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(_data.get((int)_position).get("key").toString());
					getFavoriteCheck.addListenerForSingleValueEvent(new ValueEventListener() {
						@Override
						public void onDataChange(@NonNull DataSnapshot dataSnapshot) { 
							if(dataSnapshot.exists()) {
								getFavoriteCheck.removeValue();
								favoritePostButton.setImageResource(R.drawable.add_favorite_post_ic);
							} else {
								getFavoriteCheck.setValue(_data.get((int)_position).get("key").toString());
								favoritePostButton.setImageResource(R.drawable.delete_favorite_post_ic);
							}
						}
						
						@Override
						public void onCancelled(@NonNull DatabaseError databaseError) {
							
						}
					});
					vbr.vibrate((long)(24));
				}
			});
			topMoreButton.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View _view) {
					Bundle sendPostKey = new Bundle();
					sendPostKey.putString("postKey", _data.get((int)_position).get("key").toString());
					sendPostKey.putString("postPublisherUID", _data.get((int)_position).get("uid").toString());
					sendPostKey.putString("postType", _data.get((int)_position).get("post_type").toString());
					
					if (_data.get((int)_position).containsKey("post_image") && !_data.get((int)_position).get("post_image").toString().isEmpty()) {
						sendPostKey.putString("postImg", _data.get((int)_position).get("post_image").toString());
					}
					
					PostMoreBottomSheetDialog postMoreBottomSheetDialog = new PostMoreBottomSheetDialog();
					postMoreBottomSheetDialog.setArguments(sendPostKey);
					postMoreBottomSheetDialog.show(getSupportFragmentManager(), postMoreBottomSheetDialog.getTag());
				}
			});
			userInfoProfileCard.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b) { this.setCornerRadius(a); this.setColor(b); return this; } }.getIns((int)300, Color.TRANSPARENT));
			likeButton.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b, int c, int d) { this.setCornerRadius(a); this.setStroke(b, c); this.setColor(d); return this; } }.getIns((int)300, (int)1, 0xFFE0E0E0, 0xFFF5F5F5));
			commentsButton.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b, int c, int d) { this.setCornerRadius(a); this.setStroke(b, c); this.setColor(d); return this; } }.getIns((int)300, (int)1, 0xFFE0E0E0, 0xFFF5F5F5));
			try{
				postMessageTextMiddle.setOnLongClickListener(new View.OnLongClickListener() {
					@Override
					public boolean onLongClick(View _view) {
						((ClipboardManager) getSystemService(getApplicationContext().CLIPBOARD_SERVICE)).setPrimaryClip(ClipData.newPlainText("clipboard", _data.get((int)_position).get("post_text").toString()));
						return true;
					}
				});
			}catch(Exception e){
				
			}
			userInfoProfileImage.setOnLongClickListener(new View.OnLongClickListener() {
				@Override
				public boolean onLongClick(View _view) {
					PostVideoPlayer.setVisibility(View.VISIBLE);
					PostVideoPlayer.setVideoURI(Uri.parse(_data.get((int)_position).get("post_text").toString()));
					PostVideoPlayer.start();
					return true;
				}
			});
			postImage.setOnLongClickListener(new View.OnLongClickListener() {
				@Override
				public boolean onLongClick(View _view) {
					_OpenWebView(_data.get((int)_position).get("post_image").toString());
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
	
	public class PublicPostsListAdapter extends RecyclerView.Adapter<PublicPostsListAdapter.ViewHolder> {
		
		ArrayList<HashMap<String, Object>> _data;
		
		public PublicPostsListAdapter(ArrayList<HashMap<String, Object>> _arr) {
			_data = _arr;
		}
		
		@Override
		public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
			LayoutInflater _inflater = getLayoutInflater();
			View _v = _inflater.inflate(R.layout.synapse_post_cv, null);
			RecyclerView.LayoutParams _lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
			_v.setLayoutParams(_lp);
			return new ViewHolder(_v);
		}
		
		@Override
		public void onBindViewHolder(ViewHolder _holder, final int _position) {
			View _view = _holder.itemView;
			
			final LinearLayout body = _view.findViewById(R.id.body);
			final LinearLayout top = _view.findViewById(R.id.top);
			final LinearLayout linear1 = _view.findViewById(R.id.linear1);
			final LinearLayout middle = _view.findViewById(R.id.middle);
			final LinearLayout bottom = _view.findViewById(R.id.bottom);
			final LinearLayout userInfo = _view.findViewById(R.id.userInfo);
			final LinearLayout topSpace = _view.findViewById(R.id.topSpace);
			final ImageView topMoreButton = _view.findViewById(R.id.topMoreButton);
			final androidx.cardview.widget.CardView userInfoProfileCard = _view.findViewById(R.id.userInfoProfileCard);
			final LinearLayout userInfoRight = _view.findViewById(R.id.userInfoRight);
			final ImageView userInfoProfileImage = _view.findViewById(R.id.userInfoProfileImage);
			final LinearLayout userInfoUsernameRightTop = _view.findViewById(R.id.userInfoUsernameRightTop);
			final LinearLayout btmPost = _view.findViewById(R.id.btmPost);
			final TextView userInfoUsername = _view.findViewById(R.id.userInfoUsername);
			final ImageView userInfoGenderBadge = _view.findViewById(R.id.userInfoGenderBadge);
			final ImageView userInfoUsernameVerifiedBadge = _view.findViewById(R.id.userInfoUsernameVerifiedBadge);
			final TextView postPublishDate = _view.findViewById(R.id.postPublishDate);
			final ImageView postPrivateStateIcon = _view.findViewById(R.id.postPrivateStateIcon);
			final TextView postMessageTextMiddle = _view.findViewById(R.id.postMessageTextMiddle);
			final ImageView postImage = _view.findViewById(R.id.postImage);
			final WebView linkprewebview = _view.findViewById(R.id.linkprewebview);
			final VideoView PostVideoPlayer = _view.findViewById(R.id.PostVideoPlayer);
			final LinearLayout likeButton = _view.findViewById(R.id.likeButton);
			final LinearLayout commentsButton = _view.findViewById(R.id.commentsButton);
			final LinearLayout shareButton = _view.findViewById(R.id.shareButton);
			final LinearLayout bottomSpc = _view.findViewById(R.id.bottomSpc);
			final ImageView favoritePostButton = _view.findViewById(R.id.favoritePostButton);
			final ImageView likeButtonIc = _view.findViewById(R.id.likeButtonIc);
			final TextView likeButtonCount = _view.findViewById(R.id.likeButtonCount);
			final TextView textview1 = _view.findViewById(R.id.textview1);
			final TextView tv_1 = _view.findViewById(R.id.tv_1);
			final ImageView commentsButtonIc = _view.findViewById(R.id.commentsButtonIc);
			final TextView commentsButtonCount = _view.findViewById(R.id.commentsButtonCount);
			final TextView textview2 = _view.findViewById(R.id.textview2);
			final TextView tv_2 = _view.findViewById(R.id.tv_2);
			final ImageView shareButtonIc = _view.findViewById(R.id.shareButtonIc);
			final TextView shareButtonCount = _view.findViewById(R.id.shareButtonCount);
			
			RecyclerView.LayoutParams _lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
			_view.setLayoutParams(_lp);
			body.setVisibility(View.GONE);
			userInfoProfileCard.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b) { this.setCornerRadius(a); this.setColor(b); return this; } }.getIns((int)300, Color.TRANSPARENT));
			_ImageColor(postPrivateStateIcon, 0xFF616161);
			_viewGraphics(topMoreButton, 0xFFFFFFFF, 0xFFEEEEEE, 300, 0, Color.TRANSPARENT);
			if (_data.get((int)_position).get("post_type").toString().equals("TEXT") || (_data.get((int)_position).get("post_type").toString().equals("IMAGE") || _data.get((int)_position).get("post_type").toString().equals("VIDEO"))) {
				if (_data.get((int)_position).containsKey("post_text")) {
					// Comment out or remove the old line
					// _textview_mh(postMessageTextMiddle, _data.get((int)_position).get("post_text").toString());
					
					// Get the post text content
					String postText = _data.get((int)_position).get("post_text").toString();
					
					// Initialize TextStylingUtil with the Activity Context from the TextView
					// This is crucial for clickable spans (mentions, links, spoilers) to work correctly
					TextStylingUtil textStylingUtil = new TextStylingUtil(postMessageTextMiddle.getContext());
					
					// Apply the styling to the TextView
					textStylingUtil.applyStyling(postText, postMessageTextMiddle);
					
					postMessageTextMiddle.setVisibility(View.VISIBLE);
				} else {
					postMessageTextMiddle.setVisibility(View.GONE);
				}
				if (_data.get((int)_position).containsKey("post_image")) {
					// Keep Glide using getApplicationContext() or postImage.getContext() if preferred
					// getApplicationContext() is generally safe for Glide loading
					Glide.with(getApplicationContext()).load(Uri.parse(_data.get((int)_position).get("post_image").toString())).into(postImage);
					postImage.setOnClickListener(new View.OnClickListener() {
						@Override
						public void onClick(View _view) {
							_OpenWebView(_data.get((int)_position).get("post_image").toString());
						}
					});
					postImage.setVisibility(View.VISIBLE);
				} else {
					postImage.setVisibility(View.GONE);
				}
			} else {
				postImage.setVisibility(View.GONE);
				postMessageTextMiddle.setVisibility(View.GONE);
			}
			if (_data.get((int)_position).get("post_hide_like_count").toString().equals("true")) {
				likeButtonCount.setVisibility(View.GONE);
			} else {
				likeButtonCount.setVisibility(View.VISIBLE);
			}
			if (_data.get((int)_position).get("post_hide_comments_count").toString().equals("true")) {
				commentsButtonCount.setVisibility(View.GONE);
			} else {
				commentsButtonCount.setVisibility(View.VISIBLE);
			}
			if (_data.get((int)_position).get("post_disable_comments").toString().equals("true")) {
				commentsButton.setVisibility(View.GONE);
			} else {
				commentsButton.setVisibility(View.VISIBLE);
			}
			_setTime(Double.parseDouble(_data.get((int)_position).get("publish_date").toString()), postPublishDate);
			if (UserInfoCacheMap.containsKey("uid-".concat(_data.get((int)_position).get("uid").toString()))) {
				if (_data.get((int)_position).get("post_visibility").toString().equals("private")) {
					if (_data.get((int)_position).get("uid").toString().equals(FirebaseAuth.getInstance().getCurrentUser().getUid())) {
						postPrivateStateIcon.setVisibility(View.VISIBLE);
						body.setVisibility(View.VISIBLE);
					} else {
						body.setVisibility(View.GONE);
					}
				} else {
					body.setVisibility(View.VISIBLE);
					postPrivateStateIcon.setVisibility(View.GONE);
				}
				if (UserInfoCacheMap.get("banned-".concat(_data.get((int)_position).get("uid").toString())).toString().equals("true")) {
					userInfoProfileImage.setImageResource(R.drawable.banned_avatar);
				} else {
					if (UserInfoCacheMap.get("avatar-".concat(_data.get((int)_position).get("uid").toString())).toString().equals("null")) {
						userInfoProfileImage.setImageResource(R.drawable.avatar);
					} else {
						Glide.with(getApplicationContext()).load(Uri.parse(UserInfoCacheMap.get("avatar-".concat(_data.get((int)_position).get("uid").toString())).toString())).into(userInfoProfileImage);
					}
				}
				if (UserInfoCacheMap.get("nickname-".concat(_data.get((int)_position).get("uid").toString())).toString().equals("null")) {
					userInfoUsername.setText("@" + UserInfoCacheMap.get("username-".concat(_data.get((int)_position).get("uid").toString())).toString());
				} else {
					userInfoUsername.setText(UserInfoCacheMap.get("nickname-".concat(_data.get((int)_position).get("uid").toString())).toString());
				}
				if (UserInfoCacheMap.get("gender-".concat(_data.get((int)_position).get("uid").toString())).toString().equals("hidden")) {
					userInfoGenderBadge.setVisibility(View.GONE);
				} else {
					if (UserInfoCacheMap.get("gender-".concat(_data.get((int)_position).get("uid").toString())).toString().equals("male")) {
						userInfoGenderBadge.setImageResource(R.drawable.male_badge);
						userInfoGenderBadge.setVisibility(View.VISIBLE);
					} else {
						if (UserInfoCacheMap.get("gender-".concat(_data.get((int)_position).get("uid").toString())).toString().equals("female")) {
							userInfoGenderBadge.setImageResource(R.drawable.female_badge);
							userInfoGenderBadge.setVisibility(View.VISIBLE);
						}
					}
				}
				if (UserInfoCacheMap.get("acc_type-".concat(_data.get((int)_position).get("uid").toString())).toString().equals("admin")) {
					userInfoUsernameVerifiedBadge.setImageResource(R.drawable.admin_badge);
					userInfoUsernameVerifiedBadge.setVisibility(View.VISIBLE);
				} else {
					if (UserInfoCacheMap.get("acc_type-".concat(_data.get((int)_position).get("uid").toString())).toString().equals("moderator")) {
						userInfoUsernameVerifiedBadge.setImageResource(R.drawable.moderator_badge);
						userInfoUsernameVerifiedBadge.setVisibility(View.VISIBLE);
					} else {
						if (UserInfoCacheMap.get("acc_type-".concat(_data.get((int)_position).get("uid").toString())).toString().equals("support")) {
							userInfoUsernameVerifiedBadge.setImageResource(R.drawable.support_badge);
							userInfoUsernameVerifiedBadge.setVisibility(View.VISIBLE);
						} else {
							if (UserInfoCacheMap.get("acc_type-".concat(_data.get((int)_position).get("uid").toString())).toString().equals("user")) {
								if (UserInfoCacheMap.get("verify-".concat(_data.get((int)_position).get("uid").toString())).toString().equals("true")) {
									userInfoUsernameVerifiedBadge.setVisibility(View.VISIBLE);
								} else {
									userInfoUsernameVerifiedBadge.setVisibility(View.GONE);
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
							DatabaseReference getReference = FirebaseDatabase.getInstance().getReference().child("skyline/users").child(_data.get((int)_position).get("uid").toString());
							getReference.addListenerForSingleValueEvent(new ValueEventListener() {
								@Override
								public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
									if(dataSnapshot.exists()) {
										UserInfoCacheMap.put("uid-".concat(_data.get((int)_position).get("uid").toString()), _data.get((int)_position).get("uid").toString());
										UserInfoCacheMap.put("banned-".concat(_data.get((int)_position).get("uid").toString()), dataSnapshot.child("banned").getValue(String.class));
										UserInfoCacheMap.put("nickname-".concat(_data.get((int)_position).get("uid").toString()), dataSnapshot.child("nickname").getValue(String.class));
										UserInfoCacheMap.put("username-".concat(_data.get((int)_position).get("uid").toString()), dataSnapshot.child("username").getValue(String.class));
										UserInfoCacheMap.put("avatar-".concat(_data.get((int)_position).get("uid").toString()), dataSnapshot.child("avatar").getValue(String.class));
										UserInfoCacheMap.put("gender-".concat(_data.get((int)_position).get("uid").toString()), dataSnapshot.child("gender").getValue(String.class));
										UserInfoCacheMap.put("verify-".concat(_data.get((int)_position).get("uid").toString()), dataSnapshot.child("verify").getValue(String.class));
										UserInfoCacheMap.put("acc_type-".concat(_data.get((int)_position).get("uid").toString()), dataSnapshot.child("account_type").getValue(String.class));
										
										mMainHandler.post(new Runnable() {
											@Override
											public void run() {
												if (_data.get((int)_position).get("post_visibility").toString().equals("private")) {
													if (_data.get((int)_position).get("uid").toString().equals(FirebaseAuth.getInstance().getCurrentUser().getUid())) {
														postPrivateStateIcon.setVisibility(View.VISIBLE);
														body.setVisibility(View.VISIBLE);
													} else {
														body.setVisibility(View.GONE);
													}
												} else {
													body.setVisibility(View.VISIBLE);
													postPrivateStateIcon.setVisibility(View.GONE);
												}
												if (UserInfoCacheMap.get("banned-".concat(_data.get((int)_position).get("uid").toString())).toString().equals("true")) {
													userInfoProfileImage.setImageResource(R.drawable.banned_avatar);
												} else {
													if (UserInfoCacheMap.get("avatar-".concat(_data.get((int)_position).get("uid").toString())).toString().equals("null")) {
														userInfoProfileImage.setImageResource(R.drawable.avatar);
													} else {
														Glide.with(getApplicationContext()).load(Uri.parse(UserInfoCacheMap.get("avatar-".concat(_data.get((int)_position).get("uid").toString())).toString())).into(userInfoProfileImage);
													}
												}
												if (UserInfoCacheMap.get("nickname-".concat(_data.get((int)_position).get("uid").toString())).toString().equals("null")) {
													userInfoUsername.setText("@" + UserInfoCacheMap.get("username-".concat(_data.get((int)_position).get("uid").toString())).toString());
												} else {
													userInfoUsername.setText(UserInfoCacheMap.get("nickname-".concat(_data.get((int)_position).get("uid").toString())).toString());
												}
												if (UserInfoCacheMap.get("gender-".concat(_data.get((int)_position).get("uid").toString())).toString().equals("hidden")) {
													userInfoGenderBadge.setVisibility(View.GONE);
												} else {
													if (UserInfoCacheMap.get("gender-".concat(_data.get((int)_position).get("uid").toString())).toString().equals("male")) {
														userInfoGenderBadge.setImageResource(R.drawable.male_badge);
														userInfoGenderBadge.setVisibility(View.VISIBLE);
													} else {
														if (UserInfoCacheMap.get("gender-".concat(_data.get((int)_position).get("uid").toString())).toString().equals("female")) {
															userInfoGenderBadge.setImageResource(R.drawable.female_badge);
															userInfoGenderBadge.setVisibility(View.VISIBLE);
														}
													}
												}
												if (UserInfoCacheMap.get("acc_type-".concat(_data.get((int)_position).get("uid").toString())).toString().equals("admin")) {
													userInfoUsernameVerifiedBadge.setImageResource(R.drawable.admin_badge);
													userInfoUsernameVerifiedBadge.setVisibility(View.VISIBLE);
												} else {
													if (UserInfoCacheMap.get("acc_type-".concat(_data.get((int)_position).get("uid").toString())).toString().equals("moderator")) {
														userInfoUsernameVerifiedBadge.setImageResource(R.drawable.moderator_badge);
														userInfoUsernameVerifiedBadge.setVisibility(View.VISIBLE);
													} else {
														if (UserInfoCacheMap.get("acc_type-".concat(_data.get((int)_position).get("uid").toString())).toString().equals("support")) {
															userInfoUsernameVerifiedBadge.setImageResource(R.drawable.support_badge);
															userInfoUsernameVerifiedBadge.setVisibility(View.VISIBLE);
														} else {
															if (UserInfoCacheMap.get("acc_type-".concat(_data.get((int)_position).get("uid").toString())).toString().equals("user")) {
																if (UserInfoCacheMap.get("verify-".concat(_data.get((int)_position).get("uid").toString())).toString().equals("true")) {
																	userInfoUsernameVerifiedBadge.setVisibility(View.VISIBLE);
																} else {
																	userInfoUsernameVerifiedBadge.setVisibility(View.GONE);
																}
															}
														}
													}
												}
												
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
			DatabaseReference getLikeCheck = FirebaseDatabase.getInstance().getReference("skyline/posts-likes").child(_data.get((int)_position).get("key").toString()).child(FirebaseAuth.getInstance().getCurrentUser().getUid());
			DatabaseReference getCommentsCount = FirebaseDatabase.getInstance().getReference("skyline/posts-comments").child(_data.get((int)_position).get("key").toString());
			DatabaseReference getLikesCount = FirebaseDatabase.getInstance().getReference("skyline/posts-likes").child(_data.get((int)_position).get("key").toString());
			DatabaseReference getFavoriteCheck = FirebaseDatabase.getInstance().getReference("skyline/favorite-posts").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(_data.get((int)_position).get("key").toString());
			
			getLikeCheck.addListenerForSingleValueEvent(new ValueEventListener() {
				@Override
				public void onDataChange(@NonNull DataSnapshot dataSnapshot) { 
					if(dataSnapshot.exists()) {
						likeButtonIc.setImageResource(R.drawable.post_icons_1_2);
					} else {
						likeButtonIc.setImageResource(R.drawable.post_icons_1_1);
					}
				}
				
				@Override
				public void onCancelled(@NonNull DatabaseError databaseError) {
					
				}
			});
			getCommentsCount.addListenerForSingleValueEvent(new ValueEventListener() {
				@Override
				public void onDataChange(DataSnapshot dataSnapshot) {
					long count = dataSnapshot.getChildrenCount();
					_setCount(commentsButtonCount, count);
				}
				
				@Override
				public void onCancelled(DatabaseError databaseError) {
					
				}
			});
			getLikesCount.addListenerForSingleValueEvent(new ValueEventListener() {
				@Override
				public void onDataChange(DataSnapshot dataSnapshot) {
					long count = dataSnapshot.getChildrenCount();
					_setCount(likeButtonCount, count);
					postLikeCountCache.put(_data.get((int)_position).get("key").toString(), String.valueOf((long)(count)));
				}
				
				@Override
				public void onCancelled(DatabaseError databaseError) {
					
				}
			});
			getFavoriteCheck.addListenerForSingleValueEvent(new ValueEventListener() {
				@Override
				public void onDataChange(@NonNull DataSnapshot dataSnapshot) { 
					if(dataSnapshot.exists()) {
						favoritePostButton.setImageResource(R.drawable.delete_favorite_post_ic);
					} else {
						favoritePostButton.setImageResource(R.drawable.add_favorite_post_ic);
					}
				}
				
				@Override
				public void onCancelled(@NonNull DatabaseError databaseError) {
					
				}
			});
			likeButton.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View _view) {
					DatabaseReference getLikeCheck = FirebaseDatabase.getInstance().getReference("skyline/posts-likes").child(_data.get((int)_position).get("key").toString()).child(FirebaseAuth.getInstance().getCurrentUser().getUid());
					getLikeCheck.addListenerForSingleValueEvent(new ValueEventListener() {
						@Override
						public void onDataChange(@NonNull DataSnapshot dataSnapshot) { 
							if(dataSnapshot.exists()) {
								getLikeCheck.removeValue();
								postLikeCountCache.put(_data.get((int)_position).get("key").toString(), String.valueOf((long)(Double.parseDouble(postLikeCountCache.get(_data.get((int)_position).get("key").toString()).toString()) - 1)));
								_setCount(likeButtonCount, Double.parseDouble(postLikeCountCache.get(_data.get((int)_position).get("key").toString()).toString()));
								likeButtonIc.setImageResource(R.drawable.post_icons_1_1);
							} else {
								getLikeCheck.setValue(FirebaseAuth.getInstance().getCurrentUser().getUid());
								postLikeCountCache.put(_data.get((int)_position).get("key").toString(), String.valueOf((long)(Double.parseDouble(postLikeCountCache.get(_data.get((int)_position).get("key").toString()).toString()) + 1)));
								_setCount(likeButtonCount, Double.parseDouble(postLikeCountCache.get(_data.get((int)_position).get("key").toString()).toString()));
								likeButtonIc.setImageResource(R.drawable.post_icons_1_2);
							}
						}
						
						@Override
						public void onCancelled(@NonNull DatabaseError databaseError) {
							
						}
					});
					vbr.vibrate((long)(24));
				}
			});
			commentsButton.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View _view) {
					Bundle sendPostKey = new Bundle();
					sendPostKey.putString("postKey", _data.get((int)_position).get("key").toString());
					sendPostKey.putString("postPublisherUID", _data.get((int)_position).get("uid").toString());
					sendPostKey.putString("postPublisherAvatar", UserInfoCacheMap.get("avatar-".concat(_data.get((int)_position).get("uid").toString())).toString());
					PostCommentsBottomSheetDialog postCommentsBottomSheet = new PostCommentsBottomSheetDialog();
					postCommentsBottomSheet.setArguments(sendPostKey);
					postCommentsBottomSheet.show(getSupportFragmentManager(), postCommentsBottomSheet.getTag());
				}
			});
			userInfo.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View _view) {
					intent.setClass(getApplicationContext(), ProfileActivity.class);
					intent.putExtra("uid", _data.get((int)_position).get("uid").toString());
					startActivity(intent);
				}
			});
			favoritePostButton.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View _view) {
					DatabaseReference getFavoriteCheck = FirebaseDatabase.getInstance().getReference("skyline/favorite-posts").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(_data.get((int)_position).get("key").toString());
					getFavoriteCheck.addListenerForSingleValueEvent(new ValueEventListener() {
						@Override
						public void onDataChange(@NonNull DataSnapshot dataSnapshot) { 
							if(dataSnapshot.exists()) {
								getFavoriteCheck.removeValue();
								favoritePostButton.setImageResource(R.drawable.add_favorite_post_ic);
							} else {
								getFavoriteCheck.setValue(_data.get((int)_position).get("key").toString());
								favoritePostButton.setImageResource(R.drawable.delete_favorite_post_ic);
							}
						}
						
						@Override
						public void onCancelled(@NonNull DatabaseError databaseError) {
							
						}
					});
					vbr.vibrate((long)(24));
				}
			});
			topMoreButton.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View _view) {
					Bundle sendPostKey = new Bundle();
					sendPostKey.putString("postKey", _data.get((int)_position).get("key").toString());
					sendPostKey.putString("postPublisherUID", _data.get((int)_position).get("uid").toString());
					sendPostKey.putString("postType", _data.get((int)_position).get("post_type").toString());
					
					if (_data.get((int)_position).containsKey("post_image") && !_data.get((int)_position).get("post_image").toString().isEmpty()) {
						sendPostKey.putString("postImg", _data.get((int)_position).get("post_image").toString());
					}
					
					PostMoreBottomSheetDialog postMoreBottomSheetDialog = new PostMoreBottomSheetDialog();
					postMoreBottomSheetDialog.setArguments(sendPostKey);
					postMoreBottomSheetDialog.show(getSupportFragmentManager(), postMoreBottomSheetDialog.getTag());
				}
			});
			likeButton.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b, int c, int d) { this.setCornerRadius(a); this.setStroke(b, c); this.setColor(d); return this; } }.getIns((int)300, (int)1, 0xFFE0E0E0, 0xFFF5F5F5));
			commentsButton.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b, int c, int d) { this.setCornerRadius(a); this.setStroke(b, c); this.setColor(d); return this; } }.getIns((int)300, (int)1, 0xFFE0E0E0, 0xFFF5F5F5));
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
	
	public class FollowedUsersPostsListAdapter extends RecyclerView.Adapter<FollowedUsersPostsListAdapter.ViewHolder> {
		
		ArrayList<HashMap<String, Object>> _data;
		
		public FollowedUsersPostsListAdapter(ArrayList<HashMap<String, Object>> _arr) {
			_data = _arr;
		}
		
		@Override
		public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
			LayoutInflater _inflater = getLayoutInflater();
			View _v = _inflater.inflate(R.layout.synapse_post_cv, null);
			RecyclerView.LayoutParams _lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
			_v.setLayoutParams(_lp);
			return new ViewHolder(_v);
		}
		
		@Override
		public void onBindViewHolder(ViewHolder _holder, final int _position) {
			View _view = _holder.itemView;
			
			final LinearLayout body = _view.findViewById(R.id.body);
			final LinearLayout top = _view.findViewById(R.id.top);
			final LinearLayout linear1 = _view.findViewById(R.id.linear1);
			final LinearLayout middle = _view.findViewById(R.id.middle);
			final LinearLayout bottom = _view.findViewById(R.id.bottom);
			final LinearLayout userInfo = _view.findViewById(R.id.userInfo);
			final LinearLayout topSpace = _view.findViewById(R.id.topSpace);
			final ImageView topMoreButton = _view.findViewById(R.id.topMoreButton);
			final androidx.cardview.widget.CardView userInfoProfileCard = _view.findViewById(R.id.userInfoProfileCard);
			final LinearLayout userInfoRight = _view.findViewById(R.id.userInfoRight);
			final ImageView userInfoProfileImage = _view.findViewById(R.id.userInfoProfileImage);
			final LinearLayout userInfoUsernameRightTop = _view.findViewById(R.id.userInfoUsernameRightTop);
			final LinearLayout btmPost = _view.findViewById(R.id.btmPost);
			final TextView userInfoUsername = _view.findViewById(R.id.userInfoUsername);
			final ImageView userInfoGenderBadge = _view.findViewById(R.id.userInfoGenderBadge);
			final ImageView userInfoUsernameVerifiedBadge = _view.findViewById(R.id.userInfoUsernameVerifiedBadge);
			final TextView postPublishDate = _view.findViewById(R.id.postPublishDate);
			final ImageView postPrivateStateIcon = _view.findViewById(R.id.postPrivateStateIcon);
			final TextView postMessageTextMiddle = _view.findViewById(R.id.postMessageTextMiddle);
			final ImageView postImage = _view.findViewById(R.id.postImage);
			final WebView linkprewebview = _view.findViewById(R.id.linkprewebview);
			final VideoView PostVideoPlayer = _view.findViewById(R.id.PostVideoPlayer);
			final LinearLayout likeButton = _view.findViewById(R.id.likeButton);
			final LinearLayout commentsButton = _view.findViewById(R.id.commentsButton);
			final LinearLayout shareButton = _view.findViewById(R.id.shareButton);
			final LinearLayout bottomSpc = _view.findViewById(R.id.bottomSpc);
			final ImageView favoritePostButton = _view.findViewById(R.id.favoritePostButton);
			final ImageView likeButtonIc = _view.findViewById(R.id.likeButtonIc);
			final TextView likeButtonCount = _view.findViewById(R.id.likeButtonCount);
			final TextView textview1 = _view.findViewById(R.id.textview1);
			final TextView tv_1 = _view.findViewById(R.id.tv_1);
			final ImageView commentsButtonIc = _view.findViewById(R.id.commentsButtonIc);
			final TextView commentsButtonCount = _view.findViewById(R.id.commentsButtonCount);
			final TextView textview2 = _view.findViewById(R.id.textview2);
			final TextView tv_2 = _view.findViewById(R.id.tv_2);
			final ImageView shareButtonIc = _view.findViewById(R.id.shareButtonIc);
			final TextView shareButtonCount = _view.findViewById(R.id.shareButtonCount);
			
			RecyclerView.LayoutParams _lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
			_view.setLayoutParams(_lp);
			body.setVisibility(View.GONE);
			userInfoProfileCard.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b) { this.setCornerRadius(a); this.setColor(b); return this; } }.getIns((int)300, Color.TRANSPARENT));
			_ImageColor(postPrivateStateIcon, 0xFF616161);
			_viewGraphics(topMoreButton, 0xFFFFFFFF, 0xFFEEEEEE, 300, 0, Color.TRANSPARENT);
			if (_data.get((int)_position).get("post_type").toString().equals("TEXT") || (_data.get((int)_position).get("post_type").toString().equals("IMAGE") || _data.get((int)_position).get("post_type").toString().equals("VIDEO"))) {
				if (_data.get((int)_position).containsKey("post_text")) {
					_textview_mh(postMessageTextMiddle, _data.get((int)_position).get("post_text").toString());
					//	postMessageTextMiddle.setText(_data.get((int)_position).get("post_text").toString());
					postMessageTextMiddle.setVisibility(View.VISIBLE);
				} else {
					postMessageTextMiddle.setVisibility(View.GONE);
				}
				if (_data.get((int)_position).containsKey("post_image")) {
					Glide.with(getApplicationContext()).load(Uri.parse(_data.get((int)_position).get("post_image").toString())).into(postImage);
					postImage.setVisibility(View.VISIBLE);
				} else {
					postImage.setVisibility(View.GONE);
				}
			} else {
				postImage.setVisibility(View.GONE);
				postMessageTextMiddle.setVisibility(View.GONE);
			}
			
			if (_data.get((int)_position).get("post_hide_like_count").toString().equals("true")) {
				likeButtonCount.setVisibility(View.GONE);
			} else {
				likeButtonCount.setVisibility(View.VISIBLE);
			}
			if (_data.get((int)_position).get("post_hide_comments_count").toString().equals("true")) {
				commentsButtonCount.setVisibility(View.GONE);
			} else {
				commentsButtonCount.setVisibility(View.VISIBLE);
			}
			if (_data.get((int)_position).get("post_disable_comments").toString().equals("true")) {
				commentsButton.setVisibility(View.GONE);
			} else {
				commentsButton.setVisibility(View.VISIBLE);
			}
			_setTime(Double.parseDouble(_data.get((int)_position).get("publish_date").toString()), postPublishDate);
			if (UserInfoCacheMap.containsKey("uid-".concat(_data.get((int)_position).get("uid").toString()))) {
				if (_data.get((int)_position).get("post_visibility").toString().equals("private")) {
					if (_data.get((int)_position).get("uid").toString().equals(FirebaseAuth.getInstance().getCurrentUser().getUid())) {
						postPrivateStateIcon.setVisibility(View.VISIBLE);
						body.setVisibility(View.VISIBLE);
					} else {
						body.setVisibility(View.GONE);
					}
				} else {
					body.setVisibility(View.VISIBLE);
					postPrivateStateIcon.setVisibility(View.GONE);
				}
				if (UserInfoCacheMap.get("banned-".concat(_data.get((int)_position).get("uid").toString())).toString().equals("true")) {
					userInfoProfileImage.setImageResource(R.drawable.banned_avatar);
				} else {
					if (UserInfoCacheMap.get("avatar-".concat(_data.get((int)_position).get("uid").toString())).toString().equals("null")) {
						userInfoProfileImage.setImageResource(R.drawable.avatar);
					} else {
						Glide.with(getApplicationContext()).load(Uri.parse(UserInfoCacheMap.get("avatar-".concat(_data.get((int)_position).get("uid").toString())).toString())).into(userInfoProfileImage);
					}
				}
				if (UserInfoCacheMap.get("nickname-".concat(_data.get((int)_position).get("uid").toString())).toString().equals("null")) {
					userInfoUsername.setText("@" + UserInfoCacheMap.get("username-".concat(_data.get((int)_position).get("uid").toString())).toString());
				} else {
					userInfoUsername.setText(UserInfoCacheMap.get("nickname-".concat(_data.get((int)_position).get("uid").toString())).toString());
				}
				if (UserInfoCacheMap.get("gender-".concat(_data.get((int)_position).get("uid").toString())).toString().equals("hidden")) {
					userInfoGenderBadge.setVisibility(View.GONE);
				} else {
					if (UserInfoCacheMap.get("gender-".concat(_data.get((int)_position).get("uid").toString())).toString().equals("male")) {
						userInfoGenderBadge.setImageResource(R.drawable.male_badge);
						userInfoGenderBadge.setVisibility(View.VISIBLE);
					} else {
						if (UserInfoCacheMap.get("gender-".concat(_data.get((int)_position).get("uid").toString())).toString().equals("female")) {
							userInfoGenderBadge.setImageResource(R.drawable.female_badge);
							userInfoGenderBadge.setVisibility(View.VISIBLE);
						}
					}
				}
				if (UserInfoCacheMap.get("acc_type-".concat(_data.get((int)_position).get("uid").toString())).toString().equals("admin")) {
					userInfoUsernameVerifiedBadge.setImageResource(R.drawable.admin_badge);
					userInfoUsernameVerifiedBadge.setVisibility(View.VISIBLE);
				} else {
					if (UserInfoCacheMap.get("acc_type-".concat(_data.get((int)_position).get("uid").toString())).toString().equals("moderator")) {
						userInfoUsernameVerifiedBadge.setImageResource(R.drawable.moderator_badge);
						userInfoUsernameVerifiedBadge.setVisibility(View.VISIBLE);
					} else {
						if (UserInfoCacheMap.get("acc_type-".concat(_data.get((int)_position).get("uid").toString())).toString().equals("support")) {
							userInfoUsernameVerifiedBadge.setImageResource(R.drawable.support_badge);
							userInfoUsernameVerifiedBadge.setVisibility(View.VISIBLE);
						} else {
							if (UserInfoCacheMap.get("acc_type-".concat(_data.get((int)_position).get("uid").toString())).toString().equals("user")) {
								if (UserInfoCacheMap.get("verify-".concat(_data.get((int)_position).get("uid").toString())).toString().equals("true")) {
									userInfoUsernameVerifiedBadge.setVisibility(View.VISIBLE);
								} else {
									userInfoUsernameVerifiedBadge.setVisibility(View.GONE);
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
							DatabaseReference getReference = FirebaseDatabase.getInstance().getReference().child("skyline/users").child(_data.get((int)_position).get("uid").toString());
							getReference.addListenerForSingleValueEvent(new ValueEventListener() {
								@Override
								public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
									if(dataSnapshot.exists()) {
										UserInfoCacheMap.put("uid-".concat(_data.get((int)_position).get("uid").toString()), _data.get((int)_position).get("uid").toString());
										UserInfoCacheMap.put("banned-".concat(_data.get((int)_position).get("uid").toString()), dataSnapshot.child("banned").getValue(String.class));
										UserInfoCacheMap.put("nickname-".concat(_data.get((int)_position).get("uid").toString()), dataSnapshot.child("nickname").getValue(String.class));
										UserInfoCacheMap.put("username-".concat(_data.get((int)_position).get("uid").toString()), dataSnapshot.child("username").getValue(String.class));
										UserInfoCacheMap.put("avatar-".concat(_data.get((int)_position).get("uid").toString()), dataSnapshot.child("avatar").getValue(String.class));
										UserInfoCacheMap.put("gender-".concat(_data.get((int)_position).get("uid").toString()), dataSnapshot.child("gender").getValue(String.class));
										UserInfoCacheMap.put("verify-".concat(_data.get((int)_position).get("uid").toString()), dataSnapshot.child("verify").getValue(String.class));
										UserInfoCacheMap.put("acc_type-".concat(_data.get((int)_position).get("uid").toString()), dataSnapshot.child("account_type").getValue(String.class));
										
										mMainHandler.post(new Runnable() {
											@Override
											public void run() {
												if (_data.get((int)_position).get("post_visibility").toString().equals("private")) {
													if (_data.get((int)_position).get("uid").toString().equals(FirebaseAuth.getInstance().getCurrentUser().getUid())) {
														postPrivateStateIcon.setVisibility(View.VISIBLE);
														body.setVisibility(View.VISIBLE);
													} else {
														body.setVisibility(View.GONE);
													}
												} else {
													body.setVisibility(View.VISIBLE);
													postPrivateStateIcon.setVisibility(View.GONE);
												}
												if (UserInfoCacheMap.get("banned-".concat(_data.get((int)_position).get("uid").toString())).toString().equals("true")) {
													userInfoProfileImage.setImageResource(R.drawable.banned_avatar);
												} else {
													if (UserInfoCacheMap.get("avatar-".concat(_data.get((int)_position).get("uid").toString())).toString().equals("null")) {
														userInfoProfileImage.setImageResource(R.drawable.avatar);
													} else {
														Glide.with(getApplicationContext()).load(Uri.parse(UserInfoCacheMap.get("avatar-".concat(_data.get((int)_position).get("uid").toString())).toString())).into(userInfoProfileImage);
													}
												}
												if (UserInfoCacheMap.get("nickname-".concat(_data.get((int)_position).get("uid").toString())).toString().equals("null")) {
													userInfoUsername.setText("@" + UserInfoCacheMap.get("username-".concat(_data.get((int)_position).get("uid").toString())).toString());
												} else {
													userInfoUsername.setText(UserInfoCacheMap.get("nickname-".concat(_data.get((int)_position).get("uid").toString())).toString());
												}
												if (UserInfoCacheMap.get("gender-".concat(_data.get((int)_position).get("uid").toString())).toString().equals("hidden")) {
													userInfoGenderBadge.setVisibility(View.GONE);
												} else {
													if (UserInfoCacheMap.get("gender-".concat(_data.get((int)_position).get("uid").toString())).toString().equals("male")) {
														userInfoGenderBadge.setImageResource(R.drawable.male_badge);
														userInfoGenderBadge.setVisibility(View.VISIBLE);
													} else {
														if (UserInfoCacheMap.get("gender-".concat(_data.get((int)_position).get("uid").toString())).toString().equals("female")) {
															userInfoGenderBadge.setImageResource(R.drawable.female_badge);
															userInfoGenderBadge.setVisibility(View.VISIBLE);
														}
													}
												}
												if (UserInfoCacheMap.get("acc_type-".concat(_data.get((int)_position).get("uid").toString())).toString().equals("admin")) {
													userInfoUsernameVerifiedBadge.setImageResource(R.drawable.admin_badge);
													userInfoUsernameVerifiedBadge.setVisibility(View.VISIBLE);
												} else {
													if (UserInfoCacheMap.get("acc_type-".concat(_data.get((int)_position).get("uid").toString())).toString().equals("moderator")) {
														userInfoUsernameVerifiedBadge.setImageResource(R.drawable.moderator_badge);
														userInfoUsernameVerifiedBadge.setVisibility(View.VISIBLE);
													} else {
														if (UserInfoCacheMap.get("acc_type-".concat(_data.get((int)_position).get("uid").toString())).toString().equals("support")) {
															userInfoUsernameVerifiedBadge.setImageResource(R.drawable.support_badge);
															userInfoUsernameVerifiedBadge.setVisibility(View.VISIBLE);
														} else {
															if (UserInfoCacheMap.get("acc_type-".concat(_data.get((int)_position).get("uid").toString())).toString().equals("user")) {
																if (UserInfoCacheMap.get("verify-".concat(_data.get((int)_position).get("uid").toString())).toString().equals("true")) {
																	userInfoUsernameVerifiedBadge.setVisibility(View.VISIBLE);
																} else {
																	userInfoUsernameVerifiedBadge.setVisibility(View.GONE);
																}
															}
														}
													}
												}
												
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
			DatabaseReference getLikeCheck = FirebaseDatabase.getInstance().getReference("skyline/posts-likes").child(_data.get((int)_position).get("key").toString()).child(FirebaseAuth.getInstance().getCurrentUser().getUid());
			DatabaseReference getCommentsCount = FirebaseDatabase.getInstance().getReference("skyline/posts-comments").child(_data.get((int)_position).get("key").toString());
			DatabaseReference getLikesCount = FirebaseDatabase.getInstance().getReference("skyline/posts-likes").child(_data.get((int)_position).get("key").toString());
			DatabaseReference getFavoriteCheck = FirebaseDatabase.getInstance().getReference("skyline/favorite-posts").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(_data.get((int)_position).get("key").toString());
			
			getLikeCheck.addListenerForSingleValueEvent(new ValueEventListener() {
				@Override
				public void onDataChange(@NonNull DataSnapshot dataSnapshot) { 
					if(dataSnapshot.exists()) {
						likeButtonIc.setImageResource(R.drawable.post_icons_1_2);
					} else {
						likeButtonIc.setImageResource(R.drawable.post_icons_1_1);
					}
				}
				
				@Override
				public void onCancelled(@NonNull DatabaseError databaseError) {
					
				}
			});
			getCommentsCount.addListenerForSingleValueEvent(new ValueEventListener() {
				@Override
				public void onDataChange(DataSnapshot dataSnapshot) {
					long count = dataSnapshot.getChildrenCount();
					_setCount(commentsButtonCount, count);
				}
				
				@Override
				public void onCancelled(DatabaseError databaseError) {
					
				}
			});
			getLikesCount.addListenerForSingleValueEvent(new ValueEventListener() {
				@Override
				public void onDataChange(DataSnapshot dataSnapshot) {
					long count = dataSnapshot.getChildrenCount();
					_setCount(likeButtonCount, count);
					postLikeCountCache.put(_data.get((int)_position).get("key").toString(), String.valueOf((long)(count)));
				}
				
				@Override
				public void onCancelled(DatabaseError databaseError) {
					
				}
			});
			getFavoriteCheck.addListenerForSingleValueEvent(new ValueEventListener() {
				@Override
				public void onDataChange(@NonNull DataSnapshot dataSnapshot) { 
					if(dataSnapshot.exists()) {
						favoritePostButton.setImageResource(R.drawable.delete_favorite_post_ic);
					} else {
						favoritePostButton.setImageResource(R.drawable.add_favorite_post_ic);
					}
				}
				
				@Override
				public void onCancelled(@NonNull DatabaseError databaseError) {
					
				}
			});
			
			likeButton.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View _view) {
					DatabaseReference getLikeCheck = FirebaseDatabase.getInstance().getReference("skyline/posts-likes").child(_data.get((int)_position).get("key").toString()).child(FirebaseAuth.getInstance().getCurrentUser().getUid());
					getLikeCheck.addListenerForSingleValueEvent(new ValueEventListener() {
						@Override
						public void onDataChange(@NonNull DataSnapshot dataSnapshot) { 
							if(dataSnapshot.exists()) {
								getLikeCheck.removeValue();
								postLikeCountCache.put(_data.get((int)_position).get("key").toString(), String.valueOf((long)(Double.parseDouble(postLikeCountCache.get(_data.get((int)_position).get("key").toString()).toString()) - 1)));
								_setCount(likeButtonCount, Double.parseDouble(postLikeCountCache.get(_data.get((int)_position).get("key").toString()).toString()));
								likeButtonIc.setImageResource(R.drawable.post_icons_1_1);
							} else {
								getLikeCheck.setValue(FirebaseAuth.getInstance().getCurrentUser().getUid());
								postLikeCountCache.put(_data.get((int)_position).get("key").toString(), String.valueOf((long)(Double.parseDouble(postLikeCountCache.get(_data.get((int)_position).get("key").toString()).toString()) + 1)));
								_setCount(likeButtonCount, Double.parseDouble(postLikeCountCache.get(_data.get((int)_position).get("key").toString()).toString()));
								likeButtonIc.setImageResource(R.drawable.post_icons_1_2);
							}
						}
						
						@Override
						public void onCancelled(@NonNull DatabaseError databaseError) {
							
						}
					});
					vbr.vibrate((long)(24));
				}
			});
			commentsButton.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View _view) {
					Bundle sendPostKey = new Bundle();
					sendPostKey.putString("postKey", _data.get((int)_position).get("key").toString());
					sendPostKey.putString("postPublisherUID", _data.get((int)_position).get("uid").toString());
					sendPostKey.putString("postPublisherAvatar", UserInfoCacheMap.get("avatar-".concat(_data.get((int)_position).get("uid").toString())).toString());
					PostCommentsBottomSheetDialog postCommentsBottomSheet = new PostCommentsBottomSheetDialog();
					postCommentsBottomSheet.setArguments(sendPostKey);
					postCommentsBottomSheet.show(getSupportFragmentManager(), postCommentsBottomSheet.getTag());
				}
			});
			userInfo.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View _view) {
					intent.setClass(getApplicationContext(), ProfileActivity.class);
					intent.putExtra("uid", _data.get((int)_position).get("uid").toString());
					startActivity(intent);
				}
			});
			favoritePostButton.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View _view) {
					DatabaseReference getFavoriteCheck = FirebaseDatabase.getInstance().getReference("skyline/favorite-posts").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(_data.get((int)_position).get("key").toString());
					getFavoriteCheck.addListenerForSingleValueEvent(new ValueEventListener() {
						@Override
						public void onDataChange(@NonNull DataSnapshot dataSnapshot) { 
							if(dataSnapshot.exists()) {
								getFavoriteCheck.removeValue();
								favoritePostButton.setImageResource(R.drawable.add_favorite_post_ic);
							} else {
								getFavoriteCheck.setValue(_data.get((int)_position).get("key").toString());
								favoritePostButton.setImageResource(R.drawable.delete_favorite_post_ic);
							}
						}
						
						@Override
						public void onCancelled(@NonNull DatabaseError databaseError) {
							
						}
					});
					vbr.vibrate((long)(24));
				}
			});
			topMoreButton.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View _view) {
					Bundle sendPostKey = new Bundle();
					sendPostKey.putString("postKey", _data.get((int)_position).get("key").toString());
					sendPostKey.putString("postPublisherUID", _data.get((int)_position).get("uid").toString());
					sendPostKey.putString("postType", _data.get((int)_position).get("post_type").toString());
					
					if (_data.get((int)_position).containsKey("post_image") && !_data.get((int)_position).get("post_image").toString().isEmpty()) {
						sendPostKey.putString("postImg", _data.get((int)_position).get("post_image").toString());
					}
					
					PostMoreBottomSheetDialog postMoreBottomSheetDialog = new PostMoreBottomSheetDialog();
					postMoreBottomSheetDialog.setArguments(sendPostKey);
					postMoreBottomSheetDialog.show(getSupportFragmentManager(), postMoreBottomSheetDialog.getTag());
				}
			});
			likeButton.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b, int c, int d) { this.setCornerRadius(a); this.setStroke(b, c); this.setColor(d); return this; } }.getIns((int)300, (int)1, 0xFFE0E0E0, 0xFFF5F5F5));
			commentsButton.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b, int c, int d) { this.setCornerRadius(a); this.setStroke(b, c); this.setColor(d); return this; } }.getIns((int)300, (int)1, 0xFFE0E0E0, 0xFFF5F5F5));
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
	
	public class FavoritesPostsListAdapter extends RecyclerView.Adapter<FavoritesPostsListAdapter.ViewHolder> {
		
		ArrayList<HashMap<String, Object>> _data;
		
		public FavoritesPostsListAdapter(ArrayList<HashMap<String, Object>> _arr) {
			_data = _arr;
		}
		
		@Override
		public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
			LayoutInflater _inflater = getLayoutInflater();
			View _v = _inflater.inflate(R.layout.synapse_post_cv, null);
			RecyclerView.LayoutParams _lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
			_v.setLayoutParams(_lp);
			return new ViewHolder(_v);
		}
		
		@Override
		public void onBindViewHolder(ViewHolder _holder, final int _position) {
			View _view = _holder.itemView;
			
			final LinearLayout body = _view.findViewById(R.id.body);
			final LinearLayout top = _view.findViewById(R.id.top);
			final LinearLayout linear1 = _view.findViewById(R.id.linear1);
			final LinearLayout middle = _view.findViewById(R.id.middle);
			final LinearLayout bottom = _view.findViewById(R.id.bottom);
			final LinearLayout userInfo = _view.findViewById(R.id.userInfo);
			final LinearLayout topSpace = _view.findViewById(R.id.topSpace);
			final ImageView topMoreButton = _view.findViewById(R.id.topMoreButton);
			final androidx.cardview.widget.CardView userInfoProfileCard = _view.findViewById(R.id.userInfoProfileCard);
			final LinearLayout userInfoRight = _view.findViewById(R.id.userInfoRight);
			final ImageView userInfoProfileImage = _view.findViewById(R.id.userInfoProfileImage);
			final LinearLayout userInfoUsernameRightTop = _view.findViewById(R.id.userInfoUsernameRightTop);
			final LinearLayout btmPost = _view.findViewById(R.id.btmPost);
			final TextView userInfoUsername = _view.findViewById(R.id.userInfoUsername);
			final ImageView userInfoGenderBadge = _view.findViewById(R.id.userInfoGenderBadge);
			final ImageView userInfoUsernameVerifiedBadge = _view.findViewById(R.id.userInfoUsernameVerifiedBadge);
			final TextView postPublishDate = _view.findViewById(R.id.postPublishDate);
			final ImageView postPrivateStateIcon = _view.findViewById(R.id.postPrivateStateIcon);
			final TextView postMessageTextMiddle = _view.findViewById(R.id.postMessageTextMiddle);
			final ImageView postImage = _view.findViewById(R.id.postImage);
			final WebView linkprewebview = _view.findViewById(R.id.linkprewebview);
			final VideoView PostVideoPlayer = _view.findViewById(R.id.PostVideoPlayer);
			final LinearLayout likeButton = _view.findViewById(R.id.likeButton);
			final LinearLayout commentsButton = _view.findViewById(R.id.commentsButton);
			final LinearLayout shareButton = _view.findViewById(R.id.shareButton);
			final LinearLayout bottomSpc = _view.findViewById(R.id.bottomSpc);
			final ImageView favoritePostButton = _view.findViewById(R.id.favoritePostButton);
			final ImageView likeButtonIc = _view.findViewById(R.id.likeButtonIc);
			final TextView likeButtonCount = _view.findViewById(R.id.likeButtonCount);
			final TextView textview1 = _view.findViewById(R.id.textview1);
			final TextView tv_1 = _view.findViewById(R.id.tv_1);
			final ImageView commentsButtonIc = _view.findViewById(R.id.commentsButtonIc);
			final TextView commentsButtonCount = _view.findViewById(R.id.commentsButtonCount);
			final TextView textview2 = _view.findViewById(R.id.textview2);
			final TextView tv_2 = _view.findViewById(R.id.tv_2);
			final ImageView shareButtonIc = _view.findViewById(R.id.shareButtonIc);
			final TextView shareButtonCount = _view.findViewById(R.id.shareButtonCount);
			
			RecyclerView.LayoutParams _lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
			_view.setLayoutParams(_lp);
			body.setVisibility(View.GONE);
			userInfoProfileCard.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b) { this.setCornerRadius(a); this.setColor(b); return this; } }.getIns((int)300, Color.TRANSPARENT));
			_ImageColor(postPrivateStateIcon, 0xFF616161);
			_viewGraphics(topMoreButton, 0xFFFFFFFF, 0xFFEEEEEE, 300, 0, Color.TRANSPARENT);
			if (_data.get((int)_position).get("post_type").toString().equals("TEXT") || (_data.get((int)_position).get("post_type").toString().equals("IMAGE") || _data.get((int)_position).get("post_type").toString().equals("VIDEO"))) {
				if (_data.get((int)_position).containsKey("post_text")) {
					_textview_mh(postMessageTextMiddle, _data.get((int)_position).get("post_text").toString());
					//postMessageTextMiddle.setText(_data.get((int)_position).get("post_text").toString());
					postMessageTextMiddle.setVisibility(View.VISIBLE);
				} else {
					postMessageTextMiddle.setVisibility(View.GONE);
				}
				if (_data.get((int)_position).containsKey("post_image")) {
					Glide.with(getApplicationContext()).load(Uri.parse(_data.get((int)_position).get("post_image").toString())).into(postImage);
					postImage.setVisibility(View.VISIBLE);
				} else {
					postImage.setVisibility(View.GONE);
				}
			} else {
				postImage.setVisibility(View.GONE);
				postMessageTextMiddle.setVisibility(View.GONE);
			}
			
			if (_data.get((int)_position).get("post_hide_like_count").toString().equals("true")) {
				likeButtonCount.setVisibility(View.GONE);
			} else {
				likeButtonCount.setVisibility(View.VISIBLE);
			}
			if (_data.get((int)_position).get("post_hide_comments_count").toString().equals("true")) {
				commentsButtonCount.setVisibility(View.GONE);
			} else {
				commentsButtonCount.setVisibility(View.VISIBLE);
			}
			if (_data.get((int)_position).get("post_disable_comments").toString().equals("true")) {
				commentsButton.setVisibility(View.GONE);
			} else {
				commentsButton.setVisibility(View.VISIBLE);
			}
			_setTime(Double.parseDouble(_data.get((int)_position).get("publish_date").toString()), postPublishDate);
			if (UserInfoCacheMap.containsKey("uid-".concat(_data.get((int)_position).get("uid").toString()))) {
				if (_data.get((int)_position).get("post_visibility").toString().equals("private")) {
					if (_data.get((int)_position).get("uid").toString().equals(FirebaseAuth.getInstance().getCurrentUser().getUid())) {
						postPrivateStateIcon.setVisibility(View.VISIBLE);
						body.setVisibility(View.VISIBLE);
					} else {
						body.setVisibility(View.GONE);
					}
				} else {
					body.setVisibility(View.VISIBLE);
					postPrivateStateIcon.setVisibility(View.GONE);
				}
				if (UserInfoCacheMap.get("banned-".concat(_data.get((int)_position).get("uid").toString())).toString().equals("true")) {
					userInfoProfileImage.setImageResource(R.drawable.banned_avatar);
				} else {
					if (UserInfoCacheMap.get("avatar-".concat(_data.get((int)_position).get("uid").toString())).toString().equals("null")) {
						userInfoProfileImage.setImageResource(R.drawable.avatar);
					} else {
						Glide.with(getApplicationContext()).load(Uri.parse(UserInfoCacheMap.get("avatar-".concat(_data.get((int)_position).get("uid").toString())).toString())).into(userInfoProfileImage);
					}
				}
				if (UserInfoCacheMap.get("nickname-".concat(_data.get((int)_position).get("uid").toString())).toString().equals("null")) {
					userInfoUsername.setText("@" + UserInfoCacheMap.get("username-".concat(_data.get((int)_position).get("uid").toString())).toString());
				} else {
					userInfoUsername.setText(UserInfoCacheMap.get("nickname-".concat(_data.get((int)_position).get("uid").toString())).toString());
				}
				if (UserInfoCacheMap.get("gender-".concat(_data.get((int)_position).get("uid").toString())).toString().equals("hidden")) {
					userInfoGenderBadge.setVisibility(View.GONE);
				} else {
					if (UserInfoCacheMap.get("gender-".concat(_data.get((int)_position).get("uid").toString())).toString().equals("male")) {
						userInfoGenderBadge.setImageResource(R.drawable.male_badge);
						userInfoGenderBadge.setVisibility(View.VISIBLE);
					} else {
						if (UserInfoCacheMap.get("gender-".concat(_data.get((int)_position).get("uid").toString())).toString().equals("female")) {
							userInfoGenderBadge.setImageResource(R.drawable.female_badge);
							userInfoGenderBadge.setVisibility(View.VISIBLE);
						}
					}
				}
				if (UserInfoCacheMap.get("acc_type-".concat(_data.get((int)_position).get("uid").toString())).toString().equals("admin")) {
					userInfoUsernameVerifiedBadge.setImageResource(R.drawable.admin_badge);
					userInfoUsernameVerifiedBadge.setVisibility(View.VISIBLE);
				} else {
					if (UserInfoCacheMap.get("acc_type-".concat(_data.get((int)_position).get("uid").toString())).toString().equals("moderator")) {
						userInfoUsernameVerifiedBadge.setImageResource(R.drawable.moderator_badge);
						userInfoUsernameVerifiedBadge.setVisibility(View.VISIBLE);
					} else {
						if (UserInfoCacheMap.get("acc_type-".concat(_data.get((int)_position).get("uid").toString())).toString().equals("support")) {
							userInfoUsernameVerifiedBadge.setImageResource(R.drawable.support_badge);
							userInfoUsernameVerifiedBadge.setVisibility(View.VISIBLE);
						} else {
							if (UserInfoCacheMap.get("acc_type-".concat(_data.get((int)_position).get("uid").toString())).toString().equals("user")) {
								if (UserInfoCacheMap.get("verify-".concat(_data.get((int)_position).get("uid").toString())).toString().equals("true")) {
									userInfoUsernameVerifiedBadge.setVisibility(View.VISIBLE);
								} else {
									userInfoUsernameVerifiedBadge.setVisibility(View.GONE);
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
							DatabaseReference getReference = FirebaseDatabase.getInstance().getReference().child("skyline/users").child(_data.get((int)_position).get("uid").toString());
							getReference.addListenerForSingleValueEvent(new ValueEventListener() {
								@Override
								public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
									if(dataSnapshot.exists()) {
										UserInfoCacheMap.put("uid-".concat(_data.get((int)_position).get("uid").toString()), _data.get((int)_position).get("uid").toString());
										UserInfoCacheMap.put("banned-".concat(_data.get((int)_position).get("uid").toString()), dataSnapshot.child("banned").getValue(String.class));
										UserInfoCacheMap.put("nickname-".concat(_data.get((int)_position).get("uid").toString()), dataSnapshot.child("nickname").getValue(String.class));
										UserInfoCacheMap.put("username-".concat(_data.get((int)_position).get("uid").toString()), dataSnapshot.child("username").getValue(String.class));
										UserInfoCacheMap.put("avatar-".concat(_data.get((int)_position).get("uid").toString()), dataSnapshot.child("avatar").getValue(String.class));
										UserInfoCacheMap.put("gender-".concat(_data.get((int)_position).get("uid").toString()), dataSnapshot.child("gender").getValue(String.class));
										UserInfoCacheMap.put("verify-".concat(_data.get((int)_position).get("uid").toString()), dataSnapshot.child("verify").getValue(String.class));
										UserInfoCacheMap.put("acc_type-".concat(_data.get((int)_position).get("uid").toString()), dataSnapshot.child("account_type").getValue(String.class));
										
										mMainHandler.post(new Runnable() {
											@Override
											public void run() {
												if (_data.get((int)_position).get("post_visibility").toString().equals("private")) {
													if (_data.get((int)_position).get("uid").toString().equals(FirebaseAuth.getInstance().getCurrentUser().getUid())) {
														postPrivateStateIcon.setVisibility(View.VISIBLE);
														body.setVisibility(View.VISIBLE);
													} else {
														body.setVisibility(View.GONE);
													}
												} else {
													body.setVisibility(View.VISIBLE);
													postPrivateStateIcon.setVisibility(View.GONE);
												}
												if (UserInfoCacheMap.get("banned-".concat(_data.get((int)_position).get("uid").toString())).toString().equals("true")) {
													userInfoProfileImage.setImageResource(R.drawable.banned_avatar);
												} else {
													if (UserInfoCacheMap.get("avatar-".concat(_data.get((int)_position).get("uid").toString())).toString().equals("null")) {
														userInfoProfileImage.setImageResource(R.drawable.avatar);
													} else {
														Glide.with(getApplicationContext()).load(Uri.parse(UserInfoCacheMap.get("avatar-".concat(_data.get((int)_position).get("uid").toString())).toString())).into(userInfoProfileImage);
													}
												}
												if (UserInfoCacheMap.get("nickname-".concat(_data.get((int)_position).get("uid").toString())).toString().equals("null")) {
													userInfoUsername.setText("@" + UserInfoCacheMap.get("username-".concat(_data.get((int)_position).get("uid").toString())).toString());
												} else {
													userInfoUsername.setText(UserInfoCacheMap.get("nickname-".concat(_data.get((int)_position).get("uid").toString())).toString());
												}
												if (UserInfoCacheMap.get("gender-".concat(_data.get((int)_position).get("uid").toString())).toString().equals("hidden")) {
													userInfoGenderBadge.setVisibility(View.GONE);
												} else {
													if (UserInfoCacheMap.get("gender-".concat(_data.get((int)_position).get("uid").toString())).toString().equals("male")) {
														userInfoGenderBadge.setImageResource(R.drawable.male_badge);
														userInfoGenderBadge.setVisibility(View.VISIBLE);
													} else {
														if (UserInfoCacheMap.get("gender-".concat(_data.get((int)_position).get("uid").toString())).toString().equals("female")) {
															userInfoGenderBadge.setImageResource(R.drawable.female_badge);
															userInfoGenderBadge.setVisibility(View.VISIBLE);
														}
													}
												}
												if (UserInfoCacheMap.get("acc_type-".concat(_data.get((int)_position).get("uid").toString())).toString().equals("admin")) {
													userInfoUsernameVerifiedBadge.setImageResource(R.drawable.admin_badge);
													userInfoUsernameVerifiedBadge.setVisibility(View.VISIBLE);
												} else {
													if (UserInfoCacheMap.get("acc_type-".concat(_data.get((int)_position).get("uid").toString())).toString().equals("moderator")) {
														userInfoUsernameVerifiedBadge.setImageResource(R.drawable.moderator_badge);
														userInfoUsernameVerifiedBadge.setVisibility(View.VISIBLE);
													} else {
														if (UserInfoCacheMap.get("acc_type-".concat(_data.get((int)_position).get("uid").toString())).toString().equals("support")) {
															userInfoUsernameVerifiedBadge.setImageResource(R.drawable.support_badge);
															userInfoUsernameVerifiedBadge.setVisibility(View.VISIBLE);
														} else {
															if (UserInfoCacheMap.get("acc_type-".concat(_data.get((int)_position).get("uid").toString())).toString().equals("user")) {
																if (UserInfoCacheMap.get("verify-".concat(_data.get((int)_position).get("uid").toString())).toString().equals("true")) {
																	userInfoUsernameVerifiedBadge.setVisibility(View.VISIBLE);
																} else {
																	userInfoUsernameVerifiedBadge.setVisibility(View.GONE);
																}
															}
														}
													}
												}
												
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
			DatabaseReference getLikeCheck = FirebaseDatabase.getInstance().getReference("skyline/posts-likes").child(_data.get((int)_position).get("key").toString()).child(FirebaseAuth.getInstance().getCurrentUser().getUid());
			DatabaseReference getCommentsCount = FirebaseDatabase.getInstance().getReference("skyline/posts-comments").child(_data.get((int)_position).get("key").toString());
			DatabaseReference getLikesCount = FirebaseDatabase.getInstance().getReference("skyline/posts-likes").child(_data.get((int)_position).get("key").toString());
			DatabaseReference getFavoriteCheck = FirebaseDatabase.getInstance().getReference("skyline/favorite-posts").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(_data.get((int)_position).get("key").toString());
			
			getLikeCheck.addListenerForSingleValueEvent(new ValueEventListener() {
				@Override
				public void onDataChange(@NonNull DataSnapshot dataSnapshot) { 
					if(dataSnapshot.exists()) {
						likeButtonIc.setImageResource(R.drawable.post_icons_1_2);
					} else {
						likeButtonIc.setImageResource(R.drawable.post_icons_1_1);
					}
				}
				
				@Override
				public void onCancelled(@NonNull DatabaseError databaseError) {
					
				}
			});
			getCommentsCount.addListenerForSingleValueEvent(new ValueEventListener() {
				@Override
				public void onDataChange(DataSnapshot dataSnapshot) {
					long count = dataSnapshot.getChildrenCount();
					_setCount(commentsButtonCount, count);
				}
				
				@Override
				public void onCancelled(DatabaseError databaseError) {
					
				}
			});
			getLikesCount.addListenerForSingleValueEvent(new ValueEventListener() {
				@Override
				public void onDataChange(DataSnapshot dataSnapshot) {
					long count = dataSnapshot.getChildrenCount();
					_setCount(likeButtonCount, count);
					postLikeCountCache.put(_data.get((int)_position).get("key").toString(), String.valueOf((long)(count)));
				}
				
				@Override
				public void onCancelled(DatabaseError databaseError) {
					
				}
			});
			getFavoriteCheck.addListenerForSingleValueEvent(new ValueEventListener() {
				@Override
				public void onDataChange(@NonNull DataSnapshot dataSnapshot) { 
					if(dataSnapshot.exists()) {
						favoritePostButton.setImageResource(R.drawable.delete_favorite_post_ic);
					} else {
						favoritePostButton.setImageResource(R.drawable.add_favorite_post_ic);
					}
				}
				
				@Override
				public void onCancelled(@NonNull DatabaseError databaseError) {
					
				}
			});
			
			likeButton.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View _view) {
					DatabaseReference getLikeCheck = FirebaseDatabase.getInstance().getReference("skyline/posts-likes").child(_data.get((int)_position).get("key").toString()).child(FirebaseAuth.getInstance().getCurrentUser().getUid());
					getLikeCheck.addListenerForSingleValueEvent(new ValueEventListener() {
						@Override
						public void onDataChange(@NonNull DataSnapshot dataSnapshot) { 
							if(dataSnapshot.exists()) {
								getLikeCheck.removeValue();
								postLikeCountCache.put(_data.get((int)_position).get("key").toString(), String.valueOf((long)(Double.parseDouble(postLikeCountCache.get(_data.get((int)_position).get("key").toString()).toString()) - 1)));
								_setCount(likeButtonCount, Double.parseDouble(postLikeCountCache.get(_data.get((int)_position).get("key").toString()).toString()));
								likeButtonIc.setImageResource(R.drawable.post_icons_1_1);
							} else {
								getLikeCheck.setValue(FirebaseAuth.getInstance().getCurrentUser().getUid());
								postLikeCountCache.put(_data.get((int)_position).get("key").toString(), String.valueOf((long)(Double.parseDouble(postLikeCountCache.get(_data.get((int)_position).get("key").toString()).toString()) + 1)));
								_setCount(likeButtonCount, Double.parseDouble(postLikeCountCache.get(_data.get((int)_position).get("key").toString()).toString()));
								likeButtonIc.setImageResource(R.drawable.post_icons_1_2);
							}
						}
						
						@Override
						public void onCancelled(@NonNull DatabaseError databaseError) {
							
						}
					});
					vbr.vibrate((long)(24));
				}
			});
			commentsButton.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View _view) {
					Bundle sendPostKey = new Bundle();
					sendPostKey.putString("postKey", _data.get((int)_position).get("key").toString());
					sendPostKey.putString("postPublisherUID", _data.get((int)_position).get("uid").toString());
					sendPostKey.putString("postPublisherAvatar", UserInfoCacheMap.get("avatar-".concat(_data.get((int)_position).get("uid").toString())).toString());
					PostCommentsBottomSheetDialog postCommentsBottomSheet = new PostCommentsBottomSheetDialog();
					postCommentsBottomSheet.setArguments(sendPostKey);
					postCommentsBottomSheet.show(getSupportFragmentManager(), postCommentsBottomSheet.getTag());
				}
			});
			userInfo.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View _view) {
					intent.setClass(getApplicationContext(), ProfileActivity.class);
					intent.putExtra("uid", _data.get((int)_position).get("uid").toString());
					startActivity(intent);
				}
			});
			favoritePostButton.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View _view) {
					DatabaseReference getFavoriteCheck = FirebaseDatabase.getInstance().getReference("skyline/favorite-posts").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(_data.get((int)_position).get("key").toString());
					getFavoriteCheck.addListenerForSingleValueEvent(new ValueEventListener() {
						@Override
						public void onDataChange(@NonNull DataSnapshot dataSnapshot) { 
							if(dataSnapshot.exists()) {
								getFavoriteCheck.removeValue();
								favoritePostButton.setImageResource(R.drawable.add_favorite_post_ic);
							} else {
								getFavoriteCheck.setValue(_data.get((int)_position).get("key").toString());
								favoritePostButton.setImageResource(R.drawable.delete_favorite_post_ic);
							}
						}
						
						@Override
						public void onCancelled(@NonNull DatabaseError databaseError) {
							
						}
					});
					vbr.vibrate((long)(24));
				}
			});
			topMoreButton.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View _view) {
					Bundle sendPostKey = new Bundle();
					sendPostKey.putString("postKey", _data.get((int)_position).get("key").toString());
					sendPostKey.putString("postPublisherUID", _data.get((int)_position).get("uid").toString());
					sendPostKey.putString("postType", _data.get((int)_position).get("post_type").toString());
					
					if (_data.get((int)_position).containsKey("post_image") && !_data.get((int)_position).get("post_image").toString().isEmpty()) {
						sendPostKey.putString("postImg", _data.get((int)_position).get("post_image").toString());
					}
					
					PostMoreBottomSheetDialog postMoreBottomSheetDialog = new PostMoreBottomSheetDialog();
					postMoreBottomSheetDialog.setArguments(sendPostKey);
					postMoreBottomSheetDialog.show(getSupportFragmentManager(), postMoreBottomSheetDialog.getTag());
				}
			});
			likeButton.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b, int c, int d) { this.setCornerRadius(a); this.setStroke(b, c); this.setColor(d); return this; } }.getIns((int)300, (int)1, 0xFFE0E0E0, 0xFFF5F5F5));
			commentsButton.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b, int c, int d) { this.setCornerRadius(a); this.setStroke(b, c); this.setColor(d); return this; } }.getIns((int)300, (int)1, 0xFFE0E0E0, 0xFFF5F5F5));
			postImage.setOnLongClickListener(new View.OnLongClickListener() {
				@Override
				public boolean onLongClick(View _view) {
					_OpenWebView(_data.get((int)_position).get("post_image").toString());
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
}
