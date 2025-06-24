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
import java.util.*;
import java.util.HashMap;
import java.util.regex.*;
import kr.co.prnd.readmore.*;
import me.dm7.barcodescanner.core.*;
import org.jetbrains.kotlin.*;
import org.json.*;
import com.google.firebase.database.Query;
import android.os.Handler;
import android.os.Looper;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;



public class DashboardActivity extends AppCompatActivity {
	
	private FirebaseDatabase _firebase = FirebaseDatabase.getInstance();
	
	private ProgressDialog SynapseLoadingDialog;
	
	private SwipeRefreshLayout swipe_refresh_layout;
	private ScrollView scroll;
	private LinearLayout scroll_body;
	private LinearLayout app_user_info;
	private LinearLayout app_users_stage_1;
	private LinearLayout app_users_stage_2;
	private LinearLayout app_update_manager;
	private LinearLayout app_server_manager;
	private LinearLayout app_user_manager;
	private LinearLayout app_logout;
	private CardView app_user_info_profile_card;
	private TextView app_user_info_username;
	private ImageView app_user_info_gender;
	private ImageView app_user_info_profile_image;
	private LinearLayout app_users_count;
	private LinearLayout app_online_users_count;
	private LinearLayout app_users_count_top;
	private TextView app_users_count_num;
	private TextView app_users_count_title;
	private ImageView app_users_count_arrow;
	private LinearLayout app_online_users_count_top;
	private TextView app_online_users_count_num;
	private TextView app_online_users_count_title;
	private ImageView app_online_users_count_arrow;
	private LinearLayout app_banned_users_count;
	private LinearLayout app_premium_users_count;
	private LinearLayout app_banned_users_count_top;
	private TextView app_banned_users_count_num;
	private TextView app_banned_users_count_title;
	private ImageView app_banned_users_count_arrow;
	private LinearLayout app_premium_users_count_top;
	private TextView app_premium_users_count_num;
	private TextView app_premium_users_count_title;
	private ImageView app_premium_users_count_arrow;
	private LinearLayout app_update_manager_top;
	private TextView app_update_manager_subtext;
	private TextView app_update_manager_title;
	private ImageView app_update_manager_arrow;
	private LinearLayout app_server_manager_top;
	private TextView app_server_manager_subtext;
	private TextView app_server_manager_title;
	private ImageView app_server_manager_arrow;
	private LinearLayout app_user_manager_top;
	private TextView app_user_manager_subtext;
	private TextView app_user_manager_title;
	private ImageView app_user_manager_arrow;
	private LinearLayout app_logout_top;
	private TextView app_logout_title;
	private ImageView app_logout_arrow;
	
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
		setContentView(R.layout.dashboard);
		initialize(_savedInstanceState);
		FirebaseApp.initializeApp(this);
		initializeLogic();
	}
	
	private void initialize(Bundle _savedInstanceState) {
		swipe_refresh_layout = findViewById(R.id.swipe_refresh_layout);
		scroll = findViewById(R.id.scroll);
		scroll_body = findViewById(R.id.scroll_body);
		app_user_info = findViewById(R.id.app_user_info);
		app_users_stage_1 = findViewById(R.id.app_users_stage_1);
		app_users_stage_2 = findViewById(R.id.app_users_stage_2);
		app_update_manager = findViewById(R.id.app_update_manager);
		app_server_manager = findViewById(R.id.app_server_manager);
		app_user_manager = findViewById(R.id.app_user_manager);
		app_logout = findViewById(R.id.app_logout);
		app_user_info_profile_card = findViewById(R.id.app_user_info_profile_card);
		app_user_info_username = findViewById(R.id.app_user_info_username);
		app_user_info_gender = findViewById(R.id.app_user_info_gender);
		app_user_info_profile_image = findViewById(R.id.app_user_info_profile_image);
		app_users_count = findViewById(R.id.app_users_count);
		app_online_users_count = findViewById(R.id.app_online_users_count);
		app_users_count_top = findViewById(R.id.app_users_count_top);
		app_users_count_num = findViewById(R.id.app_users_count_num);
		app_users_count_title = findViewById(R.id.app_users_count_title);
		app_users_count_arrow = findViewById(R.id.app_users_count_arrow);
		app_online_users_count_top = findViewById(R.id.app_online_users_count_top);
		app_online_users_count_num = findViewById(R.id.app_online_users_count_num);
		app_online_users_count_title = findViewById(R.id.app_online_users_count_title);
		app_online_users_count_arrow = findViewById(R.id.app_online_users_count_arrow);
		app_banned_users_count = findViewById(R.id.app_banned_users_count);
		app_premium_users_count = findViewById(R.id.app_premium_users_count);
		app_banned_users_count_top = findViewById(R.id.app_banned_users_count_top);
		app_banned_users_count_num = findViewById(R.id.app_banned_users_count_num);
		app_banned_users_count_title = findViewById(R.id.app_banned_users_count_title);
		app_banned_users_count_arrow = findViewById(R.id.app_banned_users_count_arrow);
		app_premium_users_count_top = findViewById(R.id.app_premium_users_count_top);
		app_premium_users_count_num = findViewById(R.id.app_premium_users_count_num);
		app_premium_users_count_title = findViewById(R.id.app_premium_users_count_title);
		app_premium_users_count_arrow = findViewById(R.id.app_premium_users_count_arrow);
		app_update_manager_top = findViewById(R.id.app_update_manager_top);
		app_update_manager_subtext = findViewById(R.id.app_update_manager_subtext);
		app_update_manager_title = findViewById(R.id.app_update_manager_title);
		app_update_manager_arrow = findViewById(R.id.app_update_manager_arrow);
		app_server_manager_top = findViewById(R.id.app_server_manager_top);
		app_server_manager_subtext = findViewById(R.id.app_server_manager_subtext);
		app_server_manager_title = findViewById(R.id.app_server_manager_title);
		app_server_manager_arrow = findViewById(R.id.app_server_manager_arrow);
		app_user_manager_top = findViewById(R.id.app_user_manager_top);
		app_user_manager_subtext = findViewById(R.id.app_user_manager_subtext);
		app_user_manager_title = findViewById(R.id.app_user_manager_title);
		app_user_manager_arrow = findViewById(R.id.app_user_manager_arrow);
		app_logout_top = findViewById(R.id.app_logout_top);
		app_logout_title = findViewById(R.id.app_logout_title);
		app_logout_arrow = findViewById(R.id.app_logout_arrow);
		auth = FirebaseAuth.getInstance();
		
		swipe_refresh_layout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
			@Override
			public void onRefresh() {
				_getUserReference();
				swipe_refresh_layout.setRefreshing(false);
			}
		});
		
		app_update_manager.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				
			}
		});
		
		app_server_manager.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				
			}
		});
		
		app_user_manager.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				intent.setClass(getApplicationContext(), ManageUsersActivity.class);
				startActivity(intent);
			}
		});
		
		app_logout.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				
			}
		});
		
		app_users_count.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				intent.setClass(getApplicationContext(), AllUsersActivity.class);
				startActivity(intent);
			}
		});
		
		app_online_users_count.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				intent.setClass(getApplicationContext(), OnlineUsersActivity.class);
				startActivity(intent);
			}
		});
		
		app_banned_users_count.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				intent.setClass(getApplicationContext(), BannedUsersActivity.class);
				startActivity(intent);
			}
		});
		
		app_premium_users_count.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				intent.setClass(getApplicationContext(), PremiumUsersActivity.class);
				startActivity(intent);
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
		app_user_info_profile_card.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b) { this.setCornerRadius(a); this.setColor(b); return this; } }.getIns((int)300, Color.TRANSPARENT));
		_viewGraphics(app_users_count, 0xFFF5F5F5, 0xFFE0E0E0, 28, 0, 0xFFE0E0E0);
		_viewGraphics(app_online_users_count, 0xFFF5F5F5, 0xFFE0E0E0, 28, 0, 0xFFE0E0E0);
		_viewGraphics(app_banned_users_count, 0xFFF5F5F5, 0xFFE0E0E0, 28, 0, 0xFFE0E0E0);
		_viewGraphics(app_premium_users_count, 0xFFF5F5F5, 0xFFE0E0E0, 28, 0, 0xFFE0E0E0);
		_viewGraphics(app_update_manager, 0xFFFFFFFF, 0xFFEEEEEE, 28, 3, 0xFFE0E0E0);
		_viewGraphics(app_server_manager, 0xFFFFFFFF, 0xFFEEEEEE, 28, 3, 0xFFE0E0E0);
		_viewGraphics(app_user_manager, 0xFFFFFFFF, 0xFFEEEEEE, 28, 3, 0xFFE0E0E0);
		_viewGraphics(app_logout, 0xFFFFFFFF, 0xFFEF9A9A, 28, 3, 0xFFEF9A9A);
		_ImageColor(app_logout_arrow, 0xFFF44336);
		_getUserReference();
		
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
	
	
	public void _ImageColor(final ImageView _image, final int _color) {
		_image.setColorFilter(_color,PorterDuff.Mode.SRC_ATOP);
	}
	
	
	public void _getUserReference() {
		_LoadingDialog(true);
		scroll_body.setVisibility(View.GONE);
		{
				ExecutorService mExecutorService = Executors.newSingleThreadExecutor();
				Handler mMainHandler = new Handler(Looper.getMainLooper());
				
				mExecutorService.execute(new Runnable() {
						@Override
						public void run() {
								DatabaseReference getUserReference = FirebaseDatabase.getInstance().getReference("skyline/users").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
								getUserReference.addListenerForSingleValueEvent(new ValueEventListener() {
										@Override
										public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
												mMainHandler.post(new Runnable() {
														@Override
														public void run() {
																if (dataSnapshot.exists()) {
																		_LoadingDialog(false);
																		scroll_body.setVisibility(View.VISIBLE);
																		
																		if (dataSnapshot.child("avatar").getValue(String.class).equals("null")) {
																				app_user_info_profile_image.setImageResource(R.drawable.avatar);
																		} else {
																				Glide.with(getApplicationContext()).load(Uri.parse(dataSnapshot.child("avatar").getValue(String.class))).into(app_user_info_profile_image);
																		}
																		if (dataSnapshot.child("nickname").getValue(String.class).equals("null")) {
																				app_user_info_username.setText("@" + dataSnapshot.child("username").getValue(String.class));
																		} else {
																				app_user_info_username.setText(dataSnapshot.child("nickname").getValue(String.class));
																		}
																		if (dataSnapshot.child("gender").getValue(String.class).equals("hidden")) {
																				app_user_info_gender.setVisibility(View.GONE);
																		} else {
																				if (dataSnapshot.child("gender").getValue(String.class).equals("male")) {
																						app_user_info_gender.setImageResource(R.drawable.male_badge);
																						app_user_info_gender.setVisibility(View.VISIBLE);
																				} else {
																						if (dataSnapshot.child("gender").getValue(String.class).equals("female")) {
																								app_user_info_gender.setImageResource(R.drawable.female_badge);
																								app_user_info_gender.setVisibility(View.VISIBLE);
																						}
																				}
																		}
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
		
		_getTopFourCategoryRef();
	}
	
	
	public void _getTopFourCategoryRef() {
		app_users_count_num.setText("Loading...");
		app_online_users_count_num.setText("Loading...");
		app_banned_users_count_num.setText("Loading...");
		app_premium_users_count_num.setText("Loading...");
		{
				ExecutorService mExecutorService = Executors.newSingleThreadExecutor();
				Handler mMainHandler = new Handler(Looper.getMainLooper());
				
				mExecutorService.execute(new Runnable() {
						@Override
						public void run() {
								Query getAppCountRef = FirebaseDatabase.getInstance().getReference("skyline/users");
								getAppCountRef.addListenerForSingleValueEvent(new ValueEventListener() {
										@Override
										public void onDataChange(DataSnapshot dataSnapshot) {
												mMainHandler.post(new Runnable() {
														@Override
														public void run() {
																long mCount = dataSnapshot.getChildrenCount();
									                            _setCount(app_users_count_num, (double) mCount);
														}
												});
										}
										
										@Override
										public void onCancelled(DatabaseError databaseError) {
												
										}
								});
						}
				});
		}
		{
				ExecutorService mExecutorService = Executors.newSingleThreadExecutor();
				Handler mMainHandler = new Handler(Looper.getMainLooper());
				
				mExecutorService.execute(new Runnable() {
						@Override
						public void run() {
								Query getAppCountRef = FirebaseDatabase.getInstance().getReference("skyline/users").orderByChild("status").equalTo("online");
								getAppCountRef.addListenerForSingleValueEvent(new ValueEventListener() {
										@Override
										public void onDataChange(DataSnapshot dataSnapshot) {
												mMainHandler.post(new Runnable() {
														@Override
														public void run() {
																long mCount = dataSnapshot.getChildrenCount();
									                            _setCount(app_online_users_count_num, (double) mCount);
														}
												});
										}
										
										@Override
										public void onCancelled(DatabaseError databaseError) {
												
										}
								});
						}
				});
		}
		{
				ExecutorService mExecutorService = Executors.newSingleThreadExecutor();
				Handler mMainHandler = new Handler(Looper.getMainLooper());
				
				mExecutorService.execute(new Runnable() {
						@Override
						public void run() {
								Query getAppCountRef = FirebaseDatabase.getInstance().getReference("skyline/users").orderByChild("banned").equalTo("true");
								getAppCountRef.addListenerForSingleValueEvent(new ValueEventListener() {
										@Override
										public void onDataChange(DataSnapshot dataSnapshot) {
												mMainHandler.post(new Runnable() {
														@Override
														public void run() {
																long mCount = dataSnapshot.getChildrenCount();
									                            _setCount(app_banned_users_count_num, (double) mCount);
														}
												});
										}
										
										@Override
										public void onCancelled(DatabaseError databaseError) {
												
										}
								});
						}
				});
		}
		{
				ExecutorService mExecutorService = Executors.newSingleThreadExecutor();
				Handler mMainHandler = new Handler(Looper.getMainLooper());
				
				mExecutorService.execute(new Runnable() {
						@Override
						public void run() {
								Query getAppCountRef = FirebaseDatabase.getInstance().getReference("skyline/users").orderByChild("account_premium").equalTo("true");
								getAppCountRef.addListenerForSingleValueEvent(new ValueEventListener() {
										@Override
										public void onDataChange(DataSnapshot dataSnapshot) {
												mMainHandler.post(new Runnable() {
														@Override
														public void run() {
																long mCount = dataSnapshot.getChildrenCount();
									                            _setCount(app_premium_users_count_num, (double) mCount);
														}
												});
										}
										
										@Override
										public void onCancelled(DatabaseError databaseError) {
												
										}
								});
						}
				});
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
