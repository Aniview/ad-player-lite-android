package com.adservrs.adplayer.lite.example.examples

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.net.toUri
import com.adservrs.adplayer.lite.AdPlayer
import com.adservrs.adplayer.lite.AdPlayerPreLoader
import com.adservrs.adplayer.lite.AdPlayerScaleType
import com.adservrs.adplayer.lite.AdPlayerView
import com.adservrs.adplayer.lite.example.PUB_ID
import com.adservrs.adplayer.lite.example.TAG_ID

private const val IMAGE = "https://play.aniview.com/61ad94b8a9ab494a4a51bce7/6abba2785dd854f11208b566/Aniviewlogo.png"

@Composable
fun PreloadingNativeExample(modifier: Modifier) {
    Box(modifier = modifier) {
        AndroidView(
            factory = { context ->
                val tag = AdPlayer.getTag(
                    context = context,
                    pubId = PUB_ID,
                    tagId = TAG_ID,
                )

                val controller = tag.newInReadController {
                    it.preLoader = AdPlayerPreLoader.ImageUrl(
                        imageUrl = IMAGE,
                        scaleType = AdPlayerScaleType.Contain,
                        clickListener = AdPlayerPreLoader.clickThroughUrl("https://www.google.com/search?q=drawable".toUri())
                    )
                }

                val view = AdPlayerView(context)
                view.attachController(controller)
                view
            },
            onRelease = {
                it.release()
            },
            modifier = Modifier.fillMaxSize(),
        )
    }
}
