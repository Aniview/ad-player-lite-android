package com.adservrs.adplayer.lite.example.examples

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import com.adservrs.adplayer.lite.AdPlayerView
import com.adservrs.adplayer.lite.example.PUB_ID
import com.adservrs.adplayer.lite.example.TAG_ID
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.background
import androidx.compose.ui.graphics.Color
import android.view.ViewGroup

@Composable
fun SwitchableExample(modifier: Modifier) {
    var isTopContainer by remember { mutableStateOf(true) }

    // Create a single AdPlayerView instance that will be reused
    val playerView = remember {
        mutableStateOf<AdPlayerView?>(null)
    }


    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        // Top Container
        PlayerContainer(
            modifier = Modifier.weight(1f),
            isActive = isTopContainer,
            activeLabel = "Active Player Container (Top)",
            inactiveLabel = "Top Container (Inactive)",
            playerView = playerView
        )

        Button(
            onClick = { isTopContainer = !isTopContainer },
            modifier = Modifier.align(Alignment.CenterHorizontally).padding(16.dp)
        ) {
            Text("Switch View")
        }

        // Bottom Container
        PlayerContainer(
            modifier = Modifier.weight(1f),
            isActive = !isTopContainer,
            activeLabel = "Active Player Container (Bottom)",
            inactiveLabel = "Bottom Container (Inactive)",
            playerView = playerView
        )
    }

    // Release the player when the entire SwitchableExample is disposed
    DisposableEffect(Unit) {
        onDispose {
            playerView.value?.release()
        }
    }
}

@Composable
fun PlayerContainer(
    modifier: Modifier,
    isActive: Boolean,
    activeLabel: String,
    inactiveLabel: String,
    playerView: MutableState<AdPlayerView?>
) {
    Box(
        modifier = modifier
            .background(
                if (isActive) Color.Green.copy(alpha = 0.3f)
                else Color.LightGray.copy(alpha = 0.5f)
            )
    ) {
        if (isActive) {
            VideoPlayerContainer(
                modifier = Modifier.fillMaxSize(),
                playerView = playerView
            )
            Text(
                text = activeLabel,
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(8.dp)
                    .background(Color.Black.copy(alpha = 0.7f))
                    .padding(4.dp),
                color = Color.White
            )
        } else {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = inactiveLabel,
                    modifier = Modifier
                        .background(Color.Black.copy(alpha = 0.7f))
                        .padding(16.dp),
                    color = Color.White
                )
            }
        }
    }
}

@Composable
fun VideoPlayerContainer(
    modifier: Modifier,
    playerView: MutableState<AdPlayerView?>
) {
    AndroidView(
        factory = { context ->
            // Always create the player if it doesn't exist
            val view = playerView.value ?: run {
                val newView = AdPlayerView(context)
                playerView.value = newView
                newView.load(pubId = PUB_ID, tagId = TAG_ID)
                newView
            }

            // Remove from current parent if it has one
            (view.parent as? ViewGroup)?.removeView(view)
            view
        },
        update = { view ->
            // Ensure the view is detached from any previous parent when switching
            (view.parent as? ViewGroup)?.removeView(view)
        },
        modifier = modifier,
    )
}
