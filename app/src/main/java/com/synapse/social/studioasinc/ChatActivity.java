package com.synapse.social.studioasinc;

import android.Manifest;
import android.animation.*;
import android.app.*;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.*;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
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
import android.os.Vibrator;
//import android.support.design.*;
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
import android.widget.TextView;
import androidx.annotation.*;
import androidx.appcompat.app.AppCompatActivity;
import androidx.browser.*;
import androidx.cardview.widget.CardView;
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
import com.bumptech.glide.*;
import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.*;
import com.google.android.material.color.MaterialColors;
import com.google.android.material.progressindicator.LinearProgressIndicator;
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
import com.synapse.social.studioasinc.CenterCropLinearLayoutNoEffect;
import com.synapse.social.studioasinc.FadeEditText;
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
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.*;
//import org.jetbrains.kotlin.*;
import org.json.*;
import com.google.firebase.database.Query;
import android.os.Handler;
import android.os.Looper;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
//Don't remove imports
import com.google.android.material.switchmaterial.SwitchMaterial;
import androidx.appcompat.widget.SwitchCompat;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputLayout;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.content.SharedPreferences;
import com.synapse.social.studioasinc.ImageUploader;
import androidx.browser.customtabs.CustomTabsIntent;

import com.synapse.social.studioasinc.styling.TextStylingUtil;



public class ChatActivity extends AppCompatActivity {
	
	public final int REQ_CD_IMAGE_PICKER = 101;
	
	private Timer _timer = new Timer();
	private FirebaseDatabase _firebase = FirebaseDatabase.getInstance();
	private FirebaseStorage _firebase_storage = FirebaseStorage.getInstance();
	
	private ProgressDialog SynapseLoadingDialog;
	private MediaRecorder AudioMessageRecorder;
	private double ChatMessagesLimit = 0;
	private HashMap<String, Object> ChatSendMap = new HashMap<>();
	private HashMap<String, Object> ChatInboxSend = new HashMap<>();
	private double recordMs = 0;
	private HashMap<String, Object> ChatInboxSend2 = new HashMap<>();
	private String SecondUserAvatar = "";
	private HashMap<String, Object> typingSnd = new HashMap<>();
	private String ReplyMessageID = "";
	private String SecondUserName = "";
	private String FirstUserName = "";
	private int ChatInitialSize = 0;
	private String object_clicked = "";
	private String handle = "";
	private HashMap<String, Object> block = new HashMap<>();
	private double block_switch = 0;
	private String file = "";
	private String filename = "";
	private double file_type_expand = 0;
	private String AddFromUrlStr = "";
	private String IMG_BB_API_KEY = "";
	private String path = "";
	private String imageUrl = "";
	private String AndroidDevelopersBlogURL = "";
	
	private ArrayList<HashMap<String, Object>> ChatMessagesList = new ArrayList<>();
	
	private CenterCropLinearLayoutNoEffect body;
	private CenterCropLinearLayoutNoEffect top;
	private LinearLayout middle;
	private LinearLayout bottomSpace;
	private LinearLayout mMessageReplyLayout;
	private LinearLayout message_input_overall_container;
	private CenterCropLinearLayoutNoEffect bottomAudioRecorder;
	private TextView unblock_btn;
	private TextView blocked_txt;
	private ImageView back;
	private LinearLayout topProfileLayout;
	private LinearLayout topProfileLayoutSpace;
	private ImageView imageview2;
	private ImageView imageview1;
	private ImageView more;
	private CardView topProfileCard;
	private LinearLayout topProfileLayoutRight;
	private ImageView topProfileLayoutProfileImage;
	private LinearLayout topProfileLayoutRightTop;
	private TextView topProfileLayoutStatus;
	private TextView topProfileLayoutUsername;
	private ImageView topProfileLayoutGenderBadge;
	private ImageView topProfileLayoutVerifiedBadge;
	private LinearLayout bannedUserInfo;
	private RecyclerView ChatMessagesListRecycler;
	private TextView noChatText;
	private ImageView bannedUserInfoIc;
	private TextView bannedUserInfoText;
	private CenterCropLinearLayoutNoEffect mMessageReplyLayoutBody;
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
	private LinearProgressIndicator img_upload_prog;
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
	
	private Intent intent = new Intent();
	private DatabaseReference main = _firebase.getReference("skyline");
	private ChildEventListener _main_child_listener;
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
	private TimerTask loadTimer;
	private Calendar cc = Calendar.getInstance();
	private Vibrator vbr;
	private TimerTask timer;
	private DatabaseReference blocklist = _firebase.getReference("skyline/blocklist");
	private ChildEventListener _blocklist_child_listener;
	private SharedPreferences blocked;
	private Intent image_picker = new Intent(Intent.ACTION_GET_CONTENT);
	private SharedPreferences theme;
	private AlertDialog cd;
	private StorageReference upload_selected_img = _firebase_storage.getReference("synapse/chats/images");
	private OnCompleteListener<Uri> _upload_selected_img_upload_success_listener;
	private OnSuccessListener<FileDownloadTask.TaskSnapshot> _upload_selected_img_download_success_listener;
	private OnSuccessListener _upload_selected_img_delete_success_listener;
	private OnProgressListener _upload_selected_img_upload_progress_listener;
	private OnProgressListener _upload_selected_img_download_progress_listener;
	private OnFailureListener _upload_selected_img_failure_listener;
	private Intent i = new Intent();
	private AlertDialog.Builder zorry;
	private SharedPreferences appSettings;
	
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.chat);
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
		top = findViewById(R.id.top);
		middle = findViewById(R.id.middle);
		bottomSpace = findViewById(R.id.bottomSpace);
		mMessageReplyLayout = findViewById(R.id.mMessageReplyLayout);
		message_input_overall_container = findViewById(R.id.message_input_overall_container);
		bottomAudioRecorder = findViewById(R.id.bottomAudioRecorder);
		unblock_btn = findViewById(R.id.unblock_btn);
		blocked_txt = findViewById(R.id.blocked_txt);
		back = findViewById(R.id.back);
		topProfileLayout = findViewById(R.id.topProfileLayout);
		topProfileLayoutSpace = findViewById(R.id.topProfileLayoutSpace);
		imageview2 = findViewById(R.id.imageview2);
		imageview1 = findViewById(R.id.imageview1);
		more = findViewById(R.id.more);
		topProfileCard = findViewById(R.id.topProfileCard);
		topProfileLayoutRight = findViewById(R.id.topProfileLayoutRight);
		topProfileLayoutProfileImage = findViewById(R.id.topProfileLayoutProfileImage);
		topProfileLayoutRightTop = findViewById(R.id.topProfileLayoutRightTop);
		topProfileLayoutStatus = findViewById(R.id.topProfileLayoutStatus);
		topProfileLayoutUsername = findViewById(R.id.topProfileLayoutUsername);
		topProfileLayoutGenderBadge = findViewById(R.id.topProfileLayoutGenderBadge);
		topProfileLayoutVerifiedBadge = findViewById(R.id.topProfileLayoutVerifiedBadge);
		bannedUserInfo = findViewById(R.id.bannedUserInfo);
		ChatMessagesListRecycler = findViewById(R.id.ChatMessagesListRecycler);
		noChatText = findViewById(R.id.noChatText);
		bannedUserInfoIc = findViewById(R.id.bannedUserInfoIc);
		bannedUserInfoText = findViewById(R.id.bannedUserInfoText);
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
		auth = FirebaseAuth.getInstance();
		vbr = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
		blocked = getSharedPreferences("block", Activity.MODE_PRIVATE);
		image_picker.setType("image/*");
		image_picker.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
		theme = getSharedPreferences("theme", Activity.MODE_PRIVATE);
		zorry = new AlertDialog.Builder(this);
		appSettings = getSharedPreferences("appSettings", Activity.MODE_PRIVATE);
		
		unblock_btn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				_Unblock_this_user();
			}
		});
		
		back.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				onBackPressed();
			}
		});
		
		topProfileLayout.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				i.setClass(getApplicationContext(), Chat2ndUserMoreSettingsActivity.class);
				i.putExtra("uid", getIntent().getStringExtra("uid"));
				startActivity(i);
			}
		});
		
		more.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				i.setClass(getApplicationContext(), Chat2ndUserMoreSettingsActivity.class);
				i.putExtra("uid", getIntent().getStringExtra("uid"));
				startActivity(i);
			}
		});
		
		mMessageReplyLayoutBodyCancel.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				ReplyMessageID = "null";
				mMessageReplyLayout.setVisibility(View.GONE);
				vbr.vibrate((long)(48));
			}
		});
		
		message_input_outlined_round.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				message_et.requestFocus();
			}
		});
		
		send_round_btn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				_send_btn();
			}
		});
		
		message_et.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence _param1, int _param2, int _param3, int _param4) {
				final String _charSeq = _param1.toString();
				if (img_container_layout.getVisibility() == View.VISIBLE || (file_type_expand == 1)) {
					if (_charSeq.length() == 0) {
						message_input_outlined_round.setOrientation(LinearLayout.VERTICAL);
						
						message_input_outlined_round.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b, int c, int d) { this.setCornerRadius(a); this.setStroke(b, c); this.setColor(d); return this; } }.getIns((int)95, (int)2, 0xFFC7C7C7, 0xFFFFFFFF));
						_setMargin(message_et, 0, 7, 0, 0);
						send_ic.setImageResource(R.drawable.ic_thumb_up_48px);
						FirebaseDatabase.getInstance().getReference("skyline/chats").child(getIntent().getStringExtra("uid")).child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("typing-message").removeValue();
						_TransitionManager(message_input_overall_container, 125);
						
					} else {
						message_input_outlined_round.setOrientation(LinearLayout.VERTICAL);
						
						_TransitionManager(message_input_overall_container, 125);
						typingSnd = new HashMap<>();
						typingSnd.put("uid", FirebaseAuth.getInstance().getCurrentUser().getUid());
						typingSnd.put("typingMessageStatus", "true");
						FirebaseDatabase.getInstance().getReference("skyline/chats").child(getIntent().getStringExtra("uid")).child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("typing-message").updateChildren(typingSnd);
						_setMargin(message_et, 0, 7, 0, 20);
						message_input_outlined_round.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b, int c, int d) { this.setCornerRadius(a); this.setStroke(b, c); this.setColor(d); return this; } }.getIns((int)45, (int)2, 0xFFC7C7C7, 0xFFFFFFFF));
						message_input_outlined_round.setOrientation(LinearLayout.VERTICAL);
						
						send_ic.setImageResource(R.drawable.ic_send_48px);
					}
				} else {
					if (_charSeq.length() == 0) {
						message_input_outlined_round.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b, int c, int d) { this.setCornerRadius(a); this.setStroke(b, c); this.setColor(d); return this; } }.getIns((int)100, (int)2, 0xFFC7C7C7, 0xFFFFFFFF));
						_setMargin(message_et, 0, 7, 0, 0);
						send_ic.setImageResource(R.drawable.ic_thumb_up_48px);
						FirebaseDatabase.getInstance().getReference("skyline/chats").child(getIntent().getStringExtra("uid")).child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("typing-message").removeValue();
						message_input_outlined_round.setOrientation(LinearLayout.HORIZONTAL);
						
						_TransitionManager(message_input_overall_container, 125);
					} else {
						_TransitionManager(message_input_overall_container, 125);
						message_input_outlined_round.setOrientation(LinearLayout.VERTICAL);
						
						send_ic.setImageResource(R.drawable.ic_send_48px);
					}
				}
			}
			
			@Override
			public void beforeTextChanged(CharSequence _param1, int _param2, int _param3, int _param4) {
				
			}
			
			@Override
			public void afterTextChanged(Editable _param1) {
				
			}
		});
		
		remove_selected_img_icon.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				img_container_layout.setVisibility(View.GONE);
				file = "";
				path = "";
				filename = "";
				_TransitionManager(message_input_outlined_round, 125);
				if (message_et.getText().toString().equals("")) {
					if (file_type_expand == 1) {
						message_input_outlined_round.setOrientation(LinearLayout.VERTICAL);
						
					} else {
						message_input_outlined_round.setOrientation(LinearLayout.HORIZONTAL);
						
					}
				} else {
					message_input_outlined_round.setOrientation(LinearLayout.VERTICAL);
					
				}
				message_input_outlined_round.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b, int c, int d) { this.setCornerRadius(a); this.setStroke(b, c); this.setColor(d); return this; } }.getIns((int)87, (int)3, 0xFFC7C7C7, 0xFFFFFFFF));
			}
		});
		
		expand_send_type_btn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				if (file_type_expand == 0) {
					file_type_expand++;
					devider2.setVisibility(View.VISIBLE);
					devider1.setVisibility(View.VISIBLE);
					devider.setVisibility(View.VISIBLE);
					more_send_type_btn.setVisibility(View.VISIBLE);
					send_type_voice_btn.setVisibility(View.VISIBLE);
					attachment_btn.setVisibility(View.VISIBLE);
					_TransitionManager(camera_gallery_btn_container_round, 200);
					_ImageColor(expand_send_type_btn, 0xFF2962FF);
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
					_ImageColor(expand_send_type_btn, 0xFF454644);
					if (message_et.getText().toString().equals("")) {
						if (img_container_layout.getVisibility() == View.VISIBLE) {
							message_input_outlined_round.setOrientation(LinearLayout.VERTICAL);
							
						} else {
							message_input_outlined_round.setOrientation(LinearLayout.HORIZONTAL);
							
						}
					} else {
						message_input_outlined_round.setOrientation(LinearLayout.VERTICAL);
						
					}
				}
			}
		});
		
		gallery_btn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				startActivityForResult(image_picker, REQ_CD_IMAGE_PICKER);
			}
		});
		
		send_type_voice_btn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				if (Build.VERSION.SDK_INT >= 23) {
					if (checkSelfPermission(android.Manifest.permission.RECORD_AUDIO) == android.content.pm.PackageManager.PERMISSION_DENIED) {
						requestPermissions(new String[] {android.Manifest.permission.RECORD_AUDIO}, 1000);
					} else {
						_AudioRecorderStart();
					}
				} else {
					_AudioRecorderStart();
				}
			}
		});
		
		bottomAudioRecorderCancel.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				_AudioRecorderStop();
			}
		});
		
		bottomAudioRecorderTime.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				message_input_outlined_round.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b, int c, int d) { this.setCornerRadius(a); this.setStroke(b, c); this.setColor(d); return this; } }.getIns((int)Double.parseDouble(message_et.getText().toString()), (int)2, 0xFFC7C7C7, 0xFFFFFFFF));
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
		
		_blocklist_child_listener = new ChildEventListener() {
			@Override
			public void onChildAdded(DataSnapshot _param1, String _param2) {
				GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
				final String _childKey = _param1.getKey();
				final HashMap<String, Object> _childValue = _param1.getValue(_ind);
				if (_childKey.equals(getIntent().getStringExtra("uid"))) {
					if (_childValue.containsKey(FirebaseAuth.getInstance().getCurrentUser().getUid())) {
						message_input_overall_container.setVisibility(View.GONE);
						blocked_txt.setVisibility(View.VISIBLE);
					} else {
						message_input_overall_container.setVisibility(View.VISIBLE);
						blocked_txt.setVisibility(View.GONE);
					}
				} else {
					
				}
				if (_childKey.equals(FirebaseAuth.getInstance().getCurrentUser().getUid())) {
					if (_childValue.containsKey(getIntent().getStringExtra("uid"))) {
						message_input_overall_container.setVisibility(View.GONE);
						unblock_btn.setVisibility(View.VISIBLE);
					} else {
						message_input_overall_container.setVisibility(View.VISIBLE);
						unblock_btn.setVisibility(View.GONE);
					}
				} else {
					
				}
			}
			
			@Override
			public void onChildChanged(DataSnapshot _param1, String _param2) {
				GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
				final String _childKey = _param1.getKey();
				final HashMap<String, Object> _childValue = _param1.getValue(_ind);
				if (_childKey.equals(getIntent().getStringExtra("uid"))) {
					if (_childValue.containsKey(FirebaseAuth.getInstance().getCurrentUser().getUid())) {
						message_input_overall_container.setVisibility(View.GONE);
						blocked_txt.setVisibility(View.VISIBLE);
					} else {
						message_input_overall_container.setVisibility(View.VISIBLE);
						blocked_txt.setVisibility(View.GONE);
					}
				} else {
					
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
		blocklist.addChildEventListener(_blocklist_child_listener);
		
		_upload_selected_img_upload_progress_listener = new OnProgressListener<UploadTask.TaskSnapshot>() {
			@Override
			public void onProgress(UploadTask.TaskSnapshot _param1) {
				double _progressValue = (100.0 * _param1.getBytesTransferred()) / _param1.getTotalByteCount();
				img_upload_prog.setProgress((int)_progressValue);
				img_upload_prog.setVisibility(View.VISIBLE);
				_LoadingDialog(true);
			}
		};
		
		_upload_selected_img_download_progress_listener = new OnProgressListener<FileDownloadTask.TaskSnapshot>() {
			@Override
			public void onProgress(FileDownloadTask.TaskSnapshot _param1) {
				double _progressValue = (100.0 * _param1.getBytesTransferred()) / _param1.getTotalByteCount();
				
			}
		};
		
		_upload_selected_img_upload_success_listener = new OnCompleteListener<Uri>() {
			@Override
			public void onComplete(Task<Uri> _param1) {
				final String _downloadUrl = _param1.getResult().toString();
				_LoadingDialog(false);
				cc = Calendar.getInstance();
				String uniqueMessageKey = main.push().getKey();
				ChatSendMap = new HashMap<>();
				ChatSendMap.put("uid", FirebaseAuth.getInstance().getCurrentUser().getUid());
				ChatSendMap.put("TYPE", "MESSAGE");
				ChatSendMap.put("message_text", message_et.getText().toString().trim());
				ChatSendMap.put("message_image_uri", _downloadUrl);
				ChatSendMap.put("message_state", "sended");
				if (!ReplyMessageID.equals("null")) {
					ChatSendMap.put("replied_message_id", ReplyMessageID);
				}
				ChatSendMap.put("key", uniqueMessageKey);
				ChatSendMap.put("push_date", String.valueOf((long)(cc.getTimeInMillis())));
				FirebaseDatabase.getInstance().getReference("skyline/chats").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(getIntent().getStringExtra("uid")).child(uniqueMessageKey).updateChildren(ChatSendMap);
				FirebaseDatabase.getInstance().getReference("skyline/chats").child(getIntent().getStringExtra("uid")).child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(uniqueMessageKey).updateChildren(ChatSendMap);
				ChatInboxSend = new HashMap<>();
				ChatInboxSend.put("uid", getIntent().getStringExtra("uid"));
				ChatInboxSend.put("TYPE", "MESSAGE");
				ChatInboxSend.put("last_message_uid", FirebaseAuth.getInstance().getCurrentUser().getUid());
				ChatInboxSend.put("last_message_text", message_et.getText().toString().trim());
				ChatInboxSend.put("last_message_state", "sended");
				ChatInboxSend.put("push_date", String.valueOf((long)(cc.getTimeInMillis())));
				FirebaseDatabase.getInstance().getReference("skyline/inbox").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(getIntent().getStringExtra("uid")).updateChildren(ChatInboxSend);
				ChatInboxSend2 = new HashMap<>();
				ChatInboxSend2.put("uid", FirebaseAuth.getInstance().getCurrentUser().getUid());
				ChatInboxSend2.put("TYPE", "MESSAGE");
				ChatInboxSend2.put("last_message_uid", FirebaseAuth.getInstance().getCurrentUser().getUid());
				ChatInboxSend2.put("last_message_text", message_et.getText().toString().trim());
				ChatInboxSend2.put("last_message_state", "sended");
				ChatInboxSend2.put("push_date", String.valueOf((long)(cc.getTimeInMillis())));
				FirebaseDatabase.getInstance().getReference("skyline/inbox").child(getIntent().getStringExtra("uid")).child(FirebaseAuth.getInstance().getCurrentUser().getUid()).updateChildren(ChatInboxSend2);
				message_et.setText("");
				filename = "";
				file = "";
				devider2.setVisibility(View.GONE);
				devider1.setVisibility(View.GONE);
				devider.setVisibility(View.GONE);
				more_send_type_btn.setVisibility(View.GONE);
				send_type_voice_btn.setVisibility(View.GONE);
				attachment_btn.setVisibility(View.GONE);
				_TransitionManager(camera_gallery_btn_container_round, 200);
				message_input_outlined_round.setOrientation(LinearLayout.HORIZONTAL);
				
				img_container_layout.setVisibility(View.GONE);
				img_upload_prog.setVisibility(View.GONE);
				FirebaseDatabase.getInstance().getReference("skyline/chats").child(getIntent().getStringExtra("uid")).child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("typing-message").removeValue();
				ChatSendMap.clear();
				ChatInboxSend.clear();
				ChatInboxSend2.clear();
				ReplyMessageID = "null";
				mMessageReplyLayout.setVisibility(View.GONE);
			}
		};
		
		_upload_selected_img_download_success_listener = new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
			@Override
			public void onSuccess(FileDownloadTask.TaskSnapshot _param1) {
				final long _totalByteCount = _param1.getTotalByteCount();
				
			}
		};
		
		_upload_selected_img_delete_success_listener = new OnSuccessListener() {
			@Override
			public void onSuccess(Object _param1) {
				_LoadingDialog(false);
			}
		};
		
		_upload_selected_img_failure_listener = new OnFailureListener() {
			@Override
			public void onFailure(Exception _param1) {
				final String _message = _param1.getMessage();
				
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
		if (message_et.getText().toString().trim().equals("")) {
			_TransitionManager(message_input_overall_container, 250);
			message_input_outlined_round.setOrientation(LinearLayout.HORIZONTAL);
			
		} else {
			_TransitionManager(message_input_overall_container, 250);
			message_input_outlined_round.setOrientation(LinearLayout.VERTICAL);
			
		}
		SecondUserAvatar = "null";
		ReplyMessageID = "null";
		path = "";
		ChatMessagesLimit = 80;
		file_type_expand = 0;
		block_switch = 0;
		bannedUserInfo.setElevation((float)2);
		LinearLayoutManager ChatRecyclerLayoutManager = new LinearLayoutManager(this);
		ChatRecyclerLayoutManager.setReverseLayout(false);
		ChatRecyclerLayoutManager.setStackFromEnd(true);
		ChatMessagesListRecycler.setLayoutManager(ChatRecyclerLayoutManager);
		ChatMessagesListRecycler.setAdapter(new ChatMessagesListRecyclerAdapter(ChatMessagesList));
		_getUserReference();
		ChatMessagesListRecycler.addOnScrollListener(new RecyclerView.OnScrollListener() {
			@Override
			public void onScrolled(@NonNull RecyclerView ChatMessagesListRecycler, int dx, int dy) {
				super.onScrolled(ChatMessagesListRecycler, dx, dy);
				
				LinearLayoutManager layoutManager = (LinearLayoutManager) ChatMessagesListRecycler.getLayoutManager();
				
				int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();
				int lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition();
				
				for (int i = firstVisibleItemPosition; i <= lastVisibleItemPosition; i++) {
					View childView = layoutManager.findViewByPosition(i);
					if (childView != null) {
						float percentage = calculatePercentage(layoutManager, childView);
						animateView(childView, percentage);
					}
				}
			}
			
			private float calculatePercentage(LinearLayoutManager layoutManager, View childView) {
				int itemHeight = childView.getHeight();
				int parentHeight = layoutManager.getHeight();
				int top = childView.getTop();
				int bottom = childView.getBottom();
				
				if (parentHeight == 0) {
					return 0f;
				}
				
				int visibleHeight = Math.min(bottom, parentHeight) - Math.max(top, 0);
				int totalHeight = Math.min(itemHeight, parentHeight);
				
				return (float) visibleHeight / totalHeight;
			}
			
			private void animateView(View view, float percentage) {
				view.setAlpha(percentage); // Adjust opacity based on percentage of visibility
			}
		});
		blocked_txt.setText(Html.fromHtml("<p>You can't reply to this conversation. <a href=\"https://example.com/learn-more\" style=\"color: #2962FF;\"><b>Learn more</b></a></p>"));
		camera_gallery_btn_container_round.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b, int c, int d) { this.setCornerRadius(a); this.setStroke(b, c); this.setColor(d); return this; } }.getIns((int)360, (int)0, Color.TRANSPARENT, 0xFFF0F3F8));
		send_round_btn.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b, int c, int d) { this.setCornerRadius(a); this.setStroke(b, c); this.setColor(d); return this; } }.getIns((int)360, (int)0, Color.TRANSPARENT, 0xFFF0F3F8));
		bottomAudioRecorderSend.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b, int c, int d) { this.setCornerRadius(a); this.setStroke(b, c); this.setColor(d); return this; } }.getIns((int)360, (int)0, Color.TRANSPARENT, 0xFFF0F3F8));
		message_input_outlined_round.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b, int c, int d) { this.setCornerRadius(a); this.setStroke(b, c); this.setColor(d); return this; } }.getIns((int)95, (int)3, 0xFFC7C7C7, 0xFFFFFFFF));
		img_container_layout.setVisibility(View.GONE);
		devider2.setVisibility(View.GONE);
		devider1.setVisibility(View.GONE);
		devider.setVisibility(View.GONE);
		more_send_type_btn.setVisibility(View.GONE);
		send_type_voice_btn.setVisibility(View.GONE);
		attachment_btn.setVisibility(View.GONE);
		message_input_outlined_round.setOrientation(LinearLayout.HORIZONTAL);
		
		_ImgRound(topProfileLayoutProfileImage, 100);
		_stateColor(0xFFFFFFFF, 0xFFFFFFFF);
		_ScrollingText(topProfileLayoutUsername);
	}
	
	@Override
	protected void onActivityResult(int _requestCode, int _resultCode, Intent _data) {
		super.onActivityResult(_requestCode, _resultCode, _data);
		
		switch (_requestCode) {
			case REQ_CD_IMAGE_PICKER:
			if (_resultCode == Activity.RESULT_OK) {
				ArrayList<String> _filePath = new ArrayList<>();
				if (_data != null) {
					if (_data.getClipData() != null) {
						for (int _index = 0; _index < _data.getClipData().getItemCount(); _index++) {
							ClipData.Item _item = _data.getClipData().getItemAt(_index);
							_filePath.add(FileUtil.convertUriToFilePath(getApplicationContext(), _item.getUri()));
						}
					}
					else {
						_filePath.add(FileUtil.convertUriToFilePath(getApplicationContext(), _data.getData()));
					}
				}
				file = _filePath.get((int)(0));
				path = _filePath.get((int)(0));
				filename = Uri.parse(file).getLastPathSegment();
				img_name.setText(filename);
				selected_img_preview.setImageBitmap(FileUtil.decodeSampleBitmapFromPath(_filePath.get((int)(0)), 1024, 1024));
				_TransitionManager(message_input_outlined_round, 150);
				message_input_outlined_round.setOrientation(LinearLayout.VERTICAL);
				
				img_container_layout.setVisibility(View.VISIBLE);
				img_upload_prog.setVisibility(View.GONE);
			}
			else {
				
			}
			break;
			default:
			break;
		}
	}
	
	@Override
	public void onBackPressed() {
		finish();
	}
	
	
	@Override
	public void onPause() {
		super.onPause();
		FirebaseDatabase.getInstance().getReference("skyline/chats").child(getIntent().getStringExtra("uid")).child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("typing-message").removeValue();
	}
	
	@Override
	public void onStop() {
		super.onStop();
		FirebaseDatabase.getInstance().getReference("skyline/chats").child(getIntent().getStringExtra("uid")).child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("typing-message").removeValue();
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		FirebaseDatabase.getInstance().getReference("skyline/chats").child(getIntent().getStringExtra("uid")).child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("typing-message").removeValue();
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
	
	
	public void _messageOverviewPopup(final View _view, final double _position, final ArrayList<HashMap<String, Object>> _data) {
		View pop1V = getLayoutInflater().inflate(R.layout.chat_msg_options_popup_cv_synapse, null);


		final PopupWindow pop1 = new PopupWindow(pop1V, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
		pop1.setFocusable(true);
		pop1.setInputMethodMode(ListPopupWindow.INPUT_METHOD_NOT_NEEDED);
		final LinearLayout main = pop1V.findViewById(R.id.main);
		final LinearLayout edit = pop1V.findViewById(R.id.edit);
		final LinearLayout reply = pop1V.findViewById(R.id.reply);
		final LinearLayout copy = pop1V.findViewById(R.id.copy);
		final LinearLayout delete = pop1V.findViewById(R.id.delete);
		pop1.setAnimationStyle(android.R.style.Animation_Dialog);
		int[] location = new int[2];
		View anchorView = _view;
		anchorView.getLocationOnScreen(location);
		
		int screenHeight = getApplicationContext().getResources().getDisplayMetrics().heightPixels;
		int halfScreenHeight = screenHeight / 2;
		int anchorViewHeight = anchorView.getHeight();
		
		if (location[1] < halfScreenHeight) {
			pop1.showAtLocation(anchorView, Gravity.NO_GRAVITY, location[0], location[1] + anchorViewHeight);
		} else if (location[1] > halfScreenHeight) {
			pop1.showAtLocation(anchorView, Gravity.NO_GRAVITY, location[0], location[1] - pop1.getHeight());
		} else {
			pop1.showAtLocation(anchorView, Gravity.NO_GRAVITY, location[0], location[1] + anchorViewHeight / 2 - pop1.getHeight() / 2);
		}
		if (_data.get((int)_position).get("uid").toString().equals(FirebaseAuth.getInstance().getCurrentUser().getUid())) {
			main.setGravity(Gravity.CENTER | Gravity.RIGHT);
			edit.setVisibility(View.VISIBLE);
			delete.setVisibility(View.VISIBLE);
		} else {
			main.setGravity(Gravity.CENTER | Gravity.LEFT);
			edit.setVisibility(View.GONE);
			delete.setVisibility(View.GONE);
		}
		_viewGraphics(edit, 0xFFFFFFFF, 0xFFEEEEEE, 0, 0, 0xFFFFFFFF);
		_viewGraphics(reply, 0xFFFFFFFF, 0xFFEEEEEE, 0, 0, 0xFFFFFFFF);
		_viewGraphics(copy, 0xFFFFFFFF, 0xFFEEEEEE, 0, 0, 0xFFFFFFFF);
		_viewGraphics(delete, 0xFFFFFFFF, 0xFFEEEEEE, 0, 0, 0xFFFFFFFF);
		main.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				pop1.dismiss();
			}
		});
		reply.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				ReplyMessageID = _data.get((int)_position).get("key").toString();
				if (_data.get((int)_position).get("uid").toString().equals(FirebaseAuth.getInstance().getCurrentUser().getUid())) {
					mMessageReplyLayoutBodyRightUsername.setText(FirstUserName);
				} else {
					mMessageReplyLayoutBodyRightUsername.setText(SecondUserName);
				}
				mMessageReplyLayoutBodyRightMessage.setText(_data.get((int)_position).get("message_text").toString());
				mMessageReplyLayout.setVisibility(View.VISIBLE);
				vbr.vibrate((long)(48));
				pop1.dismiss();
			}
		});
		copy.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				((ClipboardManager) getSystemService(getApplicationContext().CLIPBOARD_SERVICE)).setPrimaryClip(ClipData.newPlainText("clipboard", _data.get((int)_position).get("message_text").toString()));
				vbr.vibrate((long)(48));
				pop1.dismiss();
			}
		});
		delete.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				_DeleteMessageDialog(_data, _position);
				pop1.dismiss();
			}
		});
		edit.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				pop1.dismiss();
				cd = new AlertDialog.Builder(ChatActivity.this).create();
				LayoutInflater cdLI = getLayoutInflater();
				View cdCV = (View) cdLI.inflate(R.layout.add_image_from_url_dialog, null);
				cd.setView(cdCV);
				final LinearLayout add_button = (LinearLayout)
				cdCV.findViewById(R.id.add_button);
				final EditText image_url_input = (EditText)
				cdCV.findViewById(R.id.image_url_input);
				final TextInputLayout image_url_input_inputlayout = (TextInputLayout)
				cdCV.findViewById(R.id.image_url_input_inputlayout);
				final TextView dialog_top_title = (TextView)
				cdCV.findViewById(R.id.dialog_top_title);
				final TextView subtitle = (TextView)
				cdCV.findViewById(R.id.subtitle);
				final TextView textview3 = (TextView)
				cdCV.findViewById(R.id.textview3);
				cd.setCancelable(true);
				cd.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
				cd.show();
				dialog_top_title.setText("Edit message");
				subtitle.setText("The updated message can be reported later so that we can take action based on it.");
				textview3.setText("Update");
				image_url_input.setText(_data.get((int)_position).get("message_text").toString());
				_viewGraphics(add_button, getResources().getColor(R.color.colorPrimary), 0xFF388E3C, 300, 0, Color.TRANSPARENT);
				((EditText)image_url_input).setMaxLines((int)8);
				image_url_input_inputlayout.setBoxStrokeColor(0xFF494C54);
				image_url_input_inputlayout.setBoxBackgroundMode(image_url_input_inputlayout.BOX_BACKGROUND_OUTLINE);
				image_url_input_inputlayout.setBoxCornerRadii((float) 50,(float) 50,(float) 50,(float) 50);
				image_url_input_inputlayout.setBoxBackgroundMode(image_url_input_inputlayout.BOX_BACKGROUND_OUTLINE);
				add_button.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View _view) {
						if (!(image_url_input.getText().toString().length() == 0)) {
							ChatSendMap = new HashMap<>();
							ChatSendMap.put("uid", FirebaseAuth.getInstance().getCurrentUser().getUid());
							ChatSendMap.put("TYPE", "MESSAGE");
							ChatSendMap.put("message_uid", FirebaseAuth.getInstance().getCurrentUser().getUid());
							ChatSendMap.put("message_text", image_url_input.getText().toString().trim());
							ChatSendMap.put("message_state", "sended");
							ChatSendMap.put("push_date", String.valueOf((long)(cc.getTimeInMillis())));
							// Updating the chat reference
							FirebaseDatabase.getInstance().getReference("skyline/chats")
							.child(getIntent().getStringExtra("uid"))  // Replacing with the UID from the intent extras
							.child(FirebaseAuth.getInstance().getCurrentUser().getUid())  // Replacing with the current user UID
							.child(_data.get((int)_position).get("key").toString())  // Using the key from your data
							.updateChildren(ChatSendMap);  // Ensure ChatSendMap contains the necessary data for the update
							// Updating the chat reference
							FirebaseDatabase.getInstance().getReference("skyline/chats")
							.child(FirebaseAuth.getInstance().getCurrentUser().getUid())  // My UID
							.child(getIntent().getStringExtra("uid"))  // His UID from the intent extras
							.child(_data.get((int)_position).get("key").toString())  // Using the key from your data
							.updateChildren(ChatSendMap);  // Ensure ChatSendMap contains the necessary data for the update
							cd.dismiss();
						} else {
							SketchwareUtil.showMessage(getApplicationContext(), "Can't be empty");
						}
					}
				});
			}
		});
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
	
	
	public void _getUserReference() {
		DatabaseReference getUserReference = FirebaseDatabase.getInstance().getReference("skyline/users").child(getIntent().getStringExtra("uid"));
		getUserReference.addListenerForSingleValueEvent(new ValueEventListener() {
			@Override
			public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
				if(dataSnapshot.exists()) {
					if (dataSnapshot.child("banned").getValue(String.class).equals("true")) {
						bannedUserInfo.setVisibility(View.VISIBLE);
						topProfileLayoutProfileImage.setImageResource(R.drawable.banned_avatar);
						SecondUserAvatar = "null_banned";
						topProfileLayoutStatus.setTextColor(0xFF9E9E9E);
						topProfileLayoutStatus.setText(getResources().getString(R.string.offline));
					} else {
						bannedUserInfo.setVisibility(View.GONE);
						if (dataSnapshot.child("avatar").getValue(String.class).equals("null")) {
							topProfileLayoutProfileImage.setImageResource(R.drawable.avatar);
							SecondUserAvatar = "null";
						} else {
							Glide.with(getApplicationContext()).load(Uri.parse(dataSnapshot.child("avatar").getValue(String.class))).into(topProfileLayoutProfileImage);
							SecondUserAvatar = dataSnapshot.child("avatar").getValue(String.class);
						}
					}
					if (dataSnapshot.child("nickname").getValue(String.class).equals("null")) {
						topProfileLayoutUsername.setText("@" + dataSnapshot.child("username").getValue(String.class));
						SecondUserName = "@" + dataSnapshot.child("username").getValue(String.class);
					} else {
						topProfileLayoutUsername.setText(dataSnapshot.child("nickname").getValue(String.class));
						SecondUserName = dataSnapshot.child("nickname").getValue(String.class);
					}
					if (dataSnapshot.child("status").getValue(String.class).equals("online")) {
						topProfileLayoutStatus.setText(getResources().getString(R.string.online));
						topProfileLayoutStatus.setTextColor(0xFF2196F3);
					} else {
						if (dataSnapshot.child("status").getValue(String.class).equals("offline")) {
							topProfileLayoutStatus.setText(getResources().getString(R.string.offline));
						} else {
							_setUserLastSeen(Double.parseDouble(dataSnapshot.child("status").getValue(String.class)), topProfileLayoutStatus);
						}
						topProfileLayoutStatus.setTextColor(0xFF757575);
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
				} else {
				}
			}
			
			@Override
			public void onCancelled(@NonNull DatabaseError databaseError) {
				
			}
		});
		DatabaseReference getFirstUserName = FirebaseDatabase.getInstance().getReference("skyline/users").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
		getFirstUserName.addListenerForSingleValueEvent(new ValueEventListener() {
			@Override
			public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
				if(dataSnapshot.exists()) {
					if (dataSnapshot.child("nickname").getValue(String.class).equals("null")) {
						FirstUserName = "@" + dataSnapshot.child("username").getValue(String.class);
					} else {
						FirstUserName = dataSnapshot.child("nickname").getValue(String.class);
					}
				} else {
					
				}
			}
			@Override
			public void onCancelled(@NonNull DatabaseError databaseError) {
				
			}
		});
		
		_getChatMessagesRef();
	}
	
	
	public void _getChatMessagesRef() {
		Query getChatsMessages = FirebaseDatabase.getInstance().getReference("skyline/chats").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(getIntent().getStringExtra("uid")).limitToLast((int)ChatMessagesLimit);
		getChatsMessages.addValueEventListener(new ValueEventListener() {
			@Override
			public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
				if(dataSnapshot.exists()) {
					ChatMessagesListRecycler.setVisibility(View.VISIBLE);
					noChatText.setVisibility(View.GONE);
					ChatMessagesList.clear();
					
					try {
						GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
						for (DataSnapshot _data : dataSnapshot.getChildren()) {
							HashMap<String, Object> _map = _data.getValue(_ind);
							ChatMessagesList.add(_map);
						}
					} catch (Exception _e) {
						_e.printStackTrace();
					}
					
					if (ChatMessagesList.size() > ChatInitialSize) {
						ChatMessagesListRecycler.getAdapter().notifyDataSetChanged();
						ChatMessagesListRecycler.scrollToPosition(ChatMessagesList.size() - 1);
					} else {
						ChatMessagesListRecycler.getAdapter().notifyDataSetChanged();
					}
					
					ChatInitialSize = ChatMessagesList.size();
				} else {
					ChatMessagesListRecycler.setVisibility(View.GONE);
					noChatText.setVisibility(View.VISIBLE);
				}
			}
			
			@Override
			public void onCancelled(@NonNull DatabaseError databaseError) {
				
			}
		});
	}
	
	
	public void _AudioRecorderStart() {
		cc = Calendar.getInstance();
		recordMs = 0;
		bottomAudioRecorder.setVisibility(View.VISIBLE);
		AudioMessageRecorder = new MediaRecorder();
		
		File getCacheDir = getExternalCacheDir();
		String getCacheDirName = "audio_records";
		File getCacheFolder = new File(getCacheDir, getCacheDirName);
		getCacheFolder.mkdirs();
		File getRecordFile = new File(getCacheFolder, cc.getTimeInMillis() + ".mp3");
		String recordFilePath = getRecordFile.getAbsolutePath();
		
		AudioMessageRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
		AudioMessageRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
		AudioMessageRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
		AudioMessageRecorder.setAudioEncodingBitRate(320000);
		AudioMessageRecorder.setOutputFile(recordFilePath);
		
		try {
			AudioMessageRecorder.prepare();
			AudioMessageRecorder.start();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		vbr.vibrate((long)(48));
		timer = new TimerTask() {
			@Override
			public void run() {
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						recordMs = recordMs + 500;
						bottomAudioRecorderTime.setText(_getDurationString((long)recordMs));
					}
				});
			}
		};
		_timer.scheduleAtFixedRate(timer, (int)(0), (int)(500));
		
	}
	
	
	public void _AudioRecorderStop() {
		bottomAudioRecorder.setVisibility(View.GONE);
		if (AudioMessageRecorder != null) {
			AudioMessageRecorder.stop();
			AudioMessageRecorder.release();
			AudioMessageRecorder = null;
		}
		vbr.vibrate((long)(48));
		timer.cancel();
	}
	
	
	public String _getDurationString(final long _durationInMillis) {
		long seconds = _durationInMillis / 1000;
		long minutes = seconds / 60;
		long hours = minutes / 60;
		seconds %= 60;
		minutes %= 60;
		
		if (hours > 0) {
			return String.format("%02d:%02d:%02d", hours, minutes, seconds);
		} else {
			return String.format("%02d:%02d", minutes, seconds);
		}
	}
	
	
	public void _getOldChatMessagesRef() {
		ChatMessagesLimit = ChatMessagesLimit + 80;
		{
			ExecutorService mExecutorService = Executors.newSingleThreadExecutor();
			Handler mMainHandler = new Handler(Looper.getMainLooper());
			
			mExecutorService.execute(new Runnable() {
				@Override
				public void run() {
					Query getChatsMessages = FirebaseDatabase.getInstance().getReference("skyline/chats").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(getIntent().getStringExtra("uid")).limitToLast((int)ChatMessagesLimit);
					getChatsMessages.addListenerForSingleValueEvent(new ValueEventListener() {
						@Override
						public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
							mMainHandler.post(new Runnable() {
								@Override
								public void run() {
									if(dataSnapshot.exists()) {
										ChatMessagesListRecycler.setVisibility(View.VISIBLE);
										noChatText.setVisibility(View.GONE);
										ChatMessagesList.clear();
										try {
											GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
											for (DataSnapshot _data : dataSnapshot.getChildren()) {
												HashMap<String, Object> _map = _data.getValue(_ind);
												ChatMessagesList.add(_map);
											}
										} catch (Exception _e) {
											_e.printStackTrace();
										}
										
										ChatMessagesListRecycler.getAdapter().notifyDataSetChanged();
									} else {
										ChatMessagesListRecycler.setVisibility(View.GONE);
										noChatText.setVisibility(View.VISIBLE);
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
	
	
	public void _DeleteMessageDialog(final ArrayList<HashMap<String, Object>> _data, final double _position) {
		// Material Delete Dialog
		MaterialAlertDialogBuilder zorry = new MaterialAlertDialogBuilder(ChatActivity.this);
		
		zorry.setTitle("Delete");
		zorry.setMessage("Are you sure you want to delete this message. Please confirm your decision.");
		zorry.setIcon(R.drawable.popup_ic_3);
		zorry.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface _dialog, int _which) {
				FirebaseDatabase.getInstance().getReference("skyline/chats").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(getIntent().getStringExtra("uid")).child(_data.get((int)_position).get("key").toString()).removeValue();
				FirebaseDatabase.getInstance().getReference("skyline/chats").child(getIntent().getStringExtra("uid")).child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(_data.get((int)_position).get("key").toString()).removeValue();
			}
		});
		zorry.setNegativeButton("No", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface _dialog, int _which) {
				
			}
		});
		zorry.create().show();
	}
	
	
	public void _ScrollingText(final TextView _view) {
		_view.setSingleLine(true);
		_view.setEllipsize(TextUtils.TruncateAt.MARQUEE);
		_view.setSelected(true);
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
				_txt.setText("1 " + getResources().getString(R.string.status_text_seconds));
			} else {
				_txt.setText(String.valueOf(seconds) + " " + getResources().getString(R.string.status_text_seconds));
			}
		} else if (minutes < 60) {
			if (minutes < 2) {
				_txt.setText("1 " + getResources().getString(R.string.status_text_minutes));
			} else {
				_txt.setText(String.valueOf(minutes) + " " + getResources().getString(R.string.status_text_minutes));
			}
		} else if (hours < 24) {
			if (hours < 2) {
				_txt.setText("1 " + getResources().getString(R.string.status_text_hours));
			} else {
				_txt.setText(String.valueOf(hours) + " " + getResources().getString(R.string.status_text_hours));
			}
		} else if (days < 7) {
			if (days < 2) {
				_txt.setText("1 " + getResources().getString(R.string.status_text_days));
			} else {
				_txt.setText(String.valueOf(days) + " " + getResources().getString(R.string.status_text_days));
			}
		} else if (weeks < 4) {
			if (weeks < 2) {
				_txt.setText("1 " + getResources().getString(R.string.status_text_week));
			} else {
				_txt.setText(String.valueOf(weeks) + " " + getResources().getString(R.string.status_text_week));
			}
		} else if (months < 12) {
			if (months < 2) {
				_txt.setText("1 " + getResources().getString(R.string.status_text_month));
			} else {
				_txt.setText(String.valueOf(months) + " " + getResources().getString(R.string.status_text_month));
			}
		} else {
			if (years < 2) {
				_txt.setText("1 " + getResources().getString(R.string.status_text_years));
			} else {
				_txt.setText(String.valueOf(years) + " " + getResources().getString(R.string.status_text_years));
			}
		}
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
			ds.setColor(Color.parseColor("#FFFF00"));
			ds.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
		}
	}
	
	
	public void _swipe2rply(final View _view, final double _position, final ArrayList<HashMap<String, Object>> _data) {
		ReplyMessageID = _data.get((int)_position).get("key").toString();
		if (_data.get((int)_position).get("uid").toString().equals(FirebaseAuth.getInstance().getCurrentUser().getUid())) {
			mMessageReplyLayoutBodyRightUsername.setText(FirstUserName);
		} else {
			mMessageReplyLayoutBodyRightUsername.setText(SecondUserName);
		}
		mMessageReplyLayoutBodyRightMessage.setText(_data.get((int)_position).get("message_text").toString());
		mMessageReplyLayout.setVisibility(View.VISIBLE);
		vbr.vibrate((long)(48));
	}
	
	
	public void _send_btn() {
		if (path.equals("")) {
			if (!message_et.getText().toString().trim().equals("")) {
				cc = Calendar.getInstance();
				String uniqueMessageKey = main.push().getKey();
				ChatSendMap = new HashMap<>();
				ChatSendMap.put("uid", FirebaseAuth.getInstance().getCurrentUser().getUid());
				ChatSendMap.put("TYPE", "MESSAGE");
				ChatSendMap.put("message_text", message_et.getText().toString().trim());
				ChatSendMap.put("message_state", "sended");
				if (!ReplyMessageID.equals("null")) {
					ChatSendMap.put("replied_message_id", ReplyMessageID);
				}
				ChatSendMap.put("key", uniqueMessageKey);
				ChatSendMap.put("push_date", String.valueOf((long)(cc.getTimeInMillis())));
				FirebaseDatabase.getInstance().getReference("skyline/chats").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(getIntent().getStringExtra("uid")).child(uniqueMessageKey).updateChildren(ChatSendMap);
				FirebaseDatabase.getInstance().getReference("skyline/chats").child(getIntent().getStringExtra("uid")).child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(uniqueMessageKey).updateChildren(ChatSendMap);
				ChatInboxSend = new HashMap<>();
				ChatInboxSend.put("uid", getIntent().getStringExtra("uid"));
				ChatInboxSend.put("TYPE", "MESSAGE");
				ChatInboxSend.put("last_message_uid", FirebaseAuth.getInstance().getCurrentUser().getUid());
				ChatInboxSend.put("last_message_text", message_et.getText().toString().trim());
				ChatInboxSend.put("last_message_state", "sended");
				ChatInboxSend.put("push_date", String.valueOf((long)(cc.getTimeInMillis())));
				FirebaseDatabase.getInstance().getReference("skyline/inbox").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(getIntent().getStringExtra("uid")).updateChildren(ChatInboxSend);
				ChatInboxSend2 = new HashMap<>();
				ChatInboxSend2.put("uid", FirebaseAuth.getInstance().getCurrentUser().getUid());
				ChatInboxSend2.put("TYPE", "MESSAGE");
				ChatInboxSend2.put("last_message_uid", FirebaseAuth.getInstance().getCurrentUser().getUid());
				ChatInboxSend2.put("last_message_text", message_et.getText().toString().trim());
				ChatInboxSend2.put("last_message_state", "sended");
				ChatInboxSend2.put("push_date", String.valueOf((long)(cc.getTimeInMillis())));
				FirebaseDatabase.getInstance().getReference("skyline/inbox").child(getIntent().getStringExtra("uid")).child(FirebaseAuth.getInstance().getCurrentUser().getUid()).updateChildren(ChatInboxSend2);
				message_et.setText("");
				devider2.setVisibility(View.GONE);
				devider1.setVisibility(View.GONE);
				devider.setVisibility(View.GONE);
				more_send_type_btn.setVisibility(View.GONE);
				send_type_voice_btn.setVisibility(View.GONE);
				attachment_btn.setVisibility(View.GONE);
				mMessageReplyLayout.setVisibility(View.GONE);
				_TransitionManager(camera_gallery_btn_container_round, 200);
				message_input_outlined_round.setOrientation(LinearLayout.HORIZONTAL);
				
				FirebaseDatabase.getInstance().getReference("skyline/chats").child(getIntent().getStringExtra("uid")).child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("typing-message").removeValue();
				ChatSendMap.clear();
				ChatInboxSend.clear();
				ChatInboxSend2.clear();
				ReplyMessageID = "null";
			}
		} else {
			_LoadingDialog(true);
			ImageUploader.uploadImage(path, new ImageUploader.UploadCallback() {
				@Override
				public void onUploadComplete(String imageUrl) {
					path = "";
					_LoadingDialog(false);
					cc = Calendar.getInstance();
					String uniqueMessageKey = main.push().getKey();
					ChatSendMap = new HashMap<>();
					ChatSendMap.put("uid", FirebaseAuth.getInstance().getCurrentUser().getUid());
					ChatSendMap.put("TYPE", "MESSAGE");
					ChatSendMap.put("message_text", message_et.getText().toString().trim());
					ChatSendMap.put("message_image_uri", imageUrl);
					ChatSendMap.put("message_state", "sended");
					if (!ReplyMessageID.equals("null")) {
						ChatSendMap.put("replied_message_id", ReplyMessageID);
					}
					ChatSendMap.put("key", uniqueMessageKey);
					ChatSendMap.put("push_date", String.valueOf((long)(cc.getTimeInMillis())));
					FirebaseDatabase.getInstance().getReference("skyline/chats").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(getIntent().getStringExtra("uid")).child(uniqueMessageKey).updateChildren(ChatSendMap);
					FirebaseDatabase.getInstance().getReference("skyline/chats").child(getIntent().getStringExtra("uid")).child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(uniqueMessageKey).updateChildren(ChatSendMap);
					ChatInboxSend = new HashMap<>();
					ChatInboxSend.put("uid", getIntent().getStringExtra("uid"));
					ChatInboxSend.put("TYPE", "MESSAGE");
					ChatInboxSend.put("last_message_uid", FirebaseAuth.getInstance().getCurrentUser().getUid());
					ChatInboxSend.put("last_message_text", message_et.getText().toString().trim());
					ChatInboxSend.put("last_message_state", "sended");
					ChatInboxSend.put("push_date", String.valueOf((long)(cc.getTimeInMillis())));
					FirebaseDatabase.getInstance().getReference("skyline/inbox").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(getIntent().getStringExtra("uid")).updateChildren(ChatInboxSend);
					ChatInboxSend2 = new HashMap<>();
					ChatInboxSend2.put("uid", FirebaseAuth.getInstance().getCurrentUser().getUid());
					ChatInboxSend2.put("TYPE", "MESSAGE");
					ChatInboxSend2.put("last_message_uid", FirebaseAuth.getInstance().getCurrentUser().getUid());
					ChatInboxSend2.put("last_message_text", message_et.getText().toString().trim());
					ChatInboxSend2.put("last_message_state", "sended");
					ChatInboxSend2.put("push_date", String.valueOf((long)(cc.getTimeInMillis())));
					FirebaseDatabase.getInstance().getReference("skyline/inbox").child(getIntent().getStringExtra("uid")).child(FirebaseAuth.getInstance().getCurrentUser().getUid()).updateChildren(ChatInboxSend2);
					message_et.setText("");
					filename = "";
					file = "";
					file_type_expand = 0;
					devider2.setVisibility(View.GONE);
					devider1.setVisibility(View.GONE);
					devider.setVisibility(View.GONE);
					more_send_type_btn.setVisibility(View.GONE);
					send_type_voice_btn.setVisibility(View.GONE);
					attachment_btn.setVisibility(View.GONE);
					_TransitionManager(camera_gallery_btn_container_round, 200);
					message_input_outlined_round.setOrientation(LinearLayout.HORIZONTAL);
					
					img_container_layout.setVisibility(View.GONE);
					img_upload_prog.setVisibility(View.GONE);
					FirebaseDatabase.getInstance().getReference("skyline/chats").child(getIntent().getStringExtra("uid")).child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("typing-message").removeValue();
					ChatSendMap.clear();
					ChatInboxSend.clear();
					ChatInboxSend2.clear();
					ReplyMessageID = "null";
					mMessageReplyLayout.setVisibility(View.GONE);
				}
				
				@Override
				public void onUploadError(String errorMessage) {
					
					
					
					SketchwareUtil.showMessage(getApplicationContext(), "Failed to upload...");
				}
			});
			
		}
	}
	
	
	public void _Block(final String _uid) {
		block = new HashMap<>();
		block.put(_uid, "true");
		blocklist.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).updateChildren(block);
		block.clear();
	}
	
	
	public void _TransitionManager(final View _view, final double _duration) {
		LinearLayout viewgroup =(LinearLayout) _view;
		
		android.transition.AutoTransition autoTransition = new android.transition.AutoTransition(); autoTransition.setDuration((long)_duration); android.transition.TransitionManager.beginDelayedTransition(viewgroup, autoTransition);
	}
	
	
	public void _Unblock_this_user() {
		DatabaseReference blocklistRef = FirebaseDatabase.getInstance().getReference("skyline/blocklist");
		String myUid = FirebaseAuth.getInstance().getCurrentUser().getUid();
		String uidToRemove = getIntent().getStringExtra("uid");
		
		blocklistRef.child(myUid).child(uidToRemove).removeValue();
		// This code will restart the activity
		finish();
		startActivity(intent);
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
	
	
	public void _ImgRound(final ImageView _imageview, final double _value) {
		android.graphics.drawable.GradientDrawable gd = new android.graphics.drawable.GradientDrawable ();
		gd.setColor(android.R.color.transparent);
		gd.setCornerRadius((int)_value);
		_imageview.setClipToOutline(true);
		_imageview.setBackground(gd);
	}
	
	
	public void _OpenWebView(final String _URL) {
		AndroidDevelopersBlogURL = _URL;
		CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
		builder.setToolbarColor(Color.parseColor("#242D39"));
		CustomTabsIntent customtabsintent = builder.build();
		customtabsintent.launchUrl(this, Uri.parse(AndroidDevelopersBlogURL));
	}
	
	public class ChatMessagesListRecyclerAdapter extends RecyclerView.Adapter<ChatMessagesListRecyclerAdapter.ViewHolder> {
		
		ArrayList<HashMap<String, Object>> _data;
		
		public ChatMessagesListRecyclerAdapter(ArrayList<HashMap<String, Object>> _arr) {
			_data = _arr;
		}
		
		@Override
		public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
			LayoutInflater _inflater = getLayoutInflater();
			View _v = _inflater.inflate(R.layout.chat_msg_cv_synapse, null);
			RecyclerView.LayoutParams _lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
			_v.setLayoutParams(_lp);
			return new ViewHolder(_v);
		}
		
		@Override
		public void onBindViewHolder(ViewHolder _holder, final int _position) {
			View _view = _holder.itemView;
			
			final ProgressBar showOldMessagesProgress = _view.findViewById(R.id.showOldMessagesProgress);
			final LinearLayout body = _view.findViewById(R.id.body);
			final androidx.cardview.widget.CardView mProfileCard = _view.findViewById(R.id.mProfileCard);
			final LinearLayout message_layout = _view.findViewById(R.id.message_layout);
			final ImageView mProfileImage = _view.findViewById(R.id.mProfileImage);
			final LinearLayout menuView_d = _view.findViewById(R.id.menuView_d);
			final LinearLayout messageBG = _view.findViewById(R.id.messageBG);
			final LinearLayout my_message_info = _view.findViewById(R.id.my_message_info);
			final LinearLayout mRepliedMessageLayout = _view.findViewById(R.id.mRepliedMessageLayout);
			final androidx.cardview.widget.CardView mMessageImageBody = _view.findViewById(R.id.mMessageImageBody);
			final ImageView typingStatusIcon = _view.findViewById(R.id.typingStatusIcon);
			final com.airbnb.lottie.LottieAnimationView lottie1 = _view.findViewById(R.id.lottie1);
			final TextView message_text = _view.findViewById(R.id.message_text);
			final LinearLayout mRepliedMessageLayoutLeftBar = _view.findViewById(R.id.mRepliedMessageLayoutLeftBar);
			final LinearLayout mRepliedMessageLayoutRightBody = _view.findViewById(R.id.mRepliedMessageLayoutRightBody);
			final TextView mRepliedMessageLayoutUsername = _view.findViewById(R.id.mRepliedMessageLayoutUsername);
			final TextView mRepliedMessageLayoutMessage = _view.findViewById(R.id.mRepliedMessageLayoutMessage);
			final ImageView mMessageImageView = _view.findViewById(R.id.mMessageImageView);
			final TextView date = _view.findViewById(R.id.date);
			final ImageView message_state = _view.findViewById(R.id.message_state);
			
			typingStatusIcon.setVisibility(View.GONE);
			lottie1.setVisibility(View.GONE);
			RecyclerView.LayoutParams _lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
			_view.setLayoutParams(_lp);
			Calendar push = Calendar.getInstance();
			mProfileCard.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b) { this.setCornerRadius(a); this.setColor(b); return this; } }.getIns((int)300, Color.TRANSPARENT));
			_ImageColor(typingStatusIcon, 0xFFBDBDBD);
			if (_data.get((int)_position).containsKey("typingMessageStatus")) {
				body.setTranslationY((float)(10));
				{
					android.graphics.drawable.GradientDrawable SketchUi = new android.graphics.drawable.GradientDrawable();
					int d = (int) getApplicationContext().getResources().getDisplayMetrics().density;
					SketchUi.setColor(0xFFFFFFFF);
					SketchUi.setCornerRadius(d*25);
					messageBG.setBackground(SketchUi);
				}
				_setMargin(message_layout, 60, 0, 4, 4);
				message_layout.setGravity(Gravity.CENTER | Gravity.LEFT);
				body.setGravity(Gravity.TOP | Gravity.LEFT);
				message_state.setVisibility(View.GONE);
				// for test
				mProfileCard.setVisibility(View.GONE);
				if (SecondUserAvatar.equals("null_banned")) {
					mProfileImage.setImageResource(R.drawable.banned_avatar);
				} else {
					if (SecondUserAvatar.equals("null")) {
						mProfileImage.setImageResource(R.drawable.avatar);
					} else {
						Glide.with(getApplicationContext()).load(Uri.parse(SecondUserAvatar)).into(mProfileImage);
					}
				}
				showOldMessagesProgress.setVisibility(View.GONE);
				my_message_info.setVisibility(View.GONE);
				message_text.setVisibility(View.GONE);
				mMessageImageBody.setVisibility(View.GONE);
				mRepliedMessageLayout.setVisibility(View.GONE);
				lottie1.setVisibility(View.VISIBLE);
				typingStatusIcon.setVisibility(View.GONE);
			} else {
				body.setTranslationY((float)(0));
				if (_data.get((int)_position).get("uid").toString().equals(FirebaseAuth.getInstance().getCurrentUser().getUid())) {
					body.setGravity(Gravity.TOP | Gravity.RIGHT);
					message_layout.setGravity(Gravity.CENTER | Gravity.RIGHT);
					_setMargin(message_layout, 0, 60, 0, 0);
					message_text.setTextColor(0xFFFFFFFF);
					message_state.setVisibility(View.VISIBLE);
					mProfileCard.setVisibility(View.GONE);
					mRepliedMessageLayoutLeftBar.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b) { this.setCornerRadius(a); this.setColor(b); return this; } }.getIns((int)300, 0xFFFFFFFF));
					mRepliedMessageLayoutUsername.setTextColor(0xFFEEEEEE);
					mRepliedMessageLayoutMessage.setTextColor(0xFFEEEEEE);
					try{
						// Retrieve corner radius value from settings
						int cornerRadius = 0; // Default value
						
						try {
							cornerRadius = Integer.parseInt(appSettings.getString("ChatCornerRadius", ""));
						} catch (Exception e) {
							try {
								cornerRadius = (int) Double.parseDouble(appSettings.getString("ChatCornerRadius", ""));
							} catch (Exception ex) {
								cornerRadius = 27; // Default radius if parsing fails
							}
						}
						
						// Apply the retrieved corner radius to the GradientDrawable
						android.graphics.drawable.GradientDrawable SketchUi = new android.graphics.drawable.GradientDrawable();
						int d = (int) getApplicationContext().getResources().getDisplayMetrics().density;
						SketchUi.setColor(0xFF6B4CFF);
						SketchUi.setCornerRadius(d * cornerRadius);
						android.graphics.drawable.RippleDrawable SketchUiRD = new android.graphics.drawable.RippleDrawable(
						new android.content.res.ColorStateList(new int[][]{new int[]{}}, new int[]{0xFFE0E0E0}), SketchUi, null
						);
						
						messageBG.setBackground(SketchUiRD);
						messageBG.setClickable(true);
					}catch(Exception e){
						{
							android.graphics.drawable.GradientDrawable SketchUi = new android.graphics.drawable.GradientDrawable();
							int d = (int) getApplicationContext().getResources().getDisplayMetrics().density;
							SketchUi.setColor(0xFF6B4CFF);
							SketchUi.setCornerRadius(d*27);
							android.graphics.drawable.RippleDrawable SketchUiRD = new android.graphics.drawable.RippleDrawable(new android.content.res.ColorStateList(new int[][]{new int[]{}}, new int[]{0xFFE0E0E0}), SketchUi, null);
							messageBG.setBackground(SketchUiRD);
							messageBG.setClickable(true);
						}
					}
				} else {
					body.setGravity(Gravity.TOP | Gravity.LEFT);
					message_layout.setGravity(Gravity.CENTER | Gravity.LEFT);
					_setMargin(message_layout, 60, 0, 4, 0);
					message_text.setTextColor(0xFF000000);
					message_state.setVisibility(View.GONE);
					// for test
					mProfileCard.setVisibility(View.GONE);
					mRepliedMessageLayoutLeftBar.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b) { this.setCornerRadius(a); this.setColor(b); return this; } }.getIns((int)300, getResources().getColor(R.color.colorPrimary)));
					mRepliedMessageLayoutUsername.setTextColor(getResources().getColor(R.color.colorPrimary));
					mRepliedMessageLayoutMessage.setTextColor(0xFF000000);
					if (SecondUserAvatar.equals("null")) {
						mProfileImage.setImageResource(R.drawable.avatar);
					} else {
						Glide.with(getApplicationContext()).load(Uri.parse(SecondUserAvatar)).into(mProfileImage);
					}
					try{
						// Retrieve corner radius value from settings
						int cornerRadius = 0; // Default value
						
						try {
							cornerRadius = Integer.parseInt(appSettings.getString("ChatCornerRadius", ""));
						} catch (Exception e) {
							try {
								cornerRadius = (int) Double.parseDouble(appSettings.getString("ChatCornerRadius", ""));
							} catch (Exception ex) {
								cornerRadius = 27; // Default radius if parsing fails
							}
						}
						
						// Apply the retrieved corner radius to the GradientDrawable
						android.graphics.drawable.GradientDrawable SketchUi = new android.graphics.drawable.GradientDrawable();
						int d = (int) getApplicationContext().getResources().getDisplayMetrics().density;
						SketchUi.setColor(0xFFFFFFFF);
						SketchUi.setCornerRadius(d * cornerRadius);
						SketchUi.setStroke(d * 2, 0xFFDFDFDF);
						android.graphics.drawable.RippleDrawable SketchUiRD = new android.graphics.drawable.RippleDrawable(
						new android.content.res.ColorStateList(new int[][]{new int[]{}}, new int[]{0xFFE0E0E0}), SketchUi, null
						);
						
						messageBG.setBackground(SketchUiRD);
						messageBG.setClickable(true);
					}catch(Exception e){
						{
							android.graphics.drawable.GradientDrawable SketchUi = new android.graphics.drawable.GradientDrawable();
							int d = (int) getApplicationContext().getResources().getDisplayMetrics().density;
							SketchUi.setColor(0xFFFFFFFF);
							SketchUi.setCornerRadius(d*27);
							SketchUi.setStroke(d*2,0xFFDFDFDF);
							android.graphics.drawable.RippleDrawable SketchUiRD = new android.graphics.drawable.RippleDrawable(new android.content.res.ColorStateList(new int[][]{new int[]{}}, new int[]{0xFFE0E0E0}), SketchUi, null);
							messageBG.setBackground(SketchUiRD);
							messageBG.setClickable(true);
						}
					}
				}
				/*
_textview_mh(message_text, _data.get((int)_position).get("message_text").toString());
*/
				new TextStylingUtil(message_text.getContext()).applyStyling(_data.get((int)_position).get("message_text").toString(), message_text);
				push.setTimeInMillis((long)(Double.parseDouble(_data.get((int)_position).get("push_date").toString())));
				date.setText(new SimpleDateFormat("hh:mm a").format(push.getTime()));
				if (_data.get((int)_position).containsKey("replied_message_id")) {
					{
						ExecutorService mExecutorService = Executors.newSingleThreadExecutor();
						Handler mMainHandler = new Handler(Looper.getMainLooper());
						
						mExecutorService.execute(new Runnable() {
							@Override
							public void run() {
								DatabaseReference getRepliedMessageRef = FirebaseDatabase.getInstance().getReference("skyline/chats").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(getIntent().getStringExtra("uid")).child(_data.get((int)_position).get("replied_message_id").toString());
								getRepliedMessageRef.addListenerForSingleValueEvent(new ValueEventListener() {
									@Override
									public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
										mMainHandler.post(new Runnable() {
											@Override
											public void run() {
												if(dataSnapshot.exists()) {
													if (dataSnapshot.child("uid").getValue(String.class).equals(FirebaseAuth.getInstance().getCurrentUser().getUid())) {
														mRepliedMessageLayoutUsername.setText(FirstUserName);
													} else {
														mRepliedMessageLayoutUsername.setText(SecondUserName);
													}
													if (dataSnapshot.child("message_text").getValue(String.class) != null) {
														mRepliedMessageLayoutMessage.setText(dataSnapshot.child("message_text").getValue(String.class));
														mRepliedMessageLayout.setVisibility(View.VISIBLE);
													} else {
														mRepliedMessageLayout.setVisibility(View.GONE);
													}
												} else {
													mRepliedMessageLayout.setVisibility(View.GONE);
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
					
					mRepliedMessageLayout.setVisibility(View.VISIBLE);
				} else {
					mRepliedMessageLayout.setVisibility(View.GONE);
				}
				if (_data.get((int)_position).containsKey("message_image_uri")) {
					Glide.with(getApplicationContext()).load(Uri.parse(_data.get((int)_position).get("message_image_uri").toString())).into(mMessageImageView);
					_setMargin(message_text, 0, 0, 8, 0);
					mMessageImageBody.setVisibility(View.VISIBLE);
					mMessageImageView.setOnClickListener(new View.OnClickListener() {
						@Override
						public void onClick(View _view) {
							_OpenWebView(_data.get((int)_position).get("message_image_uri").toString());
						}
					});
				} else {
					_setMargin(message_text, 0, 0, 0, 0);
					mMessageImageBody.setVisibility(View.GONE);
				}
				if (_data.get((int)_position).get("message_state").toString().equals("sended")) {
					message_state.setImageResource(R.drawable.icon_done_round);
					_ImageColor(message_state, 0xFF424242);
				} else {
					if (_data.get((int)_position).get("message_state").toString().equals("seen")) {
						message_state.setImageResource(R.drawable.icon_done_all_round);
						_ImageColor(message_state, getResources().getColor(R.color.colorPrimary));
					}
				}
				if (_data.size() > 79) {
					if (_position == 0) {
						showOldMessagesProgress.setVisibility(View.VISIBLE);
						loadTimer = new TimerTask() {
							@Override
							public void run() {
								runOnUiThread(new Runnable() {
									@Override
									public void run() {
										_getOldChatMessagesRef();
										showOldMessagesProgress.setVisibility(View.GONE);
									}
								});
							}
						};
						_timer.schedule(loadTimer, (int)(1000));
					} else {
						showOldMessagesProgress.setVisibility(View.GONE);
					}
				} else {
					showOldMessagesProgress.setVisibility(View.GONE);
				}
				if (_position == 0) {
					my_message_info.setVisibility(View.GONE);
					mProfileImage.setVisibility(View.GONE);
					if (_data.size() == 1) {
						my_message_info.setVisibility(View.VISIBLE);
						mProfileImage.setVisibility(View.VISIBLE);
					} else {
						if (!_data.get((int)_position).get("uid").toString().equals(_data.get((int)_position + 1).get("uid").toString())) {
							my_message_info.setVisibility(View.VISIBLE);
							mProfileImage.setVisibility(View.VISIBLE);
						}
					}
				} else {
					if (!_data.get((int)_position).get("uid").toString().equals(_data.get((int)_position - 1).get("uid").toString())) {
						my_message_info.setVisibility(View.GONE);
						mProfileImage.setVisibility(View.GONE);
						if (_position == (_data.size() - 1)) {
							my_message_info.setVisibility(View.VISIBLE);
							mProfileImage.setVisibility(View.VISIBLE);
						} else {
							if (!_data.get((int)_position).get("uid").toString().equals(_data.get((int)_position + 1).get("uid").toString())) {
								my_message_info.setVisibility(View.VISIBLE);
								mProfileImage.setVisibility(View.VISIBLE);
							}
						}
					} else {
						if (_position == (_data.size() - 1)) {
							my_message_info.setVisibility(View.VISIBLE);
							mProfileImage.setVisibility(View.VISIBLE);
						} else {
							if (_data.get((int)_position).get("uid").toString().equals(_data.get((int)_position - 1).get("uid").toString()) && _data.get((int)_position).get("uid").toString().equals(_data.get((int)_position + 1).get("uid").toString())) {
								my_message_info.setVisibility(View.GONE);
								mProfileImage.setVisibility(View.GONE);
							} else {
								if (_data.get((int)_position).get("uid").toString().equals(_data.get((int)_position + 1).get("uid").toString())) {
									my_message_info.setVisibility(View.GONE);
									mProfileImage.setVisibility(View.GONE);
								} else {
									my_message_info.setVisibility(View.VISIBLE);
									mProfileImage.setVisibility(View.VISIBLE);
								}
							}
						}
					}
				}
				if (!_data.get((int)_position).get("uid").toString().equals(FirebaseAuth.getInstance().getCurrentUser().getUid())) {
					if (_data.get((int)_position).get("message_state").toString().equals("sended")) {
						FirebaseDatabase.getInstance().getReference("skyline/chats").child(getIntent().getStringExtra("uid")).child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(_data.get((int)_position).get("key").toString()).child("message_state").setValue("seen");
						FirebaseDatabase.getInstance().getReference("skyline/chats").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(getIntent().getStringExtra("uid")).child(_data.get((int)_position).get("key").toString()).child("message_state").setValue("seen");
						FirebaseDatabase.getInstance().getReference("skyline/inbox").child(getIntent().getStringExtra("uid")).child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("last_message_state").setValue("seen");
					}
				}
				lottie1.setVisibility(View.GONE);
				message_text.setVisibility(View.VISIBLE);
			}
			message_layout.setOnLongClickListener(new View.OnLongClickListener() {
				@Override
				public boolean onLongClick(View _view) {
					_messageOverviewPopup(menuView_d, _position, _data);
					return true;
				}
			});
			messageBG.setOnLongClickListener(new View.OnLongClickListener() {
				@Override
				public boolean onLongClick(View _view) {
					_messageOverviewPopup(menuView_d, _position, _data);
					return true;
				}
			});
			message_text.setOnLongClickListener(new View.OnLongClickListener() {
				@Override
				public boolean onLongClick(View _view) {
					_messageOverviewPopup(menuView_d, _position, _data);
					return true;
				}
			});
			mProfileImage.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View _view) {
					intent.setClass(getApplicationContext(), ProfileActivity.class);
					intent.putExtra("uid", getIntent().getStringExtra("uid"));
					startActivity(intent);
				}
			});
			try{
				//Retrieve text size from settings
				try {
					message_text.setTextSize((int) Integer.parseInt(appSettings.getString("ChatTextSize", "")));
					mRepliedMessageLayoutMessage.setTextSize((int) Integer.parseInt(appSettings.getString("ChatTextSize", "")));
				} catch (Exception e) {
					try {
						message_text.setTextSize((int) Double.parseDouble(appSettings.getString("ChatTextSize", "")));
						mRepliedMessageLayoutMessage.setTextSize((int) Double.parseDouble(appSettings.getString("ChatTextSize", "")));
					} catch (Exception ex) { // Changed variable name to ex
						message_text.setTextSize((int) 16);
					}
				}
			}catch(Exception e){
				
			}
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
