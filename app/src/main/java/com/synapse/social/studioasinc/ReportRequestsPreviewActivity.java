package com.synapse.social.studioasinc;

import android.animation.*;
import android.app.*;
import android.app.AlertDialog;
import android.content.*;
import android.content.DialogInterface;
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

public class ReportRequestsPreviewActivity extends AppCompatActivity {
	
	private FirebaseDatabase _firebase = FirebaseDatabase.getInstance();
	
	private HashMap<String, Object> UserInfoCacheMap = new HashMap<>();
	
	private ArrayList<HashMap<String, Object>> mAllUsersList = new ArrayList<>();
	
	private LinearLayout body;
	private LinearLayout top;
	private LinearLayout middle;
	private LinearLayout bottom_buttons;
	private ImageView mBack;
	private TextView mTitle;
	private ImageView spc;
	private SwipeRefreshLayout mSwipeLayout;
	private LinearLayout mLoadingBody;
	private LinearLayout mSwipeLayoutBody;
	private RecyclerView mUsersList;
	private TextView mNoUsersInfo;
	private ProgressBar mLoadingBodyBar;
	private TextView cancel_request;
	private TextView accept_request;
	
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
	private AlertDialog.Builder rejectDialog;
	private AlertDialog.Builder acceptDialog;
	
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.report_requests_preview);
		initialize(_savedInstanceState);
		FirebaseApp.initializeApp(this);
		initializeLogic();
	}
	
	private void initialize(Bundle _savedInstanceState) {
		body = findViewById(R.id.body);
		top = findViewById(R.id.top);
		middle = findViewById(R.id.middle);
		bottom_buttons = findViewById(R.id.bottom_buttons);
		mBack = findViewById(R.id.mBack);
		mTitle = findViewById(R.id.mTitle);
		spc = findViewById(R.id.spc);
		mSwipeLayout = findViewById(R.id.mSwipeLayout);
		mLoadingBody = findViewById(R.id.mLoadingBody);
		mSwipeLayoutBody = findViewById(R.id.mSwipeLayoutBody);
		mUsersList = findViewById(R.id.mUsersList);
		mNoUsersInfo = findViewById(R.id.mNoUsersInfo);
		mLoadingBodyBar = findViewById(R.id.mLoadingBodyBar);
		cancel_request = findViewById(R.id.cancel_request);
		accept_request = findViewById(R.id.accept_request);
		auth = FirebaseAuth.getInstance();
		rejectDialog = new AlertDialog.Builder(this);
		acceptDialog = new AlertDialog.Builder(this);
		
		mBack.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				onBackPressed();
			}
		});
		
		mSwipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
			@Override
			public void onRefresh() {
				_getUsersReference();
			}
		});
		
		cancel_request.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				rejectDialog.setTitle("Reject Report");
				rejectDialog.setMessage("Are you sure you want to reject this report?");
				rejectDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface _dialog, int _which) {
						maindb.child("skyline/admin/user-reports/".concat(getIntent().getStringExtra("uid"))).removeValue();
						SketchwareUtil.showMessage(getApplicationContext(), "Report Rejected");
						finish();
					}
				});
				rejectDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface _dialog, int _which) {
						
					}
				});
				rejectDialog.create().show();
			}
		});
		
		accept_request.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				rejectDialog.setTitle("Accept Report");
				rejectDialog.setMessage("Are you sure you want to accept the report? Once accepted the reported user will be banned.");
				rejectDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface _dialog, int _which) {
						FirebaseDatabase.getInstance().getReference("skyline/users").child(getIntent().getStringExtra("uid")).child("banned").setValue("true");
						maindb.child("skyline/admin/user-reports/".concat(getIntent().getStringExtra("uid"))).removeValue();
						SketchwareUtil.showMessage(getApplicationContext(), "Report accepted and user banned");
						finish();
					}
				});
				rejectDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface _dialog, int _which) {
						
					}
				});
				rejectDialog.create().show();
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
		top.setElevation((float)4);
		_viewGraphics(mBack, 0xFFFFFFFF, 0xFFEEEEEE, 300, 0, Color.TRANSPARENT);
		mUsersList.setLayoutManager(new LinearLayoutManager(this));
		mUsersList.setAdapter(new MUsersListAdapter(mAllUsersList));
		_viewGraphics(cancel_request, 0xFFF5F5F5, 0xFFE0E0E0, 300, 0, Color.TRANSPARENT);
		_viewGraphics(accept_request, 0xFFF50057, 0xFFC2185B, 300, 0, Color.TRANSPARENT);
		_getUsersReference();
		
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
	
	
	public void _getUsersReference() {
		mSwipeLayout.setVisibility(View.GONE);
		mUsersList.setVisibility(View.GONE);
		mNoUsersInfo.setVisibility(View.GONE);
		mLoadingBody.setVisibility(View.VISIBLE);
		{
				ExecutorService mExecutorService = Executors.newSingleThreadExecutor();
				Handler mMainHandler = new Handler(Looper.getMainLooper());
				
				mExecutorService.execute(new Runnable() {
						@Override
						public void run() {
								Query getUsersList = FirebaseDatabase.getInstance().getReference("skyline/admin/user-reports").child(getIntent().getStringExtra("uid")).child("reporter-users");
								getUsersList.addListenerForSingleValueEvent(new ValueEventListener() {
										@Override
										public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
												mMainHandler.post(new Runnable() {
														@Override
														public void run() {
																if(dataSnapshot.exists()) {
																		mSwipeLayout.setVisibility(View.VISIBLE);
																		mUsersList.setVisibility(View.VISIBLE);
																		mNoUsersInfo.setVisibility(View.GONE);
																		mLoadingBody.setVisibility(View.GONE);
																		
																		mAllUsersList.clear();
																		try {
																				GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
																				for (DataSnapshot _data : dataSnapshot.getChildren()) {
																						HashMap<String, Object> _map = _data.getValue(_ind);
																						mAllUsersList.add(_map);
																				}
																		} catch (Exception _e) {
																				_e.printStackTrace();
																		}
																		
																		mUsersList.getAdapter().notifyDataSetChanged();
																} else {
																		mSwipeLayout.setVisibility(View.VISIBLE);
																		mUsersList.setVisibility(View.GONE);
																		mNoUsersInfo.setVisibility(View.VISIBLE);
																		mLoadingBody.setVisibility(View.GONE);
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
		
		mSwipeLayout.setRefreshing(false);
	}
	
	
	public void _setAgoTime(final double _currentTime, final TextView _txt) {
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
	
	public class MUsersListAdapter extends RecyclerView.Adapter<MUsersListAdapter.ViewHolder> {
		
		ArrayList<HashMap<String, Object>> _data;
		
		public MUsersListAdapter(ArrayList<HashMap<String, Object>> _arr) {
			_data = _arr;
		}
		
		@Override
		public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
			LayoutInflater _inflater = getLayoutInflater();
			View _v = _inflater.inflate(R.layout.user_request_preview_bind, null);
			RecyclerView.LayoutParams _lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
			_v.setLayoutParams(_lp);
			return new ViewHolder(_v);
		}
		
		@Override
		public void onBindViewHolder(ViewHolder _holder, final int _position) {
			View _view = _holder.itemView;
			
			final LinearLayout body = _view.findViewById(R.id.body);
			final LinearLayout main = _view.findViewById(R.id.main);
			final LinearLayout spc = _view.findViewById(R.id.spc);
			final LinearLayout user_info = _view.findViewById(R.id.user_info);
			final LinearLayout request_body = _view.findViewById(R.id.request_body);
			final androidx.cardview.widget.CardView user_profile_card = _view.findViewById(R.id.user_profile_card);
			final LinearLayout user_right = _view.findViewById(R.id.user_right);
			final ImageView user_go_profile_arrow = _view.findViewById(R.id.user_go_profile_arrow);
			final ImageView user_profile_image = _view.findViewById(R.id.user_profile_image);
			final LinearLayout user_right_layout = _view.findViewById(R.id.user_right_layout);
			final TextView user_username = _view.findViewById(R.id.user_username);
			final TextView user_nickname = _view.findViewById(R.id.user_nickname);
			final ImageView user_gender = _view.findViewById(R.id.user_gender);
			final ImageView user_verified = _view.findViewById(R.id.user_verified);
			final TextView reporter_user_message = _view.findViewById(R.id.reporter_user_message);
			final LinearLayout request_category_layout = _view.findViewById(R.id.request_category_layout);
			final LinearLayout report_send_date_layout = _view.findViewById(R.id.report_send_date_layout);
			final TextView request_category_layout_title = _view.findViewById(R.id.request_category_layout_title);
			final TextView request_category_layout_category = _view.findViewById(R.id.request_category_layout_category);
			final TextView report_send_date_layout_title = _view.findViewById(R.id.report_send_date_layout_title);
			final TextView report_send_date_layout_time = _view.findViewById(R.id.report_send_date_layout_time);
			
			RecyclerView.LayoutParams _lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
			_view.setLayoutParams(_lp);
			main.setVisibility(View.GONE);
			user_profile_card.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b) { this.setCornerRadius(a); this.setColor(b); return this; } }.getIns((int)300, Color.TRANSPARENT));
			if (UserInfoCacheMap.containsKey("uid-".concat(_data.get((int)_position).get("report_sender_uid").toString()))) {
				if (UserInfoCacheMap.get("banned-".concat(_data.get((int)_position).get("report_sender_uid").toString())).toString().equals("true")) {
						user_profile_image.setImageResource(R.drawable.banned_avatar);
				} else {
						if (UserInfoCacheMap.get("avatar-".concat(_data.get((int)_position).get("report_sender_uid").toString())).toString().equals("null")) {
								user_profile_image.setImageResource(R.drawable.avatar);
						} else {
								Glide.with(getApplicationContext()).load(Uri.parse(UserInfoCacheMap.get("avatar-".concat(_data.get((int)_position).get("report_sender_uid").toString())).toString())).into(user_profile_image);
						}
				}
				if (UserInfoCacheMap.get("nickname-".concat(_data.get((int)_position).get("report_sender_uid").toString())).toString().equals("null")) {
						user_nickname.setText("@" + UserInfoCacheMap.get("username-".concat(_data.get((int)_position).get("report_sender_uid").toString())).toString());
				} else {
						user_nickname.setText(UserInfoCacheMap.get("nickname-".concat(_data.get((int)_position).get("report_sender_uid").toString())).toString());
				}
				user_username.setText("@" + UserInfoCacheMap.get("username-".concat(_data.get((int)_position).get("report_sender_uid").toString())).toString());
				if (UserInfoCacheMap.get("gender-".concat(_data.get((int)_position).get("report_sender_uid").toString())).toString().equals("hidden")) {
						user_gender.setVisibility(View.GONE);
				} else {
						if (UserInfoCacheMap.get("gender-".concat(_data.get((int)_position).get("report_sender_uid").toString())).toString().equals("male")) {
								user_gender.setImageResource(R.drawable.male_badge);
								user_gender.setVisibility(View.VISIBLE);
						} else {
								if (UserInfoCacheMap.get("gender-".concat(_data.get((int)_position).get("report_sender_uid").toString())).toString().equals("female")) {
										user_gender.setImageResource(R.drawable.female_badge);
										user_gender.setVisibility(View.VISIBLE);
								}
						}
				}
				if (UserInfoCacheMap.get("account_type-".concat(_data.get((int)_position).get("report_sender_uid").toString())).toString().equals("admin")) {
						user_verified.setImageResource(R.drawable.admin_badge);
						user_verified.setVisibility(View.VISIBLE);
				} else {
						if (UserInfoCacheMap.get("account_type-".concat(_data.get((int)_position).get("report_sender_uid").toString())).toString().equals("moderator")) {
								user_verified.setImageResource(R.drawable.moderator_badge);
								user_verified.setVisibility(View.VISIBLE);
						} else {
								if (UserInfoCacheMap.get("account_type-".concat(_data.get((int)_position).get("report_sender_uid").toString())).toString().equals("support")) {
										user_verified.setImageResource(R.drawable.support_badge);
										user_verified.setVisibility(View.VISIBLE);
								} else {
										if (UserInfoCacheMap.get("account_premium-".concat(_data.get((int)_position).get("report_sender_uid").toString())).toString().equals("true")) {
												user_verified.setImageResource(R.drawable.premium_badge);
												user_verified.setVisibility(View.VISIBLE);
										} else {
												if (UserInfoCacheMap.get("verify-".concat(_data.get((int)_position).get("report_sender_uid").toString())).toString().equals("true")) {
														user_verified.setImageResource(R.drawable.verified_badge);
														user_verified.setVisibility(View.VISIBLE);
												} else {
														user_verified.setVisibility(View.GONE);
												}
										}
								}
						}
				}
				main.setVisibility(View.VISIBLE);
			} else {
				{
						ExecutorService mExecutorService = Executors.newSingleThreadExecutor();
						Handler mMainHandler = new Handler(Looper.getMainLooper());
						
						mExecutorService.execute(new Runnable() {
								@Override
								public void run() {
										DatabaseReference getUserReference = FirebaseDatabase.getInstance().getReference("skyline/users").child(_data.get((int)_position).get("report_sender_uid").toString());
										getUserReference.addListenerForSingleValueEvent(new ValueEventListener() {
												@Override
												public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
														mMainHandler.post(new Runnable() {
																@Override
																public void run() {
																		if(dataSnapshot.exists()) {
																				UserInfoCacheMap.put("uid-".concat(_data.get((int)_position).get("report_sender_uid").toString()), _data.get((int)_position).get("report_sender_uid").toString());
																				UserInfoCacheMap.put("avatar-".concat(_data.get((int)_position).get("report_sender_uid").toString()), dataSnapshot.child("avatar").getValue(String.class));
																				UserInfoCacheMap.put("banned-".concat(_data.get((int)_position).get("report_sender_uid").toString()), dataSnapshot.child("banned").getValue(String.class));
																				UserInfoCacheMap.put("username-".concat(_data.get((int)_position).get("report_sender_uid").toString()), dataSnapshot.child("username").getValue(String.class));
																				UserInfoCacheMap.put("nickname-".concat(_data.get((int)_position).get("report_sender_uid").toString()), dataSnapshot.child("nickname").getValue(String.class));
																				UserInfoCacheMap.put("status-".concat(_data.get((int)_position).get("report_sender_uid").toString()), dataSnapshot.child("status").getValue(String.class));
																				UserInfoCacheMap.put("gender-".concat(_data.get((int)_position).get("report_sender_uid").toString()), dataSnapshot.child("gender").getValue(String.class));
																				UserInfoCacheMap.put("account_type-".concat(_data.get((int)_position).get("report_sender_uid").toString()), dataSnapshot.child("account_type").getValue(String.class));
																				UserInfoCacheMap.put("account_premium-".concat(_data.get((int)_position).get("report_sender_uid").toString()), dataSnapshot.child("account_premium").getValue(String.class));
																				UserInfoCacheMap.put("verify-".concat(_data.get((int)_position).get("report_sender_uid").toString()), dataSnapshot.child("verify").getValue(String.class));
												                                
																				if (UserInfoCacheMap.get("banned-".concat(_data.get((int)_position).get("report_sender_uid").toString())).toString().equals("true")) {
																						user_profile_image.setImageResource(R.drawable.banned_avatar);
																				} else {
																						if (UserInfoCacheMap.get("avatar-".concat(_data.get((int)_position).get("report_sender_uid").toString())).toString().equals("null")) {
																								user_profile_image.setImageResource(R.drawable.avatar);
																						} else {
																								Glide.with(getApplicationContext()).load(Uri.parse(UserInfoCacheMap.get("avatar-".concat(_data.get((int)_position).get("report_sender_uid").toString())).toString())).into(user_profile_image);
																						}
																				}
																				if (UserInfoCacheMap.get("nickname-".concat(_data.get((int)_position).get("report_sender_uid").toString())).toString().equals("null")) {
																						user_nickname.setText("@" + UserInfoCacheMap.get("username-".concat(_data.get((int)_position).get("report_sender_uid").toString())).toString());
																				} else {
																						user_nickname.setText(UserInfoCacheMap.get("nickname-".concat(_data.get((int)_position).get("report_sender_uid").toString())).toString());
																				}
																				user_username.setText("@" + UserInfoCacheMap.get("username-".concat(_data.get((int)_position).get("report_sender_uid").toString())).toString());
																				if (UserInfoCacheMap.get("gender-".concat(_data.get((int)_position).get("report_sender_uid").toString())).toString().equals("hidden")) {
																						user_gender.setVisibility(View.GONE);
																				} else {
																						if (UserInfoCacheMap.get("gender-".concat(_data.get((int)_position).get("report_sender_uid").toString())).toString().equals("male")) {
																								user_gender.setImageResource(R.drawable.male_badge);
																								user_gender.setVisibility(View.VISIBLE);
																						} else {
																								if (UserInfoCacheMap.get("gender-".concat(_data.get((int)_position).get("report_sender_uid").toString())).toString().equals("female")) {
																										user_gender.setImageResource(R.drawable.female_badge);
																										user_gender.setVisibility(View.VISIBLE);
																								}
																						}
																				}
																				if (UserInfoCacheMap.get("account_type-".concat(_data.get((int)_position).get("report_sender_uid").toString())).toString().equals("admin")) {
																						user_verified.setImageResource(R.drawable.admin_badge);
																						user_verified.setVisibility(View.VISIBLE);
																				} else {
																						if (UserInfoCacheMap.get("account_type-".concat(_data.get((int)_position).get("report_sender_uid").toString())).toString().equals("moderator")) {
																								user_verified.setImageResource(R.drawable.moderator_badge);
																								user_verified.setVisibility(View.VISIBLE);
																						} else {
																								if (UserInfoCacheMap.get("account_type-".concat(_data.get((int)_position).get("report_sender_uid").toString())).toString().equals("support")) {
																										user_verified.setImageResource(R.drawable.support_badge);
																										user_verified.setVisibility(View.VISIBLE);
																								} else {
																										if (UserInfoCacheMap.get("account_premium-".concat(_data.get((int)_position).get("report_sender_uid").toString())).toString().equals("true")) {
																												user_verified.setImageResource(R.drawable.premium_badge);
																												user_verified.setVisibility(View.VISIBLE);
																										} else {
																												if (UserInfoCacheMap.get("verify-".concat(_data.get((int)_position).get("report_sender_uid").toString())).toString().equals("true")) {
																														user_verified.setImageResource(R.drawable.verified_badge);
																														user_verified.setVisibility(View.VISIBLE);
																												} else {
																														user_verified.setVisibility(View.GONE);
																												}
																										}
																								}
																						}
																				}
																				main.setVisibility(View.VISIBLE);
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
			if (_data.get((int)_position).containsKey("report_message")) {
				reporter_user_message.setText(_data.get((int)_position).get("report_message").toString());
				reporter_user_message.setVisibility(View.VISIBLE);
			} else {
				reporter_user_message.setVisibility(View.GONE);
			}
			if (_data.get((int)_position).get("report_category").toString().equals("CATEGORY_1")) {
				request_category_layout_category.setText("Sexual harassment");
			} else {
				if (_data.get((int)_position).get("report_category").toString().equals("CATEGORY_2")) {
					request_category_layout_category.setText("Misleading Behavior and Spam");
				} else {
					if (_data.get((int)_position).get("report_category").toString().equals("CATEGORY_3")) {
						request_category_layout_category.setText("Nudity or Sexual Content");
					} else {
						if (_data.get((int)_position).get("report_category").toString().equals("CATEGORY_4")) {
							request_category_layout_category.setText("Grudge, hatred or other behavior");
						} else {
							if (_data.get((int)_position).get("report_category").toString().equals("CATEGORY_5")) {
								request_category_layout_category.setText("Fraud");
							} else {
								request_category_layout_category.setText("Other");
							}
						}
					}
				}
			}
			_setAgoTime(Double.parseDouble(_data.get((int)_position).get("report_open_date").toString()), report_send_date_layout_time);
			user_info.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View _view) {
					intent.setClass(getApplicationContext(), PreviewUserProfileActivity.class);
					intent.putExtra("uid", _data.get((int)_position).get("report_sender_uid").toString());
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