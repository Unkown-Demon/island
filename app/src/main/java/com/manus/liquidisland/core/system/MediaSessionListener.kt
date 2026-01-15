package com.manus.liquidisland.core.system

import android.content.ComponentName
import android.content.Context
import android.media.MediaMetadata
import android.media.session.MediaController
import android.media.session.MediaSessionManager
import android.media.session.PlaybackState
import android.util.Log

/**
 * Senior Engineer Note:
 * This listener captures active media sessions (Spotify, YouTube, etc.)
 * to display playback info in the Island.
 */
class MediaSessionListener(private val context: Context) {

    private val mediaManager = context.getSystemService(Context.MEDIA_SESSION_SERVICE) as MediaSessionManager
    private val listener = MediaSessionManager.OnActiveSessionsChangedListener { controllers ->
        controllers?.firstOrNull()?.let { updateMediaState(it) }
    }

    fun startListening(componentName: ComponentName) {
        mediaManager.addOnActiveSessionsChangedListener(listener, componentName)
        // Initial check
        mediaManager.getActiveSessions(componentName).firstOrNull()?.let { updateMediaState(it) }
    }

    private fun updateMediaState(controller: MediaController) {
        val metadata = controller.metadata
        val title = metadata?.getString(MediaMetadata.METADATA_KEY_TITLE)
        val artist = metadata?.getString(MediaMetadata.METADATA_KEY_ARTIST)
        val playbackState = controller.playbackState?.state
        
        Log.d("MediaSession", "Playing: $title by $artist (State: $playbackState)")
        // Push to Repository -> UI
    }
}
