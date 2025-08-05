package com.example.myapplication.utils

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidViewBinding
import androidx.core.net.toUri
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import com.example.myapplication.databinding.PlayerViewLayoutBinding

@SuppressLint("UnsafeOptInUsageError")
@Composable
fun VideoPlayer(
    modifier: Modifier = Modifier,
    video: Int,
    onClick: () -> Unit
) {
    val context = LocalContext.current

    val exoPlayer = remember {
        ExoPlayer.Builder(context).build().apply {
            val mediaItem = MediaItem.fromUri(
                "android.resource://${context.packageName}/${video}".toUri()
            )
            setMediaItem(mediaItem)
            repeatMode = Player.REPEAT_MODE_ONE
            playWhenReady = true
            volume = 0f
            prepare()
        }
    }

    Box(
        modifier = modifier
            .clickable { onClick.invoke() },
        contentAlignment = Alignment.Center
    ) {
        DisposableEffect(Unit) {
            onDispose {
                exoPlayer.release()
            }
        }

        AndroidViewBinding(
            factory = { inflater, parent, attachToParent ->
                val binding = PlayerViewLayoutBinding.inflate(inflater, parent, attachToParent)
                binding.root.player = exoPlayer
                binding
            },
            modifier = Modifier.fillMaxSize()
        )
    }
}