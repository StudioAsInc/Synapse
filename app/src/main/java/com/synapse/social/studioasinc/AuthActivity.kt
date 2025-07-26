package com.synapse.social.studioasinc

import android.content.Context
import android.content.Intent
import android.media.AudioAttributes
import android.media.SoundPool
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Vibrator
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.synapse.social.studioasinc.animations.layout.layoutshaker
import com.synapse.social.studioasinc.animations.textview.TVeffects

class AuthActivity : AppCompatActivity() {

    // UI Components
    private lateinit var vscroll1: ScrollView
    private lateinit var parentLayout: LinearLayout
    private lateinit var aiNameTextView: TVeffects
    private lateinit var aiResponseTextView_1: TVeffects
    private lateinit var mainHiddenLayout: LinearLayout
    private lateinit var section2Layout: LinearLayout
    private lateinit var section3Layout: LinearLayout
    private lateinit var nameLayout: LinearLayout
    private lateinit var animatorSupportLayout: LinearLayout
    private lateinit var profileHolderLayout: LinearLayout
    private lateinit var nameFirstLetterTextView: TextView
    private lateinit var usernameEditText: EditText
    private lateinit var confirmAgeCheckBox: CheckBox
    private lateinit var continueButton: Button
    private lateinit var aiResponseTextView_2: TVeffects
    private lateinit var finishButton: Button
    private lateinit var ruleTextView1: TVeffects
    private lateinit var emailLayout: LinearLayout
    private lateinit var ask_for_email_tv: TextView
    private lateinit var email_et: EditText
    private lateinit var passLayout: LinearLayout
    private lateinit var askForPassTV: TextView
    private lateinit var pass_et: EditText
    private lateinit var button1: Button

    // System services
    private lateinit var vib: Vibrator
    private lateinit var sfx: SoundPool

    // Sound IDs
    private var sfxClickId: Int = 0
    private var sfxSuccessId: Int = 0
    private var sfxUserInputEndId: Int = 0
    private var sfxErrorId: Int = 0

    // Firebase
    private lateinit var fauth: FirebaseAuth
    private val authCreateUserListener = createAuthCreateUserListener()
    private val authSignInListener = createAuthSignInListener()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.auth)
        initializeViews()
        initializeServices()
        setupWindowFlags()
        initializeFirebase() // Initialize Firebase before setting up listeners that might use it
        setupListeners()
        startIntroAnimation()
    }

    private fun initializeViews() {
        vscroll1 = findViewById(R.id.vscroll1)
        parentLayout = findViewById(R.id.parentLayout)
        aiNameTextView = findViewById(R.id.aiNameTextView)
        aiResponseTextView_1 = findViewById(R.id.aiResponseTextView_1)
        mainHiddenLayout = findViewById(R.id.mainHiddenLayout)
        section2Layout = findViewById(R.id.section2Layout)
        section3Layout = findViewById(R.id.section3Layout)
        nameLayout = findViewById(R.id.nameLayout)
        animatorSupportLayout = findViewById(R.id.animatorSupportLayout)
        profileHolderLayout = findViewById(R.id.profileHolderLayout)
        nameFirstLetterTextView = findViewById(R.id.nameFirstLetterTextView)
        usernameEditText = findViewById(R.id.usernameEditText)
        confirmAgeCheckBox = findViewById(R.id.confirmAgeCheckBox)
        continueButton = findViewById(R.id.continueButton)
        aiResponseTextView_2 = findViewById(R.id.aiResponseTextView_2)
        finishButton = findViewById(R.id.finishButton)
        ruleTextView1 = findViewById(R.id.ruleTextView1)
        emailLayout = findViewById(R.id.emailLayout)
        ask_for_email_tv = findViewById(R.id.ask_for_email_tv)
        email_et = findViewById(R.id.email_et)
        passLayout = findViewById(R.id.passLayout)
        askForPassTV = findViewById(R.id.askForPassTV)
        pass_et = findViewById(R.id.pass_et)
        button1 = findViewById(R.id.button1)
    }

    private fun initializeServices() {
        vib = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator

        // Initialize SoundPool with Builder for better control
        val builder = SoundPool.Builder()
            .setMaxStreams(4)
            .setAudioAttributes(
                AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_GAME)
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .build()
            )
        sfx = builder.build()

        // Load sounds
        sfxClickId = sfx.load(applicationContext, R.raw.sfx_scifi_click, 1)
        sfxSuccessId = sfx.load(applicationContext, R.raw.success, 1)
        sfxUserInputEndId = sfx.load(applicationContext, R.raw.user_input_end, 1)
        sfxErrorId = sfx.load(applicationContext, R.raw.sfx_tode, 1)
    }

    private fun setupWindowFlags() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            val window = window
            window.setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
            )
        }
    }

    private fun initializeFirebase() {
        FirebaseApp.initializeApp(this)
        fauth = FirebaseAuth.getInstance()
    }

    private fun setupListeners() {
        continueButton.setOnClickListener(this::handleContinueClick)
        finishButton.setOnClickListener(this::handleFinishClick)
        button1.setOnClickListener(this::handleSignUpClick)

        emailLayout.setOnClickListener { email_et.requestFocus() }
        passLayout.setOnClickListener { pass_et.requestFocus() }
    }

    private fun startIntroAnimation() {
        // Use setter methods instead of direct property access
        aiNameTextView.setTotalDuration(450L)
        aiNameTextView.setFadeDuration(150L)
        aiNameTextView.startTyping("Hello, I'm Synapse AI")

        Handler(Looper.getMainLooper()).postDelayed({
            // Use setter methods instead of direct property access
            aiResponseTextView_1.setTotalDuration(1000L)
            aiResponseTextView_1.setFadeDuration(150L)
            aiResponseTextView_1.startTyping(
                "I'm a next generation AI built to assist you in Synapse and to be safe, accurate and secure.\n\n" +
                        "I would love to get to know each other before we get started"
            )
        }, 500)

        mainHiddenLayout.postDelayed({ mainHiddenLayout.visibility = View.VISIBLE }, 2000)

        Handler(Looper.getMainLooper()).postDelayed({
            usernameEditText.requestFocus()
            val imm = getSystemService(INPUT_METHOD_SERVICE) as? InputMethodManager
            imm?.showSoftInput(usernameEditText, InputMethodManager.SHOW_IMPLICIT)
            animatorSupportLayout.visibility = View.GONE
        }, 2500)
    }

    private fun handleContinueClick(view: View) {
        val username = usernameEditText.text.toString().trim()

        if (username.isEmpty()) {
            layoutshaker.shake(nameLayout)
            vib.vibrate(100)
            sfx.play(sfxErrorId, 1.0f, 1.0f, 1, 0, 1.0f)
            return
        }

        sfx.play(sfxClickId, 1.0f, 1.0f, 1, 0, 1.0f)
        profileHolderLayout.visibility = View.VISIBLE

        Handler(Looper.getMainLooper()).postDelayed({
            // Use setter methods instead of direct property access
            aiResponseTextView_2.setTotalDuration(1300L)
            aiResponseTextView_2.setFadeDuration(150L)
            aiResponseTextView_2.startTyping("Okay $username, we're almost there, but before that one last final process. Please I kindly request you to look at Synapse terms and conditions before using their services")
            section2Layout.visibility = View.VISIBLE

            // Use setter methods instead of direct property access
            ruleTextView1.setTotalDuration(3000L)
            ruleTextView1.setFadeDuration(150L)
            ruleTextView1.startTyping("By using Synapse, you agree to follow our rules. You must be at least 13 years old to create an account. You are responsible for keeping your login information private and secure. Misuse of the platform may result in your account being restricted or removed.")
        }, 1000)

        hideKeyboard()
        usernameEditText.isEnabled = false

        Handler(Looper.getMainLooper()).postDelayed({
            findViewById<LinearLayout>(R.id.linear4).visibility = View.VISIBLE
        }, 2000)

        // Set first letter of username
        nameFirstLetterTextView.text = username.firstOrNull()?.toUpperCase()?.toString() ?: ""

        continueButton.isEnabled = false
        confirmAgeCheckBox.isEnabled = false
    }

    private fun handleFinishClick(view: View) {
        section2Layout.visibility = View.GONE
        mainHiddenLayout.visibility = View.GONE
        section3Layout.visibility = View.VISIBLE

        // Use setter methods instead of direct property access
        aiNameTextView.setTotalDuration(500L)
        aiNameTextView.setFadeDuration(150L)
        aiNameTextView.startTyping("We are almost done!")

        // Use setter methods instead of direct property access
        aiResponseTextView_1.setTotalDuration(1300L)
        aiResponseTextView_1.setFadeDuration(150L)
        aiResponseTextView_1.startTyping("Okay brother, believe me... We are going to finish this boring process within a few seconds. Just like instant noodles. First, you have to...")
    }

    private fun handleSignUpClick(view: View) {
        val email = email_et.text.toString().trim()
        val pass = pass_et.text.toString().trim()

        var isValid = true
        var shouldPlaySound = false

        // Email Validation
        if (email.isEmpty() || email.length < 10 || !email.contains("@")) {
            layoutshaker.shake(emailLayout)
            isValid = false
            shouldPlaySound = true
        }

        // Password Validation
        if (pass.isEmpty()) {
            layoutshaker.shake(passLayout)
            isValid = false
            shouldPlaySound = true
        }

        if (shouldPlaySound) {
            sfx.play(sfxErrorId, 1.0f, 1.0f, 1, 0, 1.0f)
        }

        if (isValid) {
            fauth.createUserWithEmailAndPassword(email, pass)
                .addOnCompleteListener(this, authCreateUserListener)
        }
    }

    private fun createAuthCreateUserListener(): OnCompleteListener<AuthResult> {
        return OnCompleteListener { task ->
            if (task.isSuccessful) {
                handleSuccessfulRegistration()
            } else {
                handleRegistrationError(task.exception)
            }
        }
    }

    private fun handleSuccessfulRegistration() {
        // Use setter methods instead of direct property access
        aiNameTextView.setTotalDuration(300L)
        aiNameTextView.setFadeDuration(150L)
        aiNameTextView.startTyping("Creating your account...")

        val intent = Intent(this@AuthActivity, CompleteProfileActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun handleRegistrationError(exception: Exception?) {
        if (exception == null) return

        val errorMessage = exception.message
        if ("The email address is already in use by another account." == errorMessage) {
            handleExistingAccount()
        }
        // You might want to add a Toast or log other error messages here for debugging/user feedback
    }

    private fun handleExistingAccount() {
        // Use setter methods instead of direct property access
        aiNameTextView.setTotalDuration(500L)
        aiNameTextView.setFadeDuration(150L)
        aiNameTextView.startTyping("Hey, I know you!")

        val email = email_et.text.toString()
        val pass = pass_et.text.toString()

        fauth.signInWithEmailAndPassword(email, pass)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val user = fauth.currentUser
                    if (user != null) {
                        fetchUsername(user.uid)
                    }
                } else {
                    showSignInError()
                }
            }
    }

    private fun fetchUsername(uid: String) {
        val usernameRef = FirebaseDatabase.getInstance().getReference()
            .child("skyline")
            .child("users")
            .child(uid)
            .child("username")

        usernameRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val username = dataSnapshot.getValue(String::class.java)
                if (username != null) {
                    showWelcomeMessage("You are @$username right? No further steps, Let's go...")
                } else {
                    showWelcomeMessage("I recognize you! Let's go...")
                }
                navigateToHomeAfterDelay()
            }

            override fun onCancelled(databaseError: DatabaseError) {
                showWelcomeMessage("I recognize you! Let's go...")
                navigateToHomeAfterDelay()
            }
        })
    }

    private fun showWelcomeMessage(message: String) {
        // Use setter methods instead of direct property access
        aiResponseTextView_1.setTotalDuration(1300L)
        aiResponseTextView_1.setFadeDuration(150L)
        aiResponseTextView_1.startTyping(message)
    }

    private fun navigateToHomeAfterDelay() {
        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this@AuthActivity, HomeActivity::class.java)
            startActivity(intent)
            finish()
        }, 2000)
    }

    private fun showSignInError() {
        // Use setter methods instead of direct property access
        aiResponseTextView_1.setTotalDuration(1300L)
        aiResponseTextView_1.setFadeDuration(150L)
        aiResponseTextView_1.startTyping("Hmm, that password doesn't match. Try again?")
    }

    private fun createAuthSignInListener(): OnCompleteListener<AuthResult> {
        return OnCompleteListener { task ->
            if (task.isSuccessful) {
                val intent = Intent(this@AuthActivity, HomeActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }

    private fun hideKeyboard() {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
        if (imm != null && window.decorView.windowToken != null) {
            imm.hideSoftInputFromWindow(window.decorView.windowToken, 0)
        }
    }

    override fun onBackPressed() {
        finishAffinity()
    }

    override fun onDestroy() {
        super.onDestroy()
        sfx.release()
    }
}
