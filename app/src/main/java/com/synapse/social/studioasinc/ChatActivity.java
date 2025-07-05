package com.synapse.social.studioasinc;

//===== ANDROID & JAVA IMPORTS =======
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
import android.text.*;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.style.*;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.util.*;
import android.view.*;
import android.view.View;
import android.view.View.*;
import android.view.animation.*;
import android.webkit.*;
import android.widget.*;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.TextView;
import java.io.*;
import java.io.File;
import java.io.InputStream;
import java.text.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.*;

//===== ANDROIDX & GOOGLE MATERIAL IMPORTS =======
import androidx.annotation.*;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.Adapter;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputLayout;
import com.bumptech.glide.Glide;

//===== FIREBASE IMPORTS =======
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
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
import com.google.firebase.database.Query;
import android.database.Cursor;

public class ChatActivity extends AppCompatActivity {

	//===== CONSTANTS AND REQUEST CODES =======
	public final int REQ_CD_IMAGE_PICKER = 101;
	private static final int PERMISSION_REQUEST_CODE = 1000;

	//===== FIREBASE & ANDROID SERVICE VARIABLES =======
	private FirebaseDatabase _firebase = FirebaseDatabase.getInstance();
	private FirebaseStorage _firebase_storage = FirebaseStorage.getInstance();
	private FirebaseAuth auth;
	private Vibrator vbr;

	//===== DATABASE REFERENCES =======
	private DatabaseReference blocklist_ref = _firebase.getReference("skyline/blocklist");
	private DatabaseReference chat_ref;
	private StorageReference upload_selected_img_ref;

	//===== FIREBASE LISTENERS =======
	private ChildEventListener _blocklist_child_listener;
	private ChildEventListener _chat_messages_listener;

	//===== VIEWS =======
	private CenterCropLinearLayoutNoEffect body;
	private LinearLayout mMessageReplyLayout;
	private LinearLayout message_input_overall_container;
	private CenterCropLinearLayoutNoEffect bottomAudioRecorder;
	private TextView unblock_btn;
	private TextView blocked_txt;
	private ImageView back;
	private LinearLayout topProfileLayout;
	private ImageView imageview2;
	private ImageView imageview1;
	private ImageView more;
	private CardView topProfileCard;
	private ImageView topProfileLayoutProfileImage;
	private TextView topProfileLayoutStatus;
	private TextView topProfileLayoutUsername;
	private ImageView topProfileLayoutGenderBadge;
	private ImageView topProfileLayoutVerifiedBadge;
	private LinearLayout bannedUserInfo;
	private RecyclerView ChatMessagesListRecycler;
	private TextView noChatText;
	private ImageView mMessageReplyLayoutBodyCancel;
	private TextView mMessageReplyLayoutBodyRightUsername;
	private TextView mMessageReplyLayoutBodyRightMessage;
	private LinearLayout message_input_outlined_round;
	private LinearLayout send_round_btn;
	private LinearLayout img_container_layout;
	private FadeEditText message_et;
	private LinearLayout camera_gallery_btn_container_round;
	private ImageView remove_selected_img_icon;
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

	//===== DATA & STATE VARIABLES =======
	private ArrayList<HashMap<String, Object>> ChatMessagesList = new ArrayList<>();
	private String SecondUserAvatar = "null";
	private String ReplyMessageID = "null";
	private String SecondUserName = "";
	private String FirstUserName = "";
	private String file_path = "";
	private String file_name = "";
	private boolean file_type_expanded = false;
	private boolean isLoadingOldMessages = false;
	private String oldestMessageKey = "";
	private int chatTextSize = 16;
	private int chatCornerRadius = 27;

	//===== TIMERS & DIALOGS =======
	private Timer _timer = new Timer();
	private TimerTask recordTimer;
	private ProgressDialog SynapseLoadingDialog;

	//===== UTILITIES =======
	private MediaRecorder AudioMessageRecorder;
	private double recordMs = 0;
	private Intent intent = new Intent();
	private SharedPreferences appSettings;
	private SharedPreferences theme;

	//===== ACTIVITY LIFECYCLE METHODS =======
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.chat);

		FirebaseApp.initializeApp(this);
		_initializeViews();
		_initializeListeners();

		if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED
		|| ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED
		|| ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_DENIED) {
			ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.RECORD_AUDIO}, PERMISSION_REQUEST_CODE);
		} else {
			initializeLogic();
		}
	}

	@Override
	public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
		super.onRequestPermissionsResult(requestCode, permissions, grantResults);
		if (requestCode == PERMISSION_REQUEST_CODE) {
			if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
				initializeLogic();
			} else {
				// Handle permission denial gracefully
				Toast.makeText(this, "Permissions are required to use chat features.", Toast.LENGTH_LONG).show();
				finish();
			}
		}
	}

	private void initializeLogic() {
		_UI();
		_cacheAppSettings();
		_getUserReference();
		_attachBlocklistListener();
		_attachChatListener();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		// Remove listeners to prevent memory leaks
		if (chat_ref != null && _chat_messages_listener != null) {
			chat_ref.removeEventListener(_chat_messages_listener);
		}
		if (blocklist_ref != null && _blocklist_child_listener != null) {
			blocklist_ref.removeEventListener(_blocklist_child_listener);
		}

		// Cancel any running timers
		if (recordTimer != null) {
			recordTimer.cancel();
		}
		if(_timer != null) {
			_timer.cancel();
		}

		// Remove typing status from Firebase
		if (auth.getCurrentUser() != null && getIntent().hasExtra("uid")) {
			_firebase.getReference("skyline/chats").child(getIntent().getStringExtra("uid")).child(auth.getCurrentUser().getUid()).child("typing-message").removeValue();
		}
	}

	@Override
	public void onBackPressed() {
		finish();
	}

	@Override
	protected void onActivityResult(int _requestCode, int _resultCode, Intent _data) {
		super.onActivityResult(_requestCode, _resultCode, _data);
		if (_requestCode == REQ_CD_IMAGE_PICKER && _resultCode == Activity.RESULT_OK && _data != null) {
			Uri uri = _data.getData();
			if (uri != null) {
				file_path = FileUtil.convertUriToFilePath(getApplicationContext(), uri);
				file_name = new File(file_path).getName();
				img_name.setText(file_name);
				selected_img_preview.setImageURI(uri);

				_TransitionManager(message_input_outlined_round, 150);
				message_input_outlined_round.setOrientation(LinearLayout.VERTICAL);
				img_container_layout.setVisibility(View.VISIBLE);
				img_upload_prog.setVisibility(View.GONE);
			}
		}
	}

	//===== INITIALIZATION METHODS =======
	private void _initializeViews() {
		body = findViewById(R.id.body);
		mMessageReplyLayout = findViewById(R.id.mMessageReplyLayout);
		message_input_overall_container = findViewById(R.id.message_input_overall_container);
		bottomAudioRecorder = findViewById(R.id.bottomAudioRecorder);
		unblock_btn = findViewById(R.id.unblock_btn);
		blocked_txt = findViewById(R.id.blocked_txt);
		back = findViewById(R.id.back);
		topProfileLayout = findViewById(R.id.topProfileLayout);
		imageview2 = findViewById(R.id.imageview2);
		imageview1 = findViewById(R.id.imageview1);
		more = findViewById(R.id.more);
		topProfileCard = findViewById(R.id.topProfileCard);
		topProfileLayoutProfileImage = findViewById(R.id.topProfileLayoutProfileImage);
		topProfileLayoutStatus = findViewById(R.id.topProfileLayoutStatus);
		topProfileLayoutUsername = findViewById(R.id.topProfileLayoutUsername);
		topProfileLayoutGenderBadge = findViewById(R.id.topProfileLayoutGenderBadge);
		topProfileLayoutVerifiedBadge = findViewById(R.id.topProfileLayoutVerifiedBadge);
		bannedUserInfo = findViewById(R.id.bannedUserInfo);
		ChatMessagesListRecycler = findViewById(R.id.ChatMessagesListRecycler);
		noChatText = findViewById(R.id.noChatText);
		mMessageReplyLayoutBodyCancel = findViewById(R.id.mMessageReplyLayoutBodyCancel);
		mMessageReplyLayoutBodyRightUsername = findViewById(R.id.mMessageReplyLayoutBodyRightUsername);
		mMessageReplyLayoutBodyRightMessage = findViewById(R.id.mMessageReplyLayoutBodyRightMessage);
		message_input_outlined_round = findViewById(R.id.message_input_outlined_round);
		send_round_btn = findViewById(R.id.send_round_btn);
		img_container_layout = findViewById(R.id.img_container_layout);
		message_et = findViewById(R.id.message_et);
		camera_gallery_btn_container_round = findViewById(R.id.camera_gallery_btn_container_round);
		remove_selected_img_icon = findViewById(R.id.remove_selected_img_icon);
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
		appSettings = getSharedPreferences("appSettings", Activity.MODE_PRIVATE);
		theme = getSharedPreferences("theme", Activity.MODE_PRIVATE);

		upload_selected_img_ref = _firebase_storage.getReference("synapse/chats/images");
	}

	private void _initializeListeners() {
		back.setOnClickListener(_view -> onBackPressed());

		View.OnClickListener profileClickListener = _view -> {
			intent.setClass(getApplicationContext(), Chat2ndUserMoreSettingsActivity.class);
			intent.putExtra("uid", getIntent().getStringExtra("uid"));
			startActivity(intent);
		};
		topProfileLayout.setOnClickListener(profileClickListener);
		more.setOnClickListener(profileClickListener);

		imageview1.setOnClickListener(_view -> SketchwareUtil.showMessage(getApplicationContext(), "Coming up!"));
		imageview2.setOnClickListener(_view -> SketchwareUtil.showMessage(getApplicationContext(), "Coming up!"));

		mMessageReplyLayoutBodyCancel.setOnClickListener(_view -> {
			ReplyMessageID = "null";
			mMessageReplyLayout.setVisibility(View.GONE);
			vbr.vibrate(48);
		});

		send_round_btn.setOnClickListener(_view -> _send_btn());

		unblock_btn.setOnClickListener(_view -> _unblock_this_user());

		remove_selected_img_icon.setOnClickListener(_view -> {
			img_container_layout.setVisibility(View.GONE);
			file_path = "";
			file_name = "";
			_TransitionManager(message_input_outlined_round, 125);
			if (message_et.getText().toString().isEmpty()) {
				message_input_outlined_round.setOrientation(LinearLayout.HORIZONTAL);
			} else {
				message_input_outlined_round.setOrientation(LinearLayout.VERTICAL);
			}
		});

		expand_send_type_btn.setOnClickListener(_view -> {
			if (file_type_expanded) {
				_collapse();
			} else {
				_expand();
			}
		});

		gallery_btn.setOnClickListener(_view -> {
			Intent imagePickerIntent = new Intent(Intent.ACTION_GET_CONTENT);
			imagePickerIntent.setType("image/*");
			startActivityForResult(imagePickerIntent, REQ_CD_IMAGE_PICKER);
		});

		send_type_voice_btn.setOnClickListener(_view -> _AudioRecorderStart());

		bottomAudioRecorderCancel.setOnClickListener(_view -> _AudioRecorderStop());

		message_et.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				String text = s.toString();
				DatabaseReference typingRef = _firebase.getReference("skyline/chats").child(getIntent().getStringExtra("uid")).child(auth.getCurrentUser().getUid()).child("typing-message");

				if (text.isEmpty()) {
					send_ic.setImageResource(R.drawable.love_ic);
					message_input_outlined_round.setBackgroundResource(R.drawable.input_bg_collapsed);
					message_input_outlined_round.setOrientation(LinearLayout.HORIZONTAL);
					typingRef.removeValue();
				} else {
					send_ic.setImageResource(R.drawable.icons_3);
					message_input_outlined_round.setBackgroundResource(R.drawable.input_bg_expanded);
					message_input_outlined_round.setOrientation(LinearLayout.VERTICAL);

					HashMap<String, Object> typingSnd = new HashMap<>();
					typingSnd.put("uid", auth.getCurrentUser().getUid());
					typingSnd.put("typingMessageStatus", "true");
					typingRef.setValue(typingSnd);
				}
				_TransitionManager(message_input_overall_container, 125);
			}
			@Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
			@Override public void afterTextChanged(Editable s) {}
		});
	}

	//===== FIREBASE DATA LISTENERS =======
	private void _attachBlocklistListener() {
		if (_blocklist_child_listener != null) return;
		_blocklist_child_listener = new ChildEventListener() {
			@Override
			public void onChildAdded(DataSnapshot _param1, String _param2) { _updateBlockStatus(_param1); }
			@Override
			public void onChildChanged(DataSnapshot _param1, String _param2) { _updateBlockStatus(_param1); }
			@Override
			public void onChildMoved(DataSnapshot _param1, String _param2) {}
			@Override
			public void onChildRemoved(DataSnapshot _param1) {
				_updateBlockStatus(_param1); // Re-evaluate block status
			}
			@Override
			public void onCancelled(DatabaseError _param1) {}
		};
		blocklist_ref.addChildEventListener(_blocklist_child_listener);
	}

	private void _updateBlockStatus(DataSnapshot snapshot) {
		String myUid = auth.getCurrentUser().getUid();
		String otherUid = getIntent().getStringExtra("uid");

		boolean iBlockedUser = false;
		boolean userBlockedMe = false;

		// Check if I blocked the other user
		if (snapshot.getKey().equals(myUid) && snapshot.hasChild(otherUid)) {
			iBlockedUser = true;
		}

		// Check if the other user blocked me
		if (snapshot.getKey().equals(otherUid) && snapshot.hasChild(myUid)) {
			userBlockedMe = true;
		}

		unblock_btn.setVisibility(iBlockedUser ? View.VISIBLE : View.GONE);
		blocked_txt.setVisibility(userBlockedMe ? View.VISIBLE : View.GONE);
		message_input_overall_container.setVisibility(iBlockedUser || userBlockedMe ? View.GONE : View.VISIBLE);
	}

	private void _attachChatListener() {
		if (_chat_messages_listener != null) return; // Listener already attached

		chat_ref = _firebase.getReference("skyline/chats")
				.child(auth.getCurrentUser().getUid())
				.child(getIntent().getStringExtra("uid"));

		_chat_messages_listener = new ChildEventListener() {
			@Override
			public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String previousChildName) {
				GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
				HashMap<String, Object> _map = dataSnapshot.getValue(_ind);
				if (_map != null) {
					_map.put("key", dataSnapshot.getKey()); // Make sure key is included
					if (oldestMessageKey.isEmpty() || dataSnapshot.getKey().compareTo(oldestMessageKey) < 0) {
						oldestMessageKey = dataSnapshot.getKey();
					}
					ChatMessagesList.add(_map);
					ChatMessagesListRecycler.getAdapter().notifyItemInserted(ChatMessagesList.size() - 1);
					ChatMessagesListRecycler.scrollToPosition(ChatMessagesList.size() - 1);
					noChatText.setVisibility(View.GONE);
				}
			}

			@Override
			public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String previousChildName) {
				GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
				HashMap<String, Object> _map = dataSnapshot.getValue(_ind);
				String messageKey = dataSnapshot.getKey();

				if (_map != null) {
					_map.put("key", messageKey);
					for (int i = 0; i < ChatMessagesList.size(); i++) {
						if (ChatMessagesList.get(i).get("key").toString().equals(messageKey)) {
							ChatMessagesList.set(i, _map);
							ChatMessagesListRecycler.getAdapter().notifyItemChanged(i);
							break;
						}
					}
				}
			}

			@Override
			public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
				String messageKey = dataSnapshot.getKey();
				for (int i = 0; i < ChatMessagesList.size(); i++) {
					if (ChatMessagesList.get(i).get("key").toString().equals(messageKey)) {
						ChatMessagesList.remove(i);
						ChatMessagesListRecycler.getAdapter().notifyItemRemoved(i);
						break;
					}
				}
			}

			@Override public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {}
			@Override public void onCancelled(@NonNull DatabaseError error) {}
		};
		chat_ref.limitToLast(50).addChildEventListener(_chat_messages_listener);
	}

	//===== UI & THEME METHODS =======
	private void _UI() {
		blocked_txt.setVisibility(View.GONE);
		blocked_txt.setText(Html.fromHtml("<p>You can't reply to this conversation. <a href=\"https://example.com/learn-more\" style=\"color: #2962FF;\"><b>Learn more</b></a></p>"));
		camera_gallery_btn_container_round.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b, int c, int d) { this.setCornerRadius(a); this.setStroke(b, c); this.setColor(d); return this; } }.getIns((int)360, (int)0, Color.TRANSPARENT, 0xFFF0F3F8));
		send_round_btn.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b, int c, int d) { this.setCornerRadius(a); this.setStroke(b, c); this.setColor(d); return this; } }.getIns((int)360, (int)0, Color.TRANSPARENT, 0xFFF0F3F8));
		bottomAudioRecorderSend.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b, int c, int d) { this.setCornerRadius(a); this.setStroke(b, c); this.setColor(d); return this; } }.getIns((int)360, (int)0, Color.TRANSPARENT, 0xFFF0F3F8));
		message_input_outlined_round.setBackgroundResource(R.drawable.input_bg_collapsed);
		unblock_btn.setVisibility(View.GONE);
		img_container_layout.setVisibility(View.GONE);
		_collapse(); // Start with attachment options collapsed

		_stateColor(0xFFFFFFFF, 0xFFFFFFFF);
		_ScrollingText(topProfileLayoutUsername);
		bannedUserInfo.setVisibility(View.GONE);
		mMessageReplyLayout.setVisibility(View.GONE);
		bottomAudioRecorder.setVisibility(View.GONE);
		_ImgRound(topProfileLayoutProfileImage, 300);

		LinearLayoutManager layoutManager = new LinearLayoutManager(this);
		layoutManager.setStackFromEnd(true);
		ChatMessagesListRecycler.setLayoutManager(layoutManager);
		ChatMessagesListRecycler.setAdapter(new ChatMessagesListRecyclerAdapter(ChatMessagesList, chatTextSize, chatCornerRadius));

		ChatMessagesListRecycler.addOnScrollListener(new RecyclerView.OnScrollListener() {
			@Override
			public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
				super.onScrolled(recyclerView, dx, dy);
				if (!isLoadingOldMessages && layoutManager.findFirstVisibleItemPosition() < 2 && !oldestMessageKey.isEmpty()) {
					isLoadingOldMessages = true;
					_getOldChatMessagesRef();
				}
			}
		});
	}

	private void _cacheAppSettings() {
		try {
			chatTextSize = Integer.parseInt(appSettings.getString("ChatTextSize", "16"));
			chatCornerRadius = Integer.parseInt(appSettings.getString("ChatCornerRadius", "27"));
		} catch (NumberFormatException e) {
			chatTextSize = 16;
			chatCornerRadius = 27;
		}
		_detect_theme();
	}

	//===== CORE FEATURE METHODS =======
	private void _send_btn() {
		if (!file_path.isEmpty()) {
			_uploadImageAndSend();
			return;
		}

		String messageText = message_et.getText().toString().trim();
		if (messageText.isEmpty()) {
			return; // Do nothing if text is empty
		}

		message_et.setText("");

		if (!ReplyMessageID.equals("null")) {
			final String repliedMessageId = ReplyMessageID;
			ReplyMessageID = "null"; // Reset immediately
			mMessageReplyLayout.setVisibility(View.GONE);

			DatabaseReference repliedMsgRef = chat_ref.child(repliedMessageId);
			repliedMsgRef.addListenerForSingleValueEvent(new ValueEventListener() {
				@Override
				public void onDataChange(@NonNull DataSnapshot snapshot) {
					String repliedText = "Message not found";
					String repliedUsername = "";
					if (snapshot.exists()) {
						repliedText = snapshot.child("message_text").getValue(String.class);
						if (snapshot.child("uid").getValue(String.class).equals(auth.getCurrentUser().getUid())) {
							repliedUsername = FirstUserName;
						} else {
							repliedUsername = SecondUserName;
						}
					}
					_sendMessage(messageText, null, repliedMessageId, repliedText, repliedUsername);
				}
				@Override
				public void onCancelled(@NonNull DatabaseError error) {
					_sendMessage(messageText, null, null, null, null);
				}
			});
		} else {
			_sendMessage(messageText, null, null, null, null);
		}
	}

	private void _uploadImageAndSend() {
		_LoadingDialog(true);
		Uri fileUri = Uri.fromFile(new File(file_path));
		final StorageReference fileRef = upload_selected_img_ref.child(System.currentTimeMillis() + "_" + file_name);

		fileRef.putFile(fileUri).addOnProgressListener(taskSnapshot -> {
			double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
			img_upload_prog.setProgress((int) progress);
		}).continueWithTask((Continuation<UploadTask.TaskSnapshot, Task<Uri>>) task -> {
			if (!task.isSuccessful()) {
				throw task.getException();
			}
			return fileRef.getDownloadUrl();
		}).addOnCompleteListener(task -> {
			_LoadingDialog(false);
			if (task.isSuccessful()) {
				Uri downloadUri = task.getResult();
				_sendMessage(message_et.getText().toString().trim(), downloadUri.toString(), null, null, null);
				img_container_layout.setVisibility(View.GONE);
				file_path = "";
				file_name = "";
				message_et.setText("");
			} else {
				SketchwareUtil.showMessage(getApplicationContext(), "Upload failed: " + task.getException().getMessage());
			}
		});
	}

	private void _sendMessage(String text, String imageUrl, String repliedId, String repliedText, String repliedUsername) {
		String uniqueKey = chat_ref.push().getKey();

		HashMap<String, Object> messageMap = new HashMap<>();
		messageMap.put("uid", auth.getCurrentUser().getUid());
		messageMap.put("TYPE", "MESSAGE");
		messageMap.put("message_text", text);
		if (imageUrl != null) {
			messageMap.put("message_image_uri", imageUrl);
		}
		messageMap.put("message_state", "sended");
		messageMap.put("key", uniqueKey);
		messageMap.put("push_date", String.valueOf(Calendar.getInstance().getTimeInMillis()));

		if (repliedId != null) {
			messageMap.put("replied_message_id", repliedId);
			messageMap.put("replied_message_text", repliedText);
			messageMap.put("replied_message_username", repliedUsername);
		}

		DatabaseReference otherUserChatRef = _firebase.getReference("skyline/chats").child(getIntent().getStringExtra("uid")).child(auth.getCurrentUser().getUid());
		chat_ref.child(uniqueKey).setValue(messageMap);
		otherUserChatRef.child(uniqueKey).setValue(messageMap);

		_updateInbox(text, imageUrl);
	}

	private void _updateInbox(String lastMessage, String imageUrl) {
		long timestamp = Calendar.getInstance().getTimeInMillis();
		String myUid = auth.getCurrentUser().getUid();
		String otherUid = getIntent().getStringExtra("uid");

		String displayMessage = !lastMessage.isEmpty() ? lastMessage : (imageUrl != null ? "Photo" : "Message");

		HashMap<String, Object> myInboxMap = new HashMap<>();
		myInboxMap.put("uid", otherUid);
		myInboxMap.put("TYPE", "MESSAGE");
		myInboxMap.put("last_message_uid", myUid);
		myInboxMap.put("last_message_text", displayMessage);
		myInboxMap.put("last_message_state", "sended");
		myInboxMap.put("push_date", String.valueOf(timestamp));
		_firebase.getReference("skyline/inbox").child(myUid).child(otherUid).updateChildren(myInboxMap);

		HashMap<String, Object> otherInboxMap = new HashMap<>();
		otherInboxMap.put("uid", myUid);
		otherInboxMap.put("TYPE", "MESSAGE");
		otherInboxMap.put("last_message_uid", myUid);
		otherInboxMap.put("last_message_text", displayMessage);
		otherInboxMap.put("last_message_state", "sended");
		otherInboxMap.put("push_date", String.valueOf(timestamp));
		_firebase.getReference("skyline/inbox").child(otherUid).child(myUid).updateChildren(otherInboxMap);
	}

	private void _getUserReference() {
		DatabaseReference userRef = _firebase.getReference("skyline/users").child(getIntent().getStringExtra("uid"));
		userRef.addListenerForSingleValueEvent(new ValueEventListener() {
			@Override
			public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
				if (dataSnapshot.exists()) {
					if ("true".equals(dataSnapshot.child("banned").getValue(String.class))) {
						bannedUserInfo.setVisibility(View.VISIBLE);
						topProfileLayoutProfileImage.setImageResource(R.drawable.banned_avatar);
						SecondUserAvatar = "null_banned";
						topProfileLayoutStatus.setTextColor(0xFF9E9E9E);
						topProfileLayoutStatus.setText(R.string.offline);
					} else {
						bannedUserInfo.setVisibility(View.GONE);
						String avatarUrl = dataSnapshot.child("avatar").getValue(String.class);
						if (avatarUrl == null || "null".equals(avatarUrl)) {
							topProfileLayoutProfileImage.setImageResource(R.drawable.avatar);
							SecondUserAvatar = "null";
						} else {
							SecondUserAvatar = avatarUrl;
							if (!isFinishing()) Glide.with(getApplicationContext()).load(Uri.parse(SecondUserAvatar)).into(topProfileLayoutProfileImage);
						}
					}
					// ... Rest of your user info loading logic
				}
			}
			@Override public void onCancelled(@NonNull DatabaseError databaseError) {}
		});

		DatabaseReference myUserRef = _firebase.getReference("skyline/users").child(auth.getCurrentUser().getUid());
		myUserRef.addListenerForSingleValueEvent(new ValueEventListener() {
			@Override
			public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
				if (dataSnapshot.exists()) {
					String nickname = dataSnapshot.child("nickname").getValue(String.class);
					if (nickname == null || "null".equals(nickname)) {
						FirstUserName = "@" + dataSnapshot.child("username").getValue(String.class);
					} else {
						FirstUserName = nickname;
					}
				}
			}
			@Override public void onCancelled(@NonNull DatabaseError databaseError) {}
		});
	}

	private void _getOldChatMessagesRef() {
		Query oldMessagesQuery = chat_ref.orderByKey().endBefore(oldestMessageKey).limitToLast(50);
		oldMessagesQuery.addListenerForSingleValueEvent(new ValueEventListener() {
			@Override
			public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
				if (dataSnapshot.exists()) {
					ArrayList<HashMap<String, Object>> oldMessages = new ArrayList<>();
					for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
						GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
						HashMap<String, Object> map = snapshot.getValue(_ind);
						map.put("key", snapshot.getKey());
						oldMessages.add(map);
					}
					if (!oldMessages.isEmpty()) {
						oldestMessageKey = oldMessages.get(0).get("key").toString();
						ChatMessagesList.addAll(0, oldMessages);
						ChatMessagesListRecycler.getAdapter().notifyItemRangeInserted(0, oldMessages.size());
					}
				}
				isLoadingOldMessages = false;
			}
			@Override public void onCancelled(@NonNull DatabaseError databaseError) {
				isLoadingOldMessages = false;
			}
		});
	}

	//===== POPUPS & DIALOGS =======
	private void _messageOverviewPopup(final View anchorView, final int _position, final ArrayList<HashMap<String, Object>> _data) {
		View pop1V = getLayoutInflater().inflate(R.layout.chat_msg_options_popup_cv_synapse, null);
		final PopupWindow pop1 = new PopupWindow(pop1V, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);

		final LinearLayout edit = pop1V.findViewById(R.id.edit);
		final LinearLayout reply = pop1V.findViewById(R.id.reply);
		final LinearLayout copy = pop1V.findViewById(R.id.copy);
		final LinearLayout delete = pop1V.findViewById(R.id.delete);

		if (_data.get(_position).get("uid").toString().equals(auth.getCurrentUser().getUid())) {
			edit.setVisibility(View.VISIBLE);
			delete.setVisibility(View.VISIBLE);
		} else {
			edit.setVisibility(View.GONE);
			delete.setVisibility(View.GONE);
		}

		_setupReactionListener(pop1V.findViewById(R.id.thumb_reaction_btn), "👍", pop1, _data.get(_position));
		_setupReactionListener(pop1V.findViewById(R.id.Love_reaction_btn), "❤️", pop1, _data.get(_position));
		// Add other reaction listeners here...

		reply.setOnClickListener(v -> {
			ReplyMessageID = _data.get(_position).get("key").toString();
			if (_data.get(_position).get("uid").toString().equals(auth.getCurrentUser().getUid())) {
				mMessageReplyLayoutBodyRightUsername.setText(FirstUserName);
			} else {
				mMessageReplyLayoutBodyRightUsername.setText(SecondUserName);
			}
			mMessageReplyLayoutBodyRightMessage.setText(_data.get(_position).get("message_text").toString());
			mMessageReplyLayout.setVisibility(View.VISIBLE);
			vbr.vibrate(48);
			pop1.dismiss();
		});

		copy.setOnClickListener(v -> {
			ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
			ClipData clip = ClipData.newPlainText("message", _data.get(_position).get("message_text").toString());
			clipboard.setPrimaryClip(clip);
			Toast.makeText(this, "Message copied", Toast.LENGTH_SHORT).show();
			vbr.vibrate(48);
			pop1.dismiss();
		});

		delete.setOnClickListener(v -> {
			_DeleteMessageDialog(_data.get(_position));
			pop1.dismiss();
		});

		pop1.setElevation(8f);
		pop1.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
		pop1.showAsDropDown(anchorView, 0, -anchorView.getHeight());
	}

	private void _setupReactionListener(View button, final String emoji, final PopupWindow popup, final HashMap<String, Object> messageData) {
		button.setOnClickListener(v -> {
			vbr.vibrate(48);
			String myUid = auth.getCurrentUser().getUid();
			String messageKey = messageData.get("key").toString();

			DatabaseReference reactionRef = chat_ref.child(messageKey).child("reactions").child(myUid);
			reactionRef.addListenerForSingleValueEvent(new ValueEventListener() {
				@Override
				public void onDataChange(@NonNull DataSnapshot snapshot) {
					DatabaseReference otherUserReactionRef = _firebase.getReference("skyline/chats").child(getIntent().getStringExtra("uid")).child(myUid).child(messageKey).child("reactions").child(myUid);
					if (snapshot.exists() && emoji.equals(snapshot.getValue(String.class))) {
						reactionRef.removeValue();
						otherUserReactionRef.removeValue();
					} else {
						reactionRef.setValue(emoji);
						otherUserReactionRef.setValue(emoji);
					}
					popup.dismiss();
				}
				@Override public void onCancelled(@NonNull DatabaseError error) { popup.dismiss(); }
			});
		});
	}

	private void _DeleteMessageDialog(final HashMap<String, Object> messageData) {
		new MaterialAlertDialogBuilder(this)
				.setTitle("Delete Message")
				.setMessage("Are you sure you want to permanently delete this message?")
				.setPositiveButton("Delete", (dialog, which) -> {
					String messageKey = messageData.get("key").toString();
					chat_ref.child(messageKey).removeValue();
					_firebase.getReference("skyline/chats").child(getIntent().getStringExtra("uid")).child(auth.getCurrentUser().getUid()).child(messageKey).removeValue();

					if (messageData.containsKey("message_image_uri")) {
						_LoadingDialog(true);
						_firebase_storage.getReferenceFromUrl(messageData.get("message_image_uri").toString()).delete()
								.addOnCompleteListener(task -> _LoadingDialog(false));
					}
				})
				.setNegativeButton("Cancel", null)
				.show();
	}


	//===== UTILITY & HELPER METHODS =======
	private void _expand() {
		file_type_expanded = true;
		devider.setVisibility(View.VISIBLE);
		devider1.setVisibility(View.VISIBLE);
		devider2.setVisibility(View.VISIBLE);
		attachment_btn.setVisibility(View.VISIBLE);
		send_type_voice_btn.setVisibility(View.VISIBLE);
		more_send_type_btn.setVisibility(View.VISIBLE);
		_ImageColor(expand_send_type_btn, 0xFF2962FF);
		camera_gallery_btn_container_round.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b, int c, int d) { this.setCornerRadius(a); this.setStroke(b, c); this.setColor(d); return this; } }.getIns((int)60, (int)0, Color.TRANSPARENT, 0xFFF0F3F8));
		_TransitionManager(camera_gallery_btn_container_round, 200);
	}

	private void _collapse() {
		file_type_expanded = false;
		devider.setVisibility(View.GONE);
		devider1.setVisibility(View.GONE);
		devider2.setVisibility(View.GONE);
		attachment_btn.setVisibility(View.GONE);
		send_type_voice_btn.setVisibility(View.GONE);
		more_send_type_btn.setVisibility(View.GONE);
		_ImageColor(expand_send_type_btn, 0xFF454644);
		camera_gallery_btn_container_round.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b, int c, int d) { this.setCornerRadius(a); this.setStroke(b, c); this.setColor(d); return this; } }.getIns((int)360, (int)0, Color.TRANSPARENT, 0xFFF0F3F8));
		_TransitionManager(camera_gallery_btn_container_round, 200);
	}

	private void _unblock_this_user() {
		blocklist_ref.child(auth.getCurrentUser().getUid()).child(getIntent().getStringExtra("uid")).removeValue();
	}

	private void _AudioRecorderStart() {
		recordMs = 0;
		bottomAudioRecorder.setVisibility(View.VISIBLE);
		// ... your audio recording implementation
	}

	private void _AudioRecorderStop() {
		bottomAudioRecorder.setVisibility(View.GONE);
		if (recordTimer != null) recordTimer.cancel();
		// ... your audio stop implementation
	}

	//===== ADAPTER CLASS =======
	public class ChatMessagesListRecyclerAdapter extends RecyclerView.Adapter<ChatMessagesListRecyclerAdapter.ViewHolder> {
		private ArrayList<HashMap<String, Object>> _data;
		private int _textSize;
		private int _cornerRadius;
		private SimpleDateFormat timeFormat;

		public ChatMessagesListRecyclerAdapter(ArrayList<HashMap<String, Object>> _arr, int textSize, int cornerRadius) {
			_data = _arr;
			_textSize = textSize;
			_cornerRadius = cornerRadius;
			timeFormat = new SimpleDateFormat("hh:mm a", Locale.getDefault());
		}

		@NonNull
		@Override
		public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
			View _v = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_msg_cv_synapse, parent, false);
			return new ViewHolder(_v);
		}

		@Override
		public void onBindViewHolder(@NonNull ViewHolder _holder, final int _position) {
			final HashMap<String, Object> messageData = _data.get(_position);

			boolean isMyMessage = messageData.get("uid").toString().equals(auth.getCurrentUser().getUid());

			_holder.message_text.setTextSize(_textSize);
			_holder.mRepliedMessageLayoutMessage.setTextSize(_textSize);

			_holder.message_text.setText(messageData.get("message_text").toString());
			Calendar push = Calendar.getInstance();
			push.setTimeInMillis(Long.parseLong(messageData.get("push_date").toString()));
			_holder.date.setText(timeFormat.format(push.getTime()));

			if (isMyMessage) {
				_holder.body.setGravity(Gravity.END);
				_holder.message_layout.setGravity(Gravity.END);
				_holder.message_text.setTextColor(0xFFFFFFFF);
				_holder.message_state.setVisibility(View.VISIBLE);
				_holder.mProfileCard.setVisibility(View.GONE);

				GradientDrawable myMessageBg = new GradientDrawable();
				myMessageBg.setColor(0xFF6B4CFF);
				float r = getDip(_cornerRadius);
				myMessageBg.setCornerRadii(new float[]{r, r, 0, 0, r, r, r, r});
				_holder.messageBG.setBackground(myMessageBg);

				if ("seen".equals(messageData.get("message_state").toString())) {
					_holder.message_state.setImageResource(R.drawable.icon_done_all_round);
					_ImageColor(_holder.message_state, ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary));
				} else {
					_holder.message_state.setImageResource(R.drawable.icon_done_round);
					_ImageColor(_holder.message_state, 0xFF424242);
				}
			} else {
				_holder.body.setGravity(Gravity.START);
				_holder.message_layout.setGravity(Gravity.START);
				_holder.message_text.setTextColor(0xFF000000);
				_holder.message_state.setVisibility(View.GONE);
				_holder.mProfileCard.setVisibility(View.VISIBLE);

				GradientDrawable otherMessageBg = new GradientDrawable();
				otherMessageBg.setColor(0xFFFFFFFF);
				float r = getDip(_cornerRadius);
				otherMessageBg.setCornerRadii(new float[]{0, 0, r, r, r, r, r, r});
				otherMessageBg.setStroke((int)getDip(1), 0xFFDFDFDF);
				_holder.messageBG.setBackground(otherMessageBg);

				if (!isFinishing()) {
					if ("null".equals(SecondUserAvatar)) {
						_holder.mProfileImage.setImageResource(R.drawable.avatar);
					} else {
						Glide.with(getApplicationContext()).load(Uri.parse(SecondUserAvatar)).into(_holder.mProfileImage);
					}
				}

				if ("sended".equals(messageData.get("message_state").toString())) {
					chat_ref.child(messageData.get("key").toString()).child("message_state").setValue("seen");
					_firebase.getReference("skyline/chats").child(getIntent().getStringExtra("uid")).child(auth.getCurrentUser().getUid()).child(messageData.get("key").toString()).child("message_state").setValue("seen");
				}
			}

			if (messageData.containsKey("replied_message_id")) {
				_holder.mRepliedMessageLayout.setVisibility(View.VISIBLE);
				_holder.mRepliedMessageLayoutUsername.setText(messageData.get("replied_message_username").toString());
				_holder.mRepliedMessageLayoutMessage.setText(messageData.get("replied_message_text").toString());
			} else {
				_holder.mRepliedMessageLayout.setVisibility(View.GONE);
			}

			if (messageData.containsKey("message_image_uri")) {
				_holder.mMessageImageBody.setVisibility(View.VISIBLE);
				if (!isFinishing()) Glide.with(getApplicationContext()).load(Uri.parse(messageData.get("message_image_uri").toString())).into(_holder.mMessageImageView);
			} else {
				_holder.mMessageImageBody.setVisibility(View.GONE);
			}

			_holder.messageBG.setOnLongClickListener(v -> {
				_messageOverviewPopup(v, _position, _data);
				return true;
			});
		}

		@Override
		public int getItemCount() {
			return _data.size();
		}

		public class ViewHolder extends RecyclerView.ViewHolder {
			LinearLayout body, message_layout, messageBG, mRepliedMessageLayout;
			TextView message_text, date, mRepliedMessageLayoutUsername, mRepliedMessageLayoutMessage;
			ImageView message_state, mProfileImage, mMessageImageView;
			CardView mProfileCard, mMessageImageBody;

			public ViewHolder(View v) {
				super(v);
				body = v.findViewById(R.id.body);
				message_layout = v.findViewById(R.id.message_layout);
				messageBG = v.findViewById(R.id.messageBG);
				mRepliedMessageLayout = v.findViewById(R.id.mRepliedMessageLayout);
				message_text = v.findViewById(R.id.message_text);
				date = v.findViewById(R.id.date);
				mRepliedMessageLayoutUsername = v.findViewById(R.id.mRepliedMessageLayoutUsername);
				mRepliedMessageLayoutMessage = v.findViewById(R.id.mRepliedMessageLayoutMessage);
				message_state = v.findViewById(R.id.message_state);
				mProfileImage = v.findViewById(R.id.mProfileImage);
				mMessageImageView = v.findViewById(R.id.mMessageImageView);
				mProfileCard = v.findViewById(R.id.mProfileCard);
				mMessageImageBody = v.findViewById(R.id.mMessageImageBody);
			}
		}
	}
	
	//===== DUMMY/HELPER CLASSES AND DEPRECATED METHODS FOR COMPATIBILITY =======
	// Kept as requested to ensure code compiles without external dependencies.
	private void _stateColor(int statusBarColor, int navBarColor) { getWindow().setStatusBarColor(statusBarColor); getWindow().setNavigationBarColor(navBarColor); }
	public static class SketchwareUtil { public static void showMessage(Context c, String s) { Toast.makeText(c, s, Toast.LENGTH_SHORT).show(); } }
	public static class FileUtil {
		public static String convertUriToFilePath(Context context, Uri uri) {
			String path = null;
			try (Cursor cursor = context.getContentResolver().query(uri, null, null, null, null)) {
				if (cursor != null && cursor.moveToFirst()) {
			//		int-disable-next-line
					path = cursor.getString(cursor.getColumnIndexOrThrow(android.provider.MediaStore.Images.Media.DATA));
				}
			} catch (Exception e) {
				// Fallback for newer APIs
				try (InputStream inputStream = context.getContentResolver().openInputStream(uri)) {
					File file = new File(context.getCacheDir(), "temp_file");
					try (OutputStream outputStream = new FileOutputStream(file)) {
						byte[] buffer = new byte[4 * 1024];
						int read;
						while ((read = inputStream.read(buffer)) != -1) {
							outputStream.write(buffer, 0, read);
						}
						outputStream.flush();
					}
					path = file.getAbsolutePath();
				} catch (Exception ignored) {}
			}
			return path;
		}
		public static Bitmap decodeSampleBitmapFromPath(String p, int w, int h) { return null; }
	}
	public void _TransitionManager(final View _view, final double _duration) { if (_view instanceof ViewGroup) { TransitionManager.beginDelayedTransition((ViewGroup) _view, new AutoTransition().setDuration((long)_duration)); } }
	public void _ImageColor(final ImageView _image, final int _color) { _image.setColorFilter(_color, PorterDuff.Mode.SRC_ATOP); }
	public void _ScrollingText(final TextView _view) { _view.setSingleLine(true); _view.setEllipsize(TextUtils.TruncateAt.MARQUEE); _view.setSelected(true); }
	public void _ImgRound(final ImageView _imageview, final double _value) { _imageview.setClipToOutline(true); } // Requires API 21+
	public void _LoadingDialog(final boolean _visibility) {
		if (_visibility) {
			if (SynapseLoadingDialog == null) {
				SynapseLoadingDialog = new ProgressDialog(this);
				SynapseLoadingDialog.setMessage("Loading...");
				SynapseLoadingDialog.setCancelable(false);
			}
			if (!SynapseLoadingDialog.isShowing()) SynapseLoadingDialog.show();
		} else {
			if (SynapseLoadingDialog != null && SynapseLoadingDialog.isShowing()) {
				SynapseLoadingDialog.dismiss();
			}
		}
	}
	public void _detect_theme(){} // Dummy for theme logic
	
	@Deprecated
	public float getDip(int _input) { return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, _input, getResources().getDisplayMetrics()); }
}