package com.synapse.social.studioasinc;

import android.animation.*;
import android.animation.ObjectAnimator;
import android.view.animation.*;
import android.view.animation.LinearInterpolator;
import android.app.Dialog;
import android.app.*;
import android.app.AlertDialog;
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

public class PostMoreBottomSheetDialog extends DialogFragment {
		
		private View rootView;
		private BottomSheetDialog dialog;
		private Intent intent = new Intent();
		
		private LinearLayout body;
		private LinearLayout sldr;
		private LinearLayout copyPostText;
		private LinearLayout share;
		private LinearLayout editPost;
		private LinearLayout report;
		private LinearLayout deletePost;
		private ImageView editPostIc;
		private TextView editPostTitle;
		private ImageView deletePostIc;
		private TextView deletePostTitle;
		private ImageView copyPostTextIc;
		private TextView copyPostTextTitle;
		private ImageView shareIc;
		private TextView shareTitle;
		private ImageView reportIc;
		private TextView reportTitle;
		
		private FirebaseAuth auth;
		private DatabaseReference main = FirebaseDatabase.getInstance().getReference("skyline");
		private Calendar cc = Calendar.getInstance();
		
		private String postKey = null;
		private String postPublisherUID = null;
		private String postType = null;
		private String postImg = null;
		
		@NonNull
		@Override
		public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
				dialog = new BottomSheetDialog(requireContext(), R.style.PostCommentsBottomSheetDialogStyle);
				rootView = View.inflate(getContext(), R.layout.post_settings_cbsd, null);
				dialog.setContentView(rootView);
				dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
				
				body = rootView.findViewById(R.id.body);
				sldr = rootView.findViewById(R.id.sldr);
				copyPostText = rootView.findViewById(R.id.copyPostText);
				share = rootView.findViewById(R.id.share);
				editPost = rootView.findViewById(R.id.editPost);
				report = rootView.findViewById(R.id.report);
				deletePost = rootView.findViewById(R.id.deletePost);
				editPostIc = rootView.findViewById(R.id.editPostIc);
				editPostTitle = rootView.findViewById(R.id.editPostTitle);
				deletePostIc = rootView.findViewById(R.id.deletePostIc);
				deletePostTitle = rootView.findViewById(R.id.deletePostTitle);
				copyPostTextIc = rootView.findViewById(R.id.copyPostTextIc);
				copyPostTextTitle = rootView.findViewById(R.id.copyPostTextTitle);
				shareIc = rootView.findViewById(R.id.shareIc);
				shareTitle = rootView.findViewById(R.id.shareTitle);
				reportIc = rootView.findViewById(R.id.reportIc);
				reportTitle = rootView.findViewById(R.id.reportTitle);
				
				copyPostText.setOnClickListener(new View.OnClickListener() {
						@Override
						public void onClick(View _view) {
								
						}
				});
				
				share.setOnClickListener(new View.OnClickListener() {
						@Override
						public void onClick(View _view) {
								
						}
				});
				
				editPost.setOnClickListener(new View.OnClickListener() {
						@Override
						public void onClick(View _view) {
								
						}
				});
				
				report.setOnClickListener(new View.OnClickListener() {
						@Override
						public void onClick(View _view) {
								
						}
				});
				
				deletePost.setOnClickListener(new View.OnClickListener() {
						@Override
						public void onClick(View _view) {
								deletePostDialog(postKey);
						}
				});
				
				FirebaseApp.initializeApp(getContext());
				auth = FirebaseAuth.getInstance();
				
				Display display = getActivity().getWindowManager().getDefaultDisplay();
				int screenHeight = display.getHeight();
				int desiredHeight = screenHeight * 2 / 4;
				LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
				body.setLayoutParams(params);
				
				dialog.setOnShowListener(dialogInterface -> {
						BottomSheetDialog d = (BottomSheetDialog) dialogInterface;
						View bottomSheet = d.findViewById(com.google.android.material.R.id.design_bottom_sheet);
						BottomSheetBehavior.from(bottomSheet).setHideable(true);
						BottomSheetBehavior.from(bottomSheet).setDraggable(true);
						BottomSheetBehavior.from(bottomSheet).setState(BottomSheetBehavior.STATE_EXPANDED);
				});
				
				if (getArguments() != null) {
						postKey = getArguments().getString("postKey");
						postPublisherUID = getArguments().getString("postPublisherUID");
						postType = getArguments().getString("postType");
						postImg = getArguments().getString("postImg");
				}
				
				dialogStyles();
				
				return dialog;
		}
		
		private void dialogStyles() {
				{
						android.graphics.drawable.GradientDrawable SkylineUi = new android.graphics.drawable.GradientDrawable();
						int d = (int) getActivity().getResources().getDisplayMetrics().density;
						SkylineUi.setColor(0xFFFFFFFF);SkylineUi.setCornerRadii(new float[]{d*22,d*22,d*22 ,d*22,d*0,d*0 ,d*0,d*0});
						body.setElevation(d*1);
						body.setBackground(SkylineUi);
				}
				sldr.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b) { this.setCornerRadius(a); this.setColor(b); return this; } }.getIns((int)300, 0xFFEEEEEE));
				_viewGraphics(copyPostText, 0xFFFFFFFF, 0xFFEEEEEE, 0, 0, Color.TRANSPARENT);
				_viewGraphics(share, 0xFFFFFFFF, 0xFFEEEEEE, 0, 0, Color.TRANSPARENT);
				_viewGraphics(editPost, 0xFFFFFFFF, 0xFFEEEEEE, 0, 0, Color.TRANSPARENT);
				_viewGraphics(report, 0xFFFFFFFF, 0xFFEEEEEE, 0, 0, Color.TRANSPARENT);
				_viewGraphics(deletePost, 0xFFFFFFFF, 0xFFEEEEEE, 0, 0, Color.TRANSPARENT);
				_ImageColor(deletePostIc, 0xFFF44336);
				
				if (postType.equals("TEXT")) {
						copyPostText.setVisibility(View.VISIBLE);
				} else {
						copyPostText.setVisibility(View.GONE);
				}
				
				if (postPublisherUID.equals(FirebaseAuth.getInstance().getCurrentUser().getUid()) || FirebaseAuth.getInstance().getCurrentUser().getEmail().equals("mashikahamed0@gmail.com")) {
						share.setVisibility(View.VISIBLE);
						editPost.setVisibility(View.VISIBLE);
						report.setVisibility(View.GONE);
						deletePost.setVisibility(View.VISIBLE);
				} else {
						share.setVisibility(View.VISIBLE);
						editPost.setVisibility(View.GONE);
						report.setVisibility(View.VISIBLE);
						deletePost.setVisibility(View.GONE);
				}
		}
		
		private void deletePostDialog(String key) {
				{
						final AlertDialog NewCustomDialog = new AlertDialog.Builder(getContext()).create();
						LayoutInflater NewCustomDialogLI = getLayoutInflater();
						View NewCustomDialogCV = (View) NewCustomDialogLI.inflate(R.layout.synapse_dialog_bg_view, null);
						NewCustomDialog.setView(NewCustomDialogCV);
						NewCustomDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
						
						final TextView dialog_title = (TextView) NewCustomDialogCV.findViewById(R.id.dialog_title);
						final TextView dialog_message = (TextView) NewCustomDialogCV.findViewById(R.id.dialog_message);
						final TextView dialog_no_button = (TextView) NewCustomDialogCV.findViewById(R.id.dialog_no_button);
						final TextView dialog_yes_button = (TextView) NewCustomDialogCV.findViewById(R.id.dialog_yes_button);
						
						dialog_yes_button.setTextColor(0xFFF44336);
						_viewGraphics(dialog_yes_button, 0xFFFFFFFF, 0xFFFFCDD2, 28, 0, Color.TRANSPARENT);
						dialog_no_button.setTextColor(0xFF2196F3);
						_viewGraphics(dialog_no_button, 0xFFFFFFFF, 0xFFBBDEFB, 28, 0, Color.TRANSPARENT);
						
						dialog_title.setText(getResources().getString(R.string.info));
						dialog_message.setText(getResources().getString(R.string.delete_post_dialog_message));
						dialog_yes_button.setText(getResources().getString(R.string.yes));
						dialog_no_button.setText(getResources().getString(R.string.no));
						dialog_yes_button.setOnClickListener(new View.OnClickListener() {
								@Override
								public void onClick(View _view) {
										deletePostDatas(key);
										if (postImg != null && !postImg.isEmpty()) {
											//	_DeleteFirebaseStorage(postImg);
										}
										SketchwareUtil.showMessage(getActivity(), getResources().getString(R.string.post_deleted_toast));
										NewCustomDialog.dismiss();
										dialog.dismiss();
								}
						});
						dialog_no_button.setOnClickListener(new View.OnClickListener() {
								@Override
								public void onClick(View _view) {
										NewCustomDialog.dismiss();
								}
						});
						NewCustomDialog.setCancelable(true);
						NewCustomDialog.show();
				}
		}
		
		private void deletePostDatas(String key) {
				FirebaseDatabase.getInstance().getReference("skyline/posts").child(key).removeValue();
				FirebaseDatabase.getInstance().getReference("skyline/posts-comments").child(key).removeValue();
				FirebaseDatabase.getInstance().getReference("skyline/posts-comments-like").child(key).removeValue();
				FirebaseDatabase.getInstance().getReference("skyline/posts-likes").child(key).removeValue();
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
}
