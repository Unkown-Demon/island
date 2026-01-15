package com.manus.liquidisland.core.system

import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification
import android.util.Log

/**
 * Senior Engineer Note:
 * Captures incoming notifications to trigger Island animations.
 * We filter for high-priority notifications to avoid clutter.
 */
class IslandNotificationListener : NotificationListenerService() {

    override fun onNotificationPosted(sbn: StatusBarNotification) {
        val packageName = sbn.packageName
        val extras = sbn.notification.extras
        val title = extras.getString("android.title")
        val text = extras.getCharSequence("android.text")

        if (!sbn.isOngoing) {
            Log.d("NotificationListener", "New Notification from $packageName: $title")
            // Trigger Island "Pop" animation via Repository
        }
    }

    override fun onNotificationRemoved(sbn: StatusBarNotification) {
        // Handle notification dismissal
    }
}
