package com.synapse.social.studioasinc

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.WindowManager
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.synapse.social.studioasinc.CheckpermissionActivity
import com.synapse.social.studioasinc.CenterCropLinearLayoutNoEffect
import java.util.ArrayList
import java.util.HashMap

class MainActivity : AppCompatActivity() {

    private var commentsListMap: ArrayList<HashMap<String, Any>> = ArrayList() // Changed Object to Any

    private lateinit var body: CenterCropLinearLayoutNoEffect
    private lateinit var linear1: LinearLayout
    private lateinit var middleLayout: LinearLayout
    private lateinit var linear3: LinearLayout
    private lateinit var app_logo: ImageView
    private lateinit var imageview4: ImageView

    private lateinit var auth: FirebaseAuth

    // OnCompleteListener properties are not typically stored as separate fields in Kotlin
    // when they are simple lambdas. They are usually defined inline.
    // However, if they were complex or reused, they could be defined as private vals or funs.
    // For this conversion, we will define them inline within initialize().

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main)
        initialize(savedInstanceState)
        FirebaseApp.initializeApp(this)
        initializeLogic()
    }

    private fun initialize(savedInstanceState: Bundle?) {
        body = findViewById(R.id.body)
        linear1 = findViewById(R.id.linear1)
        middleLayout = findViewById(R.id.middleLayout)
        linear3 = findViewById(R.id.linear3)
        app_logo = findViewById(R.id.app_logo)
        imageview4 = findViewById(R.id.imageview4)
        auth = FirebaseAuth.getInstance()

        app_logo.setOnLongClickListener {
            val currentUserEmail = FirebaseAuth.getInstance().currentUser?.email
            if (currentUserEmail == "mashikahamed0@gmail.com") {
                if (FirebaseAuth.getInstance().currentUser != null) {
                    finish()
                } else {
                    finish()
                }
            } else {
                // Do nothing or handle other cases
            }
            true // Return true to indicate the long click was consumed
        }

        // The following OnCompleteListener definitions are largely empty in the original Java code.
        // In Kotlin, for simple logging or no-op, they would look like this.
        // If they had more logic, they'd be lambdas.
        val authUpdateEmailListener = OnCompleteListener<Void> { task ->
            val success = task.isSuccessful
            val errorMessage = task.exception?.message ?: ""
            // Handle success/error if needed
        }

        val authUpdatePasswordListener = OnCompleteListener<Void> { task ->
            val success = task.isSuccessful
            val errorMessage = task.exception?.message ?: ""
            // Handle success/error if needed
        }

        val authEmailVerificationSentListener = OnCompleteListener<Void> { task ->
            val success = task.isSuccessful
            val errorMessage = task.exception?.message ?: ""
            // Handle success/error if needed
        }

        val authDeleteUserListener = OnCompleteListener<Void> { task ->
            val success = task.isSuccessful
            val errorMessage = task.exception?.message ?: ""
            // Handle success/error if needed
        }

        val authPhoneAuthListener = OnCompleteListener<AuthResult> { task ->
            val success = task.isSuccessful
            val errorMessage = task.exception?.message ?: ""
            // Handle success/error if needed
        }

        val authUpdateProfileListener = OnCompleteListener<Void> { task ->
            val success = task.isSuccessful
            val errorMessage = task.exception?.message ?: ""
            // Handle success/error if needed
        }

        val authGoogleSignInListener = OnCompleteListener<AuthResult> { task ->
            val success = task.isSuccessful
            val errorMessage = task.exception?.message ?: ""
            // Handle success/error if needed
        }

        val authCreateUserListener = OnCompleteListener<AuthResult> { task ->
            val success = task.isSuccessful
            val errorMessage = task.exception?.message ?: ""
            // Handle success/error if needed
        }

        val authSignInListener = OnCompleteListener<AuthResult> { task ->
            val success = task.isSuccessful
            val errorMessage = task.exception?.message ?: ""
            // Handle success/error if needed
        }

        val authResetPasswordListener = OnCompleteListener<Void> { task ->
            val success = task.isSuccessful
            // Handle success/error if needed
        }
    }

    private fun initializeLogic() {
        Handler(Looper.getMainLooper()).postDelayed({
            val currentUser = FirebaseAuth.getInstance().currentUser
            if (currentUser != null) {
                val checkUser = FirebaseDatabase.getInstance()
                    .getReference("skyline/users")
                    .child(currentUser.uid)

                checkUser.addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        if (dataSnapshot.exists()) {
                            val banned = dataSnapshot.child("banned").getValue(String::class.java)
                            if ("false" == banned) {
                                val intent = Intent(this@MainActivity, HomeActivity::class.java)
                                startActivity(intent)
                                finish()
                            } else {
                                // User is banned — handle appropriately
                                Toast.makeText(this@MainActivity, "You are banned.", Toast.LENGTH_LONG).show()
                                FirebaseAuth.getInstance().signOut() // optional
                            }
                        } else {
                            val intent = Intent(this@MainActivity, CompleteProfileActivity::class.java)
                            startActivity(intent)
                            finish()
                        }
                    }

                    override fun onCancelled(databaseError: DatabaseError) {
                        Toast.makeText(this@MainActivity, "Database error: " + databaseError.message, Toast.LENGTH_SHORT).show()
                    }
                })
            } else {
                val intent = Intent(this@MainActivity, AuthActivity::class.java)
                startActivity(intent)
            }
        }, 500) // 500ms delay

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            val w = window
            w.setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
            )
        }
    }

    override fun onBackPressed() {
        finishAffinity()
    }
}
