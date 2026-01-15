package com.manus.liquidisland.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import com.manus.liquidisland.service.IslandOverlayService

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Column {
                Text("LiquidIsland Configuration")
                Button(onClick = { checkOverlayPermission() }) {
                    Text("Grant Overlay Permission")
                }
                Button(onClick = { startIslandService() }) {
                    Text("Start Island Service")
                }
            }
        }
    }

    private fun checkOverlayPermission() {
        if (!Settings.canDrawOverlays(this)) {
            val intent = Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:$packageName"))
            startActivity(intent)
        }
    }

    private fun startIslandService() {
        val intent = Intent(this, IslandOverlayService::class.java)
        startForegroundService(intent)
    }
}
