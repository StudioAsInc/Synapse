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

public class UserProfileMoreBottomSheet extends DialogFragment {
	
	private View rootView;
	private BottomSheetDialog dialog;
	private Intent intent = new Intent();
	
	private LinearLayout body;
	private LinearLayout sldr;
	private LinearLayout settings;
	private LinearLayout copy_link;
	private LinearLayout block;
	private LinearLayout report;
	private ImageView settings_ic;
	private TextView settings_title;
	private ImageView copy_link_ic;
	private TextView copy_link_title;
	private ImageView block_ic;
	private TextView block_title;
	private ImageView report_ic;
	private TextView report_title;
	
	private FirebaseAuth auth;
	private DatabaseReference main = FirebaseDatabase.getInstance().getReference("skyline");
	private Calendar cc = Calendar.getInstance();
	
	private String userUID = null;
	
	@NonNull
	@Override
	public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
		dialog = new BottomSheetDialog(requireContext(), R.style.PostCommentsBottomSheetDialogStyle);
		rootView = View.inflate(getContext(), R.layout.more_btn_profile_cbsd, null);
		dialog.setContentView(rootView);
		dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
		
		body = rootView.findViewById(R.id.body);
		sldr = rootView.findViewById(R.id.sldr);
		settings = rootView.findViewById(R.id.settings);
		copy_link = rootView.findViewById(R.id.copy_link);
		block = rootView.findViewById(R.id.block);
		report = rootView.findViewById(R.id.report);
		settings_ic = rootView.findViewById(R.id.settings_ic);
		settings_title = rootView.findViewById(R.id.settings_title);
		copy_link_ic = rootView.findViewById(R.id.copy_link_ic);
		copy_link_title = rootView.findViewById(R.id.copy_link_title);
		block_ic = rootView.findViewById(R.id.block_ic);
		block_title = rootView.findViewById(R.id.block_title);
		report_ic = rootView.findViewById(R.id.report_ic);
		report_title = rootView.findViewById(R.id.report_title);
		
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
			userUID = getArguments().getString("userUID");
		}
		
		dialogStyles();
        
        settings.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
			//	intent.setClass(getActivity(), SettingsActivity.class);
			//	startActivity(intent);
                dismiss();
			}
		});
		
		return dialog;
	}
	
	private void dialogStyles() {
		{
			android.graphics.drawable.GradientDrawable SkylineUi = new android.graphics.drawable.GradientDrawable();
			int d = (int) getActivity().getResources().getDisplayMetrics().density;
			SkylineUi.setColor(0xFFFFFFFF);
            SkylineUi.setCornerRadii(new float[]{d*22,d*22,d*22 ,d*22,d*0,d*0 ,d*0,d*0});
			body.setElevation(d*1);
			body.setBackground(SkylineUi);
		}
		sldr.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b) { this.setCornerRadius(a); this.setColor(b); return this; } }.getIns((int)300, 0xFFEEEEEE));
		_viewGraphics(settings, 0xFFFFFFFF, 0xFFEEEEEE, 0, 0, Color.TRANSPARENT);
		_viewGraphics(copy_link, 0xFFFFFFFF, 0xFFEEEEEE, 0, 0, Color.TRANSPARENT);
		_viewGraphics(block, 0xFFFFFFFF, 0xFFEEEEEE, 0, 0, Color.TRANSPARENT);
		_viewGraphics(report, 0xFFFFFFFF, 0xFFEEEEEE, 0, 0, Color.TRANSPARENT);
		_ImageColor(block_ic, 0xFFF44336);
		_ImageColor(report_ic, 0xFFF44336);        
		
		if (userUID.equals(FirebaseAuth.getInstance().getCurrentUser().getUid())) {
			settings.setVisibility(View.VISIBLE);
			copy_link.setVisibility(View.VISIBLE);
			block.setVisibility(View.GONE);
			report.setVisibility(View.GONE);
		} else {
			settings.setVisibility(View.GONE);
			copy_link.setVisibility(View.VISIBLE);
			block.setVisibility(View.VISIBLE);
			report.setVisibility(View.VISIBLE);
		}
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
