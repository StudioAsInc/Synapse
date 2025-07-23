package com.synapse.social.studioasinc.permissionreq

import android.app.Activity
import android.content.Intent
import android.os.Build
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import android.Manifest
import android.content.pm.PackageManager
import java.util.Timer
import java.util.TimerTask

class AskPermission(private val activity: Activity) {
    private val timer = Timer()
    private var permissionCheckCount = 0
    private val maxPermissionChecks = 5 // Max times to check before giving up

    companion object {
        const val PERMISSION_REQUEST_CODE = 1000
    }

    fun checkAndRequestPermissions() {
        if (areAllPermissionsGranted()) {
            startMainActivity()
            return
        }

        requestNeededPermissions()

        // Start checking periodically (fallback for Sketchware's limitation)
        timer.schedule(object : TimerTask() {
            override fun run() {
                activity.runOnUiThread {
                    permissionCheckCount++
                    if (areAllPermissionsGranted()) {
                        timer.cancel()
                        startMainActivity()
                    } else if (permissionCheckCount >= maxPermissionChecks) {
                        timer.cancel()
                        showPermissionDeniedMessage()
                    }
                }
            }
        }, 1000, 1000) // Check every 1 second
    }

    private fun areAllPermissionsGranted(): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            ContextCompat.checkSelfPermission(activity, Manifest.permission.READ_MEDIA_IMAGES) == PackageManager.PERMISSION_GRANTED &&
            ContextCompat.checkSelfPermission(activity, Manifest.permission.READ_MEDIA_VIDEO) == PackageManager.PERMISSION_GRANTED &&
            ContextCompat.checkSelfPermission(activity, Manifest.permission.READ_MEDIA_AUDIO) == PackageManager.PERMISSION_GRANTED
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            ContextCompat.checkSelfPermission(activity, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED &&
            ContextCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
        } else {
            true
        }
    }

    private fun requestNeededPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            ActivityCompat.requestPermissions(
                activity,
                arrayOf(
                    Manifest.permission.READ_MEDIA_IMAGES,
                    Manifest.permission.READ_MEDIA_VIDEO,
                    Manifest.permission.READ_MEDIA_AUDIO
                ),
                PERMISSION_REQUEST_CODE
            )
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            ActivityCompat.requestPermissions(
                activity,
                arrayOf(
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                ),
                PERMISSION_REQUEST_CODE
            )
        }
    }

    private fun startMainActivity() {
        val intent = Intent(activity, Class.forName("com.synapse.social.studioasinc.MainActivity"))
        activity.startActivity(intent)
        activity.finish()
    }

    private fun showPermissionDeniedMessage() {
        // You can customize this message
     //   SketchwareUtil.showMessage(activity, "App needs permissions to work properly!")
    }

    fun cleanup() {
        timer.cancel()
    }
}