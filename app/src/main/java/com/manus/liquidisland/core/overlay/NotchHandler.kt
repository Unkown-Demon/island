package com.manus.liquidisland.core.overlay

import android.app.Activity
import android.os.Build
import android.view.WindowInsets
import android.util.Log

/**
 * Senior Engineer Note:
 * For Redmi 10C (Android 10), we use the DisplayCutout API.
 * Since it's a waterdrop notch, we need the safe insets to ensure
 * the "Island" starts exactly where the notch ends or wraps around it.
 */
class NotchHandler(private val activity: Activity) {

    fun getNotchDimensions(onResult: (rect: android.graphics.Rect?) -> Unit) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            activity.window.decorView.post {
                val insets = activity.window.decorView.rootWindowInsets
                val cutout = insets?.displayCutout
                if (cutout != null && cutout.boundingRects.isNotEmpty()) {
                    val notchRect = cutout.boundingRects[0]
                    Log.d("NotchHandler", "Notch detected: $notchRect")
                    onResult(notchRect)
                } else {
                    // Fallback for devices where cutout isn't reported correctly
                    onResult(null)
                }
            }
        } else {
            onResult(null)
        }
    }
}
