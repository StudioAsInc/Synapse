package com.synapse.social.studioasinc;

import android.Manifest;
import android.animation.*;
import android.app.*;
import android.app.Activity;
import android.content.*;
import android.content.ClipData;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidmads.library.qrgenearator.*;
import androidx.annotation.*;
import androidx.appcompat.app.AppCompatActivity;
import androidx.asynclayoutinflater.*;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
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
import com.synapse.social.studioasinc.FadeEditText;
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
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.*;
import kr.co.prnd.readmore.*;
import me.dm7.barcodescanner.core.*;
import org.jetbrains.kotlin.*;
import org.json.*;

public class GlobalGroupActivity extends AppCompatActivity {
	
	public final int REQ_CD_GROUP_SEND_PHOTO = 101;
	
	private Timer _timer = new Timer();
	private FirebaseDatabase _firebase = FirebaseDatabase.getInstance();
	
	private HashMap<String, Object> map = new HashMap<>();
	private double click = 0;
	private double seen = 0;
	private String myuid = "";
	private String fontName = "";
	private String typeace = "";
	private String push_key = "";
	private String group_key = "";
	private String chat_key = "";
	private String UserAvatarUri = "";
	private double file_type_expand = 0;
	private double block_switch = 0;
	
	private ArrayList<String> delete = new ArrayList<>();
	private ArrayList<HashMap<String, Object>> listmap = new ArrayList<>();
	
	private LinearLayout BG;
	private LinearLayout bar;
	private RecyclerView recyclerview1;
	private LinearLayout mMessageReplyLayout;
	private LinearLayout message_input_overall_container;
	private LinearLayout bottomAudioRecorder;
	private TextView unblock_btn;
	private TextView blocked_txt;
	private ImageView back;
	private LinearLayout linear8;
	private ImageView imageview_del;
	private ImageView imageview_help;
	private ImageView user_dp;
	private LinearLayout linear12;
	private ImageView verified;
	private TextView group_name_txt;
	private TextView group_creator_txt;
	private LinearLayout mMessageReplyLayoutBody;
	private LinearLayout mMessageReplyLayoutSpace;
	private ImageView mMessageReplyLayoutBodyIc;
	private LinearLayout mMessageReplyLayoutBodyRight;
	private ImageView mMessageReplyLayoutBodyCancel;
	private TextView mMessageReplyLayoutBodyRightUsername;
	private TextView mMessageReplyLayoutBodyRightMessage;
	private LinearLayout message_input_outlined_round;
	private LinearLayout send_round_btn;
	private LinearLayout img_container_layout;
	private FadeEditText message_et;
	private LinearLayout camera_gallery_btn_container_round;
	private CardView imgRoundLayout;
	private ImageView remove_selected_img_icon;
	private LinearLayout img_name_container;
	private ImageView selected_img_preview;
	private TextView img_name;
	private ProgressBar img_upload_prog;
	private ImageView expand_send_type_btn;
	private LinearLayout devider_mic_camera;
	private ImageView gallery_btn;
	private LinearLayout devider;
	private ImageView attachment_btn;
	private LinearLayout devider1;
	private ImageView send_type_voice_btn;
	private LinearLayout devider2;
	private ImageView more_send_type_btn;
	private ImageView send_ic;
	private ImageView bottomAudioRecorderCancel;
	private TextView bottomAudioRecorderTime;
	private ImageView bottomAudioRecorderSend;
	
	private FirebaseAuth fauth;
	private OnCompleteListener<AuthResult> _fauth_create_user_listener;
	private OnCompleteListener<AuthResult> _fauth_sign_in_listener;
	private OnCompleteListener<Void> _fauth_reset_password_listener;
	private OnCompleteListener<Void> fauth_updateEmailListener;
	private OnCompleteListener<Void> fauth_updatePasswordListener;
	private OnCompleteListener<Void> fauth_emailVerificationSentListener;
	private OnCompleteListener<Void> fauth_deleteUserListener;
	private OnCompleteListener<Void> fauth_updateProfileListener;
	private OnCompleteListener<AuthResult> fauth_phoneAuthListener;
	private OnCompleteListener<AuthResult> fauth_googleSignInListener;
	private TimerTask time_loading;
	private SharedPreferences info;
	private Calendar cal = Calendar.getInstance();
	private Intent intent = new Intent();
	private DatabaseReference main = _firebase.getReference("skyline");
	private ChildEventListener _main_child_listener;
	private Intent group_send_photo = new Intent(Intent.ACTION_GET_CONTENT);
	private DatabaseReference groups = _firebase.getReference("synapse/groups");
	private ChildEventListener _groups_child_listener;
	private DatabaseReference group_chat = _firebase.getReference("synapse/group_chat");
	private ChildEventListener _group_chat_child_listener;
	
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.global_group);
		initialize(_savedInstanceState);
		FirebaseApp.initializeApp(this);
		
		if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
			ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.READ_EXTERNAL_STORAGE}, 1000);
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
		BG = findViewById(R.id.BG);
		bar = findViewById(R.id.bar);
		recyclerview1 = findViewById(R.id.recyclerview1);
		mMessageReplyLayout = findViewById(R.id.mMessageReplyLayout);
		message_input_overall_container = findViewById(R.id.message_input_overall_container);
		bottomAudioRecorder = findViewById(R.id.bottomAudioRecorder);
		unblock_btn = findViewById(R.id.unblock_btn);
		blocked_txt = findViewById(R.id.blocked_txt);
		back = findViewById(R.id.back);
		linear8 = findViewById(R.id.linear8);
		imageview_del = findViewById(R.id.imageview_del);
		imageview_help = findViewById(R.id.imageview_help);
		user_dp = findViewById(R.id.user_dp);
		linear12 = findViewById(R.id.linear12);
		verified = findViewById(R.id.verified);
		group_name_txt = findViewById(R.id.group_name_txt);
		group_creator_txt = findViewById(R.id.group_creator_txt);
		mMessageReplyLayoutBody = findViewById(R.id.mMessageReplyLayoutBody);
		mMessageReplyLayoutSpace = findViewById(R.id.mMessageReplyLayoutSpace);
		mMessageReplyLayoutBodyIc = findViewById(R.id.mMessageReplyLayoutBodyIc);
		mMessageReplyLayoutBodyRight = findViewById(R.id.mMessageReplyLayoutBodyRight);
		mMessageReplyLayoutBodyCancel = findViewById(R.id.mMessageReplyLayoutBodyCancel);
		mMessageReplyLayoutBodyRightUsername = findViewById(R.id.mMessageReplyLayoutBodyRightUsername);
		mMessageReplyLayoutBodyRightMessage = findViewById(R.id.mMessageReplyLayoutBodyRightMessage);
		message_input_outlined_round = findViewById(R.id.message_input_outlined_round);
		send_round_btn = findViewById(R.id.send_round_btn);
		img_container_layout = findViewById(R.id.img_container_layout);
		message_et = findViewById(R.id.message_et);
		camera_gallery_btn_container_round = findViewById(R.id.camera_gallery_btn_container_round);
		imgRoundLayout = findViewById(R.id.imgRoundLayout);
		remove_selected_img_icon = findViewById(R.id.remove_selected_img_icon);
		img_name_container = findViewById(R.id.img_name_container);
		selected_img_preview = findViewById(R.id.selected_img_preview);
		img_name = findViewById(R.id.img_name);
		img_upload_prog = findViewById(R.id.img_upload_prog);
		expand_send_type_btn = findViewById(R.id.expand_send_type_btn);
		devider_mic_camera = findViewById(R.id.devider_mic_camera);
		gallery_btn = findViewById(R.id.gallery_btn);
		devider = findViewById(R.id.devider);
		attachment_btn = findViewById(R.id.attachment_btn);
		devider1 = findViewById(R.id.devider1);
		send_type_voice_btn = findViewById(R.id.send_type_voice_btn);
		devider2 = findViewById(R.id.devider2);
		more_send_type_btn = findViewById(R.id.more_send_type_btn);
		send_ic = findViewById(R.id.send_ic);
		bottomAudioRecorderCancel = findViewById(R.id.bottomAudioRecorderCancel);
		bottomAudioRecorderTime = findViewById(R.id.bottomAudioRecorderTime);
		bottomAudioRecorderSend = findViewById(R.id.bottomAudioRecorderSend);
		fauth = FirebaseAuth.getInstance();
		info = getSharedPreferences("info", Activity.MODE_PRIVATE);
		group_send_photo.setType("image/*");
		group_send_photo.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
		
		recyclerview1.addOnScrollListener(new RecyclerView.OnScrollListener() {
			@Override
			public void onScrollStateChanged(RecyclerView recyclerView, int _scrollState) {
				super.onScrollStateChanged(recyclerView, _scrollState);
				
			}
			
			@Override
			public void onScrolled(RecyclerView recyclerView, int _offsetX, int _offsetY) {
				super.onScrolled(recyclerView, _offsetX, _offsetY);
				
			}
		});
		
		back.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				
			}
		});
		
		imageview_del.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				SketchwareUtil.showMessage(getApplicationContext(), "Group calling isn't available");
			}
		});
		
		imageview_help.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				SketchwareUtil.showMessage(getApplicationContext(), "Coming next");
			}
		});
		
		user_dp.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				SketchwareUtil.showMessage(getApplicationContext(), "Only admin can do this.");
			}
		});
		
		send_round_btn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				_group_send_message();
			}
		});
		
		message_et.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence _param1, int _param2, int _param3, int _param4) {
				final String _charSeq = _param1.toString();
				if (_charSeq.length() == 0) {
					if (img_container_layout.getVisibility() == View.VISIBLE) {
						message_input_outlined_round.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b, int c, int d) { this.setCornerRadius(a); this.setStroke(b, c); this.setColor(d); return this; } }.getIns((int)45, (int)2, 0xFFC7C7C7, 0xFFFFFFFF));
						_setMargin(message_et, 0, 7, 0, 0);
						send_ic.setImageResource(R.drawable.love_ic);
						_TransitionManager(message_input_overall_container, 125);
						message_input_outlined_round.setOrientation(LinearLayout.VERTICAL);
						
					} else {
						message_input_outlined_round.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b, int c, int d) { this.setCornerRadius(a); this.setStroke(b, c); this.setColor(d); return this; } }.getIns((int)100, (int)2, 0xFFC7C7C7, 0xFFFFFFFF));
						_setMargin(message_et, 0, 7, 0, 0);
						send_ic.setImageResource(R.drawable.love_ic);
						_TransitionManager(message_input_overall_container, 125);
						message_input_outlined_round.setOrientation(LinearLayout.HORIZONTAL);
						
					}
				} else {
					send_ic.setImageResource(R.drawable.icons_3);
					/*
camera_gallery_btn_container_round.setVisibility(View.GONE);
*/
					_TransitionManager(message_input_overall_container, 125);
					_setMargin(message_et, 0, 7, 0, 20);
					message_input_outlined_round.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b, int c, int d) { this.setCornerRadius(a); this.setStroke(b, c); this.setColor(d); return this; } }.getIns((int)45, (int)2, 0xFFC7C7C7, 0xFFFFFFFF));
					message_input_outlined_round.setOrientation(LinearLayout.VERTICAL);
					
				}
			}
			
			@Override
			public void beforeTextChanged(CharSequence _param1, int _param2, int _param3, int _param4) {
				
			}
			
			@Override
			public void afterTextChanged(Editable _param1) {
				
			}
		});
		
		expand_send_type_btn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				_MessageInputOnExpandBtnClicked();
			}
		});
		
		gallery_btn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				startActivityForResult(group_send_photo, REQ_CD_GROUP_SEND_PHOTO);
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
		
		_groups_child_listener = new ChildEventListener() {
			@Override
			public void onChildAdded(DataSnapshot _param1, String _param2) {
				GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
				final String _childKey = _param1.getKey();
				final HashMap<String, Object> _childValue = _param1.getValue(_ind);
				if (_childKey.equals(group_key)) {
					group_name_txt.setText(_childValue.get("group_name").toString());
					group_creator_txt.setText("Created by ".concat(_childValue.get("creator").toString()));
					Glide.with(getApplicationContext()).load(Uri.parse(_childValue.get("group_image").toString())).into(user_dp);
					setTitle(_childValue.get("group_name").toString());
				}
			}
			
			@Override
			public void onChildChanged(DataSnapshot _param1, String _param2) {
				GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
				final String _childKey = _param1.getKey();
				final HashMap<String, Object> _childValue = _param1.getValue(_ind);
				if (_childKey.equals(group_key)) {
					group_name_txt.setText(_childValue.get("group_name").toString());
					group_creator_txt.setText("Created by ".concat(_childValue.get("creator").toString()));
					Glide.with(getApplicationContext()).load(Uri.parse(_childValue.get("group_image").toString())).into(user_dp);
					setTitle(_childValue.get("group_name").toString());
				}
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
		
		_group_chat_child_listener = new ChildEventListener() {
			@Override
			public void onChildAdded(DataSnapshot _param1, String _param2) {
				GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
				final String _childKey = _param1.getKey();
				final HashMap<String, Object> _childValue = _param1.getValue(_ind);
				group_chat.addListenerForSingleValueEvent(new ValueEventListener() {
					@Override
					public void onDataChange(DataSnapshot _dataSnapshot) {
						listmap = new ArrayList<>();
						try {
							GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
							for (DataSnapshot _data : _dataSnapshot.getChildren()) {
								HashMap<String, Object> _map = _data.getValue(_ind);
								listmap.add(_map);
							}
						} catch (Exception _e) {
							_e.printStackTrace();
						}
						recyclerview1.setAdapter(new Recyclerview1Adapter(listmap));
						recyclerview1.setLayoutManager(new LinearLayoutManager(GlobalGroupActivity.this));
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
				group_chat.addListenerForSingleValueEvent(new ValueEventListener() {
					@Override
					public void onDataChange(DataSnapshot _dataSnapshot) {
						listmap = new ArrayList<>();
						try {
							GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
							for (DataSnapshot _data : _dataSnapshot.getChildren()) {
								HashMap<String, Object> _map = _data.getValue(_ind);
								listmap.add(_map);
							}
						} catch (Exception _e) {
							_e.printStackTrace();
						}
						recyclerview1.setAdapter(new Recyclerview1Adapter(listmap));
						recyclerview1.setLayoutManager(new LinearLayoutManager(GlobalGroupActivity.this));
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
		group_chat.addChildEventListener(_group_chat_child_listener);
		
		fauth_updateEmailListener = new OnCompleteListener<Void>() {
			@Override
			public void onComplete(Task<Void> _param1) {
				final boolean _success = _param1.isSuccessful();
				final String _errorMessage = _param1.getException() != null ? _param1.getException().getMessage() : "";
				
			}
		};
		
		fauth_updatePasswordListener = new OnCompleteListener<Void>() {
			@Override
			public void onComplete(Task<Void> _param1) {
				final boolean _success = _param1.isSuccessful();
				final String _errorMessage = _param1.getException() != null ? _param1.getException().getMessage() : "";
				
			}
		};
		
		fauth_emailVerificationSentListener = new OnCompleteListener<Void>() {
			@Override
			public void onComplete(Task<Void> _param1) {
				final boolean _success = _param1.isSuccessful();
				final String _errorMessage = _param1.getException() != null ? _param1.getException().getMessage() : "";
				
			}
		};
		
		fauth_deleteUserListener = new OnCompleteListener<Void>() {
			@Override
			public void onComplete(Task<Void> _param1) {
				final boolean _success = _param1.isSuccessful();
				final String _errorMessage = _param1.getException() != null ? _param1.getException().getMessage() : "";
				
			}
		};
		
		fauth_phoneAuthListener = new OnCompleteListener<AuthResult>() {
			@Override
			public void onComplete(Task<AuthResult> task) {
				final boolean _success = task.isSuccessful();
				final String _errorMessage = task.getException() != null ? task.getException().getMessage() : "";
				
			}
		};
		
		fauth_updateProfileListener = new OnCompleteListener<Void>() {
			@Override
			public void onComplete(Task<Void> _param1) {
				final boolean _success = _param1.isSuccessful();
				final String _errorMessage = _param1.getException() != null ? _param1.getException().getMessage() : "";
				
			}
		};
		
		fauth_googleSignInListener = new OnCompleteListener<AuthResult>() {
			@Override
			public void onComplete(Task<AuthResult> task) {
				final boolean _success = task.isSuccessful();
				final String _errorMessage = task.getException() != null ? task.getException().getMessage() : "";
				
			}
		};
		
		_fauth_create_user_listener = new OnCompleteListener<AuthResult>() {
			@Override
			public void onComplete(Task<AuthResult> _param1) {
				final boolean _success = _param1.isSuccessful();
				final String _errorMessage = _param1.getException() != null ? _param1.getException().getMessage() : "";
				
			}
		};
		
		_fauth_sign_in_listener = new OnCompleteListener<AuthResult>() {
			@Override
			public void onComplete(Task<AuthResult> _param1) {
				final boolean _success = _param1.isSuccessful();
				final String _errorMessage = _param1.getException() != null ? _param1.getException().getMessage() : "";
				
			}
		};
		
		_fauth_reset_password_listener = new OnCompleteListener<Void>() {
			@Override
			public void onComplete(Task<Void> _param1) {
				final boolean _success = _param1.isSuccessful();
				
			}
		};
	}
	
	private void initializeLogic() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
			    Window w = GlobalGroupActivity.this.getWindow();
			    w.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
			    w.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
			    w.setStatusBarColor(Color.parseColor("#F6F7FB"));
		}
		_ImgRound(user_dp, 360);
		group_key = getIntent().getStringExtra("key");
		group_chat.removeEventListener(_group_chat_child_listener);
		chat_key = "Group-".concat(group_key);
		group_chat = _firebase.getReference(chat_key);
		group_chat.addChildEventListener(_group_chat_child_listener);
		bar.setElevation((float)15);
		_OneCreate();
		verified.setVisibility(View.GONE);
		bottomAudioRecorder.setVisibility(View.GONE);
	}
	
	public void _animae(final View _view, final double _duration) {
		LinearLayout viewgroup =(LinearLayout) _view;
		
		android.transition.AutoTransition autoTransition = new android.transition.AutoTransition(); autoTransition.setDuration((long)_duration); android.transition.TransitionManager.beginDelayedTransition(viewgroup, autoTransition);
	}
	
	
	public void _ImgRound(final ImageView _imageview, final double _value) {
		android.graphics.drawable.GradientDrawable gd = new android.graphics.drawable.GradientDrawable ();
		gd.setColor(android.R.color.transparent);
		gd.setCornerRadius((int)_value);
		_imageview.setClipToOutline(true);
		_imageview.setBackground(gd);
	}
	
	
	public void _rippleRoundStroke(final View _view, final String _focus, final String _pressed, final double _round, final double _stroke, final String _strokeclr) {
		android.graphics.drawable.GradientDrawable GG = new android.graphics.drawable.GradientDrawable();
		GG.setColor(Color.parseColor(_focus));
		GG.setCornerRadius((float)_round);
		GG.setStroke((int) _stroke,
		Color.parseColor("#" + _strokeclr.replace("#", "")));
		android.graphics.drawable.RippleDrawable RE = new android.graphics.drawable.RippleDrawable(new android.content.res.ColorStateList(new int[][]{new int[]{}}, new int[]{ Color.parseColor(_pressed)}), GG, null);
		_view.setBackground(RE);
	}
	
	
	public void _setProgressbarColor(final ProgressBar _prog, final String _color) {
		if (android.os.Build.VERSION.SDK_INT >= 21) {
			_prog.getIndeterminateDrawable().setColorFilter(Color.parseColor(_color), PorterDuff.Mode.SRC_IN);
			//KerDev
		}
	}
	
	
	public void _TransitionManager(final View _view, final double _duration) {
		LinearLayout viewgroup =(LinearLayout) _view;
		
		android.transition.AutoTransition autoTransition = new android.transition.AutoTransition(); autoTransition.setDuration((long)_duration); android.transition.TransitionManager.beginDelayedTransition(viewgroup, autoTransition);
	}
	
	
	public void _clickAnimation(final View _view) {
		ScaleAnimation fade_in = new ScaleAnimation(0.9f, 1f, 0.9f, 1f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.7f);
		fade_in.setDuration(300);
		fade_in.setFillAfter(true);
		_view.startAnimation(fade_in);
	}
	
	
	public void _SetToolTips(final View _view, final String _txt) {
		_view.setTooltipText(_txt);
	}
	
	
	public void _GradientDrawable(final View _view, final double _radius, final double _stroke, final double _shadow, final String _color, final String _borderColor, final boolean _ripple, final boolean _clickAnim, final double _animDuration) {
		if (_ripple) {
			android.graphics.drawable.GradientDrawable gd = new android.graphics.drawable.GradientDrawable();
			gd.setColor(Color.parseColor(_color));
			gd.setCornerRadius((int)_radius);
			gd.setStroke((int)_stroke,Color.parseColor(_borderColor));
			if (Build.VERSION.SDK_INT >= 21){
				_view.setElevation((int)_shadow);}
			android.content.res.ColorStateList clrb = new android.content.res.ColorStateList(new int[][]{new int[]{}}, new int[]{Color.parseColor("#9E9E9E")});
			android.graphics.drawable.RippleDrawable ripdrb = new android.graphics.drawable.RippleDrawable(clrb , gd, null);
			_view.setClickable(true);
			_view.setBackground(ripdrb);
		} else {
			android.graphics.drawable.GradientDrawable gd = new android.graphics.drawable.GradientDrawable();
			gd.setColor(Color.parseColor(_color));
			gd.setCornerRadius((int)_radius);
			gd.setStroke((int)_stroke,Color.parseColor(_borderColor));
			_view.setBackground(gd);
			if (Build.VERSION.SDK_INT >= 21){
				_view.setElevation((int)_shadow);}
		}
		if (_clickAnim) {
			_view.setOnTouchListener(new View.OnTouchListener() {
				@Override
				public boolean onTouch(View v, MotionEvent event) {
					switch (event.getAction()){
						case MotionEvent.ACTION_DOWN:{
							ObjectAnimator scaleX = new ObjectAnimator();
							scaleX.setTarget(_view);
							scaleX.setPropertyName("scaleX");
							scaleX.setFloatValues(0.9f);
							scaleX.setDuration((int)_animDuration);
							scaleX.start();
							
							ObjectAnimator scaleY = new ObjectAnimator();
							scaleY.setTarget(_view);
							scaleY.setPropertyName("scaleY");
							scaleY.setFloatValues(0.9f);
							scaleY.setDuration((int)_animDuration);
							scaleY.start();
							break;
						}
						case MotionEvent.ACTION_UP:{
							
							ObjectAnimator scaleX = new ObjectAnimator();
							scaleX.setTarget(_view);
							scaleX.setPropertyName("scaleX");
							scaleX.setFloatValues((float)1);
							scaleX.setDuration((int)_animDuration);
							scaleX.start();
							
							ObjectAnimator scaleY = new ObjectAnimator();
							scaleY.setTarget(_view);
							scaleY.setPropertyName("scaleY");
							scaleY.setFloatValues((float)1);
							scaleY.setDuration((int)_animDuration);
							scaleY.start();
							
							break;
						}
					}
					return false;
				}
			});
		}
		/**/
		/*
_view.setBackground(new GradientDrawable(GradientDrawable.Orientation.BR_TL, new int[] {0xFFF44336,0xFF4CAF50}));
_view.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b, int c, int d) { this.setCornerRadius(a); this.setStroke(b, c); this.setColor(d); return this; } }.getIns((int)50, (int)5, 0xFFF44336, Color.TRANSPARENT));

*/
	}
	
	
	public void _changeActivityFont(final String _fontname) {
		fontName = "fonts/".concat(_fontname.concat(".ttf"));
		overrideFonts(this,getWindow().getDecorView()); 
	} 
	private void overrideFonts(final android.content.Context context, final View v) {
		
		try {
			Typeface 
			typeace = Typeface.createFromAsset(getAssets(), fontName);;
			if ((v instanceof ViewGroup)) {
				ViewGroup vg = (ViewGroup) v;
				for (int i = 0;
				i < vg.getChildCount();
				i++) {
					View child = vg.getChildAt(i);
					overrideFonts(context, child);
				}
			} else {
				if ((v instanceof TextView)) {
					((TextView) v).setTypeface(typeace);
				} else {
					if ((v instanceof EditText )) {
						((EditText) v).setTypeface(typeace);
					} else {
						if ((v instanceof Button)) {
							((Button) v).setTypeface(typeace);
						}
					}
				}
			}
		}
		catch(Exception e)
		
		{
			SketchwareUtil.showMessage(getApplicationContext(), "Error Loading Font");
		};
	}
	
	
	public void _MessageInputOnExpandBtnClicked() {
		if (file_type_expand == 0) {
			file_type_expand++;
			devider2.setVisibility(View.VISIBLE);
			devider1.setVisibility(View.VISIBLE);
			devider.setVisibility(View.VISIBLE);
			more_send_type_btn.setVisibility(View.VISIBLE);
			send_type_voice_btn.setVisibility(View.VISIBLE);
			attachment_btn.setVisibility(View.VISIBLE);
			_TransitionManager(camera_gallery_btn_container_round, 200);
			message_input_outlined_round.setOrientation(LinearLayout.VERTICAL);
			
		} else {
			file_type_expand--;
			devider2.setVisibility(View.GONE);
			devider1.setVisibility(View.GONE);
			devider.setVisibility(View.GONE);
			more_send_type_btn.setVisibility(View.GONE);
			send_type_voice_btn.setVisibility(View.GONE);
			attachment_btn.setVisibility(View.GONE);
			_TransitionManager(camera_gallery_btn_container_round, 200);
			message_input_outlined_round.setOrientation(LinearLayout.HORIZONTAL);
			
		}
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
			    }
		    else if (p instanceof RelativeLayout.LayoutParams) {
			        RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams)p;
			        lp.setMargins(left, top, right, bottom);
			        _view.setLayoutParams(lp);
			    }
		    else if (p instanceof TableRow.LayoutParams) {
			        TableRow.LayoutParams lp = (TableRow.LayoutParams)p;
			        lp.setMargins(left, top, right, bottom);
			        _view.setLayoutParams(lp);
			    }
		
		
	}
	
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
	
	
	{
		
	}
	
	
	public void _OneCreate() {
		mMessageReplyLayout.setVisibility(View.GONE);
		file_type_expand = 0;
		blocked_txt.setVisibility(View.GONE);
		blocked_txt.setText(Html.fromHtml("<p>You can't reply to this conversation. <a href=\"https://example.com/learn-more\" style=\"color: #2962FF;\"><b>Learn more</b></a></p>"));
		camera_gallery_btn_container_round.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b, int c, int d) { this.setCornerRadius(a); this.setStroke(b, c); this.setColor(d); return this; } }.getIns((int)360, (int)0, Color.TRANSPARENT, 0xFFF0F3F8));
		send_round_btn.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b, int c, int d) { this.setCornerRadius(a); this.setStroke(b, c); this.setColor(d); return this; } }.getIns((int)360, (int)0, Color.TRANSPARENT, 0xFFF0F3F8));
		bottomAudioRecorderSend.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b, int c, int d) { this.setCornerRadius(a); this.setStroke(b, c); this.setColor(d); return this; } }.getIns((int)360, (int)0, Color.TRANSPARENT, 0xFFF0F3F8));
		block_switch = 0;
		message_input_outlined_round.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b, int c, int d) { this.setCornerRadius(a); this.setStroke(b, c); this.setColor(d); return this; } }.getIns((int)83, (int)3, 0xFFC7C7C7, 0xFFFFFFFF));
		if (message_et.getText().toString().trim().equals("")) {
			_TransitionManager(message_input_overall_container, 250);
			message_input_outlined_round.setOrientation(LinearLayout.HORIZONTAL);
			
		} else {
			_TransitionManager(message_input_overall_container, 250);
			message_input_outlined_round.setOrientation(LinearLayout.VERTICAL);
			
		}
		unblock_btn.setVisibility(View.GONE);
		img_container_layout.setVisibility(View.GONE);
		devider2.setVisibility(View.GONE);
		devider1.setVisibility(View.GONE);
		devider.setVisibility(View.GONE);
		more_send_type_btn.setVisibility(View.GONE);
		send_type_voice_btn.setVisibility(View.GONE);
		attachment_btn.setVisibility(View.GONE);
	}
	
	
	public void _group_send_message() {
		DatabaseReference getUserReference = FirebaseDatabase.getInstance().getReference("skyline/users").child(getIntent().getStringExtra("uid"));
		getUserReference.addListenerForSingleValueEvent(new ValueEventListener() {
			@Override
			public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
				if(dataSnapshot.exists()) {
					if (dataSnapshot.child("nickname").getValue(String.class).equals("null")) {
						
					} else {
						if (!message_et.getText().toString().equals("")) {
							push_key = group_chat.push().getKey();
							map = new HashMap<>();
							map.put("message", message_et.getText().toString());
							map.put("username", dataSnapshot.child("username").getValue(String.class));
							map.put("email", FirebaseAuth.getInstance().getCurrentUser().getEmail());
							map.put("uid", FirebaseAuth.getInstance().getCurrentUser().getUid());
							group_chat.child(push_key).updateChildren(map);
							map.clear();
							message_et.setText("");
						}
					}
				} else {
				}
			}
			
			@Override
			public void onCancelled(@NonNull DatabaseError databaseError) {
						
			}
		});
	}
	
	public class Recyclerview1Adapter extends RecyclerView.Adapter<Recyclerview1Adapter.ViewHolder> {
		
		ArrayList<HashMap<String, Object>> _data;
		
		public Recyclerview1Adapter(ArrayList<HashMap<String, Object>> _arr) {
			_data = _arr;
		}
		
		@Override
		public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
			LayoutInflater _inflater = getLayoutInflater();
			View _v = _inflater.inflate(R.layout.group_chat, null);
			RecyclerView.LayoutParams _lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
			_v.setLayoutParams(_lp);
			return new ViewHolder(_v);
		}
		
		@Override
		public void onBindViewHolder(ViewHolder _holder, final int _position) {
			View _view = _holder.itemView;
			
			final LinearLayout message_body = _view.findViewById(R.id.message_body);
			final ImageView profile_picture = _view.findViewById(R.id.profile_picture);
			final LinearLayout linear4 = _view.findViewById(R.id.linear4);
			final TextView sender_name = _view.findViewById(R.id.sender_name);
			final LinearLayout LinearLayoutTextBody = _view.findViewById(R.id.LinearLayoutTextBody);
			final TextView text_message = _view.findViewById(R.id.text_message);
			
			if (_data.get((int)_position).get("uid").toString().equals(FirebaseAuth.getInstance().getCurrentUser().getUid())) {
				message_body.setGravity(Gravity.CENTER_VERTICAL | Gravity.RIGHT);
				profile_picture.setVisibility(View.GONE);
				LinearLayoutTextBody.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b, int c, int d) { this.setCornerRadius(a); this.setStroke(b, c); this.setColor(d); return this; } }.getIns((int)360, (int)0, Color.TRANSPARENT, 0xFF2979FF));
				text_message.setTextColor(0xFFFFFFFF);
			} else {
				message_body.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
				profile_picture.setVisibility(View.VISIBLE);
				LinearLayoutTextBody.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b, int c, int d) { this.setCornerRadius(a); this.setStroke(b, c); this.setColor(d); return this; } }.getIns((int)360, (int)0, Color.TRANSPARENT, 0xFFEEEEEE));
				text_message.setTextColor(0xFF000000);
			}
			sender_name.setText(_data.get((int)_position).get("username").toString());
			text_message.setText(_data.get((int)_position).get("message").toString());
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