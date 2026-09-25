package com.adservrs.adplayer.lite.example.examples

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import com.adservrs.adplayer.lite.AdPlayer
import com.adservrs.adplayer.lite.AdPlayerView
import org.json.JSONObject

private const val IMAGE = "https://play.aniview.com/61ad94b8a9ab494a4a51bce7/6ab654dfae603ab00d068686/image%20(2).png"

@Composable
fun PreloadBannerExample(modifier: Modifier) {
    Box(modifier = modifier) {
        AndroidView(
            factory = { context ->
                val tag = AdPlayer.getTag(
                    context = context,
                    pubId = "61235240ea13f3415e2ab496",
                    tagId = "673eff4d316e27c6680b49fb",
                )

                val controller = tag.newInReadController {
                    val preload = JSONObject()
                        .put("type", "image")
                        .put("link", IMAGE)

                    it.initialConfig = JSONObject().put("preloader", preload)
                }

                val view = AdPlayerView(context)
                view.attachController(controller)
                view
            },
            onRelease = {
                it.release()
            },
            modifier = Modifier
                .fillMaxSize()
                .aspectRatio(16f / 9f),
        )
    }
}
