package com.adservrs.adplayer.lite.example.examples

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import com.adservrs.adplayer.lite.AdPlayerView
import com.adservrs.adplayer.lite.example.PUB_ID
import com.adservrs.adplayer.lite.example.TAG_ID

@Composable
fun SimpleExample(modifier: Modifier) {
    Box(modifier = modifier) {
        AndroidView(
            factory = {
                val view = AdPlayerView(it)
                view.load(pubId = PUB_ID, tagId = TAG_ID)
                view
            },
            onRelease = {
                it.release()
            },
            modifier = Modifier.fillMaxSize(),
        )
    }
}
