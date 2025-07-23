package com.synapse.social.studioasinc;

import android.animation.*;
import android.app.*;
import android.content.*;
import android.content.Context;
import android.content.res.*;
import android.graphics.*;
import android.graphics.drawable.*;
import android.media.*;
import android.media.SoundPool;
import android.net.*;
import android.os.*;
import android.os.Vibrator;
import android.text.*;
import android.text.style.*;
import android.util.*;
import android.view.*;
import android.view.View;
import android.view.View.*;
import android.view.animation.*;
import android.webkit.*;
import android.widget.*;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import androidx.annotation.*;
import androidx.appcompat.app.AppCompatActivity;
import androidx.browser.*;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import com.bumptech.glide.*;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.*;
import com.google.android.material.color.MaterialColors;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.synapse.social.studioasinc.animations.textview.TVeffects;
import com.theartofdev.edmodo.cropper.*;
import com.yalantis.ucrop.*;
import java.io.*;
import java.text.*;
import java.util.*;
import java.util.regex.*;
import org.json.*;
import android.view.inputmethod.InputMethodManager;
import com.synapse.social.studioasinc.animations.layout.layoutshaker;
import com.synapse.social.studioasinc.audio.SoundEffectPlayer;
import androidx.core.content.res.ResourcesCompat;

//Firebase
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

public class AuthActivity extends AppCompatActivity {
	
	private double s1 = 0;
	private double s2 = 0;
	private double s3 = 0;
	private double s4 = 0;
	private String username = "";
	
	private ScrollView vscroll1;
	private LinearLayout parentLayout;
	private TVeffects aiNameTextView;
	private TVeffects aiResponseTextView_1;
	private LinearLayout mainHiddenLayout;
	private LinearLayout section2Layout;
	private LinearLayout section3Layout;
	private LinearLayout nameLayout;
	private LinearLayout animatorSupportLayout;
	private LinearLayout linear2;
	private LinearLayout profileSeparatorLayout;
	private LinearLayout profileHolderLayout;
	private LinearLayout nameLayoutHolderLayout;
	private LinearLayout latterProfilePictureLayoutCircle;
	private TextView nameFirstLetterTextView;
	private TextView userAutoResponseTextView_1;
	private LinearLayout EditTextContainerLayout;
	private EditText usernameEditText;
	private LinearLayout ageCheckboxLayout;
	private LinearLayout continueBtnHolderLayout;
	private TextView infoTextView_1;
	private CheckBox confirmAgeCheckBox;
	private Button continueButton;
	private TVeffects aiResponseTextView_2;
	private LinearLayout linear4;
	private Button finishButton;
	private LinearLayout ruleLayout1;
	private LinearLayout ruleLayout2;
	private LinearLayout ruleLayout3;
	private LinearLayout linear6;
	private LinearLayout linear7;
	private ImageView ic_rule_1;
	private TVeffects ruleTextView1;
	private LinearLayout linear9;
	private LinearLayout linear10;
	private ImageView ic_rule_2;
	private TVeffects ruleTextView2;
	private LinearLayout linear12;
	private LinearLayout linear13;
	private ImageView ic_rule_3;
	private TVeffects ruleTextView3;
	private LinearLayout emailLayout;
	private LinearLayout linear17;
	private LinearLayout linear18;
	private LinearLayout linear19;
	private LinearLayout linear20;
	private LinearLayout linear21;
	private TextView textview1;
	private TextView ask_for_email_tv;
	private LinearLayout linear22;
	private EditText email_et;
	private LinearLayout passLayout;
	private LinearLayout linear24;
	private LinearLayout linear56;
	private LinearLayout linear57;
	private LinearLayout linear58;
	private LinearLayout linear59;
	private TextView textview14;
	private TextView askForPassTV;
	private LinearLayout linear60;
	private EditText pass_et;
	private Button button1;
	
	private Vibrator vib;
	private SoundPool sfx;
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
	
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.auth);
		initialize(_savedInstanceState);
		FirebaseApp.initializeApp(this);
		initializeLogic();
	}
	
	private void initialize(Bundle _savedInstanceState) {
		vscroll1 = findViewById(R.id.vscroll1);
		parentLayout = findViewById(R.id.parentLayout);
		aiNameTextView = findViewById(R.id.aiNameTextView);
		aiResponseTextView_1 = findViewById(R.id.aiResponseTextView_1);
		mainHiddenLayout = findViewById(R.id.mainHiddenLayout);
		section2Layout = findViewById(R.id.section2Layout);
		section3Layout = findViewById(R.id.section3Layout);
		nameLayout = findViewById(R.id.nameLayout);
		animatorSupportLayout = findViewById(R.id.animatorSupportLayout);
		linear2 = findViewById(R.id.linear2);
		profileSeparatorLayout = findViewById(R.id.profileSeparatorLayout);
		profileHolderLayout = findViewById(R.id.profileHolderLayout);
		nameLayoutHolderLayout = findViewById(R.id.nameLayoutHolderLayout);
		latterProfilePictureLayoutCircle = findViewById(R.id.latterProfilePictureLayoutCircle);
		nameFirstLetterTextView = findViewById(R.id.nameFirstLetterTextView);
		userAutoResponseTextView_1 = findViewById(R.id.userAutoResponseTextView_1);
		EditTextContainerLayout = findViewById(R.id.EditTextContainerLayout);
		usernameEditText = findViewById(R.id.usernameEditText);
		ageCheckboxLayout = findViewById(R.id.ageCheckboxLayout);
		continueBtnHolderLayout = findViewById(R.id.continueBtnHolderLayout);
		infoTextView_1 = findViewById(R.id.infoTextView_1);
		confirmAgeCheckBox = findViewById(R.id.confirmAgeCheckBox);
		continueButton = findViewById(R.id.continueButton);
		aiResponseTextView_2 = findViewById(R.id.aiResponseTextView_2);
		linear4 = findViewById(R.id.linear4);
		finishButton = findViewById(R.id.finishButton);
		ruleLayout1 = findViewById(R.id.ruleLayout1);
		ruleLayout2 = findViewById(R.id.ruleLayout2);
		ruleLayout3 = findViewById(R.id.ruleLayout3);
		linear6 = findViewById(R.id.linear6);
		linear7 = findViewById(R.id.linear7);
		ic_rule_1 = findViewById(R.id.ic_rule_1);
		ruleTextView1 = findViewById(R.id.ruleTextView1);
		linear9 = findViewById(R.id.linear9);
		linear10 = findViewById(R.id.linear10);
		ic_rule_2 = findViewById(R.id.ic_rule_2);
		ruleTextView2 = findViewById(R.id.ruleTextView2);
		linear12 = findViewById(R.id.linear12);
		linear13 = findViewById(R.id.linear13);
		ic_rule_3 = findViewById(R.id.ic_rule_3);
		ruleTextView3 = findViewById(R.id.ruleTextView3);
		emailLayout = findViewById(R.id.emailLayout);
		linear17 = findViewById(R.id.linear17);
		linear18 = findViewById(R.id.linear18);
		linear19 = findViewById(R.id.linear19);
		linear20 = findViewById(R.id.linear20);
		linear21 = findViewById(R.id.linear21);
		textview1 = findViewById(R.id.textview1);
		ask_for_email_tv = findViewById(R.id.ask_for_email_tv);
		linear22 = findViewById(R.id.linear22);
		email_et = findViewById(R.id.email_et);
		passLayout = findViewById(R.id.passLayout);
		linear24 = findViewById(R.id.linear24);
		linear56 = findViewById(R.id.linear56);
		linear57 = findViewById(R.id.linear57);
		linear58 = findViewById(R.id.linear58);
		linear59 = findViewById(R.id.linear59);
		textview14 = findViewById(R.id.textview14);
		askForPassTV = findViewById(R.id.askForPassTV);
		linear60 = findViewById(R.id.linear60);
		pass_et = findViewById(R.id.pass_et);
		button1 = findViewById(R.id.button1);
		vib = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
		fauth = FirebaseAuth.getInstance();
		
		continueButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				if (!usernameEditText.getText().toString().equals("")) {
					s1 = sfx.play((int)(1), 1.0f, 1.0f, 1, (int)(0), 1.0f);;
					profileHolderLayout.setVisibility(View.VISIBLE);
					new Handler(Looper.getMainLooper()).postDelayed(() -> {
						aiResponseTextView_2.setTotalDuration(1300L);
						aiResponseTextView_2.setFadeDuration(150L);
						aiResponseTextView_2.startTyping("Okay " + usernameEditText.getText().toString().trim() + ", we're almost there, but before that one last final process. Please I kindly request you to look at Synapse terms and conditions before using their services");
						section2Layout.setVisibility(View.VISIBLE);
						
						ruleTextView1.setTotalDuration(3000L);
						ruleTextView1.setFadeDuration(150L);
						ruleTextView1.startTyping("By using Synapse, you agree to follow our rules. You must be at least 13 years old to create an account. You are responsible for keeping your login information private and secure. Misuse of the platform may result in your account being restricted or removed.");
					}, 1000);
					InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
					imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0);
					usernameEditText.setEnabled(false);
					new Handler(Looper.getMainLooper()).postDelayed(() -> {
						linear4.setVisibility(View.VISIBLE);
					}, 2000);
					EditText usernameEditText = findViewById(R.id.usernameEditText);
					TextView nameFirstLetterTextView = findViewById(R.id.nameFirstLetterTextView);
					
					// Get the text and check it's not empty
					String username = usernameEditText.getText().toString().trim();
					if (!username.isEmpty()) {
						String firstLetter = String.valueOf(username.charAt(0)).toUpperCase(); // Optional: make it uppercase
						nameFirstLetterTextView.setText(firstLetter);
					}
					continueButton.setEnabled(false);
					confirmAgeCheckBox.setEnabled(false);
				} else {
					layoutshaker.shake(nameLayout);
					vib.vibrate((long)(100));
					s4 = sfx.play((int)(4), 1.0f, 1.0f, 1, (int)(0), 1.0f);;
				}
			}
		});
		
		finishButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				section2Layout.setVisibility(View.GONE);
				mainHiddenLayout.setVisibility(View.GONE);
				section3Layout.setVisibility(View.VISIBLE);
				aiNameTextView.setTotalDuration(500L);
				aiNameTextView.setFadeDuration(150L);
				aiNameTextView.startTyping("We are almost done!");
				aiResponseTextView_1.setTotalDuration(1300L);
				aiResponseTextView_1.setFadeDuration(150L);
				aiResponseTextView_1.startTyping("Okay brother, belive me... We are going to finish this boring process within a few seconds. Just like instant noodles. First, you have to...");
			}
		});
		
		button1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				String email = email_et.getText().toString().trim();
				String pass = pass_et.getText().toString().trim();
				
				boolean isValid = true;
				boolean shouldPlaySound = false;
				
				// Email Validation
				if (email.isEmpty() || email.length() < 10 || !email.contains("@")) {
					layoutshaker.shake(emailLayout);
					isValid = false;
					shouldPlaySound = true;
				}
				
				// Password Validation
				if (pass.isEmpty()) {
					layoutshaker.shake(passLayout);
					isValid = false;
					shouldPlaySound = true;
				}
				
				// Play sound only once if any input is invalid
				if (shouldPlaySound) {
					s4 = sfx.play((int)(4), 1.0f, 1.0f, 1, 0, 1.0f);
				}
				
				// If all valid
				if (isValid) {
					// Proceed to next step
					fauth.createUserWithEmailAndPassword(email_et.getText().toString(), pass_et.getText().toString()).addOnCompleteListener(AuthActivity.this, _fauth_create_user_listener);
				}
			}
		});
		
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
				if (_success) {
					aiNameTextView.setTotalDuration(300L);
					aiNameTextView.setFadeDuration(150L);
					aiNameTextView.startTyping("Creating your account...");
					
					// Registration successful, go to HomeActivity
					Intent intent = new Intent(AuthActivity.this, CompleteProfileActivity.class);
					startActivity(intent);
					finish();
				} else {
					if (_errorMessage.equals("The email address is already in use by another account.")) {
						aiNameTextView.setTotalDuration(500L);
						aiNameTextView.setFadeDuration(150L);
						aiNameTextView.startTyping("Hey, I know you!");
						
						// First try to sign in with the provided credentials
						fauth.signInWithEmailAndPassword(
						email_et.getText().toString(),
						pass_et.getText().toString()
						).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
							@Override
							public void onComplete(@NonNull Task<AuthResult> task) {
								if (task.isSuccessful()) {
									// Now we have a current user, so we can get the UID safely
									FirebaseUser user = fauth.getCurrentUser();
									if (user != null) {
										String uid = user.getUid();
										
										// Reference to the username in Firebase
										DatabaseReference usernameRef = FirebaseDatabase.getInstance().getReference()
										.child("skyline")
										.child("users")
										.child(uid)
										.child("username");
										
										usernameRef.addListenerForSingleValueEvent(new ValueEventListener() {
											@Override
											public void onDataChange(DataSnapshot dataSnapshot) {
												String username = dataSnapshot.getValue(String.class);
												if (username != null) {
													aiResponseTextView_1.setTotalDuration(1300L);
													aiResponseTextView_1.setFadeDuration(150L);
													aiResponseTextView_1.startTyping("You are @" + username + " right? No further steps, Let's go...");
												} else {
													// Fallback if username doesn't exist
													aiResponseTextView_1.setTotalDuration(1300L);
													aiResponseTextView_1.setFadeDuration(150L);
													aiResponseTextView_1.startTyping("I recognize you! Let's go...");
												}
												
												// Proceed to HomeActivity after delay
												new Handler().postDelayed(() -> {
													Intent intent = new Intent(AuthActivity.this, HomeActivity.class);
													startActivity(intent);
													finish();
												}, 2000);
											}
											
											@Override
											public void onCancelled(DatabaseError databaseError) {
												// Handle possible errors
												aiResponseTextView_1.setTotalDuration(1300L);
												aiResponseTextView_1.setFadeDuration(150L);
												aiResponseTextView_1.startTyping("I recognize you! Let's go...");
												
												// Proceed to HomeActivity after delay
												new Handler().postDelayed(() -> {
													Intent intent = new Intent(AuthActivity.this, HomeActivity.class);
													startActivity(intent);
													finish();
												}, 2000);
											}
										});
									}
								} else {
									// Sign in failed, show error message
									aiResponseTextView_1.setTotalDuration(1300L);
									aiResponseTextView_1.setFadeDuration(150L);
									aiResponseTextView_1.startTyping("Hmm, that password doesn't match. Try again?");
								}
							}
						});
					}
				}
			}
		};
		
		_fauth_sign_in_listener = new OnCompleteListener<AuthResult>() {
			@Override
			public void onComplete(Task<AuthResult> _param1) {
				final boolean _success = _param1.isSuccessful();
				final String _errorMessage = _param1.getException() != null ? _param1.getException().getMessage() : "";
				if (_success) {
					Intent intent = new Intent(AuthActivity.this, HomeActivity.class);
					startActivity(intent);
					finish();
				} else {
					
				}
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
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) { Window w = getWindow();  w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS); };
		// Initial AI introduction
		aiNameTextView.setTotalDuration(450L);
		aiNameTextView.setFadeDuration(150L);
		aiNameTextView.startTyping("Hello, I'm Synapse AI");
		
		// First AI response after a short delay
		new Handler(Looper.getMainLooper()).postDelayed(() -> {
			aiResponseTextView_1.setTotalDuration(1000L);
			aiResponseTextView_1.setFadeDuration(150L);
			aiResponseTextView_1.startTyping("I'm a next generation AI built to assist you in Synapse and to be safe, accurate and secure.\n\nI would love to get to know each other before we get started");
		}, 500);
		
		// Show hidden layout after typing animations are likely complete
		mainHiddenLayout.postDelayed(() -> mainHiddenLayout.setVisibility(View.VISIBLE), 2000);
		
		// Calculate total animation time (500ms delay + 1000ms typing + buffer)
		long totalAnimationTime = 2500; // Adjusted from original 3100ms to match actual delays
		
		// Final actions after all animations complete
		new Handler(Looper.getMainLooper()).postDelayed(() -> {
			usernameEditText.requestFocus();
			// Show keyboard
			InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
			if (imm != null) {
				imm.showSoftInput(usernameEditText, InputMethodManager.SHOW_IMPLICIT);
			}
			animatorSupportLayout.setVisibility(View.GONE);
		}, totalAnimationTime);
		sfx = new SoundPool((int)(4), AudioManager.STREAM_MUSIC, 0);
		s1 = sfx.load(getApplicationContext(), R.raw.sfx_scifi_click, 1);;
		s2 = sfx.load(getApplicationContext(), R.raw.success, 1);;
		s3 = sfx.load(getApplicationContext(), R.raw.user_input_end, 1);;
		s4 = sfx.load(getApplicationContext(), R.raw.sfx_tode, 1);;
		emailLayout.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				email_et.requestFocus();
			}
		});
		passLayout.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				pass_et.requestFocus();
			}
		});
	}
	
	
	@Override
	public void onBackPressed() {
		finishAffinity();
	}
}