package com.adservrs.adplayer.lite.example.examples

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import com.adservrs.adplayer.lite.AdPlayerClickThroughInterceptor
import com.adservrs.adplayer.lite.AdPlayerView
import com.adservrs.adplayer.lite.example.PUB_ID
import com.adservrs.adplayer.lite.example.TAG_ID

@Composable
fun ClickThroughInterceptorExample(modifier: Modifier) {
    Box(modifier = modifier) {
        AndroidView(
            factory = {
                val view = AdPlayerView(it)
                val controller = view.load(pubId = PUB_ID, tagId = TAG_ID)
                controller.clickThroughInterceptor.value = AdPlayerClickThroughInterceptor { uri ->
                    Toast.makeText(it, "$uri", Toast.LENGTH_SHORT).show()
                    true
                }
                view
            },
            onRelease = {
                it.release()
            },
            modifier = Modifier.fillMaxSize(),
        )
    }
}
