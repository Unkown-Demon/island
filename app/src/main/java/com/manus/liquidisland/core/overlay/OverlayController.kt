package com.manus.liquidisland.core.overlay

import android.content.Context
import android.graphics.PixelFormat
import android.view.Gravity
import android.view.View
import android.view.WindowManager
import android.os.Build

/**
 * Senior Engineer Note:
 * Using TYPE_APPLICATION_OVERLAY is mandatory for Android 10 (API 29).
 * We use FLAG_NOT_FOCUSABLE to allow touches to pass through to the app below
 * unless the island is expanded.
 */
class OverlayController(private val context: Context) {

    private val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
    private var overlayView: View? = null

    fun showOverlay(view: View) {
        val params = WindowManager.LayoutParams(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or
                    WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN or
                    WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            PixelFormat.TRANSLUCENT
        ).apply {
            gravity = Gravity.TOP or Gravity.CENTER_HORIZONTAL
            // Position it exactly at the top to merge with the notch
            y = 0 
        }

        overlayView = view
        windowManager.addView(view, params)
    }

    fun updateOverlaySize(width: Int, height: Int) {
        overlayView?.let {
            val params = it.layoutParams as WindowManager.LayoutParams
            params.width = width
            params.height = height
            windowManager.updateViewLayout(it, params)
        }
    }

    fun removeOverlay() {
        overlayView?.let {
            windowManager.removeView(it)
            overlayView = null
        }
    }
}
