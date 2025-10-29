package com.adservrs.adplayer.lite.example.examples

import android.util.Size
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import com.adservrs.adplayer.lite.AdPlayer
import com.adservrs.adplayer.lite.AdPlayerInterstitialController
import com.adservrs.adplayer.lite.AdPlayerInterstitialDismissListener
import com.adservrs.adplayer.lite.AdPlayerState
import com.adservrs.adplayer.lite.example.PUB_ID
import com.adservrs.adplayer.lite.example.TAG_ID
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

@Composable
fun Preloading(modifier: Modifier) {
    val context = LocalContext.current
    val density = LocalDensity.current
    val configuration = LocalConfiguration.current

    val coroutineScope = rememberCoroutineScope()
    val preloading = remember { mutableStateOf(false) }
    val tag = remember { AdPlayer.getTag(context, pubId = PUB_ID, tagId = TAG_ID) }

    fun newInterstitialController(): AdPlayerInterstitialController {
        return tag.newInterstitialController {
            it.onDismissListener = AdPlayerInterstitialDismissListener { it.release() }
        }
    }

    fun launch() {
        newInterstitialController().launchInterstitial()
    }

    fun preloadAndLaunch() = coroutineScope.launch {
        try {
            preloading.value = true

            val controller = newInterstitialController()
            val size = Size(
                (configuration.screenWidthDp * density.density).roundToInt(),
                (configuration.screenHeightDp * density.density).roundToInt(),
            )
            controller.preload(size)
            controller.state.first { it !is AdPlayerState.Preparing }
            controller.launchInterstitial()
        } finally {
            preloading.value = false
        }
    }

    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.Top),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        Button(enabled = !preloading.value, onClick = ::launch) {
            Text("Launch")
        }
        Button(enabled = !preloading.value, onClick = ::preloadAndLaunch) {
            Text("Preload And Launch")
        }
    }
}
