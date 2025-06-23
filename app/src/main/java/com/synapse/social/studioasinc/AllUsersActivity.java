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

public class AllUsersActivity extends AppCompatActivity {
	
	private FirebaseDatabase _firebase = FirebaseDatabase.getInstance();
	
	private ArrayList<HashMap<String, Object>> mAllUsersList = new ArrayList<>();
	
	private LinearLayout body;
	private LinearLayout top;
	private SwipeRefreshLayout mSwipeLayout;
	private LinearLayout mLoadingBody;
	private ImageView mBack;
	private TextView mTitle;
	private ImageView spc;
	private LinearLayout mSwipeLayoutBody;
	private RecyclerView mUsersList;
	private TextView mNoUsersInfo;
	private ProgressBar mLoadingBodyBar;
	
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
		setContentView(R.layout.all_users);
		initialize(_savedInstanceState);
		FirebaseApp.initializeApp(this);
		initializeLogic();
	}
	
	private void initialize(Bundle _savedInstanceState) {
		body = findViewById(R.id.body);
		top = findViewById(R.id.top);
		mSwipeLayout = findViewById(R.id.mSwipeLayout);
		mLoadingBody = findViewById(R.id.mLoadingBody);
		mBack = findViewById(R.id.mBack);
		mTitle = findViewById(R.id.mTitle);
		spc = findViewById(R.id.spc);
		mSwipeLayoutBody = findViewById(R.id.mSwipeLayoutBody);
		mUsersList = findViewById(R.id.mUsersList);
		mNoUsersInfo = findViewById(R.id.mNoUsersInfo);
		mLoadingBodyBar = findViewById(R.id.mLoadingBodyBar);
		auth = FirebaseAuth.getInstance();
		
		mSwipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
			@Override
			public void onRefresh() {
				_getUsersReference();
			}
		});
		
		mBack.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				onBackPressed();
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
		_getUsersReference();
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
								Query getUsersList = FirebaseDatabase.getInstance().getReference("skyline/users");
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
	
	public class MUsersListAdapter extends RecyclerView.Adapter<MUsersListAdapter.ViewHolder> {
		
		ArrayList<HashMap<String, Object>> _data;
		
		public MUsersListAdapter(ArrayList<HashMap<String, Object>> _arr) {
			_data = _arr;
		}
		
		@Override
		public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
			LayoutInflater _inflater = getLayoutInflater();
			View _v = _inflater.inflate(R.layout.users_list, null);
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
			name.setText("@" + _data.get((int)_position).get("username").toString());
			if (_data.get((int)_position).get("banned").toString().equals("true")) {
				profileAvatar.setImageResource(R.drawable.banned_avatar);
			} else {
				if (_data.get((int)_position).get("avatar").toString().equals("null")) {
					profileAvatar.setImageResource(R.drawable.avatar);
				} else {
					Glide.with(getApplicationContext()).load(Uri.parse(_data.get((int)_position).get("avatar").toString())).into(profileAvatar);
				}
			}
			if (_data.get((int)_position).get("nickname").toString().equals("null")) {
				username.setText("@" + _data.get((int)_position).get("username").toString());
			} else {
				username.setText(_data.get((int)_position).get("nickname").toString());
			}
			if (_data.get((int)_position).get("gender").toString().equals("hidden")) {
				genderBadge.setVisibility(View.GONE);
			} else {
				if (_data.get((int)_position).get("gender").toString().equals("male")) {
					genderBadge.setImageResource(R.drawable.male_badge);
					genderBadge.setVisibility(View.VISIBLE);
				} else {
					if (_data.get((int)_position).get("gender").toString().equals("female")) {
						genderBadge.setImageResource(R.drawable.female_badge);
						genderBadge.setVisibility(View.VISIBLE);
					}
				}
			}
			if (_data.get((int)_position).get("account_type").toString().equals("admin")) {
				badge.setImageResource(R.drawable.admin_badge);
				badge.setVisibility(View.VISIBLE);
			} else {
				if (_data.get((int)_position).get("account_type").toString().equals("moderator")) {
					badge.setImageResource(R.drawable.moderator_badge);
					badge.setVisibility(View.VISIBLE);
				} else {
					if (_data.get((int)_position).get("account_type").toString().equals("support")) {
						badge.setImageResource(R.drawable.support_badge);
						badge.setVisibility(View.VISIBLE);
					} else {
						if (_data.get((int)_position).get("account_type").toString().equals("user")) {
							if (_data.get((int)_position).get("account_premium").toString().equals("true")) {
								badge.setImageResource(R.drawable.premium_badge);
								badge.setVisibility(View.VISIBLE);
							} else {
								if (_data.get((int)_position).get("verify").toString().equals("true")) {
									badge.setImageResource(R.drawable.verified_badge);
									badge.setVisibility(View.VISIBLE);
								} else {
									badge.setVisibility(View.GONE);
								}
							}
						}
					}
				}
			}
			if (_data.get((int)_position).get("status").toString().equals("online")) {
				userStatusCircleBG.setVisibility(View.VISIBLE);
			} else {
				userStatusCircleBG.setVisibility(View.GONE);
			}
			body.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View _view) {
					intent.setClass(getApplicationContext(), PreviewUserProfileActivity.class);
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