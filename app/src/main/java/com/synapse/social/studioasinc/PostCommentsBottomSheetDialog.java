package com.synapse.social.studioasinc;

import android.animation.*;
import android.animation.ObjectAnimator;
import android.view.animation.*;
import android.view.animation.LinearInterpolator;
import android.view.inputmethod.InputMethodManager;
import androidx.activity.OnBackPressedCallback;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.*;
import android.graphics.drawable.*;
import android.view.Display;
import android.view.WindowManager;
import android.widget.*;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.text.*;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.style.*;
import android.widget.PopupWindow;
import android.view.Gravity;
import android.os.Bundle;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.content.res.ColorStateList;
import android.os.Handler;
import android.os.Looper;
import androidx.recyclerview.widget.*;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.Adapter;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.cardview.widget.CardView;
import com.bumptech.glide.Glide;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
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
import com.google.firebase.database.Query;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.io.*;
import java.text.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Calendar;
import java.util.regex.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PostCommentsBottomSheetDialog extends DialogFragment {
		
		private View rootView;
		private Intent intent = new Intent();
		
		private LinearLayout body;
		private LinearLayout top;
		private LinearLayout body_in_layout;
		private LinearLayout spc1;
		private LinearLayout commentVbody;
		private LinearLayout close;
		private LinearLayout top_middle;
		private LinearLayout gift;
		private ImageView close_ic;
		private TextView title;
		private TextView title_count;
		private ImageView gift_ic;
		private RecyclerView comments_list;
		private LinearLayout no_comments_body;
		private LinearLayout loading_body;
		private TextView no_comments_title;
		private TextView no_comments_subtext;
		private ProgressBar loading_bar;
		private LinearLayout commentsMoreFet;
		private LinearLayout CommentsBottomIn;
		private TextView emoji1;
		private TextView emoji2;
		private TextView emoji3;
		private TextView emoji4;
		private TextView emoji5;
		private TextView emoji6;
		private TextView emoji7;
		private CardView profile_image_bg_2_x2;
		private LinearLayout comment_send_layout;
		private ImageView profile_image_x;
		private EditText comment_send_input;
		private ImageView cancel_reply_mode;
		private ImageView comment_send_button;
		
		private FirebaseAuth auth;
		private DatabaseReference main = FirebaseDatabase.getInstance().getReference("skyline");
		private Calendar cc = Calendar.getInstance();
		
		private String postKey = null;
		private String pushKey = null;
		private String postPublisherUID = null;
		private String postPublisherAvatar = null;
		private String replyToCommentKey = null;
		private boolean replyToComment = false;
		private int commentsLimit = 0;
		private ArrayList<HashMap<String, Object>> commentsListMap = new ArrayList<>();
		private Handler handler;
		
		private HashMap<String, Object> UserInfoCacheMap = new HashMap<>();
		private HashMap<String, Object> postCommentLikeCountCache = new HashMap<>();
		private HashMap<String, Object> sendCommentMap = new HashMap<>();
		
		@NonNull
		@Override
		public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
				BottomSheetDialog dialog = new BottomSheetDialog(requireContext(), R.style.PostCommentsBottomSheetDialogStyle);
				rootView = View.inflate(getContext(), R.layout.synapse_comments_cbsd, null);
				dialog.setContentView(rootView);
				dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
				handler = new Handler(Looper.getMainLooper());
				
				body = rootView.findViewById(R.id.body);
				top = rootView.findViewById(R.id.top);
				body_in_layout = rootView.findViewById(R.id.body_in_layout);
				spc1 = rootView.findViewById(R.id.spc1);
				commentVbody = rootView.findViewById(R.id.commentVbody);
				close = rootView.findViewById(R.id.close);
				top_middle = rootView.findViewById(R.id.top_middle);
				gift = rootView.findViewById(R.id.gift);
				close_ic = rootView.findViewById(R.id.close_ic);
				title = rootView.findViewById(R.id.title);
				title_count = rootView.findViewById(R.id.title_count);
				gift_ic = rootView.findViewById(R.id.gift_ic);
				comments_list = rootView.findViewById(R.id.comments_list);
				no_comments_body = rootView.findViewById(R.id.no_comments_body);
				loading_body = rootView.findViewById(R.id.loading_body);
				no_comments_title = rootView.findViewById(R.id.no_comments_title);
				no_comments_subtext = rootView.findViewById(R.id.no_comments_subtext);
				loading_bar = rootView.findViewById(R.id.loading_bar);
				commentsMoreFet = rootView.findViewById(R.id.commentsMoreFet);
				CommentsBottomIn = rootView.findViewById(R.id.CommentsBottomIn);
				emoji1 = rootView.findViewById(R.id.emoji1);
				emoji2 = rootView.findViewById(R.id.emoji2);
				emoji3 = rootView.findViewById(R.id.emoji3);
				emoji4 = rootView.findViewById(R.id.emoji4);
				emoji5 = rootView.findViewById(R.id.emoji5);
				emoji6 = rootView.findViewById(R.id.emoji6);
				emoji7 = rootView.findViewById(R.id.emoji7);
				profile_image_bg_2_x2 = rootView.findViewById(R.id.profile_image_bg_2_x2);
				comment_send_layout = rootView.findViewById(R.id.comment_send_layout);
				profile_image_x = rootView.findViewById(R.id.profile_image_x);
				comment_send_input = rootView.findViewById(R.id.comment_send_input);
				cancel_reply_mode = rootView.findViewById(R.id.cancel_reply_mode);
				comment_send_button = rootView.findViewById(R.id.comment_send_button);
				
				FirebaseApp.initializeApp(getContext());
				auth = FirebaseAuth.getInstance();
				
				Display display = getActivity().getWindowManager().getDefaultDisplay();
				int screenHeight = display.getHeight();
				int desiredHeight = screenHeight * 3 / 4;
				LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, desiredHeight);
				
				comments_list.setAdapter(new Comments_listAdapter(commentsListMap));
				comments_list.setLayoutManager(new LinearLayoutManager(getActivity()));
				
				dialog.setOnShowListener(dialogInterface -> {
						BottomSheetDialog d = (BottomSheetDialog) dialogInterface;
						View bottomSheet = d.findViewById(com.google.android.material.R.id.design_bottom_sheet);
						BottomSheetBehavior.from(bottomSheet).setPeekHeight(desiredHeight);
						BottomSheetBehavior.from(bottomSheet).setHideable(true);
						BottomSheetBehavior.from(bottomSheet).setDraggable(true);
						BottomSheetBehavior.from(bottomSheet).setState(BottomSheetBehavior.STATE_EXPANDED);
				});
				
				if (getArguments() != null) {
						postKey = getArguments().getString("postKey");
						postPublisherUID = getArguments().getString("postPublisherUID");
						postPublisherAvatar = getArguments().getString("postPublisherAvatar");
						comments_list.setVisibility(View.GONE);
						no_comments_body.setVisibility(View.GONE);
						loading_body.setVisibility(View.VISIBLE);
						getCommentsCount(postKey);
						getCommentsRef(postKey, true);
				}
				
				close.setOnClickListener(new View.OnClickListener() {
						@Override
						public void onClick(View _view) {
								dialog.dismiss();
						}
				});
				
				emoji1.setOnClickListener(new View.OnClickListener() {
						@Override
						public void onClick(View _view) {
								comment_send_input.setText(comment_send_input.getText().toString().concat("😁"));
						}
				});
				
				emoji2.setOnClickListener(new View.OnClickListener() {
						@Override
						public void onClick(View _view) {
								comment_send_input.setText(comment_send_input.getText().toString().concat("🥰"));
						}
				});
				
				emoji3.setOnClickListener(new View.OnClickListener() {
						@Override
						public void onClick(View _view) {
								comment_send_input.setText(comment_send_input.getText().toString().concat("😂"));
						}
				});
				
				emoji4.setOnClickListener(new View.OnClickListener() {
						@Override
						public void onClick(View _view) {
								comment_send_input.setText(comment_send_input.getText().toString().concat("😳"));
						}
				});
				
				emoji5.setOnClickListener(new View.OnClickListener() {
						@Override
						public void onClick(View _view) {
								comment_send_input.setText(comment_send_input.getText().toString().concat("😏"));
						}
				});
				
				emoji6.setOnClickListener(new View.OnClickListener() {
						@Override
						public void onClick(View _view) {
								comment_send_input.setText(comment_send_input.getText().toString().concat("😅"));
						}
				});
				
				emoji7.setOnClickListener(new View.OnClickListener() {
						@Override
						public void onClick(View _view) {
								comment_send_input.setText(comment_send_input.getText().toString().concat("🥺"));
						}
				});
				
				cancel_reply_mode.setOnClickListener(new View.OnClickListener() {
						@Override
						public void onClick(View _view) {
								replyToComment = false;
								replyToCommentKey = null;
								comment_send_input.setHint(getResources().getString(R.string.comment));
								cancel_reply_mode.setVisibility(View.GONE);
						}
				});
				
				comment_send_input.addTextChangedListener(new TextWatcher() {
						@Override
						public void onTextChanged(CharSequence _param1, int _param2, int _param3, int _param4) {
								final String _charSeq = _param1.toString();
								if (!_charSeq.trim().equals("")) {
										comment_send_button.setVisibility(View.VISIBLE);
								}
								else {
										comment_send_button.setVisibility(View.GONE);
								}
						}
						
						@Override
						public void beforeTextChanged(CharSequence _param1, int _param2, int _param3, int _param4) {
								
						}
						
						@Override
						public void afterTextChanged(Editable _param1) {
								
						}
				});
				
				comment_send_button.setOnClickListener(new View.OnClickListener() {
						@Override
						public void onClick(View _view) {
								if (!comment_send_input.getText().toString().trim().equals("")) {
										if ((replyToComment) && (replyToCommentKey != null)) {
												cc = Calendar.getInstance();
												sendCommentMap = new HashMap<>();
												pushKey = main.push().getKey();
												sendCommentMap.put("uid", FirebaseAuth.getInstance().getCurrentUser().getUid());
												sendCommentMap.put("comment", comment_send_input.getText().toString());
												sendCommentMap.put("push_time", String.valueOf((long)(cc.getTimeInMillis())));
												sendCommentMap.put("key", pushKey);
												sendCommentMap.put("replyCommentkey", replyToCommentKey);
												sendCommentMap.put("like", "0");
												main.child("posts-comments-replies").child(postKey).child(replyToCommentKey).child(pushKey).updateChildren(sendCommentMap);
												comment_send_input.setText("");
												getCommentsRef(postKey, false);
										} else {
												cc = Calendar.getInstance();
												sendCommentMap = new HashMap<>();
												pushKey = main.push().getKey();
												sendCommentMap.put("uid", FirebaseAuth.getInstance().getCurrentUser().getUid());
												sendCommentMap.put("comment", comment_send_input.getText().toString());
												sendCommentMap.put("push_time", String.valueOf((long)(cc.getTimeInMillis())));
												sendCommentMap.put("key", pushKey);
												sendCommentMap.put("like", "0");
												main.child("posts-comments").child(postKey).child(pushKey).updateChildren(sendCommentMap);
												comment_send_input.setText("");
												getCommentsRef(postKey, false);
										}
										comment_send_input.setHint(getResources().getString(R.string.comment));
										replyToCommentKey = null;
										replyToComment = false;
										cancel_reply_mode.setVisibility(View.GONE);
								}
						}
				});
				
				getMyUserData(FirebaseAuth.getInstance().getCurrentUser().getUid());
				body.setLayoutParams(params);
				dialogStyles();
				
				return dialog;
		}
		
		public void getCommentsRef(String key, boolean increaseLimit) {
				if (increaseLimit) {
						commentsLimit = commentsLimit + 20;
				} else {
						getCommentsCount(key);
				}
				{
						ExecutorService mExecutorService = Executors.newSingleThreadExecutor();
						Handler mMainHandler = new Handler(Looper.getMainLooper());
						
						mExecutorService.execute(new Runnable() {
								@Override
								public void run() {
										Query commentsQuery = FirebaseDatabase.getInstance().getReference("skyline/posts-comments").child(key).orderByChild("like").limitToLast(commentsLimit);
										commentsQuery.addListenerForSingleValueEvent(new ValueEventListener() {
												@Override
												public void onDataChange(@NonNull DataSnapshot snapshot) {
														mMainHandler.post(new Runnable() {
																@Override
																public void run() {
																		if (snapshot.exists()) {
																				comments_list.setVisibility(View.VISIBLE);
																				no_comments_body.setVisibility(View.GONE);
																				loading_body.setVisibility(View.GONE);
																				commentsListMap.clear();
																				
																				GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
																				
																				for (DataSnapshot _data : snapshot.getChildren()) {
																						HashMap<String, Object> commentsGetMap = _data.getValue(_ind);
																						commentsListMap.add(commentsGetMap);
																						SketchwareUtil.sortListMap(commentsListMap, "like", true, false);
																				}
																				
																				comments_list.getAdapter().notifyDataSetChanged();
																		} else {
																				comments_list.setVisibility(View.GONE);
																				no_comments_body.setVisibility(View.VISIBLE);
																				loading_body.setVisibility(View.GONE);
																		}
																}
														});
												}
												
												@Override
												public void onCancelled(@NonNull DatabaseError error) {
														
												}
										});
								}
						});
				}
		}
		
		public void getMyUserData(String uid) {
				DatabaseReference getUserDetails = FirebaseDatabase.getInstance().getReference("skyline/users").child(uid);
				getUserDetails.addListenerForSingleValueEvent(new ValueEventListener() {
						@Override
						public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
								if(dataSnapshot.exists()) {
										if (!dataSnapshot.child("avatar").getValue(String.class).equals("null")) {
												Glide.with(getContext()).load(Uri.parse(dataSnapshot.child("avatar").getValue(String.class))).into(profile_image_x);
										} else {
												profile_image_x.setImageResource(R.drawable.avatar);
										}
								} else {
										profile_image_x.setImageResource(R.drawable.avatar);
								}
						}
						@Override
						public void onCancelled(@NonNull DatabaseError databaseError) {
								
						}
				});
		}
		
		public void getCommentsCount(String key) {
				{
						ExecutorService mExecutorService = Executors.newSingleThreadExecutor();
						Handler mMainHandler = new Handler(Looper.getMainLooper());
						
						mExecutorService.execute(new Runnable() {
								@Override
								public void run() {
										DatabaseReference getCommentsCount = FirebaseDatabase.getInstance().getReference("skyline/posts-comments").child(key);
										getCommentsCount.addListenerForSingleValueEvent(new ValueEventListener() {
												@Override
												public void onDataChange(DataSnapshot dataSnapshot1) {
														mMainHandler.post(new Runnable() {
																@Override
																public void run() {
																		long commentsCount = dataSnapshot1.getChildrenCount();
																		_setCommentCount(title_count, commentsCount);
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
		
		public class Comments_listAdapter extends RecyclerView.Adapter<Comments_listAdapter.ViewHolder> {
				
				ArrayList<HashMap<String, Object>> _data;
				
				public Comments_listAdapter(ArrayList<HashMap<String, Object>> _arr) {
						_data = _arr;
				}
				
				@Override
				public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
						LayoutInflater _inflater = getLayoutInflater();
						View _v = _inflater.inflate(R.layout.synapse_comments_list_cv, null);
						RecyclerView.LayoutParams _lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
						_v.setLayoutParams(_lp);
						return new ViewHolder(_v);
				}
				
				@Override
				public void onBindViewHolder(ViewHolder _holder, final int _position) {
						View _view = _holder.itemView;
						
						DatabaseReference getUserDetails = FirebaseDatabase.getInstance().getReference("skyline/users").child(_data.get((int)_position).get("uid").toString());
						DatabaseReference getCommentsRef = FirebaseDatabase.getInstance().getReference("skyline/posts-comments").child(postKey).child(_data.get((int)_position).get("key").toString());
						DatabaseReference checkCommentLike = FirebaseDatabase.getInstance().getReference("skyline/posts-comments-like").child(postKey).child(_data.get((int)_position).get("key").toString()).child(FirebaseAuth.getInstance().getCurrentUser().getUid());
						DatabaseReference getCommentsLikeCount = FirebaseDatabase.getInstance().getReference("skyline/posts-comments-like").child(postKey).child(_data.get((int)_position).get("key").toString());
						DatabaseReference commentCheckPublisherLike = FirebaseDatabase.getInstance().getReference("skyline/posts-comments-like").child(postKey).child(_data.get((int)_position).get("key").toString()).child(postPublisherUID);
						
						ArrayList<HashMap<String, Object>> commentsRepliesListMap = new ArrayList<>();
						
						final LinearLayout body = _view.findViewById(R.id.body);
						final TextView show_more_comment = _view.findViewById(R.id.show_more_comment);
						final ProgressBar progress = _view.findViewById(R.id.progress);
						final androidx.cardview.widget.CardView profileCard = _view.findViewById(R.id.profileCard);
						final LinearLayout commentUnix = _view.findViewById(R.id.commentUnix);
						final ImageView profileImage = _view.findViewById(R.id.profileImage);
						final LinearLayout top = _view.findViewById(R.id.top);
						final LinearLayout pcb = _view.findViewById(R.id.pcb);
						final LinearLayout cmBottom = _view.findViewById(R.id.cmBottom);
						final TextView username = _view.findViewById(R.id.username);
						final ImageView genderBadge = _view.findViewById(R.id.genderBadge);
						final ImageView badge = _view.findViewById(R.id.badge);
						final ImageView top_popular_1_fire_ic = _view.findViewById(R.id.top_popular_1_fire_ic);
						final ImageView top_popular_2_fire_ic = _view.findViewById(R.id.top_popular_2_fire_ic);
						final ImageView top_popular_3_fire_ic = _view.findViewById(R.id.top_popular_3_fire_ic);
						final LinearLayout spc = _view.findViewById(R.id.spc);
						final LinearLayout more = _view.findViewById(R.id.more);
						final ImageView more_ic = _view.findViewById(R.id.more_ic);
						final TextView comment_text = _view.findViewById(R.id.comment_text);
						final TextView push = _view.findViewById(R.id.push);
						final LinearLayout btmSpc = _view.findViewById(R.id.btmSpc);
						final LinearLayout like_unlike = _view.findViewById(R.id.like_unlike);
						final ImageView like_unlike_ic = _view.findViewById(R.id.like_unlike_ic);
						final TextView like_count = _view.findViewById(R.id.like_count);
						final RelativeLayout likedByPublisherLayout = _view.findViewById(R.id.likedByPublisherLayout);
						final de.hdodenhof.circleimageview.CircleImageView likedByPublisherLayoutAvatar = _view.findViewById(R.id.likedByPublisherLayoutAvatar);
						final LinearLayout likedByPublisherLayoutRelative = _view.findViewById(R.id.likedByPublisherLayoutRelative);
						final ImageView likedByPublisherLayoutHeartIc = _view.findViewById(R.id.likedByPublisherLayoutHeartIc);
						final LinearLayout replies_layout = _view.findViewById(R.id.replies_layout);
						final TextView show_other_replies_button = _view.findViewById(R.id.show_other_replies_button);
						final RecyclerView other_replies_list = _view.findViewById(R.id.other_replies_list);
						final TextView hide_replies_list_button = _view.findViewById(R.id.hide_replies_list_button);
						
						body.setVisibility(View.GONE);
						likedByPublisherLayout.setVisibility(View.GONE);
						replies_layout.setVisibility(View.GONE);
						_viewGraphics(body, 0xFFFFFFFF, 0xFFEEEEEE, 0, 0, Color.TRANSPARENT);
						profileCard.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b) { this.setCornerRadius(a); this.setColor(b); return this; } }.getIns((int)300, Color.TRANSPARENT));
						likedByPublisherLayoutHeartIc.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b) { this.setCornerRadius(a); this.setColor(b); return this; } }.getIns((int)300, 0xFFFFFFFF));
						_ImageColor(likedByPublisherLayoutHeartIc, 0xFFE91E63);        
						_ImageColor(top_popular_1_fire_ic, 0xFFF50057);
						_ImageColor(top_popular_2_fire_ic, 0xFFFF5722);
						_ImageColor(top_popular_3_fire_ic, 0xFFFF9800);
						comment_text.setText(_data.get((int)_position).get("comment").toString());
						
						other_replies_list.setAdapter(new CommentsRepliesAdapter(commentsRepliesListMap));
						other_replies_list.setLayoutManager(new LinearLayoutManager(getActivity()));
						
						{
								ExecutorService mExecutorService = Executors.newSingleThreadExecutor();
								Handler mMainHandler = new Handler(Looper.getMainLooper());
								
								mExecutorService.execute(new Runnable() {
										@Override
										public void run() {
												Query commentsRepliesQuery = FirebaseDatabase.getInstance().getReference("skyline/posts-comments-replies").child(postKey).child(_data.get((int)_position).get("key").toString()).orderByChild("like");
												commentsRepliesQuery.addListenerForSingleValueEvent(new ValueEventListener() {
														@Override
														public void onDataChange(@NonNull DataSnapshot snapshot) {
																mMainHandler.post(new Runnable() {
																		@Override
																		public void run() {
																				if (snapshot.exists()) {
																						replies_layout.setVisibility(View.VISIBLE);
																						other_replies_list.setVisibility(View.GONE);
																						hide_replies_list_button.setVisibility(View.GONE);
																						commentsRepliesListMap.clear();
																						
																						GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
																						
																						for (DataSnapshot _data : snapshot.getChildren()) {
																								HashMap<String, Object> commentsGetMap = _data.getValue(_ind);
																								commentsRepliesListMap.add(commentsGetMap);
																								SketchwareUtil.sortListMap(commentsRepliesListMap, "like", true, false);
																						}
																						
																						other_replies_list.getAdapter().notifyDataSetChanged();
																				} else {
																						replies_layout.setVisibility(View.GONE);
																				}
																		}
																});
														}
														
														@Override
														public void onCancelled(@NonNull DatabaseError error) {
																
														}
												});
										}
								});
						}
						
						show_other_replies_button.setOnClickListener(new View.OnClickListener() {
								@Override
								public void onClick(View _view) {
										if (other_replies_list.getVisibility() == View.GONE) {
												show_other_replies_button.setVisibility(View.GONE);
												other_replies_list.setVisibility(View.VISIBLE);
												hide_replies_list_button.setVisibility(View.VISIBLE);
										}
								}
						});
						
						hide_replies_list_button.setOnClickListener(new View.OnClickListener() {
								@Override
								public void onClick(View _view) {
										if (other_replies_list.getVisibility() == View.VISIBLE) {
												show_other_replies_button.setVisibility(View.VISIBLE);
												other_replies_list.setVisibility(View.GONE);
												hide_replies_list_button.setVisibility(View.GONE);
										}
								}
						});
						
						body.setOnClickListener(new View.OnClickListener() {
								@Override
								public void onClick(View _view) {
										replyToCommentKey = _data.get((int)_position).get("key").toString();
										comment_send_input.setHint(getResources().getString(R.string.reply_to_user_hint).replace("%1$s", username.getText().toString()));
										comment_send_input.requestFocus();
										InputMethodManager mImm = (InputMethodManager) requireContext().getSystemService(Context.INPUT_METHOD_SERVICE);
										mImm.showSoftInput(comment_send_input, InputMethodManager.SHOW_IMPLICIT);
										replyToComment = true;
										cancel_reply_mode.setVisibility(View.VISIBLE);
								}
						});
						
						if (postPublisherAvatar.equals("null")) {
								likedByPublisherLayoutAvatar.setImageResource(R.drawable.avatar);
						} else {
								Glide.with(getContext()).load(Uri.parse(postPublisherAvatar)).into(likedByPublisherLayoutAvatar);
						}
						
						if (UserInfoCacheMap.containsKey("uid-".concat(_data.get((int)_position).get("uid").toString()))) {
								body.setVisibility(View.VISIBLE);
								if (UserInfoCacheMap.get("banned-".concat(_data.get((int)_position).get("uid").toString())).toString().equals("true")) {
										profileImage.setImageResource(R.drawable.avatar);
								} else {
										if (UserInfoCacheMap.get("avatar-".concat(_data.get((int)_position).get("uid").toString())).toString().equals("null")) {
												profileImage.setImageResource(R.drawable.avatar);
										} else {
												Glide.with(getContext()).load(Uri.parse(UserInfoCacheMap.get("avatar-".concat(_data.get((int)_position).get("uid").toString())).toString())).into(profileImage);
										}
								}
								if (UserInfoCacheMap.get("nickname-".concat(_data.get((int)_position).get("uid").toString())).toString().equals("null")) {
										username.setText("@" + UserInfoCacheMap.get("username-".concat(_data.get((int)_position).get("uid").toString())).toString());
								} else {
										username.setText(UserInfoCacheMap.get("nickname-".concat(_data.get((int)_position).get("uid").toString())).toString());
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
						} else {
								getUserDetails.addListenerForSingleValueEvent(new ValueEventListener() {
										@Override
										public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
												if(dataSnapshot.exists()) {
														UserInfoCacheMap.put("uid-".concat(_data.get((int)_position).get("uid").toString()), _data.get((int)_position).get("uid").toString());
														UserInfoCacheMap.put("avatar-".concat(_data.get((int)_position).get("uid").toString()), dataSnapshot.child("avatar").getValue(String.class));
														UserInfoCacheMap.put("banned-".concat(_data.get((int)_position).get("uid").toString()), dataSnapshot.child("banned").getValue(String.class));
														UserInfoCacheMap.put("nickname-".concat(_data.get((int)_position).get("uid").toString()), dataSnapshot.child("nickname").getValue(String.class));
														UserInfoCacheMap.put("username-".concat(_data.get((int)_position).get("uid").toString()), dataSnapshot.child("username").getValue(String.class));
														UserInfoCacheMap.put("gender-".concat(_data.get((int)_position).get("uid").toString()), dataSnapshot.child("gender").getValue(String.class));
														UserInfoCacheMap.put("verify-".concat(_data.get((int)_position).get("uid").toString()), dataSnapshot.child("verify").getValue(String.class));
														UserInfoCacheMap.put("acc_type-".concat(_data.get((int)_position).get("uid").toString()), dataSnapshot.child("account_type").getValue(String.class));
														body.setVisibility(View.VISIBLE);
														if (dataSnapshot.child("banned").getValue(String.class).equals("true")) {
																profileImage.setImageResource(R.drawable.avatar);
														} else {
																if (dataSnapshot.child("avatar").getValue(String.class).equals("null")) {
																		profileImage.setImageResource(R.drawable.avatar);
																} else {
																		Glide.with(getContext()).load(Uri.parse(dataSnapshot.child("avatar").getValue(String.class))).into(profileImage);
																}
														}
														if (dataSnapshot.child("nickname").getValue(String.class).equals("null")) {
																username.setText("@" + dataSnapshot.child("username").getValue(String.class));
														} else {
																username.setText(dataSnapshot.child("nickname").getValue(String.class));
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
																badge.setImageResource(R.drawable.admin_badge);
																badge.setVisibility(View.VISIBLE);
														} else {
																if (dataSnapshot.child("account_type").getValue(String.class).equals("moderator")) {
																		badge.setImageResource(R.drawable.moderator_badge);
																		badge.setVisibility(View.VISIBLE);
																} else {
																		if (dataSnapshot.child("account_type").getValue(String.class).equals("support")) {
																				badge.setImageResource(R.drawable.support_badge);
																				badge.setVisibility(View.VISIBLE);
																		} else {
																				if (dataSnapshot.child("account_type").getValue(String.class).equals("user")) {
																						if (dataSnapshot.child("verify").getValue(String.class).equals("true")) {
																								badge.setImageResource(R.drawable.verified_badge);
																								badge.setVisibility(View.VISIBLE);
																						} else {
																								badge.setVisibility(View.GONE);
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
						}
						
						if (_data.size() > 19) {
								if (_position == (_data.size() - 1)) {
										show_more_comment.setVisibility(View.VISIBLE);
										progress.setVisibility(View.GONE);
										show_more_comment.setOnClickListener(new View.OnClickListener(){
												@Override
												public void onClick(View _clickedView){
														show_more_comment.setVisibility(View.GONE);
														progress.setVisibility(View.VISIBLE);
														handler.postDelayed(new Runnable() {
																@Override
																public void run() {
																		getCommentsRef(postKey, true);
																		progress.setVisibility(View.GONE);
																		handler.removeCallbacksAndMessages(null);
																}
														}, 500);
												}
										});
								} else {
										show_more_comment.setVisibility(View.GONE);
										progress.setVisibility(View.GONE);
								}
						} else {
								show_more_comment.setVisibility(View.GONE);
								progress.setVisibility(View.GONE);
						}
						
						if (!_data.get((int)_position).get("push_time").toString().equals("null")) {
								push.setVisibility(View.VISIBLE);
								_setTime(Double.parseDouble(_data.get((int)_position).get("push_time").toString()), push);
						}
						else {
								push.setVisibility(View.GONE);
						}
						
						_setCommentLikeCount(like_count, Double.parseDouble(_data.get((int)_position).get("like").toString()));
						postCommentLikeCountCache.put(_data.get((int)_position).get("key").toString(), _data.get((int)_position).get("like").toString());
						if (postCommentLikeCountCache.containsKey(_data.get((int)_position).get("key").toString())) {
								if (_position == 0) {
										if (Double.parseDouble(postCommentLikeCountCache.get(_data.get((int)_position).get("key").toString()).toString()) > 15000) {
												top_popular_1_fire_ic.setVisibility(View.VISIBLE);
												top_popular_2_fire_ic.setVisibility(View.GONE);
												top_popular_3_fire_ic.setVisibility(View.GONE);
										} else {
												top_popular_1_fire_ic.setVisibility(View.GONE);
												top_popular_2_fire_ic.setVisibility(View.GONE);
												top_popular_3_fire_ic.setVisibility(View.GONE);
										}
								} else {
										if (_position == 1) {
												if (Double.parseDouble(postCommentLikeCountCache.get(_data.get((int)_position).get("key").toString()).toString()) > 10000) {
														top_popular_1_fire_ic.setVisibility(View.GONE);
														top_popular_2_fire_ic.setVisibility(View.VISIBLE);
														top_popular_3_fire_ic.setVisibility(View.GONE);
												} else {
														top_popular_1_fire_ic.setVisibility(View.GONE);
														top_popular_2_fire_ic.setVisibility(View.GONE);
														top_popular_3_fire_ic.setVisibility(View.GONE);
												}
										} else {
												if (_position == 2) {
														if (Double.parseDouble(postCommentLikeCountCache.get(_data.get((int)_position).get("key").toString()).toString()) > 5000) {
																top_popular_1_fire_ic.setVisibility(View.GONE);
																top_popular_2_fire_ic.setVisibility(View.GONE);
																top_popular_3_fire_ic.setVisibility(View.VISIBLE);
														} else {
																top_popular_1_fire_ic.setVisibility(View.GONE);
																top_popular_2_fire_ic.setVisibility(View.GONE);
																top_popular_3_fire_ic.setVisibility(View.GONE);
														}
												} else {
														top_popular_1_fire_ic.setVisibility(View.GONE);
														top_popular_2_fire_ic.setVisibility(View.GONE);
														top_popular_3_fire_ic.setVisibility(View.GONE);
												}
										}
								}
						}
						
						commentCheckPublisherLike.addListenerForSingleValueEvent(new ValueEventListener() {
								@Override
								public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
										if(dataSnapshot.exists()) {
												likedByPublisherLayout.setVisibility(View.VISIBLE);
												likedByPublisherLayout.setOnClickListener(new View.OnClickListener(){
														@Override
														public void onClick(View _clickedView){
																_showCommentLikedByPublisherPopup();
														}
												});
										} else {
												likedByPublisherLayout.setVisibility(View.GONE);
										}
								}
								@Override
								public void onCancelled(@NonNull DatabaseError databaseError) {
										
								}
						});
						
						checkCommentLike.addListenerForSingleValueEvent(new ValueEventListener() {
								@Override
								public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
										if(dataSnapshot.exists()) {
												like_unlike_ic.setImageResource(R.drawable.post_icons_1_2);
										} else {
												like_unlike_ic.setImageResource(R.drawable.post_icons_1_1);
										}
								}
								@Override
								public void onCancelled(@NonNull DatabaseError databaseError) {
										
								}
						});
						
						like_unlike.setOnClickListener(new View.OnClickListener(){
								@Override
								public void onClick(View _clickedView){
										checkCommentLike.addListenerForSingleValueEvent(new ValueEventListener() {
												@Override
												public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
														if(dataSnapshot.exists()) {
																checkCommentLike.removeValue();
																postCommentLikeCountCache.put(_data.get((int)_position).get("key").toString(), String.valueOf((long)(Double.parseDouble(postCommentLikeCountCache.get(_data.get((int)_position).get("key").toString()).toString()) - 1)));
																_setCommentLikeCount(like_count, Double.parseDouble(postCommentLikeCountCache.get(_data.get((int)_position).get("key").toString()).toString()));
																if (postPublisherUID.equals(FirebaseAuth.getInstance().getCurrentUser().getUid())) {
																		ObjectAnimator setGoneAlphaAnim = new ObjectAnimator();
																		setGoneAlphaAnim.addListener(new Animator.AnimatorListener() {
																				@Override
																				public void onAnimationStart(Animator _param1) {
																						
																				}
																				
																				@Override
																				public void onAnimationEnd(Animator _param1) {
																						likedByPublisherLayout.setVisibility(View.GONE);
																						_param1.cancel();
																				}
																				
																				@Override
																				public void onAnimationCancel(Animator _param1) {
																						
																				}
																				
																				@Override
																				public void onAnimationRepeat(Animator _param1) {
																						
																				}
																		});
																		
																		setGoneAlphaAnim.setTarget(likedByPublisherLayout);
																		setGoneAlphaAnim.setPropertyName("alpha");
																		setGoneAlphaAnim.setInterpolator(new LinearInterpolator());
																		setGoneAlphaAnim.setFloatValues((float)(1), (float)(0));
																		setGoneAlphaAnim.setDuration((int)(94));
																		setGoneAlphaAnim.start();
																}
																like_unlike_ic.setImageResource(R.drawable.post_icons_1_1);
														} else {
																getCommentsLikeCount.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(FirebaseAuth.getInstance().getCurrentUser().getUid());
																postCommentLikeCountCache.put(_data.get((int)_position).get("key").toString(), String.valueOf((long)(Double.parseDouble(postCommentLikeCountCache.get(_data.get((int)_position).get("key").toString()).toString()) + 1)));
																_setCommentLikeCount(like_count, Double.parseDouble(postCommentLikeCountCache.get(_data.get((int)_position).get("key").toString()).toString()));
																if (postPublisherUID.equals(FirebaseAuth.getInstance().getCurrentUser().getUid())) {
																		ObjectAnimator setVisibleAlphaAnim = new ObjectAnimator();
																		setVisibleAlphaAnim.addListener(new Animator.AnimatorListener() {
																				@Override
																				public void onAnimationStart(Animator _param1) {
																						likedByPublisherLayout.setVisibility(View.VISIBLE);
																				}
																				
																				@Override
																				public void onAnimationEnd(Animator _param1) {
																						_param1.cancel();
																				}
																				
																				@Override
																				public void onAnimationCancel(Animator _param1) {
																						
																				}
																				
																				@Override
																				public void onAnimationRepeat(Animator _param1) {
																						
																				}
																		});
																		
																		setVisibleAlphaAnim.setTarget(likedByPublisherLayout);
																		setVisibleAlphaAnim.setPropertyName("alpha");
																		setVisibleAlphaAnim.setInterpolator(new LinearInterpolator());
																		setVisibleAlphaAnim.setFloatValues((float)(0), (float)(1));
																		setVisibleAlphaAnim.setDuration((int)(94));
																		setVisibleAlphaAnim.start();
																}
																like_unlike_ic.setImageResource(R.drawable.post_icons_1_2);
														}
												}
												@Override
												public void onCancelled(@NonNull DatabaseError databaseError) {
														
												}
										});
										
										getCommentsLikeCount.addListenerForSingleValueEvent(new ValueEventListener() {
												@Override
												public void onDataChange(DataSnapshot dataSnapshot) {
														long count = dataSnapshot.getChildrenCount();
														getCommentsRef.child("like").setValue(postCommentLikeCountCache.get(_data.get((int)_position).get("key").toString()).toString());
												}
												
												@Override
												public void onCancelled(DatabaseError databaseError) {
														
												}
										});
										
								}
						});
						
						profileCard.setOnClickListener(new View.OnClickListener() {
								@Override
								public void onClick(View _view) {
										intent.setClass(getContext(), ProfileActivity.class);
										intent.putExtra("uid", _data.get((int)_position).get("uid").toString());
										getContext().startActivity(intent);
								}
						});
				}
				
				@Override
				public int getItemCount() {
						return _data.size();
				}
				
				public class ViewHolder extends RecyclerView.ViewHolder {
						public ViewHolder(View _view) {
								super(_view);
						}
				}
		}
		
		public class CommentsRepliesAdapter extends RecyclerView.Adapter<CommentsRepliesAdapter.ViewHolder> {
				
				ArrayList<HashMap<String, Object>> _data;
				
				public CommentsRepliesAdapter(ArrayList<HashMap<String, Object>> _arr) {
						_data = _arr;
				}
				
				@Override
				public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
						LayoutInflater _inflater = getLayoutInflater();
						View _v = _inflater.inflate(R.layout.reply_comments_synapse, null);
						RecyclerView.LayoutParams _lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
						_v.setLayoutParams(_lp);
						return new ViewHolder(_v);
				}
				
				@Override
				public void onBindViewHolder(ViewHolder _holder, final int _position) {
						View _view = _holder.itemView;
						
						DatabaseReference getUserDetails = FirebaseDatabase.getInstance().getReference("skyline/users").child(_data.get((int)_position).get("uid").toString());
						DatabaseReference getCommentsRef = FirebaseDatabase.getInstance().getReference("skyline/posts-comments-replies").child(postKey).child(_data.get((int)_position).get("replyCommentkey").toString()).child(_data.get((int)_position).get("key").toString());
						DatabaseReference checkCommentLike = FirebaseDatabase.getInstance().getReference("skyline/posts-comments-replies-like").child(postKey).child(_data.get((int)_position).get("key").toString()).child(FirebaseAuth.getInstance().getCurrentUser().getUid());
						DatabaseReference getCommentsLikeCount = FirebaseDatabase.getInstance().getReference("skyline/posts-comments-replies-like").child(postKey).child(_data.get((int)_position).get("key").toString());
						DatabaseReference commentCheckPublisherLike = FirebaseDatabase.getInstance().getReference("skyline/posts-comments-replies-like").child(postKey).child(_data.get((int)_position).get("key").toString()).child(postPublisherUID);
						
						final LinearLayout body = _view.findViewById(R.id.body);
						final TextView show_more_comment = _view.findViewById(R.id.show_more_comment);
						final ProgressBar progress = _view.findViewById(R.id.progress);
						final androidx.cardview.widget.CardView profileCard = _view.findViewById(R.id.profileCard);
						final LinearLayout commentUnix = _view.findViewById(R.id.commentUnix);
						final ImageView profileImage = _view.findViewById(R.id.profileImage);
						final LinearLayout top = _view.findViewById(R.id.top);
						final LinearLayout pcb = _view.findViewById(R.id.pcb);
						final LinearLayout cmBottom = _view.findViewById(R.id.cmBottom);
						final TextView username = _view.findViewById(R.id.username);
						final ImageView genderBadge = _view.findViewById(R.id.genderBadge);
						final ImageView badge = _view.findViewById(R.id.badge);
						final ImageView top_popular_1_fire_ic = _view.findViewById(R.id.top_popular_1_fire_ic);
						final ImageView top_popular_2_fire_ic = _view.findViewById(R.id.top_popular_2_fire_ic);
						final ImageView top_popular_3_fire_ic = _view.findViewById(R.id.top_popular_3_fire_ic);
						final LinearLayout spc = _view.findViewById(R.id.spc);
						final LinearLayout more = _view.findViewById(R.id.more);
						final ImageView more_ic = _view.findViewById(R.id.more_ic);
						final TextView comment_text = _view.findViewById(R.id.comment_text);
						final TextView push = _view.findViewById(R.id.push);
						final LinearLayout btmSpc = _view.findViewById(R.id.btmSpc);
						final LinearLayout like_unlike = _view.findViewById(R.id.like_unlike);
						final ImageView like_unlike_ic = _view.findViewById(R.id.like_unlike_ic);
						final TextView like_count = _view.findViewById(R.id.like_count);
						final RelativeLayout likedByPublisherLayout = _view.findViewById(R.id.likedByPublisherLayout);
						final de.hdodenhof.circleimageview.CircleImageView likedByPublisherLayoutAvatar = _view.findViewById(R.id.likedByPublisherLayoutAvatar);
						final LinearLayout likedByPublisherLayoutRelative = _view.findViewById(R.id.likedByPublisherLayoutRelative);
						final ImageView likedByPublisherLayoutHeartIc = _view.findViewById(R.id.likedByPublisherLayoutHeartIc);
						final LinearLayout replies_layout = _view.findViewById(R.id.replies_layout);
						final TextView show_other_replies_button = _view.findViewById(R.id.show_other_replies_button);
						final RecyclerView other_replies_list = _view.findViewById(R.id.other_replies_list);
						final TextView hide_replies_list_button = _view.findViewById(R.id.hide_replies_list_button);
						
						body.setVisibility(View.GONE);
						likedByPublisherLayout.setVisibility(View.GONE);
						replies_layout.setVisibility(View.GONE);
						profileCard.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b) { this.setCornerRadius(a); this.setColor(b); return this; } }.getIns((int)300, Color.TRANSPARENT));
						likedByPublisherLayoutHeartIc.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b) { this.setCornerRadius(a); this.setColor(b); return this; } }.getIns((int)300, 0xFFFFFFFF));
						_ImageColor(likedByPublisherLayoutHeartIc, 0xFFE91E63);        
						_ImageColor(top_popular_1_fire_ic, 0xFFF50057);
						_ImageColor(top_popular_2_fire_ic, 0xFFFF5722);
						_ImageColor(top_popular_3_fire_ic, 0xFFFF9800);
						comment_text.setText(_data.get((int)_position).get("comment").toString());
						
						if (postPublisherAvatar.equals("null")) {
								likedByPublisherLayoutAvatar.setImageResource(R.drawable.avatar);
						} else {
								Glide.with(getContext()).load(Uri.parse(postPublisherAvatar)).into(likedByPublisherLayoutAvatar);
						}
						
						if (UserInfoCacheMap.containsKey("uid-".concat(_data.get((int)_position).get("uid").toString()))) {
								body.setVisibility(View.VISIBLE);
								if (UserInfoCacheMap.get("banned-".concat(_data.get((int)_position).get("uid").toString())).toString().equals("true")) {
										profileImage.setImageResource(R.drawable.avatar);
								} else {
										if (UserInfoCacheMap.get("avatar-".concat(_data.get((int)_position).get("uid").toString())).toString().equals("null")) {
												profileImage.setImageResource(R.drawable.avatar);
										} else {
												Glide.with(getContext()).load(Uri.parse(UserInfoCacheMap.get("avatar-".concat(_data.get((int)_position).get("uid").toString())).toString())).into(profileImage);
										}
								}
								if (UserInfoCacheMap.get("nickname-".concat(_data.get((int)_position).get("uid").toString())).toString().equals("null")) {
										username.setText("@" + UserInfoCacheMap.get("username-".concat(_data.get((int)_position).get("uid").toString())).toString());
								} else {
										username.setText(UserInfoCacheMap.get("nickname-".concat(_data.get((int)_position).get("uid").toString())).toString());
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
						} else {
								getUserDetails.addListenerForSingleValueEvent(new ValueEventListener() {
										@Override
										public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
												if(dataSnapshot.exists()) {
														UserInfoCacheMap.put("uid-".concat(_data.get((int)_position).get("uid").toString()), _data.get((int)_position).get("uid").toString());
														UserInfoCacheMap.put("avatar-".concat(_data.get((int)_position).get("uid").toString()), dataSnapshot.child("avatar").getValue(String.class));
														UserInfoCacheMap.put("banned-".concat(_data.get((int)_position).get("uid").toString()), dataSnapshot.child("banned").getValue(String.class));
														UserInfoCacheMap.put("nickname-".concat(_data.get((int)_position).get("uid").toString()), dataSnapshot.child("nickname").getValue(String.class));
														UserInfoCacheMap.put("username-".concat(_data.get((int)_position).get("uid").toString()), dataSnapshot.child("username").getValue(String.class));
														UserInfoCacheMap.put("gender-".concat(_data.get((int)_position).get("uid").toString()), dataSnapshot.child("gender").getValue(String.class));
														UserInfoCacheMap.put("verify-".concat(_data.get((int)_position).get("uid").toString()), dataSnapshot.child("verify").getValue(String.class));
														UserInfoCacheMap.put("acc_type-".concat(_data.get((int)_position).get("uid").toString()), dataSnapshot.child("account_type").getValue(String.class));
														body.setVisibility(View.VISIBLE);
														if (dataSnapshot.child("banned").getValue(String.class).equals("true")) {
																profileImage.setImageResource(R.drawable.avatar);
														} else {
																if (dataSnapshot.child("avatar").getValue(String.class).equals("null")) {
																		profileImage.setImageResource(R.drawable.avatar);
																} else {
																		Glide.with(getContext()).load(Uri.parse(dataSnapshot.child("avatar").getValue(String.class))).into(profileImage);
																}
														}
														if (dataSnapshot.child("nickname").getValue(String.class).equals("null")) {
																username.setText("@" + dataSnapshot.child("username").getValue(String.class));
														} else {
																username.setText(dataSnapshot.child("nickname").getValue(String.class));
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
																badge.setImageResource(R.drawable.admin_badge);
																badge.setVisibility(View.VISIBLE);
														} else {
																if (dataSnapshot.child("account_type").getValue(String.class).equals("moderator")) {
																		badge.setImageResource(R.drawable.moderator_badge);
																		badge.setVisibility(View.VISIBLE);
																} else {
																		if (dataSnapshot.child("account_type").getValue(String.class).equals("support")) {
																				badge.setImageResource(R.drawable.support_badge);
																				badge.setVisibility(View.VISIBLE);
																		} else {
																				if (dataSnapshot.child("account_type").getValue(String.class).equals("user")) {
																						if (dataSnapshot.child("verify").getValue(String.class).equals("true")) {
																								badge.setImageResource(R.drawable.verified_badge);
																								badge.setVisibility(View.VISIBLE);
																						} else {
																								badge.setVisibility(View.GONE);
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
						}
						
						if (_data.size() > 19) {
								if (_position == (_data.size() - 1)) {
										show_more_comment.setVisibility(View.VISIBLE);
										progress.setVisibility(View.GONE);
										show_more_comment.setOnClickListener(new View.OnClickListener(){
												@Override
												public void onClick(View _clickedView){
														show_more_comment.setVisibility(View.GONE);
														progress.setVisibility(View.VISIBLE);
														handler.postDelayed(new Runnable() {
																@Override
																public void run() {
																		getCommentsRef(postKey, true);
																		progress.setVisibility(View.GONE);
																		handler.removeCallbacksAndMessages(null);
																}
														}, 500);
												}
										});
								} else {
										show_more_comment.setVisibility(View.GONE);
										progress.setVisibility(View.GONE);
								}
						} else {
								show_more_comment.setVisibility(View.GONE);
								progress.setVisibility(View.GONE);
						}
						
						if (!_data.get((int)_position).get("push_time").toString().equals("null")) {
								push.setVisibility(View.VISIBLE);
								_setTime(Double.parseDouble(_data.get((int)_position).get("push_time").toString()), push);
						}
						else {
								push.setVisibility(View.GONE);
						}
						
						_setCommentLikeCount(like_count, Double.parseDouble(_data.get((int)_position).get("like").toString()));
						postCommentLikeCountCache.put(_data.get((int)_position).get("key").toString(), _data.get((int)_position).get("like").toString());
						if (postCommentLikeCountCache.containsKey(_data.get((int)_position).get("key").toString())) {
								if (_position == 0) {
										if (Double.parseDouble(postCommentLikeCountCache.get(_data.get((int)_position).get("key").toString()).toString()) > 15000) {
												top_popular_1_fire_ic.setVisibility(View.VISIBLE);
												top_popular_2_fire_ic.setVisibility(View.GONE);
												top_popular_3_fire_ic.setVisibility(View.GONE);
										} else {
												top_popular_1_fire_ic.setVisibility(View.GONE);
												top_popular_2_fire_ic.setVisibility(View.GONE);
												top_popular_3_fire_ic.setVisibility(View.GONE);
										}
								} else {
										if (_position == 1) {
												if (Double.parseDouble(postCommentLikeCountCache.get(_data.get((int)_position).get("key").toString()).toString()) > 10000) {
														top_popular_1_fire_ic.setVisibility(View.GONE);
														top_popular_2_fire_ic.setVisibility(View.VISIBLE);
														top_popular_3_fire_ic.setVisibility(View.GONE);
												} else {
														top_popular_1_fire_ic.setVisibility(View.GONE);
														top_popular_2_fire_ic.setVisibility(View.GONE);
														top_popular_3_fire_ic.setVisibility(View.GONE);
												}
										} else {
												if (_position == 2) {
														if (Double.parseDouble(postCommentLikeCountCache.get(_data.get((int)_position).get("key").toString()).toString()) > 5000) {
																top_popular_1_fire_ic.setVisibility(View.GONE);
																top_popular_2_fire_ic.setVisibility(View.GONE);
																top_popular_3_fire_ic.setVisibility(View.VISIBLE);
														} else {
																top_popular_1_fire_ic.setVisibility(View.GONE);
																top_popular_2_fire_ic.setVisibility(View.GONE);
																top_popular_3_fire_ic.setVisibility(View.GONE);
														}
												} else {
														top_popular_1_fire_ic.setVisibility(View.GONE);
														top_popular_2_fire_ic.setVisibility(View.GONE);
														top_popular_3_fire_ic.setVisibility(View.GONE);
												}
										}
								}
						}
						
						commentCheckPublisherLike.addListenerForSingleValueEvent(new ValueEventListener() {
								@Override
								public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
										if(dataSnapshot.exists()) {
												likedByPublisherLayout.setVisibility(View.VISIBLE);
												likedByPublisherLayout.setOnClickListener(new View.OnClickListener(){
														@Override
														public void onClick(View _clickedView){
																_showCommentLikedByPublisherPopup();
														}
												});
										} else {
												likedByPublisherLayout.setVisibility(View.GONE);
										}
								}
								@Override
								public void onCancelled(@NonNull DatabaseError databaseError) {
										
								}
						});
						
						checkCommentLike.addListenerForSingleValueEvent(new ValueEventListener() {
								@Override
								public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
										if(dataSnapshot.exists()) {
												like_unlike_ic.setImageResource(R.drawable.post_icons_1_2);
										} else {
												like_unlike_ic.setImageResource(R.drawable.post_icons_1_1);
										}
								}
								@Override
								public void onCancelled(@NonNull DatabaseError databaseError) {
										
								}
						});
						
						like_unlike.setOnClickListener(new View.OnClickListener(){
								@Override
								public void onClick(View _clickedView){
										checkCommentLike.addListenerForSingleValueEvent(new ValueEventListener() {
												@Override
												public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
														if(dataSnapshot.exists()) {
																checkCommentLike.removeValue();
																postCommentLikeCountCache.put(_data.get((int)_position).get("key").toString(), String.valueOf((long)(Double.parseDouble(postCommentLikeCountCache.get(_data.get((int)_position).get("key").toString()).toString()) - 1)));
																_setCommentLikeCount(like_count, Double.parseDouble(postCommentLikeCountCache.get(_data.get((int)_position).get("key").toString()).toString()));
																if (postPublisherUID.equals(FirebaseAuth.getInstance().getCurrentUser().getUid())) {
																		ObjectAnimator setGoneAlphaAnim = new ObjectAnimator();
																		setGoneAlphaAnim.addListener(new Animator.AnimatorListener() {
																				@Override
																				public void onAnimationStart(Animator _param1) {
																						
																				}
																				
																				@Override
																				public void onAnimationEnd(Animator _param1) {
																						likedByPublisherLayout.setVisibility(View.GONE);
																						_param1.cancel();
																				}
																				
																				@Override
																				public void onAnimationCancel(Animator _param1) {
																						
																				}
																				
																				@Override
																				public void onAnimationRepeat(Animator _param1) {
																						
																				}
																		});
																		
																		setGoneAlphaAnim.setTarget(likedByPublisherLayout);
																		setGoneAlphaAnim.setPropertyName("alpha");
																		setGoneAlphaAnim.setInterpolator(new LinearInterpolator());
																		setGoneAlphaAnim.setFloatValues((float)(1), (float)(0));
																		setGoneAlphaAnim.setDuration((int)(94));
																		setGoneAlphaAnim.start();
																}
																like_unlike_ic.setImageResource(R.drawable.post_icons_1_1);
														} else {
																getCommentsLikeCount.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(FirebaseAuth.getInstance().getCurrentUser().getUid());
																postCommentLikeCountCache.put(_data.get((int)_position).get("key").toString(), String.valueOf((long)(Double.parseDouble(postCommentLikeCountCache.get(_data.get((int)_position).get("key").toString()).toString()) + 1)));
																_setCommentLikeCount(like_count, Double.parseDouble(postCommentLikeCountCache.get(_data.get((int)_position).get("key").toString()).toString()));
																if (postPublisherUID.equals(FirebaseAuth.getInstance().getCurrentUser().getUid())) {
																		ObjectAnimator setVisibleAlphaAnim = new ObjectAnimator();
																		setVisibleAlphaAnim.addListener(new Animator.AnimatorListener() {
																				@Override
																				public void onAnimationStart(Animator _param1) {
																						likedByPublisherLayout.setVisibility(View.VISIBLE);
																				}
																				
																				@Override
																				public void onAnimationEnd(Animator _param1) {
																						_param1.cancel();
																				}
																				
																				@Override
																				public void onAnimationCancel(Animator _param1) {
																						
																				}
																				
																				@Override
																				public void onAnimationRepeat(Animator _param1) {
																						
																				}
																		});
																		
																		setVisibleAlphaAnim.setTarget(likedByPublisherLayout);
																		setVisibleAlphaAnim.setPropertyName("alpha");
																		setVisibleAlphaAnim.setInterpolator(new LinearInterpolator());
																		setVisibleAlphaAnim.setFloatValues((float)(0), (float)(1));
																		setVisibleAlphaAnim.setDuration((int)(94));
																		setVisibleAlphaAnim.start();
																}
																like_unlike_ic.setImageResource(R.drawable.post_icons_1_2);
														}
												}
												@Override
												public void onCancelled(@NonNull DatabaseError databaseError) {
														
												}
										});
										
										getCommentsLikeCount.addListenerForSingleValueEvent(new ValueEventListener() {
												@Override
												public void onDataChange(DataSnapshot dataSnapshot) {
														long count = dataSnapshot.getChildrenCount();
														getCommentsRef.child("like").setValue(postCommentLikeCountCache.get(_data.get((int)_position).get("key").toString()).toString());
												}
												
												@Override
												public void onCancelled(DatabaseError databaseError) {
														
												}
										});
										
								}
						});
						
						profileCard.setOnClickListener(new View.OnClickListener() {
								@Override
								public void onClick(View _view) {
										intent.setClass(getContext(), ProfileActivity.class);
										intent.putExtra("uid", _data.get((int)_position).get("uid").toString());
										getContext().startActivity(intent);
								}
						});
				}
				
				@Override
				public int getItemCount() {
						return _data.size();
				}
				
				public class ViewHolder extends RecyclerView.ViewHolder {
						public ViewHolder(View _view) {
								super(_view);
						}
				}
		}
		
		private void dialogStyles() {
				{
						android.graphics.drawable.GradientDrawable SkylineUi = new android.graphics.drawable.GradientDrawable();
						int d = (int) getActivity().getResources().getDisplayMetrics().density;
						SkylineUi.setColor(0xFFFFFFFF);
						SkylineUi.setCornerRadii(new float[]{d * 14, d * 14, d * 14, d * 14, d * 0, d * 0, d * 0, d * 0});
						body.setBackground(SkylineUi);
				}
				cancel_reply_mode.setVisibility(View.GONE);
				comment_send_layout.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b) { this.setCornerRadius(a); this.setColor(b); return this; } }.getIns((int)75, 0xFFF5F5F5));
				profile_image_bg_2_x2.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b) { this.setCornerRadius(a); this.setColor(b); return this; } }.getIns((int)300, Color.TRANSPARENT));
				_viewGraphics(comment_send_button, 0xFFF50057, 0xFFAD1457, 300, 0, Color.TRANSPARENT);
				_ImageColor(comment_send_button, 0xFFFFFFFF);        
				_viewGraphics(emoji1, 0xFFFFFFFF, 0xFFEEEEEE, 300, 0, Color.TRANSPARENT);
				_viewGraphics(emoji2, 0xFFFFFFFF, 0xFFEEEEEE, 300, 0, Color.TRANSPARENT);
				_viewGraphics(emoji3, 0xFFFFFFFF, 0xFFEEEEEE, 300, 0, Color.TRANSPARENT);
				_viewGraphics(emoji4, 0xFFFFFFFF, 0xFFEEEEEE, 300, 0, Color.TRANSPARENT);
				_viewGraphics(emoji5, 0xFFFFFFFF, 0xFFEEEEEE, 300, 0, Color.TRANSPARENT);
				_viewGraphics(emoji6, 0xFFFFFFFF, 0xFFEEEEEE, 300, 0, Color.TRANSPARENT);
				_viewGraphics(emoji7, 0xFFFFFFFF, 0xFFEEEEEE, 300, 0, Color.TRANSPARENT);
		}
		
		public void _ImageColor(final ImageView _image, final int _color) {
				_image.setColorFilter(_color,PorterDuff.Mode.SRC_ATOP);
		}
		
		public void _showCommentLikedByPublisherPopup() {
				View topToastNotificationView = getLayoutInflater().inflate(R.layout.synapse_comment_got_heart_cv, null);
				final PopupWindow topToastNotificationPopup = new PopupWindow(topToastNotificationView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, false);
				final androidx.cardview.widget.CardView main = topToastNotificationView.findViewById(R.id.main);
				ObjectAnimator slideIn = new ObjectAnimator();
				slideIn.setTarget(main);
				slideIn.setPropertyName("translationY");
				slideIn.setFloatValues((float)(-500), (float)(0));
				slideIn.setInterpolator(new LinearInterpolator());
				slideIn.setDuration((int)(200));
				slideIn.start();
				
				topToastNotificationView.postDelayed(() -> {
						ObjectAnimator slideOut = new ObjectAnimator();
						slideOut.setTarget(main);
						slideOut.setPropertyName("translationY");
						slideOut.setFloatValues((float)(0), (float)(-500));
						slideOut.setInterpolator(new LinearInterpolator());
						slideOut.setDuration((int)(200));
						slideOut.start();
						topToastNotificationView.postDelayed(() -> {
								topToastNotificationPopup.dismiss();
						}, 300);
				}, 2000);
				topToastNotificationPopup.showAtLocation(rootView, Gravity.TOP, 0, 0);
		}
		
		public void _viewGraphics(final View _view, final int _onFocus, final int _onRipple, final double _radius, final double _stroke, final int _strokeColor) {
				android.graphics.drawable.GradientDrawable GG = new android.graphics.drawable.GradientDrawable();
				GG.setColor(_onFocus);
				GG.setCornerRadius((float)_radius);
				GG.setStroke((int) _stroke, _strokeColor);
				android.graphics.drawable.RippleDrawable RE = new android.graphics.drawable.RippleDrawable(new android.content.res.ColorStateList(new int[][]{new int[]{}}, new int[]{ _onRipple}), GG, null);
				_view.setBackground(RE);
		}
		
		public void _setCommentCount(final TextView _txt, final double _number) {
				if (_number < 10000) {
						_txt.setText("(" + String.valueOf((long) _number) + ")");
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
						_txt.setText("(" + decimalFormat.format(formattedNumber) + numberFormat + ")");
				}
		}
		
		public void _setCommentLikeCount(final TextView _txt, final double _number) {
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
		
		public void _setTime(final double _currentTime, final TextView _txt) {
				Calendar c1 = Calendar.getInstance();
				Calendar c2 = Calendar.getInstance();
				double tm_difference = c1.getTimeInMillis() - _currentTime;
				if (tm_difference < 60000) {
						if ((tm_difference / 1000) < 2) {
								_txt.setText("1" + " " + getResources().getString(R.string.seconds_ago));
						} else {
								_txt.setText(String.valueOf((long)(tm_difference / 1000)).concat(" " + getResources().getString(R.string.seconds_ago)));
						}
				} else {
						if (tm_difference < (60 * 60000)) {
								if ((tm_difference / 60000) < 2) {
										_txt.setText("1" + " " + getResources().getString(R.string.minutes_ago));
								} else {
										_txt.setText(String.valueOf((long)(tm_difference / 60000)).concat(" " + getResources().getString(R.string.minutes_ago)));
								}
						} else {
								if (tm_difference < (24 * (60 * 60000))) {
										if ((tm_difference / (60 * 60000)) < 2) {
												_txt.setText(String.valueOf((long)(tm_difference / (60 * 60000))).concat(" " + getResources().getString(R.string.hours_ago)));
										} else {
												_txt.setText(String.valueOf((long)(tm_difference / (60 * 60000))).concat(" " + getResources().getString(R.string.hours_ago)));
										}
								} else {
										if (tm_difference < (7 * (24 * (60 * 60000)))) {
												if ((tm_difference / (24 * (60 * 60000))) < 2) {
														_txt.setText(String.valueOf((long)(tm_difference / (24 * (60 * 60000)))).concat(" " + getResources().getString(R.string.days_ago)));
												} else {
														_txt.setText(String.valueOf((long)(tm_difference / (24 * (60 * 60000)))).concat(" " + getResources().getString(R.string.days_ago)));
												}
										} else {
												c2.setTimeInMillis((long)(_currentTime));
												_txt.setText(new SimpleDateFormat("dd-MM-yyyy").format(c2.getTime()));
										}
								}
						}
				}
		}
		
		public void _progressBarColor(final ProgressBar _progressbar, final int _color) {
				int color = _color;
				_progressbar.setIndeterminateTintList(ColorStateList.valueOf(color));
				_progressbar.setProgressTintList(ColorStateList.valueOf(color));
		}
}
