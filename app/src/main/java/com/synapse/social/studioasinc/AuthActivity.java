package com.synapse.social.studioasinc;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Vibrator;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.synapse.social.studioasinc.animations.layout.layoutshaker;
import com.synapse.social.studioasinc.animations.textview.TVeffects;

public class AuthActivity extends AppCompatActivity {

    // UI Components
    private ScrollView vscroll1;
    private LinearLayout parentLayout;
    private TVeffects aiNameTextView;
    private TVeffects aiResponseTextView_1;
    private LinearLayout mainHiddenLayout;
    private LinearLayout section2Layout;
    private LinearLayout section3Layout;
    private LinearLayout nameLayout;
    private LinearLayout animatorSupportLayout;
    private LinearLayout profileHolderLayout;
    private TextView nameFirstLetterTextView;
    private EditText usernameEditText;
    private CheckBox confirmAgeCheckBox;
    private Button continueButton;
    private TVeffects aiResponseTextView_2;
    private Button finishButton;
    private TVeffects ruleTextView1;
    private LinearLayout emailLayout;
    private TextView ask_for_email_tv;
    private EditText email_et;
    private LinearLayout passLayout;
    private TextView askForPassTV;
    private EditText pass_et;
    private Button button1;

    // System services
    private Vibrator vib;
    private SoundPool sfx;

    // Sound IDs
    private int sfxClickId;
    private int sfxSuccessId;
    private int sfxUserInputEndId;
    private int sfxErrorId;

    // Firebase
    private FirebaseAuth fauth;
    private final OnCompleteListener<AuthResult> authCreateUserListener = createAuthCreateUserListener();
    private final OnCompleteListener<AuthResult> authSignInListener = createAuthSignInListener();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.auth);
        initializeViews();
        initializeServices();
        setupWindowFlags();
        setupListeners();
        initializeFirebase();
        startIntroAnimation();
    }

    private void initializeViews() {
        vscroll1 = findViewById(R.id.vscroll1);
        parentLayout = findViewById(R.id.parentLayout);
        aiNameTextView = findViewById(R.id.aiNameTextView);
        aiResponseTextView_1 = findViewById(R.id.aiResponseTextView_1);
        mainHiddenLayout = findViewById(R.id.mainHiddenLayout);
        section2Layout = findViewById(R.id.section2Layout);
        section3Layout = findViewById(R.id.section3Layout);
        nameLayout = findViewById(R.id.nameLayout);
        animatorSupportLayout = findViewById(R.id.animatorSupportLayout);
        profileHolderLayout = findViewById(R.id.profileHolderLayout);
        nameFirstLetterTextView = findViewById(R.id.nameFirstLetterTextView);
        usernameEditText = findViewById(R.id.usernameEditText);
        confirmAgeCheckBox = findViewById(R.id.confirmAgeCheckBox);
        continueButton = findViewById(R.id.continueButton);
        aiResponseTextView_2 = findViewById(R.id.aiResponseTextView_2);
        finishButton = findViewById(R.id.finishButton);
        ruleTextView1 = findViewById(R.id.ruleTextView1);
        emailLayout = findViewById(R.id.emailLayout);
        ask_for_email_tv = findViewById(R.id.ask_for_email_tv);
        email_et = findViewById(R.id.email_et);
        passLayout = findViewById(R.id.passLayout);
        askForPassTV = findViewById(R.id.askForPassTV);
        pass_et = findViewById(R.id.pass_et);
        button1 = findViewById(R.id.button1);
    }

    private void initializeServices() {
        vib = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        // Initialize SoundPool with Builder for better control
        SoundPool.Builder builder = new SoundPool.Builder()
                .setMaxStreams(4)
                .setAudioAttributes(new android.media.AudioAttributes.Builder()
                        .setUsage(android.media.AudioAttributes.USAGE_GAME)
                        .setContentType(android.media.AudioAttributes.CONTENT_TYPE_SONIFICATION)
                        .build());
        sfx = builder.build();

        // Load sounds
        sfxClickId = sfx.load(getApplicationContext(), R.raw.sfx_scifi_click, 1);
        sfxSuccessId = sfx.load(getApplicationContext(), R.raw.success, 1);
        sfxUserInputEndId = sfx.load(getApplicationContext(), R.raw.user_input_end, 1);
        sfxErrorId = sfx.load(getApplicationContext(), R.raw.sfx_tode, 1);
    }

    private void setupWindowFlags() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window window = getWindow();
            window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, 
                          WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
    }

    private void initializeFirebase() {
        FirebaseApp.initializeApp(this);
        fauth = FirebaseAuth.getInstance();
    }

    private void setupListeners() {
        continueButton.setOnClickListener(this::handleContinueClick);
        finishButton.setOnClickListener(this::handleFinishClick);
        button1.setOnClickListener(this::handleSignUpClick);

        emailLayout.setOnClickListener(v -> email_et.requestFocus());
        passLayout.setOnClickListener(v -> pass_et.requestFocus());
    }

    private void startIntroAnimation() {
        aiNameTextView.setTotalDuration(450L);
        aiNameTextView.setFadeDuration(150L);
        aiNameTextView.startTyping("Hello, I'm Synapse AI");

        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            aiResponseTextView_1.setTotalDuration(1000L);
            aiResponseTextView_1.setFadeDuration(150L);
            aiResponseTextView_1.startTyping(
                "I'm a next generation AI built to assist you in Synapse and to be safe, accurate and secure.\n\n" +
                "I would love to get to know each other before we get started"
            );
        }, 500);

        mainHiddenLayout.postDelayed(() -> mainHiddenLayout.setVisibility(View.VISIBLE), 2000);

        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            usernameEditText.requestFocus();
            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            if (imm != null) {
                imm.showSoftInput(usernameEditText, InputMethodManager.SHOW_IMPLICIT);
            }
            animatorSupportLayout.setVisibility(View.GONE);
        }, 2500);
    }

    private void handleContinueClick(View view) {
        String username = usernameEditText.getText().toString().trim();

        if (username.isEmpty()) {
            layoutshaker.shake(nameLayout);
            vib.vibrate(100);
            sfx.play(sfxErrorId, 1.0f, 1.0f, 1, 0, 1.0f);
            return;
        }

        sfx.play(sfxClickId, 1.0f, 1.0f, 1, 0, 1.0f);
        profileHolderLayout.setVisibility(View.VISIBLE);

        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            aiResponseTextView_2.setTotalDuration(1300L);
            aiResponseTextView_2.setFadeDuration(150L);
            aiResponseTextView_2.startTyping("Okay " + username + ", we're almost there, but before that one last final process. Please I kindly request you to look at Synapse terms and conditions before using their services");
            section2Layout.setVisibility(View.VISIBLE);

            ruleTextView1.setTotalDuration(3000L);
            ruleTextView1.setFadeDuration(150L);
            ruleTextView1.startTyping("By using Synapse, you agree to follow our rules. You must be at least 13 years old to create an account. You are responsible for keeping your login information private and secure. Misuse of the platform may result in your account being restricted or removed.");
        }, 1000);

        hideKeyboard();
        usernameEditText.setEnabled(false);

        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            findViewById(R.id.linear4).setVisibility(View.VISIBLE);
        }, 2000);

        // Set first letter of username
        nameFirstLetterTextView.setText(String.valueOf(username.charAt(0)).toUpperCase());

        continueButton.setEnabled(false);
        confirmAgeCheckBox.setEnabled(false);
    }

    private void handleFinishClick(View view) {
        section2Layout.setVisibility(View.GONE);
        mainHiddenLayout.setVisibility(View.GONE);
        section3Layout.setVisibility(View.VISIBLE);

        aiNameTextView.setTotalDuration(500L);
        aiNameTextView.setFadeDuration(150L);
        aiNameTextView.startTyping("We are almost done!");

        aiResponseTextView_1.setTotalDuration(1300L);
        aiResponseTextView_1.setFadeDuration(150L);
        aiResponseTextView_1.startTyping("Okay brother, believe me... We are going to finish this boring process within a few seconds. Just like instant noodles. First, you have to...");
    }

    private void handleSignUpClick(View view) {
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

        if (shouldPlaySound) {
            sfx.play(sfxErrorId, 1.0f, 1.0f, 1, 0, 1.0f);
        }

        if (isValid) {
            fauth.createUserWithEmailAndPassword(email, pass)
                .addOnCompleteListener(this, authCreateUserListener);
        }
    }

    private OnCompleteListener<AuthResult> createAuthCreateUserListener() {
        return task -> {
            if (task.isSuccessful()) {
                handleSuccessfulRegistration();
            } else {
                handleRegistrationError(task.getException());
            }
        };
    }

    private void handleSuccessfulRegistration() {
        aiNameTextView.setTotalDuration(300L);
        aiNameTextView.setFadeDuration(150L);
        aiNameTextView.startTyping("Creating your account...");

        Intent intent = new Intent(AuthActivity.this, CompleteProfileActivity.class);
        startActivity(intent);
        finish();
    }

    private void handleRegistrationError(Exception exception) {
        if (exception == null) return;

        String errorMessage = exception.getMessage();
        if ("The email address is already in use by another account.".equals(errorMessage)) {
            handleExistingAccount();
        }
    }

    private void handleExistingAccount() {
        aiNameTextView.setTotalDuration(500L);
        aiNameTextView.setFadeDuration(150L);
        aiNameTextView.startTyping("Hey, I know you!");

        String email = email_et.getText().toString();
        String pass = pass_et.getText().toString();

        fauth.signInWithEmailAndPassword(email, pass)
            .addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    FirebaseUser user = fauth.getCurrentUser();
                    if (user != null) {
                        fetchUsername(user.getUid());
                    }
                } else {
                    showSignInError();
                }
            });
    }

    private void fetchUsername(String uid) {
        DatabaseReference usernameRef = FirebaseDatabase.getInstance().getReference()
                .child("skyline")
                .child("users")
                .child(uid)
                .child("username");

        usernameRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String username = dataSnapshot.getValue(String.class);
                if (username != null) {
                    showWelcomeMessage("You are @" + username + " right? No further steps, Let's go...");
                } else {
                    showWelcomeMessage("I recognize you! Let's go...");
                }
                navigateToHomeAfterDelay();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                showWelcomeMessage("I recognize you! Let's go...");
                navigateToHomeAfterDelay();
            }
        });
    }

    private void showWelcomeMessage(String message) {
        aiResponseTextView_1.setTotalDuration(1300L);
        aiResponseTextView_1.setFadeDuration(150L);
        aiResponseTextView_1.startTyping(message);
    }

    private void navigateToHomeAfterDelay() {
        new Handler().postDelayed(() -> {
            Intent intent = new Intent(AuthActivity.this, HomeActivity.class);
            startActivity(intent);
            finish();
        }, 2000);
    }

    private void showSignInError() {
        aiResponseTextView_1.setTotalDuration(1300L);
        aiResponseTextView_1.setFadeDuration(150L);
        aiResponseTextView_1.startTyping("Hmm, that password doesn't match. Try again?");
    }

    private OnCompleteListener<AuthResult> createAuthSignInListener() {
        return task -> {
            if (task.isSuccessful()) {
                Intent intent = new Intent(AuthActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
            }
        };
    }

    private void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null && getWindow().getDecorView().getWindowToken() != null) {
            imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0);
        }
    }

    @Override
    public void onBackPressed() {
        finishAffinity();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (sfx != null) {
            sfx.release();
            sfx = null;
        }
    }
}