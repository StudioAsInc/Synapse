package com.synapse.social.studioasinc;

import android.app.AlarmManager;
import android.app.Application;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Process;
import android.util.Log;
import androidx.annotation.NonNull;

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

import java.io.*;
import java.text.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.*;
import org.json.*;

public class SynapseApp extends Application {
	
	private static Context mContext;
	private Thread.UncaughtExceptionHandler mExceptionHandler;
	
	public static FirebaseAuth mAuth;
	
	public static DatabaseReference getCheckUserReference;
	public static DatabaseReference setUserStatusRef;
    public static DatabaseReference setUserStatusReference;
    
    public static Calendar mCalendar;
	
	public static Context getContext() {
		return mContext;
	}
	
	@Override
	public void onCreate() {
		mContext = this;
		this.mExceptionHandler = Thread.getDefaultUncaughtExceptionHandler();
        this.mCalendar = Calendar.getInstance();
		FirebaseApp.initializeApp(this);
		
		this.mAuth = FirebaseAuth.getInstance();
		this.getCheckUserReference = FirebaseDatabase.getInstance().getReference().child("skyline/users");
		this.setUserStatusRef = FirebaseDatabase.getInstance().getReference(".info/connected");
		
		Thread.setDefaultUncaughtExceptionHandler(
		new Thread.UncaughtExceptionHandler() {
			@Override
			public void uncaughtException(Thread mThread, Throwable mThrowable) {
				Intent mIntent = new Intent(mContext, DebugActivity.class);
				mIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				mIntent.putExtra("error", Log.getStackTraceString(mThrowable));
				mContext.startActivity(mIntent);
				mExceptionHandler.uncaughtException(mThread, mThrowable);
			}
		});
		
		setUserStatus();
		super.onCreate();
	}
	
	public static void setUserStatus() {
		if (mAuth.getCurrentUser() != null) {
            setUserStatusReference = FirebaseDatabase.getInstance().getReference("skyline/users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("status");
			getCheckUserReference.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
				@Override
				public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
					if(dataSnapshot.exists()) {
						setUserStatusRef.addValueEventListener(new ValueEventListener() {
							@Override
							public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
								boolean connected = dataSnapshot.getValue(Boolean.class);
								if (connected) {
									setUserStatusReference.setValue("online");
									setUserStatusReference.onDisconnect().setValue(String.valueOf((long)(mCalendar.getTimeInMillis())));
								}
							}
							@Override
							public void onCancelled(@NonNull DatabaseError databaseError) {
								
							}
						});
					}
				}
				
				@Override
				public void onCancelled(@NonNull DatabaseError databaseError) {
					
				}
			});
		}
	}
	
	public static void setUserStatusOnline() {
		if (mAuth.getCurrentUser() != null) {
			FirebaseDatabase.getInstance().getReference("skyline/users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("status").setValue("online");
		}
	}
	
	public static void setUserStatusOffline() {
		if (mAuth.getCurrentUser() != null) {
			FirebaseDatabase.getInstance().getReference("skyline/users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("status").setValue(String.valueOf((long)(mCalendar.getTimeInMillis())));
		}
	}
    
    public static void setUserStatusOfflineListenerCancel() {
        setUserStatusReference.onDisconnect().cancel();
    }
}