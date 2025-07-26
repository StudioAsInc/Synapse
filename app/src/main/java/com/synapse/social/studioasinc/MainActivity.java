package com.synapse.social.studioasinc

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.Window
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.synapse.social.studioasinc.databinding.ActivityMainBinding
import com.google.android.material.color.MaterialColors

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val auth by lazy { FirebaseAuth.getInstance() }

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        
        // Material 3 Theming
        window.apply {
            // Enable edge-to-edge
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                setFlags(
                    WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                    WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
                )
            }
            
            // Set navigation bar color (API 26+)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                navigationBarColor = MaterialColors.getColor(
                    this@MainActivity,
                    com.google.android.material.R.attr.colorSurface,
                    "Navigation bar color"
                )
            }
        }

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        FirebaseApp.initializeApp(this)
        initializeLogic()
        setupLongClickListeners()
    }

    private fun setupLongClickListeners() {
        binding.appLogo.setOnLongClickListener {
            auth.currentUser?.email?.takeIf { email -> 
                email == "mashikahamed0@gmail.com" 
            }?.let {
                finish()
            }
            true
        }
    }

    private fun initializeLogic() {
        Handler(Looper.getMainLooper()).postDelayed({
            auth.currentUser?.let { user ->
                checkUserStatus(user.uid)
            } ?: run {
                startActivity(Intent(this, AuthActivity::class.java))
                finish()
            }
        }, 500)
    }

    private fun checkUserStatus(uid: String) {
        FirebaseDatabase.getInstance()
            .getReference("skyline/users")
            .child(uid)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    when {
                        !snapshot.exists() -> {
                            startActivity(Intent(this@MainActivity, CompleteProfileActivity::class.java))
                            finish()
                        }
                        snapshot.child("banned").getValue(String::class.java) == "false" -> {
                            startActivity(Intent(this@MainActivity, HomeActivity::class.java))
                            finish()
                        }
                        else -> {
                            Toast.makeText(
                                this@MainActivity,
                                "You are banned.",
                                Toast.LENGTH_LONG
                            ).show()
                            auth.signOut()
                        }
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(
                        this@MainActivity,
                        "Database error: ${error.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })
    }

    override fun onBackPressed() {
        finishAffinity()
    }
}