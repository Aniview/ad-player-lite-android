package com.adservrs.adplayer.lite.example.examples

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.adservrs.adplayer.lite.AdPlayerInReadController
import com.adservrs.adplayer.lite.AdPlayerView
import com.adservrs.adplayer.lite.example.PUB_ID
import com.adservrs.adplayer.lite.example.TAG_ID

@SuppressLint("UnusedBoxWithConstraintsScope")
@Composable
fun MasterHeadExample(modifier: Modifier) {
    val density = LocalDensity.current
    val controller = remember { mutableStateOf<AdPlayerInReadController?>(null) }

    BoxWithConstraints(modifier) {
        val targetWidth = maxWidth.value
        val targetHeight = (targetWidth - 128) * 9 / 16

        Box(
            modifier = modifier
                .size(targetWidth.dp, targetHeight.dp)
                .background(Color.Black),
        ) {
            AndroidView(
                factory = {
                    val view = AdPlayerView(it)
                    controller.value = view.load(pubId = PUB_ID, tagId = TAG_ID)
                    view.looseConstraints = true
                    view.alignment = 1f
                    view.featheringLeading = 128 * density.density
                    view
                },
                modifier = Modifier.fillMaxSize(),
            )

            Button(
                onClick = { controller.value?.toggleFullscreen() },
                modifier = Modifier
                    .padding(horizontal = 16.dp, vertical = 12.dp)
                    .align(Alignment.BottomStart),
            ) {
                Text("Explore")
            }
        }
    }
}
