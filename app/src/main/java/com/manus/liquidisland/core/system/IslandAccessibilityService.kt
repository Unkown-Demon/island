package com.manus.liquidisland.core.system

import android.accessibilityservice.AccessibilityService
import android.view.accessibility.AccessibilityEvent
import android.util.Log

/**
 * Senior Engineer Note:
 * This service is the "ears" of the Dynamic Island. It listens for window changes,
 * focus changes, and system UI updates that aren't covered by standard APIs.
 */
class IslandAccessibilityService : AccessibilityService() {

    override fun onAccessibilityEvent(event: AccessibilityEvent) {
        when (event.eventType) {
            AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED -> {
                // Detect app switches or system dialogs
                val packageName = event.packageName?.toString()
                Log.d("IslandAccessibility", "Window changed: $packageName")
                // Update Island state via Repository
            }
            AccessibilityEvent.TYPE_NOTIFICATION_STATE_CHANGED -> {
                // Fallback for notifications if ListenerService is restricted
            }
        }
    }

    override fun onInterrupt() {
        // Handle service interruption
    }

    override fun onServiceConnected() {
        super.onServiceConnected()
        Log.i("IslandAccessibility", "Service Connected")
        // Configure service info programmatically if needed
    }
}
